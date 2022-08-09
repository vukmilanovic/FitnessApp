package beans;

import enums.BuildingType;

import java.util.Calendar;
import java.util.Collection;

import enums.BuildingContent;

public class SportsBuilding extends Entity {
	
	private String name;
	private BuildingType type;
	private Collection<String> contentName;
	private Location location;
	private String logoPath;
	private double averageRating;
	private DateTime startWorkingTime;
	private DateTime endWorkingTime;
	
	public SportsBuilding() {
		super.setDeleted(false);
	}

	public SportsBuilding(String name, BuildingType type, Collection<String> contentName, Location location,
			String logoPath, double averageRating, DateTime startWorkingTime, DateTime endWorkingTime) {
		super();
		super.setDeleted(false);
		this.name = name;
		this.type = type;
		this.contentName = contentName;
		this.location = location;
		this.logoPath = logoPath;
		this.averageRating = averageRating;
		this.startWorkingTime = startWorkingTime;
		this.endWorkingTime = endWorkingTime;
	}

	public boolean isOpen() {
		// 0 is for Sunday (i think)
		int day = Calendar.DAY_OF_WEEK;
		int hours = Calendar.HOUR_OF_DAY;
		
		if((day != 0) && (hours <= this.endWorkingTime.getHours()) && (hours >= this.startWorkingTime.getHours()))
			return true;
		else
			return false;
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

	public Collection<String> getContentNames() {
		return contentName;
	}

	public void setContentNames(Collection<String> contentNames) {
		this.contentName = contentNames;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
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

	public DateTime getEndWorkingTime() {
		return endWorkingTime;
	}

	public void setendWorkingTime(DateTime endWorkingTime) {
		this.endWorkingTime = endWorkingTime;
	}

	public static BuildingType StringToType(String type) {
		switch (type) {
		case "Gym":
			return BuildingType.GYM;
		case "Pool":
			return BuildingType.POOL;
		case "Sports Center":
			return BuildingType.SPORTS_CENTER;
		case "Dance Studio":
			return BuildingType.DANCE_STUDIO;
		default:
			return null;
		}
	}
}
