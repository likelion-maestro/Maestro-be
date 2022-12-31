package maestrogroup.core.music;

import maestrogroup.core.ExceptionHandler.BaseException;
import maestrogroup.core.ExceptionHandler.BaseResponseStatus;
import maestrogroup.core.music.model.PostMusicReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MusicService {
    @Autowired
    private final MusicDao musicDao;

    public MusicService(MusicDao musicDao){
        this.musicDao = musicDao;
    }

    public void createMusic(PostMusicReq postMusicReq, int folderIdx) throws BaseException {
        // musicName을 기입하지 않았을 때에 대한 예외처리
        if (postMusicReq.getMusicName() == null || postMusicReq.getMusicName() == "") {
            throw new BaseException(
                    BaseResponseStatus.MODIFY_FIELD_NOT_FULL);
        }

        // bpm을 올바르게 기입하지 않았을 때에 대한 예외처리
        if (postMusicReq.getBpm() <= 0) {
            throw new BaseException(
                    BaseResponseStatus.INVALID_MUSIC_VALUE);
        }

        // circleNum을 올바르게 기입하지 않았을 때에 대한 예외처리
        if (postMusicReq.getCircleNum() <= 0) {
            throw new BaseException(
                    BaseResponseStatus.INVALID_MUSIC_VALUE);
        }

        try {
            musicDao.createMusic(postMusicReq, folderIdx);
        } catch (Exception exception) {
            throw new BaseException(BaseResponseStatus.SERVER_ERROR);
        }
    }

    public void deleteMusic(int musicIdx) {
        musicDao.deleteMusic(musicIdx);
    }
}
//    private String musicName;
//    private int bpm;
//    private int circleNum;