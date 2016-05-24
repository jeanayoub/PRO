/*
 -----------------------------------------------------------------------------------
 Laboratoire : 
 Fichier     : ResourceLoader.java
 Auteur(s)   : Jean AYOUB
 Date        : 24 mai 2016
 But         : 
 Remarque(s) :
 Compilateur : jdk 1.8.0_60
 -----------------------------------------------------------------------------------
 */
package data_processing;

import java.io.InputStream;

/**
 * Class.
 *
 * @author Jean AYOUB
 * @date 24 mai 2016
 * @version 1.0
 */
final public class ResourceLoader {
	public static InputStream load (String path) {
		InputStream input = ResourceLoader.class.getResourceAsStream(path);
		
		if (input == null) {
			input = ResourceLoader.class.getResourceAsStream("/" + path);
		}
		return input;
	}
}
