# DDD Animated HTML Series Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Create 6 self-contained animated HTML files in `system-design-concepts/domain-driven-design/` that visualize Domain-Driven Design concepts, matching the visual and technical conventions of the existing system-design-interview and DDIA series.

**Architecture:** Each file is a fully standalone HTML page (~1,700–1,900 lines) with inline CSS and JS. Animations are triggered by IntersectionObserver at 25% threshold. SVGs are built programmatically via `createElementNS`. All animation uses anime.js v3 (transform/opacity only). Generation order: concept files 01→05 first, then the index file 00 last.

**Tech Stack:** HTML5, CSS custom properties, anime.js v3.2.1 (CDN), d3.js v7 (CDN), Google Fonts (Inter + JetBrains Mono)

---

## File Map

| File | Path | Responsibility |
|------|------|---------------|
| Create | `system-design-concepts/domain-driven-design/01_building_blocks.html` | Entities, Value Objects, Aggregates, Domain Services, Factories |
| Create | `system-design-concepts/domain-driven-design/02_bounded_contexts.html` | Bounded Contexts, Ubiquitous Language, Context Mapping, ACL |
| Create | `system-design-concepts/domain-driven-design/03_domain_events.html` | Domain Events, Event Storming, Pub/Sub, Eventual Consistency |
| Create | `system-design-concepts/domain-driven-design/04_architectural_patterns.html` | Hexagonal Architecture, CQRS, Event Sourcing, Snapshots |
| Create | `system-design-concepts/domain-driven-design/05_ddd_in_practice.html` | Strategic/Tactical DDD, Subdomains, Pitfalls, E-commerce Example |
| Create | `system-design-concepts/domain-driven-design/00_index.html` | Galaxy/constellation navigation map linking to all 5 files |

---

## Shared Conventions (apply to ALL files)

**CSS variables (always include):**
```css
:root {
  --bg: #0d1117;
  --bg-card: #161b22;
  --bg-card-hover: #1c2330;
  --border: #30363d;
  --text: #e6edf3;
  --text-muted: #8b949e;
  --accent: /* file-specific hex */;
  --accent-dim: /* accent at 15% opacity, e.g. rgba(74,222,128,0.15) */;
}
```

**Scroll progress bar (MUST use scaleX, never width):**
```javascript
const bar = document.getElementById('progress-bar');
window.addEventListener('scroll', () => {
  const total = document.body.scrollHeight - window.innerHeight;
  if (total <= 0) return;
  bar.style.transform = 'scaleX(' + window.scrollY / total + ')';
});
```
```css
#progress-bar {
  position: fixed; top: 0; left: 0; right: 0; height: 3px;
  background: var(--accent);
  transform: scaleX(0); transform-origin: left center;
  z-index: 2000;
}
```

**IntersectionObserver pattern:**
```javascript
const observer = new IntersectionObserver((entries) => {
  entries.forEach(entry => {
    if (!entry.isIntersecting) return;
    const fn = window['animate_' + entry.target.id.replace('-','')];
    if (typeof fn === 'function') fn();
    observer.unobserve(entry.target);
  });
}, { threshold: 0.25 });
document.querySelectorAll('.concept-section').forEach(el => observer.observe(el));
```

**Animation function naming:** section id `s-11` → function `window.animate_s11()`

**SVG creation helpers (always include):**
```javascript
const NS = 'http://www.w3.org/2000/svg';
function svgEl(tag, attrs) {
  const el = document.createElementNS(NS, tag);
  Object.entries(attrs || {}).forEach(([k,v]) => el.setAttribute(k, v));
  return el;
}
```

**Animation rules:**
- Only animate `opacity` and `transform` (translateX, translateY, rotate) — never width/height/top/left
- `scale:` shorthand invalid in anime.js v3 — use `translateY` for bounce or `opacity` for pulse
- Never use `innerHTML` for dynamic content — use `textContent` and DOM methods
- Add generation guard to prevent stale animations: `const gen = ++svg._gen; ... if (svg._gen !== gen) return;`

**Navbar structure** (every content file):
```html
<div id="navbar">
  <span class="nav-brand">DDD</span>
  <div class="nav-chapters">
    <a class="nav-chip prev" href="NN_prev.html">← NN</a>
    <span class="nav-chip current">NN Current</span>
    <a class="nav-chip next" href="NN_next.html">NN →</a>
    <a class="nav-chip index-link" href="00_index.html">Index</a>
  </div>
</div>
```

