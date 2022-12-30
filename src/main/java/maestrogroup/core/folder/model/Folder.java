package maestrogroup.core.folder.model;

import lombok.AllArgsConstructor;
import lombok.Data;

// folderIdx, folderImgUrl, folderName, teamIdx

@Data
public class Folder {
    private int folderIdx;
    private String folderName;
    private int teamIdx;

    public Folder(int folderIdx, String folderName, int teamIdx) {
        this.folderIdx = folderIdx;
        this.folderName = folderName;
        this.teamIdx = teamIdx;
    }
}
