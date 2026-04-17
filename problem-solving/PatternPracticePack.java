import java.util.*;

/**
 * ============================================================
 * PatternPracticePack — MANG Staff-level DSA Interview Prep
 * Target: 2026  |  21 Patterns  |  Fully compilable w/ javac
 * ============================================================
 *
 * ╔══════════════════════════════════════════════════════════╗
 * ║            REUSABLE PATTERN TEMPLATES                    ║
 * ╚══════════════════════════════════════════════════════════╝
 *
 * ──────────────────────────────────────────────────────────
 * TEMPLATE 1 — SLIDING WINDOW (variable size)
 * ──────────────────────────────────────────────────────────
 * int left = 0, ans = 0;
 * // expand right pointer across the array
 * for (int right = 0; right < n; right++) {
 *     // 1. add nums[right] into the window
 *     // 2. while window violates constraint:
 *     while (<invalid condition>) {
 *         // remove nums[left] from window
 *         left++;
 *     }
 *     // 3. update answer with valid window [left..right]
 *     ans = Math.max(ans, right - left + 1);
 * }
 *
 * ──────────────────────────────────────────────────────────
 * TEMPLATE 2 — BINARY SEARCH (generalized on answer)
 * ──────────────────────────────────────────────────────────
 * int lo = minPossible, hi = maxPossible;
 * while (lo < hi) {
 *     int mid = lo + (hi - lo) / 2;  // avoids overflow
 *     if (feasible(mid)) {
 *         hi = mid;           // try smaller (find minimum feasible)
 *     } else {
 *         lo = mid + 1;
 *     }
 * }
 * // lo == hi == answer
 *
 * ──────────────────────────────────────────────────────────
 * TEMPLATE 3 — BFS (grid)
 * ──────────────────────────────────────────────────────────
 * int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
 * Queue<int[]> q = new ArrayDeque<>();
 * boolean[][] visited = new boolean[rows][cols];
 * q.offer(new int[]{startR, startC});
 * visited[startR][startC] = true;
 * while (!q.isEmpty()) {
 *     int[] cur = q.poll();
 *     for (int[] d : dirs) {
 *         int nr = cur[0] + d[0], nc = cur[1] + d[1];
 *         if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && !visited[nr][nc]) {
 *             visited[nr][nc] = true;
 *             q.offer(new int[]{nr, nc});
 *         }
 *     }
 * }
 *
 * ──────────────────────────────────────────────────────────
 * TEMPLATE 4 — DFS (graph, iterative with explicit stack)
 * ──────────────────────────────────────────────────────────
 * Deque<Integer> stack = new ArrayDeque<>();
 * boolean[] visited = new boolean[n];
 * stack.push(start);
 * while (!stack.isEmpty()) {
 *     int node = stack.pop();
 *     if (visited[node]) continue;
 *     visited[node] = true;
 *     for (int neighbor : adj.get(node)) {
 *         if (!visited[neighbor]) stack.push(neighbor);
 *     }
 * }
 *
 * ──────────────────────────────────────────────────────────
 * TEMPLATE 5a — TOPOLOGICAL SORT (Kahn's BFS)
 * ──────────────────────────────────────────────────────────
 * int[] inDegree = new int[n];
 * List<List<Integer>> adj = new ArrayList<>();
 * // build graph, fill inDegree[]
 * Queue<Integer> q = new ArrayDeque<>();
 * for (int i = 0; i < n; i++) if (inDegree[i] == 0) q.offer(i);
 * List<Integer> order = new ArrayList<>();
 * while (!q.isEmpty()) {
 *     int u = q.poll();
 *     order.add(u);
 *     for (int v : adj.get(u)) {
 *         if (--inDegree[v] == 0) q.offer(v);
 *     }
 * }
 * // if order.size() == n → no cycle
 *
 * ──────────────────────────────────────────────────────────
 * TEMPLATE 5b — TOPOLOGICAL SORT (DFS, post-order reverse)
 * ──────────────────────────────────────────────────────────
 * // state: 0=unvisited, 1=in-stack, 2=done
 * int[] state = new int[n];
 * Deque<Integer> result = new ArrayDeque<>();
 * boolean hasCycle = false;
 * for (int i = 0; i < n; i++) {
 *     if (state[i] == 0) dfsTopo(i, adj, state, result);
 * }
 * // dfsTopo: mark 1, recurse neighbors, mark 2, push to result front
 *
 * ──────────────────────────────────────────────────────────
 * TEMPLATE 6 — DIJKSTRA (min-heap, adjacency list)
 * ──────────────────────────────────────────────────────────
 * // dist[]: initialized to Integer.MAX_VALUE
 * // PQ stores int[]{dist, node}
 * PriorityQueue<int[]> pq = new PriorityQueue<>(
 *     (a, b) -> Integer.compare(a[0], b[0]));
 * dist[src] = 0;
 * pq.offer(new int[]{0, src});
 * while (!pq.isEmpty()) {
 *     int[] cur = pq.poll();
 *     int d = cur[0], u = cur[1];
 *     if (d > dist[u]) continue;  // stale entry
 *     for (int[] edge : adj.get(u)) {
 *         int v = edge[0], w = edge[1];
 *         if (dist[u] + w < dist[v]) {
 *             dist[v] = dist[u] + w;
 *             pq.offer(new int[]{dist[v], v});
 *         }
 *     }
 * }
 *
 * ──────────────────────────────────────────────────────────
 * TEMPLATE 7 — DSU (Union-Find with rank + path compression)
 * ──────────────────────────────────────────────────────────
 * int[] parent, rank;
 * void init(int n) { parent = new int[n]; rank = new int[n];
 *   for (int i=0;i<n;i++) parent[i]=i; }
 * int find(int x) {
 *   if (parent[x] != x) parent[x] = find(parent[x]); // path compression
 *   return parent[x];
 * }
 * boolean union(int x, int y) {
 *   int px = find(x), py = find(y);
 *   if (px == py) return false;
 *   if (rank[px] < rank[py]) { int t=px; px=py; py=t; }
 *   parent[py] = px;
 *   if (rank[px] == rank[py]) rank[px]++;
 *   return true;
 * }
 *
 * ──────────────────────────────────────────────────────────
 * TEMPLATE 8 — MONOTONIC STACK (next greater element)
 * ──────────────────────────────────────────────────────────
 * Deque<Integer> stack = new ArrayDeque<>(); // stores indices
 * int[] result = new int[n];
 * Arrays.fill(result, -1);
 * for (int i = 0; i < n; i++) {
 *     while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
 *         result[stack.pop()] = nums[i];  // nums[i] is next greater
 *     }
 *     stack.push(i);
 * }
 *
 * ──────────────────────────────────────────────────────────
 * TEMPLATE 9 — DP PATTERNS
 * ──────────────────────────────────────────────────────────
 * // 1D DP (e.g., LIS, Coin Change):
 * int[] dp = new int[n + 1];
 * dp[0] = base;
 * for (int i = 1; i <= n; i++) {
 *     for each choice j { dp[i] = transition(dp[i], dp[j]); }
 * }
 *
 * // 2D DP (e.g., LCS, Edit Distance):
 * int[][] dp = new int[m + 1][n + 1];
 * for (int i = 1; i <= m; i++) {
 *     for (int j = 1; j <= n; j++) {
 *         if (match) dp[i][j] = dp[i-1][j-1] + 1;
 *         else dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
 *     }
 * }
 *
 * // Interval DP (e.g., Burst Balloons, Matrix Chain):
 * int[][] dp = new int[n][n];
 * for (int len = 2; len <= n; len++) {
 *     for (int i = 0; i <= n - len; i++) {
 *         int j = i + len - 1;
 *         for (int k = i; k < j; k++) {
 *             dp[i][j] = Math.max(dp[i][j], dp[i][k] + dp[k+1][j] + cost(i,k,j));
 *         }
 *     }
 * }
 *
 * ──────────────────────────────────────────────────────────
 * TEMPLATE 10 — BACKTRACKING SKELETON
 * ──────────────────────────────────────────────────────────
 * List<List<Integer>> result = new ArrayList<>();
 * void backtrack(int start, List<Integer> current, int[] nums) {
 *     // base case
 *     if (satisfies(current)) { result.add(new ArrayList<>(current)); }
 *     for (int i = start; i < nums.length; i++) {
 *         // prune: if (shouldPrune(i, current)) continue;
 *         current.add(nums[i]);
 *         backtrack(i + 1, current, nums);  // or i for reuse
 *         current.remove(current.size() - 1);  // undo choice
 *     }
 * }
 */

public class PatternPracticePack {

