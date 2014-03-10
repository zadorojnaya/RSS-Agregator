package Pages;

import Data.Feeds;
import Login.DataBase;
import Data.UserData;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Naya on 06.03.14.
 */
public class CreatePages {
    public void createMenu(UserData uData,DataBase dBase) throws IOException, ParserConfigurationException, SAXException {
        dBase.loadURL(uData);
        StringBuilder path = new StringBuilder(uData.path);
            File file = new File(path.toString());
            if(!file.exists()){
                file.mkdirs();
            }
            path.append("\\Menu.jsp");
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(path.toString()), "UTF-8"));
            XMLReader reader = new XMLReader();
            out.write("<%@ page contentType=\"text/html;charset=Utf-8\"%><!DOCTYPE html>" +
                    "\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "<title></title>\n" +
                    "<style>  input.flat\n" +
                    "        {border:solid 1px black;\n" +
                    "\n" +
                    "        }\n" +
                    "        A {\n" +
                    "            text-decoration: none;\n" +
                    "        } body {margin:0;}</style>" +

                    "</head>\n" +
                    "<body >" );
            int i = 0;
            while(i<uData.linksList.size()){
                String news =  uData.linksList.get(i).getName();
                reader.writeNews(uData.linksList.get(i),uData);
                createFeeds(uData,news);
                out.write("\n<button class=\"flat\" style=\" width:100%;\"><a href = \""+news+".html\" target=\"news\">"+
                           news+"</a></button><br>");
                i++;
            }
            createFirstNews(uData);
            out.write("</Body></HTML>");
            out.close();

    }

   public void createFeeds(UserData uData, String name) throws IOException{
       StringBuilder path = new StringBuilder(uData.path);
       File file = new File(path.toString());
       if(!file.exists()){
           file.mkdirs();
       }
       path.append("\\"+name+".html");
       BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
               new FileOutputStream(path.toString()), "UTF-8"));
       out.write("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                " <meta charset=\"UTF-8\"/><title></title>\n"+
                "</head>\n" +
                "<body>");
       int count ;
       if(uData.sort){
            count = 0;
            while(count < uData.feedsList.size()){
                out.write( "<a href = \"" + uData.feedsList.get(count).link + "\"><b>" + uData.feedsList.get(count).title +
                         "</b></a><br><i>" + uData.feedsList.get(count).author + "</i>\n<br>" +
                        uData.feedsList.get(count).publishDate +
                        "<br><br>" + uData.feedsList.get(count).description+"<br><br><br>");
                count++;
            }
       }
       else{
            count = uData.feedsList.size()-1;
            while(count >= 0){
                out.write( "<a href = \"" + uData.feedsList.get(count).link + "\"><b>" + uData.feedsList.get(count).title +
                        "</b></a><br><i>" + uData.feedsList.get(count).author + "</i>\n<br>" +
                        uData.feedsList.get(count).publishDate +
                        "<br><br>" + uData.feedsList.get(count).description+"<br><br><br>");
                count--;
            }
       }
       out.write("</body></html>");
       out.close();

   }

   public void createFirstNews(UserData uData) throws IOException {
       StringBuilder path = new StringBuilder(uData.path);
       File file = new File(path.toString());
       if(!file.exists()){
           file.mkdirs();
       }
       path.append("\\allNews.html");
       BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
               new FileOutputStream(path.toString()), "UTF-8"));
       out.write("<html>\n" +
               "<head>\n" +
               "<meta charset=\"UTF-8\"/> <title></title>\n"+
               "</head>\n" +
               "<body>");
       List<Feeds> f = new ArrayList<Feeds>();
       f.addAll(uData.allFeeds);
       int count;
       if(uData.sort){
           count = 0;
           while(count < uData.allFeeds.size()){
               out.write( "<a href = \"" + f.get(count).link + "\"><b>" + f.get(count).title +
                       "</b></a><br><i>" + f.get(count).author + "</i>\n<br>" +
                       f.get(count).publishDate +
                       "<br><br>" + f.get(count).description+"<br><br><br>");
               count++;
           }
       }
       else{
           count = uData.allFeeds.size()-1;
               while(count >= 0){
                   out.write( "<a href = \"" + f.get(count).link + "\"><b>" + f.get(count).title +
                           "</b></a><br><i>" + f.get(count).author + "</i>\n<br>" +
                           f.get(count).publishDate +
                           "<br><br>" + f.get(count).description+"<br><br><br>");
                   count--;
               }
       }
       out.write("</body></html>");
       out.close();
  }

   public void createLogs(UserData uData) throws IOException{
        StringBuilder path = new StringBuilder(uData.path + "\\" + uData.login);
        File copy = new File(System.getenv("APPDATA")+"\\"+ uData.login);
        File original = new File(path.toString());
        copy(original,copy);
   }

    public void copy(File original, File copy){

        if(original.isDirectory())
        {
            copy.mkdir();

            String[] file = original.list();
            for(int i=0; i<file.length; i++)
                copy(new File(original, file[i]), new File(copy, file[i]));
        }
        else
        {
            try
            {
                FileChannel
                        reader = new FileInputStream(original).getChannel(),
                        writer = new FileOutputStream(copy).getChannel();

                writer.transferFrom(reader, 0, original.length());
                reader.close();
                writer.close();
            }
            catch(Exception e)
            {
                e.printStackTrace(System.err);
            }
        }
    }
}


