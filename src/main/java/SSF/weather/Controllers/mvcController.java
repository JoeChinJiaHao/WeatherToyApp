package SSF.weather.Controllers;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


import SSF.weather.Constants;
import SSF.weather.URLBuilderCustom;
import SSF.weather.cityNameBuilder;
import SSF.weather.Model.cityModel;
import SSF.weather.Model.weatherModel;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;

@Controller
@RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
public class mvcController {
    private static final Logger logger = Logger.getLogger(mvcController.class.getName());

    
    @PostMapping("/weather")
    public String displayWaether(@RequestBody MultiValueMap<String,String> form, Model model){
        cityModel weatherM = new cityModel();
        String name =  form.getFirst("cityName");
        String stateCode =  form.getFirst("stateCode");
        String countryCode =  form.getFirst("countryCode");
        cityNameBuilder cName= new cityNameBuilder(name)
                                .withStateCode(stateCode)
                                .withCountryCode(countryCode);

        weatherM.setName(cName.getQString());
        String lang =  form.getFirst("lang");
        String units = form.getFirst("units");
        URLBuilderCustom urlB=new URLBuilderCustom(Constants.BaseURL, weatherM.getName(), Constants.APIKey)
                                    .withLang(lang)
                                    .withUnits(units);
        units=urlB.getUnits();
        logger.log(Level.INFO, "url>>>>>%s".formatted(urlB.getQueryString()));
        RequestEntity<Void> req = RequestEntity
                                        .get(urlB.getURI())
                                        .accept(MediaType.APPLICATION_JSON)
                                        .build();
        try{
            RestTemplate template = new RestTemplate();
            ResponseEntity<String> resp=template.exchange(req, String.class);
            //best to return JSON object then code after getting that object
            try(InputStream is = new ByteArrayInputStream(resp.getBody().getBytes(StandardCharsets.UTF_8))){
                        JsonReader reader = Json.createReader(new BufferedReader(new InputStreamReader(is,StandardCharsets.UTF_8)));
                        JsonObject data = reader.readObject();
                        JsonArray weatherFromWeb=data.getJsonArray("weather");
                        String Temp = data.getJsonObject("main").get("temp").toString();
                        List<weatherModel> list = new ArrayList<weatherModel>();
                        for(JsonValue j:weatherFromWeb){
                            list.add(weatherModel.create(j.asJsonObject(), Temp,units,weatherM.getName()));
                        }

                        logger.log(Level.INFO, "list>>>%s".formatted(list.get(0).getUnits()));
                        //logger.log(Level.INFO, "json>>>%s".formatted(weatherFromWeb.getJsonObject(0).getString("description")));
              
                        model.addAttribute("weatherList", list);
                    }catch(Exception e){
                        model.addAttribute("errorMessage", e.getMessage());
                        return "Error";
                    }

        }catch(RestClientException ex){
            String[] errspt =ex.getMessage().toString().split(":",2) ;
            String[] errM = errspt[1].replace("\"","").replace("{", "").replace("}", "").split(",");
            String[] finalErrM=errM[errM.length-1].split(":");
            String[] firstErrM=errM[0].split(":");
            
            model.addAttribute("errorCode", errspt[0]);
            model.addAttribute("errorCodeNumber", firstErrM[firstErrM.length-1]);
            model.addAttribute("errorMessage", finalErrM[finalErrM.length-1]);
            return "ErrorClient";
        }
        model.addAttribute("cityName", weatherM.getName().replace("%20"," "));
        //model.addAttribute("icon",icon);
        return "weatherOfCity";
    }

}
