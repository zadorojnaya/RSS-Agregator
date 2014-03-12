package pages;

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
 * Created by Naya on 11.03.14.
 */
public class XMLReader {
    public static boolean getConnection(UserData uData){

        try {
            //we have connection
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            java.net.URL oracle = new URL("http://www.dataart.ru/blog/feed");
            URLConnection yc = oracle.openConnection();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(yc.getInputStream());
            doc.getDocumentElement().normalize();
            uData.connection = true;
            return true;
        } catch (Exception e) {
            // no connection. Now we'll connected to local storage
            e.printStackTrace();
            uData.connection = false;
            return false;
        }
    }

    public void writeNews(Links link,UserData uData) throws ParserConfigurationException, IOException, SAXException {
        List<Feeds> list = new ArrayList<Feeds>();
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            java.net.URL oracle = new URL(link.URL);
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
            uData.allFeeds.addAll(uData.feedsList);
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private String getCharacterDataFromElement(Element e) {
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

    private String getElementValue(Element parent,String label) {
        return getCharacterDataFromElement((Element)parent.getElementsByTagName(label).item(0));
    }
}
