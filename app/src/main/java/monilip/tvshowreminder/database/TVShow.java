package monilip.tvshowreminder.database;

/**
 * Created by monilip on 2014-11-07.
 */
public class TVShow {

    //private variables
    int id;
    int TVDBid;
    String title;
    Integer year;

    // empty constructor
    public TVShow() {
    }

    // constructor
    public TVShow(int id, int TVDBid, String title, Integer year) {
        this.id = id;
        this.TVDBid = TVDBid;
        this.title = title;
        this.year = year;
    }

    // constructor
    public TVShow(String title, Integer year) {
        this.title = title;
        this.year = year;
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
}
