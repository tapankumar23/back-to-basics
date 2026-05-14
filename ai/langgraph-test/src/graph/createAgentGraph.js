import { ToolMessage } from "@langchain/core/messages";
import { StateGraph, MessagesAnnotation } from "@langchain/langgraph";

import { SYSTEM_PROMPT } from "../config/constants.js";

function createLlmNode(llmWithTools) {
  return async function llmCall(state) {
    const response = await llmWithTools.invoke([
      {
        role: "system",
        content: SYSTEM_PROMPT,
      },
      ...state.messages,
    ]);

    return {
      messages: [response],
    };
  };
}

async function invokeToolCalls(toolCalls, toolsByName) {
  const results = [];

  for (const toolCall of toolCalls) {
    const selectedTool = toolsByName[toolCall.name];

    if (!selectedTool) {
      throw new Error(`Unknown tool requested: ${toolCall.name}`);
    }

    const observation = await selectedTool.invoke(toolCall.args);
    results.push(
      new ToolMessage({
        tool_call_id: toolCall.id,
        content: String(observation),
      })
    );
  }

  return results;
}

function createToolNode(toolsByName) {
  return async function toolNode(state) {
    const lastMessage = state.messages.at(-1);
    const toolCalls = lastMessage?.tool_calls ?? [];
    const results = await invokeToolCalls(toolCalls, toolsByName);

    return {
      messages: results,
    };
  };
}

function shouldContinue(state) {
  const lastMessage = state.messages.at(-1);
  return lastMessage?.tool_calls?.length ? "tools" : "__end__";
}

export function createAgentGraph({ llmWithTools, toolsByName, checkpointer }) {
  return new StateGraph(MessagesAnnotation)
    .addNode("llmCall", createLlmNode(llmWithTools))
    .addNode("tools", createToolNode(toolsByName))
    .addEdge("__start__", "llmCall")
    .addConditionalEdges("llmCall", shouldContinue, {
      tools: "tools",
      __end__: "__end__",
    })
    .addEdge("tools", "llmCall")
    .compile({ checkpointer });
}
