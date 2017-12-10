/**
*
* @author Aarabhi Pugazhendhi
*/
//Used for vehicleDetailsUI.java 
package com.neuSep17.ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class PhotoLabel extends JLabel implements ComponentListener
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    Image image;

    PhotoLabel() {}

    public void setImage(Image image)
    {
        this.image = image;
    }

    @Override
    public void paint(Graphics g) {
        if(image==null)
            return;
        int imgWidth=image.getWidth(null);
        int imgHeight=image.getHeight(null);
        float imgRatio = (float) imgWidth / imgHeight;

        int conWidth=getWidth();
        int conHeight=getHeight();
        float comRatio = (float) conWidth / conHeight;

        int reImgWidth;
        int reImgHeight;
        if( imgRatio >=comRatio ) {

            reImgWidth = conWidth;
            reImgHeight = (int) ( (float)conWidth / imgRatio);

        }else{

            reImgHeight = conHeight;
            reImgWidth = (int) ((float)conHeight * imgRatio );

        }
//        System.out.println(imgRatio+ ", " + imgHeight + ", " + imgWidth);
//        System.out.println("=========");
//        System.out.println(comRatio +  ", " + conHeight + ", " + conWidth);

        int x = (getWidth()- reImgWidth)/2;
        int y = (getHeight()-reImgHeight)/2;
        // TODO Auto-generated method stub
        g.drawImage(image,x,y,reImgWidth,reImgHeight,null);
    }
    @Override
    public void componentResized(ComponentEvent e) {
        // TODO Auto-generated method stub
        this.repaint();
    }
    @Override
    public void componentMoved(ComponentEvent e) {
        // TODO Auto-generated method stub

    }
    @Override
    public void componentShown(ComponentEvent e) {
        // TODO Auto-generated method stub

    }
    @Override
    public void componentHidden(ComponentEvent e) {
        // TODO Auto-generated method stub

    }
}
