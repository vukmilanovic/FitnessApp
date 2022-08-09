package beans;

import enums.MembershipFeeStatus;
import enums.MembershipFeeType;

public class MembershipFee extends Entity {

	private int id;
	private MembershipFeeType membershipFeeType;
	private DateTime paymentDate;
	private DateTime dateAndTimeOfValidity;
	private double price;
	private String customerUsername;
	private MembershipFeeStatus membershipFeeStatus;
	private int numberOfEntries;
	private int numberOfRemainingAppointements;
	private int totalAppereances;
	
	public MembershipFee() {
		super.setDeleted(false);
	}
	
	public MembershipFee(int id, MembershipFeeType membershipFeeType, DateTime paymentDate, DateTime dateAndTimeOfValidity,
			double price, String customerUsername, MembershipFeeStatus membershipFeeStatus, int numberOfEntries, int numberOfRemainingAppointments, int totalAppereances) {
		super();
		super.setDeleted(false);
		this.id = id;
		this.membershipFeeType = membershipFeeType;
		this.paymentDate = paymentDate;
		this.dateAndTimeOfValidity = dateAndTimeOfValidity;
		this.price = price;
		this.customerUsername = customerUsername;
		this.membershipFeeStatus = membershipFeeStatus;
		this.numberOfEntries = numberOfEntries;
		this.numberOfRemainingAppointements = numberOfRemainingAppointments;
		this.totalAppereances = totalAppereances;
	}

	public int getNumberOfRemainingAppointements() {
		return numberOfRemainingAppointements;
	}

	public void setNumberOfRemainingAppointements(int numberOfRemainingAppointements) {
		this.numberOfRemainingAppointements = numberOfRemainingAppointements;
	}

	public int getTotalAppereances() {
		return totalAppereances;
	}

	public void setTotalAppereances(int totalAppereances) {
		this.totalAppereances = totalAppereances;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public MembershipFeeType getMembershipFeeType() {
		return membershipFeeType;
	}

	public void setMembershipFeeType(MembershipFeeType membershipFeeType) {
		this.membershipFeeType = membershipFeeType;
	}

	public DateTime getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(DateTime paymentDate) {
		this.paymentDate = paymentDate;
	}

	public DateTime getDateAndTimeOfValidity() {
		return dateAndTimeOfValidity;
	}

	public void setDateAndTimeOfValidity(DateTime dateAndTimeOfValidity) {
		this.dateAndTimeOfValidity = dateAndTimeOfValidity;
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

	public MembershipFeeStatus getMembershipFeeStatus() {
		return membershipFeeStatus;
	}

	public void setMembershipFeeStatus(MembershipFeeStatus membershipFeeStatus) {
		this.membershipFeeStatus = membershipFeeStatus;
	}

	public int getNumberOfEntries() {
		return numberOfEntries;
	}

	public void setNumberOfEntries(int numberOfEntries) {
		this.numberOfEntries = numberOfEntries;
	}
	
}
