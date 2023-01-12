package application;

public class Airport extends Object {
	//Instance Variables
	private String code;
	private String city;
	private String state;
	private double latitude;
	private double longitude;
	
	//Constructor
	public Airport(String code, double latitude, double longitude, String city, String state) {
		this.code = code;
		this.longitude = longitude;
		this.latitude = latitude;
		this.city = city;
		this.state = state;
	}
	
	protected Airport(String code) {
		this.code = code;
	}
	
	//Methods
	public String getCode() {
		return code;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public String getCity() {
		return city;
	}
	
	public String getState() {
		return state;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Airport) {
			Airport a = (Airport)o;
			return this.code.equals(a.code);
		}
		return false;
	}
	
	@Override
	public String toString() {
		String end = String.format("(%s-%s, %s:  %.2f,  %.2f)", getCode(), getCity(), getState(), getLatitude(), getLongitude());
		return end;
	}

}
