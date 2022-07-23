- url 매핑이 통과되면, SecurityContext에서 인증객체를 꺼내와서 로그아웃 핸들러에게 넘겨준다

- 로그아웃 필터가 가지고 있는 로그아웃 핸들러가 몇 개 있는데, 그 중에서 SecurityContextLogoutHandler가 세션 무효화, 쿠키 삭제, clearContext를
호출한다. 해당 함수를 통해 security context를 삭제한다.

- JWT같이 세션을 사용하지 않고 토큰에 사용자의 모든 정보를 저장하는 인증 방식을 사용하면 세션 정책을 Stateless 로 설정해야 한다





