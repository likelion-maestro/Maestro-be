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
import maestrogroup.core.ExceptionHandler.BaseResponse;
import maestrogroup.core.ExceptionHandler.BaseResponseStatus;
import maestrogroup.core.Security.JWTtoken.AccessToken;
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
        jwtRepository.saveRefreshToken(RefreshToken, userIdx);
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
                .setHeaderParam("type", "jwt")
                .claim("userIdx", userIdx)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis()+1*(1000*60*60*24*7))) // Refresh Token 만료기간은 일주일로 설정
                .signWith(key, SignatureAlgorithm.HS256) // 1*(1000*60*60*24*30) : 만료기간을 365일(1년)으로 설정하는 경우
                .compact();
    }
    //                 .setExpiration(new Date(System.currentTimeMillis()+1*(1000*60*60*24*30))) // Refresh Token 만료기간은 1달로 설정

    /*
    Header에서 X-ACCESS-TOKEN 으로 JWT 추출
    @return String
     */
    public String getAccessToken(){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("Authorization");
    }

    public String getRefreshToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("RefreshToken");
    }

     // Access Token 으로부터 userIdx 추출
     // + API 호출시 HTTP Header 에 넘겨주는 Access Token 에 대한 유효성 검증 과정도 포함되어 있음
    public int getUserIdx() throws BaseException {
        //1. JWT 추출
        String accessToken = getAccessToken();
        if (accessToken == null || accessToken.length() == 0) {
            throw new BaseException(BaseResponseStatus.EMPTY_JWT);
        }

        // 2. JWT parsing
        Jws<Claims> claims;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(Secret.ACCESS_TOKEN_SECRET_KEY) // ACCESS Token 임에 착각하지 말자!
                    .build()
                    .parseClaimsJws(accessToken);
        } catch (io.jsonwebtoken.security.SignatureException signatureException) { // AccessToken 유효성 검증1. Signature(서명값) 변조여부 검증
            throw new BaseException(BaseResponseStatus.INVALID_TOKEN);
        } catch (io.jsonwebtoken.ExpiredJwtException expiredJwtException){ // AccessToken 유효성 검증2. accessToken의 만료 여부 검증
            throw new BaseException(BaseResponseStatus.ACCESS_TOKEN_EXPIRED);
        }

        /*
        // Access Token 유효성 검증2. accessToken의 만료 여부 검증
        if (claims.getBody().getExpiration().before(new Date())) { // AccessToken 이 만료된 경우
            throw new BaseException(BaseResponseStatus.ACCESS_TOKEN_EXPIRED);  // access token 이 만료되었다는 Response 를 보낸다.
        }
        */

        // userIdx 추출
        return claims.getBody().get("userIdx", Integer.class);  // jwt 에서 userIdx를 추출합니다.
    }


    // 넘어온 RefreshToken 에 대해 유효성 검증을 한 후에, 새로운 AccessToken 을 재발급해주는 메소드
    // 1. refresh 토큰의 만료시간이 지나지 않은경우, 새로운 access 토큰을 생성한다.
    // 2. refresh 토큰이 만료되었을 경우, 로그인 시도가 필요하다는 Response 메시지를 보낸다.
    //public String ReCreateAccessToken(RefreshToken refreshTokenObj) throws Exception{
    public String ReCreateAccessToken() throws BaseException {
        // RefreshToken 객체에서 refresh 토큰 추출
        // String refreshToken = refreshTokenObj.getRefreshToken();
        String refreshToken = getRefreshToken();
        String dbRefreshToken;

        // refresh token 유효성 검증1 : DB조회
        try {
            RefreshToken dbRefreshTokenObj = jwtRepository.getRefreshToken(refreshToken);
            dbRefreshToken = dbRefreshTokenObj.getRefreshToken();
        } catch(Exception ignored){  // DB에 RefreshToken 이 존재하지 않는경우
            throw new BaseException(BaseResponseStatus.NOT_DB_CONNECTED_TOKEN);
        }

        // DB에 RefreshToken이 존재하지 않거나(null), 전달받은 refeshToken 이 DB에 있는 refreshToken 과 일치하지 않는 경우
        if(dbRefreshToken == null || !dbRefreshToken.equals(refreshToken)){
            throw new BaseException(BaseResponseStatus.NOT_MATCHING_TOKEN);
        }

        // Refresh Token 에 대한 DB 조회에 성공한 경우
        // refresh token 유효성 검증2 : 형태 유효성
        Jws<Claims> claims;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(Secret.REFRESH_TOKEN_SECRET_KEY)
                    .build()
                    .parseClaimsJws(refreshToken);
        }  catch (io.jsonwebtoken.security.SignatureException signatureException){
            throw new BaseException(BaseResponseStatus.INVALID_TOKEN);
        }
        catch (io.jsonwebtoken.ExpiredJwtException expiredJwtException) { // refresh token 이 만료된 경우
            throw new BaseException(BaseResponseStatus.REFRESH_TOKEN_EXPIRED); // 새롭게 로그인읋 시도하라는 Response 를 보낸다.
        } catch (Exception ignored) { // Refresh Token이 유효하지 않은 경우 (만료여부 외의 예외처리)
            throw new BaseException(BaseResponseStatus.REFRESH_TOKEN_INVALID);
        }

        /*
        // // refresh token 유효성 검증3 : refresh token 만료기간 여부 검증
        // refresh token 의 만료기간이 지나지 않았을 경우, 새로운 access token 을 생성한다.
        if (!claims.getBody().getExpiration().before(new Date())) {
            int userIdx = claims.getBody().get("userIdx", Integer.class);
            // DB에 저장된 RefreshToken 과 비교하고 일치한다면 Access Token 을 발급한다.
            return createAccessToken(userIdx); // 새롭게 발급된 AccessToken 을 리턴
        }
        return null;
        */
        int userIdx = claims.getBody().get("userIdx", Integer.class);
        return createAccessToken(userIdx);
    }

    // 로그아웃 : 서버측에서 JWT 토큰값 자체를 변경하는 것은 불가능한 것으로 판단되어, DB에 존재하는
    // RefreshToken 을 삭제시켜서 마치 로그아웃된 것으로 구현하기
    public void makeExpireToken_WhenLogout() throws Exception{
        String refreshToken = getRefreshToken();
        String dbRefreshToken;

        // refresh token 유효성 검증1 : DB조회
        try {
            RefreshToken dbRefreshTokenObj = jwtRepository.getRefreshToken(refreshToken);
            dbRefreshToken = dbRefreshTokenObj.getRefreshToken();
        } catch(Exception ignored){  // DB에 RefreshToken 이 존재하지 않는경우
            throw new BaseException(BaseResponseStatus.NOT_DB_CONNECTED_TOKEN);
        }

        // DB에 RefreshToken이 존재하지 않거나(null), 전달받은 refeshToken 이 DB에 있는 refreshToken 과 일치하지 않는 경우
        if(dbRefreshToken == null || !dbRefreshToken.equals(refreshToken)){
            throw new BaseException(BaseResponseStatus.NOT_MATCHING_TOKEN);
        }


        Jws<Claims> claims;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(Secret.REFRESH_TOKEN_SECRET_KEY)
                    .build()
                    .parseClaimsJws(refreshToken);
        }  catch (io.jsonwebtoken.security.SignatureException signatureException){
            throw new BaseException(BaseResponseStatus.INVALID_TOKEN);
        }
        catch (io.jsonwebtoken.ExpiredJwtException expiredJwtException) { // refresh token 이 만료된 경우
            throw new BaseException(BaseResponseStatus.REFRESH_TOKEN_INVALID); // 새롭게 로그인읋 시도하라는 Response 를 보낸다.
        } catch (Exception ignored) { // Refresh Token이 유효하지 않은 경우 (만료여부 외의 예외처리)
            throw new BaseException(BaseResponseStatus.REFRESH_TOKEN_INVALID);
        }

        try {
            jwtRepository.deleteRefreshToken(dbRefreshToken);
        } catch(Exception exception){
            throw new BaseException(BaseResponseStatus.LOGOUT_FAILED); // DB 에 있는 RefreshToken 삭제에 실패한 경우
        }
    }
}
