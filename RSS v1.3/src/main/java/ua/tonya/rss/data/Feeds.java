package ua.tonya.rss.data;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Locale;

/**
 * Data structure of feeds. Obtains from the xml file
 */
 public class Feeds {
    public String author;           /*author*/
    public String description;      /*description*/
    public String link;             /*link whole information*/
    public String publishDate;      /*date of publish*/
    public String title;            /*title of news*/
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