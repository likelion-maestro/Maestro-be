package maestrogroup.core.folder.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

// folderIdx, folderImgUrl, folderName, teamIdx

@Data
public class Folder {
    @ApiModelProperty(example = "3")
    private int folderIdx;

    @ApiModelProperty(example = "크리스마스 기념 공연 곡 모음")
    private String folderName;

    @ApiModelProperty(example = "4")
    private int teamIdx;

    @ApiModelProperty(example = "1")
    private int important;

    public Folder(int folderIdx, String folderName, int teamIdx, int important) {
        this.folderIdx = folderIdx;
        this.folderName = folderName;
        this.teamIdx = teamIdx;
        this.important = important;
    }
}
