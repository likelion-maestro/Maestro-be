package maestrogroup.core.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginUserRes {
    private int userIdx;
    private String email;
    private String nickname;
    private String jwt; // 로그인에 성공하면 jwt 토큰값도 리턴
}
