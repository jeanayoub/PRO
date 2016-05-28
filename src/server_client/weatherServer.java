/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Pascal SEKLEY
 */
public class weatherServer {
   private final int port;
   
   
   public weatherServer(int port){
      this.port = port;
   }
   
   
   public void serveClients(){
      System.out.println("Starting the Receptionist Worker on a new thread...");
      new Thread(new ReceptionistWorker()).start();
   }
   
   private class ReceptionistWorker implements Runnable {

      @Override
      public void run() {
         ServerSocket serverSocket = null;
         
         try {
            serverSocket = new ServerSocket(port);
         } catch (IOException ex) {
            System.out.println("Error unable to open a server socket");
         }
         
         while(true){
            try {
               Socket clientSocket = serverSocket.accept();
               System.out.println(" A new client has arrived. Starting a new thread for it");
               new Thread(new ServantWorker(clientSocket)).start();
            } catch (IOException ex) {
               Logger.getLogger(weatherServer.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
      }
      
      
      // Inner class that will take care of client once there are connected
      // Here is where to serve clients according to the command.
      private class ServantWorker implements Runnable {
         
         Socket clientSocket;
         BufferedReader in = null;
         PrintWriter out = null;

         private ServantWorker(Socket clientSocket) {
            try {
               this.clientSocket = clientSocket;
               in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
               out = new PrintWriter(clientSocket.getOutputStream());
            } catch (IOException ex) {
               System.out.println("Error unable to initialize a servant worker");
            }

         }

         @Override
         public void run() {
            String command;
            boolean shouldRun = true;
            
            try {
               
               
               while ((shouldRun) && (command = in.readLine()) != null) {
                  
                  /* IMPLEMENT HERE WHAT THE SERVER HAS TO SEND TO THE CLIENT */
                  
                  if(command.equalsIgnoreCase("endCommand")){
                     shouldRun = false;
                  }
                  
                  
                  
               }
               System.out.println("Cleaning up resources...");
               clientSocket.close();
               in.close();
               out.close();
               
            } catch (IOException ex) {
               if (in != null) {
                  try {
                     in.close();
                  } catch (IOException ex1) {
                     ex1.getMessage();
                  }
               }
               if (out != null) {
                  out.close();
               }
               if (clientSocket != null) {
                  try {
                     clientSocket.close();
                  } catch (IOException ex1) {
                     ex1.getMessage();
                  }
               }
               ex.getMessage();
            }
            
            
         }
         
      }
      
   }
   

}
