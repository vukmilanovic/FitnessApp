package DTO;

public class CoachDTO {
	
	private String username;
	private String name;
	private String lastname;	
	
	public CoachDTO() {}
	
	public CoachDTO(String username, String name, String lastname) {
		this.username = username;
		this.name = name;
		this.lastname = lastname;
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
	
}
