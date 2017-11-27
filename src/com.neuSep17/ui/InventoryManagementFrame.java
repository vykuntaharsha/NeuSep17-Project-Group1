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
	private JLabel priceLabel;
	private JTextField priceTextField;
	private JLabel priceAlertLabel;

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
		priceLabel = new JLabel("Price");
		priceTextField = new JTextField(10);
		priceAlertLabel = new JLabel("Price should be integer.");
		priceSetTrue();
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
		componetsPanel.add(priceLabel);
		componetsPanel.add(priceTextField);
		componetsPanel.add(priceAlertLabel);
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
	}

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
				idSetTrue();
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
				return false;
			}
		}

		public boolean shouldYieldFocus(JComponent input) {
			boolean valid = verify(input);
			return true;
		}
	}

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
			if(str.contains("-")){
				webIdSetTrue();
				return true;
			}else{
				return false;
			}
		}

		@Override
		public boolean shouldYieldFocus(JComponent input) {
			boolean valid = verify(input);
			return true;
		}
	}

	private class CategoryListener implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			int keyInput = e.getKeyChar();
			if(keyInput == 67 || keyInput == 78 || keyInput == 85 || keyInput == 99
					|| keyInput == 110 || keyInput == 117 || keyInput == KeyEvent.VK_ENTER
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
				categorySetTrue();
				return false;
			}
		}

		@Override
		public boolean shouldYieldFocus(JComponent input) {
			boolean valid = verify(input);
			return true;
		}
	}

}
