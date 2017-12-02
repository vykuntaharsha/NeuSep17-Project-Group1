import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JTextArea;

public class EditIncentivesFrame extends JFrame{

	private JFrame frame;
	private JTextField txtDiscount;
	private JTextField txtEnterTitle;
	private JTextField txtPrice;
	private JTextField txtId;
	private JTextField txtMileage;
	private JTextField txtYear;
	private JTextField txtMake;
	private JTextField txtModel;
	private JTextArea txtDescription;
	private JTextArea txtDisclaimer;
	private JComboBox cbCategory;
	private JComboBox cbColor;
	private JComboBox cbType;
	private JComboBox cbTrim;
	private JComboBox cbPrice;
	private JComboBox cbMileage;
	private JButton applyBtn;
	
	


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditIncentivesFrame window = new EditIncentivesFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		//readAndGetIncentives();---------ServiceTeam
		showRecordsOnScreen();
		//writeToFile();------------ServiceTeam
	}


	private static void showRecordsOnScreen() {
		// TODO Auto-generated method stub
		
	}
	

	private void applyClicked(MouseEvent e) {
		try {
			EditIncentives ei = getIncentive();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		frame.dispatchEvent(new WindowEvent(frame,WindowEvent.WINDOW_CLOSING) );
		this.setVisible(false);
	}
	
	private EditIncentives getIncentive() throws Exception{
		String[] arr = {};
		EditIncentives ei = new EditIncentives(arr);
		StringBuilder sb = new StringBuilder();
		
		arr[0] = txtId.getText();
		
		arr[1] = txtEnterTitle.getText();
		if(isNullOrEmpty(arr,1)) {
			sb.append("Title should not be empty.");
		}
		
		arr[2] = txtDiscount.getText();
		if(isNullOrEmpty(arr,2)) {
			sb.append("Discount should not be empty.");
		}else if(!isFourDigit(arr,2)){
			sb.append("Discount should be less than $9999.");
		}
		
		arr[3] = txtDescription.getText();
		
		arr[4] = txtDisclaimer.getText();
		
		
		arr[5] = txtYear.getText();
		if(!isYear(arr,5)){
			sb.append("Year should between 1900-2017.");
		}
		
		arr[6] = txtMake.getText();
		
		arr[7] = txtModel.getText();
		
		arr[8] = txtPrice.getText();
		if(!isNumberAndDot(arr,8)){
			sb.append("Price should be numbers with no more than two decimals.");
		}
		
		arr[9] = txtMileage.getText();
		if(!isNumberAndDot(arr,9)){
			sb.append("Mileage should be numbers with no more than two decimals.");
		}
		
		
		if (IsNullOrEmpty(sb)) {
            throw new Exception(sb.toString());
		}
		
		return ei;
	}


	private boolean IsNullOrEmpty(StringBuilder sb) {
		if( sb ==null && sb.toString().isEmpty()) {
			return true;
		}
		return false;
	}


	private boolean isNumberAndDot(String[] arr, int i) {
		if(Pattern.matches("^[0-9]+(.[0-9]{2})?$", arr[i])) {
			return true;
		}
		return false;
	}


	private boolean isYear(String[] arr, int i) {
		if(Pattern.matches("[1][9][0-9][0-9]|[2][0][0-1][0-7]", arr[i])) {
			return true;
		}
		return false;
	}


	private boolean isFourDigit(String[] arr, int i) {
		if(Pattern.matches("[0-9]{1,4}", arr[i])) {
			return true;
		}
		return false;
	}


	private boolean isNullOrEmpty(String[] arr, int i) {
		if(arr[i]==null && arr[i].isEmpty()) {
			return true;
		}
		return false;
	}
	
	

	public void itemStateChanged(ItemEvent e) {
		if(e.getSource()=="cbCategory") {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				getCategory();
				
			}
		}
		
		if(e.getSource()=="cbColor") {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				getColor();
				
			}
		}
		
		if(e.getSource()=="cbType") {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				getTypes();
				
			}
		}
		
		if(e.getSource()=="cbTrim") {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				getTrim();
				
			}
		}
	}
	private EditIncentives getTrim() {
		String[] arr = {};
		EditIncentives ei = new EditIncentives(arr);
		arr[13] = (String) cbCategory.getSelectedItem();
		return ei;
	}


	private EditIncentives getTypes() {
		String[] arr = {};
		EditIncentives ei = new EditIncentives(arr);
		arr[12] = (String) cbCategory.getSelectedItem();
		return ei;
	}


	private EditIncentives getColor() {
		String[] arr = {};
		EditIncentives ei = new EditIncentives(arr);
		arr[11] = (String) cbCategory.getSelectedItem();
		return ei;
	}


	private  EditIncentives getCategory() {
		String[] arr = {};
		EditIncentives ei = new EditIncentives(arr);
		arr[10] = (String) cbCategory.getSelectedItem();
		return ei;
	}
	
	
	



	public EditIncentivesFrame() {
		initialize();
	}
	
	

	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(30, 20, 1200, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Edit Incentives: Dealer Discount");
		
		//********************leftPanel************************
		JPanel leftPanel = new JPanel();
		leftPanel.setBounds(10, 100, 520, 475);
		frame.getContentPane().add(leftPanel);
		leftPanel.setLayout(null);
		
		//start end date
		JLabel askDate = new JLabel("When does this Incentive start and end?");
		askDate.setBounds(10, 135, 305, 19);
		leftPanel.add(askDate);
		askDate.setFont(new Font("Arial", Font.BOLD, 16));
		
		JLabel lblStartDate = new JLabel("Start Date(yyyy-mm-dd)");
		lblStartDate.setBounds(10, 160, 150, 15);
		leftPanel.add(lblStartDate);
		lblStartDate.setFont(new Font("Arial", Font.PLAIN, 13));
		
		//get time model
		SpinnerDateModel modelStart = new SpinnerDateModel();
		SpinnerDateModel modelEnd = new SpinnerDateModel();
		
		//create spinner object
		JSpinner start = new JSpinner(modelStart);
		start.setBounds(10, 175, 105, 25);
		leftPanel.add(start);
		start.setValue(new Date());
		JSpinner.DateEditor sEditor = new JSpinner.DateEditor(start,"yyyy-MM-dd");
		start.setEditor(sEditor);
		
		JLabel lblEndDate = new JLabel("End Date(yyyy-mm-dd)");
		lblEndDate.setBounds(250, 160, 135, 15);
		leftPanel.add(lblEndDate);
		lblEndDate.setFont(new Font("Arial", Font.PLAIN, 13));
		JSpinner end = new JSpinner(modelEnd);
		end.setBounds(250, 175, 103, 25);
		leftPanel.add(end);
		end.setValue(new Date());
		JSpinner.DateEditor dEditor = new JSpinner.DateEditor(end,"yyyy-MM-dd");
		end.setEditor(dEditor);
		
		//discount
		JLabel lblDiscount = new JLabel("Discount:");
		lblDiscount.setBounds(10, 220, 85, 15);
		leftPanel.add(lblDiscount);
		lblDiscount.setFont(new Font("Arial", Font.BOLD, 16));
		
		JLabel label = new JLabel("$");
		label.setFont(new Font("Arial", Font.BOLD, 16));
		label.setBounds(100, 240, 10, 25);
		leftPanel.add(label);
		
		txtDiscount = new JTextField();
		txtDiscount.setBounds(10, 240, 85, 25);
		leftPanel.add(txtDiscount);
		txtDiscount.setColumns(10);
		
		//title
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
		lblTitle.setBounds(10, 70, 54, 15);
		leftPanel.add(lblTitle);
		
		txtEnterTitle = new JTextField();
		txtEnterTitle.setFont(new Font("Arial", Font.ITALIC, 12));
		txtEnterTitle.setBounds(10, 90, 400, 25);
		leftPanel.add(txtEnterTitle);
		txtEnterTitle.setColumns(10);
		
		//description
		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setFont(new Font("Arial", Font.BOLD, 16));
		lblDescription.setBounds(10, 300, 105, 15);
		leftPanel.add(lblDescription);
		
		txtDescription = new JTextArea();
		txtDescription.setFont(new Font("Arial", Font.ITALIC, 12));
		txtDescription.setBounds(20, 330, 420, 25);
		leftPanel.add(txtDescription);
		
		//disclaimer
		JLabel lblDisclaimer = new JLabel("Disclaimer:");
		lblDisclaimer.setFont(new Font("Arial", Font.BOLD, 16));
		lblDisclaimer.setBounds(10, 380, 105, 15);
		leftPanel.add(lblDisclaimer);
		
		txtDisclaimer = new JTextArea();
		txtDisclaimer.setFont(new Font("Arial", Font.ITALIC, 12));
		txtDisclaimer.setBounds(20, 410, 420, 25);
		leftPanel.add(txtDisclaimer);
		
		//incentive id
		JLabel lblIncentiveId = new JLabel("Incentive ID");
		lblIncentiveId.setFont(new Font("Arial", Font.BOLD, 16));
		lblIncentiveId.setBounds(10, 10, 90, 15);
		leftPanel.add(lblIncentiveId);
		
		txtId = new JTextField();
		txtId.setBounds(10, 30, 150, 20);
		leftPanel.add(txtId);
		txtId.setColumns(10);
		//make it uneditable
		txtId.setEditable(false);
		
		
		
		//*************************rightPanel******************************
		JPanel rightPanel = new JPanel();
		rightPanel.setBounds(535, 100, 640, 475);
		frame.getContentPane().add(rightPanel);
		rightPanel.setLayout(null);
		
		//right title
		JLabel lblApplyToInventory = new JLabel("Apply To Inventory");
		lblApplyToInventory.setFont(new Font("Arial", Font.BOLD, 16));
		lblApplyToInventory.setBounds(36, 62, 150, 20);
		rightPanel.add(lblApplyToInventory);
		
		//category
		JLabel lblCategory = new JLabel("Category");
		lblCategory.setFont(new Font("Arial", Font.BOLD, 14));
		lblCategory.setBounds(35, 110, 68, 20);
		rightPanel.add(lblCategory);
		
		cbCategory = new JComboBox();
		cbCategory.setBounds(120, 110, 150, 20);
		rightPanel.add(cbCategory);
		cbCategory.setModel(new DefaultComboBoxModel<>(new String[] {
				"All",
				"New",
				"Used",
		}));
		cbCategory.addItemListener((ItemListener) this);
		
		
		
		//year
		JLabel lblYear = new JLabel("Year");
		lblYear.setFont(new Font("Arial", Font.BOLD, 14));
		lblYear.setBounds(35, 160, 68, 20);
		rightPanel.add(lblYear);
		
		txtYear = new JTextField();
		txtYear.setBounds(120, 161, 150, 20);
		rightPanel.add(txtYear);
		txtYear.setColumns(10);

		
		//make
		JLabel lblNewLabel = new JLabel("Make");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel.setBounds(35, 210, 68, 20);
		rightPanel.add(lblNewLabel);
		
		txtMake = new JTextField();
		txtMake.setBounds(120, 211, 150, 20);
		rightPanel.add(txtMake);
		txtMake.setColumns(10);
		
		
		//model
		JLabel lblModel = new JLabel("Model");
		lblModel.setFont(new Font("Arial", Font.BOLD, 14));
		lblModel.setBounds(35, 260, 68, 20);
		rightPanel.add(lblModel);

		txtModel = new JTextField();
		txtModel.setBounds(120, 261, 150, 20);
		rightPanel.add(txtModel);
		txtModel.setColumns(10);
		
		
		//trim
		JLabel lblTrim = new JLabel("Trim");
		lblTrim.setFont(new Font("Arial", Font.BOLD, 14));
		lblTrim.setBounds(340, 210, 68, 20);
		rightPanel.add(lblTrim);
		
		cbTrim = new JComboBox();
		cbTrim.setBounds(420, 210, 150, 20);
		rightPanel.add(cbTrim);
		cbTrim.setModel(new DefaultComboBoxModel<>(new String[] {
				"All",
				"High",
				"Mid",
				"Base",
		}));
		cbTrim.addItemListener((ItemListener) this);
		
		//color
		JLabel lblColor = new JLabel("Color");
		lblColor.setFont(new Font("Arial", Font.BOLD, 14));
		lblColor.setBounds(340, 110, 68, 20);
		rightPanel.add(lblColor);
		
	    cbColor = new JComboBox();
		cbColor.setBounds(420, 110, 150, 20);
		rightPanel.add(cbColor);
		cbColor.setModel(new DefaultComboBoxModel<>(new String[] {
				"All",
				"Black",
				"White",
				"Green",
				"Blue",
				"Red",
				"Yellow",
		}));
		cbColor.addItemListener((ItemListener) this);
		
		
		//type
		JLabel lblType = new JLabel("Type");
		lblType.setFont(new Font("Arial", Font.BOLD, 14));
		lblType.setBounds(340, 160, 68, 20);
		rightPanel.add(lblType);
		
		cbType = new JComboBox();
		cbType.setBounds(420, 160, 150, 20);
		rightPanel.add(cbType);
		cbType.setModel(new DefaultComboBoxModel<>(new String[] {
				"All",
				"SUV",
				"Truck",
				"Sedan",
				"Van",
				"Coupe",
				"Wagon",
				"Convertible",
				"Sport",
				"Diesel",
				"Crossover"
		}));
		cbType.addItemListener((ItemListener) this);
		
		
		//price
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setFont(new Font("Arial", Font.BOLD, 14));
		lblPrice.setBounds(35, 310, 68, 20);
		rightPanel.add(lblPrice);
		
		cbPrice = new JComboBox();
		cbPrice.setBounds(120, 310, 90, 20);
		rightPanel.add(cbPrice);
		cbPrice.setModel(new DefaultComboBoxModel<>(new String[] {
				"Null",
				"<=",
				">=",
		}));
		
	
		
		txtPrice = new JTextField();
		txtPrice.setBounds(220, 310, 100, 20);
		rightPanel.add(txtPrice);
		txtPrice.setColumns(10);
		//txtPrice.setEditable(false);
		
		
		//mileage
		JLabel lblMileage = new JLabel("Mileage");
		lblMileage.setFont(new Font("Arial", Font.BOLD, 14));
		lblMileage.setBounds(36, 360, 68, 20);
		rightPanel.add(lblMileage);
		
		cbMileage = new JComboBox();
		cbMileage.setBounds(120, 360, 90, 20);
		rightPanel.add(cbMileage);
		cbMileage.setModel(new DefaultComboBoxModel<>(new String[] {
				"Null",
				"<=",
				">=",
		}));
		
		txtMileage = new JTextField();
		txtMileage.setBounds(220, 360, 100, 20);
		rightPanel.add(txtMileage);
		txtMileage.setColumns(10);
		//txtMileage.setEditable(false);
		
		
		//apply button
		applyBtn = new JButton("Apply");
		applyBtn.setBounds(280, 420, 93, 23);
		rightPanel.add(applyBtn);
		applyBtn.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
			    applyClicked(e);
			}
		});
		
		//top panel
		JPanel topPanel = new JPanel();
		topPanel.setBounds(180, 20, 700, 70);
		frame.getContentPane().add(topPanel);
		
		JLabel lblTopTitle = new JLabel("Edit Incentives");
		lblTopTitle.setFont(new Font("Arial", Font.BOLD, 30));
		topPanel.add(lblTopTitle);
		
	}


}
