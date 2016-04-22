/*
 -----------------------------------------------------------------------------------
 Laboratoire : 
 Fichier     : Radiancy.java
 Auteur(s)   : Jean AYOUB
 Date        : 22 avr. 2016
 But         : 
 Remarque(s) :
 Compilateur : jdk 1.8.0_60
 -----------------------------------------------------------------------------------
 */
package db.sensors;

import java.time.LocalDateTime;
import java.util.ArrayList;

import db.Data;

/**
 * Class.
 *
 * @author Jean AYOUB
 * @date 22 avr. 2016
 * @version 1.0
 */
public class Radiancy extends Data {

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
	public Radiancy(int year, int month, int dayOfMonth, int hour, int minute, double value) {
		super(year, month, dayOfMonth, hour, minute, value);
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
	public Radiancy(int year, int month, int dayOfMonth, int hour, int minute, int second, double value) {
		super(year, month, dayOfMonth, hour, minute, second, value);
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
	public Radiancy(int year, int month, int dayOfMonth, int hour, int minute, int second, int nanoOfSecond,
			double value) {
		super(year, month, dayOfMonth, hour, minute, second, nanoOfSecond, value);
	}

	/**
	 * Constructor.
	 * 
	 * @param dateAndTime
	 * @param value
	 */
	public Radiancy(LocalDateTime dateAndTime, double value) {
		super(dateAndTime, value);
	}
	
	
	/**
	 * 
	 *
	 * @return the last data in the db.
	 */
	public static Data getLastData() {

		return null;
	}
	
	
	/**
	 * 
	 *
	 * @param from
	 * @param to
	 * @return the data list for the selected interval.
	 */
	public static ArrayList<Data> getIntervalData (LocalDateTime from, 
												   LocalDateTime to) {
		return null;
	}

}
