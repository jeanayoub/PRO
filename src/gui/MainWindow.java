/*
 -----------------------------------------------------------------------------------
 Laboratoire : PRO
 Fichier     : MainWindow.java
 Auteur(s)   : Jean AYOUB
 Date        : 18 mars 2016
 But         : Design the main Window of the application. 
 Remarque(s) :
 Compilateur : jdk 1.8.0_60
 -----------------------------------------------------------------------------------
 */
package gui;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.*;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import gui.LineChartStat;

import java.util.Locale;
import extfx.scene.control.*;

/**
 * Class MainWindow represents the application's main window.
 *
 * @author Jean AYOUB
 * @date 06 avril 2016
 * @version 1.3
 */
public class MainWindow extends Application{
	public void start(Stage primaryStage) {
		
		final Group rootGroup  = new Group();
		final Scene scene = new Scene(rootGroup, 800, 600, Color.HONEYDEW);
		
		primaryStage.setTitle("Station Météo");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() { 
			@Override 
			public void handle(WindowEvent t) { 
			System.out.println("Closing..."); } } );
		
		/**
		 * Declaration and definition of all the Text fields 
		 */
		final Text text1 = new Text(100, 80,  "Météo actuelle");
		final Text text2 = new Text(60 , 400, "Pression atmosphérique");
		final Text text3 = new Text(370, 80,  "Humidité");
		final Text text4 = new Text(500, 300, "Statistiques");
		final Text text5 = new Text(600, 80,  "Thermomètre");
		
		text1.setFont(Font.font("Arial", FontWeight.BOLD, 18));
		text2.setFont(Font.font("Arial", FontWeight.BOLD, 18));
		text3.setFont(Font.font("Arial", FontWeight.BOLD, 18));
		text4.setFont(Font.font("Arial", FontWeight.BOLD, 18));
		text5.setFont(Font.font("Arial", FontWeight.BOLD, 18));
		
		rootGroup.getChildren().add(text1);
		rootGroup.getChildren().add(text2);
		rootGroup.getChildren().add(text3);
		rootGroup.getChildren().add(text4);
		rootGroup.getChildren().add(text5);
		
		
		/**
		 * Declaration and definition of all the Images
		 */
		final Image miSunny 	 = new Image("file:meteoImages/maSunny.png");
		final Image miSunnyCloud = new Image("file:meteoImages/maSunnyCloud.png");
		final Image miRainLight  = new Image("file:meteoImages/maRainLight.png");
		final Image miRainHeavy  = new Image("file:meteoImages/maRainHeavy.png");
		final Image miSnow       = new Image("file:meteoImages/maSnow.png");
		
		
		/**
		 * This code sets the image in place
		 */
		final ImageView iv1 = new ImageView();
		iv1.setFitHeight(180);
		iv1.setFitWidth (180);
		iv1.setX(60);
		iv1.setY(100);
		iv1.setImage(miSunnyCloud);
		rootGroup.getChildren().add(iv1);
		
		
		/**
		 * The Menu bar
		 */
		final MenuBar menuBar 	   = new MenuBar();
        final Menu menuStation 	   = new Menu("Station");      
        final Menu menuOptions 	   = new Menu("Option");
        final Menu menuAbout 	   = new Menu("A propos");
        final Menu menuCalendar    = new Menu("Calendrier");
        menuBar.getMenus().addAll(menuStation, menuOptions, menuAbout, 
        													menuCalendar);
        ((Group) scene.getRoot()).getChildren().addAll(menuBar);
        
   
        /**
         * The menu item for the menu Calendar and its content which is the 
         * Calendar view
         */
        final MenuItem     miCalendarShow = new MenuItem("Show");
        final CalendarView cv             = new CalendarView(Locale.FRENCH);
        menuCalendar.getItems().addAll(miCalendarShow);
        
        
        /**
         * The dialog box to be shown once pressed on the Show menu item.
         */
        final Dialog dialog = new Dialog();
        dialog.setGraphic(cv);
        dialog.getDialogPane().setPrefSize(250, 250);
    
        
        /**
         * Shows the dialog box
         */
        miCalendarShow.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	
           
            	/**
            	 * Just to make the close button close the dialog box
            	 */
            	dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
                closeButton.managedProperty().bind(closeButton.visibleProperty());
                closeButton.setVisible(false);
                dialog.showAndWait();
            }
        });
        
       
        

        
        /**
		 * The Tab Pane
		 */
        final TabPane tabPan 		 = new TabPane();
        final Tab     tabTemperature = new Tab("Température"); 
        final Tab     tabHumidity 	 = new Tab("Humidité"); 
        final Tab     tabPressure    = new Tab("Pression");
        final Tab	  tabWind 	     = new Tab("Vent");
		
        tabPan.getTabs().addAll(tabTemperature, tabHumidity, tabPressure, tabWind);
		tabPan.setLayoutX(400);
		tabPan.setLayoutY(330);
		
		tabTemperature.setContent(createTabTemperature());
		
		tabTemperature.setClosable(false);
		tabHumidity.setClosable(false);
		tabPressure.setClosable(false);
		tabWind.setClosable(false);
		
        ((Group) scene.getRoot()).getChildren().addAll(tabPan);
        
        
        /**
		 * The Progress Bars
		 */
        final ProgressBar pbPressure = new ProgressBar();
        pbPressure.setLayoutX(65);
        pbPressure.setLayoutY(450);
        pbPressure.setPrefSize(200, 30);
        rootGroup.getChildren().add(pbPressure);
        
        final ProgressBar pbHumidity = new ProgressBar();
        pbHumidity.setLayoutX(400);
        pbHumidity.setLayoutY(200);
        pbHumidity.setPrefSize(100, 20);
        pbHumidity.getTransforms().setAll( new Rotate(-90, 0, 0) );
        rootGroup.getChildren().add(pbHumidity);
        
        final ProgressBar pbTempreture = new ProgressBar();
        pbTempreture.setLayoutX(650);
        pbTempreture.setLayoutY(200);
        pbTempreture.setPrefSize(100, 20);
        pbTempreture.getTransforms().setAll( new Rotate(-90, 0, 0) );
        rootGroup.getChildren().add(pbTempreture);
	}
	
	
	
	
	/**
	 * This function creats the graph for the Temperature tab.
	 * 
	 * !!! NOT DONE YET !!!
	 * We have to pass the data via the parameter !!!
	 *
	 * @return the Line chart
	 */
	private LineChart<String, Number> createTabTemperature () {
		
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis   yAxis = new NumberAxis();
		
		xAxis.setLabel("Heures");
		yAxis.setLabel("Temperature C");
		
		LineChartStat lcs = new LineChartStat(xAxis, yAxis);
	
		lcs.setPrefSize(400, 250);
		lcs.setMaxSize(400, 250);
		
		return lcs.getLinChartStat();
	}

	/**
	 * Main function for lunching the window 
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		Application.launch(MainWindow.class, args);
	}

}
