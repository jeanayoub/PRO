

package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * @author Pascal SEKLEY
 */
public class readConfiguration {
   
   private String serverAddress;
   private int serverPort;
   
   
   public void readConfig() throws IOException{
      try {
         InputStream input = null;
         Properties prop = new Properties();
         
         input = new FileInputStream("configuration.properties");
         
         // Load properties
         prop.load(input);
         
         // Fetch the datas from the configuration file
         serverAddress = prop.getProperty("serverAddress");
         serverPort = Integer.parseInt(prop.getProperty("serverPort"));
      
         
      } catch (FileNotFoundException ex) {
         System.out.println("Error in loading configurations");
      }
   }
   
   public String getServerAddress(){
      return serverAddress;
   }
   
   public int getServerPort(){
      return serverPort;
   }

}
