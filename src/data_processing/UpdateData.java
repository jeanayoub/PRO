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

import java.util.Timer;
import java.util.TimerTask;

import db.Data;
import db.Data.Sensor;
import gui.MainWindow;
import javafx.scene.image.Image;

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
	final Image imSunny 	 = new Image("file:meteoImages/imSunny.png");
    final Image imSunnyCloud = new Image("file:meteoImages/imSunnyCloud.png");
    final Image imCloudy	 = new Image("file:meteoImages/imCloudy.png");
    
    final Image imRainHeavy  = new Image("file:meteoImages/imRainHeavy.png");
    final Image imSnow 		 = new Image("file:meteoImages/imSnow.png");
    final Image imNight		 = new Image("file:meteoImages/imNight.png");
    final Image imNightRain	 = new Image("file:meteoImages/imNightRain.png");
    final Image imNightSnow	 = new Image("file:meteoImages/imNightSnow.png");
    
 	
	
	/**
	 * Constructor.
	 * 
	 * @param period1
	 * @param period2
	 */
	public UpdateData (long period1, long period2) { 
	
		timer.schedule(new TimerTask() {
			public void run()  {
				checkLatestData ();
			}
			}, 0, period1);
		
		timer.schedule(new TimerTask() {
			public void run()  {
				MainWindow.updateLcsTemperature(Data.getLastData(
															  Sensor.TEMPERATURE));
				MainWindow.updateLcsHumidity(Data.getLastData(Sensor.HUMIDITY));
				MainWindow.updateLcsPressure(Data.getLastData(Sensor.PRESSURE));
				MainWindow.updateLcsWind(Data.getLastData(    Sensor.WIND));
			}
			}, 0, period2);
		}
	
	
	/**
	 * 
	 *
	 */
	private void checkLatestData () {
		
		double actualTemperatureValue = Data.getLastData(
										Sensor.TEMPERATURE).getValue();
		double actualHumidityValue    = Data.getLastData(
										Sensor.HUMIDITY).getValue();
		double actualPressureValue    = Data.getLastData(
										Sensor.PRESSURE).getValue();
		double actualWindValue        = Data.getLastData(
										Sensor.WIND).getValue();
		double actualRadiancyValue    = Data.getLastData(
										Sensor.RADIANCY).getValue();
		
		
		
		if (pressure != actualPressureValue) {
			MainWindow.updatePbPressure(actualPressureValue);
			pressure = actualPressureValue;
		}
			
		if (humidity != actualHumidityValue) {	
			MainWindow.updatePbHumidity(actualHumidityValue);
			humidity = actualHumidityValue;
		}
		
		if (temperature != actualTemperatureValue) {
			MainWindow.updatePbTemperature(actualTemperatureValue);
			temperature = actualTemperatureValue;
		}
		
		
		
		/*
		if (Temperature.getLastData().getValue() > 3) 
			MainWindow.updateImageView(imSnow);
		
		else if()
			MainWindow.updateImageView();
		else if()
			MainWindow.updateImageView();
		else if()
			MainWindow.updateImageView();
		else ()
			MainWindow.updateImageView();
			*/
	}
	
	
	/**  */
	public static Timer timer = new Timer();
	
	/** The actual pressure */
	private double pressure;
	/** The actual humidity */
	private double humidity;
	/** The actual temperature */
	private double temperature;

}
