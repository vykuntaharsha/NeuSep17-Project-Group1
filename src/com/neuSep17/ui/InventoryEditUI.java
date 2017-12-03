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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.neuSep17.dao.VehicleImple;
import com.neuSep17.dto.Category;
import com.neuSep17.dto.Vehicle;

//import com.neuSep17.ui.InventoryManagementFrame.ClearAllAction;
//import com.neuSep17.ui.InventoryManagementFrame.Component;

@SuppressWarnings("serial")
public class InventoryEditUI extends JFrame {
    private Component id, webId, category, year, make, model, trim, type, price;
    private JButton saveButton;
    private JButton clearButton;
    private JButton cancelButton;

    // for testing purpose. will delete when delivery
    public static void main(String[] args) {
        InventoryEditUI imf = new InventoryEditUI();
    }

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

    public InventoryEditUI() {
        super();
        createCompoments();
        createPanel();
        addListeners();
        // String wid="8938273", vid="234";
        // loadVehicle(wid, vid);
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
        id.getInputTextField().setToolTipText("123");
        saveButton = new JButton("Save");
        clearButton = new JButton("Clear");
        cancelButton = new JButton("Cancel");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(null, "Confirm?") == JOptionPane.OK_OPTION)
                    ;// save method;
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
        componetsPanel.setBackground(Color.WHITE);

        JTextField photoTextField = new JTextField("photo");
        JLabel lineLabel = new JLabel("   Vehicle Details");
        JTextField lineGraph = new JTextField();

        // photo
        photoTextField.setBounds(315, 50, 100, 100);
        photoTextField.setHorizontalAlignment(SwingConstants.CENTER);
        photoTextField.setBackground(Color.lightGray);
        componetsPanel.add(photoTextField);

        // save,cancel,clear

        cancelButton.setBounds(10, 440, 117, 29);
        componetsPanel.add(cancelButton);
        clearButton.setBounds(180, 440, 117, 29);
        componetsPanel.add(clearButton);
        saveButton.setBounds(350, 440, 117, 29);
        componetsPanel.add(saveButton);

        // line
        lineLabel.setBounds(0, 5, 500, 20);
        lineLabel.setOpaque(true);
        lineLabel.setBackground(Color.lightGray);
        componetsPanel.add(lineLabel);
        lineGraph.setBounds(0, 225, 500, 5);
        lineGraph.setBackground(Color.lightGray);
        componetsPanel.add(lineGraph);

        // 1.id
        id.getInputTextField().setBounds(90, 50, 110, 20);
        id.getInputTextField().setBackground(null);
        componetsPanel.add(id.getInputTextField());
        id.getInputTextField().setColumns(10);

        id.getFieldLabel().setHorizontalAlignment(SwingConstants.LEFT);
        id.getFieldLabel().setBounds(20, 50, 70, 20);
        componetsPanel.add(id.getFieldLabel());

        id.getAlertLabel().setHorizontalAlignment(SwingConstants.LEFT);
        id.getAlertLabel().setBounds(90, 70, 200, 20);
        componetsPanel.add(id.getAlertLabel());

        // 2.webId
        webId.getInputTextField().setColumns(10);
        webId.getInputTextField().setBounds(90, 110, 110, 20);
        componetsPanel.add(webId.getInputTextField());

        webId.getFieldLabel().setHorizontalAlignment(SwingConstants.LEFT);
        webId.getFieldLabel().setBounds(20, 110, 70, 20);
        componetsPanel.add(webId.getFieldLabel());

        webId.getAlertLabel().setHorizontalAlignment(SwingConstants.LEFT);
        webId.getAlertLabel().setBounds(90, 130, 200, 20);
        componetsPanel.add(webId.getAlertLabel());

        // 3.category
        category.getInputTextField().setColumns(10);
        category.getInputTextField().setBounds(90, 170, 110, 20);
        componetsPanel.add(category.getInputTextField());

        category.getFieldLabel().setHorizontalAlignment(SwingConstants.LEFT);
        category.getFieldLabel().setBounds(20, 170, 70, 20);
        componetsPanel.add(category.getFieldLabel());

