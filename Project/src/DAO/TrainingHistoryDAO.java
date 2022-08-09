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

import beans.DateTime;
import beans.SportsBuilding;
import beans.Training;
import beans.TrainingHistory;
import beans.CustomerType;
import beans.User;
import beans.Address;
import beans.Location;
import beans.MembershipFee;
import enums.BuildingContent;
import enums.BuildingType;
import enums.Gender;
import enums.Role;
import enums.TrainingType;
import utils.GlobalPaths;

public class TrainingHistoryDAO {

	private static TrainingHistoryDAO instance = null;
	
	public static TrainingHistoryDAO getInstance()
    {
        if (instance == null)
            instance = new TrainingHistoryDAO();
 
        return instance;
    }
	
	private Gson g = new Gson();
	private HashMap<Integer, TrainingHistory> trainingHistories;
	private HashMap<String, User> users;
	
	private TrainingHistoryDAO() { 
		Load();
		
//		SportsBuilding b1 = new SportsBuilding(
//			"Spens1",
//			BuildingType.SPORTS_CENTER,
//			new ArrayList<BuildingContent>(),
//			new Location(49.3, 15.3, new Address("Ulica", "12", "Novi Sad", "21000")),
//			"images\\spens.jpg",
//			3.5,
//			new DateTime(0, 0, 0, 8, 0),
//			new DateTime(0, 0, 0, 20, 0)
//		);
//
//		SportsBuilding b2 = new SportsBuilding(
//			"Cair",
//			BuildingType.SPORTS_CENTER,
//			new ArrayList<BuildingContent>(),
//			new Location(50.3, 16.3, new Address("Ulica2", "16", "Nis", "18000")),
//			"images\\spens.jpg",
//			3.5,
//			new DateTime(0, 0, 0, 9, 0),
//			new DateTime(0, 0, 0, 17, 0)
//		);
//		
//		User coach1 = new User("djomla", "123", "Mladen", "Krstajic", Gender.Male, new DateTime(11, 10, 1974, 0, 0), Role.Coach, new MembershipFee(), new ArrayList<SportsBuilding>(), 0, new CustomerType(), new ArrayList<TrainingHistory>(), new SportsBuilding());
//		User coach2 = new User("sima", "123", "Sima", "Trobonjaca", Gender.Male, new DateTime(11, 10, 1974, 0, 0), Role.Coach, new MembershipFee(), new ArrayList<SportsBuilding>(), 0, new CustomerType(), new ArrayList<TrainingHistory>(), new SportsBuilding());
//		User customer = this.users.get("ckepatroni");
//		User customer2 = this.users.get("spikaLudi");
//		
//		TrainingHistory th1 = new TrainingHistory(1, new DateTime(1, 7, 2022, 12, 00), 
//				new Training("workout1", TrainingType.Personal, b1, 60, coach1, "", "", 700), customer, coach1);
//		TrainingHistory th2 = new TrainingHistory(2, new DateTime(25, 6, 2022, 9, 30), 
//				new Training("workout2", TrainingType.Personal, b1, 60, coach2, "", "", 700), customer, coach2);
//		TrainingHistory th3 = new TrainingHistory(3, new DateTime(1, 4, 2022, 7, 00), 
//				new Training("workout3", TrainingType.Group, b1, 60, coach1, "", "", 700), customer2, coach1);
//		TrainingHistory th4 = new TrainingHistory(4, new DateTime(15, 7, 2022, 12, 00), 
//				new Training("workout4", TrainingType.Personal, b1, 60, coach1, "", "", 700), customer, coach1);
//		TrainingHistory th5 = new TrainingHistory(5, new DateTime(16, 7, 2022, 9, 30), 
//				new Training("workout5", TrainingType.Personal, b2, 60, coach2, "", "", 700), customer2, coach2);
//		TrainingHistory th6 = new TrainingHistory(6, new DateTime(17, 7, 2022, 07, 00), 
//				new Training("workout6", TrainingType.Group, b2, 60, coach2, "", "", 700), customer2, coach2);
//		TrainingHistory th7 = new TrainingHistory(7, new DateTime(16, 7, 2022, 07, 00), 
//				new Training("workout7", TrainingType.Group, b1, 60, coach1, "", "", 700), customer, coach1);
//		
//		this.trainingHistories.put(th1.getId(), th1);
//		this.trainingHistories.put(th2.getId(), th2);
//		this.trainingHistories.put(th3.getId(), th3);		
//		this.trainingHistories.put(th4.getId(), th4);
//		this.trainingHistories.put(th5.getId(), th5);
//		this.trainingHistories.put(th6.getId(), th6);
//		this.trainingHistories.put(th7.getId(), th7);
//
//		Save();
	}
	
