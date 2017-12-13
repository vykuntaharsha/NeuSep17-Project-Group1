//Team 6 Group 1
//Team Leader: Tianyu Hou
//Team Member: Zihan Ding, Jingyi Lin, Yujia Chen, Sihan Zhao;

package com.neuSep17.ui;

import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import com.neuSep17.dao.IncentiveImple;
import com.neuSep17.dao.PictureManager;
import com.neuSep17.dao.VehicleImple;
import com.neuSep17.dto.Incentive;
import com.neuSep17.dto.Vehicle;
import com.neuSep17.service.InventoryListService;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.JScrollPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.MouseInputListener;
import javax.swing.JComboBox;

public class InventoryListUI extends JFrame {

    private JPanel contentPane;
    private JPanel panelTop;
    private JPanel leftPanel;
    private JScrollPane scrollPane;

    private JFrame frame;

    private static Point origin = new Point();
    private JTextField txtFilter;
    private JTextField txtSearch;

    private ArrayList<Vehicle> list;
    private ArrayList<Vehicle> filter;
    private ArrayList<Incentive> incentiveList;
    
    private JTable table;

    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private JLabel labelBG;
    private JLabel labelTitleIcon;
    private JLabel lblSortby;

    private JButton close;
    private JButton min;

    private String selectedId;
    private String newSelectedId;
    
    private DefaultTableModel model;

    private final Color topBG = new Color(33, 33, 33);
    private final Color topFG = new Color(255, 255, 255);
    private final Color btnColor = new Color(198, 40, 40);
    private final Color tableOddRow = new Color(255, 255, 255);
    private final Color tableEvenRow = new Color(224, 224, 224);
    private final Color tableHeaderColor = new Color(117, 117, 117);

    private final Font sortCombobxFont = new Font("Arial", Font.ITALIC, 15);
    private final Font txtFont = new Font("Segoe UI Historic", Font.PLAIN, 22);
    private final Font tableHeaderFont = new Font("Segoe UI Historic", Font.PLAIN, 15);

    private final String[] headers = { "Id", "WebId", "Category", "Year", "Make", "Model", "Trim", "Bodytype", "Price","Discount","Photo","Vin","Entertainment"
            ,"InteriorColor","ExteriorColor","Fueltype","Engine","Transmission","Battery","OptionalFeatures"};
    
    private JComboBox<String> sortComboBox;
    private JComboBox<String> sortOrdercomboBox;

    private boolean isAscending;
    private int sortIndex;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    String dealerName = "gmps-covert-country";
                    InventoryListUI frame = new InventoryListUI(dealerName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public InventoryListUI(String dealerName) {
        init(dealerName);

        registerPanel();

        registerAEDBtn();

        registerTable();

        registerSearch();

        registerFilter();

        registerTitle();
        
        registerSortComboBox();

        setCloseAndMin();

        setDrag();
    }
    
    //init basic
    private void init(String dealerName) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String f = "data/"+dealerName;
        File file = new File(f);
        this.file = file; // team 2: Lu Niu
        list = InventoryListService.readAndGetVehicles(file);
//        list.stream().forEach(vehicle -> {
//            vehicle.getPhoto();
//        });
        /* commented by Bin Shi to optimize the loading speed
        list.parallelStream().forEach(vehicle -> {
            vehicle.getPhoto();
        });
        */
        
        PictureManager.initDealerPhotoLibrary(dealerName, list);
        
        incentiveList = getIncentives(dealerName);
        filter = new ArrayList<>();
        isAscending = true;
        selectedId = "";
        newSelectedId = "";
        contentPane = new JPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setBounds(100, 100, 1300, 800);
        contentPane.setLayout(null);
        setLocationRelativeTo(null);
        setContentPane(contentPane);
        setVisible(true);
        ToolTipManager.sharedInstance().setDismissDelay(80000);
    }
    
    private ArrayList<Incentive> getIncentives(String dealerID) {
        IncentiveImple incentiveImple = new IncentiveImple();
        return incentiveImple.getIncentivesForDealer(dealerID);
    }
    
