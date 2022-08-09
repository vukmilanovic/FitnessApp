package DTO;

import beans.DateTime;

public class PromoCodeDTO {

	private String code;
	private DateTime startValidityDate;
	private DateTime endValidityDate;
	private int usageNumber;
	private double discount;
	
	public PromoCodeDTO() {}
	
	public PromoCodeDTO(String code, DateTime startValidityDate, DateTime endValidityDate, int usageNumber,
			double discount) {
		this.code = code;
		this.startValidityDate = startValidityDate;
		this.endValidityDate = endValidityDate;
		this.usageNumber = usageNumber;
		this.discount = discount;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public DateTime getStartValidityDate() {
		return startValidityDate;
	}

	public void setStartValidityDate(DateTime startValidityDate) {
		this.startValidityDate = startValidityDate;
	}

	public DateTime getEndValidityDate() {
		return endValidityDate;
	}

	public void setEndValidityDate(DateTime endValidityDate) {
		this.endValidityDate = endValidityDate;
	}

	public int getUsageNumber() {
		return usageNumber;
	}

	public void setUsageNumber(int usageNumber) {
		this.usageNumber = usageNumber;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}
	
}
