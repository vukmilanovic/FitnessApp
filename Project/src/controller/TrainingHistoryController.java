package controller;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import java.lang.reflect.Array;
import java.util.ArrayList;

import com.google.gson.Gson;

import DTO.TrainingDTO;
import beans.DateTime;
import beans.Training;
import beans.TrainingHistory;
import enums.BuildingType;
import enums.TrainingType;
import services.TrainingHistoryService;

public class TrainingHistoryController {

	private static Gson g = new Gson();
	private static TrainingHistoryService trainingHistoryService = new TrainingHistoryService();
	
	public static void getTrainingHistories() { 
		get("rest/trainings/", (req, res) -> {
			res.type("application/json");
			return g.toJson(trainingHistoryService.getTrainingHistories());
		});
	}
	
	public static void getTrainingHistory() { 
		get("rest/trainings/oneTraining/:id", (req, res) -> {
			res.type("application/json");
			int id = Integer.parseInt(req.params("id"));
			return g.toJson(trainingHistoryService.getTrainingHistory(id));
		});
	}
	
	public static void addTrainingHistory() { 
		post("rest/trainings/add", (req, res) -> {
			res.type("application/json");
			System.out.println(req.body());
			TrainingDTO dto = g.fromJson(req.body(), TrainingDTO.class);
			return g.toJson(trainingHistoryService.addTrainingHistory(dto));
		});
	}
	
	public static void editTrainingHistory() { 
		put("rest/trainings/edit/:id", (req, res) -> {
			res.type("application/json");
			int id = Integer.parseInt(req.params("id"));
			TrainingHistory newTrainingH = g.fromJson(req.body(), TrainingHistory.class);
			trainingHistoryService.editTrainingHistory(id, newTrainingH);
			return "SUCCESS";
		});
	}
	
	public static void deleteTrainingHistory() { 
		delete("rest/trainings/delete/:id", (req, res) -> {
			res.type("application/json");
			int id = Integer.parseInt(req.params("id"));
			trainingHistoryService.deleteTrainingHistory(id);
			return "SUCCESS";
		});
	}

	public static void getAllCoaches() {
		get("rest/trainings/coaches", (req, res) -> {
			res.type("application/json");
			return g.toJson(trainingHistoryService.getAllCoaches());
		});
	}
	
	public static void getAllSportFacilities() {
		get("rest/trainings/facilities", (req, res) -> {
			res.type("application/json");
			return g.toJson(trainingHistoryService.getAllSportFacilities());
		});
	}
	
}
