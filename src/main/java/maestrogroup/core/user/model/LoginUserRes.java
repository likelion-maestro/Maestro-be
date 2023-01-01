package maestrogroup.core.user.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginUserRes {
    @ApiModelProperty(example = "2")
    private int userIdx;

    @ApiModelProperty(example = "abcde2@naver.com")
    private String email;

    @ApiModelProperty(example = "member2")
    private String nickname;

    @ApiModelProperty(example = "eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJ1c2VySWR4IjoyLCJpYXQiOjE2NzI1ODY1MjgsImV4cCI6MTY3MjU4ODMyOH0.9jucKLYE3m1WTvkYdKuvOoo0zN3guDcStuvJWg18lWc")
    private String accessToken; // 로그인에 성공하면 jwt 토큰값도 리턴

    @ApiModelProperty(example = "eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJ1c2VySWR4IjoyLCJpYXQiOjE2NzI1ODY1MjgsImV4cCI6MTY3MzE5MTMyOH0.-Xb5pLfdNE7nVxYu6MQRJzsPAyBNMKv382WrERTLJgw")
    private String refreshToken;
}
