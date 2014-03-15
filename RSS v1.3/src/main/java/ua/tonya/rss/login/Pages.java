package ua.tonya.rss.login;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Class creates list of feeds and menu.
 *
 * @author Antonina Zadorojnaya
 * @version 1.3 13 Mar 2014
 */
public class Pages {
    private static DocumentBuilder builder;
    private final static Logger log = Logger.getLogger(Database.class.getName());

    /**
     * Returns true if  creating logs was successful
     *
     * @param userData structure of user data
     * @return
     */
    public static boolean createLogs(UserData userData) {
        try {
            prepareList(userData);
            ParamLangXML();
            WriteParamXML(userData);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * method creates list of feeds to load in news.jsp
     * result records to variable UserData.feeds
     * there are elements of html code
     *
     * @param userData structure of user data
     */
    static void feeds(UserData userData) {
        List<String> feeds = new ArrayList<String>();
        int i;
        Links l = userData.linksList.get(userData.linkIndex);
        if (userData.sort) {

            /*new is first*/
            i = 0;
            while (i < l.feedsList.size()) {
                feeds.add(writeFeeds(i, userData));
                i++;
            }
        } else {

            /*old is first*/
            i = l.feedsList.size() - 1;
            while (i > 0) {
                feeds.add(writeFeeds(i, userData));
                i--;
            }
        }
        userData.feeds = feeds;
    }

    /**
     * Returns list of links to xml files to display in file menu.jsp
     * there are elements of html code
     *
     * @param userData structure of user data
     * @return
     */
    static List<String> menu(UserData userData) {
        List<String> menu = new ArrayList<String>();
        int i = 0;
        while (i < userData.linksList.size()) {
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
     * Initialization xml
     */
    private static void ParamLangXML() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            log.info(e.getMessage());
        }
    }

    /**
     * Write all missing values
     *
     * @param userData structure of user data
     * @throws java.io.IOException
     * @throws org.xml.sax.SAXException
     * @throws javax.xml.parsers.ParserConfigurationException
     */
    private static void prepareList(UserData userData) throws IOException, SAXException, ParserConfigurationException {
        int i = 0;
        while (i < userData.linksList.size()) {
            if (userData.linksList.get(i).feedsList == null) {
                XMLReader.writeNews(userData, i);
            }
            i++;
        }
    }

    /**
     * This method is called only in void method feeds.
     * Returns string of html code for every feed.
     *
     * @param newsId   count of feeds
     * @param userData structure of user data
     * @return
     */
    private static String writeFeeds(int newsId, UserData userData) {
        StringBuilder temp = new StringBuilder();
        Links l = userData.linksList.get(userData.linkIndex);
        temp.append("<a href = \"");
        temp.append(l.feedsList.get(newsId).link);
        temp.append("\"><b>");
        temp.append(l.feedsList.get(newsId).title);
        temp.append("</b></a><br><i>");
        temp.append(l.feedsList.get(newsId).author);
        temp.append("</i><br>");
        temp.append(l.feedsList.get(newsId).publishDate);
        temp.append("<br><br>");
        temp.append(l.feedsList.get(newsId).description);
        temp.append("<br><br><br>");
        return temp.toString();
    }


    /**
     * Record to xml file
     *
     * @param userData structure of user data
     * @throws javax.xml.transform.TransformerException
     * @throws java.io.IOException
     */
    private static void WriteParamXML(UserData userData) throws TransformerException, IOException {

        Document doc = builder.newDocument();
        Element LinkList = doc.createElement("LinkList");
        int linkIndex = 0;
        while (linkIndex < userData.linksList.size()) {
            Element Link = doc.createElement("Link");
            Links l = userData.linksList.get(linkIndex);

            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode(l.name));
            Link.appendChild(name);

            Element url = doc.createElement("url");
            url.appendChild(doc.createTextNode(l.url));
            Link.appendChild(url);

            Element feedList = doc.createElement("feedList");

            int feedIndex = 0;
            while (feedIndex < l.feedsList.size()) {
                Feeds f = l.feedsList.get(feedIndex);
                Element feed = doc.createElement("feed");
                feed.setAttribute("link", l.name);

                Element author = doc.createElement("author");
                author.appendChild(doc.createTextNode(f.author));
                feed.appendChild(author);

                Element date = doc.createElement("date");
                date.appendChild(doc.createTextNode(f.publishDate));
                feed.appendChild(date);

                Element title = doc.createElement("title");
                title.appendChild(doc.createTextNode(f.title));
                feed.appendChild(title);

                Element feedlink = doc.createElement("linkfeed");
                feedlink.appendChild(doc.createTextNode(f.link));
                feed.appendChild(feedlink);

                Element descr = doc.createElement("description");
                descr.appendChild(doc.createTextNode(f.description));
                feed.appendChild(descr);

                feedIndex++;
                feedList.appendChild(feed);
            }
            linkIndex++;
            Link.appendChild(feedList);
            LinkList.appendChild(Link);
        }

        doc.appendChild(LinkList);

        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(userData.path)));
    }
}