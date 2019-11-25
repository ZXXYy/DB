package embedded;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class reviewAction implements MouseListener{
	static GUI gui;
	Connection con;
	
	public reviewAction(GUI gui, Connection con) {
		this.gui = gui;
		this.con = con;	
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
            "Would you like to review for " + words[0]+"?","Review a listing", JOptionPane.YES_NO_CANCEL_OPTION);
            if(dialogResult == JOptionPane.YES_OPTION) {
            	System.out.println("Review a listing!");
            	gui.WriteReview(words[0]);
          
            }
          }
        }
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
