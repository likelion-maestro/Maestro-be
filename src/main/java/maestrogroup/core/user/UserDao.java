package maestrogroup.core.user;

import maestrogroup.core.user.model.GetUser;
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

    public void deleteUser(int userIdx){
        String deleteUserQuery = "update User set status = 'D' where userIdx = ?";
        this.jdbcTemplate.update(deleteUserQuery, userIdx);
    }

    public GetUser getUser(int userIdx){
        String GetUserQuery = "select * from User where userIdx = ?";
        System.out.println("=================================================================");
        return this.jdbcTemplate.queryForObject(GetUserQuery,
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
                userIdx);
    }
}
