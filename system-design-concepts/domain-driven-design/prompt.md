# Prompt: Generate Animated HTML Visualizations for Domain-Driven Design

---

## 🎯 OBJECTIVE

You are an expert frontend developer and system design educator. Your task is to create a **series of rich, animated, self-contained HTML files** that visually explain Domain-Driven Design (DDD) concepts from Eric Evans' "Domain-Driven Design: Tackling Complexity in the Heart of Software." Each HTML file must be fully standalone (no external dependencies except CDN-hosted libraries), visually engaging, and educational.

---

## 🧩 TOPICS COVERED

### Core DDD Concepts:
1. The Problem of Messy, Tightly Coupled Systems
2. Ubiquitous Language — Aligning Business & Engineering
3. Bounded Contexts — Dividing Large Domains
4. Entities vs Value Objects — Identity vs Attributes
5. Aggregates — Enforcing Domain Invariants
6. Repositories — Abstracting Data Access
7. Domain Services — Logic Beyond Entities
8. Domain Events — Capturing Business Happenings
9. Event Storming — Discovering the Domain
10. Context Mapping — Relationships Between Bounded Contexts
11. Hexagonal Architecture — Ports & Adapters
12.CQRS — Command Query Responsibility Segregation
13. Event Sourcing — Storing State as Event History

---

## 📁 OUTPUT FILES TO GENERATE

Generate the following **5 animated HTML files**, each covering a logical grouping of concepts:

| File # | Filename | Concepts Covered |
|--------|----------|-----------------|
| 1 | `01_building_blocks.html` | Entities, Value Objects, Aggregates, Domain Services |
| 2 | `02_bounded_contexts.html` | Bounded Contexts, Ubiquitous Language, Context Mapping |
| 3 | `03_domain_events.html` | Domain Events, Event Storming, Publish/Subscribe |
| 4 | `04_architectural_patterns.html` | Hexagonal Architecture, CQRS, Event Sourcing |
| 5 | `05_ddd_in_practice.html` | Tactical vs Strategic DDD, DDD in Real Code, Common Pitfalls |

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
- Boxes, arrows, and connections that travel between bounded contexts or aggregates
- Use CSS `stroke-dashoffset` animation or `anime.js` path following
- Color-coded elements (entities = blue, value objects = green, services = orange)

**2. State Transition Animations**
- Objects that pulse, change color, or scale when they interact
- Aggregates that lock or unlock during a transaction
- Bounded contexts that expand/contract as subdomains are added or removed

**3. Building/Construction Animations**
- Domain models that draw themselves progressively on scroll or on a timer
- Entities and value objects assembling into aggregates
- New bounded contexts appearing as the domain model grows

**4. Interactive Elements** (at least 1 per file)
- Clickable buttons to simulate "place order", "create user", "transfer funds"
- Sliders to show aggregate size limits or consistency boundaries
- Toggle switches for showing different modes (entity vs value object, command vs query)

**5. Counter/Metric Animations**
- Animated counters showing transaction counts, event history lengths
- Live bar charts updating in real-time (simulated)
- Sequence number animations for domain events

---

## 📋 PER-FILE DETAILED SPECIFICATIONS

---

### FILE 1: `01_building_blocks.html`
**Theme color**: `#4ade80` (green — foundational)

#### Sections & Animations:

**Section 1.1 — The Problem: Messy Systems**
- Start with a tangled, chaotic diagram of interconnected objects (all arrows crossing, everything coupled)
- Animate the chaos resolving into clean, separated boxes with clear boundaries
- Show the pain: a simple change ripples through 10 places (red pulse traveling along connections)

**Section 1.2 — Entities (Reference Objects)**
- Draw two "Customer" entities
- Both have the same name and attributes, but different IDs
- Show that they are NOT equal — highlight the ID as what distinguishes them
- Animate: one entity's address changes — only that entity updates

**Section 1.3 — Value Objects (Immutable Descriptors)**
- Draw a "Money" value object — amount + currency
- Show two "50 USD" objects: they ARE equal even if separate instances
- Animate: a value object being replaced, not modified (new instance created)
- Contrast with entity mutability

**Section 1.4 — Entities vs Value Objects Comparison**
- Side-by-side cards comparing entity and value object traits
- Interactive toggle: flip between examples (Customer entity vs Address value object)
- Show lifecycle difference: entities are tracked, value objects are descriptors

