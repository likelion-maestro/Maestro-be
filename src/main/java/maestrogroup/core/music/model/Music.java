package maestrogroup.core.music.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Music {
    int musicIdx;
    int bpm;
    int folderIdx;
    String musicName;
    int circleNum;
    double totalNum;


}
