package DTO;

import beans.DateTime;

public class SearchCoachDateDTO {

	private String coachUsername;
	private DateTime endDate;
	private DateTime startDate;
	private boolean isPersonal;
	
	public SearchCoachDateDTO() {}
	
	public SearchCoachDateDTO(String coachUsername, DateTime endDate, DateTime startDate, boolean isPersonal) {
		this.coachUsername = coachUsername;
		this.endDate = endDate;
		this.startDate = startDate;
		this.isPersonal = isPersonal;
	}
	
	public String getCoachUsername() {
		return coachUsername;
	}
	public void setCoachUsername(String coachUsername) {
		this.coachUsername = coachUsername;
	}
	public DateTime getEndDate() {
		return endDate;
	}
	public void setEndDate(DateTime endDate) {
		this.endDate = endDate;
	}
	public DateTime getStartDate() {
		return startDate;
	}
	public void setStartDate(DateTime startDate) {
		this.startDate = startDate;
	}
	public boolean isPersonal() {
		return isPersonal;
	}
	public void setPersonal(boolean isPersonal) {
		this.isPersonal = isPersonal;
	}
	
}
