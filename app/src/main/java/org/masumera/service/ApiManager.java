package org.masumera.service;

// import java.io.FileInputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ApiManager
 */
public class ApiManager {

  private Properties properties = new Properties();

  private static final Logger logger = LogManager.getLogger(ApiManager.class);

  public ApiManager(){
    try(InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
      if (input == null) {
        System.out.println("no se encontro archivo de configuracion");
        return;
      }
     properties.load(input); 
    } catch (IOException e){
//      e.printStackTrace();
      logger.error(e.getMessage());
    }
  }


  public String getApiKey() {
    return properties.getProperty("api.key");
  }
  
}
