package Edit;

public class EditIncentives {
	private String id;
	private String title;
	private int discount;
	private String description;
	private String disclaimer;
	
	private int year;
	private String make;
	private String model;
	private double price;
	private double mileage;
	
	private String category;
	private String color;
	private String type;
	private String trim;
	
	
    public EditIncentives(String[] arr){
		this.id = arr[0];
		this.title = arr[1];
		this.discount = Integer.parseInt(arr[2]);
		this.description = arr[3];
		this.disclaimer = arr[4];
		this.year = Integer.parseInt(arr[5]);
		this.make = arr[6];
		this.model = arr[7];
		this.price = Double.parseDouble(arr[8]);
		this.mileage = Double.parseDouble(arr[9]);
		this.setCategory(arr[10]);
		this.setColor(arr[11]);
		this.setType(arr[12]);
		this.setTrim(arr[13]);
		
	}
    
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getDiscount() {
		return discount;
	}
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDisclaimer() {
		return disclaimer;
	}
	public void setDisclaimer(String disclaimer) {
		this.disclaimer = disclaimer;
	}


	public String getMake() {
		return make;
	}


	public void setMake(String make) {
		this.make = make;
	}


	public int getYear() {
		return year;
	}


	public void setYear(int year) {
		this.year = year;
	}


	public String getModel() {
		return model;
	}


	public void setModel(String model) {
		this.model = model;
	}


	public double getMileage() {
		return mileage;
	}


	public void setMileage(double mileage) {
		this.mileage = mileage;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public String getColor() {
		return color;
	}


	public void setColor(String color) {
		this.color = color;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getTrim() {
		return trim;
	}


	public void setTrim(String trim) {
		this.trim = trim;
	}
}
