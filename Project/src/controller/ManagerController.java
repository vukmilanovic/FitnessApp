package controller;

import static spark.Spark.get;

import java.util.ArrayList;

import com.google.gson.Gson;

import beans.DateTime;
import beans.Training;
import beans.TrainingHistory;
import enums.TrainingType;
import services.ManagerService;

public class ManagerController {

	private static Gson g = new Gson();
	private static ManagerService managerService = new ManagerService();
	
	public static void GetAllManagerTrainings() {
		get("rest/managers/trainings/:username", (req, res) -> {
			res.type("application/json");
			String username = req.params("username");
			return g.toJson(managerService.GetAllManagerTrainings(username));
		});
	}
	
	public static void getAllCheckedComments() {
		get("rest/managers/checkedComments", (req, res) -> {
			res.type("application/json");
			return g.toJson(managerService.getAllCheckedComments());
		});
	}
	
	//NIJE GOTOVO!
	public static void search() {
		get("rest/managers/trainings/search", (req, res) -> {
			String username = req.queryParams("username");
			
			Double lowerPrice = Double.parseDouble(req.queryParams("lowerPrice"));
			Double higherPrice = Double.parseDouble(req.queryParams("higherPrice"));

			String firstDateStr = req.queryParams("firstDate");
			String secondDateStr = req.queryParams("secondDate");
			
			//parsiranje, splitovanje
			
			DateTime firstDate = new DateTime();
			DateTime secondDate = new DateTime();
			
			ArrayList<TrainingHistory> trainingsByCheckInDate = managerService.searchByCheckInDate(username, firstDate, secondDate);
			ArrayList<Training> trainingsByPrice = managerService.searchByPrice(username, lowerPrice, higherPrice);

			ArrayList<TrainingHistory> searchedTrainings = new ArrayList<TrainingHistory>();
			searchedTrainings.addAll(trainingsByCheckInDate);
			//searchedTrainings.addAll(trainingsByPrice);
			
			res.type("application/json");
			return g.toJson(searchedTrainings);
		});
	}
	
	public static void filter() {
		get("rest/managers/trainings/filter", (req, res) -> {
			String username = req.queryParams("username");
			String trainingTypeStr = req.queryParams("trainingType");
			TrainingType trainingType = null;
			
			if(trainingTypeStr.equals("Personal")) {
				trainingType = TrainingType.Personal;
			} else if(trainingTypeStr.equals("Group")) {
				trainingType = TrainingType.Group;
			} else if(trainingTypeStr.equals("Gym")) {
				trainingType = TrainingType.Gym;
			}
			
			ArrayList<Training> filteredTrainingsByTrainingType = managerService.filterByTrainingType(username, trainingType);
			
			res.type("application/json");
			return g.toJson(filteredTrainingsByTrainingType);
		});
	}
	
}
