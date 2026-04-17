package com.example.multiagent.agent;

import com.example.multiagent.model.AgentMessage;
import com.example.multiagent.tool.AnthropicClient;

import java.io.IOException;

/**
 * TestAgent — generates JUnit 5 unit tests for reviewed, approved code.
 *
 * Responsibilities:
 *  - Write comprehensive JUnit 5 + Mockito tests
 *  - Cover: happy paths, edge cases, thread-safety scenarios
 *  - Produce a complete, compilable test class
 *  - Include at minimum: 1 concurrency test using ExecutorService
 */
public class TestAgent extends BaseAgent {

    private static final String SYSTEM_PROMPT = """
            You are a Java QA engineer specializing in writing thorough, idiomatic JUnit 5 tests.

            When given Java implementation code:
            - Write a complete JUnit 5 test class (use @Test, @BeforeEach, @ParameterizedTest where appropriate)
            - Include all necessary imports (JUnit 5, AssertJ preferred over Hamcrest)
            - Cover: typical usage, boundary conditions, invalid inputs, thread safety
            - For thread-safety tests: use ExecutorService with a CountDownLatch or CyclicBarrier
            - Use @DisplayName annotations for readable test names
            - Name the test class <OriginalClassName>Test

            Output: A single Java test class code block. Nothing else.
            """;

    public TestAgent(AnthropicClient client) {
        super("TestAgent", SYSTEM_PROMPT, client);
    }

    /**
     * Receives the final approved implementation and produces a test class.
     */
    @Override
    public AgentMessage process(AgentMessage input)
            throws IOException, InterruptedException {
        log("generating tests for code (%d chars)".formatted(input.content().length()));

        String prompt = """
                Write a comprehensive JUnit 5 test suite for the following Java implementation.
                Pay special attention to thread-safety and edge cases:

                %s
                """.formatted(input.content());

        String tests = chat(prompt);
        return AgentMessage.assistant(name, tests);
    }
}
