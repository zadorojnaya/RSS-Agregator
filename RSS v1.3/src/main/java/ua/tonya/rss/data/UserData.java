package ua.tonya.rss.data;

import ua.tonya.rss.data.Feeds;
import ua.tonya.rss.data.Links;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * this class is a data structure associated with feeds
 *
 * @author Antonina Zadorojnaya
 */
public class UserData implements Serializable {
    public Set<Feeds> allFeeds = new TreeSet<Feeds>(new Comp());          /*stores all feed*/
    public Boolean connection = true;                           /*connection to internet*/
    public int linkIndex = 0;                                       /*index of chosen link*/
    public List<Links> linksList = new ArrayList<Links>();      /*list of url from database*/
    public Boolean loadLogs = false;                            /*true if loadFile has been called*/
    public String login;                                        /*user login*/
    public String message;                                      /*message to be displayed*/
    public String path;                                         /*path to log files*/
    public Boolean sort = true;                                 /*sort news true - old is first */
    public static StringBuilder databaseConfig = null;                 /*path to server directory*/

    public Boolean getConnection(){
        return connection;
    }
}






