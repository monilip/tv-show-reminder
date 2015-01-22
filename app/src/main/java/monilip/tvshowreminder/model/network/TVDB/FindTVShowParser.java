package monilip.tvshowreminder.model.network.TVDB;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import monilip.tvshowreminder.model.database.TVShow;

/**
 * Created by monilip on 2014-1120.
 *
 * Parsing XML: http://thetvdb.com/api/GetSeries.php?seriesname={tvshow_name}
 */
public class FindTVShowParser {
    private Document content;
    private NodeList seriesNodes;

    public FindTVShowParser(Document content) {
        this.content = content;
        this.seriesNodes = content.getElementsByTagName("Series");

    }

    public int getTVShowsNumber(){
        return seriesNodes.getLength();
    }
    private NodeList getSeriesNodes() {
        return seriesNodes;
    }

    public TVShow getTVShowData(int orderNumber){
        Node tvshow = this.getTVShow(orderNumber);
        TVShow show = new TVShow(this.getTVDBid(tvshow),this.getTitle(tvshow),this.getYear(tvshow),this.getDescription(tvshow));
        return show;
    }

    private String getDescription(Node tvshow) {
        Element seriesInfo = (Element) tvshow;
        NodeList title = seriesInfo.getElementsByTagName("Overview");
        return title.item(0).getTextContent();
    }

    public Node getTVShow(int orderNumber){
        return this.seriesNodes.item(orderNumber);
    }

    private int getTVDBid(Node tvshow) {
        Element seriesInfo = (Element) tvshow;
        NodeList id = seriesInfo.getElementsByTagName("id");
        return Integer.parseInt(id.item(0).getTextContent());
    }

    private String getTitle(Node tvshow) {
        Element seriesInfo = (Element) tvshow;
        NodeList title = seriesInfo.getElementsByTagName("SeriesName");
        return title.item(0).getTextContent();
    }

    private Integer getYear(Node tvshow) {
        Element seriesInfo = (Element) tvshow;
        NodeList firstAired = seriesInfo.getElementsByTagName("FirstAired");
        if (firstAired.item(0) != null) {
            String date = firstAired.item(0).getTextContent();
            //date is in format YYYY-MM-DD and we need only YYYY
            return Integer.parseInt(date.substring(0, 4));
        }
        else{
            return 0;
        }
    }


}
