package controller;

import beans.Address;
import beans.DateTime;
import beans.Location;
import beans.Product;
import beans.SportsBuilding;
import enums.BuildingContent;
import enums.BuildingType;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import DTO.BuildingTypeDTO;
import DTO.NewBuildingDTO;
import beans.SportsBuilding;
import services.SportsBuildingService;
import spark.QueryParamsMap;

import static spark.Spark.get;
import static spark.Spark.post;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class SportsBuildingController {
	private static Gson g = new Gson();
	private static SportsBuildingService buildingService = new SportsBuildingService();

	public static void addBuilding() {
		post("rest/buildings/add", (req, res) -> {
			res.type("application/json");
			NewBuildingDTO buildingDTO = g.fromJson(req.body(), NewBuildingDTO.class);

			SportsBuilding building = new SportsBuilding(
					buildingDTO.getName(),
					SportsBuilding.StringToType(buildingDTO.getType()),
					new ArrayList<String>(),
					new Location(
							Double.parseDouble(buildingDTO.getGeoWidth()),
							Double.parseDouble(buildingDTO.getGeoLen()),
							new Address(buildingDTO.getStreet(), buildingDTO.getNumber(), buildingDTO.getCity(), buildingDTO.getPostalCode())),
					buildingDTO.getLogoPath(),
					0,
					new DateTime(0, 0, 0, Integer.parseInt(buildingDTO.getStartHours()), 0),
					new DateTime(0, 0, 0, Integer.parseInt(buildingDTO.getEndHours()), 0));

			return g.toJson(buildingService.addBuilding(building));
		});
	}

	public static void getAll() {
		get("rest/buildings/", (req, res) -> {
			res.type("application/json");
			return g.toJson(buildingService.getAll());
		});
	}

	public static void getSportsBuilding() {
		get("rest/buildings/:name", (req, res) -> {
			res.type("application/json");
			String name = req.params("name");
			return g.toJson(buildingService.getSportsBuilding(name));
		});
	}
	
	public static void sortByNameAscending() {
		post("rest/buildings/sort/name/asc", (req, res) -> {
			res.type("application/json");
			Collection<SportsBuilding> buildings = g.fromJson(req.body(), new TypeToken<Collection<SportsBuilding>>(){}.getType());
			
			return g.toJson(buildingService.sortByNameAscending(buildings));
		});
	}
	
	public static void sortByNameDescending() {
		post("rest/buildings/sort/name/desc", (req, res) -> {
			res.type("application/json");
			Collection<SportsBuilding> buildings = g.fromJson(req.body(), new TypeToken<Collection<SportsBuilding>>(){}.getType());
			
			return g.toJson(buildingService.sortByNameDescending(buildings));
		});
	}
	
	public static void sortByLocationAscending() {
		post("rest/buildings/sort/loc/asc", (req, res) -> {
			res.type("application/json");
			Collection<SportsBuilding> buildings = g.fromJson(req.body(), new TypeToken<Collection<SportsBuilding>>(){}.getType());
			
			return g.toJson(buildingService.sortByLocationAscending(buildings));
		});
	}
	
	public static void sortByLocationDescending() {
		post("rest/buildings/sort/loc/desc", (req, res) -> {
			res.type("application/json");
			Collection<SportsBuilding> buildings = g.fromJson(req.body(), new TypeToken<Collection<SportsBuilding>>(){}.getType());
			
			return g.toJson(buildingService.sortByLocationDescending(buildings));
		});
	}
	
	public static void sortByRatingAscending() {
		post("rest/buildings/sort/rating/asc", (req, res) -> {
			res.type("application/json");
			Collection<SportsBuilding> buildings = g.fromJson(req.body(), new TypeToken<Collection<SportsBuilding>>(){}.getType());
			
			return g.toJson(buildingService.sortByRatingAscending(buildings));
		});
	}
	
	public static void sortByRatingDescending() {
		post("rest/buildings/sort/rating/desc", (req, res) -> {
			res.type("application/json");
			Collection<SportsBuilding> buildings = g.fromJson(req.body(), new TypeToken<Collection<SportsBuilding>>(){}.getType());
			
			return g.toJson(buildingService.sortByRatingDescending(buildings));
		});
	}

	public static void Query() {
		get("rest/buildings/query", (req, res) -> {
			String name = req.queryParams("name");
			String type = req.queryParams("type");
			String location = req.queryParams("loc");
			String average = req.queryParams("avg");
			String open = req.queryParams("open");

			ArrayList<SportsBuilding> nameQuery = !(name == null) ? buildingService.queryByName(name) : (ArrayList<SportsBuilding>) buildingService.getAll();

			ArrayList<SportsBuilding> typeQuery = new ArrayList<SportsBuilding>();
			if (type != null) {
				typeQuery = !type.equals("All") ? buildingService.queryByType(SportsBuilding.StringToType(type))
						: new ArrayList<SportsBuilding>();
			}

			ArrayList<SportsBuilding> locationQuery = !(location == null) ? buildingService.queryByLocation(location) : (ArrayList<SportsBuilding>) buildingService.getAll();
			
			ArrayList<SportsBuilding> ratingQuery = new ArrayList<SportsBuilding>();
			if(average != null)
				ratingQuery = !average.isEmpty() ? buildingService.queryByAverage(average)
						: buildingService.queryByAverage("0");

			// find max len of arrays
			HashMap<Integer, ArrayList<SportsBuilding>> lengths = new HashMap<Integer, ArrayList<SportsBuilding>>();
			if (!nameQuery.isEmpty())
				lengths.put(nameQuery.size(), nameQuery);
			if (!typeQuery.isEmpty())
				lengths.put(typeQuery.size(), typeQuery);
			if (!locationQuery.isEmpty())
				lengths.put(locationQuery.size(), locationQuery);
			if (!ratingQuery.isEmpty())
				lengths.put(ratingQuery.size(), ratingQuery);

			ArrayList<SportsBuilding> biggestArray = new ArrayList<SportsBuilding>();
			if (!lengths.isEmpty())
				biggestArray = lengths.get(Collections.max(lengths.keySet()));

			// go through biggest array and do logical & on all elements, and add if all
			// have that element
			ArrayList<SportsBuilding> queriedBuildings = new ArrayList<SportsBuilding>();
			for (SportsBuilding building : biggestArray) {
				boolean contained = true;

				for (ArrayList<SportsBuilding> arr : lengths.values())
					if (!arr.contains(building)) {
						contained = false;
						break;
					}

				if (contained)
					queriedBuildings.add(building);
			}

			// filter out only open buildings if needed
			if (open != null)
				if (open.equals("open"))
					queriedBuildings = (ArrayList<SportsBuilding>) buildingService.getAllOpen(queriedBuildings);

			res.type("application/json");
			return g.toJson(queriedBuildings);
		});
	}
}