        category.getAlertLabel().setHorizontalAlignment(SwingConstants.LEFT);
        category.getAlertLabel().setBounds(90, 190, 200, 20);
        componetsPanel.add(category.getAlertLabel());

        // 4.year
        year.getInputTextField().setColumns(10);
        year.getInputTextField().setBounds(90, 250, 110, 20);
        componetsPanel.add(year.getInputTextField());

        year.getFieldLabel().setHorizontalAlignment(SwingConstants.LEFT);
        year.getFieldLabel().setBounds(20, 250, 70, 20);
        componetsPanel.add(year.getFieldLabel());

        year.getAlertLabel().setHorizontalAlignment(SwingConstants.LEFT);
        year.getAlertLabel().setBounds(90, 270, 200, 20);
        componetsPanel.add(year.getAlertLabel());

        // 5.make
        make.getInputTextField().setColumns(10);
        make.getInputTextField().setBounds(90, 310, 110, 20);
        componetsPanel.add(make.getInputTextField());

        make.getFieldLabel().setHorizontalAlignment(SwingConstants.LEFT);
        make.getFieldLabel().setBounds(20, 310, 70, 20);
        componetsPanel.add(make.getFieldLabel());

        make.getAlertLabel().setHorizontalAlignment(SwingConstants.LEFT);
        make.getAlertLabel().setBounds(90, 330, 200, 20);
        componetsPanel.add(make.getAlertLabel());

        // 6.model
        model.getInputTextField().setColumns(10);
        model.getInputTextField().setBounds(90, 370, 110, 20);
        componetsPanel.add(model.getInputTextField());

        model.getFieldLabel().setHorizontalAlignment(SwingConstants.LEFT);
        model.getFieldLabel().setBounds(20, 370, 70, 20);
        componetsPanel.add(model.getFieldLabel());

        model.getAlertLabel().setHorizontalAlignment(SwingConstants.LEFT);
        model.getAlertLabel().setBounds(90, 400, 200, 20);
        componetsPanel.add(model.getAlertLabel());

        // 7.make
        trim.getInputTextField().setColumns(10);
        trim.getInputTextField().setBounds(315, 250, 110, 20);
        componetsPanel.add(trim.getInputTextField());

        trim.getFieldLabel().setHorizontalAlignment(SwingConstants.LEFT);
        trim.getFieldLabel().setBounds(250, 250, 70, 20);
        componetsPanel.add(trim.getFieldLabel());

        trim.getAlertLabel().setHorizontalAlignment(SwingConstants.LEFT);
        trim.getAlertLabel().setBounds(315, 270, 200, 20);
        componetsPanel.add(trim.getAlertLabel());

        // 8.type
        type.getInputTextField().setColumns(10);
        type.getInputTextField().setBounds(315, 310, 110, 20);
        componetsPanel.add(type.getInputTextField());

        type.getFieldLabel().setHorizontalAlignment(SwingConstants.LEFT);
        type.getFieldLabel().setBounds(250, 310, 70, 20);
        componetsPanel.add(type.getFieldLabel());

        type.getAlertLabel().setHorizontalAlignment(SwingConstants.LEFT);
        type.getAlertLabel().setBounds(315, 330, 200, 20);
        componetsPanel.add(type.getAlertLabel());

        // 9.price
        price.getInputTextField().setColumns(10);
        price.getInputTextField().setBounds(315, 370, 110, 20);
        componetsPanel.add(price.getInputTextField());

        price.getFieldLabel().setHorizontalAlignment(SwingConstants.LEFT);
        price.getFieldLabel().setBounds(250, 370, 70, 20);
        componetsPanel.add(price.getFieldLabel());

        price.getAlertLabel().setHorizontalAlignment(SwingConstants.LEFT);
        price.getAlertLabel().setBounds(315, 400, 200, 20);
        componetsPanel.add(price.getAlertLabel());

