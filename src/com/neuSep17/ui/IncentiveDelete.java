import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class IntenciveDelete extends JDialog
{
	 private JLabel lblNewLabel;
	 private JButton btnYesiAmSure;
	 private JButton btnNo;
	 private static JDialog frame;
	 
     public IntenciveDelete()
     {
    	 super(frame,true);
    	 frame=new JDialog();
    	 initialize();
    	 createComponents();
         addComponents();
         addListeners();
         makeThisVisible();
         
     }
     
     private void displayCenter(JDialog frame)
 	{
 		int windowWidth = frame.getWidth();                     //get the width of the window
 	    int windowHeight = frame.getHeight();                   //get the height
 	    Toolkit kit = Toolkit.getDefaultToolkit();              //define tool
 	    Dimension screenSize = kit.getScreenSize();             //get the size of the screen
 	    int screenWidth = screenSize.width;                     //get the width of the screen
 	    int screenHeight = screenSize.height;                   //get the height of the screen
 	    frame.setLocation(screenWidth/2-windowWidth/2, screenHeight/2-windowHeight/2);//display the frame in the center of the screen 
 	}
     
     private void initialize()
     {
 		frame.setTitle("Delete"); 
 		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 		frame.setBackground(Color.white);
     }
     private void createComponents()
     {
    	 lblNewLabel = new JLabel("      Do you really want to delete this record?");
 		// lblNewLabel.setBounds(20, 44, 330, 45);
 		 btnYesiAmSure = new JButton("Yes");
    //	 btnYesiAmSure.setBounds(197, 101, 93, 29);
    	 btnNo = new JButton("No");
    //	 btnNo.setBounds(60, 101, 93, 29);
     }

     private void addComponents()
     {
    	 Container con=frame.getContentPane();
    	 con.setLayout(new BorderLayout());
    	 JPanel content=new JPanel(new FlowLayout());
    	 JPanel Label=new JPanel(new FlowLayout());
    	 Label.add(lblNewLabel);
    	 JPanel Buttons=new JPanel(new FlowLayout());
    	 Buttons.add(btnYesiAmSure);
    	 Buttons.add(btnNo);
    	 
    	 content.add(Label);
    	 content.add(Buttons);
    	 con.add(content);
     }

     private void addListeners()
     {
    	 btnNo.addMouseListener(new MouseAdapter() {
 			public void mouseEntered(MouseEvent e) 
 			{
 				btnNo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
 				btnNo.setForeground(Color.GRAY);
             }
             public void mouseExited(MouseEvent e) {
             	btnNo.setCursor(Cursor.getDefaultCursor());
             	btnNo.setForeground(Color.BLACK);
             }
 			@Override
 			public void mouseClicked(MouseEvent arg0) {
 				frame.dispose();
 			}
 		});
    	 
    	 btnYesiAmSure.addMouseListener(new MouseAdapter() {
 			public void mouseEntered(MouseEvent e) {
 				btnYesiAmSure.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
 				btnYesiAmSure.setForeground(Color.GRAY);
             }
             public void mouseExited(MouseEvent e) {
             	btnYesiAmSure.setCursor(Cursor.getDefaultCursor());
             	btnYesiAmSure.setForeground(Color.BLACK);
             }
             
 			@Override
 			public void mouseClicked(MouseEvent e) {
 				frame.dispose();
 				JDialog frame2=new JDialog();
 				frame2.setBounds(100,100,350,150);
 				frame2.setBackground(Color.white);
 				frame2.getContentPane().setLayout(null);
 				displayCenter(frame2);
 				JLabel lblNewLabel2 = new JLabel("    Congratulations!   Successfully delete!");
 				lblNewLabel2.setBounds(30, 44, 330, 45);
 				frame2.add(lblNewLabel2);
 				frame2.getContentPane().add(lblNewLabel2);
 				frame2.setVisible(true);
 			}
 		});
     }

     private void makeThisVisible()
     {
    	 
    	 frame.setBounds(100, 100, 350, 160);
         displayCenter(frame);
         frame.setVisible(true);
     }
}
