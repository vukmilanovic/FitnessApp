package beans;

public class Location {
	
	private double geoWidth;
	private double geoLen;
	private Address address;
	
	public Location() {

	}

	public Location(double geoWidth, double geoLen, Address address) {
		super();
		this.geoWidth = geoWidth;
		this.geoLen = geoLen;
		this.address = address;
	}

	public double getGeoWidth() {
		return geoWidth;
	}

	public void setGeoWidth(double geoWidth) {
		this.geoWidth = geoWidth;
	}

	public double getGeoLen() {
		return geoLen;
	}

	public void setGeoLen(double geoLen) {
		this.geoLen = geoLen;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}