        this.add(componetsPanel);
    }

    private void makeThisVisible() {
        this.setSize(500, 520);
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
            } // invalid input:杈撳叆闄や簡鍥炶溅鍒犻櫎鍜屽ぇ灏忓啓瀛楁瘝浠ュ強妯嚎浠ュ鐨�
            String str = webId.getInputTextField().getText();
            int lastIndex = str.length() - 1;
            if (keyInput == KeyEvent.VK_MINUS) {
                if (str.equals("")) {
                    webId.setFalse();// 棣栦綅鍑虹幇妯嚎
                }
            }
            if (keyInput == KeyEvent.VK_ENTER) {
                if (str.contains("-") && str.charAt(lastIndex) != '-')// 涓嶈兘鏈熬涓�-
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

    private String[] categories = { "new", "used", "certified" };
    private String[] makes = { "All Make", "Acura", "Aston Martin", "Audi", "Bentley", "BMW", "Bugatti", "Buick",
            "Chrysler", "Citroen", "Dodge", "Ferrari", "Fiat", "Ford", "Geely", "General Motors", "GMC", "Honda" };
    private String[] types = { "Luxury", " Sedans", "Coupes", "SUVs", "Crossovers", "Wagons/Hatchbacks", "Hybrids",
            "Convertibles", "Sports Cars", "Pickup Trucks", "Minivans/Vans" };

    private VehicleImple service = new VehicleImple();
    Vehicle vehicle;

    private void setupAutoCompletes() {
        setupAutoComplete(category.getInputTextField(), new ArrayList<String>(Arrays.asList(categories)));
        setupAutoComplete(make.getInputTextField(), new ArrayList<String>(Arrays.asList(makes)));
        setupAutoComplete(type.getInputTextField(), new ArrayList<String>(Arrays.asList(types)));
    }

    private void loadVehicle(String webId, String id) {
        vehicle = service.getAVehicle(webId, id);
        this.id.getInputTextField().setText(String.valueOf(vehicle.getID()));
        this.webId.getInputTextField().setText(String.valueOf(vehicle.getWebID()));
        this.category.getInputTextField().setText(String.valueOf(vehicle.getCategory()));
        this.year.getInputTextField().setText(String.valueOf(vehicle.getYear()));
        this.make.getInputTextField().setText(String.valueOf(vehicle.getMake()));
        this.model.getInputTextField().setText(String.valueOf(vehicle.getModel()));
        this.trim.getInputTextField().setText(String.valueOf(vehicle.getTrim()));
        this.type.getInputTextField().setText(String.valueOf(vehicle.getTrim()));
        this.price.getInputTextField().setText(String.valueOf(vehicle.getPrice()));
    }

    // public boolean saveVehicle(String oldVID) {
    // if (VIDSuccessOrNot && PriceSuccessOrNot && WebIDSuccessOrNot &&
    // CategorySuccessOrNot && YearSuccessOrNot
    // && MakeSuccessOrNot && TypeSuccessOrNot && ModelSuccessOrNot &&
    // TrimSuccessOrNot) {
    //
    // Vehicle v = new Vehicle();
    // if (this.id.getInputTextField().getText() != vehicle.id
    // || webId.getInputTextField().getText() != vehicle.webId) {
    // service.deleteVehicle(vehicle.webId, vehicle.id);
    //
    // v.setID(this.id.getInputTextField().getText());
    // v.setWebID(this.webId.getInputTextField().getText());
    // v.setCategory(Category.valueOf(this.category.getInputTextField().getText()));
    // v.setYear(Integer.valueOf(this.year.getInputTextField().getText()));
    // v.setMake(this.make.getInputTextField().getText());
    // v.setModle(this.model.getInputTextField().getText());
    // v.setTrim(this.trim.getInputTextField().getText());
    // v.setBodyType(this.type.getInputTextField().getText());
    // v.setPrice(Float.parseFloat(this.price.getInputTextField().getText()));
    //
    // service.addVehicle(this.webId, v);
    // }
    //
    // return service.updateVehicle(this.webId, v.id, v);
    // }
    //
    // return false;
    // }

}
