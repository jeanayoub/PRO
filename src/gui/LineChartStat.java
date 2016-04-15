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
         *  Setting the series name.
         */
        series.setName(seriesName);
        
        
        /**
         * Populating the series with data and adding it to the chart
         */
        
        for (db.Data data : dataList) {
        	series.getData().add(new XYChart.Data(data.getTime(), data.getValue()));
        }
        this.getData().add(series);
	}
	
	
	
	
	
	public LineChartStat(String title, String seriesName, CategoryAxis xAxis, 
																NumberAxis yAxis) {
		/**
		 *  Creating the chart.
		 */
		super(xAxis, yAxis);
		this.title 		= title;
		this.seriesName = seriesName;
		this.xAxis 		= xAxis;
		this.yAxis		= yAxis;
		this.setTitle(title);


		/**
		 *  Setting the series name.
		 */
		series.setName(seriesName);
	}


	
	/**
	 * Updates the chart series by adding the latest after deleting the first data 
	 * if it exists and if there is less than 12.
	 *
	 * @param data
	 */
	public void updateSeries(db.Data data) {
		
		if (!series.getData().isEmpty() && series.getData().size() >= 12) 
			series.getData().remove(0);
		
		series.getData().add(new XYChart.Data(data.getTime(), data.getValue()));
	}
	
	
	

	private final String			 title;
	private final String			 seriesName;
	private final CategoryAxis 		 xAxis;
    private final NumberAxis   		 yAxis;
    private       XYChart.Series 	 series = new XYChart.Series();
    private       ArrayList<db.Data> dataList;
    private final int 				 MAX_SHOWING = 8;
}

