package rest;

import static spark.Spark.port;
import static spark.Spark.staticFiles;
import java.io.File;

import beans.User;
import controller.UserController;
import controller.AdminController;
import controller.CoachController;
import controller.CustomerController;
import controller.ManagerController;
import controller.PaymentController;
import controller.SportsBuildingController;
import controller.TrainingController;
import controller.TrainingHistoryController;

public class SparkAppMain {
	
	public static void main(String[] args) throws Exception {
		port(8080);

		staticFiles.externalLocation(new File("./static").getCanonicalPath());
		
		UserController.getUsers();
		UserController.getUser();
		UserController.addUser();
		UserController.editUser();
		UserController.deleteUsers();
		UserController.checkIfUserExists();
		UserController.checkIfUserExistsForRegister();
		UserController.getLoggedUser();
		UserController.getUnemployedManagers();
		UserController.addNewManager();
		UserController.getWholeUser();
		UserController.getTrainers();
		UserController.getFacilityUsers();
		
		CustomerController.GetAllCustomerTrainings();
		CustomerController.GetCustomers();
		CustomerController.search();
		CustomerController.filter();
		CustomerController.CommentSportsBuilding();
		CustomerController.getAllAcceptedComments();
		CustomerController.GetCustomersByType();
		CustomerController.sortAsc();
		CustomerController.sortDesc();
		CustomerController.getFilteredWokoutsByTrainingType();
		CustomerController.getFilteredWorkoutsByBuildingType();
		
		ManagerController.GetAllManagerTrainings();
		ManagerController.search();
		ManagerController.filter();
		ManagerController.getAllCheckedComments();
		
		CoachController.GetCoachGroupTrainings();
		CoachController.GetCoachPersonalTrainings();
		CoachController.CancelPersonalTraining();
		CoachController.search();
		CoachController.filter();
		CoachController.sortAsc();
		CoachController.sortDesc();
		CoachController.getFilteredWorkouts();
		
		AdminController.search();
		AdminController.filter();
		AdminController.getAllUsers();
		AdminController.createManager();
		AdminController.createCoach();
		AdminController.acceptComment();
		AdminController.rejectComment();
		AdminController.getAllPendingComments();
		AdminController.getAllCheckedComments();
		AdminController.filterByCustomerType();
		AdminController.checkIfStuffExists();
		AdminController.createPromoCode();
		AdminController.sortAsc();
		AdminController.sortDesc();
		
		TrainingController.addTraining();
		TrainingController.editTraining();
		TrainingController.getTrainings();
		
		TrainingHistoryController.addTrainingHistory();
		TrainingHistoryController.editTrainingHistory();
		TrainingHistoryController.deleteTrainingHistory();
		TrainingHistoryController.getTrainingHistory();
		TrainingHistoryController.getTrainingHistories();
		TrainingHistoryController.getAllCoaches();
		TrainingHistoryController.getAllSportFacilities();
		
		SportsBuildingController.getAll();
		SportsBuildingController.Query();
		SportsBuildingController.getSportsBuilding();
		SportsBuildingController.addBuilding();
		SportsBuildingController.sortByNameAscending();
		SportsBuildingController.sortByNameDescending();
		SportsBuildingController.sortByLocationAscending();
		SportsBuildingController.sortByLocationDescending();
		SportsBuildingController.sortByRatingAscending();
		SportsBuildingController.sortByRatingDescending();
		
		PaymentController.CreateMembershipFee();
		PaymentController.checkIfMembershipFeeExpired();
	}
}
