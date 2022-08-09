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

import beans.Address;
import beans.Comment;
import beans.DateTime;
import beans.Location;
import beans.SportsBuilding;
import beans.User;
import enums.BuildingContent;
import enums.BuildingType;
import enums.CommentStatus;
import utils.GlobalPaths;

public class CommentDAO {

	private static CommentDAO instance = null;
	
	public static CommentDAO getInstance() {
		if(instance == null) {
			instance = new CommentDAO();
		}
		
		return instance;
	}
	
	private Gson g = new Gson();
	private HashMap<Integer, Comment> comments;
	private HashMap<String, User> users;
	
	public CommentDAO() {
		Load();
//		String json2 = "";
//	
//		this.comments = new HashMap<Integer, Comment>();
//		
//		try {
//			BufferedReader reader = new BufferedReader(new FileReader(new File(GlobalPaths.usersDBPath)));
//			String currentLine;
//			while((currentLine = reader.readLine()) != null)
//				json2 += currentLine;
//		} catch (IOException e) {
//			return;
//		}
//		
//		this.users = g.fromJson(json2, new TypeToken<HashMap<String, User>>(){}.getType());
//		
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
//		User customer = this.users.get("ckepatroni");
//		User customer2 = this.users.get("spikaLudi");
//		
//		Comment com1 = new Comment(1, customer, b1, "comment1", 8, CommentStatus.Pending);
//		Comment com2 = new Comment(2, customer2, b2, "comment2", 10, CommentStatus.Accepted);
//		Comment com3 = new Comment(3, customer2, b2, "comment3", 6, CommentStatus.Pending);
//		
//		this.comments.put(com1.getId(), com1);
//		this.comments.put(com2.getId(), com2);
//		this.comments.put(com3.getId(), com3);
//		
//		Save();
	}
	
	public int getNewId() {
		if(comments.isEmpty())
            return 1;
        int maxId = -1;
        for(int id : comments.keySet()) {
            if(maxId < id) {
                maxId = id;
            }
        }
        return maxId+1;
	}
	
	public boolean newComment(Comment comment) {
		for(int id : comments.keySet()) {
			if(comment.getId() == id) {
				return false;
			}
		}
		
		this.comments.put(comment.getId(), comment);
		
		Save();
		return true;
	}
	
	public boolean acceptComment(int commentID) {
		for (Comment comment : getAllComments()) {
			if(comment.getId() == commentID) {
				comment.setStatus(CommentStatus.Accepted);
				Save();
				return true;
			}
		}
		return false;
	}
	
	public boolean rejectComment(int commentID) {
		for(Comment comment : getAllComments()) {
			if(comment.getId() == commentID) {
				comment.setStatus(CommentStatus.Rejected);
				Save();
				return true;
			}
		}
		return false;
	}
	
	public void editComment(int id, Comment comment) {
		this.comments.put(id, comment);
		Save();
	}
	
	public void deleteComment(int id) {
		for (Comment comment : this.comments.values()) {
			if(comment.getId() == id) {
				comment.setDeleted(true);
				break;
			}
		}
		Save();
	}
	
	public Collection<Comment> getAllComments() {
		Collection<Comment> comments = new ArrayList<Comment>();
		for (Comment comment : this.comments.values()) {
			if(comment.isDeleted() == false) {
				comments.add(comment);
			}
		}
		return comments;
	}
	
	private void Save() {
		String json = g.toJson(comments);
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(GlobalPaths.commentsDBPath)));
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
			BufferedReader reader = new BufferedReader(new FileReader(new File(GlobalPaths.commentsDBPath)));
			String currentLine;
			while((currentLine = reader.readLine()) != null)
				json += currentLine;
		}
		catch (IOException e) {
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
		if(g.fromJson(json, new TypeToken<HashMap<Integer, Comment>>(){}.getType()) == null) { System.out.println("ddd");
			this.comments = new HashMap<Integer, Comment>();
			this.users = g.fromJson(json2, new TypeToken<HashMap<String, User>>(){}.getType());
		} else { 
			this.comments = g.fromJson(json, new TypeToken<HashMap<Integer, Comment>>(){}.getType());
			this.users = g.fromJson(json2, new TypeToken<HashMap<String, User>>(){}.getType());
		}
		
	}
}
