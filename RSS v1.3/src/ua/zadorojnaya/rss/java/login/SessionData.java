package ua.zadorojnaya.rss.java.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Data structure of elements from servlet page index.jsp and feeds.jsp
 *
 * @version
 * 1 7 Mar 2014
 * @author
 * Antonina Zadorojnaya */
public class SessionData {
    public HttpSession session;
    public HttpServletRequest request;

    /*data from index.jsp page*/
    public String loginLog;             /*user name to login*/
    public String passwordLog;          /*user password to login*/
    public String loginReg;             /*user name to registration*/
    public String passwordReg;          /*user password to registration*/
    public String secondPass;           /*repeat password to registration*/
    public String button;               /*value of button on index.jsp */

    /*data from feeds.jsp page*/
    public String url;                  /*url to adding to feeds*/
    public String addName;              /*name of url to adding*/
    public String removeName;           /*name of which url to removing */
    public String feedsButton;          /*value of button on feeds.jsp page */

    /* data from menu.jsp*/
    public String menuButton;           /*name of chosen feed*/
}

