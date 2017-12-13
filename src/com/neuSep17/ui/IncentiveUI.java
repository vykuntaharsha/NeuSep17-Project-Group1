package com.neuSep17.ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class IncentiveUI {

    protected int screenWidth;
    protected int screenHeight;
    

    public IncentiveUI() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        screenWidth = screenSize.width;
        screenHeight = screenSize.height;

        System.out.println("width: "+screenWidth+": "+screenHeight);

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                System.out.println(info.getName());
                if ("Windows".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Windows is not available, you can set the GUI to another look and feel.
        }
    }

    public void displayCenter(JDialog dialog) {
        int windowWidth = dialog.getWidth();                     //get the width of the window
        int windowHeight = dialog.getHeight();                   //get the height

        dialog.setLocation(screenWidth, screenHeight);//display the frame in the center of the screen
    }

    public void displayCenter(JFrame frame) {
        int windowWidth = frame.getWidth();                     //get the width of the window
        int windowHeight = frame.getHeight();                   //get the height

        frame.setLocation(screenWidth, screenHeight);//display the frame in the center of the screen
    }

    public JFrame createFrame(String title) {
        JFrame frame = new JFrame(title);
        frame.setTitle(title);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setBackground(Color.GRAY);
        frame.setSize(screenWidth,screenHeight);
        displayCenter(frame);
        return frame;
    }

    public JButton createButton(String text) {
        JButton jButton = new JButton(text);
//        jButton.setPreferredSize(new Dimension(screenWidth/20, screenHeight/30));
        jButton.setFont(new Font("Arial", Font.PLAIN, screenWidth/100));
        return jButton;
    }

    public JComboBox createComboBox(String[] items){
        JComboBox jComboBox = new JComboBox(items);
        jComboBox.setFont(new Font("Arial", Font.PLAIN, screenWidth/100));
        return jComboBox;
    }

    public JLabel createLabel(String text) {
        JLabel jLabel = new JLabel(text);
        jLabel.setFont(new Font("Arial", Font.PLAIN, screenWidth/100));
        return jLabel;
    }

    public JLabel createLabel(String text, int horizontalAlignment){
        JLabel jLabel = createLabel(text);
        jLabel.setHorizontalAlignment(horizontalAlignment);
        return jLabel;
    }

    public JTextField createText(String text){
        JTextField textField = new JTextField(text);
        textField.setSize(screenWidth/200,screenHeight/100);
        return textField;
    }

    public ImageIcon createImageIcon(String path) {
        String rootPath = "";
        try {
            rootPath = Paths.get("").toAbsolutePath().toString();
            rootPath+= File.separator+"data" + File.separator+path;
        } catch (Exception e) {
        }
        return new ImageIcon(rootPath);
    }

    public JLabel createPicture(String path) throws IOException{
        JLabel picLabel = new JLabel(createImageIcon(path));
        picLabel.setSize(new Dimension(screenWidth/10, screenHeight/10));
        picLabel.setBounds(screenWidth/30, screenHeight/10, screenWidth/2, screenHeight/3);
        return picLabel;
    }
}
