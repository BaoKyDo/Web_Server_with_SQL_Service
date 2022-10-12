/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FinalProject;

/**
 *
 * @author Ky Do
 */
public class Movie {
    
    //declare variables
    private int id;
    private String title;
    private String director;
    private String description;
    
    //default constructor
    public Movie(){}
    
    //WITHOUT description
    public Movie(int id, String title, String director){
        
        this.id = id;
        this.title = title;
        this.director = director;
    }
    
    public Movie(int id, String title, String director, String description){
        
        this.id = id;
        this.title = title;
        this.director = director;
        this.description = description;
    }

    //GETTER AND SETTER METHODS
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDirector() {
        return director;
    }
    public void setDirector(String director) {
        this.director = director;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
