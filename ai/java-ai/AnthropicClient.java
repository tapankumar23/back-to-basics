package com.example.multiagent.tool;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

/**
 * Thin wrapper around the Anthropic /v1/messages REST API.
 *
 * Keeps the example self-contained (no third-party SDK required).
 * Uses Java 11+ HttpClient and Jackson-style manual JSON building.
 *
 * For production use, prefer the official Anthropic Java SDK or a
 * full JSON library (Jackson, Gson) instead of manual serialization.
 */
public class AnthropicClient {

    private static final String API_URL  = "https://api.anthropic.com/v1/messages";
    private static final String MODEL    = "claude-sonnet-4-20250514";
    private static final String API_VER  = "2023-06-01";
    private static final int    MAX_TOKENS = 4096;

    private final String     apiKey;
    private final HttpClient http;

    public AnthropicClient(String apiKey) {
        this.apiKey = apiKey;
        this.http   = HttpClient.newHttpClient();
    }

    /**
     * Send a conversation (list of {role, content} turns) with a system prompt
     * and return the assistant's reply text.
     *
     * @param systemPrompt  The agent's persona / instructions
     * @param messages      Alternating user/assistant turns
     * @return              Assistant reply text
     */
    public String chat(String systemPrompt,
                       List<Map<String, String>> messages) throws IOException, InterruptedException {

        String body = buildRequestJson(systemPrompt, messages);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type",      "application/json")
                .header("x-api-key",         apiKey)
                .header("anthropic-version", API_VER)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = http.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Anthropic API error %d: %s"
                    .formatted(response.statusCode(), response.body()));
        }

        return extractText(response.body());
    }

    // ── Private helpers ──────────────────────────────────────────────────────

    /** Build the JSON request body manually (avoids a JSON library dependency). */
    private String buildRequestJson(String systemPrompt,
                                    List<Map<String, String>> messages) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("  \"model\": \"").append(MODEL).append("\",\n");
        sb.append("  \"max_tokens\": ").append(MAX_TOKENS).append(",\n");
        sb.append("  \"system\": ").append(jsonString(systemPrompt)).append(",\n");
        sb.append("  \"messages\": [\n");

        for (int i = 0; i < messages.size(); i++) {
            Map<String, String> msg = messages.get(i);
            sb.append("    {\"role\": ")
              .append(jsonString(msg.get("role")))
              .append(", \"content\": ")
              .append(jsonString(msg.get("content")))
              .append("}");
            if (i < messages.size() - 1) sb.append(",");
            sb.append("\n");
        }

        sb.append("  ]\n}");
        return sb.toString();
    }

    /**
     * Extract the first text block content from the Anthropic response JSON.
     * Minimal parsing — looks for the "text" field in the content array.
     */
    private String extractText(String json) {
        // Find: "type": "text" then the following "text": "..."
        int typeIdx = json.indexOf("\"type\": \"text\"");
        if (typeIdx == -1) typeIdx = json.indexOf("\"type\":\"text\"");
        if (typeIdx == -1) {
            throw new RuntimeException("No text block in response: " + json);
        }

        int textKeyIdx = json.indexOf("\"text\"", typeIdx);
        if (textKeyIdx == -1) {
            throw new RuntimeException("No text field after type=text in: " + json);
        }

        // Find the opening quote of the value
        int valueStart = json.indexOf("\"", textKeyIdx + 7) + 1;
        // Walk forward handling escaped characters
        StringBuilder result = new StringBuilder();
        int i = valueStart;
        while (i < json.length()) {
            char c = json.charAt(i);
            if (c == '\\' && i + 1 < json.length()) {
                char next = json.charAt(i + 1);
                switch (next) {
                    case '"'  -> result.append('"');
                    case '\\' -> result.append('\\');
                    case 'n'  -> result.append('\n');
                    case 'r'  -> result.append('\r');
                    case 't'  -> result.append('\t');
                    default   -> result.append(next);
                }
                i += 2;
            } else if (c == '"') {
                break;  // end of string value
            } else {
                result.append(c);
                i++;
            }
        }
        return result.toString();
    }

    /** Wrap a Java string as a JSON string literal with escaping. */
    private String jsonString(String value) {
        if (value == null) return "null";
        return "\"" + value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t")
               + "\"";
    }
}
