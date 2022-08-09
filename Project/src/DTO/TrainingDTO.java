package DTO;

import beans.DateTime;
import enums.TrainingType;

public class TrainingDTO {

	private DateTime dateAndTimeOfCheckIn;
	private String customerUsername;
	private String coachUsername;
	private TrainingType trainingType;
	private String description;
	private String sportsBuildingName;
	private String workoutName;
	
	public TrainingDTO() {}
	
	public TrainingDTO(DateTime dateAndTimeOfCheckIn, String customerUsername, String coachUsername,
			TrainingType trainingType, String description, String sportsBuildingName, String workoutName) {
		this.dateAndTimeOfCheckIn = dateAndTimeOfCheckIn;
		this.customerUsername = customerUsername;
		this.coachUsername = coachUsername;
		this.trainingType = trainingType;
		this.description = description;
		this.sportsBuildingName = sportsBuildingName;
		this.workoutName = workoutName;
	}
	
	public String getWorkoutName() {
		return workoutName;
	}

	public void setWorkoutName(String workoutName) {
		this.workoutName = workoutName;
	}

	public DateTime getDateAndTimeOfCheckIn() {
		return dateAndTimeOfCheckIn;
	}
	public void setDateAndTimeOfCheckIn(DateTime dateAndTimeOfCheckIn) {
		this.dateAndTimeOfCheckIn = dateAndTimeOfCheckIn;
	}
	public String getCustomerUsername() {
		return customerUsername;
	}
	public void setCustomerUsername(String customerUsername) {
		this.customerUsername = customerUsername;
	}
	public String getCoachUsername() {
		return coachUsername;
	}
	public void setCoachUsername(String coachUsername) {
		this.coachUsername = coachUsername;
	}
	public TrainingType getTrainingType() {
		return trainingType;
	}
	public void setTrainingType(TrainingType trainingType) {
		this.trainingType = trainingType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSportsBuildingName() {
		return sportsBuildingName;
	}
	public void setSportsBuildingName(String sportsBuildingName) {
		this.sportsBuildingName = sportsBuildingName;
	}
	
}
