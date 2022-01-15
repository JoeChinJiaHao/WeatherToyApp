package SSF.weather.Model;


import jakarta.json.Json;
import jakarta.json.JsonObject;

public class weatherModel {
    private String temp;
    private String des;
    private String main;
    private String icon;
    private String units;
    private String cityName;
    public static weatherModel create(JsonObject O,String temp,String units,String cityName){
        final weatherModel w = new weatherModel();
        w.setTemp(temp);
        w.setDes(O.get("description").toString());
        w.setMain(O.get("main").toString());
        w.setIcon(O.get("icon").toString());
        w.setCityName(cityName);
        w.setUnits(units);
        
        return w;
    }

    public static weatherModel createUsingJsonObject(JsonObject O){
        final weatherModel w = new weatherModel();
        w.setTemp(O.get("temperature").toString());
        w.setDes(O.get("description").toString());
        w.setMain(O.get("main").toString());
        w.setIcon(O.get("icon").toString());
        w.setCityName(O.get("cityname").toString());
        return w;

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

        return Json.createObjectBuilder()
                .add("cityName", cityName)
                .add("main",main)
                .add("icon", icon)
                .add("description", des)
                .add("temperature", temp)
                .build();
    }
}
