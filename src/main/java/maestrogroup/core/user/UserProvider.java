package maestrogroup.core.user;

import maestrogroup.core.ExceptionHandler.BaseException;
import maestrogroup.core.ExceptionHandler.BaseResponse;
import maestrogroup.core.ExceptionHandler.BaseResponseStatus;
import maestrogroup.core.Security.AES128;
import maestrogroup.core.Security.JwtService;
import maestrogroup.core.Security.Secret;
import maestrogroup.core.user.model.GetUser;
import maestrogroup.core.user.model.LoginUserReq;
import maestrogroup.core.user.model.LoginUserRes;
import maestrogroup.core.user.model.LoginUserSomeField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProvider {

    @Autowired
    private final UserDao userDao;
    @Autowired
    private final JwtService jwtService;

    public UserProvider(UserDao userDao, JwtService jwtService){
        this.userDao = userDao;
        this.jwtService = jwtService;
    }
    public GetUser getUser(int userIdx){
        return userDao.getUser(userIdx);
    }

    public int Email_Duplicate_Check(String email){
        return userDao.Email_Duplicate_Check(email);
    }

    // 로그인 (password 검사)
    public LoginUserRes loginUser(LoginUserReq loginUserReq) throws BaseException {

        // 회원가입시 저장한 회원 계정의 비밓번호와, 현재 로그인 창에서 입력된 비밀번호(loginUserSomeField가 동일한지 검증한다.
        LoginUserSomeField loginUserSomeField = userDao.getSomeInfo_WhenLogin(loginUserReq); // 회원가입 때 입력받은 비밀번호를 DB에 그냥 저장한 것이 아니라 암호화해서
        String password;           // 저장했었는데, 아무튼 이 암호화된 비밀번호를 DB로부터 가져온다.
        try {
            // 복호화 : DB 에서 가져온 비밀번호를 암호화를 해제한다.(=> decrpyt 한다. 즉 인코딩(암호화)된 것을 다시 디코딩해준다.)
            password = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(loginUserSomeField.getPassword()); // 복호화
        } catch (Exception baseException){
            throw new BaseException(BaseResponseStatus.LOGIN_FAILURE);
        }

        // DB에 저장된 회원의 비밀번호(password)와, 현재 로그인 창에서 입력한 비밀번호(loginUserSomeField.getPassword())가
        // 일치하면 해당 사용자 계정에 대한 userIdx 를 가져온다.
        // 또한 jwt 를 발급해준다
        if(loginUserReq.getPassword().equals(password)){
            int userIdx = userDao.getSomeInfo_WhenLogin(loginUserReq).getUserIdx();
            String email = userDao.getSomeInfo_WhenLogin(loginUserReq).getEmail();
            String nickname = userDao.getSomeInfo_WhenLogin(loginUserReq).getNickname();
            String[] tokenList = jwtService.createTokenWhenLogin(userIdx); // access, refresh token 생성
            return new LoginUserRes(userIdx, email, nickname, tokenList[1], tokenList[0]);  // JWT 토큰을 클라이언트에게 Response로 발급해준다.
        }
        // 비밀번호가 일치하지 않는다면 로그인에 실패한것
        else{
            throw new BaseException(BaseResponseStatus.LOGIN_FAILURE);
        }
    }

    public void logoutUser() throws BaseException{
        try {
            userDao.makeExpireToken_WhenLogout();
        }
        catch (Exception baseException){
            throw new BaseException(BaseResponseStatus.ACCESS_TOKEN_EXPIRED)
        }
    }
}





