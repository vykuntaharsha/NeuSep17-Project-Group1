/**
 * 
 * @author aarabhi Pugazhendhi
 */
package com.neuSep17.ui;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.swing.GroupLayout.Alignment;
import javax.swing.table.DefaultTableModel;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.neuSep17.dao.IncentiveImple;
import com.neuSep17.dao.PictureManager;
import com.neuSep17.dto.Vehicle;
import com.neuSep17.dto.Dealer;
import com.neuSep17.dto.Incentive;
import com.neuSep17.dto.Inventory;
import com.neuSep17.dao.VehicleImple;


class VehicleDetailUI extends JFrame {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JPanel picPanel = new JPanel();
    private JPanel vehicleTab;
    private JPanel dealerTab;
    private Vehicle vehicle;
    private Dealer dealer;
    private PhotoLabel photoLabel; // A seperate to deal with the picture.
    private int screenWidth;
    private int screenHeight;
    private JLabel pageTitle;
    private JTable dealerTable;
    private JTabbedPane tabs;
    private JTable vehicleTable;
    private JLabel YourPrice;
    private JLabel YouSave;
    private boolean isDealAvailable;
    private static IncentiveImple impl = new IncentiveImple();
    private static VehicleImple vimpl = new VehicleImple(new File("data"));
    private JPanel moreVehiclesPanel;
    private JLabel lblYouMayLike;
    private JLabel[] similarVehiclePics = new JLabel[4];
    private JLabel[] similarVehicleNames = new JLabel[4];
    private Vehicle[] selectedVehicle = new Vehicle[4];
    private Vehicle[] vehi;
    ArrayList<Vehicle> vehicleList;

