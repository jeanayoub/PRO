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

import data_processing.generateFile;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

import java.util.ArrayList;
import java.util.Locale;

import db.Data;
import gui.LineChartStat;
import extfx.scene.control.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

/**
 * Class MainWindow represents the application's main window.
 *
 * @author Jean AYOUB
 * @date 06 avril 2016
 * @version 1.3
 */
public class MainWindow extends Application {
   TabPane myPane = new TabPane();

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
            System.out.println("Closing...");
         }
      });

      /**
       * Declaration and definition of all the Text fields
       */
      final Text text1 = new Text(100, 80, "Météo actuelle");
      final Text text2 = new Text(60, 400, "Pression atmosphérique");
      final Text text3 = new Text(370, 80, "Humidité");
      final Text text4 = new Text(500, 270, "Statistiques");
      final Text text5 = new Text(600, 80, "Thermomètre");

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
      final Image miSunny = new Image("file:meteoImages/maSunny.png");
      final Image miSunnyCloud = new Image("file:meteoImages/maSunnyCloud.png");
      final Image miRainLight = new Image("file:meteoImages/maRainLight.png");
      final Image miRainHeavy = new Image("file:meteoImages/maRainHeavy.png");
      final Image miSnow = new Image("file:meteoImages/maSnow.png");

      /**
       * This code sets the image in place
       */
      final ImageView iv1 = new ImageView();
      iv1.setFitHeight(180);
      iv1.setFitWidth(180);
      iv1.setX(60);
      iv1.setY(100);
      iv1.setImage(miSunnyCloud);
      rootGroup.getChildren().add(iv1);

      /**
       * The Menu bar
       */
      final MenuBar menuBar = new MenuBar();
      final Menu menuStation = new Menu("Station");
      final Menu menuOptions = new Menu("Option");
      final Menu menuAbout = new Menu("A propos");
      final Menu menuCalendar = new Menu("Calendrier");
      final Menu menuSaveAs = new Menu("Enregistrer sous ");
      
      final MenuItem menuJpeg = new MenuItem("JPEG");
      final MenuItem menuPdf = new MenuItem("PDF");
      
      menuStation.getItems().add(menuSaveAs);
      menuSaveAs.getItems().add(0, menuPdf);
      menuSaveAs.getItems().add(1, menuJpeg);
      
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
      final Dialog dialogInfo = new Dialog();
      dialogInfo.getDialogPane().setPrefSize(130, 200);
      dialogInfo.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
      Node closeButtonDialogInfo = dialogInfo.getDialogPane().lookupButton(
              ButtonType.CLOSE);
      closeButtonDialogInfo.managedProperty().bind(
              closeButtonDialogInfo.visibleProperty());

      final Text textDialogInfo = new Text("Station Météo \nVersion 1.0 "
              + "\n\nCopyrights © "
              + "\nPRO HEIG-VD "
              + "\n\nR. Combremont "
              + "\nM. Dupraz "
              + "\nP. Sekley "
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

      
      

      

      /**
       * The menu item for the menu Calendar and its content which is the Calendar
       * view
       */
      final MenuItem miCalendarShow = new MenuItem("Afficher");
      final CalendarView cv = new CalendarView(Locale.FRENCH);
      menuCalendar.getItems().addAll(miCalendarShow);

      /**
       * The dialog box to be shown once pressed on the Show menu item.
       */
      final Dialog dialogCv = new Dialog();
      dialogCv.setGraphic(cv);
      dialogCv.getDialogPane().setPrefSize(250, 250);
      dialogCv.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
      Node closeButtonDialogCv = dialogCv.getDialogPane().lookupButton(ButtonType.CLOSE);
      closeButtonDialogCv.managedProperty().bind(
              closeButtonDialogCv.visibleProperty());

      /**
       * Shows the dialog box
       */
      miCalendarShow.setOnAction(new EventHandler<ActionEvent>() {
         @Override
         public void handle(ActionEvent event) {
            /**
             * Just to make the close button close the dialog box
             */
            closeButtonDialogCv.setVisible(false);
            dialogCv.showAndWait();
         }
      });

      /**
       * The Tab Pane
       */
      final TabPane tabPan = new TabPane();
      final Tab tabTemperature = new Tab("Température");
      final Tab tabHumidity = new Tab("Humidité");
      final Tab tabPressure = new Tab("Pression");
      final Tab tabWind = new Tab("Vent");

      tabPan.getTabs().addAll(tabTemperature, tabHumidity, tabPressure, tabWind);
      tabPan.setLayoutX(350);
      tabPan.setLayoutY(280);
      myPane = tabPan;
      
      

      /**
       * !!! This is just for testing !!!
       */
      ArrayList<Data> dataList = new ArrayList<Data>();
      Data data1 = new Data(2016, 4, 12, 8, 0, 10.8);
      Data data2 = new Data(2016, 4, 12, 10, 0, 12.4);
      Data data3 = new Data(2016, 4, 12, 12, 0, 15.9);
      Data data4 = new Data(2016, 4, 12, 14, 0, 22.8);
      Data data5 = new Data(2016, 4, 12, 16, 0, 25.7);
      Data data6 = new Data(2016, 4, 12, 18, 0, 20.3);
      Data data7 = new Data(2016, 4, 12, 20, 0, 14.1);
      Data data8 = new Data(2016, 4, 12, 22, 0, 8.5);

      dataList.add(data1);
      dataList.add(data2);
      dataList.add(data3);
      dataList.add(data4);
      dataList.add(data5);
      dataList.add(data6);
      dataList.add(data7);
      dataList.add(data8);

      tabTemperature.setContent(createTabTemperature(dataList));
      tabHumidity.setContent(createTabHumidity(dataList));
      tabPressure.setContent(createTabPressure(dataList));
      tabWind.setContent(createTabWind(dataList));

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
      pbHumidity.getTransforms().setAll(new Rotate(-90, 0, 0));
      rootGroup.getChildren().add(pbHumidity);

      final ProgressBar pbTempreture = new ProgressBar();
      pbTempreture.setLayoutX(650);
      pbTempreture.setLayoutY(200);
      pbTempreture.setPrefSize(100, 20);
      pbTempreture.getTransforms().setAll(new Rotate(-90, 0, 0));
      rootGroup.getChildren().add(pbTempreture);
   }

   /**
    * This function creats the graph for the Temperature tab.
    *
    * @return Line chart
    */
   private LineChart<String, Number>
           createTabTemperature(ArrayList<Data> dataList) {

      final CategoryAxis xAxis = new CategoryAxis();
      final NumberAxis yAxis = new NumberAxis();

      xAxis.setLabel("Heures");
      yAxis.setLabel("Temperature [C]");

      LineChartStat lcs = new LineChartStat("Température",
              "Variation de la température", xAxis, yAxis, dataList);

      lcs.setPrefSize(450, 290);
      lcs.setMaxSize(450, 290);

      return lcs;
   }

   /**
    * This function creats the graph for the Humidity tab.
    *
    * @param dataList
    * @return Line chart
    */
   private LineChart<String, Number> createTabHumidity(ArrayList<Data> dataList) {

      final CategoryAxis xAxis = new CategoryAxis();
      final NumberAxis yAxis = new NumberAxis();

      xAxis.setLabel("Heures");
      yAxis.setLabel("Humidité [%]");

      LineChartStat lcs = new LineChartStat("Humidité",
              "Variation de l'humidité", xAxis, yAxis, dataList);

      lcs.setPrefSize(450, 290);
      lcs.setMaxSize(450, 290);

      return lcs;
   }

   private LineChart<String, Number> createTabPressure(ArrayList<Data> dataList) {

      final CategoryAxis xAxis = new CategoryAxis();
      final NumberAxis yAxis = new NumberAxis();

      xAxis.setLabel("Heures");
      yAxis.setLabel("Pression [bar]");

      LineChartStat lcs = new LineChartStat("Pression",
              "Variation de la pression", xAxis, yAxis, dataList);

      lcs.setPrefSize(450, 290);
      lcs.setMaxSize(450, 290);

      return lcs;
   }

   private LineChart<String, Number> createTabWind(ArrayList<Data> dataList) {

      final CategoryAxis xAxis = new CategoryAxis();
      final NumberAxis yAxis = new NumberAxis();

      xAxis.setLabel("Heures");
      yAxis.setLabel("Vitesse du vent [km/h]");

      LineChartStat lcs = new LineChartStat("Vent",
              "Variation de la vitesse du vent",
              xAxis, yAxis, dataList);

      lcs.setPrefSize(450, 290);
      lcs.setMaxSize(450, 290);

      return lcs;

   }
   //----------------------------------Pascal----------------------------------------
   /**
    * This function the current tabPane.
    *
    * 
    * @return the actual pane
    */
   
    private TabPane getTabPane(){
       return myPane;
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