**Footer** (all 6 files):
```html
<footer>Domain-Driven Design — Based on Eric Evans' DDD</footer>
```

---

## Task 1: `01_building_blocks.html` — Entities, Value Objects, Aggregates, Services

**Accent:** `#4ade80` (green) | `--accent-dim: rgba(74,222,128,0.15)`  
**Sections:** s-11 through s-18 (8 sections)  
**Nav:** no prev, next → `02_bounded_contexts.html`

### Section Content

**s-11 — The Problem: Messy Systems**
- SVG: ~12 boxes with crossing red arrows (chaotic), then animate to organized separated boxes with clean boundaries
- Show a red pulse traveling along connections to illustrate ripple effect
- Insight: "A change in one place ripples through 10 others. DDD gives us tools to model domains cleanly."

**s-12 — Entities (Reference Objects)**
- SVG: Two Customer boxes, same name "Alice", different IDs (C-001 vs C-002)
- Animate: C-001's address changes → only that entity updates, C-002 unchanged
- Highlight ID field in accent color as the identity marker
- Insight: "Entities are defined by identity, not attributes. Two entities can share all properties yet remain distinct."

**s-13 — Value Objects (Immutable Descriptors)**
- SVG: Two `Money { amount: 50, currency: "USD" }` objects
- Animate: show them as equal (green checkmark), then show one being replaced (old fades out, new fades in) vs mutated (red X)
- Insight: "Value Objects have no identity. They are equal if their attributes match. Never mutate — replace."

**s-14 — Entity vs Value Object Comparison**
- Side-by-side cards: Entity (Customer, Order, Product) vs Value Object (Money, Address, Color)
- Interactive toggle button: "Show Customer Entity" / "Show Address Value Object" — swaps diagram content
- Table: Identity? | Mutable? | Tracked? | Equality
- Insight: "If you care about WHICH thing it is, it's an Entity. If you care only about WHAT it is, it's a Value Object."

**s-15 — Aggregates**
- SVG: Order (root, accent-colored) with OrderLineItem×2 (blue), Price (green), Address (green) inside red dashed boundary
- Animate: quantity change on LineItem → root recalculates total → total pulses
- External arrow to Order root only, not to children
- Insight: "An Aggregate is a cluster with a root. All external references point only to the root. Invariants are enforced within."

**s-16 — Aggregate Rules & Boundaries**
- SVG: Order aggregate + Customer aggregate side by side
- Order holds `customerId` (value, not a reference to Customer object)
- Interactive "Place Order" button: multi-step animation — validate stock → validate payment → confirm → emit event
- Show T1 modifies Order, Customer unchanged
- Insight: "Cross-aggregate communication happens by ID. One transaction, one aggregate. Eventual consistency across aggregates."

**s-17 — Domain Services**
- SVG: `CurrencyExchangeService` box outside any aggregate boundary
- Animate: `Money{100, USD}` + `Money{?, EUR}` → service → `Money{92, EUR}`
- Contrast: anemic model (service has all logic, entities are data bags) vs rich model (entities have behavior)
- Insight: "Domain Services contain logic that doesn't naturally belong to any single Entity or Value Object."

**s-18 — Factories**
- SVG: Factory method box → produces fully initialized Order aggregate (root + line items + address)
- Animate: complex Order materializing piece by piece inside factory, then handed to client as complete unit
- Insight: "Factories encapsulate the complexity of creating Aggregates and Value Objects, keeping construction logic out of client code."

### Steps

- [ ] **Step 1: Write `01_building_blocks.html`**

  Write the complete file at `system-design-concepts/domain-driven-design/01_building_blocks.html`. Include:
  - Full `<head>` with CDN scripts, Google Fonts, and CSS (progress bar, navbar, sidebar, section cards, diagram area, insight panel, controls, responsive layout)
  - All 8 `<section class="concept-section">` blocks (s-11 through s-18) per content above
  - "Place Order" interactive button in s-16 triggering multi-step animation
  - "Toggle" interactive button in s-14 swapping entity vs value object view
  - Full JS: `animate_s11()` through `animate_s18()` using anime.js + SVG helpers
  - IntersectionObserver wiring, scroll progress bar, navbar, sidebar drawer, chapter page nav
  - Footer: `Domain-Driven Design — Based on Eric Evans' DDD`

