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
		cancelButton = new JButton("CANCEL");
	}

	private void createPanel() {
		JPanel componentsPanel = new JPanel();
		componentsPanel.setLayout(new GridLayout(0, 3));
		componentsPanel.add(saveButton);
		componentsPanel.add(clearButton);
		componentsPanel.add(cancelButton);
		this.add(componentsPanel);
	}

	private void makeThisVisible() {
		this.setSize(500, 500);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		idTextField.addKeyListener(new VIDListener());
		idTextField.setInputVerifier(new VehicleIDVerifier());
		priceTextField.addKeyListener(new PriceListener());
		priceTextField.setInputVerifier(new PriceVerifier());
		webIdTextField.addKeyListener(new WebIDListener());
		webIdTextField.setInputVerifier(new WebIDVerifier());
		categoryTextField.addKeyListener(new CategoryListener());
		categoryTextField.setInputVerifier(new CategoryVerifier());
		yearTextField.addKeyListener(new YearListener());
		yearTextField.setInputVerifier(new YearVerifier());
		makeTextField.addKeyListener(new MakeListener());
		makeTextField.setInputVerifier(new MakeVerifier());
		typeTextField.addKeyListener(new TypeListener());
		typeTextField.setInputVerifier(new TypeVerifier());
		modelTextField.setInputVerifier(new ModelVerifier());
		trimTextField.setInputVerifier(new TrimVerifier());
	}

	// VIDListener & VIDVerifier
	private class VIDListener implements KeyListener {
		@Override
		public void keyPressed(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void keyTyped(KeyEvent e) {
			int keyInput = e.getKeyChar();
			if (keyInput != KeyEvent.VK_ENTER && keyInput != KeyEvent.VK_BACK_SPACE
					&& (keyInput < KeyEvent.VK_0 || keyInput > KeyEvent.VK_9)) {
				idSetWrong();
				e.consume();// invalid numeric input will be eliminated
			}
			String str = idTextField.getText();
			if (keyInput == KeyEvent.VK_ENTER) {// enter
				if (str.length() != 10)
					idSetWrong();
				else
					idSetTrue();
			}
			if (keyInput == KeyEvent.VK_BACK_SPACE) {// backspace
				if (str.length() < 10) {
					idSetTrue();
				}
			}
			if (keyInput >= KeyEvent.VK_0 && keyInput <= KeyEvent.VK_9) {// number
				if (str.length() < 10)
					idSetTrue();
				else {
					idSetWrong();
					e.consume();
				}
			}
		}
	}

	// all SuccessOrNot instance variable is a mark for save button function!!!
	private boolean VIDSuccessOrNot;
	private class VehicleIDVerifier extends InputVerifier {
		public boolean verify(JComponent input) {
			String vid = ((JTextField) input).getText();
			if (vid.length() != 10) {
				idSetWrong();
				return false;
			} else {
				idSetTrue();
				VIDSuccessOrNot = true;
				return true;
			}
		}

		public boolean shouldYieldFocus(JComponent input) {
			verify(input);
			return true;
		}

	}

	// PriceListener & PriceVerifier

	private class PriceListener implements KeyListener {
		@Override
		public void keyPressed(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void keyTyped(KeyEvent e) {
			int keyInput = e.getKeyChar();
			if (keyInput != KeyEvent.VK_ENTER && keyInput != KeyEvent.VK_BACK_SPACE
					&& (keyInput < KeyEvent.VK_0 || keyInput > KeyEvent.VK_9)) {
				priceSetFalse();
				e.consume();
			}
			String str = priceTextField.getText();
			if (keyInput == KeyEvent.VK_ENTER) {
				if (str.equals(""))
					priceSetFalse();
				else
					priceSetTrue();
			}
			if (keyInput == KeyEvent.VK_BACK_SPACE) {
				priceSetTrue();
			}
			if (keyInput >= KeyEvent.VK_0 && keyInput <= KeyEvent.VK_9) {
				priceSetTrue();
			}
		}
	}
	private boolean PriceSuccessOrNot;
	private class PriceVerifier extends InputVerifier {
		@Override
		public boolean verify(JComponent input) {
			String str = ((JTextField) input).getText();
			if (!str.equals("")) {
				priceSetTrue();
				PriceSuccessOrNot = true;
				return true;
			} else {
				priceSetFalse();
				return false;
			}
		}

		@Override
		public boolean shouldYieldFocus(JComponent input) {
			verify(input);
			return true;
		}
	}

	// WebIDListener & WebIDVerifier
	private class WebIDListener implements KeyListener {
		@Override
		public void keyTyped(KeyEvent e) {
			int keyInput = e.getKeyChar();
			if (keyInput != KeyEvent.VK_ENTER && keyInput != KeyEvent.VK_BACK_SPACE && keyInput != KeyEvent.VK_MINUS
					&& (keyInput < 65 || (keyInput > 90 && keyInput < 97) || keyInput > 122)) {
				webidSetWrong();
				e.consume();
			} // invalid input:输入除了回车删除和大小写字母以及横线以外的
			String str = webIdTextField.getText();
			int lastIndex = str.length() - 1;
			if (keyInput == KeyEvent.VK_MINUS) {
				if (str.equals("")) {
					webidSetWrong();// 首位出现横线
				}
			}
			if (keyInput == KeyEvent.VK_ENTER) {
				if (str.contains("-") && str.charAt(lastIndex) != '-')// 不能末尾为-
					webIdSetTrue();
				else
					webidSetWrong();
			}
			if (keyInput == KeyEvent.VK_BACK_SPACE) {
				webIdSetTrue();
			}
			if ((keyInput >= 65 && keyInput <= 90) || (keyInput >= 97 && keyInput <= 122)) {
				webIdSetTrue();// valid letter input
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}
	}

	private boolean WebIDSuccessOrNot;
	private class WebIDVerifier extends InputVerifier {
		@Override
		public boolean verify(JComponent input) {
			String str = ((JTextField) input).getText();
			if (str.contains("-") && str.charAt(str.length() - 1) != '-') {
				webIdSetTrue();
				WebIDSuccessOrNot = true;
				return true;
			} else {
				webidSetWrong();
				return false;
			}
		}

		@Override
		public boolean shouldYieldFocus(JComponent input) {
			verify(input);
			return true;
		}
	}

	// CategoryListener & CategoryVerifier
	private class CategoryListener implements KeyListener {
		@Override
		public void keyTyped(KeyEvent e) {
			char keyInput = e.getKeyChar();
			if (keyInput == 'c' || keyInput == 'C' || keyInput == 'u' || keyInput == 'U' || keyInput == 'n'
					|| keyInput == 'N' || keyInput == KeyEvent.VK_ENTER || keyInput == KeyEvent.VK_BACK_SPACE) {
				categorySetTrue();
			} else {
				categorySetWrong();
				e.consume();
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}
	}
	
	private boolean CategorySuccessOrNot;
	private class CategoryVerifier extends InputVerifier {
		@Override
		public boolean verify(JComponent input) {
			String str = ((JTextField) input).getText();
			if (str.equals("new") || str.equals("used") || str.equals("certified")) {
				categorySetTrue();
				CategorySuccessOrNot = true;
				return true;
			} else {
				categorySetWrong();
				return false;
			}
		}

		@Override
		public boolean shouldYieldFocus(JComponent input) {
			verify(input);
			return true;
		}
	}

	// YearListener & YearVerifier
	private class YearListener implements KeyListener {
		@Override
		public void keyTyped(KeyEvent e) {
			int keyInput = e.getKeyChar();
			if (keyInput != KeyEvent.VK_ENTER && keyInput != KeyEvent.VK_BACK_SPACE
					&& (keyInput < KeyEvent.VK_0 || keyInput > KeyEvent.VK_9)) {
				yearSetFalse();
				e.consume();
			}
			String str = idTextField.getText();
			if (keyInput == KeyEvent.VK_ENTER) {// enter
				if (str.length() != 4)
					yearSetFalse();
				else
					yearSetTrue();
			}
			if (keyInput == KeyEvent.VK_BACK_SPACE) {// backspace
				if (str.length() < 4) {
					yearSetTrue();
				}
			}
			if (keyInput >= KeyEvent.VK_0 && keyInput <= KeyEvent.VK_9) {// number
				if (str.length() < 4)
					yearSetTrue();
				else {
					yearSetFalse();
					e.consume();
				}
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}
	}
	private boolean YearSuccessOrNot;
	private class YearVerifier extends InputVerifier {
		public boolean verify(JComponent input) {
			String year = ((JTextField) input).getText();
			if (year.length() == 4) {
				yearSetTrue();
				YearSuccessOrNot = true;
				return true;
			} else {
				yearSetFalse();
				return false;
			}
		}

		public boolean shouldYieldFocus(JComponent input) {
			verify(input);
			return true;
		}
	}

	// MakeListener & MakeVerifier
	private class MakeListener implements KeyListener {
		@Override
		public void keyTyped(KeyEvent e) {
			int keyInput = e.getKeyChar();
			if (keyInput != KeyEvent.VK_ENTER && keyInput != KeyEvent.VK_BACK_SPACE
					&& (keyInput < 65 || (keyInput > 90 && keyInput < 97) || keyInput > 122)) {// invalid input(only
																								// letters and special
																								// case)
				makeSetFalse();
				e.consume();
			}
			String str = makeTextField.getText();
			if (keyInput == KeyEvent.VK_ENTER) {
				if (str.equals("") || str.equals(null))
					makeSetFalse();
				else
					makeSetTrue();
			}
			if (keyInput == KeyEvent.VK_BACK_SPACE) {
				if (str.equals("") || str.equals(null))
					makeSetFalse();
				else
					makeSetTrue();
			}
			if ((keyInput >= 65 && keyInput <= 90) || (keyInput >= 97 && keyInput <= 122)) {
				makeSetTrue();// valid letter input
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}
	}
	private boolean MakeSuccessOrNot;
	private class MakeVerifier extends InputVerifier {
		@Override
		public boolean verify(JComponent input) {
			String str = ((JTextField) input).getText();
			if (str.equals("") || str.equals(null)) {
				makeSetFalse();
				return false;
			} else {
				makeSetTrue();
				MakeSuccessOrNot = true;
				return true;
			}
		}

		@Override
		public boolean shouldYieldFocus(JComponent input) {
			verify(input);
			return true;
		}
	}

	// TypeListener & TypeVerifier
	private class TypeListener implements KeyListener {
		@Override
		public void keyTyped(KeyEvent e) {
			int keyInput = e.getKeyChar();
			if (keyInput != KeyEvent.VK_ENTER && keyInput != KeyEvent.VK_BACK_SPACE
					&& (keyInput < 65 || (keyInput > 90 && keyInput < 97) || keyInput > 122)) {
				typeSetFalse();
				e.consume();
			}
			String str = makeTextField.getText();
			if (keyInput == KeyEvent.VK_ENTER) {
				if (str.equals("") || str.equals(null))
					typeSetFalse();
				else
					typeSetTrue();
			}
			//
			if (keyInput == KeyEvent.VK_BACK_SPACE) {
				if (str.equals("") || str.equals(null))
					typeSetFalse();
				else
					typeSetTrue();
			}
			if ((keyInput >= 65 && keyInput <= 90) || (keyInput >= 97 && keyInput <= 122)) {
				typeSetTrue();
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}
	}
	private boolean TypeSuccessOrNot;
	private class TypeVerifier extends InputVerifier {
		@Override
		public boolean verify(JComponent input) {
			String str = ((JTextField) input).getText();
			if (str.equals("") || str.equals(null)) {
				typeSetFalse();
				return false;
			} else {
				typeSetTrue();
				TypeSuccessOrNot = true;
				return true;
			}
		}

		@Override
		public boolean shouldYieldFocus(JComponent input) {
			verify(input);
			return true;
		}
	}

	// ModelVerifier
	private boolean ModelSuccessOrNot;
	private class ModelVerifier extends InputVerifier {@Override
		public boolean verify(JComponent input) {
			String str = ((JTextField) input).getText();
			if (str.equals("") || str.equals(null)) {
				modelSetFalse();
				return false;
			} else {
				modelSetTrue();
				ModelSuccessOrNot = true;
				return true;
			}
		}

		@Override
		public boolean shouldYieldFocus(JComponent input) {
			verify(input);
			return true;
		}
	}

	// TrimVerifier
	private boolean TrimSuccessOrNot;
	private class TrimVerifier extends InputVerifier {
		@Override
		public boolean verify(JComponent input) {
			String str = ((JTextField) input).getText();
			if (str.equals("") || str.equals(null)) {
				trimSetFalse();
				return false;
			} else {
				trimSetTrue();
				TrimSuccessOrNot = true;
				return true;
			}
		}

		@Override
		public boolean shouldYieldFocus(JComponent input) {
			verify(input);
			return true;
		}
	}

	private String[] categories = { "new", "used", "certified" };
	private String[] makes = { "All Make", "Acura", "Aston Martin", "Audi", "Bentley", "BMW", "Bugatti", "Buick",
			"Chrysler", "Citroen", "Dodge", "Ferrari", "Fiat", "Ford", "Geely", "General Motors", "GMC", "Honda" };
	private String[] types = { "Luxury", " Sedans", "Coupes", "SUVs", "Crossovers", "Wagons/Hatchbacks", "Hybrids",
			"Convertibles", "Sports Cars", "Pickup Trucks", "Minivans/Vans" };

	private void setupAutoCompletes() {
		setupAutoComplete(this.categoryTextField, new ArrayList<String>(Arrays.asList(categories)));
		setupAutoComplete(this.makeTextField, new ArrayList<String>(Arrays.asList(makes)));
		setupAutoComplete(this.typeTextField, new ArrayList<String>(Arrays.asList(types)));
	}

}
