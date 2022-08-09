package DTO;

import beans.DateTime;
import beans.Location;
import enums.BuildingType;

public class SportFacilityDTO {

	private String name;
	private BuildingType type;
	private Location location;
	private double averageRating;
	private DateTime startWorkingTime;
	private DateTime endWorikingTime;
	
	public SportFacilityDTO() {}
	
	public SportFacilityDTO(String name, BuildingType type, Location location, double averageRating,
			DateTime startWorkingTime, DateTime endWorikingTime) {
		this.name = name;
		this.type = type;
		this.location = location;
		this.averageRating = averageRating;
		this.startWorkingTime = startWorkingTime;
		this.endWorikingTime = endWorikingTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BuildingType getType() {
		return type;
	}

	public void setType(BuildingType type) {
		this.type = type;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}

	public DateTime getStartWorkingTime() {
		return startWorkingTime;
	}

	public void setStartWorkingTime(DateTime startWorkingTime) {
		this.startWorkingTime = startWorkingTime;
	}

	public DateTime getEndWorikingTime() {
		return endWorikingTime;
	}

	public void setEndWorikingTime(DateTime endWorikingTime) {
		this.endWorikingTime = endWorikingTime;
	}
	
}
