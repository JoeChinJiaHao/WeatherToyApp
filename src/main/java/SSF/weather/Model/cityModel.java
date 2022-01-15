package SSF.weather.Model;

import java.io.Serializable;

public class cityModel implements Serializable {
    private String cityName;
    
    public void setName(String cityName){
        this.cityName=cityName;
    }
    public String getName(){
        return this.cityName;
    }
}
