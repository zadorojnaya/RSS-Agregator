package ua.tonya.rss.data;

import ua.tonya.rss.data.Feeds;
import ua.tonya.rss.data.Links;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * this class is a data structure associated with feeds
 *
 * @author Antonina Zadorojnaya
 */
public class UserData {
    public Set<Feeds> allFeeds = new TreeSet<Feeds>(new Comp());          /*stores all feed*/
    public Boolean connection = true;                           /*connection to internet*/
    public int linkIndex;                                       /*index of chosen link*/
    public List<Links> linksList = new ArrayList<Links>();      /*list of url from database*/
    public Boolean loadLogs = false;                            /*true if loadFile has been called*/
    public String login;                                        /*user login*/
    public String message;                                      /*message to be displayed*/
    public String path;                                         /*path to log files*/
    public Boolean sort = true;                                 /*sort news true - old is first */
    public static StringBuilder databaseConfig = null;                 /*path to server directory*/

    public Boolean getSort(){
        return sort;
    }
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
            return f.parse(o2.publishDate).compareTo(f.parse(o1.publishDate));
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}






