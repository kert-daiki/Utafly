package org.masumera.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

/**
 * CallRequest
 */
public class CallRequest {

  public CallRequest(){
    
  }
  
  public String queryApi(String parameter1, String parameter2) throws IOException, InterruptedException{
 
  final String apiKey = "&apikey=a259a1e7037b418bfaa0545e6f4f9864";
  final String baseURL = "https://api.musixmatch.com/ws/1.1/";
  final String apiMethod = "track.search?";
  final String parameterTrack = "q_track=";
  final String parameterArtist = "q_artist=";
  final String completeUrl = baseURL+apiMethod+parameterTrack+parameter1.replace(" ", "+")+"&"+parameterArtist+parameter2.replace(" ", "+")+apiKey;

  HttpClient client = HttpClient.newHttpClient();

  HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create(completeUrl))
      .headers("accep", "applicaton/json")
      .build();

    HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

    String json = response.body();
    return json;
   
  }
}


