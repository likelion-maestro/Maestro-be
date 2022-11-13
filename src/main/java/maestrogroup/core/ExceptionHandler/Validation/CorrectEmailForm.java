package maestrogroup.core.ExceptionHandler.Validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
ar form = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
    return form.test(email);
 */
public class CorrectEmailForm {
    // 이메일 형식 체크
    public static boolean isValidEmailForm(String Inputemail){
        String regex = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(Inputemail);
        return matcher.find();
    }    // 날짜 형식, 전화 번호 형식 등 여러 Regex 인터넷에 검색해보자!
}
