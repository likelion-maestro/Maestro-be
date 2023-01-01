package maestrogroup.core.mapping;

import maestrogroup.core.ExceptionHandler.BaseException;
import maestrogroup.core.mapping.model.GetTeamAndImportantRes;
import maestrogroup.core.mapping.model.Mapping;
import maestrogroup.core.team.model.GetTeamRes;
import maestrogroup.core.user.model.GetUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MappingProvider {

    @Autowired
    private final MappingDao mappingDao;

    public MappingProvider(MappingDao mappingDao){
        this.mappingDao = mappingDao;
    }

    public List<GetUser> getTeamMembers(int teamIdx) throws BaseException {
        return mappingDao.getTeamMembers(teamIdx);
    }

    public List<GetTeamRes> getTeamList(int teamIdx){
        return mappingDao.getTeamList(teamIdx);
    }

    public List<GetTeamAndImportantRes> getTeamAndImportant(int userIdx) throws BaseException{
        return mappingDao.getTeamAndImportant(userIdx);
    }
}
