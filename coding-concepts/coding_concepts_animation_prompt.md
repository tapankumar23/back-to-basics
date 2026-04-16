# Prompt: Generate Animated HTML Visualizations for Coding Principles & Clean Code

---

## 🎯 OBJECTIVE

You are an expert frontend developer and software engineering educator. Your task is to create a **series of rich, animated, self-contained HTML files** that visually explain the foundational coding principles and clean code practices every software engineer must internalize. Each file must be fully standalone (no external dependencies except CDN-hosted libraries), visually engaging, and layered for **two audiences simultaneously**:

- 🟢 **New joiners**: Needs analogy-first explanations, before/after code comparisons, and clear "why this matters" callouts
- 🔵 **Seasoned engineers**: Needs nuance, edge cases, trade-offs, and "when to break the rule" guidance

Every section must serve both audiences without alienating either.

---

## 📚 CONCEPTS COVERED

### Group 1 — Clean Code Fundamentals
- Naming (variables, functions, classes)
- Functions (size, responsibility, arguments)
- Comments (when they help vs. when they hide problems)
- Formatting & Structure
- Single Level of Abstraction (SLA)

### Group 2 — SOLID Principles
- **S** — Single Responsibility Principle (SRP)
- **O** — Open/Closed Principle (OCP)
- **L** — Liskov Substitution Principle (LSP)
- **I** — Interface Segregation Principle (ISP)
- **D** — Dependency Inversion Principle (DIP)

### Group 3 — DRY, KISS, YAGNI & Everyday Discipline
- **DRY** — Don't Repeat Yourself
- **KISS** — Keep It Simple, Stupid
- **YAGNI** — You Aren't Gonna Need It
- **Boy Scout Rule** — Leave code cleaner than you found it
- **Fail Fast** — Surface errors at the earliest possible moment

### Group 4 — Object-Oriented Design Principles
- **Composition over Inheritance**
- **Law of Demeter** (Principle of Least Knowledge)
- **Tell Don't Ask**
- **Command Query Separation (CQS)**
- **Principle of Least Astonishment (POLA)**

### Group 5 — Code Smells & How to Fix Them
- Long Method / God Class
- Feature Envy
- Primitive Obsession
- Shotgun Surgery / Divergent Change
- Data Clumps
- Magic Numbers / Strings
- Deep Nesting

### Group 6 — Refactoring Patterns
- Extract Method / Extract Class
- Move Method / Move Field
- Replace Conditional with Polymorphism
- Introduce Parameter Object
- Guard Clauses (Replace Nested Conditionals)
- Rename for Intent

### Group 7 — Defensive vs Offensive Programming & Advanced Practices
- **Separation of Concerns (SoC)**
- **Defensive Programming** (validate, guard, never trust)
- **Offensive Programming** (fail fast, assert invariants, crash early)
- **Dependency Injection / Inversion of Control (IoC)**
- **Design by Contract** (preconditions, postconditions, invariants)

---

## 📁 OUTPUT FILES TO GENERATE

| File # | Filename | Concepts Covered | Accent Color |
|--------|----------|-----------------|-------------|
| 0 | `00_index.html` | Galaxy/constellation navigation map | Multi-color |
| 1 | `01_clean_code_fundamentals.html` | Naming, functions, comments, SLA, formatting | `#4ade80` green |
| 2 | `02_solid_principles.html` | SRP, OCP, LSP, ISP, DIP | `#f59e0b` amber |
| 3 | `03_dry_kiss_yagni.html` | DRY, KISS, YAGNI, Boy Scout Rule, Fail Fast | `#a78bfa` violet |
| 4 | `04_oo_design_principles.html` | Composition vs Inheritance, Law of Demeter, Tell Don't Ask, CQS, POLA | `#38bdf8` sky blue |
| 5 | `05_code_smells.html` | God Class, Feature Envy, Primitive Obsession, Shotgun Surgery, Deep Nesting | `#f472b6` pink |
| 6 | `06_refactoring_patterns.html` | Extract Method/Class, Guard Clauses, Replace Conditional with Polymorphism | `#fb923c` orange |
| 7 | `07_defensive_and_advanced.html` | SoC, Defensive/Offensive Programming, DI/IoC, Design by Contract | `#34d399` emerald |

---

## 🎨 VISUAL & ANIMATION REQUIREMENTS

