package org.masumera.body;

/**
 * Song
 */
public class Song {

  private String name;
  private String artist;
  private Album albumName;

  // public Song(String name, int duration, int rate) {
  // this.name = name;
  // this.duration = duration;
  // this.rate = rate;
  // }

  public Song(String name, String artist, Album albumName) {
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
    this.name = dataSong.name();
    this.artist = dataSong.artist();
    this.albumName = dataSong.albumName();
  }

}
