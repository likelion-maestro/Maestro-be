package maestrogroup.core.folder;

import maestrogroup.core.ExceptionHandler.BaseException;
import maestrogroup.core.ExceptionHandler.BaseResponseStatus;
import maestrogroup.core.folder.model.Folder;
import maestrogroup.core.folder.model.PostFolderReq;
import maestrogroup.core.team.TeamDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FolderProvider {

    @Autowired
    private final FolderDao folderDao;

    @Autowired
    private final TeamDao teamDao;

    public FolderProvider(FolderDao folderDao, TeamDao teamDao) {
        this.folderDao = folderDao;
        this.teamDao = teamDao;
    }

    public List<Folder> GetAllFolder(int teamIdx) throws BaseException {
        if (teamDao.isExistsTeam(teamIdx) != 1) {
            throw new BaseException(BaseResponseStatus.NOT_EXISTS_TEAM);
        }
        return folderDao.GetAllFolder(teamIdx);
    }
}
