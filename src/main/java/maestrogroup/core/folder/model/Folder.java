package maestrogroup.core.folder.model;

import lombok.AllArgsConstructor;
import lombok.Data;

// folderIdx, folderImgUrl, folderName, teamIdx

@Data
public class Folder {
    private int folderIdx;
    private String folderName;
    private int teamIdx;
    private int important;

    public Folder(int folderIdx, String folderName, int teamIdx, int important) {
        this.folderIdx = folderIdx;
        this.folderName = folderName;
        this.teamIdx = teamIdx;
        this.important = important;
    }
}
