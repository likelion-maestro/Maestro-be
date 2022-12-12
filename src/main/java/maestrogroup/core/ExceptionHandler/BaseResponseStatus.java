package maestrogroup.core.ExceptionHandler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

// enum 클래스
@Getter
public enum
BaseResponseStatus {
    // 요청 성공관련
    SUCCESS("요청에 성공했습니다.", HttpStatus.OK),

    // 요청 실패관련
    REQUEST_ERROR("입력값을 다시 확인해주세요.",HttpStatus.BAD_REQUEST),
    SERVER_ERROR("서버와의 연동에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),

    // 회원가입 관련
    INVALID_EMAIL_FORM("이메일 형식을 확인해주세요.", HttpStatus.BAD_REQUEST),
    EXIST_USER_EMAIL("이미 가입된 이메일 입니다.", HttpStatus.FORBIDDEN),
    NOT_EQUAL_PASSWORD_REPASSWORD("비밓번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD_FORM("비밀번호는 숫자,문자를 모두 포함하며, 8자~20자 사이로 입력해주세요", HttpStatus.BAD_REQUEST),
    // 로그인 관련
    LOGIN_FAILURE("존재하지 않는 아이디 또는 비밀번호입니다.", HttpStatus.FORBIDDEN),
    EMPTY_JWT("JWT를 입력해주세요", HttpStatus.BAD_REQUEST),
    INVALID_JWT("유효하지 않은 JWT 입니다.", HttpStatus.BAD_REQUEST),
    REFRESH_TOKEN_INVALID("만료된 Refresh Token 입니다.", HttpStatus.NOT_MODIFIED),

    // 회원정보 수정 관련
    MODIFY_FIELD_NOT_FULL("아직 기입하지 않은 정보가 존재합니다!", HttpStatus.BAD_REQUEST),
    MODFIY_USER_FAILURE("프로틸을 수정하는데 실패했습니다.", HttpStatus.NOT_MODIFIED),
    PASSWORD_ENCRYPTION_FAILURE("비밀번호 암호화에 실패했습니다.", HttpStatus.NOT_ACCEPTABLE);

    private final HttpStatus httpStatus;
    private final String message;

    private BaseResponseStatus(String message, HttpStatus httpStatus){
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
