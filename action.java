package embedded_sql;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;

public class action implements ActionListener{
	private int minPrice;
	private int maxPrice;
	private static int numBedroom;
	private int startDate;
	private int endDate;
	JTextField jtf[] = new JTextField[5];
	JComboBox<String> jcb;
	Connection con;
	
	public action(JTextField jtf[], JComboBox<String> jcb, Connection con) {
		this.jtf = jtf;
		this.jcb = jcb;
		this.con = con;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("Search")) {
			minPrice = Integer.parseInt(jtf[0].getText());
			maxPrice = Integer.parseInt(jtf[1].getText());
			numBedroom = Integer.parseInt(jtf[2].getText());
			startDate = Integer.parseInt(jtf[3].getText());
			endDate = Integer.parseInt(jtf[4].getText());
			
			System.out.print("minPrice="+minPrice+"\nmaxPrice="+maxPrice+"\nstartDate="+startDate+"\nnumBedroom="+numBedroom);
			SearchListings(con);
		}
	}
	public static void SearchListings(Connection con) {
		String SQL = 
				"SELECT L.id, L.name, L.description, L.number_of_bedrooms, C.price\r\n" + 
				"FROM dbo.calendar C, dbo.Listings L\r\n" + 
				"WHERE C.listing_id = L.id AND L.number_of_bedrooms = ?\r\n" + 
				"	AND( SELECT COUNT(*)\r\n" + 
				"		 FROM calendar\r\n" + 
				"		 WHERE L.id = listing_id AND date>=? AND date<=? AND available = 1\r\n" + 
				"		 AND price>=? AND price<=?\r\n" + 
				"	)=? AND C.date>=? AND C.date<=? AND C.available = 1\r\n" + 
				"		 AND C.price>=? AND C.price<=?";
		try {
			
			PreparedStatement stmt = con.prepareStatement(SQL); 
			
			stmt.setInt(1,numBedroom);
			
			
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
	
	public int getMinPrice() {
		return minPrice;
	}

	public int getMaxPrice() {
		return maxPrice;
	}

	public int getNumBedroom() {
		return numBedroom;
	}

	public int getStartDate() {
		return startDate;
	}

	public int getEndDate() {
		return endDate;
	}
}
