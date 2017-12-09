package com.neuSep17.ui;

/**
 * This function is for dealers to add/edit/delete vehicle information.
 * @author YuXin Li, Chun Yang, Lu Niu, Yuanyuan Jin, Bin Shi (Team Lead)
 */

import com.neuSep17.dao.*;
import com.neuSep17.dto.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

@SuppressWarnings("serial")
public class InventoryEditUI extends JFrame {
    private Component id, webId, category, year, make, model, trim, type, price;
    private JLabel photoLabel;
    private JButton saveButton;
    private JButton clearButton;
    private JButton cancelButton;

    // for testing purpose. will delete when delivery
    public static void main(String[] args) {
        new InventoryEditUI();
    }

    private class Component {
        private JLabel fieldLabel;
        private JTextField inputTextField;
        private JLabel alertLabel;

        public Component(String field, int length, String alert) {
            fieldLabel = new JLabel(field);
            inputTextField = new JTextField(length);
            alertLabel = new JLabel(alert);
            alertLabel.setForeground(Color.red);
            setTrue();
        }

        public void setTrue() {
            inputTextField.setBorder(new LineBorder(Color.black));
            alertLabel.setVisible(false);

        }

        public void setFalse() {
            inputTextField.setBorder(new LineBorder(Color.red));
            alertLabel.setVisible(true);
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

        public JLabel getPhoto() {
            return fieldLabel;
        }
    }

    public InventoryEditUI() {//code-review-bin: we may still need the vehicle list as the input
        super();
        createCompoments();
        createPanel();
        addListeners();
        addHotKeyListeners();
        setupAutoCompletes();
        makeThisVisible();
    }

    public InventoryEditUI(Vehicle v, InventoryListUI listUI) {//code-review-bin: we may only need vehicle and list (instead of listUI)
        super();
        createCompoments();
        createPanel();
        addListeners();
        addHotKeyListeners();
        loadVehicle(v);
        setupAutoCompletes();
        makeThisVisible();
        this.listUI = listUI;
    }
    
