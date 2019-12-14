package com.moonwalker.temperature;

public class IoTData
{

    private double temphalo;
    private double humidity;
    private String timestamphalo;
    private double tempErkely;
    private String timestampErkely;
    private String message;


    public IoTData()
    {

    }

    public double getHumidity()
    {
        return humidity;
    }
    public void setHumidity(double humidity)
    {
        this.humidity = humidity;
    }

    public double getTemphalo() {
        return temphalo;
    }

    public void setTemphalo(double temphalo) {
        this.temphalo = temphalo;
    }

    public double getTempErkely() {
        return tempErkely;
    }

    public void setTempErkely(double tempErkely) {
        this.tempErkely = tempErkely;
    }

    public String getTimestamphalo() {
        return timestamphalo;
    }

    public void setTimestamphalo(String timestamphalo) {
        this.timestamphalo = timestamphalo;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestampErkely() {
        return timestampErkely;
    }

    public void setTimestampErkely(String timestampErkely) {
        this.timestampErkely = timestampErkely;
    }
}
