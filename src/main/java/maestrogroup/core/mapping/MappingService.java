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
        mappingDao.makeTeam(userIdx, postTeamReq);
    }

    public void inviteUser(int teamIdx, int userIdx){
        mappingDao.inviteUser(teamIdx, userIdx);
    }
}








