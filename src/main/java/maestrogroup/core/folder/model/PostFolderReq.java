package maestrogroup.core.folder.model;

import lombok.Data;
// folderIdx, folderImgUrl, folderName, teamIdx

@Data
public class PostFolderReq {
    private String folderImgUrl;
    private String folderName;
    //private int teamIdx;
}
