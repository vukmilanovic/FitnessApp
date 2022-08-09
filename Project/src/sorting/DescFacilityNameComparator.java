package sorting;

import java.util.Comparator;

import beans.TrainingHistory;

public class DescFacilityNameComparator implements Comparator<TrainingHistory> {

	@Override
	public int compare(TrainingHistory o1, TrainingHistory o2) {
		return o2.getTraining().getSportsBuilding().getName().compareToIgnoreCase(o1.getTraining().getSportsBuilding().getName());
	}

}
