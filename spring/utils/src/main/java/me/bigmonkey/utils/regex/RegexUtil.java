package me.bigmonkey.utils.regex;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

    public static String maskingEmail(String email) {
        Matcher matcher = Pattern.compile(ValidationPattern.MASKING_GROUP_EMAIL).matcher(email);
        if (matcher.find()) {
            String id = matcher.group(1);

            if (id.length() > 3) {
                return email.replaceAll(ValidationPattern.MASKING_EMAIL, "$1***@$2");
            } else {
                char[] c = new char[id.length()];
                Arrays.fill(c, '*');

                return String.valueOf(c) + email.replaceAll(ValidationPattern.MASKING_EMAIL_MIN, "@$1");
            }
        } else {

            System.out.println("error");
            throw new RuntimeException();
        }
    }

    public static String maskingEmailN(String email) {
        Matcher matcher = Pattern.compile(ValidationPattern.MASKING_GROUP_EMAIL).matcher(email);

        if (matcher.find()) {
            String target = matcher.group(1);
            int length = target.length();

            if(length > 3) {
                char[] c = new char[3];
                Arrays.fill(c, '*');
                return email.replace(target, target.substring(0, length - 3) + String.valueOf(c));
            } else {
                char[] c = new char[length];
                Arrays.fill(c, '*');
                return email.replace(target, String.valueOf(c));
            }
        }

        return email;
    }

}
