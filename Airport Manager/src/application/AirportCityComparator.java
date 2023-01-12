package application;

import java.util.Comparator;

public class AirportCityComparator extends Object implements Comparator<Airport>{
	
	//Constructor
	public AirportCityComparator() {
	}
	
	@Override
	public int compare(Airport a1, Airport a2) {
		return a1.getCity().compareTo(a2.getCity());
	}

}
