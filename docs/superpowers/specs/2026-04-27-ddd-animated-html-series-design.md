# Design: Domain-Driven Design Animated HTML Series

**Date:** 2026-04-27  
**Status:** Approved  
**Source prompt:** `system-design-concepts/domain-driven-design/prompt.md`

---

## Context

This series adds a sixth learning track to the back-to-basics repo: animated, self-contained HTML visualizations of Eric Evans' Domain-Driven Design. The DDD directory already exists with only a `prompt.md`. Six files will be created to match the visual and technical conventions already established by the system-design-interview and DDIA series.

---

## File Structure

| # | Filename | Accent Color | Sections |
|---|----------|-------------|---------|
| 0 | `00_index.html` | Multi-color | Galaxy/constellation nav map |
| 1 | `01_building_blocks.html` | `#4ade80` green | 8 sections: Entities, Value Objects, Aggregates, Domain Services, Factories |
| 2 | `02_bounded_contexts.html` | `#f59e0b` amber | 8 sections: Ubiquitous Language, Bounded Contexts, Context Maps, ACL, Shared Kernel |
| 3 | `03_domain_events.html` | `#a78bfa` violet | 6 sections: Domain Events, Event Storming, Pub/Sub, Eventual Consistency |
| 4 | `04_architectural_patterns.html` | `#38bdf8` sky blue | 8 sections: Hexagonal Architecture, CQRS, Event Sourcing, Snapshots |
| 5 | `05_ddd_in_practice.html` | `#f472b6` pink | 7 sections: Strategic vs Tactical, Subdomains, Code Walkthrough, Pitfalls, E-commerce Example |

**Total:** 6 files, ~1,700–1,900 lines each, all self-contained.

---

## Technical Architecture

### CDN Dependencies (matching all other series)
- `anime.js` v3.2.1 from cdnjs.cloudflare.com
- `d3.js` v7 from d3js.org
- Google Fonts: `Inter` + `JetBrains Mono`

### CSS Variables (per file)
```css
:root {
  --bg: #0d1117;
  --bg-card: #161b22;
  --bg-card-hover: #1c2330;
  --border: #30363d;
  --text: #e6edf3;
  --text-muted: #8b949e;
  --accent: /* file-specific */;
  --accent-dim: /* 15% opacity version */;
}
```

### Page Structure (every content file)
```
<div id="progress-bar">          ← scaleX scroll indicator
<div id="navbar">                ← fixed top: chapter chips + prev/next
<button id="sidebar-toggle">     ← hamburger
<aside id="sidebar-drawer">      ← full chapter list, collapsible groups
<nav id="quicknav">              ← in-page section jump links
<main>
  <section id="s-N1" class="concept-section">
    <div class="section-card">
      <div class="card-header">   ← section number + title + replay button
      <div class="diagram-area">  ← SVG canvas
      <div class="insight-panel"> ← explanation text
<div class="chapter-page-nav">   ← bottom prev/next chapter navigation
<footer>Domain-Driven Design — Based on Eric Evans' DDD</footer>
```

### Animation Rules (enforced by CLAUDE.md)
- Animate **only** `transform` and `opacity` — never `width`, `height`, `top`, `left`
- Progress bar: `transform: scaleX(ratio)` with `transform-origin: left center` — never `style.width`
- `scale:` shorthand invalid in anime.js v3 — use `opacity` pulse or `translateY`
- SVG built via `createElementNS` — no `innerHTML`
- Guard division: `if (total <= 0) return;` before scroll ratio calculations
- IntersectionObserver at `threshold: 0.25`, unobserve after first trigger
- Animation function names: `window.animate_sNN()` matching section ID `s-NN`

### Interactive Controls (≥1 per file)
Each file has clickable "replay" buttons per section plus at least one domain-specific interactive:
- File 1: "Place Order" button triggering multi-step aggregate validation animation
- File 2: Toggles to show/hide context map relationship types
- File 3: "Publish Event" button triggering fan-out to 3 subscribers
- File 4: Toggle between command model and read model views
- File 5: "Place Order" triggering cross-context event chain

