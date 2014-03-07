package Pages;

import Data.Feeds;
import Data.Links;
import Data.UserData;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Naya on 06.03.14.
 */
public class XMLReader {


    public void writeNews(Links link,UserData uData) throws ParserConfigurationException, IOException, SAXException {
        List<Feeds> list = new ArrayList<Feeds>();
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            java.net.URL oracle = new URL(link.getURL());
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
            uData.path = uData.localpath;

        }
        catch(Exception ex) {
            ex.printStackTrace();
            if(uData.FileSave){
                uData.localpath = uData.path;
                uData.path = System.getenv("APPDATA")+"\\"+uData.login;
            }
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
