package maestrogroup.core.mapping;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import maestrogroup.core.ExceptionHandler.BaseException;
import maestrogroup.core.ExceptionHandler.BaseResponse;
import maestrogroup.core.Security.JwtService;
import maestrogroup.core.mapping.model.GetTeamAndImportantRes;
import maestrogroup.core.team.model.GetTeamRes;
import maestrogroup.core.team.model.PostTeamReq;
import maestrogroup.core.user.model.GetUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/mapping")
@Api(value = "Mapping", tags = "Mapping 관련 API (User와 Team 간 관계)")
@RestController
public class MappingController {
    @Autowired
    private final MappingService mappingService;
    @Autowired
    private final MappingProvider mappingProvider;
    @Autowired
    private final MappingDao mappingDao;

    @Autowired
    private final JwtService jwtService;

    public MappingController(MappingService mappingService, MappingProvider mappingProvider, MappingDao mappingDao, JwtService jwtService){
        this.mappingService = mappingService;
        this.mappingProvider = mappingProvider;
        this.mappingDao = mappingDao;
        this.jwtService = jwtService;
    }


    // 최초로 팀 그룹 생성하기 => 현재 로그인한 유저가 자동으로 팀 그룹에 속하도록 추가적인 API 개발 요망
    // 즉, Team 객체가 생성됨과 동시에 Mapping 객체도 생성되어서 해당 Team 과 User 를 매핑 시켜줘야함
    // 지금 API 에서는 일단 userIdx 로 임의의 실험을 위해 로그인한 유저의 pk 값을 받아오도록 함. 나중에 꼭 수정하자!

    // team 객체와 mapping 객체를 생성
    @ResponseBody
    @PostMapping("/makeTeam")
    @Operation(summary = "팀 생성", description = "jwt 를 HttpHeader에 넘겨주시고 생성해주세요. 누락된 정보가 있거나 20글자를 초과할 때 에러를 발생시켰습니다.")
    public BaseResponse makeTeam(@RequestBody PostTeamReq postTeamReq) {
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            mappingService.makeTeam(userIdxByJwt, postTeamReq);
            return new BaseResponse();
        } catch (BaseException baseException){
            return new BaseResponse(baseException.getStatus());
        }
    }
    /*
    @ResponseBody
    @PostMapping("/create_team")
    public void createTeam(@RequestBody PostTeamReq postTeamReq){
        teamService.createTeam(postTeamReq);
        //PostTeamRes postTeamRes = teamService.createTeam(postTeamReq);
        //return postTeamRes;
    }
     */

    // 이전에 생성된 팀원 그룹에 특정 유저를 초대하기 : mapping 객체만 생성함
    @PostMapping("/inviteUser/{teamIdx}/{userIdx}")
    @Operation(summary = "상대방을 팀으로 초대", description = "유저 인덱스가 userIdx인 상대방을 팀 인덱스가 teamIdx인 팀으로 초대합니다.")
    public BaseResponse inviteUser(@PathVariable("teamIdx") int teamIdx, @PathVariable("userIdx") int userIdx) {
        try {
            mappingService.inviteUser(teamIdx, userIdx);
            return new BaseResponse();
        } catch (BaseException baseException){
            return new BaseResponse(baseException.getStatus());
        }
    }


    // 팀 삭제 => 팀 객체, 해당 팀의 모든 유저들에 대한 Mapping 객체들이 모두 삭제되도록 구현
    @DeleteMapping("/deleteTeam/{teamIdx}")
    @Operation(summary = "팀 삭제")
    public BaseResponse deleteTeam(@PathVariable("teamIdx") int teamIdx){
        try{
            mappingService.deleteTeam(teamIdx);
            return new BaseResponse();
        } catch (BaseException baseException){
            return new BaseResponse(baseException.getStatus());
        }
    }

    // 팀 탈퇴
    // => 해당 유저에 대한 Mapping 객체가 삭제되도록 구현
    // 또한 해당 팀의 총 인워수가 0이 되는 경우라면, 해당 팀 객체 또한 삭제되도록 구현
    @DeleteMapping("/getOutOfTeam/{teamIdx}")
    @Operation(summary = "팀 탈퇴", description = "jwt 를 HttpHeader에 넘겨주시고 수행해주세요. 탈퇴를 시도하는 User가 해당 팀에 가입되어 있지 않았을 때 에러를 발생시켰습니다.")
    public BaseResponse getOutOfTeam(@PathVariable("teamIdx") int teamIdx)  throws BaseException{
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            mappingService.getOutOfTeam(teamIdx, userIdxByJwt);
            return new BaseResponse();
        } catch (BaseException baseException){
            return new BaseResponse(baseException.getStatus());
        }
    }

    // 특정 팀 그룹에 속하는 모든 팀멤버 출력 : ManyToMany
    @GetMapping("/getTeamMembers/{teamIdx}")
    @Operation(summary = "팀에 속한 팀원들 조회")
    public BaseResponse<List<GetUser>> getTeamMembers(@PathVariable("teamIdx") int teamIdx){
        try {
            List<GetUser> getUserList = mappingProvider.getTeamMembers(teamIdx);
            return new BaseResponse<>(getUserList);
        } catch (BaseException baseException){
            return new BaseResponse(baseException.getStatus());
        }
    }

//    // 특정 유저가 속해있는 모든 팀 그룹 출력 : ManyToMany
//    @GetMapping("/getTeamList")
//    public BaseResponse<List<GetTeamRes>> getTeamList() throws BaseException{
//        try {
//            int userIdxByJwt = jwtService.getUserIdx();
//            List<GetTeamRes> getTeamResList = mappingProvider.getTeamList(userIdxByJwt);
//            return new BaseResponse<List<GetTeamRes>>(getTeamResList);
//        } catch(BaseException baseException){
//            return new BaseResponse(baseException.getStatus());
//        }
//    }

    @PatchMapping("/changeImportanceOfTeam/{teamIdx}")
    @Operation(summary = "팀 별 표시 기능", description = "jwt 를 HttpHeader에 넘겨주시고 수행해주세요. 팀의 중요도를 나타내는 별 표시 기능입니다. important가 0이라면 1이 되고, 1이라면 0이 되도록 구현했습니다. 별 표시를 시도하는 User가 해당 팀에 가입되어 있지 않았을 때 에러를 발생시켰습니다.")
    public BaseResponse changeImportanceOfTeam(@PathVariable("teamIdx") int teamIdx) throws BaseException {
        try {
            int userIdx = jwtService.getUserIdx();
            mappingService.changeImportanceOfTeam(userIdx, teamIdx);
            return new BaseResponse();
        } catch (BaseException baseException){
            return new BaseResponse(baseException.getStatus());
        }
    }

//    @GetMapping("/getTeamListAndImportant")
    @GetMapping("/getTeamList")
    @Operation(summary = "본인이 속한 팀 목록 조회", description = "jwt 를 HttpHeader에 넘겨주시고 수행해주세요. 현재 User가 속한 팀의 목록 및 정보들을 조회합니다.")
    public BaseResponse<List<GetTeamAndImportantRes>> getTeamListAndImportant() throws BaseException{
        try {
            int userIdx = jwtService.getUserIdx();
            List<GetTeamAndImportantRes> getTeamAndImportantResList = mappingProvider.getTeamAndImportant(userIdx);
            return new BaseResponse<List<GetTeamAndImportantRes>>(getTeamAndImportantResList);
        } catch (BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
    }
}







