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
 * Class represent a Line Chart for different data
 * 
 *
 * @author Jean AYOUB
 * @date 6 avr. 2016
 * @version 1.1
 */
public class LineChartStat extends LineChart<String, Number> {
        
		/**
	 * Constructeur.
	 * 
	 * @param xAxis
	 * @param yAxis
	 */
	public LineChartStat(String title, String seriesName, CategoryAxis xAxis, NumberAxis yAxis, 
												ArrayList<db.Data> dataList) {
		super(xAxis, yAxis);
		this.title 		= title;
		this.seriesName = seriesName;
		this.xAxis 		= xAxis;
		this.yAxis		= yAxis;
		this.dataList 	= (ArrayList<db.Data>)dataList.clone();
		
		
		/**
		 *  Creating the chart
		 */
		lineChartStat = new LineChart<String, Number>(this.xAxis, this.yAxis);
		
		lineChartStat.setTitle(title);
		lineChartStat.setPrefSize(400, 280);
		lineChartStat.setMaxSize(400, 280);
		
		
        /**
         *  Defining a series
         */
        XYChart.Series series = new XYChart.Series();
        series.setName(seriesName);
        
        
        /**
         * Populating the series with data
         */
        for (db.Data data : dataList) {
        	series.getData().add(new XYChart.Data(data.getTime(), data.getValue()));
        }
        lineChartStat.getData().add(series);
	}
	
	
	/**
	 * This function returns the Line chart.
	 *
	 * @return the Line chart
	 */
	public LineChart <String, Number> getLinChartStat () {
		return lineChartStat;
	}


	private final String					 title;
	private final String					 seriesName;
	private final CategoryAxis 				 xAxis;
    private final NumberAxis   			 	 yAxis;
    private final ArrayList<db.Data>         dataList;
    private final LineChart <String, Number> lineChartStat;
}

