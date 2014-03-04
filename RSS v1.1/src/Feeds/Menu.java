package Feeds;

import Feeds.DataBase;
import Login.Servlet;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Naya on 03.03.14.
 */
public class Menu {



    static List<Links> list;

   public  static void createMenu(String filePath,String lastLogin)throws IOException{

        filePath += ("\\Menu.jsp");
        Links buf;
        list = DataBase.loadURL(lastLogin);
       BufferedWriter out = new BufferedWriter(new FileWriter(filePath));
       out.write("<!DOCTYPE html>\n" +
                   "<html>\n" +
                   "<head>\n" +
                   "    <title></title>\n" +
                   "</head>\n" +
                   "<body>" +
                   "<style>"+
                   "input.flat"+"{border:solid 1px black;}" +
                   " li {\n" +
                   "    list-style-type: none;\n" +
                   "   }"+
            "</style>"+"<Form action=\"menuServlet\" method=\"post\"><UL>");
       int i = 0;
       while(i<list.size()){
            out.write("\n<LI><input type=\"submit\" class=\"flat\" value=\""+ list.get(i).getName()+"\" name=\"button\"></LI>");
           i++;
       }
           out.write("</UL></Form></Body></HTML>");
            out.close();

          }

    public static void createNews() throws IOException {
        String path = Servlet.path + ("\\News.html");
        BufferedWriter out = new BufferedWriter(new FileWriter(path));
        out.write("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title></title>\n" +
                "</head>\n" +
                "<body>" +
                "<textarea> Here will be your news!</textarea>\n" +
                "</body></html>");
        out.close();
    }
    public static void createFeed(String name, Feed reader)throws IOException{
        String path ;
        path = Servlet.path + ("\\News.html");
        BufferedWriter out = new BufferedWriter(new FileWriter(path));
        out.write("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title></title>\n" +
                "</head>\n" +
                "<body>" +
                "<textarea>");
        int i = 0;
        while (i <  list.size()){
            if(name == list.get(i).getName()){
                reader.writeNews(list.get(i).getName());
                out.write(DataType.title + "<p>"+DataType.author+"</p> \n"+DataType.description + "<p>" + DataType.link + "</p>");
            }
        }
        out.write("</body></html>");
    }
}
