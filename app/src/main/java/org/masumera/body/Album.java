package org.masumera.body;


/**
 * Album
 */
public class Album {


  private String name;

  public Album(String name){
    this.name = name;
  }

    public String getName(){
    return name;
  }
    public void setName(String name){
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}
