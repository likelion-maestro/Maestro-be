package maestrogroup.core.Security;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maestrogroup.core.Security.JWTtoken.RefreshToken;
import maestrogroup.core.user.model.ModifyUserInfoReq;
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

    public RefreshToken getRefreshToken(String dbRefreshToken){
        String getRefreshTokenQuery = "select * from RefreshToken where refreshToken = ?";
        return this.jdbcTemplate.queryForObject(getRefreshTokenQuery,
                (rs, rowNum) -> new RefreshToken(
                        rs.getString("refreshToken")),
                dbRefreshToken);
    }

    public void changeNewRefreshToken(String updateRefreshToken, String originRefreshToken){
        String updateTokenQuery = "update RefreshToken set refreshToken = ? where refreshToken = ?";
        Object[] refreshTokenList = new Object[]{updateRefreshToken, originRefreshToken};
        this.jdbcTemplate.update(updateTokenQuery, refreshTokenList);
    }

    public void modifyUserInfo(int userIdx, ModifyUserInfoReq modifyUserInfoReq){
        String ModifyUserInfoQuery = "update User set email = ?, nickname = ?, userProfileImgUrl = ?, password = ? where userIdx = ?";
        Object[] ModifyUserInfoParams = new Object[]{modifyUserInfoReq.getEmail(), modifyUserInfoReq.getNickname(), modifyUserInfoReq.getUserProfileImgUrl(), modifyUserInfoReq.getPassword(), userIdx};
        this.jdbcTemplate.update(ModifyUserInfoQuery, ModifyUserInfoParams);
    }

    public void deleteRefreshToken(String refreshToken){
        String deleteTokenQuery = "DELETE FROM RefreshToken where tokenContents = ?";
        this.jdbcTemplate.update(deleteTokenQuery, refreshToken);
    }
}