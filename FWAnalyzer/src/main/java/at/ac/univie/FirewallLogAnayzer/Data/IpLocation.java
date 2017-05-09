package at.ac.univie.FirewallLogAnayzer.Data;

public class IpLocation {
	private String countryIsoCode;
	private String countryName;
	private String subdivisionName;
	private String subdivisionIsoCode;
	private String cityName;
	private String postCode;
	private double latitude;
	private double longitude;
	
	
	
	public IpLocation(String countryIsoCode, String countryName, String subdivisionName, String subdivisionIsoCode,
			String cityName, String postCode, double latitude, double longitude) {
		super();
		this.countryIsoCode = countryIsoCode;
		this.countryName = countryName;
		this.subdivisionName = subdivisionName;
		this.subdivisionIsoCode = subdivisionIsoCode;
		this.cityName = cityName;
		this.postCode = postCode;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	
	public String getCountryIsoCode() {
		return countryIsoCode;
	}
	public String getCountryName() {
		return countryName;
	}
	public String getSubdivisionName() {
		return subdivisionName;
	}
	public String getSubdivisionIsoCode() {
		return subdivisionIsoCode;
	}
	public String getCityName() {
		return cityName;
	}
	public String getPostCode() {
		return postCode;
	}
	public double getLatitude() {
		return latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	
	
}
