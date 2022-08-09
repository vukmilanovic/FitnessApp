package DTO;


public class CommentDTO {

	private String customerUsername;
	private String commentText;
	private int rating;
	private String facilityName;
	
	public CommentDTO() {}
	
	public CommentDTO(String customerUsername, String commentText, int rating, String facilityName) {
		super();
		this.customerUsername = customerUsername;
		this.commentText = commentText;
		this.rating = rating;
		this.facilityName = facilityName;
	}

	public String getCustomerUsername() {
		return customerUsername;
	}

	public void setCustomerUsername(String customerUsername) {
		this.customerUsername = customerUsername;
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

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	
}