	public int getNewId() {
        if(trainingHistories.isEmpty())
            return 1;
        int maxId = -1;
        for(int id : trainingHistories.keySet()) {
            if(maxId < id) {
                maxId = id;
            }
        }
        return maxId+1;
    }
	
	public ArrayList<Training> searchBySportsBuilding(String username, String buildingName) {
		ArrayList<Training> searchedTrainings = new ArrayList<Training>();
		if(buildingName.equals(null)) {
			return searchedTrainings;
		}
		
		for(Training training : getUsersTrainings(username)) {
			if(training.getSportsBuilding().getName().toLowerCase().contains(buildingName.toLowerCase()))
				searchedTrainings.add(training);
		}
		
		return searchedTrainings;
	}
	
	public ArrayList<TrainingHistory> searchByCheckInDate(String username, DateTime firstDate, DateTime secondDate) {
		ArrayList<TrainingHistory> searchedTrainings = new ArrayList<TrainingHistory>();
		if(firstDate.equals(null) || secondDate.equals(null)) {
			return searchedTrainings;
		}
		
		for (TrainingHistory trainingH : getUsersTrainingHistory(username)) {
			if(trainingH.getDateAndTimeOfCheckIn().isAfter(firstDate) && trainingH.getDateAndTimeOfCheckIn().isBefore(secondDate)) {
				searchedTrainings.add(trainingH);
			}
		}
		
		return searchedTrainings;
	}
	
	public ArrayList<Training> searchByPrice(String username, double lowerPrice, double higherPrice) {
		ArrayList<Training> searchedTrainings = new ArrayList<Training>();
		
		for(TrainingHistory trainingH : getUsersTrainingHistory(username)) {
			double price = trainingH.getTraining().getPrice();
			if(price >= lowerPrice && price <= higherPrice)
				searchedTrainings.add(trainingH.getTraining());
		}
		
		return searchedTrainings;
	}
	
	public ArrayList<Training> filterBySBuildingType(String username, BuildingType bType) {
		ArrayList<Training> filteredTrainings = new ArrayList<Training>();
		
		for(Training training : getUsersTrainings(username)) {
			if(training.getSportsBuilding().getType() == bType) {
				filteredTrainings.add(training);
			}
		}
		
		return filteredTrainings;
	}
	
	public ArrayList<Training> filterByTrainingType(String username, TrainingType trainingType) {
		ArrayList<Training> filteredTrainings = new ArrayList<Training>();
		
		for(Training training : getUsersTrainings(username)) {
			if(training.getTrainingType() == trainingType) {
				filteredTrainings.add(training);
			}
		}
		
		return filteredTrainings;
	}
	
	public boolean addTrainingHistory(TrainingHistory newTraining) {
		for(int id : trainingHistories.keySet()) {
			if(newTraining.getId() == id) {
				return false;
			}
		}
		this.trainingHistories.put(newTraining.getId(), newTraining);
		Save();
		return true;
	}
	
	public void deleteTrainingHistory(int id) {
		for (TrainingHistory trainingH : this.trainingHistories.values()) {
			if(trainingH.getId() == id) {
				trainingH.getTraining().setDeleted(true);
				break;
			}
		}
		Save();
	}
	
