package Feeds;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Naya on 05.03.14.
 */
@WebServlet("/newsServlet")
public class NewsServlet extends HttpServlet{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession s = request.getSession();
        Feed reader = Feed.getInstance();
        if(DataType.canal !=null){
        CreatePages.createFeed(reader);}
        RequestDispatcher dispatcher = request.getRequestDispatcher("News.jsp");
        if(dispatcher != null){
            dispatcher.forward(request,response);
        }
    }

}
