package monilip.tvshowreminder.model.database;

/**
 * Created by monilip on 2014-11-07.
 */
public class Episode {

    // private variables
    int id;
    int TVShowId;
    int seasonNumber;
    int episodeNumber;
    String title;
    String date;
    String description;

    // empty constructor
    public Episode() {
    }

    // constructor
    public Episode(int id, int TVShowId, int seasonNumber, int episodeNumber, String title, String date, String description) {
        this.id = id;
        this.TVShowId = TVShowId;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.title = title;
        this.date = date;
        this.description = description;
    }

    // constructor
    public Episode(int TVShowId, int seasonNumber, int episodeNumber, String title, String date, String description) {
        this.TVShowId = TVShowId;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.title = title;
        this.date = date;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTVShowId() {
        return TVShowId;
    }

    public void setTVShowId(int TVShowId) {
        this.TVShowId = TVShowId;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
