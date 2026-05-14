const DEFAULT_THREAD_ID = "math-agent-thread";
const DEFAULT_CHECKPOINT_NAMESPACE = "math-agent";

function getOptionalTrimmedString(value) {
  if (value == null) {
    return undefined;
  }

  const trimmedValue = value.trim();
  return trimmedValue === "" ? undefined : trimmedValue;
}

function parseOptionalBoolean(value, name) {
  if (value == null || value === "") {
    return undefined;
  }

  if (value === "true") {
    return true;
  }

  if (value === "false") {
    return false;
  }

  throw new Error(`${name} must be either "true" or "false" when provided`);
}

function parseOptionalPositiveInteger(value, name) {
  if (value == null || value === "") {
    return undefined;
  }

  const parsedValue = Number.parseInt(value, 10);

  if (!Number.isInteger(parsedValue) || parsedValue <= 0) {
    throw new Error(`${name} must be a positive integer when provided`);
  }

  return parsedValue;
}

export function getRedisCheckpointConfig(env = process.env) {
  const redisUrl = getOptionalTrimmedString(env.REDIS_URL);

  if (!redisUrl) {
    return null;
  }

  const defaultTTL = parseOptionalPositiveInteger(
    env.REDIS_CHECKPOINT_TTL_MINUTES,
    "REDIS_CHECKPOINT_TTL_MINUTES"
  );
  const refreshOnRead = parseOptionalBoolean(
    env.REDIS_CHECKPOINT_REFRESH_ON_READ,
    "REDIS_CHECKPOINT_REFRESH_ON_READ"
  );

  return {
    redisUrl,
    threadId:
      getOptionalTrimmedString(env.LANGGRAPH_THREAD_ID) ?? DEFAULT_THREAD_ID,
    checkpointNamespace:
      getOptionalTrimmedString(env.LANGGRAPH_CHECKPOINT_NAMESPACE) ??
      DEFAULT_CHECKPOINT_NAMESPACE,
    defaultTTL,
    refreshOnRead,
  };
}
