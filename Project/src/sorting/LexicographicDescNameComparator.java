package sorting;

import java.util.Comparator;

import beans.User;

public class LexicographicDescNameComparator implements Comparator<User> {

	@Override
	public int compare(User o1, User o2) {
		return o2.getName().compareToIgnoreCase(o1.getName());
	}
	
}
