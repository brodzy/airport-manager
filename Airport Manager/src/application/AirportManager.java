package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AirportManager extends Object{
	//Instance Variables
	private Map<String,Airport> airports = new HashMap<String,Airport>();
	
	//Constructor
	public AirportManager(Map<String,Airport> airports) {
		this.airports = airports;
	}
	
	//Methods
	public double getDistanceBetween(String code1, String code2) {
		double real = 0;
		
		//Checks if codes are in keys
		if (airports.keySet().contains(code1) && airports.keySet().contains(code2)) {
			Airport a = airports.get(code1);
			Airport b = airports.get(code2);
			double lat = a.getLatitude();
			double lon = a.getLongitude();
			double lat1 = b.getLatitude();
			double lon1 = b.getLongitude();
			
			//Calc distance
			real = DistanceCalculator.getDistance(lat,lon,lat1,lon1);
		}
		
		return real;
	}
	
	public double getDistanceBetween(Airport airport1, Airport airport2) {
		//Gets lats and lons
		double lat = airport1.getLatitude();
		double lon = airport1.getLongitude();
		double lat1 = airport2.getLatitude();
		double lon1 = airport2.getLongitude();
		
		//Calc distance
		return DistanceCalculator.getDistance(lat,lon,lat1,lon1);
	}
	
	public Airport getAirport(String code) {
		return airports.get(code);
	}
	
	public Airport getAirportClosestTo(String code) {
		double minDist = Double.MAX_VALUE;
		Airport closest = null;
		
		//Checks if code is in keys
		if (airports.keySet().contains(code)) {
			Airport a = airports.get(code);
			double lat = a.getLatitude();
			double lon = a.getLongitude();
			
			//Iterate through values in map
			Iterator<Airport> iter = airports.values().iterator();
			while(iter.hasNext()) {
				Airport b = iter.next();
				double lat1 = b.getLatitude();
				double lon1 = b.getLongitude();
				
				//Calc distance
				double real = DistanceCalculator.getDistance(lat,lon,lat1,lon1);
				
				//Updates to get the smallest distance while also checking it is not the same airport
				if (real < minDist) {
					if(!a.equals(b)) {
						minDist = real;
						closest = b;
					}
				}
			}
		}
		
		return closest;
	}
	
	public List<Airport> getAirportsWithin(String code, double withinDist){
		List<Airport> airports1 = new ArrayList<Airport>();
		
		//Checks if code is in keys
		if (airports.keySet().contains(code)) {
			Airport a = airports.get(code);
			double lat = a.getLatitude();
			double lon = a.getLongitude();
			
			//Iterate through values in map
			Iterator<Airport> iter = airports.values().iterator();
			while(iter.hasNext()) {
				Airport b = iter.next();
				double lat1 = b.getLatitude();
				double lon1 = b.getLongitude();
				
				//Calc distance
				double real = DistanceCalculator.getDistance(lat,lon,lat1,lon1);
				
				//Same code as method above except in if statement pass withinDist and add it to list after
				if (real < withinDist) {
					if(!a.equals(b)) {
					airports1.add(b);
					}
				}
			}
		}
		
		return airports1;
	}
	
	public List<Airport> getAirportsWithin(double withinDist, double lat, double lon){
		List<Airport> airports1 = new ArrayList<Airport>();
		
		//Check if valid lat and lon
		if (lat < 0 || lat > 90 || lon < 0 || lon > 180) {
			return null;
		}
		else { //Iterate through values in map
			Iterator<Airport> iter = airports.values().iterator();
			while(iter.hasNext()) {
				Airport b = iter.next();
				double lat1 = b.getLatitude();
				double lon1 = b.getLongitude();
				
				//Calc distance
				double real = DistanceCalculator.getDistance(lat,lon,lat1,lon1);
				
				//Same code as above except we don't test equals since we are at a certain lat and lon
				if (real < withinDist) {
					airports1.add(b);
				}
			}
	
		}
		return airports1;
	}

	public List<Airport> getAirportsWithin(String code1, String code2, double withinDist){
		//Create two lists by calling getAirportsWithin passing code and withinDist
		List<Airport> airports1 = getAirportsWithin(code1, withinDist);
		List<Airport> airports2 = getAirportsWithin(code2, withinDist);
		
		//Initialize new list with airports1 and grabs intersection
		List<Airport> airports3 = new ArrayList<Airport>(airports1);
		airports3.retainAll(airports2);
		
		return airports3;
	}
	
	public List<Airport> getAirportsByCity(String city){
		List<Airport> airports1 = new ArrayList<Airport>();
		
		//Iterate through values in map, extract city, compare using equals, add to list
		Iterator<Airport> iter = airports.values().iterator();
		while(iter.hasNext()) {
			Airport a = iter.next();
			String city1 = a.getCity();
			
			if (city.equals(city1)) {
				airports1.add(a);
			}
		}
		
		return airports1;
		
	}
	
	public List<Airport> getAirportsByCityState(String city, String state){
		List<Airport> airports1 = new ArrayList<Airport>();
		
		//Iterate through values in map, extract city and state, compare using equals, add to list
		Iterator<Airport> iter = airports.values().iterator();
		while(iter.hasNext()) {
			Airport a = iter.next();
			String city1 = a.getCity();
			String state1 = a.getState();
			
			if (city.equals(city1) && state.equals(state1)) {
				airports1.add(a);
			}
		}
		
		return airports1;
	}
	
	public List<Airport> getNWSNamedAirports(){
		List<Airport> airports1 = new ArrayList<Airport>();
		
		//Iterate through values in map, extract third character, compare using equals, add to list
		Iterator<Airport> iter = airports.values().iterator();
		while(iter.hasNext()) {
			Airport a = iter.next();
			String third = a.getCode().substring(2);
			
			if (third.equals("X")) {
				airports1.add(a);
			}
		}
		
		return airports1;
	}
	
	public List<Airport> getAirportsSortedBy(String sortType){
		List<Airport> airports1 = new ArrayList<>(airports.values());
		
		//Sorting by either city or state using its respective comparator
		if (sortType.equals("city")) {
			AirportCityComparator cityComp = new AirportCityComparator();
			Collections.sort(airports1, cityComp);
		}
		
		else if (sortType.equals("state")) {
			AirportStateComparator stateComp = new AirportStateComparator();
			Collections.sort(airports1, stateComp);
		}
			
		else {
			return null;
		}
		
		return airports1;
	}
	
	public List<Airport> getAirportsSortedByH(String sortType, List<Airport> airports){
		//Sorting by either city or state using its respective comparator
		if (sortType.equals("city")) {
			AirportCityComparator cityComp = new AirportCityComparator();
			Collections.sort(airports, cityComp);
		}
		
		else if (sortType.equals("state")) {
			AirportStateComparator stateComp = new AirportStateComparator();
			Collections.sort(airports, stateComp);
		}
			
		else {
			return null;
		}
		
		return airports;
	}
	
	public List<Airport> getAirports(){
		List<Airport> airports1 = new ArrayList<Airport>(airports.values());
		return airports1;
	}
	
	public List<Airport> getAirportsClosestTo(String code, int num){
		ArrayList<Airport> airports1 = new ArrayList<Airport>();
		
		//Runs getAirportClosestTo method num times while adding it to new list, also removes from map so doens't return itself each time
		for(int i=0; i < num; i++) {
			Airport a = getAirportClosestTo(code);
			airports1.add(a);
			airports.remove(a.getCode());
		}
		
		return airports1;
		
	}
	
}