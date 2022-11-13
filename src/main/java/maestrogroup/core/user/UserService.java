package maestrogroup.core.user;

import maestrogroup.core.ExceptionHandler.BaseException;
import maestrogroup.core.ExceptionHandler.BaseResponseStatus;
import maestrogroup.core.user.model.GetUser;
import maestrogroup.core.user.model.ModifyUserInfoReq;
import maestrogroup.core.user.model.SignUpUserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    // 검증 과정 도중에 예외가 발생하면 Controller 에게 BaseException 예외 객체를 던진다.
    public void createUser(SignUpUserReq signUpUserReq) throws BaseException{
        // 중복된 이메일을 가지는 유저가 또 존재하는지 확인
        if(userDao.Email_Duplicate_Check(signUpUserReq.getEmail()) == 1){
            throw new BaseException(BaseResponseStatus.EXIST_USER_EMAIL);
        }
        userDao.createUser(signUpUserReq);
    }

    public void modifyUserInfo(int userIdx, ModifyUserInfoReq modifyUserInfoReq){
        userDao.modifyUserInfo(userIdx, modifyUserInfoReq);
    }

    public void deleteUser(int userIdx){
        userDao.deleteUser(userIdx);
    }

    public GetUser getUser(int userIdx){
        return userDao.getUser(userIdx);
    }
}
