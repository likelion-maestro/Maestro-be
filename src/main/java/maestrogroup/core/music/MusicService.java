package maestrogroup.core.music;

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

    public void createMusic(PostMusicReq postMusicReq, int folderIdx) {
        musicDao.createMusic(postMusicReq, folderIdx);
    }

    public void deleteMusic(int musicIdx) {
        musicDao.deleteMusic(musicIdx);
    }
}
