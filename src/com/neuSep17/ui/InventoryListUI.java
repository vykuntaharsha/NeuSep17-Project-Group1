package com.Tianyu.dealerListDisplay;

import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JRadioButton;

public class View extends JFrame {

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
	private JCheckBox chckbxWebId;
	private JCheckBox chckbxId;
	private JCheckBox chckbxPrice;
	private JCheckBox chckbxType;
	private JCheckBox chckbxMake;
	private JCheckBox chckbxModel;
	private JCheckBox chckbxCategory;
	private List<JCheckBox> checkBoxGroup;
	private JLabel labelBG;
	private JLabel labelTitle;
	private JLabel labelTitleIcon;

	private JButton close;
	private JButton min;

	private String selectedId;

	private final Color topBG = new Color(33, 33, 33);
	private final Color topFG = new Color(255, 255, 255);
	private final Color btnColor = new Color(198, 40, 40);
	private final Color tableOddRow = new Color(255,255,255);
	private final Color tableEvenRow = new Color(224,224,224);
	private final Color tableHeaderColor = new Color(117,117,117);

	private final Font checkbxFont = new Font("Segoe UI Historic", Font.ITALIC, 21);
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
					String f = View.class.getResource("asset/test.txt").getPath();
					File file = new File(f);
					View frame = new View(file);
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
	public View(File file) {
		list = Service.readAndGetVehicles(file);
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
		labelBG.setIcon(new ImageIcon(View.class.getResource("/com/Tianyu/dealerListDisplay/asset/2.jpg")));
		labelBG.setBounds(0, 0, 350, 800);
		leftPanel.add(labelBG);

		// Top Panel
		panelTop = new JPanel();
		panelTop.setBackground(topBG);
		panelTop.setBounds(350, 0, 950, 220);
		panelTop.setLayout(null);
		contentPane.add(panelTop);
	}
	
