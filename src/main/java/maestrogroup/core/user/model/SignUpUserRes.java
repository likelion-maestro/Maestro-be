package maestrogroup.core.user.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpUserRes {
    @ApiModelProperty(example = "3")
    private int userIdx;

    @ApiModelProperty(example = "abcde3@naver.com")
    private String email;

    @ApiModelProperty(example = "COMIBCntXT+usiVZO7dtAw==")
    private String password;

    @ApiModelProperty(example = "member3")
    private String nickname;
}
