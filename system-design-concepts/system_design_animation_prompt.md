# Prompt: Generate Animated HTML Visualizations for System Design Concepts

---

## 🎯 OBJECTIVE

You are an expert frontend developer and system design educator. Your task is to create a **series of rich, animated, self-contained HTML files** that visually explain core system design concepts (scaling, distributed systems, storage, messaging, geo, pipelines, finance, and interview fundamentals). Each HTML file must be fully standalone (no external dependencies except CDN-hosted libraries), visually engaging, and educational.

---

## 🧩 TOPICS COVERED

### Core Topics:
1. Scale From Zero To Millions Of Users
2. Back-of-the-Envelope Estimation
3. A Framework For System Design Interviews
4. Design A Rate Limiter
5. Design Consistent Hashing
6. Design A Key-Value Store
7. Design A Unique ID Generator In Distributed Systems
8. Design A URL Shortener
9. Design A Web Crawler
10. Design A Notification System
11. Design A News Feed System
12. Design A Chat System
13. Design A Search Autocomplete System
14. Design YouTube
15. Design Google Drive
16. Proximity Service
17. Nearby Friends
18. Google Maps
19. Distributed Message Queue
20. Metrics Monitoring
21. Ad Click Event Aggregation
22. Hotel Reservation
23. Distributed Email Service
24. S3-like Object Storage
25. Real-time Gaming Leaderboard
26. Payment System
27. Digital Wallet
28. Stock Exchange

---

## 📁 OUTPUT FILES TO GENERATE

Generate the following **9 animated HTML files**, each covering a logical grouping of concepts:

| File # | Filename | Concepts Covered |
|--------|----------|-----------------|
| 1 | `01_scaling_fundamentals.html` | Single server → DB separation, vertical/horizontal scaling, load balancers, CDN, caching, stateless architecture, data centers, message queues, sharding |
| 2 | `02_core_distributed_systems.html` | Consistent hashing, key-value store internals, CAP theorem, replication, quorum, Gossip protocol, Merkle trees |
| 3 | `03_rate_limiter_and_id_gen.html` | Token bucket, leaky bucket, fixed/sliding window algorithms; Snowflake ID, UUID, ticket server |
| 4 | `04_storage_and_search.html` | URL shortener (base62, redirect), web crawler (BFS, politeness), search autocomplete (trie, top-K), Google Drive architecture |
| 5 | `05_feeds_and_messaging.html` | News feed fanout (push/pull), notification system (APNs/FCM/SMS), chat system (WebSocket, presence, group chat) |
| 6 | `06_video_and_object_storage.html` | YouTube (upload pipeline, CDN streaming, DAG encoding), S3-like object storage (buckets, data/metadata separation, erasure coding) |
| 7 | `07_geo_and_realtime.html` | Proximity service (geohash, quadtree), Nearby Friends (WebSocket, Redis pub-sub), Google Maps (routing, ETA, tile rendering) |
| 8 | `08_data_pipelines.html` | Distributed message queue (Kafka-like internals), Metrics monitoring (time-series DB, alerting pipeline), Ad click aggregation (Lambda/Kappa architecture) |
| 9 | `09_financial_systems.html` | Hotel reservation (idempotency, distributed lock), Payment system (PSP integration, reconciliation), Digital Wallet (event sourcing), Stock Exchange (order book, matching engine) |

---

## 🎨 VISUAL & ANIMATION REQUIREMENTS

### General Design Standards
- **Color scheme**: Deep dark background (`#0d1117`) with bright accent colors per topic group (use HSL for variety)
- **Typography**: Use Google Fonts via CDN — `Inter` for body, `JetBrains Mono` for technical labels
- **Card layout**: Each concept should live in a visible "section card" with a header, animated diagram area, and a short description panel
- **Responsiveness**: Must work on 1280px+ screens (desktop-first is fine)
- **Navigation**: Each file must have a fixed top navbar with clickable section links that smooth-scroll
- **Progress bar**: Thin animated progress indicator at the very top of the page that fills as you scroll

### Animation Rules (using vanilla JS + CSS — NO frameworks except what's listed below)

#### Allowed CDN Libraries:
- `anime.js` (v3) for JS-driven animations
- `d3.js` (v7) for data-driven diagrams (graphs, trees, force layouts)
- `Rough.js` or hand-drawn SVG style for informal diagram aesthetics (optional, for fun)

#### Animation Categories — use at least 3 per file:

