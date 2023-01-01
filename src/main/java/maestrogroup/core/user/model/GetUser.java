package maestrogroup.core.user.model;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class GetUser {
    @ApiModelProperty(example = "3")
    private int userIdx;

    @ApiModelProperty(example = "abced3@naver.com")
    private String email;

    @ApiModelProperty(example = "member3")
    private String nickname;

    @ApiModelProperty(example = "COMIBCntXT+usiVZO7dtAw==")
    private String password;
}
