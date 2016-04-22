/*
 -----------------------------------------------------------------------------------
 Laboratoire : PRO
 Fichier     : Temperature.java
 Auteur(s)   : Jean AYOUB
 Date        : 21 avr. 2016
 But         : 
 Remarque(s) :
 Compilateur : jdk 1.8.0_60
 -----------------------------------------------------------------------------------
 */
package db.sensors;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

import db.DBConnection;
import db.Data;

/**
 * Class.
 *
 * @author Jean AYOUB
 * @date 21 avr. 2016
 * @version 1.0
 */
public class Temperature extends Data{

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
	public Temperature(int year, int month, int dayOfMonth, int hour, int minute, double value) {
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
	public Temperature(int year, int month, int dayOfMonth, int hour, int minute, int second, double value) {
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
	public Temperature(int year, int month, int dayOfMonth, int hour, int minute, int second, int nanoOfSecond,
			double value) {
		super(year, month, dayOfMonth, hour, minute, second, nanoOfSecond, value);
	}

	/**
	 * Constructor.
	 * 
	 * @param dateAndTime
	 * @param value
	 */
	public Temperature(LocalDateTime dateAndTime, double value) {
		super(dateAndTime, value);
	}

	
	
	/**
	 * 
	 *
	 * @return the last data in the db.
	 */
	public static Data getLastData() {
		final String QUERY = "CALL derniereValeurCaptee('Capteur de soleil');";
		DBConnection dbConn = null ; 
		Double value = 0.;
		Date date = null;
		Time time = null;
		LocalDateTime ldt = null;
		try{
			dbConn = new DBConnection();
			//final PreparedStatement PS = dbConn.prepareStatement(QUERY);
			ResultSet result = dbConn.executeQuery(QUERY);
			if (result.next()){
				value = result.getDouble("mesure");
				date = result.getDate("dates");
				time = result.getTime("heure");
				System.out.println(value + " : " + date + ":" + time);
				//System.out.println(dateTime);
				//ldt = LocalDateTime.ofInstant(dateTime.toInstant(), ZoneId.systemDefault());
				//System.out.println("aaaa");
				//System.out.println("ldt :"+ldt);
			}
			//ldt = dateTime.toInstant().atZone(ZoneId.of("ECT")).toLocalDateTime();
			//System.out.println("ldt :"+ldt);
		
		}
		catch (SQLException se){
		      System.out.println("An error occurated during the execution!");
		      se.printStackTrace();
		     }

		     finally {
		      if (dbConn != null)
		       dbConn.close();
		     }
		
		return (Data)(new Temperature(ldt, value));
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
