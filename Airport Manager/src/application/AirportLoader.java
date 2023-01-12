package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AirportLoader extends Object {
	//Constructor
	public AirportLoader() {
	}
	
	public static Map<String, Airport> getAirportMap(File file){
		Map<String, Airport> map = new HashMap<String, Airport>();
		
		//Extracting variables from text using split to make Airport object and adding to map
		try {
			Scanner input = new Scanner(file);
			while (input.hasNext()) {
				String line = input.nextLine();
				String[] tokens = line.split("	"); 
				String code = tokens[0];
				double latitude = Double.parseDouble(tokens[1]);
				double longitude = Double.parseDouble(tokens[2]);
				String city = tokens[3];
				String state = tokens[4];
				
				Airport airport = new Airport(code, latitude, longitude, city, state);
				
				map.put(code, airport);
			}
			input.close();
		} 
		
		catch (FileNotFoundException e) {
			System.out.println("File not found!");
		}
		
		return map;
	}

}
