package maestrogroup.core.folder;

import com.fasterxml.jackson.databind.ser.Serializers;
import maestrogroup.core.ExceptionHandler.BaseException;
import maestrogroup.core.ExceptionHandler.BaseResponseStatus;
import maestrogroup.core.folder.model.Folder;
import maestrogroup.core.folder.model.PostFolderReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class FolderDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 특정 그룹에 대한 폴더생성
    // folderIdx, folderImgUrl, folderName, teamIdx
    public void createFolder(PostFolderReq postFolderReq, int teamIdx) throws BaseException {
        String curfolderName = postFolderReq.getFolderName();
        int folderUniCodeLength =  checkFolderNameLength(curfolderName);

        if(folderUniCodeLength == 0){
            throw new BaseException(BaseResponseStatus.INVALID_FOLDER_VALUE);
        }

        if(folderUniCodeLength > 20){
            throw new BaseException(BaseResponseStatus.FOLDER_NAME_LENGTH);
        }
        int folderCount = checkDuplicateFolderName(curfolderName, teamIdx);
        if (folderCount >= 1){ // 중복되는 폴더가 있는지 검증
            throw new BaseException(BaseResponseStatus.DUPLICATE_FOLDER);
        }

        try {
            String createFolderQuery = "insert into Folder (folderName, teamIdx, important) VALUES (?, ?, 0)";
            Object[] createFolderParams = new Object[]{postFolderReq.getFolderName(), teamIdx};
            this.jdbcTemplate.update(createFolderQuery, createFolderParams);
        } catch (Exception e){
            throw new BaseException(BaseResponseStatus.SERVER_ERROR);
        }
    }

    //해당 팀에 같은 이름의 폴더가 있는지에 대한 검증
    //        int nowImportance = this.jdbcTemplate.queryForObject("select important from Mapping where userIdx = ? AND teamIdx = ?", int.class, userIdx, teamIdx);
    public int checkDuplicateFolderName(String curfolderName, int teamIdx){
        String checkFolderCountQUery = "select count(*) from Folder where folderName = ? AND teamIdx = ?";
        Object[] folderNameParams = new Object[]{curfolderName, teamIdx};
        Integer folderCount = this.jdbcTemplate.queryForObject(checkFolderCountQUery, folderNameParams, Integer.class);
        return folderCount;
    }

    // 입력값으로 들어온 폴더 이름의 길이 체크
    public int checkFolderNameLength(String folderName){
        return folderName.length();
    }


    // folderIdx, folderImgUrl, folderName, teamIdx
    public List<Folder> GetAllFolder(int teamIdx) throws BaseException{
        try {
            String getAllFolderQuery = "select * from Folder where teamIdx = ?";
            int getTeamIdx = teamIdx;
            return this.jdbcTemplate.query(getAllFolderQuery,
                    (rs, rowNum) -> new Folder(
                            rs.getInt("folderIdx"),
                            rs.getString("folderName"),
                            rs.getInt("teamIdx"),
                            rs.getInt("important")),
                    getTeamIdx);
        } catch (Exception e){
            throw new BaseException(BaseResponseStatus.SERVER_ERROR);
        }
    }

    public void modifyFolder(int folderIdx, int teamIdx, ModifyFolderReq modifyFolderReq) throws BaseException{
        String curfolderName = modifyFolderReq.getFolderName();
        int folderUniCodeLength = checkFolderNameLength(curfolderName);

        if(folderUniCodeLength == 0){
            throw new BaseException(BaseResponseStatus.INVALID_FOLDER_VALUE);
        }

        if(folderUniCodeLength > 20){
            throw new BaseException(BaseResponseStatus.FOLDER_NAME_LENGTH);
        }
        int folderCount = checkDuplicateFolderName(curfolderName, teamIdx);
        if (folderCount >= 1){ // 중복되는 폴더가 있는지 검증
            throw new BaseException(BaseResponseStatus.DUPLICATE_FOLDER);
        }
        try {
            String UpdateModifyFolderQuery = "UPDATE Folder set folderName = ? where folderIdx = ?";
            Object[] modifyFolderParams = new Object[]{modifyFolderReq.getFolderName(), folderIdx};
            this.jdbcTemplate.update(UpdateModifyFolderQuery, modifyFolderParams);
        }
        catch (Exception e){
            throw new BaseException(BaseResponseStatus.SERVER_ERROR);
        }
    }

    public void deleteFolder(int folderIdx) throws BaseException{
        try {
            String deleteFolderQuery = "DELETE FROM Folder WHERE folderIdx = ?";
            deleteMusicInFolder(folderIdx);
            this.jdbcTemplate.update(deleteFolderQuery, folderIdx);
        }  catch (Exception e){
            throw new BaseException(BaseResponseStatus.SERVER_ERROR);
        }
    }

    public void deleteMusicInFolder(int folderIdx) throws BaseException{
        try {
            String deleteMusicInFolderQuery = "DELETE FROM Music WHERE folderIdx = ?";
            this.jdbcTemplate.update(deleteMusicInFolderQuery, folderIdx);
        } catch (Exception e){
            throw new BaseException(BaseResponseStatus.SERVER_ERROR);
        }
    }

    public void changeImportantOfFolder(int folderIdx) throws BaseException {
        int exist = 0;
        try {
            exist = isExistsFolder(folderIdx);
            System.out.println(exist);
        } catch (BaseException baseException){
            throw new BaseException(baseException.getStatus());
        }

        if(exist == 0) {
            try {
                int nowImportant = this.jdbcTemplate.queryForObject("select important from Folder where folderIdx = ?", int.class, folderIdx);
                int value = -1;
                if (nowImportant == 1) {
                    value = 0;
                }
                if (nowImportant == 0) {
                    value = 1;
                }
                String query = "update Folder set important = ? where folderIdx = ?";
                Object[] params = new Object[]{value, folderIdx};
                this.jdbcTemplate.update(query, params);
            } catch (Exception e) {
                throw new BaseException(BaseResponseStatus.SERVER_ERROR);
            }
        }
    }

    public int isExistsFolder(int folderIdx) throws BaseException {
        try {
            return this.jdbcTemplate.queryForObject("select exists (select folderIdx from Folder where folderIdx = ?)", int.class, folderIdx);
        } catch (Exception e){
            throw new BaseException(BaseResponseStatus.FOLDER_ERROR);
        }
    }
}
