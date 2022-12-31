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
    private final TeamProvider teamProvider;
    private final MappingDao mappingDao;

    public TeamService(TeamDao teamDao, TeamProvider teamProvider, MappingDao mappingDao) {
        this.teamDao = teamDao;
        this.teamProvider = teamProvider;
        this.mappingDao = mappingDao;
    }

    // 팀 생성시 현재 로그인 상태인 유저 (= 팀 그룹을 생성하려는 유저) 가 새롭게 생성된 팀에 종속하도록 api 수정되어야함
    public void createTeam(PostTeamReq postTeamReq){
        teamDao.createTeam(postTeamReq);
        System.out.println("postteamreq teamname:" +  postTeamReq.getTeamName());
        //PostTeamRes postTeamRes = teamDao.createTeam(postTeamReq);
        //return postTeamRes;
    }

    public void modifyTeam(PatchTeamReq patchTeamReq) throws BaseException {
        if (patchTeamReq.getTeamName() == null || patchTeamReq.getTeamName() == "") {
            throw new BaseException(BaseResponseStatus.INVALID_TEAM_NAME_FORM);
        }

        try {
            teamDao.modifyTeam(patchTeamReq);
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.SERVER_ERROR);
        }
    }

    public void deleteTeam(int teamIdx, int userIdx) throws BaseException {
        //User가 삭제할 팀에 가입되어 있었는지 검증
        if (mappingDao.isUserInTeam(teamIdx, userIdx) != 1) {
            throw new BaseException(BaseResponseStatus.USER_IS_NOT_IN_TEAM);
        }

        try {
            teamDao.deleteTeam(teamIdx);
        } catch (Exception exception) {
            throw new BaseException(BaseResponseStatus.SERVER_ERROR);
        }
    }

    public void modifyTeamLeader(int userIdx1, int userIdx2, int teamIdx) throws BaseException {
        if (teamProvider.isLeader(userIdx1, teamIdx)) {
            throw new BaseException(BaseResponseStatus.INVALID_TEAM_AUTH);
        }
        teamDao.modifyTeamLeader(userIdx2, teamIdx);
    }
}










