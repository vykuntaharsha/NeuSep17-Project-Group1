package com.neuSep17.ui;

import com.neuSep17.dto.*;
import com.neuSep17.utility.InventoryBrowseUtility;
import com.neuSep17.service.InventoryListService;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class InventoryBrowseUI implements ActionListener {
    InventoryBrowseUtility utilityObject=new InventoryBrowseUtility();
    InventoryListService inventoryServiceObject = new InventoryListService();
    Collection<Vehicle> vehicles;
    ArrayList<Vehicle> vehicleList;

    final int WIDTH = 1300;
    final int HEIGHT = 700;

    int startingIndexToDisplay = 5;
    int pageNumber=1;
    ArrayList<Vehicle> filterVehicleList = new ArrayList<>();
    int countOfResultsDisplayed = 0;

    JFrame browseInventoryFrame;
    JPanel contentPane;
    JTextField searchTextField;
    JButton searchButton, previousPageNavigateButton, nextPageNavigateButton, sortButton, filterButton;
    JComboBox categorySelect, makeSelect, typeSelect, yearSelect, priceRangeSelect, sortTypeSelect, sortValueSelect;
    JLabel makeLabel, typeLabel, yearLabel, priceLabel, categoryLabel, sortLabel, navigationLabel;
    JPanel searchPanel, filterOptionsPanel, filterResultMainPanel, navigationOptionsPanel, firstImagePanel, secondImagePanel, thirdImagePanel;
    List<JPanel> imagePanelObjectsList;

    public InventoryBrowseUI(String dealerID) throws IOException {
        InventoryBrowseUtility utilityObject = new InventoryBrowseUtility();
        vehicles = utilityObject.setObjectsforUtility(dealerID);
        vehicleList = new ArrayList<>(vehicles);

        initializeJFrame();
        initializeJButtons();
        initializeJComboBox();
        initializeJLabel();
        initializeJPanel();
        initializeJTextField();
        settingLayout();
        addingComponents();
        addListeners();
    }


    void initializeJFrame() {
        browseInventoryFrame = new JFrame("Find Your Dream Car");
        browseInventoryFrame.setSize(WIDTH, HEIGHT);
        browseInventoryFrame.setLocation(100, 150);
        browseInventoryFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        browseInventoryFrame.setVisible(true);
        contentPane = new JPanel();
        browseInventoryFrame.setContentPane(contentPane);
    }

    void initializeJButtons() {
        searchButton = new JButton("Search");
        filterButton = new JButton("FILTER");
        sortButton = new JButton("SORT");
        previousPageNavigateButton = new JButton(" << ");
        nextPageNavigateButton = new JButton(" >> ");

        searchButton.setBounds(500, 14, 100, 35);
        filterButton.setBounds(40, 200, 100, 35);
        sortButton.setBounds(1010, 14, 100, 30);
        previousPageNavigateButton.setBounds(500, 0, 80, 30);
        nextPageNavigateButton.setBounds(700, 0, 80, 30);

        previousPageNavigateButton.setEnabled(false);
        nextPageNavigateButton.setEnabled(true);
    }

    void initializeJComboBox() {
        String property;
        String[] category = {"NONE", "NEW", "USED", "CERTIFIED PRE OWNED"};
        categorySelect = new JComboBox(category);
        property = "make";
        String[] make = findUniqueVehiclePropertyValues(vehicles, property);
        makeSelect = new JComboBox(make);
        makeSelect.setSelectedItem("NONE");
        property = "type";
        String[] type = findUniqueVehiclePropertyValues(vehicles, property);
        typeSelect = new JComboBox(type);
        typeSelect.setSelectedItem("NONE");
        property = "year";
        String[] year = findUniqueVehiclePropertyValues(vehicles, property);
        yearSelect = new JComboBox(year);
        yearSelect.setSelectedItem("NONE");
        String[] price = {"NONE", "0-10000", "10000-20000", "20000-30000", "30000-40000", "above 40000"};
        priceRangeSelect = new JComboBox(price);

        String[] sortType = {"NONE", "PRICE HIGH TO LOW", "PRICE LOW TO HIGH", "YEAR LOW TO HIGH", "YEAR HIGH TO LOW"};
        sortTypeSelect = new JComboBox(sortType);

        categorySelect.setBounds(120, 10, 170, 20);
        makeSelect.setBounds(120, 50, 170, 20);
        typeSelect.setBounds(120, 90, 170, 20);
        yearSelect.setBounds(120, 130, 170, 20);
        priceRangeSelect.setBounds(120, 170, 170, 20);
        sortTypeSelect.setBounds(790, 14, 200, 30);

    }

    void initializeJLabel() {
        categoryLabel = new JLabel("CATEGORY");
        makeLabel = new JLabel("MAKE");
        typeLabel = new JLabel("TYPE");
        yearLabel = new JLabel("YEAR");
        priceLabel = new JLabel("PRICE");
        sortLabel = new JLabel("SORT BY");
        navigationLabel = new JLabel("Page "+pageNumber);


        categoryLabel.setBounds(40, 10, 80, 20);
        makeLabel.setBounds(40, 50, 40, 20);
        typeLabel.setBounds(40, 90, 40, 20);
        yearLabel.setBounds(40, 130, 40, 20);
        priceLabel.setBounds(40, 170, 40, 20);
        sortLabel.setBounds(730, 14, 50, 30);
        navigationLabel.setBounds(620, 0, 100, 30);

    }

    void initializeJPanel() {
        searchPanel = new JPanel();
        filterOptionsPanel = new JPanel();
        navigationOptionsPanel = new JPanel();

        searchPanel.setBounds(0, 0, 1200, 85);
        filterOptionsPanel.setBounds(0, 95, 350, 250);
        navigationOptionsPanel.setBounds(0, 600, 800, 50);
    }

    void initializeJTextField() {
        searchTextField = new JTextField();
        searchTextField.setBounds(175, 14, 300, 35);

    }

    void settingLayout() {
        browseInventoryFrame.setLayout(null);
        searchPanel.setLayout(null);
        filterOptionsPanel.setLayout(null);
        navigationOptionsPanel.setLayout(null);

    }

    void addingComponents() throws IOException {
        searchPanel.add(searchTextField);
        searchPanel.add(searchButton);

        searchPanel.add(sortLabel);
        searchPanel.add(sortTypeSelect);
        searchPanel.add(sortButton);

        filterOptionsPanel.add(categoryLabel);
        filterOptionsPanel.add(categorySelect);
        filterOptionsPanel.add(makeLabel);
        filterOptionsPanel.add(makeSelect);
        filterOptionsPanel.add(typeLabel);
        filterOptionsPanel.add(typeSelect);
        filterOptionsPanel.add(yearLabel);
        filterOptionsPanel.add(yearSelect);
        filterOptionsPanel.add(priceLabel);
        filterOptionsPanel.add(priceRangeSelect);
        filterOptionsPanel.add(filterButton);

        navigationOptionsPanel.add(previousPageNavigateButton);
        navigationOptionsPanel.add(navigationLabel);
        navigationOptionsPanel.add(nextPageNavigateButton);

        contentPane.add(searchPanel);
        contentPane.add(filterOptionsPanel);
        contentPane.add(navigationOptionsPanel);

        /** Displaying all the vehicles of a particular Dealer **/
        addFilterResultPanel();
        imagePanelObjectsList = createResultsPanel(5);
        try {
            display(0, vehicleList, imagePanelObjectsList);
            countOfResultsDisplayed=countOfResultsDisplayed+5;
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private String[] findUniqueVehiclePropertyValues(Collection<Vehicle> vehicles, String property) {
        Set<String> vehiclePropertySet = new HashSet<String>();
        vehiclePropertySet.add("NONE");
        for (Vehicle v : vehicles) {
            if (property.equals("make") && v.getMake() != "") {
                vehiclePropertySet.add(v.getMake().toUpperCase());
            } else if (property.equals("type") && v.getBodyType().length() != 0) {
                vehiclePropertySet.add(v.getBodyType().toUpperCase());
            } else if (property.equals("year") && v.getBodyType() != "") {
                vehiclePropertySet.add(Integer.toString(v.getYear()));
            }
        }

        return vehiclePropertySet.toArray(new String[vehiclePropertySet.size()]);
    }

    void addListeners() {
        searchButton.addActionListener(this);
        filterButton.addActionListener(this);
        previousPageNavigateButton.addActionListener(this);
        nextPageNavigateButton.addActionListener(this);
        sortButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        List<JPanel> imagePanelObjectsList;
        JLabel filterResultLabel = new JLabel("SEARCH RESULTS");
        filterResultLabel.setSize(200, 20);
        filterResultLabel.setBounds(300, 40, 200, 20);

        /** Performing appropriate Actions Based on the Event occurred **/
        if (e.getSource() == searchButton) {
            countOfResultsDisplayed=0;
            previousPageNavigateButton.setEnabled(true);
            nextPageNavigateButton.setEnabled(true);
            resetFilterValues();
            if (!searchTextField.getText().isEmpty()) {
                filterVehicleList = inventoryServiceObject.search(filterVehicleList, vehicleList, searchTextField);
                if (sortTypeSelect.getSelectedItem().toString().contains("YEAR")) {
                    utilityObject.sortByYear(filterVehicleList, (sortTypeSelect.getSelectedItem().toString() == "YEAR LOW TO HIGH"));
                } else if (sortTypeSelect.getSelectedItem().toString().contains("PRICE")) {
                    utilityObject.sortByPrice(filterVehicleList, (sortTypeSelect.getSelectedItem().toString() == "PRICE LOW TO HIGH"));
                }
                try {
                    removeCurrentResultPanel();
                    addFilterResultPanel();
                    imagePanelObjectsList = createResultsPanel(5);
                    filterResultMainPanel.add(filterResultLabel);
                    display(0, filterVehicleList, imagePanelObjectsList);
                    pageNumber=1;
                    navigationLabel.setText("Page "+pageNumber);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } else if (e.getSource() == filterButton) {
            countOfResultsDisplayed=0;
            nextPageNavigateButton.setEnabled(true);
            if (filterVehicleList.size() != 0) {
                filterVehicleList = utilityObject.filterVehicles(filterVehicleList, getFilterValues());
            } else {
                filterVehicleList = utilityObject.filterVehicles(vehicleList, getFilterValues());
            }
            if (sortTypeSelect.getSelectedItem().toString().contains("YEAR")) {
                utilityObject.sortByYear(filterVehicleList, (sortTypeSelect.getSelectedItem().toString() == "YEAR LOW TO HIGH"));
            } else if (sortTypeSelect.getSelectedItem().toString().contains("PRICE")) {
                utilityObject.sortByPrice(filterVehicleList, (sortTypeSelect.getSelectedItem().toString() == "PRICE LOW TO HIGH"));
            }
            removeCurrentResultPanel();
            addFilterResultPanel();
            imagePanelObjectsList = createResultsPanel(5);
            try {
                filterResultMainPanel.add(filterResultLabel);
                display(0, filterVehicleList, imagePanelObjectsList);
                pageNumber=1;
                navigationLabel.setText("Page "+pageNumber);
                if(countOfResultsDisplayed>filterVehicleList.size()){
                    nextPageNavigateButton.setEnabled(false);
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else if (e.getSource() == sortButton) {
            nextPageNavigateButton.setEnabled(true);
            countOfResultsDisplayed=0;
            removeCurrentResultPanel();
            addFilterResultPanel();
            imagePanelObjectsList = createResultsPanel(5);
            if (sortTypeSelect.getSelectedItem().toString().contains("YEAR")) {
                if (filterVehicleList.size() != 0) {
                    try {
                        utilityObject.sortByYear(filterVehicleList, (sortTypeSelect.getSelectedItem().toString() == "YEAR LOW TO HIGH"));
                        filterResultMainPanel.add(filterResultLabel);
                        display(0, filterVehicleList, imagePanelObjectsList);
                        pageNumber=1;
                        navigationLabel.setText("Page "+pageNumber);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    try {
                        utilityObject.sortByYear(vehicleList, (sortTypeSelect.getSelectedItem().toString() == "YEAR LOW TO HIGH"));
                        filterResultMainPanel.add(filterResultLabel);
                        display(0, vehicleList, imagePanelObjectsList);
                        pageNumber=1;
                        navigationLabel.setText("Page "+pageNumber);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }

            } else if (sortTypeSelect.getSelectedItem().toString().contains("PRICE")) {
                if (filterVehicleList.size() != 0) {
                    try {
                        utilityObject.sortByPrice(filterVehicleList, (sortTypeSelect.getSelectedItem().toString() == "PRICE LOW TO HIGH"));
                        filterResultMainPanel.add(filterResultLabel);
                        display(0, filterVehicleList, imagePanelObjectsList);
                        pageNumber=1;
                        navigationLabel.setText("Page "+pageNumber);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    try {
                        utilityObject.sortByPrice(vehicleList, (sortTypeSelect.getSelectedItem().toString() == "PRICE LOW TO HIGH"));
                        filterResultMainPanel.add(filterResultLabel);
                        pageNumber=1;
                        navigationLabel.setText("Page "+pageNumber);
                        display(0, vehicleList, imagePanelObjectsList);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        } else if (e.getSource() == previousPageNavigateButton) {
            nextPageNavigateButton.setEnabled(true);
            startingIndexToDisplay = startingIndexToDisplay - 10;
            pageNumber--;
            removeCurrentResultPanel();
            addFilterResultPanel();
            imagePanelObjectsList = createResultsPanel(5);
            try {
                if (filterVehicleList.size() != 0) {
                    filterResultMainPanel.add(filterResultLabel);
                    display(startingIndexToDisplay, filterVehicleList, imagePanelObjectsList);
                } else {
                    display(startingIndexToDisplay, vehicleList, imagePanelObjectsList);
                }
                navigationLabel.setText("Page "+pageNumber);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            if (startingIndexToDisplay == 5) {
                previousPageNavigateButton.setEnabled(false);
            }
        } else if (e.getSource() == nextPageNavigateButton) {
            previousPageNavigateButton.setEnabled(true);
            pageNumber++;
            try {
                removeCurrentResultPanel();
                addFilterResultPanel();
                imagePanelObjectsList = createResultsPanel(5);
                if (filterVehicleList.size() != 0) {
                    filterResultMainPanel.add(filterResultLabel);
                    display(startingIndexToDisplay, filterVehicleList, imagePanelObjectsList);
                    startingIndexToDisplay = startingIndexToDisplay + 5;
                    navigationLabel.setText("Page "+pageNumber);
                } else {
                    display(startingIndexToDisplay, vehicleList, imagePanelObjectsList);
                    startingIndexToDisplay = startingIndexToDisplay + 5;
                    navigationLabel.setText("Page "+pageNumber);
                    if(countOfResultsDisplayed==vehicleList.size()){
                        nextPageNavigateButton.setEnabled(false);
                    }
                }

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void resetFilterValues() {
        categorySelect.setSelectedItem("NONE");
        makeSelect.setSelectedItem("NONE");
        typeSelect.setSelectedItem("NONE");
        yearSelect.setSelectedItem("NONE");
        priceRangeSelect.setSelectedItem("NONE");
    }

    HashMap<String, String> getFilterValues() {
        HashMap<String, String> filterValuesMap = new HashMap<>();
        filterValuesMap.put("Category", categorySelect.getSelectedItem().toString());
        filterValuesMap.put("Make", makeSelect.getSelectedItem().toString());
        filterValuesMap.put("Type", typeSelect.getSelectedItem().toString());
        filterValuesMap.put("Year", yearSelect.getSelectedItem().toString());
        filterValuesMap.put("Price", priceRangeSelect.getSelectedItem().toString());
        return filterValuesMap;
    }

    public void display(int startIndex, ArrayList<Vehicle> vehiclesToDisplay, List<JPanel> imagePanelObjectsList) throws IOException {
        int counter = 0;
        while (counter < 5 && startIndex < vehiclesToDisplay.size()) {
            try {
                Image image = ImageIO.read(vehiclesToDisplay.get(startIndex).getPhotoURL().openStream());
                JButton imageButton = new JButton(new ImageIcon(image));
                imageButton.setBounds(95, 10, 100, 45);
                imageButton.addActionListener(this);
                imagePanelObjectsList.get(counter).add(imageButton);
            } catch (FileNotFoundException fe) {
                JButton imageButton = new JButton("NO IMAGE");
                imageButton.setBounds(95, 10, 100, 50);
                imagePanelObjectsList.get(counter).add(imageButton);
            }
            JLabel vehicleIDLabel = new JLabel("ID: " + vehiclesToDisplay.get(startIndex).getID());


            vehicleIDLabel.setBounds(200, 10, 100, 20);
            JLabel vehicleCategoryLabel = new JLabel("Category: " + vehiclesToDisplay.get(startIndex).getCategory().toString());
            vehicleCategoryLabel.setBounds(305, 10, 100, 20);
            JLabel vehicleMakeLabel = new JLabel("Make: " + vehiclesToDisplay.get(startIndex).getMake());
            vehicleMakeLabel.setBounds(200, 40, 200, 20);
            JLabel vehicleTypeLabel = new JLabel("Type: " + vehiclesToDisplay.get(startIndex).getBodyType());
            vehicleTypeLabel.setBounds(300, 40, 200, 20);
            JLabel vehiclePriceLabel = new JLabel("Price: " + Double.toString(vehiclesToDisplay.get(startIndex).getPrice()));
            vehiclePriceLabel.setBounds(500, 40, 100, 20);
            JLabel vehicleYearLabel = new JLabel("Year: " + Integer.toString(vehiclesToDisplay.get(startIndex).getYear()));
            vehicleYearLabel.setBounds(600, 40, 100, 20);
            imagePanelObjectsList.get(counter).add(vehicleIDLabel);
            imagePanelObjectsList.get(counter).add(vehicleCategoryLabel);
            imagePanelObjectsList.get(counter).add(vehicleMakeLabel);
            imagePanelObjectsList.get(counter).add(vehicleTypeLabel);
            imagePanelObjectsList.get(counter).add(vehiclePriceLabel);
            imagePanelObjectsList.get(counter).add(vehicleYearLabel);
            counter++;
            startIndex++;
            countOfResultsDisplayed++;
        }
        if (countOfResultsDisplayed >= vehiclesToDisplay.size()) {
                nextPageNavigateButton.setEnabled(false);
            }
    }




    private void removeCurrentResultPanel() {
        contentPane.remove(filterResultMainPanel);
    }

    private void addFilterResultPanel() {
        filterResultMainPanel = new JPanel();
        filterResultMainPanel.setBounds(350, 110, 750, 450);
        filterResultMainPanel.setLayout(null);
        filterResultMainPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        contentPane.add(filterResultMainPanel);
    }

    List<JPanel> createResultsPanel(int vehicleSizetoDisplay) {
        imagePanelObjectsList = new ArrayList<>();
        int y = 75;
        for (int i = 0; i < vehicleSizetoDisplay; i++) {
            JPanel resultImagePanel = new JPanel();
            resultImagePanel.setLayout(null);
            resultImagePanel.setBounds(5, y, 700, 60);
            //    resultImagePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            y = y + 65;
            imagePanelObjectsList.add(resultImagePanel);
            filterResultMainPanel.add(resultImagePanel);
        }

        return imagePanelObjectsList;
    }

}
