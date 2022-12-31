package maestrogroup.core.user;

import maestrogroup.core.ExceptionHandler.BaseException;
import maestrogroup.core.ExceptionHandler.BaseResponse;
import maestrogroup.core.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

@Repository
public class UserDao {

    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public SignUpUserRes createUser(SignUpUserReq signUpUserReq){
        String createUserQuery = "insert into User (email, password, nickname) VALUES (?, ?, ?)";
        Object[] createUserParams = new Object[]{signUpUserReq.getEmail(), signUpUserReq.getPassword(), signUpUserReq.getNickname()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String userIdxQuery = "select last_insert_id()";
        int userIdx = this.jdbcTemplate.queryForObject(userIdxQuery, int.class);

        String GetUserQuery = "select * from User where userIdx = ?";
        return this.jdbcTemplate.queryForObject(GetUserQuery,
                (rs, rowNum) -> new SignUpUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("nickname")),
                userIdx);
    }
    /*
     public int Email_Duplicate_Check(String email){
        String Email_Duplicate_Check_Query = "select exists(select email from User where email = ?)"; // 해당 email 값을 갖는 유저가 존재하는가?
        String checkEmailparams = email;
        return this.jdbcTemplate.queryForObject(Email_Duplicate_Check_Query, int.class, checkEmailparams); // 존재하면 1을, 존재하지 않으면 0을 리턴(int형 타입으로써 리턴)
    }
     */

    public void modifyUserInfo(int userIdx, ModifyUserInfoReq modifyUserInfoReq){
        String ModifyUserInfoQuery = "update User set email = ?, nickname = ?, password = ? where userIdx = ?";
        Object[] ModifyUserInfoParams = new Object[]{modifyUserInfoReq.getEmail(), modifyUserInfoReq.getNickname(), modifyUserInfoReq.getPassword(), userIdx};
        this.jdbcTemplate.update(ModifyUserInfoQuery, ModifyUserInfoParams);
    }

    public void deleteUser(int userIdx){
        //삭제할 User가 가입되어 있는 Team의 teamIdx를 불러옴
        List<Integer> teamIdxList = this.jdbcTemplate.queryForList("select teamIdx from Mapping where userIdx = ?", int.class, userIdx);

        for (int teamIdx : teamIdxList) {
            //해당 팀의 count를 감소시키는 부분
            int nowCount = this.jdbcTemplate.queryForObject("select count from Team where teamIdx = ?", int.class, teamIdx);
            nowCount -= 1;

            Object[] updateCountParams = new Object[]{nowCount, teamIdx};
            this.jdbcTemplate.update("update Team set count = ? where teamIdx = ?", updateCountParams);
        }

        //User 삭제
        String deleteUserQuery = "delete from User where userIdx = ?";
        this.jdbcTemplate.update(deleteUserQuery, userIdx);
    }

    public GetUser getUser(int userIdx) {
        String GetUserQuery = "select * from User where userIdx = ?";
        return this.jdbcTemplate.queryForObject(GetUserQuery,
                (rs, rowNum) -> new GetUser(
                        rs.getInt("userIdx"),
                        rs.getString("email"),
                        rs.getString("nickname"),
                        rs.getString("password")),
                userIdx);
    }

    public int Email_Duplicate_Check(String email){
        String Email_Duplicate_Check_Query = "select exists(select email from User where email = ?)"; // 해당 email 값을 갖는 유저가 존재하는가?
        String checkEmailparams = email;
        return this.jdbcTemplate.queryForObject(Email_Duplicate_Check_Query, int.class, checkEmailparams); // 존재하면 1을, 존재하지 않으면 0을 리턴(int형 타입으로써 리턴)
    }

    public LoginUserSomeField getSomeInfo_WhenLogin(LoginUserReq loginUserReq){
        String getPwdQuery = "select userIdx, password, email, nickname from User where email = ?";
        String getPwdParams = loginUserReq.getEmail();
        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs, rowNum) -> new LoginUserSomeField(
                        rs.getInt("userIdx"),
                        rs.getString("email"),
                        rs.getString("nickname"),
                        rs.getString("password")
                ),
                getPwdParams
        );
    }
}