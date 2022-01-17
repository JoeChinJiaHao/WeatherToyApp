package SSF.weather;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


import java.nio.charset.StandardCharsets;
//import java.sql.Time;
import java.util.Date;

import java.util.logging.Logger;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;



import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;



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
    public static String getTimeStamp(){
        
        long time = new Date().getTime();
        java.sql.Timestamp ts = new java.sql.Timestamp(time);
        String Ts=ts.toString().split("\\.")[0];
        return Ts;
    }

    public static void main(String[] args) throws IOException {
        //logger.log(Level.INFO,"Value from env>>>%s".formatted(APIKey));
        String s= "{\"cityName\":\"China\",\"main\":\"Clouds\",\"icon\":\"04n\",\"description\":\"overcast clouds\",\"temperature\":\"10.71\",\"timeStamp\":\"2022-01-16 16:09:46\",\"lon\":\"-99.2333\",\"lat\":\"25.7\"}";
        System.out.println(s);
        //String t = s.replace("\"", "'");
        //System.out.println( t);
        try( InputStream is = new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8))) {
            JsonReader reader = Json.createReader(is);
            JsonObject data = reader.readObject();
            System.out.println(">>>%s".formatted(data));
            is.close();
        }
        
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
