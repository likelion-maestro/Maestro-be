package maestrogroup.core.user;


import maestrogroup.core.user.model.SignUpUserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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


}
