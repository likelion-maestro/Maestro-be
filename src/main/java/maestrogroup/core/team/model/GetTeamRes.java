package maestrogroup.core.team.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTeamRes {
    @ApiModelProperty(example = "1")
    private int teamIdx;

    @ApiModelProperty(example = "멋쟁이 사자처럼 밴드")
    private String teamName;

    @ApiModelProperty(example = "7")
    private int count;

//    public GetTeamRes(int teamIdx, String teamName, String teamImgUrl, int count, int leaderIdx) {
//        this.teamIdx = teamIdx;
//        this.teamName = teamName;
//        this.teamImgUrl = teamImgUrl;
//        this.count = count;
//        this.leaderIdx = leaderIdx;
//    }
}
