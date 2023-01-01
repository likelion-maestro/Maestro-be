package maestrogroup.core.folder;

import maestrogroup.core.ExceptionHandler.BaseException;
import maestrogroup.core.ExceptionHandler.BaseResponseStatus;
import maestrogroup.core.folder.model.PostFolderReq;
import maestrogroup.core.team.TeamDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FolderService {

    @Autowired
    private final FolderDao folderDao;
    @Autowired
    private final TeamDao teamDao;

    public FolderService(FolderDao folderDao, TeamDao teamDao) {
        this.folderDao = folderDao;
        this.teamDao = teamDao;
    }

    //createFolder
    public void createFolder(PostFolderReq postFolderReq, int teamIdx) throws BaseException {
        //User가 Folder를 생성하고자 하는 팀에 속해있는지에 대한 검증
        // Team이 존재하지 않을 때에 대한 예외처리
        if (teamDao.isExistsTeam(teamIdx) != 1) {
            throw new BaseException(BaseResponseStatus.NOT_EXISTS_TEAM);
        }

        folderDao.createFolder(postFolderReq, teamIdx);
    }
    public void modifyFolder(int folderIdx, int teamIdx, ModifyFolderReq modifyFolderReq) throws BaseException{
        if (teamDao.isExistsTeam(teamIdx) != 1) {
            throw new BaseException(BaseResponseStatus.NOT_EXISTS_TEAM);
        }
        //입력값이 유효한지에 대한 검증
        folderDao.modifyFolder(folderIdx, teamIdx, modifyFolderReq);
    }

    public void deleteFolder(int folderIdx) throws BaseException{
        if(folderDao.isExistsFolder(folderIdx) != 1){
            throw new BaseException(BaseResponseStatus.NOT_EXISTS_FOLDER);
        }
        //유효한 folderIdx인지에 대한 검증
        //User가 Folder를 삭제하고자 하는 팀에 속해있는지에 대한 검증
        folderDao.deleteFolder(folderIdx);
    }

    public void changeImportantOfFolder(int folderIdx) throws BaseException{
        folderDao.changeImportantOfFolder(folderIdx);
    }
}
