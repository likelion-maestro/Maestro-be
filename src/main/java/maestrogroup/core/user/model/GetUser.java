package maestrogroup.core.user.model;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class GetUser {
    private int userIdx;
    private Timestamp createdAt;
    private String email;
    private int is_connected;
    private String nickname;
    private String password;
    private String status;
    private Timestamp updatedAt;
    private String userProfileImgUrl;
}
