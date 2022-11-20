package maestrogroup.core.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpUserRes {
    private int userIdx;
    private String email;
    private String password;
    private String nickname;
}
