package beans;

public class TrainingHistory {

	private int id;
	private DateTime dateAndTimeOfCheckIn;
	private Training training;
	private User customer;
	private User coach;
	
	public TrainingHistory() {}
	
	public TrainingHistory(int id, DateTime dateAndTimeOfCheckIn, Training training, User customer, User coach) {
		super();
		this.id = id;
		this.dateAndTimeOfCheckIn = dateAndTimeOfCheckIn;
		this.training = training;
		this.customer = customer;
		this.coach = coach;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public DateTime getDateAndTimeOfCheckIn() {
		return dateAndTimeOfCheckIn;
	}

	public void setDateAndTimeOfCheckIn(DateTime dateAndTimeOfCheckIn) {
		this.dateAndTimeOfCheckIn = dateAndTimeOfCheckIn;
	}

	public Training getTraining() {
		return training;
	}

	public void setTraining(Training trainings) {
		this.training = trainings;
	}

	public User getCustomer() {
		return customer;
	}

	public void setCustomer(User customer) {
		this.customer = customer;
	}

	public User getCoach() {
		return coach;
	}

	public void setCoach(User coach) {
		this.coach = coach;
	}
	
}
