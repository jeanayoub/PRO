/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data_processing;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDPixelMap;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;



/**
 * 
 * @author Pascal SEKLEY
 */
public class generateFile {

	/**
	 * Constructor.
	 * 
	 */
	public generateFile(){

	}

	/**
	 * 
	 *
	 * @throws IOException
	 */
	public void toPDF(TabPane tabPaneStat, String pdfFilePathname) throws IOException{
		float xPos = 100;
		final float xPosDefault = xPos;
		float yPos = 610;
		final float yPosDefault = yPos;
		final String heigLogoPath = "meteoImages/HEIG-VD_Logo.png";

		try {
			PDPageContentStream contentStream_1 = null;
			PDPageContentStream contentStream_2 = null;
			BufferedImage buffImage = null;
			PDXObjectImage ximage = null;
			BufferedImage  xHeigLogoImage = null;
			PDXObjectImage ximageLogoHeig = null;
			
			try (PDDocument document = new PDDocument()) {
				PDPage page_1 = new PDPage();
				PDPage page_2 = new PDPage();
				document.addPage(page_1);
				document.addPage(page_2);

				page_1 = (PDPage)document.getDocumentCatalog().getAllPages().get(0);
				contentStream_1 = new PDPageContentStream(document, page_1, true, true);
				contentStream_2 = new PDPageContentStream(document, page_2, true, true);
				//try (PDPageContentStream content = new PDPageContentStream(document,page)) {
				//page_1 = (PDPage)document.getDocumentCatalog().getAllPages().get(0);

				xHeigLogoImage = ImageIO.read(ResourceLoader.load(heigLogoPath));
				ximageLogoHeig = new PDPixelMap(document, xHeigLogoImage);
				contentStream_1.drawXObject(ximageLogoHeig, 80, 700, ximageLogoHeig.getWidth()-130, ximageLogoHeig.getHeight()-50);


				contentStream_1.beginText();
				contentStream_1.setFont(PDType1Font.HELVETICA_BOLD_OBLIQUE, 26);
				//java.awt.Color color = null;
				
				contentStream_1.setNonStrokingColor(255,0,0);
				contentStream_1.moveTextPositionByAmount(420, 720);
				contentStream_1.drawString("PRO-2016");
				contentStream_1.endText();
				
				
				contentStream_1.beginText();
				contentStream_1.setFont(PDType1Font.HELVETICA, 29);
				contentStream_1.setNonStrokingColor(28,134,238);
				contentStream_1.moveTextPositionByAmount(65, 600);//80, 750
				contentStream_1.drawString("Graphes de variations météorologiques");
				

				contentStream_1.setFont(PDType1Font.HELVETICA, 16);
				contentStream_1.moveTextPositionByAmount(80, 200);//80, 400
				contentStream_1.endText();


				page_2 = (PDPage)document.getDocumentCatalog().getAllPages().get(1);
				for(int i = 0; i < tabPaneStat.getTabs().size(); i++){
					buffImage = generate_png_from_container(tabPaneStat.getTabs().get(i).getContent());
					ximage = new PDJpeg(document, buffImage, 1.0f);

					contentStream_2.drawXObject(ximage, xPos, yPos, ximage.getWidth()-70, ximage.getHeight()-110);
					yPos -= 200;
				}
				contentStream_1.close();
				contentStream_2.close();
				/*		
					content.endText();

					content.beginText();
					content.setFont(PDType1Font.HELVETICA, 16);
					content.moveTextPositionByAmount(80, 700);
					content.drawString("Température : " );
					content.endText();
				 */

				//}

				// We sent to the default value for further treatments
				xPos = xPosDefault;
				yPos = yPosDefault;

				document.save(pdfFilePathname);

			}

			System.out.println("your file created in : " + 

					System.getProperty("user.dir"));
		} catch (COSVisitorException ex) {
			Logger.getLogger(generateFile.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	
	/*
	 * 
	 */

	public static BufferedImage generate_png_from_container(Node node) {
		SnapshotParameters param = new SnapshotParameters();
		param.setDepthBuffer(true);
		WritableImage snapshot = node.snapshot(param, null);
		BufferedImage tempImg = SwingFXUtils.fromFXImage(snapshot, null);
		BufferedImage img = null;
		byte[] imageInByte;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(tempImg, "png", baos);
			baos.flush();
			imageInByte = baos.toByteArray();
			baos.close();
			InputStream in = new ByteArrayInputStream(imageInByte);
			img = ImageIO.read(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//the final image sent to the PDJpeg
		return img;
	}

	/**
	 * 
	 *
	 * @param node
	 */
	public void toJpeg(Node node, String jpegfilename){
		//String fileName = "Courbe_Meteo.jpg";
		WritableImage wi;

		SnapshotParameters parameters = new SnapshotParameters();
		parameters.setFill(Color.HONEYDEW);

		int imageWidth  = (int) node.getBoundsInLocal().getWidth();
		int imageHeight = (int) node.getBoundsInLocal().getHeight();

		wi = new WritableImage(imageWidth, imageHeight);
		node.snapshot(parameters, wi);

		Image image = wi;

		BufferedImage bufImageARGB = SwingFXUtils.fromFXImage(image, null);
		BufferedImage bufImageRGB = new BufferedImage(bufImageARGB.getWidth(), bufImageARGB.getHeight(), BufferedImage.OPAQUE);

		Graphics2D graphics = bufImageRGB.createGraphics();
		graphics.drawImage(bufImageARGB, 0, 0, null);

		try {
			ImageIO.write(bufImageRGB, "jpg", new File(jpegfilename));
		} catch (IOException e) {
			e.getMessage();
		}

		graphics.dispose();

		System.out.println("your file created in : "+ System.getProperty("user.dir"));

	}



}
