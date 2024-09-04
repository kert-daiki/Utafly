package org.masumera.service;

// import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ApiManager
 */
public class ApiManager {

  private Properties properties = new Properties();

  public ApiManager(){
    try(InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
      if (input == null) {
        System.out.println("no se encontro archivo de configuracion");
        return;
      }
     properties.load(input); 
    } catch (IOException e){
      e.printStackTrace();
    }
  }

  public String getApiKey() {
    return properties.getProperty("api.key");
  }
  
}
