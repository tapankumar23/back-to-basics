# Staff-Level DSA Pattern Curriculum
## MANG 2026 — Java Track

> Companion to the animated HTML pages in `problem-solving/`. Each pattern maps to one HTML file (01–21).

---

### 1. Sliding Window

**What it is:** A technique for processing contiguous subarrays/substrings by maintaining a window of elements and sliding it across the input, avoiding redundant recomputation. Reach for it when the problem asks for an optimal contiguous subrange satisfying some constraint.

**Canonical problem set:**
- **Max Sum Subarray of Size K** — textbook fixed-window: add right element, subtract element leaving left
- **First Negative Number in Every Window of Size K** — maintain a deque of indices of negative numbers within the window
- **Count Occurrences of Anagrams (LC438)** — fixed window with character frequency map; compare maps O(26) each slide
- **Sliding Window Maximum (LC239) HARD** — monotonic deque (decreasing); front is always current window max; popping from back maintains invariant
- **Max Average Subarray I (LC643)** — fixed window sum divided by K; beware integer vs. double division
- **Longest Substring Without Repeating Characters (LC3)** — variable window with a HashSet; shrink left until duplicate removed
- **Longest Substring with K Unique Characters** — variable window; expand right, shrink left when distinct count exceeds K; track frequency map
- **Minimum Window Substring (LC76) HARD** — variable window with two frequency maps + `formed`/`required` counter trick; shrink from left to minimize
- **Fruits Into Baskets (LC904)** — longest subarray with at most 2 distinct values; classic K-unique variant
- **Longest Repeating Character Replacement (LC424) HARD** — key insight: `window_size - maxFreq <= k`; maxFreq never decreases (only need to check when it could increase)
- **Subarrays with K Different Integers (LC992)** — exactly-K = atMost(K) − atMost(K−1); template reuse
- **Binary Subarrays with Sum (LC930)** — same exactly-K trick; count subarrays with sum exactly S
- **Count Number of Nice Subarrays (LC1248)** — odd numbers treated as 1, even as 0; reduce to binary subarray sum
- **Minimum Size Subarray Sum (LC209)** — variable window; shrink from left as long as sum >= target; track min length
- **Permutation in String (LC567)** — fixed window size len(p); character frequency comparison

