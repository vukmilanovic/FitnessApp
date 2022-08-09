package services;

import java.util.Collection;

import DAO.UserDAO;
import DTO.UserDTO;
import beans.User;
import enums.Role;

public class UserService {

	private UserDAO userDAO;
	
	public UserService() {
		this.userDAO = UserDAO.getInstance();
	}
	
	public Collection<User> getUsers() {
		return this.userDAO.getUsers();
	}
	
	public UserDTO getUser(String username) {
		
		User user = this.userDAO.getUser(username);
		UserDTO dto = new UserDTO(user.getUsername(), user.getName(), user.getLastname(), user.getGender(), user.getDateOfBirth(), user.getRole());
		
		return dto;
	}
	
	public User getWholeUser(String username) {
		User user = this.userDAO.getUser(username);
		return user;
	}
	
	public boolean addUser(User newUser) {
		return this.userDAO.addUser(newUser);
	}
	
	public void editUser(String username, User newUser) {
		if(newUser.getPassword().equals("")) {
			User user = this.userDAO.getUser(username);
			newUser.setPassword(user.getPassword());
		}
		
		this.userDAO.editUser(username, newUser);
	}
	
	public void deleteUser(String username) {
		this.userDAO.deleteUser(username);
	}
	
	public Role getUsersRole(String username) {
		for (User user : this.userDAO.getUsers()) {
			if(user.getUsername().equals(username)) {
				return user.getRole(); 
			}
		}
		return null;
	}
	
	public String checkIfUserExistsForRegister(String username) {
		for (User user : this.userDAO.getUsers()) {
			if(user.getUsername().equals(username)) {
				return user.getUsername();
			}
		}
		return null;
	}
	
	public Collection<User> getTrainers(){
		return userDAO.getTrainers();
	}
	
	public String checkIfUserExists(String username, String password) {
		for (User user : this.userDAO.getUsers()) {
			if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
				return user.getUsername();
			}
		}
		return null;
	}
	
	public Collection<User> getUnemployedManagers(){
		return userDAO.getUnemployedManagers();
	}
	
	public Collection<User> getFacilityUsers(String facilityName){
		return userDAO.getFacilityUsers(facilityName);
	}
	
}
