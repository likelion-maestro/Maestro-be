package maestrogroup.core.mapping.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserIdx {
    @ApiModelProperty(example = "4")
    int userIdx;
}
