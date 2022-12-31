package maestrogroup.core.mapping;

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

    public void makeTeam(int userIdx, PostTeamReq postTeamReq){
        // (팀 이름) 입력값이 유효한지에 대한 검증
        mappingDao.makeTeam(userIdx, postTeamReq);
    }

    public void inviteUser(int teamIdx, int userIdx){
        // 초대를 받는 User가 이미 Team에 가입되어 있는지에 대한 검증
        mappingDao.inviteUser(teamIdx, userIdx);
    }

    public void deleteTeam(int teamIdx, int userIdx){
        // User가 탈퇴하고자 하는 팀에 가입되어 있는지에 대한 검증
        mappingDao.deleteTeam(teamIdx, userIdx);
    }

    public void changeImportanceOfTeam(int userIdx, int teamIdx) {
        // User가 별 표시 하려는 팀에 가입되어 있는지에 대한 검증
        mappingDao.changeImportanceOfTeam(userIdx, teamIdx);
    }
}








