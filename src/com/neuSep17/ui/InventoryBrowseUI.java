package com.neuSep17.ui;

import com.neuSep17.dao.PictureManager;
import com.neuSep17.dao.VehicleImple;
import com.neuSep17.dto.Vehicle;
import com.neuSep17.service.DealerImpleService;
import com.neuSep17.service.InventoryListService;
import com.neuSep17.utility.InventoryBrowseUtility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class InventoryBrowseUI implements ActionListener {
    public static final String DEFAULT = "NONE";
    InventoryBrowseUtility utilityObject = new InventoryBrowseUtility();
    InventoryListService inventoryServiceObject = new InventoryListService();
    Collection<Vehicle> vehicles;
    ArrayList<Vehicle> vehicleList;
    String dealer;

    final int WIDTH = 1300;
    final int HEIGHT = 700;

    int pageNumber = 1;
    ArrayList<Vehicle> filterVehicleList = new ArrayList<>();
    ArrayList<Vehicle> searchedVehicleList = new ArrayList<>();

    JFrame browseInventoryFrame;
    JPanel contentPane;
    JTextField searchTextField;
    JButton searchButton, previousPageNavigateButton, nextPageNavigateButton, sortButton, filterButton;
    JComboBox categorySelect, makeSelect, typeSelect, yearSelect, priceRangeSelect, sortTypeSelect;
    JLabel makeLabel, typeLabel, yearLabel, priceLabel, categoryLabel, sortLabel, navigationLabel;
    JPanel searchPanel, filterOptionsPanel, filterResultMainPanel, navigationOptionsPanel;
    List<JPanel> imagePanelObjectsList;
    List<JButton> vehicleIDButtonList = new ArrayList<>();


    public InventoryBrowseUI(String dealerID) throws IOException {
        dealer = dealerID;
        InventoryBrowseUtility utilityObject = new InventoryBrowseUtility();
        vehicles = utilityObject.setObjectsforUtility(dealerID);
        vehicleList = new ArrayList<>(vehicles);

        PictureManager.initDealerPhotoLibrary(dealer, vehicleList);
        initializeJFrame();
        initializeJButtons();
        initializeJComboBox(vehicleList,true);
        initializeJLabel();
        initializeJPanel();
        initializeJTextField();
        settingLayout();
        addingComponents();
        addListeners();
    }

    private void initializeJFrame() {

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        browseInventoryFrame = new JFrame("Find Your Dream Car");
        browseInventoryFrame.setSize(WIDTH, HEIGHT);
        browseInventoryFrame.setLocation(100, 150);
        browseInventoryFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        browseInventoryFrame.setVisible(true);
        contentPane = new JPanel();
        browseInventoryFrame.setContentPane(contentPane);
    }

    private void initializeJButtons() {
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

    private void initializeJComboBox(List<Vehicle> vehicles, boolean toggleSort) {
        String property;
        property = "category";
        String[] category = utilityObject.findUniqueVehiclePropertyValues(vehicles, property);
        categorySelect = (categorySelect == null) ? new JComboBox() : categorySelect;
        categorySelect.removeAllItems();
        for(String item : category ){
            categorySelect.addItem(item);
        }
        categorySelect.setSelectedItem(DEFAULT);
        property = "make";
        String[] make = utilityObject.findUniqueVehiclePropertyValues(vehicles, property);
        makeSelect = (makeSelect == null) ? new JComboBox() : makeSelect;
        makeSelect.removeAllItems();
        for(String item : make ){
            makeSelect.addItem(item);
        }
        makeSelect.setSelectedItem(DEFAULT);
        property = "type";
        String[] type = utilityObject.findUniqueVehiclePropertyValues(vehicles, property);
        typeSelect = (typeSelect == null) ? new JComboBox() : typeSelect;
        typeSelect.removeAllItems();
        for(String item : type ){
            typeSelect.addItem(item);
        }
        typeSelect.setSelectedItem(DEFAULT);
        property = "year";
        String[] year = utilityObject.findUniqueVehiclePropertyValues(vehicles, property);
        yearSelect = (yearSelect == null) ? new JComboBox() : yearSelect;
        yearSelect.removeAllItems();
        for(String item : year ){
            yearSelect.addItem(item);
        }
        yearSelect.setSelectedItem(DEFAULT);
        property = "price";
        String[] price = utilityObject.findUniqueVehiclePropertyValues(vehicles, property);
        priceRangeSelect = (priceRangeSelect == null) ? new JComboBox() : priceRangeSelect;
        priceRangeSelect.removeAllItems();
        for(String item : price ){
            priceRangeSelect.addItem(item);
        }
        priceRangeSelect.setSelectedItem(DEFAULT);

 if(toggleSort) {
     sortTypeSelect = new JComboBox(utilityObject.SORT_TYPE);
     sortTypeSelect.setSelectedItem(DEFAULT);
 }
        categorySelect.setBounds(120, 10, 170, 20);
        makeSelect.setBounds(120, 50, 170, 20);
        typeSelect.setBounds(120, 90, 170, 20);
        yearSelect.setBounds(120, 130, 170, 20);
        priceRangeSelect.setBounds(120, 170, 170, 20);
        sortTypeSelect.setBounds(790, 14, 200, 30);

    }

    private void resetFilterParameters() {
        initializeJComboBox(searchedVehicleList,false);
    }

    private void initializeJLabel() {
        categoryLabel = new JLabel("CATEGORY");
        makeLabel = new JLabel("MAKE");
        typeLabel = new JLabel("TYPE");
        yearLabel = new JLabel("YEAR");
        priceLabel = new JLabel("PRICE");
        sortLabel = new JLabel("SORT BY");
        navigationLabel = new JLabel("Page " + pageNumber);

        categoryLabel.setBounds(40, 10, 80, 20);
        makeLabel.setBounds(40, 50, 40, 20);
        typeLabel.setBounds(40, 90, 40, 20);
        yearLabel.setBounds(40, 130, 40, 20);
        priceLabel.setBounds(40, 170, 40, 20);
        sortLabel.setBounds(730, 14, 50, 30);
        navigationLabel.setBounds(620, 0, 100, 30);

    }

    private void initializeJPanel() {
        searchPanel = new JPanel();
        filterOptionsPanel = new JPanel();
        navigationOptionsPanel = new JPanel();

        searchPanel.setBounds(0, 0, 1200, 85);
        filterOptionsPanel.setBounds(0, 95, 350, 250);
        navigationOptionsPanel.setBounds(0, 600, 800, 50);
    }

    private void initializeJTextField() {
        searchTextField = new JTextField();
        searchTextField.setBounds(175, 14, 300, 35);

    }

    private void settingLayout() {
        browseInventoryFrame.setLayout(null);
        searchPanel.setLayout(null);
        filterOptionsPanel.setLayout(null);
        navigationOptionsPanel.setLayout(null);

    }

    private void addingComponents() throws IOException {
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
            display(vehicleList, imagePanelObjectsList);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    void addListeners() {
        searchButton.addActionListener(this);
        filterButton.addActionListener(this);
        previousPageNavigateButton.addActionListener(this);
        nextPageNavigateButton.addActionListener(this);
        sortButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            delegateAction(e);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void delegateAction(ActionEvent e) throws IOException {
        {
            List<JPanel> imagePanelObjectsList;

            /** Performing appropriate Actions Based on the Event occurred **/
            switch (((JButton) e.getSource()).getText().toLowerCase()) {
                case "search":
                    pageNumber = 1;
                    previousPageNavigateButton.setEnabled(false);
                    nextPageNavigateButton.setEnabled(true);
                    resetFilterValues();
                    if (!searchTextField.getText().isEmpty()) {
                        searchedVehicleList = inventoryServiceObject.search(searchedVehicleList, vehicleList, searchTextField);
                        if (sortTypeSelect.getSelectedItem().toString().contains("YEAR")) {
                            utilityObject.sortByYear(searchedVehicleList, (sortTypeSelect.getSelectedItem().toString().equals("YEAR LOW TO HIGH")));
                        } else if (sortTypeSelect.getSelectedItem().toString().contains("PRICE")) {
                            utilityObject.sortByPrice(searchedVehicleList, (sortTypeSelect.getSelectedItem().toString().equals("PRICE LOW TO HIGH")));
                        }
                        removeCurrentResultPanel();
                        addFilterResultPanel();
                        resetFilterParameters();
                        imagePanelObjectsList = createResultsPanel(5);
                        display(searchedVehicleList, imagePanelObjectsList);
                        navigationLabel.setText("Page " + pageNumber);
                    }
                    break;
                case "filter": {
                    pageNumber = 1;
                    previousPageNavigateButton.setEnabled(false);
                    nextPageNavigateButton.setEnabled(true);
                    filterVehicleList = searchedVehicleList.size()==0 ? vehicleList : searchedVehicleList;
                    filterVehicleList = utilityObject.filterVehicles(filterVehicleList, getFilterValues());
                    if(makeSelect.getSelectedItem().equals(DEFAULT) &&
                            typeSelect.getSelectedItem().equals(DEFAULT) &&
                            categorySelect.getSelectedItem().equals(DEFAULT) &&
                            yearSelect.getSelectedItem().equals(DEFAULT) &&
                            priceRangeSelect.getSelectedItem().equals(DEFAULT)) {
                        filterVehicleList = vehicleList;
                        initializeJComboBox(vehicleList,false);
                        searchTextField.setText("");
                    }
                    if (sortTypeSelect.getSelectedItem().toString().contains("YEAR")) {
                        utilityObject.sortByYear(filterVehicleList, (sortTypeSelect.getSelectedItem().toString().equals("YEAR LOW TO HIGH")));
                    } else if (sortTypeSelect.getSelectedItem().toString().contains("PRICE")) {
                        utilityObject.sortByPrice(filterVehicleList, (sortTypeSelect.getSelectedItem().toString().equals("PRICE LOW TO HIGH")));
                    }
                    removeCurrentResultPanel();
                    addFilterResultPanel();
                    imagePanelObjectsList = createResultsPanel(5);
                    display(filterVehicleList, imagePanelObjectsList);
                    searchedVehicleList = filterVehicleList;
                    resetFilterParameters();
                    navigationLabel.setText("Page " + pageNumber);
                }
                break;
                case "sort": {
                    pageNumber = 1;
                    previousPageNavigateButton.setEnabled(false);
                    nextPageNavigateButton.setEnabled(true);
                    removeCurrentResultPanel();
                    addFilterResultPanel();
                    imagePanelObjectsList = createResultsPanel(5);
                    if (sortTypeSelect.getSelectedItem().toString().contains("YEAR")) {
                        if (searchedVehicleList.size() != 0) {
                            utilityObject.sortByYear(searchedVehicleList, (sortTypeSelect.getSelectedItem().toString().equals("YEAR LOW TO HIGH")));
                            display(searchedVehicleList, imagePanelObjectsList);
                            navigationLabel.setText("Page " + pageNumber);
                        } else {
                            utilityObject.sortByYear(vehicleList, (sortTypeSelect.getSelectedItem().toString().equals("YEAR LOW TO HIGH")));
                            display(vehicleList, imagePanelObjectsList);
                            navigationLabel.setText("Page " + pageNumber);
                        }

                    } else if (sortTypeSelect.getSelectedItem().toString().contains("PRICE")) {
                        if (searchedVehicleList.size() != 0) {
                            utilityObject.sortByPrice(searchedVehicleList, (sortTypeSelect.getSelectedItem().toString().equals("PRICE LOW TO HIGH")));
                            display(searchedVehicleList, imagePanelObjectsList);
                            navigationLabel.setText("Page " + pageNumber);
                        } else {
                            utilityObject.sortByPrice(vehicleList, (sortTypeSelect.getSelectedItem().toString().equals("PRICE LOW TO HIGH")));
                            display(vehicleList, imagePanelObjectsList);
                            navigationLabel.setText("Page " + pageNumber);
                        }
                    }
                }
                break;
                case " << ": {
                    nextPageNavigateButton.setEnabled(true);
                    pageNumber--;
                    if (pageNumber == 1) {
                        previousPageNavigateButton.setEnabled(false);
                    }
                    removeCurrentResultPanel();
                    addFilterResultPanel();
                    imagePanelObjectsList = createResultsPanel(5);
                    if (searchedVehicleList.size() != 0) {
                        display(searchedVehicleList, imagePanelObjectsList);
                    } else if (filterVehicleList.size() != 0) {
                        display(filterVehicleList, imagePanelObjectsList);
                    } else {
                        display(vehicleList, imagePanelObjectsList);
                    }
                    navigationLabel.setText("Page " + pageNumber);
                }
                break;
                case " >> ": {
                    previousPageNavigateButton.setEnabled(true);
                    pageNumber++;
                    removeCurrentResultPanel();
                    addFilterResultPanel();
                    imagePanelObjectsList = createResultsPanel(5);
                    if (searchedVehicleList.size() != 0) {
                        display(searchedVehicleList, imagePanelObjectsList);
                        navigationLabel.setText("Page " + pageNumber);
                    } else if (filterVehicleList.size() != 0) {
                        display(filterVehicleList, imagePanelObjectsList);
                        navigationLabel.setText("Page " + pageNumber);
                    } else {
                        display(vehicleList, imagePanelObjectsList);
                        navigationLabel.setText("Page " + pageNumber);
                    }
                }
                break;
                default:
                    JButton imageClicked = (JButton) e.getSource();
                    VehicleImple vehicleImpleObject = new VehicleImple(new File("data"));
                    System.out.println(imageClicked.getText());
                    Vehicle vehicleObject = vehicleImpleObject.getAVehicle(dealer, imageClicked.getText());
                    DealerImpleService dealerImpleServiceObject = new DealerImpleService();
                    new VehicleDetailUI(vehicleObject, dealerImpleServiceObject.getADealer(dealer));
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

    private void display(ArrayList<Vehicle> vehiclesToDisplay, List<JPanel> imagePanelObjectsList) throws IOException {
        List<Vehicle> tempList = (pageNumber * 5 < vehiclesToDisplay.size()) ? vehiclesToDisplay.subList(((pageNumber - 1) * 5), (pageNumber * 5)) : vehiclesToDisplay.subList(((pageNumber - 1) * 5), vehiclesToDisplay.size());
        vehicleIDButtonList = new ArrayList<>();
        int counter = 0;
    //    PictureManager.initDealerPhotoLibrary(dealer,  vehiclesToDisplay);
        for (Vehicle vehicle : tempList) {
            JLabel imageLabel;
            Image image = vehicle.getPhoto();
            if (image == null) {
                imageLabel = new JLabel("NO IMAGE");
            } else {
                imageLabel = new JLabel(new ImageIcon(image));
            }
            imageLabel.setBounds(95, 10, 100, 45);
            imagePanelObjectsList.get(counter).add(imageLabel);
            JButton vehicleIDButton = new JButton(vehicle.getID());
            vehicleIDButton.addActionListener(this);
            vehicleIDButton.setBounds(200, 10, 150, 20);
            vehicleIDButtonList.add(vehicleIDButton);
            JLabel vehicleCategoryLabel = new JLabel("Category: " + vehicle.getCategory().toString());
            vehicleCategoryLabel.setBounds(400, 10, 150, 20);
            JLabel vehicleMakeLabel = new JLabel("Make: " + vehicle.getMake());
            vehicleMakeLabel.setBounds(200, 40, 200, 20);
            JLabel vehicleTypeLabel = new JLabel("Type: " + vehicle.getBodyType());
            vehicleTypeLabel.setBounds(300, 40, 200, 20);
            JLabel vehiclePriceLabel = new JLabel("Price: " + Double.toString(vehicle.getPrice()));
            vehiclePriceLabel.setBounds(500, 40, 100, 20);
            JLabel vehicleYearLabel = new JLabel("Year: " + Integer.toString(vehicle.getYear()));
            vehicleYearLabel.setBounds(600, 40, 100, 20);
            imagePanelObjectsList.get(counter).add(vehicleIDButton);
            imagePanelObjectsList.get(counter).add(vehicleCategoryLabel);
            imagePanelObjectsList.get(counter).add(vehicleMakeLabel);
            imagePanelObjectsList.get(counter).add(vehicleTypeLabel);
            imagePanelObjectsList.get(counter).add(vehiclePriceLabel);
            imagePanelObjectsList.get(counter).add(vehicleYearLabel);
            counter++;
        }
        if (pageNumber * 5 >= vehiclesToDisplay.size()) {
            nextPageNavigateButton.setEnabled(false);
        }
        browseInventoryFrame.repaint();
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
            y = y + 65;
            imagePanelObjectsList.add(resultImagePanel);
            filterResultMainPanel.add(resultImagePanel);
        }
        return imagePanelObjectsList;
    }
}
