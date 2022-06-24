package kr.co.ilinker.perf.opentalk

import static net.grinder.script.Grinder.grinder
import static org.junit.Assert.*

import org.json.JSONObject
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.LoggerFactory

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

import ch.qos.logback.classic.Level

@RunWith(GrinderRunner)
class MemberSignUp {
    // grinder.logger.warn("agent num: " + grinder.getAgentNumber() + ", process num: " + grinder.getProcessNumber() + ", thread num: " + grinder.getThreadNumber() + ", number test: " + numberTest + ", header :" + headers)
    // 공통
    public static GTest test
    public static Random random
    public static String mode
    public static JsonSlurper jsonSlurper
    public static HTTPRequest request
    public static int agentNum
    private static final int SUCCESS_CODE = 21000

    // URL 정보
    private static final String BASE_DOMAIN = "http://perf-api.linker.ac"
    private static final String MEMBER_BASE_CONTEXT = "/etoos/members"
    private static final String COMMON_CONTEXT = "/v1/common"
    private static final String SIGNUP_CONTEXT = "/v1/signup"
    private static final String SERVICE_CONTEXT = "/v1/service"
    private static final String AUTH_BASE_CONTEXT = "/etoos/auth/external/v1"

    // 테스트 용 계정 정보
    private static final String EMAIL_FORM = "@test.com"
    private static final String PASSWORD = "abcd1234"

    // API 헤더 & 파라미터 설정
    private static final String X_FORWARDED_FOR = "13.124.32.110"
    private static final String X_DEVICE_COUNTRY = "ko"
    private static final String COUNTRY_NUMBER = "+82"
    private static final String CONTENT_TYPE = "application/json"
    private static final String AUTHORIZATION= "Authorization"
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

    // CASE 정의
    private static final String SIGN_UP_TERMS = "SIGN_UP_TERMS"
    private static final String USABLE_ID = "USABLE_ID"
    private static final String REGISTRATION = "REGISTRATION"
    private static final String REGISTRATION_MEMBER_FULL = "REGISTRATION_MEMBER"
    private static final String SERVICE_MEMBER_TYPE = "SERVICE_MEMBER_TYPE"
    private static final String SERVICE_REGISTRATION = "SERVICE_REGISTRATION"
    private static final String REGISTRATION_SERVICE = "REGISTRATION_SERVICE"

    public int processNum
    public int threadNum
    public int numberTest = 1100000
    public HTTPRequest requestLocal
    public NVPair[] headers = []
    public NVPair[] params = []
    public Cookie[] cookies = []

    @BeforeProcess
    public static void beforeProcess() {
        mode = GrinderUtils.getParam("HEALTHCHECK").toUpperCase().trim()
        HTTPPluginControl.getConnectionDefaults().timeout = 6000
        test = new GTest(1, "Member-SingUp")
        request = new HTTPRequest()
        random = new Random()
        jsonSlurper = new JsonSlurper()
        // 0부터 시작
        agentNum = grinder.getAgentNumber()

        grinder.logger.info("before process.")
    }

    @BeforeThread
    public void beforeThread() {
        test.record(this, "test")
        LoggerFactory.getLogger("worker").setLevel(Level.WARN)
        grinder.statistics.delayReports = true
        requestLocal = new HTTPRequest()
        processNum = grinder.getProcessNumber()
        threadNum = grinder.getThreadNumber()

        grinder.logger.info("before thread.")
    }

