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

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import data_processing.ReceivedData;
import data_processing.ResourceLoader;
import data_processing.UpdateData;
import data_processing.generateFile;
import db.ConnectionForm;
import db.DBConnection;
import db.Data;
import db.OpenConnection;
import eu.hansolo.enzo.lcd.Lcd;
import eu.hansolo.enzo.lcd.LcdBuilder;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
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
import javafx.scene.control.SplitPane;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import utils.Hours;

import java.sql.Time;



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
	   
	   // Icon to be place for active and inactive connection   
	    final Image imActiv   = new Image(ResourceLoader.load("meteoImages/actif.png"));
	    final Image imInactiv = new Image(ResourceLoader.load("meteoImages/inactif.png"));
	    
	    final Text textActiv = new Text(430, 27, "Actif");
	    final Text textInactiv = new Text(430, 27, "Inactif");
	    textActiv.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 19));
	    textInactiv.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 19));
	    
	   

      final Group rootGroup = new Group();
      rootGroupCopy = rootGroup;
      final Scene scene = new Scene(rootGroup, 800, 600, Color.HONEYDEW);

      primaryStage.setTitle("Station Météo");
      primaryStage.setResizable(false);
      primaryStage.setScene(scene);
      primaryStage.show();
      primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
    	 @Override
         public void handle(WindowEvent t) {
            System.out.println("Closing...");
         }
      });

      /**
       * Declaration and definition of all the Text fields
       */
      final Text text1 = new Text(100,  80, "Météo actuelle");
      final Text text2 = new Text( 60, 350, "Pression atmosphérique");
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
      
      /**
       * This code sets the activ and inactiv image in place
       */
      ivConnect.setX(400);
      ivConnect.setY(10);
      
      ivConnect.setImage(imInactiv);
      rootGroup.getChildren().add(iv);
      rootGroup.getChildren().add(ivConnect);
      rootGroup.getChildren().add(textInactiv);

      /**
       * The Menu bar
       */

      final MenuBar menuBar = new MenuBar();
      final Menu menuStation     = new Menu("Station");
      final Menu menuOptions   	 = new Menu("Option");
      final Menu menuAbout     	 = new Menu("A propos");
      final Menu menuCalendar    = new Menu("Calendrier");
      final Menu menuSaveAs      = new Menu("Enregistrer Sous");
      final Menu menuPrevision   = new Menu("Prévision météorologique");
      final Menu menuDuration    = new Menu("Temps de mise à jour des graphes");

      
      final MenuItem miExit       = new MenuItem("Quitter");
      final MenuItem miOneday     = new MenuItem("1 jour");
      final MenuItem miTwoDays    = new MenuItem("2 jours");
      final MenuItem miOneWeek    = new MenuItem("1 semaine");
      final MenuItem miConnection = new MenuItem("Connexion");
      final MenuItem miDuration_1 = new MenuItem(String.valueOf(UpdateData.getDuration1Default().toMinutes()) + "min");
      final MenuItem miDuration_2 = new MenuItem(String.valueOf(UpdateData.getDuration2().toMinutes()) + "min");
      final MenuItem miDuration_3 = new MenuItem(String.valueOf(UpdateData.getDuration3().toMinutes()) + "min");
      final MenuItem miDuration_4 = new MenuItem(String.valueOf(UpdateData.getDuration4().toMinutes()) + "min");
      final MenuItem miDuration_5 = new MenuItem(String.valueOf(UpdateData.getDuration5().toMinutes()) + "min");
      final MenuItem miDuration_6 = new MenuItem(String.valueOf(UpdateData.getDuration6().toMinutes()) + "min");
   
      menuStation.getItems().addAll(miConnection, menuSaveAs, miExit);
      menuOptions.getItems().addAll(menuPrevision, menuDuration);
      menuPrevision.getItems().addAll(miOneday, miTwoDays, miOneWeek);
      menuDuration.getItems().addAll(miDuration_1, miDuration_2, miDuration_3, 
									 miDuration_4, miDuration_5, miDuration_6);

 
      menuBar.getMenus().addAll(menuStation, menuOptions, menuAbout, menuCalendar);
      ((Group) scene.getRoot()).getChildren().addAll(menuBar);
      
      
      
      
      miDuration_1.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
        	  	if(UpdateData.getPt() != null) {
        	  		UpdateData.getPt().stop();
        	  		UpdateData.getPt().getChildren().remove(1);
        	  		UpdateData.getTimelineLcs().setPeriod(
        	  									UpdateData.getDurationToStart(), 
        	  									UpdateData.getDuration1Default());
        	  		UpdateData.getPt().getChildren().add(1, 
        	  				UpdateData.getTimelineLcs().getTimeline());
        	  		UpdateData.getPt().play();
      			}
          }
       });
      
      
      
      
      miDuration_2.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
        	  	if(UpdateData.getPt() != null) {
        	  		UpdateData.getPt().stop();
        	  		UpdateData.getPt().getChildren().remove(1);
        	  		UpdateData.getTimelineLcs().setPeriod(
        	  									UpdateData.getDurationToStart(), 
        	  									UpdateData.getDuration2());
        	  		UpdateData.getPt().getChildren().add(1, 
        	  				UpdateData.getTimelineLcs().getTimeline());
        	  		UpdateData.getPt().play();
      			}
          }
       });
      
      
      
      miDuration_3.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
        	  	if(UpdateData.getPt() != null) {
        	  		UpdateData.getPt().stop();
        	  		UpdateData.getPt().getChildren().remove(1);
        	  		UpdateData.getTimelineLcs().setPeriod(
        	  									UpdateData.getDurationToStart(), 
        	  									UpdateData.getDuration3());
        	  		UpdateData.getPt().getChildren().add(1, 
        	  				UpdateData.getTimelineLcs().getTimeline());
        	  		UpdateData.getPt().play();
      			}
          }
       });
      
      
      miDuration_4.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
        	  	if(UpdateData.getPt() != null) {
        	  		UpdateData.getPt().stop();
        	  		UpdateData.getPt().getChildren().remove(1);
        	  		UpdateData.getTimelineLcs().setPeriod(
        	  									UpdateData.getDurationToStart(), 
        	  									UpdateData.getDuration4());
        	  		UpdateData.getPt().getChildren().add(1, 
        	  				UpdateData.getTimelineLcs().getTimeline());
        	  		UpdateData.getPt().play();
      			}
          }
       });
      
      
      miDuration_5.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
        	  	if(UpdateData.getPt() != null) {
        	  		UpdateData.getPt().stop();
        	  		UpdateData.getPt().getChildren().remove(1);
        	  		UpdateData.getTimelineLcs().setPeriod(
        	  									UpdateData.getDurationToStart(), 
        	  									UpdateData.getDuration5());
        	  		UpdateData.getPt().getChildren().add(1, 
        	  				UpdateData.getTimelineLcs().getTimeline());
        	  		UpdateData.getPt().play();
      			}
          }
       });
      
      miDuration_6.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
        	  	if(UpdateData.getPt() != null) {
        	  		UpdateData.getPt().stop();
        	  		UpdateData.getPt().getChildren().remove(1);
        	  		UpdateData.getTimelineLcs().setPeriod(
        	  									UpdateData.getDurationToStart(), 
        	  									UpdateData.getDuration6());
        	  		UpdateData.getPt().getChildren().add(1, 
        	  				UpdateData.getTimelineLcs().getTimeline());
        	  		UpdateData.getPt().play();
      			}
          }
       });
      
      
      
      

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
              							 + "\n2016"
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
      
      
      
 
      miExit.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
              System.out.println("Closing...");
        	  primaryStage.close();
          }
       });
      
 

      /**
       * The menu item for the menu Station and its content
       */
      menuSaveAs.setOnAction(new EventHandler<ActionEvent>() {
    	  @Override
          public void handle(ActionEvent event) {
        	  final generateFile myFile = new generateFile();

        	  FileChooser fileChooser = new FileChooser();
        	  fileChooser.setTitle("Enregistrer sous ...");
        	  fileChooser.setInitialDirectory(new File(System.getProperty("user.home"))); 

        	  FileChooser.ExtensionFilter extFilterPdf = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        	  fileChooser.getExtensionFilters().add(extFilterPdf);

        	  FileChooser.ExtensionFilter extFilterJpeg = new FileChooser.ExtensionFilter("JPEG files (*.jpeg)", "*.jpeg");
        	  fileChooser.getExtensionFilters().add(extFilterJpeg);

        	  File file = fileChooser.showSaveDialog(primaryStage);
        	  if (file != null) {
        		  
        		  // Save the file into pdf format
        		  if(fileChooser.getSelectedExtensionFilter().getDescription().equals("PDF files (*.pdf)")){
        			  try {
        				  myFile.toPDF(getTabPane(),file.toPath().toString());
        				  Alert alert = new Alert(AlertType.INFORMATION);
        				  alert.setTitle("Information Dialog");
        				  alert.setHeaderText(null);
        				  alert.setContentText("Sauvegarde en PDF Résussi !!!");
        				  alert.showAndWait();
        			  } catch (Exception e1) {
        				  e1.printStackTrace();
        			  }
        		  }
        		  // Save the file into jpeg format
        		  if(fileChooser.getSelectedExtensionFilter().getDescription().equals("JPEG files (*.jpeg)")){
        			  myFile.toJpeg(getTabPane(), file.toPath().toString());
        			  Alert alert = new Alert(AlertType.INFORMATION);
        			  alert.setTitle("Information Dialog");
        			  alert.setHeaderText(null);
        			  alert.setContentText("Sauvegarde en JPEG Résussi !!!");
        			  alert.showAndWait();
        		  }

        	  }
    		  
    	  }

      });

      
      //---------------------------------------------------------------------------
      /**
       * Create a connection to a data base in order to fetch data and display them
       */
       
      miConnection.setOnAction(new EventHandler<ActionEvent>() {
    	  @Override public void handle(ActionEvent e) {
    		  Stage stage = new Stage();

    		  GridPane 		root 			  = new GridPane();
    		  HBox 	   		btnPanel 		  = new HBox(12);
    		  Label    		lblTitle 		  = new Label("Connexion");
    		  Label 		lblConnexionName  = new Label("Connection Name:");
    		  TextField 	tfdConnectionName = new TextField();
    		  Label 		lblHostName 	  = new Label("Hostname:");
    		  TextField 	tfdHostname 	  = new TextField();
    		  Label 		lblPort 		  = new Label("Port:");
    		  TextField 	tfdPort 		  = new TextField();
    		  Label 		lblUsername 	  = new Label("Username:");
    		  TextField 	tfdUsername 	  = new TextField();
    		  Label 		lblPassword 	  = new Label("Password:");
    		  Button		btnLogin 		  = new Button("Login");
    		  Button 		btnCancel 		  = new Button("Cancel");
    		  PasswordField pwfPassword 	  = new PasswordField();

    		  stage.setTitle("Connexion à la base de données");
    		  
    		  // Set color of the stage
    		  Background backGroundColor = new Background(
    				  new BackgroundFill(Color.LIGHTSKYBLUE,
    				  CornerRadii.EMPTY,
    				  null ));
    		  root.setBackground(backGroundColor);
    		  // Bind to avoid errors
    		  pwfPassword.disableProperty().bind(
    				  						tfdUsername.textProperty().isEmpty());
    		  btnLogin.disableProperty().bind(
    				  				   tfdConnectionName.textProperty().isEmpty()
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
    		  btnLogin.addEventHandler(MouseEvent.MOUSE_ENTERED, 
    				  								new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent event) {
					btnLogin.setEffect(new DropShadow());
				}
    		  });
    		  btnLogin.addEventHandler(MouseEvent.MOUSE_EXITED, 
    				  								new EventHandler<MouseEvent>(){
  				@Override
  				public void handle(MouseEvent event) {
  					btnLogin.setEffect(null);
  				}
      		  });
    		  
    		  btnCancel.addEventHandler(MouseEvent.MOUSE_ENTERED, 
    				  								new EventHandler<MouseEvent>(){
  				@Override
  				public void handle(MouseEvent event) {
  					btnCancel.setEffect(new DropShadow());
  				}
      		  });
    		  btnCancel.addEventHandler(MouseEvent.MOUSE_EXITED, 
    				  								new EventHandler<MouseEvent>(){
    				@Override
    				public void handle(MouseEvent event) {
    					btnCancel.setEffect(null);
    				}
        		  });
    		  
    		  btnLogin.setOnAction(new EventHandler<ActionEvent>(){

				@Override
				public void handle(ActionEvent event) {
					
					// We try to get the value of the port which must be an Integer value
					// If we couldn't we sent an error alert to the user in order to give
					// a valid value
					try{
						portNumber = Integer.parseInt(tfdPort.getText());

						connectionForm.setConnectionForm(
								tfdConnectionName.getText(), 
								tfdHostname.getText(), 
								portNumber,
								tfdUsername.getText(), 
								pwfPassword.getText());

						// Close the window when login button is clicked
						stage.close();

						// Show an alert box to confirm an attempt of connection to the data base
						Alert dialogW = new Alert(AlertType.INFORMATION);
						dialogW.setTitle("Confirmation");
						dialogW.setHeaderText(null); // No header
						dialogW.setContentText(
								"Tentative de connexion à la base de données !");
						dialogW.showAndWait();

					}
					// The port value is not valid and the user have to correct the port number
					catch(Exception e){
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Erreur Saisie");
						alert.setHeaderText(null);
						alert.setContentText("Le port doit être de valeur numérique");
						alert.show();
					}
					
					
				}
    			  
    		  });
    		  
    		  // A click on the cancel button close the window
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


		 public void handle(ActionEvent event) {
		      // Date Picker
		            DatePicker dPicker = new DatePicker();
		            dPicker.setPrefSize(200, 30);
		            dPicker.setShowWeekNumbers(true);
		            Stage dateStage = new Stage();
		            dateStage.setTitle("Calendrier");
		            HBox hbox = new HBox(dPicker);
		            Scene scene = new Scene(hbox, 270, 30);
		            dateStage.setScene(scene);
		            Button button = new Button("Chercher");
		            dPicker.setOnAction(e -> {
		             LocalDate date = dPicker.getValue();
		             button.setOnAction(new EventHandler<ActionEvent>() {

		            	 public void handle(ActionEvent event) {
	                          System.out.println("date rechercée " + date);
	                           if (!Data.checkDate(date)){
	                            Alert alert = new Alert(Alert.AlertType.ERROR);
	                              alert.setHeaderText(null);
	                              alert.setContentText("Aucune données recupérée au " + date);
	                              alert.showAndWait();
	                          }
	                          else{
		                          final Stage dialog = new Stage();
		                          HBox hbox = new HBox();
		                   
		                          SplitPane splitPane1 = new SplitPane();
		                          splitPane1.setOrientation(Orientation.VERTICAL);
		                          SplitPane splitPane2 = new SplitPane();
		                          splitPane2.setOrientation(Orientation.VERTICAL);
		             
		                  
		                          ArrayList<Data> dataTemperatureList = new ArrayList<>();
		                          ArrayList<Data> dataPressureList = new ArrayList<>();
		                          ArrayList<Data> dataHumidityList = new ArrayList<>();
		                          ArrayList<Data> dataAirQualityList = new ArrayList<>();
		                     	  ArrayList<String> hoursList = Hours.getHoursList();
		                     	  for (int i = 0; i < hoursList.size() - 1; ++i){
		                     		 ReceivedData data = new ReceivedData(date,Time.valueOf(hoursList.get(i)),Time.valueOf(hoursList.get(i + 1)));
		                     		 dataTemperatureList.add(data.getTemperatureData());
		                     		 dataPressureList.add(data.getPressureData());
		                     		 dataHumidityList.add(data.getHumidityData());
		                     		 dataAirQualityList.add(data.getAirQualityData());
		                     		
		                     	  }
		                     	  
		                      
		                          LineChartStat lcTemperature
		                          = (LineChartStat) createLineChart("Température",
		                                                "Variation de la température",
		                                                "Heures",
		                                                "Temperature [°C]",
		                                                450,
		                                                290,
		                                                dataTemperatureList);
		                          LineChartStat lcHumidity
		                          =(LineChartStat) createLineChart("Humidité",
                							"Variation de l'humidité",
                							"Heures",
                							"Humidité [%]",
                							450,
                							290,
                							dataHumidityList);

		                          LineChartStat lcPressure
		                          = (LineChartStat) createLineChart("Pression",
                							"Variation de la pression",
                							"Heures",
                							"Pression [hPa]",
                							450,
                							290,
                							dataPressureList);

		                          LineChartStat lcAirQuality
		                          = (LineChartStat) createLineChart("Qualité d'air",
                							"Variation de la qualité d'air",
                							"Heures",
                							"indice[0 - 5.5]",
                							450,
                							290,
                							dataAirQualityList);
		                       
		                          splitPane1.getItems().addAll(lcTemperature, lcPressure);
		                          hbox.getChildren().add(splitPane1);
		                          splitPane2.getItems().addAll(lcHumidity, lcAirQuality);
		                          
		                          hbox.getChildren().add(splitPane2);
		                          Group gr = new Group(hbox);
		                          Scene dialogScene = new Scene(gr);
		                          dialog.setTitle("Valeur récupérés au " + date);
		                          dialog.setScene(dialogScene);
		                          dialog.show();   
	                          }
	                      }
	                  });
		            
		            });
		           
		           
		            hbox.getChildren().add(button);
		            dateStage.show();
		    }
      });
   
      

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
      
      
      /*
      
      Data data1  = new Data(2016, 4, 12,  6, 0, 0,  6.2);
      Data data2  = new Data(2016, 4, 12,  8, 0, 0, 10.8);
      Data data3  = new Data(2016, 4, 12, 10, 0, 0, 12.4);
      Data data4  = new Data(2016, 4, 12, 12, 0, 0, 15.9);
      Data data5  = new Data(2016, 4, 12, 14, 0, 0, 22.8);
      Data data6  = new Data(2016, 4, 12, 16, 0, 0, 25.7);
      Data data7  = new Data(2016, 4, 12, 18, 0, 0, 20.3);
      Data data8  = new Data(2016, 4, 12, 20, 0, 0, 14.1);
      Data data9  = new Data(2016, 4, 12, 22, 0, 0,  8.5);
      Data data10 = new Data(2016, 4, 13, 00, 0, 0,  7.2);
      Data data11 = new Data(2016, 4, 13,  2, 0, 0,  6.8);
      Data data12 = new Data(2016, 4, 13,  4, 0, 0,  2.9);
      Data data13 = new Data(2016, 4, 13,  7, 7, 0,-35.0);

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
      
      */
      

      lcsTemperature
              = (LineChartStat) createLineChart("Température",
                      							null,
                      							"Heures",
                      							"Temperature [°C]",
                      							450,
                      							290,
                      							dataList);

      lcsHumidity
              = (LineChartStat) createLineChart("Humidité",
                      							null,
                      							"Heures",
                      							"Humidité [%]",
                      							450,
                      							290,
                      							dataList);

      lcsPressure
              = (LineChartStat) createLineChart("Pression",
                      							null,
                      							"Heures",
                      							"Pression [hPa]",
                      							450,
                      							290,
                      							dataList);

      lcsAirQuality
              = (LineChartStat) createLineChart("Qualité d'air",
                      							null,
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
    		        public void changed(ObservableValue<? extends Tab> ov, 
    		        					Tab tabTemperature, 
    		        					Tab tabHumidity) {

    		        	/*
    		        	UpdateData.getPt().stop();
    		        	UpdateData.getPt().getChildren().remove(1, 5);
    		        	
    		        	UpdateData.getTimelineLcsTemperature().setPeriod(Duration.seconds(0), Duration.seconds(30));
    		        	UpdateData.getTimelineLcsHumidity().setPeriod   (Duration.seconds(0), Duration.seconds(30));
    		        	UpdateData.getTimelineLcsPressure().setPeriod   (Duration.seconds(0), Duration.seconds(30));
    		        	UpdateData.getTimelineLcsAirQuality().setPeriod (Duration.seconds(0), Duration.seconds(30));
    		        	
    		        	UpdateData.getPt().getChildren().add(UpdateData.getTimelineLcsTemperature().getTimeline());
    		        	UpdateData.getPt().getChildren().add(UpdateData.getTimelineLcsHumidity().getTimeline());
    		        	UpdateData.getPt().getChildren().add(UpdateData.getTimelineLcsPressure().getTimeline());
    		        	UpdateData.getPt().getChildren().add(UpdateData.getTimelineLcsAirQuality().getTimeline());
    		        	System.out.println("fewifewifniewnfewnfnewnfenffnef");
    		        	System.out.println(UpdateData.getPt().getChildren().size());
    		        	*/
    		        	
    		        	lcsTemperature.refreshChart();
    		        	lcsHumidity.refreshChart();
    		        	lcsPressure.refreshChart();
    		        	lcsAirQuality.refreshChart();
    		        	
    		        	UpdateData.getPt().play();
    		        }
    		    }
    		);
      
      ((Group) scene.getRoot()).getChildren().addAll(tabPan);

      /**
       * The humidity Progress Bar
       */
      progressTextValue = new Text(430, 150, "%");
      progressTextValue.setFont(Font.font("Arial", FontWeight.BOLD, 20));
      //progressTextValue.setVisible(true);
      pbHumidity.setLayoutX(400);
      pbHumidity.setLayoutY(200);
      pbHumidity.setPrefSize(100, 20);
      pbHumidity.getTransforms().setAll(new Rotate(-90, 0, 0));
      rootGroup.getChildren().add(pbHumidity);
      rootGroup.getChildren().add(progressTextValue);
 
      
    //-------------------------TEST GAUGE RADIAL---------------------------------------------
 
      lcdTemperature = LcdBuilder.create()
    		  	.prefWidth(220)
    		  	.prefHeight(100)
    		  	.layoutX(560)
    		  	.layoutY(90)
    		  	.decimals(1)
    		  	/*Lcd.STYLE_CLASS_FLAT_MIDNIGHT_BLUE*/
    		  	.styleClass(Lcd.STYLE_CLASS_LIGHTGREEN_BLACK)
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
    		  			.layoutY(370)
    		  			.build();

      rootGroup.getChildren().add(pressureGauge);
   
   }
      
 
      
      // Before updating data we need to connect to the data base
      //connectionForm = new ConnectionForm();
	   
      
		        

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
   * @param image
   */
  public static void updateImageConnect(Image image) {
	  ivConnect.setImage(image);
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
      double copyValue = pbHumidity.getProgress()*100;
	  String textValue = String.format("%.2f", copyValue);
	  textValue += " %";
      progressTextValue.setText(textValue);
   }

   

   
 
   /**
    * 
    *
    * @param lcs
    * @param data
    */
   public static void updateLcs(LineChartStat lcs, Data data) {
      lcs.updateSeries(data);
   }

   
   
   public static LineChartStat getLcsTemperature() {
	   return lcsTemperature;
   }
   
   
   public static LineChartStat getLcsHumidity() {
	   return lcsHumidity;
   }
   
   
   public static LineChartStat getLcsPressure() {
	   return lcsPressure;
   }
   
   
   public static LineChartStat getLcsAirQuality() {
	   return lcsAirQuality;
   }
   
  
   
   /**
    * 
 	*
 	* @return
 	*/
   public static ConnectionForm getConnectionForm(){
	   return  connectionForm;
   }
   
   public static Group getRootGroup(){
	   return rootGroupCopy;
   }
   
   public static boolean getIsConnected(){
	   return isConnected;
   }
   public static void setIsConnected(boolean status){
	   isConnected = status;
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
      final NumberAxis   yAxis = new NumberAxis();

      xAxis.setLabel(xAxisLabel);
      yAxis.setLabel(yAxisLabel);

      LineChartStat lcs = new LineChartStat(title,
              seriesName,
              xAxis,
              yAxis,
              dataList);

      lcs.setPrefSize(xSize, ySize);
      lcs.setMaxSize(xSize, ySize);
      lcs.setAnimated(true);
      lcs.setLegendVisible(false);
      xAxis.setAnimated(true);
      yAxis.setAnimated(true);

      return lcs;
   }

   /**
    * This method returns the current tabPane.
    *
    *
    * @return A copy of the actual pane
    */
   private TabPane getTabPane() {
      return copyPane;
   }

   
   /** A copy of the tabPane */
   private 		  TabPane 		copyPane   = new TabPane();
   /**  */
   private static ImageView 	iv 		   = new ImageView();
   
   private static ImageView			ivConnect = new ImageView();
   /**  */
   private static ProgressBar 	pbHumidity 		= new ProgressBar();
   /**  */
   private static Text   		progressTextValue;
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
   /**  */
   private static ConnectionForm connectionForm = new ConnectionForm();;
   /**  */
   private        Timeline 		 timeline;
   /**  */
   private final  long           PERIOD_CONNECTION = 10000;
   /**  */
   private final  long 			 PERIOD_UPDATE 	   = 20000;//30000
   /**  */
   private final  long			 PERIOD_INITIATE   = 3000;
   
   private static Group rootGroupCopy = new Group();
   
   private static boolean isConnected = false;
   
   private double refreshValue;
   
   private int portNumber;
   
   
   
   

   /**
    * Main method for lunching the user window.
    *
    * @param args
    */
   public static void main(String[] args) {
      Application.launch(MainWindow.class, args);
   }

}
