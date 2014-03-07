package Login;

import Data.UserData;
import Data.Links;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Naya on 06.03.14.
 */
public class DataBase {
    private Connection connect = null;

     public DataBase(){
        try{
            String user = "root";
            String pass = "123";
            String url = "jdbc:mysql://localhost/RSS";
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connect = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {

        }
    }

    boolean LogIn(String login,String pass) throws SQLException {
        Statement stmt;
        ResultSet rset;
        stmt = connect.createStatement();
        rset = stmt.executeQuery("select *from USER ");
        while(rset.next()){
            String lLogin = rset.getString("login");
            String lPass = rset.getString("pass");
            if(lLogin.equals(login) && lPass.equals(pass)) {
                return true;
            }
        }
        rset.close();
        return false;
    }
    boolean Register(String Log, String Pass) {

        try{
            Statement stmt = connect.createStatement();
            stmt.executeUpdate("insert into user (login,pass)values('"+Log+"','"+Pass+"');");
            //Login is a key value in table
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void loadURL(UserData uData)  {
        try {
            Statement stmt = connect.createStatement();
            ResultSet rset = stmt.executeQuery("SELECT *from URL");
            List<Links> links = new ArrayList<Links>();
            while(rset.next()){
                String login = rset.getString("login");
                if(login.equals(uData.login)){
                    Links l = new Links();
                    links.add(l.Links(rset.getString("URL"), rset.getString("Name")));
                }
            }
            uData.linksList = links;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addURL(DataProcessing sessionData, UserData uData,DataBase dBase){
        try{
            Statement stmt;
            stmt = connect.createStatement();
            stmt.executeUpdate("insert into URL(login,URL,Name)values('"+uData.login+
                    "','"+sessionData.URL+"','"+sessionData.addName+"');");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(DataProcessing sessionData, UserData uData, DataBase dBase) throws SQLException {
        try{
            Statement stmt;
            stmt = connect.createStatement();
            stmt.executeUpdate("delete from URL where login='"+uData.login+"' and Name='"
                    +sessionData.removeName+"'");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}