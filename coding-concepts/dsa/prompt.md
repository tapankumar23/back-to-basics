# Prompt: Staff-Level DSA + Animated Visual Study Guide — MANG 2026 (v3)

---

You are an expert MANG (Meta, Amazon, Apple, Netflix, Google) interview coach and an expert frontend educator. Your candidate codes in **Java** and is targeting **Staff Software Engineer** roles in 2026.

Create:
1) A **comprehensive, pattern-based DSA preparation curriculum**, and
2) A **matching set of self-contained animated HTML pages** that teach the same patterns via interactive visualizations and step-by-step execution.

The output must be ready to use without editing.

---

## Output Artifacts (strict)

Your response must contain these three top-level sections, in this exact order:

1) `## A. Pattern Curriculum (Reference)`
2) `## B. Animated Pages (HTML Code)`
3) `## C. Java Practice Pack (Code)`

Do not add any other top-level sections.

---

## Repo / HTML Conventions (must follow)

These pages must match an existing repo style:

- **Single-file HTML** only (no build tooling). All CSS + JS is inline.
- **CDN dependencies only**:
  - `anime.js` v3.2.1
  - `d3.js` v7
  - Google Fonts: `Inter` + `JetBrains Mono`
- **CSS variables** in `:root`:
  - `--bg: #0d1117`, `--bg-card: #161b22`, `--border: #30363d`, `--text: #e6edf3`, plus a file-specific `--accent`
- **Fixed navbar**:
  - Back link to `../coding-concepts/00_index.html` and `../system-design-concepts/00_index.html`
  - Chapter pager (prev/next)
  - In-page anchors
- **Scroll progress bar**:
  - CSS uses `width: 100%; transform: scaleX(0); transform-origin: left center`
  - JS updates with `el.style.transform = 'scaleX(' + ratio + ')'`
  - Always guard division with `if (total <= 0) return;`
- **Animation trigger**:
  - Use `IntersectionObserver` at 25% threshold for each section
  - Do not autoplay on page load (index pages are exempt; these pattern pages are not index pages)

### Animation rules (hard constraints)

- Animate **only** `transform` and `opacity` (no `width/height/top/left/margin` animations).
- In `anime.js` v3, do not use the `scale:` shorthand; use `opacity` and `translate*` transforms.

---

## B. Animated Pages (what to generate)

You must generate **one standalone HTML page per pattern** (21 total). Each page must include:

- A hero header (pattern name + when to use it)
- At least **3 section cards**, each with a diagram area and an explanation panel
- At least **2 interactive controls** (button/slider/toggle)
- A **stepper** that can play/pause/step through the algorithm on a sample input
- A **trace panel** that lists invariant(s) and updates them live
- A **code panel** that shows the Java template for the pattern (read-only, copyable)

### Required filenames

Emit the HTML in code blocks, each preceded by a line:

`FILE: problem-solving/[NN]_[slug].html`

Use these slugs:

1. `01_sliding_window.html`
2. `02_binary_search.html`
3. `03_recursion.html`
4. `04_monotonic_stack.html`
5. `05_dynamic_programming.html`
6. `06_heap_priority_queue.html`
7. `07_backtracking.html`
8. `08_mixed_must_do.html`
9. `09_arrays_two_pointers_prefix_sums.html`
10. `10_hashing_intervals_sweep_line.html`
11. `11_linked_lists.html`
12. `12_trees_bst.html`
13. `13_trie.html`
14. `14_graphs_bfs_dfs.html`
15. `15_graphs_toposort.html`
16. `16_graphs_shortest_path.html`
17. `17_graphs_mst.html`
18. `18_union_find_dsu.html`
19. `19_bit_manipulation.html`
20. `20_string_algorithms_parsing.html`
21. `21_designish_lru_lfu_stream.html`

Each file must set its own `--accent` color (choose a distinct set; keep the dark theme consistent).

---

## A. Pattern Curriculum (Reference) — format rules

For **each pattern**, include the following sub-sections — strictly in this order, no extras:

```
### [N]. Pattern Name

**What it is:** 1–2 sentences defining the pattern and when to reach for it.

**Canonical problem set:**
Include ALL problems listed under this pattern in the PROBLEM BANK section below.
For each problem, add a one-line note on what makes it representative or tricky.
Where the bank lists sub-groups (e.g., Fixed vs Variable window), preserve that grouping.

**Visualization you will build (for the HTML page):**
Describe the core scene metaphor (e.g., "window frame", "shrinking search range", "stack skyline", "DP table fill") and what the user controls.
Specify:
1) State variables shown on screen
2) The invariant(s) displayed in the trace panel
3) What each step does visually (enqueue/dequeue, pointer moves, stack push/pop, table update)

**Common interview variants / follow-ups:**
1. ...
2. ...
3. (up to 4)

**Key pitfalls & edge cases:**
- ...

**Target complexity:**
- Time: O(...) — explain which algorithm achieves this
- Space: O(...) — note any in-place alternatives

**Optimal approach summary (Java):**
One concise paragraph describing the algorithm, key data structures, invariants to maintain, and any Java-specific notes (e.g., ArrayDeque vs Stack, PriorityQueue ordering, int overflow with long).

**Staff-level system-adjacent prompts:**
1. [Scaling/streaming/memory/distributed angle that turns this into a Staff conversation]
2. [Second angle — different dimension, e.g., online vs offline, approximate vs exact, partial failure]
```

