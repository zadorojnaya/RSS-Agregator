package ua.zadorojnaya.rss.java.login;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 *  This class works with xml files
 *  Checks connection to internet and added links
 *
 * @author Antonina Zadorojnaya
 * @version 1.3 12 Mar 2014
 */
public class XMLReader {

    /**
     * checks connection to internet
     * result recorded to boolean variable UserData.connection
     *
     * @param uData structure of user data
     */
    public static void getConnection(UserData uData){

        try {

            /*we have connection*/
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            java.net.URL oracle = new URL("http://www.dataart.ru/blog/feed");
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
     * Return true if link which user try to add is a XML file
     * @param link link toXML file
     * @return
     */
    public static boolean checkConnection(String link){
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            java.net.URL oracle = new URL(link);
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
     * Method opens and reads XML file
     * Result recorded as a list of feeds to variable UserData.feedsList
     *
     * @param link link which user selected to open
     * @param uData structure of user data
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static void writeNews(Links link,UserData uData) throws ParserConfigurationException, IOException, SAXException {
        List<Feeds> list = new ArrayList<Feeds>();
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            java.net.URL oracle = new URL(link.url);
            URLConnection yc = oracle.openConnection();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(yc.getInputStream());
            doc.getDocumentElement().normalize();
            NodeList nodes = doc.getElementsByTagName("item");
            for(int i = 0; i < nodes.getLength(); ++i) {
                Element element = (Element)nodes.item(i);
                Feeds feed = new Feeds();
                feed.title = getElementValue(element,"title");
                feed.link = getElementValue(element,"link");
                feed.publishDate = getElementValue(element,"pubDate");
                feed.author = getElementValue(element, "dc:creator");
                feed.description = getElementValue(element, "description");
                list.add(feed);
            }
            uData.feedsList = list;
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * method used for reading info from xml file
     * @param e
     * @return
     */
    private static String getCharacterDataFromElement(Element e) {
        try {
            Node child = e.getFirstChild();
            if(child instanceof CharacterData) {
                CharacterData cd = (CharacterData) child;
                return cd.getData();
            }
        }catch(Exception ex) {

        }
        return "";
    }

    /**
     * method used for reading info from xml file
     * @param parent
     * @param label
     * @return
     */
    private static String getElementValue(Element parent, String label) {
        return getCharacterDataFromElement((Element)parent.getElementsByTagName(label).item(0));
    }
}