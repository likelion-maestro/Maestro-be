package maestrogroup.core.mapping;

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
    public void makeTeam(@RequestBody PostTeamReq postTeamReq) throws BaseException {
        int userIdxByJwt = jwtService.getUserIdx();
        mappingService.makeTeam(userIdxByJwt, postTeamReq);
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
    public void inviteUser(@PathVariable("teamIdx") int teamIdx) throws BaseException {
        int userIdx = jwtService.getUserIdx();
        mappingService.inviteUser(teamIdx, userIdx);
    }


    // 해당 그룹을 특정 유저가 탈퇴할떄 teamIdx 값을 가지는 Mapping 객체가 삭제되도록 구현
    // + 그리고 해당 그룹 팀의 총 인원수가 0명일떄, 그룹 객체(Team 객체)도 Mapping객체와 함께 삭제되도록 구현
    @DeleteMapping("/deleteTeam/{teamIdx}")
    public void deleteTeam(@PathVariable("teamIdx") int teamIdx) throws BaseException {
        int userIdxByJwt = jwtService.getUserIdx();
        mappingService.deleteTeam(teamIdx, userIdxByJwt);
    }

    // 특정 팀 그룹에 속하는 모든 팀멤버 출력 : ManyToMany
    @GetMapping("/getTeamMembers/{teamIdx}")
    public List<GetUser> getTeamMembers(@PathVariable("teamIdx") int teamIdx){
        return mappingProvider.getTeamMembers(teamIdx);
    }

//    // 특정 유저가 속해있는 모든 팀 그룹 출력 : ManyToMany
//    @GetMapping("/getTeamList")
//    public BaseResponse<List<GetTeamRes>> getTeamList(){
//        try {
//            int userIdxByJwt = jwtService.getUserIdx();
//            List<GetTeamRes> getTeamResList = mappingProvider.getTeamList(userIdxByJwt);
//            return new BaseResponse<List<GetTeamRes>>(getTeamResList);
//        } catch(BaseException baseException){
//            return new BaseResponse(baseException.getStatus());
//        }
//    }

    @PatchMapping("/changeImportanceOfTeam/{teamIdx}")
    public void changeImportanceOfTeam(@PathVariable("teamIdx") int teamIdx) throws BaseException {
        int userIdx = jwtService.getUserIdx();
        mappingService.changeImportanceOfTeam(userIdx, teamIdx);
    }

    @GetMapping("/getTeamList")
    public BaseResponse<List<GetTeamAndImportantRes>> getTeamListAndImportant() throws BaseException {
        try {
            int userIdx = jwtService.getUserIdx();
            List<GetTeamAndImportantRes> getTeamAndImportantResList = mappingProvider.getTeamAndImportant(userIdx);
            return new BaseResponse<List<GetTeamAndImportantRes>>(getTeamAndImportantResList);
        } catch (BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
    }
}







