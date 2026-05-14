import { ChatOpenAI } from "@langchain/openai";

import { OPENROUTER_BASE_URL, OPENROUTER_HEADERS } from "../config/constants.js";

export function createChatModel({ apiKey, model }) {
  return new ChatOpenAI({
    apiKey,
    model,
    temperature: 0,
    configuration: {
      baseURL: OPENROUTER_BASE_URL,
      defaultHeaders: OPENROUTER_HEADERS,
    },
  });
}