### General Design Standards
- **Color scheme**: Deep dark background (`#0d1117`) with bright accent colors per file
- **Typography**: Google Fonts CDN — `Inter` for body text, `JetBrains Mono` for all code samples
- **Dual-audience badge**: Every section card has a 🟢 "New to this?" expandable panel and a 🔵 "Go deeper" expandable panel
- **Code comparison panels**: Side-by-side ❌ Bad / ✅ Good code blocks, syntax-highlighted via CSS, no external syntax highlighter needed
- **Responsiveness**: Desktop-first at 1280px+; must not break at 1024px
- **Navigation**: Fixed top navbar with clickable section links, smooth-scroll
- **Progress bar**: Thin animated progress indicator at the very top filling as you scroll

### Allowed CDN Libraries
- `anime.js` (v3) — JS-driven animations
- `d3.js` (v7) — diagrams, trees, graphs
- Google Fonts — `Inter` + `JetBrains Mono`

### Animation Categories — use at least 3 per file

**1. Code Transformation Animations**
- Bad code block is shown → a "Refactor" button triggers a morphing animation where lines dissolve and rewrite into the good version
- Use `opacity`, `translateY`, and staggered line reveals
- Never animate font-size or width (layout-triggering)

**2. Metaphor/Analogy Animations**
- Each principle gets a real-world metaphor animated as an SVG scene
- Examples: SOLID = a Swiss Army knife vs. specialized tools; Law of Demeter = asking a stranger for directions vs. asking a friend; DRY = a photocopier vs. a master document
- These land for new joiners immediately, and experienced engineers can skip past them

**3. Violation → Consequence → Fix Cascade**
- A "system" (animated nodes/modules) runs happily → a violation is introduced (red highlight, shake) → the system breaks in a visible way → the fix is applied → system recovers
- This is the core teaching loop for every principle

**4. Interactive Sliders / Toggles**
- Toggle between "violating" and "following" the principle — the diagram morphs in real time
- Example: SRP toggle splits one God Class node into smaller focused classes
- Example: DRY slider shows how many places need updating when logic is duplicated (1 change = N edits)

**5. Complexity / Coupling Meters**
- Animated gauges or bar charts showing: Cognitive Complexity, Coupling Score, Changeability
- These numbers go up when violations are introduced and down when principles are applied
- Make the cost of bad code quantifiable visually, not just philosophical

---

## 📋 PER-FILE DETAILED SPECIFICATIONS

---

### FILE 1: `01_clean_code_fundamentals.html`
**Accent**: `#4ade80` (green — clarity and growth)

#### Section 1.1 — Naming
**Metaphor animation**: A blurry photograph sharpens into focus as variable names go from `d`, `temp`, `x2` to `daysSinceLastLogin`, `userEmail`, `discountedPrice`.
- Animate a "name decoder ring" — show how a reader must mentally context-switch to decode bad names vs. how good names are self-documenting
- Interactive: type a concept in a box, get a slider between cryptic ↔ expressive naming, see cognitive load meter drop
- 🟢 Analogy: bad naming is like labeling every box in a moving house "stuff"
- 🔵 Nuance: when abbreviations ARE acceptable (well-established domain terms: `url`, `id`, `dto`); Hungarian notation is dead — explain why

**Code comparison panel**:
```
❌  int d; // days since last login
✅  int daysSinceLastLogin;

❌  List<int[]> getThem() { ... }
✅  List<Cell> getFlaggedCells() { ... }
```

#### Section 1.2 — Functions
**Metaphor animation**: A function as a factory machine — animate inputs going in, exactly one transformation happening, output coming out. Then show a "do-everything machine" that sparks and jams.
- Animate the ideal function size: a single scrollable card that fits on screen with no scroll
- Show argument count cost: 0 args (ideal) → 1 → 2 → 3+ (meter turns red)
- Interactive "argument reducer": take a 4-arg function, click "refactor" → it collapses into a Parameter Object
- 🟢 Rule of thumb: if you can't describe what a function does without using "and", it does too much
- 🔵 Nuance: pure functions vs. side effects — when side effects are unavoidable, isolate them at the edges

#### Section 1.3 — Single Level of Abstraction (SLA)
**This is the hardest concept to teach — give it full visual treatment.**
- Animate a function as a layered cake: each layer is an abstraction level
- Show a "mixed levels" function where HTML rendering, business logic, and DB queries sit in the same block — highlight the layer jumps in red
- Click "fix" → watch the function decompose into well-named sub-functions each at a single level
- 🟢 Analogy: reading a newspaper — headline → sub-headline → paragraph body — you don't mix font sizes mid-paragraph
- 🔵 Nuance: Step-down rule — reading code should feel like reading a TO paragraph, each function calling the next level down

