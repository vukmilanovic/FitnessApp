package services;

import java.util.ArrayList;
import java.util.Collection;

import DAO.CommentDAO;
import DAO.TrainingHistoryDAO;
import DAO.UserDAO;
import beans.Comment;
import beans.DateTime;
import beans.Training;
import beans.TrainingHistory;
import beans.User;
import enums.CommentStatus;
import enums.TrainingType;

public class ManagerService {

	private TrainingHistoryDAO trainingHistoryDAO;
	private UserDAO userDAO;
	private CommentDAO commentDAO;
	
	public ManagerService() {
		this.trainingHistoryDAO = TrainingHistoryDAO.getInstance();
		this.userDAO = UserDAO.getInstance();
		this.commentDAO = CommentDAO.getInstance();
	}
	
	public Collection<TrainingHistory> GetAllManagerTrainings(String username) {
		User manager = this.userDAO.getUser(username); 
		Collection<TrainingHistory> managerTrainings = new ArrayList<TrainingHistory>();
		for (TrainingHistory trainingHistory : this.trainingHistoryDAO.getTrainingHistories()){
			if(trainingHistory.getTraining().getSportsBuilding().getName().equals(manager.getSportsBuilding().getName())) {
				managerTrainings.add(trainingHistory);
			}
		}
		return managerTrainings;
	}
	
	public Collection<Comment> getAllCheckedComments() {
		Collection<Comment> checkedComments = new ArrayList<Comment>();
		for(Comment comment : this.commentDAO.getAllComments()) {
			if(comment.getStatus() != CommentStatus.Pending) {
				checkedComments.add(comment);
			}
		}
		return checkedComments;
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
}
