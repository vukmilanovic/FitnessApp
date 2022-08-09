package DTO;

import enums.Role;

public class LoggedUserDTO {

	private String username;
	private Role role;
	
	public LoggedUserDTO() {}
	
	public LoggedUserDTO(String username, Role role) {
		this.username = username;
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
}
