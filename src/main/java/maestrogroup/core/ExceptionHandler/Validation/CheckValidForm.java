package maestrogroup.core.ExceptionHandler.Validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// https://moonong.tistory.com/31
public class CheckValidForm {
    // 이메일 형식 체크
    public static boolean isValidEmailForm(String Inputemail){
        String regex = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(Inputemail);
        return matcher.find();
    }    // 날짜 형식, 전화 번호 형식 등 여러 Regex 인터넷에 검색해보자!

    public static boolean isEqual_Passwrord_Check(String password, String Repassword){
        System.out.println(password);
        System.out.println(Repassword);
        //if(password != Repassword)
        if(password.equals(Repassword))
            return true;
        else
            return false;
    }

    public static boolean isValid_Password_Form(String password){
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d~!@#$%^&*()+|=]{8,20}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }
}
