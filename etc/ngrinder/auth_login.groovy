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
class MemberLogin {

    public static GTest memberAuthCodeTest
    public static GTest memberAccessTokenTest
    public static GTest serviceAuthCodeTest
    public static GTest serviceAcessTokenTest
    public static GTest loginTest

    public static int agentNum
    public static HTTPRequest request
    public static Random random
    public static String mode
    public static JsonSlurper jsonSlurper

    // URL 정보
    private static final String BASE_DOMAIN = "https://perf-api.linker.ac"
    private static final String AUTH_BASE_CONTEXT = "/etoos/auth/external/v1"

    // 테스트 용 계정 정보
    private static final String EMAIL_FORM = "@test.com"
    private static final String PASSWORD = "abcd1234"

    // API 헤더 & 파라미터 설정
    private static final String X_FORWARDED_FOR = "13.124.32.110"
    private static final String X_DEVICE_COUNTRY = "ko"
    private static final String COUNTRY_NUMBER = "+82"
    private static final String CONTENT_TYPE = "application/json"
    private static final String X_OS_CODE = "OSC001"
    private static final String PORTAL_X_CLIENT_ID = "eee3e0c97418e173f8a5f2e26eb85381"
    private static final String PORTAL_X_ClIENT_SECRET = "a9ffa78ffabb25f46ad55cfe77665076cd7932ea74b0b9a21389b85d60d18460"
    private static final String PORTAL_X_REDIRECT_URL = "https://my.linker.ac"
    private static final String SERVICE_X_CLIENT_ID = "c1adf8c0ce66d154345f20fc310e5a5c"
    private static final String SERVICE_X_ClIENT_SECRET = "3c1012923b265b2d0ee961dfabc901b8f469234de221c4ca07153fb648f3e669"
    private static final String SERVICE_REDIRECT_URL = "https://perf-front.platb.ai"

    // 검증 코드
    private static final int AUTH_SUCCESS_CODE = 0

    public int processNum
    public int threadNum
    public int numberTest = 1000400
    public int randomId;
    public HTTPRequest requestLocal
    public NVPair[] headers = []
    public NVPair[] params = []
    public Cookie[] cookies = []

    @BeforeProcess
    public static void beforeProcess() {
        mode = GrinderUtils.getParam("HEALTHCHECK").toUpperCase().trim()
        HTTPPluginControl.getConnectionDefaults().timeout = 6000
        // test = new GTest(1, "Member-Login")
        /*memberAuthCodeTest = new GTest(1, "Member AuthCode")
        memberAccessTokenTest = new GTest(2, "Member AccessToken")
        serviceAuthCodeTest = new GTest(3, "Service AuthCode")
        serviceAcessTokenTest = new GTest(4, "Service AccessToken")*/
        loginTest = new GTest(5, "login test")

        agentNum = grinder.getAgentNumber()
        request = new HTTPRequest()
        random = new Random()
        jsonSlurper = new JsonSlurper()
        grinder.logger.info("before process.")
    }

    @BeforeThread
    public void beforeThread() {
        /*memberAuthCodeTest.record(this, "memberAC")
        memberAccessTokenTest.record(this, "memberAT")
        serviceAuthCodeTest.record(this, "serviceAC")
        serviceAcessTokenTest.record(this, "serviceAT")*/
        loginTest.record(this, "test")

        requestLocal = new HTTPRequest()
        processNum = grinder.getProcessNumber()
        threadNum = grinder.getThreadNumber()

        grinder.statistics.delayReports = true
        grinder.logger.info("before thread.")
    }

    @Before
    public void before() {
        cookies.each { CookieModule.addCookie(it, HTTPPluginControl.getThreadHTTPClientContext()) }
        randomId = random.nextInt(5000) + 151
        grinder.logger.info("before thread. init cookies")
    }

