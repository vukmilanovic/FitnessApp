package sorting;

import java.util.Comparator;

import beans.User;

public class LexicographicDescUsernameComparator implements Comparator<User> {

	@Override
	public int compare(User o1, User o2) {
		return o2.getUsername().compareToIgnoreCase(o1.getUsername());
	}

}
