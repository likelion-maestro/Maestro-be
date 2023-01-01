package maestrogroup.core.team.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PatchTeamReq {
    @ApiModelProperty(example = "1")
    private int teamIdx;

    @ApiModelProperty(example = "멋쟁이 사자처럼 밴드2")
    private String teamName;

    public PatchTeamReq(int teamIdx, String teamName) {
        this.teamIdx = teamIdx;
        this.teamName = teamName;
    }
}
