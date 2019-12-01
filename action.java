package embedded;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.Date;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class action implements ActionListener, MouseListener{
	private static int minPrice;
	private static int maxPrice;
	private static int numBedroom;
	private static int listingid;
	private static Date startDate;
	private static Date endDate;
	
	private static String guestName;
	private static int numberGuest;
	
	private static String userName;
	private static Date currentDate;
	private static String reviewText;
	
	static GUI gui;
	Connection con;
	
	public action(GUI gui, Connection con) { 
		this.gui = gui;
		this.con = con;
	}
	
	// actions for different buttons
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//search for listings
		if(e.getActionCommand().equals("Search")) {
			gui.removeBooingInfo();
			//get the search information
			try {
				if(!(gui.getjtf()[0]).getText().equals("")) {
					minPrice = Integer.parseInt((gui.getjtf()[0]).getText());
					if(minPrice<0) {
						JOptionPane.showMessageDialog(null, "minPrice should greater than 0!",
							      "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				else minPrice = -1;
				if(!(gui.getjtf()[1]).getText().equals("")) {
					maxPrice = Integer.parseInt((gui.getjtf()[1]).getText());
					if(maxPrice<0) {
						JOptionPane.showMessageDialog(null, "maxPrice should greater than 0!",
							      "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				else maxPrice = -1;
				if(!(gui.getjtf()[2]).getText().equals("")) {
					numBedroom = Integer.parseInt((gui.getjtf()[2]).getText());
					if(numBedroom<0) {
						JOptionPane.showMessageDialog(null, "numBedroom should greater than 0!",
							      "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				 
				else numBedroom = -1;
			}catch(Exception ee) {
				JOptionPane.showMessageDialog(null, "please enter a valid number!",
					      "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(!(gui.getjtf()[3]).getText().equals("")) {
				try {
					startDate = java.sql.Date.valueOf((gui.getjtf()[3]).getText());
				}catch (IllegalArgumentException ie) {
					JOptionPane.showMessageDialog(null, "date format should be yyyy-MM-dd",
						      "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}	
			else {
				startDate = java.sql.Date.valueOf("1970-01-01");
				JOptionPane.showMessageDialog(null, "Please enter start date",
					      "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(!(gui.getjtf()[4]).getText().equals("")) {
				try {
					endDate = java.sql.Date.valueOf((gui.getjtf()[4]).getText());
				}catch (IllegalArgumentException ie) {
					JOptionPane.showMessageDialog(null, "date format should be yyyy-MM-dd",
						      "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
	
			else {
				endDate = java.sql.Date.valueOf("1970-01-01");
				JOptionPane.showMessageDialog(null, "Please enter end date",
					      "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			System.out.print("minPrice="+minPrice+"\nmaxPrice="+maxPrice+"\nstartDate="+startDate+"\nnumBedroom="+numBedroom+"\n");
			// judge the date validation
			Date date1 = java.sql.Date.valueOf("1970-01-01");
			long diff = endDate.getTime() - date1.getTime();
			long diff2 = startDate.getTime() - date1.getTime();
			if(endDate.getTime() - startDate.getTime()<0) {
				JOptionPane.showMessageDialog(null, "Please enter a valid end date and start date!",
					      "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			else if(diff !=0 && diff2!=0)	{
				//execute the query
				if(SearchListings(con)!=1) {
					JOptionPane.showMessageDialog(null, "Search erro!",
						      "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			System.out.print("finished!");
		}
		else if(e.getActionCommand().equals("SearchBooking")) {
			//get the booking informations
			if(!(gui.getReviewJtf()[0]).getText().equals("")) {
				userName = gui.getReviewJtf()[0].getText();
				System.out.println(userName);
			}
				
			else {
				 JOptionPane.showMessageDialog(null, "Please enter your name!",
					      "Error", JOptionPane.ERROR_MESSAGE);
				 userName = "";
				 return;
			}
			if(!(userName.equals(""))) {
				//execute query
				System.out.println(userName);
				if(SearchBooking(con)!=1) {
					JOptionPane.showMessageDialog(null, "Search error!",
						      "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			gui.removeWriteReview();
		}
		else if(e.getActionCommand().equals("Submit")) {
			//get the review information
			if(!(gui.getReviewText().getText()).equals("")) {
				reviewText = gui.getReviewText().getText();
			}
			else {
				 JOptionPane.showMessageDialog(null, "Please write a review!",
					      "Error", JOptionPane.ERROR_MESSAGE);
				 reviewText = "";
				 return;
			}

			listingid = Integer.parseInt(gui.getReviewJtf()[2].getText());

			if(!(reviewText.contentEquals(""))) {
				// insert the review
				if(WriteReview(con)!=1) {
					// JOptionPane.showMessageDialog(null, "Write a review error may due to trigger!",
					//	      "Error", JOptionPane.ERROR_MESSAGE);
					 return;
				}
				else {
					JOptionPane.showMessageDialog(new JFrame(), "successfully reviewed!");
				}
			}
		}
		else if(e.getActionCommand().equals("Book")) {
				//get the book information
    			if(!(gui.bookJtf[1].getText().equals("")))
    				guestName = gui.bookJtf[1].getText();
    			else {
    				 JOptionPane.showMessageDialog(null, "Please enter your name!",
    					      "Error", JOptionPane.ERROR_MESSAGE);
    				 guestName = "";
    				 return;
    			}
    			if(gui.bookJtf[2].getText().equals("")) {
    				JOptionPane.showMessageDialog(null, "Please enter guests number",
  					      "Error", JOptionPane.ERROR_MESSAGE);
    				numberGuest = -1;
    				return;
    			}
    			else {
    				try{
    					numberGuest = Integer.parseInt(gui.bookJtf[2].getText());
    					if(numberGuest<0) {
    						JOptionPane.showMessageDialog(null, "Please enter an valid number",
    	    					      "Error", JOptionPane.ERROR_MESSAGE);
    	    				 numberGuest = -1;
    	    				 return;
    					}
    				}catch(Exception ee) {
    					JOptionPane.showMessageDialog(null, "Please enter a number",
      					      "Error", JOptionPane.ERROR_MESSAGE);
    					return;
    				}
    				
    			}
    			if(numberGuest > 0 && !guestName.contentEquals("")) {
            		System.out.println("BOOK!");
            		//insert the booking
            		if(BookListings(con,listingid)==1)
            			JOptionPane.showMessageDialog(new JFrame(), "Booking successes!");
            		else JOptionPane.showMessageDialog(new JFrame(), "Booking fails!");
            	}
    			gui.removeBooingInfo();
    		}
	}
	public static int SearchListings(Connection con) {
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
			int count = 0;
			PreparedStatement stmt = con.prepareStatement(SQL); 
			
			stmt.setDate(1, startDate);
			stmt.setDate(2, endDate);
			stmt.setInt(3, (int)TimeUnit.DAYS.convert(endDate.getTime()-startDate.getTime(), TimeUnit.MILLISECONDS)+1);
			
			
	        ResultSet rs = stmt.executeQuery();
	
	        // Iterate through the data in the result set and display it. 
	        gui.getListModel().clear();
        	gui.getListModel().addElement("  id                         name                                      descriptor"
        			+ "      bedroomNum        total_price ");
	        while (rs.next()) 
	        { 
	        	count++;
	           System.out.println(rs.getInt(1) + " " + rs.getString(2)); 
	           String temp = rs.getInt(1) +"     " + rs.getString(2)+"     "+ rs.getString(3).substring(0,20)+ "     "
	        		   		+rs.getInt(4)+"     "+rs.getInt(5) + "\r\n";
	           gui.getListModel().addElement(temp);
	           
	        }
	        //if there is no result, display no searching results
	        if(count==0)  {
	        	gui.getListModel().clear();
	        	gui.getListModel().addElement("Sorry, no searching results!\n");
	        }
		}
        catch(Exception e) {
        	e.printStackTrace();
        	return 0;
        }
		return 1;
	}
	public static int BookListings(Connection con,int listing_id) {
		// to determine the id
		String SQL = "select COUNT(*)\r\n" + 
					 "from Bookings";
		int id = 0;
		try {
			PreparedStatement stmt = con.prepareStatement(SQL); 
			 ResultSet rs = stmt.executeQuery();
			 if(rs.next()) id = rs.getInt(1)+1;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		//insert the valid booking
		SQL = "INSERT INTO dbo.Bookings(id,listing_id,guest_name,stay_from,stay_to,number_of_guests)\r\n" + 
				"VALUES (?,?,?,?,?,?);";
		try {
			PreparedStatement stmt = con.prepareStatement(SQL); 
			
			stmt.setInt(1, id);
			stmt.setInt(2, listing_id);
			stmt.setString(3, guestName);
			stmt.setDate(4, startDate);
			stmt.setDate(5, endDate);
			stmt.setInt(6, numberGuest);
			
	        stmt.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	public static int SearchBooking(Connection con) {
		String SQL = "select *\r\n" + 
				"from Bookings\r\n" + 
				"where guest_name = ?";
		try {
			int count = 0;
			PreparedStatement stmt = con.prepareStatement(SQL); 
			
			stmt.setString(1, userName);
			
			ResultSet rs = stmt.executeQuery();
			
		    gui.getReviewListModel().clear();
	        gui.getReviewListModel().addElement("  listing_id      stay_from     stay_to      number of guest");
	        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	     // Iterate through the data in the result set and display it.
		    while (rs.next()) 
		       { 
		        	count++;
		           System.out.println(rs.getInt(1) + " " + rs.getString(2)); 
		           String temp = rs.getInt(2) +"     "+ df.format(rs.getDate(4))+ "     "
		        		   		+df.format(rs.getDate(5))+"     "+rs.getInt(6) + "\r\n";
		           gui.getReviewListModel().addElement(temp);
		           
		        }
		    // if there is no result, display no searching result
		    if(count==0)  {
		        gui.getReviewListModel().clear();
		        gui.getReviewListModel().addElement("Sorry, no searching results!\n");
		        System.out.println("fail");
		     }
		}
		catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	public static int WriteReview(Connection con) {
		String SQL = "select MAX(id)\r\n" + 
				 "from Reviews";
		int id = 0;
		try {
			PreparedStatement stmt = con.prepareStatement(SQL); 
			 ResultSet rs = stmt.executeQuery();
			 if(rs.next()) id = rs.getInt(1)+1;
		}
		catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		//insert valid review
		System.out.println("Write a review!");
		SQL = "INSERT INTO dbo.Reviews(listing_id,id,comments,guest_name)\r\n" + 
					"VALUES (?,?,?,?);"	;
		try {
			PreparedStatement stmt = con.prepareStatement(SQL); 
			
			stmt.setInt(1, listingid);
			stmt.setInt(2, id);
			stmt.setString(3, reviewText);
			stmt.setString(4, userName);
			
			
	        stmt.executeUpdate();
		}
		catch (SQLException e) {
				String temp =  "Review insert failed!"
						+ "\nSQL State:" + e.getSQLState()  
						+ "\tERROR Code:" + e.getErrorCode()
						+ "\nError Message:" +e.getMessage();
				JOptionPane.showMessageDialog(null, temp);				
				e.getNextException();
			return 0;
			
		}
		return 1;
	}

	// actions for double-clicked on listings
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
            System.out.println("book a room!");
            gui.AddBookingInfo(words[0]);
            listingid = Integer.parseInt(words[0]);
     
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
	
	public void setEndDate(Date d) {
		endDate = d;
	}
}


