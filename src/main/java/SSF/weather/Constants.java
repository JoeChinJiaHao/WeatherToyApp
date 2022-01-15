package SSF.weather;

//import java.sql.Time;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;



public class Constants {
    public static final Logger logger = Logger.getLogger(Constants.class.getName());
    public final static String APIKey = System.getenv("MyWeatherAPIKey") ; 
    public static final String BaseURL="https://api.openweathermap.org/data/2.5/weather";
    public static final String Bean_Cache_SVC="Weather_Cache";
    public static final String ENV_REDIS_PASSWORD="Redis_Pass_Weather";
    
    public String getQueryURL(String q, String appid){
        String URL=BaseURL;

        return URL;
    }
    public String getTimeStamp(){
        
        long time = new Date().getTime();
        java.sql.Timestamp ts = new java.sql.Timestamp(time);
        String Ts=ts.toString().split("\\.")[0];
        return Ts;
    }

    public static void main(String[] args) {
        logger.log(Level.INFO,"Value from env>>>%s".formatted(APIKey));
        /* cityNameBuilder cName =new cityNameBuilder("hong kong");
                                        //.withCountryCode("123")
                                        //.withStateCode("statecode");
        URLBuilderCustom urlB = new URLBuilderCustom(BaseURL, cName.getQString(),APIKey)
                                        //.withMode("xml")
                                        .withLang("th")
                                        .withUnits("metric"); */
        
   
        //logger.log(Level.INFO, "TimeStamp>>> %s".formatted(new Constants().getTimeStamp())+">>Pass>>%s".formatted(System.getenv(new Constants().ENV_REDIS_PASSWORD)));
        //RestTemplate restTemplate = new RestTemplate();//create an instance of restTemplate to handle the call/post
        //ResponseEntity<String> resp = restTemplate.getForEntity(finalURL, responseType, uriVariables)
    }


}
