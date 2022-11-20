package maestrogroup.core.Security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    public String createJwt(int userIdx){
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type", "jwt") // Header의 type 필드에다 해당 토큰의 타입을 jwt 라고명시
                .claim("userIdx", userIdx) // claim에 userIdx 할당
                .setIssuedAt(now) // 언제 발급되었는지를 현재 시간으로 보여줌
                .setExpiration(new Date(System.currentTimeMillis() + 1*(1000*60*60*24*365))) // 토큰 만료기간은 1년으로 설정
                .signWith(SignatureAlgorithm.HS256, Secret.JWT_SECRET_KEY) // 서명(Signature) 를 할때는 HS256 알고리즘을 사용하며, Secret.JWT_SECRET_KEY 라는 비밀키(Secret key) 를 가지고 Signature 를 생성
                .compact();
    }
}
