package com.neuSep17.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.neuSep17.dto.Category;
import com.neuSep17.dto.Vehicle;

@SuppressWarnings("serial")
public class InventoryManagementFrame extends JFrame {
	private Component id, webId, category, year, make, model, trim, type, price;
	private JButton saveButton;
	private JButton clearButton;
	private JButton cancelButton;

	private class Component {
		private JLabel fieldLabel;
		private JTextField inputTextField;
		private JLabel alertLabel;

		public Component(String field, int length, String alert) {
			fieldLabel = new JLabel(field);
			inputTextField = new JTextField(length);
			alertLabel = new JLabel(alert);
			setTrue();
		}

		public Component(String field, String input, int length, String alert) {
			fieldLabel = new JLabel(field);
			inputTextField = new JTextField(input, length);
			alertLabel = new JLabel(alert);
			setTrue();
		}

		public void setTrue() {
			inputTextField.setBorder(new LineBorder(Color.black));
			alertLabel.setForeground(Color.black);
		}

		public void setFalse() {
			inputTextField.setBorder(new LineBorder(Color.red));
			alertLabel.setForeground(Color.red);
		}

		public JLabel getFieldLabel() {
			return fieldLabel;
		}

		public JTextField getInputTextField() {
			return inputTextField;
		}

		public JLabel getAlertLabel() {
			return alertLabel;
		}
	}

	public InventoryManagementFrame() {
		super();
		createCompoments();
		createPanel();
		addListeners();
		loadVehicle(webId, id);
		setupAutoCompletes();
		makeThisVisible();
	}

	private void createCompoments() {
		id = new Component("ID", 10, "ID's length should be 10, only number.");
		webId = new Component("WebID", 20, "Split by \"-\".");
		category = new Component("Category", 15, "New, used or certified.");
		year = new Component("Year", 10, "YEAR.");
		make = new Component("Make", 20, "MAKE");
		model = new Component("Model", 20, "MODEL");
		trim = new Component("Trim", 10, "TRIM.");
		type = new Component("Type", 20, "Type.");
		price = new Component("Price", 20, "Price.");
		saveButton = new JButton("SAVE");
		clearButton = new JButton("CLEAR");
		clearButton.addActionListener(new ClearAllAction());
		cancelButton = new JButton("CANCEL");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

	private void createPanel() {
		JPanel componentsPanel = new JPanel();
		componentsPanel.setLayout(new GridLayout(0, 3));
		componentsPanel.add(id.getInputTextField());
		componentsPanel.add(saveButton);
		componentsPanel.add(clearButton);
		componentsPanel.add(cancelButton);
		this.add(componentsPanel);
	}

	private void makeThisVisible() {
		this.setSize(500, 500);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	private class ClearAllAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			id.getInputTextField().setText("");
			id.setTrue();
			webId.getInputTextField().setText("");
			webId.setTrue();
			category.getInputTextField().setText("");
			category.setTrue();
			year.getInputTextField().setText("");
			year.setTrue();
			make.getInputTextField().setText("");
			make.setTrue();
			model.getInputTextField().setText("");
			model.setTrue();
			trim.getInputTextField().setText("");
			trim.setTrue();
			type.getInputTextField().setText("");
			type.setTrue();
			price.getInputTextField().setText("");
			price.setTrue();
		}

	}

	@SuppressWarnings("rawtypes")
	private static boolean isAdjusting(JComboBox cbInput) {
		if (cbInput.getClientProperty("is_adjusting") instanceof Boolean) {
			return (Boolean) cbInput.getClientProperty("is_adjusting");
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	private static void setAdjusting(JComboBox cbInput, boolean adjusting) {
		cbInput.putClientProperty("is_adjusting", adjusting);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void setupAutoComplete(final JTextField txtInput, final ArrayList<String> items) {
		final DefaultComboBoxModel model = new DefaultComboBoxModel();
		final JComboBox cbInput = new JComboBox(model) {
			public Dimension getPreferredSize() {
				return new Dimension(super.getPreferredSize().width, 0);
			}
		};
		setAdjusting(cbInput, false);
		for (String item : items) {
			model.addElement(item);
		}
		cbInput.setSelectedItem(null);
		cbInput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!isAdjusting(cbInput)) {
					if (cbInput.getSelectedItem() != null) {
						txtInput.setText(cbInput.getSelectedItem().toString());
					}
				}
			}
		});

