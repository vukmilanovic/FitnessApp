package sorting;

import java.util.Comparator;

import beans.User;

public class LexicographicAscPointsComparator implements Comparator<User> {

	@Override
	public int compare(User o1, User o2) {
		return o1.getNumberOfCollectedPoints() - o2.getNumberOfCollectedPoints();
	}

}
