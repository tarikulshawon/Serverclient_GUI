/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_offline;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author TIS
 */
public class FXMLDocumentController implements Initializable {
    
    Socket connectionSocket=null;
    WorkerThread wt=null;
   
    @FXML
    TextField serverbox;
    @FXML
    Button start;
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        
        
        
        serverbox.setText("server start successfully");
        initServer();
        System.out.println("Hi");
        start.setVisible(false);
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    public void initServer() throws IOException{
    
        new Thread(()->{
        
            try{
                
                    int workerThreadCount = 0;
                    int id = 1;
                    ServerSocket welcomeSocket = new ServerSocket(6789);
                    {
                    while(true)
                    {
                        connectionSocket = welcomeSocket.accept();
                        WorkerThread wt = new WorkerThread(connectionSocket,id);
                        Thread t = new Thread(wt);
                        t.start();
                        workerThreadCount++;

                        System.out.println("Client [" + id + "] is now connected. No. of worker threads = " + workerThreadCount);

                        serverbox.setText("Client [" + id + "] is now connected. No. of worker threads = " + workerThreadCount);

                        id++;
                    }
                    }

            }catch(Exception ex){
                
            }
        
        
        
        }).start();
        
    

    }
    
    
    class WorkerThread implements Runnable
{
    private Socket connectionSocket;
    private int id;
    public WorkerThread(Socket ConnectionSocket, int id) 
    {
        this.connectionSocket=ConnectionSocket;
        this.id=id;
    }
    public void run()
    {
        String clientSentence;
        String capitalizedSentence;
        while(true)
        {
            try
            {
                DataOutputStream outToServer = new DataOutputStream(connectionSocket.getOutputStream());
                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));    
                clientSentence = inFromServer.readLine();
                
                serverbox.setText("Client [" + id + "] said: "+clientSentence);
                
                capitalizedSentence = clientSentence.toUpperCase();
                outToServer.writeBytes(capitalizedSentence + '\n');
                
            }
            catch(Exception e)
            {
                
            }
        }
    }
    
    
    
}


}