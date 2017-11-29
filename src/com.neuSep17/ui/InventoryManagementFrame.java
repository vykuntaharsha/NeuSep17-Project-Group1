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

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

@SuppressWarnings("serial")
public class InventoryManagementFrame extends JFrame {
	private JLabel idLabel;
	private JTextField idTextField;
	private JLabel idAlertLabel;
	private JLabel webIdLabel;
	private JTextField webIdTextField;
	private JLabel webIdAlertLabel;
	private JLabel categoryLabel;
	private JTextField categoryTextField;
	private JLabel categoryAlertLabel;
	private JLabel yearLabel;
	private JTextField yearTextField;
	private JLabel yearAlertLabel;
	private JLabel makeLabel;
	private JTextField makeTextField;
	private JLabel makeAlertLabel;
	private JLabel modelLabel;
	private JTextField modelTextField;
	private JLabel modelAlertLabel;
	private JLabel trimLabel;
	private JTextField trimTextField;
	private JLabel trimAlertLabel;
	private JLabel typeLabel;
	private JTextField typeTextField;
	private JLabel typeAlertLabel;
	private JLabel priceLabel;
	private JTextField priceTextField;
	private JLabel priceAlertLabel;
	private JButton saveButton;
	private JButton clearButton;
	private JButton cancelButton;

	public InventoryManagementFrame() {
		super();
		createCompoments();
		createPanel();
		addListeners();
		makeThisVisible();
	}

	private void createCompoments() {
		idLabel = new JLabel("ID");
		idTextField = new JTextField(10);
		idAlertLabel = new JLabel("ID's length should be 10, only number.");
		idSetTrue();
		webIdLabel = new JLabel("WebID");
		webIdTextField = new JTextField(20);
		webIdAlertLabel = new JLabel("Split by \"-\".");
		webIdSetTrue();
		categoryLabel = new JLabel("Category");
		ArrayList<String> categoryItems = new ArrayList<String>();
		categoryItems.add("new");
		categoryItems.add("used");
		categoryItems.add("certified");
		categoryTextField = new JTextField(15);
		setupAutoComplete(categoryTextField, categoryItems);
		categoryAlertLabel = new JLabel("New, used or certified.");
		categorySetTrue();
		yearLabel = new JLabel("Year");
		yearTextField = new JTextField(10);
		yearAlertLabel = new JLabel("YEAR.");
		yearSetTrue();
		makeLabel = new JLabel("Make");
		makeTextField = new JTextField(20);
		makeAlertLabel = new JLabel("MAKE.");
		makeSetTrue();
		modelLabel = new JLabel("Model");
		modelTextField = new JTextField(20);
		modelAlertLabel = new JLabel("MODEL.");
		modelSetTrue();
		trimLabel = new JLabel("Trim");
		trimTextField = new JTextField(20);
		trimAlertLabel = new JLabel("TRIM.");
		trimSetTrue();
		typeLabel = new JLabel("Type");
		typeTextField = new JTextField(10);
		typeAlertLabel = new JLabel("TYPE.");
		typeSetTrue();
		priceLabel = new JLabel("Price");
		priceTextField = new JTextField(10);
		priceAlertLabel = new JLabel("Price should be integer.");
		priceSetTrue();
		saveButton = new JButton("SAVE");
		clearButton = new JButton("CLEAR");
		cancelButton = new JButton("CANCEL");
	}

