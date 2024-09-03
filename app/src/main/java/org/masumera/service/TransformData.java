package org.masumera.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
// import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * GetData
 */
public class TransformData{

  private ObjectMapper objectMapper = new ObjectMapper();
  private QueryApiClient queryApiClient;

  public TransformData(QueryApiClient queryApiClient){
    this.queryApiClient = queryApiClient;
  }

  public String getTrackAsString(String trackId){
    try {
    JsonNode songDataFrom = queryApiClient.getTrack(trackId);
    return songDataFrom.toString();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public <T> T getData(String json, Class<T> fromClass){
    try {
      return objectMapper.readValue(json, fromClass);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
