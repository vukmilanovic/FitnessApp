package beans;

import enums.CommentStatus;

public class Comment extends Entity {
	
	private int id;
	private User customer;
	private SportsBuilding sportsBuilding;
	private String commentText;
	private int rating;
	private CommentStatus status;
	
	public Comment() {
		super.setDeleted(false);
	}

	public Comment(int id, User customer, SportsBuilding sportsBuilding, String commentText, int rating, CommentStatus status) {
		super();
		super.setDeleted(false);
		this.id = id;
		this.customer = customer;
		this.sportsBuilding = sportsBuilding;
		this.commentText = commentText;
		this.rating = rating;
		this.status = status;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CommentStatus getStatus() {
		return status;
	}

	public void setStatus(CommentStatus status) {
		this.status = status;
	}

	public User getCustomer() {
		return customer;
	}

	public void setCustomer(User customer) {
		this.customer = customer;
	}

	public SportsBuilding getSportsBuilding() {
		return sportsBuilding;
	}

	public void setSportsBuilding(SportsBuilding sportsBuilding) {
		this.sportsBuilding = sportsBuilding;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
}
