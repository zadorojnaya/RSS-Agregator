package ua.zadorojnaya.rss.java.login;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * this class works with database
 *
 * @author Antonina Zadorojnaya
 * @version 1.3 12 Mar 2014  *
 */
public class Database {
    private Connection connect = null;
    private static Logger log = Logger.getLogger(Database.class.getName());
    /**
     * class constructor.
     * Obtains connection to database
     */
    public Database() {
        try {
            String user = "root";
            String pass = "123";
            String url = "jdbc:mysql://localhost/RSS";
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connect = DriverManager.getConnection(url, user, pass);
            FileHandler fh = new FileHandler("logs.log");
            log.addHandler(fh);
        } catch (Exception e) {
            log.log(Level.WARNING,"error connection with database",e);
            //Error connection to data base.
        }
    }

    /**
     * Returns true if login was successful.
     * Returns false if no connection to database
     *
     * @param login name registered user
     * @param pass  password registered user
     * @return
     * @throws java.sql.SQLException
     */
    boolean login(String login, String pass, UserData userData) throws SQLException {
        PreparedStatement pstmt = connect.prepareStatement("SELECT *from users where login= ? and pass = ?;");
        pstmt.setString(1, login);
        pstmt.setString(2, pass);
        ResultSet rset = null;
        try {
            rset = pstmt.executeQuery();
            while(rset.next()){
                userData.login = rset.getString("login");
            }
            if(null != userData.login){
                return true;
            }else{
                return false;       //false if no login or pass equals
            }
        } catch ( Exception e) {

            //error
            log.log(Level.WARNING, "error in boolean Database.login", e);
            return false;
        } finally {
            if (rset != null) {
                rset.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    /**
     * Method loading all fedds from database.
     * Return true if loading was successful.
     *
     * @param userData   structure of user data
     * @return
     * @throws SQLException
     */
    boolean loadURL(UserData userData) throws SQLException {
        PreparedStatement pstmt = null;
        List<Links> links = new ArrayList<Links>();             /*List is formed from the table "links"*/
        ResultSet rset = null;
        try {
            pstmt = connect.prepareStatement("SELECT *from links where login = ?;");
            pstmt.setString(1, userData.login);
            rset = pstmt.executeQuery();
            while(rset.next()){
                Links l = new Links();
                l.url = rset.getString("URL");
                l.name = rset.getString("Name");
                links.add(l);
            }
            userData.linksList = links;
            return true;
        } catch (SQLException e) {
            log.log(Level.WARNING, "error in boolean Database.loadUrl", e);
            return false;
        }finally {
            if (rset != null) {
                rset.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    /**
     * Returns true if registration was successful,
     * returns false if login is already exist in database of if no connection with database
     *
     * @param login  that user has selected for recording
     * @param pass that user has selected for recording
     * @return
     */
    boolean register(String login, String pass) throws SQLException {
        PreparedStatement pstmt = null;

        /*Login is a key value in table*/
        try {
            pstmt = connect.prepareStatement("insert into users (login,pass)values(?,?);");
            pstmt.setString(1, login);
            pstmt.setString(2, pass);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            log.log(Level.WARNING,"error in boolean Database.register",e);
            return false;
        } finally {
            if (pstmt != null)
                pstmt.close();
        }
    }

    /**
     * Method adding new feeds.
     * Returns true if adding was successful,
     * returns false if no connection with database
     *
     * @param sessionData structure of session data
     * @param uData       structure of user data
     * @return
     * @throws SQLException
     */
    boolean addURL(SessionData sessionData, UserData uData) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = connect.prepareStatement("insert into links(login,URL,name)values(?,?,?);");
            pstmt.setString(1, uData.login);
            pstmt.setString(2, sessionData.url);
            pstmt.setString(3, sessionData.addName);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            log.log(Level.WARNING, "error in boolean Database.addURL", e);
            return false;

        }finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }


    }

    /**
     * delete url from database for user;
     * returns true if deleting was successful,
     * returns false if no connection with database
     *
     * @param sessionData structure of session data
     * @param uData       structure of user data
     * @return
     * @throws SQLException
     */
    boolean delete(SessionData sessionData, UserData uData) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = connect.prepareStatement("delete from links where login= ? and name= ?;");
            pstmt.setString(1, uData.login);
            pstmt.setString(2, sessionData.removeName);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            log.log(Level.WARNING, "error in boolean Database.addURL", e);
            return false;
        }finally {
            pstmt.close();
        }
    }
}
