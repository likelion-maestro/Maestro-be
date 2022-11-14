package maestrogroup.core.mapping;

import maestrogroup.core.mapping.model.GetUserIdx;
import maestrogroup.core.mapping.model.Mapping;
import maestrogroup.core.team.model.PostTeamReq;
import maestrogroup.core.user.model.GetUser;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;

import java.util.List;

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

    // ManyToMany 다대다 관계 활용
    public List<GetUser> getTeamMembers(int teamIdx){
        String getTeamAllUserIdxQuery = "select userIdx from Mapping where teamIdx = ?"; // teamIdx 값에 속하는 팀에 속하는 유저들의 userIdx 값들을 추출

        // userIdx 값들이 추출해서 userIdx값들이 저장된 GetUserIdx 리스트 생성
        java.util.List<GetUserIdx> GetUserIdxList = this.jdbcTemplate.query(getTeamAllUserIdxQuery,
                (rs, rowNum) -> new GetUserIdx(
                        rs.getInt("userIdx")), teamIdx);

        List<Integer> userIdxlist = new ArrayList<Integer>();
        // 각 GetUserIdx 객체로부터 userIdx 값을 추출해서 int형 리스트 생성
        for(GetUserIdx getUserIdx : GetUserIdxList){
            userIdxlist.add(getUserIdx.getUserIdx());
        }

        // 반복문으로 순환하면서 얻어온 각 userIdx 값을 기반으로 GetUser 리스트 생성
        List<GetUser> getUserList = new ArrayList<GetUser>();
        String GetUserQuery = "select * from User where userIdx = ?";

        for(int eachUserIdx : userIdxlist){
            // DB 로 부터 User 를 얻어와서
            GetUser eachUser = this.jdbcTemplate.queryForObject(GetUserQuery,
                    (rs, rowNum) -> new GetUser(
                            rs.getInt("userIdx"),
                            rs.getTimestamp("createdAt"),
                            rs.getString("email"),
                            rs.getInt("is_connected"),
                            rs.getString("nickname"),
                            rs.getString("password"),
                            rs.getString("status"),
                            rs.getTimestamp("updatedAt"),
                            rs.getString("userProfileImgUrl")),
                    eachUserIdx);
            // GetUser 리스트에 추가
            getUserList.add(eachUser);
        }

        return getUserList;
    }
}