    @Test
    public void test() {
        HTTPResponse result
        def authCode
        def accessToken
        def serviceAuthCode

        switch (mode) {
        // 로그인 API
        // 순차적으로 5000명 채움
            case 'LOGIN':
                // 통합 회원 AT 토큰 발행
                // 통합 회원 인증 코드 발급 API
                headers = changeCIdCSCIpDIPORTALHeader()
                requestLocal.setHeaders(headers)
                authCode = memberAC()

                // 통합 회원 AT 토큰 발급 API
                headers = changeCIpDIHeader()
                requestLocal.setHeaders(headers)
                result = memberAT(authCode)

                // 서비스 AT 토큰 발행
                // 서비스 인증 코드 발급 API
                /*  headers = changeCIdCSCIpDIAuthHeader(accessToken)
                  requestLocal.setHeaders(headers)
                  serviceAuthCode = serviceAC()

                  // 서비스 인증 코드로 서비스 AT 토큰 발급 API
                  headers = changeCIdCIpDIAuthHeader(accessToken)
                  requestLocal.setHeaders(headers)
                  result = serviceAT(serviceAuthCode)*/
                break

                // 여러 쓰레드로 레디스 채움
            case 'FILL_REDIS':
                numberTest++
                // 통합 회원 인증 코드 발급 API
                headers = changeCIdCSCIpDIPORTALHeader()
                requestLocal.setHeaders(headers)
                authCode = concurrentMemberAC()

                // 통합 회원 AT 토큰 발급 API
                headers = changeCIpDIHeader()
                requestLocal.setHeaders(headers)
                accessToken = memberAT(authCode)

                // 서비스 AT 토큰 발행
                // 서비스 인증 코드 발급 API
                headers = changeCIdCSCIpDIAuthHeader(accessToken)
                requestLocal.setHeaders(headers)
                serviceAuthCode = concurrentServiceAC()

                // 서비스 인증 코드로 서비스 AT 토큰 발급 API
                headers = changeCIdCIpDIAuthHeader(accessToken)
                requestLocal.setHeaders(headers)
                result = serviceAT(serviceAuthCode)
                break

            default:
                result = request.GET(BASE_DOMAIN + "/healthCheck/_check", params)
                break
        }

        def res
        res = jsonSlurper.parseText(result.text)

        // 결과 검증
        if (result.statusCode == 301 || result.statusCode == 302) {
            grinder.logger.warn('Warning. The response may not be correct. The response code was {}.', result.statusCode)
        } else {
            assertTrue(result.statusCode == 200)

            if (mode == 'LOGIN') {
                assertTrue(res.getAt("code") == AUTH_SUCCESS_CODE)
            }
        }
    }

    /* ---------------------------------
    HEADER 설정
    --------------------------------- */

    // AUTH @RequestClientId, @RequestClientIp, @RequestDeviceInfo, @RequestAuthorization의 header
    private def changeCIdCIpDIAuthHeader(def memberAccessToken) {
        return [
                new NVPair("Content-Type", CONTENT_TYPE),
                new NVPair("X-Client-Id", PORTAL_X_CLIENT_ID),
                new NVPair("X-Client-Secret", PORTAL_X_ClIENT_SECRET),
                new NVPair("X-Forwarded-For", X_FORWARDED_FOR),
                new NVPair("X-Device-Country", X_DEVICE_COUNTRY),
                new NVPair("X-Os-Code", X_OS_CODE),
                new NVPair("Authorization", 'bearer ' + memberAccessToken)
        ]
    }

