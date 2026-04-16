# Coding Concepts Animated HTML Suite — Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Build 9 self-contained animated HTML files in `coding-concepts/` that visually teach foundational coding principles to both new joiners and experienced engineers.

**Architecture:** Two-wave parallel generation — Wave 1 builds the foundational 4 concept files simultaneously, Wave 2 builds the diagnostic/synthesis 3 files + index. Each file is a standalone HTML document with CDN-only dependencies. No build step.

**Tech Stack:** HTML/CSS/JS, anime.js v3 (CDN), d3.js v7 (CDN), Google Fonts (Inter + JetBrains Mono)

---

## Reference Files

Before starting any task, read these two files — they define every requirement:
- **Full content spec:** `coding-concepts/coding_concepts_animation_prompt.md`
- **Design spec:** `docs/superpowers/specs/2026-04-16-coding-concepts-animations-design.md`

Also study an existing file for patterns:
- **Pattern reference:** `system-design-concepts/01_scaling_fundamentals.html` — follow its navbar structure, progress bar, chapter pager, CSS variable names, and IntersectionObserver pattern exactly.

---

## Shared Boilerplate (apply to every file 01–07)

Every content file must open with this exact structure:

```html
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>[Concept Group Name] — Coding Concepts</title>
  <link rel="preconnect" href="https://fonts.googleapis.com" />
  <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600;700&family=JetBrains+Mono:wght@400;600&display=swap" rel="stylesheet" />
  <script src="https://cdnjs.cloudflare.com/ajax/libs/animejs/3.2.1/anime.min.js"></script>
  <script src="https://d3js.org/d3.v7.min.js"></script>
  <style>
    :root {
      --bg: #0d1117;
      --bg-card: #161b22;
      --bg-card-hover: #1c2330;
      --border: #30363d;
      --text: #e6edf3;
      --text-muted: #8b949e;
      --accent: /* FILE-SPECIFIC */;
      --accent-dim: /* 15% opacity accent */;
      --good: #4ade80;
      --bad: #f87171;
      --neutral: #8b949e;
    }
    /* ... */
  </style>
</head>
<body>
  <div id="progress-bar"></div>   <!-- thin accent-colored bar at top, z-index:1000 -->
  <nav id="navbar">
    <a class="back-link" href="00_index.html">← Index</a>
    <!-- chapter pager: prev chip | current chip | next chip -->
    <!-- in-page section anchor links -->
  </nav>
  <main><!-- sections --></main>
  <footer>
    <p>Based on <em>Clean Code</em> — Robert C. Martin · <em>The Pragmatic Programmer</em> — Hunt &amp; Thomas · <em>Refactoring</em> — Martin Fowler · <em>Working Effectively with Legacy Code</em> — Michael Feathers</p>
  </footer>
  <script>
    // Progress bar
    // IntersectionObserver (threshold: 0.25) for each .concept-section
    // Expandable panel toggles (🟢/🔵 badges)
    // All interactive controls
    // anime.js animations per section
  </script>
</body>
</html>
```

### Chapter pager nav order
```
01 → 02 → 03 → 04 → 05 → 06 → 07
```
Each file shows ← prev | current | next → chips. File 01 has no prev. File 07 has no next.

### Per-section template
Every `<section class="concept-section" data-section="N">` must contain:
1. Section header with title + audience badges
2. Metaphor/analogy animation area (SVG or D3)
3. ❌/✅ code comparison panel (bad left, good right)
4. Violation → consequence → fix cascade animation (D3 or SVG nodes)
5. At least one interactive control (button/slider/toggle)
6. 🟢 expandable "New to this?" panel (hidden by default, toggled by badge click)
7. 🔵 expandable "Go deeper" panel (hidden by default, toggled by badge click)
8. Complexity/coupling meter (animated bar or gauge)

### Code comparison panel markup
```html
<div class="code-comparison">
  <div class="code-panel bad">
    <div class="panel-label">❌ Violates the principle</div>
    <pre><code><!-- bad code with <span class="kw">, .fn, .str, .cm, .num, .tp --> </code></pre>
  </div>
  <div class="code-panel good">
    <div class="panel-label">✅ Follows the principle</div>
    <pre><code><!-- good code --></code></pre>
  </div>
</div>
```

### Syntax highlight span classes
```css
.kw  { color: #ff7b72; }   /* keywords: if, return, class, etc. */
.fn  { color: #d2a8ff; }   /* function/method names */
.str { color: #a5d6ff; }   /* string literals */
.cm  { color: #8b949e; font-style: italic; } /* comments */
.num { color: #f2cc60; }   /* numeric literals */
.tp  { color: #ffa657; }   /* type names */
```

