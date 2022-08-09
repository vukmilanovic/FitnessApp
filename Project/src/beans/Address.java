package beans;

public class Address extends Entity {
	
	private String street;
	private String number;
	private String city;
	private String postalCode;
	
	public Address() {
		super.setDeleted(false);
	}
	
	public Address(String street, String number, String city, String postalCode) {
		super();
		super.setDeleted(false);
		this.street = street;
		this.number = number;
		this.city = city;
		this.postalCode = postalCode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@Override
	public String toString() {
		return street + " " + number + ", " + city + ", " + postalCode;
	}
	
	
}
