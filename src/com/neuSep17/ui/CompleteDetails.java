/**
 * 
 * Author - Aarabhi Pugazhendhi
 */
//Used under VehicleDetailsUI.java
package com.neuSep17.ui;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import com.neuSep17.dto.Vehicle;
import java.awt.Color;

public class CompleteDetails extends JDialog {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTable table;

    /**
     * Launch the application.
     */

    /**
     * Create the dialog.
     */
    public CompleteDetails(Vehicle vehicle) {
	
    	setUndecorated(true);
    	
    	// setLocation((Toolkit.getDefaultToolkit().getScreenSize().width)/2 - getWidth()/2, (Toolkit.getDefaultToolkit().getScreenSize().height)/2 - getHeight()/2);

    	addFocusListener(new FocusAdapter() {
    		@Override
    		public void focusLost(FocusEvent e) {
    		    dispose();
    		}
    	});
    	setMinimumSize(new Dimension(97, 0));
	setBounds(100, 100, 579, 438);
	contentPanel.setBackground(new Color(245, 222, 179));
	//getContentPane().setLayout(new BorderLayout());
	contentPanel.setLayout(new FlowLayout());
	contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
	getContentPane().add(contentPanel, BorderLayout.CENTER);
	{
		table = new JTable();
		table.setGridColor(new Color(153, 153, 51));
		table.setBackground(new Color(245, 222, 179));
		table.setShowVerticalLines(false);
		table.setRowHeight(25);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			    {"StockNumber",vehicle.getID()},{"Dealer",vehicle.getWebID()},{"Year",vehicle.getYear()},{"Make",vehicle.getMake()},
		                {"Model",vehicle.getModel()},{"Trim",vehicle.getTrim()},{"Category",vehicle.getCategory()},{"BodyType",vehicle.getBodyType()}
		                ,{"VIN",vehicle.getVin()},{"Entertainment",vehicle.getEntertainment()},{"Interior Color",vehicle.getInteriorColor()},
		                {"Exterior Color",vehicle.getExteriorColor()},{"Fuel Type",vehicle.getFuelType()},{"Engine",vehicle.getEngine()},{"Transmission",vehicle.getTransmission()},
		                    {"Battery",vehicle.getBattery()},{"Optional Features",vehicle.getOptionalFeatures()},
			},
			new String[] {
				"New column", "New column"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(131);
		table.getColumnModel().getColumn(1).setPreferredWidth(432);
		contentPanel.add(table);
	}
	
	
    }

   

}
