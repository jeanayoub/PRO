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

import java.time.LocalDateTime;


/**
 * Class.
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
	 * Returns the time of the data
	 *
	 * @return Time
	 */
	public String getTime() {
		String s = dateAndTime.toLocalTime().toString();
		return s;
	}
	
	
	/**
	 * Returns the date of the data
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

	
	private LocalDateTime dateAndTime;
	private double 		  value;
}