**Section 1.5 — Aggregates**
- Draw a cluster: one aggregate root (Order) with child entities (OrderLineItems) and value objects (Price, Address)
- Red boundary line drawn around the aggregate — enforce invariants inside
- Animate: change a line item quantity → aggregate root validates → recalculates total
- Show: external references only point to aggregate root, never to internal children

**Section 1.6 — Aggregate Rules & Boundaries**
- Two aggregates side-by-side (Order aggregate, Customer aggregate)
- Animate a cross-aggregate operation: Order references Customer by ID only, not by embedding
- Show consistency boundary: transaction T1 modifies Order aggregate, Customer aggregate unchanged
- Interactive: click "place order" → aggregate validates stock, payment, inventory (multi-step animation)

**Section 1.7 — Domain Services**
- Draw a Domain Service class outside any aggregate or entity (e.g., `CurrencyExchangeService`)
- Animate: passing two Money objects with different currencies → service computes exchange
- Contrast with anemic domain model: logic scattered in transactionscripts vs rich behavior in domain objects
- Show: service uses multiple aggregates/entities but belongs to none

**Section 1.8 — Factories**
- Animate: creating a complex aggregate (Order with multiple line items, nested addresses)
- Show factory abstracting construction complexity from client code
- Draw a factory method producing a correctly initialized aggregate

---

### FILE 2: `02_bounded_contexts.html`
**Theme color**: `#f59e0b` (amber — boundaries)

#### Sections & Animations:

**Section 2.1 — Ubiquitous Language**
- Left side: Business stakeholder speaking ("Customer wants to place an Order")
- Right side: Developer writing code (`customer.placeOrder()`)
- Animate both sides aligning — terms syncing up with a shared glossary
- Show terms highlighted consistently: "Customer", "Order", "place"
- Show misalignment example: business "Customer" vs dev "UserEntity" — red conflict marker

**Section 2.2 — The Problem of One Language Everywhere**
- A giant " monolith domain" box containing everything: Customer, Order, Invoice, SupportTicket, User, Account
- All concepts using the same terms ambiguously — "Customer" means 5 different things
- Animate: a change to Customer for sales breaks the support context (red X across the monolith)

**Section 2.3 — Bounded Contexts Defined**
- The monolith splits into 4 separate colored boxes: Sales Context, Support Context, Shipping Context, Billing Context
- Each box has its own "Customer" concept, labeled differently (SalesCustomer, SupportTicketHolder, etc.)
- Clear boundaries drawn between them — no mixed responsibilities

**Section 2.4 — Sales Context Deep Dive**
- Zoom into Sales Context: show full domain model (entities, value objects, aggregates)
- Animate the ubiquitous language specific to sales: Quote, Order, LineItem, Pricing
- Other contexts grayed out in background to show isolation

**Section 2.5 — Context Map**
- Show 4 bounded contexts as colored islands
- Draw relationships between them with different connector types:
  - Solid line: Shared Kernel (Sales and Billing share a common subset)
  - Dashed line: Customer-Supplier (Shipping "supplies" to Sales)
  - Anti-corruption layer: Sales uses an adapter to talk to Legacy system
- Clickable toggles to show/hide each relationship type

**Section 2.6 — Shared Kernel**
- Two contexts overlap with a shared area (Shared Kernel)
- Animate: code in each context referencing the shared kernel classes
- Show the constraint: shared kernel must be kept small and agreed upon by both teams

**Section 2.7 — Anti-Corruption Layer**
- Draw Legacy System on the left, Bounded Context on the right
- Animate: a translation layer (ACL) converting legacy's messy model into the clean domain model
- Show before/after: raw legacy data transforms into rich domain objects

**Section 2.8 — Open Host Service & Published Language**
- Bounded context exposes a protocol (Published Language: XML/JSON schema)
- External systems consume and build adapters against the published language
- Animate: new external system connecting via the published language without modifying the context

---

### FILE 3: `03_domain_events.html`
**Theme color**: `#a78bfa` (violet — activity)

#### Sections & Animations:

**Section 3.1 — What is a Domain Event?**
- Show an event object: `{ eventType: "OrderPlaced", occurredAt: "...", payload: {...} }`
- Animate: Order aggregate calls `DomainEvents.raise(new OrderPlacedEvent(...))`
- Event "flies" from aggregate to event bus
- Show immutable nature: events are historical facts, never modified

