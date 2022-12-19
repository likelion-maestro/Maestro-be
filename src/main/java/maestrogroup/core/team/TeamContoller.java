package maestrogroup.core.team;

import maestrogroup.core.ExceptionHandler.BaseException;
import maestrogroup.core.ExceptionHandler.BaseResponse;
import maestrogroup.core.ExceptionHandler.BaseResponseStatus;
import maestrogroup.core.team.model.GetTeamRes;
import maestrogroup.core.team.model.PatchTeamReq;
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

    @ResponseBody
    @PatchMapping("/patch_team/{teamIdx}")
    public void modifyTeam(@PathVariable("teamIdx") int teamIdx, @RequestBody PostTeamReq postTeamReq){
        PatchTeamReq patchUserReq = new PatchTeamReq(teamIdx, postTeamReq.getTeamName(), postTeamReq.getTeamImgUrl());
        teamService.modifyTeam(patchUserReq);
    }

    @ResponseBody
    @DeleteMapping("/delete_team/{teamIdx}")
    public void deleteTeam(@PathVariable("teamIdx") int teamIdx){
        teamService.deleteTeam(teamIdx);
    }

    @ResponseBody
    @PatchMapping("/change_team_leader/{teamIdx}/{userIdx1}/{userIdx2}")
    public void modifyTeamLeader(@PathVariable("teamIdx")  int teamIdx, @PathVariable("userIdx1") int userIdx1, @PathVariable("userIdx2") int userIdx2) throws BaseException {
        teamService.modifyTeamLeader(userIdx1, userIdx2, teamIdx);
    }
}










