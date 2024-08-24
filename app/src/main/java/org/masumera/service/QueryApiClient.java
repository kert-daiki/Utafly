package org.masumera.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class QueryApiClient {

    private static final String APIKEY_STRING = "&apikey=a259a1e7037b418bfaa0545e6f4f9864";
    private final String SEARCH_URL = "https://api.musixmatch.com/ws/1.1/track.search?q_track=%s"+APIKEY_STRING;
    private final String TRACK_DETAIL_URL = "https://api.musixmatch.com/ws/1.1/track.get?commontrack_id=%s"+APIKEY_STRING;

    private final HttpClient client;
    private final ObjectMapper objectMapper;

    public QueryApiClient() {
        this.client = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }


    // Método para explorar el JSON
    private void exploreJson(JsonNode node) {
        if (node == null) return;
        System.out.println("Exploring Node: " + node.toPrettyString());
        node.fieldNames().forEachRemaining(fieldName -> {
            JsonNode childNode = node.get(fieldName);
            System.out.println("Field Name: " + fieldName + " | Value: " + childNode);
            if (childNode.isObject() || childNode.isArray()) {
                exploreJson(childNode);
            }
        });
    }




    /*
     * method to search song by title and get trackid 
     * @param query
     * @return JSON response from the api
     * @throws Exception
     */
    public String searchTrackId(String query) throws IOException, InterruptedException {
    String url = String.format(SEARCH_URL, query.replace(" ", "+"));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        // HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            JsonNode messageJsonField = objectMapper.readTree(response.body());
            // System.out.println(messageJsonField.get("message"));
            JsonNode jsonResponse = objectMapper.readTree(response.body()).get("message");
            // System.out.println("\njsonResponse: " + jsonResponse);
        
            exploreJson(jsonResponse); // Función para explorar el JSON
            // JsonNode bodyNode = messageJsonField.get("body");
            // System.out.println("Body Node: " + bodyNode);

            // if (bodyNode != null) {
            JsonNode trackListNode = jsonResponse.get("body");
            System.out.println("tracklist Node: " + trackListNode);
                if (trackListNode != null && trackListNode.isArray() && trackListNode.size() > 0) {
                    JsonNode firstTrack = trackListNode.get(0).get("track");
                    if (firstTrack != null) {
                        return firstTrack.get("commontrack_id").asText();
                    }
                }
            // }
            throw new RuntimeException ("No tracks found.");
        }else {
            throw new RuntimeException("Failed to search track.");
        }
    }

    public JsonNode getTrack(String trackId) throws Exception {
        String url = String.format(TRACK_DETAIL_URL, trackId);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        // HttpResponse response = client.send(request, BodyHandlers.ofString());
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return objectMapper.readTree(response.body());
        }else {
            throw new RuntimeException("Failed to get track information.");
        }
    }

    
}
