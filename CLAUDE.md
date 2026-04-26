# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a personal learning project: animated, self-contained HTML visualizations across four series (system design interview, DDIA book, coding principles, DSA patterns) plus AI/Java experiments.

## Structure

All content lives in two parent directories. All files are standalone HTML pages — no build step, no package manager, no server required.

### `coding-concepts/`

#### `coding-concepts/principles/`

Coding principles series.

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

The generation prompt is in `coding-concepts/principles/coding_concepts_animation_prompt.md`.

Each concept section must include: metaphor/analogy animation, ❌/✅ code comparison panel, violation→consequence→fix cascade, at least one interactive control, 🟢 "New to this?" expandable panel, 🔵 "Go deeper" expandable panel, and a complexity/coupling meter.

#### `coding-concepts/dsa/`

DSA pattern study guide targeting MANG Staff-level interviews (Java, 2026).

| File | Topic | Accent Color |
|------|-------|-------------|
| `00_curriculum.md` | Full pattern curriculum reference (21 patterns, problem bank) | — |
| `01_sliding_window.html` | Fixed + variable window | `#38bdf8` sky |
| `02_binary_search.html` | Classic + binary search on answer | `#a78bfa` violet |
| `03_recursion.html` | Subsequences, permutations, stack recursion | `#4ade80` green |
| `04_monotonic_stack.html` | Next greater, histogram, trapping rain water | `#f59e0b` amber |
| `05_dynamic_programming.html` | Knapsack, LCS, interval, LIS, grid DP | `#f472b6` pink |
| `06_heap_priority_queue.html` | Top-K, median stream, merge K lists | `#fb923c` orange |
| `07_backtracking.html` | Subsets, permutations, N-Queens, Sudoku | `#34d399` emerald |
| `08_mixed_must_do.html` | Two Sum, intervals, matrix classics | `#818cf8` indigo |
| `09_arrays_two_pointers_prefix_sums.html` | Prefix sums, difference arrays, two-pointer pairs | `#22d3ee` cyan |
| `10_hashing_intervals_sweep_line.html` | HashMap/Set patterns, sweep line | `#facc15` gold |
| `11_linked_lists.html` | Floyd's cycle, reversal, merge, k-th | `#f97316` orange |
| `12_trees_bst.html` | Traversal, LCA, serialize/deserialize, BST ops | `#4ade80` green |
| `13_trie.html` | Prefix tree, wildcard, XOR trie | `#a78bfa` violet |
| `14_graphs_bfs_dfs.html` | Islands, bipartite, cycle detection | `#38bdf8` sky |
| `15_graphs_toposort.html` | Kahn BFS + DFS topo, course schedule | `#f59e0b` amber |
| `16_graphs_shortest_path.html` | Dijkstra, Bellman-Ford, multi-source BFS | `#f472b6` pink |
| `17_graphs_mst.html` | Kruskal (DSU) + Prim | `#fb923c` orange |
| `18_union_find_dsu.html` | Dynamic connectivity, path compression, union by rank | `#34d399` emerald |
| `19_bit_manipulation.html` | XOR tricks, bitmask DP, counting bits | `#818cf8` indigo |
| `20_string_algorithms_parsing.html` | KMP, Rabin-Karp, expression eval | `#22d3ee` cyan |
| `21_designish_lru_lfu_stream.html` | LRU/LFU, sliding window median, reservoir sampling | `#facc15` gold |

Java practice pack at `coding-concepts/dsa/PatternPracticePack.java` — reusable templates + 3 full solutions per pattern 1–8, stubs for the rest. The generation prompt is in `coding-concepts/dsa/prompt.md`.

Each pattern page must include: hero header, ≥3 section cards (diagram + explanation), ≥2 interactive controls, algorithm stepper (play/pause/step), live trace panel showing invariants, and a copyable Java code panel.

### `system-design-concepts/`

#### `system-design-concepts/system-design-interview/`

System design interview prep series.

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

The generation prompt is in `system-design-concepts/system-design-interview/system_design_animation_prompt.md`.

#### `system-design-concepts/data-intensive-application/`

Animated series based on *Designing Data-Intensive Applications* by Martin Kleppmann. Footer cites Kleppmann instead of the default set.