- [ ] **Step 2: Verify in browser**

  Open `system-design-concepts/domain-driven-design/01_building_blocks.html` in a browser.
  - Scroll slowly — each section animation should trigger on entry (not all at once on load)
  - Click "Replay" on any section — animation resets and replays cleanly
  - Click "Place Order" in s-16 — 3-step validation animation completes
  - Click toggle in s-14 — entity/value object diagram swaps
  - Progress bar fills smoothly as you scroll (verify it uses scaleX in DevTools, not width)
  - No console errors

- [ ] **Step 3: Commit**

  ```bash
  git add system-design-concepts/domain-driven-design/01_building_blocks.html
  git commit -m "feat(ddd): add ch1 building blocks animated page"
  ```

---

## Task 2: `02_bounded_contexts.html` — Bounded Contexts, Ubiquitous Language, Context Mapping

**Accent:** `#f59e0b` (amber) | `--accent-dim: rgba(245,158,11,0.15)`  
**Sections:** s-21 through s-28 (8 sections)  
**Nav:** prev → `01_building_blocks.html`, next → `03_domain_events.html`

### Section Content

**s-21 — Ubiquitous Language**
- SVG: Left panel "Business" with speech bubble ("Customer wants to place an Order"), right panel "Code" with `customer.placeOrder()`. Shared glossary appears between them.
- Animate both sides terms highlighting in sync (same color pulse on "Customer", "Order", "place")
- Then show misalignment: business says "Customer", dev writes "UserEntity" → red conflict marker
- Insight: "Ubiquitous Language is a shared vocabulary between business and engineers, used consistently in conversation and code."

**s-22 — The Problem of One Language Everywhere**
- SVG: Giant monolith box containing Customer, Order, Invoice, SupportTicket, User, Account — all colors mixed
- Animate: change Customer for Sales → red X radiates through monolith touching all other concepts
- Label: "5 teams, 1 model, infinite confusion"
- Insight: "When everyone shares one model, 'Customer' can mean different things to Sales, Support, and Billing. Changes break everything."

**s-23 — Bounded Contexts Defined**
- SVG: Monolith explodes into 4 colored islands — Sales (amber), Support (violet), Shipping (sky), Billing (pink)
- Each island has its own "Customer" concept labeled differently (SalesCustomer, TicketHolder, Recipient, Payer)
- Clear boundary lines between them
- Insight: "A Bounded Context is an explicit boundary within which a domain model applies. The same word means different things in different contexts."

**s-24 — Sales Context Deep Dive**
- SVG: Zoom into Sales Context — entities (Order, Quote, LineItem), value objects (Price, Discount), services (PricingService)
- Other 3 contexts shown grayed in background
- Animate domain model assembling piece by piece inside the context boundary
- Insight: "Each Bounded Context owns its full domain model. Other contexts are external — accessed only through well-defined interfaces."

**s-25 — Context Map**
- SVG: 4 context islands from s-23, with 3 relationship connectors:
  - Solid double line: "Shared Kernel" between Sales and Billing
  - Dashed arrow: "Customer-Supplier" from Shipping to Sales (Shipping supplies to Sales)
  - Shield icon + arrow: "Anti-Corruption Layer" between Sales and "Legacy CRM"
- Interactive toggles (3 checkboxes): show/hide each relationship type
- Insight: "A Context Map documents how Bounded Contexts relate. Each relationship type has different coordination costs and coupling levels."

**s-26 — Shared Kernel**
- SVG: Sales and Billing overlap in center (the Shared Kernel area, hatched pattern)
- Code snippets in each context referencing `SharedKernel.Money`, `SharedKernel.Currency`
- Red warning: shared kernel is small, frozen, must be agreed upon by both teams before changes
- Insight: "A Shared Kernel is a deliberately small subset of the domain model shared between contexts. Changes require both team's consent."

**s-27 — Anti-Corruption Layer**
- SVG: Legacy CRM (gray, messy arrows) on left → ACL translator box (amber, shield icon) → Clean Sales Context on right
- Animate: raw legacy data `{cust_id: "X", c_name: "Alice"}` enters ACL → transforms → `Customer { id: "C-001", name: "Alice" }` emerges
- Insight: "The ACL translates the external system's model into your bounded context's language, preventing pollution of your domain model."

