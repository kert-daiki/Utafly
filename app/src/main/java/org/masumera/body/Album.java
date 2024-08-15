package org.masumera.body;

import java.sql.Date;

/**
 * Album
 */
public class Album {

  private Song song;

  private String name;

  public Album(Song song, String name){
    this.song = song;
    
    this.name = name;
    
  }

  public Song getSong() {
    return song;
  }

  public String getName(){
    return name;
  }
  public void setSong(Song song){
    this.song = song;
  }

  public void setName(String name){
    this.name = name;
  }
}
