# Multi-Agent Coding Pipeline — Java

A minimal, self-contained example of a **multi-agent system** built on the
Anthropic API. No SDKs, no Spring, no extra frameworks — just Java 21 and
`java.net.http.HttpClient`.

---

## Architecture

```
User Task
    │
    ▼
┌────────────────────────────────────────────────────────────┐
│                    OrchestratorAgent                        │
│                                                            │
│  Stage 1: PLAN    Convert task → structured spec           │
│  Stage 2: CODE    Dispatch to CoderAgent                   │
│  Stage 3: REVIEW  ReviewAgent → APPROVE or REVISE loop     │
│  Stage 4: TEST    TestAgent writes JUnit 5 tests           │
│  Stage 5: ASSEMBLE Format final output                     │
└────────┬────────────────┬─────────────────┬───────────────┘
         │                │                 │
    ┌────▼────┐     ┌─────▼─────┐     ┌────▼─────┐
    │  Coder  │◄───►│  Reviewer │     │  Tester  │
    │  Agent  │     │   Agent   │     │  Agent   │
    └─────────┘     └───────────┘     └──────────┘
```

### Agent roles

| Agent | System prompt focus | Multi-turn? |
|---|---|---|
| `OrchestratorAgent` | Team lead — plans, routes, assembles | Yes (two calls) |
| `CoderAgent` | Senior engineer — writes Java code | Yes (initial + revision) |
| `ReviewAgent` | Code reviewer — structured critique | No (single review) |
| `TestAgent` | QA engineer — JUnit 5 test suites | No (single generation) |

### Key patterns demonstrated

- **Pipeline pattern** — fixed sequence of agents, each consuming the prior output
- **Revision loop** — Orchestrator checks `ReviewAgent.isApproved()` and loops back to `CoderAgent` if needed (capped at `MAX_REVISION_LOOPS`)
- **Persistent conversation history** — `BaseAgent` maintains the message list per agent, enabling multi-turn refinement within a stage
- **Shared HTTP client** — all agents share one `AnthropicClient` instance; easy to swap for the official Anthropic SDK
- **Typed messages** — `AgentMessage` record carries `agentName`, `role`, and `content` through the pipeline

---

## Setup

### Prerequisites
- Java 21+
- Maven 3.8+
- Anthropic API key

### Run

```bash
export ANTHROPIC_API_KEY=sk-ant-...

cd multiagent
mvn compile exec:java
```

### Expected output

```
══════════════════════════════════════════════════════════════════════
  MULTI-AGENT CODING PIPELINE
══════════════════════════════════════════════════════════════════════
Task: Implement a Java class called RateLimiter...

── Stage 1: PLANNING ──────────────────────────────────
[Orchestrator   ] planning task…
[Orchestrator   ] spec ready (843 chars)

── Stage 2: CODING ────────────────────────────────────
[CoderAgent     ] processing: Implement a thread-safe RateLimiter…
[CoderAgent     ] → replied (1247 chars)

── Stage 3: REVIEW ────────────────────────────────────
[Orchestrator   ] review pass 1/2
[ReviewAgent    ] reviewing code (1247 chars)
[ReviewAgent    ] → replied (623 chars)

── Review ─────────────────────────────────────────────
## SCORE
9/10
...
## VERDICT
APPROVE
───────────────────────────────────────────────────────
[Orchestrator   ] APPROVED — proceeding to tests.

── Stage 4: TESTING ───────────────────────────────────
[TestAgent      ] generating tests for code (1247 chars)
[TestAgent      ] → replied (1893 chars)

── Stage 5: ASSEMBLING ────────────────────────────────
[Orchestrator   ] assembling final output…

══════════════════════════════════════════════════════════════════════
  FINAL OUTPUT
══════════════════════════════════════════════════════════════════════
[assembled implementation + tests + summary]
```

---

## Project structure

```
multiagent/
├── pom.xml
└── src/main/java/com/example/multiagent/
    ├── Main.java                          ← entry point
    ├── model/
    │   └── AgentMessage.java              ← immutable message record
    ├── tool/
    │   └── AnthropicClient.java           ← HTTP wrapper (no SDK needed)
    ├── agent/
    │   ├── BaseAgent.java                 ← abstract base (history management)
    │   ├── CoderAgent.java                ← writes Java code
    │   ├── ReviewAgent.java               ← reviews + approves/revises
    │   └── TestAgent.java                 ← writes JUnit 5 tests
    └── orchestrator/
        └── OrchestratorAgent.java         ← pipeline coordinator
```

---

## Extending this example

### Add a new agent

1. Create `YourAgent extends BaseAgent`
2. Override `process(AgentMessage input)`
3. Add it to `OrchestratorAgent.execute()` at the appropriate stage

### Add parallel agents

```java
// Run CoderAgent and a SecurityAgent concurrently using virtual threads
try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    Future<AgentMessage> codeFuture     = executor.submit(() -> coder.process(spec));
    Future<AgentMessage> securityFuture = executor.submit(() -> security.process(spec));

    AgentMessage code     = codeFuture.get();
    AgentMessage security = securityFuture.get();
    // merge results...
}
```

### Add tool use (function calling)

Extend `AnthropicClient.chat()` to accept a `tools` JSON array and parse
`tool_use` blocks in the response — then route to Java methods and inject
`tool_result` messages back into the conversation history.

### Swap to the official Anthropic Java SDK

Replace `AnthropicClient` with the SDK client. The rest of the architecture
(`BaseAgent`, agents, orchestrator) is SDK-agnostic.

### Add a different pipeline shape

This example uses a **sequential pipeline**. Other shapes:
- **Fan-out**: Orchestrator dispatches to N agents in parallel, aggregates
- **DAG**: Agents declare dependencies; Orchestrator runs a topological sort
- **Supervisor loop**: Orchestrator keeps looping until a quality threshold is met

---

## Cost note

Each run makes ~6–8 API calls (plan + code + review + optional revision + test + assemble).
With `claude-sonnet-4-20250514` this costs approximately $0.05–$0.15 per run.
