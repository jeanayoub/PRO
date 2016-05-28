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

import db.ConnectionForm;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;

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
    final Image imSunnyCloudy = new Image(ResourceLoader.load("meteoImages/imSunnyCloudy.png"));
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
	public UpdateData() {

		timelineRealTime = new Timeline(
			      new KeyFrame(DURATION_TO_START, new EventHandler() {
			        @Override public void handle(Event event) {
			        	
			        	try {
			        		if(lostConnection){
			        			System.out.println("Connection Lost");
			        			MainWindow.updateConnectivityIcon("imInactiv");
			        		}

			        		System.out.println("Je suis UPDATE_1");
			        		
			        		// We try to get a connection if the user filled the connection form
			        		if(MainWindow.getConnectionForm().getFormStatus()){
			        			System.out.println("Je suis UPDATE_2");			        			
			        			MainWindow.setIsConnected(true);
			        			System.out.println("connection successful");
			        			
			        			checkLatestDataRealTime();
			        			//lostConnection = false;
			        			if(lostConnection){
			        				System.out.println("Je passe");
			        				MainWindow.updateConnectivityIcon("imActiv");
			        				lostConnection = false;
			        			}
			        		}
			        		else{
			        			// Connection is lost for instance disconnection
		        				return;

			        		}

			    		} catch (SQLException e) {
			    			//e.printStackTrace();
			    			System.out.println("IsConnected = "+MainWindow.getIsConnected());
			    			System.out.println("FormStatus  = "+MainWindow.getConnectionForm().getFormStatus());
			    			lostConnection = true;
			    			

			    		}

			    		}	
			      }),  
			      new KeyFrame(DURATION_1_DEFAULT)
			    );
		 timelineRealTime.setCycleCount(Timeline.INDEFINITE);
		 
		 
		 	
		timelineLcs = new TimelineLcs();
		pt 					   = new ParallelTransition();
		  
		timelineLcs.setPeriod(DURATION_TO_START, DURATION_1_DEFAULT);
		 
		pt.getChildren().add(timelineRealTime);
		pt.getChildren().add(timelineLcs.getTimeline());
		
		 
		pt.play();
	}
	
	

	/**
	 * Returns.
	 * @return
	 */
	public static TimelineLcs getTimelineLcs() {
		return timelineLcs;
	}

	
	
	
	
	/**
	 * Returns.
	 * @return
	 */
	public static ParallelTransition getPt() {
		return pt;
	}
	
	
	

	/**
	 * Returns.
	 * @return
	 */
	public static Duration getDurationToStart() {
		return DURATION_TO_START;
	}



	/**
	 * Returns.
	 * @return
	 */
	public static Duration getDuration1Default() {
		return DURATION_1_DEFAULT;
	}



	/**
	 * Returns.
	 * @return
	 */
	public static Duration getDuration2() {
		return DURATION_2;
	}



	/**
	 * Returns.
	 * @return
	 */
	public static Duration getDuration3() {
		return DURATION_3;
	}



	/**
	 * Returns.
	 * @return
	 */
	public static Duration getDuration4() {
		return DURATION_4;
	}



	/**
	 * Returns.
	 * @return
	 */
	public static Duration getDuration5() {
		return DURATION_5;
	}



	/**
	 * Returns.
	 * @return
	 */
	public static Duration getDuration6() {
		return DURATION_6;
	}



	/**
	 * 
	 *
	 * @throws SQLException
	 */
	private void checkLatestDataRealTime() throws SQLException {

		
		iconText = new Text(90, 300, "");
		
		iconText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		
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
		Data actualRain        = Data.getLastData(
				Sensor.RAIN);
		
	
		double actualTemperatureValue = actualTemperature.getValue();
		double actualHumidityValue    = actualHumidity.getValue();
		double actualPressureValue    = actualPressure.getValue();
		double actualRainValue		  = actualRain.getValue();
		double actualRadiancyValue    = actualRadiancy.getValue();
		double actualAirQualityValue  = actualAirQuality.getValue();
		
		
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
		
		if (!Double.valueOf(airQuality).equals(actualAirQualityValue)) {
			MainWindow.updateAirQualityText(actualTemperatureValue);
			airQuality = actualTemperatureValue;
		}
		

		
		/**
		 * If it's raining or snowing 
		 */
		if (actualRainValue == 1) {
			/**
			 * If it's day time
			 */
			if (actualRadiancyValue > 250) {
				
				/**
				 * If it's raining (depending on the temperature)
				 */
				if (Data.getLastData(Sensor.TEMPERATURE).getValue() >= 0){
				if (actualTemperatureValue >= 0)
					MainWindow.updateImageView(imRainLight);
					iconText.setText("Pluie");
					MainWindow.getRootGroup().getChildren().add(iconText);
				}
				/**
				 * Else it's snowing (below 0 degree)
				 */
				else{
					MainWindow.updateImageView(imSnow);
					iconText.setText("Neige");
					MainWindow.getRootGroup().getChildren().add(iconText);
				}
			}
					
			
			/**
			 * Else it's night time
			 */
			else {
				
				/**
				 * If it's raining (depending on the temperature)
				 */
				if (Data.getLastData(Sensor.TEMPERATURE).getValue() >= 0){
				if (actualTemperatureValue >= 0)
					MainWindow.updateImageView(imNightRain);
					iconText.setText("Pluie");
					MainWindow.getRootGroup().getChildren().add(iconText);
				}
				/**
				 * Else it's snowing (below 0 degree)
				 */
				else{
					MainWindow.updateImageView(imNightSnow);
					iconText.setText("Neige");
					MainWindow.getRootGroup().getChildren().add(iconText);
				}
			}
		}
		
		/**
		 * Else then there is no rain or snow fall
		 */
		else {
			
			/**
			 * If it's day time
			 */
			if (actualRadiancyValue > 160) {
				/**
				 * It's sunny / with few clouds
				 */
				MainWindow.updateImageView(imSunnyCloudy);
				iconText.setText("Ensoleillé - Nuageux");
				MainWindow.getRootGroup().getChildren().add(iconText);
			}
			
			/**
			 * Then it's night time without any rain or snow fall
			 */
			else{
				MainWindow.updateImageView(imNight);
				
			}
		}
	}
	
	
	
	/** The actual pressure */
	private double pressure;
	/** The actual humidity */
	private double humidity;
	/** The actual temperature */
	private double temperature;
	/** The actual air quality */
	private double airQuality;
	
	/**  */
	private static Timeline    timelineRealTime;
	/**  */
	private static TimelineLcs timelineLcs;
	/**  */
	private static ParallelTransition pt;
	/**  */
	private static final Duration DURATION_TO_START  = Duration.millis(0);
	/**  */
	private static final Duration DURATION_1_DEFAULT = Duration.seconds(30);//30 s
	/**  */
	private static final Duration DURATION_2		 = Duration.minutes(5);
	/**  */
	private static final Duration DURATION_3		 = Duration.minutes(30);
	/**  */
	private static final Duration DURATION_4		 = Duration.hours(1);
	/**  */
	private static final Duration DURATION_5		 = Duration.hours(2);
	/**  */
	private static final Duration DURATION_6		 = Duration.hours(4);
	
	private static Text   iconText;
	
	private static boolean lostConnection = false;
	
	
}
