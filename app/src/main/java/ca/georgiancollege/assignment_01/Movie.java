package ca.georgiancollege.assignment_01;

public class Movie {
    private String title;
    private String director;
    private int year;
    private double rating;
    private double imdbID;

    //contructor method
    public Movie(String title, String director, int year, double rating, double imdbID) {
        this.title = title;
        this.director = director;
        this.year = year;
        this.rating = rating;
        this.imdbID = imdbID;
    }

    //getter methods
    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    public int getYear() {
        return year;
    }

    public double getRating() {
        return rating;
    }

    public double getImdbID() {
        return imdbID;
    }

}
