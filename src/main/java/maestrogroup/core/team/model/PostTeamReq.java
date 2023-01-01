package maestrogroup.core.team.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostTeamReq {
    @ApiModelProperty(example = "멋쟁이 사자처럼 밴드")
    private String teamName;
}
