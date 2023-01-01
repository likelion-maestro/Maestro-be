package maestrogroup.core.user;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import maestrogroup.core.ExceptionHandler.BaseException;
import maestrogroup.core.ExceptionHandler.BaseResponse;
import maestrogroup.core.ExceptionHandler.BaseResponseStatus;
import maestrogroup.core.Security.JwtProvider;
import maestrogroup.core.Security.JwtRepository;
import maestrogroup.core.Security.JwtService;
import maestrogroup.core.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;


@RequestMapping("/user")
@RestController
@Api(value = "User", tags = "User 관련 API")
public class UserController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserDao userDao;

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final JwtProvider jwtProvider;

    @Autowired
    private final JwtRepository jwtRepository;

    public UserController(UserService userService, UserProvider userProvider, UserDao userDao, JwtService jwtService, JwtProvider jwtProvider, JwtRepository jwtRepository) {
        this.userProvider = userProvider;
        this.userService = userService;
        this.userDao = userDao;
        this.jwtService = jwtService;
        this.jwtProvider = jwtProvider;
        this.jwtRepository = jwtRepository;
    }

    // 회원가입
    @ResponseBody
    @PostMapping("/signUp")
    @Operation(summary = "회원가입", description = "이메일, 비밀번호, 2차 비밀번호, 닉네임을 입력받는다.")
    public BaseResponse<SignUpUserRes> createUser(@RequestBody SignUpUserReq signUpUserReq) {
        try {
            SignUpUserRes signUpUserRes = userService.createUser(signUpUserReq);
            return new BaseResponse<SignUpUserRes>(signUpUserRes); // 생성자에 파라미터 값을 아무것도 부여하지 않으면 성공에 대한 BaseResponse 가 생성 및 호출됨
        } catch (BaseException exception) {  // userService.createUser() 를 호출했더니 도중에 예외가 발생한 경우에 대한 예외처리
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // 로그인
    @ResponseBody
    @PostMapping("/login")
    @Operation(summary = "로그인", description = "이메일, 비밀번호를 입력받고 로그인에 성공하면 accessToken, refreshToken을 발급받는다. 로컬 스토리에 저장할것!")
    public  BaseResponse<LoginUserRes>loginUser(@RequestBody LoginUserReq loginUserReq){
        try {
            LoginUserRes loginUserRes = userProvider.loginUser(loginUserReq);
            return new BaseResponse<>(loginUserRes);
        } catch(BaseException baseException){
            return new BaseResponse<>(baseException.getStatus());
        }
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "로그아웃 호출시 클라이언트는 local storage 에 저장하고 있던 access, refresh token을 모두 삭제해야한다.")
    public BaseResponse<LoginUserRes>logoutUser(){
        try{
            userProvider.logoutUser();
            return new BaseResponse<>();
        } catch (BaseException baseException){
            return new BaseResponse<>(baseException.getStatus());
        }
    }

    // 회원정보 수정
    @ResponseBody
    @PatchMapping("/modifyUser")
    @Operation(summary = "회원정보(프로필) 수정", description = "jwt 를 HttpHeader에 넘겨주시고, 이메일, 닉네임, 비밀번호 필드 Http Body에 실어서 모두 서버에 Request 로 넘겨주세요.")
    public BaseResponse modifyUserInfo(@RequestBody ModifyUserInfoReq modifyUserInfoReq){
        try {
            int userIdxByJwt = jwtService.getUserIdx(); // 클라이언트로 부터 넘겨받은 JWT 토큰으로부터 userIdx 값 추출
            userService.modifyUserInfo(userIdxByJwt, modifyUserInfoReq);
            return new BaseResponse();
        } catch(BaseException baseException){
            return new BaseResponse(baseException.getStatus());
        }
    }

    // 계정 삭제
    // => 삭제시킬 것들 : User, Mapping
    @ResponseBody
    @DeleteMapping("/deleteUser")
    @Operation(summary = "계정 삭제")
    public BaseResponse deleteUser() {
        try {
            int userIdx = jwtService.getUserIdx();
            userService.deleteUser(userIdx);
            return new BaseResponse();
        } catch (BaseException baseException) {
            return new BaseResponse(baseException.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("/getUser")
    @Operation(summary = "회원정보(프로필) 조회", description = "jwt 를 HttpHeader에 넘겨주시고 조회해주세요. 마이페이지에 쓰이는 기능입니다.")
    public BaseResponse<GetUser> getUser(){
        try {
            int userIdx = jwtService.getUserIdx();
            return new BaseResponse<GetUser>(userService.getUser(userIdx));
        } catch (BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
    }
}