package maestrogroup.core.ExceptionHandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
@JsonPropertyOrder({"message", "httpStatus"})
public class BaseResponse{  // BaseResponse 객체를 사용할 때 성공, 실패 경우
    private final HttpStatus httpStatus;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String message;

    // 요청에 성공한 경우
    public BaseResponse(){
        this.httpStatus = BaseResponseStatus.SUCCESS.getHttpStatus();
        this.message = BaseResponseStatus.SUCCESS.getMessage();
    }

    // 요청에 실패한 경우
    public BaseResponse(BaseResponseStatus baseResponseStatus){
        this.message = baseResponseStatus.getMessage();
        this.httpStatus = baseResponseStatus.getHttpStatus();
    }
}