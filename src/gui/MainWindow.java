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

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import data_processing.UpdateData;
import data_processing.generateFile;
import db.ConnectionForm;
import db.Data;
import eu.hansolo.enzo.lcd.Lcd;
import eu.hansolo.enzo.lcd.LcdBuilder;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import extfx.scene.control.CalendarView;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;



/**
 * Class MainWindow represents the application's main window.
 *
 * @author Jean AYOUB
 * @date 06 avril 2016
 * @version 1.3
 */
public class MainWindow extends Application {

   /* (non-Javadoc)
    * @see javafx.application.Application#start(javafx.stage.Stage)
    */
   public void start(Stage primaryStage) throws IOException {

      final Group rootGroup = new Group();
      final Scene scene = new Scene(rootGroup, 800, 600, Color.HONEYDEW);

      primaryStage.setTitle("Station Météo");
      primaryStage.setResizable(false);
      primaryStage.setScene(scene);
      primaryStage.show();
      primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
         @Override
         public void handle(WindowEvent t) {
            UpdateData.timer.cancel();
            System.out.println("Closing...");
         }
      });

      /**
       * Declaration and definition of all the Text fields
       */
      final Text text1 = new Text(100,  80, "Météo actuelle");
      final Text text2 = new Text( 60, 400, "Pression atmosphérique");
      final Text text3 = new Text(370,  80, "Humidité");
      final Text text4 = new Text(500, 270, "Statistiques");
      final Text text5 = new Text(600,  80, "Thermomètre");

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
       * This code sets the image in place
       */
      iv.setFitHeight(180);
      iv.setFitWidth(180);
      iv.setX(60);
      iv.setY(100);
      //iv.setImage(miSunnyCloud);
      rootGroup.getChildren().add(iv);

      /**
       * The Menu bar
       */
      final MenuBar menuBar 	  = new MenuBar();
      final Menu 	menuStation   = new Menu("Station");
      final Menu 	menuOptions   = new Menu("Option");
      final Menu 	menuAbout     = new Menu("A propos");
      final Menu 	menuCalendar  = new Menu("Calendrier");
      final Menu 	menuSaveAs    = new Menu("Enregistrer sous ");
      final Menu 	menuPrevision = new Menu("Prévision météorologique");

      final MenuItem menuJpeg 	= new MenuItem("JPEG");
      final MenuItem menuPdf 	= new MenuItem("PDF");
      final MenuItem unJour 	= new MenuItem("1 jour");
      final MenuItem deuxJour 	= new MenuItem("2 jours");
      final MenuItem uneSemaine = new MenuItem("1 semaine");
      final MenuItem connection = new MenuItem("Connexion");
      

      menuStation.getItems().add(connection);
      menuStation.getItems().add(menuSaveAs);
      menuSaveAs.getItems().add(0, menuPdf);
      menuSaveAs.getItems().add(1, menuJpeg);
      
      menuOptions.getItems().add(menuPrevision);
      menuPrevision.getItems().add(0, unJour);
      menuPrevision.getItems().add(1, deuxJour);
      menuPrevision.getItems().add(2, uneSemaine);

      menuBar.getMenus().addAll(menuStation, menuOptions, menuAbout,
              menuCalendar);
      ((Group) scene.getRoot()).getChildren().addAll(menuBar);

      /**
       * The menu item for the menu About and its content which is the application's
       * copyright and version.
       */
      final MenuItem miAboutInfo = new MenuItem("Info");
      menuAbout.getItems().addAll(miAboutInfo);

      /**
       * The dialog box to be shown once pressed on the Info menu item.
       */
      final Dialog<Text> dialogInfo = new Dialog<Text>();
      dialogInfo.getDialogPane().setPrefSize(130, 200);
      dialogInfo.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
      Node closeButtonDialogInfo = dialogInfo.getDialogPane().lookupButton(
              ButtonType.CLOSE);
      closeButtonDialogInfo.managedProperty().bind(
              closeButtonDialogInfo.visibleProperty());

      final Text textDialogInfo = new Text("Station Météo \nVersion 1.0"
              							 + "\n\nCopyrights ©"
              							 + "\nPRO HEIG-VD"
              							 + "\n\nR. Combremont"
              							 + "\nM. Dupraz"
              							 + "\nI. Ounon"
              							 + "\nP. Sekley"
              							 + "\nJ. Ayoub");

      textDialogInfo.setFont(Font.font("Verdana", 12));
      textDialogInfo.setFill(Color.STEELBLUE);

      dialogInfo.setGraphic(textDialogInfo);

      /**
       * Shows the dialog box
       */
      miAboutInfo.setOnAction(new EventHandler<ActionEvent>() {
         @Override
         public void handle(ActionEvent event) {
            /**
             * Just to make the close button close the dialog box
             */
            closeButtonDialogInfo.setVisible(false);
            dialogInfo.showAndWait();
         }
      });

      //--------------------------------PASCAL--------------------------------------
      /**
       * The menu item for the menu Station and its content
       */
      menuPdf.setOnAction((ActionEvent event) -> {
         final generateFile myFile = new generateFile();
         try {
            myFile.toPDF();
         } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
         }
         Alert alert = new Alert(AlertType.INFORMATION);
         alert.setTitle("Information Dialog");
         alert.setHeaderText(null);
         alert.setContentText("Sauvegarde en PDF Résussi !!!");
         alert.showAndWait();
      });

      //----------------------------------Pascal-------------------------------------
      menuJpeg.setOnAction((ActionEvent event) -> {
         final generateFile myFile = new generateFile();
         myFile.toJpeg(getTabPane());
         Alert alert = new Alert(AlertType.INFORMATION);
         alert.setTitle("Information Dialog");
         alert.setHeaderText(null);
         alert.setContentText("Sauvegarde en JPEG Résussi !!!");
         alert.showAndWait();
      });
      
      //-------------------------------------------------------------------------------
      /**
       * Create a connection to a data base in order to fetch data and display them
       */
       
      connection.setOnAction(new EventHandler<ActionEvent>() {
    	  @Override public void handle(ActionEvent e) {
    		  Stage stage = new Stage();

    		  GridPane root = new GridPane();
    		  HBox btnPanel = new HBox(12);
    		  Label lblTitle = new Label("Connexion");
    		  Label lblConnexionName = new Label("Connection Name:");
    		  TextField tfdConnectionName = new TextField();
    		  Label lblHostName = new Label("Hostname:");
    		  TextField tfdHostname = new TextField();
    		  Label lblPort = new Label("Port:");
    		  TextField tfdPort = new TextField();
    		  Label lblUsername = new Label("Username:");
    		  TextField tfdUsername = new TextField();
    		  Label lblPassword = new Label("Password:");
    		  Button btnLogin = new Button("Login");
    		  Button btnCancel = new Button("Cancel");
    		  PasswordField pwfPassword = new PasswordField();

    		  stage.setTitle("Connexion à la base de données");
    		  
    		  // Set color of the stage
    		  Background backGroundColor = new Background(
    				  new BackgroundFill(Color.LIGHTSKYBLUE,
    				  CornerRadii.EMPTY,
    				  null ));
    		  root.setBackground(backGroundColor);
    		  // Bind to avoid errors
    		  pwfPassword.disableProperty().bind(tfdUsername.textProperty().isEmpty());
    		  btnLogin.disableProperty().bind(tfdConnectionName.textProperty().isEmpty()
    				  						.or(tfdUsername.textProperty().isEmpty())
    				  						.or(pwfPassword.textProperty().isEmpty()
    				  						.or(tfdPort.textProperty().isEmpty()
    				  						.or(tfdHostname.textProperty().isEmpty()))));

    		  //--- Title
    		  lblTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
    		  lblTitle.setTextFill(Color.rgb(80, 80, 180));
    		  root.add(lblTitle, 0, 0, 2, 1);
    		  GridPane.setHalignment(lblTitle, HPos.CENTER);
    		  GridPane.setMargin(lblTitle, new Insets(0, 0, 10,0));

    		  //--- Username (label and text-field)
    		  tfdUsername.setPrefColumnCount(20);
    		  root.add(lblConnexionName, 0, 1);
    		  root.add(tfdConnectionName, 1, 1);
    		  root.add(lblHostName, 0, 2);
    		  root.add(tfdHostname, 1, 2);
    		  root.add(lblPort, 0, 3);
    		  root.add(tfdPort, 1, 3);
    		  root.add(lblUsername, 0, 4);
    		  root.add(tfdUsername, 1, 4);
    		  //GridPane.setHalignment(lblUsername, HPos.RIGHT);
    		  //--- Password (label and text-field)
    		  pwfPassword.setPrefColumnCount(12);
    		  root.add(lblPassword, 0, 5);
    		  root.add(pwfPassword, 1, 5);
    		  //GridPane.setHalignment(lblPassword, HPos.RIGHT);
    		  GridPane.setFillWidth(pwfPassword, false);
    		  //--- Button panel
    		  btnPanel.getChildren().add(btnLogin);
    		  btnPanel.getChildren().add(btnCancel);
    		  btnPanel.setAlignment(Pos.CENTER_RIGHT);
    		  root.add(btnPanel, 1, 6);
    		  GridPane.setMargin(btnPanel, new Insets(10, 0, 0,0));

    

    		  //-- GridPane properties
    		  root.setAlignment(Pos.CENTER);
    		  root.setPadding(new Insets(20));
    		  root.setHgap(10);
    		  root.setVgap(15);

    		  stage.setMinWidth(300);
    		  stage.setMinHeight(350);
    		  stage.setScene(new Scene(root));
    		  
    		  // Set shadow on mouse event
    		  btnLogin.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent event) {
					btnLogin.setEffect(new DropShadow());
				}
    		  });
    		  btnLogin.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>(){
  				@Override
  				public void handle(MouseEvent event) {
  					btnLogin.setEffect(null);
  				}
      		  });
    		  
    		  btnCancel.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>(){
  				@Override
  				public void handle(MouseEvent event) {
  					btnCancel.setEffect(new DropShadow());
  				}
      		  });
    		  btnCancel.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>(){
    				@Override
    				public void handle(MouseEvent event) {
    					btnCancel.setEffect(null);
    				}
        		  });
    		  
    		  btnLogin.setOnAction(new EventHandler<ActionEvent>(){

				@Override
				public void handle(ActionEvent event) {
					
				    connectionForm = new ConnectionForm(tfdConnectionName.getText(), tfdHostname.getText(), Integer.parseInt(tfdPort.getText()),
				    		tfdUsername.getText(), pwfPassword.getText());
				    
				    // Close the window when login button is clicked
				    stage.close();
				    
				    // Show an alert box to confirm an attempt of connection to the data base
				    Alert dialogW = new Alert(AlertType.INFORMATION);
				    dialogW.setTitle("Confirmation");
				    dialogW.setHeaderText(null); // No header
				    dialogW.setContentText("Tentative de connexion à la base de données !");
				    dialogW.showAndWait();
					
				}
    			  
    		  });
    		  
    		  btnCancel.setOnAction(actionEvent -> stage.close());
    		     		  
    		  stage.show();
    	  }

      });

      /**
       * The menu item for the menu Calendar and its content which is the Calendar
       * view
       */
      
      
      final MenuItem miCalendarShow = new MenuItem("Afficher");
      menuCalendar.getItems().add(miCalendarShow);
      
      
      miCalendarShow.setOnAction(new EventHandler<ActionEvent>(){

		@Override
		public void handle(ActionEvent event) {
			// Date Picker
	          DatePicker dPicker = new DatePicker();
	          dPicker.setShowWeekNumbers(true);
	          Stage dateStage = new Stage();
	          dateStage.setTitle("Calendrier");
	          HBox hbox = new HBox(dPicker);
	       // Set color of the hbox
    		  Background backGroundColor = new Background(
    				  new BackgroundFill(Color.LIGHTPINK,
    				  CornerRadii.EMPTY,
    				  null ));
	          hbox.setBackground(backGroundColor);
	          Scene scene = new Scene(hbox, 240, 240);
	          dateStage.setScene(scene);
	          dateStage.show();
	          
	          // Get the date when clicked
	          dPicker.setOnAction(e -> {
	          LocalDate date = dPicker.getValue();
	          System.out.println("Selected date: " + date);
	          
	          // Here is an example how to get date values
	          int day = date.getDayOfMonth(); // 1..31
	          int month = date.getMonthValue(); // 1..12
	          int year = date.getYear();
	          });
			
		}
    	
      });
   
      
      
