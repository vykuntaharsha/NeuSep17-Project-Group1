package com.neuSep17.ui;

import com.neuSep17.dto.Incentive;
import com.neuSep17.service.IncentiveService;
import com.neuSep17.validation.IncentiveScreenvalidation;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Random;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class IncentiveUtilities extends JFrame implements Comparable<IncentiveUtilities> {
    private String ID;
    private String dealerID;
    private String title;
    private String startDate; //format should be "YYYY-MM-DD"
    private String endDate;//format should be "YYYY-MM-DD"
    private String description;
    private double cashValue;

    //NOTE: convert this to String using + sign as delimiter between criteria before save to the file
    private  ArrayList<String> discountCriteria = new ArrayList<>();


    //Sorting field
    private String sortingField;

    public IncentiveUtilities() {}

    public IncentiveUtilities(String ID, String dealerID, String title, String startDate, String endDate, String description,
                              double cashValue, ArrayList<String> discountCriteria) {
        this.ID =ID; this.dealerID = dealerID; this.title =title; this.startDate=startDate;
        this.endDate=endDate; this.description=description; this.cashValue=cashValue;
        this.discountCriteria=discountCriteria;
    }

    public void updateIncentive(Incentive incentive) {
        this.ID = incentive.getID();
        this.dealerID = incentive.getDealerID();
        this.title = incentive.getTitle();
        this.startDate = incentive.getStartDate();
        this.endDate = incentive.getEndDate();
        this.description = incentive.getDescription();
        this.cashValue = incentive.getCashValue();
        this.discountCriteria = incentive.getDiscountCriteria();
    }

    public void setID(String id) {
        ID=id;
    }
    public void setDealerID(String dealerID) { this.dealerID = dealerID; }
    public void setTitle(String t) {
        title=t;
    }
    public void setStartDate(String sD) {
        startDate =sD;
    }
    public void setEndDate(String eD) {
        endDate =eD;
    }
    public void setDescription(String d) {
        description=d;
    }
    public void setCashValue(float cV) {
        cashValue=cV;
    }
    public void setDiscountCriteria(ArrayList<String> dC) {
        discountCriteria=dC;
    }

    public void setSortingField(String sortField) {
        sortingField = sortField;
    }

    public String getID() {
        return ID;
    }
    public String getDealerID() { return dealerID; }
    public String getTitle() {
        return title;
    }
    public String getStartDate() {
        return startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public String getDescription() {
        return description;
    }
    public double getCashValue() {
        return cashValue;
    }
    public ArrayList<String> getDiscountCriteria() {
        return discountCriteria;
    }



    //Call this  method to sort --Nhat T.
    public void sortBy(ArrayList<IncentiveUtilities> incentiveUtilities, String sortingField) {

        for(IncentiveUtilities utilities : incentiveUtilities) {
            utilities.setSortingField(sortingField);
        }
        Collections.sort(incentiveUtilities);
    }

    //override this method from the Comparable interface --Nhat T.
    @Override
    public int compareTo(IncentiveUtilities v) {
        String s1 = getValueToSort(this);
        String s2 = getValueToSort(v);
        String decimalPattern = "([0-9]*)\\.([0-9]*)";
        String datePattern ="([0-9]{2})-([0-9]{2})-([0-9]{4})";

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");

        if(Pattern.matches(decimalPattern, s1)) {
            return (int) (Double.parseDouble(s1) - Double.parseDouble(s2));
        }
        else if(s1.matches(datePattern)) {//this is either start or end date
            Date firstDate = null;
            Date secondDate = null;
            try {
                firstDate = sdf.parse(s1);
                secondDate = sdf.parse(s2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return firstDate.compareTo(secondDate);
        }

        else {
            return s1.compareToIgnoreCase(s2);
        }
    }

    //Used by the CompareTo method --Nhat T.
    private String getValueToSort(IncentiveUtilities c) {
        String valueToCompare="";
        Field[] fields = c.getClass().getDeclaredFields();

        for(Field f :fields) {// check fields in the class against the sorting field

            if(f.getName().equalsIgnoreCase(sortingField)) {
                f.setAccessible(true); //enable field accessible

                try {//get field value to compare

                    if(f.getType() ==double.class) {
                        valueToCompare = new Double((Double)f.get(c)).toString();
                    }

                    else {
                        valueToCompare= (String)f.get(c);
                    }
                }
                catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return valueToCompare;
    }


    @Override
    public String toString(){
        //create a string array to store criteria
        StringBuilder sb = new StringBuilder();
        //for loop to form the stringBuilder
        for(String s : discountCriteria){
            sb.append(s);
            sb.append("+");
        }
        sb.deleteCharAt(sb.length()-1); //delete the last + sign

        return String.format("%s~%s~%s~%s~%s~%s~%.2f~%s\n",this.ID, this.dealerID, this.title, this.startDate, this.endDate, this.description, this.getCashValue(),sb.toString());
    }






    //=============================addIncentiveScreen,EditIncentiveScreen,DeleteIncentiveScreen=======================================

    private String disclaimer;

    //discount criteria
    private int year;
    private String make;
    private String model;
    private double price;
    private double mileage;

    private String category;
    private String color;
    private String types;
    private String trim;

    //to generate id
    private final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public String getDisclaimer() {
        return disclaimer;
    }
    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public String getMake() {
        return make;
    }
    public void setMake(String make) {
        this.make = make;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public double getMileage() {
        return mileage;
    }
    public void setMileage(double mileage) {
        this.mileage = mileage;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public String getTypes() {
        return types;
    }
    public void setTypes(String types) {
        this.types = types;
    }
    public String getTrim() {
        return trim;
    }
    public void setTrim(String trim) {
        this.trim = trim;
    }

    UI ui = new UI();
    IncentiveScreenvalidation v = new IncentiveScreenvalidation();
    //        FileWriting fw = new FileWriting();
    //DealerScreen d = new DealerScreen();                 //wait for implementation of dealerScreen
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel addIncentiveLabel;
    private JLabel titleLabel;
    private JLabel applyToInventoryLabel;
    private JTextField titleTextField;
    private JLabel categoryLabel;
    private JComboBox<String> categoryComboBox;
    private JLabel label1;
    private JLabel yearLabel;
    private JTextField yearTextField;
    private JLabel startDateLabel;
    private JLabel makeLabel;
    private JTextField makeTextField;
    private JTextField textField1;
    private JLabel modelLabel;
    private JTextField modelTextField;
    private JLabel endDateLabel;
    private JLabel priceLabel;
    private JTextField priceTextField;
    private JTextField endDateTextField;
    private JLabel mileageLabel;
    private JTextField mileageTextField;
    private JLabel colorLabel;
    private JComboBox<String> colorComboBox;
    private JLabel discountLabel;
    private JLabel typeLabel;
    private JComboBox<String> typeComboBox;
    private JTextField discountTextField;
    private JLabel trimLabel;
    private JComboBox<String> comboBox1;
    private JLabel descriptionLabel;
    private JTextField descriptionTextField;
    private JLabel label2;
    private JTextField disclaimerTextField;
    private JPanel buttonBar;
    private JButton applyButton;
    private JLabel idLabel;
    private JTextField idTextField;

    Font small = new Font("Arial",Font.PLAIN, 14);
    Font big = new Font("Arial", Font.BOLD, 16);
    Font comboBoxFont = new Font("Arial", Font.PLAIN, 12);

    //addIncentiveScreen
    public void addIncentives() {

        createComponents("add");

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                setFont("add");
                addComponentsAndSetLayoutInAddScreen();
                setButtonBar();
            }
            addPanelAToB(contentPanel, dialogPane, BorderLayout.CENTER);
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            addPanelAToB(buttonBar, dialogPane, BorderLayout.SOUTH);
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());

        makeItVisible();
    }

    //EditIncentiveScreen
    public void EditIncentives(IncentiveUtilities i) {
        createComponents("edit");

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                setFont("edit");
                addComponentsAndSetLayoutInEditScreen();
                setButtonBar();
                showIncentive(i);
            }
            addPanelAToB(contentPanel, dialogPane, BorderLayout.CENTER);
            //dialogPane.add(contentPanel, BorderLayout.CENTER);

            addPanelAToB(buttonBar, dialogPane, BorderLayout.SOUTH);
            //dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());

        makeItVisible();
    }
    //display the incentive which you are going to edit in editScreen
    private void showIncentive(IncentiveUtilities i) {
        idTextField.setEditable(true);
        idTextField.setText(i.getID());
        idTextField.setEditable(false);
        titleTextField.setText(i.getTitle());
        textField1.setText(i.getStartDate());
        endDateTextField.setText(i.getEndDate());
        discountTextField.setText(Double.toString(i.getCashValue()));
        descriptionTextField.setText(i.getDescription());
        disclaimerTextField.setText(i.getDisclaimer());
        yearTextField.setText(Integer.toString(i.getYear()));
        makeTextField.setText(i.getMake());
        modelTextField.setText(i.getModel());
        priceTextField.setText(Double.toString(i.getPrice()));
        mileageTextField.setText(Double.toString(i.getMileage()));

        //comboBox
        categoryComboBox.setSelectedItem(i.getCategory());
        colorComboBox.setSelectedItem(i.getColor());
        typeComboBox.setSelectedItem(i.getTypes());
        comboBox1.setSelectedItem(i.getTrim());
    }

    //add one panel into another
    private void addPanelAToB(JPanel a, JPanel b, String layout) {
        b.add(a,layout);
    }

    private void createComponents(String str) {
        dialogPane = new JPanel();
        contentPanel = new JPanel();

        if(str.equals("edit")) {
            idLabel = ui.createLabel("ID");
            idTextField = ui.createText("");
            idTextField.setEditable(false);
        }

        addIncentiveLabel = ui.createLabel("Edit Incentives");
        applyToInventoryLabel = ui.createLabel("Apply To Inventory");

        titleLabel = ui.createLabel("Title");
        titleTextField = ui.createText("");

        categoryLabel = ui.createLabel("Category");
        categoryComboBox = ui.createComboBox(new String[] {"All","New","Certified Pre-Owned","Used"});

        label1 = ui.createLabel("When does this incentive start and end?");
        yearLabel = ui.createLabel("Year");
        yearTextField = ui.createText("");
        startDateLabel = ui.createLabel("Start Date");

        makeLabel = ui.createLabel("Make");
        makeTextField = ui.createText("");

        textField1 = ui.createText("");
        textField1.setToolTipText("Date should be in the format of 'YYYY-MM-DD'");

        modelLabel = ui.createLabel("Model");
        modelTextField = ui.createText("");

        endDateLabel = ui.createLabel("End Date");

        priceLabel = ui.createLabel("Price");
        priceTextField = ui.createText("");

        endDateTextField = ui.createText("");
        endDateTextField.setToolTipText("Date should be in the format of 'YYYY-MM-DD'");

        mileageLabel = ui.createLabel("Mileage");
        mileageTextField = ui.createText("");

        colorLabel = ui.createLabel("Color");
        colorComboBox = ui.createComboBox(new String[] {"All","Black","White","Silver","Red","Blue","Grey","Other"});

        discountLabel = ui.createLabel("Discount");

        typeLabel = ui.createLabel("Type");
        typeComboBox = ui.createComboBox(new String[] {"All","Sedan","Coupe","Convertible","Hatchback","SUV","Van","Pickup Truck"});

        discountTextField = ui.createText("");
        discountTextField.setToolTipText("Maximum discount can be 9999$");

        trimLabel = ui.createLabel("Trim");
        comboBox1 = ui.createComboBox(new String[] {"All","High","Middle","Low"});

        descriptionLabel = ui.createLabel("Description");
        descriptionTextField = ui.createText("");

        label2 = ui.createLabel("Disclaimer");
        disclaimerTextField = ui.createText("");

        buttonBar = new JPanel();
        applyButton = ui.createButton("Apply");
        applyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(str.equals("edit")) {
                    applyButtonClicked(e,"edit");
                }else if(str.equals("add")) {
                    applyButtonClicked(e,"add");
                }

            }
        });
    }

    //executed when click on apply button
    private void applyButtonClicked(MouseEvent e, String str) {
        try {
            if(str.equals("edit")) {
    			IncentiveUtilities incentiveUtilities = getTextFromEditScreen();
    			Incentive incentive = new Incentive(
    			        incentiveUtilities.getID(), incentiveUtilities.getDealerID(), incentiveUtilities.getTitle(),
                        incentiveUtilities.getStartDate(), incentiveUtilities.getEndDate(), incentiveUtilities.getDescription(),
                        incentiveUtilities.getCashValue(), incentiveUtilities.getDiscountCriteria()
                );
                IncentiveService.updateAnIncentive(incentive);
            }else if(str.equals("add")) {
                IncentiveUtilities incentiveUtilities = getTextFromAddScreen();
                Incentive incentive = new Incentive(
                        incentiveUtilities.getID(), incentiveUtilities.getDealerID(), incentiveUtilities.getTitle(),
                        incentiveUtilities.getStartDate(), incentiveUtilities.getEndDate(), incentiveUtilities.getDescription(),
                        incentiveUtilities.getCashValue(), incentiveUtilities.getDiscountCriteria()
                );
                IncentiveService.addAnIncentive(incentive);
            }

        }catch(Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error: Unable to apply!", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }

    	/* For Balla's function
        IncentiveScreen is = new IncentiveScreen();
        */
        WindowEvent wev = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
        setVisible(false);
        dispose();
    }

    //get String from textFields and comboBoxs in editScreen
    private IncentiveUtilities getTextFromEditScreen() throws Exception {
        IncentiveUtilities i = new IncentiveUtilities();
        StringBuilder errorMessage = new StringBuilder();

        //get ID
        String idText = idTextField.getText();

        //get string from title
        String titleText = titleTextField.getText();
        if(!isTrue(v.ValidatetitleText(titleText))) {
            errorMessage.append(v.ValidatetitleText(titleText));
        }

        //get start date
        String startDateText = textField1.getText();
        if(!isTrue(v.ValidateStartDate(startDateText))) {
            errorMessage.append(v.ValidateStartDate(startDateText));
        }

        //get end date
        String endDateText = endDateTextField.getText();
        if(!isTrue(v.ValidateEndDate(startDateText,endDateText))) {
            errorMessage.append(v.ValidateEndDate(startDateText,endDateText));
        }

        //get discount
        String discountText = discountTextField.getText();
        if(!isTrue(v.ValidatediscountText(discountText))) {
            errorMessage.append(v.ValidatediscountText(discountText));
        }

        //get description
        String descriptionText = descriptionTextField.getText();
        if(!isTrue(v.ValidatedescriptionText(descriptionText))) {
            errorMessage.append(v.ValidatedescriptionText(descriptionText));
        }

        //get disclaimer
        String disclaimerText = disclaimerTextField.getText();
        if(!isTrue(v.ValidateDisclaimer(disclaimerText))) {
            errorMessage.append(v.ValidateDisclaimer(disclaimerText));
        }

        //get year
        String yearText = yearTextField.getText();
        if(!isTrue(v.ValidateyearText(yearText))) {
            errorMessage.append(v.ValidateyearText(yearText));
        }

        //get make
        String makeText = makeTextField.getText();
        if(!isTrue(v.ValidatemakeText(makeText))) {
            errorMessage.append(v.ValidatemakeText(makeText));
        }

        //get model
        String modelText = modelTextField.getText();
        if(!isTrue(v.ValidatemodelText(modelText))) {
            errorMessage.append(v.ValidatemodelText(modelText));
        }

        //get price
        String priceText = priceTextField.getText();
        if(!isTrue(v.ValidatepriceText(priceText))) {
            errorMessage.append(v.ValidatepriceText(priceText));
        }

        //get mileage
        String mileageText = mileageTextField.getText();
        if(!isTrue(v.ValidatemilleageText(mileageText))) {
            errorMessage.append(v.ValidatemilleageText(mileageText));
        }

        //get category
        String categoryBox = (String) categoryComboBox.getSelectedItem();

        //get color
        String colorBox = (String) colorComboBox.getSelectedItem();

        //get type
        String typeBox = (String) typeComboBox.getSelectedItem();

        //get trim
        String trimBox = (String) comboBox1.getSelectedItem();

        //alert if not valid
        if(!v.IsNullOrEmpty(errorMessage)) {
            throw new Exception(errorMessage.toString());
        } else {
            //if valid, set all

            i.setID(idText);
//        	i.setDealerId(d.getDealerId);// dealerid is in dealerScreen
            i.setTitle(titleText);
            i.setStartDate(startDateText);
            i.setEndDate(endDateText);
            i.setCashValue(Float.parseFloat(discountText));
            i.setDescription(descriptionText);
            i.setDisclaimer(disclaimerText);

            i.discountCriteria.add(yearText);
            i.discountCriteria.add(makeText);
            i.discountCriteria.add(modelText);
            i.discountCriteria.add(priceText);
            i.discountCriteria.add(mileageText);
            i.discountCriteria.add(categoryBox);
            i.discountCriteria.add(colorBox);
            i.discountCriteria.add(typeBox);
            i.discountCriteria.add(trimBox);
        }
        return i;
    }

    //get String from textFields and comboBoxs in addScreen
    private IncentiveUtilities getTextFromAddScreen() throws Exception {
        IncentiveUtilities i = new IncentiveUtilities();
        StringBuilder errorMessage = new StringBuilder();

        //get string from title
        String titleText = titleTextField.getText();
        if(!isTrue(v.ValidatetitleText(titleText))) {
            errorMessage.append(v.ValidatetitleText(titleText));
        }

        //get start date
        String startDateText = textField1.getText();
        if(!isTrue(v.ValidateStartDate(startDateText))) {
            errorMessage.append(v.ValidateStartDate(startDateText));
        }

        //get end date
        String endDateText = endDateTextField.getText();
        if(!isTrue(v.ValidateEndDate(startDateText,endDateText))) {
            errorMessage.append(v.ValidateEndDate(startDateText,endDateText));
        }

        //get discount
        String discountText = discountTextField.getText();
        if(!isTrue(v.ValidatediscountText(discountText))) {
            errorMessage.append(v.ValidatediscountText(discountText));
        }

        //get description
        String descriptionText = descriptionTextField.getText();
        if(!isTrue(v.ValidatedescriptionText(descriptionText))) {
            errorMessage.append(v.ValidatedescriptionText(descriptionText));
        }

        //get disclaimer
        String disclaimerText = disclaimerTextField.getText();
        if(!isTrue(v.ValidateDisclaimer(disclaimerText))) {
            errorMessage.append(v.ValidateDisclaimer(disclaimerText));
        }

        //get year
        String yearText = yearTextField.getText();
        if(!isTrue(v.ValidateyearText(yearText))) {
            errorMessage.append(v.ValidateyearText(yearText));
        }

        //get make
        String makeText = makeTextField.getText();
        if(!isTrue(v.ValidatemakeText(makeText))) {
            errorMessage.append(v.ValidatemakeText(makeText));
        }

        //get model
        String modelText = modelTextField.getText();
        if(!isTrue(v.ValidatemodelText(modelText))) {
            errorMessage.append(v.ValidatemodelText(modelText));
        }

        //get price
        String priceText = priceTextField.getText();
        if(!isTrue(v.ValidatepriceText(priceText))) {
            errorMessage.append(v.ValidatepriceText(priceText));
        }

        //get mileage
        String mileageText = mileageTextField.getText();
        if(!isTrue(v.ValidatemilleageText(mileageText))) {
            errorMessage.append(v.ValidatemilleageText(mileageText));
        }

        //get category
        String categoryBox = (String) categoryComboBox.getSelectedItem();

        //get color
        String colorBox = (String) colorComboBox.getSelectedItem();

        //get type
        String typeBox = (String) typeComboBox.getSelectedItem();

        //get trim
        String trimBox = (String) comboBox1.getSelectedItem();

        //alert if not valid
        if(!v.IsNullOrEmpty(errorMessage)) {
            throw new Exception(errorMessage.toString());
        } else {
            //if valid, set all

            i.setID(i.generateString(6));
//        	i.setDealerId(d.getDealerId);
            i.setTitle(titleText);
            i.setStartDate(startDateText);
            i.setEndDate(endDateText);
            i.setCashValue(Float.parseFloat(discountText));
            i.setDescription(descriptionText);
            i.setDisclaimer(disclaimerText);
            i.setYear(Integer.parseInt(yearText));
            i.setMake(makeText);
            i.setModel(modelText);
            i.setPrice(Double.parseDouble(priceText));
            i.setMileage(Double.parseDouble(mileageText));

            i.setCategory(categoryBox);
            i.setColor(colorBox);
            i.setTypes(typeBox);
            i.setTrim(trimBox);

            i.discountCriteria.add(yearText);
            i.discountCriteria.add(makeText);
            i.discountCriteria.add(modelText);
            i.discountCriteria.add(priceText);
            i.discountCriteria.add(mileageText);
            i.discountCriteria.add(categoryBox);
            i.discountCriteria.add(colorBox);
            i.discountCriteria.add(typeBox);
            i.discountCriteria.add(trimBox);

//        	i.setDiscountCriteria(discountCriteria);

//        	System.out.println("-----------------");
//        	System.out.println(i.getDiscountCriteria());
//        	System.out.println("-----------------");
        }
        return i;
    }

    //generate random ID
    private String generateString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for(int i = 0; i < length; i ++) {
            sb.append(allChar.charAt(random.nextInt(allChar.length())));
        }
        return sb.toString();
    }

    private boolean isTrue(String str) {
        if(str.equals("true")) {
            return true;
        }
        return false;
    }

    private void setFont(String str) {

        contentPanel.setFont(small);
        contentPanel.setLayout(new GridBagLayout());
        ((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {294, 0, 124, 0};

        if(str.equals("edit")) {
            idLabel.setFont(big);
        }


        addIncentiveLabel.setFont(big);

        titleLabel.setFont(big);

        applyToInventoryLabel.setFont(big);

        categoryLabel.setFont(big);

        categoryComboBox.setFont(comboBoxFont);

        label1.setFont(big);

        yearLabel.setFont(big);

        startDateLabel.setFont(small);

        makeLabel.setFont(big);

        modelLabel.setFont(big);

        endDateLabel.setFont(small);

        priceLabel.setFont(big);

        mileageLabel.setFont(big);

        colorLabel.setFont(big);

        colorComboBox.setFont(comboBoxFont);

        discountLabel.setFont(big);

        typeLabel.setFont(big);

        typeComboBox.setFont(comboBoxFont);

        trimLabel.setFont(big);

        comboBox1.setFont(comboBoxFont);

        descriptionLabel.setFont(big);

        label2.setFont(big);

        applyButton.setFont(small);
    }

    //add component to panel and set layout
    private void addComponentsAndSetLayoutInEditScreen() {

        //---- id Label ----
        contentPanel.add(idLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- id TextField ----
        contentPanel.add(idTextField, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- addIncentiveLabel ----
        contentPanel.add(addIncentiveLabel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- titleLabel ----
        contentPanel.add(titleLabel, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- applyToInventoryLabel ----
        contentPanel.add(applyToInventoryLabel, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
        contentPanel.add(titleTextField, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- categoryLabel ----
        contentPanel.add(categoryLabel, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- categoryComboBox ----
        contentPanel.add(categoryComboBox, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        //---- label1 ----
        contentPanel.add(label1, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- yearLabel ----
        contentPanel.add(yearLabel, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- yearTextField ----
        contentPanel.add(yearTextField, new GridBagConstraints(3, 4, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        //---- startDateLabel ----
        contentPanel.add(startDateLabel, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- makeLabel ----
        contentPanel.add(makeLabel, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
        contentPanel.add(makeTextField, new GridBagConstraints(3, 5, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        //---- textField1 ----
        contentPanel.add(textField1, new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- modelLabel ----
        contentPanel.add(modelLabel, new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
        contentPanel.add(modelTextField, new GridBagConstraints(3, 6, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        //---- endDateLabel ----
        contentPanel.add(endDateLabel, new GridBagConstraints(0, 9, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- priceLabel ----
        contentPanel.add(priceLabel, new GridBagConstraints(2, 7, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
        contentPanel.add(priceTextField, new GridBagConstraints(3, 7, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        //---- endDateTextField ----
        contentPanel.add(endDateTextField, new GridBagConstraints(0, 10, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- mileageLabel ----
        contentPanel.add(mileageLabel, new GridBagConstraints(2, 8, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
        contentPanel.add(mileageTextField, new GridBagConstraints(3, 8, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        //---- colorLabel ----
        contentPanel.add(colorLabel, new GridBagConstraints(2, 9, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- colorComboBox ----
        contentPanel.add(colorComboBox, new GridBagConstraints(3, 9, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        //---- discountLabel ----
        contentPanel.add(discountLabel, new GridBagConstraints(0, 11, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- typeLabel ----
        contentPanel.add(typeLabel, new GridBagConstraints(2, 10, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- typeComboBox ----
        contentPanel.add(typeComboBox, new GridBagConstraints(3, 10, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        //---- discountTextField ----
        contentPanel.add(discountTextField, new GridBagConstraints(0, 12, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- trimLabel ----
        contentPanel.add(trimLabel, new GridBagConstraints(2, 11, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- comboBox1 ----
        contentPanel.add(comboBox1, new GridBagConstraints(3, 11, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        //---- descriptionLabel ----
        contentPanel.add(descriptionLabel, new GridBagConstraints(0, 13, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //======== scrollPane1 ========
        contentPanel.add(descriptionTextField, new GridBagConstraints(0, 14, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- label2 ----
        contentPanel.add(label2, new GridBagConstraints(0, 15, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //======== scrollPane2 ========
        contentPanel.add(disclaimerTextField, new GridBagConstraints(0, 16, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));
    }

    private void addComponentsAndSetLayoutInAddScreen() {

        //---- addIncentiveLabel ----
        contentPanel.add(addIncentiveLabel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- titleLabel ----
        contentPanel.add(titleLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- applyToInventoryLabel ----
        contentPanel.add(applyToInventoryLabel, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
        contentPanel.add(titleTextField, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- categoryLabel ----
        contentPanel.add(categoryLabel, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- categoryComboBox ----
        contentPanel.add(categoryComboBox, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        //---- label1 ----
        contentPanel.add(label1, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- yearLabel ----
        contentPanel.add(yearLabel, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- yearTextField ----
        contentPanel.add(yearTextField, new GridBagConstraints(3, 4, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        //---- startDateLabel ----
        contentPanel.add(startDateLabel, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- makeLabel ----
        contentPanel.add(makeLabel, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
        contentPanel.add(makeTextField, new GridBagConstraints(3, 5, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        //---- textField1 ----
        contentPanel.add(textField1, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- modelLabel ----
        contentPanel.add(modelLabel, new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
        contentPanel.add(modelTextField, new GridBagConstraints(3, 6, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        //---- endDateLabel ----
        contentPanel.add(endDateLabel, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- priceLabel ----
        contentPanel.add(priceLabel, new GridBagConstraints(2, 7, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
        contentPanel.add(priceTextField, new GridBagConstraints(3, 7, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        //---- endDateTextField ----
        contentPanel.add(endDateTextField, new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- mileageLabel ----
        contentPanel.add(mileageLabel, new GridBagConstraints(2, 8, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
        contentPanel.add(mileageTextField, new GridBagConstraints(3, 8, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        //---- colorLabel ----
        contentPanel.add(colorLabel, new GridBagConstraints(2, 9, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- colorComboBox ----
        contentPanel.add(colorComboBox, new GridBagConstraints(3, 9, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        //---- discountLabel ----
        contentPanel.add(discountLabel, new GridBagConstraints(0, 9, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- typeLabel ----
        contentPanel.add(typeLabel, new GridBagConstraints(2, 10, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- typeComboBox ----
        contentPanel.add(typeComboBox, new GridBagConstraints(3, 10, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        //---- discountTextField ----
        contentPanel.add(discountTextField, new GridBagConstraints(0, 10, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- trimLabel ----
        contentPanel.add(trimLabel, new GridBagConstraints(2, 11, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- comboBox1 ----
        contentPanel.add(comboBox1, new GridBagConstraints(3, 11, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        //---- descriptionLabel ----
        contentPanel.add(descriptionLabel, new GridBagConstraints(0, 11, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //======== scrollPane1 ========
        contentPanel.add(descriptionTextField, new GridBagConstraints(0, 12, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- label2 ----
        contentPanel.add(label2, new GridBagConstraints(0, 13, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //======== scrollPane2 ========
        contentPanel.add(disclaimerTextField, new GridBagConstraints(0, 14, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));
    }

    private void setButtonBar() {
        buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
        buttonBar.setLayout(new GridBagLayout());
        ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 80};
        ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0};

        //---- applyButton ----
        buttonBar.add(applyButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
    }

    private void makeItVisible() {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    //deleteIncentiveScreen
    public void delete(String incentID) {
        // Predefine variables
        String message = "Delete this incentive (ID: " + incentID + ")?";
        String title = "Delete incentive";
        Object[] options = {"Delete", "Not now"};

        // Show dialog
        int response = JOptionPane.showOptionDialog(null, message, title,
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        // Add delete action
        if (response == 0) {
            try {
//                deleteAnIncentive(incentID);
                JOptionPane.showMessageDialog(null, "Delete successfully!", title, JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Cannot delete incentive from the file.", title, JOptionPane.ERROR_MESSAGE);
            }
        }

        /* For Balla's function
        IncentiveScreen is = new IncentiveScreen();
        */

    }

    /* Test deleteScreen
    public static void main(String[] args) {
        IncentiveUtilities GUI = new IncentiveUtilities();
        GUI.delete("001");
    }
    */


    /*  Test addScreen and editScreen
    public static void main(String args[]) {
	    IncentiveUtilities a = new IncentiveUtilities();
	    a.addIncentives();
//	    a.EditIncentives(a);
    }
	*/
}

