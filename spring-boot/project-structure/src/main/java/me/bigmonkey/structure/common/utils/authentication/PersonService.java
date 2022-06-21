/*
package me.bigmonkey.structure.common.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.etoos.member.common.enums.AuthenticationUseType;
import com.etoos.member.common.exception.MemberErrorCode;
import com.etoos.member.common.exception.MemberServiceException;
import com.etoos.member.common.utils.DateUtil;
import com.sci.v2.pccv2.secu.SciSecuManager;
import com.sci.v2.pccv2.secu.hmac.SciHmac;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class PersonService {

    @Value("${authentication.person.bizsiren.id}")
    private String bizsirenId;
    @Value("${authentication.person.bizsiren.service-number}")
    private String bizsirenServiceNumber;
    @Value("${authentication.person.bizsiren.service-number-change-name}")
    private String getBizsirenServiceNumberChangeName;
    @Value("${authentication.person.bizsiren.service-number-registration-person}")
    private String getBizsirenServiceNumberRegistrationPerson;
    @Value("${authentication.person.bizsiren.secret-key}")
    private String bizsirenSecretKey;
    @Value("${authentication.person.bizsiren.redirect-url}")
    private String bizsirenRedirectUrl;
    @Value("${authentication.person.bizsiren.callback-url}")
    private String bizsirenCallbackUrl;
    @Value("${authentication.person.bizsiren.request-num-length}")
    private int bizsirenRequestNumLength;
    @Value("${authentication.person.bizsiren.cert-type}")
    private String bizsirenCertType;
    @Value("${authentication.person.bizsiren.request-num}")
    private String bizsirenRequestNum;
    @Value("${authentication.person.bizsiren.version}")
    private String bizsirenVersion;

    public RequestData requestPerson(AuthenticationUseType useType) {
        try {
            SciSecuManager seed = new SciSecuManager();

            // 1. reqNum 생성 16자리 랜덤
//        String requestNum = GenerationCode.generateCodeCharacter(bizsirenRequestNumLength);
            String exVar = "0000000000000000"; // 복호화용 임시 필드
            String addVar = ""; // 본인인증 추가 파라미터
            String certDate = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
                    .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")); // 현재 시간

            String serviceNumber = bizsirenServiceNumber;
            if (useType == AuthenticationUseType.AUT015) {
                serviceNumber = getBizsirenServiceNumberChangeName;
            } else if (useType == AuthenticationUseType.AUT016) {
                serviceNumber = getBizsirenServiceNumberRegistrationPerson;
            }

            // 2. requestInfo 생성.
            String requestInfo = bizsirenId
                    + "^" + serviceNumber
                    + "^" + bizsirenRequestNum
                    + "^" + certDate
                    + "^" + bizsirenCertType
                    + "^" + addVar
                    + "^" + exVar;

            // 3. 1차 암호화 진행
            seed.setInfoPublic(bizsirenId, bizsirenSecretKey);
            String encryptionRequestInfo = seed.getEncPublic(requestInfo);

            // 4. 위변조 검증값 생성
            SciHmac hmac = new SciHmac();
            String hmacMessage = seed.getEncReq(encryptionRequestInfo, "HMAC");

            // 5. 2차 암호화 진행
            String secondEncryptionData = encryptionRequestInfo + "^" + hmacMessage + "^" + "0000000000000000";
            String secondEncryption = seed.getEncPublic(secondEncryptionData);

            // 6. 회원사 ID 암호화
            String idEncryption = secondEncryption + "^" + bizsirenId + "^"  + "00000000";
            String reqInfo = seed.EncPublic(idEncryption);

            return RequestData.builder().reqInfo(reqInfo).retUrl(bizsirenCallbackUrl).version(bizsirenVersion).build();
        } catch (Exception exception) {
            throw new MemberServiceException(MemberErrorCode.UTILS_FAILED_TO_REQUEST_PERSON);
        }
    }

    public VerifiedData verifyPerson(String retInfo) {
        SciSecuManager sciSecuManager = new SciSecuManager();

        sciSecuManager.setInfoPublic(bizsirenId, bizsirenSecretKey);

        // 2. 복호화
        String decInfo = sciSecuManager.getDec(retInfo, bizsirenRequestNum);

        // 3. 복호화 데이터 파싱
        String[] decInfoSplit = decInfo.split("\\^");
        if (decInfoSplit.length < 2) {
            throw new MemberServiceException(MemberErrorCode.COMMON_FAILED_TO_VERIFY_PERSON_AUTHENTICATION);
        }
        String encryptionParameter = decInfoSplit[0];
        String encryptionParameterHash = decInfoSplit[1];

        // 4. 위변조 검증
        String verificationParameter = sciSecuManager.getMsg(encryptionParameter);
        if (!verificationParameter.equals(encryptionParameterHash)) {
            throw new MemberServiceException(MemberErrorCode.COMMON_FAILED_TO_VERIFY_PERSON_AUTHENTICATION);
        }

        // 5. 파라미터 복호화
        String decParameter = sciSecuManager.getDec(encryptionParameter, bizsirenRequestNum);
        String[] decParameterSplit = decParameter.split("\\^");
        if (decParameterSplit.length < 15) {
            throw new MemberServiceException(MemberErrorCode.COMMON_FAILED_TO_VERIFY_PERSON_AUTHENTICATION);
        }
        if (!decParameterSplit[9].equals("Y")) {
            throw new MemberServiceException(MemberErrorCode.COMMON_FAILED_TO_VERIFY_PERSON_AUTHENTICATION);
        }
        int gender = 1;
        if (decParameterSplit[2].equals("F")) {
            gender = 2;
        }
        String ci = decParameterSplit[5];
        if (!StringUtils.hasText(decParameterSplit[5])) {
            ci = decParameterSplit[6];
        }

        String birth = DateUtil.convertDateFormat(decParameterSplit[1], "yyyyMMdd", "yyyy-MM-dd");

        return VerifiedData.builder()
                .name(decParameterSplit[0])
                .birYMD(birth)
                .gender(gender)
                .fgnGbn(decParameterSplit[3])
                .di(decParameterSplit[4])
                .ci(ci)
                .civersion(decParameterSplit[7])
                .reqNum(decParameterSplit[8])
                .result(decParameterSplit[9])
                .certGb(decParameterSplit[10])
                .cellNo(decParameterSplit[11])
                .cellCorp(decParameterSplit[12])
                .certDate(decParameterSplit[13])
                .addVar(decParameterSplit[14]).build();
    }

    public String getRedirectUrl() {
        return this.bizsirenRedirectUrl;
    }

    @Getter
    @Builder
    public static class RequestData {
        private String reqInfo;
        private String retUrl;
        private String version;
    }

    @Getter
    @Builder
    public static class VerifiedData {
        private String name;
        private String birYMD;
        private Integer gender;
        private String fgnGbn;
        private String di;
        private String ci;
        private String civersion;
        private String reqNum;
        private String result;
        private String certGb;
        private String cellNo;
        private String cellCorp;
        private String certDate;
        private String addVar;
    }
}
*/
