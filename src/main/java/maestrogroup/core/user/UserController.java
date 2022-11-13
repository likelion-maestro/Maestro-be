package maestrogroup.core.user;


import maestrogroup.core.user.model.ModifyUserInfoReq;
import maestrogroup.core.user.model.ModifyUserInfoRes;
import maestrogroup.core.user.model.SignUpUserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserDao userDao;

    public UserController(UserService userService, UserProvider userProvider, UserDao userDao){
        this.userProvider = userProvider;
        this.userService = userService;
        this.userDao = userDao;
    }

    // 회원가입
    @ResponseBody
    @PostMapping("/sign-up")
    public void createUser(@RequestBody SignUpUserReq signUpUserReq){
        userService.createUser(signUpUserReq);
        // 비밀번호과 재입력받은 비밀번호가 다른경우에 대한 검증 및 예외처리
        /*
        try {
            if (signUpUserReq.getPassword() != signUpUserReq.getRepassword()) {

            }
        }*/
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
}
