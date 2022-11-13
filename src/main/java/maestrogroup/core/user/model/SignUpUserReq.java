package maestrogroup.core.user.model;

import lombok.*;

// userIdx, createdAt, email, is_connected, nickname, password, status, updatedAt, userProfileImgUrl
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpUserReq {
    private String email;
    private String password;
    private String Repassword;
    private String nickname;
}
/*
private int userIdx;
    private String email;
    private String nickname;
    private String password;
    private String userProfileImgUrl;
 */
