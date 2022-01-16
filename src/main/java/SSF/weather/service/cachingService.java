package SSF.weather.service;

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
import org.springframework.data.auditing.IsNewAwareAuditingHandler;
import org.springframework.stereotype.Service;

import SSF.weather.WeatherApplication;
import SSF.weather.Model.weatherModel;
import SSF.weather.repositories.WeatherRepository;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;


@Service
public class cachingService {
    private final Logger logger = Logger.getLogger(WeatherApplication.class.getName());
    
    @Autowired
    private WeatherRepository weatherRepo;

    public void save(List<weatherModel> list,String cityName){
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        logger.log(Level.INFO, "input for saving>>>%s".formatted(list));
        list.stream()
            .forEach(v->arrBuilder.add(v.toJson()));
        JsonArray Ja = arrBuilder.build();
        weatherRepo.saveRedis(cityName, Ja.toString());
        logger.log(Level.INFO, "saving>>>%s".formatted(Ja.toString()));
    }

    public Optional<List<weatherModel>> get(String cityName){
        //get items from repo
        logger.log(Level.INFO, "getmethod>>>");
        Optional<String> opt = weatherRepo.get(cityName);
        
        //check if result is null
        if(opt.isEmpty()){
            logger.log(Level.INFO, "Empty redis");
            return Optional.empty();
        }else{
            JsonObject Jo;
            logger.log(Level.INFO, "from redis>>>>%s".formatted(opt.get().toString()));
            JsonArray jsonArr = parseJsonArray(opt.get());
            //logger.log(Level.INFO, "jsonArr>>>%s".formatted(jsonArr));
            /* List<weatherModel> weatherList = jsonArr.stream()
                                        .map(v->(JsonObject)v)
                                        .forEach(weatherModel::createUsingJsonObject)
                                        .collect(Collectors.toList()); */

            List<weatherModel> weatherList=new ArrayList<weatherModel>();

            for(int i=0;i<jsonArr.size();i++){
                //logger.log(Level.INFO, "from array1>>>>%s".formatted(jsonArr.get(i).toString().replace("\"{","{").replace("}\"", "}")));
                String s = jsonArr.get(i).toString();
                //logger.log(Level.INFO, "from array1>>>>%s".formatted(s));
                Jo=this.parseStringToJsonObject(s);
                weatherList.add(weatherModel.createUsingJsonObject(Jo));
                //logger.log(Level.INFO, "from array2>>>>%s".formatted(Jo));
                
            }
            return Optional.of(weatherList);
        }   

    }
    public JsonObject parseStringToJsonObject(String s){
            InputStream is = new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8));
            JsonReader reader = Json.createReader(is);
            JsonObject data = reader.readObject();
            //System.out.println(">>>%s".formatted(data));
            return data;
    }
    private JsonArray parseJsonArray(String s){
        //logger.log(Level.INFO, "From parsing>>>%s".formatted(s));
        try(InputStream is = new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8))){
            JsonReader reader = Json.createReader(is);
            JsonArray JA=reader.readArray();
            //logger.log(Level.INFO, "From parsing2>>>%s".formatted(JA));
            return JA;
        }catch(Exception ex){
            logger.log(Level.WARNING, "Parsing",ex);
        }
        //handle empty array thats returned
        return Json.createArrayBuilder().build();
    }


}
