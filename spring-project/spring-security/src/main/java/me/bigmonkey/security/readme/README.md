- WebSecuirtyConfigurerAdapter가 HttpSecurity 클래스를 생성하고 HS 클래스는 세부적인 보안 기능을 설정할 수 있는 API를 제공.

- 인증에 성공하면 서버에서는 세션을 만듬. 세션에 authentication(인증 토큰)객체를 만들고 Security Context에 저장.
해당 sc를 세션에 저장.
