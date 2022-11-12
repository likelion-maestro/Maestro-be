package maestrogroup.core.folder;

import maestrogroup.core.folder.model.PostFolderReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FolderProvider {

    @Autowired
    private final FolderDao folderDao;
    public FolderProvider(FolderDao folderDao) {
        this.folderDao = folderDao;
    }

}
