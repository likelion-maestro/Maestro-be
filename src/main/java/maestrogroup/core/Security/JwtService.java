package maestrogroup.core.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Value;
import maestrogroup.core.ExceptionHandler.BaseException;
import maestrogroup.core.ExceptionHandler.BaseResponseStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    /*
    JWT 생성
    @param userIdx
    @return String
     */

    /*
    private final Key key;
    public JwtService(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }
    */

    //SignatureAlgorithm.ES256
    public String createJwt(int userIdx){
        byte[] keyBytes = Decoders.BASE64.decode(Secret.JWT_SECRET_KEY);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type","jwt") // Header 의 type 에다 해당 토큰의 타입을 jwt로 명시
                .claim("userIdx",userIdx) // claim 에 userIdx 할당
                .setIssuedAt(now) // 언제 발급되었는지를 현재 시간으로 넣어줌
                .setExpiration(new Date(System.currentTimeMillis()+1*(1000*60*60*24*365))) // 토큰 만료기간은 1년으로 설정
                .signWith(key) // 서명(Signature) 를 할떄는 HS256 알고리즘 사용하며,  Secret.JWT_SECRET_KEY 라는 비밀키(Secret key) 를 가지고 Signature 를 생성한다.
                .compact();                                   //  Secret.JWT_SECRET_KEY 는 비밀키로써 .gitignore 로 절대 노출시키지 말것! 이 비밀키를 통해 내가 밝급한건인지 아닌지를 판별할 수 있으므로
    }

    /*
    Header에서 X-ACCESS-TOKEN 으로 JWT 추출
    @return String
     */
    public String getJwt(){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("X-ACCESS-TOKEN");
    }

    /*
    JWT에서 userIdx 추출
    @return int
    @throws BaseException
     */
    public int getUserIdx() throws BaseException {
        //1. JWT 추출
        String accessToken = getJwt();
        if(accessToken == null || accessToken.length() == 0){
            throw new BaseException(BaseResponseStatus.EMPTY_JWT);
        }

        // 2. JWT parsing
        Jws<Claims> claims;
        try{
            claims = Jwts.parserBuilder()
                    .setSigningKey(Secret.JWT_SECRET_KEY)
                    .build()
                    .parseClaimsJws(accessToken);
        } catch (Exception ignored) {
            throw new BaseException(BaseResponseStatus.INVALID_JWT);
        }
        System.out.println("gk=============================================");
        System.out.println(claims);
        System.out.println(claims.getBody());

        // 3. userIdx 추출
        return claims.getBody().get("userIdx",Integer.class);  // jwt 에서 userIdx를 추출합니다.
    }

}
