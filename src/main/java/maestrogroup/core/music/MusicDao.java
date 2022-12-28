package maestrogroup.core.music;

import maestrogroup.core.music.model.Music;
import maestrogroup.core.music.model.PostMusicReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Array;
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
                 rs.getInt("bpm"),
                 rs.getInt("folderIdx"),
                 rs.getString("musicImgUrl"),
                        rs.getString("musicName"),
                        rs.getInt("circleNum"),
                        rs.getInt("totalNum")),
                folderIdx);
    }
    public void createMusic(PostMusicReq postMusicReq, int folderIdx) {
        String createMusicQuery = "insert into Music (musicName, bpm, circleNum, totalNum, musicImgUrl, folderIdx) VALUES (?, ?, ?, ?, ?, ?)";
        Object[] createMusicParams = new Object[]{postMusicReq.getMusicName(), postMusicReq.getBpm(), postMusicReq.getCircleNum(), (double)60 / postMusicReq.getBpm() * postMusicReq.getCircleNum(), postMusicReq.getMusicImgUrl(), folderIdx};
        this.jdbcTemplate.update(createMusicQuery, createMusicParams);
    }
}
