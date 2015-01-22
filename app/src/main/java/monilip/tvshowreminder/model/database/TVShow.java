package monilip.tvshowreminder.model.database;

import java.io.Serializable;

/**
 * Created by monilip on 2014-11-07.
 */
public class TVShow implements Serializable{

    //private variables
    int id;
    int TVDBid;
    String title;
    Integer year;
    private String description;

    // empty constructor
    public TVShow() {
    }

    // constructor
    public TVShow(int id, int TVDBid, String title, Integer year,String description) {
        this.id = id;
        this.TVDBid = TVDBid;
        this.title = title;
        this.year = year;
        this.description = description;
    }

    // constructor
    public TVShow( int TVDBid, String title, Integer year,String description) {
        this.TVDBid = TVDBid;
        this.title = title;
        this.year = year;
        this.description = description;
    }

    @Override
    // toString
    public String toString() {
        return this.title+"("+this.year+")";
    }

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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public int getTVDBid() {
        return TVDBid;
    }

    public void setTVDBid(int TVDBid) {
        this.TVDBid = TVDBid;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
