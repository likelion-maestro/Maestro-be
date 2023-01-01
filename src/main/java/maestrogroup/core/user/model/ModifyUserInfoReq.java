package maestrogroup.core.user.model;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// userIdx, createdAt, email, is_connected, nickname, password, status, updatedAt, userProfileImgUrl
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyUserInfoReq {
    @ApiModelProperty(example = "ABCDE2@naver.com")
    private String email;

    @ApiModelProperty(example = "MEMBER2")
    private String nickname;

    @ApiModelProperty(example = "edcba4321")
    private String password;
}
