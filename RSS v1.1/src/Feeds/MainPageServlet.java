package Feeds;

import Login.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Naya on 02.03.14.
 */
@WebServlet("/MainPageServlet")
public class MainPageServlet extends HttpServlet{
    static HttpSession s;
    public static String lastLogin;
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException {
        response.setContentType("text/html");
        s = request.getSession();
        String URL = request.getParameter("URL");
        String name = request.getParameter("name");
        s.setAttribute("URL", URL);
        s.setAttribute("name", name);
        if((URL != null)&&(!URL.equals(""))&&(name!= null)&&(!name.equals(""))){
            if(DataBase.addURL(URL,name)){
                s.setAttribute("URLCon","GOOD");
            }
            else {
                s.setAttribute("URLCon","BAD");
            }
        }
        else {
            s.setAttribute("URLCon","NullURL");
        }

        Menu.createMenu(Servlet.path,lastLogin);
        RequestDispatcher dispatcher = request.getRequestDispatcher("Feeds.jsp");
        if(dispatcher != null){
            dispatcher.forward(request,response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{

    }
public static void setLogin(String Login){
   lastLogin  = Login;
}
    public static String getLastLogin(){
        return lastLogin;
    }


}
