package SSF.weather.Controllers;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import SSF.weather.Constants;
import SSF.weather.URLBuilderCustom;
import SSF.weather.WeatherApplication;
import SSF.weather.Model.weatherModel;
import SSF.weather.service.cachingService;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;

@RestController
@RequestMapping(path="/weather",produces=MediaType.APPLICATION_JSON_VALUE)
public class WeatherRestController {
    private final Logger logger = Logger.getLogger(WeatherApplication.class.getName());
    @Autowired
    private cachingService cachingSVC;
    
    @GetMapping(path="{id}")
    public ResponseEntity<String> getWeatherAsJson(@PathVariable String id){

        
        URLBuilderCustom url1=new URLBuilderCustom(Constants.BaseURL, id, Constants.APIKey);
        Optional<List<weatherModel>> opt = cachingSVC.get(url1.getQueryString());
        List<weatherModel> result = new ArrayList<weatherModel>();
        RequestEntity<Void> req = RequestEntity
                                        .get(url1.getURI())
                                        .accept(MediaType.APPLICATION_JSON)
                                        .build();
        if(opt.isPresent()){

            
            for(weatherModel wwwm:opt.get()){
                result.add(wwwm);
            }

        }else{
            //get from web
            
            try{
                RestTemplate template = new RestTemplate();
                ResponseEntity<String> resp=template.exchange(req, String.class);
                try(InputStream is = new ByteArrayInputStream(resp.getBody().getBytes(StandardCharsets.UTF_8))){
                    JsonReader reader = Json.createReader(new BufferedReader(new InputStreamReader(is,StandardCharsets.UTF_8)));
                    JsonObject data = reader.readObject();
                    JsonArray weatherFromWeb=data.getJsonArray("weather");
                    String Temp = data.getJsonObject("main").get("temp").toString();
                    String timeStamp = Constants.getTimeStamp();
                    String longitude = data.getJsonObject("coord").get("lon").toString();
                    String Latitude = data.getJsonObject("coord").get("lat").toString();
                    
                    for(JsonValue j:weatherFromWeb){
                        result.add(weatherModel.create(j.asJsonObject(), Temp,"metric",id,timeStamp,longitude,Latitude));
                    }
                    //save to caching
                    cachingSVC.save(result, url1.getQueryString());
                    
                }catch(Exception e){
                    logger.log(Level.INFO,">>>%s".formatted(e));
                    
                    return null;
                }
        }finally{}

        
        }
        //build json    
        final JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        result.stream().forEach(v->arrBuilder.add(v.toJson()));
    
        
        return ResponseEntity.ok(arrBuilder.build().toString());

    }
}
