
/**
 * @file SimpleFormInsert.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SimpleFormInsert")
public class SimpleFormInsert extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SimpleFormInsert() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String name = request.getParameter("name");
      String damage_per_second = request.getParameter("damage_per_second");
      String health = request.getParameter("health");
      String training_time = request.getParameter("training_time");
      String ground_targets = request.getParameter("ground_targets");
      String air_targets = request.getParameter("air_targets");
      String housing_space = request.getParameter("housing_space");
      String speed = request.getParameter("speed");

      Connection connection = null;
      String insertSql = " INSERT INTO clashOfClansTable (id, name, damage_per_second, health, training_time, ground_targets, air_targets, housing_space, speed) values (default, ?, ?, ?, ?, ?, ?, ?, ?)";

      try {
         DBConnection.getDBConnection();
         connection = DBConnection.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, name);
         preparedStmt.setString(2, damage_per_second);
         preparedStmt.setString(3, health);
         preparedStmt.setString(4, training_time);
         preparedStmt.setString(5, ground_targets);
         preparedStmt.setString(6, air_targets);
         preparedStmt.setString(7, housing_space);
         preparedStmt.setString(8, speed);
         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Insert Data to DB table";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n" + //
            "<ul>\n" + //
            "  <li><b>Troop Name</b>: " + name + "\n" + //
            "  <li><b>Damage Per Second</b>: " + damage_per_second + "\n" + //
            "  <li><b>Health</b>: " + health + "\n" + //
            "  <li><b>Training Time</b>: " + training_time + "\n" + //
            "  <li><b>Ground Targets?</b>: " + ground_targets + "\n" + //
            "  <li><b>Air Targets?</b>: " + air_targets + "\n" + //
            "  <li><b>Housing Space</b>: " + housing_space + "\n" + //
            "  <li><b>Speed</b>: " + speed + "\n" + //
            "</ul>\n");

      out.println("<a href=/clash-of-clans-project/simpleFormSearch.html>Search Data</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
