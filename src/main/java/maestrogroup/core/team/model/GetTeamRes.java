package maestrogroup.core.team.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTeamRes {
    private int teamIdx;
    private String teamName;
    private String teamImgUrl;
    private int count;
    private int leaderIdx;

//    public GetTeamRes(int teamIdx, String teamName, String teamImgUrl, int count, int leaderIdx) {
//        this.teamIdx = teamIdx;
//        this.teamName = teamName;
//        this.teamImgUrl = teamImgUrl;
//        this.count = count;
//        this.leaderIdx = leaderIdx;
//    }
}
