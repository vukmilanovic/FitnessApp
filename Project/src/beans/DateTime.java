package beans;

public class DateTime {

	private int day;
	private int month;
	private int year;
	private int hours;
	private int minutes;
	
	public DateTime() {}
	
	public DateTime(int day, int month, int year, int hours, int minutes) {
		super();
		this.day = day;
		this.month = month;
		this.year = year;
		this.hours = hours;
		this.minutes = minutes;
	}
	
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	
	public boolean isAfter(DateTime dateTime) {
		if(this.year > dateTime.year) {
			return true;
		} else if(this.year < dateTime.year) {
			return false;
		} else {
			if(this.month > dateTime.month) {
				return true;
			} else if (this.month < dateTime.month) {
				return false;
			} else {
				if(this.day > dateTime.day) {
					return true;
				} else if(this.day < dateTime.day) {
					return false;
				} else {
					if(this.hours > dateTime.hours) { 
						return true;
					} else if (this.hours < dateTime.hours) {
						return false;
					} else {
						if(this.minutes > dateTime.minutes) {
							return true;
						} else {
							return false;
						}
					}
				}
			}
		}
	}

	public boolean isBefore(DateTime dateTime) {
		if(this.year < dateTime.year) {
			return true;
		} else if(this.year > dateTime.year) {
			return false;
		} else {
			if(this.month < dateTime.month) {
				return true;
			} else if (this.month > dateTime.month) {
				return false;
			} else {
				if(this.day < dateTime.day) {
					return true;
				} else if(this.day > dateTime.day) {
					return false;
				} else {
					if(this.hours < dateTime.hours) { 
						return true;
					} else if (this.hours > dateTime.hours) {
						return false;
					} else {
						if(this.minutes < dateTime.minutes) {
							return true;
						} else {
							return false;
						}
					}
				}
			}
		}
	}
	
	@Override
	public boolean equals(Object dateTime) {
		
		if(dateTime == this) {
			return true;
		}
		
		if(!(dateTime instanceof DateTime)) {
			return false;
		}
		
		DateTime dt = (DateTime) dateTime;
		
		return Integer.compare(year, dt.year) == 0 
				&& Integer.compare(month, dt.month) == 0
				&& Integer.compare(day, dt.day) == 0
				&& Integer.compare(hours, dt.hours) == 0
				&& Integer.compare(minutes, dt.minutes) == 0;
		
	}
	
	@Override
	public String toString() {
		return String.valueOf(day) + "." + String.valueOf(month) + "." + String.valueOf(year) + "." + " " + String.valueOf(hours) + ":" + String.valueOf(minutes);
	}
	
}
