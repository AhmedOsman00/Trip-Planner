package com.ahmedosman.tripplanner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ahmed on 14-Feb-18.
 */

public class Validation {
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;

    public boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validatePasswordConfirmation(String password, String confirmPassword) {
        if(password.equals(confirmPassword))
            return true;
        return false;
    }

    public boolean validatePasswordLength(String password){
        if(password.length()>=6)
            return true;
        return false;
    }
}
