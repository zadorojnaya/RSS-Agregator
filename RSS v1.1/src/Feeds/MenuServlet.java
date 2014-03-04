package Feeds;

import Login.Servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by Naya on 04.03.14.
 */
@WebServlet("/menuServlet")
public class MenuServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException{
        HttpSession s = request.getSession();
        response.setContentType("text/html");
        s.setAttribute("name",request.getParameter("button"));
        Feed reader = Feed.getInstance();
          Menu.createFeed(s.getAttribute("name").toString(),reader);

        }

}
