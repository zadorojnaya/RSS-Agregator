package ua.tonya.rss.data;


import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Locale;

/**
 * Data structure of feeds. Obtains from the xml file
 */
 public class Feeds{
    public String author;           /*author*/
    public String description;      /*description*/
    public String link;             /*link whole information*/
    public String publishDate;      /*date of publish*/
    public String title;            /*title of news*/

    public String getTitle() {
        return title;
    }

    public String getAuthor(){
        return author;
    }

    public String getDescription(){
        return description;
    }

    public String getLink(){
        return link;
    }

    public String getPublishDate(){
        return publishDate;
    }
}




