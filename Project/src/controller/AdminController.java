package controller;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import java.util.ArrayList;

import com.google.gson.Gson;

import DTO.LoginDTO;
import DTO.PromoCodeDTO;
import DTO.SortDTO;
import beans.User;
import enums.CustomerTypeName;
import enums.Role;
import services.AdminService;
import services.UserService;

public class AdminController {

	private static Gson g = new Gson();
	private static AdminService adminService = new AdminService();
	private static UserService userService = new UserService();
	
	public static void createManager() {
		post("rest/admin/createManager", (req, res) -> {
			res.type("application/json");
			User manager = g.fromJson(req.body(), User.class);
			adminService.createManager(manager);
			return "SUCCESS";
		});
	}
	
	public static void checkIfStuffExists() {
		post("rest/admin/check", (req, res) -> {
			res.type("application/json");
			LoginDTO dto = g.fromJson(req.body(), LoginDTO.class);
			String username = dto.getUsername();
			String userName = userService.checkIfUserExistsForRegister(username);
		
			if(userName == null) {
				return g.toJson("True");
			}
			return g.toJson("false");
		});
	}
	
	public static void createCoach() {
		post("rest/admin/createCoach", (req, res) -> {
			res.type("application/json");
			User coach = g.fromJson(req.body(), User.class);
			adminService.createCoach(coach);
			return "SUCCESS";
		});
	}
	
	public static void acceptComment() {
		put("rest/admin/acceptComment/:id", (req, res) -> {
			res.type("application/json");
			int id = Integer.parseInt(req.params("id"));
			return adminService.acceptComment(id); 
		});
	}
	
	public static void rejectComment() {
		put("rest/admin/rejectComment/:id", (req, res) -> {
			res.type("application/json");
			int id = Integer.parseInt(req.params("id"));
			return adminService.rejectComment(id);
		});
	}
	
	public static void getAllPendingComments() {
		get("rest/admin/pendingComments", (req, res) -> {
			res.type("application/json");
			return g.toJson(adminService.getAllPendingComments());
		});
	}
	
	public static void getAllCheckedComments() {
		get("rest/admin/checkedComments/:name", (req, res) -> {
			res.type("application/json");
			String facilityName = req.params("name");
			return g.toJson(adminService.getAllCheckedFacilityComments(facilityName));
		});
	}
	
	public static void getAllUsers() { 
		get("rest/admin/users", (req, res) -> {
			res.type("application/json");
			return g.toJson(adminService.getAllUsers());
		});
	}
	
	public static void search() {
		get("rest/admin/search", (req, res) -> {
			String name = req.queryParams("name");
			String lastname = req.queryParams("lastname");
			String username = req.queryParams("username");
			
			ArrayList<User> usersByName = adminService.searchByName(name);
			ArrayList<User> usersByLastname = adminService.searchByLastname(lastname);
			ArrayList<User> usersByUsername = adminService.searchByUsername(username);
			
			ArrayList<User> searchedUsers = new ArrayList<User>();
			searchedUsers.addAll(usersByName);
			searchedUsers.addAll(usersByLastname);
			searchedUsers.addAll(usersByUsername);
			
			res.type("application/json");
			return g.toJson(searchedUsers);
		});
	}
	
	public static void filter() {
		get("rest/admin/filter", (req, res) -> {
			String roleStr = req.queryParams("role");
			Role role = null;
			
			if(roleStr.equals("Coach")) {
				role = Role.Coach;
			} else if(roleStr.equals("Administrator")) {
				role = Role.Administrator;
			} else if(roleStr.equals("Customer")) {
				role = Role.Customer;
			} else if(roleStr.equals("Manager")) {
				role = Role.Manager;
			}
			
			ArrayList<User> filteredUsers = adminService.filterByRole(role);
			
			res.type("application/json");
			return g.toJson(filteredUsers);
		});
	}
	
	public static void filterByCustomerType() {
		get("rest/admin/customerType", (req, res) -> {
			String customerTypeStr = req.queryParams("type");
			CustomerTypeName customerType = null;
			
			if(customerTypeStr.equals("Bronze")) {
				customerType = CustomerTypeName.Bronze;
			} else if(customerTypeStr.equals("Silver")) {
				customerType = CustomerTypeName.Silver;
			} else if(customerTypeStr.equals("Gold")) {
				customerType = CustomerTypeName.Gold;
			}
			
			ArrayList<User> filteredUsers = adminService.filterByCustomerType(customerType);
			res.type("application/json");
			return g.toJson(filteredUsers);
		});
	}
	
	public static void createPromoCode() {
		post("rest/admin/promo", (req, res) -> {
			res.type("application/json");
			PromoCodeDTO dto = g.fromJson(req.body(), PromoCodeDTO.class);
			return g.toJson(adminService.createPromoCode(dto));
		});
	}
	
	public static void sortAsc() {
		post("rest/admin/asc", (req, res) -> {
			res.type("application/json");
			SortDTO dto = g.fromJson(req.body(), SortDTO.class);
			return g.toJson(adminService.sortAsc(dto));
		});
	}
	
	public static void sortDesc() {
		post("rest/admin/desc", (req, res) -> {
			res.type("application/json");
			SortDTO dto = g.fromJson(req.body(), SortDTO.class);
			return g.toJson(adminService.sortDesc(dto));
		});
	}
}
