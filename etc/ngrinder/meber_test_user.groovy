package kr.co.ilinker.perf.opentalk

import HTTPClient.Cookie
import HTTPClient.CookieModule
import HTTPClient.HTTPResponse
import HTTPClient.NVPair
import groovy.json.JsonSlurper
import net.grinder.plugin.http.HTTPPluginControl
import net.grinder.plugin.http.HTTPRequest
import net.grinder.script.GTest
import net.grinder.scriptengine.groovy.junit.GrinderRunner
import net.grinder.scriptengine.groovy.junit.annotation.BeforeProcess
import net.grinder.scriptengine.groovy.junit.annotation.BeforeThread
import net.grinder.util.GrinderUtils
import org.json.JSONObject
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.Test

import static net.grinder.script.Grinder.grinder
import static org.junit.Assert.*

@RunWith(GrinderRunner)
class MemberTestUser {

    public static GTest test
    public static HTTPRequest request
    public static Random random
    public static String mode
    public static JsonSlurper jsonSlurper

    // URL 정보
    private static final String BASE_DOMAIN = "http://perf-api.linker.ac"
    private static final String MEMBER_BASE_CONTEXT = "/etoos/members"
    private static final String COMMON_CONTEXT = "/v1/common"
    private static final String SIGNUP_CONTEXT = "/v1/signup"
    private static final String SERVICE_CONTEXT = "/v1/service"
    private static final String AUTH_BASE_CONTEXT = "/etoos/auth/external/v1"

    // 테스트 용 계정 정보
    private static final String EMAIL_FORM = "@test.com"
    private static final int EMAIL_NUM = 150
    private static final int NICKNAME_NUM = 150
    private static final String PASSWORD = "abcd1234"

    // API 헤더 & 파라미터 설정
    private static final String X_FORWARDED_FOR = "13.124.32.110"
    private static final String X_DEVICE_COUNTRY = "ko"
    private static final String COUNTRY_NUMBER = "+82"
    private static final String CONTENT_TYPE = "application/json"
    private static final String X_OS_CODE = "OSC001"
    private static final String ACCOUNT_REGISTER_USE_TYPE = "AUT001"
    private static final String SERVICE_REGISTER_USE_TYPE = "AUT004"
    private static final String POSSESSION_TYPE = "PST001"
    private static final String ID_TYPE = "IDT001"
    private static final String PORTAL_X_CLIENT_ID = "eee3e0c97418e173f8a5f2e26eb85381"
    private static final String PORTAL_X_ClIENT_SECRET = "a9ffa78ffabb25f46ad55cfe77665076cd7932ea74b0b9a21389b85d60d18460"
    private static final String PORTAL_X_REDIRECT_URL = "https://my.linker.ac"
    private static final String SERVICE_X_CLIENT_ID = "c1adf8c0ce66d154345f20fc310e5a5c"
    private static final String SERVICE_X_ClIENT_SECRET = "3c1012923b265b2d0ee961dfabc901b8f469234de221c4ca07153fb648f3e669"
    private static final String SERVICE_REDIRECT_URL = "https://perf-front.platb.ai"

    // 검증 코드
    private static final int SUCCESS_CODE = 21000

    public NVPair[] headers = []
    public NVPair[] params = []
    public Cookie[] cookies = []

    private String getRegistrationEmailId() {
        return 'test-' + ++EMAIL_NUM + EMAIL_FORM
    }

    private String getCurrentEmailId() {
        return 'test-' + EMAIL_NUM + EMAIL_FORM
    }

    @BeforeProcess
    public static void beforeProcess() {
        mode = GrinderUtils.getParam("HEALTHCHECK").toUpperCase().trim()
        HTTPPluginControl.getConnectionDefaults().timeout = 6000
        test = new GTest(1, "Member-SingUp")
        request = new HTTPRequest()
        random = new Random()
        jsonSlurper = new JsonSlurper()
        grinder.logger.info("before process.");
    }

    @BeforeThread
    public void beforeThread() {
        test.record(this, "test")
        grinder.statistics.delayReports = true;
        grinder.logger.info("before thread.");
    }

    @Before
    public void before() {
        cookies.each {
            CookieModule.addCookie(it, HTTPPluginControl.getThreadHTTPClientContext())
        }
        grinder.logger.info("before test. init cookies");
    }

