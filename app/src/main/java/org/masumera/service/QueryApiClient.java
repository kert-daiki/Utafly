package org.masumera.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

// import java.util.Properties;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
// import com.google.common.collect.Iterators;

import org.masumera.body.Album;
import org.masumera.body.Song;

public class QueryApiClient {

  private static final Logger logger = LogManager.getLogger(QueryApiClient.class);

  ApiManager apiManager = new ApiManager();
  
  private final String apikey = apiManager.getApiKey();
  
  // System.out.println("hello");
  
  
  
  // System.out.println("here"));
  // System.out.println(apikey);app.java
  // System.out.println("here");
  

  private final String APIKEY_STRING = "&apikey=" + apikey;
  
  private final String SEARCH_URL = "https://api.musixmatch.com/ws/1.1/track.search?q_track=%s" + "&page_size=3&page=1"
      + APIKEY_STRING;
  private final String TRACK_DETAIL_URL = "https://api.musixmatch.com/ws/1.1/track.get?commontrack_id=%s"
      + APIKEY_STRING;

  private final HttpClient client;
  private final ObjectMapper objectMapper;

  public QueryApiClient() {
    this.client = HttpClient.newHttpClient();
    this.objectMapper = new ObjectMapper();
  }

  // // Método para explorar el JSON
  // private void exploreJson(JsonNode node) {
  // if (node == null) return;
  // node.fieldNames().forEachRemaining(fieldName -> {
  // JsonNode childNode = node.get(fieldName);
  // System.out.println("Field Name: " + fieldName + " | Value: " + childNode);
  // if (childNode.isObject() || childNode.isArray()) {
  // exploreJson(childNode);
  // }
  // });
  // }
  //

  /*
   * method to search song by title and get trackid
   * 
   * @param query
   * 
   * @return JSON response from the api
   * 
   * @throws Exception
   */
  public List<Song> searchTrackId(String query) throws IOException, InterruptedException {
    logger.info("Searching track id " + query);
    String url = String.format(SEARCH_URL, query.replace(" ", "+"));
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .GET()
        .build();
    HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
    // HttpResponse<String> response = client.send(request,
    // HttpResponse.BodyHandlers.ofString());

    if (response.statusCode() == 200) {
      JsonNode jsonResponse = objectMapper.readTree(response.body());
      JsonNode messageNode = jsonResponse.get("message");
      // System.out.println(messageNode);

      // if (messageNode != null && messageNode.has("body")) {
      // // Acceder al campo 'body' dentro de 'message'
      // JsonNode bodyNode = messageNode.get("body");
      // System.out.println("Body Node: " + bodyNode);

      // // Obtener el valor del campo 'body' como texto
      // String body = bodyNode.toString();
      // System.out.println("Contenido del cuerpo: " + body);

      // // Acceder al campo 'track_list' dentro de 'body'
      // JsonNode trackListNode = bodyNode.get("track_list");
      // System.out.println("Tracklist Node: " + trackListNode);
      // } else {
      // System.out.println("El campo 'message' o 'body' no existe en el JSON.");
      // }

      // if (jsonResponse.has("body")) {
      // String body = jsonResponse.get("body").asText();
      // System.out.println(body);
      // }
      JsonNode bodyNode = messageNode.get("body");
      // JsonNode bodyNode = jsonResponse.get("message").get("body");

      /*
       * Array of track_list songs
       */
      List<Song> trackList = new ArrayList<>();
      JsonNode trackListNode = bodyNode.get("track_list");
      // System.out.println(trackListNode);
      Iterator<JsonNode> elements = trackListNode.elements();

      while (elements.hasNext()) {
        JsonNode songNode = elements.next();
        String id = songNode.path("track").path("commontrack_id").asText();
        System.out.println(id);
        String name = songNode.path("track").path("track_name").asText();
        String artist = songNode.path("track").path("artist_name").asText();
        Album album = new Album(songNode.path("track").path("album_name").asText());
        trackList.add(new Song(id, name, artist, album));
        // trackList.add(new Song(id));
      }

      return trackList;
      // System.out.println("tracklist Node: " + trackListNode);

      // if (trackListNode != null && trackListNode.isArray() && trackListNode.size()
      // > 0) {
      // JsonNode firstTrack = trackListNode.get(0).get("track");
      // if (firstTrack != null) {
      // return firstTrack.get("track_id").asText();
      // }
      // }
      // }
      // throw new RuntimeException("No tracks found.");
    } else {
      throw new RuntimeException("Failed to search track.");
    }
  }

  public String searchQuerySongId(List<Song> songs) throws IOException, InterruptedException {
    // List<Song> songs = searchTrackId(queryRequest);

    // Display the results
    System.out.println("Available songs:");
    for (int i = 0; i < songs.size(); i++) {
      System.out.println((i + 1) + ". " + songs.get(i).getName());
    }
    // Get user input
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    System.out.println("Select a song by number");
    int choice = Integer.parseInt(reader.readLine());

    if (choice > 0 && choice <= songs.size()) {
      Song selectedSong = songs.get(choice - 1);
      String selectedSongId = selectedSong.getId();
      return selectedSongId;
    } else {
      throw new RuntimeException("Failed");
      // System.out.println("Invalid choice");
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
      // JsonNode artistNode =
      // jsonResponseTrack.get("message").get("body").get("track").get("artist_name");
      JsonNode jsonReturn = jsonResponseTrack.get("message").get("body").get("track");

      return jsonReturn;
    } else {
      logger.warn("warn Log Message");
      logger.info("INFO log Message");
      throw new RuntimeException("Failed to get track information.");
    }
  }

}
