package ua.tonya.rss.login;

import org.xml.sax.SAXException;
import ua.tonya.rss.data.Links;
import ua.tonya.rss.data.RequestData;
import ua.tonya.rss.data.UserData;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
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
    private final static Logger log = Logger.getLogger(LoginServlet.class.getName());

    /**
     * main method of servlet
     *
     * @param request  Http request
     * @param response http response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession s = request.getSession();

        UserData.databaseConfig = null;
        UserData.databaseConfig = new StringBuilder(getServletContext().getRealPath(""));
        UserData.databaseConfig.append("\\");
        UserData.databaseConfig.append("databaseConfig.xml");

        Database dataBase = new Database();
        UserData userData;
        if (s.getAttribute("uData") != null) {
            userData = (UserData) s.getAttribute("uData");
        } else {
            userData = new UserData();
        }
        RequestData requestData = new RequestData();
        requestData.request = request;
        requestData = dataProcessing(requestData);
        XMLReader.getConnection(userData);
        userData.message = null;

        if (requestData.button != null) {
            if (indexPageProcessing(userData, dataBase, requestData)) {
                dispatcher = request.getRequestDispatcher("feeds.jsp");
            } else {
                dispatcher = request.getRequestDispatcher("index.jsp");
            }
        } else if (requestData.feedsButton != null) {
            feedsPageProcessing(userData, dataBase, requestData);
            dispatcher = request.getRequestDispatcher("feeds.jsp");
        } else if (requestData.menuButton != null) {
            try {
                menuPageProcessing(userData, requestData);
            } catch (SAXException e) {                                              /*there will never be an error*/
            } catch (ParserConfigurationException e) {                              /*because of userData.connection*/
            }
            dispatcher = request.getRequestDispatcher("menu.jsp");
        }

        s.setAttribute("uData", userData);
        if ("Log off".equals(requestData.feedsButton)) {
            s.invalidate();
            dispatcher = request.getRequestDispatcher("index.jsp");
        }
        response.setContentType("text/html");
        if (dispatcher != null) {
            dispatcher.forward(request, response);
        }
    }

    /**
     * Returns a request data object that will be used for further selection methods.
     *
     * @param data from page request
     * @return
     */
    private RequestData dataProcessing(RequestData data) {

        /*from index.jsp*/
        data.button = data.request.getParameter("button");
        data.loginLog = data.request.getParameter("LoginLog");
        data.passwordLog = data.request.getParameter("PassLog");
        data.loginReg = data.request.getParameter("LoginReg");
        data.passwordReg = data.request.getParameter("PassReg");
        data.secondPass = data.request.getParameter("PassRegA");

        /*from feeds.jsp*/
        data.feedsButton = data.request.getParameter("feedButton");
        data.addName = data.request.getParameter("addName");
        data.removeName = data.request.getParameter("removeName");
        data.url = data.request.getParameter("URL");
        data.menuButton = data.request.getParameter("News");

        return data;
    }

    /**
     * Works with page feeds.jsp
     * processes all buttons on this page.
     *
     * @param userData    structure of user data
     * @param dataBase    that the application uses
     * @param requestData structure of request data
     * @throws java.io.IOException
     */
    private void feedsPageProcessing(UserData userData, Database dataBase, RequestData requestData)
            throws IOException {
        if ("add".equals(requestData.feedsButton)) {
            if (userData.connection) {
                if ((null != requestData.url) && (null != requestData.addName) &&
                        (!requestData.url.equals(("")) && (!requestData.addName.equals("")))) {
                    try {
                        if (XMLReader.checkConnection(requestData.url)) {
                            if (dataBase.addURL(requestData, userData)) {

                                /* was added*/
                                Links l = new Links();
                                userData.message = "new URL was added";
                                l.name = requestData.addName;
                                l.url = requestData.url;
                                userData.linksList.add(l);
                                userData.linkIndex = userData.linksList.size() - 1;
                                XMLReader.writeNews(userData);
                                if (!userData.sort) {
                                    XMLReader.reverse(userData, userData.linkIndex);
                                }
                            }
                        } else userData.message = "we can't add this url";
                    } catch (Exception e) {

                        /*error connection to data base*/
                        userData.message = "error connection with database";
                        log.info(e.getMessage());
                    }
                } else {

                    /*please enter URL to add*/
                    userData.message = "enter url to add, please";
                }
            } else {

                /*no connection */
                userData.message = "you can't add news if no internet connection";
            }
        } else if ("remove".equals(requestData.feedsButton)) {
            try {
                if ((requestData.removeName != null) && (!requestData.removeName.equals(""))) {
                    if (dataBase.delete(requestData, userData)) {

                        /* was removed*/
                        userData.message = "if it is still in menu - you couldn't remove it";
                        Links l = new Links();
                        l.name = requestData.removeName;
                        int i = 0;
                        while (userData.linksList.size() > i) {
                            if (l.name.equals(userData.linksList.get(i).name))
                                userData.linksList.remove(i);
                            i++;

                        }
                        userData.linkIndex = -1;
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
        } else if ("Sort by date: new is first".equals(requestData.feedsButton)) {
            if (userData.linkIndex != -1) {
                userData.sort = true;
                XMLReader.reverseAll(userData);
            }
        } else if ("Sort by date: old is first".equals(requestData.feedsButton)) {
            if (userData.linkIndex != -1) {
                userData.sort = false;
                XMLReader.reverseAll(userData);
            }
        } else if ("create logs".equals(requestData.feedsButton)) {
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
     * @param requestData structure of session data
     * @return
     */
    private boolean indexPageProcessing(UserData userData, Database dataBase, RequestData requestData) {
        if ("Sign in".equals(requestData.button)) {
            if (null != requestData.loginLog && null != requestData.passwordLog) {
                try {
                    if (login(requestData.loginLog, requestData.passwordLog, dataBase, userData)) {

                        /* all is good*/
                        return true;
                    } else {

                        /* wrong password*/
                        userData.message = "wrong password or login";
                    }
                } catch (Exception e) {

                    /* error connection to data base*/
                    userData.message = "error connection with database";
                    log.info(e.getMessage());

                }
            } else {

                /* enter password or login*/
                userData.message = "enter login and password";
            }
        } else if ("Create new account".equals(requestData.button)) {
            if (requestData.secondPass.equals(requestData.passwordReg)) {
                try {
                    if (register(requestData.loginReg, requestData.passwordReg, dataBase, userData)) {

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
    private Boolean login(String login, String pass, Database dataBase, UserData userData) throws Exception {
        if (dataBase.login(login, pass, userData)) {
            StringBuilder p = new StringBuilder(getServletContext().getRealPath(""));
            p.append("\\_");
            p.append(login);
            p.append(".xml");
            userData.path = p.toString();
            if (userData.connection) {
                dataBase.loadURL(userData);
                Pages.prepareList(userData);
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
     * @param requestData structure of session data
     */
    private void menuPageProcessing(UserData userData, RequestData requestData) throws IOException,
            SAXException, ParserConfigurationException {
        if ("All Feeds".equals(requestData.menuButton)) {
            userData.linkIndex = -1;
        } else {
            int i = 0;
            while (i < userData.linksList.size()) {
                if (userData.linksList.get(i).name.equals(requestData.menuButton)) {
                    userData.linkIndex = i;
                    break;
                }
                i++;
            }
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
    private boolean register(String login, String pass, Database dataBase, UserData userData) throws Exception {
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