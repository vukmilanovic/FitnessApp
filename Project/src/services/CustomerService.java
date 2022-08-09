package services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import sorting.*;
import DAO.CommentDAO;
import DAO.SportsBuildingDAO;
import DAO.TrainingHistoryDAO;
import DAO.UserDAO;
import DTO.CommentDTO;
import DTO.SortDTO;
import beans.Comment;
import beans.DateTime;
import beans.SportsBuilding;
import beans.Training;
import beans.TrainingHistory;
import beans.User;
import enums.BuildingType;
import enums.CommentStatus;
import enums.CustomerTypeName;
import enums.Role;
import enums.TrainingType;

public class CustomerService {

	private UserDAO userDAO;
	private TrainingHistoryDAO trainingHistoryDAO;
	private CommentDAO commentDAO;
	private SportsBuildingDAO sportsBuildingDAO;
	
	public CustomerService() {
		this.userDAO = UserDAO.getInstance();
		this.trainingHistoryDAO = TrainingHistoryDAO.getInstance();
		this.commentDAO = CommentDAO.getInstance();
		this.sportsBuildingDAO = SportsBuildingDAO.getInstance();
	}
	
	public Collection<User> GetAllCustomers() {
		Collection<User> customers = new ArrayList<User>();
		for (User user : userDAO.getUsers()) {
			if(user.getRole().equals(Role.Customer)) {
				customers.add(user);
			}
		}
		return customers;
	}
	
	public Collection<User> GetCustomersByType(CustomerTypeName type) {
		Collection<User> customersByType = new ArrayList<User>();
		for (User customer : GetAllCustomers()) {
			if(customer.getCustomerType().getTypeName() == type)
				customersByType.add(customer);
		}
		return customersByType;
	}
	
	public Collection<TrainingHistory> GetAllCustomerTrainings(String username) {
		LocalDateTime localdt = LocalDateTime.now().minusDays(30);
		DateTime dt = new DateTime(localdt.getDayOfMonth(), localdt.getMonthValue(), localdt.getYear(), localdt.getHour(), localdt.getMinute());
		Collection<TrainingHistory> customerTrainings = new ArrayList<TrainingHistory>();
		for (TrainingHistory trainingHistory : trainingHistoryDAO.getTrainingHistories()) {
			if(trainingHistory.getCustomer().getUsername().equals(username) && trainingHistory.getDateAndTimeOfCheckIn().isAfter(dt)) {
				customerTrainings.add(trainingHistory);
			}
		}
		return customerTrainings;
	}
	
	public boolean CommentSportsBuilding(CommentDTO dto) {
		
		User customer = this.userDAO.getUser(dto.getCustomerUsername());
		SportsBuilding facility = this.sportsBuildingDAO.getSportsBuilding(dto.getFacilityName());

		if(!checkIfUserVisitFacility(customer, facility)) {
			return false;
		}
		
		Comment newComment = new Comment(this.commentDAO.getNewId(), customer, facility, dto.getCommentText(), dto.getRating(), CommentStatus.Pending);
		
		return this.commentDAO.newComment(newComment);
	}
	
	private boolean checkIfUserVisitFacility(User customer, SportsBuilding facility) {
		for (SportsBuilding building : customer.getVisitedSportsBuildings()) {
			if(building.getName().equals(facility.getName())) {
				return true;
			}
		}
		return false;
	}
	
	private Collection<Comment> getAllAcceptedComments() {
		Collection<Comment> acceptedComments = new ArrayList<Comment>();
		for (Comment comment : this.commentDAO.getAllComments()) {
			if(comment.getStatus() == CommentStatus.Accepted)
				acceptedComments.add(comment);
		}
		
		return acceptedComments;
	}
	
	public Collection<Comment> getAllAcceptedComments(String facilityName) {
		Collection<Comment> acceptedFacilityComments = new ArrayList<Comment>();
		for (Comment comment : getAllAcceptedComments()) {
			if(comment.getSportsBuilding().getName().equals(facilityName)) {
				acceptedFacilityComments.add(comment);
			}
		}
		return acceptedFacilityComments;
	}
	
	public ArrayList<Training> searchBySportsBuilding(String username, String buildingName) {
		return this.trainingHistoryDAO.searchBySportsBuilding(username, buildingName);
	}
	
	public ArrayList<Training> filterBySBuildingType(String username, BuildingType bType) {
		return this.trainingHistoryDAO.filterBySBuildingType(username, bType);
	}
	
	public ArrayList<TrainingHistory> searchByCheckInDate(String username, DateTime firstDate, DateTime secondDate) {
		return this.trainingHistoryDAO.searchByCheckInDate(username, firstDate, secondDate);
	}
	
	public ArrayList<Training> searchByPrice(String username, double lowerPrice, double higherPrice) {
		return this.trainingHistoryDAO.searchByPrice(username, lowerPrice, higherPrice);
	}
	
	public ArrayList<Training> filterByTrainingType(String usernmae, TrainingType trainingType) {
		return this.trainingHistoryDAO.filterByTrainingType(usernmae, trainingType);
	}
	
	public ArrayList<TrainingHistory> sortAsc(SortDTO dto) {
		
		ArrayList<TrainingHistory> sortedTrainings = new ArrayList<TrainingHistory>(GetAllCustomerTrainings(dto.getUsername()));
		
		if(dto.getSortBy().equals("Facility")) {
			Collections.sort(sortedTrainings, new AscFacilityNameComparator());
			return sortedTrainings;
		} else if(dto.getSortBy().equals("Date")) {
			Collections.sort(sortedTrainings, new AscDateComparator());
			return sortedTrainings;
		}
		
		return null;
	}
	
	public ArrayList<TrainingHistory> sortDesc(SortDTO dto) {
		
		ArrayList<TrainingHistory> sortedTrainings = new ArrayList<TrainingHistory>(GetAllCustomerTrainings(dto.getUsername()));
		
		if(dto.getSortBy().equals("Facility")) {
			Collections.sort(sortedTrainings, new DescFacilityNameComparator());
			return sortedTrainings;
		} else if(dto.getSortBy().equals("Date")) {
			Collections.sort(sortedTrainings, new DescDateComparator());
			return sortedTrainings;
		}
		
		return null;
	}
	
	public ArrayList<TrainingHistory> getFilteredWorkoutsByTrainingType(String username, TrainingType workoutType) {
		
		ArrayList<TrainingHistory> filteredWorkouts = new ArrayList<TrainingHistory>();
		
		for (TrainingHistory trainingHistory : GetAllCustomerTrainings(username)) {
			if(trainingHistory.getTraining().getTrainingType() == workoutType) {
				filteredWorkouts.add(trainingHistory);
			}
		}
		
		return filteredWorkouts;
	}
	
	public ArrayList<TrainingHistory> getFilteredWorkoutsByBuildingType(String username, BuildingType type) {
		
		ArrayList<TrainingHistory> filteredWorkouts = new ArrayList<TrainingHistory>();
		
		for (TrainingHistory trainingHistory : GetAllCustomerTrainings(username)) {
			if(trainingHistory.getTraining().getSportsBuilding().getType() == type) {
				filteredWorkouts.add(trainingHistory);
			}
		}
		
		return filteredWorkouts;
	}
}
