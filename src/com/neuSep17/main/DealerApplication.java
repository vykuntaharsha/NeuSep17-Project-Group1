package com.neuSep17.main;

import com.neuSep17.dto.Dealer;
import com.neuSep17.dto.Incentive;
import com.neuSep17.service.DealerImpleService;
import com.neuSep17.ui.IncentiveUI;
import com.neuSep17.ui.InventoryListUI;
import com.neuSep17.ui.ManageIncentivesUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class DealerApplication extends IncentiveUI {

    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel distanceLabel;
    private JLabel statusLabel;
    private JPanel controlPanel;
    private JComboBox<Dealer> dealersComboBox;

    public static void main(String[] args) throws IOException {
        DealerApplication welcome = new DealerApplication();
        welcome.createUI();
    }

    public void createUI() throws IOException {

        prepareGUI();

        JPanel vehiclesPanel = new JPanel(new GridLayout(5,1));
        JButton incentive = getIncentiveButton();
        JButton inventory = getInventoryButton();
        JButton sales = getSalesButton();
        vehiclesPanel.add(incentive);
        vehiclesPanel.add(new JLabel("                            "));
        vehiclesPanel.add(inventory);
        vehiclesPanel.add(new JLabel("                            "));
        vehiclesPanel.add(sales);

        JPanel managePanel = new JPanel(new GridLayout(5,1));
        JButton appointments = getAppointmentsButton();
        JButton employees = getEmployeesButton();
        JButton schedules = getSchedulesButton();
        managePanel.add(appointments);
        managePanel.add(new JLabel("                            "));
        managePanel.add(employees);
        managePanel.add(new JLabel("                            "));
        managePanel.add(schedules);

        JPanel dummyPanel = new JPanel();
        dummyPanel.add(new JLabel("                            "));

        JPanel outer = new JPanel();

        outer.add(vehiclesPanel);
        outer.add(dummyPanel);
        outer.add(managePanel);
        controlPanel.add(outer);
        mainFrame.setVisible(true);
    }

    private void prepareGUI() throws IOException {
        mainFrame = new JFrame("Welcome Dealer !");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        screenWidth = screenSize.width;
        screenHeight = screenSize.height;
        mainFrame.setSize(2400,2000);
        mainFrame.setVisible(true);
        mainFrame.setLayout(new GridLayout(3, 1));

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
        }

        JPanel imageNestedPanel = new JPanel();
        imageNestedPanel.setLayout(new GridLayout(4,1));
        JLabel imageLabel = pictures();
        JLabel imageLabel1  = pictures1();

        headerLabel = new JLabel("Welcome Dealer", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.PLAIN, 50));

        imageNestedPanel.add(imageLabel);
        imageNestedPanel.add(headerLabel);
        imageNestedPanel.add(imageLabel1);

        JPanel comboBoxPanel = new JPanel();
        dealersComboBox = getDealersComboBox();
        comboBoxPanel.add(dealersComboBox);

        statusLabel = new JLabel("", JLabel.CENTER);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 40));



        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        mainFrame.add(imageNestedPanel);
        mainFrame.add(comboBoxPanel);
        mainFrame.add(controlPanel);
        mainFrame.setVisible(true);
    }


    private JButton getSalesButton() {

        JButton Sales = new JButton("Sales");
        Sales.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("Clicked on Sales");
            }
        });

        return Sales;
    }
    private JButton getEmployeesButton() {

        JButton Employees = new JButton("Manage Employees");
        Employees.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("Clicked on Manage Employee");
            }
        });

        return Employees;
    }

    private JButton getInventoryButton() {

        JButton inventoryButton = new JButton("Manage Inventory");
        inventoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Dealer selectedDealer = (Dealer) dealersComboBox.getSelectedItem();
                String dealerId = selectedDealer.getId();
                System.out.println("Selected dealer id: "+dealerId+","+selectedDealer.getName()+","+selectedDealer.getEmailId());
                try {
                    InventoryListUI inventoryListUI = new InventoryListUI(dealerId);
                    inventoryListUI.setVisible(true);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        return inventoryButton;
    }

    private JButton getIncentiveButton() {

        JButton incentiveButton = new JButton("Manage Incentives");

        incentiveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Dealer selectedDealer = (Dealer) dealersComboBox.getSelectedItem();
                String dealerId = selectedDealer.getId();
                System.out.println("Selected dealer id: "+dealerId+","+selectedDealer.getName()+","+selectedDealer.getEmailId());
                try {
                    ManageIncentivesUI manageIncentivesUI = new ManageIncentivesUI(dealerId);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        return incentiveButton;

    }

    private JButton getAppointmentsButton() {

        JButton appointmentsButton = new JButton("Appointments");
        appointmentsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //statusLabel.setText("Dealer: "+chooseDealer.getSelectedItem()+" is selected.");
            }
        });
        return appointmentsButton;

    }

    private JButton getSchedulesButton() {

        JButton schedulesButton = new JButton("Schedules");
        schedulesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //statusLabel.setText("Dealer: "+chooseDealer.getSelectedItem()+" is selected.");
            }
        });
        return schedulesButton;

    }

    private JComboBox<Dealer> getDealersComboBox(){
        ArrayList<Dealer> dealers = new ArrayList<>();
        try {
            DealerImpleService dealerImpleService = new DealerImpleService();
            dealers = dealerImpleService.getDealers();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JComboBox<Dealer> comboBox = new JComboBox(dealers.toArray());
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if(value instanceof Dealer){
                    Dealer dealer = (Dealer) value;
                    setText(dealer.getName());
                }
                return this;
            }
        } );
        return comboBox;
    }


    private JLabel pictures() throws IOException{
        JLabel picLabel = createPicture("DealerHeaderImage1.png");
        picLabel.setSize(new Dimension(screenWidth, screenHeight/3));
        picLabel.setBounds(screenWidth, screenHeight/3, screenWidth, screenHeight/3);
        return picLabel;
    }

    private JLabel pictures1() throws IOException{
        JLabel picLabel1 = createPicture("DealerHeaderImage2.png");
        picLabel1.setSize(new Dimension(screenWidth, screenHeight/3));
        picLabel1.setBounds(screenWidth, screenHeight/3, screenWidth, screenHeight/3);

        return picLabel1;
    }
}
