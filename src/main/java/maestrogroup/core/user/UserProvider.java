package maestrogroup.core.user;

import maestrogroup.core.user.model.GetUser;
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
}
