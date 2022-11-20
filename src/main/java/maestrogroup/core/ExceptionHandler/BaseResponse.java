package maestrogroup.core.ExceptionHandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
@JsonPropertyOrder({"message", "httpStatus"})
public class BaseResponse<T>{  // BaseResponse 객체를 사용할 때 성공, 실패 경우
    private HttpStatus httpStatus;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    private T result;

    // 요청에 성공한 경우
    public BaseResponse(){
        this.httpStatus = BaseResponseStatus.SUCCESS.getHttpStatus();
        this.message = BaseResponseStatus.SUCCESS.getMessage();
    }

    public BaseResponse(T result){
        this.httpStatus = BaseResponseStatus.SUCCESS.getHttpStatus();
        this.message = BaseResponseStatus.SUCCESS.getMessage();
        this.result = result;
    }

    // 요청에 실패한 경우
    public BaseResponse(BaseResponseStatus baseResponseStatus){
        this.message = baseResponseStatus.getMessage();
        this.httpStatus = baseResponseStatus.getHttpStatus();
    }
}