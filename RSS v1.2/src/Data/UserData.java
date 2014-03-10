package Data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Naya on 06.03.14.
 */
public class UserData {
    public String login;
    public List<Links> linksList;
    public List<Feeds> feedsList;
    public String path;
    public Boolean sort=true;
    public Set<Feeds> allFeeds = new TreeSet<Feeds>(new Comp());
    public Boolean fileSave = false;
    public Boolean connection = true;

//    class Comp implements Comparator<Feeds> {
//
//        @Override
//        public int compare(Feeds obj1, Feeds obj2) {
//            return obj1.publishDate.compareTo(obj2.publishDate);
//        }
//    }

}
    class Comp implements Comparator<Feeds> {
        SimpleDateFormat f = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
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


