package controller;

import static spark.Spark.get;
import static spark.Spark.post;

import java.util.ArrayList;
import java.util.Collection;

import com.google.gson.Gson;

import DTO.CommentDTO;
import DTO.SearchDateDTO;
import DTO.SortDTO;
import beans.Comment;
import beans.DateTime;
import beans.Training;
import beans.TrainingHistory;
import enums.BuildingType;
import enums.CustomerTypeName;
import enums.TrainingType;
import services.CustomerService;

public class CustomerController {

	private static Gson g = new Gson();
	private static CustomerService customerService = new CustomerService();
	
	public static void GetCustomers() { 
		get("rest/customers/", (req, res) -> {
			res.type("application/json");
			return g.toJson(customerService.GetAllCustomers());
		});
	}
	
	public static void GetCustomersByType() {
		get("rest/customers/:type", (req, res) -> {
			res.type("application/json");
			String typeStr = req.params("type");
			CustomerTypeName type = null;
			
			if(typeStr == "Silver") {
				type = CustomerTypeName.Silver;
			} else if (typeStr == "Bronze") {
				type = CustomerTypeName.Bronze;
			} else if (typeStr == "Gold") {
				type = CustomerTypeName.Gold;
			}
			
			return g.toJson(customerService.GetCustomersByType(type));
		});
	}
	
	public static void GetAllCustomerTrainings() { 
		get("rest/customers/trainings/:username", (req, res) -> {
			res.type("application/json");
			String username = req.params("username");
			return g.toJson(customerService.GetAllCustomerTrainings(username));
		});
	}
	
	public static void CommentSportsBuilding() {
		post("rest/customers/comment", (req, res) -> {
			res.type("application/json");
			CommentDTO dto = g.fromJson(req.body(), CommentDTO.class);
			return customerService.CommentSportsBuilding(dto);
		});
	}
	
	public static void getAllAcceptedComments() {
		get("rest/customers/acceptedComments/:name", (req, res) -> {
			res.type("application/json");
			String facilityName = req.params("name");
			return g.toJson(customerService.getAllAcceptedComments(facilityName));
		});
	}
	
	//NIJE GOTOVO!
	public static void search() {
		post("rest/customers/searchDate", (req, res) -> {
			res.type("application/json");
//			Double lowerPrice = Double.parseDouble(req.queryParams("lowerPrice"));
//			Double higherPrice = Double.parseDouble(req.queryParams("higherPrice"));
			SearchDateDTO dto = g.fromJson(req.body(), SearchDateDTO.class);
			
			//ArrayList<Training> trainingsByBuildingName = customerService.searchBySportsBuilding(username, buildingName);
			ArrayList<TrainingHistory> trainingsByCheckInDate = customerService.searchByCheckInDate(dto.getCustomerUsername(), dto.getStartDate(), dto.getEndDate());
			//ArrayList<Training> trainingsByPrice = customerService.searchByPrice(username, lowerPrice, higherPrice);
			
			Collection<TrainingHistory> searchedTrainings = new ArrayList<TrainingHistory>();
			
			//searchedTrainings.addAll(trainingsByBuildingName);
			searchedTrainings.addAll(trainingsByCheckInDate);
			//searchedTrainings.addAll(trainingsByPrice);
			
			return g.toJson(searchedTrainings);
		});
	}
	
	public static void filter() {
		get("rest/customers/trainings/filter", (req, res) -> {
			String username = req.queryParams("username");
			String bTypeStr = req.queryParams("bType");
			String trainingTypeStr = req.queryParams("trainingType");
			BuildingType bType = null;
			TrainingType trainingType = null;
			
			if(bTypeStr.equals("SPORTS_CENTER")) {
				bType = BuildingType.SPORTS_CENTER;
			} else if(bTypeStr.equals("DANCE_STUDIO")) {
				bType = BuildingType.DANCE_STUDIO;
			} else if(bTypeStr.equals("POOL")) {
				bType = BuildingType.POOL;
			} else if(bTypeStr.equals("GYM")) {
				bType = BuildingType.GYM;
			}
			
			if(trainingTypeStr.equals("Personal")) {
				trainingType = TrainingType.Personal;
			} else if(trainingTypeStr.equals("Group")) {
				trainingType = TrainingType.Group;
			} else if(trainingTypeStr.equals("Gym")) {
				trainingType = TrainingType.Gym;
			}
			
			ArrayList<Training> filteredTrainingsBySBuildingType = customerService.filterBySBuildingType(username, bType);
			ArrayList<Training> filteredTrainingsByTrainingType = customerService.filterByTrainingType(username, trainingType);
			
			ArrayList<Training> filteredTrainings = new ArrayList<Training>();
			filteredTrainings.addAll(filteredTrainingsBySBuildingType);
			filteredTrainings.addAll(filteredTrainingsByTrainingType);
			
			res.type("application/json");
			return g.toJson(filteredTrainings);
		});
	}
	
	public static void getFilteredWokoutsByTrainingType() { 
		get("rest/customers/workouts/filtert" , (req, res) -> {
			res.type("applications/json");
			String username = req.queryParams("username");
			String workoutTypeStr = req.queryParams("workoutType");
			
			TrainingType ttype = null;
			
			if(workoutTypeStr.equals("Personal")) {
				ttype = TrainingType.Personal;
			} else if(workoutTypeStr.equals("Group")) {
				ttype = TrainingType.Group;
			}
			
			return g.toJson(customerService.getFilteredWorkoutsByTrainingType(username, ttype));
		});
	}
	
	public static void getFilteredWorkoutsByBuildingType() {
		get("rest/customers/workouts/filterb" , (req, res) -> {
			res.type("applications/json");
			String username = req.queryParams("username");
			String buildingTypeStr = req.queryParams("type");
			BuildingType btype = null;
			
			if(buildingTypeStr.equals("GYM")) {
				btype = BuildingType.GYM;
			} else if(buildingTypeStr.equals("POOL")) {
				btype = BuildingType.POOL;
			} else if(buildingTypeStr.equals("SPORTS_CENTER")) {
				btype = BuildingType.SPORTS_CENTER;
			} else if(buildingTypeStr.equals("DANCE_STUDIO")) {
				btype = BuildingType.DANCE_STUDIO;
			}
			
			return g.toJson(customerService.getFilteredWorkoutsByBuildingType(username, btype));
		});
	}
	
	public static void sortAsc() {
		post("rest/customers/asc", (req, res) -> {
			res.type("application/json");
			SortDTO dto = g.fromJson(req.body(), SortDTO.class);
			return g.toJson(customerService.sortAsc(dto));
		});
	}
	
	public static void sortDesc() {
		post("rest/customers/desc", (req, res) -> {
			res.type("application/json");
			SortDTO dto = g.fromJson(req.body(), SortDTO.class);
			return g.toJson(customerService.sortDesc(dto));
		});
	}
}
