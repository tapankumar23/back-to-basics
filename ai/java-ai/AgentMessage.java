package com.example.multiagent.model;

/**
 * Immutable message passed between agents in the pipeline.
 *
 * @param agentName  Which agent produced this message
 * @param role       "user" or "assistant" (Anthropic API roles)
 * @param content    The text content of the message
 */
public record AgentMessage(String agentName, String role, String content) {

    /** Convenience factory for user-role messages */
    public static AgentMessage user(String agentName, String content) {
        return new AgentMessage(agentName, "user", content);
    }

    /** Convenience factory for assistant-role messages */
    public static AgentMessage assistant(String agentName, String content) {
        return new AgentMessage(agentName, "assistant", content);
    }

    @Override
    public String toString() {
        return "[%s/%s]: %s".formatted(agentName, role, content);
    }
}