**s-28 — Open Host Service & Published Language**
- SVG: Sales Context box with an "OHS" port on the right, publishing a JSON/REST schema
- 3 external consumers connecting to the published schema (Logistics App, Analytics, Partner API)
- Animate: new consumer plugs in without modifying the Sales Context internals
- Insight: "An Open Host Service exposes a well-documented Published Language. External consumers adapt to it — not the other way around."

### Steps

- [ ] **Step 1: Write `02_bounded_contexts.html`**

  Write the complete file at `system-design-concepts/domain-driven-design/02_bounded_contexts.html`. Include:
  - All CSS with amber `--accent: #f59e0b`
  - All 8 sections (s-21 through s-28)
  - Interactive show/hide toggles in s-25 (3 checkbox-style buttons for relationship types)
  - Full JS animations per section content above
  - Navbar with prev=`01_building_blocks.html`, next=`03_domain_events.html`, index=`00_index.html`
  - Footer: `Domain-Driven Design — Based on Eric Evans' DDD`

- [ ] **Step 2: Verify in browser**

  Open `02_bounded_contexts.html` in browser.
  - Scroll through all 8 sections — animations trigger correctly on scroll
  - In s-25: click each relationship toggle — lines appear/disappear cleanly
  - s-27 ACL animation: data transformation plays through completely
  - No console errors, no layout breaks at 1280px+

- [ ] **Step 3: Commit**

  ```bash
  git add system-design-concepts/domain-driven-design/02_bounded_contexts.html
  git commit -m "feat(ddd): add ch2 bounded contexts animated page"
  ```

---

## Task 3: `03_domain_events.html` — Domain Events, Event Storming, Pub/Sub

**Accent:** `#a78bfa` (violet) | `--accent-dim: rgba(167,139,250,0.15)`  
**Sections:** s-31 through s-36 (6 sections)  
**Nav:** prev → `02_bounded_contexts.html`, next → `04_architectural_patterns.html`

### Section Content

**s-31 — What is a Domain Event?**
- SVG: Event object `{ type: "OrderPlaced", occurredAt: "2026-04-27T10:00Z", payload: { orderId: "O-123" } }`
- Animate: Order aggregate calls `raise(OrderPlacedEvent)` → event "flies" from aggregate to event bus (dotted path)
- Lock icon on event: immutable, never modified
- Insight: "A Domain Event is an immutable record of something that happened in the domain. Past tense. Facts."

**s-32 — OrderPlaced Event Flow**
- SVG: User → Order Aggregate → Event Bus → 3 handlers (Shipping, Billing, Notification) fanning out
- Interactive "Place Order" button: triggers sequential animation — aggregate validates → event raised → 3 arrows animate to each handler simultaneously
- Color each handler differently (shipping=sky, billing=amber, notification=green)
- Insight: "One event, many handlers. Each bounded context reacts independently, decoupling producers from consumers."

**s-33 — Event Storming**
- SVG: Long horizontal timeline board
- Animate stickies appearing in sequence:
  - Orange "commands" (PlaceOrder, ConfirmPayment, ShipOrder)
  - Violet "events" (OrderPlaced, PaymentConfirmed, OrderShipped)  
  - Blue "read models" (OrderStatus view, InventoryView)
  - Stick figure "actors" (Customer, Warehouse)
- Each type labeled with its sticky color in a legend
- Insight: "Event Storming is a collaborative workshop: orange=commands, violet=events, blue=read models, yellow=actors. Map the whole business flow in one session."

**s-34 — Aggregates & State Machine**
- SVG: Order aggregate with state machine inside: Draft → Submitted → Paid → Shipped → Delivered
- Animate each transition: click "Advance State" button steps through states
- Each transition emits a named event (shown flying off the aggregate)
- Event history list builds up on the right
- Insight: "Aggregates evolve through state transitions. Each transition is a meaningful business moment captured as a Domain Event."