//      final CalendarView cv = new CalendarView(Locale.FRENCH);
//      menuCalendar.getItems().addAll(miCalendarShow);
//      
//
//      
//      /*
//      cv.setOnMousePressed(new EventHandler<MouseEvent>() {
//          public void handle(MouseEvent me) {
//        	  System.out.println(cv.getSelectedDate());
//            }
//          });
//       */
//
//      /**
//       * The dialog box to be shown once pressed on the Show menu item.
//       */
//      final Dialog<CalendarView> dialogCv = new Dialog<CalendarView>();
//      dialogCv.setGraphic(cv);
//      //final Dialog<DatePicker> dialogCv = new Dialog<DatePicker>();
//      //dialogCv.setGraphic(dp);
//      dialogCv.getDialogPane().setPrefSize(250, 250);
//      dialogCv.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
//      Node closeButtonDialogCv = dialogCv.getDialogPane().lookupButton(ButtonType.CLOSE);
//      closeButtonDialogCv.managedProperty().bind(
//              closeButtonDialogCv.visibleProperty());
//
//      /**
//       * Shows the dialog box
//       */
//      miCalendarShow.setOnAction(new EventHandler<ActionEvent>() {
//         @Override
//         public void handle(ActionEvent event) {
//            /**
//             * Just to make the close button close the dialog box
//             */
//            closeButtonDialogCv.setVisible(false);
//            dialogCv.showAndWait();
//         }
//      });
      //-------------------------------------------------------------------------
      
      
      
      
      
      
      
      

      /**
       * The Tab Pane
       */
      final TabPane tabPan = new TabPane();
      final Tab tabTemperature = new Tab("Température");
      final Tab tabHumidity = new Tab("Humidité");
      final Tab tabPressure = new Tab("Pression");
      final Tab tabAirQuality = new Tab("Qualité d'air");

      tabPan.getTabs().addAll(tabTemperature, tabHumidity, tabPressure, tabAirQuality);
      tabPan.setLayoutX(350);
      tabPan.setLayoutY(280);
      copyPane = tabPan;

      /**
       * !!! This is just for testing !!!
       */
      ArrayList<Data> dataList = new ArrayList<Data>();
      Data data1  = new Data(2016, 4, 12,  6, 0,  6.2);
      Data data2  = new Data(2016, 4, 12,  8, 0, 10.8);
      Data data3  = new Data(2016, 4, 12, 10, 0, 12.4);
      Data data4  = new Data(2016, 4, 12, 12, 0, 15.9);
      Data data5  = new Data(2016, 4, 12, 14, 0, 22.8);
      Data data6  = new Data(2016, 4, 12, 16, 0, 25.7);
      Data data7  = new Data(2016, 4, 12, 18, 0, 20.3);
      Data data8  = new Data(2016, 4, 12, 20, 0, 14.1);
      Data data9  = new Data(2016, 4, 12, 22, 0,  8.5);
      Data data10 = new Data(2016, 4, 13, 00, 0,  7.2);
      Data data11 = new Data(2016, 4, 13,  2, 0,  6.8);
      Data data12 = new Data(2016, 4, 13,  4, 0,  2.9);
      Data data13 = new Data(2016, 4, 13,  7, 7, -35.0);

      
      dataList.add(data1);
      dataList.add(data2);
      dataList.add(data3);
      dataList.add(data4);
      dataList.add(data5);
      dataList.add(data6);
      dataList.add(data7);
      dataList.add(data8);
      dataList.add(data9);
      dataList.add(data10);
      dataList.add(data11);
      dataList.add(data12);
      
      

      lcsTemperature
              = (LineChartStat) createLineChart("Température",
                      							"Variation de la température",
                      							"Heures",
                      							"Temperature [°C]",
                      							450,
                      							290,
                      							dataList);

      lcsHumidity
              = (LineChartStat) createLineChart("Humidité",
                      							"Variation de l'humidité",
                      							"Heures",
                      							"Humidité [%]",
                      							450,
                      							290,
                      							dataList);

      lcsPressure
              = (LineChartStat) createLineChart("Pression",
                      							"Variation de la pression",
                      							"Heures",
                      							"Pression [hPa]",
                      							450,
                      							290,
                      							dataList);

      lcsAirQuality
              = (LineChartStat) createLineChart("Qualité d'air",
                      							"Variation de la qualité d'air",
                      							"Heures",
                      							"indice[0 - 5.5]",
                      							450,
                      							290,
                      							dataList);

      tabTemperature.setContent(lcsTemperature);
      tabHumidity.setContent(lcsHumidity);
      tabPressure.setContent(lcsPressure);
      tabAirQuality.setContent(lcsAirQuality);

      /**
       * !!! Just to test the updateSeries method. !!!
       */
      //lcsTemperature.updateSeries(data13);
      //lcsTemperature.updateSeries(Temperature.getLastData());

      tabTemperature.setClosable(false);
      tabHumidity.setClosable(false);
      tabPressure.setClosable(false);
      tabAirQuality.setClosable(false);
      
      tabPan.getSelectionModel().selectedItemProperty().addListener(
    		    new ChangeListener<Tab>() {
    		        @Override
    		        public void changed(ObservableValue<? extends Tab> ov, Tab tabTemperature, Tab tabHumidity) {
    		        	
    		        	lcsTemperature.refreshChart();
    		        	lcsHumidity.refreshChart();
    		        	lcsPressure.refreshChart();
    		        	lcsAirQuality.refreshChart();
    		        }
    		    }
    		);
      
      ((Group) scene.getRoot()).getChildren().addAll(tabPan);

      /**
       * The humidity Progress Bar
       */
      pbHumidity.setLayoutX(400);
      pbHumidity.setLayoutY(200);
      pbHumidity.setPrefSize(100, 20);
      pbHumidity.getTransforms().setAll(new Rotate(-90, 0, 0));
      rootGroup.getChildren().add(pbHumidity);
      

      
    //-------------------------TEST GAUGE RADIAL---------------------------------------------
 
      lcdTemperature = LcdBuilder.create()
    		  	.prefWidth(220)
    		  	.prefHeight(100)
    		  	.layoutX(560)
    		  	.layoutY(90)
    		  	.styleClass(/*Lcd.STYLE_CLASS_FLAT_MIDNIGHT_BLUE*/Lcd.STYLE_CLASS_LIGHTGREEN_BLACK)
    		  	.backgroundVisible(true)
    		  	.valueFont(Lcd.LcdFont.DIGITAL_BOLD)
    		  	.lowerRightTextVisible(true)
    		  	.lowerRightText("PRO-2016")
    		  	.title("Temperature")
    		  	.titleVisible(true)
    		  	.unit("°C")
    		  	.unitVisible(true)
    		   	.build();
      
      rootGroup.getChildren().add(lcdTemperature);
      
      
      pressureGauge = new Gauge();
      pressureGauge = GaugeBuilder.create()
    		  			.knobColor(Color.AQUAMARINE)
    		  			.borderPaint(Paint.valueOf("green"))
    		  			.prefSize(310, 200)
    		  			.minValue(0)
    		  			.maxValue(1100)
    		  			.title("Pression")
    		  			.unit("hPa")
    		  			.unit("hPa")
    		  			.shadowsEnabled(true)
    		  			.layoutY(410)
    		  			.build();

      rootGroup.getChildren().add(pressureGauge);
      
 
      
      // Before updating data we need to connect to the data base
         //connectionForm = new ConnectionForm();
	     UpdateData updateData = new UpdateData(5000);

   }
   
