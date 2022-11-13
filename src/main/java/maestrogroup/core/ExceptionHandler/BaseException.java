package maestrogroup.core.ExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseException extends Exception {
    private BaseResponseStatus status; // BaseResponseStatus 객체에 매핑 =>
    // @AllArgsConstructor 를 통해 생성자를 자동 호출하고 status 에 할당될 값을 자동으로 초기화시킴
}
