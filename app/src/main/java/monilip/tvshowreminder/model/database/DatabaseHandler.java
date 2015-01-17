package monilip.tvshowreminder.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.DateFormat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by monilip on 2014-11-07.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "tvshowsManager";

    // Tables names
    private static final String TABLE_TVSHOWS = "tvshows";
    private static final String TABLE_EPISODES = "episodes";

    // Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TVDB_ID = "TVDBid";
    private static final String KEY_TVSHOW_ID = "TVShowId";
    private static final String KEY_TITLE = "title";
    private static final String KEY_SEASON_NUMBER = "seasonNumber";
    private static final String KEY_EPISODE_NUMBER = "episodeNumber";
    private static final String KEY_DATE = "date";
    private static final String KEY_YEAR = "year";
    private static final String KEY_DESCRIPION = "description";



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        //tvshows table
        String CREATE_TVSHOWS_TABLE = "CREATE TABLE " + TABLE_TVSHOWS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TVDB_ID + " INTEGER,"
                + KEY_TITLE + " TEXT,"
                + KEY_YEAR + " INTEGER,"
               // + KEY_DESCRIPION + " TEXT"
                + ")";
        db.execSQL(CREATE_TVSHOWS_TABLE);

        //episodes table
        String CREATE_EPISODES_TABLE = "CREATE TABLE " + TABLE_EPISODES + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TVSHOW_ID + " INTEGER,"
                + KEY_SEASON_NUMBER + " INTEGER,"
                + KEY_EPISODE_NUMBER + " INTEGER,"
                + KEY_TITLE + " TEXT,"
                + KEY_DATE + " TEXT,"
               // + KEY_DESCRIPION + " TEXT,"
                + "FOREIGN KEY (" + KEY_TVSHOW_ID + ") REFERENCES " + TABLE_TVSHOWS + "(" + KEY_ID + ")"
                + ")";
        db.execSQL(CREATE_EPISODES_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TVSHOWS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EPISODES);

        // Create tables again
        onCreate(db);
    }

    public TVShow getTVShowFromEpisode(Episode episode){
        return getTVShowById(episode.getTVShowId());
    }

    //TV-SHOWS
    public void addTVShow(TVShow tvshow) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, tvshow.getTitle());
        values.put(KEY_TVDB_ID, tvshow.getTVDBid());
        values.put(KEY_YEAR, tvshow.getYear());

        // inserting row
        db.insert(TABLE_TVSHOWS, null, values);
        db.close(); // Closing database connection
    }

    public void addTVShow(int tvdbIt, String title, int year) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, title);
        values.put(KEY_TVDB_ID, tvdbIt);
        values.put(KEY_YEAR, year);

        // inserting Row
        db.insert(TABLE_TVSHOWS, null, values);
        db.close(); // Closing database connection
    }

    public TVShow getTVShowById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_TVSHOWS + " WHERE "
                + KEY_ID + " = " + id;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.getCount() == 0 || !cursor.moveToFirst()){
            return null;
        }

        TVShow tvshow = new TVShow(cursor.getInt(cursor.getColumnIndex(KEY_ID)),cursor.getInt(cursor.getColumnIndex(KEY_TVDB_ID)),
                cursor.getString(cursor.getColumnIndex(KEY_TITLE)),cursor.getInt(cursor.getColumnIndex(KEY_YEAR)));

        return tvshow;
    }

    public TVShow getTVShowByTVDBId(int TVDBid) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_TVSHOWS + " WHERE "
                + KEY_TVDB_ID + " = " + TVDBid;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.getCount() == 0 || !cursor.moveToFirst()){
            return null;
        }

        TVShow tvshow = new TVShow(cursor.getInt(cursor.getColumnIndex(KEY_ID)),cursor.getInt(cursor.getColumnIndex(KEY_TVDB_ID)),
                cursor.getString(cursor.getColumnIndex(KEY_TITLE)),cursor.getInt(cursor.getColumnIndex(KEY_YEAR)));

        cursor.close();
        return tvshow;
    }

    public TVShow getTVShow(String title,int year) {
        SQLiteDatabase db = this.getReadableDatabase();

        title = title.replace("'","''");
        String selectQuery = "SELECT * FROM " + TABLE_TVSHOWS + " WHERE "
                + KEY_TITLE + " = '" + title + "' AND " + KEY_YEAR + " = " + year;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.getCount() == 0 || !cursor.moveToFirst()) {
            return null;
        }
        cursor.moveToFirst();
        TVShow tvshow = new TVShow(cursor.getInt(cursor.getColumnIndex(KEY_ID)),cursor.getInt(cursor.getColumnIndex(KEY_TVDB_ID)),
                cursor.getString(cursor.getColumnIndex(KEY_TITLE)),cursor.getInt(cursor.getColumnIndex(KEY_YEAR)));

        cursor.close();
        return tvshow;
    }

    public boolean isTVShow(int TVDBid) {
        if (this.getTVShowByTVDBId(TVDBid) == null){
            return false;
        }
        return true;
    }


    public List<TVShow> getAllTVShows() {
        List<TVShow> tvshows = new ArrayList<TVShow>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_TVSHOWS;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                TVShow tvshow = new TVShow(cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                        cursor.getInt(cursor.getColumnIndex(KEY_TVDB_ID)),
                        cursor.getString(cursor.getColumnIndex(KEY_TITLE)),
                        cursor.getInt(cursor.getColumnIndex(KEY_TVDB_ID)));

                tvshows.add(tvshow);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return tvshows;
    }
    //EPISODES
    public void addEpisode(Episode episode) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TVSHOW_ID, episode.getTVShowId());
        values.put(KEY_SEASON_NUMBER, episode.getSeasonNumber());
        values.put(KEY_EPISODE_NUMBER, episode.getEpisodeNumber());
        values.put(KEY_TITLE, episode.getTitle());
        values.put(KEY_DATE, episode.getDate());

        // inserting row
        db.insert(TABLE_EPISODES, null, values);
        db.close(); // Closing database connection
    }

    public Episode getEpisodeById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_EPISODES + " WHERE "
                + KEY_ID + " = " + id;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.getCount() == 0 || !cursor.moveToFirst()){
            return null;
        }

        Episode episode = new Episode(cursor.getInt(cursor.getColumnIndex(KEY_ID)),cursor.getInt(cursor.getColumnIndex(KEY_TVSHOW_ID)),
                cursor.getInt(cursor.getColumnIndex(KEY_SEASON_NUMBER)),cursor.getInt(cursor.getColumnIndex(KEY_EPISODE_NUMBER)),cursor.getString(cursor.getColumnIndex(KEY_TITLE)),cursor.getString(cursor.getColumnIndex(KEY_DATE)));

        cursor.close();
        return episode;
    }

    public Episode getEpisode(int tvshowId, int seasonNr, int episodeNr) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_EPISODES + " WHERE "
                + KEY_TVSHOW_ID + " = " + tvshowId + " AND "
                + KEY_SEASON_NUMBER + " = " + seasonNr + " AND "
                + KEY_EPISODE_NUMBER + " = " + episodeNr;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.getCount() == 0 || !cursor.moveToFirst()){
            return null;
        }

        Episode episode = new Episode(cursor.getInt(cursor.getColumnIndex(KEY_ID)),cursor.getInt(cursor.getColumnIndex(KEY_TVSHOW_ID)),
                cursor.getInt(cursor.getColumnIndex(KEY_SEASON_NUMBER)),cursor.getInt(cursor.getColumnIndex(KEY_EPISODE_NUMBER)),cursor.getString(cursor.getColumnIndex(KEY_TITLE)),cursor.getString(cursor.getColumnIndex(KEY_DATE)));

        cursor.close();
        return episode;
    }

    public boolean isEpisode(Episode episode) {
        if (this.getEpisode(episode.getTVShowId(),episode.getSeasonNumber(),episode.getEpisodeNumber()) == null){
            return false;
        }
        return true;
    }

    public List<Episode> getAllEpisodeFromTVShow(int tvshowId) {
        List<Episode> episodes = new ArrayList<Episode>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_EPISODES + " WHERE "
                + KEY_TVSHOW_ID + " = " + tvshowId;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Episode episode = new Episode(cursor.getInt(cursor.getColumnIndex(KEY_ID)),cursor.getInt(cursor.getColumnIndex(KEY_TVSHOW_ID)),
                        cursor.getInt(cursor.getColumnIndex(KEY_SEASON_NUMBER)),cursor.getInt(cursor.getColumnIndex(KEY_EPISODE_NUMBER)),cursor.getString(cursor.getColumnIndex(KEY_TITLE)),cursor.getString(cursor.getColumnIndex(KEY_DATE)));

                episodes.add(episode);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return episodes;
    }

    public void addEpisodesToTVShow(List<String[]> episodesDataList, int tvshowId) {
        Integer episodesCount =  episodesDataList.size();
        for (int i = 0; i < episodesCount; i++) {
            String[] epData = episodesDataList.get(i);
            Episode episode = new Episode(tvshowId,Integer.parseInt(epData[0]),Integer.parseInt(epData[1]),epData[2],epData[3]);
            if (!this.isEpisode(episode)) {
                this.addEpisode(episode);
            }
        }
    }

    public List<Episode> getEpisodesFromDates(String fromDate,String toDate){
        List<Episode> episodes = new ArrayList<Episode>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_EPISODES + " WHERE "
                + "Date(" + KEY_DATE + ") >= Date('" + fromDate + "')"
                + " AND Date(" + KEY_DATE + ") <= Date('" + toDate + "')"
                + "ORDER BY + " + KEY_DATE;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Episode episode = new Episode(cursor.getInt(cursor.getColumnIndex(KEY_ID)),cursor.getInt(cursor.getColumnIndex(KEY_TVSHOW_ID)),
                        cursor.getInt(cursor.getColumnIndex(KEY_SEASON_NUMBER)),cursor.getInt(cursor.getColumnIndex(KEY_EPISODE_NUMBER)),cursor.getString(cursor.getColumnIndex(KEY_TITLE)),cursor.getString(cursor.getColumnIndex(KEY_DATE)));

                episodes.add(episode);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return episodes;
    }

    public List<Episode> getNextEpisodesData(){
        List<Episode> episodes = new ArrayList<Episode>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_EPISODES + " WHERE "
                + "Date(" + KEY_DATE + ") >= DATE('now', '-1 day') "
                + "GROUP BY " + KEY_TVSHOW_ID + " "
                + "ORDER BY " + KEY_DATE;
        Log.d("TEXT",selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Episode episode = new Episode(cursor.getInt(cursor.getColumnIndex(KEY_ID)),cursor.getInt(cursor.getColumnIndex(KEY_TVSHOW_ID)),
                        cursor.getInt(cursor.getColumnIndex(KEY_SEASON_NUMBER)),cursor.getInt(cursor.getColumnIndex(KEY_EPISODE_NUMBER)),cursor.getString(cursor.getColumnIndex(KEY_TITLE)),cursor.getString(cursor.getColumnIndex(KEY_DATE)));

                episodes.add(episode);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return episodes;

    }



    //TODO

    //TV-SHOWS
    //public List<TVShow> getTVShowByTitle(String title) {} //may be more than one result
    //public List<TVShow> getTVShowByYear(String year) {}  //may be more than one result
    //public int getTVShowsCount(){}
    //public int updateTVShow(TVShow tvshow) {}
    //public void deleteTVShow(TVShow tvshow) {)

    //EPISODES
    //public int getEpisodesFromTVShowCount(int tvshowId){}
    //public int getSeasonsFromTVShowCount(int tvshowId){}
    //public int updateEpisode(Episode episode) {}
    //public void deleteEpisode(Episode episode) {)
}
