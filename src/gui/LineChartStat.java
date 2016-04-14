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
 * @version 1.1
 */
public class LineChartStat extends LineChart<String, Number> {
        
		/**
	 * Constructor.
	 * 
	 * @param xAxis
	 * @param yAxis
	 */
	public LineChartStat(String title, String seriesName, CategoryAxis xAxis, NumberAxis yAxis, 
												ArrayList<db.Data> dataList) {
		/**
		 *  Creating the chart
		 */
		super(xAxis, yAxis);
		this.title 		= title;
		this.seriesName = seriesName;
		this.xAxis 		= xAxis;
		this.yAxis		= yAxis;
		this.dataList 	= (ArrayList<db.Data>)dataList.clone();
		this.setTitle(title);	
		
		
        /**
         *  Defining a series
         */
        series = new XYChart.Series();
        series.setName(seriesName);
        
        
        /**
         * Populating the series with data and adding it to the chart
         */
        for (db.Data data : dataList) {
        	series.getData().add(new XYChart.Data(data.getTime(), data.getValue()));
        }
        this.getData().add(series);
	}
	

	private final String			 title;
	private final String			 seriesName;
	private final CategoryAxis 		 xAxis;
    private final NumberAxis   		 yAxis;
    private final ArrayList<db.Data> dataList;
    private final XYChart.Series series;
}

