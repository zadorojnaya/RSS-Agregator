package Feeds;


/**
 * Created by Naya on 03.03.14.
 */

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Feed {
    private static Feed instance = null;
    private static File u;
  //  public static StringBuilder stb = new StringBuilder();
    private Feed() {
    }
    public static Feed getInstance() {
        if(instance == null)
            instance = new Feed();
        return instance;
    }
    public static String getURL() throws ParserConfigurationException, IOException, SAXException {
        String URL;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        DocumentBuilder db = dbf.newDocumentBuilder();
        InputStream is = new FileInputStream(u);


        Document doc = db.parse(is);

        doc.getDocumentElement().normalize();
        NodeList nodes = doc.getElementsByTagName("channel");
        Node fstNode = nodes.item(0);
        //if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
            Element fstElmnt = (Element) fstNode;
            NodeList d = fstElmnt.getElementsByTagName("firstname");
            Element fstNmElmnt = (Element) d.item(0);
            NodeList fstNm = fstNmElmnt.getChildNodes();
            URL = fstNm.item(0).getNodeValue().toString();

        return URL;

    }
    public List<DataType> writeNews(File file) {
        List<DataType> listFeed = new ArrayList<DataType>();
        try {
            u = file;

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            DocumentBuilder db = dbf.newDocumentBuilder();
            InputStream is = new FileInputStream(file);


            Document doc = db.parse(is);

            doc.getDocumentElement().normalize();


//            File u = file;
//            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//       //     URL u = new URL("http://www.dataart.ru/blog/feed");//feed from dataBase;
//
//            Document doc = builder.parse(u);
            NodeList nodes = doc.getElementsByTagName("item");
            for(int i = 0; i < nodes.getLength(); ++i) {
                Element element = (Element)nodes.item(i);
                DataType d = new DataType();
                d.title = getElementValue(element,"title");
                d.link = getElementValue(element,"link");
                d.publishDate = getElementValue(element,"pubDate");
                d.author = getElementValue(element, "dc:creator");
                d.description = getElementValue(element, "description");
                listFeed.add(d);
            }//for
        }//try
        catch(Exception ex) {
            ex.printStackTrace();
        }
        return listFeed;
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