#### Section 1.4 — Comments
**Controversial section — teach the nuance, not the rule.**
- Animate a "comment graveyard": stale comments that lie about what the code does — highlight them tombstone-red
- Side-by-side: comment explaining *what* (bad) vs. comment explaining *why* (good)
- Interactive: show a complex regex — poll: "would you comment this?" — reveal the answer and reasoning
- 🟢 Types of comments that are always OK: legal notices, intent explanations, clarification of external API quirks, TODOs (with tickets)
- 🔵 Types of comments that signal a design smell: anything that explains what the code does (the code should do that), commented-out code blocks

#### Section 1.5 — Formatting & Structure
- Animate a codebase as a city street: well-formatted code = clear street signs, consistent block sizes, no obstructions
- Show the "newspaper metaphor": most important things at the top, details below
- Demonstrate vertical distance: related things are close, unrelated things are far
- 🔵 Key insight: formatting rules don't matter as long as they're consistent — the cost is debating them, not the choice itself. Use autoformatters and move on.

---

### FILE 2: `02_solid_principles.html`
**Accent**: `#f59e0b` (amber — structural strength)

The SOLID acronym itself should animate in letter by letter, each lighting up as its section loads.

#### Section 2.1 — Single Responsibility Principle (SRP)
**Metaphor**: Swiss Army knife vs. a chef's knife
- Animate a `UserManager` god class: it handles auth, sends emails, generates reports, formats dates — each responsibility shown as a glowing node attached to one class
- Click "Apply SRP": watch the class explode into `AuthService`, `EmailService`, `ReportGenerator` — each a clean, single node
- Show change impact: if "email format changes", in the bad design ALL of UserManager is touched (full red highlight), in the good design only `EmailService` lights up
- Interactive: drag responsibilities onto a class node — watch it get bloated. Remove them — watch it become focused.
- 🟢 One reason to change: if you can name more than one stakeholder (Marketing *and* Finance *and* IT) who could ask you to change this class, it has too many responsibilities
- 🔵 Nuance: SRP operates at multiple levels — method, class, module, service. It's fractal.

#### Section 2.2 — Open/Closed Principle (OCP)
**Metaphor**: A USB port vs. soldering a device directly onto a motherboard
- Animate a `PaymentProcessor` that has `if type == "stripe"` / `else if type == "paypal"` — show adding a new payment type requires opening and modifying the class (red warning: "modification = risk")
- Apply OCP: extract a `PaymentProvider` interface, show `StripeProvider` and `PayPalProvider` implementing it. Adding `ApplePayProvider` = adding a new file only (green: "extension = safe")
- Show before/after test coverage impact: in the bad version, every new payment type risks breaking old tests; in the good version, old tests never need touching
- 🔵 Nuance: OCP doesn't mean "never modify" — it means design for extension so modifications are rare. Premature abstraction that tries to be OCP-ready before knowing the variation axis is a YAGNI violation.

#### Section 2.3 — Liskov Substitution Principle (LSP)
**This is the most misunderstood SOLID principle — use a strong visual.**
**Metaphor**: A contract — if you sign it, you must honour every clause
- Classic penguin example: animate `Bird` with `fly()`. `Penguin extends Bird` overrides `fly()` to throw an exception — animate the crash
- Show the correct fix: `FlyingBird` and `NonFlyingBird` hierarchy
- Show a less obvious LSP violation: a `Square extends Rectangle` where setting width also sets height — animate how code that works on `Rectangle` silently breaks when handed a `Square`
- Interactive: drop a subclass into a function that expects the parent — watch it either pass (green) or violate (red flash + broken output)
- 🟢 Litmus test: if you need an `instanceof` check, you're probably violating LSP
- 🔵 Nuance: LSP is about behavioural subtyping, not just type hierarchy. Preconditions can't be strengthened; postconditions can't be weakened; invariants must be preserved.

#### Section 2.4 — Interface Segregation Principle (ISP)
**Metaphor**: A restaurant menu with everything on it vs. a focused tasting menu
- Animate a fat `IWorker` interface: `work()`, `eat()`, `sleep()`, `attendMeeting()`, `writeCode()`
- A `Robot` class is forced to implement `eat()` and `sleep()` — animate the robot trying to eat, then crashing
- Split into `IWorkable`, `IFeedable`, `ISleepable` — Robot only implements `IWorkable`, human implements all three
- Show the concrete benefit: changing the `IFeedable` contract doesn't force Robot to be recompiled/retested
- 🔵 Nuance: ISP and SRP are related — if your interface is fat, your implementation class likely violates SRP too. Fix SRP first.

