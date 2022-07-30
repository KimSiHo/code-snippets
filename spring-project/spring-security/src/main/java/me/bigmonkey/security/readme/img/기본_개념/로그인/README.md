- 기본 로그인
인증에 성공하면 서버에서는 세션을 만든다
세션에 authentication(인증 토큰) 객체를 만들고 security context에 저장. 해당 sc를 세션에 저장한다

- 로그아웃
url 매핑이 통과되면, SecurityContext에서 인증객체를 꺼내와서 로그아웃 핸들러에게 넘겨준다.
로그아웃 필터가 가지고 있는 로그아웃 핸들러가 몇 개 있는데, 그 중에서 SecurityContextLogoutHandler가 세션 무효화, 쿠키 삭제, clearContext를
호출한다. 
해당 함수를 통해 security context를 삭제한다.

- 리멤버 미 인증
리멤버 미 기능을 활성화시키면 해당 쿠키가 추가. 그리고 해당 인코딩된 문자열안에는 인증 시 사용했던 사용자 id와 패스워드 그리고 해당 쿠키
만료일이 담겨있다.
이제 JSESSIONID를 삭제하고 url을 요청해도 로그인 하라는 페이지가 안뜬다.
필터에서 리멤버 미 토큰이 있는지 검사하고, 토큰이 있으면 해당 문자열을 파싱해서 id와 패스워드를 얻어서 user 객체를 얻고 해당 user객체로
다시금 인증을 시도한다.
인증이 성공하면 해당 인증 객체를 다시 SecurityContext에 저장한다.

- 리멤버 미 필터
tokenBases는 토큰을 메모리에 저장하고, 사용자 요청을 온 리퀘스트 헤더에 있는 토큰값과 비교. 기본 토큰 만료일은 14일이다.
Persistent는 토큰을 DB에 저장하고 비교한다.

- csrf filter
서버가 클라이언트에 발급한 csrf 토큰값을 클라이언트가 요청을 보낼 때, 서버에 있는 토큰값과 비교해서 일치하지 않으면
 accessDeniedHandler 같은 메소드를 호출해서 접근을 제한시킨다.
PACTH, POST, PUT, DELETE 메소드를 사용할 때 csrf 토큰이 있어야 한다.

- 로그아웃(2)

post방식으로 보내면 로그아웃 필터가 해당 요청을 받아서 로그아웃 처리를 한다.
get 요청으로 보낼 때는 SecurityContextLogoutHandler클래스를 구현해서 로그아웃 처리를 할 수 있으며, 
로그아웃 필터도 해당 클래스를 사용해서 로그아웃 처리를 하고 있다.

폼 인증 - Web Authentication Details

사용자가 입력한 아이디와 비밀번호 말고도 추가적인 정보들이 들어올 수 있는데, 해당 정보들을 인증 과정 중에 참조하거나 인증 외에도
 지속적으로 참조할 수 있도록 처리해주는 클래스가 WebAuthenticationDeatils 클래스이고 해당 클래스를 생성해주는 클래스가 
 AuthenticationDetailsSource클래스이다.
필터가 Authentication 객체를 만들고 해당 객체에 사용자가 입력한 ID/PW를 입력.
Authentication 객체에는 details라는 object형 참조가 있어 해당 참조에서 WebAuthenticationDetails객체를 참조한다.
WebAuthenticationDetails객체는 파라미터로 request를 받는다. request의 getParameter함수로 추가 파라미터를 설정할 수 있고,
스프링 시큐리티가 기본적으로 sessionId와 remoteAddress를 처리해주고 있다.

폼 인증 - Web Authentication Details(2)

인증 필터에 다음과 같은 작업이 있다.
usernamePassowrdAuthenticaitonFilter에.