package maestrogroup.core.team;

import maestrogroup.core.team.model.GetTeamRes;
import maestrogroup.core.team.model.PatchTeamReq;
import maestrogroup.core.team.model.PostTeamReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class TeamDao {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void createTeam(PostTeamReq postTeamReq) {
        String createTeamQuery = "insert into Team teamName VALUES ?";
        this.jdbcTemplate.update(createTeamQuery, postTeamReq.getTeamName());

        String lastInsertTeamIdx = "select last_insert_id()";
        String insertTeamQuery = "select * from Team where teamIdx = ?";
    }

    public List<GetTeamRes> getAllTeam(){
        String getAllTeamQuery = "select * from Team";
        return this.jdbcTemplate.query(getAllTeamQuery,
                (rs, rowNum) -> new GetTeamRes(
                  rs.getInt("teamIdx"),
                  rs.getString("teamName"),
                  rs.getInt("count")
                )
        );
    }

    public void modifyTeam(PatchTeamReq patchUserReq){
        String modifyTeamQuery = "update Team set teamName = ? where teamIdx = ?";
        Object[] modifyUserNameParams = new Object[]{patchUserReq.getTeamName(), patchUserReq.getTeamIdx()};
        this.jdbcTemplate.update(modifyTeamQuery, modifyUserNameParams);
    }

    public void deleteTeam(int teamIdx){
        String deleteTeamQuery = "delete from Team where teamIdx = ?";
        this.jdbcTemplate.update(deleteTeamQuery, teamIdx);
    }

    public int getLeaderIdx(int teamIdx) {
        String getLeaderIdxQuery = "select leaderIdx from Team where teamIdx = ?";
//        Object getLeaderIdxParams = new Object{teamIdx};
        return this.jdbcTemplate.queryForObject(getLeaderIdxQuery, int.class, teamIdx);
    }

    public void modifyTeamLeader(int userIdx2, int teamIdx) {
        String modifyTeamLeaderQuery = "update Team set leaderIdx = ? where teamIdx = ?";
        Object[] modifyTeamLeaderParams = new Object[]{userIdx2, teamIdx};
        this.jdbcTemplate.update(modifyTeamLeaderQuery, modifyTeamLeaderParams);
    }
}




















