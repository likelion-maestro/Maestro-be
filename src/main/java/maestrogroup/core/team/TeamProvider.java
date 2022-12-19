package maestrogroup.core.team;

import maestrogroup.core.team.model.GetTeamRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamProvider {

    @Autowired
    private final TeamDao teamDao;

    public TeamProvider(TeamDao teamDao) {
        this.teamDao = teamDao;
    }

    public List<GetTeamRes> getAllTeam(){
        List<GetTeamRes> getTeamResList = teamDao.getAllTeam();
        return getTeamResList;
    }

    public int getLeaderIdx(int teamIdx) {
        return teamDao.getLeaderIdx(teamIdx);
    }
}
