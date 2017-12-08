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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
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

import com.neuSep17.dto.Vehicle;
import com.neuSep17.service.InventoryListService;
import com.neuSep17.ui.InventoryListUI.LinkCellRenderer;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.MouseInputListener;
import javax.swing.JRadioButton;

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
    private JTable table;

    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;

    private JCheckBox chckbxYear;
    private JCheckBox chckbxExteriorColor;
    private JCheckBox chckbxId;
    private JCheckBox chckbxPrice;
    private JCheckBox chckbxInteriorColor;
    private JCheckBox chckbxMake;
    private JCheckBox chckbxModel;
    private JCheckBox chckbxCategory;
    private JCheckBox chckbxEngine;
    private JCheckBox chckbxBodytype;
    private List<JCheckBox> checkBoxGroup;
    private JLabel labelBG;
    private JLabel labelTitle;
    private JLabel labelTitleIcon;

    private JButton close;
    private JButton min;

    private String selectedId;
    
    private DefaultTableModel model;

    private final Color topBG = new Color(33, 33, 33);
    private final Color topFG = new Color(255, 255, 255);
    private final Color btnColor = new Color(198, 40, 40);
    private final Color tableOddRow = new Color(255, 255, 255);
    private final Color tableEvenRow = new Color(224, 224, 224);
    private final Color tableHeaderColor = new Color(117, 117, 117);

    private final Font checkbxFont = new Font("Segoe UI Historic", Font.ITALIC, 19);
    private final Font radioFont = new Font("Segoe UI Historic", Font.ITALIC, 20);
    private final Font txtFont = new Font("Segoe UI Historic", Font.PLAIN, 22);
    private final Font tableHeaderFont = new Font("Segoe UI Historic", Font.PLAIN, 15);
    private final Font titleFont = new Font("Malgun Gothic", Font.BOLD, 30);

    private ButtonGroup sortGroup;
    private JRadioButton rdbtnHighToLow;
    private JRadioButton rdbtnLowToHigh;

    private boolean isAscending;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    String dealerName = "gmps-covert-country";
                    InventoryListUI frame = new InventoryListUI(dealerName);
                    frame.setVisible(true);
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
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String f = "data/"+dealerName;
        File file = new File(f);
        this.file = file; // team 2: Lu Niu
        list = InventoryListService.readAndGetVehicles(file);
        filter = new ArrayList<>();
        isAscending = true;
        contentPane = new JPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setBounds(100, 100, 1300, 800);
        contentPane.setLayout(null);
        setLocationRelativeTo(null);
        setContentPane(contentPane);

        registerPanel();

        registerAEDBtn();

        registerRadio();

        registerSortCheckBox();

        registerTable();

        registerSearch();

        registerFilter();

        registerTitle();

        setCloseAndMin();

        setDrag();

        rdbtnLowToHigh.setSelected(true);
    }

    // close
    private void exit() {
        System.exit(0);
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
        String placeholder = "Filter";
        txtFilter = new JTextField(placeholder);
        txtFilter.setForeground(Color.GRAY);
        txtFilter.setBackground(topBG);
        txtFilter.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
        txtFilter.setFont(txtFont);
        txtFilter.setCaretColor(topFG);
        txtFilter.setBounds(270, 171, 177, 29);
        panelTop.add(txtFilter);
        txtFilter.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtFilter.getText().equals(placeholder)) {
                    txtFilter.setText("");
                    txtFilter.setForeground(topFG);
                    clearSelection();
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtFilter.getText().isEmpty()) {
                    txtFilter.setForeground(Color.GRAY);
                    txtFilter.setText(placeholder);
                    InventoryListService.fillTable(list, table);
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
                InventoryListService.fillTable(filter, table);
            }
        });
    }

    // Search
    private void registerSearch() {
        String placeholder = "Search";
        txtSearch = new JTextField(placeholder);
        txtSearch.setForeground(Color.GRAY);
        txtSearch.setBackground(topBG);
        txtSearch.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
        txtSearch.setFont(txtFont);
        txtSearch.setCaretColor(topFG);
        txtSearch.setBounds(57, 172, 177, 29);
        panelTop.add(txtSearch);
        txtSearch.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtSearch.getText().equals(placeholder)) {
                    txtSearch.setText("");
                    txtSearch.setForeground(topFG);
                    clearSelection();
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtSearch.getText().isEmpty()) {
                    txtSearch.setForeground(Color.GRAY);
                    txtSearch.setText(placeholder);
                    InventoryListService.fillTable(list, table);
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
                InventoryListService.fillTable(filter, table);
            }
        });
    }

    // Sort Function
    private void registerSortCheckBox() {

        ArrayList<Vehicle> tmp = new ArrayList<>(list);
        checkBoxGroup = new ArrayList<JCheckBox>();
        chckbxEngine = new JCheckBox("Engine");
        chckbxEngine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSelectedCheckBox(chckbxEngine);
                fillTableAfterSorting(tmp);
            }
        });
        chckbxEngine.setFont(checkbxFont);
        chckbxEngine.setBackground(topBG);
        chckbxEngine.setForeground(topFG);
        chckbxEngine.setBounds(591, 75, 97, 29);
        panelTop.add(chckbxEngine);

        chckbxYear = new JCheckBox("Year");
        chckbxYear.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setSelectedCheckBox(chckbxYear);
                fillTableAfterSorting(tmp);
            }
        });
        chckbxYear.setFont(checkbxFont);
        chckbxYear.setBackground(topBG);
        chckbxYear.setForeground(topFG);
        chckbxYear.setBounds(695, 75, 83, 29);
        panelTop.add(chckbxYear);

        chckbxId = new JCheckBox("Id");
        chckbxId.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setSelectedCheckBox(chckbxId);
                fillTableAfterSorting(tmp);
            }
        });
        chckbxId.setFont(checkbxFont);
        chckbxId.setBackground(topBG);
        chckbxId.setForeground(topFG);
        chckbxId.setBounds(385, 75, 112, 29);
        panelTop.add(chckbxId);
        
        chckbxExteriorColor = new JCheckBox("ExteriorColor");
        chckbxExteriorColor.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setSelectedCheckBox(chckbxExteriorColor);
                fillTableAfterSorting(tmp);
            }
        });
        chckbxExteriorColor.setFont(checkbxFont);
        chckbxExteriorColor.setBackground(topBG);
        chckbxExteriorColor.setForeground(topFG);
        chckbxExteriorColor.setBounds(785, 111, 165, 29);
        panelTop.add(chckbxExteriorColor);
        
        chckbxInteriorColor = new JCheckBox("InteriorColor");
        chckbxInteriorColor.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setSelectedCheckBox(chckbxInteriorColor);
                fillTableAfterSorting(tmp);
            }
        });
        chckbxInteriorColor.setFont(checkbxFont);
        chckbxInteriorColor.setBackground(topBG);
        chckbxInteriorColor.setForeground(topFG);
        chckbxInteriorColor.setBounds(785, 75, 165, 29);
        panelTop.add(chckbxInteriorColor);

        chckbxPrice = new JCheckBox("Price");
        chckbxPrice.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setSelectedCheckBox(chckbxPrice);
                fillTableAfterSorting(tmp);
            }
        });
        chckbxPrice.setFont(checkbxFont);
        chckbxPrice.setBackground(topBG);
        chckbxPrice.setForeground(topFG);
        chckbxPrice.setBounds(695, 111, 97, 29);
        panelTop.add(chckbxPrice);

        chckbxMake = new JCheckBox("Make");
        chckbxMake.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setSelectedCheckBox(chckbxMake);
                fillTableAfterSorting(tmp);
            }
        });
        chckbxMake.setFont(checkbxFont);
        chckbxMake.setBackground(topBG);
        chckbxMake.setForeground(topFG);
        chckbxMake.setBounds(501, 75, 83, 29);
        panelTop.add(chckbxMake);

        chckbxCategory = new JCheckBox("Category");
        chckbxCategory.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setSelectedCheckBox(chckbxCategory);
                fillTableAfterSorting(tmp);
            }
        });
        chckbxCategory.setFont(checkbxFont);
        chckbxCategory.setBackground(topBG);
        chckbxCategory.setForeground(topFG);
        chckbxCategory.setBounds(385, 111, 112, 29);
        panelTop.add(chckbxCategory);

        chckbxModel = new JCheckBox("Model");
        chckbxModel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setSelectedCheckBox(chckbxModel);
                fillTableAfterSorting(tmp);
            }
        });
        chckbxModel.setFont(checkbxFont);
        chckbxModel.setBackground(topBG);
        chckbxModel.setForeground(topFG);
        chckbxModel.setBounds(501, 111, 87, 29);
        panelTop.add(chckbxModel);

        chckbxBodytype = new JCheckBox("Type");
        chckbxBodytype.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setSelectedCheckBox(chckbxBodytype);
                fillTableAfterSorting(tmp);
            }
        });
        chckbxBodytype.setFont(checkbxFont);
        chckbxBodytype.setBackground(topBG);
        chckbxBodytype.setForeground(topFG);
        chckbxBodytype.setBounds(591, 111, 83, 29);
        panelTop.add(chckbxBodytype);

        checkBoxGroup.add(chckbxCategory);
        checkBoxGroup.add(chckbxId);
        checkBoxGroup.add(chckbxMake);
        checkBoxGroup.add(chckbxModel);
        checkBoxGroup.add(chckbxPrice);
        checkBoxGroup.add(chckbxBodytype);
        checkBoxGroup.add(chckbxEngine);
        checkBoxGroup.add(chckbxYear);
        checkBoxGroup.add(chckbxExteriorColor);
        checkBoxGroup.add(chckbxInteriorColor);
    }

    // ADD Table
    private void registerTable() {
        String[] headers = { "Id", "WebId", "Category", "Year", "Make", "Model", "Trim", "Bodytype", "Price", "Photo","Vin","Entertainment"
                ,"InteriorColor","ExteriorColor","Fueltype","Engine","Transmission","Battery","OptionalFeatures"};
        Object[][] cellData = null;

        model = new DefaultTableModel(cellData, headers) {
            public boolean isCellEditable(int row, int column) {
                return false;
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
            //Photo
            case 9:
                column.setMinWidth(150);
                column.setMaxWidth(150);
                continue;
            //Vin
            case 10:
                column.setMinWidth(150);
                column.setMaxWidth(150);
                continue;
            //Entertainment
            case 11:
                column.setMinWidth(400);
                column.setMaxWidth(400);
                continue;
            //InteriorColor
            case 12:
                column.setMinWidth(150);
                column.setMaxWidth(150);
                continue;
            //ExteriorColor
            case 13:
                column.setMinWidth(150);
                column.setMaxWidth(150);
                continue;
            //Fueltype
            case 14:
                column.setMinWidth(100);
                column.setMaxWidth(100);
                continue;
            //Engine
            case 15:
                column.setMinWidth(250);
                column.setMaxWidth(250);
                continue;
            //Transmission
            case 16:
                column.setMinWidth(250);
                column.setMaxWidth(250);
                continue;
            //Battery
            case 17:
                column.setMinWidth(100);
                column.setMaxWidth(100);
                continue;
            //OptionalFeatures
            case 18:
                column.setMinWidth(350);
                column.setMaxWidth(350);
                continue;
            }
        }
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        InventoryListService.fillTable(list, table);
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
        labelTitleIcon.setBounds(43, 11, 130, 129);
        panelTop.add(labelTitleIcon);
        labelTitle = new JLabel("Inventory List Management");
        labelTitle.setFont(titleFont);
        labelTitle.setForeground(topFG);
        labelTitle.setBounds(199, 12, 481, 61);
        panelTop.add(labelTitle);
    }

    // ADD DELETE EDIT BTN
    private void registerAEDBtn() {
        btnAdd = new JButton("Add");
        btnAdd.setBorderPainted(false);
        InventoryListUI that = this; // team 2: Lu Niu
        btnAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (getSelectedId() == null) {
                    InventoryEditUI tempui = new InventoryEditUI();
                } else {
                    for (Vehicle v : list) {
                        if (v.getID().equals(getSelectedId())) {
                            InventoryEditUI inventoryEditUI = new InventoryEditUI(v, that);
                        }
                    }
                }

            }
        });
        btnAdd.setFont(new Font("Segoe UI Historic", Font.PLAIN, 25));
        btnAdd.setForeground(new Color(255, 255, 255));
        btnAdd.setBackground(btnColor);
        btnAdd.setBounds(545, 165, 110, 40);
        panelTop.add(btnAdd);

        btnDelete = new JButton("Delete");
        btnDelete.setBorderPainted(false);
         //team2: yuanyuan jin start
        btnDelete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                VehicleImple service = new VehicleImple() ;
                        int reply = JOptionPane.showConfirmDialog(null, "Confirm delete ?", "Delete", JOptionPane.YES_NO_OPTION);
                        if (reply == JOptionPane.YES_OPTION) {
                         //delete selected data
                            for (Vehicle v : list) {
                                if (v.getID().equals(getSelectedId())) {
                                    service.deleteVehicle(v.getWebID(),v.getID());                               
                                }                               
                            }                       
                        }                
            }                    
                });
        
       //team 2 Yuanyuan jin end
        btnDelete.setFont(new Font("Segoe UI Historic", Font.PLAIN, 25));
        btnDelete.setForeground(new Color(255, 255, 255));
        btnDelete.setBackground(btnColor);
        btnDelete.setBounds(805, 165, 110, 40);
        panelTop.add(btnDelete);

        btnEdit = new JButton("Edit");
        btnEdit.setBorderPainted(false);
        btnEdit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // team 2: Lu Niu
                InventoryEditUI imf = new InventoryEditUI(getSelectedVehicle(), that);
            }
        });
        btnEdit.setFont(new Font("Segoe UI Historic", Font.PLAIN, 25));
        btnEdit.setForeground(new Color(255, 255, 255));
        btnEdit.setBackground(btnColor);
        btnEdit.setBounds(675, 165, 110, 40);
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

    private void registerRadio() {
        ArrayList<Vehicle> tmp = new ArrayList<>(list);
        rdbtnHighToLow = new JRadioButton("Sort High To Low");
        rdbtnHighToLow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (rdbtnHighToLow.isSelected()) {
                    isAscending = false;
                    ArrayList<Vehicle> sortList = filter.size() == 0 ? tmp : filter;
                    if (sortByCheckState(sortList)) {
                        InventoryListService.fillTable(sortList, table);
                    } else {
                        InventoryListService.fillTable(list, table);
                    }
                }
            }
        });
        rdbtnHighToLow.setFont(radioFont);
        rdbtnHighToLow.setBounds(198, 75, 178, 29);
        rdbtnHighToLow.setBackground(topBG);
        rdbtnHighToLow.setForeground(topFG);

        rdbtnLowToHigh = new JRadioButton("Sort Low To High");
        rdbtnLowToHigh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isAscending = true;
                ArrayList<Vehicle> sortList = filter.size() == 0 ? tmp : filter;
                if (sortByCheckState(sortList)) {
                    InventoryListService.fillTable(sortList, table);
                } else {
                    InventoryListService.fillTable(list, table);
                }
            }
        });
        rdbtnLowToHigh.setFont(radioFont);
        rdbtnLowToHigh.setBounds(198, 111, 178, 29);
        rdbtnLowToHigh.setBackground(topBG);
        rdbtnLowToHigh.setForeground(topFG);

        sortGroup = new ButtonGroup();
        sortGroup.add(rdbtnHighToLow);
        sortGroup.add(rdbtnLowToHigh);

        panelTop.add(rdbtnHighToLow);
        panelTop.add(rdbtnLowToHigh);
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

    // clear Selected CheckBox;
    private void clearSelection() {
        for (JCheckBox cbx : checkBoxGroup) {
            cbx.setSelected(false);
        }
    }

    // set Selected to CheckBox;
    private void setSelectedCheckBox(JCheckBox c) {
        for (JCheckBox cbx : checkBoxGroup) {
            if (cbx == c)
                continue;
            cbx.setSelected(false);
        }
    }

    // fill table using sort result;
    private void fillTableAfterSorting(ArrayList<Vehicle> tmp) {
        ArrayList<Vehicle> sortList = filter.size() == 0 ? tmp : filter;
        if (sortByCheckState(sortList)) {
            InventoryListService.fillTable(sortList, table);
        } else {
            InventoryListService.fillTable(list, table);
        }
    }

 // check State
    private boolean sortByCheckState(ArrayList<Vehicle> sortList) {
        if (chckbxEngine.isSelected()) {
            InventoryListService.sortByEngine(sortList, isAscending);
            return true;

        } else if (chckbxCategory.isSelected()) {
            InventoryListService.sortByCategory(sortList, isAscending);
            return true;

        } else if (chckbxId.isSelected()) {
            InventoryListService.sortById(sortList, isAscending);
            return true;

        } else if (chckbxMake.isSelected()) {
            InventoryListService.sortByMake(sortList, isAscending);
            return true;

        } else if (chckbxModel.isSelected()) {
            InventoryListService.sortByModel(sortList, isAscending);
            return true;

        } else if (chckbxPrice.isSelected()) {
            InventoryListService.sortByPrice(sortList, isAscending);
            return true;

        } else if (chckbxBodytype.isSelected()) {
            InventoryListService.sortByType(sortList, isAscending);
            return true;

        } else if (chckbxYear.isSelected()) {
            InventoryListService.sortByYear(sortList, isAscending);
            return true;
        } else if (chckbxExteriorColor.isSelected()) {
            InventoryListService.sortByExteriorColor(sortList, isAscending);
            return true;
        } else if (chckbxInteriorColor.isSelected()) {
            InventoryListService.sortByInteriorColor(sortList, isAscending);
            return true;
        } 
        return false;
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
            this.setForeground(Color.BLACK);
            table.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            this.setText(value.toString());
            if (row % 2 == 0)
                setBackground(tableEvenRow);
            if (row % 2 == 1)
                setBackground(tableOddRow);
            //link style
            if (row == this.row && column == this.col && column == 9) {
                    this.setForeground(Color.RED);
                    table.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    return this;
            }
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
            selectedId = (String) model.getValueAt(selectedRow, 0);
            System.out.println(selectedId);
            Point p = e.getPoint();
            int c = table.columnAtPoint(p);
            if(c != 9){
                return;
            }
            try {
                String s = table.getValueAt(selectedRow, c).toString();
                URL url = new URL(s.split("\"")[1]);
                Desktop.getDesktop().browse(url.toURI());
            } catch (Exception ex) {
                Logger.getLogger(LinkCellRenderer.class.getName()).log(Level.SEVERE, null, ex);
            }
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
    public void refreshTable() {
        list = InventoryListService.readAndGetVehicles(file);
        InventoryListService.fillTable(list, table);
    }
}