**1. Flow Animations**
- Arrows and data packets that travel along SVG paths between nodes
- Use CSS `stroke-dashoffset` animation or `anime.js` path following
- Color-coded packet types (reads = blue, writes = orange, cache hits = green)

**2. State Transition Animations**
- Nodes that pulse, change color, or scale when they "receive" a request
- Server nodes that visually "fail" and recover (opacity + shake effect)
- Highlight active vs. idle components

**3. Building/Construction Animations**
- Architecture diagrams that draw themselves progressively on scroll or on a timer
- New layers or services appearing as the system "scales up"

**4. Interactive Elements** (at least 1 per file)
- Clickable buttons to simulate "send request", "add server", "cache miss", etc.
- Sliders to show how parameters change system behavior (e.g., token bucket rate)
- Toggle switches for showing different modes (push vs. pull fanout, etc.)

**5. Counter/Metric Animations**
- Animated counters showing QPS, latency, cache hit rate
- Live bar charts updating in real-time (simulated)

---

## 📋 PER-FILE DETAILED SPECIFICATIONS

---

### FILE 1: `01_scaling_fundamentals.html`
**Theme color**: `#4ade80` (green — growth)

#### Sections & Animations:

**Section 1.1 — Single Server to Multi-Tier**
- Start with 1 server box. Show a "users" icon group multiplying over 3 seconds.
- Animate the single server splitting into: Web Server → App Server → Database
- Show a DNS resolution animation (user → DNS → IP returned → server)

**Section 1.2 — Vertical vs Horizontal Scaling**
- Side-by-side comparison cards
- Left (vertical): One server box grows taller (RAM/CPU bars fill up) — then hits a ceiling with a red "MAX" indicator
- Right (horizontal): New server boxes clone themselves and appear beside the original with a "+" pop animation

**Section 1.3 — Load Balancer**
- 3 incoming requests animate as colored dots
- Dots hit a "Load Balancer" node, then get routed to 3 different backend servers (round-robin shown by color-coding each route)
- Server health: click a button to "kill" one server; watch the LB reroute traffic

**Section 1.4 — Database Replication (Primary + Replicas)**
- Write operations (orange arrows) only go to Primary
- Read operations (blue arrows) fan out to Replicas
- Show replication lag with a subtle delay animation
- Simulate a primary failure: replica gets promoted (color changes + label update)

**Section 1.5 — Cache Layer (Redis)**
- Show request flow: User → App Server → Cache check
- "Cache HIT": green glow, fast path, label "~1ms"
- "Cache MISS": red outline, proceeds to DB, label "~100ms", then populates cache
- Add a "Cache Hit Rate" animated gauge (0–100%)

**Section 1.6 — CDN**
- World map SVG with dots representing users in US, Europe, Asia
- CDN edge nodes appear near each region
- Static assets (images/JS) animate as flying from origin to CDN, then from CDN to users
- Dynamic requests still flow to origin server (show the contrast)

**Section 1.7 — Stateless Architecture**
- Before: User session stored on Server A. User hits Server B → 403 error (red flash)
- After: Session stored in shared Redis. Either server can serve the user (green checkmark)

**Section 1.8 — Message Queue (Async Processing)**
- Producer sends messages → Queue (buffer visualization with filling blocks)
- Consumer workers pull from queue independently
- Demonstrate: producer spikes (fast fill) while consumers catch up at their own rate
- Animate queue depth rising and falling

**Section 1.9 — Database Sharding**
- Animate a large "Users Table" splitting by `user_id % N` into shard 0, 1, 2
- Show how a query routes to the correct shard
- Show a "resharding" animation when a new shard is added

---

### FILE 2: `02_core_distributed_systems.html`
**Theme color**: `#f59e0b` (amber — complexity)

#### Sections & Animations:

**Section 2.1 — Consistent Hashing**
- Draw a circular ring (hash ring) using SVG
- Animate nodes being placed on the ring (with their hash positions)
- Animate a key being hashed and walking clockwise to find its server
- Add/remove a node and animate only a subset of keys migrating (contrast with naive modulo hashing that moves all keys)
- Show virtual nodes: one physical server has 3 virtual positions on the ring

**Section 2.2 — Key-Value Store Internals**
- Diagram: Client → Coordinator → 3 replicas
- Show write path: WAL (Write-Ahead Log) → MemTable → SSTable flush
- Show read path: bloom filter check → MemTable → SSTable levels
- Animate compaction: multiple SSTables merging into one
- Show tunable consistency: W + R > N slider that users can adjust

