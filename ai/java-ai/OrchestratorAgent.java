package com.example.multiagent.orchestrator;

import com.example.multiagent.agent.CoderAgent;
import com.example.multiagent.agent.ReviewAgent;
import com.example.multiagent.agent.TestAgent;
import com.example.multiagent.model.AgentMessage;
import com.example.multiagent.tool.AnthropicClient;

import java.io.IOException;

/**
 * OrchestratorAgent — coordinates the multi-agent coding pipeline.
 *
 * Pipeline stages:
 *
 *   Stage 1 — PLAN
 *     Orchestrator breaks the user task into a clear spec for CoderAgent.
 *
 *   Stage 2 — CODE
 *     CoderAgent writes the initial implementation.
 *
 *   Stage 3 — REVIEW (with optional revision loop)
 *     ReviewAgent reviews the code.
 *     If REVISE: CoderAgent applies feedback (max MAX_REVISION_LOOPS times).
 *     If APPROVE: proceed to Stage 4.
 *
 *   Stage 4 — TEST
 *     TestAgent writes JUnit 5 tests for the approved implementation.
 *
 *   Stage 5 — ASSEMBLE
 *     Orchestrator formats and returns the final output.
 *
 * The orchestrator itself also calls Claude to plan and assemble,
 * making it a true "agent" and not just a router.
 */
public class OrchestratorAgent {

    private static final int MAX_REVISION_LOOPS = 2;

    private static final String SYSTEM_PROMPT = """
            You are a software engineering team lead coordinating a coding pipeline.
            You receive high-level tasks and translate them into precise specifications
            for a coding agent.

            When planning:
            - Identify all functional requirements
            - List explicit constraints (thread-safety, performance, API surface)
            - Flag potential edge cases the coder must handle
            - Be concise and directive — the coder follows your spec exactly

            When assembling the final output:
            - Present the implementation code first
            - Follow with the test class
            - Add a brief summary of what was built and any notable design decisions
            """;

    private final AnthropicClient client;
    private final CoderAgent      coder;
    private final ReviewAgent     reviewer;
    private final TestAgent       tester;

    public OrchestratorAgent(String apiKey) {
        this.client   = new AnthropicClient(apiKey);
        this.coder    = new CoderAgent(client);
        this.reviewer = new ReviewAgent(client);
        this.tester   = new TestAgent(client);
    }

    /**
     * Execute the full multi-agent pipeline for a coding task.
     *
     * @param userTask  The high-level task description from the user
     * @return          Final assembled output (implementation + tests + summary)
     */
    public AgentMessage execute(String userTask) throws IOException, InterruptedException {

        // ── Stage 1: PLAN ────────────────────────────────────────────────────
        System.out.println("\n── Stage 1: PLANNING ──────────────────────────────────");
        AgentMessage spec = plan(userTask);
        System.out.println("Spec prepared.\n");

        // ── Stage 2: INITIAL CODE ────────────────────────────────────────────
        System.out.println("── Stage 2: CODING ────────────────────────────────────");
        AgentMessage code = coder.process(spec);

        // ── Stage 3: REVIEW + OPTIONAL REVISION LOOP ────────────────────────
        System.out.println("\n── Stage 3: REVIEW ────────────────────────────────────");
        AgentMessage finalCode = reviewLoop(code);

        // ── Stage 4: TEST ────────────────────────────────────────────────────
        System.out.println("\n── Stage 4: TESTING ───────────────────────────────────");
        AgentMessage tests = tester.process(finalCode);

        // ── Stage 5: ASSEMBLE ────────────────────────────────────────────────
        System.out.println("\n── Stage 5: ASSEMBLING ────────────────────────────────");
        return assemble(userTask, finalCode, tests);
    }

    // ── Private pipeline stages ───────────────────────────────────────────────

    /**
     * Stage 1: Orchestrator plans — converts user task into a precise spec.
     */
    private AgentMessage plan(String userTask) throws IOException, InterruptedException {
        System.out.println("[Orchestrator   ] planning task…");

        String planPrompt = """
                A developer has requested the following:

                %s

                Write a precise, structured specification for a CoderAgent to implement this.
                Include: class name, method signatures with types, thread-safety requirements,
                edge cases to handle, and any design constraints.
                """.formatted(userTask);

        String spec = client.chat(SYSTEM_PROMPT,
                java.util.List.of(
                        java.util.Map.of("role", "user", "content", planPrompt)
                ));

        System.out.println("[Orchestrator   ] spec ready (%d chars)".formatted(spec.length()));
        return AgentMessage.user("Orchestrator", spec);
    }

    /**
     * Stage 3: Review loop — review → revise if needed, up to MAX_REVISION_LOOPS.
     */
    private AgentMessage reviewLoop(AgentMessage code)
            throws IOException, InterruptedException {

        AgentMessage currentCode = code;

        for (int pass = 1; pass <= MAX_REVISION_LOOPS; pass++) {
            System.out.printf("[Orchestrator   ] review pass %d/%d%n", pass, MAX_REVISION_LOOPS);

            AgentMessage review = reviewer.process(currentCode);
            System.out.println("\n── Review ─────────────────────────────────────────────");
            System.out.println(review.content());
            System.out.println("───────────────────────────────────────────────────────");

            if (reviewer.isApproved(review.content())) {
                System.out.println("[Orchestrator   ] APPROVED — proceeding to tests.");
                return currentCode;
            }

            if (pass == MAX_REVISION_LOOPS) {
                System.out.println("[Orchestrator   ] max revisions reached — using latest code.");
                return currentCode;
            }

            // Ask CoderAgent to apply the review feedback
            System.out.println("[Orchestrator   ] requesting revision from CoderAgent…");
            String revisionPrompt = """
                    The following review feedback was provided for your code.
                    Apply ALL critical issues and improvements, then return the complete revised file.

                    REVIEW FEEDBACK:
                    %s
                    """.formatted(review.content());

            currentCode = coder.process(AgentMessage.user("Orchestrator", revisionPrompt));
        }

        return currentCode;
    }

    /**
     * Stage 5: Assemble — format the final combined output.
     */
    private AgentMessage assemble(String originalTask,
                                  AgentMessage finalCode,
                                  AgentMessage tests)
            throws IOException, InterruptedException {

        System.out.println("[Orchestrator   ] assembling final output…");

        String assemblePrompt = """
                The coding pipeline is complete. Assemble a final deliverable for this task:

                ORIGINAL TASK:
                %s

                IMPLEMENTATION:
                %s

                TESTS:
                %s

                Write a concise summary (3–5 sentences) of what was built, key design decisions,
                and any caveats. Then present the implementation and test files clearly labeled.
                """.formatted(originalTask, finalCode.content(), tests.content());

        String assembled = client.chat(SYSTEM_PROMPT,
                java.util.List.of(
                        java.util.Map.of("role", "user", "content", assemblePrompt)
                ));

        return AgentMessage.assistant("Orchestrator", assembled);
    }
}
