package embedded_sql;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class a  {
	public static void main(String[] args)  {
		Connection con = null;
		con = ConnectToDB();
		GUI gui = new GUI(con);
		//SearchListings(con);

	}
	
	public static Connection ConnectToDB() {
		Connection con = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String connectionUrl = "jdbc:sqlserver://localhost:1433;" +  
					  // "database=CMPT354;"
					"user=light;password=Ccjjll55.;";  
			con = DriverManager.getConnection(connectionUrl);
			}
			catch (Exception e) {
		        e.printStackTrace();
		    }
		return con;
	}
	public static void SearchListings(Connection con) {
		int num = 3;
		String SQL = 
				"SELECT L.id, L.name, L.description, L.number_of_bedrooms, C.price\r\n" + 
				"FROM dbo.calendar C, dbo.Listings L\r\n" + 
				"WHERE C.listing_id = L.id AND L.number_of_bedrooms = "+ num +"\r\n" + 
				"	AND( SELECT COUNT(*)\r\n" + 
				"		 FROM calendar\r\n" + 
				"		 WHERE L.id = listing_id AND date>='2016-01-07' AND date<='2016-01-10' AND available = 1\r\n" + 
				"		 AND price>=30 AND price<=100\r\n" + 
				"	)=4 AND C.date>='2016-01-07' AND C.date<='2016-01-10' AND C.available = 1\r\n" + 
				"		 AND C.price>=30 AND C.price<=100";
		try {
			PreparedStatement stmt = con.prepareStatement(SQL); 
			
	        ResultSet rs = stmt.executeQuery();
	
	        // Iterate through the data in the result set and display it. 
	        while (rs.next()) 
	        { 
	           System.out.println(rs.getInt(1) + " " + rs.getString(2)); 
	        }
		}
        catch(Exception e) {
        	e.printStackTrace();
        }
	}
	
}
