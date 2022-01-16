package SSF.weather.Model;



import java.util.logging.Level;
import java.util.logging.Logger;

import SSF.weather.WeatherApplication;
import jakarta.json.Json;
import jakarta.json.JsonObject;

public class weatherModel {
    private final Logger logger = Logger.getLogger(WeatherApplication.class.getName());
    private String temp;
    private String des;
    private String main;
    private String icon;
    private String units="metric";
    private String cityName;
    private String timeStamp;
    private String lon;
    private String lat;
    public static weatherModel create(JsonObject O,String temp,String units,String cityName, String timeStamp,String lon, String lat){
        final weatherModel w = new weatherModel();
        w.setTemp(temp);
        w.setDes(O.get("description").toString());
        w.setMain(O.get("main").toString());
        w.setIcon(O.get("icon").toString());
        w.setCityName(cityName);
        w.setUnits(units);
        w.setTimeStamp(timeStamp);
        w.setLon(lon);
        w.setLat(lat);
        return w;
    }
    
    public static weatherModel createUsingJsonObject(JsonObject O){
        final weatherModel w = new weatherModel();
        w.setTemp(O.get("temperature").toString().replace("\"", ""));
        w.setDes(O.get("description").toString());
        w.setMain(O.get("main").toString());
        w.setIcon(O.get("icon").toString());
        w.setCityName(O.get("cityName").toString());
        w.setTimeStamp(O.get("timeStamp").toString().replace("\"", ""));
        w.setLon(O.get("lon").toString().replace("\"", ""));
        w.setLat(O.get("lat").toString().replace("\"", ""));
        return w;

    }
    
    public void setLon(String longitude){
        this.lon=longitude;
    }
    public void setLat(String Latitude){
        this.lat=Latitude;
    }
    public String getLat(){
        return this.lat;
    }
    public String getLon(){
        return this.lon;
    }
    public void setTimeStamp(String timeStamp){
        this.timeStamp=timeStamp;
    }
    public String getTimeStamp(){
        return this.timeStamp;
    }
    public void setCityName(String cityName){
        this.cityName=cityName;
    }
    public String getCityName(){
        return this.cityName;
    }
    public void setUnits(String units){
        this.units=units;
    }
    public String getUnits(){
        return this.units;
    }
    public void setTemp(String temp){
        this.temp=temp;
    }
    public void setDes(String des){
        this.des=des;
    }
    public void setMain(String main){
        this.main=main;
    }
    public void setIcon(String icon){
        this.icon=icon;
    }
    public String getTemp(){
        return this.temp;
    }
    public String getIcon(){
        return this.icon.replace("\"", "");
    }
    public String getMain(){
        return this.main.replace("\"", "");
    }
    public String getDes(){
        return this.des.replace("\"", "");
    }
    public JsonObject toJson(){
        JsonObject jack=Json.createObjectBuilder()
                        .add("cityName", cityName)
                        .add("main",main.replace("\"", ""))
                        .add("icon", icon.replace("\"", ""))
                        .add("description", des.replace("\"", ""))
                        .add("temperature", temp)
                        .add("timeStamp",timeStamp)
                        .add("lon",lon)
                        .add("lat",lat)
                        .add("units",units)
                        .build();
        //logger.log(Level.INFO, "building Json>>>>%s".formatted(jack));

        return jack;
    }
}
