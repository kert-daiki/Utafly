package org.masumera.body;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * DataSong
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DataSong(
      @JsonAlias("track_name") String name,
      @JsonAlias("artist_name") String artist,
      @JsonAlias("album_name") Album albumName
) {
}
