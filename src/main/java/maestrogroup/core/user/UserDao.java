package maestrogroup.core.user;

import maestrogroup.core.user.model.ModifyUserInfoReq;
import maestrogroup.core.user.model.SignUpUserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UserDao {

    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void createUser(SignUpUserReq signUpUserReq){
        String createUserQuery = "insert into User (email, password, nickname) VALUES (?, ?, ?)";
        Object[] createUserParams = new Object[]{signUpUserReq.getEmail(), signUpUserReq.getPassword(), signUpUserReq.getNickname()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);
    }

    public void modifyUserInfo(int userIdx, ModifyUserInfoReq modifyUserInfoReq){
        String ModifyUserInfoQuery = "update User set email = ?, nickname = ?, userProfileImgUrl = ?, password = ? where userIdx = ?";
        Object[] ModifyUserInfoParams = new Object[]{modifyUserInfoReq.getEmail(), modifyUserInfoReq.getNickname(), modifyUserInfoReq.getUserProfileImgUrl(), modifyUserInfoReq.getPassword(), userIdx};
        this.jdbcTemplate.update(ModifyUserInfoQuery, ModifyUserInfoParams);
    }
    /*
    public void modifyTeam(PatchTeamReq patchUserReq){
        String modifyTeamQuery = "update Team set teamName = ?,  teamImgUrl = ? where teamIdx = ?";
        Object[] modifyUserNameParams = new Object[]{patchUserReq.getTeamName(), patchUserReq.getTeamImgUrl(), patchUserReq.getTeamIdx()};
        this.jdbcTemplate.update(modifyTeamQuery, modifyUserNameParams);
    }
     */
}
