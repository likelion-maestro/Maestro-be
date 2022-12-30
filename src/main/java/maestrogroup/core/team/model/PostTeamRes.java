package maestrogroup.core.team.model;

import lombok.Data;


@Data
public class PostTeamRes {
    private int teamIdx;
    private String teamName;
    private int count;

    public PostTeamRes(int teamIdx, String teamName, String teamImgUrl, int count) {
        this.teamIdx = teamIdx;
        this.teamName = teamName;
        this.count = count;
    }
}
