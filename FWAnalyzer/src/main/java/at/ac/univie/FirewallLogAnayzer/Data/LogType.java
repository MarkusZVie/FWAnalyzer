package at.ac.univie.FirewallLogAnayzer.Data;

public class LogType {
	private String manufacturer;
	private int id;
	

	public LogType(String manufacturer, int id) {
		super();
		this.manufacturer = manufacturer;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}


	
	
}
