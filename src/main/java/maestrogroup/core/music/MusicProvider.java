package maestrogroup.core.music;

import maestrogroup.core.music.model.Music;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicProvider {

    @Autowired
    private final MusicDao musicDao;

    public MusicProvider(MusicDao musicDao){
        this.musicDao = musicDao;
    }

    public List<Music> GetAllMusic(int folderIdx){
        return musicDao.GetAllMusic(folderIdx);
    }
}
