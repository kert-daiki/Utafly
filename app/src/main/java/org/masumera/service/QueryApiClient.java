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
    private final String SEARCH_URL = "https://api.musixmatch.com/ws/1.1/track.search?q_track=%s"+"&page_size=3&page=1"+APIKEY_STRING;
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
            // JsonNode messageJsonField = objectMapper.readTree(response.body());
            // // System.out.println(messageJsonField.get("message"));
            JsonNode jsonResponse = objectMapper.readTree(response.body());
            // System.out.println("\njsonResponse: " + jsonResponse);
        
            // exploreJson(jsonResponse); // Función para explorar el JSON
            // JsonNode bodyNode = messageJsonField.get("body");
            // System.out.println("Body Node: " + bodyNode);

            // if (bodyNode != null) {
            
            JsonNode messageNode = jsonResponse.get("message");
            // System.out.println(messageNode);

            // if (messageNode != null && messageNode.has("body")) {
            //     // Acceder al campo 'body' dentro de 'message'
            //     JsonNode bodyNode = messageNode.get("body");
            //     System.out.println("Body Node: " + bodyNode);

            //     // Obtener el valor del campo 'body' como texto
            //     String body = bodyNode.toString();
            //     System.out.println("Contenido del cuerpo: " + body);

            //     // Acceder al campo 'track_list' dentro de 'body'
            //     JsonNode trackListNode = bodyNode.get("track_list");
            //     System.out.println("Tracklist Node: " + trackListNode);
            // } else {
            //     System.out.println("El campo 'message' o 'body' no existe en el JSON.");
            // }
                        
            // if (jsonResponse.has("body")) {
            // String body = jsonResponse.get("body").asText();
            // System.out.println(body);
            // }
            JsonNode bodyNode = messageNode.get("body");

            JsonNode trackListNode = bodyNode.get("track_list");
            // System.out.println("tracklist Node: " + trackListNode);
    
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
            JsonNode jsonResponseTrack = objectMapper.readTree(response.body());
            JsonNode artistNode = jsonResponseTrack.get("message").get("body").get("track").get("artist_name");

            return artistNode;
        }else {
            throw new RuntimeException("Failed to get track information.");
        }
    }

    
}
