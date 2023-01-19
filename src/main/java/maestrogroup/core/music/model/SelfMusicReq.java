package maestrogroup.core.music.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelfMusicReq {
    @ApiModelProperty(example = "80")
    private int bpm;

    @ApiModelProperty(example = "4")
    private int circleNum;
}
