package maestrogroup.core.user;


import maestrogroup.core.ExceptionHandler.BaseException;
import maestrogroup.core.ExceptionHandler.BaseResponse;
import maestrogroup.core.ExceptionHandler.BaseResponseStatus;
import maestrogroup.core.user.model.GetUser;
import maestrogroup.core.user.model.ModifyUserInfoReq;
import maestrogroup.core.user.model.ModifyUserInfoRes;
import maestrogroup.core.user.model.SignUpUserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public BaseResponse createUser(@RequestBody @Valid SignUpUserReq signUpUserReq) {
        try {
            System.out.println(signUpUserReq.getEmail());
            userService.createUser(signUpUserReq);
            return new BaseResponse(); // 생성자에 파라미터 값을 아무것도 부여하지 않으면 성공에 대한 BaseResponse 가 생성 및 호출됨
        } catch (BaseException exception) {  // userService.createUser() 를 호출했더니 도중에 예외가 발생한 경우에 대한 예외처리ㄷ
            return new BaseResponse(exception.getStatus());
        }
    }

    // 회원정보 수정
    @ResponseBody
    @PatchMapping("/modifyUser/{userIdx}")
    public void modifyUserInfo(@PathVariable("userIdx") int userIdx, @RequestBody ModifyUserInfoReq modifyUserInfoReq){
        userService.modifyUserInfo(userIdx, modifyUserInfoReq);
    }

    // 계정 삭제 : status 필드값을 변경하자. DELETE 메소드 요청을 하지말고!!!
    @ResponseBody
    @PatchMapping("/deleteUser/{userIdx}")
    public void deleteUser(@PathVariable("userIdx") int userIdx){
        userService.deleteUser(userIdx);
    }

    @ResponseBody
    @GetMapping("/getUser/{userIdx}")
    public GetUser getUser(@PathVariable("userIdx") int userIdx){
        return userProvider.getUser(userIdx);
    }
}
