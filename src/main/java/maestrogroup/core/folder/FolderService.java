package maestrogroup.core.folder;

import maestrogroup.core.folder.model.PostFolderReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FolderService {

    @Autowired
    private final FolderDao folderDao;

    public FolderService(FolderDao folderDao) {
        this.folderDao = folderDao;
    }

    //createFolder
    public void createFolder(PostFolderReq postFolderReq, int teamIdx){
        //유효한 teamIdx인지에 대한 검증
        //User가 Folder를 생성하고자 하는 팀에 속해있는지에 대한 검증
        //입력값이 유효한지에 대한 검증
        //해당 팀에 같은 이름의 폴더가 있는지에 대한 검증
        folderDao.createFolder(postFolderReq, teamIdx);
    }

    public void modifyFolder(int folderIdx, ModifyFolderReq modifyFolderReq){
        //User가 Folder를 수정하고자 하는 팀에 속해있는지에 대한 검증
        //입력값이 유효한지에 대한 검증
        folderDao.modifyFolder(folderIdx, modifyFolderReq);
    }

    public void deleteFolder(int folderIdx){
        //유효한 folderIdx인지에 대한 검증
        //User가 Folder를 삭제하고자 하는 팀에 속해있는지에 대한 검증
        folderDao.deleteFolder(folderIdx);
    }

    public void changeImportantOfFolder(int folderIdx) {
        folderDao.changeImportantOfFolder(folderIdx);
    }
}