**Section 3.2 — OrderPlaced Event Flow**
- User clicks "Place Order"
- Order aggregate validates and transitions state
- OrderPlacedEvent raised → dispatched to multiple handlers:
  - ShippingContext handler: begins fulfillment
  - BillingContext handler: initiates payment
  - NotificationContext handler: sends confirmation email
- Animate 3 arrows fanning out to different bounded contexts

**Section 3.3 — Event Storming Introduction**
- A long timeline/banana-board style canvas
- Animate domain events being stickied onto the board in chronological order: OrderPlaced → PaymentReceived → OrderShipped → OrderDelivered
- Show orange "commands" preceding events: PlaceOrder command → OrderPlaced event
- Show blue "read models" appearing as projections of events
- Add actors (stick figures) triggering commands

**Section 3.4 — Aggregates & Event Generation**
- Draw an aggregate (Order) with a state machine inside
- Animate state transitions: Draft → Submitted → Paid → Shipped → Delivered
- Each transition emits a domain event (OrderSubmitted, OrderPaid, etc.)
- Show event recording in aggregate's event history

**Section 3.5 — Publish/Subscribe Pattern**
- Publisher: SalesContext
- Subscribers: ShippingContext, NotificationContext, AnalyticsContext
- Animate: event published once → received by all 3 subscribers independently
- Show subscription management: new subscriber can subscribe to past events (if replay enabled)
- Dead letter queue: failed event delivery shown in red, retry arrows

**Section 3.6 — Eventual Consistency Between Contexts**
- Show two bounded contexts with a domain event bridging them
- Time arrow: event raised at T1, ShippingContext receives at T2
- Highlight the consistency window: between T1 and T2, views are inconsistent
- Animate: both contexts slowly "syncing" to the same state after event delivery

---

### FILE 4: `04_architectural_patterns.html`
**Theme color**: `#38bdf8` (sky blue — structure)

#### Sections & Animations:

**Section 4.1 — Hexagonal Architecture Overview**
- Draw a hexagon with "Domain" at the center (highlighted in accent color)
- Animate ports (interfaces) appearing on the left side: RepositoryPort, PaymentPort, NotificationPort
- Animate adapters (implementations) appearing on the left: MySQLRepositoryAdapter, StripeAdapter, SendGridAdapter
- Animate driving adapters on the right: RESTController, CommandHandler
- Show: domain has no dependencies on external frameworks or infrastructure

**Section 4.2 — Ports (Interfaces)**
- Draw interface contracts as "holes" in the hexagon
- Type definitions appear: `interface OrderRepository { findById(id): Order; save(order): void; }`
- Animate: domain declares it needs a repository, doesn't care about MySQL vs PostgreSQL

**Section 4.3 — Adapters (Implementations)**
- Draw MySQL adapter plugging into the repository port
- Animate: switching to PostgreSQL adapter — old adapter detaches, new one plugs in — domain unchanged
- Show: adapters are swappable, domain is isolated

**Section 4.4 — CQRS (Command Query Responsibility Segregation)**
- Split screen: Command side (left) vs Query side (right)
- Left: User submits command → CommandHandler → Domain → Event → EventBus → Projection
- Right: Query arrives → ReadModel (pre-built projection) → instant response
- Animate: writes going through full domain model; reads bypassing domain entirely
- Show separate models: Command model (rich, normalized) vs Query model (denormalized, read-optimized)

**Section 4.5 — CQRS Event Flow**
- Animate: new Order created (command) → OrderPlacedEvent → projection updates read model
- Query side: `SELECT * FROM order_read_model WHERE customer_id = ?` returns instantly
- Show: command side takes 50ms (domain logic), query side takes 2ms (pre-built view)
- Toggle: show what the read model looks like vs the write model

**Section 4.6 — Event Sourcing**
- Traditional approach: Store current state (balance: $100)
- Event sourcing approach: Store all events (Deposited $50, Withdrew $20, Deposited $70 → balance = $100)
- Animate replay: events listed sequentially → running total computed → final balance revealed
- Show event store as an append-only log

**Section 4.7 — Event Sourcing vs Traditional**
- Toggle between two views:
  - Traditional: update balance row → old state overwritten
  - Event sourcing: append Deposited event → old state preserved, new event added
- Show snapshots: every 100 events → collapse into a snapshot for faster replay