**Section 2.3 — CAP Theorem**
- Triangle diagram with CP, AP, CA at vertices
- Examples animate into the correct corner (Zookeeper = CP, Cassandra = AP, MySQL = CA)
- Network partition button: click it and watch what happens to each system type

**Section 2.4 — Gossip Protocol**
- Grid of nodes. One node gets updated state (glows).
- Watch state propagate peer-to-peer in "gossip rounds" — each node infects 2–3 neighbors per round
- Show convergence: all nodes eventually have the same state (all same color)

**Section 2.5 — Quorum & Replication**
- N=5 nodes. Animate W (write quorum) and R (read quorum) as selections.
- Show W=3, R=3 → strong consistency
- Show W=1, R=1 → eventual consistency (faster but stale reads shown)

**Section 2.6 — Merkle Trees**
- Binary tree diagram where leaf nodes are data hashes
- Animate two trees being compared — mismatched nodes light up red
- Show how only the divergent branch needs to be synced

---

### FILE 3: `03_rate_limiter_and_id_gen.html`
**Theme color**: `#a78bfa` (violet — control)

#### Sections & Animations:

**Section 3.1 — Token Bucket Algorithm**
- Animated bucket that fills with tokens at a steady rate (drip animation from above)
- Requests arrive and consume tokens (tokens disappear)
- When bucket is empty, requests are rejected (red bounce + "429 Too Many Requests")
- Slider: control token refill rate

**Section 3.2 — Leaky Bucket Algorithm**
- Bucket fills with incoming requests at variable rate
- Water leaks out at a FIXED rate from the bottom (processed queue)
- Overflow = dropped requests (splash animation)

**Section 3.3 — Fixed Window Counter**
- Timeline of 1-second windows
- Counters increment inside each window box
- Show boundary exploit: 5 req at end of window 1 + 5 req at start of window 2 = 10 in 1 second (highlight the vulnerability in red)

**Section 3.4 — Sliding Window Log & Counter**
- Rolling window that slides along a timeline
- Show how the window clips old requests
- Counter updates dynamically as the window moves (JS-animated)

**Section 3.5 — Distributed Rate Limiter**
- Multiple API gateway nodes each with local counters
- Centralized Redis storing global counters
- Animate the sync between local and global state (with arrows)

**Section 3.6 — Snowflake ID Generator**
- Animated breakdown of 64-bit ID structure:
  - Bit 0: Sign (gray)
  - Bits 1–41: Timestamp (blue, count up)
  - Bits 42–51: Datacenter + Machine ID (orange)
  - Bits 52–63: Sequence number (green, cycles 0→4095)
- Show two concurrent ID generations: different sequence numbers, same timestamp
- Show clock skew problem: machine clock goes backward → ID generator pauses (visual freeze)

---

### FILE 4: `04_storage_and_search.html`
**Theme color**: `#38bdf8` (sky blue — information)

#### Sections & Animations:

**Section 4.1 — URL Shortener**
- User types long URL → system generates short code (base62 encoding animation showing character mapping)
- Redirect flow: short URL → 301/302 redirect → original URL (animate HTTP response codes)
- Database schema appears with hash_key → long_url row highlighted
- Show hash collision handling: chained probing animation

**Section 4.2 — Web Crawler**
- Seed URLs enter a queue
- Crawler workers dequeue, fetch page (animated HTTP request)
- HTML parser extracts links (highlight `<a href>` tags)
- New URLs added to queue (with deduplication: duplicate URL bounces off a "Bloom Filter" wall)
- Show politeness: per-domain delay timers, robots.txt check
- BFS tree grows visually as pages are discovered

**Section 4.3 — Trie for Search Autocomplete**
- Animate trie being built character by character as words are inserted
- Type in the search box (letter by letter) and watch the trie traverse and highlight matching paths
- Top-K results float up from the trie nodes with frequency scores
- Show caching: popular prefix → result served from cache (instant green flash)

**Section 4.4 — Google Drive / Cloud File Storage**
- Upload flow: file → block splitting → chunk upload to block servers → metadata to DB
- Show deduplication: same file chunk from user B → hash match → no re-upload (green "deduped")
- Sync flow: change on desktop → delta computed → only changed blocks uploaded
- Version history: timeline of document versions, clickable to "restore"

---

### FILE 5: `05_feeds_and_messaging.html`
**Theme color**: `#f472b6` (pink — social)

#### Sections & Animations:

