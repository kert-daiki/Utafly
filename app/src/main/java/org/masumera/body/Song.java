package org.masumera.body;

/**
 * Song
 */
public class Song {

  private String name;
  private int duration;
  private int rate;

  // public Song(String name, int duration, int rate) {
  //   this.name = name;
  //   this.duration = duration;
  //   this.rate = rate;
  // }

  public String getName() {
    return name;
  }

  public Song(String name, int duration, int rate) {
    this.name = name;
    this.duration = duration;
    this.rate = rate;
  }

  public int getDuration() {
    return duration;
  }

  public int getRate() {
    return rate;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setRate(int rate) {
    this.rate = rate;
  }

  public void setDuration(int duration){
    this.duration = duration;
  }

}
