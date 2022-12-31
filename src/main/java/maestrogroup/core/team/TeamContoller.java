package maestrogroup.core.team;

import maestrogroup.core.ExceptionHandler.BaseException;
import maestrogroup.core.ExceptionHandler.BaseResponse;
import maestrogroup.core.Security.JwtService;
import maestrogroup.core.team.model.PatchTeamReq;
import maestrogroup.core.team.model.PostTeamReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeamContoller {
    @Autowired // 의존관계 주입
    private final TeamDao teamDao;
    @Autowired
    private final TeamProvider teamProvider;
    @Autowired
    private final TeamService teamService;
    @Autowired
    private final JwtService jwtService;
    public TeamContoller(TeamDao teamDao, TeamProvider teamProvider, TeamService teamService, JwtService jwtService) {
        this.teamDao = teamDao;
        this.teamProvider = teamProvider;
        this.teamService = teamService;
        this.jwtService = jwtService;
    }

//    @ResponseBody
//    @PostMapping("/create_team")
//    public void createTeam(@RequestBody PostTeamReq postTeamReq){
//        teamService.createTeam(postTeamReq);
//        //PostTeamRes postTeamRes = teamService.createTeam(postTeamReq);
//        //return postTeamRes;
//    }

//    @ResponseBody
//    @GetMapping("/get_all_team")
//    public List<GetTeamRes> getAllTeam(){
//        List<GetTeamRes> getTeamResList = teamProvider.getAllTeam();
//        return getTeamResList;
//    }

    @ResponseBody
    @PatchMapping("/patch_team/{teamIdx}")
    public BaseResponse modifyTeam(@PathVariable("teamIdx") int teamIdx, @RequestBody PostTeamReq postTeamReq) throws BaseException {
        try {
            PatchTeamReq patchUserReq = new PatchTeamReq(teamIdx, postTeamReq.getTeamName());
            teamService.modifyTeam(patchUserReq);
            return new BaseResponse();
        } catch (BaseException baseException) {
            return new BaseResponse(baseException.getStatus());
        }
    }

    @ResponseBody
    @DeleteMapping("/delete_team/{teamIdx}")
    public BaseResponse deleteTeam(@PathVariable("teamIdx") int teamIdx) throws BaseException{
        try {
            int userIdx = jwtService.getUserIdx();
            teamService.deleteTeam(teamIdx, userIdx);
            return new BaseResponse();
        } catch (BaseException baseException) {
            return new BaseResponse(baseException.getStatus());
        }
    }

//    @ResponseBody
//    @PatchMapping("/change_team_leader/{teamIdx}/{userIdx1}/{userIdx2}")
//    public void modifyTeamLeader(@PathVariable("teamIdx")  int teamIdx, @PathVariable("userIdx1") int userIdx1, @PathVariable("userIdx2") int userIdx2) throws BaseException {
//        teamService.modifyTeamLeader(userIdx1, userIdx2, teamIdx);
//    }
}










