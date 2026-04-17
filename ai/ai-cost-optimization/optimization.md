# LLM Cost Optimization — Principal Engineer Notes

## Executive Principle
Treat cost as an emergent property of the system. Most savings come from eliminating unnecessary work: fewer model invocations, fewer tokens per invocation, fewer retries, and fewer “expensive paths” taken by default. Model selection is the final tuning knob after the system is efficient.

## Operating Model (How to Run Cost Like an Engineering Problem)
- Establish a **unit cost**: cost per successful task, cost per resolved ticket, cost per generated artifact.
- Track **leading indicators**: calls per task, tokens per task, retrieval depth, cache hit rate, retry rate, tool-call rate.
- Separate **fixed vs variable cost**: infra, vector DB storage/IO, embedding rebuilds, LLM input/output.
- Optimize only with **quality gates**: do not trade savings for regressions in correctness, latency, or safety.

## The Optimization Order (Non-Negotiable)
1. **Measure**: instrument and attribute spend across the pipeline.
2. **Route**: avoid LLM/RAG for requests that don’t need them.
3. **Reduce tokens**: clean sources, tighten retrieval, cap output.
4. **Cache**: reuse answers at the right abstraction level.
5. **Tier models**: cheapest capable model per route, with escalation.

## Cost Anatomy (Where the Money Goes)
Cost is dominated by two multipliers:
- **Call volume**: QPS × routes that invoke LLM × retries
- **Token volume**: input tokens (system + user + context) + output tokens

Typical buckets to attribute:
- LLM generation (input/output)
- Embeddings (initial + re-embedding cadence)
- Retrieval (vector DB queries, rerankers, storage)
- Infrastructure (compute, networking, observability)
- Retries/errors (timeouts, 429s, tool failures)

## Simple Cost Equation (Mental Model)
Monthly cost is approximately:
- **LLM** = Σ(route) [calls(route) × (in_tokens + out_tokens) × $/token(model(route))]
- **Embeddings** = embedded_tokens × $/token(embed_model) + re-embed churn
- **Retrieval/Infra** = queries + storage + compute
- **Waste tax** = retries + over-retrieval + oversized outputs

Your optimization goal is to reduce the multipliers first: **calls**, then **tokens**, then **retries**.

## Governance & Cost Controls (Keep Optimizations in Place)
- Enforce **per-tenant/per-workspace budgets** and quotas (daily/monthly) with explicit “degraded mode” behaviors.
- Do **spend attribution** by route, tenant, feature flag, and model so cost has an owner and a backlog.
- Add **anomaly detection** (sudden spikes in tokens/task, retries, tool calls, cache miss rate) with alerting.
- Gate new features behind **cost guardrails**: maximum context budget, maximum tool calls, maximum retries.

## Reliability Engineering (Retries Are a Cost Multiplier)
- Make all side-effecting actions **idempotent**; dedupe retries by request ID.
- Use **timeouts, circuit breakers, and exponential backoff with jitter**; cap retries by route.
- Implement “fail cheap” fallbacks: tool-only response, cached response, short answer, or explicit “try again” with no LLM.
- Track and cap **429/5xx retry storms**; retries should be visible in cost dashboards.

## Load Shaping (Prevent Cost Blowups Under Traffic)
- Collapse **duplicate in-flight requests** (singleflight) and coalesce bursts on hot keys.
- Batch work where possible: **embedding batches**, vector DB queries, reranker invocations.
- Control concurrency for expensive routes (RAG + LLM), and prioritize low-cost routes under load.
- Prefer **streaming + early stop** when UX allows; stop generation once the user’s need is met.

## Retrieval Economics (RAG Is a Pipeline, Not a Toggle)
- Use **multi-stage retrieval**: cheap coarse recall → dedup → rerank → strict context budget.
- Attribute reranker and retrieval cost separately; rerankers can dominate spend if unbounded.
- Enforce **context budgets centrally** (hard token cap) and validate in logs for every request.
- Cache intermediate artifacts: retrieval results, tool outputs, reranker results where safe.

## Embedding Lifecycle Management (Control Churn)
- Avoid full rebuilds by default: use **change detection (hashing)** and incremental re-embedding.
- Deduplicate chunks across sources; track chunk lineage/versioning to prevent duplicate indexing.
- Define freshness SLAs per corpus and route; do not re-embed “just because” without measured value.

