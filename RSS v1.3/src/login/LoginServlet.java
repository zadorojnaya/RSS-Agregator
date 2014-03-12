package login;

import pages.CreatePages;
import pages.UserData;
import pages.XMLReader;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Naya on 11.03.14.
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet{
    private RequestDispatcher dispatcher;

    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        HttpSession s = request.getSession();
        DataBase dataBase;
        UserData userData;
        Cookie sessionCookie;
        if((sessionCookie = newCookie(request)) != null){
            if((s.getAttribute("dBase") != null)&&(s.getAttribute("uData") != null)){
                dataBase = (DataBase) s.getAttribute("dBase");
                userData = (UserData) s.getAttribute("uData");
            }else {
                dataBase = new DataBase();
                userData = new UserData();
            }
            Cookie cookie = new Cookie ("JSESSIONID",sessionCookie.getValue());
            response.addCookie(cookie);
        }else{
            dataBase = new DataBase();
            userData = new UserData();
        }

        SessionData sessionData = dataProcessing(s,request);
        dispatcher = request.getRequestDispatcher("index.jsp");
        XMLReader.getConnection(userData);
        if(sessionData.button != null){
            indexPageProcessing(userData, dataBase, sessionData);
        }
        if(sessionData.feedsButton != null){
            feedsPageProcessing(userData,dataBase,sessionData);
        }
        if(dispatcher != null){
            dispatcher.forward(request,response);
        }
        s.setAttribute("dBase",dataBase);
        s.setAttribute("uData",userData);
    }

    private SessionData dataProcessing(HttpSession s,HttpServletRequest request){
        SessionData data = new SessionData();
        s.setAttribute("buffer", request.getParameter("button"));
        data.button = (String)s.getAttribute("buffer");
        s.setAttribute("buffer",request.getParameter("LoginLog"));
        data.loginLog = (String)s.getAttribute("buffer");
        s.setAttribute("buffer",request.getParameter("PassLog"));
        data.passwordLog = (String)s.getAttribute("buffer");
        s.setAttribute("buffer",request.getParameter("LoginReg"));
        data.loginReg = (String)s.getAttribute("buffer");
        s.setAttribute("buffer",request.getParameter("PassReg"));
        data.passwordReg = (String)s.getAttribute("buffer");
        s.setAttribute("buffer",request.getParameter("PassRegA"));
        data.secondPass = (String)s.getAttribute("buffer");

        /*from feeds.jsp*/
        s.setAttribute("buffer", request.getParameter("feedButton"));
        data.feedsButton = (String)s.getAttribute("buffer");
        s.setAttribute("buffer", request.getParameter("addName"));
        data.addName = (String)s.getAttribute("buffer");
        s.setAttribute("buffer", request.getParameter("removeName"));
        data.removeName = (String)s.getAttribute("buffer");
        s.setAttribute("buffer", request.getParameter("URL"));
        data.URL = (String)s.getAttribute("buffer");
        data.session = s;
        return data;
    }

    private  Boolean login(String login, String pass, DataBase dataBase, UserData userData) throws SQLException {
        if(dataBase.logIn(login, pass)){
            userData.login = login;
            userData.path = getServletContext().getRealPath("")+"\\"+login;
            return true;
        } else {
            return false;
        }
    }

    private boolean register(String login, String pass, DataBase dataBase, UserData userData){
        try {
            dataBase.Register(login,pass);
            userData.login = login;
            userData.path = getServletContext().getRealPath("")+"\\"+login;
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Cookie newCookie(HttpServletRequest request){
        Cookie cookies[] = request.getCookies();
        Cookie sessionCookie = null;
        if(cookies != null){
            for(int i = 0; i < cookies.length; ++i){
                if(cookies[i].getName().equals("JSESSIONID")){
                    sessionCookie = cookies[i];
                }
            }
        }
        return sessionCookie;
    }

    private void indexPageProcessing(UserData userData, DataBase dataBase, SessionData sessionData){
        if("Sign in".equals(sessionData.button)){
            if (null != sessionData.loginLog && null != sessionData.passwordLog){
                try {
                    if(login(sessionData.loginLog,sessionData.passwordLog,dataBase,userData)){
                        if((userData.connection == false)){
                            File file = new File(userData.path.toString()+"\\"+"\\Menu.jsp");
                            if((!file.exists())){
                                file.mkdirs();
                                sessionData.session.setAttribute("Login", null);
                                sessionData.session.setAttribute("News", null);
                           //     s.setAttribute("URLCon","If you here at first - add a new Feed!!!") ;
                            }
                            sessionData.session.setAttribute("Login", sessionData.loginLog + "\\Menu.jsp");
                            sessionData.session.setAttribute("News", sessionData.loginLog + "\\allNews.html");
                        }else{
                            userData.page.createMenu(userData, dataBase);
                            sessionData.session.setAttribute("Login", sessionData.loginLog + "\\Menu.jsp");
                            sessionData.session.setAttribute("News", sessionData.loginLog + "\\allNews.html");
                        }
                        dispatcher = sessionData.request.getRequestDispatcher("feeds.jsp");
                    }else{
                        sessionData.session.setAttribute("sessionLabel", "Wrong password or login");
                        dispatcher = sessionData.request.getRequestDispatcher("index.jsp");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    sessionData.session.setAttribute("sessionLabel", "Error connection to DataBase");
                    dispatcher = sessionData.request.getRequestDispatcher("index.jsp");
                }
            }else{
                sessionData.session.setAttribute("sessionLabel", "Wrong password or login");
                dispatcher = sessionData.request.getRequestDispatcher("index.jsp");
            }
        }else{if("Create new account".equals(sessionData.button)){
                if(sessionData.secondPass.equals(sessionData.passwordReg)){
                    try {
                        if(register(sessionData.loginReg, sessionData.passwordReg,dataBase,userData)){
                            userData.page.createMenu(userData, dataBase);
                            if(userData.connection){
                                sessionData.session.setAttribute("Login", sessionData.loginReg + "\\Menu.jsp");
                                sessionData.session.setAttribute("News", sessionData.loginReg + "\\allNews.html");
                            }else{
                                sessionData.session.setAttribute("Login", null);
                                sessionData.session.setAttribute("News", null);
                            }
                            sessionData.session.setAttribute("URLCon", "Add a new Feed!!!");
                            dispatcher = sessionData.request.getRequestDispatcher("feeds.jsp");
                        }else{
                            sessionData.session.setAttribute("sessionLabel", "chose another login");
                            dispatcher = sessionData.request.getRequestDispatcher("index.jsp");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        sessionData.session.setAttribute("sessionLabel", "Error connection to DataBase");
                        dispatcher = sessionData.request.getRequestDispatcher("index.jsp");
                    }
                }else{
                    sessionData.session.setAttribute("sessionLabel", "pass not equals");
                    dispatcher = sessionData.request.getRequestDispatcher("index.jsp");
                }
            }
        }
    }

    private void feedsPageProcessing(UserData userData, DataBase dataBase, SessionData sessionData) throws IOException {
//        dispatcher = request.getRequestDispatcher("feeds.jsp");
        if("add".equals(sessionData.feedsButton)){
            if(userData.connection){
                if((null != sessionData.URL)&&(null != sessionData.addName)&&
                        (!sessionData.URL.equals((""))&&(!sessionData.addName.equals("")))){
                    try {
                        if(dataBase.addURL(sessionData,userData)){
                            sessionData.session.setAttribute("URLCon", "was added");
                            userData.page.createMenu(userData, dataBase);
                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                        sessionData.session.setAttribute("URLCon", "error connectionDB");
                    }
                }else {
                    sessionData.session.setAttribute("URLCon", "NullURL");
                }
                dispatcher = sessionData.request.getRequestDispatcher("feeds.jsp");
            }else{
                sessionData.session.setAttribute("URLCon", "You can't add news if you have no connection");
            }
        }else if("remove".equals(sessionData.feedsButton)){
                if(userData.connection){
                    try {
                        if((sessionData.removeName!=null)&&(!sessionData.removeName.equals(""))){
                            if(dataBase.delete(sessionData,userData,dataBase)){
                                sessionData.session.setAttribute("URLCon", "removed");
                                userData.page.createMenu(userData, dataBase);
                            }
                            sessionData.session.setAttribute("URLCon", "nothing to remove");// no elements
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        sessionData.session.setAttribute("URLCon", "error connection");
                    }
                }else{
                    sessionData.session.setAttribute("URLCon", "You can't remove news if you have no connection");
                }
        }else if("Sort by date: new is first".equals(sessionData.feedsButton)){
                if(userData.connection){
                    userData.sort =false;
                    try {
                        userData.page.createFirstNews(userData);
                        userData.page.createMenu(userData, dataBase);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                        sessionData.session.setAttribute("URLCon", "You can't sort news if you have no connection");
                }
        }else if("Sort by date: old is first".equals(sessionData.feedsButton)){
                if(userData.connection){
                    userData.sort = true;
                    try {
                        userData.page.createFirstNews(userData);
                        userData.page.createMenu(userData, dataBase);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    sessionData.session.setAttribute("URLCon", "You can't sort news if you have no connection");
                }
        }else if("create logs".equals(sessionData.feedsButton)){
                if(userData.connection){
                    userData.page.createLogs(userData);
                    userData.fileSave = true;
                }
                else{
                    sessionData.session.setAttribute("URLCon", "You can't create logs if you have no connection");
                }
        }
        dispatcher = sessionData.request.getRequestDispatcher("feeds.jsp");
    }
}
