package DTO;

public class NewBuildingDTO {
	// naziv, tip, loc, logo, radno vreme
	
	private String name;
	private String type;
	private String geoWidth;
	private String geoLen;
	private String street;
	private String number;
	private String city;
	private String postalCode;
	private String logoPath;
	private String startHours;
	private String endHours;
	
	public NewBuildingDTO(String name, String type, String geoWidth, String geoLen, String street, String number,
			String city, String postalCode, String logoPath, String startHours, String endHours) {
		super();
		this.name = name;
		this.type = type;
		this.geoWidth = geoWidth;
		this.geoLen = geoLen;
		this.street = street;
		this.number = number;
		this.city = city;
		this.postalCode = postalCode;
		this.logoPath = logoPath;
		this.startHours = startHours;
		this.endHours = endHours;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getGeoWidth() {
		return geoWidth;
	}

	public String getGeoLen() {
		return geoLen;
	}

	public String getStreet() {
		return street;
	}

	public String getNumber() {
		return number;
	}

	public String getCity() {
		return city;
	}

	public String getPostalCode() {
		return postalCode;
	}
	
	public String getLogoPath() {
		return logoPath;
	}

	public String getStartHours() {
		return startHours;
	}

	public String getEndHours() {
		return endHours;
	}
}
