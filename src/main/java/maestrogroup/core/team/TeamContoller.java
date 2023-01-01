package maestrogroup.core.team;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import maestrogroup.core.ExceptionHandler.BaseException;
import maestrogroup.core.ExceptionHandler.BaseResponse;
import maestrogroup.core.Security.JwtService;
import maestrogroup.core.team.model.GetTeamRes;
import maestrogroup.core.team.model.PatchTeamReq;
import maestrogroup.core.team.model.PostTeamReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "Team", tags = "Team 관련 API")
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

    @ResponseBody
    @GetMapping("/get_all_team")
    @Operation(summary = "모든 팀의 정보 조회 (테스트 전용)", description = "서비스 내 모든 팀의 정보를 조회합니다. 단순 테스트 용이니 참고 바랍니다.")
    public List<GetTeamRes> getAllTeam(){
        List<GetTeamRes> getTeamResList = teamProvider.getAllTeam();
        return getTeamResList;
    }

    @ResponseBody
    @PatchMapping("/patch_team/{teamIdx}")
    @Operation(summary = "팀 정보 수정", description = "jwt 를 HttpHeader에 넘겨주시고 수정해주세요. 수정을 시도하는 User가 해당 팀원이 아닐 경우 에러를 발생시킵니다. 입력이 누락된 데이터가 있을 시 에러를 발생시킵니다.")
    public BaseResponse modifyTeam(@PathVariable("teamIdx") int teamIdx, @RequestBody PostTeamReq postTeamReq){
        try {
            int userIdx = jwtService.getUserIdx();
            PatchTeamReq patchUserReq = new PatchTeamReq(teamIdx, postTeamReq.getTeamName());
            teamService.modifyTeam(patchUserReq, userIdx);
            return new BaseResponse();
        } catch (BaseException baseException) {
            return new BaseResponse(baseException.getStatus());
        }
    }

//    @ResponseBody
//    @DeleteMapping("/delete_team/{teamIdx}")
//    @Operation(summary = "팀 삭제", description = "jwt 를 HttpHeader에 넘겨주시고 삭제해주세요. 수정을 시도하는 User가 해당 팀원이 아닐 경우 에러를 발생시킵니다.")
//    public BaseResponse deleteTeam(@PathVariable("teamIdx") int teamIdx){
//        try {
//            int userIdx = jwtService.getUserIdx();
//            teamService.deleteTeam(teamIdx, userIdx);
//            return new BaseResponse();
//        } catch (BaseException baseException) {
//            return new BaseResponse(baseException.getStatus());
//        }
//    }

//    @ResponseBody
//    @PatchMapping("/change_team_leader/{teamIdx}/{userIdx1}/{userIdx2}")
//    public void modifyTeamLeader(@PathVariable("teamIdx")  int teamIdx, @PathVariable("userIdx1") int userIdx1, @PathVariable("userIdx2") int userIdx2) throws BaseException {
//        teamService.modifyTeamLeader(userIdx1, userIdx2, teamIdx);
//    }
}










