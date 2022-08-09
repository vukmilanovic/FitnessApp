package sorting;

import java.util.Comparator;

import beans.User;

public class LexicographicAscLastnameComparator implements Comparator<User> {

	@Override
	public int compare(User o1, User o2) {
		return o1.getLastname().compareToIgnoreCase(o2.getLastname());
	}

}
