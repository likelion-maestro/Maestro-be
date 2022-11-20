package maestrogroup.core.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// userIdx, createdAt, email, is_connected, nickname, password, status, updatedAt, userProfileImgUrl
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserSomeField {
    private int userIdx;
    private String email;
    private String nickname;
    private String password;
}
