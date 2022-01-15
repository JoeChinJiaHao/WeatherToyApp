package SSF.weather.repositories;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import SSF.weather.Constants;
import java.util.Optional;
@Repository
public class WeatherRepository {
    @Autowired
    @Qualifier(Constants.Bean_Cache_SVC)
    private RedisTemplate<String, String> template;

    public void saveRedis(String cityName, String value){
        template.opsForValue().set(this.normalise(cityName), value,5L,TimeUnit.MINUTES);
    }
    public String normalise(String k){
        return k.trim().toLowerCase();
    }
    public boolean hasKey(String k){
        return template.hasKey(this.normalise(k));
    }
    public Optional<String> get(String cityName){
        String value = template.opsForValue().get(this.normalise(cityName));
        return Optional.ofNullable(value);
    }
}
