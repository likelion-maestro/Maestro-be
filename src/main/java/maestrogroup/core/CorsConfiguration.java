package maestrogroup.core;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("http://mastero:3000", "http://mastero.com:3000") // 클라이언트 주소
                .allowedHeaders("*")
                .allowedMethods("POST", "GET", "DELETE", "PATCH", "OPTIONS")
                .allowCredentials(true)
                .exposedHeaders("X-AUTH-TOKEN")
                .maxAge(3600);
    }
}
