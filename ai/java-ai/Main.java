package com.example.multiagent;

import com.example.multiagent.model.AgentMessage;
import com.example.multiagent.orchestrator.OrchestratorAgent;

/**
 * Multi-Agent Coding Example using the Anthropic API.
 *
 * Architecture:
 *
 *   ┌─────────────────────────────────────────────┐
 *   │              OrchestratorAgent               │
 *   │  - Breaks down high-level task               │
 *   │  - Routes sub-tasks to specialized agents    │
 *   │  - Aggregates results                        │
 *   └──────┬──────────────┬───────────────┬────────┘
 *          │              │               │
 *   ┌──────▼──────┐ ┌─────▼──────┐ ┌────▼───────┐
 *   │  CoderAgent │ │ReviewAgent │ │  TestAgent  │
 *   │  Writes code│ │Reviews code│ │Writes tests │
 *   └─────────────┘ └────────────┘ └────────────┘
 *
 * Flow:
 *   1. User gives a high-level coding task
 *   2. Orchestrator plans: [code → review → test]
 *   3. CoderAgent generates implementation
 *   4. ReviewAgent critiques and suggests fixes
 *   5. CoderAgent applies fixes
 *   6. TestAgent writes unit tests
 *   7. Orchestrator assembles final output
 *
 * Usage:
 *   Set ANTHROPIC_API_KEY environment variable, then run Main.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        String apiKey = System.getenv("ANTHROPIC_API_KEY");
        if (apiKey == null || apiKey.isBlank()) {
            System.err.println("ERROR: Set the ANTHROPIC_API_KEY environment variable.");
            System.exit(1);
        }

        // The high-level task given to the orchestrator
        String userTask = """
                Implement a Java class called RateLimiter that uses the Token Bucket algorithm.
                Requirements:
                - Thread-safe
                - Configurable: capacity and refill-rate (tokens/second)
                - Method: boolean tryAcquire()  — returns true if a token is available
                - Method: boolean tryAcquire(int tokens) — acquire multiple tokens atomically
                - Graceful handling of edge cases (zero capacity, negative tokens)
                """;

        System.out.println("═".repeat(70));
        System.out.println("  MULTI-AGENT CODING PIPELINE");
        System.out.println("═".repeat(70));
        System.out.println("Task:\n" + userTask);
        System.out.println("═".repeat(70));

        OrchestratorAgent orchestrator = new OrchestratorAgent(apiKey);
        AgentMessage result = orchestrator.execute(userTask);

        System.out.println("\n" + "═".repeat(70));
        System.out.println("  FINAL OUTPUT");
        System.out.println("═".repeat(70));
        System.out.println(result.content());
    }
}
