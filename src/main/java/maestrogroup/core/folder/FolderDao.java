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
        String createFolderQuery = "insert into Folder (folderImgUrl, folderName, teamIdx) VALUES (?, ?, ?)";
        Object[] createFolderParams = new Object[]{postFolderReq.getFolderImgUrl(), postFolderReq.getFolderName(), teamIdx};
        System.out.println("teamIdx:" + createFolderParams[2]);
        System.out.println("folderName:" + createFolderParams[1]);
        this.jdbcTemplate.update(createFolderQuery, createFolderParams);
    }


    // folderIdx, folderImgUrl, folderName, teamIdx
    public List<Folder> GetAllFolder(int teamIdx){
        String getAllFolderQuery = "select * from Folder where teamIdx = ?";
        int getTeamIdx = teamIdx;
        return this.jdbcTemplate.query(getAllFolderQuery,
                (rs, rowNum) -> new Folder(
                        rs.getInt("folderIdx"),
                        rs.getString("folderImgUrl"),
                        rs.getString("folderName"),
                        rs.getInt("teamIdx")),
                getTeamIdx);
    }
}
