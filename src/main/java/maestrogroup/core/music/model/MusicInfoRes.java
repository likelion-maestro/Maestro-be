package maestrogroup.core.music.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Array;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicInfoRes {
    int musicIdx;
    int bpm;
    int folderIdx;
    String musicName;
    int circleNum;
    double totalNum;
    List<Double> startTimes;
}
