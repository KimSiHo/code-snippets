package me.bigmonkey.structure.common.utils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringConvert {

    public static String maskingId(String id, IdType idType) {
        if (IdType.IDT001 == idType) {
            return maskingEmail(id);
        } else {
            return maskingPhone(id);
        }
    }

    public static String getActiveProfileFromArguments(String[] args) {
        if (args == null || args.length == 0) {
            return null;
        }

        for (String argument : args) {
            if (argument.contains("spring.profiles.active=")) {
                String[] profiles = argument.split("spring.profiles.active=");
                return profiles[1];
            }
        }

        return null;
    }

    public static String decodeBase64(String input) {
        return new String(Base64.getDecoder().decode(input.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    }

    public static String maskingPhone(String phone) {
        Matcher matcher = Pattern.compile(ValidationPattern.MASKING_GROUP_PHONE).matcher(phone);
        if (matcher.find()) {
            return phone.replaceAll(ValidationPattern.MASKING_GROUP_PHONE, "$1****$3");
        } else {
            throw new MemberServiceException(MemberErrorCode.UTILS_FAILED_TO_MASKING);
        }
    }

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
            throw new MemberServiceException(MemberErrorCode.UTILS_FAILED_TO_MASKING);
        }
    }

    public static String maskingEmailN(String email) {
        Matcher matcher = Pattern.compile(ValidationPattern.MASKING_GROUP_EMAIL).matcher(email);

        if (matcher.find()) {
            String target = matcher.group(1);
            int length = target.length();

            if (length > 3) {
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