---

## C. Java Practice Pack (what to generate)

Provide a single Java source file in a code block, preceded by:

`FILE: problem-solving/PatternPracticePack.java`

Requirements:

- Include reusable pattern templates (sliding window, binary search, BFS/DFS, topo, Dijkstra, DSU, monotonic stack, DP table patterns, backtracking skeleton).
- For each of patterns 1–8, include **3 fully implemented solutions** selected from the bank (must include at least one “tricky” favorite like `Minimum Window Substring`, `Median of Two Sorted Arrays`, `Largest Rectangle in Histogram`, `Trapping Rain Water`, `Find Median from Data Stream`, `Sudoku Solver`, `Merge Intervals`).
- For remaining bank problems, include method stubs with:
  - signature
  - 3-line “approach + invariant” note
  - target time/space complexity
- Avoid external libraries; use Java standard library only.

Java-specific constraints (weave into code choices):

- Prefer `ArrayDeque<>` over `Stack<>`.
- `PriorityQueue` is min-heap by default; use safe comparators (`Integer.compare`) to avoid overflow.
- Use `long` for sums/products where overflow is plausible.

---

## Problem Bank (Canonical, Curated)

These problems are **required** in section A — every single one must appear under its pattern. Do not drop, rename, or reorder within a group. You may add up to 3 additional problems per pattern if they fill a genuine gap, but the bank problems are non-negotiable.

---

### 🔵 Pattern 1 — Sliding Window (15 problems)

**Fixed Window**
1. Maximum Sum Subarray of Size K
2. First Negative Number in Every Window of Size K
3. Count Occurrences of Anagrams
4. Sliding Window Maximum (LeetCode 239)
5. Maximum Average Subarray I

**Variable Window**
1. Longest Substring Without Repeating Characters
2. Longest Substring with K Unique Characters
3. Minimum Window Substring
4. Fruits Into Baskets
5. Longest Repeating Character Replacement
6. Subarrays with K Different Integers
7. Binary Subarrays with Sum
8. Number of Nice Subarrays
9. Minimum Size Subarray Sum
10. Permutation in String

---

### 🟣 Pattern 2 — Binary Search (15 problems)

**Classic / Direct Search**
1. Binary Search (LeetCode 704)
2. First and Last Position of Element in Sorted Array
3. Count Occurrences in Sorted Array
4. Search in Rotated Sorted Array
5. Find Minimum in Rotated Sorted Array
6. Peak Element
7. Search in Nearly Sorted Array
8. Floor and Ceil in Sorted Array
9. Find Smallest Letter Greater Than Target

**Binary Search on the Answer**
10. Capacity to Ship Packages Within D Days
11. Koko Eating Bananas
12. Aggressive Cows
13. Allocate Minimum Pages
14. Painter's Partition Problem
15. Median of Two Sorted Arrays

---

### 🟢 Pattern 3 — Recursion (12 problems)

1. Print All Subsequences
2. Generate All Permutations
3. Letter Case Permutation
4. Generate Balanced Parentheses
5. N-bit Binary Numbers with More 1s than 0s
6. Tower of Hanoi
7. Josephus Problem
8. Sort an Array using Recursion
9. Delete Middle Element of Stack
10. Reverse a Stack
11. K-th Symbol in Grammar
12. Power Set

---

### 🟡 Pattern 4 — Stack + Monotonic Stack (15 problems)

1. Next Greater Element I & II
2. Nearest Greater to Left
3. Stock Span Problem
4. Largest Rectangle in Histogram
5. Max Area Rectangle in Binary Matrix
6. Trapping Rain Water
7. Min Stack
8. Valid Parentheses
9. Remove K Digits
10. Decode String
11. Daily Temperatures
12. Asteroid Collision
13. Simplify Path
14. Celebrity Problem
15. Score of Parentheses

---

### 🔴 Pattern 5 — Dynamic Programming (30 problems)

**Knapsack Pattern**
1. 0/1 Knapsack
2. Subset Sum
3. Equal Sum Partition
4. Count Subsets with Given Sum
5. Minimum Subset Sum Difference
6. Target Sum

**Unbounded Knapsack**
7. Coin Change (Min Coins)
8. Coin Change (Ways / Count)
9. Rod Cutting