**Common interview variants / follow-ups:**
1. What if the stream is infinite? (Can't buffer entire input — use circular buffer / sliding deque)
2. Extend to 2D matrix: sliding window on rows + prefix sums on columns for O(n²·m) rectangle sum
3. How would you handle Unicode characters instead of ASCII? (Use HashMap instead of int[26])
4. What if elements are doubles with floating-point precision issues? (Track sum carefully; avoid repeated addition)

**Key pitfalls & edge cases:**
- Off-by-one: window size condition `right - left + 1 == k` vs `> k`
- The `maxFreq` trick in LC424 — it never decreases, so you don't need to recompute it when the window shrinks; this is subtle but critical
- Exactly-K problems: the atMost(K) − atMost(K−1) decomposition is non-obvious; practice the template
- Empty string / K=0 inputs
- Shrinking the left pointer in variable windows: must update data structures (map, set) before advancing left
- For frequency-map comparison (anagram), comparing two maps each step is O(26) not O(n); don't use `.equals()` on HashMap

**Target complexity:**
- Time: O(n) — each element enters and exits the window at most once
- Space: O(k) for fixed window; O(alphabet size) or O(distinct elements) for variable window

**Optimal approach summary (Java):**
For fixed windows, maintain a running sum or frequency array, adding `arr[right]` and subtracting `arr[right - k]` each iteration. For variable windows, use two pointers `left` and `right`; advance `right` unconditionally, then shrink `left` while the window violates the constraint. The key invariant is that `[left, right]` always represents a valid (or minimal-invalid) window. In Java, use `int[]` arrays of size 26 for ASCII character frequencies (faster than HashMap); use `ArrayDeque<Integer>` for monotonic deque problems. For LC76 (Min Window Substring), maintain `required` = distinct chars needed and `formed` = distinct chars satisfying their count; when `formed == required` shrink from left.

**Staff-level system-adjacent prompts:**
1. **Streaming distinct-user 24-hour window:** How would you count distinct active users in the last 24 hours for a high-throughput event stream? A naive sliding window would require storing all user IDs. Discuss HyperLogLog for approximate distinct count, sliding-window HLL with time buckets, and the tradeoff between precision and memory.
2. **Sub-10ms quantile approximation:** For P99 latency monitoring over a 5-minute rolling window, a sorted array is too expensive to maintain. How does t-digest work, and how would you implement a thread-safe sliding-window t-digest with periodic bucket merging for a monitoring service receiving 100k events/sec?

---

### 2. Binary Search

**What it is:** Halve the search space each iteration by exploiting a monotonic predicate or sorted order. Reach for it when the search space is sorted or when a feasibility function is monotone (if X is feasible, so is X+1 or X-1).

**Canonical problem set:**
- **Binary Search (LC704)** — canonical template: `lo=0, hi=n-1, mid=lo+(hi-lo)/2`; avoid `(lo+hi)/2` overflow
- **Find First and Last Position (LC34)** — two binary searches: first occurrence (bias left) and last occurrence (bias right); template variants matter
- **Count Occurrences in Sorted Array** — lastPos − firstPos + 1; combine two binary searches
- **Search in Rotated Sorted Array (LC33)** — determine which half is sorted, then decide which half target falls in
- **Find Minimum in Rotated Sorted Array (LC153)** — compare `mid` to `hi`; minimum is in the unsorted half
- **Find Peak Element (LC162)** — if `arr[mid] < arr[mid+1]` peak is to the right; both sides work
- **Search in Nearly Sorted Array** — check `mid-1`, `mid`, `mid+1`; still O(log n) amortized
- **Floor and Ceiling in Sorted Array** — track last valid answer in the lo/hi loop
- **Smallest Letter Greater Than Target (LC744)** — wrap-around edge case; `% n` trick
- **Capacity to Ship Packages (LC1011)** — search on answer: binary search on `[max_weight, sum_weight]`; feasibility = simulate days needed
- **Koko Eating Bananas (LC875)** — binary search on eating speed `[1, max_pile]`; feasibility = total hours ≤ h
- **Aggressive Cows (SPOJ)** — binary search on minimum distance; feasibility = greedy placement check
- **Allocate Minimum Pages / Painter's Partition** — binary search on max pages/time; feasibility = count students/painters needed
- **Median of Two Sorted Arrays (LC4) HARDEST** — binary search partition: find partition point in smaller array such that `maxLeft1 ≤ minRight2` and `maxLeft2 ≤ minRight1`; O(log min(m,n))

**Common interview variants / follow-ups:**
1. What if the array has duplicates in LC33? — LC81: worst case degrades to O(n); must handle `arr[lo] == arr[mid] == arr[hi]`
2. Extend Koko to minimize the number of workers given a deadline constraint
3. Binary search on a 2D sorted matrix (LC74): treat as flattened 1D array, `mid/n` and `mid%n` for row/col
4. Template discipline: closed interval `[lo, hi]` vs half-open `[lo, hi)` — pick one and be consistent

**Key pitfalls & edge cases:**
- Integer overflow: always use `mid = lo + (hi - lo) / 2`
- Off-by-one in termination: `while (lo <= hi)` vs `while (lo < hi)` — the second keeps a 2-element window and needs careful post-loop handling
- Search-on-answer: identify the monotone predicate first; ensure feasibility(lo) is false and feasibility(hi) is true (or vice versa)
- Rotated array: when `arr[lo] == arr[mid]`, you can't determine which side is sorted — increment lo
- LC34 "last occurrence": set `ans = mid` then `lo = mid + 1` to bias right; opposite for first occurrence

**Target complexity:**
- Time: O(log n) for standard; O(log min(m,n)) for LC4; O(n log n) for search-on-answer when feasibility check is O(n)
- Space: O(1) iterative; O(log n) recursive call stack

**Optimal approach summary (Java):**
Use the closed-interval template: `int lo = 0, hi = n - 1; while (lo <= hi) { int mid = lo + (hi - lo) / 2; ... }`. For first-occurrence variants, when `arr[mid] == target` record `ans = mid` and set `hi = mid - 1`. For search-on-answer, set `lo` to the minimum possible answer and `hi` to the maximum; the feasibility check determines whether to go left or right. For LC4, always binary search on the shorter array; the partition invariant ensures `maxLeft ≤ minRight` on both sides. In Java, watch for `Integer.MIN_VALUE` in edge cases when computing medians with empty partitions — use `Integer.MIN_VALUE` and `Integer.MAX_VALUE` as sentinels.

**Staff-level system-adjacent prompts:**
1. **B-tree index for a 5TB sorted file:** How would you design a B-tree index to support O(log n) range queries on a 5TB sorted file stored on disk, where each disk page is 4KB? Discuss fan-out, index page structure, and how binary search maps to tree traversal vs. page I/O amortization.
2. **Distributed binary search on partitioned arrays:** Given a sorted dataset partitioned across 1000 shards where each shard holds a sorted segment, how would you perform a binary search to find the k-th smallest element? Discuss the two-phase protocol: first broadcast range query to all shards to get counts, then binary search on the answer space with O(log(max_val) * num_shards) total RPCs.

---

### 3. Recursion

**What it is:** Breaking a problem into identical subproblems on smaller inputs, solving them, and combining results. Reach for it when the problem has natural self-similar structure — generating all combinations, tree traversal, divide-and-conquer — and when iteration would require explicit stack management.

**Canonical problem set:**
- **Print All Subsequences of a String** — binary choice at each index: include or exclude; 2^n leaves
- **Generate All Permutations** — swap-based: fix position i, recurse on i+1; backtrack by swapping back
- **Letter Case Permutation (LC784)** — branch on letters (upper/lower), don't branch on digits
- **Balanced Parentheses / Generate Parentheses (LC22)** — state = (open_count, close_count); recurse only if invariant holds
- **N-bit Binary Strings with More 1s Than 0s at Every Prefix** — Ballot problem variant; pruning prevents invalid prefixes
- **Tower of Hanoi** — classic 3-move recursion; recurrence T(n) = 2T(n-1)+1; demonstrates exponential recursion
- **Josephus Problem** — mathematical recursion: `f(n,k) = (f(n-1,k) + k) % n`; O(n) tail-recursive form
- **Sort Array via Recursion (Merge Sort)** — divide at mid, sort halves, merge; O(n log n) time O(n) space
- **Delete Middle Element of a Stack** — recursion simulates the extra stack; pop all, delete at size/2, push back
- **Reverse a Stack** — two mutually recursive functions: reverse(stack) and insertAtBottom(stack, item)
- **K-th Symbol in Grammar (LC779)** — parent of position k in row n is at `(k-1)/2` in row n-1; value depends on parity
- **Power Set (LC78)** — at each element, branch include/exclude; results accumulate at leaves

**Common interview variants / follow-ups:**
1. Convert any tail-recursive function to iterative (accumulator pattern); identify non-tail recursion and convert with explicit stack
2. Memoize overlapping subproblems: recognize when the same (index, state) pair is visited multiple times → HashMap cache
3. What happens with 500k recursive calls? — Stack depth limits in Java (default ~10k frames); convert to iterative with `ArrayDeque` explicitly

**Key pitfalls & edge cases:**
- Missing or incorrect base case — most common bug; always enumerate base cases explicitly
- Forgetting to backtrack in permutation/combination generation (undoing the choice after recursion)
- Stack overflow on large inputs — Java's default thread stack is ~512KB; deep recursion on n>10k needs `-Xss` or iterative conversion
- Branching factor matters: 2^n vs n! blows up differently; identify before coding
- Trust the recursion — a common beginner mistake is trying to trace the full call tree instead of trusting the inductive hypothesis

**Target complexity:**
- Time: O(2^n) for subset/subsequence generation; O(n!) for permutations; O(n log n) for divide-and-conquer
- Space: O(n) call stack depth for linear recursion; O(n) for divide-and-conquer; O(2^n) for enumeration (output size)

**Optimal approach summary (Java):**
Structure recursive functions with: (1) base case first, (2) recursive case with smaller subproblem, (3) combine results. For enumeration (subsets, permutations), use a `List<T> current` passed by reference and backtrack by removing the last element. For stack-based problems (reverse stack, delete middle), use the call stack as an implicit second stack. When converting to iterative, replace the call stack with an explicit `ArrayDeque<State>` where `State` captures all local variables. In Java, `ArrayList` passed to recursive methods is shared — always call `new ArrayList<>(current)` when adding to results, not `current` directly.

**Staff-level system-adjacent prompts:**
1. **K-th lexicographic permutation via Lehmer codes:** Given n! orderings of n elements, compute the k-th permutation in O(n²) using the Lehmer code (factoradic number system) without enumerating all permutations. How would this extend to finding the k-th combination or the rank of a given permutation — useful in distributed job assignment systems?
2. **StackOverflow on 500k-node DFS:** A production DFS on a social graph with 500k nodes caused a StackOverflowError. Walk through the iterative conversion using an explicit `ArrayDeque<Integer>` stack, the state machine needed to simulate pre/in/post-order, and how to profile stack depth before shipping.

---

### 4. Stack + Monotonic Stack

**What it is:** A stack that maintains a monotonically increasing or decreasing sequence of elements; when a new element violates the monotonic property, pop and process elements until the property is restored. Reach for it when you need the "next greater/smaller element" or when the answer for each element depends on the nearest element satisfying a comparison.

**Canonical problem set:**
- **Next Greater Element I (LC496)** — precompute NGE for nums2 using monotonic stack; answer queries via HashMap
- **Next Greater Element II (LC503)** — circular array; iterate 2n with index modulo; monotonic stack of indices
- **Nearest Greater to Left** — iterate left-to-right; pop while stack top ≤ current; stack top is left-NGE
- **Stock Span Problem** — span = distance to previous greater price; maintain (price, span) or just index stack
- **Largest Rectangle in Histogram (LC84) HARD** — for each bar, find left and right boundaries where height < current; pop when violated; process remaining stack at end
- **Max Area Rectangle in Binary Matrix (LC85)** — reduce to histogram per row using running height; apply LC84 on each row; O(m·n)
- **Trapping Rain Water (LC42) HARD** — two approaches: (1) precompute leftMax/rightMax arrays O(n) space, (2) two-pointer O(1) space, (3) monotonic stack O(n)
- **Min Stack (LC155)** — maintain auxiliary min stack in sync; push min of (current, minStack.peek()) to min stack
- **Valid Parentheses (LC20)** — push opening brackets; on closing, check top matches; empty at end
- **Remove K Digits (LC402)** — maintain increasing monotonic stack of digits; pop when larger digit precedes smaller; trim trailing digits if k remaining
- **Decode String (LC394)** — two stacks: one for counts, one for string prefixes; push on `[`, pop and expand on `]`
- **Daily Temperatures (LC739)** — monotonic stack of indices; pop when current temp > stack-top temp; answer = current_index − popped_index
- **Asteroid Collision (LC735)** — simulate with stack; positive asteroids push, negative ones destroy positives from top; both same sign coexist
- **Simplify Path (LC71)** — split on `/`, use deque as stack; `..` pops, `.` and empty skip, else push
- **Celebrity Problem** — eliminate non-celebrities: if A knows B, A is not celebrity; two-pointer or stack reduction
- **Score of Parentheses (LC856)** — stack tracking current score; `()` contributes 1; `(X)` contributes 2X

**Common interview variants / follow-ups:**
1. Find the previous smaller element (PSE) — symmetric to NGE; decrease the monotonic property
2. Sum of subarray minimums (LC907): each element contributes `left_count * right_count` times as minimum; use PSE/NSE
3. Max width ramp (LC962): monotonic stack of candidates + reverse scan from right
4. What if the stack needs to support O(1) max in addition to O(1) min? — Dual auxiliary stacks

**Key pitfalls & edge cases:**
- Deciding whether to use `>=` or `>` when popping — determines left vs. right boundary behavior in histogram; affects duplicate handling
- Clearing remaining stack elements: in histogram problems, remaining elements on the stack need to be processed with virtual boundaries
- Index vs. value: almost always store indices in the monotonic stack (not values) so you can compute distances
- In LC402 Remove K Digits, after the stack loop, if k > 0 remove from the end; also handle leading zeros

**Target complexity:**
- Time: O(n) — each element is pushed and popped at most once
- Space: O(n) for the stack

**Optimal approach summary (Java):**
Use `Deque<Integer> stack = new ArrayDeque<>()` (prefer `ArrayDeque` over `Stack` in Java — it's faster and not synchronized). For monotonic decreasing stack (next greater element), pop while `stack.peek() <= current`; the popped element's NGE is `current`. For LC84 (histogram), push indices; pop when `heights[i] < heights[stack.peek()]`; width = `i - stack.peek() - 1` (or `i` if stack is empty). Always handle the empty-stack case when computing left boundary. For Min Stack, co-maintain a parallel min stack — push `Math.min(val, minStack.isEmpty() ? val : minStack.peek())`.

**Staff-level system-adjacent prompts:**
1. **Online stream previous-greater with bounded memory:** In a real-time event stream, you need to answer "how long ago was the last event with a higher value" for every incoming event using at most O(W) memory where W is the lookback window. Discuss how a bounded monotonic deque with timestamp expiration satisfies this, and where this pattern appears in financial tick data processing.
2. **Distributed histogram largest-rectangle:** Given a histogram stored as a columnar dataset across 100 shards (each shard owns a range of x-coordinates), design a distributed algorithm to find the largest rectangle. Discuss the divide-and-conquer merge step: the rectangle spanning the shard boundary requires cross-shard state exchange.

---

### 5. Dynamic Programming

**What it is:** Solve problems with optimal substructure and overlapping subproblems by memoizing or tabulating subproblem solutions. Reach for it when the brute-force recursion recomputes the same state repeatedly and the problem asks for an optimal value or count.

**Canonical problem set:**
- **0/1 Knapsack** — `dp[i][w] = max(dp[i-1][w], dp[i-1][w-wt[i]] + val[i])`; can reduce to 1D with reverse iteration
- **Subset Sum** — boolean DP; `dp[i][s] = dp[i-1][s] || dp[i-1][s-arr[i]]`
- **Equal Sum Partition** — check if total sum is even; reduce to subset sum with target = total/2
- **Count Subsets with Given Sum** — count DP variant; `dp[i][s] += dp[i-1][s-arr[i]]`
- **Minimum Subset Sum Difference** — find subset sum closest to total/2 using DP; answer = total − 2*closest
- **Target Sum (LC494)** — assign + or − to each number; reduces to count subsets with sum = (target + total) / 2
- **Coin Change (LC322)** — unbounded knapsack: `dp[i] = min(dp[i], dp[i-coin]+1)` for each coin; initialize to infinity
- **Coin Change II (LC518)** — count ways: outer loop coins, inner loop amounts (order matters for unbounded)
- **Rod Cutting** — unbounded knapsack on lengths; max revenue by cutting rod
- **LCS (LC1143)** — `dp[i][j] = dp[i-1][j-1]+1` if match, else `max(dp[i-1][j], dp[i][j-1])`
- **Longest Common Substring** — `dp[i][j] = dp[i-1][j-1]+1` if match, else 0; track global max
- **Shortest Common Supersequence (LC1092)** — length = len(s1) + len(s2) − LCS(s1, s2); reconstruct by tracing DP table
- **Min Insertions to Make Palindrome** — = len(s) − LPS(s); LPS = LCS(s, reverse(s))
- **Longest Palindromic Subsequence (LC516)** — LCS of s and reverse(s)
- **Min Deletions to Make Palindrome** — same as min insertions (symmetry)
- **Matrix Chain Multiplication** — interval DP: `dp[i][j]` = min cost to multiply matrices i..j; try all split points k
- **Palindrome Partitioning II (LC132)** — `dp[i]` = min cuts for s[0..i]; for each palindrome s[j..i], `dp[i] = min(dp[j-1]+1)`
- **Boolean Parenthesization** — count ways to parenthesize to get True/False; interval DP with operator types
- **Egg Dropping (LC887)** — `dp[e][f]` = max floors testable with e eggs and f trials; or binary search + DP
- **Burst Balloons (LC312)** — interval DP; `dp[i][j]` = max coins bursting all in (i,j); the last balloon k to burst earns `nums[i]*nums[k]*nums[j]`
- **Scramble String (LC87)** — 3D DP or memoization; `dp[s1][s2]` = can s1 be scrambled to s2
- **LIS (LC300)** — patience sorting / binary search O(n log n); or plain O(n²) DP
- **Max Sum Increasing Subsequence** — LIS variant tracking sum instead of length
- **Longest Bitonic Subsequence** — LIS from left + LIS from right − 1 at each index
- **Unique Paths (LC62)** — `dp[i][j] = dp[i-1][j] + dp[i][j-1]`; combinatorics shortcut: C(m+n-2, m-1)
- **Min Path Sum (LC64)** — same recurrence, minimize instead of count
- **Triangle (LC120)** — bottom-up; `dp[j] = min(dp[j], dp[j+1]) + triangle[i][j]`; in-place
- **Maximal Square (LC221)** — `dp[i][j] = min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1]) + 1` if cell is '1'
- **Dungeon Game (LC174)** — right-to-left, bottom-to-top DP; must ensure health ≥ 1 at every cell

**Common interview variants / follow-ups:**
1. Space optimization: most 2D DP can be reduced to O(min(m,n)) using rolling arrays
2. Reconstruction: trace back through DP table by checking which transition was taken
3. LIS in O(n log n): patience sorting with binary search (Arrays.binarySearch on tails array)
4. What if items can be used fractionally? — Greedy (fractional knapsack) vs DP (0/1 knapsack)

**Key pitfalls & edge cases:**
- Unbounded vs 0/1 knapsack: inner loop direction matters — forward for unbounded, reverse for 0/1 when using 1D DP
- Coin Change initialization: `dp[0] = 0`, `dp[i] = Integer.MAX_VALUE` — guard against `Integer.MAX_VALUE + 1` overflow when checking `dp[i-coin] != Integer.MAX_VALUE`
- Interval DP traversal order: must iterate by increasing interval length, not just by i
- Egg Drop: the standard DP is O(KN²); the O(KN log N) version uses binary search
- Target Sum (LC494): the partition formula `(target + total) / 2` requires target + total to be even and non-negative

**Target complexity:**
- Time: O(n·W) knapsack, O(n²) LCS/LIS naive, O(n log n) LIS optimal, O(n³) interval DP
- Space: O(n·W) → O(W) with rolling array; Hirschberg's for O(n) space LCS reconstruction

**Optimal approach summary (Java):**
Define the DP state explicitly before coding: "dp[i][j] represents X for subproblem Y." Write the recurrence, identify base cases, and determine traversal order (usually top-down or left-to-right). In Java, use `int[][]` initialized with `Arrays.fill` inside a loop rather than `new int[n][m]` which zero-initializes. For 1D rolling-array knapsack: outer loop over items, inner loop over capacity in reverse (0/1) or forward (unbounded). For LIS O(n log n): maintain a `tails` array where `tails[i]` is the smallest tail of any increasing subsequence of length `i+1`; use `Arrays.binarySearch` to find insertion position. For interval DP, write `for (int len = 2; len <= n; len++) for (int i = 0; i + len - 1 < n; i++) { int j = i + len - 1; ... }`.

**Staff-level system-adjacent prompts:**
1. **Rolling array + Hirschberg for large-scale sequence alignment:** In bioinformatics, aligning two DNA sequences of length 10M each requires O(n²) space with standard LCS DP — 100TB for long long. Explain Hirschberg's divide-and-conquer algorithm that reduces space to O(n) while maintaining O(mn) time, and how this maps to distributed alignment jobs.
2. **FPTAS for distributed knapsack:** In a cloud resource allocation system, you have a knapsack-style problem across thousands of servers. A fully polynomial-time approximation scheme (FPTAS) scales item values by a factor to reduce the DP table size and achieve (1−ε) approximation in O(n²/ε) time. How would you implement this for distributed bin-packing of workloads?

---

### 6. Heap / Priority Queue

**What it is:** A complete binary tree satisfying the heap property; provides O(log n) insert and extract-min/max, and O(1) peek. Reach for it when you need repeated access to the minimum or maximum, top-K elements, or merging K sorted sequences.

**Canonical problem set:**
- **Kth Largest Element (LC215)** — min-heap of size K; after processing all elements, peek is the answer; O(n log k)
- **K Closest Points to Origin (LC973)** — max-heap of size K on distance; pop when size > K; or quickselect O(n) average
- **Top K Frequent Elements (LC347)** — frequency map + min-heap of (freq, elem) of size K; bucket sort O(n) alternative
- **Sort a K-Sorted (Nearly Sorted) Array** — min-heap of size K+1; always extract min and insert next; O(n log k)
- **Merge K Sorted Lists (LC23)** — min-heap of (value, listNode); poll and push next node; O(N log k) where N = total nodes
- **Find Median from Data Stream (LC295) HARD** — two heaps: max-heap for lower half, min-heap for upper half; maintain size difference ≤ 1; median is peek(s) or average of two peeks
- **Minimum Cost to Connect Ropes** — Huffman-style: always merge two smallest; min-heap gives O(n log n)
- **Reorganize String (LC767)** — max-heap by frequency; greedily place most-frequent char, alternating; if top freq > (n+1)/2 impossible
- **Task Scheduler (LC621)** — math formula solution exists; simulation with max-heap + cooldown queue also works
- **K Closest in Sorted Array (LC658)** — binary search for closest element + two-pointer or heap expansion
- **Sort Characters by Frequency (LC451)** — bucket sort or max-heap by frequency
- **Smallest Range Covering Elements from K Lists (LC632) HARD** — min-heap tracking one element per list; maintain current max separately; advance the minimum to shrink range

**Common interview variants / follow-ups:**
1. Find the top-K elements in a stream — maintain a min-heap of size K; O(n log k) time O(k) space
2. Running median with window constraint (LC480): sliding window median combines heap + lazy deletion (tombstone set)
3. Why prefer quickselect over heap for top-K? — Quickselect O(n) average vs O(n log k); heap is better when K is large or input is streaming
4. How does Java's PriorityQueue compare to a Fibonacci heap for Dijkstra? — Java's is binary heap O((V+E)log V); Fibonacci heap theoretical O(E + V log V) but constant factors make it impractical

**Key pitfalls & edge cases:**
- Java `PriorityQueue` is a min-heap by default; for max-heap use `Collections.reverseOrder()` or `(a, b) -> b - a` (avoid subtraction for integers — use `Integer.compare(b, a)` to avoid overflow)
- `PriorityQueue` does not support O(1) contains or O(log n) arbitrary removal — use a `TreeMap` or lazy deletion when needed
- Two-heap median (LC295): when inserting, always route through one heap then rebalance; don't insert directly to the "wrong" heap
- LC632: maintaining the current window maximum while advancing the minimum requires tracking max in each heap poll

**Target complexity:**
- Time: O(n log k) for top-K; O(n log n) for full sort; O(log n) per insert/extract
- Space: O(k) for top-K heap; O(k) for K-way merge

**Optimal approach summary (Java):**
`PriorityQueue<Integer> minHeap = new PriorityQueue<>()` for min-heap; `new PriorityQueue<>(Collections.reverseOrder())` for max-heap. For custom objects: `new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]))`. For top-K with a min-heap: add element, then `if (heap.size() > k) heap.poll()`. For two-heap median: `lowerHalf` is a max-heap, `upperHalf` is a min-heap; after each insert, ensure `lowerHalf.size() == upperHalf.size()` or `lowerHalf.size() == upperHalf.size() + 1`. For K-way merge using a `PriorityQueue<int[]>` storing `{value, listIndex, elementIndex}`.

**Staff-level system-adjacent prompts:**
1. **t-digest for streaming median at scale:** Java's two-heap approach requires exact data; at 1M events/sec, memory is unbounded. Explain t-digest: a sketch data structure that compresses the distribution into weighted centroids, guarantees O(1) amortized insert, and answers quantile queries with bounded error. How would you implement a distributed t-digest with periodic merge across fleet nodes?
2. **Multi-pass merge for exabyte-scale K-way merge:** MapReduce sort requires merging K sorted files that don't fit in memory. Describe external merge sort: pass 1 generates sorted runs of size M (RAM), pass 2+ merges K runs at a time using a K-way min-heap reading one page per run. Calculate optimal K given disk I/O bandwidth and available memory.

---

### 7. Backtracking

**What it is:** Systematic enumeration of candidates by incrementally building solutions and abandoning (backtracking) partial candidates that cannot lead to valid completions. Reach for it when the solution space is a tree of choices and you need all valid solutions or the optimal one among all valid solutions.

**Canonical problem set:**
- **Subsets (LC78)** — at each index, branch include/exclude; all 2^n paths in the recursion tree are valid leaves
- **Subsets II (LC90)** — sort first; skip duplicates at the same recursion level: `if (i > start && nums[i] == nums[i-1]) continue`
- **Permutations (LC46)** — swap `nums[i]` with each `nums[j]` for j ≥ i; recurse on i+1; swap back to backtrack
- **Permutations II (LC47)** — sort + `used[]` boolean array; skip if `used[i]` or `(i > 0 && nums[i] == nums[i-1] && !used[i-1])`
- **Combination Sum (LC39)** — unbounded: same element can be reused; don't advance start index when recursing
- **Combination Sum II (LC40)** — each element used once; sort + skip duplicates at same level
- **N-Queens (LC51) HARD** — for each row, try each column; prune if column or diagonal is occupied; `cols`, `diag1` (row-col), `diag2` (row+col) as boolean arrays or bitmasks
- **Sudoku Solver (LC37) HARD** — constraint propagation: for each empty cell try 1-9; check row/col/box; backtrack on failure; with arc-consistency preprocessing converges much faster
- **Word Search (LC79)** — DFS on grid; mark cell visited (in-place with sentinel), recurse in 4 directions, unmark on backtrack
- **Rat in a Maze** — same as Word Search but on a binary matrix; track visited array explicitly

**Common interview variants / follow-ups:**
1. Generate all valid IP addresses from a string — backtracking with 4 segments, each 0-255, no leading zeros
2. Restore IP addresses / break string into words (Word Break II LC140) — memoized backtracking to avoid re-exploration
3. Palindrome Partitioning (LC131) — backtrack + precompute palindrome table with DP
4. How would you parallelize backtracking search? — Work-stealing on the top-level branches; each worker gets a subtree

**Key pitfalls & edge cases:**
- Forgetting to backtrack (undo the choice): in-place modifications must be reversed — mark visited before recursing, unmark after
- Duplicate handling in Subsets II / Permutations II: must sort first; the condition `i > start && nums[i] == nums[i-1]` relies on sorted order
- N-Queens diagonal indices: `row - col` can be negative; use `row - col + n` as the offset, or use a HashSet
- Combination Sum: when target becomes 0, add result; when target < 0, return early (pruning)
- In LC37 Sudoku, find the next empty cell efficiently — scanning left-to-right top-to-bottom is fine at 9×9 scale

**Target complexity:**
- Time: O(2^n) for subsets, O(n!) for permutations, O(n^(m+1)) for combination sum, O(n!) for N-Queens (upper bound; actual much less with pruning)
- Space: O(n) recursion depth; O(n) for current path

**Optimal approach summary (Java):**
Structure every backtracking solution as: (1) base case — add solution to results if valid/complete, (2) loop over choices, (3) make choice, (4) recurse, (5) undo choice. Use a `List<Integer> path` and `result.add(new ArrayList<>(path))` to snapshot the path. For N-Queens, use three boolean arrays: `boolean[] cols = new boolean[n]`, `boolean[] diag1 = new boolean[2*n]` (row-col+n), `boolean[] diag2 = new boolean[2*n]` (row+col) — faster than HashSet. For Sudoku, precompute `boolean[9][9]` for each row/col/box constraint. Prune aggressively: in Combination Sum, if `remaining < 0` return immediately.

**Staff-level system-adjacent prompts:**
1. **Constructive O(n) N-Queens:** The explicit formula for placing queens on an n×n board without recursion uses modular arithmetic to construct a valid arrangement directly in O(n). When would you choose this over backtracking, and how does this relate to perfect hash function construction?
2. **Distributed Sudoku with work-stealing:** For a 16×16 or 25×25 Sudoku (harder constraint satisfaction), backtracking alone is too slow. Describe a distributed solver: the master enumerates the first few levels of the search tree (branching factor ~10), assigns subtrees to workers via a shared work queue (work-stealing), and uses constraint propagation (AC-3 arc consistency) locally on each worker to prune faster.

---

### 8. Mixed / Must-Do Foundational Problems

**What it is:** A curated set of problems that appear at nearly every MANG screen — not a single pattern but the intersection of arrays, two pointers, and hashmaps. These problems are table stakes: if you cannot solve them fluently, no other preparation matters.

**Canonical problem set:**
- **Two Sum (LC1)** — HashMap storing complement; single pass O(n); handle duplicate keys
- **3Sum (LC15)** — sort + two pointers; skip duplicates at outer loop and inner pointers; O(n²)
- **Container With Most Water (LC11)** — two pointers from ends; always advance the shorter wall; proof: the taller side can't improve by moving
- **Merge Intervals (LC56)** — sort by start; merge when `intervals[i].start <= last.end`; update end to max
- **Insert Interval (LC57)** — find insertion point; merge overlapping intervals in a single pass; no sort needed (input is sorted)
- **Product of Array Except Self (LC238)** — prefix product left pass, suffix product right pass; no division; O(1) extra space with output array
- **Rotate Image (LC48)** — transpose (swap `[i][j]` and `[j][i]`), then reverse each row; in-place
- **Set Matrix Zeroes (LC73)** — use first row and first column as markers; handle `(0,0)` ambiguity with a separate flag
- **Spiral Matrix (LC54)** — shrink boundary (top/bottom/left/right) after each pass; check boundary validity before each direction
- **Majority Element (LC169)** — Boyer-Moore Voting: candidate + count; increment when same, decrement when different, reset at 0; candidate is majority
- **Gas Station (LC134)** — if total gas >= total cost, solution exists; start from the first station where running sum resets to 0

**Common interview variants / follow-ups:**
1. 4Sum (LC18): sort + two-pointer inner loop; O(n³); generalize to kSum recursively
2. Merge Intervals streaming: maintain sorted interval set with TreeMap; merge lazily on query
3. Majority Element II (LC229): Boyer-Moore for up to 2 candidates (elements appearing > n/3 times); validate candidates in second pass
4. Rotate Image for non-square matrices — requires extra space (reshape) or algorithmic rotation cycles

**Key pitfalls & edge cases:**
- Two Sum: when the two indices are the same element, the HashMap approach naturally handles it since you check before inserting or query before the element is inserted
- 3Sum: duplicate handling requires advancing past duplicates both at the outer loop and after finding a valid pair inside
- Merge Intervals: after sorting, if the array has one interval, return it as-is; don't assume `result` is non-empty
- Spiral Matrix: after processing top row, increment `top`; check `top <= bottom` before processing left column to avoid double-counting on single-row remaining
- Gas Station: the greedy works only because there's at most one valid starting position; if total fuel is insufficient, return -1 immediately

**Target complexity:**
- Time: O(n) Two Sum, Majority Element, Gas Station, Product Array; O(n log n) Merge Intervals (sort); O(n²) 3Sum
- Space: O(n) Two Sum; O(1) in-place for most matrix problems; O(n) output for Merge Intervals

**Optimal approach summary (Java):**
For Two Sum: `Map<Integer, Integer> seen = new HashMap<>()` storing value→index; check `seen.containsKey(target - nums[i])` before `seen.put(nums[i], i)`. For 3Sum: `Arrays.sort(nums)`; outer loop with two-pointer inner; skip `nums[i] == nums[i-1]` for outer, skip duplicates for inner after finding pair. For Boyer-Moore: `int candidate = 0, count = 0`; iterate: if `count == 0` set `candidate = num`, then `count += (num == candidate) ? 1 : -1`. For Product Array: use the output array as the left-prefix array, then multiply right-suffix in a second pass with a running variable.

**Staff-level system-adjacent prompts:**
1. **10B interval streaming merge:** Given a stream of 10 billion time intervals (start, end), maintain a merged interval set for real-time overlap queries. Discuss segment trees vs. interval trees vs. augmented BSTs (Java `TreeMap` with ceiling/floor queries), and how to shard by time range across multiple nodes for a distributed calendar conflict detection system.
2. **Product array at 1B scale offline:** In a distributed MapReduce context, compute the product-of-all-except-self for a 1B-element array. Phase 1: prefix products per partition. Phase 2: communicate partition boundary products. Phase 3: local suffix multiplication with global context. How does this map to a Spark RDD scan with accumulators?

---

### 9. Arrays, Two Pointers, Prefix Sums

**What it is:** Prefix sums build a precomputed array enabling O(1) range queries after O(n) preprocessing. Two pointers eliminate O(n²) brute force on sorted data by maintaining invariants at both ends or converging toward a target. Difference arrays enable O(1) range updates with O(n) final materialization.

**Canonical problem set:**
- **Subarray Sum Equals K (LC560)** — prefix sum + HashMap: `count[prefixSum - k]++`; count of subarrays ending at i with sum K
- **Range Sum Query Immutable (LC303)** — prefix sum array; `sum(l, r) = prefix[r+1] - prefix[l]`
- **2D Range Sum Query (LC304)** — 2D prefix sums; inclusion-exclusion: `sum = pre[r2+1][c2+1] - pre[r1][c2+1] - pre[r2+1][c1] + pre[r1][c1]`
- **Product of Array Except Self (LC238)** — two-pass prefix/suffix product; classic O(1) space trick
- **Maximum Subarray (LC53)** — Kadane's algorithm: `curMax = max(num, curMax + num)`; track global max; handles all-negative
- **Minimum Size Subarray Sum (LC209)** — two pointers; shrink from left while sum >= target; O(n)
- **Sort Colors (LC75)** — Dutch National Flag: three pointers (low, mid, high); single pass
- **Trapping Rain Water (LC42)** — two-pointer: advance the shorter side, water = min(leftMax, rightMax) − height
- **Squares of a Sorted Array (LC977)** — two pointers from ends; largest square is at one of the ends; fill result right-to-left
- **3Sum Closest (LC16)** — sort + two pointers; track minimum absolute difference from target
- **Difference Array for Range Updates** — `diff[l] += val, diff[r+1] -= val`; prefix sum to materialize; useful for overlapping range increment problems

**Common interview variants / follow-ups:**
1. Maximum sum rectangle in 2D matrix: fix left and right columns, reduce to 1D maximum subarray (Kadane's); O(n²·m)
2. Subarray with equal 0s and 1s: replace 0 with -1, reduce to subarray sum = 0, use prefix sum + HashMap
3. Minimum operations to make array equal (LC1551): difference array insight; answer is sum of first ⌈n/2⌉ odd numbers
4. 2D difference array: 4-corner updates for rectangle range increments; O(1) per update, O(mn) finalize

**Key pitfalls & edge cases:**
- Prefix sum offset: `prefix[0] = 0` sentinel makes range sum formula clean; `prefix[i] = prefix[i-1] + arr[i-1]`
- Subarray Sum Equals K: initialize `map.put(0, 1)` for subarrays starting at index 0
- Kadane's handles all-negative: initialize `curMax = Integer.MIN_VALUE` and start from `nums[0]`; or `curMax = nums[0]`, `maxSum = nums[0]`
- Dutch National Flag: when `arr[mid] == 1`, only advance mid; when `arr[mid] == 2`, swap with high and decrement high (don't advance mid)
- Difference array: `r+1` may be out of bounds — use size `n+1` array

**Target complexity:**
- Time: O(n) preprocessing, O(1) query for prefix sums; O(n) for two-pointer; O(n log n) for sort-dependent
- Space: O(n) prefix array; O(1) for two-pointer in-place

**Optimal approach summary (Java):**
For prefix sums, build `int[] prefix = new int[n+1]` with `prefix[i+1] = prefix[i] + nums[i]`; query `sum(l, r) = prefix[r+1] - prefix[l]`. For LC560, use `HashMap<Integer, Integer> countMap = new HashMap<>()` initialized with `{0: 1}`; running sum updates the map after each index. For Kadane's: `int cur = nums[0], max = nums[0]; for (int i = 1; i < n; i++) { cur = Math.max(nums[i], cur + nums[i]); max = Math.max(max, cur); }`. For difference array: `int[] diff = new int[n+1]`; apply `diff[l]++, diff[r+1]--`; recover with `for (int i = 1; i <= n; i++) diff[i] += diff[i-1]`.

**Staff-level system-adjacent prompts:**
1. **Fenwick tree (BIT) for mutable O(log n) range queries:** When the array is updated frequently (not immutable), a prefix sum array requires O(n) rebuild. A Binary Indexed Tree supports O(log n) point update and prefix sum query. Implement the `update(i, delta)` and `query(l, r)` operations in Java, and explain where BITs appear in real systems (e.g., order book cumulative volume, event counter time series).
2. **GPU prefix sum vectorization:** Modern GPUs implement parallel prefix sum (scan) in O(log n) steps using a work-efficient up-sweep/down-sweep algorithm. Explain the two phases, their use in GPU stream compaction (e.g., removing null results from a parallel filter), and how this relates to distributed prefix sums in MapReduce (partial prefix per partition + global correction).

---

### 10. Hashing + Intervals + Sweep Line

**What it is:** Hashing provides O(1) average-case lookup, insertion, and deletion. Interval problems model time ranges, resource allocation, and overlap detection. Sweep line processes events in sorted order (typically by x-coordinate or time) to answer queries about the state at any point.

**Canonical problem set:**
- **Group Anagrams (LC49)** — sort each string as key (O(k log k)) or use char frequency array as key (O(k)); group by key
- **Top K Frequent Words (LC692)** — frequency map + min-heap of size K, or bucket sort by frequency
- **Longest Consecutive Sequence (LC128)** — HashSet; for each number start a sequence only if `num-1` not in set; O(n)
- **4Sum Count (LC454)** — HashMap of pair sums from (A,B); count complement pairs from (C,D); O(n²)
- **Meeting Rooms I (LC252)** — sort by start; check if any `intervals[i].start < intervals[i-1].end`
- **Meeting Rooms II (LC253)** — min-heap of end times or sweep line: events = starts (+1) and ends (−1); max concurrent = answer
- **Non-Overlapping Intervals (LC435)** — greedy: sort by end; keep interval if it doesn't overlap previous; count removals = total − kept
- **Interval List Intersections (LC986)** — two-pointer on both lists; intersection when `max(a.start, b.start) <= min(a.end, b.end)`; advance pointer with smaller end
- **My Calendar I (LC729)** — TreeMap (red-black tree); `floorKey(start)` and `ceilingKey(start)` for overlap check; O(log n) per insert
- **My Calendar III (LC732)** — difference array or segment tree with lazy propagation; max concurrent bookings
- **Skyline Problem (LC218) HARD** — sweep line: events = (x, height) for start/end; max-heap or TreeMap of active heights; emit point when max height changes
- **Employee Free Time** — merge all intervals per employee, then find gaps in merged union; sweep line or two-pointer

**Common interview variants / follow-ups:**
1. Merge intervals in a stream: use a `TreeMap<Integer, Integer>` (start → end); on insert, merge overlapping neighbors
2. Count points covered by at least K intervals: difference array +1/−1 at start/end; prefix sum; scan for values ≥ K
3. Minimum number of conference rooms for K concurrent rooms with buffer time: shift end time by buffer, then Meeting Rooms II
4. Anagram detection in large documents: rolling hash over fixed window (same as Count Anagrams)

**Key pitfalls & edge cases:**
- Longest Consecutive (LC128): must check `!set.contains(num - 1)` before starting a sequence to avoid O(n²) behavior
- Non-Overlapping: greedy sorts by END (not start); this is the activity selection problem — choosing earliest-ending activity maximizes remaining space
- Skyline: buildings ending at x should be removed before buildings starting at x are added — handle with negative heights for start events to sort starts before ends at same x
- My Calendar I: `TreeMap.floorKey` and `ceilingKey` can return null; always null-check before comparing
- 4Sum Count: HashMap approach is O(n²) time O(n²) space; don't confuse with 4Sum which is O(n³)

**Target complexity:**
- Time: O(n log n) for interval problems (sort); O(n) for Longest Consecutive; O(n²) for 4Sum Count
- Space: O(n) for all hash-based solutions

**Optimal approach summary (Java):**
For Group Anagrams, use `char[] chars = word.toCharArray(); Arrays.sort(chars); String key = new String(chars)` as the HashMap key. For Longest Consecutive, use `HashSet<Integer> set = new HashSet<>(Arrays.asList(nums))` (one-liner population). For Meeting Rooms II sweep: `int[] events = new int[2 * n]`; encode starts as positive and ends as negative timestamps; sort by time (break ties: ends before starts); scan and track running count. For Skyline, use `TreeMap<Integer, Integer> heightMap = new TreeMap<>()` storing height→count; add building heights on start, remove on end; emit if `heightMap.lastKey()` changes.

**Staff-level system-adjacent prompts:**
1. **Distributed max concurrent across shards:** Count the maximum number of concurrent active sessions across a distributed system where each shard logs its own session start/end events. Phase 1: each shard generates a difference array. Phase 2: merge difference arrays (sum at each timestamp). Phase 3: prefix scan for global maximum. Discuss clock skew — how does NTP uncertainty affect session boundary assignment, and how does a logical clock (Lamport) help?
2. **Count-Min Sketch for streaming frequency:** A Group Anagrams-style counting problem on a stream of 1B words per day requires O(n) space in a HashMap. Count-Min Sketch uses d hash functions and a w-column table (d×w array) to estimate frequency within ε·n additive error with probability 1−δ, where d = log(1/δ) and w = e/ε. Implement this in Java and discuss when it's preferable to HyperLogLog (distinct count) vs. Count-Min (frequency estimation).

---

### 11. Linked Lists

**What it is:** A sequence of nodes where each node holds a value and a pointer to the next node. Reach for linked list problems when in-place manipulation with pointer rewiring is required, or when the problem involves cycle detection, reversal, or merging of ordered sequences.

**Canonical problem set:**
- **Reverse Linked List (LC206)** — iterative: prev/curr/next triple-pointer; or recursive: reverse rest, point tail to head
- **Reverse Linked List II (LC92)** — reverse [left, right] sublist; dummy head avoids edge cases; find left-1 node, reverse k nodes
- **Linked List Cycle (LC141)** — Floyd's slow/fast pointers; if they meet, cycle exists
- **Linked List Cycle II (LC142)** — Floyd's: after meeting, reset one pointer to head; advance both one step; meeting point = cycle start; mathematical proof essential
- **Find Middle (LC876)** — slow/fast pointers; when fast reaches end, slow is at middle; for even length, returns second middle
- **Merge Two Sorted Lists (LC21)** — dummy head; compare and attach the smaller node; attach remaining
- **Merge K Sorted Lists (LC23)** — min-heap of size K with (value, node); or divide-and-conquer merge pairs; O(N log k)
- **Remove Nth Node From End (LC19)** — two pointers with N gap; when front reaches end, back is at target's predecessor; dummy head handles head deletion
- **Intersection of Two Linked Lists (LC160)** — equalize lengths: advance longer list by difference; or two-pointer restart (both traverse a+b total nodes)
- **Palindrome Linked List (LC234)** — find middle, reverse second half, compare from both ends, restore (optional)
- **Copy List with Random Pointer (LC138)** — HashMap node→copy; two passes; or interleave: insert copies next to originals, set random pointers, detach
- **LRU Cache (LC146)** — doubly linked list + HashMap; O(1) get/put; recently-used nodes move to head; evict from tail

**Common interview variants / follow-ups:**
1. Detect cycle and return cycle length — after Floyd's meeting, keep one pointer fixed and count steps until they meet again
2. Add two numbers represented as linked lists (LC2) — carry-aware addition; handle different lengths with null-padding
3. Flatten a multilevel doubly linked list (LC430) — DFS/iterative with a stack
4. What if you need O(1) space for palindrome check? — Reverse second half in-place (see LC234); restore afterward for correctness

**Key pitfalls & edge cases:**
- Dummy head pattern: `ListNode dummy = new ListNode(0); dummy.next = head;` — eliminates special-casing for head deletion
- Floyd's Cycle II proof: if cycle start is `c` steps from head and meeting point is `m` steps into the cycle, the math shows `head + c` ≡ `meetingPoint + c` (mod cycle length); so advancing head pointer to the meeting point, both advance by c, converging at cycle start
- LC19 N-th from end: use a dummy head to handle the case where n = list length (removing the head)
- LC138 interleave approach: don't forget to restore the original list by detaching the copies
- Reversing a sublist (LC92): find the node before `left`, use prev/curr/next in the reversal loop exactly `right - left` times

**Target complexity:**
- Time: O(n) for all standard problems; O(N log k) for merge K sorted
- Space: O(1) for most (two-pointer, reversal); O(n) for hashmap-based (LC138, LC146)

**Optimal approach summary (Java):**
Define `ListNode dummy = new ListNode(-1); dummy.next = head;` as a standard pattern for any problem that might modify the head. For Floyd's cycle detection: `ListNode slow = head, fast = head; while (fast != null && fast.next != null) { slow = slow.next; fast = fast.next.next; }`. For LRU Cache: `class LRUCache { Map<Integer, Node> map; Node head, tail; }` where `head` is most-recent sentinel and `tail` is least-recent sentinel; `moveToHead(node)` removes and reinserts at head. Java's `LinkedHashMap` can shortcut LRU: `new LinkedHashMap<>(capacity, 0.75f, true)` (access-order mode) with `removeEldestEntry` override.

**Staff-level system-adjacent prompts:**
1. **Off-heap storage for 100M-node cache:** A JVM heap-based linked list for 100M nodes causes GC pressure and pause times. Discuss off-heap allocation using `sun.misc.Unsafe` or `ByteBuffer.allocateDirect()`, serializing node data to raw memory, and using long addresses as "pointers." How do Caffeine and Chronicle Map approach this?
2. **Sharded LRU with Caffeine:** A single LRU cache becomes a bottleneck at 1M QPS. Describe sharding the cache by key hash across N segments (each with its own lock), the W-TinyLFU admission policy used by Caffeine (frequency sketch + window cache + main cache), and why TinyLFU outperforms pure LRU for skewed Zipfian workloads.

---

### 12. Trees + BST

**What it is:** A hierarchical data structure where each node has at most two children (binary tree) or satisfies the BST invariant (left subtree < root < right subtree). Reach for trees when data has hierarchical structure, when you need sorted order traversal, or when you need O(log n) search/insert/delete.

**Canonical problem set:**
- **Inorder / Preorder / Postorder Traversals** — recursive and iterative versions; inorder of BST is sorted; iterative inorder uses explicit stack
- **Level Order Traversal (LC102)** — BFS with queue; track level boundaries using queue size snapshot
- **Maximum Depth (LC104)** — `max(depth(left), depth(right)) + 1`; or iterative BFS counting levels
- **Diameter of Binary Tree (LC543)** — at each node, diameter through root = depth(left) + depth(right); return max via a helper that returns depth but updates global diameter
- **Symmetric Tree (LC101)** — compare left subtree mirrored against right; recursive or iterative with deque
- **Path Sum II (LC113)** — DFS backtracking; add node to path, recurse, remove on return; collect when leaf with remaining sum = 0
- **Serialize / Deserialize Binary Tree (LC297) HARD** — preorder with null markers (`#`); serialize to string, deserialize with queue of tokens
- **LCA of Binary Tree (LC236)** — if current node is either target, return it; recurse left and right; if both non-null, current is LCA
- **Binary Tree Maximum Path Sum (LC124)** — at each node, max contribution = node + max(0, left_gain, right_gain); path sum through root = node + left_gain + right_gain; update global max
- **Validate BST (LC98)** — pass (min, max) bounds; left subtree must have max < current, right must have min > current
- **Kth Smallest in BST (LC230)** — inorder traversal with counter; stop at k-th; or augmented BST with subtree sizes
- **Delete Node in BST (LC450)** — find node; if leaf delete, if one child replace, if two children find inorder successor, swap value, delete successor
- **Construct BST from Preorder + Inorder (LC105)** — inorder gives left/right split; preorder gives root; recurse with index maps for O(n) construction
- **Flatten Binary Tree to Linked List (LC114)** — postorder: flatten right, flatten left, attach left to right, attach old right to end of left

**Common interview variants / follow-ups:**
1. Morris traversal: O(1) space inorder traversal by threading right pointers to inorder successor; restore afterward
2. Vertical order traversal (LC987): BFS with (row, col) tracking; `TreeMap<col, TreeMap<row, List<val>>>` 
3. Right side view (LC199): BFS level order; take last element of each level
4. Recover BST (LC99): two nodes swapped; find them in inorder traversal (predecessor > successor anomaly)

**Key pitfalls & edge cases:**
- LC124 Max Path Sum: node value can be negative — use `Math.max(0, leftGain)` to prune negative contributions; the global max must be updated (not returned) inside the helper
- LC236 LCA: assumes both nodes exist in the tree; if one doesn't exist, the algorithm returns the existing one (may need to validate separately)
- Validate BST: using Integer.MIN/MAX as bounds fails for nodes with those exact values — use `Long.MIN_VALUE` and `Long.MAX_VALUE`, or use `null` bounds
- Serialize/Deserialize: preorder with null markers requires careful token parsing; BFS-style serialization (level order with nulls) is often more intuitive but produces longer strings
- Delete in BST: two-children case — the inorder successor is `min(rightSubtree)`; copy its value and delete that node (which has at most one child)

**Target complexity:**
- Time: O(n) for all traversal-based; O(log n) average O(n) worst for BST operations on unbalanced tree; O(n log n) for vertical order
- Space: O(h) for recursive DFS where h = height; O(n) for BFS / level-order

**Optimal approach summary (Java):**
For recursive DFS problems, the pattern is: `if (root == null) return base;` then process children and combine. For iterative inorder: `Deque<TreeNode> stack = new ArrayDeque<>(); TreeNode curr = root; while (curr != null || !stack.isEmpty()) { while (curr != null) { stack.push(curr); curr = curr.left; } curr = stack.pop(); process(curr); curr = curr.right; }`. For LCA: `if (root == null || root == p || root == q) return root; TreeNode left = lca(root.left,p,q), right = lca(root.right,p,q); return (left!=null && right!=null) ? root : (left!=null ? left : right)`. For BST validation, pass `long min = Long.MIN_VALUE, long max = Long.MAX_VALUE`.

**Staff-level system-adjacent prompts:**
1. **Self-balancing trees for sequential inserts:** Inserting sorted data into a plain BST degrades to O(n) height (a linked list). Explain AVL rotation invariants (balance factor ∈ {-1,0,1}) vs. Red-Black tree coloring invariants; why Java's `TreeMap` uses Red-Black; and when you'd choose a B-tree (disk-resident) over an in-memory balanced BST.
2. **Binary lifting for O(log n) LCA on 1B nodes:** For a static tree with 1B nodes (e.g., a corporate org chart or phylogenetic tree), precompute `ancestor[node][k] = 2^k-th ancestor` for all k up to log₂(n). Answer each LCA query in O(log n) using bit decomposition of depth difference. Discuss memory layout (column-major for cache efficiency) and parallelizing preprocessing across shards.

---

### 13. Trie

**What it is:** A tree where each path from root to a node represents a string prefix; each node has up to 26 children (for lowercase ASCII). O(m) insert and search where m = string length — impossible to beat with a hash map when prefix queries are needed.

**Canonical problem set:**
- **Implement Trie (LC208)** — `TrieNode { TrieNode[] children; boolean isEnd; }`; insert/search/startsWith each O(m)
- **Word Search II (LC212) HARD** — build trie from word list; DFS on board; at each cell, advance trie pointer; mark found words; optimization: delete leaf nodes of found words to prune
- **Add and Search Words (LC211)** — trie with wildcard `.` support; `.` triggers recursion into all children
- **Replace Words (LC648)** — build trie from dictionary; for each word in sentence, find shortest prefix in trie
- **Longest Word in Dictionary (LC720)** — build trie; BFS/DFS to find longest word where every prefix is also a word; `isEnd` must be true at each level
- **Map Sum Pairs (LC677)** — trie where each node stores sum of values of all keys with that prefix; update propagates through prefix
- **Maximum XOR of Two Numbers (LC421)** — XOR trie: insert binary representation; for each number, greedily choose opposite bit if available; O(32n)
- **Palindrome Pairs (LC336) HARD** — for each word, check if its reverse prefix/suffix exists in the trie; handles all cases including empty string
- **Stream of Characters (LC1032)** — reversed trie of words; maintain a set of active search pointers; advance on each character received
- **Count Words with a Given Prefix** — trie traversal; count all leaf nodes (words) reachable from the prefix node

**Common interview variants / follow-ups:**
1. Autocomplete system (LC642): trie where each node stores top-K frequent completions — maintained as a min-heap; O(m + K log K) per query
2. Word Break II (LC140): trie-based DFS with memoization for all valid segmentations
3. Design a phone directory with trie + DFS for all completions
4. How would you compress a trie? — Patricia trie (radix tree / DAWG) merges single-child chains into single edges; reduces space from O(alphabet × nodes) to O(keys)

**Key pitfalls & edge cases:**
- Trie node children: `TrieNode[] children = new TrieNode[26]` — initialize lazily (only create when needed), not upfront for all 26
- LC212 Word Search II: without removing found words from trie, same word found multiple times; also, must mark `node.word = null` instead of using a separate visited array for performance
- XOR Trie: always insert 32 bits (or 31 for positive ints), handling leading zeros; iterate from MSB to LSB
- LC211 wildcard: brute-force DFS through all children when `.` encountered; base case: if end of pattern, return `node.isEnd`
- Palindrome Pairs: edge cases — empty string pairs with all palindromes; words that are palindromes themselves pair with empty string

**Target complexity:**
- Time: O(m) per insert/search; O(m · 26^m) worst case for wildcard search; O(n · m) to build trie for n words of length m
- Space: O(n · m · 26) naive; O(n · m) with lazy initialization

**Optimal approach summary (Java):**
```java
class TrieNode {
    TrieNode[] children = new TrieNode[26];
    boolean isEnd;
    String word; // optional: store word at end node for Word Search II
}
```
For insert: `TrieNode node = root; for (char c : word.toCharArray()) { int i = c - 'a'; if (node.children[i] == null) node.children[i] = new TrieNode(); node = node.children[i]; } node.isEnd = true;`. For XOR Trie, use a `TrieNode[2]` children array (bits 0 and 1), insert 31 bits from MSB, and greedily choose the opposite bit during maximum XOR query. For LC212, after finding a word, set `node.word = null` and prune upward (remove childless nodes) to avoid revisiting.

**Staff-level system-adjacent prompts:**
1. **Radix tree / DAWG for memory reduction:** A production autocomplete system with 100M English words in a vanilla trie requires O(26 × nodes × 8 bytes) ≈ tens of GB. Explain how a Directed Acyclic Word Graph (DAWG) deduplicates shared suffixes (unlike a trie which only shares prefixes), reducing storage by 5-10×. Discuss the DAWG construction algorithm (incremental minimization) and its use in spell-checkers.
2. **Sharded trie for autocomplete at scale:** Google-scale autocomplete (billions of queries/day) cannot fit in one machine's trie. Describe partitioning strategies: (a) by prefix (all words starting with a-m on shard 1, n-z on shard 2), (b) by consistent hashing on words, (c) replicated hot-prefix cache with lazy cold-prefix trie. Discuss fan-out on queries, cache warming, and serving stale autocomplete gracefully during replication lag.

---

### 14. Graphs: BFS + DFS

**What it is:** BFS explores layer by layer from a source using a queue, finding shortest paths in unweighted graphs. DFS explores as deep as possible using a stack (explicit or call stack), suitable for cycle detection, connected components, and path existence. Both are O(V + E).

**Canonical problem set:**
- **Number of Islands (LC200)** — DFS/BFS flood fill; mark visited by setting to '0'; count components
- **Max Area of Island (LC695)** — DFS with area counting; update global max per component
- **Clone Graph (LC133)** — BFS/DFS with a `HashMap<Node, Node>` mapping original to clone; create clones lazily
- **Pacific Atlantic Water Flow (LC417)** — reverse BFS/DFS from both oceans simultaneously; intersection of reachable sets
- **Walls and Gates (LC286)** — multi-source BFS from all gates simultaneously; fill distance to nearest gate
- **01 Matrix (LC542)** — multi-source BFS from all 0s; BFS naturally gives shortest distance; avoid starting from 1s (harder)
- **Shortest Path in Binary Matrix (LC1091)** — BFS from (0,0); 8-directional; track visited to avoid revisits
- **Rotting Oranges (LC994)** — multi-source BFS from all rotten oranges; count fresh oranges; check if any remain after BFS
- **Course Schedule (LC207)** — detect cycle in directed graph via DFS (coloring: 0=unvisited, 1=in-stack, 2=done) or Kahn's BFS
- **Is Graph Bipartite? (LC785)** — BFS/DFS graph 2-coloring; assign alternating colors; fail if neighbor has same color
- **Word Ladder (LC127)** — BFS on word graph; neighbors = words differing by one letter; precompute neighbors or generate on-the-fly; bidirectional BFS for speedup
- **Number of Connected Components (LC323)** — BFS/DFS count of components, or DSU

**Common interview variants / follow-ups:**
1. Shortest path with obstacles elimination (LC1293): BFS with state `(x, y, k)` where k = remaining eliminations; O(m·n·k)
2. Jump Game IV (LC1345): BFS with value-indexed adjacency list; visit each value-group only once to avoid TLE
3. All paths from source to target in DAG (LC797): DFS backtracking; no visited array needed (DAG guarantees no cycles)
4. Minimum steps to reach target word with bidirectional BFS — always expand the smaller frontier

**Key pitfalls & edge cases:**
- BFS visited marking: mark nodes as visited WHEN ENQUEUED, not when dequeued, to prevent the same node being enqueued multiple times
- Multi-source BFS: enqueue ALL sources before starting; don't process one source completely then the next
- DFS cycle detection in directed graph: use a "visiting" set (currently in DFS stack) distinct from "visited" (completed); a node in "visiting" indicates a back edge (cycle)
- Word Ladder: the pattern approach (replace each character with `*`) builds an adjacency list in O(n · m · 26) — faster than comparing all word pairs O(n² · m)
- Graph given as edges vs. adjacency list: build `Map<Integer, List<Integer>> graph = new HashMap<>()` explicitly when input is edge list

**Target complexity:**
- Time: O(V + E) for both BFS and DFS; O(V · E) for Word Ladder with naive neighbor generation
- Space: O(V) for queue/stack and visited set

**Optimal approach summary (Java):**
For BFS: `Queue<int[]> queue = new LinkedList<>(); boolean[][] visited = new boolean[m][n]; queue.offer(start); visited[r][c] = true; while (!queue.isEmpty()) { int[] curr = queue.poll(); for (int[] dir : DIRS) { ... if (!visited[nr][nc]) { visited[nr][nc] = true; queue.offer(...); } } }`. For DFS cycle detection in directed graph: `int[] state = new int[n]` where 0=unvisited, 1=visiting, 2=visited; DFS returns `true` if cycle found when `state[node] == 1`. For multi-source BFS (LC542, LC994, Walls and Gates), pre-populate the queue and distance array with all sources before the BFS loop.

**Staff-level system-adjacent prompts:**
1. **Distributed BFS with Pregel:** Facebook's social graph (3B nodes, 100B edges) doesn't fit in one machine. Pregel processes graphs in supersteps: each vertex receives messages from neighbors, updates its state, and sends messages to neighbors for the next superstep. Describe how BFS maps to Pregel (message = distance), the termination condition, and how to handle vertex cuts across partitions.
2. **Dynamic connectivity with link-cut trees:** In a network topology that changes over time (link additions/deletions), checking connectivity for every query is expensive if done from scratch. Link-cut trees (splay tree-based) support `link`, `cut`, and `connected` queries each in O(log n) amortized. When would you choose this over Union-Find with rollback?

---

### 15. Graphs: Topological Sort

**What it is:** A linear ordering of vertices in a Directed Acyclic Graph (DAG) such that for every directed edge u→v, u appears before v. Two algorithms: Kahn's BFS (process nodes with in-degree 0) or DFS postorder (append to result after all descendants are processed, then reverse). Cycle detection is a free byproduct.

**Canonical problem set:**
- **Course Schedule (LC207)** — detect cycle; if topological sort visits all n nodes, no cycle exists; use Kahn's or DFS coloring
- **Course Schedule II (LC210)** — return a valid ordering; Kahn's naturally produces it; DFS produces reverse postorder
- **Course Schedule III (LC630)** — greedy with max-heap: schedule courses in order of deadline; if adding exceeds deadline, replace the longest-duration course in the heap
- **Alien Dictionary (LC269) HARD** — build DAG from adjacent word pairs (compare character by character to find first difference); topological sort to get character order; detect cycle = invalid input; shorter word prefix of longer = invalid
- **Sequence Reconstruction (LC444)** — verify that `org` is the unique topological sort of the graph built from `seqs`; unique sort exists iff at each step exactly one node has in-degree 0
- **Minimum Height Trees (LC310)** — find centroids: repeatedly remove leaf nodes (degree 1); remaining nodes are MHT roots; topological-sort-like peeling
- **Parallel Courses (LC1136)** — find the longest path in the DAG (critical path); = number of semesters needed
- **Build Order (CTCI)** — classical dependency resolution via Kahn's; report error if cycle detected

**Common interview variants / follow-ups:**
1. Task scheduling with dependencies and k processors — critical path method (CPM): longest path in DAG = minimum project duration
2. Reconstruct itinerary (LC332) — Eulerian path in directed graph; Hierholzer's algorithm with topo sort-like DFS; lexicographic smallest requires priority queue adjacency
3. Given a topological sort, verify it's the unique valid sort — process in order, check that each node only depends on nodes already processed
4. Topological sort on disconnected DAG — Kahn's handles naturally; DFS must iterate over all unvisited nodes

**Key pitfalls & edge cases:**
- Cycle detection: in Kahn's, if the topological sort result contains fewer than n nodes, a cycle exists
- Alien Dictionary: when word A is a prefix of word B but appears after B in the input, the dictionary is invalid (immediately return "")
- LC444 Unique Reconstruction: need both that the sort is valid AND unique (queue never has more than one node at any step)
- DFS postorder: result needs to be reversed at the end; or prepend to front of result list
- Self-loops: a node with an edge to itself has in-degree ≥ 1 initially; Kahn's correctly identifies it as a cycle participant

**Target complexity:**
- Time: O(V + E) for both Kahn's and DFS topological sort
- Space: O(V + E) for graph representation + O(V) for result/queue/stack

**Optimal approach summary (Java):**
For Kahn's BFS: build `int[] inDegree = new int[n]` and `List<List<Integer>> adj = new ArrayList<>();`; enqueue all nodes with `inDegree[i] == 0`; process queue, decrementing in-degree of neighbors, enqueuing those that reach 0; collect processed nodes. For DFS: `int[] state = new int[n]` (0/1/2); DFS from each unvisited node; append to result list when state transitions to 2 (visited); reverse at end. For Alien Dictionary, compare adjacent words in the word list to extract character ordering edges, then run topological sort. Check `result.size() == numChars` for cycle detection.

**Staff-level system-adjacent prompts:**
1. **Incremental cycle detection on edge addition:** In a build system where dependencies are added dynamically (e.g., a Makefile), checking for cycles on every edge addition with a full topological sort is O(V+E) per addition. Explain the algorithm for incremental cycle detection: maintain a topological order and update it when a new edge is added; cycle detected when forward traversal from the destination reaches the source in O(k log k) where k is the affected segment.
2. **DAG-level parallelism for microservice deployment:** In a microservice deployment system, services have startup dependencies (A must start before B). The deployment DAG's critical path determines the minimum total deployment time. Describe how to compute the critical path using dynamic programming on the topological order, schedule services onto parallel deploy workers using list scheduling, and estimate speedup via Amdahl's Law.

---

### 16. Graphs: Shortest Path

**What it is:** Finding the minimum cost path between nodes in a weighted graph. Dijkstra's O((V+E) log V) works for non-negative weights; Bellman-Ford O(VE) handles negative weights and detects negative cycles; multi-source BFS handles unweighted graphs in O(V+E).

**Canonical problem set:**
- **Network Delay Time (LC743)** — single-source shortest path from node k; Dijkstra's; answer = max of all distances (if any unreachable, return -1)
- **Cheapest Flights Within K Stops (LC787)** — Bellman-Ford variant: relax edges exactly K+1 times; use a copy of distances array at each iteration to prevent chaining within one round
- **Path with Minimum Effort (LC1631)** — Dijkstra's on a grid where edge weight = abs(height difference); minimize the maximum edge weight on the path
- **Swim in Rising Water (LC778)** — Dijkstra's where "distance" = max elevation visited; or binary search + BFS/DFS
- **Find City with Smallest Number of Neighbors (LC1334)** — Floyd-Warshall all-pairs shortest paths; O(n³) fine for n ≤ 100
- **Shortest Path with Obstacles Elimination (LC1293)** — BFS with state (r, c, k); effectively shortest path in a 3D graph
- **Word Ladder (LC127)** — BFS (unweighted); bidirectional BFS halves the search space in practice

**Common interview variants / follow-ups:**
1. K-th shortest path: Yen's algorithm for simple K-shortest paths; or priority queue with repeated state visits (allowing non-simple paths)
2. All-pairs shortest paths for dense graphs: Floyd-Warshall O(n³); check `dist[i][k] + dist[k][j] < dist[i][j]` for each intermediate node k
3. Reconstruct the shortest path: maintain a `prev[]` array in Dijkstra's; backtrack from destination
4. Dijkstra's with a decrease-key operation: Java's `PriorityQueue` doesn't support decrease-key; instead, push duplicate (newDist, node) and skip if `dist[node] < polledDist`

**Key pitfalls & edge cases:**
- Dijkstra's with negative edges: produces incorrect results — use Bellman-Ford or reweight with Johnson's algorithm
- LC787 Cheapest Flights: using standard Dijkstra violates the K-stop constraint; Bellman-Ford with exactly K+1 iterations is safer; must copy dist array before each iteration
- Floyd-Warshall: initialize `dist[i][i] = 0`; detect negative cycles if `dist[i][i] < 0` after all iterations
- Grid Dijkstra: states are `(row, col)` not just node indices; use `int[][] dist = new int[m][n]` initialized to `Integer.MAX_VALUE`
- Bidirectional BFS termination: stop when a node is dequeued that the other BFS has already visited; answer = min over all nodes of (distA[node] + distB[node])

**Target complexity:**
- Time: O((V+E) log V) Dijkstra's with binary heap; O(VE) Bellman-Ford; O(V³) Floyd-Warshall; O(V+E) BFS
- Space: O(V+E) for adjacency list; O(V²) Floyd-Warshall distance matrix

**Optimal approach summary (Java):**
For Dijkstra's: `int[] dist = new int[n]; Arrays.fill(dist, Integer.MAX_VALUE); dist[src] = 0; PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[0]-b[0]); pq.offer(new int[]{0, src}); while (!pq.isEmpty()) { int[] curr = pq.poll(); if (curr[0] > dist[curr[1]]) continue; for (int[] neighbor : graph[curr[1]]) { if (dist[curr[1]] + neighbor[1] < dist[neighbor[0]]) { dist[neighbor[0]] = dist[curr[1]] + neighbor[1]; pq.offer(new int[]{dist[neighbor[0]], neighbor[0]}); } } }`. For Bellman-Ford (LC787): initialize `int[] dist = new int[n+1]; Arrays.fill(dist, Integer.MAX_VALUE); dist[src] = 0;` then loop K+1 times: `int[] temp = dist.clone(); for (int[] flight : flights) { if (dist[u] != MAX && dist[u] + cost < temp[v]) temp[v] = dist[u]+cost; } dist = temp;`.

**Staff-level system-adjacent prompts:**
1. **Contraction hierarchies for real-time routing:** Google Maps computes shortest paths on road networks with 1B+ edges in under 10ms. Contraction hierarchies preprocess the graph by iteratively contracting (removing) unimportant nodes and adding shortcut edges; queries then use a bidirectional Dijkstra on the contracted graph. Discuss the preprocessing-query tradeoff, how node importance is defined, and why this technique can't be used when edge weights change frequently (live traffic).
2. **Johnson's algorithm for negative weights at scale:** Bellman-Ford is O(VE) = too slow for all-pairs shortest paths in large graphs with negative weights. Johnson's algorithm reweights all edges to non-negative using a single Bellman-Ford pass from a virtual node, then runs Dijkstra from each vertex. Explain the reweighting correctness proof and when you'd use this in a financial network (e.g., currency arbitrage detection) vs. Floyd-Warshall.

---

### 17. Graphs: MST (Minimum Spanning Tree)

**What it is:** A spanning tree of a weighted undirected graph with minimum total edge weight. Kruskal's sorts edges and adds them if they don't form a cycle (uses DSU); Prim's grows the tree greedily from a starting node using a min-heap. Both are O(E log E) / O(E log V).

**Canonical problem set:**
- **Min Cost to Connect All Points (LC1584)** — complete graph on 2D points; Manhattan distance edges; Prim's is better (dense graph: O(n²) with array-based Prim's vs O(n² log n) with heap)
- **Number of Operations to Make Network Connected (LC1319)** — count edges; if fewer than n-1 edges, return -1; count connected components via DSU; answer = components - 1
- **Remove Max Number of Edges (LC1579)** — process type-3 edges (both players) first; then type-1 (Alice only) and type-2 (Bob only); use separate DSU for each; maximize removed edges while keeping both spanning
- **Water Distribution in a Village (LC1168)** — add virtual node 0 connected to each house with well cost; MST of augmented graph = optimal strategy
- **Standard MST (Kruskal's / Prim's)** — template for all MST problems; Kruskal's simpler to code, Prim's better for dense graphs
- **Second Minimum Spanning Tree** — find MST; then for each edge NOT in MST, temporarily add it (creating a cycle), remove the heaviest edge in the cycle (find with DFS/LCA), compute new total; minimum over all such swaps
- **Borůvka's Algorithm** — each component selects its cheapest outgoing edge simultaneously; repeat until one component; O(E log V); parallelizable

**Common interview variants / follow-ups:**
1. Dynamic MST: edge weight changes — recompute locally (is the changed edge still in MST? does a better edge now exist?)
2. Steiner tree (generalization of MST): connect a subset of K nodes with minimum total edge weight; NP-hard in general; DP on subsets for small K
3. Maximum spanning tree: negate all weights and run standard MST algorithm
4. MST as approximation: 2-approximation for metric TSP; MST → Euler tour → shortcut repeated vertices (triangle inequality)

**Key pitfalls & edge cases:**
- Kruskal's requires DSU with path compression + union by rank for O(α(n)) per operation; naïve union is O(n) per operation
- Prim's with array for dense graphs: O(V²) time; for sparse graphs, use a min-heap: O(E log V)
- LC1584: don't build the full adjacency list (n² edges) — use Prim's with an array tracking minimum cost to connect each node, updating in O(n) per step: O(n²) total
- Disconnected graph: MST doesn't exist for disconnected graph; detect with DSU (components > 1 after Kruskal's)
- LC1579: type-3 edges must be processed before type-1 and type-2 to maximize removal

**Target complexity:**
- Time: O(E log E) Kruskal's; O(E log V) Prim's with binary heap; O(n²) Prim's with array for complete graph
- Space: O(V + E) for graph; O(V) for DSU

**Optimal approach summary (Java):**
Kruskal's template: `int[][] edges` sorted by weight; DSU `int[] parent = new int[n], rank = new int[n]`; `for (int[] e : edges) { if (find(e[0]) != find(e[1])) { union(e[0], e[1]); totalCost += e[2]; mstEdges++; } } return mstEdges == n-1 ? totalCost : -1`. For Prim's on LC1584 (dense): `int[] minCost = new int[n]; Arrays.fill(minCost, Integer.MAX_VALUE); boolean[] inMST = new boolean[n]; minCost[0] = 0; for (int i = 0; i < n; i++) { find node u with min minCost not in MST; add u to MST; for each node v not in MST update minCost[v] }`. This O(n²) Prim's avoids building the O(n²) edge list.

**Staff-level system-adjacent prompts:**
1. **Spatial indexing for 10k data center complete graph:** When finding the MST of 10,000 data centers (complete graph = 50M edges), building the full edge list is expensive. Use a k-d tree or locality-sensitive hashing to find the k nearest neighbors per node (approximating sparse Euclidean MST). The Euclidean MST edge set is contained within the Delaunay triangulation (O(n log n) to compute), reducing the edge count to O(n) before running Kruskal's.
2. **MST as 2-approximation foundation for TSP:** The Traveling Salesman Problem (TSP) on metric spaces has a 2-approximation: compute MST, perform an Euler tour (treating MST as multigraph), shortcut repeated vertices using triangle inequality to get a Hamiltonian cycle. Explain why this is a 2-approximation (tour ≤ 2 × OPT), and how Christofides' algorithm improves this to 1.5× by adding minimum-weight perfect matching on odd-degree vertices.

---

### 18. Union-Find (DSU)

**What it is:** A data structure that maintains a partition of elements into disjoint sets, supporting near-O(1) `find` (with path compression) and `union` (with union by rank/size). Reach for it when processing connectivity queries online — as edges arrive — without needing to enumerate paths.

**Canonical problem set:**
- **Number of Connected Components (LC323)** — union all edges; count distinct roots at end
- **Graph Valid Tree (LC261)** — tree iff exactly n-1 edges AND connected (no cycle); union returns false if same component
- **Redundant Connection (LC684)** — first edge whose endpoints are already connected is the redundant edge
- **Accounts Merge (LC721)** — union all emails of the same account; group by root; sort emails in each group
- **Smallest String with Swaps (LC1202)** — union swappable indices; sort characters within each component
- **Number of Provinces (LC547)** — union all edges; count distinct roots
- **Making a Large Island (LC827) HARD** — label each island with an ID; precompute island sizes; for each 0-cell, sum unique neighboring island sizes + 1; answer = max
- **Satisfiability of Equality Equations (LC990)** — process `==` equations first (union); then check `!=` equations (find must return different roots)
- **Remove Max Edges to Keep Graph Traversable (LC1579)** — see MST pattern above; use two DSUs

**Common interview variants / follow-ups:**
1. Online connectivity queries with deletions — DSU doesn't support deletion; use offline reverse processing (process deletions as additions in reverse time order)
2. DSU with rollback (for undo operations): use union by rank WITHOUT path compression; maintain an undo stack of (node, old_parent, old_rank) pairs
3. DSU on tree (Small-to-Large merging): process tree queries by merging smaller sets into larger; O(n log n) total
4. Weighted DSU: maintain a `weight[]` array where `weight[i]` = weight relative to parent; update during path compression; use for problems like "evaluate division" (LC399)

**Key pitfalls & edge cases:**
- Path compression must update `parent[x]` to `find(parent[x])`; recursive path compression can stack overflow for large n — use iterative two-pass or path halving
- Union by rank: always attach the smaller-rank tree under the larger-rank root; only increment rank when two equal-rank trees merge
- LC721 Accounts Merge: emails (strings) need a `HashMap<String, Integer>` to assign integer IDs before using DSU; alternatively, use a `HashMap<String, String>` DSU
- Detecting cycle in undirected graph: if both nodes of an edge have the same root, adding the edge creates a cycle
- LC827: use a `Set<Integer>` to avoid counting the same island twice when a 0-cell is adjacent to the same island from multiple sides

**Target complexity:**
- Time: O(α(n)) ≈ O(1) amortized per find/union with path compression + union by rank; O(log n) without path compression (needed for rollback)
- Space: O(n) for parent and rank arrays

**Optimal approach summary (Java):**
```java
int[] parent, rank;
void init(int n) { parent = new int[n]; rank = new int[n]; for (int i = 0; i < n; i++) parent[i] = i; }
int find(int x) { if (parent[x] != x) parent[x] = find(parent[x]); return parent[x]; }
boolean union(int x, int y) {
    int px = find(x), py = find(y);
    if (px == py) return false; // already connected
    if (rank[px] < rank[py]) { int t = px; px = py; py = t; }
    parent[py] = px;
    if (rank[px] == rank[py]) rank[px]++;
    return true;
}
```
For LC721 Accounts Merge: map each email to an integer ID, union all emails of the same account, then group emails by their root ID using a `Map<Integer, TreeSet<String>>` (TreeSet for sorted order), and prepend the account name.

**Staff-level system-adjacent prompts:**
1. **CAS-based lock-free DSU:** In a concurrent environment (multi-threaded graph processing), a lock-based DSU creates contention. Describe a lock-free DSU using Compare-And-Swap (CAS): `find` uses path compression with CAS to update parent pointers; `union` uses CAS-based compare-and-swap loops. Discuss ABA problems, memory ordering guarantees needed (Java `AtomicIntegerArray`), and whether lock-free DSU is faster than a striped-lock approach in practice.
2. **DSU without path compression for rollback support:** Some problems require undoing union operations (e.g., offline deletion queries processed in reverse, or backtracking in constraint satisfaction). Explain why path compression breaks rollback (it modifies ancestor chains), why union by rank alone gives O(log n) find, and how to implement an undo stack that stores `(node, old_parent, old_rank)` for each union to support O(log n) rollback.

---

### 19. Bit Manipulation

**What it is:** Direct manipulation of integer bits using bitwise operators (AND `&`, OR `|`, XOR `^`, NOT `~`, left shift `<<`, right shift `>>`). Reach for it for space-efficient set representations, XOR cancellation tricks, extracting/setting individual bits, and bitmask DP over subsets.

**Canonical problem set:**
- **Single Number (LC136)** — XOR all elements; pairs cancel to 0; isolated element remains
- **Single Number II (LC137)** — each bit appears 3k or 3k+1 times; count bit 1s modulo 3; or state machine with `ones` and `twos`
- **Single Number III (LC260)** — XOR gives `a^b`; find any set bit; partition into two groups; XOR within each group
- **Number of 1 Bits (LC191)** — Brian Kernighan: `n &= (n-1)` clears lowest set bit; count iterations; or `Integer.bitCount(n)` in Java
- **Counting Bits (LC338)** — `dp[i] = dp[i >> 1] + (i & 1)`; DP using the fact that `i >> 1` has one fewer bit
- **Power of Two (LC231)** — `n > 0 && (n & (n-1)) == 0`; powers of two have exactly one set bit
- **Reverse Bits (LC190)** — process 32 bits right-to-left; `result = (result << 1) | (n & 1); n >>= 1`
- **Missing Number (LC268)** — XOR [0..n] with array; or sum formula `n*(n+1)/2 - sum(arr)`
- **Find the Duplicate Number (LC287)** — Floyd's cycle detection on value-as-index mapping; or binary search on value range
- **Subsets via Bitmask (LC78)** — iterate `mask` from 0 to `1<<n - 1`; for each mask, iterate bits to collect elements
- **Maximum XOR of Two Numbers (LC421)** — prefix trie of binary representations; greedy bit-by-bit; or bit-by-bit Gaussian elimination
- **Bitmask DP (TSP/Hamiltonian Path skeleton)** — `dp[mask][i]` = min cost to visit all nodes in `mask` ending at node `i`; transitions add one unvisited node to mask; O(2^n × n²)

**Common interview variants / follow-ups:**
1. Find two missing numbers: XOR gives a^b, find differing bit, partition, XOR within partitions
2. Swap without temp: `a ^= b; b ^= a; a ^= b` — classic but breaks on `a == b` (same memory location); avoid in production
3. Check if two integers have opposite signs: `(x ^ y) < 0`
4. Bitmask DP subset enumeration: `for (int sub = mask; sub > 0; sub = (sub-1) & mask)` enumerates all subsets of `mask` in O(3^n) total

**Key pitfalls & edge cases:**
- Java integer overflow: `1 << 31` overflows to `Integer.MIN_VALUE`; use `1L << 31` for long; for 32-bit problems, mask with `0xFFFFFFFFL` when using `long`
- Signed vs. unsigned right shift: `>>` is arithmetic (sign-extending); `>>>` is logical (zero-filling); for bit manipulation problems, usually want `>>>`
- LC136 XOR: only works if exactly one element appears an odd number of times; the pair-cancellation property requires pairs to appear an EVEN number of times
- Single Number II state machine: the `ones` and `twos` update order matters: `twos = (twos | (ones & num)); ones = ones ^ num; ... ` — get the exact formula right
- Bitmask DP: nodes must be indexed 0..n-1; `mask |= (1 << node)` to add node to mask; `(mask >> node) & 1` to check membership

**Target complexity:**
- Time: O(1) for single-integer bit operations; O(n) for array-based; O(2^n × n) for bitmask DP
- Space: O(1) for bit tricks; O(2^n × n) for bitmask DP

**Optimal approach summary (Java):**
For XOR cancellation: `int xor = 0; for (int n : nums) xor ^= n;`. For Brian Kernighan bit count: `int count = 0; while (n != 0) { n &= (n-1); count++; }`. For bitmask DP: `int[][] dp = new int[1 << n][n]; for (int[] row : dp) Arrays.fill(row, Integer.MAX_VALUE / 2); dp[1][0] = 0; for (int mask = 1; mask < (1 << n); mask++) for (int u = 0; u < n; u++) { if ((mask & (1 << u)) == 0 || dp[mask][u] == INF) continue; for (int v = 0; v < n; v++) { if ((mask & (1 << v)) != 0) continue; int newMask = mask | (1 << v); dp[newMask][v] = Math.min(dp[newMask][v], dp[mask][u] + dist[u][v]); } }`. In Java, always use `Integer.bitCount(n)` for production code; use manual bit operations only when the problem requires specific bit-level logic.

**Staff-level system-adjacent prompts:**
1. **Bloom filter bit-math for 1B items at 1% FPR:** A Bloom filter for 1B items at 1% false positive rate requires approximately 9.6 bits per item (total ~1.2GB) and k ≈ 6.6 hash functions. Implement a Bloom filter using a `BitSet` in Java, explain the formula `m = -n*ln(p) / (ln(2))²` and `k = (m/n)*ln(2)`, and discuss why Bloom filters are used in HBase (avoid disk reads for absent keys) and in distributed systems for set membership without network round-trips.
2. **O(1) permission checking via bitmask:** In an authorization system with up to 64 permissions, represent a user's permission set as a `long` bitmask. Checking if a user has all required permissions: `(userPerms & requiredPerms) == requiredPerms`. Design a role-based access control system where roles are bitmasks, users inherit role bitmasks via `OR`, and permission checks are single `AND` operations — discuss scalability to millions of permission checks per second without database queries.

---

### 20. String Algorithms + Parsing

**What it is:** Algorithms for pattern matching (KMP, Rabin-Karp), string parsing (expression evaluation with two stacks), and string DP (edit distance, word break). Reach for KMP when you need O(n+m) pattern matching with no backtracking; use rolling hash for multi-pattern or 2D matching; use two-stack evaluation for expression parsing.

**Canonical problem set:**
- **strStr / Find Needle in Haystack (LC28)** — KMP: build failure function (LPS array); O(n+m) matching without backtracking
- **Repeated String Match (LC686)** — append copies until length ≥ len(b) + len(a); check at most 3 copies; use KMP for the search
- **Shortest Palindrome (LC214) HARD** — KMP on `s + '#' + reverse(s)`; LPS value at last position gives length of longest palindromic prefix; prepend the remaining suffix reversed
- **Find All Anagrams (LC438)** — fixed sliding window with char frequency arrays; compare with target frequency; O(n)
- **Decode Ways (LC91)** — DP: `dp[i]` = number of ways to decode `s[0..i-1]`; single digit (1-9) and two-digit (10-26) transitions
- **Basic Calculator I (LC224)** — stack for parentheses; handle `+`, `-`, `(`, `)`; sign tracking with a sign stack
- **Basic Calculator II (LC227)** — two-stack or one-stack: evaluate `*` and `/` immediately; push `+` and `-` results onto stack for deferred evaluation
- **Evaluate Reverse Polish Notation (LC150)** — single stack; push numbers, pop two for each operator
- **Longest Valid Parentheses (LC32)** — stack of indices; push `-1` as sentinel; when `)` encountered, pop; if stack empty push index, else update max = `i - stack.peek()`
- **Regular Expression Matching (LC10) HARD** — DP: `dp[i][j]` = whether `s[0..i-1]` matches `p[0..j-1]`; `*` case handles 0 or more of preceding character
- **Word Break (LC139)** — DP: `dp[i]` = whether `s[0..i-1]` can be segmented; for each `i`, check all `j < i` where `s[j..i-1]` is in the dictionary; use trie for O(n²) → better constant
- **Minimum Window Substring (LC76)** — see Sliding Window pattern; listed here as a string algorithm classic

**Common interview variants / follow-ups:**
1. Rabin-Karp rolling hash for multi-pattern matching: hash all patterns, compute rolling hash of text; O(n + m) average with O(nm) worst case for hash collisions
2. Z-algorithm: O(n+m) pattern matching alternative to KMP; Z[i] = length of longest string starting at i that is also a prefix of the string
3. Edit distance (LC72): classic 2D DP; `dp[i][j] = dp[i-1][j-1]` if `s[i]==t[j]`, else `1 + min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])`
4. Expression parsing with variables / function calls: extend two-stack to handle function names and argument lists; tokenization as a separate phase

**Key pitfalls & edge cases:**
- KMP failure function: compute LPS (Longest Proper Prefix which is also Suffix) for the pattern; `lps[0] = 0` always; iterate with two pointers `len` and `i`
- LC214 Shortest Palindrome: the `#` separator in `s + '#' + rev(s)` is essential to prevent the KMP from crossing the boundary between s and rev(s)
- Decode Ways (LC91): `s[0] == '0'` → 0 ways immediately; `dp[0] = 1` (empty string = 1 way); two-digit check: `10 ≤ val ≤ 26`; beware of `"06"` which is NOT a valid two-digit decoding
- Basic Calculator II: when scanning `+` or `-`, push the evaluated previous term (using `sign * operand`) onto stack, don't combine yet; at end, sum the stack
- Longest Valid Parentheses: the stack-sentinel approach is cleaner than the DP approach; always initialize stack with `[-1]`

**Target complexity:**
- Time: O(n+m) KMP; O(n+m) Rabin-Karp average; O(nm) DP for regex/edit-distance; O(n²) Word Break naive
- Space: O(m) for KMP failure function; O(nm) for DP tables; O(n) for expression evaluation stacks

**Optimal approach summary (Java):**
KMP failure function: `int[] lps = new int[m]; int len = 0, i = 1; while (i < m) { if (p[i] == p[len]) { lps[i++] = ++len; } else if (len != 0) { len = lps[len-1]; } else { lps[i++] = 0; } }`. KMP search: two pointers `i` (text) and `j` (pattern); advance both on match; on mismatch with `j > 0`, `j = lps[j-1]`; record match when `j == m`. For Basic Calculator II: `int result = 0, operand = 0, sign = 1; Deque<Integer> stack = new ArrayDeque<>();` — when operator or end reached, push `sign * operand` to stack, reset operand; `*` and `/` pop from stack, compute, push result. For Decode Ways: `int[] dp = new int[n+1]; dp[0] = 1; dp[1] = s.charAt(0) == '0' ? 0 : 1; for (int i = 2; i <= n; i++) { int one = s.charAt(i-1) - '0'; int two = Integer.parseInt(s.substring(i-2, i)); if (one != 0) dp[i] += dp[i-1]; if (two >= 10 && two <= 26) dp[i] += dp[i-2]; }`.

**Staff-level system-adjacent prompts:**
1. **Parallel KMP on chunked text with boundary stitching:** To search for a pattern of length m in a 1TB text file distributed across 1000 chunks, each worker runs KMP on its chunk. The edge case: matches spanning chunk boundaries are missed. Solve by overlapping chunks by `m-1` bytes; the overlapping region of each chunk is re-processed, and matches starting in the overlap count to the later chunk. Discuss how this maps to a MapReduce job with adjustable overlap parameter.
2. **Spreadsheet formula DAG dependency parallelization:** A spreadsheet with 1M cells has formula dependencies forming a DAG. Parsing each formula (using two-stack expression evaluation) identifies which cells it depends on. Topological sort of the dependency DAG gives the evaluation order; cells with no dependencies can be evaluated in parallel. Discuss cycle detection (circular references), incremental recomputation (only re-evaluate cells downstream of changed cells), and how Google Sheets achieves near-real-time collaborative updates.

---

### 21. Design-ish DSA: LRU / LFU / Streams

**What it is:** Data structures that bridge pure algorithms and system design — cache eviction policies (LRU, LFU), streaming statistics (median, top-K, reservoir sampling), and hybrid data structures. These problems are often asked at Staff+ level as they require combining multiple classic structures and discussing production-scale implications.

**Canonical problem set:**
- **LRU Cache (LC146)** — doubly linked list (recency order) + HashMap (O(1) lookup); get moves node to front; put evicts from tail if at capacity; Java shortcut: `LinkedHashMap` with `accessOrder=true` and `removeEldestEntry` override
- **LFU Cache (LC460) HARD** — three components: (1) `Map<key, value>`, (2) `Map<key, frequency>`, (3) `Map<frequency, LinkedHashSet<key>>`; maintain `minFreq`; on access increment freq, move key to next freq bucket; on evict, remove LRU from `freqToKeys[minFreq]`
- **Find Median from Data Stream (LC295)** — two heaps: max-heap `lower` (size ≥ min-heap `upper`); after each insert, rebalance sizes; median = `lower.peek()` or average of two tops
- **Sliding Window Median (LC480)** — two heaps + lazy deletion; when window slides out, add to a "dead" set; skip dead elements when peeking; remove from heaps lazily only when they become the top
- **Top K Frequent in a Stream** — min-heap of size K on (frequency, element); HashMap for frequency; O(n log k) per stream
- **Reservoir Sampling (LC382)** — select k-th element with probability 1/k; replace with probability 1/i at step i; uniform distribution guaranteed
- **Weighted Reservoir Sampling (LC398)** — each element has a weight; replace with probability `weight / cumulative_weight`; or Efraimidis-Spirakis: assign key `random^(1/w)`, keep K largest keys
- **Design Twitter (LC355)** — 10 most recent tweets from followed users; maintain a heap over each user's tweet list; `followUser` / `unfollowUser` modify a HashMap of follow sets; get feed = K-way merge of tweet lists
- **Stock Price Fluctuation (LC2034)** — `TreeMap<price, count>` for O(log n) min/max; `HashMap<timestamp, price>` for O(1) update; on update, decrement old price count in TreeMap

**Common interview variants / follow-ups:**
1. LRU with TTL: add expiration timestamp to each node; on get, check expiry; lazy expiration vs. eager expiration with a timer wheel
2. LFU with O(1) all operations: the `Map<freq, LinkedHashSet>` approach achieves O(1) amortized by maintaining a doubly linked list of frequency buckets
3. Random pick with weight: prefix sum array + binary search on cumulative weights; O(log n) per pick
4. Count distinct elements in a sliding window: O(n) with HashMap tracking counts; decrement on slide-out, remove when count reaches 0

**Key pitfalls & edge cases:**
- LRU `LinkedHashMap` shortcut: must pass `true` as the third constructor argument for access-order; `removeEldestEntry` receives the eldest (LRU) entry; return `size() > capacity`
- LFU `minFreq` update: when existing key is updated, `minFreq` might need to change; if the updated key was the only key at `minFreq`, increment `minFreq`; when a new key is inserted, `minFreq` resets to 1
- Two-heap median: use `Integer.compare(a, b)` not `(a - b)` for comparator to avoid overflow; manage rebalancing after every operation
- Sliding Window Median: lazy deletion requires careful handling — when checking heap tops, skip all deleted elements; the deleted set must store counts (not just membership) for elements that appear multiple times
- Reservoir sampling: in Java, `Math.random()` returns `[0, 1)`; use `random.nextInt(i + 1)` for uniform integer selection; ensure the first element is always selected (base case)

**Target complexity:**
- Time: O(1) for LRU get/put; O(1) amortized for LFU; O(log n) for heap median; O(1) for reservoir sampling
- Space: O(capacity) for caches; O(n) for two-heap median (unbounded stream); O(k) for reservoir

**Optimal approach summary (Java):**
LRU with `LinkedHashMap`: `class LRUCache extends LinkedHashMap<Integer,Integer> { int cap; LRUCache(int cap) { super(cap, 0.75f, true); this.cap = cap; } public int get(int k) { return super.getOrDefault(k, -1); } public void put(int k, int v) { super.put(k,v); } @Override protected boolean removeEldestEntry(Map.Entry<Integer,Integer> e) { return size() > cap; } }`. For LFU: `Map<Integer, Integer> keyVal, keyFreq; Map<Integer, LinkedHashSet<Integer>> freqKeys; int minFreq, capacity;` — on get: increment freq, move from `freqKeys[f]` to `freqKeys[f+1]`, update `minFreq` if `freqKeys[minFreq]` empty. On put: if capacity exceeded, evict first element of `freqKeys.get(minFreq)`, then insert with freq=1 and set `minFreq=1`. For two-heap median: always insert into `lowerHalf` first, then push `lowerHalf.poll()` to `upperHalf`; if `upperHalf.size() > lowerHalf.size()`, push back to `lowerHalf`. This ensures `lowerHalf.size() >= upperHalf.size()` always.

**Staff-level system-adjacent prompts:**
1. **Sharded LRU + Caffeine W-TinyLFU at 1M QPS:** A single Java LRU cache using `LinkedHashMap` with `synchronized` methods becomes a bottleneck at 1M QPS due to lock contention. Caffeine uses a lock-striped approach with 16 stripes per segment and an asynchronous buffer (ring buffer) for write ordering, achieving near-zero contention. The W-TinyLFU admission policy uses a Count-Min Sketch to estimate frequency without per-entry overhead, combining a small LRU window cache (1% capacity) with a large SLRU main cache (99%) to resist burst-then-disappear workloads that defeat pure LRU. Describe how you'd tune Caffeine for a read-heavy product catalog cache with Zipfian access patterns.
2. **Distributed reservoir sampling with Vitter's Algorithm Z:** Standard reservoir sampling requires seeing all n elements before the sample is finalized. For a continuous stream across 1000 shards, each shard maintains its own reservoir of size k; to merge reservoirs, use the weighted reservoir sampling property: the final reservoir is a weighted sample where each element's weight is 1 (uniform). Vitter's Algorithm Z generates skip distances directly (without examining every element), achieving O(n/k) expected time by computing how many elements to skip before the next replacement. Discuss how this enables sampling 1B events/day on 10 nodes with O(k) memory each.

---

*End of curriculum — 21 patterns covering the full MANG Staff-level DSA interview landscape.*
