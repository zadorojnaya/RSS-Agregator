package Feeds;


/**
 * Created by Naya on 03.03.14.
 */

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.net.URL;

public class Feed {
    private static Feed instance = null;
  //  public static StringBuilder stb = new StringBuilder();
    private Feed() {
    }
    public static Feed getInstance() {
        if(instance == null)
            instance = new Feed();
        return instance;
    }

    public void writeNews(String URL) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            URL u = new URL(URL);//feed from dataBase;
            Document doc = builder.parse(u.openStream());
            NodeList nodes = doc.getElementsByTagName("item");
            for(int i=0;i<nodes.getLength();i++) {
                Element element = (Element)nodes.item(i);

                DataType.title = getElementValue(element,"title");
                DataType.link = getElementValue(element,"link");
                DataType.publishDate = getElementValue(element,"pubDate");
                DataType.author = getElementValue(element, "dc:creator");
                DataType.description = getElementValue(element, "description");

            }//for
        }//try
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
    } //private String getCharacterDataFromElement

//    protected float getFloat(String value) {
//        if(value != null && !value.equals(""))
//            return Float.parseFloat(value);
//        else
//            return 0;
//    }

    protected String getElementValue(Element parent,String label) {
        return getCharacterDataFromElement((Element)parent.getElementsByTagName(label).item(0));
    }

}
