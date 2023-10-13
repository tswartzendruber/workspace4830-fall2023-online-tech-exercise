import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/MyServletDB")
public class MyServletDB extends HttpServlet {
   private static final long serialVersionUID = 1L;
   static String url = "jdbc:mysql://ec2-3-139-73-107.us-east-2.compute.amazonaws.com:3306/clashOfClansDatabase";
   static String user = "tswartzendruber_remote";
   static String password = "Neny6549!!!";
   static Connection connection = null;

   public MyServletDB() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("text/html;charset=UTF-8");
      response.getWriter().println("-------- MySQL JDBC Connection Testing ------------<br>");
      try {
         Class.forName("com.mysql.cj.jdbc.Driver");//("com.mysql.jdbc.Driver");
      } catch (ClassNotFoundException e) {
         System.out.println("Where is your MySQL JDBC Driver?");
         e.printStackTrace();
         return;
      }
      response.getWriter().println("MySQL JDBC Driver Registered!<br>");
      connection = null;
      try {
         connection = DriverManager.getConnection(url, user, password);
      } catch (SQLException e) {
         System.out.println("Connection Failed! Check output console");
         e.printStackTrace();
         return;
      }
      if (connection != null) {
         response.getWriter().println("You made it, take control your database now!<br>");
      } else {
         System.out.println("Failed to make connection!");
      }
      try {
         String selectSQL = "SELECT * FROM clashOfClansTable";// WHERE MYUSER LIKE ?";
//         String theUserName = "user%";
         response.getWriter().println(selectSQL + "<br>");
         response.getWriter().println("------------------------------------------<br>");
         PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
//         preparedStatement.setString(1, theUserName);
         ResultSet rs = preparedStatement.executeQuery();
         while (rs.next()) {
        	 String id = rs.getString("ID");
             String name = rs.getString("name");
             String damage_per_second = rs.getString("damage_per_second");
             String health = rs.getString("health");
             String training_time = rs.getString("training_time");
             String ground_targets = rs.getString("ground_targets");
             String air_targets = rs.getString("air_targets");
             String housing_space = rs.getString("housing_space");
             String speed = rs.getString("speed");
             response.getWriter().append("Troop ID: " + id + ", ");
             response.getWriter().append("Troop Name: " + name + ", ");
             response.getWriter().append("Damage Per Second: " + damage_per_second + ", ");
             response.getWriter().append("Health: " + health + ", ");
             response.getWriter().append("Training Time: " + training_time + ", ");
             response.getWriter().append("Ground Targets?: " + ground_targets + ", ");
             response.getWriter().append("Air Targets?: " + air_targets + ", ");
             response.getWriter().append("Housing Space: " + housing_space + ", ");
             response.getWriter().append("Speed: " + speed + "<br>");
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }
}