    @Test
    public void test() {
        HTTPResponse result

        switch (mode) {
        // 회원 가입
            case "REGIST_MEMBER":
                // 통합 회원 가입
                // 세션 키 생성 API
                changeCIdCIpPORTALHeader()
                request.setHeaders(headers)
                result = request.POST(BASE_DOMAIN + MEMBER_BASE_CONTEXT + COMMON_CONTEXT + "/request/sessionkey", createSessionKeyBody(ACCOUNT_REGISTER_USE_TYPE).getBytes())
                def sessionKeyJson = jsonSlurper.parseText(result.text)
                def sessionKey = sessionKeyJson.getAt("value").getAt("session_key")
                // grinder.logger.debug("generate session key :" + sessionKey)

                // 약관 동의 API
                changeCIdCIpSERVICEHeader()
                request.setHeaders(headers)
                result = request.POST(BASE_DOMAIN + MEMBER_BASE_CONTEXT + SIGNUP_CONTEXT + "/terms", createTermsAgreeBody(sessionKey).getBytes())
                // def termsAgreeJson = jsonSlurper.parseText(result.text)
                // def termsAgree = sessionKeyJson.getAt("message")
                // grinder.logger.debug("terms agree result :" + termsAgree)

                // ID 사용 가능 여부 API
                changeCIdCIpSERVICEHeader()
                request.setHeaders(headers)
                result = request.POST(BASE_DOMAIN + MEMBER_BASE_CONTEXT + SIGNUP_CONTEXT + "/usable/id", createUsableIdBody(sessionKey).getBytes())
                // def usableIdJson = jsonSlurper.parseText(result.text)
                // def usableId = usableIdJson.getAt("value")
                // grinder.logger.debug("usable id result :" + usableId)

                // 점유 인증 요청 API
                changeCIdCIpSERVICEHeader()
                request.setHeaders(headers)
                result = request.POST(BASE_DOMAIN + MEMBER_BASE_CONTEXT + COMMON_CONTEXT + "/request/possession", createRequestPossessionBody(sessionKey).getBytes())
                def sendCodeJson = jsonSlurper.parseText(result.text)
                def sendCode = sendCodeJson.getAt("value").getAt("auth_code")
                // grinder.logger.debug("possession auth code :" + authCode)

                // 점유 인증 인증 API
                changeCIdCIpSERVICEHeader()
                request.setHeaders(headers)
                result = request.POST(BASE_DOMAIN + MEMBER_BASE_CONTEXT + COMMON_CONTEXT + "/verify/possession", createVerifyPossessionBody(sessionKey, sendCode).getBytes())
                def verifyPossessionJson = jsonSlurper.parseText(result.text)
                def verifyPossession = verifyPossessionJson.getAt("value")
                // grinder.logger.debug("verify possession result :" + verifyPossession)

                // 회원가입 회원 정보 입력 API
                changeCIdCIpPORTALHeader();
                request.setHeaders(headers);
                result = request.POST(BASE_DOMAIN + MEMBER_BASE_CONTEXT + SIGNUP_CONTEXT + "/registration", createRegistrationBody(sessionKey).getBytes())
                // grinder.logger.debug("registration result")
                // grinder.logger.debug(result.text)
                // def registrationJson = jsonSlurper.parseText(result.text)
                // def registration = sessionKeyJson.getAt("message")
                // grinder.logger.debug("member registration result :" + registration)

                // 통합 회원 토큰 발행
                // 로그인 API
                changeCIdCSCIpDIPORTALHeader()
                request.setHeaders(headers)
                result = request.POST(BASE_DOMAIN + AUTH_BASE_CONTEXT + "/signin/account", createLoginBody().getBytes())
                def authCodeJson = jsonSlurper.parseText(result.text)
                def authCode = authCodeJson.getAt("value").getAt("code")
                // grinder.logger.debug("auth code result :" + authCode)

                // 토큰 생성 API
                changeCIpDIHeader()
                request.setHeaders(headers)
                result = request.POST(BASE_DOMAIN + AUTH_BASE_CONTEXT + "/token/create", createTokenCreateBody(authCode).getBytes())
                def accessTokenJson = jsonSlurper.parseText(result.text)
                def accessToken = accessTokenJson.getAt("value").getAt("access_token")
                grinder.logger.debug("member access token result :" + accessToken)

                // 서비스 가입
                // 서비스별 회원 유형 선택 API
                changeCIdCIpUIHeader(accessToken);
                request.setHeaders(headers);
                result = request.POST(BASE_DOMAIN + MEMBER_BASE_CONTEXT + SERVICE_CONTEXT + "/skygo/save/type", createServiceMemberTypeBody(sessionKey).getBytes())
                // grinder.logger.debug(result.text)
                // def memberTypeJson = jsonSlurper.parseText(result.text)
                // def memberType = memberTypeJson.getAt("message")
                // grinder.logger.debug("service member type result :" + memberType)

                // 서비스 등록 API
                changeCIdCIpUIHeader(accessToken);
                request.setHeaders(headers);
                result = request.POST(BASE_DOMAIN + MEMBER_BASE_CONTEXT + SERVICE_CONTEXT + "/skygo/registration", createServiceRegistrationBody(sessionKey).getBytes())
                // grinder.logger.debug(result.text)
                // def serviceRegistrationJson = jsonSlurper.parseText(result.text)
                // def serviceRegistration = serviceRegistrationJson.getAt("message")
                // grinder.logger.debug("service registration result :" + serviceRegistration)
                break

            default:
                result = request.GET(BASE_DOMAIN + "/healthCheck/_check", params)
                break;
        }

        def res

        // 결과 파싱
        if (mode == "REGIST_MEMBER") {
            res = jsonSlurper.parseText(result.text)
        }

        // 결과 검증
        if (result.statusCode == 301 || result.statusCode == 302) {
            grinder.logger.warn('Warning. The response may not be correct. The response code was {}.', result.statusCode)
        } else {
            assertTrue(result.statusCode == 200)

            // RETURN JSON 데이터 검증
            if (mode == "REGIST_MEMBER") {
                assertTrue(res.getAt("code") == SUCCESS_CODE)
            }
        }
    }