		txtInput.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				setAdjusting(cbInput, true);
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					if (cbInput.isPopupVisible()) {
						e.setKeyCode(KeyEvent.VK_ENTER);
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_UP
						|| e.getKeyCode() == KeyEvent.VK_DOWN) {
					e.setSource(cbInput);
					cbInput.dispatchEvent(e);
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						txtInput.setText(cbInput.getSelectedItem().toString());
						cbInput.setPopupVisible(false);
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					cbInput.setPopupVisible(false);
				}
				setAdjusting(cbInput, false);
			}
		});
		txtInput.getDocument().addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent e) {
				updateList();
			}

			public void removeUpdate(DocumentEvent e) {
				updateList();
			}

			public void changedUpdate(DocumentEvent e) {
				updateList();
			}

			private void updateList() {
				setAdjusting(cbInput, true);
				model.removeAllElements();
				String input = txtInput.getText();
				if (!input.isEmpty()) {
					for (String item : items) {
						if (item.toLowerCase().startsWith(input.toLowerCase())) {
							model.addElement(item);
						}
					}
				}
				cbInput.setPopupVisible(model.getSize() > 0);
				setAdjusting(cbInput, false);
			}
		});
		txtInput.setLayout(new BorderLayout());
		txtInput.add(cbInput, BorderLayout.SOUTH);
	}

	private void addListeners() {
		id.getInputTextField().addKeyListener(new VIDListener());
		id.getInputTextField().setInputVerifier(new VehicleIDVerifier());
		price.getInputTextField().addKeyListener(new PriceListener());
		price.getInputTextField().setInputVerifier(new PriceVerifier());
		webId.getInputTextField().addKeyListener(new WebIDListener());
		webId.getInputTextField().setInputVerifier(new WebIDVerifier());
		category.getInputTextField().addKeyListener(new CategoryListener());
		category.getInputTextField().setInputVerifier(new CategoryVerifier());
		year.getInputTextField().addKeyListener(new YearListener());
		year.getInputTextField().setInputVerifier(new YearVerifier());
		make.getInputTextField().addKeyListener(new MakeListener());
		make.getInputTextField().setInputVerifier(new MakeVerifier());
		type.getInputTextField().addKeyListener(new TypeListener());
		type.getInputTextField().setInputVerifier(new TypeVerifier());
		model.getInputTextField().setInputVerifier(new ModelVerifier());
		trim.getInputTextField().setInputVerifier(new TrimVerifier());
	}
	//VIDListener & VIDVerifier
	private class VIDListener implements KeyListener {
	    @Override
	    public void keyPressed(KeyEvent e){}
	    @Override
	    public void keyReleased(KeyEvent e){}
	    @Override
	    public void keyTyped(KeyEvent e){
	        int keyInput = e.getKeyChar();
	        if(keyInput != KeyEvent.VK_ENTER && keyInput != KeyEvent.VK_BACK_SPACE
					&& (keyInput < KeyEvent.VK_0 || keyInput > KeyEvent.VK_9)){
	            id.setFalse();
	            e.consume();//invalid numeric input will be eliminated
	        }
	        String str = id.getInputTextField().getText();
	        if(keyInput == KeyEvent.VK_ENTER){//enter
	        	if(str.length() != 10)
	        		id.setFalse();
	        	else
	        		id.setTrue();
			}
			if(keyInput == KeyEvent.VK_BACK_SPACE){//backspace
	        	if(str.length() < 10){
					id.setTrue();
				}
			}
			if(keyInput >= KeyEvent.VK_0 && keyInput <= KeyEvent.VK_9){//number
				if(str.length() < 10)
					id.setTrue();
				else{
					id.setFalse();
					e.consume();
				}
			}
	    }
	}
	//all SuccessOrNot instance variable is a mark for save button function!!!
	private boolean VIDSuccessOrNot;
	private class VehicleIDVerifier extends InputVerifier {
		public boolean verify(JComponent input){
			String vid = ((JTextField)input).getText();
			if(vid.length() != 10){
				id.setFalse();
				return false;
			}else{
				id.setTrue();
				VIDSuccessOrNot = true;
				return true;
			}
		}
		public boolean shouldYieldFocus(JComponent input) {
			verify(input);
			return true;
		}
	}

	//PriceListener & PriceVerifier
	private class PriceListener implements KeyListener {
		@Override
		public void keyPressed(KeyEvent e){}
		@Override
		public void keyReleased(KeyEvent e){}
		@Override
		public void keyTyped(KeyEvent e){
			int keyInput = e.getKeyChar();
			if(keyInput != KeyEvent.VK_ENTER && keyInput != KeyEvent.VK_BACK_SPACE
					&& (keyInput < KeyEvent.VK_0 || keyInput > KeyEvent.VK_9)){
				price.setFalse();
				e.consume();
			}
			String str = price.getInputTextField().getText();
			if(keyInput == KeyEvent.VK_ENTER){
				if(str.equals(""))
					price.setFalse();
				else
					price.setTrue();
			}
			if(keyInput == KeyEvent.VK_BACK_SPACE){
					price.setTrue();
			}
			if(keyInput >=KeyEvent.VK_0 && keyInput <= KeyEvent.VK_9 ){
				price.setTrue();
			}
		}
	}

	private boolean PriceSuccessOrNot;
	private class PriceVerifier extends InputVerifier {
		@Override
		public boolean verify(JComponent input){
			String str = ((JTextField)input).getText();
			if(!str.equals("")){
				price.setTrue();
				PriceSuccessOrNot = true;
				return true;
			}else{
				price.setFalse();
				return false;
			}
		}
		@Override
		public boolean shouldYieldFocus(JComponent input) {
			verify(input);
			return true;
		}
	}

	//WebIDListener & WebIDVerifier
	private class WebIDListener implements KeyListener{
		@Override
		public void keyTyped(KeyEvent e) {
			int keyInput = e.getKeyChar();
			if(keyInput != KeyEvent.VK_ENTER && keyInput != KeyEvent.VK_BACK_SPACE
					&& keyInput != KeyEvent.VK_MINUS
					&& (keyInput < 65 || (keyInput > 90 && keyInput < 97)
					|| keyInput > 122)){
				webId.setFalse();
				e.consume();
			}//invalid input:输入除了回车删除和大小写字母以及横线以外的
			String str = webId.getInputTextField().getText();
			int lastIndex = str.length() - 1;
			if(keyInput == KeyEvent.VK_MINUS){
				if(str.equals("")){
					webId.setFalse();//首位出现横线
				}
			}
			if(keyInput == KeyEvent.VK_ENTER){
				if(str.contains("-") && str.charAt(lastIndex) != '-')//不能末尾为-
					webId.setTrue();
				else
					webId.setFalse();
			}
			if(keyInput == KeyEvent.VK_BACK_SPACE){
				webId.setTrue();
			}
			if((keyInput >= 65 && keyInput <= 90) || (keyInput >= 97 && keyInput <= 122)){
				webId.setTrue();//valid letter input
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {}

		@Override
		public void keyReleased(KeyEvent e) {}
	}
	private boolean WebIDSuccessOrNot;
	private class WebIDVerifier extends InputVerifier{
		@Override
		public boolean verify(JComponent input) {
			String str = ((JTextField)input).getText();
			if(str.contains("-") && str.charAt(str.length() - 1) != '-'){
				webId.setTrue();
				WebIDSuccessOrNot = true;
				return true;
			}else{
				webId.setFalse();
				return false;
			}
		}
		@Override
		public boolean shouldYieldFocus(JComponent input) {
			verify(input);
			return true;
		}
	}

	//CategoryListener & CategoryVerifier
	private class CategoryListener implements KeyListener{
		@Override
		public void keyTyped(KeyEvent e) {
			char keyInput = e.getKeyChar();
			if(keyInput == 'c' || keyInput == 'C' || keyInput == 'u' || keyInput == 'U'
					|| keyInput == 'n' || keyInput == 'N' || keyInput == KeyEvent.VK_ENTER
					|| keyInput == KeyEvent.VK_BACK_SPACE) {
				category.setTrue();
			}else{
				category.setFalse();
				e.consume();
			}
		}
		@Override
		public void keyPressed(KeyEvent e) {}

		@Override
		public void keyReleased(KeyEvent e) {}
	}
	private boolean CategorySuccessOrNot;
	private class CategoryVerifier extends InputVerifier {
		@Override
		public boolean verify(JComponent input) {
			String str = ((JTextField)input).getText();
			if (str.equals("new") || str.equals("used") || str.equals("certified")){
				category.setTrue();
				CategorySuccessOrNot = true;
				return true;
			}else {
				category.setFalse();
				return false;
			}
		}
		@Override
		public boolean shouldYieldFocus(JComponent input) {
			verify(input);
			return true;
		}
	}
	//YearListener & YearVerifier
	private class YearListener implements KeyListener{
		@Override
		public void keyTyped(KeyEvent e) {
			int keyInput = e.getKeyChar();
			if(keyInput != KeyEvent.VK_ENTER && keyInput != KeyEvent.VK_BACK_SPACE
					&& (keyInput < KeyEvent.VK_0 || keyInput > KeyEvent.VK_9)){
				year.setFalse();
				e.consume();
			}
			String str = year.getInputTextField().getText();
			if(keyInput == KeyEvent.VK_ENTER){//enter
				if(str.length() != 4)
					year.setFalse();
				else
					year.setTrue();
			}
			if(keyInput == KeyEvent.VK_BACK_SPACE){//backspace
				if(str.length() < 4){
					year.setTrue();
				}
			}
			if(keyInput >= KeyEvent.VK_0 && keyInput <= KeyEvent.VK_9){//number
				if(str.length() < 4)
					year.setTrue();
				else{
					year.setFalse();
					e.consume();
				}
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {}

		@Override
		public void keyReleased(KeyEvent e) {}
	}

	private boolean YearSuccessOrNot;
	private class YearVerifier extends InputVerifier{
		public boolean verify(JComponent input){
			String str = ((JTextField)input).getText();
			if(str.length() == 4){
				year.setTrue();
				YearSuccessOrNot = true;
				return true;
			}else{
				year.setFalse();
				return false;
			}
		}

		public boolean shouldYieldFocus(JComponent input) {
			verify(input);
			return true;
		}
	}
	//MakeListener & MakeVerifier
	private class MakeListener implements KeyListener{
		@Override
		public void keyTyped(KeyEvent e) {
			int keyInput = e.getKeyChar();
			if (keyInput != KeyEvent.VK_ENTER && keyInput != KeyEvent.VK_BACK_SPACE
					&& (keyInput < 65 || (keyInput > 90 && keyInput < 97)
					|| keyInput > 122)) {//invalid input(only letters and special case)
				make.setFalse();
				e.consume();
			}
			String str = make.getInputTextField().getText();
			if(keyInput == KeyEvent.VK_ENTER){
				if(str.equals("") || str.equals(null))
					make.setFalse();
				else
					make.setTrue();
			}
			if(keyInput == KeyEvent.VK_BACK_SPACE){
				if(str.equals("") || str.equals(null))
					make.setFalse();
				else
					make.setTrue();
			}
			if((keyInput >= 65 && keyInput <= 90) || (keyInput >= 97 && keyInput <= 122)){
				make.setTrue();//valid letter input
			}
		}
		@Override
		public void keyPressed(KeyEvent e) {}
		@Override
		public void keyReleased(KeyEvent e) {}
	}

	private boolean MakeSuccessOrNot;
	private class MakeVerifier extends InputVerifier{
		@Override
		public boolean verify(JComponent input){
			String str = ((JTextField)input).getText();
			if(str.equals("") || str.equals(null)){
				make.setFalse();
				return false;
			}else{
				make.setTrue();
				MakeSuccessOrNot = true;
				return true;
			}
		}

		@Override
		public boolean shouldYieldFocus(JComponent input){
			verify(input);
			return true;
		}
	}
	//TypeListener & TypeVerifier
	private class TypeListener implements KeyListener{
		@Override
		public void keyTyped(KeyEvent e) {
			int keyInput = e.getKeyChar();
			if (keyInput != KeyEvent.VK_ENTER && keyInput != KeyEvent.VK_BACK_SPACE
					&& (keyInput < 65 || (keyInput > 90 && keyInput < 97)
					|| keyInput > 122)) {
				type.setFalse();
				e.consume();
			}
			String str = make.getInputTextField().getText();
			if(keyInput == KeyEvent.VK_ENTER){
				if(str.equals("") || str.equals(null))
					type.setFalse();
				else
					type.setTrue();
			}
			//
			if(keyInput == KeyEvent.VK_BACK_SPACE){
				if(str.equals("") || str.equals(null))
					type.setFalse();
				else
					type.setTrue();
			}
			if((keyInput >= 65 && keyInput <= 90) || (keyInput >= 97 && keyInput <= 122)){
				type.setTrue();
			}
		}
		@Override
		public void keyPressed(KeyEvent e) {}

		@Override
		public void keyReleased(KeyEvent e) {}
	}

	private boolean TypeSuccessOrNot;
	private class TypeVerifier extends InputVerifier{
		@Override
		public boolean verify(JComponent input){
			String str = ((JTextField)input).getText();
			if(str.equals("") || str.equals(null)){
				type.setFalse();
				return false;
			}else{
				type.setTrue();
				TypeSuccessOrNot = true;
				return true;
			}
		}

		@Override
		public boolean shouldYieldFocus(JComponent input){
			verify(input);
			return true;
		}
	}
	//ModelVerifier
	private boolean ModelSuccessOrNot;
	private class ModelVerifier extends InputVerifier{
		@Override
		public boolean verify(JComponent input){
			String str = ((JTextField)input).getText();
			if(str.equals("") || str.equals(null)){
				model.setFalse();
				return false;
			}else{
				model.setTrue();
				ModelSuccessOrNot = true;
				return true;
			}
		}

		@Override
		public boolean shouldYieldFocus(JComponent input){
			verify(input);
			return true;
		}
	}

	//TrimVerifier
	private boolean TrimSuccessOrNot;
	private class TrimVerifier extends InputVerifier{
		@Override
		public boolean verify(JComponent input){
			String str = ((JTextField)input).getText();
			if(str.equals("") || str.equals(null)){
				trim.setFalse();
				return false;
			}else{
				trim.setTrue();
				TrimSuccessOrNot = true;
				return true;
			}
		}
		@Override
		public boolean shouldYieldFocus(JComponent input){
			verify(input);
			return true;
		}
	}
	
	private String[] categories = { "new", "used", "certified" };
	private String[] makes = { "All Make", "Acura", "Aston Martin", "Audi", "Bentley", "BMW", "Bugatti", "Buick",
			"Chrysler", "Citroen", "Dodge", "Ferrari", "Fiat", "Ford", "Geely", "General Motors", "GMC", "Honda" };
	private String[] types = { "Luxury", " Sedans", "Coupes", "SUVs", "Crossovers", "Wagons/Hatchbacks", "Hybrids",
			"Convertibles", "Sports Cars", "Pickup Trucks", "Minivans/Vans" };
	
	private VehicleImpleService service = new VehicleImpleService();
	Vehicle vehicle;
	
	private void setupAutoCompletes() {
		setupAutoComplete(category.getInputTextField(), new ArrayList<String>(Arrays.asList(categories)));
		setupAutoComplete(make.getInputTextField(), new ArrayList<String>(Arrays.asList(makes)));
		setupAutoComplete(type.getInputTextField(), new ArrayList<String>(Arrays.asList(types)));
	}	
	
	private void loadVehicle(String webId, String id) {	
	    vehicle = service.getAVehicle(webId, id);
	    this.id.getInputTextField().setText(String.valueOf(vehicle.id));
        this.webId.getInputTextField().setText(String.valueOf(vehicle.webId));
	    this.category.getInputTextField().setText(String.valueOf(vehicle.category));
	    this.year.getInputTextField().setText(String.valueOf(vehicle.year));
        this.make.getInputTextField().setText(String.valueOf(vehicle.make));
        this.model.getInputTextField().setText(String.valueOf(vehicle.model));
        this.trim.getInputTextField().setText(String.valueOf(vehicle.trim));
        this.type.getInputTextField().setText(String.valueOf(vehicle.type));
        this.price.getInputTextField().setText(String.valueOf(vehicle.price));
	}
	
	public boolean saveVehicle() {
	    if(VIDSuccessOrNot && PriceSuccessOrNot && WebIDSuccessOrNot && CategorySuccessOrNot && 
	            YearSuccessOrNot && MakeSuccessOrNot && TypeSuccessOrNot && ModelSuccessOrNot && 
	            TrimSuccessOrNot) {
	        
	        Vehicle v = new Vehicle();
        	    if(this.id.getInputTextField().getText() != vehicle.id || 
        	            webId.getInputTextField().getText() != vehicle.webId) {    
        	        service.deleteVehicle(vehicle.webId, vehicle.id);	        
        	        
        	        v.id = this.id.getInputTextField().getText();
        	        v.webId = this.webId.getInputTextField().getText();
        	        v.category = Category.valueOf(this.category.getInputTextField().getText());
        	        v.year = Integer.valueOf(this.year.getInputTextField().getText());
        	        v.make = this.make.getInputTextField().getText();
        	        v.model = this.model.getInputTextField().getText();
        	        v.trim = this.trim.getInputTextField().getText();
        	        v.type = this.type.getInputTextField().getText();
        	        v.price = Float.parseFloat(this.price.getInputTextField().getText());
        	        
        	        service.addVehicle(this.webId, v);
        	    }        
        	    
        	    return service.updateVehicle(this.webId, v.id, v);
	    }

	    return false;
	}
}
