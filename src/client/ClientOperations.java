/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author swapn
 */
public class ClientOperations extends Thread {
    public Socket clientSocket;
    int operations;
    
    public ClientOperations(Socket clientSocket, int operations) {
        this.clientSocket = clientSocket;
        this.operations = operations;
    }    
    
    public void run(){
        if( operations == 1 ){ // send to server
 
            while( true ){
                try {
                    BufferedReader inFromClient = new BufferedReader( new InputStreamReader( System.in ) );

                    String operation = inFromClient.readLine();
                    
                    DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

                    if( "Online User Lists".equals(operation)){

                        outToServer.writeBytes("Online User Lists" + '\n');

                    }else if( "Accecpt Friend Request".equals(operation) ){
                        
                        outToServer.writeBytes("Accecpt Friend Request" + '\n');
                        
                        System.out.print("Accecpt Friend Request From : ");
                        
                        String name = inFromClient.readLine();
                        
                        outToServer.writeBytes(name + '\n');
 
                    }else if( "Send Friend Request".equals(operation) ){
                        
                        outToServer.writeBytes("Send Friend Request" + '\n');
                        
                        System.out.print("Friend Request To: ");
                        
                        String name = inFromClient.readLine();
                        
                        outToServer.writeBytes(name + '\n');
                        
                    }else if( "Unicast".equals(operation) ){
                        
                        outToServer.writeBytes("Unicast" + '\n');
                        
                        System.out.print("To: ");
                        
                        String name = inFromClient.readLine();
                        
                        outToServer.writeBytes(name + '\n');
                        
                        System.out.print("Message: ");
                        
                        String msg = inFromClient.readLine();
                        
                        outToServer.writeBytes(msg + '\n');
                          
                    }else if("Multicast".equals(operation) ){
                        
                        outToServer.writeBytes("Multicast" + '\n');
                        
                        System.out.print("To: ");
                        
                        String name = inFromClient.readLine();
                        
                        outToServer.writeBytes(name + '\n');
                        
                        System.out.print("Message: ");
                        
                        String msg = inFromClient.readLine();
                        
                        outToServer.writeBytes(msg + '\n');
                        
                    }else if("Broadcast".equals(operation) ){
                        
                        outToServer.writeBytes("Broadcast" + '\n');
                        
                        System.out.print("Message: ");
                        
                        String msg = inFromClient.readLine();
                        
                        outToServer.writeBytes(msg + '\n');
                    }else if( "log out".equals( operation ) ){
                        
                        outToServer.writeBytes("log out" + '\n');
                        
                        break;
                    }else if( "Create Chat Room".equals( operation ) ){
                        
                        outToServer.writeBytes("Create Chat Room" + '\n');
                        
                        System.out.print("Chat Room Name: ");
                        
                        String name = inFromClient.readLine();
                        
                        outToServer.writeBytes(name + '\n');

                    }else if( "Open Chat Room".equals( operation ) ){
                        
                        outToServer.writeBytes("Open Chat Room" + '\n');
                        
                        System.out.print("Chat Room Name: ");
                        
                        String name = inFromClient.readLine();
                        
                        outToServer.writeBytes(name + '\n');
                        
                        // dhore nilam sob somoy thik naam dibe
                        
                        String msg = inFromClient.readLine();
                        
                        outToServer.writeBytes(msg + '\n');
                        
                        while( true ){
                            
                            if( "Exit Chat Room".equals(msg) ) break;
                            
                            msg = inFromClient.readLine();
                            
                            outToServer.writeBytes(msg + '\n');  
   
                        }
                        
                        System.out.println("Chat Room Closed!");

                    }else if( "Add Friend to Chat Room".equals( operation ) ){
                        
                        outToServer.writeBytes("Add Friend to Chat Room" + '\n');
                        
                        System.out.print("Chat Room Name: ");
                        
                        String cname = inFromClient.readLine();
                        
                        outToServer.writeBytes(cname + '\n');
                        
                        System.out.print("User Name: ");
                        
                        String name = inFromClient.readLine();
                        
                        outToServer.writeBytes(name + '\n');

                    }
         
                    
                } catch (IOException ex) {
                    Logger.getLogger(ClientOperations.class.getName()).log(Level.SEVERE, null, ex);
                }


            }
        }else if( operations == 2 ){ // receive from server
            try{
                while( true ){

                    
                    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             
                    String modifiedSentence = inFromServer.readLine();
                    
                    if( "Online User Lists".equals(modifiedSentence) ){
                        
                        ObjectInputStream objectInput = new ObjectInputStream(clientSocket.getInputStream());
                        
                        try {
                            Object object = objectInput.readObject();

                            ArrayList<String> allUsers = new ArrayList<String>();

                            allUsers =  (ArrayList<String>) object;

                            for( String i: allUsers ){
                                System.out.println(i);
                            }

                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(ClientOperations.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }else{
                        System.out.println(modifiedSentence);
                    }
                    
                    System.out.println("receive msg complete!");
                }
            }catch (IOException ex) {
                    Logger.getLogger(ClientOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("asche sesh e");
    }
    
    
}
