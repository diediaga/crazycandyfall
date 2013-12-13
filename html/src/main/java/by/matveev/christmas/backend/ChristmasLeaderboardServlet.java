package by.matveev.christmas.backend;


import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.google.appengine.api.datastore.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class ChristmasLeaderboardServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        final String cmd = req.getParameter("c");
        if ("submit".equals(cmd)) {
            final String token = req.getParameter("token");
            final String name = req.getParameter("name");
            final String score = req.getParameter("score");
            final String platform = req.getParameter("platform");
            final Date date = new Date();

            final Key leaderboardKey = KeyFactory.createKey("Leaderboard", "Leaderboard");

            final Entity record = new Entity("Score", leaderboardKey);
            record.setProperty("token", token);
            record.setProperty("date", date);
            record.setProperty("score", score);
            record.setProperty("name", name);
            record.setProperty("platform", platform);

            final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
            datastore.put(record);

            final JsonValue value = new JsonValue(JsonValue.ValueType.object);
            value.setName("response");
            value.set("ok");
            resp.getWriter().println( value.toString());
        }
    }
}
