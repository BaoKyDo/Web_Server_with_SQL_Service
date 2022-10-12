package FinalProject;


import FinalProject.WebServer;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ky Do
 */
public class GUIServerInterface extends JFrame{
    
    private JTextArea getInput;
        private JTextArea displayOutput;
        private JButton stopButton;
        private JButton startButton;
        private JPanel inputContainer;
        
        private ObjectOutputStream output;
        private ObjectInputStream input;
        private Socket connection;
        private ServerSocket server;  
        
    public GUIServerInterface(){
        
        //set title bar
        super("Server");
        
        //set dimension
        setSize(500, 500);
        
//        //determine layer layout
        BorderLayout layout = new BorderLayout();
        setLayout(layout);
         
        displayOutput = new JTextArea();
        getInput = new JTextArea();
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        
//        //add some control
        inputContainer = new JPanel();
        inputContainer.add(startButton);
        inputContainer.add(stopButton);
        
        add(displayOutput, BorderLayout.CENTER);
        add(inputContainer, BorderLayout.SOUTH);
        
        startButton.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent ae){
                        start(getInput);
                    }
                }
        );
        
        
        stopButton.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent ae){
                        stop();
                    }
                }
        );
        
        
          setVisible(true);       
    }    
   
    private void start(JTextArea out){
        
        //display text to the client side
        try{
            WebServer web = new WebServer();
        }
        catch(Exception e){
            System.out.println("Oops! " + e.toString());
        }
            
    }
    
    private void stop(){
        
        System.exit(0);
            
    }
    
    public static void main(String[] args){
        GUIServerInterface gui = new GUIServerInterface();
    }
    
}

