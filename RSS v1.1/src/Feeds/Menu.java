package Feeds;

import Feeds.DataBase;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.ListIterator;

/**
 * Created by Naya on 03.03.14.
 */
public class Menu {
    private static String fileName = new String ("menu.html");
    static PrintWriter outFile;
   public static void post()throws IOException{

       ListIterator<String> itrURL = Feeds.DataBase.listURL.listIterator();
             try {
           outFile = new PrintWriter(String.valueOf(new FileReader(fileName)));
           outFile.print("<!DOCTYPE html>\n" +
                   "<html>\n" +
                   "<head>\n" +
                   "    <title></title>\n" +
                   "</head>\n" +
                   "<body>" + "<UL>");
           while(itrURL.hasNext()){
               outFile.print("<LI><a href="+itrURL.next()+"</A");
           }
           outFile.print("</UL></Body></HTML>");

       } catch (FileNotFoundException e) {
           e.printStackTrace();
       }

   }


}
