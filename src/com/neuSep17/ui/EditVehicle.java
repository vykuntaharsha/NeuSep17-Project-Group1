package com.neuSep17.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
//import com.jgoodies.forms.layout.FormLayout;
//import com.jgoodies.forms.layout.ColumnSpec;
//import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EditVehicle {

	private JFrame frame;
	private JTextField vin;
	private JTextField year;
	private JTextField model;
	private JTextField mileage;
	private JTextField color;
	private JTextField trim;
	private JTextField exterior;
	private JTextField interior;
	private JTextField price;
	private JTextField body;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditVehicle window = new EditVehicle();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EditVehicle() {
	    
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton saveButton = new JButton("SAVE");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showConfirmDialog(null, "Confirm ?", "Confirmation", JOptionPane.YES_NO_OPTION);
			}
		});
		saveButton.setBounds(89, 230, 117, 29);
		frame.getContentPane().add(saveButton);
		
		JButton cancelButton = new JButton("CANCEL");
		cancelButton.setBounds(256, 230, 117, 29);
		frame.getContentPane().add(cancelButton);
		
		vin = new JTextField();
		vin.setBounds(89, 18, 113, 26);
		frame.getContentPane().add(vin);
		vin.setColumns(10);
		
		JLabel lblVin = new JLabel("VIN:");
		lblVin.setHorizontalAlignment(SwingConstants.CENTER);
		lblVin.setBounds(6, 23, 71, 16);
		frame.getContentPane().add(lblVin);
		
		year = new JTextField();
		year.setColumns(10);
		year.setBounds(89, 60, 113, 26);
		frame.getContentPane().add(year);
		
		JLabel lblYear = new JLabel("YEAR:");
		lblYear.setHorizontalAlignment(SwingConstants.CENTER);
		lblYear.setBounds(6, 65, 71, 16);
		frame.getContentPane().add(lblYear);
		
		model = new JTextField();
		model.setColumns(10);
		model.setBounds(315, 18, 117, 26);
		frame.getContentPane().add(model);
		
		JLabel lblModel = new JLabel("MODEL:");
		lblModel.setHorizontalAlignment(SwingConstants.CENTER);
		lblModel.setBounds(239, 23, 64, 16);
		frame.getContentPane().add(lblModel);
		
		mileage = new JTextField();
		mileage.setColumns(10);
		mileage.setBounds(315, 60, 117, 26);
		frame.getContentPane().add(mileage);
		
		JLabel lblMileage = new JLabel("MILEAGE:");
		lblMileage.setHorizontalAlignment(SwingConstants.CENTER);
		lblMileage.setBounds(239, 65, 64, 16);
		frame.getContentPane().add(lblMileage);
		
		color = new JTextField();
		color.setColumns(10);
		color.setBounds(89, 98, 113, 26);
		frame.getContentPane().add(color);
		
		JLabel lblPrice = new JLabel("COLOR:");
		lblPrice.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrice.setBounds(6, 103, 71, 16);
		frame.getContentPane().add(lblPrice);
		
		trim = new JTextField();
		trim.setColumns(10);
		trim.setBounds(315, 98, 117, 26);
		frame.getContentPane().add(trim);
		
		JLabel lblTrim = new JLabel("TRIM:");
		lblTrim.setHorizontalAlignment(SwingConstants.CENTER);
		lblTrim.setBounds(233, 103, 64, 16);
		frame.getContentPane().add(lblTrim);
		
		exterior = new JTextField();
		exterior.setColumns(10);
		exterior.setBounds(89, 140, 113, 26);
		frame.getContentPane().add(exterior);
		
		JLabel lblPrice_1 = new JLabel("EXTERIOR:");
		lblPrice_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrice_1.setBounds(6, 145, 71, 16);
		frame.getContentPane().add(lblPrice_1);
		
		interior = new JTextField();
		interior.setColumns(10);
		interior.setBounds(315, 136, 117, 26);
		frame.getContentPane().add(interior);
		
		JLabel lblTransmission = new JLabel("INTERIOR:");
		lblTransmission.setHorizontalAlignment(SwingConstants.CENTER);
		lblTransmission.setBounds(239, 145, 64, 16);
		frame.getContentPane().add(lblTransmission);
		
		price = new JTextField();
		price.setColumns(10);
		price.setBounds(89, 182, 117, 26);
		frame.getContentPane().add(price);
		
		body = new JTextField();
		body.setColumns(10);
		body.setBounds(315, 182, 117, 26);
		frame.getContentPane().add(body);
		
		JLabel lblBody = new JLabel("BODY:");
		lblBody.setHorizontalAlignment(SwingConstants.CENTER);
		lblBody.setBounds(239, 187, 64, 16);
		frame.getContentPane().add(lblBody);
		
		JLabel label_3 = new JLabel("PRICE:");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBounds(6, 187, 71, 16);
		frame.getContentPane().add(label_3);
	}
}
