package com.example.multiagent.agent;

import com.example.multiagent.model.AgentMessage;
import com.example.multiagent.tool.AnthropicClient;

import java.io.IOException;

/**
 * ReviewAgent — reviews Java code and produces structured, actionable feedback.
 *
 * Responsibilities:
 *  - Identify correctness bugs, race conditions, and edge case gaps
 *  - Flag style and API design issues
 *  - Score confidence 1–10 and decide if another revision pass is needed
 *  - Never rewrite the code itself — only produce a review
 *
 * The orchestrator reads the review and decides whether to loop back to CoderAgent.
 */
public class ReviewAgent extends BaseAgent {

    private static final String SYSTEM_PROMPT = """
            You are a senior Java code reviewer at a top-tier tech company.
            Your reviews are precise, actionable, and prioritized.

            When reviewing code, structure your response EXACTLY as follows:

            ## SCORE
            X/10  (where 10 = ship immediately, 1 = rewrite from scratch)

            ## CRITICAL ISSUES  (must fix — correctness/safety)
            - <issue>: <explanation and fix>

            ## IMPROVEMENTS  (should fix — quality/clarity)
            - <issue>: <explanation and fix>

            ## POSITIVES
            - <what is done well>

            ## VERDICT
            APPROVE  — if score >= 8 and no critical issues
            REVISE   — if score < 8 or any critical issues exist

            Be brutally honest. If there are no issues in a category, write "None."
            Do not write code in your review — only describe changes needed.
            """;

    public ReviewAgent(AnthropicClient client) {
        super("ReviewAgent", SYSTEM_PROMPT, client);
    }

    /**
     * Receives code from CoderAgent and returns a structured review.
     */
    @Override
    public AgentMessage process(AgentMessage input)
            throws IOException, InterruptedException {
        log("reviewing code (%d chars)".formatted(input.content().length()));

        String prompt = """
                Please review the following Java code:

                %s

                Focus especially on: thread safety, edge cases, API design, and idiom quality.
                """.formatted(input.content());

        String review = chat(prompt);
        return AgentMessage.assistant(name, review);
    }

    /**
     * Parse the VERDICT line from the review to determine if another revision is needed.
     * Returns true if the review says APPROVE.
     */
    public boolean isApproved(String reviewContent) {
        return reviewContent.contains("VERDICT") &&
               reviewContent.contains("APPROVE") &&
               !reviewContent.contains("REVISE");
    }
}