**s-35 — Publish/Subscribe Pattern**
- SVG: SalesContext publisher on left, 3 subscribers (Shipping, Notification, Analytics) on right connected by dashed lines
- Animate: event published → 3 delivery arrows → success on 2, failure (red X) on Analytics
- Dead letter queue box at bottom: failed event queued → retry arrow → success
- Insight: "Pub/Sub decouples event producers from consumers. Failed deliveries go to a Dead Letter Queue for retry, ensuring no event is lost."

**s-36 — Eventual Consistency**
- SVG: SalesContext on left, ShippingContext on right, time axis below
- T1 marker: OrderPlaced event raised in Sales
- T2 marker: event received by Shipping
- Between T1 and T2: Sales shows "Confirmed", Shipping shows "Pending" (inconsistent, highlighted yellow)
- After T2: both show "Confirmed" (consistent, green)
- Insight: "Between event publication and consumption, contexts are temporarily inconsistent. Design your system to tolerate this window."

### Steps

- [ ] **Step 1: Write `03_domain_events.html`**

  Write the complete file at `system-design-concepts/domain-driven-design/03_domain_events.html`. Include:
  - All CSS with violet `--accent: #a78bfa`
  - All 6 sections (s-31 through s-36)
  - "Place Order" button in s-32 with sequential fan-out animation
  - "Advance State" button in s-34 stepping through the Order state machine
  - Dead letter queue + retry animation in s-35
  - Full IntersectionObserver wiring, navbar, sidebar, progress bar, footer

- [ ] **Step 2: Verify in browser**

  Open `03_domain_events.html` in browser.
  - s-32: Click "Place Order" — aggregate validates, then 3 simultaneous arrows animate to handlers
  - s-34: Click "Advance State" repeatedly — state transitions correctly Draft→Submitted→Paid→Shipped→Delivered, events appear in history
  - s-35: Dead letter queue animation completes with retry success
  - s-36: Consistency timeline animation shows T1/T2 gap clearly
  - No console errors

- [ ] **Step 3: Commit**

  ```bash
  git add system-design-concepts/domain-driven-design/03_domain_events.html
  git commit -m "feat(ddd): add ch3 domain events animated page"
  ```

---

## Task 4: `04_architectural_patterns.html` — Hexagonal Architecture, CQRS, Event Sourcing

**Accent:** `#38bdf8` (sky blue) | `--accent-dim: rgba(56,189,248,0.15)`  
**Sections:** s-41 through s-48 (8 sections)  
**Nav:** prev → `03_domain_events.html`, next → `05_ddd_in_practice.html`

### Section Content

**s-41 — Hexagonal Architecture Overview**
- SVG: Hexagon with "Domain" at center (accent color), ports on edges (RepositoryPort, PaymentPort, NotificationPort on left/bottom), driving adapters on right (RESTController, CommandHandler)
- Animate: ports appearing on edges, then adapters plugging in
- Domain center stays static, adapters animate around it
- Insight: "The domain sits at the center, completely isolated. Ports are interfaces the domain declares. Adapters are the implementations the outside world provides."

**s-42 — Ports (Interfaces)**
- SVG: Hexagon edges become "interface holes", typed with `interface OrderRepository { findById(id: string): Order; save(order: Order): void; }`
- Animate: domain code referencing `OrderRepository` interface (no MySQL, no Postgres mentioned)
- Insight: "Ports are interfaces. The domain knows what it needs (an OrderRepository) but not how it's provided. This is the Dependency Inversion Principle in action."

**s-43 — Adapters (Swappable)**
- SVG: Left side: MySQL adapter connected to repository port. Animate: MySQL adapter detaches (slides away), PostgreSQL adapter slides in, connects
- Domain hexagon center unchanged throughout
- Interactive toggle: "Switch to PostgreSQL" button triggers the swap animation
- Insight: "Adapters are pluggable. Swap MySQL for PostgreSQL — your domain never knows. Tests use an in-memory adapter; production uses the real one."

**s-44 — CQRS: Split Screen**
- SVG: Split vertically — Command side (left, accent=sky) vs Query side (right, accent=amber)
- Left: `PlaceOrderCommand` → CommandHandler → Domain → OrderPlacedEvent → EventBus → Projection updates ReadModel
- Right: `GetOrderQuery` → QueryHandler → ReadModel → instant response (no domain involvement)
- Animate both sides simultaneously on "Run" button click
- Insight: "CQRS separates writes (commands through domain) from reads (direct to read model). Each side is optimized independently."

