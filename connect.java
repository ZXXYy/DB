package embedded;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

// main class for the project
public class connect  {
	public static void main(String[] args)  {
		Connection con = null;
		con = ConnectToDB();
		// generate the GUI 
		GUI gui = new GUI(con);	
	}
	
	//connect to the asked database
	public static Connection ConnectToDB() {
		Connection con = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String connectionUrl = "jdbc:sqlserver://cypress:1433;" 
					 +  "database=xiaoyez354;"
					+ "user=s_xiaoyez;password=yffA6Nh3A3NQ34AN;";  
			con = DriverManager.getConnection(connectionUrl);
			}
			catch (Exception e) {
		        e.printStackTrace();
		    }
		return con;
	}
	
}
