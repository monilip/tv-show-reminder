package monilip.tvshowreminder.network.TVDB;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import monilip.tvshowreminder.database.TVShow;

/**
 * Created by monilip on 2014-11-09.
 */
public class TVShowParser {
    private Document content;
    private Node seriesNode;
    private NodeList episodesNodes;

    public TVShowParser(Document content) {
        this.content = content;
        NodeList nodes = content.getElementsByTagName("Series");
        this.seriesNode = nodes.item(0);
        this.episodesNodes = content.getElementsByTagName("Episodes");
    }

    private Node getSeriesNode() {
        return seriesNode;
    }

    private NodeList getEpisodesNodes() {
        return episodesNodes;
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
        return Integer.parseInt(date.substring(0,3));
    }

    public TVShow getTVShow(){
        TVShow TVShow = new TVShow(this.getTVDBid(), this.getTitle(),this.getYear());
        return TVShow;
    }
}
