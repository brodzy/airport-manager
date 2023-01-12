package application;

import java.util.Comparator;

public class AirportStateComparator extends Object implements Comparator<Airport>{
	
	//Constructor
	public AirportStateComparator() {
	}
	
	@Override
	public int compare(Airport a1, Airport a2) {
		return a1.getState().compareTo(a2.getState());
	}

}
