package DTO;

import beans.DateTime;
import enums.Gender;
import enums.Role;

public class UserDTO {
	
	private String username;
	private String name;
	private String lastname;
	private Gender gender;
	private DateTime dateOfBirth;
	private Role role;
	
	public UserDTO() {}

	public UserDTO(String username, String name, String lastname, Gender gender, DateTime dateOfBirth, Role role) {
		this.username = username;
		this.name = name;
		this.lastname = lastname;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public DateTime getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(DateTime dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
}
