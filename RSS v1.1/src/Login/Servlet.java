package Login;

import Feeds.CreatePages;
import Feeds.Links;
import Feeds.MainPageServlet;
import Feeds.CreatePages;
import sun.applet.Main;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Naya on 02.03.14.
 */
@WebServlet("/Main")
public class Servlet extends HttpServlet {
    public static String path;
    DataType sessionData;
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{

    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
        response.setContentType("text/html");

        HttpSession s = request.getSession();
        sessionData = dataProcessing(s,request);
        path = getServletContext().getRealPath("");
        DataBase dBase = new DataBase();
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        if(sessionData.button != null){
            if("Log In".equals(sessionData.button)){
                if (null != sessionData.loginLog){
                      try {
                            if(login(sessionData.loginLog, sessionData.passwordLog,dBase)){
                                CreatePages.createMenu(path, sessionData.loginLog);
                                CreatePages.createNews();
                                MainPageServlet.setLogin(sessionData.loginLog);
                                dispatcher = request.getRequestDispatcher("Feeds.jsp");
                                }
                          else  s.setAttribute("sessionLabel","Wrong password or login");
                      } catch (SQLException e) {
                          e.printStackTrace();
                          s.setAttribute("sessionLabel", dBase.dataBaseConnect);
                      }

                }
                 else {
                    s.setAttribute("sessionLabel","Wrong password or login");
                 }
            }
            else
            if("Log".equals(sessionData.button)){
                    if(sessionData.secondPass.equals(sessionData.passwordReg)){
                        if(register(sessionData.loginReg, sessionData.passwordReg,dBase)){
                            CreatePages.createMenu(path, sessionData.loginReg);
                            CreatePages.createNews();
                            MainPageServlet.setLogin(sessionData.loginReg);
                            dispatcher = request.getRequestDispatcher("Feeds.jsp");
                          }
                        else
                        {  s.setAttribute("sessionLabel","we have this login. choose another,please");}
                    }
                    else{
                     s.setAttribute("sessionLabel","pass not equals");
                    }

            }

       }
        if(dispatcher != null){
            dispatcher.forward(request,response);
        }
    }

    private DataType dataProcessing(HttpSession s,HttpServletRequest request){
        DataType data = new DataType();
        s.setAttribute("buffer", request.getParameter("button"));
        data.button = (String)s.getAttribute("buffer");
        s.setAttribute("buffer",request.getParameter("Login"));
        data.loginLog = (String)s.getAttribute("buffer");
        s.setAttribute("buffer",request.getParameter("Password"));
        data.passwordLog = (String)s.getAttribute("buffer");
        s.setAttribute("buffer",request.getParameter("LoginReg"));
        data.loginReg = (String)s.getAttribute("buffer");
        s.setAttribute("buffer",request.getParameter("PasswordReg"));
        data.passwordReg = (String)s.getAttribute("buffer");
        s.setAttribute("buffer",request.getParameter("PasswordReg2"));
        data.secondPass = (String)s.getAttribute("buffer");
        return data;
       }

    public  Boolean login(String login, String pass, DataBase dBase) throws SQLException {
       if(dBase.LogIn(login,pass)){
            return true;
       }
        else {
           return false;
       }
    }
    public boolean register(String login, String pass, DataBase dBase){
        if(dBase.Register(login,pass)){
            return true;
        }
        else
            return false;
    }





}

