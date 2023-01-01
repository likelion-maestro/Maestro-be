package maestrogroup.core.music;

import maestrogroup.core.ExceptionHandler.BaseException;
import maestrogroup.core.ExceptionHandler.BaseResponseStatus;
import maestrogroup.core.folder.FolderDao;
import maestrogroup.core.music.model.Music;
import maestrogroup.core.music.model.MusicInfoRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicProvider {

    @Autowired
    private final MusicDao musicDao;
    @Autowired
    private final FolderDao folderDao;

    public MusicProvider(MusicDao musicDao, FolderDao folderDao){
        this.musicDao = musicDao;
        this.folderDao = folderDao;
    }

    public List<Music> GetAllMusic(int folderIdx) throws BaseException {
        // Folder가 존재하지 않을 때에 대한 예외처리
        if (folderDao.isExistsFolder(folderIdx) != 1) {
            throw new BaseException(BaseResponseStatus.NOT_EXISTS_FOLDER);
        }

        try {
            return musicDao.GetAllMusic(folderIdx);
        } catch (Exception exception) {
            throw new BaseException(BaseResponseStatus.SERVER_ERROR);
        }
    }

    public MusicInfoRes GetMusicInfo(int musicIdx) throws BaseException {
        // Music이 존재하지 않을 때에 대한 예외처리
        if (musicDao.isExistsMusic(musicIdx) != 1) {
            throw new BaseException(BaseResponseStatus.NOT_EXISTS_MUSIC);
        }
        return musicDao.GetMusicInfo(musicIdx);
    }
}