    public VehicleDetailUI(Vehicle v, Dealer dealer) throws IOException {
	System.out.println("Working inside vehicle");
    
    	setType(Type.POPUP);
    	setBackground(new Color(255, 255, 255));
        this.vehicle = v;
        this.dealer = dealer;
        
        vehi = getSimilarVehicles(); 
        PictureManager.initDealerPhotoLibrary(dealer.getName(),vehicleList);
        
        createComponents();
        addComponentsUsingLayout();
        makeThisVisible();
        this.setTitle(vehicle.getMake()+" "+vehicle.getModel()+" "+vehicle.getYear());

        // Listen to component size changing.
        this.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                int[] size = getWindowWidthAndHeight();
                setPanelBorders(size[0], size[1]);
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public void createComponents() throws MalformedURLException, IOException {
        createDesPanel();//descriptionPanel.getMainPanel();

    }



    /**
     * The function below takes care of creating the description panel
     * */
    public void createDesPanel() {
	tabs = new JTabbedPane();
        tabs.setBackground(new Color(240, 230, 140));

        tabs.setFont(new Font("Bookman Old Style", Font.BOLD, 16));
         dealerTab = new JPanel();
         dealerTab.setFont(new Font("Bookman Old Style", Font.BOLD, 25));
        
        
        // Create two panels as the tabs to switch in tabbedPanelLayout
         vehicleTab = new JPanel();
         vehicleTab.setFont(new Font("Tahoma", Font.BOLD, 25));
         tabs.add("Vehicle Detail",vehicleTab);
         
         vehicleTable = new JTable(18,2);
         vehicleTable.setGridColor(new Color(153, 153, 51));
         vehicleTable.setBackground(new Color(245, 245, 220));
         vehicleTable.setFont(new Font("Bookman Old Style", Font.PLAIN, 15));
         vehicleTable.setShowVerticalLines(false);
         vehicleTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
         vehicleTable.setSize(new Dimension(45, 50));
         vehicleTable.setEnabled(false);
         vehicleTable.setRowHeight(30);
         vehicleTable.setModel(new DefaultTableModel(
         	new Object[][] {
         		{"Stock Number", vehicle.getID()},
         		{"Dealer", vehicle.getWebID()},
         		{"Category", vehicle.getCategory()},
         		{"Year", vehicle.getYear()},
         		{"Make", vehicle.getMake()},
         		{"Model", vehicle.getModel()},
         		{"Trim", vehicle.getTrim()},
         		{"Body Type", vehicle.getBodyType()},
         		{"Price", vehicle.getPrice()},
         		
         		
         	},
         	new String[] {
         		"", ""
         	}
         ) {
         	/**
	     * 
	     */
	    private static final long serialVersionUID = 1L;
		Class[] columnTypes = new Class[] {
         		String.class, String.class
         	};
         	public Class getColumnClass(int columnIndex) {
         		return columnTypes[columnIndex];
         	}
         });
         vehicleTable.getColumnModel().getColumn(0).setPreferredWidth(112);
         vehicleTable.getColumnModel().getColumn(1).setPreferredWidth(150);
         vehicleTable.setBorder(null);
         
         JButton btnMore = new JButton("More...");
         btnMore.setFont(new Font("Bookman Old Style", Font.BOLD, 16));
         btnMore.setActionCommand("More...\r\n");
         btnMore.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    CompleteDetails dialog = new CompleteDetails(vehicle);
		    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		    dialog.setFocusable(true);
		    dialog.pack();
		    
		    dialog.setLocationRelativeTo(null);
		    dialog.setVisible(true);
		    
		}
	});
         
         JPanel panel = new JPanel();
         panel.setFont(new Font("Bookman Old Style", Font.PLAIN, 16));
         panel.setBackground(new Color(245, 245, 220));

         
         GroupLayout gl_vehicleTab = new GroupLayout(vehicleTab);
         gl_vehicleTab.setHorizontalGroup(
         	gl_vehicleTab.createParallelGroup(Alignment.LEADING)
         		.addComponent(vehicleTable, GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
         		.addGroup(gl_vehicleTab.createSequentialGroup()
         			.addContainerGap(227, Short.MAX_VALUE)
         			.addComponent(btnMore)
         			.addContainerGap())
         		.addComponent(panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
         );
         gl_vehicleTab.setVerticalGroup(
         	gl_vehicleTab.createParallelGroup(Alignment.LEADING)
         		.addGroup(gl_vehicleTab.createSequentialGroup()
         			.addComponent(vehicleTable, GroupLayout.PREFERRED_SIZE, 268, GroupLayout.PREFERRED_SIZE)
         			.addPreferredGap(ComponentPlacement.RELATED)
         			.addComponent(btnMore)
         			.addPreferredGap(ComponentPlacement.UNRELATED)
         			.addComponent(panel, GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE))
         );
         
         
         JLabel Deal = new JLabel("DEAL!");
         Deal.setForeground(Color.RED);
         Deal.setFont(new Font("Verdana", Font.BOLD, 25));
         
         JLabel MRP = new JLabel("<html><body><font color = 'Red'>MRP: </font><b style='text-decoration: line-through;'>$"+vehicle.getPrice()+"</b></body></html>");
         MRP.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
         
         Double newPrice = calculatePrice(vehicle.getPrice());
         System.out.println(newPrice);
         YourPrice = new JLabel("<html><body><font color = 'Blue'>Your Price: </font>$"+String.valueOf(newPrice)+"</body></html>");
         YourPrice.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
         
         YouSave = new JLabel("<html><body><<font color = 'Blue'>You Save: </font>$"+String.valueOf(vehicle.getPrice()-newPrice)+"</body></html>");
         YouSave.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
         isDealAvailable = (newPrice !=0.0);
         if(isDealAvailable)
         {
         GroupLayout dealPanel = new GroupLayout(panel);
         dealPanel.setHorizontalGroup(
         	dealPanel.createParallelGroup(Alignment.LEADING)
         		.addGroup(dealPanel.createSequentialGroup()
         			.addGroup(dealPanel.createParallelGroup(Alignment.LEADING, false)
         				.addGroup(dealPanel.createSequentialGroup()
         					.addGap(101)
         					.addComponent(Deal))
         				.addGroup(dealPanel.createSequentialGroup()
         					.addContainerGap()
         					.addComponent(MRP, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
         				.addGroup(dealPanel.createSequentialGroup()
         					.addContainerGap()
         					.addComponent(YourPrice, GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE))
         				.addGroup(dealPanel.createSequentialGroup()
         					.addContainerGap()
         					.addComponent(YouSave, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
         			.addContainerGap(59, Short.MAX_VALUE))
         );
         dealPanel.setVerticalGroup(
         	dealPanel.createParallelGroup(Alignment.LEADING)
         		.addGroup(dealPanel.createSequentialGroup()
         			.addContainerGap()
         			.addComponent(Deal)
         			.addPreferredGap(ComponentPlacement.RELATED)
         			.addComponent(MRP)
         			.addPreferredGap(ComponentPlacement.RELATED)
         			.addComponent(YourPrice)
         			.addGap(4)
         			.addComponent(YouSave)
         			.addContainerGap())
         );
         
         panel.setLayout(dealPanel);
         }
         vehicleTab.setLayout(gl_vehicleTab);
        
        dealerTable = new JTable();
        dealerTable.setBorder(null);
        dealerTable.setGridColor(new Color(153, 153, 51));
        dealerTable.setBackground(new Color(245, 245, 220));
        dealerTable.setFont(new Font("Bookman Old Style", Font.BOLD, 18));
        dealerTable.setRowHeight(50);
        dealerTable.setShowVerticalLines(false);
        dealerTable.setEnabled(false);
        dealerTable.setModel(new DefaultTableModel(
        	new Object[][] {
        		{"ID", dealer.getId()},
        		{"Name", dealer.getName()},
        		{"URL","<html><body><a href='"+dealer.getUrl()+"'>"+dealer.getUrl()+"</a></body></html>"},
        		{"Email","<html><body><a href='mailto:"+dealer.getEmailId()+"'>"+dealer.getEmailId()+"</a></body></html>"},
        		{"Contact",dealer.getContactNumber()},
        	},
        	new String[] {
        		"New column", "New column"
        	}
        ));
        dealerTable.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                int row = dealerTable.rowAtPoint(new Point(e.getX(), e.getY()));
                int col = dealerTable.columnAtPoint(new Point(e.getX(), e.getY()));
                System.out.println(row + " " + col);

                String url = (String) dealerTable.getModel().getValueAt(row, col);
                System.out.println(url + " was clicked");
                String arr[] = url.split("'");
                System.out.println(arr[1]);
                Desktop desktop = Desktop.getDesktop();
                
                URI uri = URI.create(arr[1]);
                try {
		    desktop.browse(uri);
		} catch (IOException e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		}
            }
        });
        dealerTable.getColumnModel().getColumn(0).setPreferredWidth(116);
        dealerTable.getColumnModel().getColumn(1).setPreferredWidth(151);
        dealerTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        

        
        GroupLayout dealLayout = new GroupLayout(dealerTab);
        dealLayout.setHorizontalGroup(
        	dealLayout.createParallelGroup(Alignment.LEADING)
        		.addComponent(dealerTable, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
        );
        dealLayout.setVerticalGroup(
        	dealLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(dealLayout.createSequentialGroup()
        			.addComponent(dealerTable, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(225, Short.MAX_VALUE))
        );
        dealerTab.setLayout(dealLayout); 
     


        tabs.add("Dealer",dealerTab);
        
}

    private double calculatePrice(double price) {
	// TODO Auto-generated method stub
	ArrayList<String> criteria;
	    double discountedPrice = 0;
	System.out.println("full"+impl.getIncentivesForDealer(dealer.getId()));
	ArrayList<Incentive> incentives = impl.getIncentivesForDealer(dealer.getId());
	Date currentDate = Calendar.getInstance().getTime();
	for(Incentive i:incentives)
	{
	     System.out.println(Calendar.getInstance().getTime());
	     Date startDate = null;
	     Date endDate = null;
	     DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
	     try {
	         startDate = df.parse(i.getStartDate());
	         endDate = df.parse(i.getEndDate());
	     } catch (Exception e) {
	         e.printStackTrace();
	     }
	     	
	        if(currentDate.compareTo(startDate)<=0 || currentDate.compareTo(endDate)>=0)
	        {
	            continue;
	        }
	        criteria = i.getDiscountCriteria();
		int FeatureCounter = 0;
		for(String value:criteria)
		{
		    if(value.equalsIgnoreCase(vehicle.getMake()))
		    {
			FeatureCounter++;
		    }
		    else if(value.equalsIgnoreCase(vehicle.getModel()))
		    {
			FeatureCounter++;
		    }
		    else if(value.equalsIgnoreCase(String.valueOf(vehicle.getYear())))
		    {
			FeatureCounter++;
		    }
		    else if(value.equalsIgnoreCase(String.valueOf(vehicle.getCategory())))
		    {
		    FeatureCounter++;
		    }
		    else if(value.equalsIgnoreCase(String.valueOf(vehicle.getBodyType())))
            	    {
		          FeatureCounter++;
                    }
		    else if(value.equalsIgnoreCase(String.valueOf(vehicle.getPrice())))
            	   {
                  FeatureCounter++;
                   }
		    else if(value.equalsIgnoreCase(String.valueOf(vehicle.getTrim())))
            {
                  FeatureCounter++;
            }
		    else if(vehicle.getExteriorColor().toLowerCase().contains(value)||vehicle.getInteriorColor().toLowerCase().contains(value))
            {
                  FeatureCounter++;
            }
		    else if(value.equalsIgnoreCase("ALL"))
		    {
		     FeatureCounter++;
		    }
		    System.out.println(FeatureCounter);
		}
		if(FeatureCounter == criteria.size())
		  {
		      discountedPrice = price - i.getCashValue();
		      return discountedPrice;
		  }
	}
	return discountedPrice;
	
    }
    
    private Vehicle [] getSimilarVehicles() {
	// TODO Auto-generated method stub
	Inventory inv = vimpl.getInventory(dealer.getId());
	//Collection<Vehicle> vehicles = inv.getVehicles();
    vehicleList = new ArrayList<Vehicle>(inv.getVehicles());
      
	return vehicleList.toArray(new Vehicle[vehicleList.size()]);
    }

    public void addComponentsUsingLayout() throws IOException {
        Container container = getContentPane();
        container.removeAll();

        vehicleTab.setBackground(new Color(245, 245, 220));
        dealerTab.setBackground(new Color(245, 245, 220));
        
        pageTitle = new JLabel(vehicle.getYear() + "   " + vehicle.getMake() + "   " + vehicle.getModel());
        pageTitle.setHorizontalAlignment(SwingConstants.CENTER);
        pageTitle.setFont(new Font("Californian FB", Font.BOLD, 30));

        // Get the screen size, for calculating the initial window size;
        screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        
        photoLabel = new PhotoLabel();
        picPanel.setLayout(new BorderLayout());
        picPanel.add(photoLabel, BorderLayout.CENTER);
        picPanel.setBackground(new Color(245, 245, 220));    

        container.add(picPanel, BorderLayout.CENTER );


        
        container.add(tabs, BorderLayout.LINE_START);
        tabs.setPreferredSize(new Dimension(400, 100));
        container.add(pageTitle, BorderLayout.PAGE_START);

        container.setBackground(new Color(245, 245, 220));
        tabs.setBackground(Color.WHITE);
        
        moreVehiclesPanel = new JPanel();
        moreVehiclesPanel.setBackground(new Color(255, 222, 173));
        moreVehiclesPanel.setPreferredSize(new Dimension(300, 215));
        getContentPane().add(moreVehiclesPanel, BorderLayout.SOUTH);
        moreVehiclesPanel.setLayout(null);
        	
        createBottomPanel();
        
        for(int j = 0 ; j<4; j++)
        {
            moreVehiclesPanel.add(similarVehiclePics[j]);
            moreVehiclesPanel.add(similarVehicleNames[j]);            
        }
    
        moreVehiclesPanel.add(lblYouMayLike);       	        
        container.revalidate();
        container.repaint();
        
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
    }
    }

    private void createBottomPanel() {
	Image im = null;
	int i,m;
	          
       int numOfSelectedVehicle = 0;
       for(i = 0 ; i < vehi.length;i++)
       {
           im = vehi[i].getPhoto();
           if(im==null)
           {
               continue;
           }
           selectedVehicle[numOfSelectedVehicle] = vehi[i];
           numOfSelectedVehicle++;
           if(numOfSelectedVehicle==4)
               break;
       }
       for(m=0;m<4;m++)
       {
	        similarVehiclePics[m] = new JLabel();
       		//similarVehiclePics[m].setIcon(new ImageIcon(new ImageIcon(selectedVehicle[m].getPhotoURL()).getImage().getScaledInstance(242, 138, Image.SCALE_DEFAULT)));
	        similarVehiclePics[m].setIcon(new ImageIcon(selectedVehicle[m].getPhoto().getScaledInstance(242, 138, Image.SCALE_DEFAULT)));
	        similarVehicleNames[m] = new JLabel(selectedVehicle[m].getMake()+" "+selectedVehicle[m].getModel()+" "+selectedVehicle[m].getYear()+" "+selectedVehicle[m].getCategory());
       		similarVehicleNames[m].setHorizontalAlignment(SwingConstants.CENTER);
                Vehicle v = selectedVehicle[m];
                similarVehiclePics[m].addMouseListener(new MouseAdapter() {
    		@Override
    		public void mouseClicked(MouseEvent e) {
    		    try {
    			new VehicleDetailUI(v,dealer);
		    } catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		    }
    		}
    	});
        }
            
         
       similarVehiclePics[0].setBounds(135, 42, 242, 138);
       similarVehiclePics[1].setBounds(529, 42, 242, 138);
       similarVehiclePics[2].setBounds(974, 42, 239, 138);        
       similarVehiclePics[3].setBounds(1420, 42, 240, 138);
        
       similarVehicleNames[0].setBounds(135, 186, 242, 20);
       similarVehicleNames[1].setBounds(529, 186, 242, 25);
       similarVehicleNames[2].setBounds(974, 186, 239, 20);
       similarVehicleNames[3].setBounds(1430, 179, 194, 20);
        
        lblYouMayLike = new JLabel("You may like...");
        lblYouMayLike.setFont(new Font("Tahoma", Font.BOLD, 22));
        lblYouMayLike.setBounds(15, 0, 165, 36);	
    }


    public void setPanelBorders(int width, int height) {

        int[] title = { height/20, width/20, height/20, 0 };
        int[] des = {0, width/20, height/20, 0};
        int[] pic = {0, width/20, height/20, width/20};

        pageTitle.setBorder(new EmptyBorder(title[0],title[1],title[2],title[3]));
        tabs.setBorder( new EmptyBorder(des[0],des[1],des[2],des[3]));
        picPanel.setBorder(new EmptyBorder(pic[0],pic[1],pic[2],pic[3]));
        
    }


    // Get the width and height of the window;
    public int[] getWindowWidthAndHeight() {
        int[] res = new int[2];
        res[0] = this.getWidth();
        res[1] = this.getHeight();
        System.out.println(Arrays.toString(res));
        return res;
    }


    private void makeThisVisible() {
        int width = screenWidth * 9/10;
        int height = screenHeight * 9/10;
        int x = (screenWidth - width) / 2;
        int y = (screenHeight - height) / 2;
        // Initialize the window always pop out at the center of the screen;
        // And take 2/3 width and height of the screen.
        this.setBounds(x, y, width, height);
        setPanelBorders(width, height);
        this.setVisible(true);
        
        this.addWindowListener(new WindowListener() {


            @Override
            public void windowOpened(WindowEvent e) {
                // TODO Auto-generated method stub

                //Image image = Toolkit.getDefaultToolkit().getImage(vehicle.getPhotoURL());
                BufferedImage image = null;
		    //image = ImageIO.read(vehicle.getPhotoURL());
		    image = (BufferedImage) vehicle.getPhoto();
		    System.out.println(image);
		    if(image==null)
		    {
		        try {
		            image = ImageIO.read(new File("data/No_Image_Available.jpg"));
		            } 
		            catch (IOException e2) {
		                e2.printStackTrace();
		            }
		    }
                ImageIcon icon = new ImageIcon(image);
                photoLabel.setImage(image);
                icon=new ImageIcon(icon.getImage().getScaledInstance(photoLabel.getWidth(),photoLabel.getHeight(), Image.SCALE_DEFAULT));
                photoLabel.setIcon(icon);
                photoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
          }

            @Override
            public void windowIconified(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowClosing(WindowEvent e) {
                // TODO Auto-generated method stub
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }

            @Override
            public void windowClosed(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowActivated(WindowEvent e) {
                // TODO Auto-generated method stub

            }
        });
	try {
	    Thread t1 = new Thread(new Runnable() {
	         public void run() {
	              // code goes here.
	             try {
	                 FileInputStream file = new FileInputStream("data/carstartgarage.wav");
                
	             BufferedInputStream bf = new BufferedInputStream(file);
	                 AudioInputStream audioIn = AudioSystem.getAudioInputStream(bf);
	                 Clip clip = AudioSystem.getClip();
	                 clip.open(audioIn);
	                 clip.start();
	             } catch (Exception e) {
	                    // TODO Auto-generated catch block
	                    e.printStackTrace();
	                }
	         }
	    });  
	    t1.start();
	    
		//Thread.sleep(3000);
	    
	    
//	    clip.close();
//	    audioIn.close();
//	    bf.close();
//	    file.close();
	    if(isDealAvailable)
	    {
	        Thread t2 = new Thread(new Runnable() {
	             public void run() {
	                  // code goes here.
	                 try {
	                     Thread.sleep(3000);
	                     FileInputStream file = new FileInputStream("data/youhaveadeal.wav");
	                
	                 BufferedInputStream bf = new BufferedInputStream(file);
	                     AudioInputStream audioIn = AudioSystem.getAudioInputStream(bf);
	                     Clip clip = AudioSystem.getClip();
	                     clip.open(audioIn);
	                     clip.start();
	                 } catch (Exception e) {
	                        // TODO Auto-generated catch block
	                        e.printStackTrace();
	                    }
	             }
	        });  
	        t2.start();
//	        file = new FileInputStream("data/youhaveadeal.wav");
//        	    
//        	    bf = new BufferedInputStream(file);
//        	    audioIn = AudioSystem.getAudioInputStream(bf);
//        	    clip = AudioSystem.getClip();
//        	    clip.open(audioIn);
//        	    clip.start();
	    }
	    
	    
	    	
	} catch (Exception e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}
    	
    }
}



