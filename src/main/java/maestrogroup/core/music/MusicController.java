package maestrogroup.core.music;

import maestrogroup.core.music.model.Music;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/music")
@RestController
@CrossOrigin(origins = "http://maestro:3000")
public class MusicController {

    @Autowired
    private final MusicProvider musicProvider;
    @Autowired
    private final MusicService musicService;
    @Autowired
    private final MusicDao musicDao;

    public MusicController(MusicProvider musicProvider, MusicService musicService, MusicDao musicDao){
        this.musicDao = musicDao;
        this.musicProvider = musicProvider;
        this.musicService = musicService;
    }

    @GetMapping("/{folderIdx}")
    public List<Music> GetAllMusic(@PathVariable("folderIdx") int folderIdx){
        return musicProvider.GetAllMusic(folderIdx);
    }
}
