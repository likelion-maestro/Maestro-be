package maestrogroup.core.Security;

import io.swagger.v3.oas.annotations.Operation;
import maestrogroup.core.ExceptionHandler.BaseException;
import maestrogroup.core.ExceptionHandler.BaseResponse;
import maestrogroup.core.Security.JWTtoken.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jwtSecurity")
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
    @Operation(summary = "access Token 만료시 재발급 받기", description = "refreshToken을 Http Header 로 넘겨서 새로운 accessToken을 발급 받아주세요.")
    public BaseResponse<AccessToken> getNewAccessToken(){
        try{
            String newAccessToken = jwtService.ReCreateAccessToken();
            AccessToken returnAccessToken = new AccessToken(newAccessToken);
            return new BaseResponse(returnAccessToken);
        } catch(BaseException baseException){
            return new BaseResponse(baseException.getStatus());
        }
    }
}
