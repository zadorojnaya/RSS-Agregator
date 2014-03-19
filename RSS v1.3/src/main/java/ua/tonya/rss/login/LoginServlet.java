package ua.tonya.rss.login;

import org.xml.sax.SAXException;
import ua.tonya.rss.data.Feeds;
import ua.tonya.rss.data.Links;
import ua.tonya.rss.data.SessionData;
import ua.tonya.rss.data.UserData;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * Servlet class
 *
 * @author Antonina Zadorojnaya
 * @version 1.3 12 Mar 2014
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
    private RequestDispatcher dispatcher;
    private final static Logger log = Logger.getLogger(Database.class.getName());

    /**
     * main method of servlet
     *
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        HttpSession s = request.getSession();

        UserData.databaseConfig = null;
        UserData.databaseConfig = new StringBuilder(getServletContext().getRealPath(""));
        UserData.databaseConfig.append("\\");
        UserData.databaseConfig.append("databaseConfig.xml");

        Database dataBase;
        UserData userData;

        if ((s.getAttribute("dBase") != null) && (s.getAttribute("uData") != null)) {
            dataBase = (Database) s.getAttribute("dBase");
            userData = (UserData) s.getAttribute("uData");
        } else {
            dataBase = new Database();
            userData = new UserData();
        }

        SessionData sessionData = new SessionData();
        sessionData.request = request;
        sessionData = dataProcessing(s, sessionData);
        XMLReader.getConnection(userData);
        try {
            XMLReader.fileRead(userData);
        } catch (Exception e) {
        }
        userData.message = null;

        if (sessionData.button != null) {
            if (indexPageProcessing(userData, dataBase, sessionData)) {
                dispatcher = request.getRequestDispatcher("feeds.jsp");
            } else {
                dispatcher = request.getRequestDispatcher("index.jsp");
            }
        } else if (sessionData.feedsButton != null) {
            feedsPageProcessing(userData, dataBase, sessionData);
            dispatcher = request.getRequestDispatcher("feeds.jsp");
        } else if (sessionData.menuButton != null) {
            try {
                s.setAttribute("feeds",menuPageProcessing(userData, sessionData));
            } catch (SAXException e) {                                              /*there will never be an error*/
            } catch (ParserConfigurationException e) {                              /*because of userData.connection*/
            }
            dispatcher = request.getRequestDispatcher("menu.jsp");
        }

        s.setAttribute("dBase", dataBase);
        s.setAttribute("uData", userData);
        s.setAttribute("message", userData.message);

        if (userData.linksList != null) {
            s.setAttribute("list", userData.linksList);
        }

        s.setAttribute("allFeeds",userData.allFeeds);
        if (dispatcher != null) {
            dispatcher.forward(request, response);
        }

        dataBase.connectionClose();
    }

    /**
     * Returns an SessionData object that will be used for further selection methods.
     *
     * @param s HttpSession that was obtained at the beginning
     * @return
     */
    private SessionData dataProcessing(HttpSession s, SessionData data) {

        /*from index.jsp*/
        s.setAttribute("buffer", data.request.getParameter("button"));
        data.button = (String) s.getAttribute("buffer");
        s.setAttribute("buffer", data.request.getParameter("LoginLog"));
        data.loginLog = (String) s.getAttribute("buffer");
        s.setAttribute("buffer", data.request.getParameter("PassLog"));
        data.passwordLog = (String) s.getAttribute("buffer");
        s.setAttribute("buffer", data.request.getParameter("LoginReg"));
        data.loginReg = (String) s.getAttribute("buffer");
        s.setAttribute("buffer", data.request.getParameter("PassReg"));
        data.passwordReg = (String) s.getAttribute("buffer");
        s.setAttribute("buffer", data.request.getParameter("PassRegA"));
        data.secondPass = (String) s.getAttribute("buffer");

        /*from feeds.jsp*/
        s.setAttribute("buffer", data.request.getParameter("feedButton"));
        data.feedsButton = (String) s.getAttribute("buffer");
        s.setAttribute("buffer", data.request.getParameter("addName"));
        data.addName = (String) s.getAttribute("buffer");
        s.setAttribute("buffer", data.request.getParameter("removeName"));
        data.removeName = (String) s.getAttribute("buffer");
        s.setAttribute("buffer", data.request.getParameter("URL"));
        data.url = (String) s.getAttribute("buffer");
        data.session = s;
        s.setAttribute("buffer", data.request.getParameter("News"));
        data.menuButton = (String) s.getAttribute("buffer");
        return data;
    }

    /**
     * Works with page feeds.jsp
     * processes all buttons on this page.
     *
     * @param userData    structure of user data
     * @param dataBase    that the application uses
     * @param sessionData structure of session data
     * @throws java.io.IOException
     */
    private void feedsPageProcessing(UserData userData, Database dataBase, SessionData sessionData)
            throws IOException {

        if ("add".equals(sessionData.feedsButton)) {
            if (userData.connection) {
                if ((null != sessionData.url) && (null != sessionData.addName) &&
                        (!sessionData.url.equals(("")) && (!sessionData.addName.equals("")))) {
                    try {
                        if (XMLReader.checkConnection(sessionData.url)) {
                            if (dataBase.addURL(sessionData, userData)) {

                                /* was added*/
                                Links l = new Links();
                                userData.message = "new URL was added";
                                l.name = sessionData.addName;
                                l.url = sessionData.url;
                                userData.linksList.add(l);

                                /*refresh menu*/

                            }
                        } else userData.message = "we can't add this url";
                    } catch (Exception e) {

                        /*error connection to data base*/
                        userData.message = "error connection with database";
                        log.info(e.getMessage());
                    }
                } else {

                    /*please enter URL to add*/
                    userData.message = "enter url to add? please";
                }
            } else {

                /*no connection */
                userData.message = "you can't add news if no internet connection";
            }
        } else if ("remove".equals(sessionData.feedsButton)) {
            if (userData.connection) {
                try {
                    if ((sessionData.removeName != null) && (!sessionData.removeName.equals(""))) {
                        if (dataBase.delete(sessionData, userData)) {

                            /* was removed*/
                            userData.message = "if it is still in menu - you couldn't remove";
                            Links l = new Links();
                            l.name = sessionData.removeName;
                            int i = 0;
                            while (userData.linksList.size() > i) {
                                if (l.name.equals(userData.linksList.get(i).name))
                                    userData.linksList.remove(i);
                                i++;
                            }

                            /*refresh menu*/

                        } else {

                            /*you have no this item in your feeds list*/
                            userData.message = "no items found to be removed";
                        }

                    } else {

                        /* please enter feed to remove*/
                        userData.message = "enter item to remove, please";
                    }
                } catch (Exception e) {

                    /*error connection to database*/
                    userData.message = "error connection with database";
                    log.info(e.getMessage());
                }
            } else {

                 /*no connection */
                userData.message = "you can't remove news if no internet connection";
            }
        } else if ("Sort by date: new is first".equals(sessionData.feedsButton)) {
            userData.sort = false;
            XMLReader.reverse(userData);
        } else if ("Sort by date: old is first".equals(sessionData.feedsButton)) {
            userData.sort = true;
            XMLReader.reverse(userData);
        } else if ("create logs".equals(sessionData.feedsButton)) {
            if (userData.connection) {

                    /*create logs*/
                Pages.createLogs(userData);
                userData.message = "logs were created";
            } else {

                     /*no connection */
                userData.message = "you can't create logs if no internet connection";
            }
        }
    }

    /**
     * working with the page index.jsp
     * processes login or register.
     * if all goes well - returns true for the subsequent transition to feeds.jsp
     *
     * @param userData    structure of user data
     * @param dataBase    that the application uses
     * @param sessionData structure of session data
     * @return
     */
    private boolean indexPageProcessing(UserData userData, Database dataBase, SessionData sessionData) {
        if ("Sign in".equals(sessionData.button)) {
            if (null != sessionData.loginLog && null != sessionData.passwordLog) {
                try {
                    if (login(sessionData.loginLog, sessionData.passwordLog, dataBase, userData)) {

                        /* all is good*/
                        return true;
                    } else {

                        /* wrong password*/
                        userData.message = "wrong password or login";
                    }
                } catch (Exception e) {

                    /* error connection to data base*/
                    userData.message = "error connection with database";
                    e.printStackTrace();

                }
            } else {

                /* enter password or login*/
                userData.message = "enter login and password";
            }
        } else if ("Create new account".equals(sessionData.button)) {
            if (sessionData.secondPass.equals(sessionData.passwordReg)) {
                try {
                    if (register(sessionData.loginReg, sessionData.passwordReg, dataBase, userData)) {

                            /* how to work with rss-aggregator*/
                        return true;
                    } else {

                            /* choose another login*/
                        userData.message = "choose another login, please";
                    }
                } catch (Exception e) {

                        /* error connection to database*/
                    userData.message = "error connection with database";
                    log.info(e.getMessage());

                }
            } else {

                    /* pass not equals*/
                userData.message = "passes not equals";
            }
        }
        return false;
    }

    /**
     * Returns true if login and password are equals with those that exist in the database.
     * Returns false if password or login not equals to database data or if no connection with database
     *
     * @param login    name registered user
     * @param pass     password registered user
     * @param dataBase that the application uses
     * @param userData structure of user data
     * @return
     * @throws java.sql.SQLException
     */
    private Boolean login(String login, String pass, Database dataBase, UserData userData) throws SQLException,
            IOException, SAXException, ParserConfigurationException {
        if (dataBase.login(login, pass, userData)) {
            StringBuilder p = new StringBuilder(getServletContext().getRealPath(""));
            p.append("\\_");
            p.append(login);
            p.append(".xml");
            userData.path = p.toString();
            if (userData.connection) {
                dataBase.loadURL(userData);
            } else {
                if (!XMLReader.fileRead(userData)) {
                    userData.message = "You have no internet connection!";
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Return List of Feeds to be loaded to news.jsp
     *
     * @param userData    structure of user data
     * @param sessionData structure of session data
     */
    private List<Feeds> menuPageProcessing(UserData userData, SessionData sessionData) throws IOException,
            SAXException, ParserConfigurationException {
        int i = 0;
        while (i < userData.linksList.size()) {
            if (userData.linksList.get(i).name.equals(sessionData.menuButton)) {
                userData.linkIndex = i;
                break;
            }
            i++;
        }
        if (userData.connection) {
            XMLReader.writeNews(userData);
        } else if (!userData.loadLogs) {
            XMLReader.fileRead(userData);
        }
        return userData.linksList.get(userData.linkIndex).feedsList;
    }

    /**
     *
     *
     * Returns true if registration was successful.
     * Returns false if no connection with database or already have a login
     *
     * @param login    that user has selected for recording
     * @param pass     that user has selected for recording
     * @param dataBase that the application uses
     * @param userData structure of user data
     * @return boolean
     */
    private boolean register(String login, String pass, Database dataBase, UserData userData) throws SQLException {
        if (dataBase.register(login, pass)) {
            userData.login = login;
            StringBuilder p = new StringBuilder(getServletContext().getRealPath(""));
            p.append("\\_");
            p.append(login);
            p.append(".xml");
            userData.path = p.toString();
            return true;
        } else {
            return false;
        }
    }
}

