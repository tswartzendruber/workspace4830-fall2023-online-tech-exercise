import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SimpleFormSearch")
public class SimpleFormSearch extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SimpleFormSearch() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String keyword = request.getParameter("keyword");
      search(keyword, response);
   }

   void search(String keyword, HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Database Result";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");

      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try {
         DBConnection.getDBConnection();
         connection = DBConnection.connection;

         if (keyword.isEmpty()) {
            String selectSQL = "SELECT * FROM clashOfClansTable";
            preparedStatement = connection.prepareStatement(selectSQL);
         } else {
            String selectSQL = "SELECT * FROM clashOfClansTable WHERE name LIKE ?";
            String theUserName = "%" + keyword + "%";
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, theUserName);
         }
         ResultSet rs = preparedStatement.executeQuery();

         while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name").trim();
            String damage_per_second = rs.getString("damage_per_second").trim();
            String health = rs.getString("health").trim();
            String training_time = rs.getString("training_time").trim();
            String ground_targets = rs.getString("ground_targets").trim();
            String air_targets = rs.getString("air_targets").trim();
            String housing_space = rs.getString("housing_space").trim();
            String speed = rs.getString("speed").trim();
            if (keyword.isEmpty() || name.contains(keyword)) {
               out.println("ID: " + id + ", ");
               out.println("Troop Name: " + name + ", ");
               out.println("Damage Per Second: " + damage_per_second + ", ");
               out.println("Health: " + health + ", ");
               out.println("Training Time: " + training_time + ", ");
               out.println("Ground Targets?: " + ground_targets + ", ");
               out.println("Air Targets?: " + air_targets + ", ");
               out.println("Housing Space: " + housing_space + ", ");
               out.println("Speed: " + speed + "<br>");
            }
         }
         out.println("<a href=/clash-of-clans-project/simpleFormSearch.html>Search Data</a> <br>");
         out.println("</body></html>");
         rs.close();
         preparedStatement.close();
         connection.close();
      } catch (SQLException se) {
         se.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if (preparedStatement != null)
               preparedStatement.close();
         } catch (SQLException se2) {
         }
         try {
            if (connection != null)
               connection.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
