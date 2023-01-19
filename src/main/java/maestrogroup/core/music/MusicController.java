package maestrogroup.core.music;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import maestrogroup.core.ExceptionHandler.BaseException;
import maestrogroup.core.ExceptionHandler.BaseResponse;
import maestrogroup.core.music.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/music")
@Api(value = "Music", tags = "Music 관련 API")
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
    @Operation(summary = "특정 폴더 내 음악들의 정보 불러오기", description = "")
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
    @Operation(summary = "음악 생성", description = "음악 이름, BPM, 애니메이션에 필요한 동그라미의 개수를 입력해주세요. 누락된 정보가 있거나 BPM, 동그라미 개수가 0보다 같거나 작을 때 에러를 발생시켰습니다.")
    public BaseResponse createMusic (@RequestBody PostMusicReq postMusicReq, @PathVariable int folderIdx) {
        try {
            musicService.createMusic(postMusicReq, folderIdx);
            return new BaseResponse();
        } catch (BaseException baseException){
            return new BaseResponse(baseException.getStatus());
        }
    }

    @GetMapping("getMusicInfo/{musicIdx}")
    @Operation(summary = "음악 정보 조회", description = "음악의 기본적인 정보와 더불어, 메트로놈 애니메이션에 필요한 정보들을 제공합니다.")
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
    @Operation(summary = "음악 정보 수정", description = "음악 정보를 수정합니다. 누락된 정보가 있거나 BPM, 동그라미 개수가 0보다 같거나 작을 때 에러를 발생시켰습니다.")
    public BaseResponse modifyMusic(@RequestBody PostMusicReq postMusicReq, @PathVariable("musicIdx") int musicIdx) {
        try {
            musicService.modifyMusic(postMusicReq, musicIdx);
            return new BaseResponse();
        } catch (BaseException baseException){
            return new BaseResponse(baseException.getStatus());
        }
    }

    @DeleteMapping("deleteMusic/{musicIdx}")
    @Operation(summary = "음악 삭제")
    public BaseResponse deleteMusic(@PathVariable("musicIdx") int musicIdx) {
        try {
            musicService.deleteMusic(musicIdx);
            return new BaseResponse<>();
        } catch (BaseException baseException) {
            return new BaseResponse(baseException.getStatus());
        }
    }

    @PostMapping("/self")
    @Operation(summary = "Requestbody를 이용한 개인 메트로놈 페이지에 필요한 정보 조회", description = "BPM, 동그라미 개수가 0보다 같거나 작을 때 에러를 발생시켰습니다.")
    public BaseResponse<SelfMusicRes> getSelfMusicInfoWithBody(@RequestBody SelfMusicReq selfMusicReq) {
        try {
            int bpm = selfMusicReq.getBpm();
            int circleNum = selfMusicReq.getCircleNum();
            SelfMusicRes selfMusicInfo = musicProvider.getSelfMusicInfo(bpm, circleNum);
            return new BaseResponse(selfMusicInfo);
        } catch (BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
    }

    @GetMapping("/self/{bpm}/{circleNum}")
    @Operation(summary = "Pathvariable을 이용한 개인 메트로놈 페이지에 필요한 정보 조회", description = "BPM, 동그라미 개수가 0보다 같거나 작을 때 에러를 발생시켰습니다.")
    public BaseResponse<SelfMusicRes> getSelfMusicInfoWithBody(@PathVariable("bpm") int bpm, @PathVariable("circleNum") int circleNum) {
        try {
            SelfMusicRes selfMusicInfo = musicProvider.getSelfMusicInfo(bpm, circleNum);
            return new BaseResponse(selfMusicInfo);
        } catch (BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
    }
}