**s-45 — CQRS Event Flow & Performance**
- SVG: Two timelines — Command path (50ms: domain validation + persistence + event) and Query path (2ms: pre-built view)
- Animate timer counting for each path
- Toggle button: show Command model schema (normalized tables) vs Read model schema (denormalized view)
- Insight: "The write model is normalized for correctness. The read model is denormalized for speed. CQRS lets you optimize each independently."

**s-46 — Event Sourcing**
- SVG: Traditional (left): single `balance: $100` row in DB. Event Sourced (right): append-only log of events
- Events: `Deposited $50` → `Withdrew $20` → `Deposited $70` → replay running total → `balance: $100`
- Animate replay: events highlight sequentially, running total counter increments
- Insight: "Event Sourcing stores state as a sequence of events. Current state is computed by replaying events. You never lose history."

**s-47 — Event Sourcing vs Traditional (Toggle)**
- Interactive toggle button switching between two views:
  - Traditional: UPDATE balance SET amount=100 → row overwritten, old value lost (red indicator)
  - Event Sourced: INSERT event → log grows, old events preserved (green indicator)
- Animate the state transition between views
- Insight: "Traditional storage destroys history on each update. Event Sourcing treats history as the source of truth."

**s-48 — Snapshots**
- SVG: Horizontal event stream of 500 events
- Animate snapshots appearing at events 100, 200, 300, 400 (checkpoint markers)
- Show replay: "Replay from snapshot 400 + 100 events" vs "Replay all 500" — highlight the efficiency
- Progress bars comparing replay time (with vs without snapshots)
- Insight: "Snapshots periodically capture aggregate state so replay starts from the checkpoint, not event #1. Essential for long-lived aggregates."

### Steps

- [ ] **Step 1: Write `04_architectural_patterns.html`**

  Write the complete file at `system-design-concepts/domain-driven-design/04_architectural_patterns.html`. Include:
  - All CSS with sky blue `--accent: #38bdf8`
  - All 8 sections (s-41 through s-48)
  - "Switch to PostgreSQL" toggle in s-43 with adapter swap animation
  - "Run" button in s-44 triggering simultaneous command + query animations
  - Timer animations in s-45 (50ms vs 2ms)
  - Toggle in s-47 switching between traditional vs event-sourced views
  - Snapshot efficiency animation in s-48
  - Full navbar (prev=`03_domain_events.html`, next=`05_ddd_in_practice.html`), sidebar, progress bar, footer

- [ ] **Step 2: Verify in browser**

  Open `04_architectural_patterns.html` in browser.
  - s-43: "Switch to PostgreSQL" — adapter slides out and new one slides in, domain unchanged
  - s-44: "Run" — both command and query paths animate simultaneously
  - s-46: Event replay animation shows running total counting up correctly
  - s-47: Toggle cleanly switches between traditional and event-sourced views
  - s-48: Snapshot markers appear, replay progress bars animate
  - No console errors

- [ ] **Step 3: Commit**

  ```bash
  git add system-design-concepts/domain-driven-design/04_architectural_patterns.html
  git commit -m "feat(ddd): add ch4 architectural patterns animated page"
  ```

---

## Task 5: `05_ddd_in_practice.html` — Applied DDD, Pitfalls, E-Commerce Example

**Accent:** `#f472b6` (pink) | `--accent-dim: rgba(244,114,182,0.15)`  
**Sections:** s-51 through s-57 (7 sections)  
**Nav:** prev → `04_architectural_patterns.html`, no next (last file)

### Section Content

**s-51 — Strategic vs Tactical DDD**
- SVG: Two columns — Strategic (big picture): Bounded Contexts, Context Maps, Subdomains, Ubiquitous Language. Tactical (code): Entities, Value Objects, Aggregates, Domain Events, Repositories, Services.
- Animate: system first decomposed strategically (contexts emerge), then zoomed into tactical (entities assemble inside)
- Insight: "Strategic DDD defines where and how to draw boundaries. Tactical DDD defines how to implement inside those boundaries."

**s-52 — Subdomains**
- SVG: Business domain circle broken into 3 regions:
  - Core Domain: gold star, largest, "Your Competitive Advantage" (e.g., Pricing Engine)
  - Supporting Subdomain: silver, medium, "Needed but not differentiating" (e.g., Notifications)
  - Generic Subdomain: bronze, smallest, "Buy it off-the-shelf" (e.g., Authentication, Email)
