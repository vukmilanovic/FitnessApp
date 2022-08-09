package DTO;

import beans.DateTime;

public class ManagerDTO {
	private String username;
	private String password;
	private String name;
	private String lastname;
	private String gender;
	private DateTime dob;
	private String building;
	
	public ManagerDTO(String username, String password, String name, String lastname, String gender, DateTime dob, String building) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.lastname = lastname;
		this.gender = gender;
		this.dob = dob;
		this.building = building;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public String getLastname() {
		return lastname;
	}

	public String getGender() {
		return gender;
	}

	public DateTime getDob() {
		return dob;
	}
	
	public String getBuilding() {
		return building;
	}
}