    // AUTH @RequestClientId, @RequesClientSecret, @RequestClientIp, @RequestDeviceInfo, @RequestAuthorization의 header
    private def changeCIdCSCIpDIAuthHeader(def memberAccessToken) {
        return [
                new NVPair("Content-Type", CONTENT_TYPE),
                new NVPair("X-Client-Id", PORTAL_X_CLIENT_ID),
                new NVPair("X-Client-Secret", PORTAL_X_ClIENT_SECRET),
                new NVPair("X-Forwarded-For", X_FORWARDED_FOR),
                new NVPair("X-Device-Country", X_DEVICE_COUNTRY),
                new NVPair("X-Os-Code", X_OS_CODE),
                new NVPair("Authorization", 'bearer ' + memberAccessToken)
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

    /* ---------------------------------
    REQUEST BODY 설정
    --------------------------------- */

    // 로그인 API REQUEST BODY 설정
    private String createLoginBody() {
        JSONObject object = new JSONObject()
        object.put("client_id", SERVICE_X_CLIENT_ID)
        object.put("client_secret", SERVICE_X_ClIENT_SECRET)
        object.put("id", getLoginEmailId())
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

    // 서비스 인증 코드 생성 API REQUEST BODY 설정
    private String createServiceAuthCodeBody() {
        JSONObject object = new JSONObject()
        object.put("client_id", SERVICE_X_CLIENT_ID)
        object.put("client_secret", SERVICE_X_ClIENT_SECRET)
        object.put("id", getLoginEmailId())
        object.put("password", PASSWORD)
        object.put("redirect_uri", SERVICE_REDIRECT_URL)
        object.put("response_type", "code")
        object.put("captcha_response", "")

        return object.toString()
    }

    // 서비스 인증 토큰 생성 API REQUEST BODY 설정
    private String createServiceTokenBody(def serviceAuthCode) {
        JSONObject object = new JSONObject()
        object.put("client_id", SERVICE_X_CLIENT_ID)
        object.put("client_secret", SERVICE_X_ClIENT_SECRET)
        object.put("code", serviceAuthCode)
        object.put("grant_type", "authorization_code")

        return object.toString()
    }

    // 로그인 API REQUEST BODY 설정
    private String createConcurrentLoginBody() {
        JSONObject object = new JSONObject()
        object.put("client_id", SERVICE_X_CLIENT_ID)
        object.put("client_secret", SERVICE_X_ClIENT_SECRET)
        object.put("id", getConcurrentEmailId())
        object.put("password", PASSWORD)
        object.put("redirect_uri", SERVICE_REDIRECT_URL)
        object.put("response_type", "code")
        object.put("captcha_response", "")

        return object.toString()
    }

    // 서비스 인증 코드 생성 API REQUEST BODY 설정
    private String createConcurrentServiceAuthCodeBody() {
        JSONObject object = new JSONObject()
        object.put("client_id", SERVICE_X_CLIENT_ID)
        object.put("client_secret", SERVICE_X_ClIENT_SECRET)
        object.put("id", getConcurrentEmailId())
        object.put("password", PASSWORD)
        object.put("redirect_uri", SERVICE_REDIRECT_URL)
        object.put("response_type", "code")
        object.put("captcha_response", "")

        return object.toString()
    }

    /* ---------------------------------
   요청 함수
   --------------------------------- */

    private def memberAC() {
        def result = requestLocal.POST(BASE_DOMAIN + AUTH_BASE_CONTEXT + "/signin/account", createLoginBody().getBytes())
        def authCodeJson = jsonSlurper.parseText(result.text)
        def authCode = authCodeJson.getAt("value").getAt("code")
        //grinder.logger.info("member authcode :" + authCode)

        //return result
        return authCode
    }

    private def memberAT(def authCode) {
        def result = requestLocal.POST(BASE_DOMAIN + AUTH_BASE_CONTEXT + "/token/create", createTokenCreateBody(authCode).getBytes())
        def accessTokenJson = jsonSlurper.parseText(result.text)
        def accessToken = accessTokenJson.getAt("value").getAt("access_token")
        //grinder.logger.info("member accessToken :" + accessToken)

        //return accessToken
        return result
    }

    private def serviceAC() {
        def result = requestLocal.POST(BASE_DOMAIN + AUTH_BASE_CONTEXT + "/sso/signin/account", createServiceAuthCodeBody().getBytes())
        def serviceAuthCodeJson = jsonSlurper.parseText(result.text)
        def serviceAuthCode = serviceAuthCodeJson.getAt("value").getAt("code")
        //grinder.logger.info("service auth code :" + serviceAuthCode)

        return serviceAuthCode
    }

    private def serviceAT(def serviceAuthCode) {
        def result = requestLocal.POST(BASE_DOMAIN + AUTH_BASE_CONTEXT + "/sso/token/create", createServiceTokenBody(serviceAuthCode).getBytes())
        def serviceAccessTokenJson = jsonSlurper.parseText(result.text)
        def serviceAccessToken = serviceAccessTokenJson.getAt("value").getAt("access_token")
        //grinder.logger.info("service access token :" + serviceAccessToken)

        return result
    }

    private def concurrentMemberAC() {
        def result = requestLocal.POST(BASE_DOMAIN + AUTH_BASE_CONTEXT + "/signin/account", createConcurrentLoginBody().getBytes())
        def authCodeJson = jsonSlurper.parseText(result.text)
        def authCode = authCodeJson.getAt("value").getAt("code")
        //grinder.logger.info("member authcode :" + authCode)

        return authCode
    }

    private def concurrentServiceAC() {
        def result = requestLocal.POST(BASE_DOMAIN + AUTH_BASE_CONTEXT + "/sso/signin/account", createConcurrentServiceAuthCodeBody().getBytes())
        def serviceAuthCodeJson = jsonSlurper.parseText(result.text)
        def serviceAuthCode = serviceAuthCodeJson.getAt("value").getAt("code")
        //grinder.logger.info("service auth code :" + serviceAuthCode)

        return serviceAuthCode
    }

    /* ---------------------------------
    UTIL 함수
    --------------------------------- */

    private String getLoginEmailId() {
        return 'test-' + randomId + EMAIL_FORM
    }

    private String getConcurrentEmailId() {
        String id = 'test-' + String.valueOf(agentNum) + String.valueOf(processNum) + String.valueOf(threadNum) + "-" + String.valueOf(numberTest) + EMAIL_FORM
        //grinder.logger.debug(grinder.getAgentNumber() + ", " + grinder.getProcessNumber() +", " + grinder.getThreadNumber() + ", " + numberTest + ", reg id: " + id)
        return id
    }
}
