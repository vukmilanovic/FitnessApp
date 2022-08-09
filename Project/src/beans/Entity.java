package beans;

public class Entity {

	private boolean deleted;
	
	public Entity() {}
	
	public Entity(boolean deleted) {
		this.deleted = deleted;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
}