    @Before
    public void before() {
        def accessToken
        def sessionKey
        def sendCode

        cookies.each {
            CookieModule.addCookie(it, HTTPPluginControl.getThreadHTTPClientContext())
        }

        if(mode == SIGN_UP_TERMS) {
            sessionKey = requestSessionKey(ACCOUNT_REGISTER_USE_TYPE)
            headers += [new NVPair("Session-Key", sessionKey)]
        }

        if(mode == USABLE_ID) {
            numberTest++
            sessionKey = requestSessionKey(ACCOUNT_REGISTER_USE_TYPE)
            sessionKey = signUpTerms(sessionKey)
            headers += [new NVPair("Session-Key", sessionKey)]
        }

        if(mode == REGISTRATION) {
            numberTest++
            sessionKey = requestSessionKey(ACCOUNT_REGISTER_USE_TYPE)
            sessionKey = signUpTerms(sessionKey)
            usableId(sessionKey)
            sendCode = requestPossession(sessionKey)
            sessionKey = verifyPossession(sessionKey, sendCode)
            headers += [new NVPair("Session-Key", sessionKey)]
        }

        if(mode == SERVICE_MEMBER_TYPE) {
            numberTest++
            accessToken = memberToken()
            sessionKey = requestSessionKey(SERVICE_REGISTER_USE_TYPE)

            headers += [new NVPair(AUTHORIZATION, accessToken)]
            headers += [new NVPair("Session-Key", sessionKey)]
        }

        if(mode == SERVICE_REGISTRATION) {
            numberTest++
            accessToken = memberToken()
            sessionKey = requestSessionKey(SERVICE_REGISTER_USE_TYPE)
            sessionKey = serviceMemberType(sessionKey, accessToken)

            headers += [new NVPair(AUTHORIZATION, accessToken)]
            headers += [new NVPair("Session-Key", sessionKey)]
        }

        if(mode == REGISTRATION_SERVICE) {
            numberTest++
            accessToken = memberToken()

            headers += [new NVPair(AUTHORIZATION, accessToken)]
        }

        grinder.logger.info("before test. init cookies")
    }

    @Test
    public void test() {
        HTTPResponse result
        def accessToken
        def sessionKey
        def sendCode

        switch (mode) {
        // 약관 리스트 요청 API
            case "NEED_TERMS":
                headers = changeCIdCIpPORTALHeader()
                requestLocal.setHeaders(headers)
                result = requestLocal.GET(BASE_DOMAIN + MEMBER_BASE_CONTEXT + COMMON_CONTEXT + "/need/terms")
                break

                // 서비스 별 필요한 프로필 리스트 요청 API
            case "NEED_PROFILE":
                headers = changeCIdCIpSERVICEHeader()
                requestLocal.setHeaders(headers)
                result = requestLocal.GET(BASE_DOMAIN + MEMBER_BASE_CONTEXT + COMMON_CONTEXT + "/need/profile?memberServiceType=MST013")
                break

                // 세션 키 생성 API
            case "SESSION_KEY":
                headers = changeCIdCIpPORTALHeader()
                requestLocal.setHeaders(headers)
                result = requestLocal.POST(BASE_DOMAIN + MEMBER_BASE_CONTEXT + COMMON_CONTEXT + "/request/sessionkey", createSessionKeyBody(ACCOUNT_REGISTER_USE_TYPE).getBytes())
                break

                // 약관 동의 API
            case SIGN_UP_TERMS:
                sessionKey = getHeaderValue("Session-Key")
                headers = changeCIdCIpSERVICEHeader()
                requestLocal.setHeaders(headers)
                result = requestLocal.POST(BASE_DOMAIN + MEMBER_BASE_CONTEXT + SIGNUP_CONTEXT + "/terms", createTermsAgreeBody(sessionKey).getBytes())
                break

                // 회원가입 회원 정보 입력 API
            case USABLE_ID:
                sessionKey = getHeaderValue("Session-Key")
                result = usableId(sessionKey)
                break

                // 회원 가입 API
            case REGISTRATION:
                sessionKey = getHeaderValue("Session-Key")
                result = registrationMember(sessionKey)
                break

                // 통합 회원 가입 전체 프로세스
            case REGISTRATION_MEMBER_FULL:
                numberTest++
                sessionKey = requestSessionKey(ACCOUNT_REGISTER_USE_TYPE)
                sessionKey = signUpTerms(sessionKey)
                usableId(sessionKey)
                sendCode = requestPossession(sessionKey)
                //result = verifyPossession(sessionKey, sendCode)
                sessionKey = verifyPossession(sessionKey, sendCode)
                result = registrationMember(sessionKey)
                break

                // 서비스 회원 유형 선택 API
            case SERVICE_MEMBER_TYPE:
                sessionKey = getHeaderValue("Session-Key")
                accessToken = getHeaderValue(AUTHORIZATION)
                grinder.logger.info("session key" + sessionKey + ", " + accessToken)

                headers = changeCIdCIpUIHeader(accessToken)
                requestLocal.setHeaders(headers)
                result = requestLocal.POST(BASE_DOMAIN + MEMBER_BASE_CONTEXT + SERVICE_CONTEXT + "/skygo/save/type", createServiceMemberTypeBody(sessionKey).getBytes())
                break

                // 서비스 등록 API
            case SERVICE_REGISTRATION:
                sessionKey = getHeaderValue("Session-Key")
                accessToken = getHeaderValue(AUTHORIZATION)

                headers = changeCIdCIpUIHeader(accessToken)
                requestLocal.setHeaders(headers)
                result = requestLocal.POST(BASE_DOMAIN + MEMBER_BASE_CONTEXT + SERVICE_CONTEXT + "/skygo/registration", createServiceRegistrationBody(sessionKey).getBytes())
                break

                // 서비스 가입 전체 프로세스
            case REGISTRATION_SERVICE:
                accessToken = getHeaderValue(AUTHORIZATION)

                sessionKey = requestSessionKey(SERVICE_REGISTER_USE_TYPE)
                sessionKey = serviceMemberType(sessionKey, accessToken)
                result = registrationService(sessionKey, accessToken)
                break

            default:
                result = requestLocal.GET(BASE_DOMAIN + "/healthCheck/_check", params)
                break
        }

        grinder.logger.debug("result :" + result.text)
        def res = jsonSlurper.parseText(result.text)

        // 결과 검증
        if (result.statusCode == 301 || result.statusCode == 302) {
            grinder.logger.warn('Warning. The response may not be correct. The response code was {}.', result.statusCode)
        } else {
            assertTrue(result.statusCode == 200)
            assertTrue(res.getAt("code") == SUCCESS_CODE)
        }
    }

