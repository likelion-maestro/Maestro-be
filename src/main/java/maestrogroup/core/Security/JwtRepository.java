package maestrogroup.core.Security;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maestrogroup.core.ExceptionHandler.BaseException;
import maestrogroup.core.ExceptionHandler.BaseResponseStatus;
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

    public int checkDuplicateBlackAccessToken(String accessTokenParam){
        String findBlackUserQuery = "select count(*) from BlackList where accessToken = ?";
        Object[] accessTokenQueryParams = new Object[]{accessTokenParam};
        Integer tokenCount = this.jdbcTemplate.queryForObject(findBlackUserQuery, accessTokenQueryParams, Integer.class);
        System.out.println(tokenCount);
        return tokenCount;
    }

    // 만료안된 accessToken을 계속 요청으로 보내서 블랙리스트 db에 과축적 되는 현상을 방지하기 위한 예외처리 요망
    // => 특정 토큰에 대한 블랙리스트 데이터는 유일성을 보장할 것, 즉 동일한 토큰을 또 보냈을 때 db에 저장되는 현상을 막자.
    // 단 특정 유저가 짧은 시간에 로그인을 여러번 시도해서 한 유저가 여러개의
    // 토큰을 지닐 수 있므므로 userIdx값이 아닌, 토큰값을 기준으로 판단하자.
    public void saveBlickList(int userIdx, String accessToken, int exp) throws BaseException {
        try {
            int tokenCount = checkDuplicateBlackAccessToken(accessToken);
            if (tokenCount >= 2){
                throw new BaseException(BaseResponseStatus.ACCESS_TOKEN_EXPIRED);
            }
        }
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
        Object[] ModifyUserInfoParams = new Object[]{modifyUserInfoReq.getEmail(), modifyUserInfoReq.getNickname(), modifyUserInfoReq.getPassword(), userIdx};
        this.jdbcTemplate.update(ModifyUserInfoQuery, ModifyUserInfoParams);
    }

    public void deleteRefreshToken(String refreshToken){
        String deleteTokenQuery = "DELETE FROM RefreshToken where tokenContents = ?";
        this.jdbcTemplate.update(deleteTokenQuery, refreshToken);
    }
}