**Section 4.8 — Snapshots**
- Animate: event stream of 500 events
- At event 100: snapshot created (Order #1 state captured)
- At event 200: snapshot created
- Replay from snapshot at 200 + next 50 events = faster than replaying all 250

---

### FILE 5: `05_ddd_in_practice.html`
**Theme color**: `#f472b6` (pink — applied)

#### Sections & Animations:

**Section 5.1 — Strategic vs Tactical DDD**
- Two-column layout: Strategic (big picture) vs Tactical (code-level)
- Strategic: Bounded Contexts, Context Maps, Ubiquitous Language, Subdomains
- Tactical: Entities, Value Objects, Aggregates, Domain Events, Repositories, Domain Services
- Animate a system being decomposed strategically first, then implemented tactically

**Section 5.2 — Subdomains**
- Business domain broken into 3 subdomains:
  - Core Domain (gold star): competitive advantage, most important
  - Supporting Subdomain (silver): needed but not differentiating
  - Generic Subdomain (bronze): commodity, buy vs build
- Animate: where to invest most effort (heavy modeling on Core Domain, off-the-shelf for Generic)

**Section 5.3 — DDD in Code — A Walkthrough**
- Show a real code structure: `domain/` folder with `entities/`, `valueObjects/`, `services/`, `events/`, `aggregates/`
- Animate: placing code into correct directories based on DDD type
- Show naming conventions: `Order.java`, `Money.java`, `CurrencyExchangeService.java`

**Section 5.4 — Repository Implementation**
- Draw repository interface in domain layer (no framework imports)
- Draw JPA implementation in infrastructure layer (@Entity, @Table annotations)
- Animate: client code depending only on interface, implementation injected via DI
- Show: how the adapter pattern isolates domain from persistence details

**Section 5.5 — Common DDD Pitfalls**
- 4 pitfall cards animate in:
  1. Anemic Domain Model: entities with getters/setters but no behavior (red warning)
  2. God Objects: massive aggregates doing too much (size bloat animation)
  3. Misplaced services: business logic in application layer instead of domain (wrong floor highlighted)
  4. Context leaks: bounded contexts bleeding into each other (colors mixing)
- Show correct versions alongside each pitfall

**Section 5.6 — Integrating DDD with Modern Frameworks**
- Spring Boot application structure: domain folder separate from infrastructure
- Animate: `@Entity` annotations only in infrastructure adapters, not in domain
- Show CQRS with MediatR or similar library wiring commands to handlers

**Section 5.7 — Real-World DDD Example — E-Commerce**
- Full e-commerce bounded contexts animate in:
  - Catalog Context: Product, Category, Inventory
  - Order Context: Order, OrderLineItem, OrderStatus
  - Payment Context: Payment, Invoice, Refund
  - Customer Context: Customer, Address, LoyaltyPoints
- Context map connecting them with events
- Clickable: click "place order" → chain of events flows across all contexts

---

## 🧩 TECHNICAL IMPLEMENTATION NOTES

### HTML File Structure Template
```html
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>[Concept Group Name] — Domain-Driven Design</title>
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
- [ ] **Educational accuracy**: Concepts are correct and aligned with Eric Evans' DDD principles
- [ ] **Accessible**: Sufficient color contrast; important info conveyed via text, not color alone
- [ ] **Dark mode only**: All 5 files use the same dark background base with file-specific accent colors
- [ ] **Navigation**: Fixed top navbar with smooth-scroll links to each section within the file
- [ ] **Footer**: Each file has a footer reading "Domain-Driven Design — Based on Eric Evans' DDD"

---

## 🚀 GENERATION ORDER

Generate files in this order, as later files build on concepts from earlier ones:

1. `01_building_blocks.html` — foundational entities, value objects, aggregates, services
2. `02_bounded_contexts.html` — strategic DDD: context boundaries and language
3. `03_domain_events.html` — events and their role in connecting contexts
4. `04_architectural_patterns.html` — hexagonal, CQRS, event sourcing
5. `05_ddd_in_practice.html` — applied DDD, common pitfalls, real-world example

---

## 💡 BONUS IDEAS (implement if time allows)

- **Index page** (`00_index.html`): A visual mind-map where each branch is a DDD concept cluster. Clicking a branch navigates to the relevant HTML file. Animated connections show relationships between concepts.
- **Quiz mode**: After the animation, a mini-quiz card appears: "Is this an Entity or a Value Object?" with 4 options and explanations.
- **DDD case study toggle**: Show the same e-commerce example in both "bad/anemic" design vs proper DDD design.
- **Event storming mini-simulator**: A drag-and-drop board where users can place commands, events, and actors.

---

*End of Prompt*