#### Section 2.5 — Dependency Inversion Principle (DIP)
**Metaphor**: A power outlet standard — your laptop doesn't depend on a specific power plant
- Animate `OrderService` directly instantiating `MySQLRepository` — draw a hard dependency arrow pointing down to infrastructure
- Show the problem: to test `OrderService`, you must spin up MySQL (animate the heavy dependency chain loading)
- Apply DIP: `OrderService` depends on `IOrderRepository` interface — both the service and `MySQLRepository` point to the abstraction
- Show test becoming trivial: `MockOrderRepository` snaps in without touching `OrderService`
- Interactive: swap `MySQLRepository` for `PostgreSQLRepository` for `InMemoryRepository` — watch the system adapt with zero changes to business logic
- 🔵 Nuance: DIP is NOT the same as Dependency Injection (DI is a mechanism; DIP is the principle). DI frameworks like Spring enforce DIP but DIP can be applied manually.

---

### FILE 3: `03_dry_kiss_yagni.html`
**Accent**: `#a78bfa` (violet — discipline and restraint)

#### Section 3.1 — DRY (Don't Repeat Yourself)
**Metaphor**: A master document vs. a photocopier — every update to the original propagates, but photocopies must each be updated manually
- Animate a codebase with 5 copies of validation logic — show one business rule change requiring 5 edits, and 3 developers missing 2 of them (highlight the bugs that remain)
- Interactive: DRY-ness slider — move it from "fully duplicated" to "fully extracted" — watch a single-source-of-truth emerge and the change radius shrink to 1
- Show DRY applying beyond code: DB schema, tests, documentation, configuration
- 🔵 Nuance: **The Rule of Three** — don't abstract on the second occurrence; wait for the third. The wrong abstraction is worse than the duplication (Sandi Metz). Also: DRY is about knowledge, not syntax — two identical-looking code blocks that represent different concepts should NOT be merged.

#### Section 3.2 — KISS (Keep It Simple, Stupid)
**Metaphor**: A Rube Goldberg machine vs. pressing a button
- Animate a complex chain of abstractions solving a problem that one function could solve directly — show cognitive overhead as a growing number
- Side-by-side: a regex with 10 lookaheads vs. a 3-line readable parser
- Interactive "complexity meter": write a solution, watch it score on cyclomatic complexity — simplify, watch the score drop
- 🟢 Question to ask: "Would a new team member understand this in 30 seconds?"
- 🔵 Nuance: Simple ≠ easy to write. The simplest solution often requires the most thought. Cleverness is the enemy of simplicity. Quote: "Any fool can write code that a computer can understand. Good programmers write code that humans can understand." — Martin Fowler

#### Section 3.3 — YAGNI (You Aren't Gonna Need It)
**Metaphor**: Packing for a trip — the "I might need this" suitcase vs. packing exactly what's on the itinerary
- Animate a codebase growing with unused abstractions, flags, plugin systems, and config options "for future flexibility"
- Show the hidden cost: dead code paths that get tested, documented, and maintained forever
- Animate the speculative feature vs. the actual future requirement — show how often they don't match (the future requirement is always different from what was anticipated)
- 🔵 Nuance: YAGNI and OCP tension — OCP says "design for extension"; YAGNI says "don't extend prematurely". Resolution: extend when you have the second use case, not in anticipation of it. The first user defines the interface; the second user validates it.

#### Section 3.4 — Boy Scout Rule
**Metaphor**: A hiking trail maintained by everyone vs. a trail maintained by no one (tragedy of the commons)
- Animate a codebase metric dashboard (complexity, coverage, linting warnings) trending upward over time as bad code accumulates
- Show the Boy Scout approach: each PR leaves the area around the touched code slightly cleaner — watch the metrics trend downward over time
- Show what "leave it cleaner" means concretely: rename a confusing variable, extract a readable sub-function, delete a dead code branch
- 🔵 Nuance: The key constraint — "in the area you're already touching". Boy Scout Rule is not a license for large refactors inside a feature PR. Scope it.

