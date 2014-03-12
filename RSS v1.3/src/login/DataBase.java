package login;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Naya on 11.03.14.
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
            //Error connection to data base.
        }
    }

    boolean logIn(String login,String pass) throws SQLException {
        PreparedStatement pstmt = connect.prepareStatement("Select *from users where login= ? and pass = ?");
        pstmt.setString(1,login);
        pstmt.setString(2,pass);
        ResultSet rset = null;
        List<Links> links = new ArrayList<Links>();
        try{
            if(pstmt.execute()){
                pstmt = connect.prepareStatement("SELECT *from links,users where links.login = users.login");
                rset = pstmt.executeQuery();
                Links l = new Links();
                l.url = rset.getString("URL");
                l.name = rset.getString("Name");
                links.add(l);
                return true;
            }
            else
                return false;       //no login or pass equals
        }
        catch (
                Exception e
                ){
            //error
            return false;
        }
        finally {
           if (rset != null) {
                rset.close();
            }
            if( pstmt != null){
                pstmt.close();
            }

        }
    }

    boolean register(String log, String pass) throws SQLException {
        PreparedStatement pstmt = null;
        /*Login is a key value in table*/
        pstmt = connect.prepareStatement("insert into user (login,pass)values(?,?);");
        pstmt.setString(1,log);
        pstmt.setString(2,pass);
        pstmt.executeUpdate("");
        pstmt.close();
        return true;

    }

    boolean addURL(SessionData sessionData, UserData uData) throws SQLException {
        PreparedStatement pstmt = null;
        pstmt = connect.prepareStatement("insert into URL(login,URL,name)values(?,?,?);");
        pstmt.setString(1,uData.login);
        pstmt.setString(2,sessionData.url);
        pstmt.setString(3,sessionData.addName);
        pstmt.executeUpdate("");
        pstmt.close();
        return true;
    }

    boolean delete(SessionData sessionData, UserData uData) throws SQLException {
        PreparedStatement pstmt = null;
        pstmt = connect.prepareStatement("delete from URL where login= ? and name= ?");
        pstmt.setString(1,uData.login);
        pstmt.setString(2,sessionData.removeName);
        pstmt.executeUpdate("");
        pstmt.close();
        return true;
    }


//    public void loadURL(UserData uData)  {
//        PreparedStatement pstmt = null;
//        try {
//            pstmt = connect.prepareStatement("s");
//            List<Links> links = new ArrayList<Links>();
//            while(rset.next()){
//                String login = rset.getString("login");
//                if(login.equals(uData.login)){
//                    Links l = new Links();
//                    links.add(l.Links(rset.getString("URL"), rset.getString("Name")));
//                }
//            }
//            uData.linksList = links;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