    private void createCompoments() {
        id = new Component("ID", 10, "Numeric value of size 10.");
        webId = new Component("WebID", 20, "Valid letter input split by \"-\".");
        category = new Component("Category", 15, "New, used or certified.");
        year = new Component("Year", 10, "Numeric value of size 4.");
        make = new Component("Make", 20, "Vehicle Brand");
        model = new Component("Model", 20, "Vehicle Model");
        trim = new Component("Trim", 10, "Vehicle Trim.");
        type = new Component("Type", 20, "Vehicle Type.");
        price = new Component("Price", 20, "Integer Only.");
        saveButton = new JButton("Save");
        saveButton.setBackground(Color.gray);
        saveButton.setForeground(Color.black);
        clearButton = new JButton("Clear");
        clearButton.setBackground(Color.gray);
        clearButton.setForeground(Color.black);
        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Color.gray);
        cancelButton.setForeground(Color.black);
        photoLabel = new JLabel("Photo");// photo
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(null, "Save?", "ave",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                    try {
                        // save method;
                        saveVehicle(vehicle);
                    } catch (MalformedURLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    dispose();
                }
            }
        });
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(null, "Clear all?", "Clear",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    ClearAllAction clearAllAction = new ClearAllAction();
                    clearAllAction.actionPerformed(e);
                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(null, "Cancel?", "Cancel",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (JOptionPane.showConfirmDialog(null, "Cancel?", "Cancel",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
                    dispose();
            }
        });
    }

    private void createPanel() {
        JPanel componetsPanel = new JPanel();
        componetsPanel.setBounds(100, 100, 450, 300);
        componetsPanel.setLayout(null);
        componetsPanel.setBackground(Color.lightGray);

        JTextField lineGraph = new JTextField();

        // photo by Bin Shi
        photoLabel.setBounds(315, 50, 100, 100);
        photoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        photoLabel.setBackground(Color.lightGray);
        photoLabel.setToolTipText("Click to Change");
        componetsPanel.add(photoLabel);

        // save,cancel,clear

        cancelButton.setBounds(10, 440, 117, 29);
        componetsPanel.add(cancelButton);
        clearButton.setBounds(180, 440, 117, 29);
        componetsPanel.add(clearButton);
        saveButton.setBounds(350, 440, 117, 29);
        componetsPanel.add(saveButton);

        // line
        lineGraph.setBounds(0, 225, 500, 5);
        lineGraph.setBackground(Color.lightGray);
        lineGraph.setEditable(false);
        componetsPanel.add(lineGraph);

        // 1.id
        id.getInputTextField().setBounds(90, 40, 110, 20);
        componetsPanel.add(id.getInputTextField());
        id.getInputTextField().setColumns(10);

        id.getFieldLabel().setHorizontalAlignment(SwingConstants.LEFT);
        id.getFieldLabel().setBounds(20, 40, 70, 20);
        componetsPanel.add(id.getFieldLabel());

        id.getAlertLabel().setHorizontalAlignment(SwingConstants.LEFT);
        id.getAlertLabel().setBounds(90, 20, 200, 20);
        componetsPanel.add(id.getAlertLabel());

        // 2.webId
        webId.getInputTextField().setColumns(10);
        webId.getInputTextField().setBounds(90, 100, 110, 20);
        componetsPanel.add(webId.getInputTextField());

        webId.getFieldLabel().setHorizontalAlignment(SwingConstants.LEFT);
        webId.getFieldLabel().setBounds(20, 100, 70, 20);
        componetsPanel.add(webId.getFieldLabel());

        webId.getAlertLabel().setHorizontalAlignment(SwingConstants.LEFT);
        webId.getAlertLabel().setBounds(90, 80, 200, 20);
        componetsPanel.add(webId.getAlertLabel());

        // 3.category
        category.getInputTextField().setColumns(10);
        category.getInputTextField().setBounds(90, 160, 110, 20);
        componetsPanel.add(category.getInputTextField());

        category.getFieldLabel().setHorizontalAlignment(SwingConstants.LEFT);
        category.getFieldLabel().setBounds(20, 160, 70, 20);
        componetsPanel.add(category.getFieldLabel());

        category.getAlertLabel().setHorizontalAlignment(SwingConstants.LEFT);
        category.getAlertLabel().setBounds(90, 140, 200, 20);
        componetsPanel.add(category.getAlertLabel());

        // 4.year
        year.getInputTextField().setColumns(10);
        year.getInputTextField().setBounds(90, 260, 110, 20);
        componetsPanel.add(year.getInputTextField());

        year.getFieldLabel().setHorizontalAlignment(SwingConstants.LEFT);
        year.getFieldLabel().setBounds(20, 260, 70, 20);
        componetsPanel.add(year.getFieldLabel());

        year.getAlertLabel().setHorizontalAlignment(SwingConstants.LEFT);
        year.getAlertLabel().setBounds(90, 240, 200, 20);
        componetsPanel.add(year.getAlertLabel());

        // 5.make
        make.getInputTextField().setColumns(10);
        make.getInputTextField().setBounds(90, 320, 110, 20);
        componetsPanel.add(make.getInputTextField());

        make.getFieldLabel().setHorizontalAlignment(SwingConstants.LEFT);
        make.getFieldLabel().setBounds(20, 320, 70, 20);
        componetsPanel.add(make.getFieldLabel());

        make.getAlertLabel().setHorizontalAlignment(SwingConstants.LEFT);
        make.getAlertLabel().setBounds(90, 300, 200, 20);
        componetsPanel.add(make.getAlertLabel());

        // 6.model
        model.getInputTextField().setColumns(10);
        model.getInputTextField().setBounds(90, 380, 110, 20);
        componetsPanel.add(model.getInputTextField());

        model.getFieldLabel().setHorizontalAlignment(SwingConstants.LEFT);
        model.getFieldLabel().setBounds(20, 380, 70, 20);
        componetsPanel.add(model.getFieldLabel());

        model.getAlertLabel().setHorizontalAlignment(SwingConstants.LEFT);
        model.getAlertLabel().setBounds(90, 360, 200, 20);
        componetsPanel.add(model.getAlertLabel());

        // 7.trim
        trim.getInputTextField().setColumns(10);
        trim.getInputTextField().setBounds(315, 260, 110, 20);
        componetsPanel.add(trim.getInputTextField());

        trim.getFieldLabel().setHorizontalAlignment(SwingConstants.LEFT);
        trim.getFieldLabel().setBounds(250, 260, 70, 20);
        componetsPanel.add(trim.getFieldLabel());

        trim.getAlertLabel().setHorizontalAlignment(SwingConstants.LEFT);
        trim.getAlertLabel().setBounds(315, 240, 200, 20);
        componetsPanel.add(trim.getAlertLabel());

        // 8.type
        type.getInputTextField().setColumns(10);
        type.getInputTextField().setBounds(315, 320, 110, 20);
        componetsPanel.add(type.getInputTextField());

        type.getFieldLabel().setHorizontalAlignment(SwingConstants.LEFT);
        type.getFieldLabel().setBounds(250, 320, 70, 20);
        componetsPanel.add(type.getFieldLabel());

        type.getAlertLabel().setHorizontalAlignment(SwingConstants.LEFT);
        type.getAlertLabel().setBounds(315, 300, 200, 20);
        componetsPanel.add(type.getAlertLabel());

        // 9.price
        price.getInputTextField().setColumns(10);
        price.getInputTextField().setBounds(315, 380, 110, 20);
        componetsPanel.add(price.getInputTextField());

        price.getFieldLabel().setHorizontalAlignment(SwingConstants.LEFT);
        price.getFieldLabel().setBounds(250, 380, 70, 20);
        componetsPanel.add(price.getFieldLabel());

        price.getAlertLabel().setHorizontalAlignment(SwingConstants.LEFT);
        price.getAlertLabel().setBounds(315, 360, 200, 20);
        componetsPanel.add(price.getAlertLabel());
        this.add(componetsPanel);
    }

    private void makeThisVisible() {
        this.setSize(500, 520);
        this.setTitle("Vehicle Details");
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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

    private void addHotKeyListeners() {
        cancelButton.setMnemonic(KeyEvent.VK_A);
        cancelButton.setToolTipText("Alt + A");
        clearButton.setMnemonic(KeyEvent.VK_C);
        clearButton.setToolTipText("Alt + C");
        saveButton.setMnemonic(KeyEvent.VK_S);
        saveButton.setToolTipText("Alt + S");
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

        // photo listeners and actions by Bin Shi
        photoLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String input = JOptionPane.showInputDialog(photoLabel, "Enter the URL of the photo:", null);
                if (input != null && !input.isEmpty()) {
                    try {
                        URL url = new URL(input.trim());
                        loadPhoto(url);
                    } catch (MalformedURLException e1) {
                        System.out.println("Entered invalid URL:"+input);
                        if(PropertyManager.getProperty("debug").equalsIgnoreCase("true")) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });

    }

    // VIDListener & VIDVerifier
    private class VIDListener implements KeyListener {
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.isMetaDown()){
                if(e.getKeyCode() == KeyEvent.VK_C){
                    id.setTrue();
                    return;
                }
                return;
            }
        }
        @Override
        public void keyReleased(KeyEvent e) {
        }

        @Override
        public void keyTyped(KeyEvent e) {
            int keyInput = e.getKeyChar();
            if (keyInput != KeyEvent.VK_ENTER && keyInput != KeyEvent.VK_BACK_SPACE
                    && (keyInput < KeyEvent.VK_0 || keyInput > KeyEvent.VK_9)) {
                id.setFalse();
                e.consume();// invalid numeric input will be eliminated
            }
            String str = id.getInputTextField().getText();
            if (keyInput == KeyEvent.VK_ENTER) {// enter
                if (str.length() != 10)
                    id.setFalse();
                else
                    id.setTrue();
            }
            if (keyInput == KeyEvent.VK_BACK_SPACE) {// backspace
                if (str.length() < 10) {
                    id.setTrue();
                }
            }
            if (keyInput >= KeyEvent.VK_0 && keyInput <= KeyEvent.VK_9) {// number
                if (str.length() < 10)
                    id.setTrue();
                else {
                    id.setFalse();
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
                id.setFalse();
                VIDSuccessOrNot = false;
                return false;
            } else {
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

    // PriceListener & PriceVerifier
    private class PriceListener implements KeyListener {
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.isMetaDown()){
                if(e.getKeyCode() == KeyEvent.VK_C){
                    price.setTrue();
                    return;
                }
                return;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

        @Override
        public void keyTyped(KeyEvent e) {
            int keyInput = e.getKeyChar();
            if (keyInput != KeyEvent.VK_ENTER && keyInput != KeyEvent.VK_BACK_SPACE
                    && (keyInput < KeyEvent.VK_0 || keyInput > KeyEvent.VK_9)) {
                price.setFalse();
                e.consume();
            }
            String str = price.getInputTextField().getText();
            if (keyInput == KeyEvent.VK_ENTER) {
                if (str.equals(""))
                    price.setFalse();
                else
                    price.setTrue();
            }
            if (keyInput == KeyEvent.VK_BACK_SPACE) {
                price.setTrue();
            }
            if (keyInput >= KeyEvent.VK_0 && keyInput <= KeyEvent.VK_9) {
                price.setTrue();
            }
        }
    }

    private boolean PriceSuccessOrNot;

    private class PriceVerifier extends InputVerifier {
        @Override
        public boolean verify(JComponent input) {
            String str = ((JTextField) input).getText();
            if (!str.equals("")) {
                price.setTrue();
                PriceSuccessOrNot = true;
                return true;
            } else {
                price.setFalse();
                PriceSuccessOrNot = false;
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
                webId.setFalse();
                e.consume();
            } //invalid input are those which exclude enter, backspace,letters and -
            String str = webId.getInputTextField().getText();
            int lastIndex = str.length() - 1;
            if (keyInput == KeyEvent.VK_MINUS) {
                if (str.equals("")) {
                    webId.setFalse();//- appears at the first position
                }
            }
            if (keyInput == KeyEvent.VK_ENTER) {
                if (str.contains("-") && str.charAt(lastIndex) != '-')//cannot end by -
                    webId.setTrue();
                else
                    webId.setFalse();
            }
            if (keyInput == KeyEvent.VK_BACK_SPACE) {
                webId.setTrue();
            }
            if ((keyInput >= 65 && keyInput <= 90) || (keyInput >= 97 && keyInput <= 122)) {
                webId.setTrue();// valid letter input
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if(e.isMetaDown()){
                if(e.getKeyCode() == KeyEvent.VK_C){
                    webId.setTrue();
                    return;
                }
                return;
            }
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
                webId.setTrue();
                WebIDSuccessOrNot = true;
                return true;
            } else {
                webId.setFalse();
                WebIDSuccessOrNot = false;
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
                category.setTrue();
            } else {
                category.setFalse();
                e.consume();
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if(e.isMetaDown()){
                if(e.getKeyCode() == KeyEvent.VK_C){
                    category.setTrue();
                    return;
                }
                return;
            }
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
            if (str.equals("new") || str.equals("used") || str.equals("certified") || str.equals("NEW")
                    || str.equals("USED") || str.equals("CERTIFIED")) {
                category.setTrue();
                CategorySuccessOrNot = true;
                return true;
            } else {
                category.setFalse();
                CategorySuccessOrNot = false;
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
                year.setFalse();
                e.consume();
            }
            String str = year.getInputTextField().getText();
            if (keyInput == KeyEvent.VK_ENTER) {// enter
                if (str.length() != 4)
                    year.setFalse();
                else
                    year.setTrue();
            }
            if (keyInput == KeyEvent.VK_BACK_SPACE) {// backspace
                if (str.length() < 4) {
                    year.setTrue();
                }
            }
            if (keyInput >= KeyEvent.VK_0 && keyInput <= KeyEvent.VK_9) {// number
                if (str.length() < 4)
                    year.setTrue();
                else {
                    year.setFalse();
                    e.consume();
                }
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if(e.isMetaDown()){
                if(e.getKeyCode() == KeyEvent.VK_C){
                    year.setTrue();
                    return;
                }
                return;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }

    private boolean YearSuccessOrNot;

    private class YearVerifier extends InputVerifier {
        public boolean verify(JComponent input) {
            String str = ((JTextField) input).getText();
            if (str.length() == 4) {
                year.setTrue();
                YearSuccessOrNot = true;
                return true;
            } else {
                year.setFalse();
                YearSuccessOrNot = false;
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
                make.setFalse();
                e.consume();
            }
            String str = make.getInputTextField().getText();
            if (keyInput == KeyEvent.VK_ENTER) {
                if (str.equals("") || str.equals(null))
                    make.setFalse();
                else
                    make.setTrue();
            }
            if (keyInput == KeyEvent.VK_BACK_SPACE) {
                if (str.equals("") || str.equals(null))
                    make.setFalse();
                else
                    make.setTrue();
            }
            if ((keyInput >= 65 && keyInput <= 90) || (keyInput >= 97 && keyInput <= 122)) {
                make.setTrue();// valid letter input
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if(e.isMetaDown()){
                if(e.getKeyCode() == KeyEvent.VK_C){
                    make.setTrue();
                    return;
                }
                return;
            }
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
                make.setFalse();
                MakeSuccessOrNot = false;
                return false;
            } else {
                make.setTrue();
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
                type.setFalse();
                e.consume();
            }
            String str = make.getInputTextField().getText();
            if (keyInput == KeyEvent.VK_ENTER) {
                if (str.equals("") || str.equals(null))
                    type.setFalse();
                else
                    type.setTrue();
            }
            //
            if (keyInput == KeyEvent.VK_BACK_SPACE) {
                if (str.equals("") || str.equals(null))
                    type.setFalse();
                else
                    type.setTrue();
            }
            if ((keyInput >= 65 && keyInput <= 90) || (keyInput >= 97 && keyInput <= 122)) {
                type.setTrue();
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if(e.isMetaDown()){
                if(e.getKeyCode() == KeyEvent.VK_C){
                    type.setTrue();
                    return;
                }
                return;
            }
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
                type.setFalse();
                TypeSuccessOrNot = false;
                return false;
            } else {
                type.setTrue();
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

    private class ModelVerifier extends InputVerifier {
        @Override
        public boolean verify(JComponent input) {
            String str = ((JTextField) input).getText();
            if (str.equals("") || str.equals(null)) {
                model.setFalse();
                ModelSuccessOrNot = false;
                return false;
            } else {
                model.setTrue();
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
                trim.setFalse();
                TrimSuccessOrNot = false;
                return false;
            } else {
                trim.setTrue();
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
    
    public boolean canSave(){
        if (VIDSuccessOrNot && PriceSuccessOrNot && WebIDSuccessOrNot && CategorySuccessOrNot && YearSuccessOrNot
                && MakeSuccessOrNot && TypeSuccessOrNot && ModelSuccessOrNot && TrimSuccessOrNot)
            return true;
        else
            return false;
    }

    private String[] categories = { "NEW", "USED", "CERTIFIED" };
    private String[] makes = { "All Make", "Acura", "Aston Martin", "Audi", "Bentley", "BMW", "Bugatti", "Buick",
            "Chrysler", "Citroen", "Dodge", "Ferrari", "Fiat", "Ford", "Geely", "General Motors", "GMC", "Honda" };
    private String[] types = { "Luxury", " Sedans", "Coupes", "SUVs", "Crossovers", "Wagons/Hatchbacks", "Hybrids",
            "Convertibles", "Sports Cars", "Pickup Trucks", "Minivans/Vans" };

    private void validateTextFields() {
        webId.getInputTextField().requestFocusInWindow();
        year.getInputTextField().requestFocusInWindow();
        make.getInputTextField().requestFocusInWindow();
        model.getInputTextField().requestFocusInWindow();
        trim.getInputTextField().requestFocusInWindow();
        type.getInputTextField().requestFocusInWindow();
        price.getInputTextField().requestFocusInWindow();
        category.getInputTextField().requestFocusInWindow();
        id.getInputTextField().requestFocusInWindow();
    }

    private VehicleImple service = new VehicleImple();

    private Vehicle vehicle;
    private InventoryListUI listUI;

    private void setupAutoCompletes() {
        setupAutoComplete(category.getInputTextField(), new ArrayList<String>(Arrays.asList(categories)));
        setupAutoComplete(make.getInputTextField(), new ArrayList<String>(Arrays.asList(makes)));
        setupAutoComplete(type.getInputTextField(), new ArrayList<String>(Arrays.asList(types)));
    }

    private void loadVehicle(Vehicle v) {
        if (v == null) {
            return;
        }
        
        vehicle = v;
        id.getInputTextField().setText(String.valueOf(vehicle.getID()));
        webId.getInputTextField().setText(String.valueOf(vehicle.getWebID()));
        webId.getInputTextField().setCaretPosition(0);
        category.getInputTextField().setText(String.valueOf(vehicle.getCategory()));
        category.getInputTextField().requestFocusInWindow();
        year.getInputTextField().setText(String.valueOf(vehicle.getYear()));
        make.getInputTextField().setText(String.valueOf(vehicle.getMake()));
        model.getInputTextField().setText(String.valueOf(vehicle.getModel()));
        trim.getInputTextField().setText(String.valueOf(vehicle.getTrim()));
        trim.getInputTextField().setCaretPosition(0);
        type.getInputTextField().setText(String.valueOf(vehicle.getTrim()));
        type.getInputTextField().setCaretPosition(0);
        price.getInputTextField().setText(String.valueOf(vehicle.getPrice()));

        //later load photo to avoid blocking UI by Bin Shi
        SwingUtilities.invokeLater(() -> {
            loadPhoto(vehicle.getPhotoURL());
        });
    }
    
    // load the photo by Bin Shi
    private void loadPhoto(URL photoURL) {
        boolean noPhoto = true;
        if (photoURL != null) {
            Image image = vehicle.getPhoto();
            if (image != null) {
                ImageIcon icon = new ImageIcon(image);
                if (icon != null){
                    photoLabel.setIcon(icon);
                    noPhoto = false;
                }
            }
        }
        if(noPhoto) photoLabel.setText("No Photo");
        else photoLabel.setText(photoURL.toString());
    }
  
    public boolean saveVehicle(Vehicle vehicle) throws MalformedURLException {
        this.validateTextFields();
        if (canSave()) {
            Vehicle v = new Vehicle();
            String idText = this.id.getInputTextField().getText();
            String webIdText = this.webId.getInputTextField().getText();
            v.setID(idText);
            v.setWebID(webIdText);
            v.setCategory(Category.valueOf(this.category.getInputTextField().getText().toUpperCase()));
            v.setYear(Integer.valueOf(this.year.getInputTextField().getText()));
            v.setMake(this.make.getInputTextField().getText());
            v.setModel(this.model.getInputTextField().getText());
            v.setTrim(this.trim.getInputTextField().getText());
            v.setBodyType(this.type.getInputTextField().getText());
            v.setPrice(Float.parseFloat(this.price.getInputTextField().getText()));
            URL url;
            try {
                // This will throw exception in case of no photo.
                url = new URL(photoLabel.getText());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                url = new URL("https://vignette.wikia.nocookie.net/arthur/images/a/a7/No_Image.jpg");
            }
            
            v.setPhotoURL(url);
            v.setSortingField("sF");
            if(vehicle != null) {
                v.setEntertainment(vehicle.getEntertainment());
                v.setExteriorColor(vehicle.getExteriorColor());
                v.setBattery(vehicle.getBattery());
                v.setEngine(vehicle.getEngine());
                v.setFuelType(vehicle.getFuelType());
                v.setInteriorColor(vehicle.getInteriorColor());
                v.setOptionalFeatures(vehicle.getOptionalFeatures());
                v.setTransmission(vehicle.getTransmission());
                v.setVin(vehicle.getVin());               
            } else {
                v.setEntertainment(" ");
                v.setExteriorColor(" ");
                v.setBattery(" ");
                v.setEngine(" ");
                v.setFuelType(" ");
                v.setInteriorColor(" ");
                v.setOptionalFeatures(" ");
                v.setTransmission(" ");
                v.setVin(" "); 
            }

            boolean creatingNewVehicle = false;
            if (vehicle == null || vehicle.getID() == null || vehicle.getWebID() == null) {
                creatingNewVehicle = true;
            } else if (!idText.equalsIgnoreCase(vehicle.getID())
                    || !webIdText.equalsIgnoreCase(vehicle.getWebID())) {

                service.deleteVehicle(vehicle.getWebID(), vehicle.getID());
                creatingNewVehicle = true;
            }
            
            boolean result = creatingNewVehicle ? 
                    service.addVehicle(v.getWebID(), v)
                    : service.updateVehicle(v.getWebID(), v);
                    
            if (!result) {
                JOptionPane.showMessageDialog(null, "Failed to save, please verify your input.");
                return false;
            }
                    
            if (listUI != null) {
                listUI.refreshTable(v);
            }

            return true;
        }

        return false;
    }
}
