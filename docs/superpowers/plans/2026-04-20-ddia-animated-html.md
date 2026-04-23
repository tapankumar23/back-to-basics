# DDIA Animated HTML Series — Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Create 9 self-contained animated HTML files covering every concept in *Designing Data-Intensive Applications* by Martin Kleppmann, matching the visual style and conventions of the existing `system-design-concepts/` series.

**Architecture:** Each file is a single standalone HTML page (no build step, no server) using anime.js v3 for animations, d3.js v7 for data-driven diagrams, and IntersectionObserver for scroll-triggered section reveals. All files share the same dark theme base with a file-specific accent color.

**Tech Stack:** HTML5, CSS3 (custom properties, transforms only), vanilla JS, anime.js v3.2.1 (CDN), d3.js v7 (CDN), Google Fonts (Inter + JetBrains Mono)

---

## Shared Conventions (read once, apply everywhere)

### CSS Variables (every file)
```css
:root {
  --bg: #0d1117;
  --bg-card: #161b22;
  --bg-card-hover: #1c2330;
  --border: #30363d;
  --text: #e6edf3;
  --text-muted: #8b949e;
  --accent: /* file-specific */;
  --accent-dim: /* accent at 15% opacity */;
}
```

### Animation Rules (enforced)
- Only animate `transform` and `opacity` — **never** `width`, `height`, `top`, `left`, `margin`
- Progress bar: `transform: scaleX(ratio)` with `transform-origin: left center` — never `style.width`
- Scroll guard: `if (total <= 0) return;` before every division in scroll handlers
- `scale:` shorthand invalid in anime.js v3 — use `opacity` pulse or `translateY` instead
- Scroll triggers: `IntersectionObserver` at `threshold: 0.25`, `unobserve` after first trigger

### IntersectionObserver Pattern
```javascript
const observer = new IntersectionObserver((entries) => {
  entries.forEach(entry => {
    if (entry.isIntersecting) {
      animateSection(entry.target.dataset.section);
      observer.unobserve(entry.target);
    }
  });
}, { threshold: 0.25 });
document.querySelectorAll('.concept-section').forEach(el => observer.observe(el));
```

### Progress Bar Pattern
```javascript
const bar = document.getElementById('progress-bar');
window.addEventListener('scroll', () => {
  const total = document.body.scrollHeight - window.innerHeight;
  if (total <= 0) return;
  bar.style.transform = 'scaleX(' + (window.scrollY / total) + ')';
});
```

### Packet Animation Pattern (data flow dots along SVG paths)
```javascript
function animatePacket(svg, pathEl, color, duration = 1200) {
  const dot = document.createElementNS('http://www.w3.org/2000/svg', 'circle');
  dot.setAttribute('r', 5);
  dot.setAttribute('fill', color);
  svg.appendChild(dot);
  anime({
    targets: dot,
    translateX: anime.path(pathEl)('x'),
    translateY: anime.path(pathEl)('y'),
    duration,
    easing: 'easeInOutSine',
    complete: () => dot.remove()
  });
}
```

### Code Highlighting (CSS-only, no lib)
```html
<span class="kw">class</span> <span class="tp">Node</span> {
  <span class="fn">write</span>(<span class="str">"key"</span>, <span class="num">42</span>); <span class="cm">// WAL first</span>
}
```
```css
.kw { color: #ff7b72; }
.fn { color: #d2a8ff; }
.str { color: #a5d6ff; }
.cm { color: #8b949e; }
.num { color: #79c0ff; }
.tp { color: #ffa657; }
```

### Navbar Template (every content file)
```html
<div id="progress-bar" style="position:fixed;top:0;left:0;right:0;height:3px;background:var(--accent);transform:scaleX(0);transform-origin:left center;z-index:1000;"></div>
<nav id="navbar">
  <span class="nav-brand">DDIA</span>
  <div class="chapter-pager">
    <!-- prev chip (disabled on first file) -->
    <a href="PREV.html" class="chapter-nav-chip">
      <span class="nav-kicker">← prev</span>
      <span class="nav-title">Prev Title</span>
    </a>
    <span class="chapter-nav-chip current">
      <span class="nav-kicker">ch N</span>
      <span class="nav-title">Current Title</span>
    </span>
    <!-- next chip (disabled on last file) -->
    <a href="NEXT.html" class="chapter-nav-chip">
      <span class="nav-kicker">next →</span>
      <span class="nav-title">Next Title</span>
    </a>
  </div>
  <a href="00_index.html" class="back-link">↩ Index</a>
  <!-- in-page section anchors -->
  <a href="#s1">Section 1</a>
  <a href="#s2">Section 2</a>
  <!-- ... -->
  <!-- cross-series links -->
  <a href="../system-design-concepts/00_index.html">System Design</a>
  <a href="../coding-concepts/00_index.html">Coding Concepts</a>
</nav>
```

### Footer Template
```html
<footer style="text-align:center;padding:3rem 2rem;color:var(--text-muted);font-size:13px;border-top:1px solid var(--border);margin-top:4rem;">
  Designing Data-Intensive Applications — Martin Kleppmann
</footer>
```

### Section Card Template
```html
<section id="s1" class="concept-section" data-section="1">
  <div class="section-card">
    <div class="section-header">
      <h2>Section Title</h2>
      <p class="section-desc">2–3 line concept description.</p>
    </div>
    <div class="diagram-area">
      <svg id="diag-1" viewBox="0 0 700 300" width="100%"></svg>
    </div>
    <div class="controls">
      <button id="btn-1" class="ctrl-btn">▶ Simulate</button>
    </div>
    <div class="trace-panel" id="trace-1">
      <!-- live invariant text updated by JS -->
    </div>
  </div>
</section>
```

---

## Files to Create

| File | Accent | Prev | Next |
|------|--------|------|------|
| `data-intensive-application/00_index.html` | multi-color | — | — |
| `data-intensive-application/01_reliability_scalability.html` | `#60a5fa` | disabled | `02_data_models.html` |
| `data-intensive-application/02_data_models.html` | `#f472b6` | `01_reliability_scalability.html` | `03_storage_retrieval_encoding.html` |
| `data-intensive-application/03_storage_retrieval_encoding.html` | `#fb923c` | `02_data_models.html` | `04_replication.html` |
| `data-intensive-application/04_replication.html` | `#4ade80` | `03_storage_retrieval_encoding.html` | `05_partitioning.html` |
| `data-intensive-application/05_partitioning.html` | `#a78bfa` | `04_replication.html` | `06_transactions.html` |
| `data-intensive-application/06_transactions.html` | `#f59e0b` | `05_partitioning.html` | `07_distributed_problems_consensus.html` |
| `data-intensive-application/07_distributed_problems_consensus.html` | `#34d399` | `06_transactions.html` | `08_batch_stream_future.html` |
| `data-intensive-application/08_batch_stream_future.html` | `#818cf8` | `07_distributed_problems_consensus.html` | disabled |

---

