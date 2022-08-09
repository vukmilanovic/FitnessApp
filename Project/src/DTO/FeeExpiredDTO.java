package DTO;

public class FeeExpiredDTO {

	private String token;
	private String comment;
	
	public FeeExpiredDTO() {}
	
	public FeeExpiredDTO(String token, String comment) {
		this.token = token;
		this.comment = comment;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