### Animation trigger pattern (copy exactly)
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

### Code transform animation pattern
```javascript
function animateCodeTransform(badEl, goodEl, durationMs = 800) {
  anime({
    targets: badEl,
    opacity: [1, 0],
    translateY: [0, -10],
    duration: durationMs / 2,
    easing: 'easeInSine',
    complete: () => {
      badEl.style.display = 'none';
      goodEl.style.display = 'block';
      anime({
        targets: goodEl,
        opacity: [0, 1],
        translateY: [10, 0],
        duration: durationMs / 2,
        easing: 'easeOutSine'
      });
    }
  });
}
```

### Constraints (never violate)
- Animate only `transform` and `opacity` — never `width`, `height`, `top`, `left`, `margin`
- No external syntax highlighter — `<span>` classes only
- Desktop-first 1280px+, must not break at 1024px
- Dark background `#0d1117` throughout
- No moralizing language ("always", "never", "you must") — frame as trade-offs

---

## Quality Checklist (run before every commit)

Check every item. If any fails, fix before committing.

- [ ] Opens in browser without console errors
- [ ] Progress bar fills as you scroll
- [ ] Navbar shows correct prev/next chapter chips
- [ ] All sections start invisible, animate in on scroll (not on load)
- [ ] Every section has 🟢 and 🔵 expandable panels that open/close on click
- [ ] Every section has at least one ❌/✅ code comparison block with syntax highlighting
- [ ] At least one interactive control per file works (button/slider/toggle)
- [ ] Every section shows what breaks on violation (not just the correct version)
- [ ] No console errors from anime.js or d3.js
- [ ] Footer is present with all four book credits
- [ ] Looks correct at both 1280px and 1024px widths

---

## WAVE 1 — Tasks 1–4 (run in parallel)

---

### Task 1: `01_clean_code_fundamentals.html`

**File:** Create `coding-concepts/01_clean_code_fundamentals.html`  
**Accent:** `#4ade80` (green)  
**Sections:** Naming · Functions · Single Level of Abstraction (SLA) · Comments · Formatting & Structure  
**Full content spec:** See `coding_concepts_animation_prompt.md` sections 1.1–1.5

- [ ] **Step 1: Create the file**

  Build the full HTML file with all 5 sections. For each section implement:

  **Section 1.1 — Naming**
  - Metaphor: blurry photo sharpening as names go from `d`, `temp`, `x2` → `daysSinceLastLogin`, `userEmail`, `discountedPrice`. Use CSS `filter: blur()` animated via anime.js (`opacity`/`transform` for the reveal, blur via CSS class toggle).
  - Code comparison:
    ```
    ❌  int d; // days since last login
    ✅  int daysSinceLastLogin;

    ❌  List<int[]> getThem() { ... }
    ✅  List<Cell> getFlaggedCells() { ... }
    ```
  - Interactive: "Cognitive Load" meter — a bar that fills red for bad names, drops to green for good names. Toggle via a "Show good names" button.
  - 🟢 Analogy: labeling every moving box "stuff"
  - 🔵 Nuance: acceptable abbreviations (`url`, `id`, `dto`); Hungarian notation is obsolete

  **Section 1.2 — Functions**
  - Metaphor: SVG factory machine — input enters, one transformation, output exits. Second SVG shows a "do-everything machine" sparking.
  - Code comparison: a 4-argument function vs. same function using a Parameter Object
  - Interactive: argument count slider (0–4+). Meter turns red at 3+.
  - 🟢 Rule: if you need "and" to describe it, it does too much
  - 🔵 Nuance: pure functions vs. side effects — isolate side effects at system edges

  **Section 1.3 — Single Level of Abstraction (SLA)**
  - Metaphor: layered cake SVG — each layer = abstraction level. Mixed-level function highlighted in red where layers jump.
  - Code comparison: function mixing HTML rendering + business logic + DB queries vs. clean decomposed version
  - Interactive: "Fix" button — watch function decompose into named sub-functions via staggered anime.js reveal
  - 🟢 Analogy: newspaper hierarchy — headline → sub-headline → body copy, no mixed font sizes
  - 🔵 Nuance: Step-down rule — each function calls the next level down

  **Section 1.4 — Comments**
  - Metaphor: SVG "comment graveyard" — tombstone-red stale comments
  - Code comparison: comment explaining *what* (bad) vs. comment explaining *why* (good)
  - Interactive: reveal toggle showing a complex regex — "would you comment this?" reveal button shows answer
  - 🟢 Always-OK comments: legal notices, intent explanations, API quirks, TODOs with tickets
  - 🔵 Design smell comments: anything explaining what the code does; commented-out code blocks

  **Section 1.5 — Formatting & Structure**
  - Metaphor: D3 "city street" diagram — well-formatted = clear signage vs. cluttered street
  - Show newspaper metaphor: important things top, details below — vertical distance animation
  - 🔵 Key insight: rules don't matter as long as consistent — use autoformatters

