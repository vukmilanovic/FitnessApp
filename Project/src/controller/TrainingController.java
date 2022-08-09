package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import DTO.NewTrainingDTO;
import beans.SportsBuilding;
import beans.Training;

import static spark.Spark.post;
import static spark.Spark.put;

import java.util.Collection;

import services.TrainingService;

public class TrainingController {
	private static Gson g = new Gson();
	private static TrainingService trainings = new TrainingService();
	
	public static void addTraining() {
		post("rest/buildings/addContent/add", (req, res) -> {
			res.type("application/json");
			
			NewTrainingDTO trainingDTO = g.fromJson(req.body(), NewTrainingDTO.class);
			
			Training training = new Training(
					trainingDTO.getName(),
					Training.typeFromString(trainingDTO.getTrainingType()),
					trainingDTO.getSportsBuilding(),
					Integer.parseInt(trainingDTO.getDuration()),
					trainingDTO.getCoach(),
					trainingDTO.getDescription(),
					trainingDTO.getPicturePath(),
					Double.parseDouble(trainingDTO.getPrice()));
			
			trainings.addTraining(training);
			return "SUCCESS";
		});
	}
	
	public static void editTraining() {
		put("rest/buildings/addContent/edit", (req, res) -> {
			res.type("application/json");
			
			NewTrainingDTO trainingDTO = g.fromJson(req.body(), NewTrainingDTO.class);
			
			Training newTraining = new Training(
					trainingDTO.getName(),
					Training.typeFromString(trainingDTO.getTrainingType()),
					trainingDTO.getSportsBuilding(),
					Integer.parseInt(trainingDTO.getDuration()),
					trainingDTO.getCoach(),
					trainingDTO.getDescription(),
					trainingDTO.getPicturePath(),
					Double.parseDouble(trainingDTO.getPrice()));
			 
			trainings.editTraining(newTraining);
			
			return "SUCCESS";
		});
	}
	
	public static void getTrainings() {
		post("rest/buildings/getTrainings", (req, res) ->{
			res.type("application/json");
			System.out.println(req.body());
			
			Collection<String> names = g.fromJson(req.body(), new TypeToken<Collection<String>>(){}.getType());
			
			return g.toJson(trainings.getTrainings(names));
		});
	}
}