## Conversation State & Context Compression
- Summarize/compact long threads into a **state representation** (facts, constraints, open items), not raw history.
- Use **extractive summaries and citations** for grounding; avoid shipping entire chats as context.
- Allocate context by route: support chat ≠ research assistant ≠ coding agent.

## Evaluation & Rollout (Optimize on the Frontier, Not by Intuition)
- Maintain a **golden set** and score routes on correctness, latency, safety, and unit cost.
- Ship changes via canary/A-B, and require **non-regression gates** for quality and cost.
- Log escalation reasons and failure modes; use them to refine routing, prompts, and retrieval.

## High-Leverage Design Moves (What Actually Works)

### 1) Stop “model swap” as the primary strategy
Swapping models without changing the system usually yields marginal savings because call/token volume dominates. In many products, the “default path” is the problem (everything going through RAG + LLM).

### 2) Pre-embedding hygiene (eliminate token garbage)
Do not embed boilerplate:
- headers/footers, pagination, legal banners, navigation
- duplicated sections across documents
- non-semantic markup and noisy whitespace

Impact:
- fewer embedded tokens (direct cost reduction)
- higher retrieval precision (downstream token reduction due to less context)

### 3) Routing before reasoning (classification + policy)
Define routes and make them explicit:
- **Trivial**: greeting/FAQ → template/static response
- **Deterministic**: account lookup, status → tools/DB only (no LLM)
- **Contextual**: explanation with sources → retrieval + LLM
- **High-risk**: sensitive actions → stricter policy + tool gating + audit

Routing should be cheap, fast, and measurable. The output of routing is a policy decision: which tools, which model tier, which context budget, which output format.

### 4) Context budgets (you’re sending too much)
Over-retrieval is a hidden tax and often harms quality. Apply:
- tight top-k with a hard **context token budget**
- reranking to select fewer, better chunks
- chunk deduplication and overlap control
- query rewriting only when it proves value (and measure it)

Rule: context is a scarce resource; allocate it like CPU.

### 5) Output control (stop paying for prose)
Outputs should be format-first:
- classification: label only
- extraction: JSON with a schema
- answer: N bullets, max M tokens, no tangents
- long-form: only on routes that justify it

If the UI needs detail, use progressive disclosure: start short, expand on demand.

### 6) Semantic caching (cache meaning, not strings)
Most products have heavy repetition. Cache at the right abstraction:
- exact cache (prompt hash) for deterministic calls
- semantic cache (embedding similarity) for paraphrases
- partial cache for expensive sub-steps (retrieval results, tool outputs)

Guardrails:
- partition by tenant/user where required
- TTL aligned to data freshness
- invalidate on knowledge updates and policy changes

### 7) Model tiering (last mile optimization)
After call/token volume is controlled, tier models by route:
- **cheap** for classification, routing, templating, simple Q&A
- **mid** for summaries, extraction, grounded explanations
- **top** for complex reasoning, ambiguous edge cases, high-stakes decisions

Always keep an escalation path and log when escalation was needed; that’s your signal to adjust routing or prompts.

## Implementation Checklist (What “Done” Looks Like)
- Per-route dashboards: calls, tokens, latency, retries, success, cache hit rate, quality score
- Spend governance: budgets/quotas, attribution, anomaly alerts, degraded-mode policies
- Context budget enforcement (hard cap) and measured top-k/reranking policy
- Deterministic tool-first routes for lookups and actions
- Output schemas and token caps per route
- Semantic cache with safety partitions and invalidation strategy
- Retry and load controls: idempotency, backoff/jitter, circuit breakers, concurrency caps, singleflight
- Embedding lifecycle: dedup/versioning, incremental re-embed, freshness SLAs
- Eval gates: golden set, canary/A-B, explicit cost-quality acceptance criteria
- Tiered models with evaluation gates and automatic escalation

## Common Failure Modes
- “Everything goes to RAG” default
- No attribution: can’t tell whether spend is retrieval, tokens, or retries
- Over-retrieval + long prompts + verbose outputs (triple compounding)
- Cache without safety boundaries (privacy leaks, stale answers)
- Tiering without evaluation (silent quality degradation)
- Unbounded retries and tool-call cascades under load
- Re-embedding churn without change detection or measurable impact
