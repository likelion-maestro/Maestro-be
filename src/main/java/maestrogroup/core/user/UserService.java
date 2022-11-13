package maestrogroup.core.user;

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

    public void createUser(SignUpUserReq signUpUserReq){
        userDao.createUser(signUpUserReq);
    }

    public void modifyUserInfo(int userIdx, ModifyUserInfoReq modifyUserInfoReq){
        userDao.modifyUserInfo(userIdx, modifyUserInfoReq);
    }

    public void deleteUser(int userIdx){
        userDao.deleteUser(userIdx);
    }
}
