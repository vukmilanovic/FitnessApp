package DTO;

import beans.DateTime;

public class SearchDateDTO {
	
	private String customerUsername;
	private DateTime startDate;
	private DateTime endDate;
	
	public SearchDateDTO() {}

	public SearchDateDTO(String customerUsername, DateTime startDate, DateTime endDate) {
		this.customerUsername = customerUsername;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getCustomerUsername() {
		return customerUsername;
	}

	public void setCustomerUsername(String customerUsername) {
		this.customerUsername = customerUsername;
	}

	public DateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(DateTime startDate) {
		this.startDate = startDate;
	}

	public DateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(DateTime endDate) {
		this.endDate = endDate;
	}
	
}
