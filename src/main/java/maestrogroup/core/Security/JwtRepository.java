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

    public void saveRefreshToken(String newRefreshToken, int userIdx){
        String saveTokenQuery = "insert into RefreshToken (tokenContents, userIdx) VALUES (?, ?)";
        Object[] saveTokenQueryParams  = new Object[]{newRefreshToken, userIdx};
        this.jdbcTemplate.update(saveTokenQuery, saveTokenQueryParams);
    }

    public RefreshToken getRefreshToken(String dbRefreshToken){
        String getRefreshTokenQuery = "select * from RefreshToken where tokenContents = ?";
        return this.jdbcTemplate.queryForObject(getRefreshTokenQuery,
                (rs, rowNum) -> new RefreshToken(
                        rs.getString("tokenContents")),
                dbRefreshToken);
    }

    public void saveBlickList(int userIdx, String accessToken, int exp){
        String saveTokenQuery = "insert into BlackList (userIdx, accessToken, expireDate) VALUES (?, ?, ?)";
        Object[] saveTokenQueryParams = new Object[]{userIdx, accessToken, exp};
        this.jdbcTemplate.update(saveTokenQuery, saveTokenQueryParams);
    }

    public void changeNewRefreshToken(String updateRefreshToken, String originRefreshToken){
        String updateTokenQuery = "update RefreshToken set tokenContents = ? where tokenContents = ?";
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