#### Section 3.5 — Fail Fast
**Metaphor**: A car's dashboard warning light vs. waiting until the engine seizes
- Animate a request flow where bad input is accepted and travels deep into the system before crashing in an unrelated component — show how hard the error is to trace
- Then show fail-fast: validation at the boundary, immediate rejection with a clear error, clean call stack
- Show the difference in debugging time: Fail-slow = 2 hours of root-cause analysis. Fail-fast = immediate stack trace pointing at the source.
- Animate assertions as runtime sentinels: they catch "impossible" states when they first become possible
- 🟢 Golden rule: validate at the border of your system; assert invariants everywhere else
- 🔵 Nuance: Fail-fast is offensive programming. Know when to be defensive instead (user-facing input, external APIs, payment flows).

---

### FILE 4: `04_oo_design_principles.html`
**Accent**: `#38bdf8` (sky blue — structure and relationships)

#### Section 4.1 — Composition over Inheritance
**Metaphor**: LEGO bricks vs. a pre-built toy that can't be modified
- Animate a deep inheritance hierarchy: `Animal → Mammal → Pet → Dog → GuideDog` — show a feature request ("dogs can swim") requiring changes at the wrong level, or an impossible hierarchy for a RoboticGuideDog
- Show composition: `Dog` has a `SwimmingBehaviour`, `GuideBehaviour`, `FetchBehaviour` — each behavior is a composable piece. RoboticGuideDog gets the same `GuideBehaviour` with zero hierarchy changes
- Interactive: drag-and-drop behavior modules onto an entity — watch it gain capabilities without touching a class hierarchy
- 🔵 Nuance: "Favour composition" is not "never use inheritance". Inheritance is appropriate for true IS-A relationships with stable, shared contracts. Rule of thumb: use inheritance for type identity, composition for behaviour sharing.

#### Section 4.2 — Law of Demeter (LoD)
**Metaphor**: "Don't talk to strangers." If you want a friend's phone number, ask your friend — don't reach into their pocket.
- Animate a chain call: `order.getCustomer().getAddress().getCity().getPostalCode()` — highlight each `.` as a dependency on a foreign object's internals
- Show the consequence: if `Address` changes its structure, `OrderService` breaks even though it has nothing to do with addresses
- Apply LoD: `order.getShippingPostalCode()` — one hop, one dependency, the path is encapsulated inside `Order`
- 🟢 Heuristic: count the dots. More than one dot on a single chain is usually a LoD violation.
- 🔵 Nuance: Data structures (DTOs, records) are exempt from LoD — the law applies to objects with behaviour. `point.x` is fine. Also: method chaining in fluent interfaces (builders, streams) is an intentional LoD exception.

#### Section 4.3 — Tell Don't Ask
**Metaphor**: A manager who delegates vs. a micromanager who reads every email themselves
- Animate an object querying state from another object and making a decision on its behalf — show this as an `if obj.isEligible()` block living outside the object
- Contrast with telling: `obj.applyDiscount(rate)` — the object knows its own state and acts on it
- Show the coupling consequence: the "asker" must know the internal state structure — any change to that state leaks through
- 🔵 Nuance: TDA and CQS (next section) can appear to conflict — reconcile by noting TDA is about behaviour delegation, CQS is about return-type discipline. They work at different levels.

#### Section 4.4 — Command Query Separation (CQS)
**Metaphor**: A vending machine button (query: "what's in slot B3?") vs. the dispense button (command: "give me B3")
- Animate a method `List<Order> processAndReturnOrders()` — show it doing two things: mutating state AND returning data. A caller who only wants to read still triggers a side effect.
- Split into `processOrders()` (command, void return) and `getOrders()` (query, returns data, no side effect)
- Show the testing benefit: queries are trivially testable (pure), commands can be tested for side effects in isolation
- 🔵 Nuance: Some languages/frameworks make CQS impractical (atomic `pop()` from a queue is intentionally both). Flag these as legitimate exceptions, not rule breaks. CQRS (architectural pattern) is the scaled-up version of this principle.

#### Section 4.5 — Principle of Least Astonishment (POLA)
**Metaphor**: A light switch that turns on the fan instead of the light. Technically works. Completely wrong.
- Animate a `save()` method that also sends an email — show a caller's surprise when their test starts producing emails
- Show a `toString()` override that modifies state — animate the debugging nightmare
- Contrast with POLA-compliant design: behavior matches the name, matches the type signature, matches the convention
- 🟢 Question to ask: "Would a teammate who hasn't read this code be surprised by what it does?"
- 🔵 Nuance: POLA is context-sensitive. `+=` for string concatenation is astonishing in most languages, normal in Python. Know your context's conventions.

