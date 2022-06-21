/*
package me.bigmonkey.structure.common.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.amazonaws.services.simpleemail.model.*;
import com.etoos.member.common.exception.MemberErrorCode;
import com.etoos.member.common.exception.MemberServiceException;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class GoogleCaptchaService {
    private final RestTemplate restTemplate;

    @Value("${authentication.captcha.google.url}")
    private String url;

    @Value("${authentication.captcha.google.secret}")
    private String secret;

    public boolean checkRecaptcha(String captchaResponse) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("secret", secret);
        body.add("response", captchaResponse);
        try {
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(url);

            // 3. 요청
            HttpEntity<String> allRequest = new HttpEntity<>(body);

            GoogleCaptchaResultDto response = restTemplate.postForObject(uriBuilder.build().toUri(), body, GoogleCaptchaResultDto.class);

            if (response == null || !response.getSuccess()) {
                throw new MemberServiceException(MemberErrorCode.UTILS_FAILED_TO_GOOGLE_RECAPTCHA);
            }
        } catch (Exception exception1) {
            throw new MemberServiceException(MemberErrorCode.UTILS_FAILED_TO_GOOGLE_RECAPTCHA);
        }

        return true;
    }

    @Getter
    @Setter
    public static class GoogleCaptchaResultDto {

        private Boolean success;

        @JsonProperty(value = "error-codes")
        private List<String> errorCodes;

        @JsonProperty(value = "challenge_ts")
        private String challengeTs;
        private String hostname;

    }
}
*/
