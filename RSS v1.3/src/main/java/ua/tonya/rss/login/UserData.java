package ua.tonya.rss.login;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * this class is a data structure associated with feeds
 *
 * @author Antonina Zadorojnaya
 */
public class UserData {
    public Set<Feeds> allFeeds = new TreeSet<Feeds>();          /*stores all feed*/
    public Boolean connection = true;                           /*connection to internet*/
    public Object feeds;                                        /*to display on news.jsp*/
    public int linkIndex;                                       /*index of chosen link*/
    public List<Links> linksList = new ArrayList<Links>();      /*list of url from database*/
    public Boolean loadLogs = false;                            /*true if loadFile has been called*/
    public String login;                                        /*user login*/
    public String message;                                      /*message to be displayed*/
    public String path;                                         /*path to log files*/
    public Boolean sort = true;                                   /*sort news true - old is first */
}

/**
 * class for sorting data in set of Feeds
 */
class Comp implements Comparator<Feeds> {
    SimpleDateFormat f = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);

    /**
     * Sorting by date.
     * using simple date format
     *
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
    public String author;           /*author*/
    public String description;      /*description*/
    public String link;             /*link whole information*/
    public String publishDate;      /*date of publish*/
    public String title;            /*title of news*/
}

/**
 * Data structure of links. Obtains from the database
 */
class Links {
    public List<Feeds> feedsList = new ArrayList<Feeds>();   /*list of news from xml file*/
    public String name;                                      /*name of feed*/
    public String url;                                       /*url of feed*/
}