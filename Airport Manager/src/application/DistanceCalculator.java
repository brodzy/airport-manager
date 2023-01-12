package application;
/**
 * Contains a static method to get the distance between two pairs of latitude
 * and longitude and this class has been provided to you.
 * @author dgibson
 *
 */
public class DistanceCalculator {

	private final static double conversionRateDR = 0.017453;
	private final static double conversionRateKM = 0.621371;
	private final static double radiusEarth = 6371.01;

	/**
	 * Returns the distance between two pairs of latitude & longitude
	 * @param lat1
	 * @param long1
	 * @param lat2
	 * @param long2
	 * @return
	 */
	public static double getDistance(double lat1, double long1, double lat2, double long2){

		double lat1R, long1R, lat2R, long2R;

		lat1R = convertToRadians(lat1);
		lat2R = convertToRadians(lat2);

		long1R = convertToRadians(long1);
		long2R = convertToRadians(long2);

		double distance = Math.acos(Math.sin(lat1R) *
				Math.sin(lat2R) +
				Math.cos(lat1R) * Math.cos(lat2R) *
				Math.cos(long2R - long1R)) * radiusEarth;
		return convertToMiles(distance);
	}
	
	private static double convertToMiles(double distance){
		return distance * conversionRateKM;
	}

	private static double convertToRadians(double degree){
		return degree * conversionRateDR;
	}
}
