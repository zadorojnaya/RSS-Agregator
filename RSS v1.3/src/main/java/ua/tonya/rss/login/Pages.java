package ua.tonya.rss.login;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import ua.tonya.rss.data.Feeds;
import ua.tonya.rss.data.Links;
import ua.tonya.rss.data.UserData;

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
     * Takes values from all links
     *
     * @param userData structure of user data
     * @throws java.io.IOException
     * @throws org.xml.sax.SAXException
     * @throws javax.xml.parsers.ParserConfigurationException
     */
    public static void prepareList(UserData userData) throws IOException, SAXException, ParserConfigurationException {
        int i = 0;
        while (i < userData.linksList.size()) {
            XMLReader.writeNews(userData, i);
            i++;
        }
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