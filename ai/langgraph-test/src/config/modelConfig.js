import { config as loadDotenv } from "dotenv";

import { DEFAULT_OPENROUTER_MODEL } from "./constants.js";

let hasLoadedEnvironment = false;

export function loadEnvironment() {
  if (hasLoadedEnvironment) {
    return;
  }

  loadDotenv();
  hasLoadedEnvironment = true;
}

export function getModelConfig(env = process.env) {
  const apiKey = env.OPENROUTER_API_KEY;

  if (!apiKey) {
    throw new Error("OPENROUTER_API_KEY is not set");
  }

  return {
    apiKey,
    model: env.OPENROUTER_MODEL ?? DEFAULT_OPENROUTER_MODEL,
  };
}
