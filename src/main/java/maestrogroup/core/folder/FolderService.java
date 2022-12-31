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
        folderDao.createFolder(postFolderReq, teamIdx);
    }

    public void modifyFolder(int folderIdx, ModifyFolderReq modifyFolderReq){
        folderDao.modifyFolder(folderIdx, modifyFolderReq);
    }

    public void deleteFolder(int folderIdx){
        folderDao.deleteFolder(folderIdx);
    }

    public void changeImportantOfFolder(int folderIdx) {
        folderDao.changeImportantOfFolder(folderIdx);
    }
}
