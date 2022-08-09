package DTO;

import beans.SportsBuilding;
import beans.User;

public class NewTrainingDTO {
	private String name;
	private String trainingType;
	private SportsBuilding sportsBuilding;
	private String duration;
	private User coach;
	private String description;
	private String picturePath;
	private String price;
	
	public NewTrainingDTO(String name, String trainingType, SportsBuilding sportsBuilding, String duration, User coach,
			String description, String picturePath, String price) {
		this.name = name;
		this.trainingType = trainingType;
		this.sportsBuilding = sportsBuilding;
		this.duration = duration;
		this.coach = coach;
		this.description = description;
		this.picturePath = picturePath;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public String getTrainingType() {
		return trainingType;
	}

	public SportsBuilding getSportsBuilding() {
		return sportsBuilding;
	}

	public String getDuration() {
		return duration;
	}

	public User getCoach() {
		return coach;
	}

	public String getDescription() {
		return description;
	}

	public String getPicturePath() {
		return picturePath;
	}

	public String getPrice() {
		return price;
	}
}
