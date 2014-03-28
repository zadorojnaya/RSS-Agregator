package ua.tonya.rss.login;

import ua.tonya.rss.data.Links;
import ua.tonya.rss.data.RequestData;
import ua.tonya.rss.data.UserData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class works with database
 *
 * @author Antonina Zadorojnaya
 * @version 1.3 12 Mar 2014  *
 */
public class Database {
    private final static Logger log = Logger.getLogger(Database.class.getName());

    /**
     * class constructor.
     * Obtains connection to database
     */
    private static Connection getConnection() {
        try {
                return DriverManager.getConnection(DataInfo.url, DataInfo.user, DataInfo.pass);
            } catch (Exception e) {
                log.info(e.getMessage());
                return null;
            }
    }

    /**
     * Method adding new feeds.
     * Returns true if adding was successful,
     * returns false if no connection with database
     *
     * @param requestData structure of session data
     * @param uData       structure of user data
     * @return
     */
    static boolean addURL(RequestData requestData, UserData uData) {
        if (checkingLinkName(requestData.addName, uData)) {
            try (PreparedStatement pstmt = getConnection().prepareStatement("insert into links(login,URL,name)values(?,?,?);")) {
                pstmt.setString(1, uData.login);
                pstmt.setString(2, requestData.url);
                pstmt.setString(3, requestData.addName);
                pstmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                log.info(e.getMessage());
                return false;
            }
        }
        uData.message = "you have link with this name. Choose another one.";
        return false;
    }

    /**
     * User can not add the same channels.
     * This method checks for the existence of such a name in the database
     * and returns false, if one is already there.
     *
     * @param link to new rss channel
     * @param userData structure of user data
     * @return
     */
    static boolean checkingLinkName(String link, UserData userData) {
        try (PreparedStatement p = getConnection().prepareStatement("SELECT *from links where login= ? and name = ?;");) {
            p.setString(1, userData.login);
            p.setString(2, link);
            p.execute();
            int i = 0;
            try (ResultSet rset = p.executeQuery();) {
                while (rset.next()) {
                    i++;
                }
            }
            if (i == 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e1) {
            log.info(e1.getMessage());
            return false;
        }
    }

    /**
     * delete url from database for user;
     * returns true if deleting was successful,
     * returns false if no connection with database
     *
     * @param requestData structure of session data
     * @param uData       structure of user data
     * @return
     */
    static boolean delete(RequestData requestData, UserData uData) {
        try (PreparedStatement pstmt = getConnection().prepareStatement("delete from links where login= ? and name= ?;")) {
            pstmt.setString(1, uData.login);
            pstmt.setString(2, requestData.removeName);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            log.info(e.getMessage());
            return false;
        }
    }

    /**
     * Method loading all feeds from database.
     * Return true if loading was successful.
     *
     * @param userData structure of user data
     * @return
     */
    static boolean loadURL(UserData userData) {
        List<Links> links = new ArrayList<Links>();             /*List is formed from the table "links"*/
        try (PreparedStatement pstmt = getConnection().prepareStatement("SELECT *from links where login = ?;");) {
            pstmt.setString(1, userData.login);
            try (ResultSet rset = pstmt.executeQuery();) {
                while (rset.next()) {
                    Links l = new Links();
                    l.url = rset.getString("URL");
                    l.name = rset.getString("Name");
                    links.add(l);
                }
                userData.linksList = links;
                return true;
            }
        } catch (SQLException e) {
            log.log(Level.WARNING, "error in boolean Database.loadUrl", e);
            return false;
        }
    }

    /**
     * Returns true if login was successful.
     * Returns false if no connection to database
     *
     * @param login name registered user
     * @param pass  password registered user
     * @return
     */
    static boolean login(String login, String pass, UserData userData) throws Exception {
        try (PreparedStatement pstmt = getConnection().prepareStatement("SELECT *from users where login= ? and pass= password(?) ;")) {
            pstmt.setString(1, login);
            pstmt.setString(2, pass);
            try (ResultSet rset = pstmt.executeQuery();) {
                while (rset.next()) {
                    userData.login = rset.getString("login");
                }
                if (null != userData.login) {
                    return true;
                } else {
                    return false;       //false if no login or pass equals
                }
            }
        } catch (SQLException e) {
            log.info(e.getMessage());
            return false;
        }
    }

    /**
     * Returns true if registration was successful,
     * returns false if login is already exist in database of if no connection with database
     *
     * @param login that user has selected for recording
     * @param pass  that user has selected for recording
     * @return
     */
    static boolean register(String login, String pass) throws Exception {

        /*Login is a key value in table*/
        try (PreparedStatement pstmt = getConnection().prepareStatement("insert into users values(?,password(?));");) {
            pstmt.setString(1, login);
            pstmt.setString(2, pass);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            log.info(e.getMessage());
            return false;
        }
    }

    static void loadDatabase(String realPath){
        UserData.databaseConfig = null;
        UserData.databaseConfig = new StringBuilder(realPath);
        UserData.databaseConfig.append("\\");
        UserData.databaseConfig.append("databaseConfig.xml");
        XMLReader.getDatabaseInfo();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            log.info(e.getMessage());
        }
    }

    /**
     * Data structure for connection to database
     */
    static class DataInfo{
        static String user;
        static String pass;
        static String url;
    }
}