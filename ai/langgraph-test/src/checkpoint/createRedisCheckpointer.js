import { RedisSaver } from "@langchain/langgraph-checkpoint-redis";

export async function createRedisCheckpointer(redisCheckpointConfig) {
  if (!redisCheckpointConfig) {
    return null;
  }

  const ttlConfig = {};

  if (redisCheckpointConfig.defaultTTL !== undefined) {
    ttlConfig.defaultTTL = redisCheckpointConfig.defaultTTL;
  }

  if (redisCheckpointConfig.refreshOnRead !== undefined) {
    ttlConfig.refreshOnRead = redisCheckpointConfig.refreshOnRead;
  }

  return RedisSaver.fromUrl(
    redisCheckpointConfig.redisUrl,
    Object.keys(ttlConfig).length ? ttlConfig : undefined
  );
}

export function createCheckpointRunConfig(redisCheckpointConfig) {
  if (!redisCheckpointConfig) {
    return undefined;
  }

  return {
    configurable: {
      thread_id: redisCheckpointConfig.threadId,
      checkpoint_ns: redisCheckpointConfig.checkpointNamespace,
    },
  };
}
