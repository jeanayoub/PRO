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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Locale;

import db.*;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Gauge.NeedleSize;
import eu.hansolo.medusa.Gauge.ScaleDirection;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.medusa.Marker;
import eu.hansolo.medusa.Section;
import eu.hansolo.medusa.TickLabelLocation;
import eu.hansolo.medusa.TickMarkType;
import eu.hansolo.medusa.skins.ModernSkin;
import eu.hansolo.medusa.skins.SlimSkin;
import gui.LineChartStat;
import extfx.scene.control.*;
import data_processing.UpdateData;
import data_processing.generateFile;


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
      final Dialog<CalendarView> dialogCv = new Dialog<CalendarView>();
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
      Data data13 = new Data(2016, 4, 13,  7, 7, 35.0);

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
                      							"Temperature [C]",
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
                      							"Pression [bar]",
                      							450,
                      							290,
                      							dataList);

      lcsWind
              = (LineChartStat) createLineChart("Vent",
                      							"Variation de la vitesse du vent",
                      							"Heures",
                      							"Vitesse [km/h]",
                      							450,
                      							290,
                      							dataList);

      tabTemperature.setContent(lcsTemperature);
      tabHumidity.setContent(lcsHumidity);
      tabPressure.setContent(lcsPressure);
      tabWind.setContent(lcsWind);

      /**
       * !!! Just to test the updateSeries method. !!!
       */
      lcsTemperature.updateSeries(data13);
      //lcsTemperature.updateSeries(Temperature.getLastData());

      tabTemperature.setClosable(false);
      tabHumidity.setClosable(false);
      tabPressure.setClosable(false);
      tabWind.setClosable(false);

      ((Group) scene.getRoot()).getChildren().addAll(tabPan);

      /**
       * The Progress Bars
       */
      pbPressure.setLayoutX(65);
      pbPressure.setLayoutY(450);
      pbPressure.setPrefSize(200, 30);
      rootGroup.getChildren().add(pbPressure);

      pbHumidity.setLayoutX(400);
      pbHumidity.setLayoutY(200);
      pbHumidity.setPrefSize(100, 20);
      pbHumidity.getTransforms().setAll(new Rotate(-90, 0, 0));
      rootGroup.getChildren().add(pbHumidity);

    //-------------------------TEST GAUGE RADIAL---------------------------------------------
      
//      pbTemperature.setLayoutX(650);
//      pbTemperature.setLayoutY(200);
//      pbTemperature.setPrefSize(100, 20);
//      pbTemperature.getTransforms().setAll(new Rotate(-90, 0, 0));
//      rootGroup.getChildren().add(pbTemperature);
      
    //-------------------------TEST GAUGE RADIAL---------------------------------------------
      Gauge gauge = GaugeBuilder.create()
              .prefSize(160,160)
              .scaleDirection(ScaleDirection.COUNTER_CLOCKWISE)
              .tickLabelLocation(TickLabelLocation.OUTSIDE)
              .startAngle(90)
              .angleRange(270)
              .minValue(-1)
              .maxValue(2)
              .zeroColor(Color.ORANGE)
              .majorTickMarkType(TickMarkType.TRIANGLE)
              .sectionsVisible(true)
              .sections(new Section(1.5, 2, Color.rgb(200, 0, 0, 0.5)))
              .areasVisible(true)
              .areas(new Section(-0.5, 0.5, Color.rgb(0, 200, 0, 0.5)))
              .markersVisible(true)
              .markers(new Marker(0.75, "Marker 1", Color.MAGENTA))
              .needleColor(Color.DARKCYAN)
              .needleSize(NeedleSize.THICK)
              .build();
      gauge.setLayoutX(565);
      gauge.setLayoutY(90);
      gauge.setValue(1.5);
      rootGroup.getChildren().add(gauge);

      
      
      
      
      // !!! JUST A RANDOM VALUE !!!!!!!!
      UpdateData updateData = new UpdateData(5000, 72 * 100000);

   }
   
//   public static void initTemp(){
// 	  //radial = new GaugeBuilder().create().prefWidth(150);
//   }

   /**
    *
    *
    * @param iv
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
   public static void updatePbPressure(double value) {
      pbPressure.setProgress(value);
   }

   /**
    *
    *
    * @param value
    */
   public static void updatePbHumidity(double value) {
      pbHumidity.setProgress(value);
   }

   /**
    *
    *
    * @param value
    */
//   public static void updatePbTemperature(double value) {
//      pbTemperature.setProgress(value);
//   }

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
   public static void updateLcsWind(Data data) {
      lcsWind.updateSeries(data);
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
   private TabPane copyPane = new TabPane();
   /**  */
   private static ImageView iv = new ImageView();
   /**  */
   private static ProgressBar pbPressure    = new ProgressBar();
   /**  */
   private static ProgressBar pbHumidity    = new ProgressBar();
   /**  */
   //private static ProgressBar pbTemperature = new ProgressBar();
   private static Gauge gauge;
   /**  */
   private static LineChartStat lcsTemperature;
   /**  */
   private static LineChartStat lcsHumidity;
   /**  */
   private static LineChartStat lcsPressure;
   /**  */
   private static LineChartStat lcsWind;

   /**
    * Main method for lunching the user window.
    *
    * @param args
    */
   public static void main(String[] args) {
      Application.launch(MainWindow.class, args);

   }

}
