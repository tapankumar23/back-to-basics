# Coding Concepts Animated HTML Suite — Design Spec

**Date:** 2026-04-16  
**Status:** Approved  
**Source prompt:** `coding-concepts/coding_concepts_animation_prompt.md`

---

## Overview

Build a self-contained suite of 9 animated HTML files in `coding-concepts/` that visually explain foundational coding principles and clean code practices. Mirrors the `system-design-concepts/` suite in visual language, conventions, and technical stack — but is fully independent (separate index, no cross-linking).

Target audience: dual-layered — new joiners (analogy-first) and seasoned engineers (nuance, trade-offs, edge cases) simultaneously.

---

## Output Files

| File | Concepts | Accent Color | Wave |
|------|----------|-------------|------|
| `01_clean_code_fundamentals.html` | Naming, Functions, SLA, Comments, Formatting | `#4ade80` green | 1 |
| `02_solid_principles.html` | SRP, OCP, LSP, ISP, DIP | `#f59e0b` amber | 1 |
| `03_dry_kiss_yagni.html` | DRY, KISS, YAGNI, Boy Scout Rule, Fail Fast | `#a78bfa` violet | 1 |
| `04_oo_design_principles.html` | Composition over Inheritance, LoD, Tell Don't Ask, CQS, POLA | `#38bdf8` sky blue | 1 |
| `05_code_smells.html` | God Class, Feature Envy, Primitive Obsession, Shotgun Surgery, Divergent Change, Deep Nesting, Magic Numbers | `#f472b6` pink | 2 |
| `06_refactoring_patterns.html` | Extract Method, Guard Clauses, Replace Conditional with Polymorphism, Parameter Object, Move Method, Rename for Intent | `#fb923c` orange | 2 |
| `07_defensive_and_advanced.html` | SoC, Defensive/Offensive Programming, DI/IoC, Design by Contract | `#34d399` emerald | 2 |
| `00_index.html` | Galaxy/constellation navigation map | multi-color | 2 (last) |

---

## Technical Stack

Every file is a **single self-contained `.html` file**. No build step, no package manager.

### CDN Dependencies
```html
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600;700&family=JetBrains+Mono:wght@400;600&display=swap" rel="stylesheet" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/animejs/3.2.1/anime.min.js"></script>
<script src="https://d3js.org/d3.v7.min.js"></script>
```

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
  --accent-dim: /* 15-20% opacity accent */;
  --good: #4ade80;
  --bad: #f87171;
}
```

### Syntax Highlighting (CSS-only, no external lib)
```css
.kw  { color: #ff7b72; }   /* keywords */
.fn  { color: #d2a8ff; }   /* function names */
.str { color: #a5d6ff; }   /* strings */
.cm  { color: #8b949e; font-style: italic; } /* comments */
.num { color: #f2cc60; }   /* numbers */
.tp  { color: #ffa657; }   /* types */
```

---

## Per-File Structure

Every content file (`01`–`07`) must include all of the following.

### Navigation
- Fixed top navbar (`z-index: 999`, `backdrop-filter: blur`) with:
  - Chapter pager: `← prev file | current file name | next file →` linking within `coding-concepts/` only
  - Back link to `coding-concepts/00_index.html`
  - In-page section anchor links
- Scroll progress bar (thin, `--accent` colored) at very top of page (`z-index: 1000`)

### Per Section (every concept section must have)
1. **Metaphor/analogy animation** — SVG scene or D3 visualization illustrating the concept with real-world analogy
2. **❌/✅ code comparison panel** — side-by-side bad/good code blocks in `JetBrains Mono` with CSS syntax highlighting (`<span class="kw">` etc.)
3. **Violation → consequence → fix cascade** — animated nodes/modules showing breakage on violation, then recovery on fix
4. **At least one interactive control** — toggle, slider, or button (e.g. refactor button, complexity slider, drag-and-drop behavior)
5. **🟢 "New to this?" panel** — expandable, analogy-first explanation for new joiners
6. **🔵 "Go deeper" panel** — expandable, nuance/edge-case/trade-off guidance for experienced engineers
7. **Complexity/coupling meter** — animated gauge or bar chart quantifying cost of violation (e.g. cognitive complexity score, coupling score, change radius)

### Animation Requirements
- **Scroll-triggered**: All section animations start via `IntersectionObserver` at 25% threshold — never on page load
- **60fps safe**: Only animate `transform` and `opacity`. Never animate `width`, `height`, `top`, `left`, or `margin`
- **Code transformation**: "Refactor" button morphs bad → good code via `opacity` + `translateY` stagger using `anime.js`
- **Minimum 3 animation categories per file**: code transformation, metaphor/SVG, violation-consequence-fix cascade

### Footer
```
Based on Clean Code — Robert C. Martin · The Pragmatic Programmer — Hunt & Thomas · Refactoring — Martin Fowler · Working Effectively with Legacy Code — Michael Feathers
```

---

## 00_index.html

Galaxy/constellation map styled identically to `system-design-concepts/00_index.html`:
- Each of the 7 concept files = a star/node, colored by its accent color
- Click a node → navigate to that file
- Animated constellation lines connecting related concepts (e.g. SRP ↔ SoC ↔ Composition over Inheritance)
- Sidebar panel listing all files with descriptions
- No link to system-design-concepts (fully independent)

---

## Build Sequence (Two-Wave Approach)

### Wave 1 — Foundational files (4 parallel subagents)
Files `01`, `02`, `03`, `04` built simultaneously. These establish the visual vocabulary and core principles that Wave 2 files reference.

**Review gate:** validate at least one Wave 1 file in a browser before launching Wave 2.

### Wave 2 — Diagnostic/synthesis files + index (3 parallel + 1 sequential)
Files `05`, `06`, `07` built in parallel (they reference Wave 1 concepts as context for "prescriptions" and "fixes").  
`00_index.html` built last — after all 7 content files exist so it can accurately link to them.

---

## Quality Checklist (per file)

- [ ] Single `.html` file, CDN-only dependencies
- [ ] Every section has both 🟢 and 🔵 expandable panels
- [ ] Every principle has at least one ❌/✅ code comparison block
- [ ] Scroll-triggered animations (IntersectionObserver, 25% threshold)
- [ ] At least one interactive control per file
- [ ] Every principle shows what goes wrong on violation (not just the correct version)
- [ ] Animations use `transform` + `opacity` only
- [ ] Dark background (`#0d1117`) with file-specific accent color
- [ ] Chapter pager in navbar links correctly to adjacent coding-concepts files
- [ ] Footer cites source books

---

## Constraints

- No external syntax highlighter — CSS `<span>` classes only
- No framework, no build step — pure HTML/CSS/JS
- Desktop-first at 1280px+, must not break at 1024px
- No cross-linking to `system-design-concepts/`
- No moralizing language ("always", "never", "you must") — frame everything as trade-offs
