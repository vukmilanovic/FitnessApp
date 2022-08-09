package DTO;

import java.time.LocalDateTime;

public class CancelTrainingDTO {

	private String username;
	private String trainingName;
	
	public CancelTrainingDTO(String username, String trainingName) {
		super();
		this.username = username;
		this.trainingName = trainingName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTrainingName() {
		return trainingName;
	}

	public void setTrainingName(String trainingName) {
		this.trainingName = trainingName;
	}
	
}
