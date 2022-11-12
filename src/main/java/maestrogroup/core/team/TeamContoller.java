package maestrogroup.core.team;

import maestrogroup.core.team.model.GetTeamRes;
import maestrogroup.core.team.model.PostTeamReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TeamContoller {
    @Autowired // 의존관계 주입
    private final TeamDao teamDao;
    @Autowired
    private final TeamProvider teamProvider;
    @Autowired
    private final TeamService teamService;

    public TeamContoller(TeamDao teamDao, TeamProvider teamProvider, TeamService teamService) {
        this.teamDao = teamDao;
        this.teamProvider = teamProvider;
        this.teamService = teamService;
    }

    @ResponseBody
    @PostMapping("/create_team")
    public void createTeam(@RequestBody PostTeamReq postTeamReq){
        teamService.createTeam(postTeamReq);
        //PostTeamRes postTeamRes = teamService.createTeam(postTeamReq);
        //return postTeamRes;
    }

    @ResponseBody
    @GetMapping("/get_all_team")
    public List<GetTeamRes> getAllTeam(){
        List<GetTeamRes> getTeamResList = teamProvider.getAllTeam();
        return getTeamResList;
    }
}









