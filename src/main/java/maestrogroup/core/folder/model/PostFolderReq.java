package maestrogroup.core.folder.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
// folderIdx, folderImgUrl, folderName, teamIdx

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostFolderReq {
    @ApiModelProperty(example = "크리스마스 기념 공연 곡 모음")
    private String folderName;
    //private int teamIdx;
}
