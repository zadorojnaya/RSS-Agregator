package Feeds;

import Login.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Naya on 02.03.14.
 */
@WebServlet("/MainPageServlet")
public class MainPageServlet extends HttpServlet{
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException {

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
        response.setContentType("text/html");
        HttpSession s = request.getSession();
        Feed reader = Feed.getInstance();
        reader.writeNews();
        s.setAttribute("URL", request.getParameter("URL"));
        if(s.getAttribute("URL") != null){
             if(DataBase.addURL(s.getAttribute("URL").toString())){
                 s.setAttribute("URLCon","GOOD");
             }
             else {
                s.setAttribute("URLCon","BAD");
                }
        }
        else {
            s.setAttribute("URLCon","NullURL");
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("Feeds.jsp");
        if(dispatcher != null){
            dispatcher.forward(request,response);
        }
    }


}
