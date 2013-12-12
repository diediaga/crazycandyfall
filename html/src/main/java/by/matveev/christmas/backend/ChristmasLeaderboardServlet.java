package by.matveev.christmas.backend;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChristmasLeaderboardServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String cmd = req.getParameter("cmd");


        resp.setContentType("text/plain");
        resp.getWriter().println("Hello, world");
    }
}
