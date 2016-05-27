/*
 -----------------------------------------------------------------------------------
 Laboratoire : 
 Fichier     : LcsTimeLine.java
 Auteur(s)   : Jean AYOUB
 Date        : 26 mai 2016
 But         : 
 Remarque(s) :
 Compilateur : jdk 1.8.0_60
 -----------------------------------------------------------------------------------
 */
package data_processing;

import java.sql.SQLException;

import db.Data;
import db.Data.Sensor;
import gui.LineChartStat;
import gui.MainWindow;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.util.Duration;

/**
 * Class.
 *
 * @author Jean AYOUB
 * @date 26 mai 2016
 * @version 1.0
 */
public class TimelineLcs {
	
	
	public void setPeriod(Duration d1, Duration d2) {
		this.timeline = new Timeline(
			      new KeyFrame(d1, new EventHandler() {
				        @Override public void handle(Event event) {
				        	try {
								MainWindow.updateLcs(MainWindow.getLcsTemperature(), Data.getLastData(Sensor.TEMPERATURE));
								MainWindow.updateLcs(MainWindow.getLcsHumidity(), Data.getLastData(Sensor.HUMIDITY));
								MainWindow.updateLcs(MainWindow.getLcsPressure(), Data.getLastData(Sensor.PRESSURE));
								MainWindow.updateLcs(MainWindow.getLcsAirQuality(), Data.getLastData(Sensor.AIR_QUALITY));
								System.out.println("LA DEDANS");
							} catch (SQLException e) {
								return;
							}
				        }
				      }),  
				     new KeyFrame(d2)
				    );
			 timeline.setCycleCount(Timeline.INDEFINITE);
	}
	
	public void start() {
		if (timeline != null)
			timeline.play();
	}
	
	public void stop() {
		if (timeline != null)
			timeline.stop();
	}
	
	public Timeline getTimeline () {
		return this.timeline;
	}
	
	
	private Timeline      timeline = null;
}
