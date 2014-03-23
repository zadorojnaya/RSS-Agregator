package ua.tonya.rss.login;

import ua.tonya.rss.data.DataInfo;
import ua.tonya.rss.data.Links;
import ua.tonya.rss.data.RequestData;
import ua.tonya.rss.data.UserData;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    private Connection connect = null;
    private final static Logger log = Logger.getLogger(Database.class.getName());

    /**
     * class constructor.
     * Obtains connection to database
     */
    public boolean getConnection() {
        if(XMLReader.getDatabaseInfo()){
                try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connect = DriverManager.getConnection(DataInfo.url, DataInfo.user, DataInfo.pass);
                return true;
            } catch (ClassNotFoundException e) {
                log.info(e.getMessage());
                return false;
            } catch (SQLException e) {
                log.info(e.getMessage());
                return false;
            } catch (InstantiationException e) {
                log.info(e.getMessage());
                return false;
            } catch (IllegalAccessException e) {
                log.info(e.getMessage());
                return false;
            } catch (Exception e) {
                log.info(e.getMessage());
                return false;
                }
        } else {
            return false;
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
    boolean addURL(RequestData requestData, UserData uData) {


        if(checkingLinkName(requestData.addName,uData)){
            this.getConnection();
            try (PreparedStatement pstmt = connect.prepareStatement("insert into links(login,URL,name)values(?,?,?);") ){
                pstmt.setString(1, uData.login);
                pstmt.setString(2, requestData.url);
                pstmt.setString(3, requestData.addName);
                pstmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                log.info(e.getMessage());
                return false;
            } finally {
                this.connectionClose();
            }
        }
        uData.message = "you have link with this name. Choose another one.";
        return false;

    }

    boolean checkingLinkName(String link, UserData userData){
        this.getConnection();
        try(PreparedStatement p = connect.prepareStatement("SELECT *from links where login= ? and name = ?;");){
            p.setString(1, userData.login);
            p.setString(2, link);
            p.execute();
            int i = 0;
            try(ResultSet rset = p.executeQuery();){
                while (rset.next()) {
                 i++;
                }
            }
            if(i == 0){
                return true;
            } else {
                return false;
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
            return false;
        }finally {
            this.connectionClose();
        }
    }

    boolean connectionClose(){
        try {
            connect.close();
            return true;
        } catch (SQLException e) {
            log.info(e.getMessage());
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
    boolean delete(RequestData requestData, UserData uData) {
        this.getConnection();
        try (PreparedStatement pstmt = connect.prepareStatement("delete from links where login= ? and name= ?;")) {
            pstmt.setString(1, uData.login);
            pstmt.setString(2, requestData.removeName);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            log.info(e.getMessage());
            return false;
        }finally {
            this.connectionClose();
        }
    }

    /**
     * Method loading all feeds from database.
     * Return true if loading was successful.
     *
     * @param userData structure of user data
     * @return
     */
    boolean loadURL(UserData userData) {
        this.getConnection();
        List<Links> links = new ArrayList<Links>();             /*List is formed from the table "links"*/
        try (PreparedStatement pstmt = connect.prepareStatement("SELECT *from links where login = ?;");){
            pstmt.setString(1, userData.login);
            try( ResultSet rset = pstmt.executeQuery();){
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
        }finally {
            this.connectionClose();
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
    boolean login(String login, String pass, UserData userData) throws Exception {
        this.getConnection();
        try (PreparedStatement pstmt = connect.prepareStatement("SELECT *from users where login= ? and pass= password(?) ;")){
            pstmt.setString(1, login);
            pstmt.setString(2, pass);
            try(ResultSet rset = pstmt.executeQuery();){
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
        }finally {
            this.connectionClose();
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
    boolean register(String login, String pass) throws Exception {
        this.getConnection();
        /*Login is a key value in table*/
        try ( PreparedStatement pstmt = connect.prepareStatement("insert into users values(?,password(?));");) {
            pstmt.setString(1, login);
            pstmt.setString(2, pass);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            log.info(e.getMessage());
            return false;
        }finally {
            this.connectionClose();
        }
    }


}
