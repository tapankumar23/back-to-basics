# DDIA Animated HTML Series — Design Spec

**Date:** 2026-04-20  
**Author:** Tapan  
**Status:** Approved

---

## Overview

Create a series of animated, self-contained HTML visualizations covering every concept in *Designing Data-Intensive Applications* by Martin Kleppmann — modeled exactly after the `system-design-concepts/` series already in this repo.

---

## Directory & File Structure

All files live in the existing `data-intensive-application/` directory.

```
data-intensive-application/
├── Designing-Data-Intensive-Application.pdf  (existing)
├── 00_index.html
├── 01_reliability_scalability.html
├── 02_data_models.html
├── 03_storage_retrieval_encoding.html
├── 04_replication.html
├── 05_partitioning.html
├── 06_transactions.html
├── 07_distributed_problems_consensus.html
└── 08_batch_stream_future.html
```

Total: 9 files (1 index + 8 content pages).

---

## File-by-File Content Plan

### `00_index.html` — Galaxy Navigation Map
- Accent: multi-color (matching other index pages)
- 8 stars clustered loosely by the book's 3 parts
- Load animations (not scroll-triggered, matching `system-design-concepts/00_index.html`)
- Click a star → navigates to that chapter file
- Cross-links to `../system-design-concepts/00_index.html` and `../coding-concepts/00_index.html`

---

### `01_reliability_scalability.html` — accent `#60a5fa` (blue)
**Source:** Chapter 1

Sections:
1. **Reliability** — hardware faults (RAID/redundancy animation), software errors (chaos monkey animation), human errors (dark deployment / canary release)
2. **Scalability** — load parameters defined, latency percentiles (p50/p95/p99) animated histogram, tail latency amplification in fan-out
3. **Throughput vs Response Time** — interactive slider showing trade-off under load
4. **Maintainability** — operability / simplicity / evolvability — coupling meter animation, abstraction layers

---

### `02_data_models.html` — accent `#f472b6` (pink)
**Source:** Chapter 2

Sections:
1. **Relational Model** — tables, joins, normalization, impedance mismatch ORM translation animation
2. **Document Model** — JSON tree, schema flexibility, locality advantage, one-to-many without joins
3. **Relational vs Document** — interactive toggle: same resume data rendered in each model side-by-side
4. **Graph Model** — property graph (nodes + edges with properties), triple-store (subject-predicate-object), Cypher vs SPARQL query animation
5. **Query Languages** — SQL declarative vs MapReduce imperative: same aggregation shown both ways
6. **Schema-on-read vs Schema-on-write** — animated comparison: schema enforcement at write time vs runtime parse

---

### `03_storage_retrieval_encoding.html` — accent `#fb923c` (orange)
**Source:** Chapters 3 + 4

Sections:
1. **Hash Index** — in-memory hashmap → append-only log, segment compaction + merging animation
2. **LSM-Tree + SSTables** — writes to MemTable → flush to SSTable → multi-level compaction cascade, bloom filter check on reads
3. **B-Trees** — node page reads/writes, node split on overflow, WAL (crash recovery animation)
4. **OLTP vs OLAP** — query pattern contrast (point lookups vs full scans), star schema, data warehouse ETL pipeline
5. **Column-Oriented Storage** — column compression (run-length encoding animation), vectorized CPU execution, sort orders
6. **Encoding Formats** — JSON/Thrift/Protobuf/Avro side-by-side byte size comparison, schema evolution: forward/backward compatibility matrix toggle

---

### `04_replication.html` — accent `#4ade80` (green)
**Source:** Chapter 5

Sections:
1. **Single-Leader Replication** — write → leader → replication log (statement-based / WAL / row-based) → followers animated flow
2. **Replication Lag Anomalies** — three timelines: read-your-own-writes violation, monotonic reads violation, consistent prefix reads violation — each with a fix
3. **Multi-Leader Replication** — multi-datacenter topology, conflict detection, resolution strategies: last-write-wins vs merge vs CRDT
4. **Leaderless Replication (Dynamo)** — quorum writes (w) + reads (r), w+r>n guarantee animation, sloppy quorum + hinted handoff
5. **Anti-Entropy & Read Repair** — version vectors, detecting stale replicas, Merkle tree sync

---

### `05_partitioning.html` — accent `#a78bfa` (violet)
**Source:** Chapter 6

Sections:
1. **Key-Range Partitioning** — sorted key ranges, manual boundaries, hot spot problem (all writes to one partition)
2. **Hash Partitioning** — consistent hash ring, virtual nodes, uniform distribution animation
3. **Secondary Indexes: Local vs Global** — local: scatter/gather query fan-out animation; global: distributed index update on write
4. **Rebalancing Strategies** — fixed partitions (move whole partition), dynamic splitting (when partition grows too large), proportional (Cassandra-style)
5. **Request Routing** — three approaches: client-aware routing, routing tier, ZooKeeper/gossip metadata service

