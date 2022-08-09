package DTO;

import enums.TrainingType;

public class SortDTO {

	private String sortBy;
	private String username;
	private TrainingType type;
	
	public SortDTO() {}	

	public SortDTO(String sortBy, String username, TrainingType type) {
		this.sortBy = sortBy;
		this.username = username;
		this.type = type;
	}
	
	public TrainingType getType() {
		return type;
	}

	public void setType(TrainingType type) {
		this.type = type;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
