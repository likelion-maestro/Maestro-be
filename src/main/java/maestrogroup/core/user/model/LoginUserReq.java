package maestrogroup.core.user.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserReq {
    @ApiModelProperty(example = "abcde2@naver.com")
    private String email;

    @ApiModelProperty(example = "abcde1234")
    private String password;
}

/*
private int userIdx;
    private String email;
    private String nickname;
    private String password;
    private String userProfileImgUrl;
 */