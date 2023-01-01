package maestrogroup.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")  // 프로그램에서 제공하는 URL
                .allowedOrigins("http://localhost:3000", "localhost:3000", "https://localhost:3000", "https://maestrometronome.netlify.app/", "http://maestrometronome.netlify.app/") // 클라이언트 주소. 어떤 출처들을 허용할 것인지
                .allowedHeaders("*") // 어떤 헤더들을 허용할 것인지
                .allowedMethods("POST", "GET", "DELETE", "PATCH", "OPTIONS") // 어떤 메소드들을 허용할 것인지
                .allowCredentials(true)  // Credentialed Request : 인증 관련 헤더를 포함할때 사용하는 요청 => true 로 설정해줘야 클라이언트 측에서 보낸것을 받을 수 있다.
                .maxAge(3600); // preflight 요청에 대한 응답을 브라우저에서 캐싱하는 시간
        //                 .exposedHeaders("X-AUTH-TOKEN")
    }
}

/*
    preflight
    Simple request가 아닌 요청 메시지보다 먼저 보내는 메시지로, 브라우저는 응답값으로 실제 데이터 전송 여부를 판단.

    CORS는 응답이 Access-Control-Allow-Credentials: true 을 가질 경우, Access-Controll-Allow-Origin의 값으로 *를 사용하지 못하게 막고 있다
    Access-Control-Allow-Credentials: true를 사용하는 경우는 사용자 인증이 필요한 리소스 접근이 필요한 경우인데,
    만약 Access-Control-Allow-Origin: *를 허용한다면,
    CSRF 공격에 매우 취약해져 악의적인 사용자가 인증이 필요한 리소스를 마음대로 접근할 수 있음.
 */
