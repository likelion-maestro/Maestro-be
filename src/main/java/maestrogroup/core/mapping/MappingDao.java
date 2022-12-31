package maestrogroup.core.mapping;

import maestrogroup.core.mapping.model.GetTeamAndImportantRes;
import maestrogroup.core.mapping.model.GetTeamIdx;
import maestrogroup.core.mapping.model.GetUserIdx;
import maestrogroup.core.mapping.model.Mapping;
import maestrogroup.core.music.model.Music;
import maestrogroup.core.team.model.GetTeamRes;
import maestrogroup.core.team.model.PostTeamReq;
import maestrogroup.core.user.model.GetUser;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.objenesis.ObjenesisHelper;
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
        //초대하는 부분
        String inviteUserQuery = "insert into Mapping (teamIdx, userIdx) VALUES (?, ?)";
        Object[] inviteUserParams = new Object[]{teamIdx, userIdx};
        this.jdbcTemplate.update(inviteUserQuery, inviteUserParams);

        //해당 팀의 count를 증가시키는 부분
        int nowCount = this.jdbcTemplate.queryForObject("select count from Team where teamIdx = ?", int.class, teamIdx);
        nowCount += 1;

        Object[] updateCountParams = new Object[]{nowCount, teamIdx};
        this.jdbcTemplate.update("update Team set count = ? where teamIdx = ?", updateCountParams);
    }

    public void makeTeam(int userIdx, PostTeamReq postTeamReq){
        String makeTeamQuery = "insert into Team (teamName, count) VALUES (?, 1)";
        this.jdbcTemplate.update(makeTeamQuery, postTeamReq.getTeamName());

        String makeMappingQuery = "insert into Mapping (teamIdx, userIdx) values (?, ?)";
        String lastInsertTeamIdxQuery = "select last_insert_id()";      // teamIdx 값 추출
        int teamIdx = this.jdbcTemplate.queryForObject(lastInsertTeamIdxQuery, int.class);

        Object[] createMappingParams = new Object[]{teamIdx, userIdx};
        this.jdbcTemplate.update(makeMappingQuery, createMappingParams); // Mapping 객체 생성
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
                            rs.getString("email"),
                            rs.getString("nickname"),
                            rs.getString("password")),
                    eachUserIdx);
            // GetUser 리스트에 추가
            getUserList.add(eachUser);
        }

        return getUserList;
    }

    public List<GetTeamRes> getTeamList(int userIdx){
        String getUserAllTeamIdxQuery = "select teamIdx from Mapping where userIdx = ?";

        java.util.List<GetTeamIdx> GetTeamIdxList = this.jdbcTemplate.query(getUserAllTeamIdxQuery,
                (rs, rowNum) -> new GetTeamIdx(
                        rs.getInt("teamIdx")), userIdx);

        List<Integer> teamIdxList = new ArrayList<Integer>();
        for(GetTeamIdx getTeamIdx : GetTeamIdxList){
            teamIdxList.add(getTeamIdx.getTeamIdx());
        }

        // 반복문으로 순환하면서 얻어온 각 userIdx 값을 기반으로 GetUser 리스트 생성
        List<GetTeamRes> getTeamResList = new ArrayList<GetTeamRes>();
        String GetTeamResQuery = "select * from Team where teamIdx = ?";

        for(int eachTeamIdx : teamIdxList){
            GetTeamRes eachTeam = this.jdbcTemplate.queryForObject(GetTeamResQuery,
                    (rs, rowNum) -> new GetTeamRes(
                            rs.getInt("teamIdx"),
                            rs.getString("teamName"),
                            rs.getInt("count")
                    ),
                    eachTeamIdx);

            getTeamResList.add(eachTeam);
        }

        return getTeamResList;
    }

    // public void deleteTeam(int teamIdx){
    //        String deleteTeamQuery = "delete from Team where teamIdx = ?";
    //        this.jdbcTemplate.update(deleteTeamQuery, teamIdx);
    //    }
    public void deleteTeam(int teamIdx, int userIdx){
        String deleteTeamQuery = "delete from Mapping where teamIdx = ? AND userIdx = ?";
        Object[] deleteTeamQueryParams = new Object[]{teamIdx, userIdx};
        this.jdbcTemplate.update(deleteTeamQuery, deleteTeamQueryParams);

        //해당 팀의 count를 감소시키는 부분
        int nowCount = this.jdbcTemplate.queryForObject("select count from Team where teamIdx = ?", int.class, teamIdx);
        nowCount -= 1;

        Object[] updateCountParams = new Object[]{nowCount, teamIdx};
        this.jdbcTemplate.update("update Team set count = ? where teamIdx = ?", updateCountParams);

        //if nowCount <= 0: 팀 삭제 기능 추후에 추가!
    }

    public void changeImportanceOfTeam(int userIdx, int teamIdx) {
        int nowImportance = this.jdbcTemplate.queryForObject("select important from Mapping where userIdx = ? AND teamIdx = ?", int.class, userIdx, teamIdx);
        int value = -1;
        if (nowImportance == 1) {
            value = 0;
        }
        if (nowImportance == 0) {
            value = 1;
        }
        String changeImportanceOfTeamQuery = "update Mapping set important = ? where teamIdx = ?";
        Object[] params = new Object[]{value, teamIdx};
        this.jdbcTemplate.update(changeImportanceOfTeamQuery, params);
    }

    public List<GetTeamAndImportantRes> getTeamAndImportant(int userIdx){
        List<GetTeamAndImportantRes> result = new ArrayList<GetTeamAndImportantRes>();

        List<Integer> teamIdxList = this.jdbcTemplate.queryForList("select teamIdx from Mapping where userIdx = ?", int.class, userIdx);
        for (int teamIdx : teamIdxList) {

            int important = this.jdbcTemplate.queryForObject("select important from Mapping where userIdx = ? AND teamIdx = ?", int.class, userIdx, teamIdx);

            GetTeamAndImportantRes eachTeam = this.jdbcTemplate.queryForObject("select * from Team where teamIdx = ?",
                    (rs, rowNum) -> new GetTeamAndImportantRes(
                            rs.getInt("teamIdx"),
                            rs.getString("teamName"),
                            rs.getInt("count"),
                            important),
                    teamIdx);
            result.add(eachTeam);
        }
        return result;
    }

    public int isUserInTeam(int teamIdx, int userIdx) {
        String query = "select exists (select userIdx from Mapping where teamIdx = ? AND userIdx = ?)";
        Object[] params= new Object[]{teamIdx, userIdx};
        return this.jdbcTemplate.queryForObject(query, int.class, params);
    }
}









