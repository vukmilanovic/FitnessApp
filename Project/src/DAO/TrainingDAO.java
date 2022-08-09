package DAO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import beans.SportsBuilding;
import beans.Training;
import beans.User;
import utils.GlobalPaths;

public class TrainingDAO {

	private static TrainingDAO instance = null;

	public static TrainingDAO getInstance() {
		if (instance == null)
			instance = new TrainingDAO();

		return instance;
	}

	private Gson g = new Gson();
	private HashMap<String, Training> trainings = new HashMap<String, Training>();
	private SportsBuildingDAO buildingDAO = SportsBuildingDAO.getInstance();
	private UserDAO userDAO = UserDAO.getInstance();

	public TrainingDAO() {
		Load();
	}

	public Training addTraining(Training training) {
		if (trainings.containsKey(training.getName()))
			if (!trainings.get(training.getName()).isDeleted())
				return null;

		User coach = null;
		if(training.getCoach() != null) {
			coach = userDAO.getUser(training.getCoach().getUsername());
			coach.setSportsBuilding(training.getSportsBuilding());
			userDAO.editUser(coach.getUsername(), coach);
		}
		
		trainings.put(training.getName(), training);
		buildingDAO.addTraining(training);
		Save();
		return training;
	}

	public void editTraining(Training newTraining) {
		this.trainings.remove(newTraining.getName());
		this.trainings.put(newTraining.getName(), newTraining);
		
		User coach = null;
		if(newTraining.getCoach() != null) {
			coach = userDAO.getUser(newTraining.getCoach().getUsername());
			coach.setSportsBuilding(newTraining.getSportsBuilding());
			userDAO.editUser(coach.getUsername(), coach);
		}
		buildingDAO.addTraining(newTraining);
		
		Save();
	}

	public Collection<Training> getTrainings(Collection<String> trainingNames) {
		Collection<Training> foundTrainings = new ArrayList<Training>();
		
		for(String name : trainingNames)
			if(trainings.containsKey(name))
				foundTrainings.add(trainings.get(name));
		
		return foundTrainings;
	}

	private void Save() {
		String json = g.toJson(trainings);

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(GlobalPaths.trainingsDBPath)));
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void Load() {
		String json = "";

		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(GlobalPaths.trainingsDBPath)));
			String currentLine;
			while ((currentLine = reader.readLine()) != null)
				json += currentLine;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return;
		}

		if (g.fromJson(json, new TypeToken<HashMap<String, Training>>() {
		}.getType()) == null) {
			this.trainings = new HashMap<>();
		} else {
			this.trainings = g.fromJson(json, new TypeToken<HashMap<String, Training>>() {
			}.getType());
		}

	}
}
