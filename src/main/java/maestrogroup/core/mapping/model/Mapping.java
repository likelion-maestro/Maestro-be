package maestrogroup.core.mapping.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mapping {
    @ApiModelProperty(example = "1")
    int mappingIdx;

    @ApiModelProperty(example = "0")
    int important;

    @ApiModelProperty(example = "1")
    int teamIdx;

    @ApiModelProperty(example = "4")
    int userIdx;
}
