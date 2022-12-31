package maestrogroup.core.folder;

import maestrogroup.core.ExceptionHandler.BaseException;
import maestrogroup.core.folder.model.Folder;
import maestrogroup.core.folder.model.PostFolderReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FolderProvider {

    @Autowired
    private final FolderDao folderDao;
    public FolderProvider(FolderDao folderDao) {
        this.folderDao = folderDao;
    }

    public List<Folder> GetAllFolder(int teamIdx) throws BaseException {
        return folderDao.GetAllFolder(teamIdx);
    }
}
