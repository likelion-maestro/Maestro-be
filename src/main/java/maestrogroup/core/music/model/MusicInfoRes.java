package maestrogroup.core.music.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Array;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicInfoRes {
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

    @ApiModelProperty(example = "[" +
            "0.0," +
            "0.75," +
            "1.5," +
            "2.25" +
            "]")
    List<Double> startTimes;
}
