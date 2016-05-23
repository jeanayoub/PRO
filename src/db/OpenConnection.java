package db;

import java.sql.SQLException;

public class OpenConnection {
	
	public OpenConnection(ConnectionForm connectionForm){
		
		try {
			System.out.println("OpenConexDebug");
			dbConn = new DBConnection(connectionForm);
		} catch (SQLException e) {
			System.out.println("Error in openning connection to the database");
			e.printStackTrace();
		}
	}
	
	public static DBConnection getConnectionLink(){
		return dbConn;
	}

	
	private static DBConnection dbConn; 

}
