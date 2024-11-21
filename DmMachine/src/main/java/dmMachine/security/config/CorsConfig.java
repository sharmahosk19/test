package dmMachine.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // or specify your allowed origins
                .allowedMethods("GET", "POST", "PUT", "DELETE") // or specify which methods are allowed
                .allowedHeaders("*"); // or specify which headers are allowed
    }
}