    // 세션 키 생성 API REQUEST BODY 설정
    private String createSessionKeyBody(def useType) {
        JSONObject object = new JSONObject();
        object.put("use_type", useType);

        return object.toString();
    }

    // 약관 동의 API REQUEST BODY 설정
    private String createTermsAgreeBody(def sessionKey) {
        JSONObject object = new JSONObject();
        object.put("session_key", sessionKey);
        object.put("terms", true);
        object.put("optional_terms", ["TTC005"]);
        object.put("service_terms", true);
        object.put("service_optional_terms", ["TTC005"]);

        return object.toString();
    }

    // 점유 인증 요청 API REQUEST BODY 설정
    private String createRequestPossessionBody(def sessionKey) {
        JSONObject object = new JSONObject();
        object.put("session_key", sessionKey);
        object.put("type", POSSESSION_TYPE);
        object.put("use_type", ACCOUNT_REGISTER_USE_TYPE);
        object.put("country_number", COUNTRY_NUMBER);
        object.put("email", getCurrentEmailId());

        return object.toString();
    }

    // 점유 인증 인증 API REQUEST BODY 설정
    private String createVerifyPossessionBody(def sessionKey, def sendCode) {
        JSONObject object = new JSONObject();
        object.put("session_key", sessionKey);
        object.put("type", POSSESSION_TYPE);
        object.put("use_type", ACCOUNT_REGISTER_USE_TYPE);
        object.put("code", sendCode)
        object.put("email", getCurrentEmailId());

        return object.toString();
    }

    // ID 사용 가능 여부 확인 API REQUEST BODY 설정
    private String createUsableIdBody(def sessionKey) {
        JSONObject object = new JSONObject();
        object.put("session_key", sessionKey);
        object.put("country_number", COUNTRY_NUMBER);
        object.put("id_type", ID_TYPE);
        object.put("email", getRegistrationEmailId());

        return object.toString();
    }

    // 회원 가입 API REQUEST BODY 설정
    private String createRegistrationBody(def sessionKey) {
        JSONObject object = new JSONObject();
        object.put("session_key", sessionKey);
        object.put("id_type", ID_TYPE);
        object.put("password", PASSWORD);
        object.put("nickname", getNickname());
        object.put("birth", "1993-01-25");
        object.put("country_number", COUNTRY_NUMBER);
        object.put("email", getCurrentEmailId());

        return object.toString();
    }

    // 로그인 API REQUEST BODY 설정
    private String createLoginBody() {
        JSONObject object = new JSONObject();
        object.put("client_id", PORTAL_X_CLIENT_ID);
        object.put("client_secret", PORTAL_X_ClIENT_SECRET);
        object.put("id", getCurrentEmailId());
        object.put("password", PASSWORD);
        object.put("redirect_uri", SERVICE_REDIRECT_URL);
        object.put("response_type", "code");
        object.put("captcha_response", "");

        return object.toString();
    }

    // 토큰 생성 API REQUEST BODY 설정
    private String createTokenCreateBody(def authCode) {
        JSONObject object = new JSONObject();
        object.put("client_id", PORTAL_X_CLIENT_ID);
        object.put("client_secret", PORTAL_X_ClIENT_SECRET);
        object.put("code", authCode);
        object.put("grant_type", "authorization_code");

        return object.toString();
    }

