package maestrogroup.core.folder;

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

    public void createFolder(PostFolderReq postFolderReq, int teamIdx){
        String createFolderQuery = "insert into Folder (folderName, teamIdx, important) VALUES (?, ?, 0)";
        Object[] createFolderParams = new Object[]{postFolderReq.getFolderName(), teamIdx};
        this.jdbcTemplate.update(createFolderQuery, createFolderParams);
    }


    // folderIdx, folderImgUrl, folderName, teamIdx
    public List<Folder> GetAllFolder(int teamIdx){
        String getAllFolderQuery = "select * from Folder where teamIdx = ?";
        int getTeamIdx = teamIdx;
        return this.jdbcTemplate.query(getAllFolderQuery,
                (rs, rowNum) -> new Folder(
                        rs.getInt("folderIdx"),
                        rs.getString("folderName"),
                        rs.getInt("teamIdx"),
                        rs.getInt("important")),
                getTeamIdx);
    }

    public void modifyFolder(int folderIdx, ModifyFolderReq modifyFolderReq){
        String UpdateModifyFolderQuery = "UPDATE Folder set folderName = ? where folderIdx = ?";
        Object[] modifyFolderParams = new Object[]{modifyFolderReq.getFolderName(), folderIdx};
        this.jdbcTemplate.update(UpdateModifyFolderQuery, modifyFolderParams);
    }

    public void deleteFolder(int folderIdx){
        String deleteFolderQuery = "DELETE FROM Folder WHERE folderIdx = ?";
        this.jdbcTemplate.update(deleteFolderQuery, folderIdx);
    }

    public void changeImportantOfFolder(int folderIdx) {
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
    }

    public int isExistsFolder(int folderIdx) {
        return this.jdbcTemplate.queryForObject("select exists (select folderIdx from Folder where folderIdx = ?)", int.class, folderIdx);
    }
}
