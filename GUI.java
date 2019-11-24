package embedded;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.util.Vector;

import javax.swing.*;

public class GUI extends JFrame{
	JPanel jp[] = new JPanel[6];
	JLabel jlb[] = new JLabel[5];
	JButton jb;
	JTextField jtf[] = new JTextField[5];
	JComboBox<String> jcb;
	JSplitPane jsplit;
	JList<String> jlist;
	JPanel search = new JPanel();
	DefaultListModel<String> listModel;
	JScrollPane scrollPane = new JScrollPane();
	
	
	Connection con;
	//public static void main(String[] args) {
	//	GUI gui = new GUI();
	//}
	
	public GUI(Connection con) {
		this.con = con;
		for(int i = 0;i<6;i++) {
			jp[i] = new JPanel();
		}
		
		jlb[0] = new JLabel("Minimum price");
		jlb[1] = new JLabel("Maximum price");
		jlb[2] = new JLabel("number of bedrooms");
		jlb[3] = new JLabel("Start date");
		jlb[4] = new JLabel("End date");
		
		jb = new JButton("Search");
		
		for(int i = 0; i<5; i++) {
			jtf[i] = new JTextField(10);
		}
		
		String [] bedroomNum = {"1","2","3","4","5","6","7","8","9"};
		JComboBox<String> jcb = new JComboBox<String>(bedroomNum);
		
		this.setLayout(new GridLayout(6,1));
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
		//this.add(jsplit);
		this.setContentPane(jsplit);
		
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
		
	}

	public JTextField[] getjtf() {
		return jtf;
	}
	public DefaultListModel getListModel(){
		return listModel;
	}
	public JList<String> getJList(){
		return jlist;
	}
}

