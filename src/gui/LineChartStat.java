/*
 -----------------------------------------------------------------------------------
 Laboratoire : 
 Fichier     : LineChartTemperature.java
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


/**
 * Class represent a Line Chart for different data
 * 
 * !!! THIS CLASS IS NOT DONE YET: IT IS NOT GENERIC !!!
 *
 * @author Jean AYOUB
 * @date 6 avr. 2016
 * @version 1.0
 */
public class LineChartStat extends LineChart<String, Number> {
        
		/**
	 * Constructeur.
	 * 
	 * @param xAxis
	 * @param yAxis
	 */
	public LineChartStat(CategoryAxis xAxis, NumberAxis yAxis)  {
		super(xAxis, yAxis);
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		
		
		/**
		 *  Creating the chart
		 */
		lineChartStat = new LineChart<String, Number>(this.xAxis, this.yAxis);
		
		lineChartStat.setTitle("Temperature");
		lineChartStat.setPrefSize(400, 250);
		lineChartStat.setMaxSize(400, 250);
		
		
        /**
         *  Defining a series
         */
        XYChart.Series series = new XYChart.Series();
        series.setName("Temperature");
        
        
        
        /**
         * Populating the series with data
         */
        series.getData().add(new XYChart.Data("08:00", 23));
        series.getData().add(new XYChart.Data("10:00", 14));
        series.getData().add(new XYChart.Data("12:00", 15));
        series.getData().add(new XYChart.Data("14:00", 24));
        series.getData().add(new XYChart.Data("16:00", 34));
        series.getData().add(new XYChart.Data("18:00", 36));
        series.getData().add(new XYChart.Data("20:00", 22));
 		
        lineChartStat.getData().add(series);        
	}
	
	
	/**
	 * This function returns the Line chart 
	 * 
	 *
	 * @return the Line chart
	 */
	public LineChart <String, Number> getLinChartStat () {
		return lineChartStat;
	}


	private final CategoryAxis xAxis;
    private final NumberAxis   yAxis;
    private final LineChart <String, Number> lineChartStat;
}

