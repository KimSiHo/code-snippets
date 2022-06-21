/*
package me.bigmonkey.structure.common.utils;

import java.net.URI;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.etoos.member.common.crypto.Hash;
import com.etoos.member.common.exception.MemberErrorCode;
import com.etoos.member.common.exception.MemberServiceException;
import com.etoos.member.common.utils.GenerationCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SmsService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${authentication.sms.uplus.base-url}")
    private String baseUrl = "https://openapi.sms.uplus.co.kr:4443";

    @Value("${authentication.sms.uplus.send-path}")
    private String sendPath = "/v1/send";

    @Value("${authentication.sms.uplus.from}")
    private String from;

    @Value("${authentication.sms.uplus.api-key}")
    private String apiKey;

    @Value("${authentication.sms.uplus.api-secret}")
    private String apiSecret;

    private String UPLUS_SHA256_CODE = "0";
    private String SEND_TYPE_S = "S"; // 즉시 메세지 발송
    private String SEND_TYPE_R = "R"; // 예약 메세지 발송
    private String MSG_TYPE_SMS = "S"; // SMS 전송
    private String MSG_TYPE_MMS = "M"; // MMS 전송
    private String MSG_TYPE_LMS = "L"; // LMS 전송

    public void sendSms(String to, String message) {
        // 1. Timestamp 생성. (millisecond 포함 13자리)
        String timestamp = Long.toString(ZonedDateTime.now(ZoneOffset.UTC).toInstant().toEpochMilli());
        // 2. salt 생성. (10자리 랜덤)
        String salt = GenerationCode.generateCodeNumber(10);
        // 3. UPLUS hash 값 생성.
        String hash = getHash(timestamp, salt);

        // 4. header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("hash_hmac", hash);
        headers.add("api_key", apiKey);
        headers.add("timestamp", timestamp);
        headers.add("cret_txt", salt);
        headers.add("algorithm", UPLUS_SHA256_CODE);
        headers.add("Content-Type", "multipart/form-data; charset=UTF-8;");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("send_type", SEND_TYPE_S);
        params.add("to", to);
        params.add("from", from);
        params.add("msg_type", MSG_TYPE_SMS);
        params.add("msg", message);

        HttpEntity<MultiValueMap<String, String>> allRequest = new HttpEntity<>(params, headers);
        URI uri = URI.create(baseUrl + sendPath);

        ResponseEntity<String> response = restTemplate.postForEntity(uri, allRequest, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new MemberServiceException(MemberErrorCode.UTILS_FAILED_TO_CONNECT_SMS);
        }

        try {
            SmsData result = objectMapper.readValue(response.getBody(), SmsData.class);
            if (!result.rcode.equals("1000")) {
                log.error(result.rcode);
                log.error(result.rdesc);
                throw new MemberServiceException(MemberErrorCode.UTILS_FAILED_TO_SEND_SMS);
            }
        } catch (JsonProcessingException exception) {
            throw new MemberServiceException(MemberErrorCode.UTILS_FAILED_TO_PARSE_SMS_DATA);
        }
    }

    private String getHash(String timestamp, String salt) {
        StringBuilder input = new StringBuilder();
        input.append(apiKey);
        input.append(timestamp);
        input.append(salt);
        input.append(apiSecret);

        return Hash.sha256(input.toString());
    }

    @Getter
    public static class SmsData {
        private String rcode;
        private String rdesc;
        private Map<String, String> data;
    }
}
*/
