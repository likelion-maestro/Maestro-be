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
    public BaseResponse modifyTeamLeader(@PathVariable("teamIdx") int teamIdx, int userIdx1, int userIdx2) {
        int nowLeaderIdx = teamProvider.getLeaderIdx(teamIdx);
        try {
            if (nowLeaderIdx == userIdx1) {
                teamService.modifyTeamLeader(userIdx2, teamIdx);
                return new BaseResponse<>();
            }
            else {
                throw new BaseException(BaseResponseStatus.INVALID_TEAM_AUTH);
            }
        } catch(BaseException baseException) {
            //userIdx1은 팀장이 아니므로 변경 권한이 없습니다.
            return new BaseResponse(baseException.getStatus());
        }
    }
}










