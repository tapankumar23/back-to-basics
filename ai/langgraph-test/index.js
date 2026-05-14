import { runMathAgent } from "./src/app/runMathAgent.js";

async function main() {
  const { model, result, threadId, usesRedisCheckpointer } =
    await runMathAgent();
  console.log(`Using model: ${model}`);
  if (usesRedisCheckpointer) {
    console.log(`Redis checkpointer thread: ${threadId}`);
  }
  console.log(result.messages.at(-1)?.content);
}

main().catch((error) => {
  console.error(error);
  process.exit(1);
});