---

### `06_transactions.html` — accent `#f59e0b` (amber)
**Source:** Chapter 7

Sections:
1. **ACID** — each letter as a card: Atomicity (all-or-nothing animation), Consistency (invariants), Isolation (concurrent tx lanes), Durability (crash + recovery animation)
2. **Weak Isolation: Read Committed** — dirty reads blocked, dirty writes blocked — animated tx timeline
3. **Snapshot Isolation / MVCC** — version chain per row, each transaction reads its consistent snapshot, read skew prevention
4. **Isolation Levels Ladder** — interactive: read uncommitted → read committed → repeatable read → serializable — anomalies that each level prevents
5. **Write Skew & Phantoms** — doctor on-call race condition step-by-step animation, phantom reads, materializing conflicts
6. **Serializability** — actual serial execution vs 2PL (shared/exclusive locks, deadlock animation) vs Serializable Snapshot Isolation (optimistic, detect conflict on commit, abort)

---

### `07_distributed_problems_consensus.html` — accent `#34d399` (emerald)
**Source:** Chapters 8 + 9

Sections:
1. **Partial Failures** — node crash vs network partition vs Byzantine faults — unreliable networks animation, packet loss/reorder/delay
2. **Unreliable Clocks** — wall clock drift animation, NTP sync, monotonic clocks, why you can't trust timestamps for ordering
3. **Logical Clocks** — Lamport timestamps step-by-step animation, happens-before relation, vector clocks
4. **Process Pauses** — GC stop-the-world causing lease expiry, response time uncertainty, fencing tokens
5. **Linearizability vs Serializability** — timeline diagram showing the subtle distinction; CAP theorem precise formulation (network partition always happens)
6. **Consensus** — 2PC coordinator failure (blocking problem animation), Raft: leader election + log replication step-by-step, epoch/term numbering
7. **Total Order Broadcast & Distributed Transactions** — XA transactions, how consensus relates to total order broadcast, ZooKeeper use cases

---

### `08_batch_stream_future.html` — accent `#818cf8` (indigo)
**Source:** Chapters 10 + 11 + 12

Sections:
1. **Unix Philosophy → MapReduce** — pipe chaining animation, fault tolerance via materializing intermediate state, immutable inputs
2. **MapReduce Internals** — map phase → shuffle (partition by key) → sort → reduce — animated data flow with multiple workers
3. **Dataflow Engines** — Spark/Flink: pipelining without materializing intermediate state, operator graph animation
4. **Stream Processing** — event log as source of truth, stream-table joins (enrichment animation), windowed aggregations (tumbling/sliding/session windows)
5. **Lambda vs Kappa Architecture** — toggle between: Lambda (batch + speed + serving layers) vs Kappa (single streaming path)
6. **Exactly-Once Semantics** — idempotence animation, distributed transactions in streams, atomic commit across stream + side effects
7. **The Future: Unbundling Databases** — dataflow as application architecture, derived data systems, end-to-end argument animation

---

## Technical Conventions

Identical to `system-design-concepts/` and `problem-solving/` series:

| Convention | Value |
|---|---|
| CDN libs | `anime.js` v3.2.1, `d3.js` v7, Google Fonts |
| Fonts | Inter (body), JetBrains Mono (code/labels) |
| Base CSS vars | `--bg: #0d1117`, `--bg-card: #161b22`, `--border: #30363d`, `--text: #e6edf3` |
| Animation triggers | `IntersectionObserver` at 25% threshold (index pages use load animations) |
| Animated properties | Only `transform` and `opacity` — never `width`, `height`, `top`, `left` |
| Progress bar | `transform: scaleX()` with `transform-origin: left center` |
| Code highlighting | CSS-only via `<span>` classes: `.kw .fn .str .cm .num .tp` |
| Scroll guard | `if (total <= 0) return;` before every scroll division |

### Per-section structure (each section card must include)
- Animated diagram area
- Explanation panel (2–3 lines)
- ≥1 interactive control (button / slider / toggle)
- Live trace panel showing invariants where relevant

### Navbar (every content page)
- Fixed top, chapter pager (prev/next)
- Back link to `00_index.html`
- Cross-links: `../system-design-concepts/00_index.html`, `../coding-concepts/00_index.html`
- In-page section anchors

### Footer
> Designing Data-Intensive Applications — Martin Kleppmann

---

## Out of Scope
- No build step, no package manager, no server
- No external images (all diagrams drawn in JS/SVG)
- No quiz mode or speed control (not part of existing series)

---

## Implementation Order

Generate in reading order — later files assume concepts from earlier ones are understood:

1. `00_index.html`
2. `01_reliability_scalability.html`
3. `02_data_models.html`
4. `03_storage_retrieval_encoding.html`
5. `04_replication.html`
6. `05_partitioning.html`
7. `06_transactions.html`
8. `07_distributed_problems_consensus.html`
9. `08_batch_stream_future.html`
