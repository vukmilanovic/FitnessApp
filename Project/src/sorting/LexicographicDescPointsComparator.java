package sorting;

import java.util.Comparator;

import beans.User;

public class LexicographicDescPointsComparator implements Comparator<User> {

	@Override
	public int compare(User o1, User o2) {
		//??
		return o2.getNumberOfCollectedPoints() - o1.getNumberOfCollectedPoints();
	}

}
