package maestrogroup.core.music;

import maestrogroup.core.ExceptionHandler.BaseException;
import maestrogroup.core.ExceptionHandler.BaseResponse;
import maestrogroup.core.music.model.Music;
import maestrogroup.core.music.model.MusicInfoRes;
import maestrogroup.core.music.model.PostMusicReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/music")
@RestController
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

    @GetMapping("getMusicList/{folderIdx}")
    public BaseResponse<List<Music>> GetAllMusic(@PathVariable("folderIdx") int folderIdx){
        try {
            List<Music> musicList = musicProvider.GetAllMusic(folderIdx);
            return new BaseResponse<>(musicList);
        } catch (BaseException baseException){
            return new BaseResponse<>(baseException.getStatus());
        }
    }

    @ResponseBody
    @PostMapping("createMusic/{folderIdx}")
    public void createMusic (@RequestBody PostMusicReq postMusicReq, @PathVariable int folderIdx) {
        musicService.createMusic(postMusicReq, folderIdx);
    }

    @GetMapping("getMusicInfo/{musicIdx}")
    public List<MusicInfoRes> GetAllMusicInfo(@PathVariable("musicIdx") int musicIdx){
        return musicProvider.GetMusicInfo(musicIdx);
    }

    @ResponseBody
    @PatchMapping("modifyMusic/{musicIdx}")
    public void modifyMusic(@RequestBody PostMusicReq postMusicReq, @PathVariable("musicIdx") int musicIdx) {
        musicService.modifyMusic(postMusicReq, musicIdx);
    }
}
