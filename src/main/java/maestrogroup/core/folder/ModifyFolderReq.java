package maestrogroup.core.folder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
class ModifyFolderReq {
    private String folderName;

    // 일단 클라이언트로부터 JSON 인자를 1개를 받을때는 폴더 이미지가 아닌, 폴더 이름이 수정도되록
}
