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

    public void changeNewRefreshToken(String updateRefreshToken){
        String updateTokenQuery = "update RefreshToken (refreshToken) VALUES (?)";
        this.jdbcTemplate.update(updateTokenQuery, updateRefreshToken);
    }

    public void deleteRefreshToken(String refreshToken){
        String deleteTokenQuery = "DELETE FROM RefreshToken where tokenContents = ?";
        this.jdbcTemplate.update(deleteTokenQuery, refreshToken);
    }
}