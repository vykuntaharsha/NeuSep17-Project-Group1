package IncentiveBrowser;

import javax.swing.*;
import java.awt.*;

public class IncentiveScreen extends JFrame{

    private static JFrame mainFrame;
    private JPanel headerPanel;
    private JPanel filterPanel;
    private JPanel toolPanel;
    private JPanel listPanel;

    // GridBadConstraints shortcut methods
    public class GBC extends GridBagConstraints {

        public GBC(int gridx, int gridy) {
            this.gridx = gridx;
            this.gridy = gridy;
        }

        public GBC(int gridx, int gridy, int gridwidth, int gridheight) {
            this.gridx = gridx;
            this.gridy = gridy;
            this.gridwidth = gridwidth;
            this.gridheight = gridheight;
        }

        public GBC setAnchor(int anchor) {
            this.anchor = anchor;
            return this;
        }

        public GBC setFill(int fill) {
            this.fill = fill;
            return this;
        }

        public GBC setWeight(double weightx, double weighty) {
            this.weightx = weightx;
            this.weighty = weighty;
            return this;
        }

        public GBC setInsets(int top, int left, int bottom, int right) {
            this.insets = new Insets(top, left, bottom, right);
            return this;
        }

        public GBC setIpad(int ipadx, int ipady) {
            this.ipadx = ipadx;
            this.ipady = ipady;
            return this;
        }
    }

    public IncentiveScreen() {
        mainFrame = new JFrame();
        initialize();
        createComponents();
        addComponents();
        addListeners();
        makeThisVisible();
    }

    private void initialize() {
        mainFrame.setTitle("Incentive Management");
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setLayout(new GridBagLayout());
        mainFrame.setSize(1280, 700);
    }

    public void createComponents() {
        headerPanel = new JPanel();
        filterPanel = new JPanel();
        toolPanel = new JPanel();
        listPanel = new JPanel();
    }

    public void addComponents() {
        // Header
        JLabel headerTitle = new JLabel("Incentive Management");
        JButton inventoryButton = new JButton("Inventory");
        JButton incentiveButton = new JButton("Incentive");
        headerPanel.add(headerTitle);
        headerPanel.add(inventoryButton);
        headerPanel.add(incentiveButton);
        headerPanel.setBackground(Color.lightGray);
        headerPanel.setPreferredSize(new Dimension(1280, 100));
        mainFrame.add(headerPanel, new GBC(0, 0, 2, 1).setFill(GBC.BOTH));

        // Filter
        JLabel filterTitle = new JLabel("Filter");
        filterPanel.add(filterTitle);
        filterPanel.setBackground(Color.white);
        mainFrame.add(filterPanel, new GBC(0, 1, 1, 2).setAnchor(GBC.WEST).setIpad(200, 500).setFill(GBC.BOTH));

        // Tool
        JButton addButton = new JButton("Add");
        addButton.setPreferredSize(new Dimension(100,20));
        JLabel searchBar = new JLabel("SEARCH");
        toolPanel.add(addButton);
        toolPanel.add(searchBar);
        toolPanel.setBackground(Color.lightGray);
        mainFrame.add(toolPanel, new GBC(1,1).setAnchor(GBC.WEST).setFill(GBC.BOTH));

        // List
        listPanel.setBackground(Color.lightGray);
        listPanel.setLayout(new GridLayout(5, 3, 5, 5));
        createAndAddIncentiveRecord("description1");
        createAndAddIncentiveRecord("description2");
        createAndAddIncentiveRecord("description3");
        createAndAddIncentiveRecord("description4");
        createAndAddIncentiveRecord("description5");
        mainFrame.add(listPanel, new GBC(1, 2).setFill(GBC.BOTH));

    }

    /*
    // Create incentive records table
    public void createAndAddTable() {
        // Create column names
        String[] columnNames = {"Description"};

        // Test data
        Object[][] data = {{"Des1"}, {"Des2"}, {"Des3"}, {"Des4"}, {"Des5"}};

        JTable table = new JTable(data, columnNames);
        table.setBackground(Color.lightGray);

        JScrollPane scrollPane = new JScrollPane(table);
    }
    */

    // This method will be replaced by receiving an Incentive class
    public void createAndAddIncentiveRecord(String description) {
        JLabel desp = new JLabel(description);
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        listPanel.add(desp);
        listPanel.add(editButton);
        listPanel.add(deleteButton);
    }

    public void addListeners() {

    }

    private void makeThisVisible()
    {
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    IncentiveScreen screen = new IncentiveScreen();
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
