package controller;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import java.security.Key;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.google.gson.Gson;

import DTO.FeeExpiredDTO;
import DTO.JwtDTO;
import DTO.LoggedUserDTO;
import DTO.LoginDTO;
import DTO.ManagerDTO;
import beans.CustomerType;
import beans.DateTime;
import beans.MembershipFee;
import beans.SportsBuilding;
import beans.TrainingHistory;
import beans.User;
import enums.Gender;
import enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import services.CustomerService;
import services.PaymentService;
import services.SportsBuildingService;
import services.UserService;

public class UserController {
	
	private static Gson g = new Gson();
	private static UserService userService = new UserService();
	private static PaymentService paymentService = new PaymentService();
	private static SportsBuildingService sportsService = new SportsBuildingService(); 
	private static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
	public static void getUsers() { 
		get("rest/users/", (req, res) -> {
			res.type("application/json");
			return g.toJson(userService.getUsers());
		});
	}
	
	public static void getUser() { 
		get("rest/users/oneUser/:username", (req, res) -> {
			res.type("application/json");
			String username = req.params("username");
			return g.toJson(userService.getUser(username));
		});
	}
	
	public static void getWholeUser() {
		get("rest/users/wholeUser/:username", (req, res) -> {
			res.type("application/json");
			String username = req.params("username");
			return g.toJson(userService.getWholeUser(username));
		});
	}
	
	public static void checkIfUserExistsForRegister() {
		post("rest/users/checkReg", (req, res) -> {
			res.type("application/json");
			LoginDTO dto = g.fromJson(req.body(), LoginDTO.class);
			String username = dto.getUsername();
			String userName = userService.checkIfUserExistsForRegister(username);
			
			if(userName == null) {
				String jws = Jwts.builder().setSubject(username).setIssuedAt(new Date()).signWith(key).compact();
				return g.toJson(jws);
			}
			return g.toJson("");
		});
	}
	
	public static void getLoggedUser() {
		get("rest/users/logged", (req, res) -> {
			String auth  = req.headers("Authorization");
			System.out.println("Authorization: " + auth);
			if((auth) != null && (auth.contains("Bearer "))) {
				String jwt = auth.substring(auth.indexOf("Bearer ") + 7);
				try {
					Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
					String username = claims.getBody().getSubject();
					return g.toJson(new LoggedUserDTO(username, userService.getUsersRole(username)));
				} catch(Exception e) {
					System.out.println(e.getMessage());
				}
			}
			
			return g.toJson(new LoggedUserDTO());
		});
	}
	
	public static void checkIfUserExists() {
		post("rest/users/check", (req, res) -> {
			res.type("application/json");
			LoginDTO dto = g.fromJson(req.body(), LoginDTO.class);
			String username = dto.getUsername();
			String password = dto.getPassword();			
			String userName = userService.checkIfUserExists(username, password);

			if(userName != null) {

				String jws = Jwts.builder().setSubject(userName).setIssuedAt(new Date()).signWith(key).compact();
				//validacija o isteku clanarine
				//ne moze da upadne dok ne plati
				if(paymentService.checkIfMembershipFeeExpired(userName)) {
					return g.toJson(new FeeExpiredDTO(jws, "expired"));
				}
				
				return g.toJson(new FeeExpiredDTO(jws, ""));
			}
			return g.toJson(new FeeExpiredDTO("", ""));
		});
	}
	
	public static void addUser() { 
		post("rest/users/add", (req, res) -> {
			res.type("application/json");
			User user = g.fromJson(req.body(), User.class);
			userService.addUser(user);
			return "SUCCESS";
		});
	}
	
	public static void editUser() { 
		put("rest/users/edit/:username", (req, res) -> {
			res.type("application/json");
			String username = req.params("username");
			User user = g.fromJson(req.body(), User.class);
			
			if(user.getUsername() != null) {
				System.out.println(user.getUsername());
				userService.editUser(username, user);
				String jws = Jwts.builder().setSubject(user.getUsername()).setIssuedAt(new Date()).signWith(key).compact();
				return g.toJson(jws);
			}

			return g.toJson("");
		});
	}
	
	public static void deleteUsers() { 
		delete("rest/users/delete/:username", (req, res) -> {
			res.type("application/json");
			String username = req.params("username");
			userService.deleteUser(username);
			return "SUCCESS";
		});
	}
	
	public static void getUnemployedManagers(){
		get("rest/users/managers", (req, res) -> {
			res.type("application/json");
			return g.toJson(userService.getUnemployedManagers());
		});
	}
	
	public static void addNewManager() {
		post("rest/users/add/manager", (req, res) -> {
			res.type("application/json");
			System.out.println(req.body());
			ManagerDTO managerDTO = g.fromJson(req.body(), ManagerDTO.class);
			
			if(userService.checkIfUserExistsForRegister(managerDTO.getUsername()) != null)
				return "USER ALREADY EXISTS";
			
			Gender gen = null;
			if(managerDTO.getGender().equals("Male"))
				gen = Gender.Male;
			else
				gen = Gender.Female;
			
			User manager = new User(
					managerDTO.getUsername(),
					managerDTO.getPassword(),
					managerDTO.getName(),
					managerDTO.getLastname(),
					gen,
					managerDTO.getDob(),
					Role.Manager,
					new MembershipFee(),
					new ArrayList<SportsBuilding>(),
					0,
					new CustomerType(),
					new ArrayList<TrainingHistory>(),
					sportsService.getSportsBuilding(managerDTO.getBuilding()));
			
			userService.addUser(manager);
			return "SUCCESS";
		});
	}
	
	public static void getTrainers() {
		get("rest/users/trainers", (req, res) -> {
			res.type("application/json");
			return g.toJson(userService.getTrainers());
		});
	}
	
	public static void getFacilityUsers(){
		get("rest/users/:facility", (req, res) -> {
			res.type("application/json");
			String facilityName = req.params("facility");
			return g.toJson(userService.getFacilityUsers(facilityName));
		});
	}
}
