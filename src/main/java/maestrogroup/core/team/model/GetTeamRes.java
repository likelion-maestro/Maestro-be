package maestrogroup.core.team.model;

import lombok.Data;

@Data
public class GetTeamRes {
    private int teamIdx;
    private String teamName;
    private String teamImgUrl;
    private int count;

    public GetTeamRes(int teamIdx, String teamName, String teamImgUrl, int count) {
        this.teamIdx = teamIdx;
        this.teamName = teamName;
        this.teamImgUrl = teamImgUrl;
        this.count = count;
    }
}
