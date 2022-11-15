package maestrogroup.core.mapping;

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

    public MappingController(MappingService mappingService, MappingProvider mappingProvider, MappingDao mappingDao){
        this.mappingService = mappingService;
        this.mappingProvider = mappingProvider;
        this.mappingDao = mappingDao;
    }


    // 최초로 팀 그룹 생성하기 => 현재 로그인한 유저가 자동으로 팀 그룹에 속하도록 추가적인 API 개발 요망
    // 즉, Team 객체가 생성됨과 동시에 Mapping 객체도 생성되어서 해당 Team 과 User 를 매핑 시켜줘야함
    // 지금 API 에서는 일단 userIdx 로 임의의 실험을 위해 로그인한 유저의 pk 값을 받아오도록 함. 나중에 꼭 수정하자!

    // team 객체와 mapping 객체를 생성
    @ResponseBody
    @PostMapping("makeTeam/{userIdx}")
    public void makeTeam(@PathVariable("userIdx") int userIdx, @RequestBody PostTeamReq postTeamReq){
        System.out.println(userIdx);
        System.out.println(postTeamReq.getTeamName());
        System.out.println(postTeamReq.getTeamImgUrl());
        mappingService.makeTeam(userIdx, postTeamReq);
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
    public void inviteUser(@PathVariable("teamIdx") int teamIdx, @PathVariable("userIdx") int userIdx){
        mappingService.inviteUser(teamIdx, userIdx);
    }


    // 해당 그룹을 특정 유저가 탈퇴할떄 Mapping 객체가 삭제되도록 구현
    // + 그리고 해당 그룹 팀의 총 인원수가 0명일떄, 그룹 객체(Team 객체)도 Mapping객체와 함께 삭제되도록 구현
    @DeleteMapping("deleteTeam/{teamIdx}")
    public void deleteTeam(@PathVariable("teamIdx") int teamIdx){

    }

    // 특정 팀 그룹에 속하는 모든 팀멤버 출력 : ManyToMany
    @GetMapping("/getTeamMembers/{teamIdx}")
    public List<GetUser> getTeamMembers(@PathVariable("teamIdx") int teamIdx){
        return mappingProvider.getTeamMembers(teamIdx);
    }

    // 특정 유저가 속해있는 모든 팀 그룹 출력 : ManyToMany
    @GetMapping("/getTeamList/{userIdx}")
    public List<GetTeamRes> getTeamList(@PathVariable("userIdx") int userIdx){
        return mappingProvider.getTeamList(userIdx);
    }
}







