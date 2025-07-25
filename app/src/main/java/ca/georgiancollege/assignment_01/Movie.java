package ca.georgiancollege.assignment_01;

public class Movie {
    private String title;
    private String year;
    private String imdbID;
    private String director;
    private String rating;

    public Movie(String title, String year, String imdbID, String director, String rating) {
        this.title = title;
        this.year = year;
        this.imdbID = imdbID;
        this.director = director;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    public String getRating() {
        return rating;
    }

    public String getYear() {
        return year;
    }
    public String getImdbID() {
        return imdbID;
    }

}

