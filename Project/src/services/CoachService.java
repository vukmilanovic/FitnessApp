package services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import DAO.TrainingHistoryDAO;
import DAO.UserDAO;
import DTO.SortDTO;
import beans.DateTime;
import beans.Training;
import beans.TrainingHistory;
import enums.BuildingType;
import enums.TrainingType;
import sorting.AscDateComparator;
import sorting.AscFacilityNameComparator;
import sorting.DescDateComparator;
import sorting.DescFacilityNameComparator;

public class CoachService {

	private UserDAO userDAO;
	private TrainingHistoryDAO trainingHistoryDAO;
	
	public CoachService() {
		this.userDAO = UserDAO.getInstance();
		this.trainingHistoryDAO = TrainingHistoryDAO.getInstance();
	}
	
	private TrainingHistory GetCoachesTraningHistory(String username, String workoutName) {
		for (TrainingHistory trainingH : GetAllCoachPersonalTrainingHistories(username)) {
			if(trainingH.getTraining().getName().equals(workoutName)) {
				return trainingH;
			}
		}
		return null;
	}
	
	private Collection<TrainingHistory> GetAllCoachPersonalTrainingHistories(String username) {
		Collection<TrainingHistory> coachTrainingHistories = new ArrayList<TrainingHistory>();
		for (TrainingHistory trainingH : GetAllPersonalTrainings()) {
			if(trainingH.getCoach().getUsername().equals(username)) {
				coachTrainingHistories.add(trainingH);
			}
		}
		return coachTrainingHistories;
	}
	
	private Collection<TrainingHistory> GetAllPersonalTrainings() {
		Collection<TrainingHistory> personalTrainings = new ArrayList<TrainingHistory>();
		for (TrainingHistory trainingHistory : this.trainingHistoryDAO.getTrainingHistories()) {
			if(trainingHistory.getTraining().getTrainingType() == TrainingType.Personal) {
				personalTrainings.add(trainingHistory);
			}
		}
		return personalTrainings;
	}
	
	private Collection<TrainingHistory> GetAllGroupTrainings() {
		Collection<TrainingHistory> groupTrainings = new ArrayList<TrainingHistory>();
		for (TrainingHistory trainingHistory : this.trainingHistoryDAO.getTrainingHistories()) {
			if(trainingHistory.getTraining().getTrainingType() == TrainingType.Group) {
				groupTrainings.add(trainingHistory);
			}
		}
		return groupTrainings;
	}
	
	public Collection<TrainingHistory> GetCoachPersonalTrainings(String username) {
		Collection<TrainingHistory> coachPersonalTrainings = new ArrayList<TrainingHistory>();
		for (TrainingHistory trainingH : GetAllPersonalTrainings()) {
			if(trainingH.getCoach().getUsername().equals(username)) {
				coachPersonalTrainings.add(trainingH);
			}
		}
		return coachPersonalTrainings;
	}
	
	public Collection<TrainingHistory> GetCoachGroupTrainings(String username) {
		Collection<TrainingHistory> coachGroupTrainings = new ArrayList<TrainingHistory>();
		for (TrainingHistory trainingH : GetAllGroupTrainings()) {
			if(trainingH.getCoach().getUsername().equals(username)) {
				coachGroupTrainings.add(trainingH);
			}
		}
		return coachGroupTrainings;
	}
	
	public TrainingHistory CancelPersonalTraining(String username, String trainingName) {
		
		LocalDateTime localdt = LocalDateTime.now().plusDays(2);
		DateTime dt = new DateTime(localdt.getDayOfMonth(), localdt.getMonthValue(), localdt.getYear(), localdt.getHour(), localdt.getMinute());		
		TrainingHistory tHistory = GetCoachesTraningHistory(username, trainingName);
		
		if(dt.isBefore(tHistory.getDateAndTimeOfCheckIn())) {
			this.trainingHistoryDAO.deleteTrainingHistory(tHistory.getId());
			return tHistory;
		}
		
		return null;
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
	
	public Collection<TrainingHistory> searchByCheckInDateForCoach(String username, DateTime firstDate, DateTime secondDate, boolean isPersonal) {
		Collection<TrainingHistory> trainings = new ArrayList<TrainingHistory>();
		if(isPersonal) {
			for (TrainingHistory tHistory : this.trainingHistoryDAO.searchByCheckInDate(username, firstDate, secondDate)) {
				if(tHistory.getTraining().getTrainingType() == TrainingType.Personal) {
					trainings.add(tHistory);
				}
			}
		} else {
			for (TrainingHistory tHistory : this.trainingHistoryDAO.searchByCheckInDate(username, firstDate, secondDate)) {
				if(tHistory.getTraining().getTrainingType() == TrainingType.Group) {
					trainings.add(tHistory);
				}
			}
		}
		return trainings;
	}
	
	public ArrayList<Training> searchByPrice(String username, double lowerPrice, double higherPrice) {
		return this.trainingHistoryDAO.searchByPrice(username, lowerPrice, higherPrice);
	}
	
	public ArrayList<Training> filterByTrainingType(String usernmae, TrainingType trainingType) {
		return this.trainingHistoryDAO.filterByTrainingType(usernmae, trainingType);
	}
	
	public ArrayList<TrainingHistory> sortAsc(SortDTO dto) {
		
		ArrayList<TrainingHistory> sortedTrainings = null;
		
		if(dto.getType() == TrainingType.Personal) {
			sortedTrainings = new ArrayList<TrainingHistory>(GetCoachPersonalTrainings(dto.getUsername()));
		} else if(dto.getType() == TrainingType.Group) {
			sortedTrainings = new ArrayList<TrainingHistory>(GetCoachGroupTrainings(dto.getUsername()));
		}
		
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
		
		ArrayList<TrainingHistory> sortedTrainings = null;
		
		if(dto.getType() == TrainingType.Personal) {
			sortedTrainings = new ArrayList<TrainingHistory>(GetCoachPersonalTrainings(dto.getUsername()));
		} else if(dto.getType() == TrainingType.Group) {
			sortedTrainings = new ArrayList<TrainingHistory>(GetCoachGroupTrainings(dto.getUsername()));
		}
		
		if(dto.getSortBy().equals("Facility")) {
			Collections.sort(sortedTrainings, new DescFacilityNameComparator());
			return sortedTrainings;
		} else if(dto.getSortBy().equals("Date")) {
			Collections.sort(sortedTrainings, new DescDateComparator());
			return sortedTrainings;
		}
		
		return null;
	}
	
	public ArrayList<TrainingHistory> getFilteredWorkouts(BuildingType type, String coachUsername, TrainingType workoutType) {
		ArrayList<TrainingHistory> workouts = null;
		ArrayList<TrainingHistory> filteredWorkouts = new ArrayList<TrainingHistory>();
		if(workoutType == TrainingType.Group) {
			workouts = new ArrayList<TrainingHistory>(GetCoachGroupTrainings(coachUsername));
		} else if(workoutType == TrainingType.Personal) {
			workouts = new ArrayList<TrainingHistory>(GetCoachPersonalTrainings(coachUsername));
		}
		
		for (TrainingHistory trainingHistory : workouts) {
			if(trainingHistory.getTraining().getSportsBuilding().getType() == type) {
				filteredWorkouts.add(trainingHistory);
			}
		}
		
		return filteredWorkouts;
	}
}
