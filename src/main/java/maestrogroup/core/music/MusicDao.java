package maestrogroup.core.music;

import maestrogroup.core.music.model.Music;
import maestrogroup.core.music.model.MusicInfoRes;
import maestrogroup.core.music.model.PostMusicReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Array;
import java.util.ArrayList;
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
                        rs.getDouble("totalNum")),
                folderIdx);
    }
    public void createMusic(PostMusicReq postMusicReq, int folderIdx) {
        String createMusicQuery = "insert into Music (musicName, bpm, circleNum, totalNum, musicImgUrl, folderIdx) VALUES (?, ?, ?, ?, ?, ?)";
        Object[] createMusicParams = new Object[]{postMusicReq.getMusicName(), postMusicReq.getBpm(), postMusicReq.getCircleNum(), (double)60 / postMusicReq.getBpm() * postMusicReq.getCircleNum(), postMusicReq.getMusicImgUrl(), folderIdx};
        this.jdbcTemplate.update(createMusicQuery, createMusicParams);
    }

    public List<MusicInfoRes> GetMusicInfo(int musicIdx){
        int bpm = this.jdbcTemplate.queryForObject("select bpm from Music where musicIdx = ?", int.class, musicIdx);
        double waitTime = (double) 60 / bpm;
        int circleNum = this.jdbcTemplate.queryForObject("select circleNum from Music where musicIdx = ?", int.class, musicIdx);

        double num = 0;
        List<Double> startTimes = new ArrayList<>();
        for (int i = 0; i < circleNum; i++) {
            startTimes.add(num);
            num += waitTime;
        }

        String GetMusicInfoQuery = "select * from Music where musicIdx = ?";
        return this.jdbcTemplate.query(GetMusicInfoQuery,
                (rs, rowNum) -> new MusicInfoRes(
                        rs.getInt("musicIdx"),
                        rs.getInt("bpm"),
                        rs.getInt("folderIdx"),
                        rs.getString("musicImgUrl"),
                        rs.getString("musicName"),
                        rs.getInt("circleNum"),
                        rs.getDouble("totalNum"),
                        startTimes),
                musicIdx);
    }
}
