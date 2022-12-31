package maestrogroup.core.music;

import maestrogroup.core.ExceptionHandler.BaseException;
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
        musicDao.createMusic(postMusicReq, folderIdx);
    }

    public void modifyMusic(PostMusicReq postMusicReq, int musicIdx) throws BaseException{
        musicDao.modifyMusic(postMusicReq, musicIdx);
    }
}
