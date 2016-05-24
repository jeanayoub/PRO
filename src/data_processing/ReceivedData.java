package data_processing;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import db.Data;
import db.Data.Sensor;

public class ReceivedData {
	
	public ReceivedData(LocalDate date){
		this.date = date;
	}
	
	
	public ArrayList<Data> getTemperatureData(){
		return Data.getValueInDay(
				Sensor.TEMPERATURE, date);
	}
	
	public ArrayList<Data> getHumidityData(){
		return Data.getValueInDay(
				Sensor.HUMIDITY, date);
	}
	
	public ArrayList<Data> getPressureData(){
		return Data.getValueInDay(Sensor.PRESSURE, date);
	}
	
	public ArrayList<Data> getAirQualityData(){
		return Data.getValueInDay(Sensor.AIR_QUALITY, date);
	}
	
	public ArrayList<Data> getRadiancyData(){
		return Data.getValueInDay(Sensor.RADIANCY, date);
	}
	
	public ArrayList<Data> getRainData(){
		return Data.getValueInDay(Sensor.RAIN, date);
	}
	
	private LocalDate date;
}
