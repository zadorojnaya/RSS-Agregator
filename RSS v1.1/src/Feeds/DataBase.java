package Feeds;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Naya on 03.03.14.
 */
public class DataBase {
    static Login.DataBase db = new Login.DataBase();


    public static boolean addURL(String URL,String name){
        try{
            Statement stmt = db.getCon().createStatement();
            stmt.executeUpdate("insert into URL(login,URL,Name)values('"+MainPageServlet.getLastLogin()+"','"+URL+"','"+name+"');");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static List<Links> loadURL(String lastlogin)  {
        Statement stmt = null;
         List<Links> listURL = new ArrayList<Links>();
        try {
            stmt = db.getCon().createStatement();
            ResultSet rset = stmt.executeQuery("SELECT *from URL");
            while(rset.next()){
                String login = rset.getString("login");
                if(login.equals(lastlogin)){
                    Links l = new Links();
                     listURL.add(l.Links(rset.getString("URL"),rset.getString("Name")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
return listURL;
//        rset.close();

    }

}
