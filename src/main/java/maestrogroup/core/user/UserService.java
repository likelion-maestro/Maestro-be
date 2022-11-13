package maestrogroup.core.user;

import maestrogroup.core.ExceptionHandler.BaseException;
import maestrogroup.core.ExceptionHandler.BaseResponseStatus;
import maestrogroup.core.ExceptionHandler.Validation.CheckValidForm;
import maestrogroup.core.user.model.GetUser;
import maestrogroup.core.user.model.ModifyUserInfoReq;
import maestrogroup.core.user.model.SignUpUserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.TimeZone;

@Service
public class UserService {

    @Autowired
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    // 검증 과정 도중에 예외가 발생하면 Controller 에게 BaseException 예외 객체를 던진다.
    public void createUser(SignUpUserReq signUpUserReq) throws BaseException{
        // 입력받은 이메일을 빈 값으로 요청하지 않았는지 유효성(Validation) 검사
        if(signUpUserReq.getEmail() == null || signUpUserReq.getEmail() == ""){
            throw new BaseException(BaseResponseStatus.INVALID_EMAIL_FORM);
        }

        // 이메일 정규화 표현 : email@naver.com 과 같은 형식인지 유효성 검사.
        if(!CheckValidForm.isValidEmailForm(signUpUserReq.getEmail())){
            throw new BaseException(BaseResponseStatus.INVALID_EMAIL_FORM);
        }

        // 중복된 이메일을 가지는 유저가 또 존재하는지 확인
        if(userDao.Email_Duplicate_Check(signUpUserReq.getEmail()) == 1){
            throw new BaseException(BaseResponseStatus.EXIST_USER_EMAIL);
        }

        // 비밓번호와 재입력받은 비밓번호가 같은지 다른지 유효성 검사 (다르면 예외 발생)
        if(!CheckValidForm.isEqual_Passwrord_Check(signUpUserReq.getPassword(), signUpUserReq.getRepass())){
            throw new BaseException(BaseResponseStatus.NOT_EQUAL_PASSWORD_REPASSWORD);
        }

        // 비밀번호 형식에 대한 유효성 검사
        if(!CheckValidForm.isValid_Password_Form(signUpUserReq.getPassword())){
            throw new BaseException(BaseResponseStatus.INVALID_PASSWORD_FORM);
        }

        try {
            userDao.createUser(signUpUserReq);  // Dao를 통해 User 생성
        } catch(Exception exception){ // 서버 및 DB 에 연동해서 데이터를 Dao에서 데이터를 처리할 떄 문제가 발생한 경우
            throw new BaseException(BaseResponseStatus.SERVER_ERROR);  // 예외를 던지기
        }
    }

    public void modifyUserInfo(int userIdx, ModifyUserInfoReq modifyUserInfoReq) throws BaseException{
        // 모든 필드가 입력되지 않을경우 프로필 수정이 불가능
        if(modifyUserInfoReq.getEmail()== null || modifyUserInfoReq.getEmail() == ""){
            throw new BaseException(BaseResponseStatus.MODIFY_FIELD_NOT_FULL);
        }

        if(modifyUserInfoReq.getPassword() == null || modifyUserInfoReq.getPassword() == ""){
            throw new BaseException(BaseResponseStatus.MODIFY_FIELD_NOT_FULL);
        }

        if(modifyUserInfoReq.getNickname() == null || modifyUserInfoReq.getNickname() == ""){
            throw new BaseException(BaseResponseStatus.MODIFY_FIELD_NOT_FULL);
        }

        // 회원가입때와 마찬가지로 비밓번호 및 이메일에 대한 유효성 검사 처리

        // 입력받은 이메일을 빈 값으로 요청하지 않았는지 유효성(Validation) 검사
        if(modifyUserInfoReq.getEmail() == null || modifyUserInfoReq.getEmail() == ""){
            throw new BaseException(BaseResponseStatus.INVALID_EMAIL_FORM);
        }

        // 이메일 정규화 표현 : email@naver.com 과 같은 형식인지 유효성 검사.
        if(!CheckValidForm.isValidEmailForm(modifyUserInfoReq.getEmail())){
            throw new BaseException(BaseResponseStatus.INVALID_EMAIL_FORM);
        }

        // 비밀번호 형식에 대한 유효성 검사
        if(!CheckValidForm.isValid_Password_Form(modifyUserInfoReq.getPassword())){
            throw new BaseException(BaseResponseStatus.INVALID_PASSWORD_FORM);
        }


        try {
            userDao.modifyUserInfo(userIdx, modifyUserInfoReq);
        } catch(Exception exception) { // 서버 및 DB 에 연동해서 데이터를 Dao에서 데이터를 처리할 떄 문제가 발생한 경우
            throw new BaseException(BaseResponseStatus.SERVER_ERROR);  // 예외를 던지기
        }
    }

    public void deleteUser(int userIdx, Timestamp timestamp){
        userDao.deleteUser(userIdx, timestamp);
    }

    public GetUser getUser(int userIdx){
        return userDao.getUser(userIdx);
    }
}
