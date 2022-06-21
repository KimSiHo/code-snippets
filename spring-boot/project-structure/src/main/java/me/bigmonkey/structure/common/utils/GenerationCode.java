/*
 * @(#)GenerationCode 2021-11-03
 *
 * Copyright 2021 Etoos Education. All rights Reserved.
 * Etoos PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package me.bigmonkey.structure.common.utils;

import java.security.SecureRandom;
import java.util.UUID;

import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.codec.binary.StringUtils;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class GenerationCode {
    private static final SecureRandom random = new SecureRandom();
    private static final char[] characterTable = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'
    };

    /**
     * 숫자로 구성된 인증 코드 생성
     *
     * @param codeLength
     * @return
     */
    public static String generateCodeNumber(int codeLength) {
        final int range = (int)Math.pow(10.0, codeLength);
        final int trim = (int)Math.pow(10.0, codeLength - 1.0);

        int result = random.nextInt(range) + trim;
        if (result > range) {
            result = result - trim;
        }

        return String.valueOf(result);
    }

    /**
     * 알파벳, 숫자로 구성된 인증 코드 생성
     *
     * @param codeLength
     * @return
     */
    public static String generateCodeCharacter(int codeLength) {
        final int tableLength = characterTable.length;
        final StringBuilder result = new StringBuilder();
        for (int i = 0; i < codeLength; i++) {
            result.append(characterTable[random.nextInt(tableLength)]);
        }

        return result.toString();
    }

    /**
     * UUID 기반의 22자리 unique ID 생성
     * (16진수 36자리 UUID -> 64진수 22자리 UUID)
     *
     * @return 22자리의 UUID
     */
    public static String randomUniqueID() {
        final UUID uuid = UUID.randomUUID();
        final long msb = uuid.getMostSignificantBits();
        final long lsb = uuid.getLeastSignificantBits();

        final byte[] base64UUID = Base64.encodeBase64(
                mergeBytes(longToByteBigEndian(msb), longToByteBigEndian(lsb)), false, true);
        return StringUtils.newStringUsAscii(base64UUID);
    }

    private static byte[] longToByteBigEndian(long l) {
        final byte[] bytes = new byte[8];

        for (int i = 7; i >= 0; --i) {
            bytes[i] = (byte)((int)l);
            l >>>= 8;
        }

        return bytes;
    }

    private static byte[] mergeBytes(byte[] b1, byte[] b2) {
        final byte[] b = new byte[b1.length + b2.length];
        System.arraycopy(b1, 0, b, 0, b1.length);
        System.arraycopy(b2, 0, b, b1.length, b2.length);
        return b;
    }
}
