package maestrogroup.core.team.model;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class PostTeamRes {
    @ApiModelProperty(example = "1")
    private int teamIdx;

    @ApiModelProperty(example = "멋쟁이 사자처럼 밴드")
    private String teamName;

    @ApiModelProperty(example = "1")
    private int count;

    public PostTeamRes(int teamIdx, String teamName, String teamImgUrl, int count) {
        this.teamIdx = teamIdx;
        this.teamName = teamName;
        this.count = count;
    }
}
