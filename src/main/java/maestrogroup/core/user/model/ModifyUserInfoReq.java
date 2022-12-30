package maestrogroup.core.user.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// userIdx, createdAt, email, is_connected, nickname, password, status, updatedAt, userProfileImgUrl
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyUserInfoReq {
    private String email;
    private String nickname;
    private String password;
}
