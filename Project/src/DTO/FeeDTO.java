package DTO;

import enums.MembershipFeeType;

public class FeeDTO {

	private MembershipFeeType type;
	private double price;
	private String customerUsername;
	private int numberOfEntries;
	private int totalAppereances;
	private String code;
	
	public FeeDTO() {}

	public FeeDTO(MembershipFeeType type, double price, String customerUsername, int numberOfEntries,
			int totalAppereances, String code) {
		this.type = type;
		this.price = price;
		this.customerUsername = customerUsername;
		this.numberOfEntries = numberOfEntries;
		this.totalAppereances = totalAppereances;
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public MembershipFeeType getType() {
		return type;
	}

	public void setType(MembershipFeeType type) {
		this.type = type;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getCustomerUsername() {
		return customerUsername;
	}

	public void setCustomerUsername(String customerUsername) {
		this.customerUsername = customerUsername;
	}

	public int getNumberOfEntries() {
		return numberOfEntries;
	}

	public void setNumberOfEntries(int numberOfEntries) {
		this.numberOfEntries = numberOfEntries;
	}

	public int getTotalAppereances() {
		return totalAppereances;
	}

	public void setTotalAppereances(int totalAppereances) {
		this.totalAppereances = totalAppereances;
	}
	
}
