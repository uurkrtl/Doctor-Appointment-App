package de.schnellertermin.backend.services.concretes;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OpenAiManager {
    @Value("${open.ai.key}")
    private String apiKey;
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    public String sendRequest(String imageUrl, String description) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(API_URL);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Authorization", "Bearer " + apiKey);

            JSONObject requestBody = new JSONObject();
            requestBody.put("model", "gpt-4o");

            JSONArray messages = getObjects(imageUrl, description);

            requestBody.put("messages", messages);
            requestBody.put("max_tokens", 200);

            StringEntity entity = new StringEntity(requestBody.toString());
            httpPost.setEntity(entity);

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                String responseString = EntityUtils.toString(response.getEntity());
                return extractContent(responseString);
            }
        }
    }

    private static JSONArray getObjects(String imageUrl, String description) {
        String shortDescription = description.length() > 100 ? description.substring(0, 100) : description;
        JSONArray messages = new JSONArray();

        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");

        JSONArray contentArray = new JSONArray();

        JSONObject textContent = new JSONObject();
        textContent.put("type", "text");
        textContent.put("text", "Could you rate the urgency of the treatment at the doctor's picture between 0 and 5? (0: most urgent). Description: " + shortDescription + ". Just write only rate");
        contentArray.put(textContent);

        JSONObject imageUrlContent = new JSONObject();
        imageUrlContent.put("type", "image_url");
        JSONObject imageUrlObject = new JSONObject();
        imageUrlObject.put("url", imageUrl);
        imageUrlContent.put("image_url", imageUrlObject);
        contentArray.put(imageUrlContent);

        userMessage.put("content", contentArray);
        messages.put(userMessage);
        return messages;
    }

    private String extractContent(String responseString) {
        JSONObject jsonResponse = new JSONObject(responseString);
        JSONArray choices = jsonResponse.getJSONArray("choices");
        if (!choices.isEmpty()) {
            JSONObject firstChoice = choices.getJSONObject(0);
            return firstChoice.getJSONObject("message").getString("content");
        }
        return "No content found.";
    }
}
