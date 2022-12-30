package maestrogroup.core.music.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostMusicReq {
    private String musicName;
    private int bpm;
    private int circleNum;
}
