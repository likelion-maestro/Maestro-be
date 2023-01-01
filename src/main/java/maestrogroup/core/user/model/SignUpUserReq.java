package maestrogroup.core.user.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

// userIdx, createdAt, email, is_connected, nickname, password, status, updatedAt, userProfileImgUrl
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpUserReq {
    //@Email(message = "올바른 이메일 형식이 아닙니다.")
    @ApiModelProperty(example = "abcde2@naver.com")
    private String email;

    @ApiModelProperty(example = "abcde12345")
    private String password;

    @ApiModelProperty(example = "abcde12345")
    private String repass;

    @ApiModelProperty(example = "member2")
    private String nickname;
    
}
// implementation 'org.springframework.boot:spring-boot-starter-validation' 로 의존성을 추가해야지 유효성 검사 관련 어노테이션 사용가능
// https://victorydntmd.tistory.com/332


/*
@NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")

@NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
 */
