package Feeds;

import Login.Servlet;
import org.xml.sax.SAXException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by Naya on 04.03.14.
 */
@WebServlet("/menuServlet")
public class MenuServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession s = request.getSession();
        response.setContentType("text/html");
        s.setAttribute("name", request.getParameter("button"));
        DataType.canal = (String) s.getAttribute("name");



//      CreatePages.createFeed(reader);
        RequestDispatcher dispatcher = request.getRequestDispatcher("Menu.jsp");
        if(dispatcher != null){
            dispatcher.forward(request,response);
        }
        }

}
