import {
  createCheckpointRunConfig,
  createRedisCheckpointer,
} from "../checkpoint/createRedisCheckpointer.js";
import { getRedisCheckpointConfig } from "../config/checkpointConfig.js";
import { getModelConfig, loadEnvironment } from "../config/modelConfig.js";
import { createAgentGraph } from "../graph/createAgentGraph.js";
import { createChatModel } from "../llm/createChatModel.js";
import { createMathTools, createToolRegistry } from "../tools/mathTools.js";

export function createInitialMessages() {
  return [
    {
      role: "user",
      content: "What is the product of 20 and 3 then subtract it by 1 then by adding 3 finally divide by 2?",
    },
  ];
}

export async function runMathAgent({
  env = process.env,
  messages = createInitialMessages(),
} = {}) {
  loadEnvironment();

  const modelConfig = getModelConfig(env);
  const redisCheckpointConfig = getRedisCheckpointConfig(env);
  const tools = createMathTools();
  const toolsByName = createToolRegistry(tools);
  const llm = createChatModel(modelConfig);
  const llmWithTools = llm.bindTools(tools);
  const checkpointer = await createRedisCheckpointer(redisCheckpointConfig);

  try {
    const agentGraph = createAgentGraph({
      llmWithTools,
      toolsByName,
      checkpointer,
    });
    const result = await agentGraph.invoke(
      { messages },
      createCheckpointRunConfig(redisCheckpointConfig)
    );

    return {
      model: modelConfig.model,
      result,
      threadId: redisCheckpointConfig?.threadId ?? null,
      usesRedisCheckpointer: checkpointer !== null,
    };
  } finally {
    await checkpointer?.end();
  }
}
