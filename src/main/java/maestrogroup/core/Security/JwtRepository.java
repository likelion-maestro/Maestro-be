package maestrogroup.core.Security;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
// import org.springframework.stereotype.Service;

@Repository
@RequiredArgsConstructor
@Slf4j
public class JwtRepository {
    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void saveRefreshToken(String newRefreshToken){
        String saveTokenQuery = "insert into RefreshToken (refreshToken) VALUES (?)";
        this.jdbcTemplate.update(saveTokenQuery, newRefreshToken);
    }

    public void deleteRefreshToken(String refreshToken){
        String deleteTokenQuery = "update FROM RefreshToken where tokenContents = ?";
        this.jdbcTemplate.update(deleteTokenQuery, refreshToken);
    }
    /*
    public void deleteUser(int userIdx, Timestamp timestamp){
        //System.out.println(simpleDateFormat.format(timestamp));
        String deleteUserQuery = "update User set status = 'D', updatedAt = ? where userIdx = ?";
        Object[] deleteUserQueryParams = new Object[]{timestamp, userIdx};
        this.jdbcTemplate.update(deleteUserQuery, deleteUserQueryParams);
    }

     */
}