## Task 0: `00_index.html` — Galaxy Navigation Map

**Files:**
- Create: `data-intensive-application/00_index.html`

**Description:** Full-viewport canvas with 8 star nodes arranged as a constellation, clustered by the book's 3 parts. Stars animate in on load (not scroll). Clicking a star navigates to the chapter. Sidebar drawer lists all chapters. Background has animated star-field particles. Cross-links to other series in a top bar.

**Sections represented as stars:**
```
Part I — Foundations (bottom-left cluster, blue tones)
  ★ Ch1  Reliability & Scalability      → 01_reliability_scalability.html
  ★ Ch2  Data Models                    → 02_data_models.html
  ★ Ch3+4 Storage, Retrieval & Encoding → 03_storage_retrieval_encoding.html

Part II — Distributed Data (center cluster, green/amber tones)
  ★ Ch5  Replication                    → 04_replication.html
  ★ Ch6  Partitioning                   → 05_partitioning.html
  ★ Ch7  Transactions                   → 06_transactions.html
  ★ Ch8+9 Distributed Problems          → 07_distributed_problems_consensus.html

Part III — Derived Data (top-right cluster, indigo tones)
  ★ Ch10-12 Batch, Stream & Future      → 08_batch_stream_future.html
```

**Star node data structure (JS):**
```javascript
const chapters = [
  { id: 1, label: 'Reliability &\nScalability',   file: '01_reliability_scalability.html',       color: '#60a5fa', part: 1, cx: 180, cy: 420 },
  { id: 2, label: 'Data Models',                  file: '02_data_models.html',                   color: '#f472b6', part: 1, cx: 310, cy: 480 },
  { id: 3, label: 'Storage &\nEncoding',          file: '03_storage_retrieval_encoding.html',    color: '#fb923c', part: 1, cx: 240, cy: 550 },
  { id: 4, label: 'Replication',                  file: '04_replication.html',                   color: '#4ade80', part: 2, cx: 480, cy: 340 },
  { id: 5, label: 'Partitioning',                 file: '05_partitioning.html',                  color: '#a78bfa', part: 2, cx: 580, cy: 420 },
  { id: 6, label: 'Transactions',                 file: '06_transactions.html',                  color: '#f59e0b', part: 2, cx: 510, cy: 510 },
  { id: 7, label: 'Distributed\nProblems',        file: '07_distributed_problems_consensus.html',color: '#34d399', part: 2, cx: 650, cy: 310 },
  { id: 8, label: 'Batch, Stream\n& Future',      file: '08_batch_stream_future.html',           color: '#818cf8', part: 3, cx: 780, cy: 220 },
];
```

**Constellation lines (connect within same part):**
```javascript
const lines = [[1,2],[2,3],[1,3], [4,5],[5,6],[4,6],[4,7], [7,8]];
```

**Load animation sequence (anime.js, triggers on DOMContentLoaded):**
```javascript
// 1. Fade in constellation lines
anime({ targets: '.const-line', opacity: [0, 0.3], duration: 800, delay: anime.stagger(100), easing: 'easeOutSine' });
// 2. Pop in star nodes
anime({ targets: '.star-node', opacity: [0,1], translateY: [15,0], scale: [0.6,1], duration: 600,
        delay: anime.stagger(120, {start: 400}), easing: 'easeOutBack' });
// 3. Pulse ring on hover (CSS: .star-node:hover .star-ring { animation: pulse 1.2s infinite; })
```

**Background star field:**
```javascript
// Generate 120 small white dots at random positions, animate opacity 0→1 with staggered delay
// Use anime.js timeline: each dot tweens opacity 0.1→0.6→0.1 on loop
const canvas = document.getElementById('bg-canvas');
// Draw 120 circles via d3 or SVG, random x/y within viewport
```

**Top bar (not a full navbar, simpler):**
```html
<div id="top-bar">
  <span class="series-title">Designing Data-Intensive Applications</span>
  <div class="series-links">
    <a href="../system-design-concepts/00_index.html">System Design ↗</a>
    <a href="../coding-concepts/00_index.html">Coding Concepts ↗</a>
    <a href="../problem-solving/00_curriculum.md">DSA Patterns ↗</a>
  </div>
</div>
```

- [ ] **Step 1: Create the file with full structure**

Write `data-intensive-application/00_index.html` containing:
- `overflow: hidden`, `height: 100vh` on body (fullscreen canvas, no scroll)
- Background SVG/canvas with 120 particle dots animated via anime.js loop
- D3 or SVG-based constellation: lines first, then star circles + labels
- Sidebar drawer (hamburger button → slide in from left) listing all 8 chapters grouped by part
- Top bar with series cross-links
- All load animations wired (lines → stars → labels, staggered)
- Hover: star scales up (`transform: scale(1.15)`), glow ring pulses
- Click: `window.location.href = chapter.file`

- [ ] **Step 2: Verify in browser**

```bash
open data-intensive-application/00_index.html
```

