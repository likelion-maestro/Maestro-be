package maestrogroup.core.team;


import maestrogroup.core.team.model.PatchTeamReq;
import maestrogroup.core.team.model.PostTeamReq;
import org.springframework.stereotype.Service;

@Service
public class TeamService {
    private final TeamDao teamDao;
    private final TeamProvider teamProvider;

    public TeamService(TeamDao teamDao, TeamProvider teamProvider) {
        this.teamDao = teamDao;
        this.teamProvider = teamProvider;
    }

    public void createTeam(PostTeamReq postTeamReq){
        teamDao.createTeam(postTeamReq);
        System.out.println("postteamreq teamname:" +  postTeamReq.getTeamName());
        //PostTeamRes postTeamRes = teamDao.createTeam(postTeamReq);
        //return postTeamRes;
    }

    public void modifyTeam(PatchTeamReq patchTeamReq){
        teamDao.modifyTeam(patchTeamReq);
    }

    public void deleteTeam(int teamIdx){
        teamDao.deleteTeam(teamIdx);
    }
}



