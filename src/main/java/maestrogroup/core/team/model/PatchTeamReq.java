package maestrogroup.core.team.model;

import lombok.Data;

@Data
public class PatchTeamReq {
    private int teamIdx;
    private String teamName;
    private String teamImgUrl;

    public PatchTeamReq(int teamIdx, String teamName, String teamImgUrl) {
        this.teamIdx = teamIdx;
        this.teamName = teamName;
        this.teamImgUrl = teamImgUrl;
    }
}
