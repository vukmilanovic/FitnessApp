package sorting;

import java.util.Comparator;

import beans.User;

public class LexicographicAscNameComparator implements Comparator<User> {

	@Override
	public int compare(User o1, User o2) {
		return o1.getName().compareToIgnoreCase(o2.getName());
	}

}
