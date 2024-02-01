package crm.demo.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

@Configuration
public class CloudinaryConfig {
    private final String CLOUD_NAME = "dpxkin5lm";
    private final String API_KEY = "736466128745899";
    private final String API_SECRET = "iGNtfMY9Xajm7f5SalrFEuw7eKE";

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", CLOUD_NAME);
        config.put("api_key", API_KEY);
        config.put("api_secret", API_SECRET);

        return new Cloudinary(config);
    }
}