	//Filter
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
					Service.fillTable(list,table);
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
				filter = Service.filter(filter, list, txtFilter);
				Service.fillTable(filter,table);
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
					Service.fillTable(list,table);
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
				filter = Service.search(filter, list, txtSearch);
				Service.fillTable(filter,table);
			}
		});
	}

	// Sort Function
	private void registerSortCheckBox() {
		
		ArrayList<Vehicle> tmp = new ArrayList<>(list);
		checkBoxGroup = new ArrayList<JCheckBox>();
		chckbxWebId = new JCheckBox("WebId");
		chckbxWebId.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setSelectedCheckBox(chckbxWebId);
				fillTableAfterSorting(tmp);
			}
		});
		chckbxWebId.setFont(checkbxFont);
		chckbxWebId.setBackground(topBG);
		chckbxWebId.setForeground(topFG);
		chckbxWebId.setBounds(433, 75, 134, 29);
		panelTop.add(chckbxWebId);

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
		chckbxYear.setBounds(585, 75, 113, 29);
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
		chckbxId.setBounds(712, 75, 93, 29);
		panelTop.add(chckbxId);

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
		chckbxPrice.setBounds(822, 75, 97, 29);
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
		chckbxMake.setBounds(822, 111, 105, 29);
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
		chckbxCategory.setBounds(433, 111, 134, 29);
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
		chckbxModel.setBounds(585, 111, 113, 29);
		panelTop.add(chckbxModel);

		chckbxType = new JCheckBox("Type");
		chckbxType.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setSelectedCheckBox(chckbxType);
				fillTableAfterSorting(tmp);
			}
		});
		chckbxType.setFont(checkbxFont);
		chckbxType.setBackground(topBG);
		chckbxType.setForeground(topFG);
		chckbxType.setBounds(712, 111, 93, 29);
		panelTop.add(chckbxType);
		
		checkBoxGroup.add(chckbxCategory);
		checkBoxGroup.add(chckbxId);
		checkBoxGroup.add(chckbxMake);
		checkBoxGroup.add(chckbxModel);
		checkBoxGroup.add(chckbxPrice);
		checkBoxGroup.add(chckbxType);
		checkBoxGroup.add(chckbxWebId);
		checkBoxGroup.add(chckbxYear);
	}

	// ADD Table
	private void registerTable() {
		String[] headers = { "Id", "WebId", "Category", "Year", "Make", "Model", "Trim", "Type", "Price", "Photo" };
		Object[][] cellData = null;

		DefaultTableModel model = new DefaultTableModel(cellData, headers) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table = new JTable(model);
		TableColumn column = null;
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		for (int i = 0; i < headers.length; i++) {
			column = table.getColumnModel().getColumn(i);
			switch (i) {
			case 0:
				column.setMinWidth(100);
				column.setMaxWidth(100);
				continue;
			case 1:
				column.setMinWidth(100);
				column.setMaxWidth(100);
				continue;
			case 2:
				column.setMinWidth(80);
				column.setMaxWidth(80);
				continue;
			case 3:
				column.setMinWidth(50);
				column.setMaxWidth(50);
				continue;
			case 4:
				column.setMinWidth(100);
				column.setMaxWidth(100);
				continue;
			case 5:
				column.setMinWidth(200);
				column.setMaxWidth(200);
				continue;
			case 6:
				column.setMinWidth(300);
				column.setMaxWidth(300);
				continue;
			case 7:
				column.setMinWidth(150);
				column.setMaxWidth(150);
				continue;
			case 8:
				column.setMinWidth(100);
				column.setMaxWidth(100);
				continue;
			case 9:
				column.setMinWidth(550);
				column.setMaxWidth(550);
				continue;
			}
		}
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int selectedRow = table.getSelectedRow();
				selectedId = (String) model.getValueAt(selectedRow, 0);
				System.out.println(selectedId);
			}
		});
		
		Service.fillTable(list,table);
		JTableHeader tableHeader = table.getTableHeader();
		tableHeader.setReorderingAllowed(false);
		tableHeader.setBackground(tableHeaderColor);
		tableHeader.setForeground(topFG);
		tableHeader.setFont(tableHeaderFont);
		table.setShowGrid(false);
		table.setIntercellSpacing(new Dimension(0,0));
		table.setRowMargin(0);
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(350, 220, 950, 580);
		table.setPreferredScrollableViewportSize(new Dimension(1950, 580));
		// set ceter alignment;
		DefaultTableCellRenderer tablecell = new DefaultTableCellRenderer() {
			 @Override  
             public Component getTableCellRendererComponent(JTable table,  
                     Object value, boolean isSelected, boolean hasFocus,  
                     int row, int column) {  
                 if (row % 2 == 0)  
                     setBackground(tableEvenRow); 
                 else if (row % 2 == 1)  
                     setBackground(tableOddRow);  
                 return super.getTableCellRendererComponent(table, value,  
                         isSelected, hasFocus, row, column);  
             }  
		};
		tablecell.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, tablecell);
		// set horizon scroll;
		scrollPane.setAutoscrolls(true);
		contentPane.add(scrollPane);

		scrollPane.setViewportView(table);
	}
	
	//Title and Icon
	private void registerTitle() {
		labelTitleIcon = new JLabel("");
		labelTitleIcon.setIcon(new ImageIcon(View.class.getResource("/com/Tianyu/dealerListDisplay/asset/home.png")));
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
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});
		btnAdd.setFont(new Font("Segoe UI Historic", Font.PLAIN, 25));
		btnAdd.setForeground(new Color(255, 255, 255));
		btnAdd.setBackground(btnColor);
		btnAdd.setBounds(545, 165, 110, 40);
		panelTop.add(btnAdd);

		btnDelete = new JButton("Delete");
		btnDelete.setBorderPainted(false);
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				for (Vehicle v : list) {
					if (v.id.equals(getSelectedId())) {

					}
				}
			}
		});
		btnDelete.setFont(new Font("Segoe UI Historic", Font.PLAIN, 25));
		btnDelete.setForeground(new Color(255, 255, 255));
		btnDelete.setBackground(btnColor);
		btnDelete.setBounds(805, 165, 110, 40);
		panelTop.add(btnDelete);

		btnEdit = new JButton("Edit");
		btnEdit.setBorderPainted(false);
		JFrame that = this;
		btnEdit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			    // Gets selected vehicle.
			    for (Vehicle v : list) {
                    if (v.id.equals(getSelectedId())) {                      
                        InventoryEditUI imf = new InventoryEditUI(v);
                        that.dispose();
                    }
                }
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
		close.setIcon(new ImageIcon(View.class.getResource("/com/Tianyu/dealerListDisplay/asset/close.png")));
		close.setBounds(905, 12, 32, 38);
		panelTop.add(close);

		min = new JButton("");
		min.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				minimize();
			}
		});
		min.setIcon(new ImageIcon(View.class.getResource("/com/Tianyu/dealerListDisplay/asset/min.png")));
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
				if(rdbtnHighToLow.isSelected()) {
					isAscending = false;
					ArrayList<Vehicle> sortList = filter.size() == 0? tmp : filter;
					if(sortByCheckState(sortList)) {
						Service.fillTable(sortList, table);
					}else {
						Service.fillTable(list,table);
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
				ArrayList<Vehicle> sortList = filter.size() == 0? tmp : filter;
				if(sortByCheckState(sortList)) {
					Service.fillTable(sortList, table);
				}else {
					Service.fillTable(list,table);
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
	
	//clear Selected CheckBox;
	private void clearSelection() {
		for(JCheckBox cbx : checkBoxGroup) {
			cbx.setSelected(false);
		}
	}
	
	//set Selected to CheckBox;
	private void setSelectedCheckBox(JCheckBox c) {
		for(JCheckBox cbx : checkBoxGroup) {
			if(cbx == c) continue;
			cbx.setSelected(false);
		}
	}
	
	//fill table using sort result;
	private void fillTableAfterSorting(ArrayList<Vehicle> tmp) {
		ArrayList<Vehicle> sortList = filter.size() == 0? tmp : filter;
		if(sortByCheckState(sortList)) {
			Service.fillTable(sortList,table);
		}else {
			Service.fillTable(list,table);
		}
	}
	
	//check State
	private boolean sortByCheckState(ArrayList<Vehicle> sortList) {
		if (chckbxWebId.isSelected()) {
			Service.sortByWebId(sortList,isAscending);
			return true;
			
		} else if (chckbxCategory.isSelected()) {
			Service.sortByCategory(sortList,isAscending);
			return true;
			
		} else if (chckbxId.isSelected()) {
			Service.sortById(sortList,isAscending);
			return true;
			
		} else if (chckbxMake.isSelected()) {
			Service.sortByMake(sortList,isAscending);
			return true;
			
		} else if (chckbxModel.isSelected()) {
			Service.sortByModel(sortList,isAscending);
			return true;
			
		} else if (chckbxPrice.isSelected()) {
			Service.sortByPrice(sortList,isAscending);
			return true;
			
		} else if (chckbxType.isSelected()) {
			Service.sortByType(sortList,isAscending);
			return true;
			
		} else if (chckbxYear.isSelected()) {
			Service.sortByYear(sortList,isAscending);
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
}
