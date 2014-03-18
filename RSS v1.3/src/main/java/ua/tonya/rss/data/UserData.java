package ua.tonya.rss.data;

import ua.tonya.rss.data.Feeds;
import ua.tonya.rss.data.Links;

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
    public Boolean sort = true;                                 /*sort news true - old is first */
    public static StringBuilder databaseConfig = null;                 /*path to server directory*/
}







