package utils;

import java.util.ArrayList;

public class Hours {
	public static ArrayList<String> getHoursList(){
		ArrayList<String> hoursList = new ArrayList<>();
		final String extension = ":00:00";
		 for (short i = 0; i < 24; i++){
     		 if (i < 10)
     		   hoursList.add( "0" +i + extension);
     		 else
     			 hoursList.add(i + extension);
     	  }
		 return hoursList;
	}
	
}
