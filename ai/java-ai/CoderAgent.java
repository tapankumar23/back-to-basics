package com.example.multiagent.agent;

import com.example.multiagent.model.AgentMessage;
import com.example.multiagent.tool.AnthropicClient;

import java.io.IOException;

/**
 * CoderAgent — writes Java implementation code.
 *
 * Responsibilities:
 *  - Translate a specification into clean, compilable Java code
 *  - Apply reviewer feedback when given a second message
 *  - Produce only the code block, no surrounding explanation
 */
public class CoderAgent extends BaseAgent {

    private static final String SYSTEM_PROMPT = """
            You are an expert Java software engineer specializing in writing clean,
            production-quality code.

            When given a specification:
            - Write complete, compilable Java code
            - Include package declaration and all imports
            - Add concise Javadoc for public methods
            - Handle edge cases and document assumptions
            - Prefer modern Java idioms (records, var, sealed classes where appropriate)
            - Use java.util.concurrent primitives for thread safety

            When given reviewer feedback:
            - Apply ALL suggested improvements
            - Return only the revised complete code file
            - Do not explain what you changed; just return the improved code

            Output format: A single Java code block. Nothing else.
            """;

    public CoderAgent(AnthropicClient client) {
        super("CoderAgent", SYSTEM_PROMPT, client);
    }

    /**
     * First call: generate initial implementation from a specification.
     * Second call: apply review feedback and return revised code.
     */
    @Override
    public AgentMessage process(AgentMessage input)
            throws IOException, InterruptedException {
        log("processing: " + summarize(input.content()));
        String code = chat(input.content());
        return AgentMessage.assistant(name, code);
    }

    private String summarize(String text) {
        return text.length() > 80 ? text.substring(0, 80) + "…" : text;
    }
}
