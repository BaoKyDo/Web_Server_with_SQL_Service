/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FinalProject;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Ky Do
 */
public class WebServer {
    
    static ServerSocket requestListener;
    
    public static int HTTP_PORT = 12346;
    
    static ExecutorService responses;
    
    public WebServer(){
        
        try
        {
            
            //wait for IE to connect
            requestListener = new ServerSocket(HTTP_PORT);
            
            System.out.println("Waiting For IE to request a page: ");
            
            while(true){
            
            responses = Executors.newFixedThreadPool(100);
            
            Runnable webpage = Start();
            
            responses.execute(webpage);
            
            //Listener listen = new Listener()
            
            responses.shutdown();
            }
            
        }
        catch(Exception e){
            System.out.println("Error from Web Server side: " + e.toString());
        }
    }
    
    public Responder Start(){
        
        while(true){
            try {
                
                Responder r = new Responder(requestListener.accept());
                
                return r;
                
            } catch (IOException ex) {
                System.out.println("Error from start-method: " + ex.toString());
            }
            catch(Exception e){
                System.out.println("Error: " + e.toString());
            }
        }
        
        
    }
}

        
        