	private void createPanel() {
		JPanel componetsPanel = new JPanel();
		componetsPanel.setLayout(new GridLayout(0, 3));
		componetsPanel.add(idLabel);
		componetsPanel.add(idTextField);
		componetsPanel.add(idAlertLabel);
		componetsPanel.add(webIdLabel);
		componetsPanel.add(webIdTextField);
		componetsPanel.add(webIdAlertLabel);
		componetsPanel.add(categoryLabel);
		componetsPanel.add(categoryTextField);
		componetsPanel.add(categoryAlertLabel);
		componetsPanel.add(yearLabel);
		componetsPanel.add(yearTextField);
		componetsPanel.add(yearAlertLabel);
		componetsPanel.add(priceLabel);
		componetsPanel.add(priceTextField);
		componetsPanel.add(priceAlertLabel);
		componetsPanel.add(makeLabel);
		componetsPanel.add(makeTextField);
		componetsPanel.add(makeAlertLabel);
		componetsPanel.add(modelLabel);
		componetsPanel.add(modelTextField);
		componetsPanel.add(modelAlertLabel);
		componetsPanel.add(trimLabel);
		componetsPanel.add(trimTextField);
		componetsPanel.add(trimAlertLabel);
		componetsPanel.add(typeLabel);
		componetsPanel.add(typeTextField);
		componetsPanel.add(typeAlertLabel);
		componetsPanel.add(priceLabel);
		componetsPanel.add(priceTextField);
		componetsPanel.add(priceAlertLabel);
		componetsPanel.add(saveButton);
		componetsPanel.add(clearButton);
		componetsPanel.add(cancelButton);
		this.add(componetsPanel);
	}

