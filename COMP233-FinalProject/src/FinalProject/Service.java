/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FinalProject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author Ky Do
 */
public abstract class Service{
    
    private DataOutputStream responseWriter;
    
    public Service(DataOutputStream responseWriter)
    {
        this.responseWriter = responseWriter;
    }

    public DataOutputStream getResponseWriter() {return responseWriter;}
    
    public void setResponseWriter(DataOutputStream responseWriter) {this.responseWriter = responseWriter;}
    
    public abstract void doWork();
}



class SQLSelectService extends Service {

    private String requestString;
    private MovieDAO dbLayer;
    private String criteria;
    private String field;
    
    
    //This contructor will be called from the run method of a
    //Responder. It passed the HTTP request info, and the ouput
    //object to the service
    
    public SQLSelectService(DataOutputStream responseWriter, String requestString){
        
        super(responseWriter);
        this.requestString = requestString;
        
    }
    
    
            
    public void doWork(){
        
        try{

            criteria = requestString.substring(27, requestString.indexOf("&Field="));
            criteria = criteria.replace("+", " " );
            field = requestString.substring(requestString.indexOf("Field=")+6, requestString.indexOf("&Submit"));
            
            
            ArrayList<Movie> movies;
            dbLayer = new MovieDAO();

            //executes the query to get an ArrayList of Movies
            movies = dbLayer.getMovieByCriteria(field, criteria);

            //set up the web page:
            getResponseWriter().writeBytes("<!DOCTYPE html><html><head><title>test");
            
            getResponseWriter().writeBytes("</title></head><body>");
            

//            loop through the arrayList writing it to IE using the responseWriter
            for (int i = 0; i < movies.size(); i++) {

                getResponseWriter().writeBytes("<br/>Id: " + movies.get(i).getId());
                getResponseWriter().writeBytes("<br/>Title: " + movies.get(i).getTitle());
                getResponseWriter().writeBytes("<br/>Director: " + movies.get(i).getDirector());
                getResponseWriter().flush();

            }
            getResponseWriter().writeBytes("</body></html>");
            
            //close connection
            
            getResponseWriter().close();
        }
          
        
        catch(Exception e){
            System.out.println("Error from SQLSelectService: " + e.toString());
        }
    }

}
