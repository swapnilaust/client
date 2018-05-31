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
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author swapn
 */
public class SendReceiveHandler extends Thread{
    
    public int operation;
    public Socket clientSocket;
    
    public SendReceiveHandler(Socket clientSocket, int operation) {
        this.operation = operation;
        this.clientSocket = clientSocket;
    }
    
    public void run(){
        try {
            // TODO code application logic here
            
            BufferedReader inFromClient = new BufferedReader( new InputStreamReader( System.in ) );
            
            if( operation == 1 ){
                while( true ){
                    String sentence = inFromClient.readLine();
                    
                    DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            
                    outToServer.writeBytes(sentence + '\n');

                    System.out.println("send msg complete!");
                }
            }else if( operation == 2 ){
                while( true ){
                    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             
                    String modifiedSentence = inFromServer.readLine();

                    System.out.println(modifiedSentence);
                    System.out.println("receive msg complete!");
                }
            }
            
           
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
