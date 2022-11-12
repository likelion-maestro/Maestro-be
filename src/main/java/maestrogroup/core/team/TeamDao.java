package maestrogroup.core.team;

import maestrogroup.core.team.model.GetTeamRes;
import maestrogroup.core.team.model.PostTeamReq;
import maestrogroup.core.team.model.PostTeamRes;
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
        String createTeamQuery = "insert into Team (teamName, teamImgUrl) VALUES (?, ?)";
        Object[] createUserParams = new Object[]{postTeamReq.getTeamName(), postTeamReq.getTeamImgUrl()};
        this.jdbcTemplate.update(createTeamQuery, createUserParams);

        String lastInsertTeamIdx = "select last_insert_id()";
        String insertTeamQuery = "select * from Team where teamIdx = ?";

        System.out.println("=======================================================");
    }

    public List<GetTeamRes> getAllTeam(){
        String getAllTeamQuery = "select * from Team";
        return this.jdbcTemplate.query(getAllTeamQuery,
                (rs, rowNum) -> new GetTeamRes(
                  rs.getInt("teamIdx"),
                  rs.getString("teamName"),
                  rs.getString("teamImgUrl"),
                  rs.getInt("count"))
        );
    }
}




















