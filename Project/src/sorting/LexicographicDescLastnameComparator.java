package sorting;

import java.util.Comparator;

import beans.User;

public class LexicographicDescLastnameComparator implements Comparator<User> {

	@Override
	public int compare(User o1, User o2) {
		return o2.getLastname().compareToIgnoreCase(o1.getLastname());
	}

}
