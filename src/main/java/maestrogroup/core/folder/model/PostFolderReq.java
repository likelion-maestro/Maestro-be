package maestrogroup.core.folder.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
// folderIdx, folderImgUrl, folderName, teamIdx

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostFolderReq {
    private String folderName;
    //private int teamIdx;
}
