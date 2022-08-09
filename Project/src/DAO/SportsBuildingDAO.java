package DAO;

import utils.GlobalPaths;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import beans.Address;
import beans.DateTime;
import beans.Location;
import beans.SportsBuilding;
import beans.Training;
import enums.BuildingType;
import enums.BuildingContent;

public class SportsBuildingDAO {
	
	private static SportsBuildingDAO instance = null;
	
	public static SportsBuildingDAO getInstance() {
		if(instance == null)
			instance = new SportsBuildingDAO();
		
		return instance;
	}
	
	private Gson g = new Gson();
	private HashMap<String, SportsBuilding> buildings = new HashMap<String, SportsBuilding>();
	
	public SportsBuildingDAO() {		
		Load();
	}
	
	public SportsBuilding addBuilding(SportsBuilding building) {
		if(buildings.containsKey(building.getName()))
			if(!buildings.get(building.getName()).isDeleted())
				return null;
		
		buildings.put(building.getName(), building);
		Save();
		return building;
	}
	
	public Collection<SportsBuilding> getAll(){
		Collection<SportsBuilding> sBuildings = new ArrayList<SportsBuilding>();
		for (SportsBuilding sportsBuilding : this.buildings.values()) {
			if(sportsBuilding.isDeleted() == false)
				sBuildings.add(sportsBuilding);
		}
		return sortOpenBuildings(sBuildings);
	}
	
	public void addTraining(Training training) {
		if(buildings.get(training.getSportsBuilding().getName()).getContentNames() != null)
			buildings.get(training.getSportsBuilding().getName()).getContentNames().add(training.getName());
		else {
			buildings.get(training.getSportsBuilding().getName()).setContentNames(new ArrayList<String>());
			buildings.get(training.getSportsBuilding().getName()).getContentNames().add(training.getName());
		}
		Save();
		System.out.println(buildings.get(training.getSportsBuilding().getName()).getContentNames().size());
	}
	
	private Collection<SportsBuilding> sortOpenBuildings(Collection<SportsBuilding> buildings){
		Collection<SportsBuilding> open = new ArrayList<SportsBuilding>();
		Collection<SportsBuilding> closed = new ArrayList<SportsBuilding>();
		
		for(SportsBuilding b : buildings) {
			if(b.isOpen())
				open.add(b);
			else
				closed.add(b);
		}
		
		// this is like append whole list to end
		open.addAll(closed);
		
		return open;
	}
	
	public SportsBuilding getSportsBuilding(String name) {
		for (SportsBuilding sBuilding : getAll()) {
			if(sBuilding.getName().equals(name)) {
				return sBuilding;
			}
		}
		return null;
	}
	
	public ArrayList<SportsBuilding> queryByName(String name){
		ArrayList<SportsBuilding> queriedBuildings = new ArrayList<SportsBuilding>();
		if(name == null)
			return queriedBuildings;
		
		for(SportsBuilding building : buildings.values())
			if(building.getName().toLowerCase().contains(name.toLowerCase()))
				queriedBuildings.add(building);
		
		return queriedBuildings;
	}
	
	public ArrayList<SportsBuilding> queryByType(BuildingType type){
		ArrayList<SportsBuilding> queriedBuildings = new ArrayList<SportsBuilding>();
		if(type == null)
			return queriedBuildings;
		
		for(SportsBuilding building : buildings.values())
			if(building.getType() == type)
				queriedBuildings.add(building);
		
		return queriedBuildings;
	}
	
	public ArrayList<SportsBuilding> queryByLocation(String city){
		ArrayList<SportsBuilding> queriedBuildings = new ArrayList<SportsBuilding>();
		if(city == null)
			return queriedBuildings;
		
		for(SportsBuilding building : buildings.values())
			if(building.getLocation().getAddress().getCity().equals(city))
				queriedBuildings.add(building);
		
		return queriedBuildings;
	}
	
	public ArrayList<SportsBuilding> queryByAverage(String average){
		ArrayList<SportsBuilding> queriedBuildings = new ArrayList<SportsBuilding>();
		if(average == null)
			return queriedBuildings;
		
		for(SportsBuilding building : buildings.values())
			if(building.getAverageRating() >= Double.parseDouble(average))
				queriedBuildings.add(building);
		
		return queriedBuildings;
	}
	
