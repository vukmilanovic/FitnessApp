package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import sorting.*;
import DAO.CommentDAO;
import DAO.PromoCodeDAO;
import DAO.UserDAO;
import DTO.PromoCodeDTO;
import DTO.SortDTO;
import beans.Comment;
import beans.CustomerType;
import beans.MembershipFee;
import beans.PromoCode;
import beans.SportsBuilding;
import beans.TrainingHistory;
import beans.User;
import enums.CommentStatus;
import enums.CustomerTypeName;
import enums.Role;

public class AdminService {

	private UserDAO userDAO;
	private CommentDAO commentDAO;
	private PromoCodeDAO promoCodeDAO;
	
	public AdminService() {
		this.userDAO = UserDAO.getInstance();
		this.commentDAO = CommentDAO.getInstance();
		this.promoCodeDAO = PromoCodeDAO.getInstance();
	}
	
	public boolean createManager(User manager) {
		if(manager.getRole() != Role.Manager)
			return false;
		manager.setCustomerType(new CustomerType());
		manager.setMembershipFee(new MembershipFee());
		manager.setVisitedSportsBuildings(new ArrayList<SportsBuilding>());
		manager.setNumberOfCollectedPoints(0);
		manager.setTrainingHistory(new ArrayList<TrainingHistory>());
		//ovo za sportsBuilding ce se menjati
		manager.setSportsBuilding(new SportsBuilding());
		
		return this.userDAO.addUser(manager);
	}
	
	public boolean createCoach(User coach) {
		if(coach.getRole() != Role.Coach)
			return false;
		
		coach.setCustomerType(new CustomerType());
		coach.setMembershipFee(new MembershipFee());
		coach.setVisitedSportsBuildings(new ArrayList<SportsBuilding>());
		coach.setNumberOfCollectedPoints(0);
		coach.setTrainingHistory(new ArrayList<TrainingHistory>());
		coach.setSportsBuilding(new SportsBuilding());
		
		return this.userDAO.addUser(coach);
	}
	
	public boolean acceptComment(int commentID) {
		return this.commentDAO.acceptComment(commentID);
	}
	
	public boolean rejectComment(int commentID) {
		return this.commentDAO.rejectComment(commentID);
	}
	
	public Collection<Comment> getAllPendingComments() {
		Collection<Comment> pendingComments = new ArrayList<Comment>();
		for(Comment comment : this.commentDAO.getAllComments()) {
			if(comment.getStatus() == CommentStatus.Pending)
				pendingComments.add(comment);
		}
		return pendingComments;
	}
	
	private Collection<Comment> getAllCheckedComments() {
		Collection<Comment> checkedComments = new ArrayList<Comment>();
		for(Comment comment : this.commentDAO.getAllComments()) {
			if(comment.getStatus() != CommentStatus.Pending) {
				checkedComments.add(comment);
			}
		}
		return checkedComments;
	}
	
	public Collection<Comment> getAllCheckedFacilityComments(String facilityName) {
		Collection<Comment> checkedFacilityComments = new ArrayList<Comment>();
		for (Comment comment : getAllCheckedComments()) {
			if(comment.getSportsBuilding().getName().equals(facilityName)) {
				checkedFacilityComments.add(comment);
			}
		}
		return checkedFacilityComments;
	}
	
	public ArrayList<User> searchByName(String name) {
		return this.userDAO.searchByName(name);
	}
	
	public ArrayList<User> searchByLastname(String lastname) {
		return this.userDAO.searchByLastname(lastname);
	}
	
	public ArrayList<User> searchByUsername(String username) {
		return this.userDAO.searchByUsername(username);
	}
	
	public ArrayList<User> filterByRole(Role role) {
		return this.userDAO.filterByRole(role);
	}
	
	public ArrayList<User> filterByCustomerType(CustomerTypeName customerType) {
		return this.userDAO.filterByCustomerType(customerType);
	}
	
	public Collection<User> getAllUsers() {
		return this.userDAO.getUsers();
	}
	
	public boolean createPromoCode(PromoCodeDTO code) {
		
		if(!checkIfCodeAlreadyExists(code.getCode())) {
			PromoCode promo = new PromoCode(this.promoCodeDAO.getNewId(), code.getCode(), code.getStartValidityDate(), code.getEndValidityDate(), code.getUsageNumber(), code.getDiscount());
			return this.promoCodeDAO.CreatePromoCode(promo);
		}
		
		return false;
	}
	
	private boolean checkIfCodeAlreadyExists(String code) {
		for (PromoCode promoCode : this.promoCodeDAO.getAllCodes()) {
			if(promoCode.getCode().equals(code)) {
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<User> sortAsc(SortDTO dto) {
		
		ArrayList<User> sortedUsers = new ArrayList<User>(this.userDAO.getUsers());
		
		if(dto.getSortBy().equals("Name")) {
			Collections.sort(sortedUsers, new LexicographicAscNameComparator());
			return sortedUsers;
		} else if(dto.getSortBy().equals("Lastname")) {
			Collections.sort(sortedUsers, new LexicographicAscLastnameComparator());
			return sortedUsers;
		} else if(dto.getSortBy().equals("Username")) {
			Collections.sort(sortedUsers, new LexicographicAscUsernameComparator());
			return sortedUsers;
		} else if(dto.getSortBy().equals("Points")) {
			Collections.sort(sortedUsers, new LexicographicAscPointsComparator());
			return sortedUsers;
		} 

		return null;
	}
	
	public ArrayList<User> sortDesc(SortDTO dto) {

		ArrayList<User> sortedUsers = new ArrayList<User>(this.userDAO.getUsers());
		
		if(dto.getSortBy().equals("Name")) {
			Collections.sort(sortedUsers, new LexicographicDescNameComparator());
			return sortedUsers;
		} else if(dto.getSortBy().equals("Lastname")) {
			Collections.sort(sortedUsers, new LexicographicDescLastnameComparator());
			return sortedUsers;
		} else if(dto.getSortBy().equals("Username")) {
			Collections.sort(sortedUsers, new LexicographicDescUsernameComparator());
			return sortedUsers;
		} else if(dto.getSortBy().equals("Points")) {
			Collections.sort(sortedUsers, new LexicographicDescPointsComparator());
			return sortedUsers;
		}
	
		return null;
	}
}
