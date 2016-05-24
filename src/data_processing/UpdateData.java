/*
 -----------------------------------------------------------------------------------
 Laboratoire : 
 Fichier     : UpdateData.java
 Auteur(s)   : Jean AYOUB
 Date        : 22 avr. 2016
 But         : 
 Remarque(s) :
 Compilateur : jdk 1.8.0_60
 -----------------------------------------------------------------------------------
 */
package data_processing;

import java.sql.SQLException;
import java.util.Timer;

import db.DBConnection;
import db.Data;
import db.Data.Sensor;
import gui.MainWindow;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.util.Duration;
import javafx.animation.KeyFrame;

/**
 * Class.
 *
 * @author Jean AYOUB
 * @date 22 avr. 2016
 * @version 1.0
 */
public class UpdateData {
	
	
	/**
     * Declaration and definition of all the Images
     */
	
	
	
	/**  */
	final Image imSunny 	  = new Image(ResourceLoader.load("meteoImages/imSunny.png"));
    /**  */
    final Image imSunnyCloudy = new Image(ResourceLoader.load("meteoImages/imSunnyCloudy.png"));
    /**  */
    final Image imCloudy	  = new Image(ResourceLoader.load("meteoImages/imCloudy.png"));
    /** */
    final Image imRainLight   = new Image(ResourceLoader.load("meteoImages/imRainLight.png"));
    /**  */
    final Image imRainHeavy   = new Image(ResourceLoader.load("meteoImages/imRainHeavy.png"));
    /**  */
    final Image imSnow 		  = new Image(ResourceLoader.load("meteoImages/imSnow.png"));
    /**  */
    final Image imNight		  = new Image(ResourceLoader.load("meteoImages/imNight.png"));
    /**  */
    final Image imNightRain	  = new Image(ResourceLoader.load("meteoImages/imNightRain.png"));
    /**  */
    final Image imNightSnow	  = new Image(ResourceLoader.load("meteoImages/imNightSnow.png"));
    
     
    
 	
	
	/**
	 * Constructor.
	 * 
	 * @param period1
	 * @param period2
	 * @throws SQLException 
	 */
	public UpdateData (long period_1, long period_2) {

		timeline = new Timeline(
			      new KeyFrame(Duration.millis(period_1), new EventHandler() {
			        @Override public void handle(Event event) {
			        	
			        	try {
			    			DBConnection dbConn = 
			    				new DBConnection(MainWindow.getConnectionForm());
			    			connectionError = false;
			    			System.out.println("connection successful");
			    		} catch (SQLException e) {
			    			connectionError = true;
			    			System.out.println("connection failed !");
			    			return;
			    		}
			    		
			        		checkLatestData();

			        }
			      }),  
			      new KeyFrame(Duration.millis(period_2))
			    );
		 timeline.setCycleCount(Timeline.INDEFINITE);
		 timeline.play();
		 
	}
	
	
	/**
	 * 
	 *
	 */
	private void checkLatestData () {
		
		
		Data actualTemperature = Data.getLastData(
				Sensor.TEMPERATURE);
		Data actualHumidity    = Data.getLastData(
				Sensor.HUMIDITY);
		Data actualPressure    = Data.getLastData(
				Sensor.PRESSURE);
		Data actualAirQuality  = Data.getLastData(
				Sensor.AIR_QUALITY);
		Data actualRadiancy    = Data.getLastData(
				Sensor.RADIANCY);
		
		
		double actualTemperatureValue = actualTemperature.getValue();
		double actualHumidityValue    = actualHumidity.getValue();
		double actualPressureValue    = actualPressure.getValue();
		//double actualAirQualityValue  = actualAirQuality.getValue();
		//double actualRadiancyValue    = actualRadiancy.getValue();
		
		
		if (!Double.valueOf(pressure).equals(actualPressureValue)) {
			MainWindow.updatePressureGauge(actualPressureValue);
			pressure = actualPressureValue;
		}
			
		if (!Double.valueOf(humidity).equals(actualHumidityValue)) {	
			MainWindow.updatePbHumidity(actualHumidityValue);
			humidity = actualHumidityValue;
		}
		
		if (!Double.valueOf(temperature).equals(actualTemperatureValue)) {
			MainWindow.updateLcdTemperature(actualTemperatureValue);
			temperature = actualTemperatureValue;
		}
		

		MainWindow.updateLcsTemperature(actualTemperature);
		MainWindow.updateLcsHumidity   (actualHumidity);
		MainWindow.updateLcsPressure   (actualPressure);
		MainWindow.updateLcsAirQuality (actualAirQuality);
				
		
		
		/**
		 * If it's raining or snowing 
		 */
		if (Data.getLastData(Sensor.RAIN).getValue() == 1) {
			/**
			 * If it's day time
			 */
			if (Data.getLastData(Sensor.RADIANCY).getValue() > 250) {
				
				/**
				 * If it's raining (depending on the temperature)
				 */
				if (Data.getLastData(Sensor.TEMPERATURE).getValue() >= 0)
					MainWindow.updateImageView(imRainLight);
				/**
				 * Else it's snowing (below 0 degree)
				 */
				else
					MainWindow.updateImageView(imSnow);
			}
					
			
			/**
			 * Else it's night time
			 */
			else {
				
				/**
				 * If it's raining (depending on the temperature)
				 */
				if (Data.getLastData(Sensor.TEMPERATURE).getValue() >= 0)
					MainWindow.updateImageView(imNightRain);
				/**
				 * Else it's snowing (below 0 degree)
				 */
				else
					MainWindow.updateImageView(imNightSnow);
			}
		}
		
		/**
		 * Else then there is no rain or snow fall
		 */
		else {
			
			/**
			 * If it's day time
			 */
			if (Data.getLastData(Sensor.RADIANCY).getValue() > 250) {
				
				/**
				 * It's extremely sunny
				 */
				if (Data.getLastData(Sensor.RADIANCY).getValue() > 450)
					MainWindow.updateImageView(imSunny);
				/**
				 * It's sunny with few clouds
				 */
				else if (Data.getLastData(Sensor.RADIANCY).getValue() > 300)
					MainWindow.updateImageView(imSunnyCloudy);
				/**
				 * It's cloudy
				 */
				else
					MainWindow.updateImageView(imCloudy);
			}
			
			/**
			 * Then it's night time without any rain or snow fall
			 */
			else
				MainWindow.updateImageView(imNight);
		}
	}
	
	public static boolean getConnectionError() {
		return connectionError;
	}
	
	
	
	/**  */
	public  static Timer   timer = new Timer();
	/** The actual pressure */
	private 	   double  pressure;
	/** The actual humidity */
	private 	   double  humidity;
	/** The actual temperature */
	private 	   double  temperature;
	/**  */
	private static boolean status;
	/**  */
	private static int 	   timeToStop;
	/**  */
	private static boolean connectionError = false;
	/**  */
	private 	   Timeline timeline;
}
