package maestrogroup.core.team.model;

import lombok.Data;

@Data
public class PatchTeamReq {
    private int teamIdx;
    private String teamName;

    public PatchTeamReq(int teamIdx, String teamName) {
        this.teamIdx = teamIdx;
        this.teamName = teamName;
    }
}
