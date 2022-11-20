package maestrogroup.core.user;


import maestrogroup.core.ExceptionHandler.BaseException;
import maestrogroup.core.ExceptionHandler.BaseResponse;
import maestrogroup.core.ExceptionHandler.BaseResponseStatus;
import maestrogroup.core.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserDao userDao;

    public UserController(UserService userService, UserProvider userProvider, UserDao userDao) {
        this.userProvider = userProvider;
        this.userService = userService;
        this.userDao = userDao;
    }

    // 회원가입
    @ResponseBody
    @PostMapping("/sign-up")
    public BaseResponse createUser(@RequestBody SignUpUserReq signUpUserReq) {
        try {
            userService.createUser(signUpUserReq);
            return new BaseResponse<>(); // 생성자에 파라미터 값을 아무것도 부여하지 않으면 성공에 대한 BaseResponse 가 생성 및 호출됨
        } catch (BaseException exception) {  // userService.createUser() 를 호출했더니 도중에 예외가 발생한 경우에 대한 예외처리
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // 로그인
    @ResponseBody
    @PostMapping("/login")
    public  BaseResponse<LoginUserRes>loginUser(@RequestBody LoginUserReq loginUserReq){
        try {
            LoginUserRes loginUserRes = userProvider.loginUser(loginUserReq);
            return new BaseResponse<>(loginUserRes);
        } catch(BaseException baseException){
            return new BaseResponse<>(baseException.getStatus());
        }
    }

    // 회원정보 수정
    @ResponseBody
    @PatchMapping("/modifyUser/{userIdx}")
    public BaseResponse modifyUserInfo(@PathVariable("userIdx") int userIdx, @RequestBody ModifyUserInfoReq modifyUserInfoReq){
        try {
            userService.modifyUserInfo(userIdx, modifyUserInfoReq);
            return new BaseResponse();
        } catch(BaseException baseException){
            return new BaseResponse(baseException.getStatus());
        }
    }

    // 계정 삭제 : status 필드값을 변경하자. DELETE 메소드 요청을 하지말고!!!
    @ResponseBody
    @PatchMapping("/deleteUser/{userIdx}")
    public void deleteUser(@PathVariable("userIdx") int userIdx){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        userService.deleteUser(userIdx, timestamp);
    }

    @ResponseBody
    @GetMapping("/getUser/{userIdx}")
    public GetUser getUser(@PathVariable("userIdx") int userIdx){
        return userProvider.getUser(userIdx);
    }
}
