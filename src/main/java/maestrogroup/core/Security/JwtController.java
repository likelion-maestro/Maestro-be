package maestrogroup.core.Security;

import maestrogroup.core.ExceptionHandler.BaseException;
import maestrogroup.core.ExceptionHandler.BaseResponse;
import maestrogroup.core.Security.JWTtoken.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jwtSecurity")
@CrossOrigin(origins = "http://maestro:3000")
public class JwtController {
    @Autowired
    private final JwtProvider jwtProvider;

    @Autowired
    private final JwtService jwtService;

    public JwtController(JwtProvider jwtProvider, JwtService jwtService){
        this.jwtProvider = jwtProvider;
        this.jwtService = jwtService;
    }

    @ResponseBody
    @PostMapping("/getNewAccessToken")
    public BaseResponse<AccessToken> getNewAccessToken(){
        String refreshToken = jwtService.getRefreshToken(); // HTTP Header를 통해 Access Token 과 Refresh Token 을 클라이언트로부터 전달받고
        String originAccessToken = jwtService.getAccessToken();

        try{
            String newAccessToken = jwtService.ReCreateAccessToken();
            AccessToken returnAccessToken = new AccessToken(newAccessToken);
            return new BaseResponse<AccessToken>(returnAccessToken);
        } catch(BaseException baseException){
            return new BaseResponse<>(baseException.getStatus());
        }
    }
}