---

### FILE 5: `05_code_smells.html`
**Accent**: `#f472b6` (pink — detection and diagnosis)

**Meta-animation for this file**: A "Code Doctor" character runs a scan on a codebase. Each smell is a symptom. The scan finds it, highlights it, diagnoses it, and prescribes a refactoring from File 6.

#### Section 5.1 — God Class / Long Method
- Animate a class node growing with method after method until it fills the screen — visual overload mirrors cognitive overload
- Show the symptoms: >500 lines, >20 methods, imports from every layer of the system
- Metric: Cyclomatic complexity bar turns red
- Prescription: Extract Class, Extract Method

#### Section 5.2 — Feature Envy
- Animate a method inside `OrderService` that constantly reaches into `Customer` data — draw the dependency arrows accumulating until the method "envies" another class's data more than its own
- Show the smell: more lines reference another object's fields than the host class's own fields
- Prescription: Move Method

#### Section 5.3 — Primitive Obsession
- Animate a function signature: `createUser(String name, String email, String phone, String country, int age, boolean isAdmin)` — show how each String is a landmine waiting to be passed in the wrong order
- Show the mistake it enables: `createUser(email, name, phone, country, age, isAdmin)` — compiles, crashes at runtime
- Prescription: Introduce Value Objects — `Email`, `PhoneNumber`, `Country` each carry their own validation

#### Section 5.4 — Shotgun Surgery
- Animate a single business rule change (e.g., "discount logic changes") that requires edits across 7 different files — show the editor jumping file to file
- Contrast with the LoD/SRP-compliant version where the change is local to one class
- Prescription: Move Method, Extract Class, consolidate dispersed logic

#### Section 5.5 — Divergent Change
- The mirror of Shotgun Surgery: one class that changes for many different reasons — Marketing asks for change, Finance asks for change, IT asks for change — all hit the same class
- Prescription: SRP — split the class by axis of change

#### Section 5.6 — Deep Nesting
- Animate code indenting deeper and deeper: `if → for → if → try → if → if` — show the pyramid of doom
- Cognitive complexity meter hits the ceiling
- Prescription: Guard Clauses (File 6) — flip conditionals and return early, flatten the pyramid visually

#### Section 5.7 — Magic Numbers & Strings
- Animate `if (status == 3)` — show a new engineer staring at it, unable to understand what 3 means without looking elsewhere
- Show a refactored enum or named constant: `if (status == OrderStatus.SHIPPED)` — self-documenting
- 🔵 Nuance: Not all literals are magic — `array.length - 1`, `0`, `1` in obvious mathematical contexts are fine.

---

### FILE 6: `06_refactoring_patterns.html`
**Accent**: `#fb923c` (orange — transformation)

**Meta-animation**: Each section shows a "before" code panel that is visually degraded (slightly blurry, low contrast), then plays a transformation animation into the "after" panel (sharp, bright, clean).

#### Section 6.1 — Extract Method
- Animate a 60-line method — highlight a coherent block of 15 lines
- Watch those lines lift out of the method and collapse into a named sub-function called in their place
- Show how the parent method now reads like a table of contents

#### Section 6.2 — Guard Clauses (Replace Nested Conditionals)
- Animate the deep nesting pyramid from Section 5.6
- Apply guard clauses one by one: each early-return flips a condition and removes one level of indentation — watch the pyramid flatten in real time
- Final result: zero nesting, happy path flows straight down

```
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

#### Section 6.3 — Replace Conditional with Polymorphism
- Animate a `switch (shape.type)` block that calculates area differently per type — show how adding a new shape requires modifying this class (OCP violation)
- Extract each case into a polymorphic `area()` method on each shape subclass
- New shapes = new files only

#### Section 6.4 — Introduce Parameter Object
- Animate a method with 5+ parameters — show the mental overhead of remembering order
- Wrap them into a `SearchCriteria` or `CreateUserRequest` value object
- Show IDE support: the object's fields are self-documenting; validation moves inside the object

#### Section 6.5 — Move Method / Move Field
- Animate Feature Envy from Section 5.2 being resolved: the method physically "moves" across the class boundary in the animation
- The original class now just delegates, or removes the dependency entirely

#### Section 6.6 — Rename for Intent
- The simplest and most impactful refactoring — animate the Naming section from File 1 applied as a mechanical, safe change
- Show IDE rename refactoring as a cascade — every reference updates atomically
- 🔵 Key insight: renaming is not cosmetic. It's the cheapest and highest-leverage refactoring there is.

---

### FILE 7: `07_defensive_and_advanced.html`
**Accent**: `#34d399` (emerald — safety and architecture)