- [ ] **Step 2: Run quality checklist**

  Open `coding-concepts/01_clean_code_fundamentals.html` in a browser. Check every item in the Quality Checklist above. Fix any failures before proceeding.

- [ ] **Step 3: Commit**

  ```bash
  git add coding-concepts/01_clean_code_fundamentals.html
  git commit -m "feat: add 01_clean_code_fundamentals animated HTML"
  ```

---

### Task 2: `02_solid_principles.html`

**File:** Create `coding-concepts/02_solid_principles.html`  
**Accent:** `#f59e0b` (amber)  
**Sections:** SRP · OCP · LSP · ISP · DIP  
**Full content spec:** See `coding_concepts_animation_prompt.md` sections 2.1–2.5

- [ ] **Step 1: Create the file**

  The SOLID acronym letters animate in one by one (staggered `opacity` + `translateY` via anime.js) as a hero animation at the top of the page. Each letter links to its section.

  **Section 2.1 — Single Responsibility Principle (SRP)**
  - Metaphor: SVG Swiss Army knife (one bloated node) vs. chef's knife (focused nodes)
  - D3 diagram: `UserManager` god class with glowing responsibility nodes (auth, email, reports, date formatting) attached. Click "Apply SRP" → class explodes into `AuthService`, `EmailService`, `ReportGenerator` nodes via D3 force simulation.
  - Interactive: drag responsibility nodes onto a class node — watch it bloat. Remove them — watch it focus.
  - Change impact demo: "email format changes" → in bad design full `UserManager` flashes red; in good design only `EmailService` flashes.
  - 🟢 "One reason to change" — name more than one stakeholder = too many responsibilities
  - 🔵 Nuance: SRP is fractal — method, class, module, service level

  **Section 2.2 — Open/Closed Principle (OCP)**
  - Metaphor: USB port (extension safe) vs. soldering directly to motherboard (modification risky)
  - Code comparison: `PaymentProcessor` with `if type == "stripe" / else if type == "paypal"` vs. `PaymentProvider` interface with `StripeProvider`, `PayPalProvider` implementations
  - Show: adding `ApplePayProvider` = new file only (green "extension = safe") vs. modifying the `if` chain (red "modification = risk")
  - 🔵 Nuance: OCP doesn't mean never modify. Premature abstraction before knowing the variation axis is YAGNI.

  **Section 2.3 — Liskov Substitution Principle (LSP)**
  - Metaphor: SVG contract document — sign it, honor every clause
  - Animated crash: `Bird` with `fly()`, `Penguin extends Bird` overrides `fly()` to throw — animate exception crash
  - Fix: `FlyingBird` / `NonFlyingBird` hierarchy
  - Second example: `Square extends Rectangle` — setting width also sets height, silent breakage animation
  - Interactive: drop a subclass into a function expecting parent — green pass or red flash
  - 🟢 Litmus: if you need `instanceof`, you're probably violating LSP
  - 🔵 Nuance: behavioural subtyping — preconditions can't strengthen, postconditions can't weaken, invariants must hold

  **Section 2.4 — Interface Segregation Principle (ISP)**
  - Metaphor: bloated restaurant menu vs. focused tasting menu
  - D3 diagram: fat `IWorker` interface forcing `Robot` to implement `eat()` / `sleep()` — animate robot trying to eat, crashing
  - Split into `IWorkable` / `IFeedable` / `ISleepable` — Robot only gets `IWorkable`
  - 🔵 Nuance: if interface is fat, implementation likely violates SRP too — fix SRP first

  **Section 2.5 — Dependency Inversion Principle (DIP)**
  - Metaphor: power outlet standard — laptop doesn't depend on specific power plant
  - D3 diagram: `OrderService` → `MySQLRepository` hard dependency arrow pointing down. Apply DIP → both point to `IOrderRepository` abstraction.
  - Interactive: swap `MySQLRepository` → `PostgreSQLRepository` → `InMemoryRepository` with a dropdown — watch system adapt, `OrderService` unchanged
  - 🔵 Nuance: DIP ≠ DI. DI is mechanism; DIP is principle. Spring/Guice enforce DIP but DIP can be applied manually.

