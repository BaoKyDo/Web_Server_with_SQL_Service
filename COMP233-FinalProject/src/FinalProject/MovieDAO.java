/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FinalProject;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

public class MovieDAO {
    
    //make connection with sql server
    public Connection getConnection(){
        Connection conn = null;
        try{
            
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");            
            String connectionUrl =  "jdbc:sqlserver://localhost:1434;DatabaseName=Movies;User=javaApps;Password=java";
            conn = DriverManager.getConnection(connectionUrl);
        }
        catch(Exception e){
            e.printStackTrace();
            conn = null;
        }
        return conn;
}
    
    public ArrayList getMovies(){
        
        ArrayList movies = new ArrayList();
        Connection conn = getConnection();
        ResultSet rs = null;
        
        try{
            Statement stmt = conn.createStatement();
            //insert sql syntax
            rs = stmt.executeQuery("SELECT id, title, director FROM Movies");
            //while the next data exists
            while(rs.next()){
                Movie m = new Movie(Integer.parseInt(rs.getString(1)),
                                    rs.getString(2),
                                    rs.getString(3));
                movies.add(m);
            }
            //close connection
            conn.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return movies;
    }
    
    public void updateMovie(Movie itsMovie){
        
        //recall connection
        Connection conn = getConnection();
        
        try {
            Statement stmt = conn.createStatement();
            //use sql syntax
            String updateStatement = "UPDATE Movies SET Title = '" + itsMovie.getTitle()
                                   + "', Director= '" + itsMovie.getDirector()
                                   + "', Description= '" + itsMovie.getDescription()+
                                   "' WHERE id = " + itsMovie.getId();
            //execute update statement
            stmt.execute(updateStatement);
            conn.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    
    public Movie getMovieById(int id){
        
        Movie result = null;
        
        Connection conn = getConnection();
        
        ResultSet rs = null;
        
        try{
            Statement stmt = conn.createStatement();
            //sql syntax
            rs = stmt.executeQuery("SELECT id, title, director, description FROM Movies WHERE id =" +id);
            
            if(rs.next()){
                //cast them into movie class
                Movie m = new Movie(Integer.parseInt(rs.getString(1)),
                                    rs.getString(2),
                                    rs.getString(3),
                                    rs.getString(4));
                result = m;
            }
            conn.close();
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    
    public ArrayList getMovieByCriteria(String field, String criteria){
        
        ArrayList movies = new ArrayList();
        Connection conn = getConnection();
        ResultSet rs = null;
        
        try{
            
            Statement stmt = conn.createStatement();
            //insert sql syntax
            rs = stmt.executeQuery("SELECT * FROM Movies WHERE " + field + " = '" + criteria + "'");
            //while the next data exists
            while(rs.next()){
                Movie m = new Movie(Integer.parseInt(rs.getString(1)),
                                    rs.getString(2),
                                    rs.getString(3));
                
                movies.add(m);
            }
            //close connection
            conn.close();
        }
        catch(Exception e){
            System.out.println("Error from MovieDAO: " + e.toString());
        }
        
        return movies;
    }
        
}


