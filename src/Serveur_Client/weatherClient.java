/*
 * Pascal
 */

package Serveur_Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import utils.readConfiguration;

/**
 * 
 * @author Pascal SEKLEY
 */
public class weatherClient {
   
   public void start(){
      Socket clientSocket = null;
      BufferedReader reader;
      BufferedWriter writer;
      
      readConfiguration readConf = new readConfiguration();
      
      
      try{
         clientSocket = new Socket(readConf.getServerAddress(), readConf.getServerPort());
         reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
         writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
         
         // We wait il the stream is ready to be used
         while(!reader.ready()){}
         
         // Do something while we did not get the end command.
         do{
            
            /* FILL HERE WITH WHAT WE WANT TO GET FROM THE SERVER */
            
         } while(!clientSocket.isClosed());
 
      }catch(Exception e){
         e.getMessage();
      }
      
      
   }

}