**Section 5.1 — News Feed: Fan-out on Write (Push)**
- Celebrity user publishes a post
- Fan-out service reads follower list (500K followers)
- Animate post being written to each follower's feed cache in parallel (many arrows fanning out)
- Problem: celebrity with 10M followers → highlight "hot key" problem in red

**Section 5.2 — News Feed: Fan-out on Read (Pull)**
- User opens feed
- System reads posts from all followees in parallel
- Merge and rank results in real-time
- Highlight: fresh data but slower (latency meter increases)

**Section 5.3 — Hybrid Approach**
- Toggle between: regular users (push) vs. celebrities (pull)
- Show how hybrid handles hot celebrities gracefully

**Section 5.4 — Notification System**
- Notification pipeline: publisher → notification server → 3 branches:
  - APNs (iOS push) — show Apple logo, animate push delivery
  - FCM (Android push) — show Android logo
  - SMS gateway (Twilio)
  - Email (SendGrid)
- Show retry queue: failed delivery → retry with exponential backoff (timer animation)
- Rate limiting per user to prevent spam (token bucket visualization in mini)

**Section 5.5 — Chat System**
- WebSocket handshake animation: HTTP upgrade → persistent bidirectional connection
- Message flow: Sender → Chat Server A → Message Queue → Chat Server B → Receiver
- Show presence indicator: user goes offline (heartbeat stops) → "last seen" updates
- Group chat: fan-out to all group members (multiple recipients highlighted)
- Show message storage: recent messages in cache, older in Cassandra

---

### FILE 6: `06_video_and_object_storage.html`
**Theme color**: `#fb923c` (orange — media)

#### Sections & Animations:

**Section 6.1 — YouTube: Video Upload Pipeline**
- Raw video file uploaded to original storage
- Task scheduler triggers encoding workers
- DAG (Directed Acyclic Graph) of encoding tasks animates:
  - Video transcoding: 360p, 720p, 1080p, 4K (parallel branches)
  - Audio transcoding
  - Thumbnail generation
  - Watermarking
- Encoded files uploaded to CDN distribution nodes

**Section 6.2 — YouTube: Video Streaming**
- User requests a video → CDN edge node serves it if cached
- CDN miss → origin fetches from storage
- Adaptive bitrate: user's bandwidth degrades (bar drops) → quality auto-switches from 1080p to 480p (animate label change)
- Show CDN PoP (Points of Presence) on world map

**Section 6.3 — S3-like Object Storage**
- Bucket → Objects hierarchy (folder-like tree)
- Upload: data split into chunks → distributed across data nodes (consistent hash ring decides placement)
- Metadata stored separately (show metadata service as a fast lookup layer)
- Erasure coding: file split into 6 data + 3 parity chunks → any 3 can be lost and file recovered (animate losing 3 nodes and reconstructing)
- Versioning: slider showing different versions of same object

---

### FILE 7: `07_geo_and_realtime.html`
**Theme color**: `#34d399` (emerald — location)

#### Sections & Animations:

**Section 7.1 — Proximity Service: Geohash**
- World map → zoom into a city grid
- Animate the recursive subdivision of space into geohash cells
- Show different precision levels (zoom in = more precise hash = smaller cell)
- User drops a pin → geohash computed → nearby businesses queried from matching cells
- Show edge case: user at geohash boundary → must also query adjacent cells

**Section 7.2 — Proximity Service: Quadtree**
- Empty space → animate recursive quad subdivision when points are added
- Leaf node capacity hit → node splits into 4 children (animate the split)
- Range query: draw a circle → highlight all intersecting quad nodes

**Section 7.3 — Nearby Friends**
- Map with user dots (mobile devices)
- WebSocket connections shown as thin lines to chat servers
- Each user publishes location every 30s → pub-sub channel per user
- Friends subscribed to your channel receive location update (animate the ripple)
- Redis pub-sub routing visualization: publisher → Redis → multiple subscribers

**Section 7.4 — Google Maps: Routing**
- Graph of road network (nodes = intersections, edges = road segments)
- Dijkstra / A* algorithm animation: frontier expanding, shortest path highlighted
- Traffic data overlaid: roads turn red/yellow/green based on real-time speed
- ETA recalculation: road segment speed changes → path recalculates

**Section 7.5 — Google Maps: Map Tiles**
- World map split into a grid of tiles at different zoom levels
- Show tile hierarchy (zoom 0 = 1 tile, zoom 18 = millions)
- User pans the map → tile IDs computed → fetched from CDN
- Pre-generated static tiles vs. dynamic rendering for real-time data

