package maestrogroup.core.music.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelfMusicRes {
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