package db;

public class ConnectionForm {
	
	
	public ConnectionForm(){
		this.connectionName = "";
		this.hostname = "";
		this.port = 0;
		this.username = "";
		this.password = "";
		this.formFilled = false;
	}
	
	public ConnectionForm(String connectionName, String hostname, int port, String username, String password){
		this.connectionName = connectionName;
		this.hostname = hostname;
		this.port = port;
		this.username = username;
		this.password = password;
		
		formFilled = true;
	}
	
	public String getHostname(){
		return hostname;
	}
	
	public int getPort(){
		return port;
	}
	public String getUsername(){
		return username;
	}
	
	public String getPassword(){
		return password;
	}
	
	public String getConnectionName(){
		return connectionName;
	}
	
	public boolean getFormStatus(){
		return formFilled;
	}
	
	
	
	private  String connectionName;
	private  String hostname;
	private  int port;
	private  String username;
	private  String password;
	private boolean formFilled = false;

}
