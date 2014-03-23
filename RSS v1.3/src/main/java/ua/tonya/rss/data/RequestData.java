package ua.tonya.rss.data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Data structure of elements from servlet page index.jsp and feeds.jsp
 *
 * @author Antonina Zadorojnaya
 * @version 2 21 Mar 2014
 */
public class RequestData{
    public HttpServletRequest request;
    /*data from index.jsp page*/
    public String button;               /*value of button on index.jsp */
    public String loginLog;             /*user name to login*/
    public String loginReg;             /*user name to registration*/
    public String passwordLog;          /*user password to login*/
    public String passwordReg;          /*user password to registration*/
    public String secondPass;           /*repeat password to registration*/
    /*data from feeds.jsp page*/
    public String addName;              /*name of url to adding*/
    public String feedsButton;          /*value of button on feeds.jsp page */
    public String removeName;           /*name of which url to removing */
    public String url;                  /*url to adding to feeds*/
    /* data from menu.jsp page*/
    public String menuButton;           /*name of chosen feed*/
}