- Animate investment sliders: Core gets most effort (heavy modeling), Generic gets least (use library)
- Insight: "Not all parts of your domain deserve equal effort. Invest deeply where you have unique competitive advantage."

**s-53 — DDD in Code**
- SVG: Folder tree animating into place:
  ```
  src/
  ├── domain/
  │   ├── entities/       (Order.java, Customer.java)
  │   ├── valueObjects/   (Money.java, Address.java)
  │   ├── aggregates/     (OrderAggregate.java)
  │   ├── services/       (CurrencyExchangeService.java)
  │   ├── events/         (OrderPlacedEvent.java)
  │   └── repositories/   (OrderRepository.java - interface only)
  └── infrastructure/
      └── persistence/    (JpaOrderRepository.java)
  ```
- Animate files sliding into their correct folders
- Insight: "Domain code has zero framework imports. Infrastructure adapters implement domain interfaces. The dependency arrow always points inward."

**s-54 — Repository Pattern**
- SVG: Two boxes — `OrderRepository` (interface, in domain layer) and `JpaOrderRepository` (implementation, in infrastructure layer)
- Animate: client code depends on interface (arrow from client to interface), implementation injected via DI (arrow from DI container to impl)
- Show: `@Repository`, `@Entity`, `@Table` annotations appear only in infrastructure box
- Insight: "The domain declares what it needs (an interface). The infrastructure provides the implementation. Dependency Injection wires them at runtime."

**s-55 — Common DDD Pitfalls**
- 4 pitfall cards animate in from bottom:
  1. **Anemic Domain Model**: Entity with only getters/setters, all logic in service → show `orderService.calculateTotal(order)` (red) vs `order.calculateTotal()` (green)
  2. **God Aggregate**: One aggregate with 50+ fields and 20 child entities → size bloat animation, then split into 3 smaller aggregates
  3. **Misplaced Business Logic**: pricing logic in REST controller (red floor highlighted) → move to domain service (green)
  4. **Context Bleed**: Sales Customer and Support TicketHolder sharing a class → colors mixing, then separated
- Each card has a "wrong" view first, then "correct" view slides in
- Insight: "DDD's biggest enemy is convenience. The anemic domain model is the #1 pitfall — it looks like OO but isn't."

**s-56 — DDD + Modern Frameworks (Spring Boot)**
- SVG: Spring Boot project structure with `@SpringBootApplication`
- Show `@Entity` annotations present only in infrastructure package
- `@DomainService` stereotypes on domain services
- CommandHandler wired to domain via MediatR-style pattern
- Animate: request flows from REST controller → command → handler → aggregate → event → projection
- Insight: "Frameworks are infrastructure. Annotate your adapters, not your domain objects. Your domain should be testable without starting Spring."

**s-57 — Real-World E-Commerce Example**
- SVG: 4 bounded contexts animate in as colored islands:
  - Catalog (green): Product, Category, Inventory
  - Order (sky): Order, OrderLineItem, OrderStatus
  - Payment (amber): Payment, Invoice, Refund
  - Customer (pink): Customer, Address, LoyaltyPoints
- Context map connections between them (with event labels on arrows)
- Interactive "Place Order" button: chain of events animates across all 4 contexts:
  1. Order context: OrderPlaced
  2. Payment context: PaymentInitiated → PaymentConfirmed
  3. Catalog context: InventoryReserved
  4. Customer context: LoyaltyPointsAdded
  5. Order context: OrderConfirmed
- Insight: "DDD lets complex domains evolve independently. Each context has its own team, codebase, and deployment. Events connect them."

### Steps

- [ ] **Step 1: Write `05_ddd_in_practice.html`**

  Write the complete file at `system-design-concepts/domain-driven-design/05_ddd_in_practice.html`. Include:
  - All CSS with pink `--accent: #f472b6`
  - All 7 sections (s-51 through s-57)
  - Investment slider animation in s-52 (Core/Supporting/Generic effort bars)
  - Folder tree build animation in s-53
  - 4 pitfall cards with wrong→right transition in s-55
  - Interactive "Place Order" multi-context event chain in s-57
  - Navbar (prev=`04_architectural_patterns.html`, no next), sidebar, progress bar, footer

