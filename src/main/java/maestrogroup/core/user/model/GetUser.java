package maestrogroup.core.user.model;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class GetUser {
    private int userIdx;
    private String email;
    private String nickname;
    private String password;
}
