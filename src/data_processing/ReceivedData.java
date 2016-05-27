package data_processing;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import db.Data;
import db.Data.Sensor;

public class ReceivedData {
	
	public ReceivedData(LocalDate date, Time beginTime, Time endTime){
		this.date = date;
		this.beginTime = beginTime;
		this.endTime = endTime;
	}
	
	
	public Data getTemperatureData(){
		return Data.averageValue(Sensor.TEMPERATURE, date, beginTime, endTime);
	}
	
	public Data getHumidityData(){
		return Data.averageValue(Sensor.HUMIDITY, date, beginTime, endTime);
	}
	
	public Data getPressureData(){
		return Data.averageValue(Sensor.PRESSURE, date, beginTime, endTime);
	}
	
	public Data getAirQualityData(){
		return Data.averageValue(Sensor.AIR_QUALITY, date, beginTime, endTime);
	}
	
	public Data getRadiancyData(){
		return Data.averageValue(Sensor.RADIANCY, date, beginTime, endTime);
	}
	
	public ArrayList<Data> getRainData(){
		return Data.getValueInDay(Sensor.RAIN, date);
	}
	
	private LocalDate date;
	private Time beginTime;
	private Time endTime;
}
