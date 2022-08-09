package beans;

import enums.TrainingType;

public class Training extends Entity {

	private String name;
	private TrainingType trainingType;
	private SportsBuilding sportsBuilding;
	private int duration;
	private User coach;
	private String description;
	private String picturePath;
	private double price;
	
	public Training() {
		super.setDeleted(false);
	}
	
	public Training(String name, TrainingType trainingType, SportsBuilding sportsBuilding, int duration, User coach, String description, String picturePath, double price) {
		super();
		super.setDeleted(false);
		this.name = name;
		this.trainingType = trainingType;
		this.sportsBuilding = sportsBuilding;
		this.duration = duration;
		this.coach = coach;
		this.description = description;
		this.picturePath = picturePath;
		this.price = price;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TrainingType getTrainingType() {
		return trainingType;
	}

	public void setTrainingType(TrainingType trainingType) {
		this.trainingType = trainingType;
	}
	
	public SportsBuilding getSportsBuilding() {
		return sportsBuilding;
	}

	public void setSportsBuilding(SportsBuilding sportsBuilding) {
		this.sportsBuilding = sportsBuilding;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public User getCoach() {
		return coach;
	}

	public void setCoach(User coach) {
		this.coach = coach;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public static TrainingType typeFromString(String type) {
		switch(type) {
		case "Personal":
			return TrainingType.Personal;
		case "Group":
			return TrainingType.Group;
		case "Gym":
			return TrainingType.Gym;
		case "Sauna":
			return TrainingType.Sauna;
		case "Massage":
			return TrainingType.Massage;
		default:
			System.out.println("You're not supposed to be here");
			return null;
		}
	}
}
