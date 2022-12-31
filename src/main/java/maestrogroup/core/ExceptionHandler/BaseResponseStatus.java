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
    NOT_DB_CONNECTED_TOKEN("토큰이 DB에서 조회되지 않습니다.", HttpStatus.NOT_ACCEPTABLE),
    NOT_MATCHING_TOKEN("올바른 토큰을 입력으로 넣어주세요. 토큰이 조회되지 않습니다.", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN("잘못된 토큰이 입력되었거나 변조된 토큰이 입력되었습니다.", HttpStatus.BAD_REQUEST),
    REFRESH_TOKEN_INVALID("유효하지 않은 Refresh Token 입니다.", HttpStatus.NOT_MODIFIED),
    REFRESH_TOKEN_EXPIRED("만료된 Refresh Token 입니다. 로그인을 새롭게 시도해주세요.", HttpStatus.NOT_ACCEPTABLE),
    ACCESS_TOKEN_EXPIRED(" 만료된 Access Token 입니다. 새로운 Access Token을 발급 받으세요. ", HttpStatus.NOT_ACCEPTABLE),
    LOGOUT_FAILED("로그아웃에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),

    BLACK_USER_EXIST("블랙리스트에 등록된 토큰입니다. 현재 유저는 로그아웃된 상태이므로, 다시 로그인을 시도해주세요.", HttpStatus.BAD_REQUEST),

    // 회원정보 수정 관련
    MODIFY_FIELD_NOT_FULL("아직 기입하지 않은 정보가 존재합니다!", HttpStatus.BAD_REQUEST),
    MODFIY_USER_FAILURE("프로필을 수정하는데 실패했습니다.", HttpStatus.NOT_MODIFIED),
    PASSWORD_ENCRYPTION_FAILURE("비밀번호 암호화에 실패했습니다.", HttpStatus.NOT_ACCEPTABLE),
    INVALID_TEAM_AUTH("팀장이 아니므로, 팀장 권한 부여가 불가능합니다.", HttpStatus.NOT_ACCEPTABLE),

    // 로그인 관련
    INVALID_USER("존재하지 않는 회원입니다.", HttpStatus.BAD_REQUEST),

    // 팀 관련
    INVALID_TEAM_NAME_FORM("팀 이름을 입력해주세요.", HttpStatus.BAD_REQUEST),
    NOT_EXISTS_TEAM("팀이 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    USER_IS_NOT_IN_TEAM("팀에 가입되어 있지 않아 삭제할 권한이 없습니다.", HttpStatus.BAD_REQUEST);
    private final HttpStatus httpStatus;
    private final String message;

    private BaseResponseStatus(String message, HttpStatus httpStatus){
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
