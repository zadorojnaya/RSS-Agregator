package ua.zadorojnaya.rss.java.login;

import org.xml.sax.SAXException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Servlet class
 *
 * @author Antonina Zadorojnaya
 * @version 1.3 12 Mar 2014
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
    private RequestDispatcher dispatcher;

    /**
     * main method of servlet
     *
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        HttpSession s = request.getSession();
        Database dataBase;
        UserData userData;
        Cookie sessionCookie;
        if ((sessionCookie = newCookie(request)) != null) {
            if ((s.getAttribute("dBase") != null) && (s.getAttribute("uData") != null)) {
                dataBase = (Database) s.getAttribute("dBase");
                userData = (UserData) s.getAttribute("uData");
            } else {
                dataBase = new Database();
                userData = new UserData();
            }
            Cookie cookie = new Cookie("JSESSIONID", sessionCookie.getValue());
            response.addCookie(cookie);
        } else {
            dataBase = new Database();
            userData = new UserData();
        }
        SessionData sessionData = new SessionData();
        sessionData.request = request;
        sessionData = dataProcessing(s, sessionData);
        XMLReader.getConnection(userData);
        userData.message = null;
        if (sessionData.button != null) {
            if (indexPageProcessing(userData, dataBase, sessionData)) {
                dispatcher = request.getRequestDispatcher("feeds.jsp");
            } else {
                dispatcher = request.getRequestDispatcher("index.jsp");
            }
        }else if (sessionData.feedsButton != null) {
            feedsPageProcessing(userData, dataBase, sessionData);
             dispatcher = request.getRequestDispatcher("feeds.jsp");
        }else if(sessionData.menuButton != null){
            try {
                s.setAttribute("allNews", menuPageProcessing(userData, sessionData));
            } catch (SAXException e) {                                              /*there will never be an error*/
            } catch (ParserConfigurationException e) {                              /*because of userData.connection*/
            }
            dispatcher = request.getRequestDispatcher("menu.jsp");
        }
        s.setAttribute("dBase", dataBase);
        s.setAttribute("uData", userData);
        s.setAttribute("message",userData.message);
        if(userData.linksList != null){
            s.setAttribute("list",Pages.menu(userData));
        }
        if (dispatcher != null) {
            dispatcher.forward(request, response);
        }

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
    private Boolean login(String login, String pass, Database dataBase, UserData userData) throws SQLException {
        if (dataBase.login(login, pass,userData)) {
            dataBase.loadURL(userData);
            userData.path = getServletContext().getRealPath("") + "\\" + login;
            return true;
        } else {
            return false;
        }
    }

    /**
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
        if(dataBase.register(login, pass)){
            userData.login = login;
            userData.path = getServletContext().getRealPath("");
            return true;
        }else {
            return false;
        }
    }

    /**
     * Method returns session cookie that used for determining initial values
     *
     * @param request
     * @return session cookie
     */
    private Cookie newCookie(HttpServletRequest request) {
        Cookie cookies[] = request.getCookies();
        Cookie sessionCookie = null;
        if (cookies != null) {
            for (int i = 0; i < cookies.length; ++i) {
                if (cookies[i].getName().equals("JSESSIONID")) {
                    sessionCookie = cookies[i];
                }
            }
        }
        return sessionCookie;
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

                        /* error connection to data Bbase*/
                    userData.message = "error connection with database";
                    e.printStackTrace();

                }
            } else {

                    /* pass not equals*/
                userData.message = "passes not equals";
            }
        }
        return false;
    }

    /**
     * Works with page feeds.jsp
     * processes all buttons on this page.
     *
     * @param userData    structure of user data
     * @param dataBase    that the application uses
     * @param sessionData structure of session data
     * @throws IOException
     */
    private void feedsPageProcessing(UserData userData, Database dataBase, SessionData sessionData) throws IOException {

        if ("add".equals(sessionData.feedsButton)) {
            if (userData.connection) {
                if ((null != sessionData.url) && (null != sessionData.addName) &&
                        (!sessionData.url.equals(("")) && (!sessionData.addName.equals("")))) {
                    try {
                        if (dataBase.addURL(sessionData, userData)) {

                            /* was added*/
                            userData.message = "new URL was added";

                            /*refresh menu*/
                            Pages.menu(userData);
                        }
                    } catch (Exception e) {

                        /*error connection to data base*/
                        userData.message = "error connection with database";
                        e.printStackTrace();
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
                            userData.message = "url was removed";

                            /*refresh menu*/
                            Pages.menu(userData);
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
                }
            } else {

                 /*no connection */
                userData.message = "you can't remove news if no internet connection";
            }
        } else if ("Sort by date: new is first".equals(sessionData.feedsButton)) {
            if (userData.connection) {
                userData.sort = true;
                Pages.feeds(userData);
            } else {

                     /*no connection */
                userData.message = "you can't sort news if no internet connection";
            }
        } else if ("Sort by date: old is first".equals(sessionData.feedsButton)) {
            if (userData.connection) {
                userData.sort = false;
                Pages.feeds(userData);
            } else {

                     /*no connection */
                userData.message = "you can't sort news if no internet connection";
            }
        } else if ("create logs".equals(sessionData.feedsButton)) {
            if (userData.connection) {

                    /*create logs*/
                userData.message = "logs were created";
            } else {

                     /*no connection */
                userData.message = "you can't create logs if no internet connection";
            }
        }
    }

    /**
     * Return List of Feeds to be loaded to news.jsp
     * @param userData    structure of user data
     * @param sessionData structure of session data
     */
    private Object menuPageProcessing(UserData userData, SessionData sessionData) throws IOException,
            SAXException, ParserConfigurationException {

        if(userData.connection){
            int i = 0;
            while(i < userData.linksList.size()) {
                if(userData.linksList.get(i).name.equals(sessionData.menuButton))
                XMLReader.writeNews(userData.linksList.get(i),userData);
                i++;
            }
           return Pages.feeds(userData);
        }else{

            /*Loading info from file*/

        }
        return null;
    }
}