	private void makeThisVisible() {
		this.setSize(500, 500);
		this.setVisible(true);
		this.setResizable(false);
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

	private void idSetWrong() {
		idTextField.setBorder(new LineBorder(Color.red));
		idAlertLabel.setForeground(Color.red);
	}

	private void idSetTrue() {
		idTextField.setBorder(new LineBorder(Color.black));
		idAlertLabel.setForeground(Color.black);
	}

	private void webidSetWrong() {
		webIdTextField.setBorder(new LineBorder(Color.red));
		webIdAlertLabel.setForeground(Color.red);
	}

	private void webIdSetTrue() {
		webIdTextField.setBorder(new LineBorder(Color.black));
		webIdAlertLabel.setForeground(Color.black);
	}

	private void categorySetWrong() {
		categoryTextField.setBorder(new LineBorder(Color.red));
		categoryAlertLabel.setForeground(Color.red);
	}

	private void categorySetTrue() {
		categoryTextField.setBorder(new LineBorder(Color.black));
		categoryAlertLabel.setForeground(Color.black);
	}

	private void yearSetTrue() {
		yearTextField.setBorder(new LineBorder(Color.black));
		yearAlertLabel.setForeground(Color.black);
	}

	private void yearSetFalse() {
		yearTextField.setBorder(new LineBorder(Color.red));
		yearAlertLabel.setForeground(Color.red);
	}

	private void makeSetTrue() {
		makeTextField.setBorder(new LineBorder(Color.black));
		makeAlertLabel.setForeground(Color.black);
	}

	private void makeSetFalse() {
		makeTextField.setBorder(new LineBorder(Color.red));
		makeAlertLabel.setForeground(Color.red);
	}

	private void modelSetTrue() {
		modelTextField.setBorder(new LineBorder(Color.black));
		modelAlertLabel.setForeground(Color.black);
	}

	private void modelSetFalse() {
		modelTextField.setBorder(new LineBorder(Color.red));
		modelAlertLabel.setForeground(Color.red);
	}

	private void trimSetTrue() {
		trimTextField.setBorder(new LineBorder(Color.black));
		trimAlertLabel.setForeground(Color.black);
	}

	private void trimSetFalse() {
		trimTextField.setBorder(new LineBorder(Color.red));
		trimAlertLabel.setForeground(Color.red);
	}

	private void typeSetTrue() {
		typeTextField.setBorder(new LineBorder(Color.black));
		typeAlertLabel.setForeground(Color.black);
	}

	private void typeSetFalse() {
		typeTextField.setBorder(new LineBorder(Color.red));
		typeAlertLabel.setForeground(Color.red);
	}

	private void priceSetTrue() {
		priceTextField.setBorder(new LineBorder(Color.black));
		priceAlertLabel.setForeground(Color.black);
	}

	private void priceSetFalse() {
		priceTextField.setBorder(new LineBorder(Color.red));
		priceAlertLabel.setForeground(Color.red);
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
	            idSetWrong();
	            e.consume();//invalid numeric input will be eliminated
	        }
			String str = idTextField.getText();
	        if(keyInput == KeyEvent.VK_ENTER){//enter
	        	if(str.length() != 10)
	        		idSetWrong();
	        	else
	        		idSetTrue();
			}
			if(keyInput == KeyEvent.VK_BACK_SPACE){//backspace
	        	if(str.length() < 10){
	        		idSetTrue();
				}
			}
			if(keyInput >= KeyEvent.VK_0 && keyInput <= KeyEvent.VK_9){//number
				if(str.length() < 10)
					idSetTrue();
				else{
					idSetWrong();
					e.consume();
				}
			}
	    }
	}

	private class VehicleIDVerifier extends InputVerifier {
		public boolean verify(JComponent input){
			String vid = ((JTextField)input).getText();
			if(vid.length() < 10){
				idSetWrong();
				return false;
			}else if(vid.length() == 10){
				idSetTrue();
				return true;
			}else{
				return false;
			}
		}

		public boolean shouldYieldFocus(JComponent input) {
			boolean valid = verify(input);
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
				priceSetFalse();
				e.consume();//invalid numeric input will be eliminated
			}
			String str = priceTextField.getText();

			if(keyInput == KeyEvent.VK_ENTER){
				if(str == "")
					priceSetFalse();
				else
					priceSetTrue();
			}
			if(keyInput == KeyEvent.VK_BACK_SPACE){
				if(str != "")
					priceSetTrue();
			}
			if(keyInput >=KeyEvent.VK_0 && keyInput <= KeyEvent.VK_9 ){
				priceSetTrue();
			}
		}
	}

	private class PriceVerifier extends InputVerifier {
		public boolean verify(JComponent input){
			String str = ((JTextField)input).getText();
			if(!str.equals("")){
				priceSetTrue();
				return true;
			}else{
				priceSetFalse();
				return false;
			}
		}

		public boolean shouldYieldFocus(JComponent input) {
			boolean valid = verify(input);
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
				webidSetWrong();
				e.consume();
			}//invalid input:输入除了回车删除和大小写字母以及横线以外的
			String str = webIdTextField.getText();
			int lastIndex = str.length() - 1;
			if(keyInput == KeyEvent.VK_MINUS){
				if(str.equals("")){
					webidSetWrong();//首位出现横线
				}
			}
			if(keyInput == KeyEvent.VK_ENTER){
				if(str.contains("-") && str.charAt(lastIndex) != '-')//不能末尾为-
					webIdSetTrue();
				else
					webidSetWrong();
			}
			if(keyInput == KeyEvent.VK_BACK_SPACE){
				webIdSetTrue();
			}
			if((keyInput >= 65 && keyInput <= 90) || (keyInput >= 97 && keyInput <= 122)){
				webIdSetTrue();
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {}

		@Override
		public void keyReleased(KeyEvent e) {}
	}

	private class WebIDVerifier extends InputVerifier{

		@Override
		public boolean verify(JComponent input) {
			String str = ((JTextField)input).getText();
			if(str.contains("-") && str.charAt(str.length() - 1) != '-'){
				webIdSetTrue();
				return true;
			}else{
				webidSetWrong();
				return false;
			}
		}

		@Override
		public boolean shouldYieldFocus(JComponent input) {
			boolean valid = verify(input);
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
				categorySetTrue();
			}else{
				categorySetWrong();
				e.consume();
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {}

		@Override
		public void keyReleased(KeyEvent e) {}
	}

	private class CategoryVerifier extends InputVerifier {
		@Override
		public boolean verify(JComponent input) {
			String str = ((JTextField)input).getText();
			if (str.equals("new") || str.equals("used") || str.equals("certified")){
				categorySetTrue();
				return true;
			}else {
				categorySetWrong();
				return false;
			}
		}

		@Override
		public boolean shouldYieldFocus(JComponent input) {
			boolean valid = verify(input);
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
				yearSetFalse();
				e.consume();
			}
			String str = idTextField.getText();
			if(keyInput == KeyEvent.VK_ENTER){//enter
				if(str.length() != 4)
					yearSetFalse();
				else
					yearSetTrue();
			}
			if(keyInput == KeyEvent.VK_BACK_SPACE){//backspace
				if(str.length() < 4){
					yearSetTrue();
				}
			}
			if(keyInput >= KeyEvent.VK_0 && keyInput <= KeyEvent.VK_9){//number
				if(str.length() < 4)
					yearSetTrue();
				else{
					yearSetFalse();
					e.consume();
				}
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {}

		@Override
		public void keyReleased(KeyEvent e) {}
	}

	private class YearVerifier extends InputVerifier{
		public boolean verify(JComponent input){
			String year = ((JTextField)input).getText();
			if(year.equals("") || year.length() < 4){
				yearSetFalse();
				return false;
			}else if(year.length() == 4){
				yearSetTrue();
				return true;
			}else{
				return false;
			}
		}

		public boolean shouldYieldFocus(JComponent input) {
			boolean valid = verify(input);
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
					|| keyInput > 122)) {
				makeSetFalse();
				e.consume();
			}
			String str = makeTextField.getText();
			if(keyInput == KeyEvent.VK_ENTER){
				if(str.equals("") || str.equals(null))
					makeSetTrue();
				else
					makeSetFalse();
			}
			if(keyInput == KeyEvent.VK_BACK_SPACE){
				if(str.equals("") || str.equals(null))
					makeSetTrue();
				else
					makeSetFalse();
			}
			if((keyInput >= 65 && keyInput <= 90) || (keyInput >= 97 && keyInput <= 122)){
				makeSetTrue();
			}

		}
		@Override
		public void keyPressed(KeyEvent e) {}

		@Override
		public void keyReleased(KeyEvent e) {}
	}

	private class MakeVerifier extends InputVerifier{
		@Override
		public boolean verify(JComponent input){
			String str = ((JTextField)input).getText();
			if(str.equals("") || str.equals(null)){
				makeSetFalse();
				return false;
			}else{
				makeSetTrue();
				return true;
			}
		}

		@Override
		public boolean shouldYieldFocus(JComponent input){
			boolean valid = verify(input);
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
				typeSetFalse();
				e.consume();
			}
			String str = makeTextField.getText();
			if(keyInput == KeyEvent.VK_ENTER){
				if(str.equals("") || str.equals(null))
					typeSetTrue();
				else
					typeSetFalse();
			}
			if(keyInput == KeyEvent.VK_BACK_SPACE){
				if(str.equals("") || str.equals(null))
					typeSetTrue();
				else
					typeSetFalse();
			}
			if((keyInput >= 65 && keyInput <= 90) || (keyInput >= 97 && keyInput <= 122)){
				typeSetTrue();
			}

		}
		@Override
		public void keyPressed(KeyEvent e) {}

		@Override
		public void keyReleased(KeyEvent e) {}
	}

	private class TypeVerifier extends InputVerifier{
		@Override
		public boolean verify(JComponent input){
			String str = ((JTextField)input).getText();
			if(str.equals("") || str.equals(null)){
				typeSetFalse();
				return false;
			}else{
				typeSetTrue();
				return true;
			}
		}

		@Override
		public boolean shouldYieldFocus(JComponent input){
			boolean valid = verify(input);
			return true;
		}
	}
	//ModelVerifier
	private class ModelVerifier extends InputVerifier{
		@Override
		public boolean verify(JComponent input){
			String str = ((JTextField)input).getText();
			if(str.equals("") || str.equals(null)){
				modelSetFalse();
				return false;
			}else{
				modelSetTrue();
				return true;
			}
		}

		@Override
		public boolean shouldYieldFocus(JComponent input){
			boolean valid = verify(input);
			return true;
		}
	}
	//TrimVerifier
	private class TrimVerifier extends InputVerifier{
		@Override
		public boolean verify(JComponent input){
			String str = ((JTextField)input).getText();
			if(str.equals("") || str.equals(null)){
				trimSetFalse();
				return false;
			}else{
				trimSetTrue();
				return true;
			}
		}
		@Override
		public boolean shouldYieldFocus(JComponent input){
			boolean valid = verify(input);
			return true;
		}
	}

}
