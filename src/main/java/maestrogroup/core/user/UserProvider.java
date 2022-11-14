package maestrogroup.core.user;

import maestrogroup.core.ExceptionHandler.BaseException;
import maestrogroup.core.ExceptionHandler.BaseResponse;
import maestrogroup.core.ExceptionHandler.BaseResponseStatus;
import maestrogroup.core.user.model.GetUser;
import maestrogroup.core.user.model.LoginUserReq;
import maestrogroup.core.user.model.LoginUserRes;
import maestrogroup.core.user.model.LoginUserSomeField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProvider {

    @Autowired
    private UserDao userDao;

    public UserProvider(UserDao userDao){
        this.userDao = userDao;
    }
    public GetUser getUser(int userIdx){
        return userDao.getUser(userIdx);
    }

    public int Email_Duplicate_Check(String email){
        return userDao.Email_Duplicate_Check(email);
    }

    public void loginUser(LoginUserReq loginUserReq) throws BaseException {
        try {
            LoginUserSomeField loginUserSomeField = userDao.getPwd(loginUserReq);
            String password;
        } catch (Exception baseException){
            throw new BaseException(BaseResponseStatus.LOGIN_FAILURE);
        }
    }
}
