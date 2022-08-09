package sorting;

import java.time.LocalDate;
import java.util.Comparator;

import beans.DateTime;
import beans.TrainingHistory;

public class DescDateComparator implements Comparator<TrainingHistory> {

	@Override
	public int compare(TrainingHistory o1, TrainingHistory o2) {
		DateTime dt1 = o1.getDateAndTimeOfCheckIn();
		DateTime dt2 = o2.getDateAndTimeOfCheckIn();
		
		LocalDate d1 = LocalDate.of(dt1.getYear(), dt1.getMonth(), dt1.getDay());
		LocalDate d2 = LocalDate.of(dt2.getYear(), dt2.getMonth(), dt2.getDay());
		
		return d2.compareTo(d1);
	}

}