#### Section 7.1 — Separation of Concerns (SoC)
**Metaphor**: A newspaper — different sections for different concerns, you can read Sports without reading Business
- Animate a monolithic function that fetches data, transforms it, formats it as HTML, and logs everything
- Separate it into: Data Layer, Business Logic Layer, Presentation Layer — each animated as a physical tier
- Show that a UI change no longer touches the DB layer, and a DB change no longer touches the view
- 🔵 SoC is the parent principle of SRP (class level), MVC (architecture level), and microservices (system level). It's fractal.

#### Section 7.2 — Defensive vs. Offensive Programming
**Frame this as a worldview debate, not a binary choice.**
- Animate two "schools": Defensive (castle with moats, guards, drawbridges — validate everything, never crash) vs. Offensive (trip wire alarms, crash immediately when something is wrong — surface bugs immediately)
- Show where each is appropriate:
  - **Defensive**: user-facing input validation, public API boundaries, payment processing, data from external services
  - **Offensive**: internal invariants, function preconditions inside a module you control, assertions in business logic that "should never happen"
- Animate the hidden cost of over-defensive code: a system that silently swallows errors and returns garbage downstream is harder to debug than one that crashes immediately
- 🟢 Rule of thumb: be defensive at the border of your system, offensive inside it
- 🔵 Nuance: Offensive programming is not exception swallowing's opposite — it's about asserting, not ignoring. Pair with monitoring and alerting so crashes are caught.

#### Section 7.3 — Dependency Injection & Inversion of Control (IoC)
**Extend the DIP section from File 2 with the mechanism.**
- Animate three forms of DI: constructor injection (preferred), setter injection, method injection
- Show a DI container as a "wiring diagram" — objects declare their dependencies; the container resolves and provides them
- Show testability benefit: inject a `FakeClock` instead of `SystemClock` in tests — time-dependent code becomes deterministic
- Show the before (objects creating their dependencies — hard dependencies) vs. after (objects declaring their dependencies — injected)
- 🔵 Nuance: DI frameworks (Spring, Guice, .NET DI) are convenient but not required. Manual DI in the main composition root is often cleaner for smaller systems. Over-reliance on DI frameworks can obscure the actual dependency graph.

