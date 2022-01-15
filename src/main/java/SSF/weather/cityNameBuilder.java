package SSF.weather;

public class cityNameBuilder {
    private final String cityName;
    private String stateCode="";
    private String countryCode="";

    public cityNameBuilder(String cityName){
        this.cityName=cityName.replace(" ","%20");
    }

    public cityNameBuilder withStateCode(String stateCode){
        this.stateCode=stateCode;
        return this;
    }
    public cityNameBuilder withCountryCode(String countryCode){
        this.countryCode=countryCode;
        return this;
    }
    public String getQString(){
        String result=cityName;
        
        if(stateCode!=""){
            result=result+","+stateCode;
        }
        if(countryCode!=""){
            result=result+","+countryCode;
        }
        return result;
    }


}
