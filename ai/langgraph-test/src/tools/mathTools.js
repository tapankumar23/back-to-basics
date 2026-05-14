import { tool } from "@langchain/core/tools";
import { z } from "zod";

const mathSchema = z.object({
  a: z.number().describe("first number"),
  b: z.number().describe("second number"),
});

function createArithmeticOperationTool({ name, description, execute }) {
  return tool(async ({ a, b }) => String(execute(a, b)), {
    name,
    description,
    schema: mathSchema,
  });
}

export function createMathTools() {
  return [
    createArithmeticOperationTool({
      name: "multiply",
      description: "Useful when you want to multiply two numbers together",
      execute: (a, b) => a * b,
    }),
    createArithmeticOperationTool({
      name: "add",
      description: "Useful when you want to add two numbers together",
      execute: (a, b) => a + b,
    }),
    createArithmeticOperationTool({
      name: "divide",
      description: "Useful when you want to divide two numbers together",
      execute: (a, b) => {
        if (b === 0) {
          throw new Error("Cannot divide by zero");
        }

        return a / b;
      },
    }),
    createArithmeticOperationTool({
      name: "subtract",
      description: "Useful when you want to subtract two numbers together",
      execute: (a, b) => a - b,
    }),
  ];
}

export function createToolRegistry(tools) {
  return Object.fromEntries(
    tools.map((currentTool) => [currentTool.name, currentTool])
  );
}
