package ua.zadorojnaya.rss.java.login;

import java.util.ArrayList;
import java.util.List;

/**
 * Class creates list of feeds and menu.
 * @author Antonina Zadorojnaya
 * @version 1.3 13 Mar 2014  *
 */
public class Pages {
    /**
     * Returns list of links to xml files to display in file menu.jsp
     * there are elements of html code
     *
     * @param userData structure of user data
     * @return
     */
    static List<String> menu(UserData userData){
        List<String> menu = new ArrayList<String>();
        int i = 0;
        while(i < userData.linksList.size()){
            StringBuilder temp = new StringBuilder();
            temp.append("<input class=\"flat\" style=\" width:100%;\" type =submit name = \"News\" value =\"");
            temp.append(userData.linksList.get(i).name);
            temp.append("\"><br>");
            menu.add(temp.toString());
            i++;
        }
        return menu;
    }

    /**
     * method creates list of feeds to load in news.jsp
     * result records to variable UserData.feeds
     * there are elements of html code
     *
     * @param userData structure of user data
     */
    static void feeds(UserData userData){
        List<String> feeds = new ArrayList<String>();
        int i;
        if(userData.sort){

            /*new is first*/
            i = 0;
            while(i < userData.feedsList.size()){
                feeds.add(writeFeeds(i,userData));
                i++;
            }
        }else{

            /*old is first*/
            i = userData.feedsList.size() - 1;
            while (i > 0){
                feeds.add(writeFeeds(i,userData));
                i--;
            }
        }
        userData.feeds = feeds;
    }

    /**
     * This method is called only in void method feeds.
     * Returns string of html code for every feed.
     * @param i count of feeds
     * @param userData structure of user data
     * @return
     */
    private static String writeFeeds(int i, UserData userData){
        StringBuilder temp = new StringBuilder();
        temp.append("<a href = \"");
        temp.append(userData.feedsList.get(i).link);
        temp.append("\"><b>");
        temp.append(userData.feedsList.get(i).title);
        temp.append("</b></a><br><i>");
        temp.append(userData.feedsList.get(i).author);
        temp.append("</i><br>");
        temp.append(userData.feedsList.get(i).publishDate);
        temp.append("<br><br>");
        temp.append(userData.feedsList.get(i).description);
        temp.append("<br><br><br>");
        return temp.toString();
    }
}
