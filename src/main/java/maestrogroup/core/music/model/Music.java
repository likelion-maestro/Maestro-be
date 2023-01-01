package maestrogroup.core.music.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Music {
    @ApiModelProperty(example = "1")
    int musicIdx;

    @ApiModelProperty(example = "80")
    int bpm;

    @ApiModelProperty(example = "4")
    int folderIdx;

    @ApiModelProperty(example = "Ditto")
    String musicName;

    @ApiModelProperty(example = "4")
    int circleNum;

    @ApiModelProperty(example = "3.0")
    double totalNum;


}
