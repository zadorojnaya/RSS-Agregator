package Feeds;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        if(dispatcher != null){
            dispatcher.forward(request,response);
        }

    }
}
