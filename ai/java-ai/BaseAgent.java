package com.example.multiagent.agent;

import com.example.multiagent.model.AgentMessage;
import com.example.multiagent.tool.AnthropicClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Base class for all agents in the pipeline.
 *
 * Each agent has:
 *  - A name (for logging)
 *  - A system prompt (its persona and responsibilities)
 *  - A conversation history (multi-turn memory within a single task)
 *  - Access to the shared AnthropicClient
 *
 * Agents communicate by receiving AgentMessage inputs and returning
 * AgentMessage outputs. The orchestrator wires them together.
 */
public abstract class BaseAgent {

    protected final String           name;
    protected final String           systemPrompt;
    protected final AnthropicClient  client;

    /** In-memory conversation history for multi-turn interactions. */
    private final List<Map<String, String>> history = new ArrayList<>();

    protected BaseAgent(String name, String systemPrompt, AnthropicClient client) {
        this.name         = name;
        this.systemPrompt = systemPrompt;
        this.client       = client;
    }

    /**
     * Core method each agent must implement.
     * Receives a message, produces a response message.
     */
    public abstract AgentMessage process(AgentMessage input)
            throws IOException, InterruptedException;

    /**
     * Send a user message to Claude, appending it to history,
     * and store the assistant reply in history too.
     *
     * Supports multi-turn: call chat() multiple times on the same agent
     * instance to continue the conversation.
     */
    protected String chat(String userMessage) throws IOException, InterruptedException {
        history.add(Map.of("role", "user", "content", userMessage));

        String reply = client.chat(systemPrompt, history);

        history.add(Map.of("role", "assistant", "content", reply));

        log("→ replied (%d chars)".formatted(reply.length()));
        return reply;
    }

    /** Clear conversation history (reset between tasks). */
    protected void clearHistory() {
        history.clear();
    }

    /** Print a labeled log line. */
    protected void log(String message) {
        System.out.printf("[%-15s] %s%n", name, message);
    }

    public String getName() { return name; }
}
