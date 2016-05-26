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
	public UpdateData (long period_1, long period_2) {

		timeline = new Timeline(
			      new KeyFrame(Duration.millis(period_1), new EventHandler() {
			        @Override public void handle(Event event) {
			        	
			        	try {
			        		System.out.println("Je suis UPDATE_1");
			        		
			        		// We try to get a connection if the user filled the connection form
			        		if(MainWindow.getConnectionForm().getFormStatus() || MainWindow.getIsConnected()){
			        			System.out.println("Je suis UPDATE_2");
			        			DBConnection dbConn = 
			        					new DBConnection(MainWindow.getConnectionForm());
			        			
			        			MainWindow.setIsConnected(true);
			        			//connectionError = false;
			        			System.out.println("connection successful");
			        			checkLatestData();

			        		}
			    		} catch (SQLException e) {
			    			//e.printStackTrace();
			    			System.out.println("IsConnected = "+MainWindow.getIsConnected());
			    			System.out.println("FormStatus  = "+MainWindow.getConnectionForm().getFormStatus());
			    			MainWindow.setIsConnected(false);
			    			//connectionError = true;
			    			System.out.println("connection failed !");

			    			// Show an alert box to the user
			    			Alert alert = new Alert(AlertType.ERROR);
		          			alert.setTitle("Erreur");
		          			alert.setHeaderText(null);
		          			alert.setContentText("Echec de connexion. Veuillez recommencer svp !");
		          			
		          			// We reset the connection from and let the user  try again
		          			MainWindow.getConnectionForm().resetConnectionForm();
		          			alert.show();
			    			return;
			    		}

			    		}	
			        //}
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
	private void checkLatestData() throws SQLException{
		
		iconText = new Text(90, 300, "");
		
		iconText.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 30));
		
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
		//double actualAirQualityValue  = actualAirQuality.getValue();
		
		
		
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
				iconText.setText("Ensoleillé");
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
	
	public static boolean getConnectionError() {
		return connectionError;
	}
	
	public static boolean getErrorLogin(){
		return errorLogin;
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
	//private static boolean status;
	/**  */
	//private static int 	   timeToStop;
	/**  */
	private static boolean connectionError = false;
	
	private static boolean errorLogin = true;
	/**  */
	private 	   Timeline timeline;
	
	private static Text   iconText;
	
	
}
