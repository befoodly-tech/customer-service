package com.befoodly.be.utils;

import org.apache.logging.log4j.util.Strings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumberValidatorUtil {
    public static boolean isValidPhoneNumber(String phoneNumber) {
        Pattern p = Pattern.compile("^\\d{10}$");
        Matcher m = p.matcher(phoneNumber);
        return (Strings.isNotBlank(phoneNumber) && m.matches());
    }
}
