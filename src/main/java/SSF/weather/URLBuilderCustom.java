package SSF.weather;

import java.net.URI;

import org.thymeleaf.expression.Uris;

public class URLBuilderCustom {
    private final String BaseURL;
    private final String cityName;
    private final String appid;
    private String mode="";
    private String units="&units=metric";
    private String lang="&lang=en";

    public URLBuilderCustom (String BaseURL,String cityName,String appid){
        this.BaseURL=BaseURL;
        this.cityName="?q="+cityName;
        this.appid="&appid="+appid;
    }
    public URLBuilderCustom withMode(String mode){
        if(!mode.equals(""))
        this.mode="&mode="+mode;
        return this;
    }
    public String getUnits(){
        return this.units.substring(units.indexOf("=")+1,units.length());
    }

    public URLBuilderCustom withUnits(String units){
        if(!units.equals(""))
        this.units="&units="+units;
        return this;
    }
    public URLBuilderCustom withLang(String lang){
        if(!lang.equals(""))
        this.lang="&lang="+lang;
        return this;
    }
    public String getQueryString(){
        String results=BaseURL+cityName+appid+units+lang+mode;
        return results;

    }
    public URI getURI(){
        String results=BaseURL+cityName+appid+units+lang+mode;
        return URI.create(results);
    }

}