	public void editTrainingHistory(int id, TrainingHistory trainingHistory) {
		this.trainingHistories.put(id, trainingHistory);
		Save();
	}
	
	public Collection<TrainingHistory> getTrainingHistories() {
		Collection<TrainingHistory> tHistories = new ArrayList<TrainingHistory>();
		for (TrainingHistory trainingH : this.trainingHistories.values()) {
			if(trainingH.getTraining().isDeleted() == false) {
				tHistories.add(trainingH);
			}
		}
		return tHistories;
	}
	
	public Collection<TrainingHistory> getUsersTrainingHistory(String username) {
		Collection<TrainingHistory> usersTrainingHistory = new ArrayList<TrainingHistory>();
		for (TrainingHistory trainingHistory : this.trainingHistories.values()) {
			if(trainingHistory.getCoach() == null) {
				continue;
			}
			if((trainingHistory.getCustomer().getUsername().equals(username) || trainingHistory.getCoach().getUsername().equals(username)) && trainingHistory.getTraining().isDeleted() == false)
				usersTrainingHistory.add(trainingHistory);
		}
		
		return usersTrainingHistory;
	}
	
	public ArrayList<Training> getUsersTrainings(String username) {
		ArrayList<Training> trainings = new ArrayList<Training>();
		for (TrainingHistory trainingH : trainingHistories.values()) {
			if((trainingH.getCustomer().getUsername().equals(username) || trainingH.getCoach().getUsername().equals(username)) && trainingH.getTraining().isDeleted() == false)
				trainings.add(trainingH.getTraining());
		}
		return trainings;
	}
	
	public TrainingHistory getTrainingHistory(int id) {
		TrainingHistory trainingHistory = this.trainingHistories.get(id);
		if(trainingHistory.getTraining().isDeleted() == true)
			return null;
		return trainingHistories.get(id);
	}
	
	private void Save() {
		String json = g.toJson(trainingHistories);

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(GlobalPaths.trainingHistoriesDBPath)));
			writer.write(json);
			writer.close();
		
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void Load() {
		String json = "";
		String json2 = "";
		
		try { 
			BufferedReader reader = new BufferedReader(new FileReader(new File(GlobalPaths.trainingHistoriesDBPath)));
			String currentLine;
			while((currentLine = reader.readLine()) != null)
				json += currentLine;
			
		}
		catch (IOException e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			return;
		}
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(GlobalPaths.usersDBPath)));
			String currentLine;
			while((currentLine = reader.readLine()) != null)
				json2 += currentLine;
		} catch (IOException e) {
			return;
		}
		
		if(g.fromJson(json, new TypeToken<HashMap<Integer, TrainingHistory>>(){}.getType()) == null && g.fromJson(json2, new TypeToken<HashMap<String, User>>(){}.getType()) == null) {
			this.trainingHistories = new HashMap<Integer, TrainingHistory>();
			this.users = new HashMap<String, User>();
		} else if(g.fromJson(json, new TypeToken<HashMap<Integer, TrainingHistory>>(){}.getType()) == null && g.fromJson(json2, new TypeToken<HashMap<String, User>>(){}.getType()) != null) {
			this.trainingHistories = new HashMap<Integer, TrainingHistory>();
			this.users = g.fromJson(json2, new TypeToken<HashMap<String, User>>(){}.getType());
		} else if (g.fromJson(json, new TypeToken<HashMap<Integer, TrainingHistory>>(){}.getType()) != null && g.fromJson(json2, new TypeToken<HashMap<String, User>>(){}.getType()) == null) {
			this.users = new HashMap<String, User>();
			this.trainingHistories = g.fromJson(json, new TypeToken<HashMap<Integer, TrainingHistory>>(){}.getType());
		}
		else {
			this.trainingHistories = g.fromJson(json, new TypeToken<HashMap<Integer, TrainingHistory>>(){}.getType());
			this.users = g.fromJson(json2, new TypeToken<HashMap<String, User>>(){}.getType());
		}
	}
}
