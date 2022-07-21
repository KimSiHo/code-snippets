- url 매핑이 통과되면, SecurityContext에서 인증객체를 꺼내와서 로그아웃 핸들러에게 넘겨준다

- 로그아웃 필터가 가지고 있는 로그아웃 핸들러가 몇 개 있는데, 그 중에서 SecurityContextLogoutHandler가
세션 무효화, 쿠키 삭제, clearContext를 호출한다
해당 함수를 통해 security context를 삭제한다



