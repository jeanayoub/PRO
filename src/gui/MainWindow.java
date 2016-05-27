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
import java.sql.SQLException;
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
import db.DBConnection;


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
   @SuppressWarnings("static-access")
public void start(Stage primaryStage) throws IOException {
	   
	   // Icon to be place for active and inactive connection   
	    //final Image imActiv   = new Image(ResourceLoader.load("meteoImages/actif.png"));
	    //final Image imInactiv = new Image(ResourceLoader.load("meteoImages/inactif.png"));
	    
	    //final Text textActiv = new Text(740, 27, "Actif");
	    //final Text textInactiv = new Text(740, 27, "Inactif");
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
            UpdateData.timer.cancel();
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
      ivConnect.setX(710);
      ivConnect.setY(10);
      
      ivConnect.setImage(imInactiv);
      rootGroup.getChildren().add(iv);
      rootGroup.getChildren().add(ivConnect);
      rootGroup.getChildren().add(textInactiv);

      /**
       * The Menu bar
       */
      final MenuBar menuBar 	  = new MenuBar();
      final Menu 	menuStation   = new Menu("Station");
      final Menu 	menuOptions   = new Menu("Option");
      final Menu 	menuAbout     = new Menu("A propos");
      final Menu 	menuCalendar  = new Menu("Calendrier");
      final Menu 	menuSaveAs    = new Menu("Enregistrer Sous");
      final Menu 	menuPrevision = new Menu("Prévision météorologique");

      
      final MenuItem exit       = new MenuItem("Quitter");
      final MenuItem oneday 	= new MenuItem("1 jour");
      final MenuItem twoDays 	= new MenuItem("2 jours");
      final MenuItem oneWeek 	= new MenuItem("1 semaine");
      final MenuItem connection = new MenuItem("Connexion");
      final MenuItem disconnection = new MenuItem("Déconnexion");
      final MenuItem refreshPeriod = new MenuItem("Fixer le délai de rafraichissement");
      

      menuStation.getItems().add(connection);
      menuStation.getItems().add(disconnection);
      menuStation.getItems().add(menuSaveAs);
      menuStation.getItems().add(exit);
      
      menuOptions.getItems().add(menuPrevision);
      menuOptions.getItems().add(refreshPeriod);
      menuPrevision.getItems().add(0, oneday);
      menuPrevision.getItems().add(1, twoDays);
      menuPrevision.getItems().add(2, oneWeek);
      
      disconnection.setDisable(true);

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
      
      refreshPeriod.setOnAction(new EventHandler<ActionEvent>(){

		@Override
		public void handle(ActionEvent event) {
			// TODO Auto-generated method stub
			Stage stage = new Stage();
			stage.setTitle("Période de rafraichissement");

			GridPane root = new GridPane();
			Label 		lblPeriod  = new Label("Temps de raffraichissement (Secondes):");
			TextField 	tfdPeriod = new TextField();
			Button btnValidate = new Button ("Valider");
			
			//double value = 0;

			
			root.add(lblPeriod, 0, 1);
			root.add(tfdPeriod, 1, 1);
			root.add(btnValidate, 2, 2);

			root.setAlignment(Pos.CENTER);
			root.setPadding(new Insets(20));
			root.setHgap(10);
			root.setVgap(15);

			stage.setMinWidth(100);
			stage.setMinHeight(50);
			stage.setScene(new Scene(root));
			
			// Set action on btnValidate
			btnValidate.setOnAction(new EventHandler<ActionEvent>(){

				@Override
				public void handle(ActionEvent event) {
					// We try to get the value if its a valid numerical value, the program can
					// continue, otherwise we send an alert error message so that the user
					// try again
					try{
						refreshValue = Double.parseDouble(tfdPeriod.getText());
						System.out.println(refreshValue);
						stage.close();
					}
					catch(Exception e){
						Alert alert = new Alert(AlertType.ERROR);
		        		alert.setTitle("Erreur Saisie");
		        		alert.setHeaderText(null);
		        		alert.setContentText("Veuillez entrer une valeur numérique");
		        		alert.show();
					}
				
				}
				
			});
			
			
			stage.show();
		}
    	  
      });
      
 
      exit.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
        	  UpdateData.timer.cancel();
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
        				  alert.setContentText("Sauvegarde en PDF Réussi !!!");
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
       
      connection.setOnAction(new EventHandler<ActionEvent>() {
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
					DBConnection testConnection = null;
					
					try{
						portNumber = Integer.parseInt(tfdPort.getText());

						connectionForm.setConnectionForm(
								tfdConnectionName.getText(), 
								tfdHostname.getText(), 
								portNumber,
								tfdUsername.getText(), 
								pwfPassword.getText());
						
						// We try to get a connection to the database
						System.out.println("Waiting to connect");
						if (connectionForm.getFormStatus() && (MainWindow.getIsConnected()==false)) {
							System.out.println("Apres waiting");
							try{
								testConnection = new DBConnection(MainWindow.getConnectionForm());
								testConnection.close();
								isConnected = true;
								System.out.println("Connection Successful");

								// Close the window when login button is clicked
								stage.close();

								Alert alert = new Alert(AlertType.CONFIRMATION);
								alert.setTitle("Confirmation");
								alert.setHeaderText(null);
								alert.setContentText("Connexion réussi !");
								alert.show();
								
								// Update the connectivity icon image
								ivConnect.setImage(imActiv);
				        		rootGroup.getChildren().remove(imInactiv);
				        		rootGroup.getChildren().remove(textInactiv);
				        		rootGroup.getChildren().add(textActiv);
				        		
				        		disconnection.setDisable(false);
								
								// Start updating
								UpdateData updateData = new UpdateData(PERIOD_INITIATE, PERIOD_UPDATE);

							}
							catch(SQLException e){
								System.out.println("Connection failed, try again");
								
								// We reset the connection form for the next try
								MainWindow.getConnectionForm().resetConnectionForm();
								
								// Show an alert box to the user
				    			Alert alert = new Alert(AlertType.ERROR);
			          			alert.setTitle("Erreur");
			          			alert.setHeaderText(null);
			          			alert.setContentText("Echec de connexion. Veuillez recommencer svp !");
			          			alert.show();
							}
							finally{
								// Disability the menu connection in order to disconnect before
								// trying to get connected again
								connection.setDisable(true);
							}

						}
						//System.out.println("Isconnected = " + getIsConnected());

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
		            dPicker.setPrefSize(230, 30);
		            dPicker.setShowWeekNumbers(true);
		            Stage dateStage = new Stage();
		            dateStage.setTitle("Calendrier");
		            HBox hbox = new HBox(dPicker);
		            Scene scene = new Scene(hbox, 230, 30);
		            dateStage.setScene(scene);
		            Button button = new Button("Chercher");
		            dPicker.setOnAction(e -> {
		             LocalDate date = dPicker.getValue();
		             button.setOnAction(new EventHandler<ActionEvent>() {

		                  public void handle(ActionEvent event) {
		                      System.out.println("date rechercée " + date);
		                      final Stage dialog = new Stage();
		                      VBox dialogVbox = new VBox(100);
		                      ReceivedData data = new ReceivedData(date);
		                      dialogVbox.getChildren().add(new Text("Données récupéré au " + date));
		                      dialogVbox.getChildren().add(new Text("Temperature : "));
		                      for (int i = 0; i < data.getTemperatureData().size(); i++){
		                        Data dataR = data.getTemperatureData().get(i);
		                        dialogVbox.getChildren().add(new Text(dataR.getValue() + "Dégré"));
		                      }
		                      
		                      Scene dialogScene = new Scene(dialogVbox, 300, 200);
		                      dialog.setScene(dialogScene);
		                      dialog.show();   
		                  }
		              });
		            
		            });
		           
		           
		            hbox.getChildren().add(button);
		            dateStage.show();
		    }
      });
   
      
      disconnection.setOnAction(new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			if(isConnected){
				connection.setDisable(false);
				isConnected = false;
				MainWindow.getConnectionForm().resetConnectionForm();
				disconnection.setDisable(true);
				
				resetStation();
				
				// Update the connectivity icon image
				ivConnect.setImage(imInactiv);
        		rootGroup.getChildren().remove(imActiv);
        		rootGroup.getChildren().remove(textActiv);
        		rootGroup.getChildren().add(textInactiv);

			}
			
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
    		        public void changed(ObservableValue<? extends Tab> ov, 
    		        					Tab tabTemperature, 
    		        					Tab tabHumidity) {
    		        	
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
   
   
   
   
   
   public static ImageView getIvConnect(){
	   return ivConnect;
   }
      
   
   
   
   public static void updateConnectivityIcon(String status){
	// Update the connectivity icon image
	   if(status.equals("imActiv")){
		   
		   MainWindow.getRootGroup().getChildren().remove(imActiv);
		   MainWindow.getRootGroup().getChildren().remove(textActiv);
		   ivConnect.setImage(imActiv);
		   //MainWindow.getRootGroup().getChildren().add(textInactiv);
	   }
	   else if(status.equals("imInactiv")){
		   ivConnect.setImage(imInactiv);
		   MainWindow.getRootGroup().getChildren().remove(imActiv);
		   MainWindow.getRootGroup().getChildren().remove(textActiv);
		   //MainWindow.getRootGroup().getChildren().add(textInactiv);
	   }
   }
   

   /**
   *
   *
   * @param 
   */
   public static void resetStation(){
	   updateImageView(null);
	   updateLcdTemperature(0.);
	   updatePressureGauge(0.);
//	   lcsTemperature.getData().clear();
//	   lcsHumidity.getData().clear();
//	   lcsPressure.getData().clear();
//	   lcsAirQuality.getData().clear();
	   updatePbHumidity(0.);
   }

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
	  updateImageView(image);
	  
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
   
   private static final Image imActiv   = new Image(ResourceLoader.load("meteoImages/actif.png"));
   private static final Image imInactiv = new Image(ResourceLoader.load("meteoImages/inactif.png"));
   private static final Text textActiv = new Text(740, 27, "Actif");
   private static final Text textInactiv = new Text(740, 27, "Inactif");
      


   /**
    * Main method for lunching the user window.
    *
    * @param args
    */
   public static void main(String[] args) {
      Application.launch(MainWindow.class, args);
   }

}