Check:
- Stars animate in on load (not all at once)
- Constellation lines visible between stars in same part
- Hovering a star shows its label and glow
- Clicking a star navigates (404 is fine — target files don't exist yet)
- Hamburger opens sidebar drawer with chapter list
- Cross-series links in top bar are correct

- [ ] **Step 3: Commit**

```bash
git add data-intensive-application/00_index.html
git commit -m "feat(ddia): add galaxy index page with constellation navigation"
```

---

## Task 1: `01_reliability_scalability.html`

**Files:**
- Create: `data-intensive-application/01_reliability_scalability.html`

**Accent:** `#60a5fa` (blue) — `--accent-dim: rgba(96,165,250,0.15)`
**Prev:** disabled **Next:** `02_data_models.html` "Data Models"
**Source:** DDIA Chapter 1

**Sections:**

**S1 — Reliability**
- Three fault type cards (hardware, software, human) each with an icon + short description
- Hardware fault card: animated RAID diagram — 5 disk rectangles, one turns red (fault), others stay green, data reconstructed (reconstruction bar fills)
- Software fault card: chaos monkey animation — a random node in a cluster (5 boxes) gets a red "KILL" badge, turns grey, system reroutes (packet dots repath)
- Human error card: canary deployment timeline — v1.0 bar (100% traffic) → v1.1 bar grows from 5%→100% while monitoring gauge stays green
- Interactive: "Inject Fault" button restarts the hardware fault animation

**S2 — Scalability: Load Parameters & Percentiles**
- Left panel: load parameters table (requests/sec, DB writes/sec, cache hit ratio, simultaneous users) — rows fade in sequentially
- Right panel: animated histogram of response times — bars build up showing p50=20ms, p95=150ms, p99=400ms with labeled markers
- "Tail Latency Fan-out" sub-diagram: 1 frontend request fans to 100 backend calls (SVG lines fanning out), one slow backend (red) determines overall latency — p99 of individual becomes p50 of aggregate; animate the slow dot lagging behind the others
- Interactive: slider "Number of backends" (1–100) → recalculates and redraws the tail latency visualization

**S3 — Throughput vs Response Time**
- Dual-axis animated chart: X=time, left Y=throughput (req/s), right Y=response time (ms)
- As load increases (animate adding "user" icons 1→1000), throughput line climbs then plateaus, response time line stays flat then hockey-sticks up
- "Knee of curve" moment highlighted with a vertical dashed line and label
- Interactive: slider "Requests/sec" (100–10000) → updates both lines

**S4 — Maintainability**
- Three principle cards: Operability, Simplicity, Evolvability
- Operability: ops runbook animation — checklist items check themselves off (monitoring ✓, deployment ✓, runbook ✓)
- Simplicity: coupling meter — a dial from "Simple" to "Complex"; bad code snippet shown with tangled arrows; good abstraction shown with clean layered boxes
- Evolvability: timeline showing schema v1 → v2 → v3 with backward-compatible changes highlighted green, breaking changes highlighted red

```javascript
// Section animation map — wire each section id to its animator function
const animators = {
  's1': animateReliability,
  's2': animateScalability,
  's3': animateLoadCurve,
  's4': animateMaintainability,
};
```

- [ ] **Step 1: Create the file**

Write `data-intensive-application/01_reliability_scalability.html` — full standalone HTML with all 4 sections, navbar (prev disabled, next → `02_data_models.html`), progress bar, footer, all animations and interactive controls wired.

- [ ] **Step 2: Verify in browser**

```bash
open data-intensive-application/01_reliability_scalability.html
```

Check:
- Progress bar fills as you scroll (transform not width)
- S1 animation triggers when section scrolls into view
- "Inject Fault" button replays hardware fault animation
- S2 histogram bars animate in; percentile labels appear
- S3 slider moves both chart lines correctly
- S4 coupling meter animates
- Navbar: prev chip is visually disabled (opacity 0.4, no pointer-events); next chip links to `02_data_models.html`
- No console errors

- [ ] **Step 3: Commit**

```bash
git add data-intensive-application/01_reliability_scalability.html
git commit -m "feat(ddia): add ch1 reliability & scalability animated page"
```

---

## Task 2: `02_data_models.html`

**Files:**
- Create: `data-intensive-application/02_data_models.html`

**Accent:** `#f472b6` (pink) — `--accent-dim: rgba(244,114,182,0.15)`
**Prev:** `01_reliability_scalability.html` "Reliability & Scalability" **Next:** `03_storage_retrieval_encoding.html` "Storage & Encoding"
**Source:** DDIA Chapter 2

**Sections:**

**S1 — Historical Data Models & Many-to-Many**
- IMS tree animation: hierarchical record tree builds top-down, arrows show parent→child only (no lateral); attempt to follow a many-to-many link shown as a dead-end (red ✗)
- CODASYL network model: graph of pointer nodes, "manual traversal" animation — a cursor walks through access paths step by step
- "Why Relational Won" panel: query optimizer shown as a black box — app sends SQL, optimizer finds path, result returned; contrast with explicit access path code

**S2 — Relational Model**
- Tables draw in (users, companies, positions) with join arrows
- ORM impedance mismatch: nested JSON object on left → flat relational rows on right; translation animation with mapping arrows
- Normalization animation: denormalized row → split into two normalized tables

**S3 — Document Model**
- JSON tree expands node by node (resume: name → positions → education)
- Locality advantage: single document fetch (one arrow) vs relational multi-join (three arrows fanning out)
- Schema flexibility: new field appears in one document without breaking others

**S4 — Relational vs Document (Interactive Toggle)**
- Same resume data in both models shown side by side
- Toggle button "Switch Model" — left panel animates out, right panel animates in showing the same data in the other model
- Pros/cons table fades in below for each model

**S5 — Graph Model**
- Property graph SVG: nodes (Person, Location) with labeled edges (BORN_IN, LIVES_IN, MARRIED_TO); nodes and edges draw in sequentially
- Triple-store: same data as (subject, predicate, object) rows in a table
- Cypher query: `MATCH (p:Person)-[:BORN_IN]->(c:Country)` shown with syntax highlighting, matching nodes highlighted in the diagram
- SPARQL equivalent shown side by side

**S6 — Query Languages: Declarative vs Imperative**
- Side-by-side: SQL `SELECT avg(age) FROM sharks WHERE family='Carcharias'` vs MapReduce JS function
- SQL animation: query planner finds rows → aggregate → return (3-step visual)
- MapReduce animation: map step highlights matching docs, reduce sums them

**S7 — Schema-on-read vs Schema-on-write**
- Two timelines side by side
- Schema-on-write: schema gate at write time — invalid doc bounces back (red), valid doc passes (green); schema migration shown as ALTER TABLE animation
- Schema-on-read: all docs accepted at write; parse-time check on read — old code path vs new code path in `if` branch animation

**S8 — Datalog**
- Fact table: `within(IdahoUSA). within(USANorthAmerica).` shown as triple rows
- Rule: `within_r(Loc, Name) :- within(Loc, Name).` with recursive application shown step by step
- Same query as S5 shown in Datalog syntax vs Cypher vs SPARQL — three-column comparison panel

- [ ] **Step 1: Create the file**

Write `data-intensive-application/02_data_models.html` — all 8 sections, toggle interaction in S4, syntax-highlighted code panels, all animations scroll-triggered.

- [ ] **Step 2: Verify in browser**

```bash
open data-intensive-application/02_data_models.html
```

Check:
- S1 IMS tree builds top-down; many-to-many dead-end shown
- S4 toggle button switches between relational and document views
- S5 graph nodes and edges draw sequentially
- S8 Datalog recursive rule steps animate clearly
- Navbar prev/next links correct
- No console errors

- [ ] **Step 3: Commit**

```bash
git add data-intensive-application/02_data_models.html
git commit -m "feat(ddia): add ch2 data models animated page"
```

---

## Task 3: `03_storage_retrieval_encoding.html`

**Files:**
- Create: `data-intensive-application/03_storage_retrieval_encoding.html`

**Accent:** `#fb923c` (orange) — `--accent-dim: rgba(251,146,60,0.15)`
**Prev:** `02_data_models.html` "Data Models" **Next:** `04_replication.html` "Replication"
**Source:** DDIA Chapters 3 + 4

**Sections:**

**S1 — Hash Index**
- Append-only log: new write entries appear at the bottom of a growing log (rectangles with key=value labels)
- In-memory hashmap: key → byte offset arrows update as writes occur
- Compaction animation: two segment files → merge → one compacted file (old entries with same key removed, newest kept)
- Interactive: "Write Key" button adds a new entry and updates the hashmap

**S2 — LSM-Tree + SSTables**
- MemTable (red balanced BST in memory): inserts keep it sorted
- Flush: MemTable drains down into an SSTable file (sorted blocks drawn as a file on disk)
- Multi-level compaction: L0 fills up → triggers merge with L1 → cascade; files animate sliding and merging
- Read path: bloom filter check (fast, probabilistic) → MemTable check → L0 SSTables → L1 (highlight which level is hit)
- Interactive: "Trigger Compaction" button runs the merge animation

**S3 — B-Trees**
- Tree of page nodes (each is a box with keys and child pointers)
- Write: find correct leaf → update in place → WAL entry appears first (undo log at top)
- Node split: leaf overflows → splits into two nodes → parent gains a new key (animate the restructure)
- Crash recovery: WAL replay animation — entries replay in order, tree restored

**S4 — B-Trees vs LSM-Trees**
- Three metric bars side-by-side: Write Amplification, Read Amplification, Space Amplification
- B-Tree bars shown in blue, LSM bars in orange
- LSM write amplification explained: the same data written during compaction N times — counter ticks up
- Interactive slider: "Workload" (Write-heavy ← → Read-heavy) — bars animate to reflect which wins under each workload

**S5 — OLTP vs OLAP**
- Left panel (OLTP): small point-lookup query (user_id=12345), index traversal animation, result returned quickly
- Right panel (OLAP): full table scan, aggregate calculation, star schema diagram (fact table + dimension tables)
- ETL pipeline: operational DB → nightly batch → data warehouse (three boxes with animated arrow flow)

**S6 — Column-Oriented Storage**
- Row store: full row read highlighted (many columns fetched, most unused)
- Column store: only salary column read (single thin column highlighted)
- Run-length encoding animation: `[1,1,1,2,2,3]` → `[(1,3),(2,2),(3,1)]` — compression ratio badge
- Vectorized execution: CPU processes chunk of column values in a tight loop (batch arrows)
- Writing via LSM buffer: in-memory sorted writes → flush to column files

**S7 — Data Cubes and Materialized Views**
- 2D OLAP cube grid animates: dimensions = product × date; each cell shows pre-aggregated sales total
- Roll-up: cells merge upward (daily → weekly → monthly)
- Drill-down: click a cell → explodes into sub-cells (more granular)
- Materialized view refresh: underlying data changes → dependent view cells flash and update

**S8 — Other Indexing Structures**
- Multi-column index: compound key `(last_name, first_name)` — range scan shown, why ordering matters
- Full-text fuzzy index: Levenshtein automaton — query "acme" matches "acne" with edit distance 1 (path through automaton highlighted)
- In-memory DB (VoltDB/Redis): no disk I/O path; durability via replica or append-only log; speed comparison bar vs disk-based

**S9 — Encoding Formats**
- Four codec cards: JSON, Thrift, Protobuf, Avro
- Byte size comparison bars animate in (JSON largest, Protobuf ~10x smaller)
- Schema evolution matrix: rows = reader version, cols = writer version; cells show ✓ compatible or ✗ broken; toggle "Forward" vs "Backward" compatibility
- Avro union type example for optional fields

**S10 — Modes of Dataflow**
- Three cards appear sequentially:
  - Dataflow through Databases: writer writes encoded bytes → reader later decodes — timeline shows v1 writer, v2 reader coexisting
  - Dataflow through Services (REST vs RPC): REST shows resource URLs + HTTP verbs; RPC shows "looks like function call" but with network failure modal popping up (latency, retry, partial failure); comparison table
  - Message-Passing Dataflow: producer → broker queue → consumer; one-way (fire-and-forget) vs request-reply; actor model: each actor has mailbox (inbox animation)

- [ ] **Step 1: Create the file**

Write `data-intensive-application/03_storage_retrieval_encoding.html` — all 10 sections, B-Tree/LSM comparison slider, compaction trigger button, encoding format toggle.

- [ ] **Step 2: Verify in browser**

```bash
open data-intensive-application/03_storage_retrieval_encoding.html
```

Check:
- S1 "Write Key" button appends to log and updates hashmap arrows
- S2 "Trigger Compaction" button merges SSTable files
- S4 workload slider moves B-tree vs LSM metric bars
- S7 OLAP cube drill-down interaction works
- S9 encoding format byte-size bars animate; compatibility matrix toggles
- No console errors

- [ ] **Step 3: Commit**

```bash
git add data-intensive-application/03_storage_retrieval_encoding.html
git commit -m "feat(ddia): add ch3+4 storage, retrieval & encoding animated page"
```

---

## Task 4: `04_replication.html`

**Files:**
- Create: `data-intensive-application/04_replication.html`

**Accent:** `#4ade80` (green) — `--accent-dim: rgba(74,222,128,0.15)`
**Prev:** `03_storage_retrieval_encoding.html` "Storage & Encoding" **Next:** `05_partitioning.html` "Partitioning"
**Source:** DDIA Chapter 5

**Sections:**

**S1 — Single-Leader Replication**
- Three nodes: Leader (L), Follower 1 (F1), Follower 2 (F2)
- Write packet (orange dot) travels to Leader → Leader writes to WAL (grey rectangle grows) → replication log packets (blue) travel to both followers
- Read packets (blue dots) go directly to followers
- Three replication log format buttons (toggle): Statement-based / WAL shipping / Row-based; each shows a different log content panel

**S2 — Synchronous vs Asynchronous Replication**
- Timeline diagram: Leader at top, F1 (sync) below, F2 (async) below that
- Sync: Leader sends log → waits for F1 ACK → responds to client; F1 ACK packet shown; F2 gets log but no wait
- Async: Leader responds to client immediately; F2 log delivery happens later (dashed arrow)
- Node outage panel: "Leader Fails" button → failover steps animate: detect (timeout) → elect F1 as leader → client reconfigured → F2 catches up
- Durability vs availability slider: sync (durable, slower) ↔ async (fast, potential data loss on crash)

**S3 — Replication Lag Anomalies**
- Three timeline cards, each showing a violation then the fix:
  - Read-your-own-writes: user writes to leader, reads from stale follower → sees old value (red); fix: route user's own reads to leader for 1 minute
  - Monotonic reads: user reads v2 from F1, then reads v1 from F2 (time goes backward — red); fix: sticky routing to same replica
  - Consistent prefix reads: causally related writes arrive out of order at follower (answer before question — red); fix: causally related writes to same partition

**S4 — Multi-Leader Replication**
- Two datacenters: DC1 (Leader A) and DC2 (Leader B)
- Write to A: replicates within DC1 to followers, async replication to Leader B, B replicates within DC2
- Conflict: same key written in A and B simultaneously → conflict detection (red flash on both leaders)
- Resolution panel with three strategy buttons:
  - Last-Write-Wins: timestamp comparison, one wins (show timestamp drift problem)
  - Application merge: both values kept, merged (e.g. shopping cart union)
  - CRDT: automatic convergent merge, no conflict possible

**S5 — Multi-Leader Topologies**
- Three topology diagrams (toggle):
  - Circular: A→B→C→A; break one node → entire ring stalled (red)
  - Star: all nodes through a central hub; hub failure → all replication stops
  - All-to-all: every leader → every other leader; causality violation shown: update arrives before the insert it depends on (out-of-order animation); fix: version vectors attached to each write

**S6 — Leaderless Replication (Dynamo)**
- 5 node ring; W=3, R=3, N=5
- Write: client sends to all 5, waits for 3 ACKs (3 dots light up), proceeds
- Read: client reads from all 5, takes newest value based on version number (one stale node shown in grey)
- Sloppy quorum: preferred nodes down → write to any available nodes with "hinted handoff" note; when preferred nodes recover → hints replayed
- Interactive: slider for W and R (1–5); warning shown when W+R ≤ N

**S7 — Anti-Entropy & Read Repair**
- Read repair: read reveals stale replica → client writes updated value back (animated)
- Anti-entropy process: background comparison using Merkle tree; two trees shown side by side, differing branch lights up red → sync only that subtree

- [ ] **Step 1: Create the file**

Write `data-intensive-application/04_replication.html` — all 7 sections, failover button, conflict resolution toggle, W+R slider with validity warning.

- [ ] **Step 2: Verify in browser**

```bash
open data-intensive-application/04_replication.html
```

Check:
- S1 write/read packets flow to correct nodes
- S2 "Leader Fails" button triggers failover animation sequence
- S3 each anomaly card shows violation then fix
- S4 conflict resolution buttons switch strategies
- S5 topology toggle shows all three layouts
- S6 W+R slider shows warning when W+R ≤ N
- No console errors

- [ ] **Step 3: Commit**

```bash
git add data-intensive-application/04_replication.html
git commit -m "feat(ddia): add ch5 replication animated page"
```

---

## Task 5: `05_partitioning.html`

**Files:**
- Create: `data-intensive-application/05_partitioning.html`

**Accent:** `#a78bfa` (violet) — `--accent-dim: rgba(167,139,250,0.15)`
**Prev:** `04_replication.html` "Replication" **Next:** `06_transactions.html` "Transactions"
**Source:** DDIA Chapter 6

**Sections:**

**S1 — Key-Range Partitioning**
- Horizontal bar divided into key ranges: [A–F], [G–M], [N–T], [U–Z] each on a different node
- Write animation: key "hello" → hashes to [G-M] partition (packet routes there)
- Hot spot: all sensor writes for `2024-01-15` prefix → all go to date partition (that bar turns red, overloaded indicator)

**S2 — Hash Partitioning**
- Consistent hash ring (circle SVG) with 4 node positions and virtual node markers
- Key hashed → finds position on ring → routes clockwise to nearest node
- Add a node: new node appears on ring, only keys in its arc migrate (animate those packets moving, others stay)
- Remove a node: its keys redistribute to next node only

**S3 — Skewed Workloads & Hot Spots**
- Celebrity problem: one user_id (Beyoncé) → millions of writes → single partition overloaded (red heat bar)
- Mitigation animation: add 2-byte random prefix to key → writes spread across 100 partitions (bar goes from 1 red → 100 green)
- Read cost shown: must now query all 100 partitions and merge (more arrows fanning out)
- Trade-off dial: skew reduction ↔ read complexity

**S4 — Secondary Indexes: Local vs Global**
- Local (scatter-gather): query for `color=red` cars → fan out to all 3 partitions, each scans local index, results merged (3 arrows out, 3 results in, merge step)
- Global (term-partitioned): `color=red` index term lives on Partition 2; query routes only there; but writes to any car must update Partition 2's index (cross-partition write arrows)
- Toggle button to switch between local and global views

**S5 — Rebalancing Strategies**
- Fixed partitions: 100 partitions on 3 nodes; add node 4 → 25 partitions migrate (animate partition boxes moving)
- Dynamic splitting: one partition grows too large (bar overflows) → splits into two (animate the split); shrink → merges back
- Proportional (Cassandra): number of partitions grows with number of nodes; new node picks random existing partitions and splits them

**S6 — Request Routing**
- Three architecture diagrams (toggle):
  - Client-aware: client has partition map, routes directly (fast, complex client)
  - Routing tier: all requests → router → correct node (simple client, extra hop)
  - ZooKeeper: nodes register partitions with ZK; router subscribes to ZK for map updates; ZK change notification animation

- [ ] **Step 1: Create the file**

Write `data-intensive-application/05_partitioning.html` — all 6 sections, hash ring with add/remove node, secondary index toggle, rebalancing strategy buttons.

- [ ] **Step 2: Verify in browser**

```bash
open data-intensive-application/05_partitioning.html
```

Check:
- S2 hash ring renders as circle; add/remove node animations work
- S3 prefix mitigation animation shows spread across partitions
- S4 local vs global secondary index toggle works
- S5 rebalancing strategy selector shows three distinct visuals
- S6 routing tier toggle shows three architectures
- No console errors

- [ ] **Step 3: Commit**

```bash
git add data-intensive-application/05_partitioning.html
git commit -m "feat(ddia): add ch6 partitioning animated page"
```

---

## Task 6: `06_transactions.html`

**Files:**
- Create: `data-intensive-application/06_transactions.html`

**Accent:** `#f59e0b` (amber) — `--accent-dim: rgba(245,158,11,0.15)`
**Prev:** `05_partitioning.html` "Partitioning" **Next:** `07_distributed_problems_consensus.html` "Distributed Problems"
**Source:** DDIA Chapter 7

**Sections:**

**S1 — ACID**
- Four cards appearing sequentially (staggered slide-in):
  - Atomicity: account transfer animation — debit and credit as two steps; if step 2 fails → both rolled back (red flash, balance restored)
  - Consistency: invariant check — account balance ≥ 0; violating transaction rejected (red ✗)
  - Isolation: two concurrent transactions shown as parallel lanes; they don't see each other's intermediate state
  - Durability: data written to disk before ACK; crash simulation → recovery from disk log (green ✓)

**S2 — Single-Object vs Multi-Object Operations**
- Single-object: increment counter (read-modify-write); JSON blob partial update; atomic CAS shown
- Multi-object need: foreign key check requires looking at two tables atomically; denormalized counter must match actual count
- Error handling: network failure after write → retry causes double-write → idempotency key solution

**S3 — Weak Isolation: Read Committed**
- Timeline with Tx A and Tx B interleaved:
  - Dirty read: Tx A reads Tx B's uncommitted value → Tx B aborts → Tx A has phantom data (red); Read Committed blocks this: Tx B's old committed value shown instead
  - Dirty write: Tx A and Tx B both try to update same row; dirty write causes invoice sent to wrong person (red); blocked by row-level lock

**S4 — Snapshot Isolation / MVCC**
- Version chain per row: `(val=10, txid=1) → (val=20, txid=5) → (val=30, txid=9)`
- Tx 7 starts: sees txid≤7, reads val=20 (ignores later txid=9 version)
- Read skew scenario: account total check sees old balance on one account, new balance on other — MVCC prevents this
- Interactive: "Start Transaction" button creates a new tx with a snapshot; "Commit Another Tx" button adds a new version; show whether reading tx sees new version (no — snapshot is frozen)

**S5 — Isolation Levels Ladder**
- Interactive: four buttons for each level; selecting a level shows which anomalies are prevented (green ✓) vs still possible (red ✗):
  - Read Uncommitted: dirty reads ✗, dirty writes ✗, read skew ✗, lost updates ✗, write skew ✗, phantoms ✗
  - Read Committed: dirty reads ✓, dirty writes ✓
  - Repeatable Read / Snapshot: + read skew ✓
  - Serializable: all ✓

**S6 — Preventing Lost Updates**
- Race condition animation: Tx A reads counter=50, Tx B reads counter=50, both increment → both write 51 (should be 52)
- Four solutions (tabbed):
  - Atomic write: `UPDATE counter SET val = val + 1` — single operation, no race
  - Explicit lock: `SELECT FOR UPDATE` — Tx B blocks until Tx A releases
  - Automatic detection: DB detects lost update → aborts Tx B → Tx B retries (gets 51, writes 52)
  - Application-level (multi-leader): both values merged by application code

**S7 — Write Skew & Phantoms**
- Doctor on-call step-by-step:
  1. Tx A reads: 2 doctors on call → proceeds to withdraw
  2. Tx B reads: 2 doctors on call → proceeds to withdraw  
  3. Both commit: 0 doctors on call (violated constraint)
- Phantom reads: Tx A checks no meeting at 12pm → schedules meeting; Tx B does the same → double-booking
- Materializing conflicts: create explicit lock row for each 12pm slot; both transactions lock it → serialized

**S8 — Serializability**
- Three approaches with toggle:
  - Actual serial: single-threaded queue of transactions (fast when tx fit in memory, stored procedures)
  - Two-Phase Locking (2PL): shared locks (reads), exclusive locks (writes); deadlock scenario — Tx A waits for B's lock, Tx B waits for A's lock — cycle detected → one aborted
  - Serializable Snapshot Isolation (SSI): optimistic — both proceed, read set tracked; commit checks if any read premise was invalidated by concurrent write → aborts the later one; counter showing aborts vs commits

- [ ] **Step 1: Create the file**

Write `data-intensive-application/06_transactions.html` — all 8 sections, MVCC snapshot interaction, isolation level selector, lost update tabs, 2PL deadlock animation, SSI abort counter.

- [ ] **Step 2: Verify in browser**

```bash
open data-intensive-application/06_transactions.html
```

Check:
- S1 atomicity animation rolls back both steps on failure
- S4 MVCC snapshot stays frozen; new version not visible to old snapshot tx
- S5 isolation level selector updates anomaly grid correctly
- S6 all four lost update strategies are tabbed and clear
- S7 doctor on-call animation shows the write skew step by step
- S8 toggle between actual serial / 2PL / SSI works; deadlock cycle animates
- No console errors

- [ ] **Step 3: Commit**

```bash
git add data-intensive-application/06_transactions.html
git commit -m "feat(ddia): add ch7 transactions animated page"
```

---

## Task 7: `07_distributed_problems_consensus.html`

**Files:**
- Create: `data-intensive-application/07_distributed_problems_consensus.html`

**Accent:** `#34d399` (emerald) — `--accent-dim: rgba(52,211,153,0.15)`
**Prev:** `06_transactions.html` "Transactions" **Next:** `08_batch_stream_future.html` "Batch, Stream & Future"
**Source:** DDIA Chapters 8 + 9

**Sections:**

**S1 — Partial Failures**
- 5-node cluster diagram; "Inject Failure" button cycles through failure modes:
  - Node crash: one node greys out; others continue
  - Network partition: link between two nodes turns red/dashed; packets dropped (dots disappear mid-flight)
  - Packet delay: dot travels slowly, finally arrives late
- Cloud vs HPC contrast: HPC stops on any fault (checkpoint-restart); cloud continues with partial failures

**S2 — Byzantine Faults & System Models**
- Byzantine node animation: node sends message "value=5" to N1 and "value=7" to N2 simultaneously (contradictory) — both shown; other nodes confused
- System model cards (three): crash-stop (node stops and stays stopped), crash-recovery (node restarts, may have lost memory), Byzantine (node lies)
- Safety vs liveness: safety = "nothing bad happens" (invariant bar stays in bounds); liveness = "something good eventually happens" (progress indicator advances)
- Note panel: Byzantine tolerance out of scope for most data systems (blockchain exception)

**S3 — Unreliable Clocks**
- Wall clock drift animation: two node clocks ticking at slightly different rates; after 1 hour, 100ms apart — NTP sync jerks them back
- Ordering by timestamp: Event A on Node 1 happens before Event B on Node 2, but Node 1's clock is ahead → A has higher timestamp → looks like it happened after B (wrong ordering shown in red)
- Confidence interval: Google TrueTime — each timestamp returned as [earliest, latest] interval, not a point; operations only commit when intervals don't overlap

**S4 — Logical Clocks**
- Lamport timestamps step-by-step (3 nodes, 6 events):
  - Each event increments local counter
  - Send: attach counter; receive: take max(local, received)+1
  - Timeline drawn with events labeled by Lamport time; happens-before arrows shown
- Vector clocks: each node has a vector [a, b, c]; concurrent events detected when neither vector dominates the other (shown with red "concurrent" label)

**S5 — Process Pauses**
- GC pause animation: Node A pauses mid-lease (pause bar expands); lease expires on Node B side (timer runs out, leader election starts); A resumes, tries to act as leader → split-brain scenario
- Fencing token: each lock grant has a monotonically increasing token; stale leader's token (42) rejected by storage server (expects ≥43)
- Unbounded delays: any node can pause for arbitrarily long (GC, OS preemption, VM migration); you cannot tell paused from dead

**S6 — Ordering and Causality**
- Causal order animation: post (event A) → reply (event B); causal dependency tracked
- Three consistency levels on a spectrum bar: eventual consistency → causal consistency → linearizability; trade-off labels
- Sequence number ordering: Lamport clocks give total order but don't capture true causality; example where Lamport order contradicts causal order
- Version vectors as causal dependency tracker: each write carries the version vector of all events it causally depends on

**S7 — Implementing Linearizable Systems**
- Grid of architectures with linearizable verdict:
  - Single-leader (reads from leader only): ✓ Linearizable
  - Single-leader (reads from followers): ✗ Not linearizable (stale reads)
  - Consensus algorithms: ✓ Linearizable
  - Multi-leader: ✗ Not linearizable (conflicting writes)
  - Leaderless quorum: ✗ Usually not (network delays can violate)
- Each cell animates in with a verdict icon; clicking shows why

**S8 — Linearizability vs Serializability & CAP**
- Timeline diagram: two transactions, four operations; linearizable timeline must respect wall-clock order of overlapping operations
- Serializability: transactions can be reordered to a serial schedule — does NOT require real-time ordering
- Venn diagram: strict serializability = serializable + linearizable (the intersection)
- CAP theorem: network partition always happens; during partition choose: C (refuse requests, stay consistent) or A (serve requests, risk stale data)
- Multi-datacenter cost: cross-DC linearizable reads require cross-DC round-trip (latency animation)

**S9 — Consensus**
- Formal properties cards: Termination, Agreement, Validity, Integrity (each with a one-line definition)
- 2PC coordinator failure: Phase 1 prepare → all vote yes → coordinator crashes → participants blocked forever waiting (red freeze animation)
- Raft leader election step-by-step:
  1. Follower timeout → candidate; sends RequestVote
  2. Majority votes yes → becomes leader
  3. Leader sends heartbeats + log entries to followers
  4. Simulate leader crash → new election
- FLP impossibility: deterministic async consensus is impossible in theory; Raft bypasses via randomized timeouts (note card)

**S10 — Total Order Broadcast & Distributed Transactions**
- Total order broadcast = consensus = atomic broadcast (equivalence shown as three labels on one box)
- XA transactions: coordinator talks to DB + message queue via 2PC; both must commit or both abort
- ZooKeeper use cases animation: 
  - Leader election: nodes register ephemeral node; watch for deletion
  - Service discovery: node registers its address; clients read
  - Distributed lock: ephemeral sequential node; lowest sequence number = lock holder

- [ ] **Step 1: Create the file**

Write `data-intensive-application/07_distributed_problems_consensus.html` — all 10 sections, failure mode injector, Raft election step-through, CAP partition toggle, linearizability verdict grid.

- [ ] **Step 2: Verify in browser**

```bash
open data-intensive-application/07_distributed_problems_consensus.html
```

Check:
- S1 failure mode injector cycles through crash/partition/delay
- S4 Lamport timestamp animation steps through events correctly
- S5 fencing token correctly rejects stale leader's request
- S7 linearizability verdict grid shows correct yes/no per architecture
- S9 Raft election steps animate in sequence; leader crash triggers re-election
- S10 ZooKeeper use cases panel shows all three scenarios
- No console errors

- [ ] **Step 3: Commit**

```bash
git add data-intensive-application/07_distributed_problems_consensus.html
git commit -m "feat(ddia): add ch8+9 distributed problems & consensus animated page"
```

---

## Task 8: `08_batch_stream_future.html`

**Files:**
- Create: `data-intensive-application/08_batch_stream_future.html`

**Accent:** `#818cf8` (indigo) — `--accent-dim: rgba(129,140,248,0.15)`
**Prev:** `07_distributed_problems_consensus.html` "Distributed Problems" **Next:** disabled
**Source:** DDIA Chapters 10 + 11 + 12

**Sections:**

**S1 — Unix Philosophy → MapReduce**
- Unix pipe chain animation: `cat access.log | grep 'error' | awk '{print $7}' | sort | uniq -c`
  - Each pipe stage draws as a box; data flows left-to-right as dot packets
  - Intermediate files: each stage output materializes as a temp file (rectangle) before next stage reads it
- Hadoop comparison: same pipeline but each stage = MapReduce job; fault tolerance via re-running from materialized intermediate output
- Immutable input: original dataset never modified (lock icon)

**S2 — MapReduce Internals**
- Multi-worker animation with 3 mappers and 2 reducers:
  1. Map phase: each mapper reads its input split, emits (key, value) pairs (dots with labels)
  2. Shuffle: dots move across and group by key (partition by hash of key) — visual clustering
  3. Sort: within each reducer's partition, dots sort by key
  4. Reduce: reducer processes each key group, emits final output
- "Simulate MapReduce" button runs the full animation

**S3 — MapReduce Join Strategies**
- Three strategy tabs:
  - Reduce-side join: both datasets shuffled by join key → arrive at same reducer → join happens there; sort-merge animation
  - Map-side broadcast hash join: small dataset loaded into each mapper's memory (broadcast animation, small dataset → all 3 mappers); join happens in-mapper (no shuffle)
  - Partitioned map-side join: both datasets pre-partitioned and pre-sorted by join key → mapper joins local partition directly; no shuffle, fastest

**S4 — Graph & Iterative Processing**
- Pregel BSP (Bulk Synchronous Parallel) animation:
  - 6-node graph displayed
  - Superstep 1: each node sends messages to neighbors (arrows appear); barrier (horizontal line) — all messages delivered
  - Superstep 2: each node processes received messages, updates state
  - Repeat until convergence (all nodes same color)
- PageRank convergence: bar chart of per-node rank values; bars update each superstep until stable
- Why MapReduce doesn't fit: between supersteps must write all state to disk (materialise) and re-read — shown as slow disk I/O; Pregel keeps vertex state in memory across supersteps

**S5 — Output of Batch Workflows**
- Three output types shown as pipeline endpoints:
  - Search index: Lucene segment files built offline → atomic swap (old dir → new dir swap animation)
  - Key-value store for serving: read-only RocksDB files → copied to application servers (scp animation)
  - ML features: pre-computed feature vectors → stored for model serving
- Immutable output + atomic swap: old output stays until new is ready (blue → green swap); rollback = swap back

**S6 — Dataflow Engines (Spark/Flink)**
- Operator graph (DAG): Source → Filter → Join → Aggregate → Sink; boxes connected by arrows
- Contrast with MapReduce: no materializing intermediate state between operators — data streams in-memory from operator to operator
- Fault tolerance: recompute lost partitions from upstream (lineage graph shown)

**S7 — Partitioned Logs (Kafka)**
- Log visualization: growing sequence of offset-numbered blocks in a partition
- Producer appends to tail; consumer reads from its current offset (offset pointer advances)
- Two consumer groups: each has its own independent offset pointer; both can replay from any offset
- Log compaction: for each key, only latest value retained; old entries fade and compact
- Log vs traditional queue: queue deletes on consume (one consumer per message); log retains, any consumer can replay

**S8 — Keeping Systems in Sync**
- Dual-write problem animation:
  1. App writes to DB (success ✓)
  2. App writes to search index (failure ✗)
  3. Systems now permanently divergent (split-brain icon)
- Full re-sync: expensive, shown as a giant data transfer arrow (slow)
- CDC as solution: DB log → CDC consumer → search index; single source of truth; rewind and replay shown

**S9 — Change Data Capture (CDC)**
- DB transaction log: INSERT/UPDATE/DELETE events stream out
- Debezium-style consumer: reads log → emits events to Kafka topic
- Downstream systems subscribe: search index, cache, data warehouse each receive events and update
- Schema evolution: ALTER TABLE adds column → CDC handles before/after image changes
- CDC vs dual-write panel: CDC is after-the-fact (single writer, log is truth); dual-write has no coordination

**S10 — Event Sourcing**
- Event log (append-only): `[UserRegistered, EmailVerified, OrderPlaced, OrderShipped, OrderCancelled]`
- Current state derived by replaying: aggregate function processes events left to right → current state box updates
- Snapshot: after N events, take snapshot of current state → replay only from snapshot forward (performance)
- Event sourcing vs CDC distinction:
  - CDC: database-level changes (row updates), implementation detail
  - Event sourcing: application-level intents (business events), part of the domain model
- CQRS connection: command side writes events; query side maintains read-optimized views

**S11 — Stream Processing**
- Stream-table join (enrichment): click event stream → join with user_profile table → enriched events
- Stream-stream join (windowed): ad click stream joins ad impression stream within 1-hour window → matched pairs
- Table-table join: user_profile materialised view updates when either table changes
- Window types toggle: tumbling (non-overlapping fixed), sliding (overlapping), session (gap-based) — visual timeline

**S12 — Lambda vs Kappa Architecture**
- Toggle button switches between two views:
  - Lambda: three layers shown — batch layer (historical, accurate), speed layer (real-time, approximate), serving layer (merges both); query must combine results from both
  - Kappa: single streaming pipeline handles all data; older data replayed through same stream processor; simpler architecture, one codebase

**S13 — Exactly-Once Semantics**
- At-most-once: fire and forget; failure → message lost (gap animation)
- At-least-once: retry on failure → possible duplicate (duplicate dot appears)
- Exactly-once via idempotence: duplicate message detected by dedup key → dropped (only one dot reaches destination)
- Distributed transaction in stream: stream processor + external DB commit atomically; two-phase approach shown

**S14 — Exactly-Once Semantics** *(renamed in plan: "The Future: Unbundling Databases")*
- Dataflow as application architecture: no central DB; each service has its own derived view maintained via event streams
- End-to-end argument: uniqueness constraint (e.g. username) cannot be enforced purely at DB level when distributed; must check at application level with a distributed counter/consensus
- Timeliness vs integrity: integrity (no data loss, no corruption) vs timeliness (how fresh the data is); can decouple them — eventual consistency for timeliness, strong guarantees for integrity
- Asynchronous checks: place order → optimistically accept → async fraud check → compensate if needed (saga-like flow animation)

**S15 — Doing the Right Thing**
- Predictive analytics feedback loop: model trained on past data → predictions influence future behavior → future data reflects predictions → model reinforces bias (circular arrow animation, self-fulfilling prophecy)
- Privacy: data as liability panel — data you don't collect can't be breached; retention policy animation (data auto-deletes after 90 days)
- Right to be forgotten vs immutable log: user deletion request → event log has their data permanently; solution: encrypt personal data with per-user key → delete key → data becomes unreadable (key deletion animation)
- Responsible principles card: minimize collection, enable portability, audit access

- [ ] **Step 1: Create the file**

Write `data-intensive-application/08_batch_stream_future.html` — all 15 sections, MapReduce simulation button, join strategy tabs, window type toggle, Lambda/Kappa toggle, exactly-once mode selector.

- [ ] **Step 2: Verify in browser**

```bash
open data-intensive-application/08_batch_stream_future.html
```

Check:
- S2 "Simulate MapReduce" button runs full map→shuffle→sort→reduce animation
- S3 join strategy tabs switch between three distinct diagrams
- S7 Kafka consumer offset pointer advances independently per consumer group
- S10 event sourcing replay animation sums events to produce current state correctly
- S12 Lambda/Kappa toggle shows two completely different architectures
- S13 exactly-once mode selector shows at-most-once, at-least-once, exactly-once
- S15 key deletion animation renders data unreadable
- No console errors

- [ ] **Step 3: Commit**

```bash
git add data-intensive-application/08_batch_stream_future.html
git commit -m "feat(ddia): add ch10-12 batch, stream & future animated page"
```

---

## Task 9: Final Integration Check

**Files:** All 9 files in `data-intensive-application/`

- [ ] **Step 1: Verify cross-navigation works**

Open each file and confirm:
- Every prev/next chip navigates to the correct file
- Back link goes to `00_index.html`
- Cross-series links go to `../system-design-concepts/00_index.html` and `../coding-concepts/00_index.html`
- Index page stars link to the correct chapter files

```bash
open data-intensive-application/00_index.html
```

- [ ] **Step 2: Verify animation rules across all files**

For each file, open DevTools console and run:
```javascript
// Should return 0 — no width/height animations
document.querySelectorAll('[style*="width:"]').length
// Progress bar should use transform, not width
getComputedStyle(document.getElementById('progress-bar')).transform
```

Confirm no `style.width` usage in progress bars across all files.

- [ ] **Step 3: Verify scroll guard in all files**

Search each file for division operations in scroll handlers — confirm all have `if (total <= 0) return;`:
```bash
grep -n "scrollHeight\|scrollY\|scrollTop" data-intensive-application/*.html
```
Each result should be preceded by the guard.

- [ ] **Step 4: Final commit**

```bash
git add data-intensive-application/
git commit -m "feat(ddia): complete DDIA animated HTML series — all 9 files"
```

---

## Self-Review Against Spec

**Spec coverage check:**

| Spec Requirement | Plan Task |
|---|---|
| `00_index.html` galaxy nav, 8 stars, 3-part clusters | Task 0 |
| `01` — Reliability, Scalability, Throughput, Maintainability | Task 1 (S1–S4) |
| `02` — IMS/CODASYL history, Relational, Document, Graph, Query languages, Schema-on-read/write, Datalog | Task 2 (S1–S8) |
| `03` — Hash index, LSM+SSTables, B-Trees, B-Tree vs LSM comparison, OLTP/OLAP, Column storage, Data cubes, Other indexes, Encoding formats, Modes of dataflow | Task 3 (S1–S10) |
| `04` — Single-leader, Sync/async replication, Replication lag, Multi-leader, Multi-leader topologies, Leaderless, Anti-entropy | Task 4 (S1–S7) |
| `05` — Key-range, Hash partitioning, Hot spots, Secondary indexes, Rebalancing, Request routing | Task 5 (S1–S6) |
| `06` — ACID, Single/multi-object ops, Read committed, MVCC, Isolation levels, Lost updates, Write skew, Serializability | Task 6 (S1–S8) |
| `07` — Partial failures, Byzantine+system models, Unreliable clocks, Logical clocks, Process pauses, Ordering+causality, Linearizable systems, Linearizability vs serializability, Consensus, Total order broadcast | Task 7 (S1–S10) |
| `08` — Unix/MapReduce, MapReduce internals, Join strategies, Graph processing, Batch output, Dataflow engines, Kafka log, Keeping in sync, CDC, Event sourcing, Stream processing, Lambda/Kappa, Exactly-once, Future/unbundling, Doing the right thing | Task 8 (S1–S15) |
| Navbar: prev/next, back link, cross-series links, section anchors | All tasks |
| Footer: Martin Kleppmann credit | All tasks |
| Only `transform` + `opacity` animated | Shared conventions + Task 9 verification |
| Progress bar uses `scaleX`, not `width` | Shared conventions + Task 9 verification |
| `IntersectionObserver` at 25% threshold | Shared conventions |
| CDN only: anime.js v3.2.1, d3.js v7, Google Fonts | Shared conventions |

All spec requirements covered. No gaps found.
