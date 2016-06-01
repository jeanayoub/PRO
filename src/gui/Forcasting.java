package gui;

import java.sql.SQLException;

import db.Data;
import db.Data.Sensor;

public class Forcasting {
	//private Data dataHourAgoPressure;
    private Data dataActualPressure;
    private double pressureHourAgo;
    private double pressureNow;

    // Constructor: get back the data
    public Forcasting()
    {
        try
        {
            //get the last pressure value
        	dataActualPressure = Data.getLastData(Sensor.PRESSURE);
            pressureNow = dataActualPressure.getValue();
            
          //get the pressure value an hour ago
            pressureHourAgo = Data.getOneHourBeforeValue(Sensor.PRESSURE);
            System.out.println("Presion Hour Ago = " + pressureHourAgo);
            System.out.println("Presion Now = " + pressureNow);
            

        }
        catch(SQLException e)
        {
        	e.printStackTrace();
        }

    }

    public int makeForcasting()
    {
    	int value = -1;
        double pressureDifference;
        pressureDifference = pressureHourAgo - pressureNow;
        
        System.out.println("PresionDiffe = " + pressureDifference);
        //Good weather
        if(Double.compare(pressureDifference, -1.5) <= 0) //pressureDifference <= -1.5
        {
            value =  1;
        }
        //No change in weather
        if(Double.compare(pressureDifference, -1.5) > 0 && Double.compare(pressureDifference, 1.5) < 0)//pressureDifference > -1.5 && pressureDifference < 1.5
        {
            value = 0;System.out.println("value = "+value);
        }
        // bad weather
        if(Double.compare(pressureDifference, 1.5) >= 0 && Double.compare(pressureDifference, 2.5) < 0)//pressureDifference >= 1.5 && pressureDifference < 2.5
        {
            value = 2;
        }
        // really bad weather
        if(Double.compare(pressureDifference, 2.5) >= 0)//pressureDifference >= 2.5
        {
            value = 3;
        }
        
        return value;
    }

}
