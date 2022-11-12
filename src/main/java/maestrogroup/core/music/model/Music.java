package maestrogroup.core.music.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Music {
    int musicIdx;
    int BPM;
    int folderIdx;
    String musicImgUrl;
    String musicName;


}
