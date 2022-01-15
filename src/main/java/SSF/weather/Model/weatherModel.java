package SSF.weather.Model;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class weatherModel {
    private String temp;
    private String des;
    private String main;
    private String icon;
    private String units;
    public static weatherModel create(JsonObject O,String temp,String units){
        final weatherModel w = new weatherModel();
        w.setTemp(temp);
        w.setDes(O.get("description").toString());
        w.setMain(O.get("main").toString());
        w.setIcon(O.get("icon").toString());
        w.setUnits(units);
        
        return w;
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
}
