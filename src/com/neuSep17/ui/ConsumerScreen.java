import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

public class ConsumerScreen {

    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel footerLabel;
    private JLabel distanceLabel;
    private JLabel statusLabel;
    private JPanel controlPanel;
    private JComboBox<String> chooseDealer;

    public static void main(String[] args) throws IOException {
        ConsumerScreen  welcome = new ConsumerScreen();
        welcome.createUI();
    }

    public void createUI() throws IOException {

        prepareGUI();

        JPanel distanceNestedPanel = new JPanel();
        JLabel distanceTextLabel = new JLabel("Show dealers within distance (mi):", JLabel.CENTER);
        distanceTextLabel.setFont(new Font("Arial", Font.PLAIN, 40));
        JSlider slider = getSlider();
//        distanceNestedPanel.add(imageLabel);
        distanceNestedPanel.add(distanceTextLabel);
        distanceNestedPanel.add(slider);
        distanceNestedPanel.add(distanceLabel);

        JPanel dealersListNestedPanel = new JPanel();
        JLabel label = new JLabel("Select Dealer:",JLabel.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 40));
        JComboBox<String> dealersComboBox = getDealersComboBox();
        dealersListNestedPanel.add(label);
        dealersListNestedPanel.add(dealersComboBox);

        JPanel submitNestedPanel = new JPanel();
        JButton submitButton = getSubmitButton();
        submitNestedPanel.add(submitButton);

        JPanel outer = new JPanel(new GridLayout(5,1));
        outer.add(distanceNestedPanel);
        outer.add(dealersListNestedPanel);
        outer.add(submitNestedPanel);


        controlPanel.add(outer);
        mainFrame.setVisible(true);
    }

    private void prepareGUI() throws IOException {
        mainFrame = new JFrame("Welcome User");
        mainFrame.setSize(2500,2000);
        mainFrame.setLayout(new GridLayout(3, 2));

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }


        JPanel imageNestedPanel = new JPanel();
        imageNestedPanel.setLayout(new GridLayout(2,1));
        JLabel imageLabel = pictures();
        imageNestedPanel.add(imageLabel);

        headerLabel = new JLabel("Welcome to your dream car site!", JLabel.CENTER);
        headerLabel.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 100));

        imageNestedPanel.add(headerLabel);

        statusLabel = new JLabel("", JLabel.CENTER);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 40));

        distanceLabel = new JLabel("", JLabel.CENTER);
        distanceLabel.setFont(new Font("Arial", Font.PLAIN, 40));

        footerLabel = new JLabel("", JLabel.CENTER);

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout() );

        mainFrame.add(imageNestedPanel);
//        mainFrame.add(headerLabel);
        mainFrame.add(controlPanel);
        mainFrame.add(statusLabel);
        mainFrame.setVisible(true);
    }

    private JButton getSubmitButton() {

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("Dealer: "+chooseDealer.getSelectedItem()+" is selected.");
            }
        });

        submitButton.setFont(new Font("Arial", Font.PLAIN, 36));
        submitButton.setPreferredSize(new Dimension(350, 100));
        return submitButton;
    }

    private JComboBox<String> getDealersComboBox(){
        String [] dealers = new String[]{"SeattleAudi","HondaBellevue"};
        chooseDealer   = new JComboBox<>(dealers);
        chooseDealer.setPreferredSize(new Dimension(600, 50));
        chooseDealer.setForeground(Color.BLACK);
        chooseDealer.setFont(new Font("Arial", Font.PLAIN, 36));

        return chooseDealer;
    }

    private JSlider getSlider() {
        // Set the slider
        JSlider slider = new JSlider();
        slider.setMinorTickSpacing(10);
        slider.setPaintTicks(true);
        slider.setFont(new Font("Arial", Font.PLAIN, 36));

        // Set the labels to be painted on the slider
        slider.setPaintLabels(true);

        // Add positions label in the slider
        Hashtable<Integer, JLabel> position = new Hashtable<Integer, JLabel>();
        position.put(0, new JLabel("0"));
        position.put(50, new JLabel("50"));
        position.put(100, new JLabel("100"));

        // Set the label to be drawn
        slider.setLabelTable(position);

        distanceLabel.setText("(selected: " + slider.getValue() +")");

        // Add change listener to the slider
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                distanceLabel.setText("(selected: " + ((JSlider)e.getSource()).getValue() +")");
            }
        });

        return slider;


    }
    private JLabel pictures() throws IOException{
        JLabel picLabel = new JLabel(new ImageIcon("C:\\Users\\diksh\\Desktop\\image.jpg"));
//        picLabel.setSize(new Dimension(500, 200));

//        picLabel.setBounds(100, 200, 2000, 800);

        return picLabel;
    }
}
