package Feeds;

import Feeds.DataBase;
import Login.Servlet;
import com.sun.java.util.jar.pack.*;


import javax.servlet.ServletContext;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Naya on 03.03.14.
 */
public class CreatePages {



    public static List<Links> list;

   public  static void createMenu(String filePath,String lastLogin)throws IOException{

        filePath += ("\\Menu.jsp");
        Links buf;
        list = DataBase.loadURL(lastLogin);
       BufferedWriter out = new BufferedWriter(new FileWriter(filePath));
       out.write("<%@ page contentType=\"text/html;charset=UTF-8\" language=\"java\" %>\n<!DOCTYPE html>\n" +
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
            out.write("\n<LI><input type=\"submit\" style=\"width:200px;\" class=\"flat\" value=\""+ list.get(i).getName()+"\" name=\"button\"></LI>");
           i++;
       }
           out.write("</UL></Form></Body></HTML>");
            out.close();

          }

    public static void createNews() throws IOException {
        String path = Servlet.path + ("\\News.jsp");
        BufferedWriter out = new BufferedWriter(new FileWriter(path));
        out.write("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title></title>\n" +
                "</head>\n" +
                "<body>" +
                "<Form action=\"newsServlet\" method=\"post\"> \n"+
               "<input type = \"submit\" value = \"Download\">\n"
    +"</Form>\n" +
                "Your news will be here. Jast click the button \"Download\"</body></html>"
        );
        out.close();
    }

    public static void createFeed(Feed reader)throws IOException{
        String path ;
        path = Servlet.path + ("\\News.jsp");
        BufferedWriter out = new BufferedWriter(new FileWriter(path));
        out.write("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                " <title></title>\n"+
                "</head>\n" +
                "<body>"+
                "<Form action=\"newsServlet\" method=\"post\"> \n"+
                        "<input type = \"submit\">\n"
                        +"</Form>\n"+
                "");
        List<DataType> s = findFeed(reader);
            int count = 0;
            while(count < s.size()){
                out.write( "<a href = \"" + s.get(count).link + "\"><b>" + s.get(count).title + "</b></a><br><i>"
                        + s.get(count).author + "</i>\n<br>" + s.get(count).publishDate +
                        "<br><br>" + s.get(count).description+"<br><br><br>");
                count++;
            }
        out.write("</body></html>");
        out.close();
        }


    private static List<DataType> findFeed(Feed reader) throws IOException {
        int i = 0;
        List<DataType> s = new ArrayList<DataType>();
        while (i < list.size()){
            String buf = list.get(i).getName();
           File f =  refresh(i,buf);

        if(DataType.canal.equals(buf)){
//        reader.writeNews(f);
             s = reader.writeNews(f);
          }
            ++i;
        }
        return s;
    }

    private static File refresh(int i, String name) throws IOException {
        String path = Servlet.path;
        path += "/Resources/src/log/"+ name+".xml";
        File f = new File(path);
        BufferedWriter out = new BufferedWriter(new FileWriter(path));
        URL oracle = new URL(list.get(i).getURL());
        URLConnection yc = oracle.openConnection();
        InputStreamReader in = new InputStreamReader(
                yc.getInputStream());

       // StringBuffer inputLine = new StringBuffer();
        char[] cBuf = new char[10000];
        int len;
        while( (len = in.read(cBuf) )> 0){
             out.write(cBuf,0,len);
        }
        in.close();
        out.close();
        return f;
    }
}