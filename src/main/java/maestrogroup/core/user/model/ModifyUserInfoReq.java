package maestrogroup.core.user.model;
import lombok.Data;

// userIdx, createdAt, email, is_connected, nickname, password, status, updatedAt, userProfileImgUrl
@Data
public class ModifyUserInfoReq {
    private String email;
    private String nickname;
    private String password;
    private String userProfileImgUrl;
}
