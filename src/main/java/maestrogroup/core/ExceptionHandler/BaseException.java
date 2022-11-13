package maestrogroup.core.ExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseException extends Exception {
    private BaseResponseStatus status; // BaseResponseStatus 객체에 매핑
}
