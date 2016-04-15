/*
 * Pascal
 */

package data_processing;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javax.imageio.ImageIO;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;



/**
 * 
 * @author Pascal SEKLEY
 */
public class generateFile {
   
   public generateFile(){
      
   }

   public void toPDF() throws IOException{
      
      try {
         String fileName = "Données_Météo.pdf";
         try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
           
            
            document.addPage(page);
            try (PDPageContentStream content = new PDPageContentStream(document,page)) {
               content.beginText();
               content.setFont(PDType1Font.HELVETICA, 26);
               
               content.moveTextPositionByAmount(220, 750);
               content.drawString("Données récent de notre météo. ");
               content.endText();
               
               
               content.beginText();
               content.setFont(PDType1Font.HELVETICA, 16);
               content.moveTextPositionByAmount(80, 700);
               content.drawString("Température : " );
               content.endText();
       
            }
            document.save(fileName);
         }
  
         System.out.println("your file created in : "+ System.getProperty("user.dir"));
      } catch (COSVisitorException ex) {
         Logger.getLogger(generateFile.class.getName()).log(Level.SEVERE, null, ex);
      }
   }
   
   
   
   public void toJpeg(Node node){
      String fileName = "Courbe_Météo.jpg";
      WritableImage wi;

      SnapshotParameters parameters = new SnapshotParameters();
      parameters.setFill(Color.HONEYDEW);

      int imageWidth = (int) node.getBoundsInLocal().getWidth();
      int imageHeight = (int) node.getBoundsInLocal().getHeight();

      wi = new WritableImage(imageWidth, imageHeight);
      node.snapshot(parameters, wi);
      
      Image image = wi;
      
      
      BufferedImage bufImageARGB = SwingFXUtils.fromFXImage(image, null);
      BufferedImage bufImageRGB = new BufferedImage(bufImageARGB.getWidth(), bufImageARGB.getHeight(), BufferedImage.OPAQUE);

      Graphics2D graphics = bufImageRGB.createGraphics();
      graphics.drawImage(bufImageARGB, 0, 0, null);

      try {
         ImageIO.write(bufImageRGB, "jpg", new File(fileName));
      } catch (IOException e) {
         e.getMessage();
      }

      graphics.dispose();

      System.out.println("your file created in : "+ System.getProperty("user.dir"));

   }
   
   

}
