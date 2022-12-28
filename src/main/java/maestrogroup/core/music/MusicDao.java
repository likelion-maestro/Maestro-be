package maestrogroup.core.music;

import maestrogroup.core.music.model.Music;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class MusicDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // musicIdx, BPM, folderIdx, musicImgUrl, musicName;
    public List<Music> GetAllMusic(int folderIdx){
        String GetAllMusicQuery = "select * from Music where folderIdx = ?";
        return this.jdbcTemplate.query(GetAllMusicQuery,
                (rs, rowNum) -> new Music(
                 rs.getInt("musicIdx"),
                 rs.getInt("BPM"),
                 rs.getInt("folderIdx"),
                 rs.getString("musicImgUrl"),
                        rs.getString("musicName"),
                        rs.getInt("circleNum"),
                        rs.getInt("totalNum")),
                folderIdx);
    }
}
