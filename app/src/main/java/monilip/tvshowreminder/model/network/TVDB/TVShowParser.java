package monilip.tvshowreminder.model.network.TVDB;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

import monilip.tvshowreminder.model.database.TVShow;

/**
 * Created by monilip on 2014-11-09.
 *
 * Parsing XML: http://thetvdb.com/data/series/{tvdb_id}/all
 */
public class TVShowParser {
    private Document content;
    private Node seriesNode;
    private NodeList episodesNodes;

    public TVShowParser(Document content) {
        this.content = content;
        NodeList nodes = content.getElementsByTagName("Series");
        this.seriesNode = nodes.item(0);
        this.episodesNodes = content.getElementsByTagName("Episode");
    }

    //TVshow
    private Node getSeriesNode() {
        return seriesNode;
    }

    public int getTVDBid() {
        Element seriesInfo = (Element) this.getSeriesNode();
        NodeList id = seriesInfo.getElementsByTagName("id");
        return Integer.parseInt(id.item(0).getTextContent());
    }

    private String getTitle() {
        Element seriesInfo = (Element) this.getSeriesNode();
        NodeList title = seriesInfo.getElementsByTagName("SeriesName");
        return title.item(0).getTextContent();
    }

    private Integer getYear() {
        Element seriesInfo = (Element) this.getSeriesNode();
        NodeList id = seriesInfo.getElementsByTagName("FirstAired");
        String date = id.item(0).getTextContent();
        //date is in format YYYY-MM-DD and we need only YYYY
        return Integer.parseInt(date.substring(0,4));
    }

    public String getDescription() {
        Element seriesInfo = (Element) this.getSeriesNode();
        NodeList title = seriesInfo.getElementsByTagName("SeriesName");
        return title.item(0).getTextContent();
    }

    public TVShow getTVShow(){
        TVShow TVShow = new TVShow(this.getTVDBid(), this.getTitle(),this.getYear(),this.getDescription());
        return TVShow;
    }

    //Episodes
    private NodeList getEpisodesNodes() {
        return episodesNodes;
    }

    private String getElementValue(Element element, String name) {
        Element value = (Element) element.getElementsByTagName(name).item(0);
        return value.getTextContent();
    }


    public List<String[]> getEpisodesDataList(){
        List<String[]> episodes = new ArrayList<String[]>();
        Integer episodesCount =  this.getEpisodesNodes().getLength();
        for (int i = 0; i < episodesCount; i++) {
            Element element = (Element) this.getEpisodesNodes().item(i);
            if (Integer.parseInt(this.getElementValue(element, "SeasonNumber")) != 0){
                String[] episode = new String[5];
                episode[0] = this.getElementValue(element, "SeasonNumber");
                episode[1] = this.getElementValue(element, "EpisodeNumber");
                episode[2] = this.getElementValue(element, "EpisodeName");
                episode[3] = this.getElementValue(element, "FirstAired");
                episode[4] = this.getElementValue(element, "Overview");
                episodes.add(episode);
             }
        }
        return episodes;
    }
}
