package ua.tonya.rss.data;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Locale;

/**
 * Created by Naya on 21.03.14.
 */
public class Comp implements Comparator<Feeds>, Serializable{
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