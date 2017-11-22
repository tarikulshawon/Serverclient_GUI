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
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author TIS
 */
public class ClientController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    String sentence=null;
   // String modifiedSentence=null;
    Socket clientSocket=null;
    BufferedReader inFromUser = null; 
    DataOutputStream outToServer=null;
    BufferedReader inFromServer=null;

    
    @FXML
    TextArea msgbox;
    @FXML
    TextField showmsg;
    
    @FXML
    public void sendmsg(ActionEvent event) throws IOException {
        
        String msg=msgbox.getText() ;
        if(outToServer!=null){
            outToServer.writeBytes(msg+ '\n');
           
        }
         System.out.println("check");
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            initClient();
        } catch (IOException ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
     
    }    
    
    public void initClient() throws IOException{
        clientSocket = new Socket("localhost", 6789);
        outToServer = new DataOutputStream(clientSocket.getOutputStream());
        inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        new Thread(()->{
        
        try{
            

            
            while(true)
            {
                sentence = inFromUser.readLine();
              //  outToServer.writeBytes(sentence + '\n');

                showmsg.setText(sentence);

               // modifiedSentence = inFromServer.readLine();
                System.out.println("From Server : "+sentence);
            } 

        }catch(Exception ex){
            
        }
        
        }).start();
              
}
    
    
}