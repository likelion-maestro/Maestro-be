package maestrogroup.core.folder.model;

import lombok.AllArgsConstructor;
import lombok.Data;

// folderIdx, folderImgUrl, folderName, teamIdx

@Data
public class Folder {
    private int folderIdx;
    private String folderImgUrl;
    private String folderName;
    private int teamIdx;

    public Folder(int folderIdx, String folderImgUrl, String folderName, int teamIdx) {
        this.folderIdx = folderIdx;
        this.folderImgUrl = folderImgUrl;
        this.folderName = folderName;
        this.teamIdx = teamIdx;
    }
}