- [ ] **Step 2: Run quality checklist**

  Open `coding-concepts/02_solid_principles.html` in a browser. Check every item in the Quality Checklist. Fix any failures.

- [ ] **Step 3: Commit**

  ```bash
  git add coding-concepts/02_solid_principles.html
  git commit -m "feat: add 02_solid_principles animated HTML"
  ```

---

### Task 3: `03_dry_kiss_yagni.html`

**File:** Create `coding-concepts/03_dry_kiss_yagni.html`  
**Accent:** `#a78bfa` (violet)  
**Sections:** DRY · KISS · YAGNI · Boy Scout Rule · Fail Fast  
**Full content spec:** See `coding_concepts_animation_prompt.md` sections 3.1–3.5

- [ ] **Step 1: Create the file**

  **Section 3.1 — DRY (Don't Repeat Yourself)**
  - Metaphor: SVG master document vs. photocopier — original updates propagate; copies need manual updates
  - D3 diagram: 5 nodes representing duplicated validation logic. "Business rule changes" event → all 5 flash red, 3 developers miss 2 of them (2 nodes stay red = bugs)
  - Interactive: DRY-ness slider from "fully duplicated" (5 red nodes) → "fully extracted" (1 green source-of-truth node, 5 blue dependency arrows). Change radius counter: shows `5 edits` → `1 edit`.
  - 🔵 Nuance: Rule of Three — abstract on third occurrence, not second. Wrong abstraction > duplication (Sandi Metz). DRY is about knowledge, not syntax.

  **Section 3.2 — KISS (Keep It Simple, Stupid)**
  - Metaphor: SVG Rube Goldberg machine (long animated chain) vs. single button press
  - Code comparison: regex with 10 lookaheads vs. 3-line readable parser
  - Interactive: "Complexity meter" — cyclomatic complexity score bar. Two code snippets, toggle between them, watch score change.
  - 🟢 Question: "Would a new team member understand this in 30 seconds?"
  - 🔵 Quote: "Any fool can write code a computer understands. Good programmers write code humans understand." — Fowler

  **Section 3.3 — YAGNI (You Aren't Gonna Need It)**
  - Metaphor: SVG over-packed suitcase vs. lean itinerary-exact packing
  - Animated codebase growing with unused abstractions, flags, plugin systems — dead code mass meter rising
  - Show speculative feature vs. actual future requirement — they don't match (different shape/color node)
  - 🔵 Nuance: OCP/YAGNI tension — extend when you have the second use case, not before. First user defines interface; second user validates it.

  **Section 3.4 — Boy Scout Rule**
  - Metaphor: SVG hiking trail — maintained by everyone vs. tragedy of the commons
  - D3 line chart: codebase metrics (complexity, warnings) trending upward over time with bad PRs, then trending downward with Boy Scout PRs
  - Show concrete "leave it cleaner" examples: rename confusing variable, extract readable sub-function, delete dead code branch
  - 🔵 Key constraint: "in the area you're already touching" — not a license for large refactors

  **Section 3.5 — Fail Fast**
  - Metaphor: SVG dashboard warning light vs. waiting for engine seizure
  - Animated request flow: bad input travels deep → crashes in unrelated component (long red path). Then fail-fast: validation at boundary → immediate rejection (short red path, clear stack trace)
  - Debugging time comparison bar chart: Fail-slow = 2 hours. Fail-fast = immediate.
  - 🟢 Rule: validate at system border; assert invariants inside
  - 🔵 Nuance: Fail-fast = offensive programming. Know when defensive is needed (user-facing input, payments, external APIs).

- [ ] **Step 2: Run quality checklist**

  Open `coding-concepts/03_dry_kiss_yagni.html` in browser. Check every item. Fix failures.

- [ ] **Step 3: Commit**

  ```bash
  git add coding-concepts/03_dry_kiss_yagni.html
  git commit -m "feat: add 03_dry_kiss_yagni animated HTML"
  ```

---

### Task 4: `04_oo_design_principles.html`

**File:** Create `coding-concepts/04_oo_design_principles.html`  
**Accent:** `#38bdf8` (sky blue)  
**Sections:** Composition over Inheritance · Law of Demeter · Tell Don't Ask · CQS · POLA  
**Full content spec:** See `coding_concepts_animation_prompt.md` sections 4.1–4.5

- [ ] **Step 1: Create the file**

  **Section 4.1 — Composition over Inheritance**
  - Metaphor: SVG LEGO bricks vs. pre-built toy that can't be modified
  - D3 tree: deep hierarchy `Animal → Mammal → Pet → Dog → GuideDog` — animate a feature request ("dogs can swim") requiring a wrong-level change. Show `RoboticGuideDog` breaking the hierarchy.
  - Composition version: `Dog` node with pluggable `SwimmingBehaviour`, `GuideBehaviour`, `FetchBehaviour` modules
  - Interactive: drag-and-drop behavior modules onto entity node — watch capabilities added without touching hierarchy
  - 🔵 Nuance: use inheritance for type identity (IS-A), composition for behaviour sharing

  **Section 4.2 — Law of Demeter (LoD)**
  - Metaphor: "Don't talk to strangers" — ask your friend for their number, don't reach into their pocket
  - Animated chain call: `order.getCustomer().getAddress().getCity().getPostalCode()` — each `.` highlighted as a new foreign dependency
  - Consequence: `Address` changes → `OrderService` breaks (red flash)
  - Fix: `order.getShippingPostalCode()` — one hop
  - Code comparison showing both
  - 🟢 Heuristic: count the dots. More than one = likely LoD violation
  - 🔵 Nuance: DTOs/records exempt. Fluent interface method chaining is intentional LoD exception.

  **Section 4.3 — Tell Don't Ask**
  - Metaphor: SVG delegating manager vs. micromanager reading every email
  - Code comparison: `if (obj.isEligible()) { applyDiscount(); }` (outside) vs. `obj.applyDiscount(rate)` (inside)
  - D3 coupling diagram: "asker" has arrows into internal state fields → any change leaks
  - 🔵 Nuance: TDA and CQS work at different levels — reconcile by noting TDA = behaviour delegation, CQS = return-type discipline

  **Section 4.4 — Command Query Separation (CQS)**
  - Metaphor: SVG vending machine — query button ("what's in B3?") vs. dispense button ("give me B3")
  - Code comparison: `List<Order> processAndReturnOrders()` (does both) vs. `processOrders()` + `getOrders()` (separated)
  - Animated: caller who only wants to read still triggers mutation side effect — show unexpected state change
  - 🔵 Nuance: atomic `pop()` from a queue = legitimate exception. CQRS is the architectural scale-up.

  **Section 4.5 — Principle of Least Astonishment (POLA)**
  - Metaphor: SVG light switch that turns on the fan instead of the light
  - Animated: `save()` method that also sends email — caller's surprise; tests producing unexpected emails
  - `toString()` override that modifies state — debugging nightmare animation
  - Code comparison: surprising vs. POLA-compliant design
  - 🟢 Question: "Would a teammate who hasn't read this code be surprised?"
  - 🔵 Nuance: POLA is context-sensitive — know your language/framework conventions

- [ ] **Step 2: Run quality checklist**

  Open `coding-concepts/04_oo_design_principles.html` in browser. Check every item. Fix failures.

- [ ] **Step 3: Commit**

  ```bash
  git add coding-concepts/04_oo_design_principles.html
  git commit -m "feat: add 04_oo_design_principles animated HTML"
  ```

---

## WAVE 1 REVIEW GATE

Before starting Wave 2, open at least `01_clean_code_fundamentals.html` and `02_solid_principles.html` in a browser and scroll through every section. Verify:
- Animations trigger on scroll (not on load)
- Interactive controls work
- Code comparison panels render correctly with syntax highlighting
- 🟢/🔵 panels expand and collapse
- Chapter pager shows correct prev/next links

If quality issues are found, fix them before proceeding to Wave 2.

---

## WAVE 2 — Tasks 5–7 (run in parallel), then Task 8

---

### Task 5: `05_code_smells.html`

**File:** Create `coding-concepts/05_code_smells.html`  
**Accent:** `#f472b6` (pink)  
**Sections:** God Class · Feature Envy · Primitive Obsession · Shotgun Surgery · Divergent Change · Deep Nesting · Magic Numbers  
**Full content spec:** See `coding_concepts_animation_prompt.md` sections 5.1–5.7

- [ ] **Step 1: Create the file**

  **Meta-animation:** "Code Doctor" character (simple SVG figure with stethoscope) runs a scan animation at page load — a scanning line sweeps across a code block SVG. Each section = finding a new symptom.

  **Section 5.1 — God Class / Long Method**
  - D3: class node grows with method after method until it fills its box — visual overload mirrors cognitive overload
  - Cyclomatic complexity bar turns red as the class grows
  - Prescription badge: "→ Extract Class, Extract Method (see File 06)"
  - Code comparison: bloated class with >20 methods vs. focused class

  **Section 5.2 — Feature Envy**
  - D3: method inside `OrderService` with dependency arrows accumulating toward `Customer` data fields — arrows turn red as envy grows
  - Counter: "Lines referencing Customer: 8 / Lines referencing own class: 2" — displayed as animated counters
  - Prescription badge: "→ Move Method (see File 06)"

  **Section 5.3 — Primitive Obsession**
  - Code comparison:
    ```
    ❌  createUser(String name, String email, String phone, String country, int age, boolean isAdmin)
    ✅  createUser(UserCreationRequest request)
        // where UserCreationRequest holds Email, PhoneNumber, Country value objects
    ```
  - Animated mistake: `createUser(email, name, phone, ...)` — parameters swapped, compiles fine, crashes at runtime. Show wrong-order call with red highlight.
  - Prescription badge: "→ Introduce Parameter Object (see File 06)"

  **Section 5.4 — Shotgun Surgery**
  - D3 file graph: "discount logic changes" event → 7 different file nodes flash red in sequence — editor jumping file to file animation
  - Contrast: LoD/SRP-compliant version where only 1 node flashes
  - Change radius counter: "7 files to edit" → "1 file to edit"
  - Prescription badge: "→ Move Method, Extract Class (see File 06)"

  **Section 5.5 — Divergent Change**
  - D3: single class node with 3 different colored arrows arriving from Marketing, Finance, IT — all hitting the same class
  - "Axis of change" labels on each arrow
  - Prescription badge: "→ SRP — split by axis of change (see File 02)"

  **Section 5.6 — Deep Nesting**
  - Animated pyramid: code indenting deeper `if → for → if → try → if → if` with a visual "pyramid of doom" building up
  - Cognitive complexity meter hits ceiling as nesting depth increases
  - Prescription badge: "→ Guard Clauses (see File 06)"
  - Code comparison:
    ```java
    ❌  if (user != null) {
          if (user.isActive()) {
            if (user.hasPermission()) {
              doWork();
            }
          }
        }

    ✅  if (user == null) return;
        if (!user.isActive()) return;
        if (!user.hasPermission()) return;
        doWork();
    ```

  **Section 5.7 — Magic Numbers & Strings**
  - Code comparison: `if (status == 3)` vs. `if (status == OrderStatus.SHIPPED)`
  - Animated "confusion meter" — new engineer staring at `3`, meter reads "What does 3 mean?" in red
  - 🔵 Nuance: not all literals are magic — `array.length - 1`, `0`, `1` in obvious math contexts are fine

- [ ] **Step 2: Run quality checklist**

  Open `coding-concepts/05_code_smells.html` in browser. Check every item. Fix failures.

- [ ] **Step 3: Commit**

  ```bash
  git add coding-concepts/05_code_smells.html
  git commit -m "feat: add 05_code_smells animated HTML"
  ```

---

### Task 6: `06_refactoring_patterns.html`

**File:** Create `coding-concepts/06_refactoring_patterns.html`  
**Accent:** `#fb923c` (orange)  
**Sections:** Extract Method · Guard Clauses · Replace Conditional with Polymorphism · Introduce Parameter Object · Move Method · Rename for Intent  
**Full content spec:** See `coding_concepts_animation_prompt.md` sections 6.1–6.6

- [ ] **Step 1: Create the file**

  **Meta-animation:** Each section's "before" code panel is visually degraded (CSS `opacity: 0.6`, slight `blur(0.5px)`, desaturated). Playing the transform animation sharpens it to full clarity.

  **Section 6.1 — Extract Method**
  - Animated: 60-line method displayed in a scrollable code block. Highlight a coherent 15-line block. Click "Extract" → highlighted lines lift out (`translateY(-20px)`, `opacity: 0`) and reappear as a new named sub-function card below. Parent method now reads like a table of contents.
  - Code comparison: before (long inline method) vs. after (method calling named sub-functions)

  **Section 6.2 — Guard Clauses**
  - Animated pyramid from Section 5.6 with a "Flatten" button. Each click applies one guard clause — condition flips, one indentation level collapses (`translateX(-16px)` + line rewrite animation). Final state: zero nesting, happy path straight down.
  - Code comparison (full):
    ```java
    ❌  if (user != null) {
          if (user.isActive()) {
            if (user.hasPermission()) {
              doWork();
            }
          }
        }

    ✅  if (user == null) return;
        if (!user.isActive()) return;
        if (!user.hasPermission()) return;
        doWork();
    ```

  **Section 6.3 — Replace Conditional with Polymorphism**
  - Code comparison: `switch (shape.type)` calculating area per type vs. polymorphic `area()` on each shape class
  - D3 diagram: switch block node → adding new shape = modifying existing class (red). Polymorphic version → adding new shape = new file only (green).
  - Interactive: "Add Triangle shape" button — in bad design, existing class node flashes red (modification). In good design, new `Triangle` node appears (extension).

  **Section 6.4 — Introduce Parameter Object**
  - Code comparison: 5-argument method vs. `SearchCriteria` / `CreateUserRequest` value object
  - Animated: 5 parameter cards collapse and merge into one `Request` card via anime.js
  - Show: IDE-style field autocomplete on the object (static mockup) — self-documenting

  **Section 6.5 — Move Method**
  - D3: Feature Envy diagram from Section 5.2. Click "Move Method" → method node physically animates across the class boundary to `Customer` class. Dependency arrows reverse or disappear.

  **Section 6.6 — Rename for Intent**
  - Code comparison: cryptic names → expressive names (same as Section 1.1 but framed as a refactoring action)
  - Animated: IDE rename refactoring cascade — one node renames, all reference nodes update atomically (staggered highlight)
  - 🔵 Key insight: renaming is the cheapest, highest-leverage refactoring. It's not cosmetic.

- [ ] **Step 2: Run quality checklist**

  Open `coding-concepts/06_refactoring_patterns.html` in browser. Check every item. Fix failures.

- [ ] **Step 3: Commit**

  ```bash
  git add coding-concepts/06_refactoring_patterns.html
  git commit -m "feat: add 06_refactoring_patterns animated HTML"
  ```

---

### Task 7: `07_defensive_and_advanced.html`

**File:** Create `coding-concepts/07_defensive_and_advanced.html`  
**Accent:** `#34d399` (emerald)  
**Sections:** Separation of Concerns · Defensive vs. Offensive Programming · DI/IoC · Design by Contract  
**Full content spec:** See `coding_concepts_animation_prompt.md` sections 7.1–7.4

- [ ] **Step 1: Create the file**

  **Section 7.1 — Separation of Concerns (SoC)**
  - Metaphor: SVG newspaper — sections for different concerns, readable independently
  - D3 diagram: monolithic function with mixed layers. Click "Separate" → layers physically sort into three tiers: Data Layer (blue), Business Logic (amber), Presentation (green)
  - Show: "UI changes" only touches Presentation tier. "DB changes" only touches Data tier. Cross-tier impact bars drop to zero.
  - 🔵 SoC is fractal: SRP (class), MVC (architecture), microservices (system)

  **Section 7.2 — Defensive vs. Offensive Programming**
  - Two SVG "schools": Castle (Defensive — moats, guards, drawbridges) vs. Trip Wire Alarm (Offensive — crash immediately)
  - Toggle between schools — diagram morphs between castle and alarm system
  - Appropriate-use table animated in:
    - Defensive: user-facing input, public API, payment flows, external services
    - Offensive: internal invariants, preconditions inside controlled modules, "should never happen" assertions
  - Hidden cost animation: over-defensive system swallowing error → garbage propagates downstream (long red path vs. short loud crash)
  - 🟢 Rule: defensive at border, offensive inside
  - 🔵 Offensive ≠ exception swallowing opposite. Pair with monitoring so crashes are caught.

  **Section 7.3 — Dependency Injection & IoC**
  - Three DI forms animated in sequence: constructor injection (preferred, green badge), setter injection (yellow), method injection (yellow)
  - D3 wiring diagram: DI container as central node. Objects declare dependencies as labeled arrows; container resolves and provides them.
  - Interactive: swap `SystemClock` → `FakeClock` in a test via a toggle — show test output going from `"non-deterministic"` to `"deterministic"`
  - Code comparison: objects instantiating dependencies (hard) vs. objects declaring dependencies (injected)
  - 🔵 Nuance: DI frameworks convenient but not required. Manual DI in composition root is often cleaner for smaller systems.

  **Section 7.4 — Design by Contract**
  - Metaphor: SVG legal contract with three clauses highlighted — Preconditions, Postconditions, Invariants
  - Animated contract violation: caller passes negative quantity → precondition check fires immediately (clean, loud error). Contrast: no contract → negative quantity flows 10 layers deep → `NullPointerException` in payment calc.
  - Call stack depth comparison: DbC = 1 frame. No DbC = 10 frames.
  - Class invariant demo: `BankAccount` with `balance >= 0` — invariant guard animates on every state mutation attempt
  - Code example (language-agnostic pseudocode):
    ```
    function transfer(amount):
      require amount > 0          // Precondition
      require balance >= amount   // Precondition
      balance = balance - amount
      ensure balance >= 0         // Postcondition
    ```
  - 🟢 Even without a DbC library, thinking in preconditions/postconditions improves API design
  - 🔵 Native DbC: Kotlin (`require`, `ensure`, `check`), Python (`assert`), Eiffel (original)

- [ ] **Step 2: Run quality checklist**

  Open `coding-concepts/07_defensive_and_advanced.html` in browser. Check every item. Fix failures.

- [ ] **Step 3: Commit**

  ```bash
  git add coding-concepts/07_defensive_and_advanced.html
  git commit -m "feat: add 07_defensive_and_advanced animated HTML"
  ```

---

### Task 8: `00_index.html` (after Tasks 5–7 complete)

**File:** Create `coding-concepts/00_index.html`  
**Style:** Galaxy/constellation map — identical visual language to `system-design-concepts/00_index.html`

- [ ] **Step 1: Study the existing index for patterns**

  Read `system-design-concepts/00_index.html` in full. Replicate:
  - Full-screen dark canvas (`overflow: hidden; height: 100vh`)
  - SVG or canvas star field background
  - Sidebar toggle button (hamburger) + slide-in sidebar panel
  - Constellation lines between related nodes
  - Node hover: accent glow + description tooltip
  - Node click: navigate to file

- [ ] **Step 2: Create the file**

  **7 constellation nodes** — one per concept file:

  | Node | Label | Accent | File |
  |------|-------|--------|------|
  | 01 | Clean Code Fundamentals | `#4ade80` | `01_clean_code_fundamentals.html` |
  | 02 | SOLID Principles | `#f59e0b` | `02_solid_principles.html` |
  | 03 | DRY · KISS · YAGNI | `#a78bfa` | `03_dry_kiss_yagni.html` |
  | 04 | OO Design Principles | `#38bdf8` | `04_oo_design_principles.html` |
  | 05 | Code Smells | `#f472b6` | `05_code_smells.html` |
  | 06 | Refactoring Patterns | `#fb923c` | `06_refactoring_patterns.html` |
  | 07 | Defensive & Advanced | `#34d399` | `07_defensive_and_advanced.html` |

  **Constellation lines** connecting related nodes (draw as SVG `<line>` elements with low opacity):
  - 01 ↔ 02 (clean code informs SOLID)
  - 02 ↔ 03 (SOLID informs DRY/KISS discipline)
  - 02 ↔ 04 (SOLID and OO design are siblings)
  - 04 ↔ 05 (OO violations = code smells)
  - 05 ↔ 06 (smells diagnosed → refactoring prescribed)
  - 02 ↔ 07 (DIP enables IoC/DI)
  - 01 ↔ 07 (clean code fundamentals underpin all advanced practices)

  **Sidebar panel:** Lists all 7 files with file number, accent-colored dot, title, and one-line description. Each entry is a clickable link.

  **Title:** "Coding Concepts" in large Inter 700 weight, top-center.

  **No link to system-design-concepts.**

- [ ] **Step 3: Run quality checklist for index**

  Open `coding-concepts/00_index.html` in browser. Verify:
  - All 7 nodes render with correct colors
  - Clicking each node navigates to the correct file
  - Constellation lines visible between related nodes
  - Sidebar opens/closes via hamburger
  - No console errors

- [ ] **Step 4: Commit**

  ```bash
  git add coding-concepts/00_index.html
  git commit -m "feat: add 00_index galaxy navigation map for coding-concepts"
  ```

---

## Final Verification

After all 8 tasks complete:

- [ ] Open `coding-concepts/00_index.html` and navigate to every file from it
- [ ] In each file, verify chapter pager prev/next links navigate correctly through the series
- [ ] Verify file 01 has no "prev" chip, file 07 has no "next" chip
- [ ] Verify all files link back to `00_index.html`
- [ ] Count: exactly 8 files exist in `coding-concepts/` (00–07)

```bash
ls coding-concepts/*.html | sort
# Expected:
# coding-concepts/00_index.html
# coding-concepts/01_clean_code_fundamentals.html
# coding-concepts/02_solid_principles.html
# coding-concepts/03_dry_kiss_yagni.html
# coding-concepts/04_oo_design_principles.html
# coding-concepts/05_code_smells.html
# coding-concepts/06_refactoring_patterns.html
# coding-concepts/07_defensive_and_advanced.html
```
