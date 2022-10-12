/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FinalProject;

import java.io.*;
import java.net.*;
import java.io.ObjectInputStream;
import javax.swing.JTextArea;

/**
 *
 * @author Ky Do
 */
public class Listener implements Runnable{
    
    private ObjectInputStream input;
    private JTextArea chatOutput;
    
    public Listener(ObjectInputStream input,JTextArea chatOutput ){
        
        this.input = input;
        this.chatOutput = chatOutput;
    }
    
    public void run(){
        
        try{
            while(true){
                
                //wait for input
                String messageIn = (String)(input.readObject());
                
                //display input
                chatOutput.append(messageIn);
                chatOutput.append("\n");
            }
        }
        catch(Exception e){
            System.out.println("Error from chat Listener: " + e.toString());
        }
    }
}
