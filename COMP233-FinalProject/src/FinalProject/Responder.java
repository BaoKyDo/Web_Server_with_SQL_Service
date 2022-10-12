 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FinalProject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ky Do
 */
public class Responder implements Runnable{
    
    
    private Socket requestHandler;
    private Scanner requestReader;
    private Scanner pageReader;
    private DataOutputStream pageWriter;
    private String HTTPMessage;
    private String requestedURL;
    private String requestedFile;
    static FileWriter fw ;
    static int i = 0;
            
    
    public Responder(Socket requestHandler){
        this.requestHandler = requestHandler;
       
    }
    
    public void run(){
        
        
        try{
            
             fw = new FileWriter(new File("text.txt"), false);
            
            String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            
            System.out.println("Page Requested: Request Header");
            
            
            requestReader = new Scanner(
                    //create an InputStreamReader
                    //InputStreamReader reads the input and translate/encode it into a default form
                    //where the computer can understand and accept it
                    new InputStreamReader(
                            //get the input from the outside source
                            requestHandler.getInputStream()));
            
           
               
            int lineCount = 0;
            
            do{    
                lineCount++;
                
                //read the input and display it 
                HTTPMessage = requestReader.nextLine();
                System.out.println(HTTPMessage);
                
                if(HTTPMessage.equals("GET / HTTP/1.1") || HTTPMessage.equals("GET /subdir HTTP/1.1")){
                    HTTPMessage = "GET /default.htm HTTP/1.1";
                }
                
                
                if(lineCount == 1){
                    //buid up a string to hold the path to the requested file
                    // GET /testpage.htm HTTP/1.1
                    if (HTTPMessage.equals("GET WebRoot\\doSERVICE?Criteria=Jaws&Field=Title&submit=Run+Service HTTP/1.1")) {
                        requestedFile = HTTPMessage.substring(4, HTTPMessage.indexOf("HTTP/1.1") - 1);
                    
                    } else {
                        requestedFile = "WebRoot\\" + HTTPMessage.substring(5, HTTPMessage.indexOf("HTTP/1.1") - 1);
                    }
                    
                }
                
                //trimming any unnecessary spaces
                requestedFile = requestedFile.trim();
            
                
                //Read through the page/file
                if(lineCount == 1){
                try{
                pageReader = new Scanner(new File(requestedFile));
                }
                catch(FileNotFoundException fnfe){
//                    requestedFile =  "WebRoot/Util/Error404.htm";
                    pageReader = new Scanner(new File("WebRoot/Util/Error404.htm"));
                }
                pageWriter = new DataOutputStream(requestHandler.getOutputStream());
                pageWriter.flush();
                
                fw.write(requestedFile + " : " + timeStamp + " " + i++);
                fw.write(System.lineSeparator());
                
                if(requestedFile.indexOf("doSERVICE") > -1){
                    Service s = new SQLSelectService(pageWriter, requestedFile);
                    
                    s.doWork();
                }
                else{
                //checking for existing line
                while(pageReader.hasNext()){
                    //read through each line
                    String s = pageReader.nextLine();
                    //display the content of the line
                    pageWriter.writeBytes(s);
                }
                //tell the browser we're done sending
                pageReader.close();
                pageWriter.close();
                requestHandler.close();
                }
                }
                
                
           }
           while(HTTPMessage.length() != 0);
                fw.close();
        }
        catch(Exception e){
            System.out.println("Error From Responder: " + e.toString());
            System.out.println("\n");
            e.printStackTrace();
        }
    }
}