#### Section 7.4 — Design by Contract (DbC)
**This is the formal underpinning of LSP and Fail Fast.**
- Animate a function as a legal contract: Preconditions (what caller must guarantee), Postconditions (what the function guarantees in return), Invariants (what is always true about this object's state)
- Show a contract violation: caller passes a negative quantity — animate the precondition check firing immediately with a clear, meaningful error
- Contrast with no contract: the negative quantity flows through, causes a `NullPointerException` in a payment calculation 10 layers later
- Show class invariants: a `BankAccount` where `balance >= 0` is always true — animate the invariant check guarding every state mutation
- 🟢 Even if you don't use a DbC library: the discipline of thinking in preconditions/postconditions improves API design enormously
- 🔵 Languages with native DbC: Eiffel (original), Kotlin (`require`, `ensure`, `check`), Python (`assert`). Show a code example in a language-agnostic pseudocode style.

---

## 🧩 TECHNICAL IMPLEMENTATION NOTES

### HTML File Structure Template
```html
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>[Concept Group Name] — Coding Principles</title>
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
      --accent: /* file-specific */;
      --accent-dim: /* 20% opacity accent */;
      --good: #4ade80;   /* ✅ good code colour */
      --bad: #f87171;    /* ❌ bad code colour */
      --neutral: #8b949e;
    }
  </style>
</head>
<body>
  <div id="progress-bar"></div>
  <nav id="navbar"><!-- section links --></nav>
  <main>
    <section id="section-N" class="concept-section">
      <div class="section-header">
        <h2>Section Title</h2>
        <div class="audience-badges">
          <button class="badge badge-new">🟢 New to this?</button>
          <button class="badge badge-senior">🔵 Go deeper</button>
        </div>
        <p class="section-desc">2–3 line concept summary</p>
      </div>

      <div class="metaphor-area"><!-- SVG analogy animation --></div>

      <div class="code-comparison">
        <div class="code-panel bad">
          <div class="panel-label">❌ Violates the principle</div>
          <pre><code><!-- bad code --></code></pre>
        </div>
        <div class="refactor-arrow">→</div>
        <div class="code-panel good">
          <div class="panel-label">✅ Follows the principle</div>
          <pre><code><!-- good code --></code></pre>
        </div>
      </div>

      <div class="diagram-area">
        <svg id="diagram-N"><!-- consequence animation --></svg>
      </div>

      <div class="controls"><!-- sliders, toggles, buttons --></div>

      <div class="insight-panel new-joiner" hidden><!-- 🟢 expandable --></div>
      <div class="insight-panel senior" hidden><!-- 🔵 expandable --></div>
    </section>
  </main>
  <footer>
    <p>Based on <em>Clean Code</em> by Robert C. Martin · <em>The Pragmatic Programmer</em> · <em>Refactoring</em> by Martin Fowler</p>
  </footer>
  <script>
    // IntersectionObserver for scroll-triggered animations (25% threshold)
    // Interactive control handlers
    // Code transformation animations
  </script>
</body>
</html>
```

### Code Block Syntax Highlighting (no external library)
Use CSS classes on `<span>` elements inside `<pre><code>`:
```css
.kw  { color: #ff7b72; }   /* keywords */
.fn  { color: #d2a8ff; }   /* function names */
.str { color: #a5d6ff; }   /* strings */
.cm  { color: #8b949e; font-style: italic; } /* comments */
.num { color: #f2cc60; }   /* numbers */
.tp  { color: #ffa657; }   /* types */
```

### Animation Trigger Pattern
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

### Code Transformation Animation Pattern
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

---

## ✅ QUALITY CHECKLIST

Each HTML file must satisfy ALL of the following:

- [ ] **Self-contained**: Single `.html` file, CDN-only dependencies
- [ ] **Dual-audience**: Every section has both a 🟢 analogy-first explanation and a 🔵 nuance/edge-case panel
- [ ] **Code comparisons**: Every principle has at least one ❌/✅ before/after code block in `JetBrains Mono`
- [ ] **Scroll-triggered**: Animations start when sections enter viewport, not on load
- [ ] **Interactive**: At minimum one interactive control (button, slider, toggle) per file
- [ ] **Consequence-visible**: Every principle shows *what goes wrong* when violated, not just *what's correct*
- [ ] **60fps safe**: All animations use `transform` and `opacity` only — no `width`, `height`, `top`, `left`, `margin`
- [ ] **No layout thrash**: All DOM reads before all DOM writes in animation loops
- [ ] **Dark mode only**: Consistent `#0d1117` base with file-specific accent
- [ ] **No moralizing**: Avoid language like "always", "never", "you must". Frame as trade-offs.
- [ ] **Sources credited**: Footer cites source books/authors for intellectual honesty

---

## 🚀 GENERATION ORDER

Generate in this order (each file references concepts from prior files):

1. `01_clean_code_fundamentals.html` — foundational vocabulary; all other files use naming and function principles
2. `02_solid_principles.html` — the most referenced set; later files assume familiarity
3. `03_dry_kiss_yagni.html` — behavioural discipline built on top of SOLID understanding
4. `04_oo_design_principles.html` — advanced structural thinking, requires SOLID as context
5. `05_code_smells.html` — recognition layer; references all prior principles as the fixes
6. `06_refactoring_patterns.html` — the action layer; every refactoring fixes a smell from File 5
7. `07_defensive_and_advanced.html` — architectural thinking; synthesises everything above
8. `00_index.html` — generate last; it links all complete files

---

## 💡 ADVANCED FEATURES (implement if scope allows)

- **Audience toggle**: A global toggle in the navbar between "New Joiner Mode" (shows analogies, hides nuance panels by default) and "Seasoned Mode" (shows nuance panels by default, collapses analogies)
- **Principle relationship map**: An SVG graph showing how principles relate — e.g., DIP enables OCP, SRP enables SoC, LoD enables Tell Don't Ask — clickable to navigate
- **"Smell Detector" game**: Show a code snippet, ask the user to identify the smell, reveal the answer with an animation
- **Playback speed control**: A global 0.5× / 1× / 2× animation speed slider that scales all `anime.js` durations

---

*Based on: "Clean Code" — Robert C. Martin · "The Pragmatic Programmer" — Hunt & Thomas · "Refactoring" — Martin Fowler · "Working Effectively with Legacy Code" — Michael Feathers*
