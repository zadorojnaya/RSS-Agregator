package Login;

import Data.UserData;
import Pages.CreatePages;
import Pages.XMLReader;
import org.xml.sax.SAXException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Naya on 06.03.14.
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException {

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
        response.setContentType("text/html");
        HttpSession s = request.getSession();
        DataBase dBase;
        UserData uData;
        Cookie sessionCookie;
        CreatePages page = new CreatePages();
        if((sessionCookie = newCookie(request)) != null){
            if((s.getAttribute("dBase") != null)&&(s.getAttribute("uData") != null)){
                dBase = (DataBase) s.getAttribute("dBase");
                uData = (UserData) s.getAttribute("uData");
            }
            else {
                dBase = new DataBase();
                uData = new UserData();
            }
            Cookie cookie = new Cookie ("JSESSIONID",sessionCookie.getValue());
            response.addCookie(cookie);
        }
        else{
            dBase = new DataBase();
            uData = new UserData();
        }
        DataProcessing sessionData = dataProcessing(s,request);
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        XMLReader.getConnection(uData);
        if(sessionData.button != null){
            if("Sign in".equals(sessionData.button)){
                if (null != sessionData.loginLog && null != sessionData.passwordLog){
                    try {
                       if(login(sessionData.loginLog,sessionData.passwordLog,dBase,uData)){
                           if((uData.connection == false)){
                           File file = new File(uData.path.toString()+"\\"+"\\Menu.jsp");
                           if((!file.exists())){
                               file.mkdirs();
                               s.setAttribute("Login",null);
                                s.setAttribute("News",null);
                                s.setAttribute("URLCon","If you here at first - add a new Feed!!!") ;
                              }
                               s.setAttribute("Login",sessionData.loginLog+"\\Menu.jsp");
                               s.setAttribute("News",sessionData.loginLog+"\\allNews.html");
                           }
                           else{
                               page.createMenu(uData, dBase);
                                s.setAttribute("Login",sessionData.loginLog+"\\Menu.jsp");
                                s.setAttribute("News",sessionData.loginLog+"\\allNews.html");
                           }
                            dispatcher = request.getRequestDispatcher("feeds.jsp");}
                        else{
                           s.setAttribute("sessionLabel","Wrong password or login");
                           dispatcher = request.getRequestDispatcher("index.jsp");
                       }
                    } catch (Exception e) {
                        e.printStackTrace();
                        s.setAttribute("sessionLabel","Error connection to DataBase");
                        dispatcher = request.getRequestDispatcher("index.jsp");
                    }
                }else{
                    s.setAttribute("sessionLabel","Wrong password or login");
                    dispatcher = request.getRequestDispatcher("index.jsp");
                }
            }
            else{
                if("Create new account".equals(sessionData.button)){
                    if(sessionData.secondPass.equals(sessionData.passwordReg)){
                        try {
                            if(register(sessionData.loginReg, sessionData.passwordReg,dBase,uData)){
                                page.createMenu(uData,dBase);
                                if(uData.connection){
                                    s.setAttribute("Login",sessionData.loginReg+"\\Menu.jsp");
                                    s.setAttribute("News",sessionData.loginReg+"\\allNews.html");
                                }
                                else{
                                    s.setAttribute("Login",null);
                                    s.setAttribute("News",null);
                                }
                                s.setAttribute("URLCon","Add a new Feed!!!");
                                dispatcher = request.getRequestDispatcher("feeds.jsp");
                            }
                            else {
                                s.setAttribute("sessionLabel","chose another login");
                                dispatcher = request.getRequestDispatcher("index.jsp");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            s.setAttribute("sessionLabel","Error connection to DataBase");
                            dispatcher = request.getRequestDispatcher("index.jsp");
                        }
                    }
                    else{
                        s.setAttribute("sessionLabel","pass not equals");
                        dispatcher = request.getRequestDispatcher("index.jsp");
                    }
                }
            }
        }
        // feeds page processing;
        if(sessionData.feedsButton != null){
            dispatcher = request.getRequestDispatcher("feeds.jsp");
            if("add".equals(sessionData.feedsButton)){
                if(uData.connection){
                    if((null != sessionData.URL)&&(null != sessionData.addName)&&
                       (!sessionData.URL.equals((""))&&(!sessionData.addName.equals("")))){
                        try {
                            if(dBase.addURL(sessionData,uData,dBase)){
                                s.setAttribute("URLCon","was added");
                                page.createMenu(uData,dBase);
                            }
                        }catch (Exception e) {
                            e.printStackTrace();
                            s.setAttribute("URLCon", "error connectionDB");
                        }
                    }
                    else {
                        s.setAttribute("URLCon","NullURL");
                    }
                    dispatcher = request.getRequestDispatcher("feeds.jsp");
                }
                else{
                    s.setAttribute("URLCon","You can't add news if you have no connection");
                }
            }
            else{
                if("remove".equals(sessionData.feedsButton)){
                    if(uData.connection){
                        try {
                            if((sessionData.removeName!=null)&&(!sessionData.removeName.equals(""))){
                                if(dBase.delete(sessionData,uData,dBase)){
                                    s.setAttribute("URLCon","removed");
                                    page.createMenu(uData,dBase);
                                }
                                s.setAttribute("URLCon","nothing to remove");// no elements
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            s.setAttribute("URLCon","error connection");
                        }
                    }
                    else{
                        s.setAttribute("URLCon","You can't remove news if you have no connection");
                    }
                }
                else{
                    if("Sort by date: new is first".equals(sessionData.feedsButton)){
                        if(uData.connection){
                            uData.sort =false;
                            try {
                                page.createFirstNews(uData);
                                page.createMenu(uData,dBase);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            s.setAttribute("URLCon","You can't sort news if you have no connection");
                        }
                    }
                    else{
                        if("Sort by date: old is first".equals(sessionData.feedsButton)){
                            if(uData.connection){
                                uData.sort = true;
                                try {
                                    page.createFirstNews(uData);
                                    page.createMenu(uData,dBase);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            else{
                                s.setAttribute("URLCon","You can't sort news if you have no connection");
                            }
                        }
                        else{
                            if("create logs".equals(sessionData.feedsButton)){
                                if(uData.connection){
                                    page.createLogs(uData);
                                    uData.fileSave = true;
                                }
                                else{
                                    s.setAttribute("URLCon","You can't create logs if you have no connection");
                                }
                            }
                        }
                    }
                }
            }
            dispatcher = request.getRequestDispatcher("feeds.jsp");
        }
        if(dispatcher != null){
            dispatcher.forward(request,response);
        }
        s.setAttribute("dBase",dBase);
        s.setAttribute("uData",uData);
    }

    private DataProcessing dataProcessing(HttpSession s,HttpServletRequest request){
        DataProcessing data = new DataProcessing();
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
        //from feeds.jsp
        s.setAttribute("buffer", request.getParameter("feedButton"));
        data.feedsButton = (String)s.getAttribute("buffer");
        s.setAttribute("buffer", request.getParameter("addName"));
        data.addName = (String)s.getAttribute("buffer");
        s.setAttribute("buffer", request.getParameter("removeName"));
        data.removeName = (String)s.getAttribute("buffer");
        s.setAttribute("buffer", request.getParameter("URL"));
        data.URL = (String)s.getAttribute("buffer");
        return data;
    }
    public  Boolean login(String login, String pass, DataBase dBase, UserData uData) throws SQLException {
        if(dBase.LogIn(login,pass)){
            uData.login = login;
            uData.path = getServletContext().getRealPath("")+"\\"+login;
            return true;
        }
        else {
            return false;
        }
    }

        public boolean register(String login, String pass, DataBase dBase, UserData uData){
            if(dBase.Register(login,pass)){
                uData.login = login;
                uData.path = getServletContext().getRealPath("")+"\\"+login;
                return true;
            }
            else
                return false;
        }

    public Cookie newCookie(HttpServletRequest request){
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
}
