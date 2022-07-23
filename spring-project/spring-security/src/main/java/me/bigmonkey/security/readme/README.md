- WebSecuirtyConfigurerAdapter가 HttpSecurity 클래스를 생성하고 HS 클래스는 세부적인 보안 기능을 설정할 수 있는 API를 제공.

- 인증에 성공하면 서버에서는 세션을 만듬. 세션에 authentication(인증 토큰)객체를 만들고 Security Context에 저장.
해당 sc를 세션에 저장.

- 리멤버 미 기능을 활성화하면 쿠키가 추가. 그리고 해당 인코딩된 문자열안에는 인증 시 사용했던 id와 패드워드 그리고 해당 퀴
만료일이 담겨있다.
필터에서 리멤버 미 토큰이 있는지 검사하고, 토큰이 있으면 해당 문자열을 파싱해서 id와 패스워드를 얻어서 user 객체를 얻고 해당 
user 객체로 다시금 인증을 시도.
인증이 성공하면 해당 인증 객체를 다시 SecurityContext 에 저장

세션이 끊긴 다음, 이전 사용자가 요청을 보내서 concurrentSessionFilter가 세션이 만료되었는지 여부를 확인할 때, SessionManagementFilter에 있던 이전 사용자 세션 만료 설정을 참조해서 확인한다.
해당 설정이 되있을 경우, 세션을 만료하도록 logout을 호출하고 오류 페이지를 응답.
sessionManagementFilter에서 세션 만료 설정을 먼저하고 concurrentSessionFilter에서 해당 설정 값이 true이면 로그아웃하는 로직으로 되어있다.

- 인가 API 권한 설정 방식에는 선언적 방식와 동적 방식 (DB 연동 프로그래밍)이 있다
