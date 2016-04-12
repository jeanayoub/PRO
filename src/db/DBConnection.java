/*
 -----------------------------------------------------------------------------------
 Laboratoire : PRO
 Fichier     : DBConnection.java
 Auteur(s)   : Jean AYOUB
 Date        : 12 avr. 2016
 But         : 
 Remarque(s) :
 Compilateur : jdk 1.8.0_60
 -----------------------------------------------------------------------------------
 */

package db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Class.
 *
 * @author Jean AYOUB
 * @date 18 janv. 2016
 * @version 1.0
 */
public class DBConnection {

	// NEED TO BE UPDATED
	private static final String URL =  "jdbc:mysql://localhost:3306/xxx?user=root&password=";

	
	private Connection connection;
	public  Statement  statement;


	public DBConnection () throws SQLException{

		connection = DriverManager.getConnection(URL);	
		statement  = connection.createStatement();
	}

	
	public ResultSet executeQuery (String sql) throws SQLException {
		return statement.executeQuery(sql);
	}

	
	public int executeUpdate (String sql) throws SQLException {
		return statement.executeUpdate(sql);
	}
	

	public PreparedStatement prepareStatement (String sql) throws SQLException {
		return connection.prepareStatement(sql);
	}
	
	
	public boolean execute (String sql) throws SQLException {
		return statement.execute(sql);
	}
	
	
	public CallableStatement prepareCall (String sql) throws SQLException {
		return connection.prepareCall(sql);
	}

	
	public void close() {
		
		try {
			
			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}	
	}
}