    // =========================================================
    // Helper classes
    // =========================================================

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int v) { val = v; }
    }

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int v) { val = v; }
    }

    // =========================================================
    // PATTERN 1 — SLIDING WINDOW
    // =========================================================

    /**
     * 1a. Minimum Window Substring (LC 76) — Hard
     * Strategy: Expand right until all t-chars covered, then shrink left to find minimum.
     * Maintain need[] count map; 'formed' tracks how many distinct chars are satisfied.
     */
    public String minWindow(String s, String t) {
        if (s == null || t == null || s.length() < t.length()) return "";

        int[] need = new int[128];
        for (char c : t.toCharArray()) need[c]++;

        int required = 0;
        for (int x : need) if (x > 0) required++;

        int left = 0, formed = 0;
        int[] window = new int[128];
        int minLen = Integer.MAX_VALUE, minStart = 0;

        for (int right = 0; right < s.length(); right++) {
            char rc = s.charAt(right);
            window[rc]++;
            // check if this char's requirement is newly satisfied
            if (need[rc] > 0 && window[rc] == need[rc]) formed++;

            // shrink from left while window is valid
            while (formed == required) {
                if (right - left + 1 < minLen) {
                    minLen = right - left + 1;
                    minStart = left;
                }
                char lc = s.charAt(left);
                window[lc]--;
                if (need[lc] > 0 && window[lc] < need[lc]) formed--;
                left++;
            }
        }
        return minLen == Integer.MAX_VALUE ? "" : s.substring(minStart, minStart + minLen);
    }

    /**
     * 1b. Longest Repeating Character Replacement (LC 424)
     * Key insight: window is valid if (windowSize - maxFreq) <= k.
     * We never shrink maxFreq — this is an optimization (only care about growing).
     */
    public int characterReplacement(String s, int k) {
        int[] freq = new int[26];
        int left = 0, maxFreq = 0, ans = 0;

        for (int right = 0; right < s.length(); right++) {
            freq[s.charAt(right) - 'A']++;
            maxFreq = Math.max(maxFreq, freq[s.charAt(right) - 'A']);

            // if replacements needed exceed k, shrink window
            while ((right - left + 1) - maxFreq > k) {
                freq[s.charAt(left) - 'A']--;
                left++;
            }
            ans = Math.max(ans, right - left + 1);
        }
        return ans;
    }

    /**
     * 1c. Subarrays with K Different Integers (LC 992)
     * Trick: exactly(k) = atMost(k) - atMost(k-1).
     * atMost(k): count subarrays with at most k distinct integers.
     */
    public int subarraysWithKDistinct(int[] nums, int k) {
        return atMostK(nums, k) - atMostK(nums, k - 1);
    }

    private int atMostK(int[] nums, int k) {
        Map<Integer, Integer> count = new HashMap<>();
        int left = 0, ans = 0;
        for (int right = 0; right < nums.length; right++) {
            count.merge(nums[right], 1, Integer::sum);
            while (count.size() > k) {
                int lv = nums[left++];
                count.merge(lv, -1, Integer::sum);
                if (count.get(lv) == 0) count.remove(lv);
            }
            ans += right - left + 1; // all subarrays ending at right
        }
        return ans;
    }

    // ── Sliding Window Stubs ──────────────────────────────────

    /**
     * Maximum Sum Subarray of Size K
     * Approach: Fixed-size window; add incoming, subtract outgoing element.
     * Invariant: window always contains exactly k elements.
     * Time: O(n), Space: O(1)
     */
    public int maxSumSubarrayOfSizeK(int[] nums, int k) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Count Occurrences of Anagrams (LC 438)
     * Approach: Fixed window of size |p|; compare freq arrays (or use a 'matches' counter).
     * Invariant: window size equals p.length at all times.
     * Time: O(n), Space: O(1) — fixed 26-char alphabet
     */
    public int countAnagrams(String txt, String pat) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Sliding Window Maximum (LC 239)
     * Approach: Monotonic decreasing deque of indices; front always holds current max.
     * Invariant: deque front is always within [left, right] window bounds.
     * Time: O(n), Space: O(k)
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Fruits Into Baskets (LC 904)
     * Approach: Variable window; at most 2 distinct fruit types (same as atMostK with k=2).
     * Invariant: window contains at most 2 distinct values.
     * Time: O(n), Space: O(1)
     */
    public int totalFruit(int[] fruits) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Binary Subarrays with Sum (LC 930)
     * Approach: atMost(goal) - atMost(goal-1); handle goal=0 edge case.
     * Invariant: prefix sum within window never exceeds goal.
     * Time: O(n), Space: O(1)
     */
    public int numSubarraysWithSum(int[] nums, int goal) {
        throw new UnsupportedOperationException("stub");
    }

    // =========================================================
    // PATTERN 2 — BINARY SEARCH
    // =========================================================

    /**
     * 2a. Median of Two Sorted Arrays (LC 4) — Hard
     * Binary search on partition of the smaller array.
     * Ensure nums1 is the shorter array; partition such that left halves combined
     * have (m+n+1)/2 elements. Adjust until cross-partition order is satisfied.
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // always binary search on the smaller array
        if (nums1.length > nums2.length) return findMedianSortedArrays(nums2, nums1);

        int m = nums1.length, n = nums2.length;
        int lo = 0, hi = m;
        int half = (m + n + 1) / 2;

        while (lo <= hi) {
            int i = lo + (hi - lo) / 2; // partition index in nums1
            int j = half - i;            // partition index in nums2

            int maxLeft1  = (i == 0) ? Integer.MIN_VALUE : nums1[i - 1];
            int minRight1 = (i == m) ? Integer.MAX_VALUE : nums1[i];
            int maxLeft2  = (j == 0) ? Integer.MIN_VALUE : nums2[j - 1];
            int minRight2 = (j == n) ? Integer.MAX_VALUE : nums2[j];

            if (maxLeft1 <= minRight2 && maxLeft2 <= minRight1) {
                // correct partition found
                if ((m + n) % 2 == 1) return Math.max(maxLeft1, maxLeft2);
                return (Math.max(maxLeft1, maxLeft2) + Math.min(minRight1, minRight2)) / 2.0;
            } else if (maxLeft1 > minRight2) {
                hi = i - 1; // move partition left in nums1
            } else {
                lo = i + 1; // move partition right in nums1
            }
        }
        return 0.0; // unreachable for valid input
    }

    /**
     * 2b. Capacity to Ship Packages Within D Days (LC 1011)
     * Binary search on the ship capacity (answer space).
     * Lower bound: max single weight; upper bound: sum of all weights.
     * Feasibility: simulate loading; if days needed <= D, capacity is sufficient.
     */
    public int shipWithinDays(int[] weights, int days) {
        int lo = 0, hi = 0;
        for (int w : weights) {
            lo = Math.max(lo, w); // must fit heaviest package
            hi += w;              // worst case: ship everything in one day
        }

        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (canShip(weights, days, mid)) {
                hi = mid;     // try smaller capacity
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }

    private boolean canShip(int[] weights, int days, int capacity) {
        int daysNeeded = 1, currentLoad = 0;
        for (int w : weights) {
            if (currentLoad + w > capacity) {
                daysNeeded++;
                currentLoad = 0;
            }
            currentLoad += w;
        }
        return daysNeeded <= days;
    }

    /**
     * 2c. Search in Rotated Sorted Array (LC 33)
     * One half is always sorted. Determine which half and check if target lies in it.
     */
    public int search(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] == target) return mid;

            if (nums[lo] <= nums[mid]) {
                // left half is sorted
                if (nums[lo] <= target && target < nums[mid]) {
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            } else {
                // right half is sorted
                if (nums[mid] < target && target <= nums[hi]) {
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            }
        }
        return -1;
    }

    // ── Binary Search Stubs ───────────────────────────────────

    /**
     * Find Minimum in Rotated Sorted Array (LC 153)
     * Approach: When nums[mid] > nums[hi], minimum is in right half; else left half.
     * Invariant: lo always moves toward the inflection (rotation) point.
     * Time: O(log n), Space: O(1)
     */
    public int findMin(int[] nums) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Koko Eating Bananas (LC 875)
     * Approach: Binary search on eating speed k; check if all piles eaten within h hours.
     * Invariant: feasible(k) is monotone — if k works, k+1 also works.
     * Time: O(n log(max_pile)), Space: O(1)
     */
    public int minEatingSpeed(int[] piles, int h) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Aggressive Cows (classic)
     * Approach: Binary search on minimum distance; greedily check if k cows fit.
     * Invariant: if distance d is feasible, any d' < d is also feasible (monotone).
     * Time: O(n log n + n log(max_dist)), Space: O(1)
     */
    public int aggressiveCows(int[] stalls, int k) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Allocate Minimum Pages (classic / LC 1891-like)
     * Approach: Binary search on max pages per student; check with greedy allocation.
     * Invariant: answer space is [max_single_book, sum_of_all]; monotone feasibility.
     * Time: O(n log(sum)), Space: O(1)
     */
    public int allocateMinPages(int[] books, int students) {
        throw new UnsupportedOperationException("stub");
    }

    // =========================================================
    // PATTERN 3 — RECURSION
    // =========================================================

    /**
     * 3a. Generate Balanced Parentheses (LC 22)
     * At each step, we can add '(' if open < n, or ')' if close < open.
     */
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        generateParenHelper(result, new StringBuilder(), 0, 0, n);
        return result;
    }

    private void generateParenHelper(List<String> result, StringBuilder sb,
                                      int open, int close, int n) {
        if (sb.length() == 2 * n) {
            result.add(sb.toString());
            return;
        }
        if (open < n) {
            sb.append('(');
            generateParenHelper(result, sb, open + 1, close, n);
            sb.deleteCharAt(sb.length() - 1);
        }
        if (close < open) {
            sb.append(')');
            generateParenHelper(result, sb, open, close + 1, n);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    /**
     * 3b. Power Set (LC 78)
     * Bit manipulation: for each number 0..2^n-1, include element i if bit i is set.
     * Also shown: recursive include/exclude approach.
     */
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        int n = nums.length;
        // Bit manipulation approach
        for (int mask = 0; mask < (1 << n); mask++) {
            List<Integer> subset = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) subset.add(nums[i]);
            }
            result.add(subset);
        }
        return result;
    }

    // Recursive include/exclude approach for power set
    private void subsetsRecursive(int[] nums, int idx,
                                   List<Integer> current, List<List<Integer>> result) {
        if (idx == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }
        current.add(nums[idx]);           // include nums[idx]
        subsetsRecursive(nums, idx + 1, current, result);
        current.remove(current.size() - 1); // exclude nums[idx]
        subsetsRecursive(nums, idx + 1, current, result);
    }

    /**
     * 3c. K-th Symbol in Grammar (LC 779)
     * Row n is built by replacing 0→01, 1→10 from row n-1.
     * Key: element k in row n is determined by parent at ceil(k/2) in row n-1.
     * If parent is 0: children are 0,1. If parent is 1: children are 1,0.
     */
    public int kthGrammar(int n, int k) {
        if (n == 1) return 0; // base case: row 1 is "0"
        int parent = kthGrammar(n - 1, (k + 1) / 2);
        boolean isRightChild = (k % 2 == 0);
        if (parent == 0) return isRightChild ? 1 : 0;
        else             return isRightChild ? 0 : 1;
    }

    // ── Recursion Stubs ───────────────────────────────────────

    /**
     * Print All Subsequences of an array
     * Approach: Recursive include/exclude at each index; print when index == n.
     * Invariant: at depth d, first d elements have been decided (in or out).
     * Time: O(2^n * n), Space: O(n) recursion stack
     */
    public void printSubsequences(int[] arr) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Generate All Permutations (LC 46)
     * Approach: Swap current index with every index from current to end; recurse; swap back.
     * Invariant: elements before current index are fixed; rest are candidates.
     * Time: O(n! * n), Space: O(n)
     */
    public List<List<Integer>> permute(int[] nums) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Tower of Hanoi
     * Approach: Move n-1 disks to aux peg, move nth disk to dest, move n-1 from aux to dest.
     * Invariant: only smaller disks can rest on larger disks.
     * Time: O(2^n), Space: O(n) recursion stack
     */
    public void towerOfHanoi(int n, char from, char to, char aux) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Delete Middle Element of Stack
     * Approach: Recurse to middle (depth == size/2), skip that element, rebuild on return.
     * Invariant: recursion depth tracks how deep in the stack we are.
     * Time: O(n), Space: O(n) recursion stack
     */
    public void deleteMid(Deque<Integer> stack, int size, int current) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Reverse a Stack using Recursion
     * Approach: Pop all elements recursively; on the way back, insertAtBottom each element.
     * Invariant: after reversal, top element is what was at the bottom.
     * Time: O(n^2), Space: O(n) recursion stack
     */
    public void reverseStack(Deque<Integer> stack) {
        throw new UnsupportedOperationException("stub");
    }

    // =========================================================
    // PATTERN 4 — STACK + MONOTONIC STACK
    // =========================================================

    /**
     * 4a. Largest Rectangle in Histogram (LC 84) — Hard
     * Maintain a monotonic increasing stack of indices.
     * Sentinel: append 0 at the end to flush remaining stack elements.
     * When popping index i, the width extends from stack.peek()+1 to current j-1.
     */
    public int largestRectangleArea(int[] heights) {
        Deque<Integer> stack = new ArrayDeque<>();
        int maxArea = 0;
        int n = heights.length;

        for (int i = 0; i <= n; i++) {
            int h = (i == n) ? 0 : heights[i]; // sentinel 0 flushes stack at end

            while (!stack.isEmpty() && heights[stack.peek()] > h) {
                int height = heights[stack.pop()];
                int width = stack.isEmpty() ? i : i - stack.peek() - 1;
                maxArea = Math.max(maxArea, height * width);
            }
            stack.push(i);
        }
        return maxArea;
    }

    /**
     * 4b. Trapping Rain Water (LC 42)
     * Two-pointer approach: O(1) space.
     * Water at position i = min(maxLeft, maxRight) - height[i].
     * Move the pointer with the smaller max inward — that side is the bottleneck.
     */
    public int trap(int[] height) {
        int left = 0, right = height.length - 1;
        int maxLeft = 0, maxRight = 0, water = 0;

        while (left < right) {
            if (height[left] < height[right]) {
                if (height[left] >= maxLeft) maxLeft = height[left];
                else water += maxLeft - height[left];
                left++;
            } else {
                if (height[right] >= maxRight) maxRight = height[right];
                else water += maxRight - height[right];
                right--;
            }
        }
        return water;
    }

    /**
     * 4c. Daily Temperatures (LC 739)
     * Monotonic decreasing stack of indices.
     * When a warmer temperature is found, pop and record the wait days.
     */
    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] result = new int[n];
        Deque<Integer> stack = new ArrayDeque<>(); // indices of unresolved days

        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && temperatures[stack.peek()] < temperatures[i]) {
                int idx = stack.pop();
                result[idx] = i - idx;
            }
            stack.push(i);
        }
        return result;
    }

    // ── Monotonic Stack Stubs ─────────────────────────────────

    /**
     * Next Greater Element I (LC 496)
     * Approach: Monotonic decreasing stack on nums2; store results in a map; look up nums1.
     * Invariant: stack holds elements whose NGE has not been found yet.
     * Time: O(m+n), Space: O(n)
     */
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Stock Span Problem
     * Approach: Monotonic decreasing stack of (price, span); accumulate spans when popping.
     * Invariant: stack holds prices greater than the current; span includes all consecutive smaller prices.
     * Time: O(n) amortized, Space: O(n)
     */
    public int[] stockSpan(int[] prices) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Min Stack (LC 155)
     * Approach: Two stacks — main stack and a parallel min-tracking stack.
     * Invariant: minStack.peek() always equals the minimum of the current main stack.
     * Time: O(1) all ops, Space: O(n)
     */
    // Implemented as inner class MinStack below

    /**
     * Remove K Digits (LC 402)
     * Approach: Monotonic increasing stack; pop larger digit when next is smaller (greedy).
     * Invariant: remaining stack is the smallest possible number prefix.
     * Time: O(n), Space: O(n)
     */
    public String removeKdigits(String num, int k) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Asteroid Collision (LC 735)
     * Approach: Stack; positive asteroids pushed, negative asteroids collide with stack top.
     * Invariant: stack only holds surviving positive (or negative at bottom) asteroids.
     * Time: O(n), Space: O(n)
     */
    public int[] asteroidCollision(int[] asteroids) {
        throw new UnsupportedOperationException("stub");
    }

    static class MinStack {
        private final Deque<Integer> stack    = new ArrayDeque<>();
        private final Deque<Integer> minStack = new ArrayDeque<>();

        public void push(int val) {
            stack.push(val);
            if (minStack.isEmpty() || val <= minStack.peek()) minStack.push(val);
        }

        public void pop() {
            int val = stack.pop();
            if (val == minStack.peek()) minStack.pop();
        }

        public int top()    { return stack.peek(); }
        public int getMin() { return minStack.peek(); }
    }

    // =========================================================
    // PATTERN 5 — DYNAMIC PROGRAMMING
    // =========================================================

    /**
     * 5a. 0/1 Knapsack
     * 2D DP: dp[i][w] = max value using first i items with capacity w.
     * Optimized to 1D: iterate capacity in reverse to prevent using same item twice.
     */
    public int knapsack(int[] weights, int[] values, int W) {
        int n = weights.length;
        // 1D space-optimized version
        int[] dp = new int[W + 1];
        for (int i = 0; i < n; i++) {
            // iterate backwards: ensures item i is used at most once
            for (int w = W; w >= weights[i]; w--) {
                dp[w] = Math.max(dp[w], dp[w - weights[i]] + values[i]);
            }
        }
        return dp[W];
    }

    /**
     * 5b. Longest Common Subsequence (LC 1143)
     * dp[i][j] = LCS length of text1[0..i-1] and text2[0..j-1].
     * If chars match: dp[i][j] = dp[i-1][j-1] + 1.
     * Else: dp[i][j] = max(dp[i-1][j], dp[i][j-1]).
     */
    public int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length(), n = text2.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[m][n];
    }

    /**
     * 5c. Burst Balloons (LC 312) — Hard
     * Interval DP: think of k as the LAST balloon to burst in range [i, j].
     * Pad with boundary 1s. dp[i][j] = max coins from bursting all balloons in (i,j).
     * dp[i][j] = max over k: dp[i][k] + nums[i]*nums[k]*nums[j] + dp[k][j]
     */
    public int maxCoins(int[] nums) {
        int n = nums.length;
        // pad with boundary sentinels
        int[] arr = new int[n + 2];
        arr[0] = arr[n + 1] = 1;
        for (int i = 0; i < n; i++) arr[i + 1] = nums[i];
        int N = n + 2;

        int[][] dp = new int[N][N];
        // len is the length of the open interval (i, j)
        for (int len = 2; len < N; len++) {
            for (int i = 0; i < N - len; i++) {
                int j = i + len;
                for (int k = i + 1; k < j; k++) {
                    // k is the last balloon burst in open interval (i, j)
                    dp[i][j] = Math.max(dp[i][j],
                        dp[i][k] + arr[i] * arr[k] * arr[j] + dp[k][j]);
                }
            }
        }
        return dp[0][N - 1];
    }

    // ── DP Stubs ──────────────────────────────────────────────

    /**
     * Coin Change (LC 322)
     * Approach: Bottom-up DP; dp[i] = min coins to make amount i; try each coin.
     * Invariant: dp[0]=0; dp[i] = min(dp[i-coin]+1) for each valid coin.
     * Time: O(amount * coins), Space: O(amount)
     */
    public int coinChange(int[] coins, int amount) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Subset Sum (classic)
     * Approach: dp[i][s] = true if sum s achievable with first i elements; 1D space opt.
     * Invariant: dp[s] becomes true once we find any subset summing to s.
     * Time: O(n * target), Space: O(target)
     */
    public boolean subsetSum(int[] nums, int target) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Longest Increasing Subsequence — O(n log n) patience sorting (LC 300)
     * Approach: Maintain 'tails' array; binary search for position to update/extend.
     * Invariant: tails[i] is the smallest tail element for LIS of length i+1.
     * Time: O(n log n), Space: O(n)
     */
    public int lengthOfLIS(int[] nums) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Minimum Path Sum (LC 64)
     * Approach: 2D DP; dp[i][j] = min cost to reach cell (i,j) from (0,0).
     * Invariant: dp[i][j] = grid[i][j] + min(dp[i-1][j], dp[i][j-1]).
     * Time: O(m*n), Space: O(m*n) or O(n) with rolling array
     */
    public int minPathSum(int[][] grid) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Egg Dropping Problem (LC 887)
     * Approach: DP + binary search on floors; dp[k][n] = min trials with k eggs, n floors.
     * Invariant: for each drop, worst case = 1 + max(egg breaks → test below, egg survives → test above).
     * Time: O(k * n log n), Space: O(k * n)
     */
    public int superEggDrop(int k, int n) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Matrix Chain Multiplication (classic interval DP)
     * Approach: Interval DP; dp[i][j] = min multiplications to compute chain from i to j.
     * Invariant: split point k gives dp[i][k] + dp[k+1][j] + p[i-1]*p[k]*p[j] cost.
     * Time: O(n^3), Space: O(n^2)
     */
    public int matrixChainOrder(int[] dims) {
        throw new UnsupportedOperationException("stub");
    }

    // =========================================================
    // PATTERN 6 — HEAP / PRIORITY QUEUE
    // =========================================================

    /**
     * 6a. Find Median from Data Stream (LC 295)
     * Two heaps: maxHeap for lower half, minHeap for upper half.
     * Balance invariant: maxHeap.size() == minHeap.size() OR maxHeap.size() == minHeap.size() + 1.
     */
    static class MedianFinder {
        // max-heap: lower half (negate values for max-heap behavior)
        private final PriorityQueue<Integer> maxHeap =
            new PriorityQueue<>((a, b) -> Integer.compare(b, a));
        // min-heap: upper half
        private final PriorityQueue<Integer> minHeap =
            new PriorityQueue<>(Integer::compare);

        public void addNum(int num) {
            maxHeap.offer(num);
            // balance: ensure maxHeap top <= minHeap top
            minHeap.offer(maxHeap.poll());
            // maintain size invariant: maxHeap >= minHeap
            if (minHeap.size() > maxHeap.size()) maxHeap.offer(minHeap.poll());
        }

        public double findMedian() {
            if (maxHeap.size() > minHeap.size()) return maxHeap.peek();
            return (maxHeap.peek() + minHeap.peek()) / 2.0;
        }
    }

    /**
     * 6b. Merge K Sorted Lists (LC 23)
     * Min-heap stores (node.val, listIndex, node).
     * Poll minimum, add to result, push next node from same list.
     * O(N log k) where N = total nodes, k = number of lists.
     */
    public ListNode mergeKLists(ListNode[] lists) {
        // PQ: compare by node value
        PriorityQueue<ListNode> pq = new PriorityQueue<>(
            (a, b) -> Integer.compare(a.val, b.val));

        for (ListNode node : lists) {
            if (node != null) pq.offer(node);
        }

        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        while (!pq.isEmpty()) {
            ListNode node = pq.poll();
            cur.next = node;
            cur = cur.next;
            if (node.next != null) pq.offer(node.next);
        }
        return dummy.next;
    }

    /**
     * 6c. Task Scheduler (LC 621)
     * Max-heap of task frequencies. Simulate cooldown intervals.
     * Each cycle: try to schedule up to (n+1) tasks, using most frequent first.
     * If fewer than (n+1) tasks available, idle time fills the gap.
     */
    public int leastInterval(char[] tasks, int n) {
        int[] freq = new int[26];
        for (char c : tasks) freq[c - 'A']++;

        PriorityQueue<Integer> maxHeap =
            new PriorityQueue<>((a, b) -> Integer.compare(b, a));
        for (int f : freq) if (f > 0) maxHeap.offer(f);

        int time = 0;
        while (!maxHeap.isEmpty()) {
            List<Integer> temp = new ArrayList<>();
            int cycle = n + 1;         // one cooldown window
            int tasksInCycle = 0;

            while (cycle-- > 0 && !maxHeap.isEmpty()) {
                int f = maxHeap.poll();
                if (f > 1) temp.add(f - 1);
                tasksInCycle++;
            }
            maxHeap.addAll(temp);
            // if heap is empty, we only need tasksInCycle more, no idle padding
            time += maxHeap.isEmpty() ? tasksInCycle : n + 1;
        }
        return time;
    }

    // ── Heap Stubs ────────────────────────────────────────────

    /**
     * K Closest Points to Origin (LC 973)
     * Approach: Max-heap of size k; maintain k closest seen so far.
     * Invariant: heap.peek() is the farthest of the k closest points.
     * Time: O(n log k), Space: O(k)
     */
    public int[][] kClosest(int[][] points, int k) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Top K Frequent Elements (LC 347)
     * Approach: Frequency map + min-heap of size k; or bucket sort O(n).
     * Invariant: heap contains exactly k most frequent elements seen so far.
     * Time: O(n log k), Space: O(n)
     */
    public int[] topKFrequent(int[] nums, int k) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Reorganize String (LC 767)
     * Approach: Max-heap by frequency; alternately pick two most frequent chars.
     * Invariant: no two adjacent characters are the same; impossible if max_freq > (n+1)/2.
     * Time: O(n log 26) = O(n), Space: O(26) = O(1)
     */
    public String reorganizeString(String s) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Smallest Range Covering Elements from K Lists (LC 632)
     * Approach: Min-heap of current heads; track global max; advance min, update range.
     * Invariant: heap always has exactly one element from each list; range = [min, globalMax].
     * Time: O(N log k), Space: O(k)
     */
    public int[] smallestRange(List<List<Integer>> nums) {
        throw new UnsupportedOperationException("stub");
    }

    // =========================================================
    // PATTERN 7 — BACKTRACKING
    // =========================================================

    /**
     * 7a. N-Queens (LC 51)
     * Track used columns, diagonals (r-c), anti-diagonals (r+c).
     * Place one queen per row; skip invalid columns.
     */
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<>();
        int[] queens = new int[n]; // queens[row] = column of queen in that row
        Arrays.fill(queens, -1);
        Set<Integer> cols    = new HashSet<>();
        Set<Integer> diag1   = new HashSet<>(); // r - c
        Set<Integer> diag2   = new HashSet<>(); // r + c

        nQueensBacktrack(0, n, queens, cols, diag1, diag2, result);
        return result;
    }

    private void nQueensBacktrack(int row, int n, int[] queens,
                                   Set<Integer> cols, Set<Integer> diag1, Set<Integer> diag2,
                                   List<List<String>> result) {
        if (row == n) {
            result.add(buildBoard(queens, n));
            return;
        }
        for (int col = 0; col < n; col++) {
            if (cols.contains(col) || diag1.contains(row - col) || diag2.contains(row + col))
                continue;
            queens[row] = col;
            cols.add(col); diag1.add(row - col); diag2.add(row + col);
            nQueensBacktrack(row + 1, n, queens, cols, diag1, diag2, result);
            queens[row] = -1;
            cols.remove(col); diag1.remove(row - col); diag2.remove(row + col);
        }
    }

    private List<String> buildBoard(int[] queens, int n) {
        List<String> board = new ArrayList<>();
        for (int r = 0; r < n; r++) {
            char[] row = new char[n];
            Arrays.fill(row, '.');
            row[queens[r]] = 'Q';
            board.add(new String(row));
        }
        return board;
    }

    /**
     * 7b. Sudoku Solver (LC 37) — Hard
     * Find first empty cell; try digits 1-9; check row/col/box validity; recurse.
     * Backtrack by resetting cell to '.' if no digit works.
     */
    public void solveSudoku(char[][] board) {
        solveSudokuHelper(board);
    }

    private boolean solveSudokuHelper(char[][] board) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board[r][c] == '.') {
                    for (char d = '1'; d <= '9'; d++) {
                        if (isValidSudoku(board, r, c, d)) {
                            board[r][c] = d;
                            if (solveSudokuHelper(board)) return true;
                            board[r][c] = '.'; // backtrack
                        }
                    }
                    return false; // no valid digit found
                }
            }
        }
        return true; // all cells filled
    }

    private boolean isValidSudoku(char[][] board, int row, int col, char d) {
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == d) return false;
            if (board[i][col] == d) return false;
            int br = 3 * (row / 3) + i / 3;
            int bc = 3 * (col / 3) + i % 3;
            if (board[br][bc] == d) return false;
        }
        return true;
    }

    /**
     * 7c. Word Search (LC 79)
     * DFS from each cell matching word[0]; mark visited in-place with '#'.
     * Restore cell after DFS (backtrack).
     */
    public boolean exist(char[][] board, String word) {
        int rows = board.length, cols = board[0].length;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (wordSearchDFS(board, word, r, c, 0)) return true;
            }
        }
        return false;
    }

    private boolean wordSearchDFS(char[][] board, String word, int r, int c, int idx) {
        if (idx == word.length()) return true;
        if (r < 0 || r >= board.length || c < 0 || c >= board[0].length) return false;
        if (board[r][c] != word.charAt(idx)) return false;

        char tmp = board[r][c];
        board[r][c] = '#'; // mark visited in-place
        boolean found = wordSearchDFS(board, word, r + 1, c, idx + 1)
                     || wordSearchDFS(board, word, r - 1, c, idx + 1)
                     || wordSearchDFS(board, word, r, c + 1, idx + 1)
                     || wordSearchDFS(board, word, r, c - 1, idx + 1);
        board[r][c] = tmp; // restore (backtrack)
        return found;
    }

    // ── Backtracking Stubs ────────────────────────────────────

    /**
     * Subsets II — with duplicates (LC 90)
     * Approach: Sort first; skip duplicate elements at same recursion depth.
     * Invariant: i > start && nums[i] == nums[i-1] → skip to avoid duplicate subsets.
     * Time: O(2^n * n), Space: O(n)
     */
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Combination Sum (LC 39)
     * Approach: Backtrack with candidates; allow reuse (recurse with same index i, not i+1).
     * Invariant: current sum never exceeds target; candidates sorted for early termination.
     * Time: O(n^(target/min)), Space: O(target/min) recursion depth
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Rat in a Maze (classic)
     * Approach: DFS from (0,0) to (n-1,n-1); move down/right/up/left; mark visited.
     * Invariant: only move to cells with value 1; restore visited on backtrack.
     * Time: O(4^(n^2)) worst case, Space: O(n^2)
     */
    public List<String> findPaths(int[][] maze) {
        throw new UnsupportedOperationException("stub");
    }

    // =========================================================
    // PATTERN 8 — MIXED MUST-DO
    // =========================================================

    /**
     * 8a. Merge Intervals (LC 56)
     * Sort by start. For each interval, if it overlaps with last merged, extend end.
     * Overlap condition: current.start <= last.end.
     */
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        List<int[]> merged = new ArrayList<>();

        for (int[] interval : intervals) {
            if (merged.isEmpty() || interval[0] > merged.get(merged.size() - 1)[1]) {
                merged.add(interval);
            } else {
                merged.get(merged.size() - 1)[1] =
                    Math.max(merged.get(merged.size() - 1)[1], interval[1]);
            }
        }
        return merged.toArray(new int[0][]);
    }

    /**
     * 8b. Product of Array Except Self (LC 238)
     * First pass (left to right): result[i] = product of all elements to the left.
     * Second pass (right to left): multiply in product of all elements to the right.
     * O(1) extra space (output array doesn't count).
     */
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        result[0] = 1;

        // forward pass: result[i] = product of nums[0..i-1]
        for (int i = 1; i < n; i++) result[i] = result[i - 1] * nums[i - 1];

        // backward pass: multiply in product of nums[i+1..n-1]
        int rightProduct = 1;
        for (int i = n - 1; i >= 0; i--) {
            result[i] *= rightProduct;
            rightProduct *= nums[i];
        }
        return result;
    }

    /**
     * 8c. Container With Most Water (LC 11)
     * Two-pointer: move the pointer with the shorter height inward.
     * Reasoning: moving the taller pointer can only decrease or maintain area.
     */
    public int maxArea(int[] height) {
        int left = 0, right = height.length - 1, maxWater = 0;
        while (left < right) {
            int h = Math.min(height[left], height[right]);
            maxWater = Math.max(maxWater, h * (right - left));
            if (height[left] < height[right]) left++;
            else right--;
        }
        return maxWater;
    }

    // ── Mixed Must-Do Stubs ───────────────────────────────────

    /**
     * Two Sum (LC 1)
     * Approach: Hash map; for each num, check if complement (target-num) exists.
     * Invariant: map stores each element's index; one-pass is sufficient.
     * Time: O(n), Space: O(n)
     */
    public int[] twoSum(int[] nums, int target) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * 3Sum (LC 15)
     * Approach: Sort; fix one element; use two pointers for remaining pair.
     * Invariant: skip duplicates at the fixed index and during two-pointer scan.
     * Time: O(n^2), Space: O(1) extra (excluding output)
     */
    public List<List<Integer>> threeSum(int[] nums) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Rotate Image (LC 48)
     * Approach: Transpose matrix (swap [i][j] with [j][i]), then reverse each row.
     * Invariant: transposing + reversing rows equals 90-degree clockwise rotation.
     * Time: O(n^2), Space: O(1)
     */
    public void rotate(int[][] matrix) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Set Matrix Zeroes (LC 73)
     * Approach: Use first row/col as markers; scan matrix; zero out marked rows/cols.
     * Invariant: first row/col flag must be handled separately to avoid false zeros.
     * Time: O(m*n), Space: O(1)
     */
    public void setZeroes(int[][] matrix) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Spiral Matrix (LC 54)
     * Approach: Maintain top/bottom/left/right boundaries; shrink after each traversal direction.
     * Invariant: boundaries converge inward; stop when top > bottom or left > right.
     * Time: O(m*n), Space: O(1) extra
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        throw new UnsupportedOperationException("stub");
    }

    // =========================================================
    // PATTERN 9 — ARRAYS, TWO POINTERS, PREFIX SUMS (Stubs)
    // =========================================================

    /**
     * Two Sum II — sorted input (LC 167)
     * Approach: Two pointers from ends; if sum > target move right pointer left, else move left right.
     * Invariant: one valid pair guaranteed; pointers converge without missing it.
     * Time: O(n), Space: O(1)
     */
    public int[] twoSumSorted(int[] numbers, int target) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * 3Sum (LC 15) — see stub in Pattern 8 above
     * Approach: Sort + fix + two pointers; skip duplicates to avoid duplicate triplets.
     * Invariant: for fixed i, two-pointer finds all pairs summing to -nums[i].
     * Time: O(n^2), Space: O(1)
     */
    // threeSum defined above

    /**
     * 4Sum (LC 18)
     * Approach: Sort + fix two outer indices + two pointers; skip duplicates at each level.
     * Invariant: use long to avoid overflow when summing four integers.
     * Time: O(n^3), Space: O(1)
     */
    public List<List<Integer>> fourSum(int[] nums, int target) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Subarray Sum Equals K (LC 560)
     * Approach: Prefix sum + hash map; count[prefix] = times prefix sum has occurred.
     * Invariant: subarray(i..j) sums to k iff prefixSum[j] - prefixSum[i-1] == k.
     * Time: O(n), Space: O(n)
     */
    public int subarraySum(int[] nums, int k) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Maximum Product Subarray (LC 152)
     * Approach: Track both maxProd and minProd (negatives flip); update at each step.
     * Invariant: at each index, either extend previous subarray or start fresh.
     * Time: O(n), Space: O(1)
     */
    public int maxProduct(int[] nums) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Trapping Rain Water — prefix sum variant (LC 42)
     * Approach: Precompute leftMax[] and rightMax[] arrays; water[i] = min(leftMax[i], rightMax[i]) - height[i].
     * Invariant: water at each bar is bounded by the shorter of the two tallest bars on either side.
     * Time: O(n), Space: O(n)
     */
    public int trapPrefixVariant(int[] height) {
        throw new UnsupportedOperationException("stub");
    }

    // =========================================================
    // PATTERN 10 — HASHING + INTERVALS + SWEEP LINE (Stubs)
    // =========================================================

    /**
     * Group Anagrams (LC 49)
     * Approach: Sort each string → use as hash key; group by key.
     * Invariant: anagrams have identical sorted character sequences.
     * Time: O(n * k log k) where k = max string length, Space: O(n * k)
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Longest Consecutive Sequence (LC 128)
     * Approach: Add all to HashSet; for each sequence start (n-1 not in set), count streak.
     * Invariant: only start counting from sequence beginnings to ensure O(n) total.
     * Time: O(n), Space: O(n)
     */
    public int longestConsecutive(int[] nums) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Top K Frequent Words (LC 692)
     * Approach: Frequency map + min-heap of size k (sort by freq desc, then lex asc).
     * Invariant: heap comparator: lower freq first; same freq → reverse lex order (so poll removes worst).
     * Time: O(n log k), Space: O(n)
     */
    public List<String> topKFrequentWords(String[] words, int k) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Merge Intervals — hashing / counting variant
     * Approach: Sweep line with events; +1 at start, -1 at end+1; scan for overlaps.
     * Invariant: running count > 0 means we're inside at least one interval.
     * Time: O(n log n), Space: O(n)
     */
    public int[][] mergeWithSweep(int[][] intervals) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Employee Free Time (LC 759)
     * Approach: Collect all intervals, sort by start, merge → gaps between merged intervals = free time.
     * Invariant: free time exists between end of one merged block and start of the next.
     * Time: O(n log n), Space: O(n)
     */
    public List<int[]> employeeFreeTime(List<List<int[]>> schedule) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Meeting Rooms II (LC 253)
     * Approach: Separate start[] and end[] arrays; two-pointer sweep; count simultaneous meetings.
     * Invariant: increment rooms when meeting starts before previous ends; decrement otherwise.
     * Time: O(n log n), Space: O(n)
     */
    public int minMeetingRooms(int[][] intervals) {
        throw new UnsupportedOperationException("stub");
    }

    // =========================================================
    // PATTERN 11 — LINKED LISTS (Stubs)
    // =========================================================

    /**
     * Reverse Linked List — iterative (LC 206)
     * Approach: Three pointers: prev=null, curr, next; re-link each node backwards.
     * Invariant: after processing node, curr.next points to prev (reversed so far).
     * Time: O(n), Space: O(1)
     */
    public ListNode reverseList(ListNode head) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Reverse Linked List — recursive (LC 206)
     * Approach: Recurse to tail; on the way back, set head.next.next = head, head.next = null.
     * Invariant: returned node is always the new head (original tail).
     * Time: O(n), Space: O(n) call stack
     */
    public ListNode reverseListRecursive(ListNode head) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Detect Cycle — Floyd's algorithm (LC 141)
     * Approach: Slow pointer moves 1 step, fast moves 2; they meet iff cycle exists.
     * Invariant: if cycle, fast eventually laps slow; if no cycle, fast reaches null.
     * Time: O(n), Space: O(1)
     */
    public boolean hasCycle(ListNode head) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Find Cycle Start (LC 142)
     * Approach: After slow/fast meet, reset one pointer to head; advance both 1 step; meeting point = cycle start.
     * Invariant: distance from head to cycle-start equals distance from meeting point to cycle-start.
     * Time: O(n), Space: O(1)
     */
    public ListNode detectCycle(ListNode head) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Merge Two Sorted Lists (LC 21)
     * Approach: Dummy head + iterative comparison; attach smaller node, advance that pointer.
     * Invariant: dummy.next is always the head of the merged list being built.
     * Time: O(m+n), Space: O(1)
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Reverse Nodes in K-Group (LC 25) — Hard
     * Approach: Check k nodes exist; reverse them in-place; recurse on tail; reconnect.
     * Invariant: each group is reversed independently; last group (< k nodes) stays unchanged.
     * Time: O(n), Space: O(n/k) recursion stack
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * LRU Cache (LC 146) — Full implementation in Pattern 21 inner class.
     * Approach: HashMap + doubly linked list; O(1) get and put.
     * Invariant: most recently used at head; least recently used at tail.
     * Time: O(1) get/put, Space: O(capacity)
     */
    // See LRUCache inner class in Pattern 21

    // =========================================================
    // PATTERN 12 — TREES + BST (Stubs)
    // =========================================================

    /**
     * Binary Tree Level Order Traversal (LC 102)
     * Approach: BFS with queue; at each level, poll exactly queue.size() nodes.
     * Invariant: queue contains exactly the nodes of the current level at each iteration start.
     * Time: O(n), Space: O(n)
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Lowest Common Ancestor — BST (LC 235)
     * Approach: If both p,q < root go left; both > root go right; else root is LCA.
     * Invariant: BST property lets us navigate without examining the whole tree.
     * Time: O(h) = O(log n) balanced, Space: O(1) iterative
     */
    public TreeNode lowestCommonAncestorBST(TreeNode root, TreeNode p, TreeNode q) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Lowest Common Ancestor — general binary tree (LC 236)
     * Approach: Post-order DFS; if both subtrees return non-null, current node is LCA.
     * Invariant: return node if it matches p or q, or propagate non-null child result upward.
     * Time: O(n), Space: O(h)
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Serialize / Deserialize Binary Tree (LC 297)
     * Approach: Pre-order DFS with null markers; deserialize using a queue of tokens.
     * Invariant: "null" tokens preserve structural position; non-null tokens are values.
     * Time: O(n), Space: O(n)
     */
    public String serialize(TreeNode root) {
        throw new UnsupportedOperationException("stub");
    }

    public TreeNode deserialize(String data) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Diameter of Binary Tree (LC 543)
     * Approach: For each node, diameter = leftHeight + rightHeight; track global max.
     * Invariant: diameter through node = sum of depths of deepest left and right descendants.
     * Time: O(n), Space: O(h)
     */
    public int diameterOfBinaryTree(TreeNode root) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Path Sum II (LC 113)
     * Approach: DFS with running sum; at leaves, check sum == targetSum and record path.
     * Invariant: backtrack by removing last element after recursive calls return.
     * Time: O(n^2) worst case (copying paths), Space: O(n)
     */
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Validate BST (LC 98)
     * Approach: Pass (min, max) bounds; left subtree bound max = node.val; right bound min = node.val.
     * Invariant: every node must satisfy min < node.val < max (strict).
     * Time: O(n), Space: O(h)
     */
    public boolean isValidBST(TreeNode root) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Kth Smallest Element in BST (LC 230)
     * Approach: In-order traversal (left → root → right) yields sorted order; count to k.
     * Invariant: in-order traversal of BST visits nodes in ascending order.
     * Time: O(h + k), Space: O(h)
     */
    public int kthSmallest(TreeNode root, int k) {
        throw new UnsupportedOperationException("stub");
    }

    // =========================================================
    // PATTERN 13 — TRIE (Stubs)
    // =========================================================

    /**
     * Implement Trie — insert / search / startsWith (LC 208)
     * Approach: 26-child array per node; isEnd flag at word termination nodes.
     * Invariant: path from root to isEnd=true node spells out a complete word.
     * Time: O(m) per op where m = word length, Space: O(m * 26) per word
     */
    static class Trie {
        // stub
        private TrieNode trieRoot = new TrieNode();

        static class TrieNode {
            TrieNode[] children = new TrieNode[26];
            boolean isEnd = false;
        }

        public void insert(String word) {
            throw new UnsupportedOperationException("stub");
        }

        public boolean search(String word) {
            throw new UnsupportedOperationException("stub");
        }

        public boolean startsWith(String prefix) {
            throw new UnsupportedOperationException("stub");
        }
    }

    /**
     * Word Search II (LC 212) — Trie + DFS
     * Approach: Build Trie of words; DFS from each cell; prune branches not in Trie.
     * Invariant: mark visited in-place; restore on backtrack; remove found words from Trie to avoid dups.
     * Time: O(M*N * 4^L) where L = max word length, Space: O(total chars in dict)
     */
    public List<String> findWords(char[][] board, String[] words) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Replace Words (LC 648)
     * Approach: Build Trie of roots; for each word in sentence, find shortest matching prefix.
     * Invariant: traverse Trie; return prefix when isEnd hit; else return original word.
     * Time: O(D + S) where D = dict chars, S = sentence chars, Space: O(D * 26)
     */
    public String replaceWords(List<String> dictionary, String sentence) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Maximum XOR of Two Numbers in an Array (LC 421) — XOR Trie
     * Approach: Build Trie of 32-bit representations; for each number, greedily pick opposite bit.
     * Invariant: prefer bit that maximizes XOR at each Trie level (greedy bit-by-bit).
     * Time: O(n * 32), Space: O(n * 32)
     */
    public int findMaximumXOR(int[] nums) {
        throw new UnsupportedOperationException("stub");
    }

    // =========================================================
    // PATTERN 14 — GRAPHS: BFS + DFS (Stubs)
    // =========================================================

    /**
     * Number of Islands (LC 200)
     * Approach: DFS/BFS from each unvisited '1'; mark connected land as visited.
     * Invariant: each DFS call visits all land cells in one island.
     * Time: O(m*n), Space: O(m*n)
     */
    public int numIslands(char[][] grid) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Clone Graph (LC 133)
     * Approach: BFS/DFS with a HashMap mapping original → clone node.
     * Invariant: map prevents infinite loops and ensures each node cloned exactly once.
     * Time: O(V+E), Space: O(V)
     */
    // Uses generic Node — stubbed as comment
    // public Node cloneGraph(Node node) { throw new UnsupportedOperationException("stub"); }

    /**
     * Pacific Atlantic Water Flow (LC 417)
     * Approach: Reverse BFS from Pacific border cells and Atlantic border cells separately; intersect results.
     * Invariant: water flows from high to low; reverse = flow from ocean inward (low to high).
     * Time: O(m*n), Space: O(m*n)
     */
    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Course Schedule — cycle detection (LC 207)
     * Approach: DFS with 3-state coloring (0=unvisited, 1=in-progress, 2=done); cycle = entering in-progress node.
     * Invariant: back edge in DFS = cycle in directed graph.
     * Time: O(V+E), Space: O(V+E)
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Bipartite Check (LC 785)
     * Approach: BFS coloring with 2 colors; if adjacent nodes share a color → not bipartite.
     * Invariant: graph is bipartite iff it contains no odd-length cycles.
     * Time: O(V+E), Space: O(V)
     */
    public boolean isBipartite(int[][] graph) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Word Ladder (LC 127)
     * Approach: BFS level by level; for each word, try all 26 substitutions per position.
     * Invariant: BFS guarantees shortest transformation sequence.
     * Time: O(M^2 * N) where M = word length, N = dict size, Space: O(M^2 * N)
     */
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        throw new UnsupportedOperationException("stub");
    }

    // =========================================================
    // PATTERN 15 — GRAPHS: TOPOLOGICAL SORT (Stubs)
    // =========================================================

    /**
     * Course Schedule II — Kahn's BFS (LC 210)
     * Approach: Build in-degree array; BFS from 0-in-degree nodes; append to order; decrement neighbors.
     * Invariant: valid topological order exists iff final order contains all n nodes.
     * Time: O(V+E), Space: O(V+E)
     */
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Alien Dictionary (LC 269 / classic)
     * Approach: Build graph from adjacent word pairs (first differing char = edge); Kahn's topo sort.
     * Invariant: invalid input if word1 is prefix of word2 but appears after (e.g., "abc" before "ab").
     * Time: O(C) where C = total chars in all words, Space: O(U+min(U^2, N)) U=unique chars
     */
    public String alienOrder(String[] words) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Sequence Reconstruction (LC 444 / classic)
     * Approach: Build graph from sequences; check if topo sort yields unique order matching org.
     * Invariant: unique topo order exists iff at every step exactly one node has in-degree 0.
     * Time: O(V + total sequence length), Space: O(V+E)
     */
    public boolean sequenceReconstruction(int[] org, List<List<Integer>> sequences) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Parallel Courses — longest path in DAG (LC 1136)
     * Approach: Kahn's topo sort; track longest path to each node (dp[v] = max(dp[v], dp[u]+1)).
     * Invariant: semester count = length of longest chain in the DAG.
     * Time: O(V+E), Space: O(V+E)
     */
    public int minimumSemesters(int n, int[][] relations) {
        throw new UnsupportedOperationException("stub");
    }

    // =========================================================
    // PATTERN 16 — GRAPHS: SHORTEST PATH (Stubs)
    // =========================================================

    /**
     * Dijkstra — single source shortest path
     * Approach: Min-heap; relax edges greedily; skip stale heap entries (dist > dist[u]).
     * Invariant: once a node is popped from heap, its shortest distance is finalized.
     * Time: O((V+E) log V), Space: O(V+E)
     */
    public int[] dijkstra(int n, List<List<int[]>> adj, int src) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Bellman-Ford — handles negative weights
     * Approach: Relax all edges V-1 times; Vth relaxation detects negative cycle.
     * Invariant: after k iterations, shortest paths using at most k edges are correct.
     * Time: O(V*E), Space: O(V)
     */
    public int[] bellmanFord(int n, int[][] edges, int src) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Network Delay Time (LC 743)
     * Approach: Dijkstra from node k; answer = max of all dist[] values (or -1 if unreachable).
     * Invariant: all edge weights > 0, so Dijkstra applies directly.
     * Time: O((V+E) log V), Space: O(V+E)
     */
    public int networkDelayTime(int[][] times, int n, int k) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Cheapest Flights Within K Stops (LC 787)
     * Approach: Bellman-Ford with exactly k+1 iterations; copy dist[] at start of each iteration.
     * Invariant: dist[v] after i iterations = cheapest flight using at most i edges.
     * Time: O(K*E), Space: O(V)
     */
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Swim in Rising Water (LC 778)
     * Approach: Binary search on time t + BFS/DFS feasibility; or Dijkstra (min max-weight path).
     * Invariant: can reach (n-1,n-1) iff there exists a path where all cells ≤ t.
     * Time: O(n^2 log n), Space: O(n^2)
     */
    public int swimInWater(int[][] grid) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Number of Ways to Arrive at Destination (LC 1976)
     * Approach: Dijkstra; also track count[] of shortest paths; update count when equal dist found.
     * Invariant: count[v] += count[u] whenever dist[u] + w == dist[v].
     * Time: O((V+E) log V), Space: O(V+E)
     */
    public int countPaths(int n, int[][] roads) {
        throw new UnsupportedOperationException("stub");
    }

    // =========================================================
    // PATTERN 17 — GRAPHS: MST (Stubs)
    // =========================================================

    /**
     * Min Cost to Connect All Points (LC 1584)
     * Approach: Prim's O(n^2) with visited array; or Kruskal with DSU on sorted edges.
     * Invariant: at each step, add cheapest edge connecting unvisited node to MST.
     * Time: O(n^2) Prim or O(n^2 log n) Kruskal, Space: O(n^2)
     */
    public int minCostConnectPoints(int[][] points) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Minimum Spanning Tree — generic Kruskal
     * Approach: Sort edges by weight; union-find to avoid cycles; add edge if different components.
     * Invariant: greedy choice of cheapest edge that doesn't form cycle is always safe (matroid).
     * Time: O(E log E), Space: O(V)
     */
    public int kruskalMST(int n, int[][] edges) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Find Critical and Pseudo-Critical Edges in MST (LC 1489)
     * Approach: Critical = MST weight increases when edge removed; Pseudo = MST weight same when edge forced in.
     * Invariant: use DSU for Kruskal; run O(E) passes each excluding/including one edge.
     * Time: O(E^2 * alpha(V)), Space: O(V)
     */
    public List<List<Integer>> findCriticalAndPseudoCriticalEdges(int n, int[][] edges) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Remove Max Edges to Keep Graph Traversable (LC 1579)
     * Approach: Greedy; add type-3 edges first (shared), then type-1 for Alice, type-2 for Bob.
     * Invariant: remove edge only if both Alice and Bob are already connected without it.
     * Time: O(E * alpha(V)), Space: O(V)
     */
    public int maxNumEdgesToRemove(int n, int[][] edges) {
        throw new UnsupportedOperationException("stub");
    }

    // =========================================================
    // PATTERN 18 — UNION-FIND (DSU) (Stubs + Inner Class)
    // =========================================================

    static class DSU {
        private final int[] parent, rank;
        private int components;

        DSU(int n) {
            parent = new int[n];
            rank   = new int[n];
            components = n;
            for (int i = 0; i < n; i++) parent[i] = i;
        }

        int find(int x) {
            if (parent[x] != x) parent[x] = find(parent[x]); // path compression
            return parent[x];
        }

        boolean union(int x, int y) {
            int px = find(x), py = find(y);
            if (px == py) return false; // already connected
            if (rank[px] < rank[py]) { int t = px; px = py; py = t; }
            parent[py] = px;
            if (rank[px] == rank[py]) rank[px]++;
            components--;
            return true;
        }

        boolean connected(int x, int y) { return find(x) == find(y); }
        int getComponents() { return components; }
    }

    /**
     * Number of Provinces (LC 547)
     * Approach: DSU; for each edge in adjacency matrix, union the two nodes.
     * Invariant: number of provinces = number of distinct roots after all unions.
     * Time: O(n^2 * alpha(n)), Space: O(n)
     */
    public int findCircleNum(int[][] isConnected) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Redundant Connection (LC 684)
     * Approach: Process edges in order; first edge where both nodes already in same component = redundant.
     * Invariant: edge creates a cycle iff both endpoints share a root before the union.
     * Time: O(E * alpha(V)), Space: O(V)
     */
    public int[] findRedundantConnection(int[][] edges) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Accounts Merge (LC 721)
     * Approach: DSU on emails; map each email to one representative account; group by root.
     * Invariant: all emails in same account after merge share the same DSU root.
     * Time: O(n * k * alpha(n*k)) where k = avg emails per account, Space: O(n*k)
     */
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Most Stones Removed with Same Row or Column (LC 947)
     * Approach: DSU on rows and columns (offset cols by n to avoid collision); count non-root stones.
     * Invariant: stones in same component can be reduced to 1; answer = total - components.
     * Time: O(n * alpha(n)), Space: O(n)
     */
    public int removeStones(int[][] stones) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Evaluate Division — weighted DSU (LC 399)
     * Approach: DSU with weights; union a/b = val means weight[a] / weight[find(a)] tracks ratio to root.
     * Invariant: query a/b = weight[a] / weight[b] if same component; -1 otherwise.
     * Time: O((Q+E) * alpha(V)), Space: O(V)
     */
    public double[] calcEquation(List<List<String>> equations,
                                  double[] values,
                                  List<List<String>> queries) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Making a Large Island (LC 827)
     * Approach: Label each island with an id; for each 0-cell, compute sum of adjacent distinct island sizes + 1.
     * Invariant: DSU assigns root = island label; size[] tracks component size.
     * Time: O(n^2), Space: O(n^2)
     */
    public int largestIsland(int[][] grid) {
        throw new UnsupportedOperationException("stub");
    }

    // =========================================================
    // PATTERN 19 — BIT MANIPULATION (Stubs)
    // =========================================================

    /**
     * Single Number I (LC 136)
     * Approach: XOR all elements; pairs cancel (a ^ a = 0); lone element remains.
     * Invariant: XOR is commutative and associative; a ^ 0 = a.
     * Time: O(n), Space: O(1)
     */
    public int singleNumber(int[] nums) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Single Number II (LC 137) — appears 3 times except one
     * Approach: Count bits modulo 3 for each of 32 positions; reconstruct the answer.
     * Invariant: bit appears 3k or 3k+1 times; 3k+1 means it belongs to the single number.
     * Time: O(32n) = O(n), Space: O(1)
     */
    public int singleNumberII(int[] nums) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Single Number III (LC 260) — two numbers appear once
     * Approach: XOR all → xor of two uniques; find any set bit; partition array by that bit; XOR each group.
     * Invariant: the differing bit separates the two unique numbers into different groups.
     * Time: O(n), Space: O(1)
     */
    public int[] singleNumberIII(int[] nums) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Counting Bits (LC 338)
     * Approach: dp[i] = dp[i >> 1] + (i & 1); even numbers have same bit count as i/2.
     * Invariant: right-shifting removes LSB; parity of LSB adds 0 or 1.
     * Time: O(n), Space: O(n)
     */
    public int[] countBits(int n) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Reverse Bits (LC 190)
     * Approach: Take LSB, shift into result from MSB position; repeat 32 times.
     * Invariant: use >>> (unsigned right shift) to avoid sign extension.
     * Time: O(32) = O(1), Space: O(1)
     */
    public int reverseBits(int n) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Number of 1 Bits (LC 191) — Hamming weight
     * Approach: n & (n-1) clears the lowest set bit; count iterations until n == 0.
     * Invariant: each iteration removes exactly one set bit.
     * Time: O(number of set bits), Space: O(1)
     */
    public int hammingWeight(int n) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Sum of Two Integers without + or - (LC 371)
     * Approach: a ^ b gives sum without carry; (a & b) << 1 gives carry; repeat until carry == 0.
     * Invariant: carry propagates left; process terminates in O(32) iterations.
     * Time: O(1) for 32-bit ints, Space: O(1)
     */
    public int getSum(int a, int b) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Subsets via bitmask (LC 78)
     * Approach: Enumerate 0..2^n - 1; bit i set → include nums[i] in subset.
     * Invariant: each bitmask uniquely encodes one subset; 2^n total subsets.
     * Time: O(2^n * n), Space: O(2^n * n)
     */
    // Already implemented above in Pattern 3 (subsets method)

    /**
     * Maximum XOR of Two Numbers — Trie approach (LC 421)
     * Approach: Insert all numbers into XOR Trie; for each number greedily pick opposite bit path.
     * Invariant: opposite bit at each level maximizes XOR contribution from that bit position.
     * Time: O(n * 32), Space: O(n * 32)
     */
    // Already stubbed in Pattern 13 (findMaximumXOR method)

    // =========================================================
    // PATTERN 20 — STRING ALGORITHMS + PARSING (Stubs)
    // =========================================================

    /**
     * KMP — build failure function + search (classic)
     * Approach: failure[i] = length of longest proper prefix of pattern[0..i] that is also a suffix.
     * Invariant: on mismatch, jump to failure[j-1] instead of restarting; never re-examine matched chars.
     * Time: O(n + m) where n = text length, m = pattern length, Space: O(m)
     */
    public List<Integer> kmpSearch(String text, String pattern) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Rabin-Karp — rolling hash search
     * Approach: Hash pattern; slide window hash over text; on hash match, verify with string compare.
     * Invariant: rolling hash update: remove leftmost char contribution, add new rightmost char.
     * Time: O(n + m) average, O(n*m) worst case (many hash collisions), Space: O(1)
     */
    public List<Integer> rabinKarp(String text, String pattern) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Basic Calculator II (LC 227) — +, -, *, / without parentheses
     * Approach: Stack; on * or /, pop and apply immediately; on + or -, push signed value; sum stack at end.
     * Invariant: higher-precedence ops (* /) resolved immediately; + and - deferred to final sum.
     * Time: O(n), Space: O(n)
     */
    public int calculate(String s) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Decode Ways (LC 91)
     * Approach: DP; dp[i] = ways to decode s[0..i-1]; check single-digit and two-digit decodings.
     * Invariant: dp[i] += dp[i-1] if s[i-1] != '0'; dp[i] += dp[i-2] if s[i-2..i-1] in [10,26].
     * Time: O(n), Space: O(n) or O(1) with two variables
     */
    public int numDecodings(String s) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Longest Valid Parentheses (LC 32)
     * Approach: Stack of indices; push -1 as base; on '(' push index; on ')' pop and compute length.
     * Invariant: stack bottom always holds the index of last unmatched ')'.
     * Time: O(n), Space: O(n)
     */
    public int longestValidParentheses(String s) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Regular Expression Matching (LC 10) — Hard
     * Approach: 2D DP; dp[i][j] = true if pattern[0..j-1] matches s[0..i-1].
     * Invariant: '*' in pattern can match 0 or more of preceding element; handle '.' as wildcard.
     * Time: O(m*n), Space: O(m*n)
     */
    public boolean isMatch(String s, String p) {
        throw new UnsupportedOperationException("stub");
    }

    // =========================================================
    // PATTERN 21 — DESIGN-ISH DSA (Inner Classes + Stubs)
    // =========================================================

    /**
     * LRU Cache (LC 146)
     * HashMap + doubly linked list for O(1) get and put.
     * Most recently used at head; least recently used at tail.
     * On get: move node to head. On put: add to head; if over capacity, evict tail.
     */
    static class LRUCache {
        private final int capacity;
        private final Map<Integer, LRUNode> map = new HashMap<>();

        // sentinel head and tail nodes simplify edge cases
        private final LRUNode head = new LRUNode(0, 0);
        private final LRUNode tail = new LRUNode(0, 0);

        static class LRUNode {
            int key, val;
            LRUNode prev, next;
            LRUNode(int k, int v) { key = k; val = v; }
        }

        LRUCache(int capacity) {
            this.capacity = capacity;
            head.next = tail;
            tail.prev = head;
        }

        public int get(int key) {
            LRUNode node = map.get(key);
            if (node == null) return -1;
            moveToHead(node);
            return node.val;
        }

        public void put(int key, int value) {
            LRUNode node = map.get(key);
            if (node != null) {
                node.val = value;
                moveToHead(node);
            } else {
                LRUNode newNode = new LRUNode(key, value);
                map.put(key, newNode);
                addToHead(newNode);
                if (map.size() > capacity) {
                    LRUNode evicted = removeTail();
                    map.remove(evicted.key);
                }
            }
        }

        private void addToHead(LRUNode node) {
            node.prev = head;
            node.next = head.next;
            head.next.prev = node;
            head.next = node;
        }

        private void removeNode(LRUNode node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        private void moveToHead(LRUNode node) {
            removeNode(node);
            addToHead(node);
        }

        private LRUNode removeTail() {
            LRUNode evicted = tail.prev;
            removeNode(evicted);
            return evicted;
        }
    }

    /**
     * LFU Cache (LC 460) — Hard
     * HashMap<key, value>, HashMap<key, freq>, HashMap<freq, LinkedHashSet<key>>.
     * Track minFreq; on get/put, update freq bucket and minFreq.
     * LinkedHashSet preserves insertion order within same frequency → evict oldest on tie.
     */
    static class LFUCache {
        private final int capacity;
        private int minFreq = 0;
        private final Map<Integer, Integer> keyToVal  = new HashMap<>();
        private final Map<Integer, Integer> keyToFreq = new HashMap<>();
        private final Map<Integer, LinkedHashSet<Integer>> freqToKeys = new HashMap<>();

        LFUCache(int capacity) {
            this.capacity = capacity;
        }

        public int get(int key) {
            if (!keyToVal.containsKey(key)) return -1;
            increaseFreq(key);
            return keyToVal.get(key);
        }

        public void put(int key, int value) {
            if (capacity <= 0) return;
            if (keyToVal.containsKey(key)) {
                keyToVal.put(key, value);
                increaseFreq(key);
            } else {
                if (keyToVal.size() >= capacity) evictLFU();
                keyToVal.put(key, value);
                keyToFreq.put(key, 1);
                freqToKeys.computeIfAbsent(1, k -> new LinkedHashSet<>()).add(key);
                minFreq = 1;
            }
        }

        private void increaseFreq(int key) {
            int freq = keyToFreq.get(key);
            keyToFreq.put(key, freq + 1);
            freqToKeys.get(freq).remove(key);
            if (freqToKeys.get(freq).isEmpty()) {
                freqToKeys.remove(freq);
                if (minFreq == freq) minFreq++;
            }
            freqToKeys.computeIfAbsent(freq + 1, k -> new LinkedHashSet<>()).add(key);
        }

        private void evictLFU() {
            LinkedHashSet<Integer> minFreqKeys = freqToKeys.get(minFreq);
            int evictKey = minFreqKeys.iterator().next(); // oldest key at minFreq
            minFreqKeys.remove(evictKey);
            if (minFreqKeys.isEmpty()) freqToKeys.remove(minFreq);
            keyToVal.remove(evictKey);
            keyToFreq.remove(evictKey);
        }
    }

    /**
     * Sliding Window Median (LC 480)
     * Approach: Two heaps (maxHeap lower half, minHeap upper half); remove by lazy deletion.
     * Invariant: size difference between heaps ≤ 1; delayed removals tracked in a HashMap.
     * Time: O(n log k), Space: O(k)
     */
    public double[] medianSlidingWindow(int[] nums, int k) {
        throw new UnsupportedOperationException("stub");
    }

    /**
     * Design Twitter (LC 355)
     * Approach: HashMap<userId, list of (timestamp, tweetId)>; follow/unfollow set per user.
     *           getNewsFeed: min-heap merging top-10 across all followees.
     * Invariant: global timestamp ensures correct recency ordering across users.
     * Time: O(F log F) getNewsFeed where F = followees, Space: O(U + T)
     */
    static class Twitter {
        // stub
        public void postTweet(int userId, int tweetId) {
            throw new UnsupportedOperationException("stub");
        }
        public List<Integer> getNewsFeed(int userId) {
            throw new UnsupportedOperationException("stub");
        }
        public void follow(int followerId, int followeeId) {
            throw new UnsupportedOperationException("stub");
        }
        public void unfollow(int followerId, int followeeId) {
            throw new UnsupportedOperationException("stub");
        }
    }

    /**
     * All O`one Data Structure (LC 432)
     * Approach: Doubly linked list of frequency buckets + HashMap<key, bucketNode>.
     *           Increment/decrement moves key to adjacent bucket in O(1).
     * Invariant: list is sorted by frequency; head = min freq bucket, tail = max freq bucket.
     * Time: O(1) all ops, Space: O(n)
     */
    static class AllOne {
        // stub
        public void inc(String key) {
            throw new UnsupportedOperationException("stub");
        }
        public void dec(String key) {
            throw new UnsupportedOperationException("stub");
        }
        public String getMaxKey() {
            throw new UnsupportedOperationException("stub");
        }
        public String getMinKey() {
            throw new UnsupportedOperationException("stub");
        }
    }

    /**
     * Random Pick with Weight (LC 528)
     * Approach: Build prefix sum array of weights; binary search for random value in [0, totalWeight).
     * Invariant: each index i is selected with probability weights[i] / totalWeight.
     * Time: O(n) init, O(log n) pickIndex, Space: O(n)
     */
    static class WeightedRandom {
        private final int[] prefixSums;
        private final int   total;
        private final Random rand = new Random();

        WeightedRandom(int[] w) {
            prefixSums = new int[w.length];
            prefixSums[0] = w[0];
            for (int i = 1; i < w.length; i++) prefixSums[i] = prefixSums[i - 1] + w[i];
            total = prefixSums[w.length - 1];
        }

        public int pickIndex() {
            int target = rand.nextInt(total) + 1; // [1, total]
            int lo = 0, hi = prefixSums.length - 1;
            while (lo < hi) {
                int mid = lo + (hi - lo) / 2;
                if (prefixSums[mid] < target) lo = mid + 1;
                else hi = mid;
            }
            return lo;
        }
    }

    // =========================================================
    // main — smoke test entry point
    // =========================================================

    public static void main(String[] args) {
        System.out.println("PatternPracticePack loaded — 21 patterns, MANG Staff 2026");

        PatternPracticePack p = new PatternPracticePack();

        // ── Pattern 1 smoke tests ─────────────────────────────
        assert "BANC".equals(p.minWindow("ADOBECODEBANC", "ABC"))
            : "minWindow failed";
        assert p.characterReplacement("ABAB", 2) == 4
            : "characterReplacement failed";
        assert p.subarraysWithKDistinct(new int[]{1,2,1,2,3}, 2) == 7
            : "subarraysWithKDistinct failed";

        // ── Pattern 2 smoke tests ─────────────────────────────
        assert Math.abs(p.findMedianSortedArrays(new int[]{1,3}, new int[]{2}) - 2.0) < 1e-9
            : "findMedianSortedArrays failed";
        assert p.shipWithinDays(new int[]{1,2,3,4,5,6,7,8,9,10}, 5) == 15
            : "shipWithinDays failed";
        assert p.search(new int[]{4,5,6,7,0,1,2}, 0) == 4
            : "search rotated failed";

        // ── Pattern 3 smoke tests ─────────────────────────────
        assert p.generateParenthesis(3).size() == 5
            : "generateParenthesis failed";
        assert p.subsets(new int[]{1,2,3}).size() == 8
            : "subsets failed";
        assert p.kthGrammar(4, 5) == 1
            : "kthGrammar failed";

        // ── Pattern 4 smoke tests ─────────────────────────────
        assert p.largestRectangleArea(new int[]{2,1,5,6,2,3}) == 10
            : "largestRectangleArea failed";
        assert p.trap(new int[]{0,1,0,2,1,0,1,3,2,1,2,1}) == 6
            : "trap failed";
        assert Arrays.equals(p.dailyTemperatures(new int[]{73,74,75,71,69,72,76,73}),
                             new int[]{1,1,4,2,1,1,0,0})
            : "dailyTemperatures failed";

        // ── Pattern 5 smoke tests ─────────────────────────────
        assert p.knapsack(new int[]{1,3,4,5}, new int[]{1,4,5,7}, 7) == 9
            : "knapsack failed";
        assert p.longestCommonSubsequence("abcde", "ace") == 3
            : "LCS failed";
        assert p.maxCoins(new int[]{3,1,5,8}) == 167
            : "maxCoins failed";

        // ── Pattern 6 smoke tests ─────────────────────────────
        MedianFinder mf = new MedianFinder();
        mf.addNum(1); mf.addNum(2);
        assert Math.abs(mf.findMedian() - 1.5) < 1e-9 : "MedianFinder failed";
        mf.addNum(3);
        assert Math.abs(mf.findMedian() - 2.0) < 1e-9 : "MedianFinder failed";

        assert p.leastInterval(new char[]{'A','A','A','B','B','B'}, 2) == 8
            : "leastInterval failed";

        // ── Pattern 7 smoke tests ─────────────────────────────
        assert p.solveNQueens(4).size() == 2
            : "solveNQueens failed";
        char[][] sudoku = {
            {'5','3','.','.','7','.','.','.','.'},
            {'6','.','.','1','9','5','.','.','.'},
            {'.','9','8','.','.','.','.','6','.'},
            {'8','.','.','.','6','.','.','.','3'},
            {'4','.','.','8','.','3','.','.','1'},
            {'7','.','.','.','2','.','.','.','6'},
            {'.','6','.','.','.','.','2','8','.'},
            {'.','.','.','4','1','9','.','.','5'},
            {'.','.','.','.','8','.','.','7','9'}
        };
        p.solveSudoku(sudoku);
        assert sudoku[0][2] == '4' : "solveSudoku failed";

        assert p.exist(new char[][]{{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}}, "ABCCED")
            : "wordSearch failed";

        // ── Pattern 8 smoke tests ─────────────────────────────
        int[][] merged = p.merge(new int[][]{{1,3},{2,6},{8,10},{15,18}});
        assert merged.length == 3 : "merge failed";
        assert Arrays.equals(p.productExceptSelf(new int[]{1,2,3,4}), new int[]{24,12,8,6})
            : "productExceptSelf failed";
        assert p.maxArea(new int[]{1,8,6,2,5,4,8,3,7}) == 49
            : "maxArea failed";

        // ── LRU Cache smoke test ──────────────────────────────
        LRUCache lru = new LRUCache(2);
        lru.put(1, 1); lru.put(2, 2);
        assert lru.get(1) == 1 : "LRU get failed";
        lru.put(3, 3);           // evicts key 2
        assert lru.get(2) == -1 : "LRU eviction failed";

        // ── LFU Cache smoke test ──────────────────────────────
        LFUCache lfu = new LFUCache(2);
        lfu.put(1, 1); lfu.put(2, 2);
        assert lfu.get(1) == 1 : "LFU get failed";
        lfu.put(3, 3);           // evicts key 2 (freq 1, oldest)
        assert lfu.get(2) == -1 : "LFU eviction failed";
        assert lfu.get(3) == 3  : "LFU get 3 failed";

        // ── WeightedRandom smoke test (distribution check) ────
        WeightedRandom wr = new WeightedRandom(new int[]{1, 3});
        int count0 = 0;
        for (int i = 0; i < 4000; i++) if (wr.pickIndex() == 0) count0++;
        // index 0 should be picked ~25% of the time; allow wide margin
        assert count0 > 500 && count0 < 1500 : "WeightedRandom distribution failed";

        System.out.println("All smoke tests passed.");
    }
}
