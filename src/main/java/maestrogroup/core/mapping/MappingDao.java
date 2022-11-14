package maestrogroup.core.mapping;

import maestrogroup.core.mapping.model.Mapping;
import maestrogroup.core.team.model.PostTeamReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class MappingDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void inviteUser(int teamIdx, int userIdx) {
        String inviteUserQuery = "insert into Mapping (teamIdx, userIdx) VALUES (?, ?)";
        Object[] inviteUserParams = new Object[]{teamIdx, userIdx};
        this.jdbcTemplate.update(inviteUserQuery, inviteUserParams);
    }

    public void makeTeam(int userIdx, PostTeamReq postTeamReq){
        String makeTeamQuery = "insert into Team (teamName, teamImgUrl) VALUES (?, ?)";
        Object[] createTeamParams = new Object[]{postTeamReq.getTeamName(), postTeamReq.getTeamImgUrl()};
        this.jdbcTemplate.update(makeTeamQuery, createTeamParams);

        String makeMappingQuery = "insert into Mapping (teamIdx, userIdx) values (?, ?)";
        String lastInsertTeamIdxQuery = "select last_insert_id()";      // teamIdx 값 추출
        int teamIdx = this.jdbcTemplate.queryForObject(lastInsertTeamIdxQuery, int.class);

        Object[] createMappingParams = new Object[]{teamIdx, userIdx};
        this.jdbcTemplate.update(makeTeamQuery, createMappingParams); // Mapping 객체 생성
    }
    /*
    public void createTeam(PostTeamReq postTeamReq) {
        String createTeamQuery = "insert into Team (teamName, teamImgUrl) VALUES (?, ?)";
        Object[] createUserParams = new Object[]{postTeamReq.getTeamName(), postTeamReq.getTeamImgUrl()};
        this.jdbcTemplate.update(createTeamQuery, createUserParams);

        String lastInsertTeamIdx = "select last_insert_id()";
        String insertTeamQuery = "select * from Team where teamIdx = ?";
    }
     */
}









