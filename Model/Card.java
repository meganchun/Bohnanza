package Model;

public class Card {

	// instance variables
	private String name;
	private int numOfBean;
	private int[] beanometer;
	private String fileName;
	
	// constructor
	public Card(String name, int numOfBean,
			int[] beanometer, String fileName) {
		
		super();
		this.name = name;
		this.numOfBean = numOfBean;
		this.beanometer = beanometer;
		this.fileName = fileName;
	
	}
	
	// getters and setters
	public String getBeanType() {
		return name;
	}
	
	public void setBeanType(String name) {
		this.name = name;
	}
	
	public int getNumOfBean() {
		return numOfBean;
	}
	
	public void setNumOfBean(int numOfBean) {
		this.numOfBean = numOfBean;
	}
	
	public int[] getbeanometer() {
		return beanometer;
	}
	
	public void setbeanometer(int[] beanometer) {
		this.beanometer = beanometer;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void getFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public int getCoinsEarned(int numberOfBeans) {	
		
		for (int i = 0; i < getbeanometer().length; i++) {
			if (i == numberOfBeans && getbeanometer()[i] != 0) {
				return getbeanometer()[i];
			}
		}
		
		
		for (int i = numberOfBeans-1; i >= 0; i--) {
			if (getbeanometer()[i] != 0) {
				return getbeanometer()[i];
			}
		}
		
		return 0;
		
	}
	
}
