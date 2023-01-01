package maestrogroup.core.mapping;

import maestrogroup.core.ExceptionHandler.BaseException;
import maestrogroup.core.ExceptionHandler.BaseResponse;
import maestrogroup.core.ExceptionHandler.BaseResponseStatus;
import maestrogroup.core.team.model.PostTeamReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MappingService {

    @Autowired
    private final MappingDao mappingDao;

    public MappingService(MappingDao mappingDao){
        this.mappingDao = mappingDao;
    }

    public void makeTeam(int userIdx, PostTeamReq postTeamReq) throws BaseException {
        // (팀 이름) 입력값이 유효한지에 대한 검증
        if(postTeamReq.getTeamName() == "" || postTeamReq.getTeamName() == null){
            throw new BaseException(BaseResponseStatus.INVALID_TEAM_VALUE);
        }

        if(postTeamReq.getTeamName().length() > 20){
            throw new BaseException(BaseResponseStatus.TOO_LONG_TEAM_VALUE);
        }

        mappingDao.makeTeam(userIdx, postTeamReq);
    }

    public void inviteUser(int teamIdx, int userIdx) throws BaseException{
        mappingDao.inviteUser(teamIdx, userIdx);
    }

    public void getOutOfTeam(int teamIdx, int userIdx) throws BaseException{
        // User가 탈퇴하고자 하는 팀에 가입되어 있는지에 대한 검증
        mappingDao.getOutOfTeam(teamIdx, userIdx);
    }

    public void changeImportanceOfTeam(int userIdx, int teamIdx) {
        // User가 별 표시 하려는 팀에 가입되어 있는지에 대한 검증
        mappingDao.changeImportanceOfTeam(userIdx, teamIdx);
    }
}








