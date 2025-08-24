package Project;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

public class ReplyFunctions {

	


    // Replace with your Gemini API Key
    private static final String API_KEY = "AIzaSyB3d9wBuKf8z2YqK3uZAO9UmFkmGHsG0AA";

        public static String generateReply(String userMessage) {
            String responseText = "";
            try {
                // Gemini endpoint (latest model)
                String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + API_KEY;

                // Open connection
                URL url = new URL(apiUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                // Create JSON body
                String jsonBody = "{"
                        + "\"contents\": [{\"parts\":[{\"text\":\"" + userMessage.replace("\"", "\\\"") + "\"}]}]"
                        + "}";

                // Send request
                try (OutputStream os = conn.getOutputStream()) {
                    os.write(jsonBody.getBytes("utf-8"));
                }

                // Check HTTP response code
                int statusCode = conn.getResponseCode();
                InputStream inputStream = (statusCode == 200) ? conn.getInputStream() : conn.getErrorStream();

                // Read response
                StringBuilder response = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "utf-8"))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                }

                // Parse JSON to extract "text"
                String jsonResponse = response.toString();
                if (statusCode == 200) {
                    JSONObject obj = new JSONObject(jsonResponse);
                    JSONArray candidates = obj.getJSONArray("candidates");
                    JSONObject firstCandidate = candidates.getJSONObject(0);
                    JSONArray parts = firstCandidate.getJSONObject("content").getJSONArray("parts");
                    responseText = parts.getJSONObject(0).getString("text");
                } else {
                    responseText = "Error: " + statusCode + " - " + jsonResponse;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return responseText;
        }

        public static void main(String[] args) {
            String reply = generateReply("I am getting bore");
            System.out.println("Gemini Response: " + reply);
        }
    }
       
