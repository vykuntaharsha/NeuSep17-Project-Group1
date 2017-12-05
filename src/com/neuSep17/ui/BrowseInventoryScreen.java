import com.neuSep17.dto.Vehicle;
import com.neuSep17.utility.BrowseInventoryUtility;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class BrowseInventoryScreen implements ActionListener {
    BrowseInventoryUtility utilityObject = new BrowseInventoryUtility();
    Collection<Vehicle> vehicles = utilityObject.setObjectsforUtility();


    final int WIDTH = 1300;
    final int HEIGHT = 700;

    JFrame browseInventoryFrame;
    JPanel contentPane;
    JTextField searchTextField;
    JButton searchButton, previousPageNavigateButton, nextPageNavigateButton, sortButton;
    JComboBox categorySelect, makeSelect, typeSelect, yearSelect, priceRangeSelect, sortTypeSelect, sortValueSelect;
    JLabel makeLabel, typeLabel, yearLabel, priceLabel, categoryLabel, sortLabel, navigationLabel;
    JPanel searchPanel, filterOptionsPanel, filterResultMainPanel, navigationOptionsPanel, firstImagePanel, secondImagePanel, thirdImagePanel;


    public BrowseInventoryScreen() throws IOException {
    }


    void setComponents() throws IOException {
        String property;

        browseInventoryFrame = new JFrame("Find Your Dream Car");
        browseInventoryFrame.setSize(WIDTH, HEIGHT);
        browseInventoryFrame.setLocation(100, 150);
        browseInventoryFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        browseInventoryFrame.setVisible(true);
        contentPane = new JPanel();
        browseInventoryFrame.setContentPane(contentPane);
        searchTextField = new JTextField();
        searchButton = new JButton("Search");
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
        String[] sortType = {"NONE", "PRICE", "YEAR"};
        sortTypeSelect = new JComboBox(sortType);
        String[] sortValues = {"NONE", "HIGH TO LOW", "LOW TO HIGH"};
        sortValueSelect = new JComboBox(sortValues);
        sortButton = new JButton("SORT");
        previousPageNavigateButton = new JButton(" << ");
        nextPageNavigateButton = new JButton(" >> ");

        categoryLabel = new JLabel("CATEGORY");
        makeLabel = new JLabel("MAKE");
        typeLabel = new JLabel("TYPE");
        yearLabel = new JLabel("YEAR");
        priceLabel = new JLabel("PRICE");
        sortLabel = new JLabel("SORT BY");
        int num = 1;
        int totalNum = 10;
        navigationLabel = new JLabel("Page " + num + " of " + totalNum);

        searchPanel = new JPanel();
        filterOptionsPanel = new JPanel();
        filterResultMainPanel = new JPanel();
        navigationOptionsPanel = new JPanel();

        firstImagePanel = new JPanel();
        secondImagePanel = new JPanel();
        thirdImagePanel = new JPanel();

        browseInventoryFrame.setLayout(null);
        searchPanel.setBounds(0, 0, 1200, 85);
        filterOptionsPanel.setBounds(0, 95, 250, 250);
        filterResultMainPanel.setBounds(0, 95, 1100, 500);
        navigationOptionsPanel.setBounds(0, 600, 800, 50);

        searchPanel.setLayout(null);
        searchTextField.setBounds(175, 14, 300, 35);
        searchButton.setBounds(500, 14, 100, 35);
        sortLabel.setBounds(730, 14, 50, 30);
        sortTypeSelect.setBounds(790, 14, 100, 30);
        sortValueSelect.setBounds(900, 14, 100, 30);
        sortButton.setBounds(1010, 14, 100, 30);

        filterOptionsPanel.setLayout(null);
        categoryLabel.setBounds(40, 10, 80, 20);
        categorySelect.setBounds(120, 10, 120, 20);
        makeLabel.setBounds(40, 50, 40, 20);
        makeSelect.setBounds(120, 50, 120, 20);
        typeLabel.setBounds(40, 90, 40, 20);
        typeSelect.setBounds(120, 90, 120, 20);
        yearLabel.setBounds(40, 130, 40, 20);
        yearSelect.setBounds(120, 130, 120, 20);
        priceLabel.setBounds(40, 170, 40, 20);
        priceRangeSelect.setBounds(120, 170, 120, 20);


        filterResultMainPanel.setLayout(null);
        filterResultMainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        navigationOptionsPanel.setLayout(null);
        navigationLabel.setBounds(600, 0, 120, 30);
        previousPageNavigateButton.setBounds(500, 0, 80, 30);
        nextPageNavigateButton.setBounds(700, 0, 80, 30);

        searchPanel.add(searchTextField);
        searchPanel.add(searchButton);

        searchPanel.add(sortLabel);
        searchPanel.add(sortTypeSelect);
        searchPanel.add(sortValueSelect);
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

        navigationOptionsPanel.add(previousPageNavigateButton);
        navigationOptionsPanel.add(navigationLabel);
        navigationOptionsPanel.add(nextPageNavigateButton);


        contentPane.add(searchPanel);
        contentPane.add(filterOptionsPanel);
        contentPane.add(filterResultMainPanel);
        contentPane.add(navigationOptionsPanel);


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
        previousPageNavigateButton.addActionListener(this);
        nextPageNavigateButton.addActionListener(this);
        sortButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == searchButton) {
            try {
                List<JPanel> imagePanelObjectsList = createResultsPanel(7);
                display(0, vehicles, imagePanelObjectsList);

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else if (e.getSource() == sortButton) {
            // call the method to sort
        } else if (e.getSource() == previousPageNavigateButton) {
            // call the method to navigate
        } else if (e.getSource() == nextPageNavigateButton) {
            // call the method to navigate

            try {
                List<JPanel> imagePanelObjectsList = createResultsPanel(7);
                display(7, vehicles, imagePanelObjectsList);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void display(int startIndex, Collection<Vehicle> vehicles, List<JPanel> imagePanelObjectsList) throws IOException {
        List<Vehicle> vehicleList = new ArrayList<>(vehicles);
        int counter = 0;
        while (counter < 7) {
            Image image = ImageIO.read(vehicleList.get(startIndex).getPhotoURL().openStream());
            JLabel imageLabel = new JLabel(new ImageIcon(image));
            imageLabel.setBounds(95, 10, 100, 50);
            imagePanelObjectsList.get(counter).add(imageLabel);
            JLabel vehicleIDLabel = new JLabel("ID: " + vehicleList.get(startIndex).getID());
            vehicleIDLabel.setBounds(200, 10, 100, 20);
            JLabel vehicleMakeLabel = new JLabel("Make: " + vehicleList.get(startIndex).getMake());
            vehicleMakeLabel.setBounds(200, 40, 200, 20);
            JLabel vehicleTypeLabel = new JLabel("Type: " + vehicleList.get(startIndex).getBodyType());
            vehicleTypeLabel.setBounds(300, 40, 200, 20);
            JLabel vehiclePriceLabel = new JLabel("Price: " + Double.toString(vehicleList.get(startIndex).getPrice()));
            vehiclePriceLabel.setBounds(500, 40, 100, 20);
            JLabel vehicleYearLabel = new JLabel("Year: " + Integer.toString(vehicleList.get(startIndex).getYear()));
            vehicleYearLabel.setBounds(600, 40, 100, 20);
            imagePanelObjectsList.get(counter).add(vehicleIDLabel);
            imagePanelObjectsList.get(counter).add(vehicleMakeLabel);
            imagePanelObjectsList.get(counter).add(vehicleTypeLabel);
            imagePanelObjectsList.get(counter).add(vehiclePriceLabel);
            imagePanelObjectsList.get(counter).add(vehicleYearLabel);
            counter++;
            startIndex++;
        }


    }

    /**
     * private void removeCurrentPanel() {
     * filterResultMainPanel.remove();
     * }
     **/

    List<JPanel> createResultsPanel(int vehicleSizetoDisplay) {
        List<JPanel> imagePanelObjectsList = new ArrayList<>();
        int y = 10;
        for (int i = 0; i < vehicleSizetoDisplay; i++) {
            JPanel resultImagePanel = new JPanel();
            resultImagePanel.setLayout(null);
            resultImagePanel.setBounds(320, y, 700, 60);
            resultImagePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            y = y + 65;
            imagePanelObjectsList.add(resultImagePanel);
            filterResultMainPanel.add(resultImagePanel);

        }
        /**  firstImagePanel.setBounds(320, 10, 700, 60);
         secondImagePanel.setBounds(320, 75, 700, 60);
         thirdImagePanel.setBounds(320, 140, 700, 60); **/


        /**   filterResultMainPanel.add(firstImagePanel);
         filterResultMainPanel.add(secondImagePanel);
         filterResultMainPanel.add(thirdImagePanel); **/

        return imagePanelObjectsList;
    }


}
