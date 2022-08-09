package controller;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;

import java.util.ArrayList;
import java.util.Collection;

import com.google.gson.Gson;

import DTO.CancelTrainingDTO;
import DTO.SearchCoachDateDTO;
import DTO.SortDTO;
import beans.Training;
import beans.TrainingHistory;
import enums.BuildingType;
import enums.TrainingType;
import services.CoachService;

public class CoachController {
	
	private static Gson g = new Gson();
	private static CoachService coachService = new CoachService();
	
	public static void GetCoachPersonalTrainings() {
		get("rest/coaches/personal/:username", (req, res) -> {
			res.type("application/json");
			String username = req.params("username");
			return g.toJson(coachService.GetCoachPersonalTrainings(username));
		});
	}
	
	public static void GetCoachGroupTrainings() {
		get("rest/coaches/group/:username", (req, res) -> {
			res.type("application/json");
			String username = req.params("username");
			return g.toJson(coachService.GetCoachGroupTrainings(username));
		});
	}
	
	public static void CancelPersonalTraining() { 
		delete("rest/coaches/cancelPersonal", (req, res) -> {
			res.type("application/json");
			CancelTrainingDTO cancelTraining = g.fromJson(req.body(), CancelTrainingDTO.class);
			String username = cancelTraining.getUsername();
			String trainingName = cancelTraining.getTrainingName();
			return g.toJson(coachService.CancelPersonalTraining(username, trainingName));
		});
	}
	
	//NIJE GOTOVO!
	public static void search() {
		post("rest/coaches/search", (req, res) -> {
			res.type("application/json");
			//Double lowerPrice = Double.parseDouble(req.queryParams("lowerPrice"));
			//Double higherPrice = Double.parseDouble(req.queryParams("higherPrice"));
			SearchCoachDateDTO dto = g.fromJson(req.body(), SearchCoachDateDTO.class);
			
			//ArrayList<Training> trainingsByBuildingName = coachService.searchBySportsBuilding(username, buildingName);
			Collection<TrainingHistory> trainingsByCheckInDate = coachService.searchByCheckInDateForCoach(dto.getCoachUsername(), dto.getStartDate(), dto.getEndDate(), dto.isPersonal());
			//ArrayList<Training> trainingsByPrice = coachService.searchByPrice(username, lowerPrice, higherPrice);
			
			ArrayList<TrainingHistory> searchedTrainings = new ArrayList<TrainingHistory>();
			//searchedTrainings.addAll(trainingsByBuildingName);
			searchedTrainings.addAll(trainingsByCheckInDate);
			//searchedTrainings.addAll(trainingsByPrice);
			

			return g.toJson(searchedTrainings);
		});
	}
	
	public static void filter() {
		get("rest/coaches/trainings/filter", (req, res) -> {
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
			
			ArrayList<Training> filteredTrainingsBySBuildingType = coachService.filterBySBuildingType(username, bType);
			ArrayList<Training> filteredTrainingsByTrainingType = coachService.filterByTrainingType(username, trainingType);
			
			ArrayList<Training> filteredTrainings = new ArrayList<Training>();
			filteredTrainings.addAll(filteredTrainingsBySBuildingType);
			filteredTrainings.addAll(filteredTrainingsByTrainingType);
			
			res.type("application/json");
			return g.toJson(filteredTrainings);
		});
	}
	
	public static void sortAsc() {
		post("rest/coaches/asc", (req, res) -> {
			res.type("application/json");
			SortDTO dto = g.fromJson(req.body(), SortDTO.class);
			return g.toJson(coachService.sortAsc(dto));
		});
	}
	
	public static void sortDesc() {
		post("rest/coaches/desc", (req, res) -> {
			res.type("application/json");
			SortDTO dto = g.fromJson(req.body(), SortDTO.class);
			return g.toJson(coachService.sortDesc(dto));
		});
	}
	
	public static void getFilteredWorkouts() {
		get("rest/coaches/filterBuilding", (req, res) -> {
			res.type("application/json");
			String username = req.queryParams("username");
			String workoutTypeStr = req.queryParams("workoutType");
			String buildingTypeStr = req.queryParams("type");
			
			BuildingType btype = null;
			TrainingType ttype = null;
			
			if(workoutTypeStr.equals("Personal")) {
				ttype = TrainingType.Personal;
			} else if(workoutTypeStr.equals("Group")) {
				ttype = TrainingType.Group;
			}
			
			if(buildingTypeStr.equals("GYM")) {
				btype = BuildingType.GYM;
			} else if(buildingTypeStr.equals("POOL")) {
				btype = BuildingType.POOL;
			} else if(buildingTypeStr.equals("SPORTS_CENTER")) {
				btype = BuildingType.SPORTS_CENTER;
			} else if(buildingTypeStr.equals("DANCE_STUDIO")) {
				btype = BuildingType.DANCE_STUDIO;
			}
			
			return g.toJson(coachService.getFilteredWorkouts(btype, username, ttype));
		});
	}
}