    //sortBycomboBox
    private void registerSortComboBox() {
        lblSortby = new JLabel("SortBy:");
        lblSortby.setFont(txtFont);
        lblSortby.setForeground(topFG);
        lblSortby.setBounds(39, 177, 94, 25);
        panelTop.add(lblSortby);
        
        ArrayList<Vehicle> tmp = new ArrayList<>(list);
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(headers);
        sortComboBox = new JComboBox<String>(model);
        sortComboBox.insertItemAt("Empty", 0);
        sortComboBox.setFont(sortCombobxFont);
        sortComboBox.setSelectedIndex(0);
        sortComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sortIndex = ((JComboBox)e.getSource()).getSelectedIndex();
                if(sortIndex == 0) {
                    InventoryListService.fillTable(filter.size() == 0? list : filter,incentiveList, table);
                }else {
                    InventoryListService.sortByHeaders(filter.size() == 0? tmp : filter, isAscending, headers[sortIndex-1]);
                    InventoryListService.fillTable(filter.size() == 0? tmp : filter,incentiveList, table);
                }
            }
        });
        sortComboBox.setBounds(129, 178, 122, 27);
        panelTop.add(sortComboBox);
        
        //sortOrderBox
        String[] item = {"Sort From Low To High", "Sort From High To Low"}; 
        DefaultComboBoxModel<String> modelOrder = new DefaultComboBoxModel<>(item);
        sortOrdercomboBox = new JComboBox<String>(modelOrder);
        sortOrdercomboBox.setFont(sortCombobxFont);
        sortOrdercomboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int idx = ((JComboBox)e.getSource()).getSelectedIndex();
                isAscending = idx == 0? true : false;
                
                if(sortIndex == 0) {
                    InventoryListService.fillTable(filter.size() == 0? list : filter,incentiveList, table);
                }else {
                    InventoryListService.sortByHeaders(filter.size() == 0? tmp : filter, isAscending, headers[sortIndex-1]);
                    InventoryListService.fillTable(filter.size() == 0? tmp : filter,incentiveList, table);
                }
            }
        });
        sortOrdercomboBox.setBounds(277, 178, 197, 27);
        panelTop.add(sortOrdercomboBox);
    }

    // close the window
    private void exit() {
        //only exit this window not the application
        dispose();
    }

    // minimize
    private void minimize() {
        frame.setExtendedState(ICONIFIED);
    }

    // Panel
    private void registerPanel() {
        // Left Panel
        leftPanel = new JPanel();
        leftPanel.setBounds(0, 0, 350, 800);
        leftPanel.setLayout(null);
        contentPane.add(leftPanel);

        labelBG = new JLabel("");
        labelBG.setDisplayedMnemonic('0');
        labelBG.setIcon(new ImageIcon(InventoryListUI.class.getResource("asset/InventoryListUIBG2.jpg")));
        labelBG.setBounds(0, 0, 350, 800);
        leftPanel.add(labelBG);

        // Top Panel
        panelTop = new JPanel();
        panelTop.setBackground(topBG);
        panelTop.setBounds(350, 0, 950, 220);
        panelTop.setLayout(null);
        contentPane.add(panelTop);
    }

    // Filter
    private void registerFilter() {
        String placeholder = "FilterBy Any Keywords...";
        txtFilter = new JTextField(placeholder);
        txtFilter.setToolTipText("<html><h2 style =\"font-family : Arial; font-style: italic\">please type anything you want!"
                + "<br/> The order of Keywords matters"
                + "<br/> Please type the words following the order of headers"
                + "<br/> No need to type the whole words</h2></html>");
        txtFilter.setForeground(Color.GRAY);
        txtFilter.setBackground(topBG);
        txtFilter.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
        txtFilter.setFont(txtFont);
        txtFilter.setCaretColor(topFG);
        txtFilter.setBounds(533, 100, 360, 40);
        panelTop.add(txtFilter);
        txtFilter.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtFilter.getText().equals(placeholder)) {
                    txtFilter.setText("");
                    txtFilter.setForeground(topFG);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtFilter.getText().isEmpty()) {
                    txtFilter.setForeground(Color.GRAY);
                    txtFilter.setText(placeholder);
                    InventoryListService.fillTable(list,incentiveList, table);
                }
            }
        });

        txtFilter.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                warn();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                warn();
            }

            public void warn() {
                filter = InventoryListService.filter(filter, list, txtFilter);
                InventoryListService.fillTable(filter, incentiveList,table);
            }
        });
    }

    // Search
    private void registerSearch() {
        String placeholder = "SearchBy ID\\Vin\\Make\\Model\\...";
        txtSearch = new JTextField(placeholder);
        txtSearch.setForeground(Color.GRAY);
        txtSearch.setToolTipText("<html><h2 style =\"font-family : Arial; font-style: italic\">please type anything you want!"
                + "<br/>The keywords order doesn't matter "
                + "<br/>But need to type the complete words"
                + "<br/>type like eg: 2013 used chevrolet car"
                + "<br/>will get the same result as: chervolet 2013 car used</h2></html>");
        txtSearch.setBackground(topBG);
        txtSearch.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
        txtSearch.setFont(txtFont);
        txtSearch.setCaretColor(topFG);
        txtSearch.setBounds(533, 54, 360, 40);
        panelTop.add(txtSearch);
        txtSearch.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtSearch.getText().equals(placeholder)) {
                    txtSearch.setText("");
                    txtSearch.setForeground(topFG);
//                    clearSelection();
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtSearch.getText().isEmpty()) {
                    txtSearch.setForeground(Color.GRAY);
                    txtSearch.setText(placeholder);
                    InventoryListService.fillTable(list,incentiveList, table);
                }
            }
        });

        txtSearch.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                warn();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                warn();
            }

            public void warn() {
                filter = InventoryListService.search(filter, list, txtSearch);
                InventoryListService.fillTable(filter, incentiveList, table);
            }
        });
    }

    // ADD Table
    private void registerTable() {
        Object[][] cellData = null;

        model = new DefaultTableModel(cellData, headers) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
            public Class getColumnClass(int column)
            {
                return getValueAt(0, column).getClass();
            }
        };

        table = new JTable(model);
        TableColumn column = null;
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setRowHeight(50);
        for (int i = 0; i < headers.length; i++) {
            column = table.getColumnModel().getColumn(i);
            switch (i) {
            //Id
            case 0:
                column.setMinWidth(100);
                column.setMaxWidth(100);
                continue;
            //webId
            case 1:
                column.setMinWidth(150);
                column.setMaxWidth(150);
                continue;
            //Category
            case 2:
                column.setMinWidth(80);
                column.setMaxWidth(80);
                continue;
            //Year
            case 3:
                column.setMinWidth(50);
                column.setMaxWidth(50);
                continue;
            //Make
            case 4:
                column.setMinWidth(100);
                column.setMaxWidth(100);
                continue;
            //Model
            case 5:
                column.setMinWidth(150);
                column.setMaxWidth(150);
                continue;
            //Trim
            case 6:
                column.setMinWidth(300);
                column.setMaxWidth(300);
                continue;
            //Bodytype
            case 7:
                column.setMinWidth(150);
                column.setMaxWidth(150);
                continue;
            //Price
            case 8:
                column.setMinWidth(100);
                column.setMaxWidth(100);
                continue;
            //Discount:
            case 9:
                column.setMinWidth(300);
                column.setMaxWidth(300);
                continue;
            //Photo
            case 10:
                column.setMinWidth(90);
                column.setMaxWidth(90);
                continue;
            //Vin
            case 11:
                column.setMinWidth(150);
                column.setMaxWidth(150);
                continue;
            //Entertainment
            case 12:
                column.setMinWidth(600);
                column.setMaxWidth(600);
                continue;
            //InteriorColor
            case 13:
                column.setMinWidth(150);
                column.setMaxWidth(150);
                continue;
            //ExteriorColor
            case 14:
                column.setMinWidth(150);
                column.setMaxWidth(150);
                continue;
            //Fueltype
            case 15:
                column.setMinWidth(100);
                column.setMaxWidth(100);
                continue;
            //Engine
            case 16:
                column.setMinWidth(250);
                column.setMaxWidth(250);
                continue;
            //Transmission
            case 17:
                column.setMinWidth(250);
                column.setMaxWidth(250);
                continue;
            //Battery
            case 18:
                column.setMinWidth(100);
                column.setMaxWidth(100);
                continue;
            //OptionalFeatures
            case 19:
                column.setMinWidth(350);
                column.setMaxWidth(350);
                continue;
            }
        }
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        InventoryListService.fillTable(list,incentiveList, table);
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setReorderingAllowed(false);
        tableHeader.setBackground(tableHeaderColor);
        tableHeader.setForeground(topFG);
        tableHeader.setFont(tableHeaderFont);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setRowMargin(0);
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(350, 220, 950, 580);
        table.setPreferredScrollableViewportSize(new Dimension(1950, 580));

        LinkCellRenderer renderer = new LinkCellRenderer();

        table.setDefaultRenderer(Object.class, renderer);

        table.addMouseListener(renderer);
        table.addMouseMotionListener(renderer);
        
        
        renderer.setHorizontalAlignment(JLabel.CENTER);
        // set horizon scroll;
        scrollPane.setAutoscrolls(true);
        contentPane.add(scrollPane);

        scrollPane.setViewportView(table);
    }

    // Title and Icon
    private void registerTitle() {
        labelTitleIcon = new JLabel("");
        labelTitleIcon.setIcon(new ImageIcon(InventoryListUI.class.getResource("asset/InventoryListUIhome.png")));
        labelTitleIcon.setBounds(30, 15, 438, 129);
        panelTop.add(labelTitleIcon);
    }

    // ADD DELETE EDIT BTN
    private void registerAEDBtn() {
        btnAdd = new JButton("Add");
        btnAdd.setBorderPainted(false);
        InventoryListUI that = this; // team 2: Lu Niu
        btnAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (getSelectedId() == null || getSelectedId().isEmpty()) { 
                    InventoryEditUI tempui = new InventoryEditUI(null, that, true);
                } else {
                    for (Vehicle v : list) {
                        if (v.getID().equals(getSelectedId())) {
                            InventoryEditUI inventoryEditUI = new InventoryEditUI(v, that, true);
                        }
                    }
                }
            }
        });
        btnAdd.setFont(new Font("Segoe UI Historic", Font.PLAIN, 25));
        btnAdd.setForeground(new Color(255, 255, 255));
        btnAdd.setBackground(btnColor);
        btnAdd.setBounds(533, 165, 120, 40);
        panelTop.add(btnAdd);

        btnDelete = new JButton("Delete");
        btnDelete.setBorderPainted(false);
        //team2: yuanyuan jin start
        btnDelete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (getSelectedId() == null || getSelectedId().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please Select An Item To Delete", "Error Message",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    VehicleImple service = new VehicleImple();
                    int clickButton = JOptionPane.showConfirmDialog(null, "Confirm delete ?", "Delete",
                            JOptionPane.YES_NO_OPTION);
                    Vehicle deleteV = null;
                    if (clickButton == JOptionPane.YES_OPTION) {
                        
                        // delete selected data
                        for(int i=0;i<list.size();i++){
                            if(list.get(i).getID().equals(getSelectedId())){
                                service.deleteVehicle(list.get(i).getWebID(), list.get(i).getID());
                                list.remove(i);// remove the vehicle from the list object
                            }
                        }
                    }
                    
                    // refreshTable
                    refreshTable(null,deleteV);
                }
            }
        });
       //team 2 Yuanyuan jin end
        btnDelete.setFont(new Font("Segoe UI Historic", Font.PLAIN, 25));
        btnDelete.setForeground(new Color(255, 255, 255));
        btnDelete.setBackground(btnColor);
        btnDelete.setBounds(803, 165, 120, 40);
        panelTop.add(btnDelete);

        btnEdit = new JButton("Edit");
        btnEdit.setBorderPainted(false);
        btnEdit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (getSelectedId() == null || getSelectedId().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please Select An Item To Edit", "Error Message", JOptionPane.ERROR_MESSAGE);
                } else {
                    InventoryEditUI imf = new InventoryEditUI(getSelectedVehicle(), that, false);
                }
            }
        });
        btnEdit.setFont(new Font("Segoe UI Historic", Font.PLAIN, 25));
        btnEdit.setForeground(new Color(255, 255, 255));
        btnEdit.setBackground(btnColor);
        btnEdit.setBounds(668, 165, 120, 40);
        panelTop.add(btnEdit);
    }

    private void setCloseAndMin() {
        close = new JButton("");
        close.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                exit();
            }
        });
        close.setBorderPainted(false);
        close.setBackground(topBG);
        close.setIcon(new ImageIcon(InventoryListUI.class.getResource("asset/InventoryListUIclose.png")));
        close.setBounds(905, 12, 32, 38);
        panelTop.add(close);

        min = new JButton("");
        min.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                minimize();
            }
        });
        min.setIcon(new ImageIcon(InventoryListUI.class.getResource("asset/InventoryListUImin.png")));
        min.setBorderPainted(false);
        min.setBackground(topBG);
        min.setBounds(860, 12, 32, 38);
        panelTop.add(min);
    }

    // setDrag
    private void setDrag() {
        frame = (JFrame) contentPane.getParent().getParent().getParent();

        frame.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                origin.x = e.getX();
                origin.y = e.getY();
            }
        });

        frame.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point p = frame.getLocation();
                frame.setLocation(p.x + e.getX() - origin.x, p.y + e.getY() - origin.y);
            }
        });
    }

    public String getSelectedId() {
        return selectedId;
    }

    public ArrayList<Vehicle> getList() {
        return list;
    }
    
    public Vehicle getSelectedVehicle() {
        for(Vehicle v : list) {
            if(v.getID().equals(selectedId)) {
                return v;
            }
        }
        return null;
    }
    
 public class LinkCellRenderer extends DefaultTableCellRenderer implements MouseInputListener {
        
        //Mouse point row;
        private int row = -1;
        //Mouse point col;
        private int col = -1;
        //Table Listener;
        private JTable table = null;
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            //default style
            this.table = table;
//            this.setForeground(Color.BLACK);
//            table.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            this.setText(value.toString());
            if (row % 2 == 0)
                setBackground(tableEvenRow);
            if (row % 2 == 1)
                setBackground(tableOddRow);
            //link style
//           if (row == this.row && column == this.col && column == 9) {
//                    this.setForeground(Color.RED);
//                    table.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//                    return this;
//            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }

        public void mouseExited(MouseEvent e) {
            if (table != null) {
                int oldRow = row;
                int oldCol = col;
                row = -1;
                col = -1;
                if (oldRow != -1 && oldCol != -1) {
                    Rectangle rect = table.getCellRect(oldRow, oldCol, false);
                    table.repaint(rect);
                }
            }
        }

        public void mouseDragged(MouseEvent e) {
        }

        public void mouseMoved(MouseEvent e) {
            if (table != null) {
                Point p = e.getPoint();
                int oldRow = row;
                int oldCol = col;
                row = table.rowAtPoint(p);
                col = table.columnAtPoint(p);
                if (oldRow != -1 && oldCol != -1) {
                    Rectangle rect = table.getCellRect(oldRow, oldCol, false);
                    table.repaint(rect);
                }
                if (row != -1 && col != -1) {
                    Rectangle rect = table.getCellRect(row, col, false);
                    table.repaint(rect);
                }
            }
        }

        public void mouseClicked(MouseEvent e) {
            int selectedRow = table.getSelectedRow();
            if(selectedId.isEmpty()) {
                selectedId = (String) model.getValueAt(selectedRow, 0);
            }else {
                newSelectedId = (String) model.getValueAt(selectedRow, 0);
            }
            
            if(!newSelectedId.isEmpty() && !newSelectedId.equals(selectedId)) {
                selectedId = newSelectedId;
                newSelectedId = "";
            }
            
            if(selectedId.equals(newSelectedId)) {
                table.clearSelection();
                selectedId = "";
                newSelectedId = "";
                return;
            }
            
            //link to web
//           Point p = e.getPoint();
//            int c = table.columnAtPoint(p);
//            if(c != 9){
//                return;
//            }
//            try {
//                String s = table.getValueAt(selectedRow, c).toString();
//                URL url = new URL(s.split("\"")[1]);
//                Desktop.getDesktop().browse(url.toURI());
//            } catch (Exception ex) {
//                Logger.getLogger(LinkCellRenderer.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }
    }
    
    // team 2: Lu Niu
    private File file;
    public void refreshTable(Vehicle addedVehicle, Vehicle deletedVehicle) {
        if (addedVehicle != null) {
            Vehicle vToBeRemoved = null;
            for (Vehicle v : list) {
                if (v.getWebID().equalsIgnoreCase(addedVehicle.getWebID()) && 
                        v.getID().equalsIgnoreCase(addedVehicle.getID())) {
                    vToBeRemoved = v;
                }
            }
            
            list.remove(vToBeRemoved);
            list.add(addedVehicle);   
        }       
        
        if (deletedVehicle != null) {
            list.remove(deletedVehicle);
        }
        
        InventoryListService.fillTable(list, incentiveList, table);
    }
}