    /* ---------------------------------
    HEADER 설정
    --------------------------------- */

    // MEMBER @RequestClientIp, @RequestClientId-PORTAL header
    private def changeCIdCIpPORTALHeader() {
        return [
                new NVPair("Content-Type", CONTENT_TYPE),
                new NVPair("X-Client-Id", PORTAL_X_CLIENT_ID),
                new NVPair("X-Forwarded-For", X_FORWARDED_FOR),
                new NVPair("X-Device-Country", X_DEVICE_COUNTRY),
                new NVPair("X-Os-Code", X_OS_CODE)
        ]
    }

    // MEMBER @RequestClientIp, @RequestClientId-SERVICE header
    private def changeCIdCIpSERVICEHeader() {
        return [
                new NVPair("Content-Type", CONTENT_TYPE),
                new NVPair("X-Client-Id", SERVICE_X_CLIENT_ID),
                new NVPair("X-Forwarded-For", X_FORWARDED_FOR),
                new NVPair("X-Device-Country", X_DEVICE_COUNTRY)
        ]
    }

    // MEMBER @RequestClientIp, @RequestClientId-SERVICE, @RequestDevice header
    private def changeCIdCIpDevSERVICEHeader() {
        return [
                new NVPair("Content-Type", CONTENT_TYPE),
                new NVPair("X-Client-Id", SERVICE_X_CLIENT_ID),
                new NVPair("X-Forwarded-For", X_FORWARDED_FOR),
                new NVPair("X-Device-Country", X_DEVICE_COUNTRY),
                new NVPair("X-Os-Code", "OSC001"),
        ]
    }

