package DAO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import beans.*;
import enums.CustomerTypeName;
import enums.Gender;
import enums.MembershipFeeStatus;
import enums.MembershipFeeType;
import enums.Role;
import io.jsonwebtoken.lang.Collections;
import utils.GlobalPaths;

public class UserDAO {
	
	private static UserDAO instance = null;
	
	public static UserDAO getInstance() {
		if(instance == null) {
			instance = new UserDAO();
		}
		
		return instance;
	}
	
	private Gson g = new Gson();
	private HashMap<String, User> users;
	private MembershipFeeDAO membershipFeeDAO;
	
	public UserDAO() {
		Load();
//		this.membershipFeeDAO = MembershipFeeDAO.getInstance();
//		
//		MembershipFee fee = new MembershipFee(1, MembershipFeeType.Monthly, new DateTime(1, 6, 2022, 0, 0), new DateTime(1, 7, 2022, 0, 0), 2500, "ckepatroni", MembershipFeeStatus.Active, 1, 5, 30);
//		User admin = new User("stevica", "321", "Stevan", "Stevanovic", Gender.Male, new DateTime(2, 4, 1978, 0, 0), Role.Administrator, new MembershipFee(), new ArrayList<SportsBuilding>(), 0, new CustomerType(), new ArrayList<TrainingHistory>(), new SportsBuilding());
//		User coach1 = new User("djomla", "123", "Mladen", "Krstajic", Gender.Male, new DateTime(11, 10, 1974, 0, 0), Role.Coach, new MembershipFee(), new ArrayList<SportsBuilding>(), 0, new CustomerType(), new ArrayList<TrainingHistory>(), new SportsBuilding());
//		User coach2 = new User("sima", "123", "Sima", "Trobonjaca", Gender.Male, new DateTime(11, 10, 1974, 0, 0), Role.Coach, new MembershipFee(), new ArrayList<SportsBuilding>(), 0, new CustomerType(), new ArrayList<TrainingHistory>(), new SportsBuilding());
//		User customer1 = new User("ckepatroni", "123", "Vuk", "Milanovic", Gender.Male, new DateTime(15, 9, 2000, 0, 0), Role.Customer, fee, new ArrayList<SportsBuilding>(), 0, new CustomerType(CustomerTypeName.Gold, 0.15, 1000), new ArrayList<TrainingHistory>(), new SportsBuilding());
//		User customer2 = new User("spikaLudi", "123", "Nikola", "Spiric", Gender.Male, new DateTime(18, 3, 2000, 0, 0), Role.Customer, new MembershipFee(), new ArrayList<SportsBuilding>(), 0, new CustomerType(CustomerTypeName.Bronze, 0.05, 150), new ArrayList<TrainingHistory>(), new SportsBuilding());
//		this.membershipFeeDAO.newMembershipFee(fee);
//		
//		this.users.put(admin.getUsername(), admin);
//		this.users.put(coach1.getUsername(), coach1);
//		this.users.put(coach2.getUsername(), coach2);
//		this.users.put(customer1.getUsername(), customer1);
//		this.users.put(customer2.getUsername(), customer2);
		
//		SportsBuildingDAO buildingDAO = SportsBuildingDAO.getInstance();
//		User manager = new User(
//				"vlodson",
//				"1312",
//				"Vladimir",
//				"Lunic",
//				Gender.Male,
//				new DateTime(10, 4, 2000, 0, 0),
//				Role.Manager,
//				new MembershipFee(),
//				new ArrayList<SportsBuilding>(),
//				0,
//				new CustomerType(),
//				new ArrayList<TrainingHistory>(),
//				null);
		
//		User manager = new User(
//				"grom",
//				"1312",
//				"Vladimir",
//				"Lunic",
//				Gender.Male,
//				new DateTime(10, 4, 2000, 0, 0),
//				Role.Manager,
//				new MembershipFee(),
//				new ArrayList<SportsBuilding>(),
//				0,
//				new CustomerType(),
//				new ArrayList<TrainingHistory>(),
//				buildingDAO.getSportsBuilding("Spens1"));
//
//		this.users.put(manager.getUsername(), manager);
		Save();
	}
	
	public void setNewNumberOfPoints(User customer, int points) {
		customer.setNumberOfCollectedPoints(points);
		Save();
	}
	
	public void setCustomersFee(User customer, MembershipFee newFee) {
		customer.setMembershipFee(newFee);
		Save();
	}
	
	public void setCustomersVisitedBuilding(User customer, SportsBuilding facility) {
		customer.getVisitedSportsBuildings().add(facility);
		Save();
	}
	
