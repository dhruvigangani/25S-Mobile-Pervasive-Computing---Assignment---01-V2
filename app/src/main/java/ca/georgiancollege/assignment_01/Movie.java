package ca.georgiancollege.assignment_01;

public class Movie {
    private String title;
    private String director;
    private String year;
    private String rating;
    private String imdbID;

    //contructor method
    public Movie(String title, String director, String year, String rating, String imdbID) {
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

    public String getYear() {
        return year;
    }

    public String getRating() {
        return rating;
    }

    public String getImdbID() {
        return imdbID;
    }

}
