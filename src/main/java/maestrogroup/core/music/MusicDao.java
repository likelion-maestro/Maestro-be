package maestrogroup.core.music;

import maestrogroup.core.ExceptionHandler.BaseException;
import maestrogroup.core.ExceptionHandler.BaseResponseStatus;
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
    public List<Music> GetAllMusic(int folderIdx) throws BaseException{
        try {
            String GetAllMusicQuery = "select * from Music where folderIdx = ?";
            return this.jdbcTemplate.query(GetAllMusicQuery,
                    (rs, rowNum) -> new Music(
                            rs.getInt("musicIdx"),
                            rs.getInt("bpm"),
                            rs.getInt("folderIdx"),
                            rs.getString("musicName"),
                            rs.getInt("circleNum"),
                            rs.getDouble("totalNum")),
                    folderIdx);
        } catch (Exception e){
            throw new BaseException(BaseResponseStatus.SERVER_ERROR);
        }
    }

    public void createMusic(PostMusicReq postMusicReq, int folderIdx) throws BaseException{
        try {
            String createMusicQuery = "insert into Music (musicName, bpm, circleNum, totalNum, folderIdx) VALUES (?, ?, ?, ?, ?)";
            Object[] createMusicParams = new Object[]{postMusicReq.getMusicName(), postMusicReq.getBpm(), postMusicReq.getCircleNum(), (double) 60 / postMusicReq.getBpm() * postMusicReq.getCircleNum(), folderIdx};
            this.jdbcTemplate.update(createMusicQuery, createMusicParams);
        } catch (Exception e){
            throw new BaseException(BaseResponseStatus.SERVER_ERROR);
        }
    }

    public MusicInfoRes GetMusicInfo(int musicIdx) throws BaseException{
        try {
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
            return this.jdbcTemplate.queryForObject(GetMusicInfoQuery,
                    (rs, rowNum) -> new MusicInfoRes(
                            rs.getInt("musicIdx"),
                            rs.getInt("bpm"),
                            rs.getInt("folderIdx"),
                            rs.getString("musicName"),
                            rs.getInt("circleNum"),
                            rs.getDouble("totalNum"),
                            startTimes),
                    musicIdx);
        } catch (Exception e){
            throw new BaseException(BaseResponseStatus.SERVER_ERROR);
        }
    }

    public void modifyMusic(PostMusicReq postMusicReq, int musicIdx) {
        String ModifyMusicQuery = "update Music set musicName = ?, bpm = ?, circleNum = ?, totalNum = ? where musicIdx = ?";
        Object[] ModifyMusicParams = new Object[]{postMusicReq.getMusicName(), postMusicReq.getBpm(), postMusicReq.getCircleNum(), (double)60 / postMusicReq.getBpm() * postMusicReq.getCircleNum(), musicIdx};
        this.jdbcTemplate.update(ModifyMusicQuery, ModifyMusicParams);

//        private String musicName;
//        private int bpm;
//        private int circleNum;
    }
}
