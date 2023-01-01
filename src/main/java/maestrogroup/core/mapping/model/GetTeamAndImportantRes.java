package maestrogroup.core.mapping.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetTeamAndImportantRes {
    @ApiModelProperty(example = "4")
    private int teamIdx;

    @ApiModelProperty(example = "멋쟁이 사자처럼 밴드")
    private String teamName;

    @ApiModelProperty(example = "7")
    private int count;

    @ApiModelProperty(example = "0")
    private int important;
}