- [ ] **Step 2: Verify in browser**

  Open `05_ddd_in_practice.html` in browser.
  - s-52: Effort bars animate correctly (Core gets most, Generic gets least)
  - s-53: Folder tree files animate into correct locations
  - s-55: All 4 pitfall cards animate in; wrong/right views are distinct
  - s-57: "Place Order" triggers event chain across all 4 contexts in sequence
  - Navbar "prev" link points to `04_architectural_patterns.html`, no "next" chip shown
  - No console errors

- [ ] **Step 3: Commit**

  ```bash
  git add system-design-concepts/domain-driven-design/05_ddd_in_practice.html
  git commit -m "feat(ddd): add ch5 DDD in practice animated page"
  ```

---

## Task 6: `00_index.html` — Galaxy Navigation Map

**Accent:** Multi-color (each concept file gets its own accent)  
**Nav:** No prev/next (index page) — each "star" links to its concept file  
**Pattern:** Match existing `00_index.html` files in system-design-interview and DDIA series

### Content

Galaxy/constellation map with 5 "star clusters" (one per concept file):
- **Building Blocks** (green `#4ade80`): center-left, 8-pointed star shape
- **Bounded Contexts** (amber `#f59e0b`): upper-right, larger orbit
- **Domain Events** (violet `#a78bfa`): lower-right, mid orbit
- **Architectural Patterns** (sky `#38bdf8`): upper-left, far orbit
- **DDD in Practice** (pink `#f472b6`): bottom-center, near orbit

Animated constellation lines connecting concepts that build on each other (Building Blocks → Domain Events → Architectural Patterns). Stars pulse with their accent color. Clicking a star or its label navigates to the corresponding HTML file.

Title card: "Domain-Driven Design" with subtitle "Based on Eric Evans' DDD"

Since this is an index file, animations trigger on page load (not IntersectionObserver).

### Steps

- [ ] **Step 1: Write `00_index.html`**

  Write the complete file at `system-design-concepts/domain-driven-design/00_index.html`. Include:
  - Galaxy SVG with 5 star clusters, each positioned and colored per its concept file
  - Stars drawn with d3.js or anime.js — each star is a clickable `<a>` wrapping a star SVG shape
  - Constellation lines animating in on page load (stroke-dashoffset animation)
  - Stars pulse with their accent color (opacity animation loop)
  - Topic labels below each star with chapter number
  - Navbar: "DDD" brand, no chapter chips (index has no prev/next)
  - No progress bar (index pages are exempt per CLAUDE.md)
  - No sidebar drawer (index pages typically use direct star click navigation)
  - Footer: `Domain-Driven Design — Based on Eric Evans' DDD`

- [ ] **Step 2: Verify in browser**

  Open `00_index.html` in browser.
  - Constellation lines animate in on page load
  - All 5 stars pulse continuously
  - Clicking any star or label navigates to the correct `.html` file
  - Hover state on stars shows a glow/highlight effect
  - Title and subtitle readable against dark background
  - No console errors

- [ ] **Step 3: Commit**

  ```bash
  git add system-design-concepts/domain-driven-design/00_index.html
  git commit -m "feat(ddd): add galaxy index navigation page"
  ```

---

## Self-Review Checklist

- [x] All 6 files planned with complete section content
- [x] No TBD or placeholder steps — all SVG content, animations, and interactive behaviors described
- [x] Accent colors match prompt.md: green/amber/violet/sky/pink/multi
- [x] Footer matches required text: "Domain-Driven Design — Based on Eric Evans' DDD"
- [x] Animation rules enforced: transform/opacity only, scaleX for progress bar, no innerHTML
- [x] IntersectionObserver pattern included in all concept files
- [x] Index page exempt from IntersectionObserver (uses load animations per CLAUDE.md)
- [x] Interactive controls: ≥1 per file (Place Order, toggles, state machine stepper, switch buttons)
- [x] Nav prev/next links: Task 1 has no prev, Task 5 has no next, Task 6 (index) has neither
- [x] Generation order: 01→02→03→04→05→00 (index last)
- [x] Type/naming consistency: section IDs `s-NN`, function names `animate_sNN`, SVG IDs `svg-NN`
