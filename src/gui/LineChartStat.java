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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;



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
		this.title = title;	
		
		
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
		this.title = title;

		
	}


	
	/**
	 * Updates the chart series by adding the latest after deleting the first data 
	 * if it exists and if there is less than 12.
	 *
	 * @param data
	 */
	public void updateSeries(db.Data data) {
	
		
		if (series.getData().size() != 0) {
			if (data.getTime().length() == 8) {
				System.out.println(data.getTime().substring(0,PRECISION_SEC) + "  ---  " + series.getData().get(series.getData().size() - 1).getXValue());
				if(data.getTime().substring(0,PRECISION_SEC).equals(
						series.getData().get(series.getData().size() - 1).getXValue())) {
					return;
				}
			}
			
			else {
				
				System.out.println(data.getTime().substring(0,PRECISION_MIN) + "  ---  " + series.getData().get(series.getData().size() - 1).getXValue());
				if(data.getTime().substring(0,PRECISION_MIN).equals(
						series.getData().get(series.getData().size() - 1).getXValue())) {
						return;
				}
			}
		}
		
		
		if (series.getData().size() >= MAX_SHOWING) {
			series.getData().remove(0, series.getData().size() - MAX_SHOWING);
			series.getData().size();
		}
		
		series.getData().add(
				new XYChart.Data<String, Number> (data.getTime(), data.getValue()));
		
		if (data.getDate() != lastDate.toString()) {
			
			
			lastDate = data.getDate();
			date = ", Dernière mise à jour " + lastDate;
			
			setTitle(title + date);
		}
		
	}
	
	

	public void refreshChart () {
		this.updateAxisRange();
	}
	
	
	
    /**  */
    private XYChart.Series<String, Number> series = new Series<String, Number>();
    /**  */
    private final  int    MAX_SHOWING 	= 12;
    
    //private static LocalDate lastDate = LocalDate.of(1000, 1, 1);
    private static String lastDate 		= "1000-01-01"; 
    /**  */
    private static String date 			= "";
    /**  */
    private 	   String title;
    /**  */
    private final  int    PRECISION_MIN = 5;
    /**  */
    private final  int 	  PRECISION_SEC = 8;
   
    
}

