package ua.tonya.rss.login;

import org.w3c.dom.CharacterData;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * This class works with xml files
 * Checks connection to internet and added links
 *
 * @author Antonina Zadorojnaya
 * @version 1.3 12 Mar 2014
 */
public class XMLReader {
    private final static Logger log = Logger.getLogger(Database.class.getName());

    /**
     * Return true if link which user try to add is a XML file
     *
     * @param link link to XML file
     * @return
     */
    public static boolean checkConnection(String link) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            URL oracle = new URL(link);
            URLConnection yc = oracle.openConnection();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(yc.getInputStream());
            doc.getDocumentElement().normalize();
            return true;
        } catch (Exception e) {

            /*if it's no a xml file*/
            return false;
        }
    }

    /**
     *
     * @param uData structure of user data
     * @return
     */
    static boolean createFirstNews(UserData uData) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputStream is = new FileInputStream(uData.path);
            Document doc = db.parse(is);
            doc.getDocumentElement().normalize();

            NodeList feedNode = doc.getElementsByTagName("feed");
            List<Feeds> feedsList = new ArrayList<Feeds>();
            for (int j = 0; j < feedNode.getLength(); ++j) {
                Feeds f = new Feeds();
                Element feed = (Element) feedNode.item(j);

                f.author = getElementValue(feed, "author");
                f.title = getElementValue(feed, "title");
                f.publishDate = getElementValue(feed, "date");
                f.link = getElementValue(feed, "linkfeed");
                f.description = getElementValue(feed, "description");

                feedsList.add(f);
            }
            uData.allFeeds.addAll(feedsList);
            return true;
        } catch (Exception e) {
            log.info(e.getMessage());
            return false;
        }
    }

    /**
     * Read XMl user file;
     *
     * @param uData structure of user data
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws java.io.IOException
     * @throws org.xml.sax.SAXException
     */
    public static Boolean fileRead(UserData uData) throws ParserConfigurationException, IOException, SAXException {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputStream is = new FileInputStream(uData.path);
            Document doc = db.parse(is);
            doc.getDocumentElement().normalize();

            NodeList nodes = doc.getElementsByTagName("Link");
            List<Links> l = new ArrayList<Links>();
            for (int i = 0; i < nodes.getLength(); ++i) {
                Element element = (Element) nodes.item(i);
                Links link = new Links();

                link.name = getElementValue(element, "name");
                link.url = getElementValue(element, "url");

                NodeList feedNode = doc.getElementsByTagName("feed");
                List<Feeds> feedsList = new ArrayList<Feeds>();
                for (int j = 0; j < feedNode.getLength(); ++j) {
                    Feeds f = new Feeds();
                    Element feed = (Element) feedNode.item(j);
                    if (feed.getAttribute("link").equals(link.name)) {
                        f.author = getElementValue(feed, "author");
                        f.title = getElementValue(feed, "title");
                        f.publishDate = getElementValue(feed, "date");
                        f.link = getElementValue(feed, "linkfeed");
                        f.description = getElementValue(feed, "description");

                        feedsList.add(f);
                    }
                }
                link.feedsList = feedsList;
                uData.allFeeds.addAll(feedsList);
                l.add(link);
            }
            uData.linksList = l;

            uData.loadLogs = true;
            return true;
        } catch (Exception e) {
            log.info(e.getMessage());
            return false;
        }
    }

    /**
     * method used for reading info from xml file
     *
     * @param el element from xml file
     * @return
     */
    private static String getCharacterDataFromElement(Element el) {
        try {
            Node child = el.getFirstChild();
            if (child instanceof CharacterData) {
                CharacterData cd = (CharacterData) child;
                return cd.getData();
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return "";
    }

    /**
     * checks connection to internet
     * result recorded to boolean variable UserData.connection
     *
     * @param uData structure of user data
     */
    public static void getConnection(UserData uData) {

        try {

            /*we have connection*/
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            URL oracle = new URL("http://www.dataart.ru/blog/feed");
            URLConnection yc = oracle.openConnection();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(yc.getInputStream());
            doc.getDocumentElement().normalize();
            uData.connection = true;
        } catch (Exception e) {

            /* no connection. Now we'll connected to local storage*/
            uData.connection = false;
        }
    }

    /**
     * method used for reading info from xml file
     *
     * @param parent
     * @param label
     * @return
     */
    private static String getElementValue(Element parent, String label) {
        return getCharacterDataFromElement((Element) parent.getElementsByTagName(label).item(0));
    }

    /**
     * Method opens and reads XML file
     * Result recorded as a list of feeds to variable UserData.feedsList
     *
     * @param uData structure of user data
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws java.io.IOException
     * @throws org.xml.sax.SAXException
     */
    public static void writeNews(UserData uData, int index) throws ParserConfigurationException, IOException, SAXException {
        List<Feeds> list = new ArrayList<Feeds>();
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            URL oracle = new URL(uData.linksList.get(index).url);
            URLConnection yc = oracle.openConnection();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(yc.getInputStream());
            doc.getDocumentElement().normalize();
            NodeList nodes = doc.getElementsByTagName("item");
            for (int i = 0; i < nodes.getLength(); ++i) {
                Element element = (Element) nodes.item(i);
                Feeds feed = new Feeds();
                feed.title = getElementValue(element, "title");
                feed.link = getElementValue(element, "link");
                feed.publishDate = getElementValue(element, "pubDate");
                feed.author = getElementValue(element, "dc:creator");
                feed.description = getElementValue(element, "description");
                list.add(feed);
            }
            uData.linksList.get(index).feedsList = list;
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    /**
     * Overloading
     * @param uData structure of user data
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    public static void writeNews(UserData uData) throws IOException, SAXException, ParserConfigurationException {
        writeNews(uData, uData.linkIndex);
    }
}