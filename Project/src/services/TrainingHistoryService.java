package services;

import java.util.ArrayList;
import java.util.Collection;

import DAO.SportsBuildingDAO;
import DAO.TrainingHistoryDAO;
import DAO.UserDAO;
import DTO.CoachDTO;
import DTO.SportFacilityDTO;
import DTO.TrainingDTO;
import beans.SportsBuilding;
import beans.Training;
import beans.TrainingHistory;
import beans.User;
import enums.Role;

public class TrainingHistoryService {

	private TrainingHistoryDAO trainingHistoryDAO;
	private UserDAO userDAO;
	private SportsBuildingDAO sportsBuildingDAO;
	
	public TrainingHistoryService() {
		this.trainingHistoryDAO = TrainingHistoryDAO.getInstance();
		this.userDAO = UserDAO.getInstance();
		this.sportsBuildingDAO = SportsBuildingDAO.getInstance();
	}
	
	//treba ovde dodati i logiku za placanje clanarine ako je istekla, a kupac pokusa da prijavi trening
	//umanjiti broj mogucih dolazaka za 1
	public boolean addTrainingHistory(TrainingDTO newTraining) {
		//zakazivanje novog treninga/prijava na trening
		User customer = this.userDAO.getUser(newTraining.getCustomerUsername());
		SportsBuilding sBuilding = this.sportsBuildingDAO.getSportsBuilding(newTraining.getSportsBuildingName());
		
		if(customer.getMembershipFee().getNumberOfRemainingAppointements() != 0) {
			customer.getMembershipFee().setNumberOfRemainingAppointements(customer.getMembershipFee().getNumberOfRemainingAppointements() - 1);
		} else {
			return false;
		}
		
		User coach = null;
		if(!newTraining.getCoachUsername().equals("")) {
			coach = this.userDAO.getUser(newTraining.getCoachUsername());
		} 
		
		Training training = new Training(newTraining.getWorkoutName(), newTraining.getTrainingType(), sBuilding, 60, coach, newTraining.getDescription(), "", 0);
		TrainingHistory newTrainingHistory = new TrainingHistory(this.trainingHistoryDAO.getNewId(), newTraining.getDateAndTimeOfCheckIn(), training, customer, coach);
		
		checkIfCustomerVisitedFacility(customer, sBuilding);
		return this.trainingHistoryDAO.addTrainingHistory(newTrainingHistory);
	}
	
	public void checkIfCustomerVisitedFacility(User customer, SportsBuilding facility) {
		
		if(!customer.getVisitedSportsBuildings().isEmpty()) {
			for (SportsBuilding building : customer.getVisitedSportsBuildings()) {
				if(building.getName().equals(facility.getName())) {
					return;
				}
			}
		}
		
		//PRVI PUT GA JE POSETIO (TREBACE OKO KOMENTARA)
		this.userDAO.setCustomersVisitedBuilding(customer, facility);
	}
	
	public void deleteTrainingHistory(int id) {
		this.trainingHistoryDAO.deleteTrainingHistory(id);
	}
	
	public void editTrainingHistory(int id, TrainingHistory trainingHistory) {
		this.trainingHistoryDAO.editTrainingHistory(id, trainingHistory);
	}
	
	public Collection<TrainingHistory> getTrainingHistories() {
		return this.trainingHistoryDAO.getTrainingHistories();
	}	
	
	public TrainingHistory getTrainingHistory(int id) {
		return this.trainingHistoryDAO.getTrainingHistory(id);
	}
	
	public Collection<CoachDTO> getAllCoaches() {
		Collection<CoachDTO> coaches = new ArrayList<CoachDTO>();
		for (User user : this.userDAO.getUsers()) {
			if(user.getRole() == Role.Coach) {
				CoachDTO dto = new CoachDTO(user.getUsername(), user.getName(), user.getLastname());
				coaches.add(dto);
			}
		}
		return coaches;
	}
	
	public Collection<SportFacilityDTO> getAllSportFacilities() {
		Collection<SportFacilityDTO> sportFacilities = new ArrayList<SportFacilityDTO>();
		for (SportsBuilding sBuilding : this.sportsBuildingDAO.getAll()) {
			SportFacilityDTO sfDTO = new SportFacilityDTO(sBuilding.getName(), sBuilding.getType(), sBuilding.getLocation(), sBuilding.getAverageRating(), sBuilding.getStartWorkingTime(), sBuilding.getEndWorkingTime());
			sportFacilities.add(sfDTO);
		}
		return sportFacilities;
	}
	
}
