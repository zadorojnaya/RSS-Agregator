package ua.tonya.rss.data;

import ua.tonya.rss.data.Feeds;

import java.util.ArrayList;
import java.util.List;

/**
 * Data structure of links. Obtains from the database
 */
public class Links {
    public List<Feeds> feedsList = new ArrayList<Feeds>();   /*list of news from xml file*/
    public String name;                                      /*name of feed*/
    public String url;                                       /*url of feed*/

    public String getName(){
        return name;
    }
}