    // MEMBER @RequestClientIp, @RequestClientId, @RequestUserInfo의 header
    private def changeCIdCIpUIHeader(def accessToken) {
        return [
                new NVPair("Content-Type", CONTENT_TYPE),
                new NVPair("X-Client-Id", PORTAL_X_CLIENT_ID),
                new NVPair("X-Forwarded-For", X_FORWARDED_FOR),
                new NVPair("X-Device-Country", X_DEVICE_COUNTRY),
                new NVPair("Authorization", accessToken)
        ]
    }

    // AUTH @RequestClientId, @RequesClientSecret, @RequestClientIp, @RequestDeviceInfo의 header
    private def changeCIdCSCIpDIPORTALHeader() {
        return [
                new NVPair("Content-Type", CONTENT_TYPE),
                new NVPair("X-Client-Id", PORTAL_X_CLIENT_ID),
                new NVPair("X-Client-Secret", PORTAL_X_ClIENT_SECRET),
                new NVPair("X-Forwarded-For", X_FORWARDED_FOR),
                new NVPair("X-Device-Country", X_DEVICE_COUNTRY),
                new NVPair("X-Os-Code", X_OS_CODE)
        ]
    }

    // AUTH @RequestClientIp, @RequestDeviceInfo의 header
    private def changeCIpDIHeader() {
        return [
                new NVPair("Content-Type", CONTENT_TYPE),
                new NVPair("X-Forwarded-For", X_FORWARDED_FOR),
                new NVPair("X-Device-Country", X_DEVICE_COUNTRY),
                new NVPair("X-Os-Code", X_OS_CODE)
        ]
    }

    /* ---------------------------------
    REQUEST BODY 설정
    --------------------------------- */

    // 세션 키 생성 API REQUEST BODY 설정
    private String createSessionKeyBody(def useType) {
        JSONObject object = new JSONObject()
        object.put("use_type", useType)

        return object.toString()
    }

    // 약관 동의 API REQUEST BODY 설정
    private String createTermsAgreeBody(def sessionKey) {
        JSONObject object = new JSONObject()
        object.put("session_key", sessionKey)
        object.put("terms", true)
        object.put("optional_terms", ["TTC005"])
        object.put("service_terms", true)
        object.put("service_optional_terms", ["TTC005"])

        return object.toString()
    }

    // 점유 인증 요청 API REQUEST BODY 설정
    private String createRequestPossessionBody(def sessionKey) {
        JSONObject object = new JSONObject()
        object.put("session_key", sessionKey)
        object.put("type", POSSESSION_TYPE)
        object.put("use_type", ACCOUNT_REGISTER_USE_TYPE)
        object.put("country_number", COUNTRY_NUMBER)
        object.put("email", getRegistrationEmailId())

        return object.toString()
    }

    // 점유 인증 인증 API REQUEST BODY 설정
    private String createVerifyPossessionBody(def sessionKey, def sendCode) {
        JSONObject object = new JSONObject()
        object.put("session_key", sessionKey)
        object.put("type", POSSESSION_TYPE)
        object.put("use_type", ACCOUNT_REGISTER_USE_TYPE)
        object.put("code", sendCode)
        object.put("email", getRegistrationEmailId())

        return object.toString()
    }

    // ID 사용 가능 여부 확인 API REQUEST BODY 설정
    private String createUsableIdBody(def sessionKey) {
        JSONObject object = new JSONObject()
        object.put("session_key", sessionKey)
        object.put("country_number", COUNTRY_NUMBER)
        object.put("id_type", ID_TYPE)
        object.put("email", getRegistrationEmailId())

        return object.toString()
    }

    // 회원 가입 API REQUEST BODY 설정
    private String createRegistrationBody(def sessionKey) {
        JSONObject object = new JSONObject()
        object.put("session_key", sessionKey)
        object.put("id_type", ID_TYPE)
        object.put("password", PASSWORD)
        object.put("nickname", getNickname())
        object.put("birth", "1993-01-25")
        object.put("country_number", COUNTRY_NUMBER)
        object.put("email", getRegistrationEmailId())

        return object.toString()
    }

