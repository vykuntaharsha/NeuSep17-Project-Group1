import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.regex.Pattern;

public class AddIncentiveScreen extends JFrame {
    public AddIncentiveScreen() {
        initComponents();
        setVisible(true);
    }

    //--------------------------

    private void applyButtonMouseClicked(MouseEvent e) {
        try {
            Incentive i = GetIncentive();
            //idm.add(i);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error: Unable to apply!", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }

        WindowEvent wev = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
        setVisible(false);
        dispose();

        //TODO: do what needs to be done next
    }

    private Incentive GetIncentive() throws Exception {
        Incentive i = new Incentive();
        StringBuilder errorMessage = new StringBuilder();

        String titleText = titleTextField.getText();
        if (IsNullOrEmpty(titleText)) {
            errorMessage.append("Title : should not be empty.\n");
        } else {
            if (ContainsSpecialCharacters(titleText)) {
                errorMessage.append("Title : should not contain special characters.\n");
            }
        }

        String discountText = discountTextField.getText();
        if (IsNullOrEmpty(discountText))
        {
            errorMessage.append("Discount : should not be empty.\n");
        }
        else
        {
            if (Pattern.matches("[0-9]{1,4}",discountText))
            {
                errorMessage.append("Discount : should contain only numbers and maximum value is 9999$.\n");
            }
        }

        String descriptionText = descriptionTextField.getText();
        if (IsNullOrEmpty(descriptionText))
        {
            errorMessage.append("Description : should not be empty.\n");
        }

        String disclaimerText = disclaimerTextField.getText();
        if (IsNullOrEmpty(disclaimerText))
        {
            errorMessage.append("Disclaimer : should not be empty.\n");
        }

        String makeText = makeTextField.getText();
        if (ContainsSpecialCharacters(makeText))
        {
            errorMessage.append("Make : should not contain special characters.\n");
        }

        String modelText = modelTextField.getText();
        if (ContainsSpecialCharacters(modelText))
        {
            errorMessage.append("Model : should not contain special characters.\n");
        }

        String yearText = yearTextField.getText();
        if(!IsNullOrEmpty(yearText)) {
            if (!Pattern.matches("[1][9][1-9][0-9]|[2][0][0][0-9]|[2][0][1][0-8]", yearText)) {
                errorMessage.append("Year : should be between 1910 to 2018.\n");
            }
        }

        String priceText = priceTextField.getText();
        if (!Pattern.matches("[0-9]*",priceText))
        {
            errorMessage.append("Price : should contain only numbers.\n");
        }

        String mileageText = mileageTextField.getText();
        if (!Pattern.matches("[0-9]*",mileageText))
        {
            errorMessage.append("Mileage : should contain only numbers.\n");
        }

        // validate all fields;

        if (!IsNullOrEmpty(errorMessage)) {
            throw new Exception(errorMessage.toString());
        } else {
            i.setTitle(titleText);
        }
        return i;
    }

    private boolean IsNullOrEmpty(String text) {
        return (text == null || text.trim().isEmpty());
    }

    private boolean IsNullOrEmpty(StringBuilder text) {
        return (text == null || text.toString().trim().isEmpty());
    }

    private boolean ContainsSpecialCharacters(String text) {
        return !Pattern.matches("[a-zA-Z0-9 ]*", text);
    }



    //---------------------------

    private void initComponents() {

        dialogPane = new JPanel();
        contentPanel = new JPanel();
        titleLabel = new JLabel();
        titleTextField = new JTextField();
        discountLabel = new JLabel();
        discountTextField = new JTextField();
        startDateLabel = new JLabel();
        startDateTextField = new JTextField();
        endDateLabel = new JLabel();
        endDateTextField = new JTextField();
        descriptionLabel = new JLabel();
        descriptionTextField = new JTextField();
        disclaimerLabel = new JLabel();
        disclaimerTextField = new JTextField();
        applyTolabel = new JLabel();
        makeLabel = new JLabel();
        makeTextField = new JTextField();
        modelLabel = new JLabel();
        modelTextField = new JTextField();
        yearLabel = new JLabel();
        yearTextField = new JTextField();
        categoryLabel = new JLabel();
        categoryComboBox = new JComboBox<>();
        colorLabel = new JLabel();
        colorTextField = new JTextField();
        priceLabel = new JLabel();
        priceTextField = new JTextField();
        mileageLabel = new JLabel();
        mileageTextField = new JTextField();
        typeLabel = new JLabel();
        typeComboBox = new JComboBox<>();
        buttonBar = new JPanel();
        applyButton = new JButton();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setBackground(Color.white);

            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setBackground(Color.white);
                contentPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {30, 0, 0};
                ((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                ((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
                ((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

                //---- titleLabel ----
                titleLabel.setText("Title");
                titleLabel.setFont(new Font("Calibri", Font.PLAIN, 14));
                contentPanel.add(titleLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));

                //---- titleTextField ----
                titleTextField.setFont(new Font("Calibri", Font.PLAIN, 14));
                titleTextField.setText("                                                                       ");
                contentPanel.add(titleTextField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 0), 0, 0));

                //---- discountLabel ----
                discountLabel.setText("Discount");
                discountLabel.setFont(new Font("Calibri", Font.PLAIN, 14));
                contentPanel.add(discountLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));

                //---- discountTextField ----
                discountTextField.setFont(new Font("Calibri", Font.PLAIN, 14));
                contentPanel.add(discountTextField, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 0), 0, 0));

                //---- startDateLabel ----
                startDateLabel.setText("Start Date");
                startDateLabel.setFont(new Font("Calibri", Font.PLAIN, 14));
                contentPanel.add(startDateLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));

                //---- startDateTextField ----
                startDateTextField.setFont(new Font("Calibri", Font.PLAIN, 14));
                startDateTextField.setToolTipText("Date should be in 'YYYY-MM-DD' format");
                contentPanel.add(startDateTextField, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 0), 0, 0));

                //---- endDateLabel ----
                endDateLabel.setText("End Date");
                endDateLabel.setFont(new Font("Calibri", Font.PLAIN, 14));
                endDateLabel.setToolTipText("Date should be in 'YYYY-MM-DD'  format");
                contentPanel.add(endDateLabel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));

                //---- endDateTextField ----
                endDateTextField.setFont(new Font("Calibri", Font.PLAIN, 14));
                endDateTextField.setToolTipText("Date should be in 'YYYY-MM-DD' format.");
                contentPanel.add(endDateTextField, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 0), 0, 0));

                //---- descriptionLabel ----
                descriptionLabel.setText("Description");
                descriptionLabel.setFont(new Font("Calibri", Font.PLAIN, 14));
                contentPanel.add(descriptionLabel, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));

                //---- descriptionTextField ----
                descriptionTextField.setFont(new Font("Calibri", Font.PLAIN, 14));
                contentPanel.add(descriptionTextField, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 0), 0, 0));

                //---- disclaimerLabel ----
                disclaimerLabel.setText("Disclaimer");
                disclaimerLabel.setFont(new Font("Calibri", Font.PLAIN, 14));
                contentPanel.add(disclaimerLabel, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));

                //---- disclaimerTextField ----
                disclaimerTextField.setFont(new Font("Calibri", Font.PLAIN, 14));
                contentPanel.add(disclaimerTextField, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 0), 0, 0));

                //---- applyTolabel ----
                applyTolabel.setText("Apply To");
                applyTolabel.setFont(new Font("Calibri", Font.PLAIN, 14));
                contentPanel.add(applyTolabel, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));

                //---- makeLabel ----
                makeLabel.setText("Make");
                makeLabel.setFont(new Font("Calibri", Font.PLAIN, 14));
                contentPanel.add(makeLabel, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));

                //---- makeTextField ----
                makeTextField.setFont(new Font("Calibri", Font.PLAIN, 14));
                makeTextField.setToolTipText("Enter make or nothing to apply incentive for all makes.");
                contentPanel.add(makeTextField, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 0), 0, 0));

                //---- modelLabel ----
                modelLabel.setText("Model");
                modelLabel.setFont(new Font("Calibri", Font.PLAIN, 14));
                contentPanel.add(modelLabel, new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));

                //---- modelTextField ----
                modelTextField.setFont(new Font("Calibri", Font.PLAIN, 14));
                modelTextField.setToolTipText("Enter a model or nothing to apply incentive for all models.");
                contentPanel.add(modelTextField, new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 0), 0, 0));

                //---- yearLabel ----
                yearLabel.setText("Year");
                yearLabel.setFont(new Font("Calibri", Font.PLAIN, 14));
                contentPanel.add(yearLabel, new GridBagConstraints(0, 9, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));

                //---- yearTextField ----
                yearTextField.setFont(new Font("Calibri", Font.PLAIN, 14));
                yearTextField.setToolTipText("Enter year between 1910 and 2018");
                contentPanel.add(yearTextField, new GridBagConstraints(1, 9, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 0), 0, 0));

                //---- categoryLabel ----
                categoryLabel.setText("Category");
                categoryLabel.setFont(new Font("Calibri", Font.PLAIN, 14));
                contentPanel.add(categoryLabel, new GridBagConstraints(0, 10, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));

                //---- categoryComboBox ----
                categoryComboBox.setFont(new Font("Calibri", Font.PLAIN, 14));
                categoryComboBox.setMaximumRowCount(4);
                categoryComboBox.setModel(new DefaultComboBoxModel<>(new String[] {
                        "All",
                        "New",
                        "Certified Pre-Owned",
                        "Used"
                }));
                contentPanel.add(categoryComboBox, new GridBagConstraints(1, 10, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 0), 0, 0));

                //---- colorLabel ----
                colorLabel.setText("Color");
                colorLabel.setFont(new Font("Calibri", Font.PLAIN, 14));
                contentPanel.add(colorLabel, new GridBagConstraints(0, 11, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));

                //---- colorTextField ----
                colorTextField.setFont(new Font("Calibri", Font.PLAIN, 14));
                contentPanel.add(colorTextField, new GridBagConstraints(1, 11, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 0), 0, 0));

                //---- priceLabel ----
                priceLabel.setText("Price");
                priceLabel.setFont(new Font("Calibri", Font.PLAIN, 14));
                contentPanel.add(priceLabel, new GridBagConstraints(0, 12, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));

                //---- priceTextField ----
                priceTextField.setFont(new Font("Calibri", Font.PLAIN, 14));
                priceTextField.setToolTipText("Enter maximum price of vehicle for which the incentive is applied or nothing for all price ranges.");
                contentPanel.add(priceTextField, new GridBagConstraints(1, 12, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 0), 0, 0));

                //---- mileageLabel ----
                mileageLabel.setText("Mileage");
                mileageLabel.setFont(new Font("Calibri", Font.PLAIN, 14));
                contentPanel.add(mileageLabel, new GridBagConstraints(0, 13, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));

                //---- mileageTextField ----
                mileageTextField.setFont(new Font("Calibri", Font.PLAIN, 14));
                mileageTextField.setToolTipText("Enter maximum mileage of vehicle for which the incentive is applied or nothing for all mileage ranges.");
                contentPanel.add(mileageTextField, new GridBagConstraints(1, 13, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 0), 0, 0));

                //---- typeLabel ----
                typeLabel.setText("Type");
                typeLabel.setFont(new Font("Calibri", Font.PLAIN, 14));
                contentPanel.add(typeLabel, new GridBagConstraints(0, 14, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));

                //---- typeComboBox ----
                typeComboBox.setFont(new Font("Calibri", Font.PLAIN, 14));
                typeComboBox.setModel(new DefaultComboBoxModel<>(new String[] {
                        "All",
                        "SUV",
                        "Coupe",
                        "Convertible",
                        "Sedan",
                        "Hatchback",
                        "Van",
                        "Pickup Truck"
                }));
                contentPanel.add(typeComboBox, new GridBagConstraints(1, 14, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(contentPanel, BorderLayout.NORTH);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setBackground(Color.white);
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0};

                //---- applyButton ----
                applyButton.setText("Apply");
                applyButton.setFont(new Font("Calibri", Font.PLAIN, 14));

                applyButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        applyButtonMouseClicked(e);
                    }
                });

                buttonBar.add(applyButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
    }


    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel titleLabel;
    private JTextField titleTextField;
    private JLabel discountLabel;
    private JTextField discountTextField;
    private JLabel startDateLabel;
    private JTextField startDateTextField;
    private JLabel endDateLabel;
    private JTextField endDateTextField;
    private JLabel descriptionLabel;
    private JTextField descriptionTextField;
    private JLabel disclaimerLabel;
    private JTextField disclaimerTextField;
    private JLabel applyTolabel;
    private JLabel makeLabel;
    private JTextField makeTextField;
    private JLabel modelLabel;
    private JTextField modelTextField;
    private JLabel yearLabel;
    private JTextField yearTextField;
    private JLabel categoryLabel;
    private JComboBox<String> categoryComboBox;
    private JLabel colorLabel;
    private JTextField colorTextField;
    private JLabel priceLabel;
    private JTextField priceTextField;
    private JLabel mileageLabel;
    private JTextField mileageTextField;
    private JLabel typeLabel;
    private JComboBox<String> typeComboBox;
    private JPanel buttonBar;
    private JButton applyButton;

}