//
//   public static void getConnexionInfo(Button btnLogin){
//	   
//   }
   
//   public void initializeConnectionToBD(){
//	   //while(connectionForm.getFormStatus() == false){
//		   UpdateData updateData = new UpdateData(5000);
//		   //dbConn = new DBConnection(connectionForm);
//	   //}
//   }

   /**
    *
    *
    * @param image
    */
   public static void updateImageView(Image image) {
      iv.setImage(image);
   }
   
   
   
   /**
   *
   *
   * @param value
   */
  public static void updateLcdTemperature(double value) {
	  lcdTemperature.setValue(value);
  }
  
  
  
  /**
  *
  *
  * @param value
  */
 public static void updatePressureGauge(double value) {
	 pressureGauge.setValue(value);
 }
   
   

   /**
    *
    *
    * @param value
    */
   public static void updatePbHumidity(double value) {
      pbHumidity.setProgress(value/100.);
     // pbHumidity.setVisible(true);
   }

   

   /**
    *
    *
    * @param data
    */
   public static void updateLcsTemperature(Data data) {
      lcsTemperature.updateSeries(data);
   }

   /**
    *
    *
    * @param data
    */
   public static void updateLcsHumidity(Data data) {
      lcsHumidity.updateSeries(data);
   }

   /**
    *
    *
    * @param data
    */
   public static void updateLcsPressure(Data data) {
      lcsPressure.updateSeries(data);
   }

   /**
    *
    *
    * @param data
    */
   public static void updateLcsAirQuality(Data data) {
	   lcsAirQuality.updateSeries(data);
   }
   
   public static ConnectionForm getConnectionForm(){
	   return  connectionForm;
   }
   
   

   /**
    * This method creats a line chart graph customized for this application.
    *
    * @param title
    * @param seriesName
    * @param xAxisLabel
    * @param yAxisLabel
    * @param xSize
    * @param ySize
    * @param dataList
    * @return Line Chart
    */
   private LineChart<String, Number> createLineChart(String title,
           String seriesName,
           String xAxisLabel,
           String yAxisLabel,
           double xSize,
           double ySize,
           ArrayList<db.Data> dataList) {

      final CategoryAxis xAxis = new CategoryAxis();
      final NumberAxis yAxis = new NumberAxis();

      xAxis.setLabel(xAxisLabel);
      yAxis.setLabel(yAxisLabel);

      LineChartStat lcs = new LineChartStat(title,
              seriesName,
              xAxis,
              yAxis,
              dataList);

      lcs.setPrefSize(xSize, ySize);
      lcs.setMaxSize(xSize, ySize);

      return lcs;
   }

   //----------------------------------Pascal--------------------------------------
   /**
    * This method returns the current tabPane.
    *
    *
    * @return A copy of the actual pane
    */
   private TabPane getTabPane() {
      return copyPane;
   }
    //----------------------------------Pascal--------------------------------------

   /** A copy of the tabPane */
   private 		  TabPane 		copyPane 		= new TabPane();
   /**  */
   private static ImageView 	iv 				= new ImageView();
   /**  */
   private static ProgressBar 	pbHumidity 		= new ProgressBar();
   /**  */
   private static Lcd 			lcdTemperature;
   /**  */
   private static Gauge 		pressureGauge;
   /**  */
   private static LineChartStat lcsTemperature;
   /**  */
   private static LineChartStat lcsHumidity;
   /**  */
   private static LineChartStat lcsPressure;
   /**  */
   private static LineChartStat lcsAirQuality;
   
   private static ConnectionForm connectionForm;
   
   //private static DBConnection dbConn;

   /**
    * Main method for lunching the user window.
    *
    * @param args
    */
   public static void main(String[] args) {
      Application.launch(MainWindow.class, args);
   }

}
