package maestrogroup.core.user;

import maestrogroup.core.ExceptionHandler.BaseException;
import maestrogroup.core.ExceptionHandler.BaseResponse;
import maestrogroup.core.ExceptionHandler.BaseResponseStatus;
import maestrogroup.core.ExceptionHandler.Validation.CheckValidForm;
import maestrogroup.core.Security.AES128;
import maestrogroup.core.Security.JwtRepository;
import maestrogroup.core.Security.JwtService;
import maestrogroup.core.Security.Secret;
import maestrogroup.core.user.model.GetUser;
import maestrogroup.core.user.model.ModifyUserInfoReq;
import maestrogroup.core.user.model.SignUpUserReq;
import maestrogroup.core.user.model.SignUpUserRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.TimeZone;

@Service
public class UserService {

    @Autowired
    private final UserDao userDao;
    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final JwtRepository jwtRepository;

    public UserService(UserDao userDao, JwtService jwtService, JwtRepository jwtRepository) {
        this.userDao = userDao;
        this.jwtService = jwtService;
        this.jwtRepository = jwtRepository;
    }

    // 검증 과정 도중에 예외가 발생하면 Controller 에게 BaseException 예외 객체를 던진다.
    public SignUpUserRes createUser(SignUpUserReq signUpUserReq) throws BaseException{
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

        String encrypt_pwd;
        // 암호화: postUserReq에서 제공받은 비밀번호를 보안을 위해 암호화시켜 DB에 저장
        // ex) password123 -> dfhsjfkjdsnj4@!$!@chdsnjfwkenjfnsjfnjsd.fdsfaifsadjfjaf
        try{
            encrypt_pwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(signUpUserReq.getPassword());
            signUpUserReq.setPassword(encrypt_pwd); // 암호화한 비밀번호를 새롭게 저장
        } catch(Exception ignored){ // 암호화가 실패한 경우에 대한 에러처리
            throw new BaseException(BaseResponseStatus.PASSWORD_ENCRYPTION_FAILURE);
        }

        try {
            SignUpUserRes signUpUserRes = userDao.createUser(signUpUserReq);  // Dao를 통해 User 생성
            return signUpUserRes;
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

    public void deleteUser(int userIdx) throws BaseException {
        try {
            userDao.deleteUser(userIdx);
        } catch (Exception exception) {
            throw new BaseException(BaseResponseStatus.SERVER_ERROR);
        }
    }

    public GetUser getUser(int userIdx) throws BaseException {
        try {
            return userDao.getUser(userIdx);
        } catch (Exception exception) {
            throw new BaseException(BaseResponseStatus.SERVER_ERROR);
        }
    }
}