	public Collection<SportsBuilding> sortByNameAscending(Collection<SportsBuilding> buildings){
		List<String> building_names = new ArrayList<String>();
		for(SportsBuilding b : buildings)
			building_names.add(b.getName());
		
		Collections.sort(building_names);
		
		Collection<SportsBuilding> sorted_buildings = new ArrayList<SportsBuilding>();
		for(String name : building_names)
			for(SportsBuilding building : buildings)
				if(building.getName().equals(name) && !sorted_buildings.contains(building))
					sorted_buildings.add(building);
		
		return sorted_buildings;
	}
	
	public Collection<SportsBuilding> sortByNameDescending(Collection<SportsBuilding> buildings){
		List<String> building_names = new ArrayList<String>();
		for(SportsBuilding b : buildings)
			building_names.add(b.getName());
		
		Collections.sort(building_names, Collections.reverseOrder());
		
		Collection<SportsBuilding> sorted_buildings = new ArrayList<SportsBuilding>();
		for(String name : building_names)
			for(SportsBuilding building : buildings)
				if(building.getName().equals(name) && !sorted_buildings.contains(building))
					sorted_buildings.add(building);
		
		return sorted_buildings;
	}
	
	public Collection<SportsBuilding> sortByLocationAscending(Collection<SportsBuilding> buildings){
		List<String> building_locations = new ArrayList<String>();
		for(SportsBuilding b : buildings)
			building_locations.add(b.getLocation().getAddress().getCity());
		
		Collections.sort(building_locations);
		
		Collection<SportsBuilding> sorted_buildings = new ArrayList<SportsBuilding>();
		for(String location : building_locations)
			for(SportsBuilding building : buildings)
				if(building.getLocation().getAddress().getCity().equals(location) && !sorted_buildings.contains(building))
					sorted_buildings.add(building);
		
		return sorted_buildings;
	}
	
	public Collection<SportsBuilding> sortByLocationDescending(Collection<SportsBuilding> buildings){
		List<String> building_locations = new ArrayList<String>();
		for(SportsBuilding b : buildings)
			building_locations.add(b.getLocation().getAddress().getCity());
		
		Collections.sort(building_locations, Collections.reverseOrder());
		
		Collection<SportsBuilding> sorted_buildings = new ArrayList<SportsBuilding>();
		for(String location : building_locations)
			for(SportsBuilding building : buildings)
				if(building.getLocation().getAddress().getCity().equals(location) && !sorted_buildings.contains(building))
					sorted_buildings.add(building);
		
		return sorted_buildings;
	}
	
	public Collection<SportsBuilding> sortByRatingAscending(Collection<SportsBuilding> buildings){
		List<Double> building_ratings = new ArrayList<Double>();
		for(SportsBuilding b : buildings)
			building_ratings.add(b.getAverageRating());
		
		Collections.sort(building_ratings);
		
		Collection<SportsBuilding> sorted_buildings = new ArrayList<SportsBuilding>();
		for(Double rating : building_ratings)
			for(SportsBuilding building : buildings)
				if(building.getAverageRating() == rating && !sorted_buildings.contains(building))
					sorted_buildings.add(building);
		
		return sorted_buildings;
	}
	
	public Collection<SportsBuilding> sortByRatingDescending(Collection<SportsBuilding> buildings){
		List<Double> building_ratings = new ArrayList<Double>();
		for(SportsBuilding b : buildings)
			building_ratings.add(b.getAverageRating());
		
		Collections.sort(building_ratings, Collections.reverseOrder());
		
		Collection<SportsBuilding> sorted_buildings = new ArrayList<SportsBuilding>();
		for(Double rating : building_ratings)
			for(SportsBuilding building : buildings)
				if(building.getAverageRating() == rating && !sorted_buildings.contains(building))
					sorted_buildings.add(building);
		
		return sorted_buildings;
	}
	
	private void Save() {
		String json = g.toJson(buildings);
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(GlobalPaths.sportsObjectsDBPath)));
			writer.write(json);
			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void Load() {
		String json = "";
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(GlobalPaths.sportsObjectsDBPath)));
			String currentLine;
			while((currentLine = reader.readLine()) != null)
				json += currentLine;
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			return;
		}
		
		if(g.fromJson(json, new TypeToken<HashMap<String, SportsBuilding>>(){}.getType()) == null) {
			this.buildings = new HashMap<>();
		} else {
			this.buildings = g.fromJson(json, new TypeToken<HashMap<String, SportsBuilding>>(){}.getType());
		}
		
	}
}
