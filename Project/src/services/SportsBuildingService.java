package services;

import java.util.ArrayList;
import java.util.Collection;

import DAO.SportsBuildingDAO;
import beans.SportsBuilding;
import enums.BuildingType;

public class SportsBuildingService {
	private SportsBuildingDAO buildings = SportsBuildingDAO.getInstance();
	
	public SportsBuilding addBuilding(SportsBuilding building) {
		return this.buildings.addBuilding(building);
	}
	
	public Collection<SportsBuilding> getAll(){
		return this.buildings.getAll();
	}
	
	public SportsBuilding getSportsBuilding(String name) {
		for (SportsBuilding sBuilding : getAll()) {
			if(sBuilding.getName().equals(name))
				return sBuilding;
		}
		return null;
	}
	
	public ArrayList<SportsBuilding> queryByName(String name){
		return this.buildings.queryByName(name);
	}
	
	public ArrayList<SportsBuilding> queryByType(BuildingType type){
		return this.buildings.queryByType(type);
	}
	
	public ArrayList<SportsBuilding> queryByLocation(String city){
		return this.buildings.queryByLocation(city);
	}
	
	public ArrayList<SportsBuilding> queryByAverage(String average){
		return this.buildings.queryByAverage(average);
	}
	
	public Collection<SportsBuilding> sortByNameAscending(Collection<SportsBuilding> buildings){
		return this.buildings.sortByNameAscending(buildings);
	}
	
	public Collection<SportsBuilding> sortByNameDescending(Collection<SportsBuilding> buildings){
		return this.buildings.sortByNameDescending(buildings);
	}
	
	public Collection<SportsBuilding> sortByLocationAscending(Collection<SportsBuilding> buildings){
		return this.buildings.sortByLocationAscending(buildings);
	}
	
	public Collection<SportsBuilding> sortByLocationDescending(Collection<SportsBuilding> buildings){
		return this.buildings.sortByLocationDescending(buildings);
	}
	
	public Collection<SportsBuilding> sortByRatingAscending(Collection<SportsBuilding> buildings){
		return this.buildings.sortByRatingAscending(buildings);
	}
	
	public Collection<SportsBuilding> sortByRatingDescending(Collection<SportsBuilding> buildings){
		return this.buildings.sortByRatingDescending(buildings);
	}
	
	public Collection<SportsBuilding> getAllOpen(Collection<SportsBuilding> buildings){
		for(SportsBuilding b : buildings)
			if(!b.isOpen())
				buildings.remove(b);
		
		return buildings;
	}
}
