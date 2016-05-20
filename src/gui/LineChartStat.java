/*
 -----------------------------------------------------------------------------------
 Laboratoire : PRO
 Fichier     : LineChartStat.java
 Auteur(s)   : Jean AYOUB
 Date        : 6 avr. 2016
 But         : 
 Remarque(s) :
 Compilateur : jdk 1.8.0_60
 -----------------------------------------------------------------------------------
 */
package gui;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;



/**
 * Class represent a personalized Line Chart for different elements 
 * 
 *
 * @author Jean AYOUB
 * @date 6 avr. 2016
 * @version 1.2
 */
public class LineChartStat extends LineChart<String, Number> {
        
	
	/**
	 * Constructor.
	 * 
	 * @param title
	 * @param seriesName
	 * @param xAxis
	 * @param yAxis
	 * @param dataList
	 */
	public LineChartStat(String 			title, 
						 String 			seriesName, 
						 CategoryAxis 		xAxis, 
						 NumberAxis 		yAxis, 
						 ArrayList<db.Data> dataList) {
		/**
		 *  Creating the chart
		 */
		super(xAxis, yAxis);
		this.setTitle(title);
		this.series.setName(seriesName);
			
		
		
        /**
         *  Setting the series name.
         */
        series.setName(seriesName);
        
        
        /**
         * Populating the series with data and adding it to the chart
         */
        
        for (db.Data data : dataList) {
        	series.getData().add(
        				new Data<String, Number>(data.getTime(), data.getValue()));
        }
        this.getData().add(series);
	}
	
	
	
	
	
	/**
	 * Constructor.
	 * 
	 * @param title
	 * @param seriesName
	 * @param xAxis
	 * @param yAxis
	 */
	public LineChartStat(String 	  title, 
						 String 	  seriesName, 
						 CategoryAxis xAxis, 
						 NumberAxis   yAxis) {
		/**
		 *  Creating the chart.
		 */
		super(xAxis, yAxis);
		this.setTitle(title);
		this.series.setName(seriesName);

		/**
		 *  Setting the series name.
		 */
		
	}


	
	/**
	 * Updates the chart series by adding the latest after deleting the first data 
	 * if it exists and if there is less than 12.
	 *
	 * @param data
	 */
	public void updateSeries(db.Data data) {
		
		if (series.getData().size() != 0) {
			if(data.getTime().substring(0,5).equals(series.getData().get(series.getData().size() - 1).getXValue())) {
				return;
			}
		}
		
		if (series.getData().size() >= MAX_SHOWING) 
			series.getData().remove(0, series.getData().size() - MAX_SHOWING);
		
		series.getData().add(
				new XYChart.Data<String, Number> (data.getTime(), data.getValue()));
	}
	
	
	
public void refreshChart () {
	this.updateAxisRange();
	}
	
	
    /**  */
    private XYChart.Series<String, Number> series = new Series<String, Number>();

    /**  */
    private final int MAX_SHOWING = 12;
}

