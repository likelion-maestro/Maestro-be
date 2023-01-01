package maestrogroup.core.folder;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import maestrogroup.core.ExceptionHandler.BaseException;
import maestrogroup.core.ExceptionHandler.BaseResponse;
import maestrogroup.core.ExceptionHandler.BaseResponseStatus;
import maestrogroup.core.Security.JwtService;
import maestrogroup.core.folder.model.Folder;
import maestrogroup.core.folder.model.*;
import maestrogroup.core.folder.model.PostFolderReq;
import maestrogroup.core.team.TeamDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Api(value = "Folder", tags = "Folder 관련 API")
@RestController
public class FolderController {

    @Autowired
    private final FolderProvider folderProvider;
    @Autowired
    private final FolderService folderService;
    @Autowired
    private final FolderDao folderDao;
    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final TeamDao teamDao;

    public FolderController(FolderProvider folderProvider, FolderService folderService, FolderDao folderDao, JwtService jwtService, TeamDao teamDao) {
        this.folderProvider = folderProvider;
        this.folderService = folderService;
        this.folderDao = folderDao;
        this.jwtService = jwtService;
        this.teamDao = teamDao;
    }

    // POST: /create_folder/{teamID}
    // folderIdx, folderImgUrl, folderName, teamIdx
    @ResponseBody
    @PostMapping("/create_folder/{teamIdx}")
    @Operation(summary = "폴더 생성", description = "특정 팀의 폴더를 생성합니다.")
    public BaseResponse createFolder(@PathVariable("teamIdx") int teamIdx, @RequestBody PostFolderReq postFolderReq) {
        try {
            folderService.createFolder(postFolderReq, teamIdx);
            return new BaseResponse();
        } catch (BaseException baseException) {
            return new BaseResponse(baseException.getStatus());
        }
    }

    // 특정 팀에 대한 폴더 리스트 조회
    @ResponseBody
    @GetMapping("/get_all_folder/{teamIdx}")
    @Operation(summary = "특정 팀의 폴더 목록 조회")
    public BaseResponse<List<Folder>> GetAllFolder(@PathVariable("teamIdx") int teamIdx) throws BaseException{
        try {
            List<Folder> folderList = folderProvider.GetAllFolder(teamIdx);
            return new BaseResponse<>(folderList);
        } catch (BaseException baseException) {
            return new BaseResponse(baseException.getStatus());
        }
    }

    // 폴더 수정 : 그런데 만약에 인자값이 1개만 들어온다면? ModifyFolderReq 의 생성자를 어떻게 선언하고 처리하는가?
    // => 인자 1개만 받으면 안되니까, 인자를 반드시 2개 모두 입력받기 위한 검증 및 에외처리 필요함
    // ex) 폴더 이미지 필드값에 아무것도 저장이 안된 상태(= NULL 값이 저장된 상태)로 요청이 들어오면 다시 입력하라고 Response를 보내자.
    // 또한 폴더 수정페이지로 넘어갔을떄, 기본적으로 폴더 이미지와 폴더 이름 수정란에 아무것도 값이 없는것이 아닌, 디폴트 값으로 기존에
    // 해당 폴더에 저장되어있던 값들을 수정란에 띄어주는 것으로 가정
    @ResponseBody
    @PatchMapping("/patchFolder/{folderIdx}/{teamIdx}")
    @Operation(summary = "폴더 정보 수정", description = "폴더 이름을 수정합니다.")
    public BaseResponse modifyFolder(@PathVariable("folderIdx") int folderIdx, @PathVariable("teamIdx") int teamIdx, @RequestBody ModifyFolderReq modifyFolderReq) {
        try {
            folderService.modifyFolder(folderIdx, teamIdx, modifyFolderReq);
            return new BaseResponse();
        } catch (BaseException baseException) {
            return new BaseResponse(baseException.getStatus());
        }
    }

    // 폴더를 삭제할떄 폴더안에 담겨있는 음악 객체들도 모두 삭제되도록 API 추가 개발 처리요망
    @ResponseBody
    @DeleteMapping("/deleteFolder/{folderIdx}")
    @Operation(summary = "폴더 삭제")
    public BaseResponse deleteFolder(@PathVariable("folderIdx") int folderIdx) {
        try {
            folderService.deleteFolder(folderIdx);
            return new BaseResponse();
        } catch (BaseException baseException) {
            return new BaseResponse(baseException.getStatus());
        }
    }

    @ResponseBody
    @PatchMapping("changeImportantOfFolder/{folderIdx}")
    @Operation(summary = "폴더 별 표시 기능", description = "폴더의 중요도를 나타내는 별 표시 기능입니다. important가 0이라면 1이 되고, 1이라면 0이 되도록 구현했습니다.")
    public BaseResponse changeImportantOfFolder(@PathVariable("folderIdx") int folderIdx) {
        try {
            folderService.changeImportantOfFolder(folderIdx);
            return new BaseResponse();
        } catch (BaseException baseException) {
            return new BaseResponse(baseException.getStatus());
        }

    }
}