---

### FILE 8: `08_data_pipelines.html`
**Theme color**: `#818cf8` (indigo — data flow)

#### Sections & Animations:

**Section 8.1 — Distributed Message Queue (Kafka-like)**
- Topics → Partitions layout diagram
- Producer sends messages → assigned to partition by key hash
- Messages animate into partition as ordered log entries (sequential blocks)
- Multiple consumer groups: each group reads independently, different offsets highlighted
- Retention: old messages fade out after retention period
- Show replication: leader partition + 2 followers, leader failure → follower election animation

**Section 8.2 — Metrics Monitoring System**
- Metrics sources: servers, DBs, apps → collection agents
- Time-series DB (like Prometheus/InfluxDB): show how time-stamped metrics are stored
- Aggregation: raw metrics → 1-min rollups → 1-hour rollups (animated compression)
- Alert rules: threshold line on chart → when metric crosses → alert fires (red flash, notification icon)
- Dashboard: live updating line chart with multiple metrics

**Section 8.3 — Ad Click Event Aggregation**
- Billions of ad click events streaming in
- Lambda architecture: show both paths:
  - **Batch layer**: events stored in data lake → daily MapReduce job → aggregated reports
  - **Speed layer**: events go through stream processor (Flink/Spark) → real-time counter
  - **Serving layer**: query combines both
- Kappa architecture alternative: single streaming path (toggle button to switch views)
- Deduplication: same click event arrives twice → fingerprint checked → second dropped

---

### FILE 9: `09_financial_systems.html`
**Theme color**: `#facc15` (gold — finance)

#### Sections & Animations:

**Section 9.1 — Hotel Reservation**
- Inventory table with room counts
- Concurrent reservations: two users booking last room simultaneously (race condition shown with red conflict)
- Solution A — Optimistic locking: version number check → one wins, other retries (animate the retry)
- Solution B — Database constraint: CHECK constraint prevents negative inventory
- Idempotency key: same request sent twice → second is ignored (idempotency store lookup animation)

**Section 9.2 — Payment System**
- Pay-in flow: User → Payment Service → PSP (Stripe/Braintree) → Card Networks (Visa/MC) → Issuing Bank
- Pay-out flow: Platform → Wallet service → Bank transfer
- Show double-entry accounting ledger: every transaction has debit + credit entry
- Reconciliation: daily batch job compares internal ledger vs PSP records → discrepancies flagged (red highlight)
- Webhook handling: PSP sends async result → idempotency check before processing

**Section 9.3 — Digital Wallet**
- Event sourcing pattern: show a series of events (deposit $100, withdraw $20, transfer $30)
- Wallet balance = replay of all events (animate summing events to get balance)
- Show distributed transaction (Transfer between wallets) using:
  - 2-Phase Commit (2PC): Phase 1 prepare → Phase 2 commit — show coordinator + 2 participants
  - Saga pattern alternative: compensating transactions if one step fails

**Section 9.4 — Stock Exchange**
- Order book visualization: real-time bid/ask table with animated new orders
- Matching engine: buy order at $100 arrives → matches existing sell at $100 → trade executed (green flash)
- Market order vs. limit order distinction (animate different routing)
- Show sequencer: ensures strict ordering of events (queue with sequence numbers)
- Show the ring buffer / LMAX Disruptor pattern: lock-free queue between sequencer and matching engine

---

## 🧩 TECHNICAL IMPLEMENTATION NOTES

### HTML File Structure Template
```html
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>[Concept Group Name] — System Design</title>
  <!-- Google Fonts -->
  <link rel="preconnect" href="https://fonts.googleapis.com" />
  <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600;700&family=JetBrains+Mono:wght@400;600&display=swap" rel="stylesheet" />
  <!-- anime.js -->
  <script src="https://cdnjs.cloudflare.com/ajax/libs/animejs/3.2.1/anime.min.js"></script>
  <!-- d3.js -->
  <script src="https://d3js.org/d3.v7.min.js"></script>
  <style>
    /* CSS custom properties for theming */
    :root {
      --bg: #0d1117;
      --bg-card: #161b22;
      --bg-card-hover: #1c2330;
      --border: #30363d;
      --text: #e6edf3;
      --text-muted: #8b949e;
      --accent: /* file-specific accent color */;
      --accent-dim: /* 30% opacity version */;
    }
    /* Scroll progress bar */
    /* Navigation bar */
    /* Section card styles */
    /* SVG diagram container styles */
    /* Animation target classes */
    /* Interactive control styles */
  </style>
</head>
<body>
  <div id="progress-bar"></div>
  <nav id="navbar"><!-- section links --></nav>
  <main>
    <section id="section-1" class="concept-section">
      <div class="section-header">
        <h2>Section Title</h2>
        <p class="section-desc">Brief concept description</p>
      </div>
      <div class="diagram-area">
        <svg id="diagram-1"><!-- animated SVG diagram --></svg>
      </div>
      <div class="controls"><!-- interactive buttons/sliders --></div>
      <div class="info-panel"><!-- key insight text --></div>
    </section>
    <!-- more sections -->
  </main>
  <script>
    // All animation logic here
    // IntersectionObserver to trigger animations on scroll
    // Interactive control handlers
  </script>
</body>
</html>
```