    // 서비스 별 회원 유형 선택 API REQUEST BODY 설정
    private String createServiceMemberTypeBody(def sessionKey) {
        JSONObject object = new JSONObject()
        object.put("session_key", sessionKey)
        // object.put("serviceID", "skygo")
        object.put("type", "MST013")

        return object.toString();
    }

    // 서비스 등록 API REQUEST BODY 설정
    private String createServiceRegistrationBody(def sessionKey) {
        JSONObject object = new JSONObject()
        JSONObject profile = new JSONObject()
        profile.put("name", getPersonName())
        profile.put("grade", "SGT001")
        profile.put("last_school", 1)
        profile.put("last_school_name", "서울고등학교")
        profile.put("nickname", getCurrentNickname())
        profile.put("parent_phone", "01056785678")
        profile.put("school_type", "SFT001")
        profile.put("high_school_field", "HST001")
        profile.put("university_field_target", ["UFT001"])
        profile.put("university_screening_target", ["USC001"])

        // object.put("serviceId", "skygo")
        object.put("session_key", sessionKey)
        object.put("profile", profile)

        return object.toString()
    }

    // MEMBER @RequestClientIp, @RequestClientId-PORTAL header
    private void changeCIdCIpPORTALHeader() {
        headers = [
                new NVPair("Content-Type", CONTENT_TYPE),
                new NVPair("X-Client-Id", PORTAL_X_CLIENT_ID),
                new NVPair("X-Forwarded-For", X_FORWARDED_FOR),
                new NVPair("X-Device-Country", X_DEVICE_COUNTRY),
                new NVPair("X-Os-Code", X_OS_CODE)
        ]
    }

    // MEMBER @RequestClientIp, @RequestClientId-SERVICE header
    private void changeCIdCIpSERVICEHeader() {
        headers = [
                new NVPair("Content-Type", CONTENT_TYPE),
                new NVPair("X-Client-Id", SERVICE_X_CLIENT_ID),
                new NVPair("X-Forwarded-For", X_FORWARDED_FOR),
                new NVPair("X-Device-Country", X_DEVICE_COUNTRY)
        ]
    }

    // MEMBER @RequestClientIp, @RequestClientId, @RequestUserInfo의 header
    private void changeCIdCIpUIHeader(def accessToken) {
        headers = [
                new NVPair("Content-Type", CONTENT_TYPE),
                new NVPair("X-Client-Id", PORTAL_X_CLIENT_ID),
                new NVPair("X-Forwarded-For", X_FORWARDED_FOR),
                new NVPair("X-Device-Country", X_DEVICE_COUNTRY),
                new NVPair("Authorization", accessToken)
        ]
    }

    // AUTH @RequestClientId, @RequesClientSecret, @RequestClientIp, @RequestDeviceInfo의 header
    private void changeCIdCSCIpDIPORTALHeader() {
        headers = [
                new NVPair("Content-Type", CONTENT_TYPE),
                new NVPair("X-Client-Id", PORTAL_X_CLIENT_ID),
                new NVPair("X-Client-Secret", PORTAL_X_ClIENT_SECRET),
                new NVPair("X-Forwarded-For", X_FORWARDED_FOR),
                new NVPair("X-Device-Country", X_DEVICE_COUNTRY),
                new NVPair("X-Os-Code", X_OS_CODE)
        ]
    }

    // AUTH @RequestClientIp, @RequestDeviceInfo의 header
    private void changeCIpDIHeader() {
        headers = [
                new NVPair("Content-Type", CONTENT_TYPE),
                new NVPair("X-Forwarded-For", X_FORWARDED_FOR),
                new NVPair("X-Device-Country", X_DEVICE_COUNTRY),
                new NVPair("X-Os-Code", X_OS_CODE)
        ]
    }

    // 서비스 프로필 기입 용 이름 반환
    private String getPersonName() {
        String str = ""
        def character = ["가", "나", "다", "라", "마", "바", "사", "아", "자", "차", "카", "타", "파", "하"]
        Random random = new Random()

        for (i in 0..<3) {
            str += character.get(random.nextInt(14))
        }
        return str
    }

    // 회원 가입 용 닉네임 반환
    private String getNickname() {
        String str = "가" + ++NICKNAME_NUM
        return str
    }

    // 현재 닉네임 반환
    private String getCurrentNickname() {
        String str = "가" + NICKNAME_NUM
        return str
    }
}

