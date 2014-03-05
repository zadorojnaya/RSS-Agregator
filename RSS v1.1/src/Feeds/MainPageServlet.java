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
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Naya on 02.03.14.
 */
@WebServlet("/MainPageServlet")
public class MainPageServlet extends HttpServlet{
    static HttpSession s;
    private static HttpServletRequest gRequest;
    private static HttpServletResponse gResponse;
    public static String lastLogin;
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException {
        response.setContentType("text/html");
        gRequest = request;
        gResponse = response;
        s = request.getSession();
        String URL = request.getParameter("URL");
        String name = request.getParameter("name");
        String prevName = request.getParameter("prevName");
        s.setAttribute("button",request.getParameter("button"));
        s.setAttribute("URL", URL);
        s.setAttribute("name", name);
        s.setAttribute("prevName",prevName);
        s.setAttribute("nameDel",request.getParameter("delName"));
        s.setAttribute("newName",request.getParameter("newName"));
        if(s.getAttribute("button").equals("add")){
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
            CreatePages.createMenu(Servlet.path, lastLogin);
            refresh();
        }
        else{

                try {
                    if((s.getAttribute("nameDel")!=null)&&(!s.getAttribute("nameDel").equals(""))){
                        DataBase.delete(s.getAttribute("nameDel").toString());
                        s.setAttribute("URLCon","Good");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    s.setAttribute("URLCon","BAD");
                }
            CreatePages.createMenu(Servlet.path, lastLogin);
            refresh();

        }
    }

    public static void refresh() throws ServletException, IOException {
        RequestDispatcher dispatcher = gRequest.getRequestDispatcher("Feeds.jsp");
        if(dispatcher != null){
            dispatcher.forward(gRequest,gResponse);
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
