package maestrogroup.core.folder.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class patchFolderReq {
    @ApiModelProperty(example = "크리스마스 기념 곡 모음집")
    private String folderName;

}
