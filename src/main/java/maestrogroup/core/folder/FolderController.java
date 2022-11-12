package maestrogroup.core.folder;

import maestrogroup.core.folder.model.Folder;
import maestrogroup.core.folder.model.PostFolderReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FolderController {

    @Autowired
    private final FolderProvider folderProvider;
    @Autowired
    private final FolderService folderService;
    @Autowired
    private final FolderDao folderDao;

    public FolderController(FolderProvider folderProvider, FolderService folderService, FolderDao folderDao) {
        this.folderProvider = folderProvider;
        this.folderService = folderService;
        this.folderDao = folderDao;
    }

    // POST: /create_folder/{teamID}
    // folderIdx, folderImgUrl, folderName, teamIdx
    @ResponseBody
    @PostMapping("/create_folder/{teamIdx}")
    public void createFolder(@PathVariable("teamIdx") int teamIdx, @RequestBody PostFolderReq postFolderReq){
        folderService.createFolder(postFolderReq, teamIdx);
    }

    // 특정 팀에 대한 폴더 리스트 조회
    @ResponseBody
    @GetMapping("/get_all_folder/{teamIdx}")
    public List<Folder> GetAllFolder(@PathVariable("teamIdx") int teamIdx){
        return folderProvider.GetAllFolder(teamIdx);
    }
}
