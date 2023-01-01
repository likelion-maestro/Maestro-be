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
    public BaseResponse createMusic (@RequestBody PostMusicReq postMusicReq, @PathVariable int folderIdx) {
        try {
            musicService.createMusic(postMusicReq, folderIdx);
            return new BaseResponse();
        } catch (BaseException baseException){
            return new BaseResponse(baseException.getStatus());
        }
    }

    @GetMapping("getMusicInfo/{musicIdx}")
    public BaseResponse<MusicInfoRes> GetAllMusicInfo(@PathVariable("musicIdx") int musicIdx){
        try {
            MusicInfoRes musicInfo = musicProvider.GetMusicInfo(musicIdx);
            return new BaseResponse(musicInfo);
        } catch (BaseException baseException){
            return new BaseResponse(baseException.getStatus());
        }
    }

    @ResponseBody
    @PatchMapping("modifyMusic/{musicIdx}")
    public BaseResponse modifyMusic(@RequestBody PostMusicReq postMusicReq, @PathVariable("musicIdx") int musicIdx) {
        try {
            musicService.modifyMusic(postMusicReq, musicIdx);
            return new BaseResponse();
        } catch (BaseException baseException){
            return new BaseResponse(baseException.getStatus());
        }
    }

    @DeleteMapping("deleteMusic/{musicIdx}")
    public BaseResponse deleteMusic(@PathVariable("musicIdx") int musicIdx) {
        try {
            musicService.deleteMusic(musicIdx);
            return new BaseResponse<>();
        } catch (BaseException baseException) {
            return new BaseResponse(baseException.getStatus());
        }
    }
}
