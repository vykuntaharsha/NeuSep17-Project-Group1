package com.neuSep17.ui;

import com.neuSep17.dto.Dealer;
import com.neuSep17.service.DealerImpleService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class DealerScreen {

    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel distanceLabel;
    private JLabel statusLabel;
    private JPanel controlPanel;
    private JComboBox<Dealer> dealersComboBox;

    public static void main(String[] args) throws IOException {
        DealerScreen welcome = new DealerScreen();
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
        mainFrame = new JFrame("Welcome Dealer");
        mainFrame.setSize(2500,2000);
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
        imageNestedPanel.setLayout(new GridLayout(2,1));
        JLabel imageLabel = pictures();

        headerLabel = new JLabel("Welcome Dealer", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.PLAIN, 50));

        imageNestedPanel.add(imageLabel);
        imageNestedPanel.add(headerLabel);

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
                    ManageIncentivesScreen manageIncentivesScreen = new ManageIncentivesScreen(dealerId);
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
        JLabel picLabel = new JLabel(new ImageIcon("C:\\Users\\diksh\\Desktop\\NeuSep17-Project-Group1-master-src\\NeuSep17-Project-Group1-master\\src\\com\\neuSep17\\ui\\asset\\CarDealer.jpg"));
        picLabel.setSize(new Dimension(5000, 2000));

        return picLabel;
    }
}



