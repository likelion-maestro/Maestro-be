package maestrogroup.core.team;


import maestrogroup.core.ExceptionHandler.BaseException;
import maestrogroup.core.ExceptionHandler.BaseResponseStatus;
import maestrogroup.core.mapping.MappingDao;
import maestrogroup.core.team.model.PatchTeamReq;
import maestrogroup.core.team.model.PostTeamReq;
import org.springframework.stereotype.Service;

@Service
public class TeamService {
    private final TeamDao teamDao;
    private final MappingDao mappingDao;
    private final TeamProvider teamProvider;

    public TeamService(TeamDao teamDao, MappingDao mappingDao, TeamProvider teamProvider) {
        this.teamDao = teamDao;
        this.mappingDao = mappingDao;
        this.teamProvider = teamProvider;
    }

    // 팀 생성시 현재 로그인 상태인 유저 (= 팀 그룹을 생성하려는 유저) 가 새롭게 생성된 팀에 종속하도록 api 수정되어야함
    public void createTeam(PostTeamReq postTeamReq){
        teamDao.createTeam(postTeamReq);
        System.out.println("postteamreq teamname:" +  postTeamReq.getTeamName());
        //PostTeamRes postTeamRes = teamDao.createTeam(postTeamReq);
        //return postTeamRes;
    }

    public void modifyTeam(PatchTeamReq patchTeamReq, int userIdx) throws BaseException {
        // 수정할 팀이 존재하는지 검증
        if (teamDao.isExistsTeam(patchTeamReq.getTeamIdx()) != 1) {
            throw new BaseException(BaseResponseStatus.NOT_EXISTS_TEAM);
        }

        // 수정한 내용이 공백일 경우에 대한 검증
        if (patchTeamReq.getTeamName() == null || patchTeamReq.getTeamName() == "") {
            throw new BaseException(BaseResponseStatus.INVALID_TEAM_NAME_FORM);
        }

        //User가 수정할 팀에 가입되어 있었는지 검증
        if (mappingDao.isUserInTeam(patchTeamReq.getTeamIdx(), userIdx) != 1) {
            throw new BaseException(BaseResponseStatus.USER_IS_NOT_IN_TEAM);
        }

        teamDao.modifyTeam(patchTeamReq);
    }

    public void deleteTeam(int teamIdx){
        teamDao.deleteTeam(teamIdx);
    }

    public void modifyTeamLeader(int userIdx1, int userIdx2, int teamIdx) throws BaseException {
        if (teamProvider.isLeader(userIdx1, teamIdx)) {
            throw new BaseException(BaseResponseStatus.INVALID_TEAM_AUTH);
        }
        teamDao.modifyTeamLeader(userIdx2, teamIdx);
    }
}










