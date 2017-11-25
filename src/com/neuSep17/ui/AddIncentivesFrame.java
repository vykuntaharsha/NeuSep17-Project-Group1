package com.neuSep17.ui;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstraints;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AddIncentivesFrame extends JFrame {
    public AddIncentivesFrame() {
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        titleLabel = new JLabel();
        titleTextField = new JTextField();
        discountLabel = new JLabel();
        discountTextField = new JTextField();
        startDateLabel = new JLabel();
        comboBox1 = new JComboBox();
        comboBox2 = new JComboBox();
        comboBox3 = new JComboBox();
        endDateLabel = new JLabel();
        comboBox4 = new JComboBox();
        comboBox5 = new JComboBox();
        comboBox6 = new JComboBox();
        descriptionLabel = new JLabel();
        descriptionTextField = new JTextField();
        disclaimerLabel = new JLabel();
        disclaimerTextField = new JTextField();
        buttonBar = new JPanel();
        applyButton = new JButton();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new TableLayout(new double[][]{
                        {TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED},
                        {TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED}}));
                ((TableLayout) contentPanel.getLayout()).setHGap(5);
                ((TableLayout) contentPanel.getLayout()).setVGap(5);

                //---- titleLabel ----
                titleLabel.setText("Title");
                contentPanel.add(titleLabel, new TableLayoutConstraints(0, 0, 0, 0, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));
                contentPanel.add(titleTextField, new TableLayoutConstraints(1, 0, 1, 0, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));

                //---- discountLabel ----
                discountLabel.setText("Discount");
                contentPanel.add(discountLabel, new TableLayoutConstraints(0, 1, 0, 1, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));
                contentPanel.add(discountTextField, new TableLayoutConstraints(1, 1, 1, 1, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));

                //---- startDateLabel ----
                startDateLabel.setText("Start Date");
                contentPanel.add(startDateLabel, new TableLayoutConstraints(0, 2, 0, 2, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));
                contentPanel.add(comboBox1, new TableLayoutConstraints(1, 2, 1, 2, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));
                contentPanel.add(comboBox2, new TableLayoutConstraints(2, 2, 2, 2, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));
                contentPanel.add(comboBox3, new TableLayoutConstraints(3, 2, 3, 2, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));

                //---- endDateLabel ----
                endDateLabel.setText("End Date");
                contentPanel.add(endDateLabel, new TableLayoutConstraints(0, 3, 0, 3, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));
                contentPanel.add(comboBox4, new TableLayoutConstraints(1, 3, 1, 3, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));
                contentPanel.add(comboBox5, new TableLayoutConstraints(2, 3, 2, 3, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));
                contentPanel.add(comboBox6, new TableLayoutConstraints(3, 3, 3, 3, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));

                //---- descriptionLabel ----
                descriptionLabel.setText("Description");
                contentPanel.add(descriptionLabel, new TableLayoutConstraints(0, 4, 0, 4, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));
                contentPanel.add(descriptionTextField, new TableLayoutConstraints(1, 4, 1, 4, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));

                //---- disclaimerLabel ----
                disclaimerLabel.setText("Disclaimer");
                contentPanel.add(disclaimerLabel, new TableLayoutConstraints(0, 5, 0, 5, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));
                contentPanel.add(disclaimerTextField, new TableLayoutConstraints(1, 5, 1, 5, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new TableLayout(new double[][]{
                        {TableLayout.FILL, TableLayout.PREFERRED},
                        {TableLayout.PREFERRED}}));
                ((TableLayout) buttonBar.getLayout()).setHGap(5);
                ((TableLayout) buttonBar.getLayout()).setVGap(5);

                //---- applyButton ----
                applyButton.setText("APPLY");
                buttonBar.add(applyButton, new TableLayoutConstraints(1, 0, 1, 0, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // End of component initialization

    }

    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel titleLabel;
    private JTextField titleTextField;
    private JLabel discountLabel;
    private JTextField discountTextField;
    private JLabel startDateLabel;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JLabel endDateLabel;
    private JComboBox comboBox4;
    private JComboBox comboBox5;
    private JComboBox comboBox6;
    private JLabel descriptionLabel;
    private JTextField descriptionTextField;
    private JLabel disclaimerLabel;
    private JTextField disclaimerTextField;
    private JPanel buttonBar;
    private JButton applyButton;
}
