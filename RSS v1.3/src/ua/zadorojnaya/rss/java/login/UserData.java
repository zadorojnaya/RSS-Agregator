package ua.zadorojnaya.rss.java.login;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * this class is a data structure associated with feeds
 *
 * @author
 * Antonina Zadorojnaya */
public class UserData {
    public String login;                                        /*user login*/
    public List<Links> linksList;                               /*list of url from database*/
    public List<Feeds> feedsList;                               /*list of feeds from xml file*/
    public String path;                                         /*path to log files*/
    public Boolean sort=true;                                   /*sort news true - old is first */
    public Set<Feeds> allFeeds = new TreeSet<Feeds>(new Comp());    /*Set of feeds for all feeds*/
    public Boolean connection = true;                           /*connection to internet*/
    public String message;                                      /*message to be displayed*/
    public Object feeds;                                        /*to display on news.jsp*/
}

/**
 * class for sorting data in set of Feeds
 */
class Comp implements Comparator<Feeds> {
    SimpleDateFormat f = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);

    /**
     * Sorting by date.
     * using simple date format
     * @param o1 parameter #1
     * @param o2 parameter #2
     * @return int for compare method
     */
    @Override
    public int compare(Feeds o1, Feeds o2) {
        try {
            return f.parse(o1.publishDate).compareTo(f.parse(o2.publishDate));
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}

/**
 * Data structure of feeds. Obtains from the xml file
 */
class Feeds {
    public String title;            /*title of news*/
    public String link;             /*link whole information*/
    public String publishDate;      /*date of publish*/
    public String author;           /*author*/
    public String description;      /*description*/
}

/**
 * Data structure of links. Obtains from the database
 */
class Links {
    public String url;              /*url of feed*/
    public String name;             /*name of feed*/
}