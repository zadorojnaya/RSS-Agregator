package Feeds;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Naya on 03.03.14.
 */
public class DataBase {
    static Login.DataBase db = new Login.DataBase();
    public static List<String> listURL = new LinkedList<String>();

    public static boolean addURL(String URL){
        try{
            Statement stmt = db.getCon().createStatement();
            stmt.executeUpdate("insert into URL(login,URL)values('"+db.getLastLogin()+"','"+URL+"');");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean LogIn(String Log, String Pass) throws SQLException {
        Statement stmt = db.getCon().createStatement();
        ResultSet rset = stmt.executeQuery("SELECT *from URL");
         while(rset.next()){
            String login = rset.getString("login");
            if(login.equals(db.getLastLogin())){
            listURL.add(rset.getString("URL")+"\">"+rset.getString("Name"));
            return true;
            }
        }
        rset.close();
        return false;
    }

}
