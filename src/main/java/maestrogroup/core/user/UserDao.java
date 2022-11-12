package maestrogroup.core.user;

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
}
