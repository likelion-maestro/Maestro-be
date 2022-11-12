package maestrogroup.core.team;


import maestrogroup.core.team.model.PostTeamReq;
import maestrogroup.core.team.model.PostTeamRes;
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
}



