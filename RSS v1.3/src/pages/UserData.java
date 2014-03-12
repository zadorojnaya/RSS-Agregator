package pages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Naya on 11.03.14.
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
    public CreatePages page;
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


class Feeds {
    public String title;
    public String link;
    public String publishDate;
    public String author;
    public String description;
}


class Links {
    public String URL;
    public String name;
}


