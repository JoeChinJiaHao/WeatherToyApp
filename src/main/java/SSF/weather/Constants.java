package SSF.weather;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Constants {
    public static final Logger logger = Logger.getLogger(Constants.class.getName());
    public final static String APIKey = System.getenv("MyWeatherAPIKey") ; 
    public static final String BaseURL="https://api.openweathermap.org/data/2.5/weather";
    public static final String Bean_Cache_SVC="Weather_Cache";
    public String getQueryURL(String q, String appid){
        String URL=BaseURL;

        return URL;
    }


    public static void main(String[] args) {
        logger.log(Level.INFO,"Value from env>>>%s".formatted(APIKey));
        cityNameBuilder cName =new cityNameBuilder("hong kong");
                                        //.withCountryCode("123")
                                        //.withStateCode("statecode");
        URLBuilderCustom urlB = new URLBuilderCustom(BaseURL, cName.getQString(),APIKey)
                                        //.withMode("xml")
                                        .withLang("th")
                                        .withUnits("metric");
        
        
        logger.log(Level.INFO, "combined URL>>> %s".formatted(urlB.getQueryString()));
        RestTemplate restTemplate = new RestTemplate();//create an instance of restTemplate to handle the call/post
        //ResponseEntity<String> resp = restTemplate.getForEntity(finalURL, responseType, uriVariables)
    }


}
