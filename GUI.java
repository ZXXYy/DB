package embedded;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.Date;
import java.util.Vector;

import javax.swing.*;

public class GUI extends JFrame{
	JTabbedPane options;
	
	//for search listings
	JPanel jp[] = new JPanel[6];
	JLabel jlb[] = new JLabel[6];
	JButton jb;
	JTextField jtf[] = new JTextField[5];
	JComboBox<String> jcb;
	JSplitPane jsplit;
	JList<String> jlist;
	JPanel search = new JPanel();
	DefaultListModel<String> listModel;
	JScrollPane scrollPane = new JScrollPane();
	
	//for message box
	JTextField nameField = new JTextField(20);
    JTextField numberField = new JTextField(5);
    
    //for write a review
    JPanel reviewJP[] = new JPanel[3];
    JPanel add = new JPanel();
    JLabel reviewJlb[] = new JLabel[4];
    JButton reviewJb;
    JButton searchBooking;
    JTextField reviewJtf[] = new JTextField[3];
    JSplitPane reviewJsplit;
	JList<String> reviewJlist;
	JPanel review = new JPanel();
	DefaultListModel<String> reviewListModel;
	JScrollPane reviewScrollPane = new JScrollPane();
	JScrollPane textScrollPane;
	JTextArea jta;
	
	Connection con;
	//public static void main(String[] args) {
	//	GUI gui = new GUI();
	//}
	
	public GUI(Connection con) {
		this.con = con;
		
		// for search listings
		for(int i = 0;i<6;i++) {
			jp[i] = new JPanel();
		}
		
		jlb[0] = new JLabel("Minimum price");
		jlb[1] = new JLabel("Maximum price");
		jlb[2] = new JLabel("number of bedrooms");
		jlb[3] = new JLabel("Start date");
		jlb[4] = new JLabel("End date");
		jlb[5] = new JLabel("Listing_id");
		
		jb = new JButton("Search");
		
		for(int i = 0; i<5; i++) {
			jtf[i] = new JTextField(10);
		}
		
		String [] bedroomNum = {"1","2","3","4","5","6","7","8","9"};
		JComboBox<String> jcb = new JComboBox<String>(bedroomNum);
		
		//search.setLayout(new GridLayout(6,1));
		for(int i = 0;i<5;i++) {
			jp[i].add(jlb[i]);
			//if(i<2) jp[i].add(jtf[i]);
			jp[i].add(jtf[i]);
		}
		//jp[2].add(jcb);
		//jp[3].add(jtf[2]);
		//jp[4].add(jtf[3]);
		jp[5].add(jb);
		
	
		for(int i = 0;i<6;i++) {
			search.add(jp[i]);
		}
		
		listModel = new DefaultListModel<String>();
		listModel.addElement("  id   name   descriptor   number of bedroom   total_price ");
		jlist = new JList<String>(listModel);
		scrollPane.setViewportView(jlist);
		jlist.setLayoutOrientation(JList.VERTICAL);
		
		jsplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,search,scrollPane);
		jsplit.setOneTouchExpandable(true);
		jsplit.setDividerLocation(300);
		jsplit.setContinuousLayout(true);
		jsplit.setSize(700,1000);
		
		// for write reviews
		reviewJlb[0] = new JLabel("user name");
		reviewJlb[1] = new JLabel("current date");
		reviewJlb[3] = new JLabel("review text");
		reviewJlb[2] = new JLabel("Write a review for");
		
		reviewJb = new JButton("submit");
		searchBooking = new JButton("Search Booking");
		reviewJtf[0] = new JTextField(20);
		reviewJtf[1] = new JTextField(15);
		reviewJtf[2] = new JTextField(20);
	
		
		for(int i = 0;i<3;i++) {
			reviewJP[i] = new JPanel();
			reviewJP[i].add(reviewJlb[i]);
			reviewJP[i].add(reviewJtf[i]);
		}
		reviewJP[0].add(searchBooking);
		review.setLayout(new BorderLayout());
		add.setLayout(new GridLayout(4,1));
		add.add(reviewJP[0]);
		review.add(add,BorderLayout.NORTH);
		//review.add(searchBooking);
		
		reviewListModel = new DefaultListModel<String>();
		reviewListModel.addElement("  listing_id      stay_from     stay_to      number of guest");
		reviewJlist = new JList<String>(reviewListModel);
		reviewScrollPane.setViewportView(reviewJlist);
		reviewJlist.setLayoutOrientation(JList.VERTICAL);
		
		
		reviewJsplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,review,reviewScrollPane);
		reviewJsplit.setOneTouchExpandable(true);
		reviewJsplit.setDividerLocation(500);
		reviewJsplit.setContinuousLayout(true);
		reviewJsplit.setSize(700,1000);
		
		// integrate the search listings and write reviews
		options = new JTabbedPane();
		options.add("Search listings",jsplit);
		options.add("Write reviews",reviewJsplit);
		
		this.setContentPane(options);
		//this.setContentPane(jsplit);
		
		this.setSize(1000,500);
		this.setTitle("Search Listings");
		this.setLocation(100,200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setVisible(true);
		
		//jb.addActionListener(this);
		action A = new action(this,con);
		jb.addActionListener(A);
		jb.setActionCommand("Search");
		jlist.addMouseListener((MouseListener)A);
		
		// write review listener
		searchBooking.addActionListener(A);
		searchBooking.setActionCommand("SearchBooking");
		reviewJlist.addMouseListener(new reviewAction(this,con));
		reviewJb.addActionListener(A);
		reviewJb.setActionCommand("Submit");
	}

	public JTextField[] getjtf() {
		return jtf;
	}
	public DefaultListModel<String> getListModel(){
		return listModel;
	}
	public DefaultListModel<String> getReviewListModel(){
		return reviewListModel;
	}
	public JList<String> getJList(){
		return jlist;
	}
	public JTextField[] getReviewJtf() {
		return reviewJtf;
	}
	public JTextArea getReviewText() { 
		return jta;
	}
	public boolean JOptionPaneMultiInput() {

	      JPanel myPanel = new JPanel();
	      myPanel.add(new JLabel("Please enter your name:"));
	      myPanel.add(nameField);
	      myPanel.add(Box.createHorizontalStrut(15)); // a spacer
	      myPanel.add(new JLabel("Please enter the number of guests:"));
	      myPanel.add(numberField);

	      int result = JOptionPane.showConfirmDialog(null, myPanel, 
	               "Please Enter Information", JOptionPane.OK_CANCEL_OPTION);
	      return result == JOptionPane.OK_OPTION;
	}
	//unfinished
	
	public void AddBookingInfo(int listing_id, Date stay_from, Date stay_to) {
		JLabel list = new JLabel("listing_id: "+Integer.toString(listing_id));
		
	}
	public void WriteReview(String word) {
		add.add(reviewJP[2]);
		add.add(reviewJP[1]);
		add.add(reviewJlb[3]);
		reviewJtf[2].setText(word);
		jta = new JTextArea();
		textScrollPane = new JScrollPane(jta);
		textScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		review.add(textScrollPane,BorderLayout.CENTER);
		review.add(reviewJb,BorderLayout.SOUTH);
		revalidate();
		repaint();
	}
}

