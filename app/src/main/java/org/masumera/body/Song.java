package org.masumera.body;

/**
 * Song
 */
public class Song {

  private String name;
  private String artist;
  private Album albumName;
  private String id;

  // public Song(String name, int duration, int rate) {
  // this.name = name;
  // this.duration = duration;
  // this.rate = rate;
  // }

  public Song(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Song(String id, String name, String artist, Album albumName) {
    this.id = id;
    this.name = name;
    this.artist = artist;
    this.albumName = albumName;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getArtist() {
    return artist;
  }

  public void setArtist(String artist) {
    this.artist = artist;
  }

  public Album getAlbumName() {
    return albumName;
  }

  public void setAlbumName(Album albumName) {
    this.albumName = albumName;
  }

  public Song(DataSong dataSong) {
    this.id = dataSong.id();
    this.name = dataSong.name();
    this.artist = dataSong.artist();
    this.albumName = dataSong.albumName();
  }

  @Override
  public String toString() {
    return "name: " + name +
           ", artist: " + artist + '\'' + 
           ", albumName: " + albumName + '\'' ;
  }

}
