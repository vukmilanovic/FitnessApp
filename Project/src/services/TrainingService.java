package services;

import java.util.Collection;

import DAO.TrainingDAO;
import beans.Training;

public class TrainingService {
	
	private TrainingDAO trainings = TrainingDAO.getInstance();
	
	public Training addTraining(Training training) {
		return this.trainings.addTraining(training);
	}
	
	public void editTraining(Training newTraining) {
		this.trainings.editTraining(newTraining);
	}
	
	public Collection<Training> getTrainings(Collection<String> trainingNames){
		if(trainingNames == null)
			return null;
		
		return this.trainings.getTrainings(trainingNames);
	}
}
