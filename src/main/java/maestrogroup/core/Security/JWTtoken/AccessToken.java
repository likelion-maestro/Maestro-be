package maestrogroup.core.Security.JWTtoken;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessToken {
    @ApiModelProperty(example = "eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJ1c2VySWR4IjoxMSwiaWF0IjoxNjcyNTg3MjgxLCJleHAiOjE2NzI1ODkwODF9.0zPVjNqR3B9vlCvYM6h3fdMduJoh6gak5mowOv84-KU")
    String accessToken;
}