### Animation Trigger Pattern
Use `IntersectionObserver` to trigger animations when sections scroll into view:
```javascript
const observer = new IntersectionObserver((entries) => {
  entries.forEach(entry => {
    if (entry.isIntersecting) {
      animateSection(entry.target.id);
      observer.unobserve(entry.target);
    }
  });
}, { threshold: 0.25 });

document.querySelectorAll('.concept-section').forEach(el => observer.observe(el));
```

### Packet/Data Flow Pattern (reusable)
```javascript
function animatePacket(svgId, pathId, color, label, duration) {
  const path = document.querySelector(`#${svgId} #${pathId}`);
  const length = path.getTotalLength();
  const packet = document.createElementNS('http://www.w3.org/2000/svg', 'circle');
  packet.setAttribute('r', 6);
  packet.setAttribute('fill', color);
  svg.appendChild(packet);
  anime({
    targets: packet,
    translateX: anime.path(path)('x'),
    translateY: anime.path(path)('y'),
    duration: duration,
    easing: 'easeInOutSine',
    complete: () => svg.removeChild(packet)
  });
}
```

---

## ✅ QUALITY CHECKLIST

Each HTML file must satisfy ALL of the following before being considered complete:

- [ ] **Self-contained**: All code in a single `.html` file; only CDN dependencies allowed
- [ ] **Loads fast**: No more than 3 CDN scripts; SVGs drawn in JS, not large embedded images
- [ ] **Scroll-triggered**: Animations start when section enters viewport (not all at page load)
- [ ] **Interactive**: At least 1 interactive control (button/slider/toggle) per file
- [ ] **Annotated**: Key components are labeled; concepts have a 2–3 line explanation below each diagram
- [ ] **Smooth**: All animations are 60fps (use `transform` and `opacity`, avoid layout-triggering properties)
- [ ] **Educational accuracy**: Concepts are correct and aligned with established system design and distributed systems practices
- [ ] **Accessible**: Sufficient color contrast; important info conveyed via text, not color alone
- [ ] **Dark mode only**: All 9 files use the same dark background base with file-specific accent colors
- [ ] **Navigation**: Fixed top navbar with smooth-scroll links to each section within the file
- [ ] **Footer**: Each file has a footer reading "System Design Concepts"

---

## 🚀 GENERATION ORDER

Generate files in this order, as later files build on concepts from earlier ones:

1. `01_scaling_fundamentals.html` — foundational; many other concepts depend on it
2. `02_core_distributed_systems.html` — theoretical underpinnings
3. `03_rate_limiter_and_id_gen.html` — concrete algorithm animations
4. `04_storage_and_search.html` — data structures in action
5. `05_feeds_and_messaging.html` — applied distributed systems
6. `06_video_and_object_storage.html` — heavy data systems
7. `07_geo_and_realtime.html` — spatial and real-time systems
8. `08_data_pipelines.html` — streaming and analytics
9. `09_financial_systems.html` — consistency-critical systems

---

## 💡 BONUS IDEAS (implement if time allows)

- **Index page** (`00_index.html`): A visual galaxy/constellation map where each star is a concept. Clicking a star navigates to the relevant HTML file. Stars cluster by topic group.
- **Quiz mode**: After the animation, a mini-quiz card appears: "Which algorithm would you choose if you need strict per-IP rate limiting?" with 4 options.
- **Comparison toggle**: Many sections have "Option A vs Option B" — add a toggle that flips between them with a smooth transition rather than showing both at once.
- **Speed control**: A global playback speed slider (0.5x, 1x, 2x) that scales all `anime.js` durations.

---

*End of Prompt*
