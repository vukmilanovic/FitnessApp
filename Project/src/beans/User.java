package beans;
import java.util.Collection;

import enums.Gender;
import enums.Role;

public class User extends Entity {

	private String username;
	private String password;
	private String name;
	private String lastname;
	private Gender gender;
	private DateTime dateOfBirth;
	private Role role;
	private MembershipFee membershipFee;
	private Collection<SportsBuilding> visitedSportsBuildings;
	private int numberOfCollectedPoints;
	private CustomerType customerType;
	private Collection<TrainingHistory> trainingHistory;
	private SportsBuilding sportsBuilding;
	
	public User() {
		super.setDeleted(false);
	}
	
	public User(String username, String password, String name, String lastname, Gender gender, DateTime dateOfBirth, Role role, MembershipFee membershipFee, Collection<SportsBuilding> visitedSportsBuildings, int numberOfCollectedPoints, CustomerType customerType, Collection<TrainingHistory> trainingHistory, SportsBuilding sportsBuilding) {
		super();
		super.setDeleted(false);
		this.username = username;
		this.password = password;
		this.name = name;
		this.lastname = lastname;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.role = role;
		this.membershipFee = membershipFee;
		this.visitedSportsBuildings = visitedSportsBuildings;
		this.numberOfCollectedPoints = numberOfCollectedPoints;
		this.customerType = customerType;
		this.trainingHistory = trainingHistory;
		this.sportsBuilding = sportsBuilding;
	}

	public MembershipFee getMembershipFee() {
		return membershipFee;
	}

	public void setMembershipFee(MembershipFee membershipFee) {
		this.membershipFee = membershipFee;
	}

	public Collection<SportsBuilding> getVisitedSportsBuildings() {
		return visitedSportsBuildings;
	}

	public void setVisitedSportsBuildings(Collection<SportsBuilding> visitedSportsBuildings) {
		this.visitedSportsBuildings = visitedSportsBuildings;
	}

	public int getNumberOfCollectedPoints() {
		return numberOfCollectedPoints;
	}

	public void setNumberOfCollectedPoints(int numberOfCollectedPoints) {
		this.numberOfCollectedPoints = numberOfCollectedPoints;
	}

	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	public Collection<TrainingHistory> getTrainingHistory() {
		return trainingHistory;
	}

	public void setTrainingHistory(Collection<TrainingHistory> trainingHistory) {
		this.trainingHistory = trainingHistory;
	}

	public SportsBuilding getSportsBuilding() {
		return sportsBuilding;
	}

	public void setSportsBuilding(SportsBuilding sportsBuilding) {
		this.sportsBuilding = sportsBuilding;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
