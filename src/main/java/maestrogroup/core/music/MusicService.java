package maestrogroup.core.music;

import maestrogroup.core.ExceptionHandler.BaseException;
import maestrogroup.core.ExceptionHandler.BaseResponseStatus;
import maestrogroup.core.folder.FolderDao;
import maestrogroup.core.music.model.PostMusicReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MusicService {
    @Autowired
    private final MusicDao musicDao;
    @Autowired
    private final FolderDao folderDao;

    public MusicService(MusicDao musicDao, FolderDao folderDao){
        this.musicDao = musicDao;
        this.folderDao = folderDao;
    }

    public void createMusic(PostMusicReq postMusicReq, int folderIdx) throws BaseException {
        // Music을 생성할 Folder가 존재하지 않을 때에 대한 예외처리
        if (folderDao.isExistsFolder(folderIdx) != 1) {
            throw new BaseException(BaseResponseStatus.NOT_EXISTS_FOLDER);
        }

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

    public void deleteMusic(int musicIdx) throws BaseException {
        // 삭제할 Music이 존재하지 않을 때에 대한 예외처리
        if (musicDao.isExistsMusic(musicIdx) != 1) {
            throw new BaseException(BaseResponseStatus.NOT_EXISTS_MUSIC);
        }

        musicDao.deleteMusic(musicIdx);
    }
}
//    private String musicName;
//    private int bpm;
//    private int circleNum;