package embedded;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.Date;
import java.util.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class action implements ActionListener, MouseListener{
	private static int minPrice;
	private static int maxPrice;
	private static int numBedroom;
	private static int listingid;
	private static Date startDate;
	private static Date endDate;
	static GUI gui;
	Connection con;
	
	public action(GUI gui, Connection con) {
		this.gui = gui;
		this.con = con;
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("Search")) {
			if(!(gui.getjtf()[0]).getText().equals(""))
				minPrice = Integer.parseInt((gui.getjtf()[0]).getText());
			else minPrice = -1;
			if(!(gui.getjtf()[1]).getText().equals(""))
				maxPrice = Integer.parseInt((gui.getjtf()[1]).getText());
			else maxPrice = -1;
			if(!(gui.getjtf()[2]).getText().equals(""))
			 numBedroom = Integer.parseInt((gui.getjtf()[2]).getText());
			else numBedroom = -1;
			if(!(gui.getjtf()[3]).getText().equals(""))
				startDate = java.sql.Date.valueOf((gui.getjtf()[3]).getText());
			else {
				startDate = java.sql.Date.valueOf("0000-00-00");
			}
			if(!(gui.getjtf()[4]).getText().equals(""))
				endDate = java.sql.Date.valueOf((gui.getjtf()[4]).getText());
			else endDate = java.sql.Date.valueOf("0000-00-00");
			
			
			System.out.print("minPrice="+minPrice+"\nmaxPrice="+maxPrice+"\nstartDate="+startDate+"\nnumBedroom="+numBedroom+"\n");
			SearchListings(con);
			System.out.print("finished!");
			
		}
	}
	public static void SearchListings(Connection con) {
		String SQL = 
				"SELECT L.id, L.name, L.description, L.number_of_bedrooms, sum(C.price)\r\n" + 
				"FROM dbo.calendar C, dbo.Listings L\r\n" + 
				"WHERE C.listing_id = L.id\r\n"+
				"AND available = 1\r\n" +
				"AND date>=? \r\n" + 
				"AND date<=? \r\n";
				if(numBedroom!=-1)
					SQL = SQL + "AND L.number_of_bedrooms = "+ numBedroom + "\r\n";
				if(minPrice!=-1)
					SQL = SQL + " AND price>=" + minPrice+ "\r\n";
				if(maxPrice!=-1)
					SQL = SQL + " AND price<=" + maxPrice+ "\r\n";  
				SQL = SQL +"group by L.id, L.name, L.description, L.number_of_bedrooms\r\n" + 
							"having count(*)>=?\r\n" + 
							"order by L.id";
		try {
			
			PreparedStatement stmt = con.prepareStatement(SQL); 
			
			stmt.setDate(1, startDate);
			stmt.setDate(2, endDate);
			stmt.setInt(3, endDate.getDate()-startDate.getDate()+1);
			
			
	        ResultSet rs = stmt.executeQuery();
	
	        // Iterate through the data in the result set and display it. 
	        if(rs.next()==false)  {
	        	gui.getListModel().clear();
	        	gui.getListModel().addElement("Sorry, no searching results!\n");
	        }
	        gui.getListModel().clear();
        	gui.getListModel().addElement("  id   name   descriptor   number of bedroom   total_price ");
	        while (rs.next()) 
	        { 
	        	
	           System.out.println(rs.getInt(1) + " " + rs.getString(2)); 
	           String temp = rs.getInt(1) +"     " + rs.getString(2)+"     "+ rs.getString(3).substring(0,20)+ "     "
	        		   		+rs.getInt(4)+"     "+rs.getInt(5) + "\r\n";
	           gui.getListModel().addElement(temp);
	           
	        }
		}
        catch(Exception e) {
        	e.printStackTrace();
        }
	}
	public static int BookListings(Connection con) {
		
		return 1;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		JList<String> theList = (JList<String>) e.getSource();
        if (e.getClickCount() == 2) {
          int index = theList.locationToIndex(e.getPoint());
          if (index >= 1) {
            String temp = theList.getModel().getElementAt(index);
            String [] words = temp.split("     ");
            System.out.println("Double-clicked on: " + words[0] + " "+words[1]);
            JFrame frame = null;
            int dialogResult = JOptionPane.showConfirmDialog(frame,
            "Would you like to book it","Book a room", JOptionPane.YES_NO_CANCEL_OPTION);
            if(dialogResult == JOptionPane.YES_OPTION) {
            	System.out.println("book a room!");
            	
            }
          }
        }
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}