| File | Topic | Accent Color |
|------|-------|-------------|
| `00_index.html` | Galaxy/constellation navigation index | Multi-color |
| `01_reliability_scalability.html` | Ch1: Reliability, Scalability, Maintainability | `#4ade80` green |
| `02_data_models.html` | Ch2: Relational, Document, Graph models | `#f59e0b` amber |
| `03_storage_retrieval_encoding.html` | Ch3+4: LSM-tree, B-tree, indexes, encoding | `#a78bfa` violet |
| `04_replication.html` | Ch5: Leader/follower, multi-leader, leaderless | `#38bdf8` sky blue |
| `05_partitioning.html` | Ch6: Range/hash partitioning, secondary indexes | `#f472b6` pink |
| `06_transactions.html` | Ch7: ACID, isolation levels, serializability | `#fb923c` orange |
| `07_distributed_problems_consensus.html` | Ch8+9: Faults, clocks, ordering, Raft, ZooKeeper | `#34d399` emerald |
| `08_batch_stream_future.html` | Ch10-12: MapReduce, Kafka, stream joins, ethics | `#818cf8` indigo |

### `ai/`

Experimental AI code not part of the visualization series.

- `ai/java-ai/` — Multi-agent Java system using Anthropic API (`OrchestratorAgent`, `CoderAgent`, `ReviewAgent`, `TestAgent`). Maven project (`pom.xml`). See `ai/java-ai/README.md`.
- `ai/ai-cost-optimization/` — LLM cost optimization reference (`llm_cost_optimization.html`, `optimization.md`).

## Technical Conventions

Every HTML file follows the same pattern:

- **Dependencies (CDN only)**: `anime.js` v3.2.1, `d3.js` v7, Google Fonts (`Inter` + `JetBrains Mono`)
- **CSS variables**: `--bg: #0d1117`, `--bg-card: #161b22`, `--border: #30363d`, `--text: #e6edf3`, plus a file-specific `--accent`
- **Animations trigger on scroll** via `IntersectionObserver` at 25% threshold — not on page load (index pages are exempt — they use load animations)
- **Interactive controls** (buttons/sliders/toggles) in every content file
- **Scroll progress bar**: CSS must use `width: 100%; transform: scaleX(0); transform-origin: left center`. JS updates via `el.style.transform = 'scaleX(' + ratio + ')'`. Never use `style.width` for this.
- **Fixed navbar** with chapter pager (prev/next), back link to `00_index.html`, and in-page section anchors
- **Footer** cites: `Clean Code — Robert C. Martin · The Pragmatic Programmer — Hunt & Thomas · Refactoring — Martin Fowler · Working Effectively with Legacy Code — Michael Feathers` (DDIA series uses Kleppmann instead)

### Animation rules (enforced — violations cause review failures)

- Animate **only** `transform` and `opacity`. Never `width`, `height`, `top`, `left`, or `margin`.
- The `scale:` shorthand is not valid in anime.js v3 — use `opacity` pulse or `translateY` instead.
- All metric bars and progress indicators must use `transform: scaleX(fraction)` with `transform-origin: left center`, never `transition: width`.
- Always add `if (total <= 0) return;` before any division in scroll handlers.
- CSS-only syntax highlighting via `<span>` classes: `.kw` `.fn` `.str` `.cm` `.num` `.tp` — no external lib.
- No `innerHTML` for dynamic content — use `textContent` and safe DOM methods to avoid XSS.

## Commands

### DSA Practice Pack (Java)

```bash
cd coding-concepts/dsa
javac PatternPracticePack.java
java -ea PatternPracticePack
```

### Multi-Agent Pipeline (Maven)

```bash
export ANTHROPIC_API_KEY=sk-ant-...
cd ai/java-ai
mvn compile exec:java
```

## Viewing

Open HTML files directly in a browser. No local server needed.

## Git Worktrees

When using git worktrees (e.g. for feature branches), always place them at the **global location**:

```
~/.config/worktrees/back-to-basics/<branch-name>/
```

Do **not** create worktrees inside the project directory. Project folders are content — tooling artifacts belong outside the repo.
