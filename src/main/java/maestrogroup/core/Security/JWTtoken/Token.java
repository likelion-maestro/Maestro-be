package maestrogroup.core.Security.JWTtoken;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    private String refreshToken;
    private String accessToken;
}
