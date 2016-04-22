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

import db.sensors.*;
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
    final Image imRainLight  = new Image("file:meteoImages/imRainLight.png");
    final Image imRainHeavy  = new Image("file:meteoImages/imRainHeavy.png");
    final Image imSnow 		 = new Image("file:meteoImages/imSnow.png");
    final Image imNight		 = new Image("file:meteoImages/imNight.png");
    final Image imNightRain	 = new Image("file:meteoImages/imNightRain.png");
    final Image imNightSnow	 = new Image("file:meteoImages/imNightSnow.png");
    
 	
	
	public UpdateData (long period1, long period2) { 
	
		timer.schedule(new TimerTask() {
			public void run()  {
				checkLatestData ();
			}
			}, 0, period1);
		
		timer.schedule(new TimerTask() {
			public void run()  {
				MainWindow.updateLcsTemperature(Temperature.getLastData());
				MainWindow.updateLcsHumidity(Humidity.getLastData());
				MainWindow.updateLcsPressure(Pressure.getLastData());
				MainWindow.updateLcsWind(Wind.getLastData());
			}
			}, 0, period2);
		}
	
	
	private void checkLatestData () {
		
		double actualTemperatureValue = Temperature.getLastData().getValue();
		double actualHumidityValue    = Humidity.getLastData().getValue();
		double actualPressureValue    = Pressure.getLastData().getValue();
		double actualWindValue        = Wind.getLastData().getValue();
		double actualRadiancyValue    = Radiancy.getLastData().getValue();
		
		
		
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
