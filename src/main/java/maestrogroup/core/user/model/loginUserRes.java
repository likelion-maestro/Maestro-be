package maestrogroup.core.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class loginUserRes {
    private int userIdx;
    private String email;
    private String nickname;
    private String password;
    private String userProfileImgUrl;
}