**LCS Pattern**
10. Longest Common Subsequence
11. Longest Common Substring
12. Print LCS
13. Shortest Common Supersequence
14. Minimum Insertions to Make Palindrome
15. Longest Palindromic Subsequence
16. Minimum Deletions to Make Strings Equal

**Interval / Advanced DP**
17. Matrix Chain Multiplication
18. Palindrome Partitioning
19. Boolean Parenthesization
20. Egg Dropping Problem
21. Burst Balloons
22. Scramble String

**LIS Pattern**
23. Longest Increasing Subsequence
24. Maximum Sum Increasing Subsequence
25. Longest Bitonic Subsequence

**Grid DP**
26. Unique Paths
27. Minimum Path Sum
28. Triangle
29. Maximal Square
30. Dungeon Game

---

### 🟠 Pattern 6 — Heap / Priority Queue (12 problems)

1. Kth Largest Element in an Array
2. K Closest Points to Origin
3. Top K Frequent Elements
4. Sort a K-Sorted Array
5. Merge K Sorted Lists
6. Find Median from Data Stream
7. Connect Ropes with Minimum Cost
8. Reorganize String
9. Task Scheduler
10. K Closest Numbers in Sorted Array
11. Frequency Sort
12. Smallest Range Covering Elements from K Lists

---

### ⚫ Pattern 7 — Backtracking (10 problems)

1. Subsets
2. Subsets II (with duplicates)
3. Permutations
4. Permutations II (with duplicates)
5. Combination Sum
6. Combination Sum II
7. N-Queens
8. Sudoku Solver
9. Word Search
10. Rat in a Maze

---

### ⚪ Pattern 8 — Mixed / Must-Do Interview Problems (11 problems)

1. Two Sum
2. 3Sum
3. Container With Most Water
4. Merge Intervals
5. Insert Interval
6. Product of Array Except Self
7. Rotate Image
8. Set Matrix Zeroes
9. Spiral Matrix
10. Majority Element
11. Gas Station

---

## Additional Coverage Requirements (Patterns 9–21)

Beyond the 8 bank patterns above, you MUST also cover every pattern in this list as full pattern entries (same per-pattern template). For these, generate your own 6–12 representative problems (the bank does not specify them):

9. **Arrays, Two Pointers, Prefix Sums** — separate from Sliding Window; focus on prefix sums, difference arrays, and classic two-pointer pair problems
10. **Hashing + Interval problems** — HashMap/HashSet patterns, interval overlap detection, sweep line
11. **Linked Lists** — reversal, cycle detection (Floyd's), merge, k-th from end, intersection
12. **Trees + BST** — traversal, serialization/deserialization, LCA, BST invariants, diameter, path sum variants
13. **Trie** — prefix tree, wildcard search, XOR trie for max XOR
14. **Graphs: BFS + DFS** — islands, connected components, bipartite check, cycle detection (directed + undirected)
15. **Graphs: Topological Sort** — Kahn (BFS) and DFS; course schedule family
16. **Graphs: Shortest Path** — Dijkstra (with states), Bellman-Ford, multi-source BFS
17. **Graphs: MST** — Kruskal (DSU) and Prim; when it appears at Staff level
18. **Union-Find (DSU)** — dynamic connectivity; when to prefer DSU over BFS/DFS
19. **Bit Manipulation** — XOR tricks, bit masks, counting bits, bitmask DP
20. **String Algorithms + Parsing** — KMP failure function, Rabin-Karp intuition, expression evaluation, bracket parsing
21. **Design-ish DSA: LRU / LFU + Stream Problems** — LRU (LinkedHashMap vs manual DLL+Map), LFU, sliding window median, reservoir sampling

---

## Staff-Level Emphasis (apply to ALL 21 patterns)

For every representative problem listed in section A, explicitly flag whether it rewards:

- Clear decomposition into subproblems with named invariants
- Proof of correctness (e.g., “the window always satisfies property X because we contract when Y is violated”)
- Complexity derivation from first principles (not just assertion)
- Tradeoff discussion (time vs space, preprocessing vs query time, approximate vs exact)

Each pattern must include **two distinct** system-adjacent prompts. Draw from:

- Data does not fit on one machine (partitioning/coordination)
- Unbounded stream, single pass with O(k) memory
- Sub-10ms API target (what to precompute offline)
- Massive graph (10B edges) constraints
- Mid-computation failure (checkpoint/restart)
- Noisy/approximate inputs (graceful degradation)
- Need top-K approximate (sketches/heap/streaming)
- Runs inside a transaction (consistency implications)

---

## Tone & Quality Constraints

- **Zero fluff.** No pep talks. Every sentence transfers information.
- **Specific and verifiable.** Add LeetCode numbers where ambiguity is likely. Every complexity claim must name the algorithm that achieves it.
- **Honest about Staff signal.** If a pattern is rarely the focus at Staff level but appears as a building block, say so explicitly.
- **Do not skip artifacts.** All three sections A/B/C must be present, and B must include 21 complete HTML files.
