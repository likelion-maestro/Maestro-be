package maestrogroup.core.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maestrogroup.core.ExceptionHandler.BaseException;
import maestrogroup.core.ExceptionHandler.BaseResponseStatus;
import maestrogroup.core.Security.JWTtoken.RefreshToken;
import maestrogroup.core.user.model.LoginUserRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;


@Service
@Slf4j
@RequiredArgsConstructor
public class JwtService {
    @Autowired JwtRepository jwtRepository;

    public JwtService(JwtRepository jwtRepository){
        this.jwtRepository = jwtRepository;
    }

    public String[] createTokenWhenLogin(int userIdx){
        String RefreshToken = createRefreshToken(userIdx);
        String AccessToken = createAccessToken(userIdx);
        // 발급받은 RefreshToken 은 DB 에 저장
        jwtRepository.saveRefreshToken(RefreshToken);
        String[] tokenList = {RefreshToken, AccessToken};
        return tokenList;
    }

    // Access Token 생성
    public String createAccessToken(int userIdx){
        byte[] keyBytes = Decoders.BASE64.decode(Secret.ACCESS_TOKEN_SECRET_KEY);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type","jwt") // Header 의 type 에다 해당 토큰의 타입을 jwt로 명시
                .claim("userIdx",userIdx) // claim 에 userIdx 할당
                .setIssuedAt(now) // 언제 발급되었는지를 현재 시간으로 넣어줌
                .setExpiration(new Date(System.currentTimeMillis()+1*(1000*60*30))) // Access Token 만료기간은 30분으로 설정
                .signWith(key, SignatureAlgorithm.HS256) // 서명(Signature) 를 할떄는 HS256 알고리즘 사용하며,  Secret.JWT_SECRET_KEY 라는 비밀키(Secret key) 를 가지고 Signature 를 생성한다.
                .compact();                                   //  Secret.JWT_SECRET_KEY 는 비밀키로써 .gitignore 로 절대 노출시키지 말것! 이 비밀키를 통해 내가 밝급한건인지 아닌지를 판별할 수 있으므로
    }

    // Refresh Token 생성
    public String createRefreshToken(int userIdx){
        byte[] keyBytes = Decoders.BASE64.decode(Secret.REFRESH_TOKEN_SECRET_KEY);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        Date now = new Date();
        return Jwts.builder()
                .setIssuedAt(now)
                .claim("userIdx", userIdx)
                .setExpiration(new Date(now.getTime() + 1*(1000*60*60*24*30))) // Refresh Token 만료기간은 1달로 설정
                .signWith(key, SignatureAlgorithm.HS256) // 1*(1000*60*60*24*30) : 만료기간을 365일(1년)으로 설정하는 경우
                .compact();
    }
    // .signWith(Secret.JWT_SECRET_KEY, SignatureAlgorithm.HS256)


    /*
    Header에서 X-ACCESS-TOKEN 으로 JWT 추출
    @return String
     */
    public String getAccessToken(){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("ACCESS-TOKEN");
    }

    /*
    Access Token 으로부터 userIdx 추출
    @return int
    @throws BaseException
     */
    public int getUserIdx() throws BaseException {
        //1. JWT 추출
        String accessToken = getAccessToken();
        if(accessToken == null || accessToken.length() == 0){
            throw new BaseException(BaseResponseStatus.EMPTY_JWT);
        }

        // 2. JWT parsing
        Jws<Claims> claims;
        try{
            claims = Jwts.parserBuilder()
                    .setSigningKey(Secret.ACCESS_TOKEN_SECRET_KEY) // ACCESS Token 임에 착각하지 말자!
                    .build()
                    .parseClaimsJws(accessToken);

            if(claims.getBody().getExpiration().before(new Date())){ // AccessToken 이 만료된 경우
                int userIdx = claims.getBody().get("userIdx", Integer.class);
                createAccessToken(userIdx); // AccessToken 을 새롭게 발급
            }
        } catch (Exception ignored) { // Access token 의 만료기간이 지난경우, 예외을 발생시켜서 새롭게 Access Token 을 발급받는다.
            throw new BaseException(BaseResponseStatus.INVALID_JWT);
        }
        System.out.println(claims);
        System.out.println(claims.getBody());

        // 3. userIdx 추출
        return claims.getBody().get("userIdx",Integer.class);  // jwt 에서 userIdx를 추출합니다.
    }

    // 넘어온 RefreshToken 에 대해 유효성 검증을 한 후에, 새로운 AccessToken 을 재발급해주는 메소드
    // 1. refresh 토큰의 만료시간이 지나지 않은경우, 새로운 access 토큰을 생성한다.
    // 2. refresh 토큰이 만료되었을 경우, 로그인 시도가 필요하다는 Response 메시지를 보낸다.
    public String ReCreateAccessToken(RefreshToken refreshTokenObj) throws Exception{
        // refresh 객체에서 refreshToken 객체 추출
        String refreshToken = refreshTokenObj.getRefreshToken();

        // refresh token 만료기간 여부 검증
        Jws<Claims> claims;
        try{
           claims = Jwts.parserBuilder()
                   .setSigningKey(Secret.REFRESH_TOKEN_SECRET_KEY)
                   .build()
                   .parseClaimsJws(refreshToken);

           // refresh token 의 만료기간이 지나지 않았을 경우, 새로운 access token 을 생성한다.
            if(!claims.getBody().getExpiration().before(new Date())){
                int userIdx = claims.getBody().get("userIdx", Integer.class);
                return createAccessToken(userIdx); // 새롭게 발급된 AccessToken 을 리턴
            }

        } catch(Exception ignored){
            // Refresh Token 이 만료된 경우
            throw new BaseException(BaseResponseStatus.REFRESH_TOKEN_INVALID);
        }

        return null;
    }
    
    // accessToken 을 기반

}
