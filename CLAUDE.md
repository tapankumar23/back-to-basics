# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a personal learning project: two series of animated, self-contained HTML visualizations — one for core system design concepts and one for coding principles (SOLID, Clean Code, etc.).

## Structure

Each series lives in its own directory. All files are standalone HTML pages — no build step, no package manager, no server required.

### `system-design-concepts/`

| File | Topic | Accent Color |
|------|-------|-------------|
| `00_index.html` | Galaxy/constellation navigation map | Multi-color |
| `01_scaling_fundamentals.html` | Scaling, load balancers, caching, sharding | `#4ade80` green |
| `02_core_distributed_systems.html` | Consistent hashing, CAP, gossip, quorum | `#f59e0b` amber |
| `03_rate_limiter_and_id_gen.html` | Rate limiting algorithms, Snowflake IDs | `#a78bfa` violet |
| `04_storage_and_search.html` | URL shortener, web crawler, trie, Drive | `#38bdf8` sky blue |
| `05_feeds_and_messaging.html` | News feed fanout, notifications, chat | `#f472b6` pink |
| `06_video_and_object_storage.html` | YouTube pipeline, S3-like storage | `#fb923c` orange |
| `07_geo_and_realtime.html` | Geohash, quadtree, nearby friends, Maps | `#34d399` emerald |
| `08_data_pipelines.html` | Kafka internals, metrics, ad click aggregation | `#818cf8` indigo |
| `09_financial_systems.html` | Hotel reservation, payments, wallet, stock exchange | `#facc15` gold |
| `10_interview_foundations.html` | Interview frameworks, estimation, back-of-envelope | `#22d3ee` cyan |
| `11_email_and_leaderboard.html` | Distributed email service, real-time leaderboard | `#f97316` orange |

The generation prompt is in `system-design-concepts/system_design_animation_prompt.md`.

### `coding-concepts/`

| File | Topic | Accent Color |
|------|-------|-------------|
| `00_index.html` | Radial mind map navigation | Multi-color |
| `01_clean_code_fundamentals.html` | Naming, functions, comments, formatting | `#4ade80` green |
| `02_solid_principles.html` | SRP, OCP, LSP, ISP, DIP | `#f59e0b` amber |
| `03_dry_kiss_yagni.html` | DRY, KISS, YAGNI, Boy Scout Rule, Fail Fast | `#a78bfa` violet |
| `04_oo_design_principles.html` | Composition, Law of Demeter, Tell Don't Ask, CQS, POLA | `#38bdf8` sky blue |
| `05_code_smells.html` | God Class, Feature Envy, Primitive Obsession, Shotgun Surgery, Deep Nesting, Magic Numbers | `#f472b6` pink |
| `06_refactoring_patterns.html` | Extract Method, Guard Clauses, Replace Conditional with Polymorphism, Move Method, Rename for Intent | `#fb923c` orange |
| `07_defensive_and_advanced.html` | SoC, Defensive/Offensive Programming, DI/IoC, Design by Contract | `#34d399` emerald |

The generation prompt is in `coding-concepts/coding_concepts_animation_prompt.md`.

## Technical Conventions

Every HTML file follows the same pattern:

- **Dependencies (CDN only)**: `anime.js` v3.2.1, `d3.js` v7, Google Fonts (`Inter` + `JetBrains Mono`)
- **CSS variables**: `--bg: #0d1117`, `--bg-card: #161b22`, `--border: #30363d`, `--text: #e6edf3`, plus a file-specific `--accent`
- **Animations trigger on scroll** via `IntersectionObserver` at 25% threshold — not on page load (index pages are exempt — they use load animations)
- **Interactive controls** (buttons/sliders/toggles) in every content file
- **Scroll progress bar**: CSS must use `width: 100%; transform: scaleX(0); transform-origin: left center`. JS updates via `el.style.transform = 'scaleX(' + ratio + ')'`. Never use `style.width` for this.
- **Fixed navbar** with chapter pager (prev/next), back link to `00_index.html`, and in-page section anchors
- **Footer** cites: `Clean Code — Robert C. Martin · The Pragmatic Programmer — Hunt & Thomas · Refactoring — Martin Fowler · Working Effectively with Legacy Code — Michael Feathers`

### Animation rules (enforced — violations cause review failures)

- Animate **only** `transform` and `opacity`. Never `width`, `height`, `top`, `left`, or `margin`.
- The `scale:` shorthand is not valid in anime.js v3 — use `opacity` pulse or `translateY` instead.
- All metric bars and progress indicators must use `transform: scaleX(fraction)` with `transform-origin: left center`, never `transition: width`.
- Always add `if (total <= 0) return;` before any division in scroll handlers.
- CSS-only syntax highlighting via `<span>` classes: `.kw` `.fn` `.str` `.cm` `.num` `.tp` — no external lib.

### Per-section structure (coding-concepts files)

Each concept section must include: metaphor/analogy animation, ❌/✅ code comparison panel, violation→consequence→fix cascade, at least one interactive control, 🟢 "New to this?" expandable panel, 🔵 "Go deeper" expandable panel, and a complexity/coupling meter.

## Viewing

Open files directly in a browser. No local server needed.