    // 로그인 API REQUEST BODY 설정
    private String createLoginBody() {
        JSONObject object = new JSONObject()
        object.put("client_id", PORTAL_X_CLIENT_ID)
        object.put("client_secret", PORTAL_X_ClIENT_SECRET)
        object.put("id", getRegistrationEmailId())
        object.put("password", PASSWORD)
        object.put("redirect_uri", SERVICE_REDIRECT_URL)
        object.put("response_type", "code")
        object.put("captcha_response", "")

        return object.toString()
    }

    // 토큰 생성 API REQUEST BODY 설정
    private String createTokenCreateBody(def authCode) {
        JSONObject object = new JSONObject()
        object.put("client_id", PORTAL_X_CLIENT_ID)
        object.put("client_secret", PORTAL_X_ClIENT_SECRET)
        object.put("code", authCode)
        object.put("grant_type", "authorization_code")

        return object.toString()
    }

    // 서비스 별 회원 유형 선택 API REQUEST BODY 설정
    private String createServiceMemberTypeBody(def sessionKey) {
        JSONObject object = new JSONObject()
        object.put("session_key", sessionKey)
        // object.put("serviceID", "skygo")
        object.put("type", "MST013")

        return object.toString()
    }

    // 서비스 등록 API REQUEST BODY 설정
    private String createServiceRegistrationBody(def sessionKey) {
        JSONObject object = new JSONObject()
        JSONObject profile = new JSONObject()
        profile.put("name", getPersonName())
        profile.put("grade", "SGT001")
        profile.put("last_school", 1)
        profile.put("last_school_name", "서울고등학교")
        profile.put("nickname", getNickname())
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

    /* ---------------------------------
    요청 함수
    --------------------------------- */

    private def requestSessionKey(def useType) {
        headers = changeCIdCIpPORTALHeader()
        requestLocal.setHeaders(headers)
        def result = requestLocal.POST(BASE_DOMAIN + MEMBER_BASE_CONTEXT + COMMON_CONTEXT + "/request/sessionkey", createSessionKeyBody(useType).getBytes())
        grinder.logger.debug("session key result :" + result.text)
        //return result

        def resultJson = jsonSlurper.parseText(result.text)
        return resultJson.getAt("value").getAt("session_key")
    }

    private def signUpTerms(def sessionKey) {
        headers = changeCIdCIpSERVICEHeader()
        requestLocal.setHeaders(headers)
        def result = requestLocal.POST(BASE_DOMAIN + MEMBER_BASE_CONTEXT + SIGNUP_CONTEXT + "/terms", createTermsAgreeBody(sessionKey).getBytes())
        //grinder.logger.debug("sign up result :" + result.text)

        def resultJson = jsonSlurper.parseText(result.text)
        return resultJson.getAt("value").getAt("session_key")
    }

    private def usableId(def sessionKey) {
        headers = changeCIdCIpSERVICEHeader()
        requestLocal.setHeaders(headers)
        def result = requestLocal.POST(BASE_DOMAIN + MEMBER_BASE_CONTEXT + SIGNUP_CONTEXT + "/usable/id", createUsableIdBody(sessionKey).getBytes())
        //grinder.logger.debug("usable id result :" + result.text)

        def resultJson = jsonSlurper.parseText(result.text)
        //return resultJson.getAt("value")
        return result
    }

    private def requestPossession(def sessionKey) {
        headers = changeCIdCIpSERVICEHeader()
        requestLocal.setHeaders(headers)
        def result = requestLocal.POST(BASE_DOMAIN + MEMBER_BASE_CONTEXT + COMMON_CONTEXT + "/request/possession", createRequestPossessionBody(sessionKey).getBytes())
        //grinder.logger.debug("auth code result :" + result.text)

        def resultJson = jsonSlurper.parseText(result.text)
        return resultJson.getAt("value").getAt("auth_code")
    }

    private def verifyPossession(def sessionKey, def sendCode) {
        headers = changeCIdCIpSERVICEHeader()
        requestLocal.setHeaders(headers)
        def result = requestLocal.POST(BASE_DOMAIN + MEMBER_BASE_CONTEXT + COMMON_CONTEXT + "/verify/possession", createVerifyPossessionBody(sessionKey, sendCode).getBytes())
        //grinder.logger.debug("verify result :" + result.text)

        def verifyPossessionJson = jsonSlurper.parseText(result.text)
        //return result
        return verifyPossessionJson.getAt("value").getAt("session_key")
    }

    private def registrationMember(def sessionKey) {
        headers = changeCIdCIpDevSERVICEHeader()
        requestLocal.setHeaders(headers)
        def result = requestLocal.POST(BASE_DOMAIN + MEMBER_BASE_CONTEXT + SIGNUP_CONTEXT + "/registration", createRegistrationBody(sessionKey).getBytes())
        return result
    }

    // 통합 회원 토큰 발행
    private def memberToken() {
        // 로그인 API
        headers = changeCIdCSCIpDIPORTALHeader()
        requestLocal.setHeaders(headers)
        def result = requestLocal.POST(BASE_DOMAIN + AUTH_BASE_CONTEXT + "/signin/account", createLoginBody().getBytes())
        grinder.logger.debug("auth login result :" + result.text)

        def resultJson = jsonSlurper.parseText(result.text)
        def authCode = resultJson.getAt("value").getAt("code")

        // 토큰 생성 API
        headers = changeCIdCSCIpDIPORTALHeader()
        requestLocal.setHeaders(headers)
        result = requestLocal.POST(BASE_DOMAIN + AUTH_BASE_CONTEXT + "/token/create", createTokenCreateBody(authCode).getBytes())
        grinder.logger.debug("create token result :" + result.text)

        resultJson = jsonSlurper.parseText(result.text)
        return resultJson.getAt("value").getAt("access_token")
    }

    // 서비스별 회원 유형 선택 API
    private def serviceMemberType(def sessionKey, def accessToken) {
        headers = changeCIdCIpUIHeader(accessToken)
        requestLocal.setHeaders(headers)
        def result = requestLocal.POST(BASE_DOMAIN + MEMBER_BASE_CONTEXT + SERVICE_CONTEXT + "/skygo/save/type", createServiceMemberTypeBody(sessionKey).getBytes())

        def resultJson = jsonSlurper.parseText(result.text)
        return resultJson.getAt("value").getAt("session_key")
    }

    // 서비스 등록 API
    private def registrationService(def sessionKey, def accessToken) {
        headers = changeCIdCIpUIHeader(accessToken)
        requestLocal.setHeaders(headers)
        def result = requestLocal.POST(BASE_DOMAIN + MEMBER_BASE_CONTEXT + SERVICE_CONTEXT + "/skygo/registration", createServiceRegistrationBody(sessionKey).getBytes())
        //grinder.logger.debug("service registration result :" + result.text)
        //def resultJson = jsonSlurper.parseText(result.text)

        return result
    }

    /* ---------------------------------
    UTIL 함수
    --------------------------------- */

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
        String id = 'test-' + agentNum + processNum + threadNum + "-" + numberTest + EMAIL_FORM
        String str = "nick-" + id
        return str
    }

    private String getRegistrationEmailId() {
        String id = 'test-' + String.valueOf(agentNum) + String.valueOf(processNum) + String.valueOf(threadNum) + "-" + String.valueOf(numberTest) + EMAIL_FORM
        //grinder.logger.debug(grinder.getAgentNumber() + ", " + grinder.getProcessNumber() +", " + grinder.getThreadNumber() + ", " + numberTest + ", reg id: " + id)
        return id
    }

    private String getHeaderValue(String name) {
        for (NVPair nvPair : headers) {
            if(nvPair.getName() == name) {
                return nvPair.getValue()
            }
        }
    }
}
