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

    // 팀 생성시 현재 로그인 상태인 유저 (= 팀 그룹을 생성하려는 유저) 가 새롭게 생성된 팀에 종속하도록 api 수정되어야함
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

    public void modifyTeamLeader(int userIdx2, int teamIdx) {
        teamDao.modifyTeamLeader(userIdx2, teamIdx);
    }
}