### Footer
All 6 files: `Domain-Driven Design — Based on Eric Evans' DDD`

### Navigation
- Index page links to all 5 concept files
- Each concept file navbar: "DDD Index" link + prev/next chapter chips
- Sidebar drawer lists all 6 files as a series

---

## Content Mapping (per prompt.md)

### 00_index.html
Galaxy/constellation map — 5 "star clusters" for the 5 concept files, animated connections showing DDD concept relationships. Matches the pattern of existing `00_index.html` files in other series.

### 01_building_blocks.html (8 sections)
1.1 Messy Systems (chaos → order animation)  
1.2 Entities (ID-based identity, two customers same name different ID)  
1.3 Value Objects (immutable, replace not mutate)  
1.4 Entity vs Value Object comparison (interactive toggle)  
1.5 Aggregates (Order root + children, red boundary)  
1.6 Aggregate Rules (cross-aggregate by ID only, place-order simulation)  
1.7 Domain Services (CurrencyExchangeService outside any aggregate)  
1.8 Factories (complex aggregate construction abstraction)

### 02_bounded_contexts.html (8 sections)
2.1 Ubiquitous Language (business ↔ dev alignment animation)  
2.2 Monolith problem (one Customer means 5 things)  
2.3 Bounded Contexts (monolith splits into 4 colored islands)  
2.4 Sales Context deep dive (zoomed domain model)  
2.5 Context Map (4 contexts + relationship lines: solid/dashed/ACL)  
2.6 Shared Kernel (two contexts overlap, constraint visualization)  
2.7 Anti-Corruption Layer (legacy → ACL → clean model)  
2.8 Open Host Service & Published Language

### 03_domain_events.html (6 sections)
3.1 What is a Domain Event? (event object, immutable fact)  
3.2 OrderPlaced event flow (3 handlers fan-out)  
3.3 Event Storming (timeline board, orange commands → events)  
3.4 Aggregates & event generation (state machine, Draft→Delivered)  
3.5 Publish/Subscribe (dead letter queue, retry arrows)  
3.6 Eventual Consistency (T1 raised, T2 received, sync animation)

### 04_architectural_patterns.html (8 sections)
4.1 Hexagonal Architecture overview (hexagon, ports, adapters)  
4.2 Ports as interfaces (domain declares needs)  
4.3 Adapters swappable (MySQL → PostgreSQL swap animation)  
4.4 CQRS split-screen (command left, query right)  
4.5 CQRS event flow (write 50ms, read 2ms, projection update)  
4.6 Event Sourcing (append-only log, balance replay)  
4.7 Event Sourcing vs Traditional (toggle view)  
4.8 Snapshots (500 events, checkpoints every 100)

### 05_ddd_in_practice.html (7 sections)
5.1 Strategic vs Tactical DDD (two-column decompose → implement)  
5.2 Subdomains (Core/Supporting/Generic with gold/silver/bronze)  
5.3 DDD in code (folder structure animation)  
5.4 Repository pattern (interface → JPA implementation via DI)  
5.5 Common pitfalls (4 pitfall cards: anemic model, god object, misplaced service, context leak)  
5.6 DDD + modern frameworks (Spring Boot structure)  
5.7 Real-world e-commerce (4 bounded contexts, full event chain on "place order")

---

## Generation Order
1. `01_building_blocks.html` (foundational)
2. `02_bounded_contexts.html`
3. `03_domain_events.html`
4. `04_architectural_patterns.html`
5. `05_ddd_in_practice.html`
6. `00_index.html` (built last — links to all 5 completed files)

---

## Verification
- Open each HTML file directly in a browser (no server needed)
- Scroll through all sections — animations should trigger on entry
- Click all interactive buttons — validate animations complete and reset correctly
- Verify progress bar fills smoothly using `scaleX` (not width)
- Verify navbar prev/next chapter links resolve correctly
- Check footer reads "Domain-Driven Design — Based on Eric Evans' DDD"
