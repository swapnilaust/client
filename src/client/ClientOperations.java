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
        if( operations == 1 ){
 
            while( true ){
                try {
                    BufferedReader inFromClient = new BufferedReader( new InputStreamReader( System.in ) );

                    String operation = inFromClient.readLine();

                    if( "Online User Lists".equals(operation)){

                        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

                        outToServer.writeBytes("Online User Lists" + '\n');

                    }

                } catch (IOException ex) {
                    Logger.getLogger(ClientOperations.class.getName()).log(Level.SEVERE, null, ex);
                }


            }
        }else if( operations == 2 ){
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
    }
    
    
}
