/*
 -----------------------------------------------------------------------------------
 Laboratoire : PRO
 Fichier     : Data.java
 Auteur(s)   : Jean AYOUB
 Date        : 12 avr. 2016
 But         : 
 Remarque(s) :
 Compilateur : jdk 1.8.0_60
 -----------------------------------------------------------------------------------
 */
package db;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;



/**
 * Class representing the data type.
 *
 * @author Jean AYOUB
 * @date 8 avr. 2016
 * @version 1.1
 */
public class Data {
	
	
	/**
	 * Constructor.
	 * 
	 * @param dateAndTime
	 * @param value
	 */
	public Data (LocalDateTime dateAndTime, double value) {
		this.dateAndTime = dateAndTime;
		this.value       = value;
		
	}
	

	/**
	 * Constructor.
	 * 
	 * @param year
	 * @param month
	 * @param dayOfMonth
	 * @param hour
	 * @param minute
	 * @param value
	 */
	public Data (int year, int month, int dayOfMonth, int hour, 
										int minute, double value) {
		this.dateAndTime = LocalDateTime.of(year, month, dayOfMonth, hour, minute);
		this.value       = value;
	}
	
	
	
	/**
	 * Constructor.
	 * 
	 * @param year
	 * @param month
	 * @param dayOfMonth
	 * @param hour
	 * @param minute
	 * @param second
	 * @param value
	 */
	public Data (int year, int month, int dayOfMonth, 
					int hour, int minute, int second, double value) {
		this(LocalDateTime.of(year, month, dayOfMonth, hour, minute, second), value);
	}
	
	
	
	/**
	 * Constructor.
	 * 
	 * @param year
	 * @param month
	 * @param dayOfMonth
	 * @param hour
	 * @param minute
	 * @param second
	 * @param nanoOfSecond
	 * @param value
	 */
	public Data (int year, int month, int dayOfMonth, 
			int hour, int minute, int second, int nanoOfSecond, double value) {
		this(LocalDateTime.of(year, month, dayOfMonth, hour, minute, second, nanoOfSecond), value);
}
	
	
	
	/**
	 * Returns the DateTime attribut.
	 *
	 * @return date and time
	 */
	public LocalDateTime getDateTime() {
		return this.dateAndTime;
	}
	
	
	/**
	 * Returns the value attribut. 
	 *
	 * @return numerical value
	 */
	public double getValue() {
		return this.value;
	}
	
	
	/**
	 * Returns the time of the data.
	 *
	 * @return Time
	 */
	public String getTime() {
		String s = dateAndTime.toLocalTime().toString();
		return s;
	}
	
	
	/**
	 * Returns the date of the data.
	 *
	 * @return Date
	 */
	public String getDate() {
		String s = dateAndTime.toLocalDate().toString();
		return s;
	}
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return dateAndTime.toString() + ", " + value;
	}
	
	
	
	public enum Sensor {
		
		TEMPERATURE("temperatureSensor"),
		HUMIDITY("humiditySensor"),
		PRESSURE("pressureSensor"),
		RADIANCY("radiancySensor"),
		RAIN("rainSensor"),
		AIR_QUALITY("airQualitySensor");
		
		private Sensor (String id) {
			this.id = id;
		}
		
		public String toString() {
			return this.id;
		}
		
		private final String id; 
	}
	
	
	
	
	/**
	 * 
	 *
	 * @param sensor
	 * @return the last data in the db for the selected sensorID.
	 */
	public static Data getLastData(Sensor sensor) {
		final String QUERY   = "CALL lastCapturedValue('" + sensor + "');";
		DBConnection dbConn  = null ; 
		Double 		 value   = 0.;
		Date   		 date    = null;
		Time   		 time    = null;
		int    		 year    = 0, 
					 month   = 0, 
					 day	 = 0, 
					 hours   = 0, 
					 minutes = 0, 
					 seconds = 0;
		
		try{
			dbConn = new DBConnection();
			
			ResultSet result = dbConn.executeQuery(QUERY);
			if (result.next()){
				value = result.getDouble("value_");
				date  = result.getDate  ("date_");
				time  = result.getTime  ("time_");
				
				// TEST DB !!!!!
				//System.out.println(value + " : " + date + ":" + time);
				String tempString = date.toString();
				year              = Integer.parseInt(tempString.substring(0, 4));
				if (Integer.parseInt(tempString.substring(5,6)) == 1)
					month = Integer.parseInt(tempString.substring(5,7));
				else
					month = Integer.parseInt(String.valueOf(tempString.substring(6,7)));
				
				
				if (Integer.parseInt(String.valueOf(tempString.substring(8,9))) == 1)
					day = Integer.parseInt(tempString.substring(8,10));
				else
					day = Integer.parseInt(String.valueOf(tempString.substring(9,10)));
				
				
				tempString        = time.toString();
				
				if (Integer.parseInt(tempString.substring(0, 1)) == 0)
					hours = Integer.parseInt(tempString.substring(1, 2));
				else
					hours = Integer.parseInt(tempString.substring(0, 2));

				
				if (Integer.parseInt(tempString.substring(3, 4)) == 0)
					minutes = Integer.parseInt(tempString.substring(4, 5));
				else
					minutes = Integer.parseInt(tempString.substring(3, 5));
				
				
				if (Integer.parseInt(tempString.substring(6, 7)) == 0)
					seconds = Integer.parseInt(tempString.substring(7, 8));
				else
					seconds = Integer.parseInt(tempString.substring(6, 8));	
			}
		}
		catch (SQLException se){
		      System.out.println("An error occurated during the execution!");
		      se.printStackTrace();
		     }

		     finally {
		      if (dbConn != null)
		       dbConn.close();
		     }
		
		System.out.println("Sensor " + sensor + new Data(year, month, day, hours, minutes, seconds, value));
		return new Data(year, month, day, hours, minutes, seconds, value);
	}
	
	
	
	/**
	 * 
	 *
	 * @param sensor
	 * @param from
	 * @param to
	 * @return the data list for the selected interval.
	 */
	public static ArrayList<Data> getIntervalData (Sensor 		 sensor,
												   LocalDateTime from, 
												   LocalDateTime to) {
		return null;
	}
	
	
	
		
	/** Date and time of the data */
	private LocalDateTime dateAndTime;
	/** Value of the data */
	private double 		  value;
}