	public ArrayList<User> searchByName(String name) {
		ArrayList<User> searchedUsers = new ArrayList<User>();
		
		if(name.equals(null)) {
			return searchedUsers;
		}
		
		for(User user : this.users.values()) {
			if(user.getName().toLowerCase().contains(name.toLowerCase()))
				searchedUsers.add(user);
		}
		
		return searchedUsers;
	}
	
	public ArrayList<User> searchByLastname(String lastname) {
		ArrayList<User> searchedUsers = new ArrayList<User>();
		
		if(lastname.equals(null)) {
			return searchedUsers;
		}
		
		for(User user : this.users.values()) {
			if(user.getLastname().toLowerCase().contains(lastname.toLowerCase()))
				searchedUsers.add(user);
		}
		
		return searchedUsers;
	}
	
	public ArrayList<User> searchByUsername(String username) {
		ArrayList<User> searchedUsers = new ArrayList<User>();
		
		if(username.equals(null)) {
			return searchedUsers;
		}
		
		for(User user : this.users.values()) {
			if(user.getUsername().toLowerCase().contains(username.toLowerCase()))
				searchedUsers.add(user);
		}
		
		return searchedUsers;
	}
	
	public ArrayList<User> filterByRole(Role role) {
		ArrayList<User> filteredUsers = new ArrayList<User>();
		
		for(User user : this.users.values()) {
			if(user.getRole() == role)
				filteredUsers.add(user);
		}
		
		return filteredUsers;
	}
	
	public ArrayList<User> filterByCustomerType(CustomerTypeName customerType) {
		ArrayList<User> filteredUsers = new ArrayList<User>();
		
		for (User user : this.users.values()) {
			if(user.getCustomerType().getTypeName() == customerType)
				filteredUsers.add(user);
		}
		
		return filteredUsers;
	}
	
	public boolean addUser(User newUser) {
		for (String username : users.keySet()) {
			if(newUser.getUsername().equals(username)) {
				return false;
			}
		}
		this.users.put(newUser.getUsername(), newUser);
		Save();
		return true;
	}
	
	public void deleteUser(String username) {
		for (User user : this.users.values()) {
			if(user.getUsername().equals(username)) {
				user.setDeleted(true);
				break;
			}
		}
		Save();
	}
	
	public void editUser(String username, User user) {
		this.users.remove(username);
		this.users.put(user.getUsername(), user);
		Save();
	}
	
	public Collection<User> getUsers() {
		Collection<User> users = new ArrayList<User>();
		for (User user : this.users.values()) {
			if(user.isDeleted() == false) {
				users.add(user);
			}
		}
		return users;
	}
	
	public User getUser(String username) {
		User user = users.get(username);
		if(user.isDeleted() == true) { 
			return null;
		}
		return user;
	}
	
	public Collection<User> getUsersByRole(Role role) {
		Collection<User> users = new ArrayList<User>();
		for (User user : getUsers()) {
			if(user.getRole() == role) {
				users.add(user);
			}
		}
		return users;
	}
	
	public Collection<User> getUnemployedManagers(){
		Collection<User> users = new ArrayList<User>();
		for (User user : this.users.values()) {
			if(!user.isDeleted() && user.getRole() == Role.Manager && user.getSportsBuilding() == null) {
				users.add(user);
			}
		}
		return users;
	}
	
	public Collection<User> getCustomersByType(CustomerTypeName type) {
		Collection<User> customers = new ArrayList<User>();
		for (User customer : getUsersByRole(Role.Customer)) {
			if(customer.getCustomerType().getTypeName() == type) {
				customers.add(customer);
			}
		}
		return customers;
	}
	
	public Collection<User> getTrainers(){
		Collection<User> trainers = new ArrayList<User>();
		for(User u : users.values()) {
			if(!u.isDeleted() && u.getRole() == Role.Coach)
				trainers.add(u);
		}
		
		return trainers;
	}
	
	public Collection<User> getFacilityUsers(String facilityName){
		Collection<User> facilityUsers = new ArrayList<User>();
		
		for(User u : users.values())
			for(SportsBuilding s : u.getVisitedSportsBuildings())
				if(s.getName().equals(facilityName)) {
					facilityUsers.add(u);
					break;
				}
		
		return facilityUsers;
	}
	
	private void Save() {
		String json = g.toJson(users);
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(GlobalPaths.usersDBPath)));
			writer.write(json);
			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void Load() {
		String json = "";
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(GlobalPaths.usersDBPath)));
			String currentLine;
			while((currentLine = reader.readLine()) != null)
				json += currentLine;
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			return;
		}
		
		if(g.fromJson(json, new TypeToken<HashMap<String, User>>(){}.getType()) == null) {
			this.users = new HashMap<String, User>();
		} else {
			this.users = g.fromJson(json, new TypeToken<HashMap<String, User>>(){}.getType());
		}
		
	}
}
