package maestrogroup.core.Security.JWTtoken;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken{
    @ApiModelProperty(example = "eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJ1c2VySWR4IjoxMSwiaWF0IjoxNjcyNTg3MjgxLCJleHAiOjE2NzMxOTIwODF9.oCGljA9e36qJoDS9jsfO3nLijNYoxvHM3ib01UF8-68")
    String refreshToken;
}
