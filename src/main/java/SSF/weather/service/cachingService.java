package SSF.weather.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SSF.weather.WeatherApplication;
import SSF.weather.Model.weatherModel;
import SSF.weather.repositories.WeatherRepository;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;


@Service
public class cachingService {
    private final Logger logger = Logger.getLogger(WeatherApplication.class.getName());
    
    @Autowired
    private WeatherRepository weatherRepo;

    public void save(List<weatherModel> list,String cityName){
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        list.stream()
            .forEach(v->arrBuilder.add(v.toJson().toString()));
        weatherRepo.saveRedis(cityName, arrBuilder.build().toString());
    }

    public Optional<List<weatherModel>> get(String cityName){
        //get items from repo
        Optional<String> opt = weatherRepo.get(cityName);
        //check if result is null
        if(opt.isEmpty()){
            return Optional.empty();
        }else{
            JsonArray jsonArr = parseJsonArray(opt.get());
            List<weatherModel> weatherList = jsonArr.stream()
                                        .map(v->(JsonObject) v)
                                        .map(weatherModel::createUsingJsonObject)
                                        .collect(Collectors.toList());
            return Optional.of(weatherList);
        }   

    }

    private JsonArray parseJsonArray(String s){
        try(InputStream is = new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8))){
            JsonReader reader = Json.createReader(new BufferedReader(new InputStreamReader(is)));
            return reader.readArray();
        }catch(Exception ex){
            logger.log(Level.WARNING, "Parsing",ex);
        }
        //handle empty array thats returned
        return Json.createArrayBuilder().build();
    }


}
