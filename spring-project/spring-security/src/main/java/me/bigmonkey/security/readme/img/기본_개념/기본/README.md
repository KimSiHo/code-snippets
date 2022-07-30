- Delegating Filter Proxy

서블릿 필터는 서블릿 스펙을 구현한 서블릿 컨테이너에서 생성되고 동작한다. 스프링 빈은 스프링 컨테이너에서 동작하기에 서로 동작하는 곳이
 다르므로, 서블릿 필터는 스프링 빈을 사용할 수 없다.
서블릿 필터는 스프링 기술을 사용하지 못하는데, 스프링 기술을 사용하고자 하는 요구사항이 등장.
처음에 DelegatingFilterProxy 서블릿이 요청을 받는다. 해당 필터 프록시는 서블릿 필터이다.
필터 프록시가 서블릿 필터를 구현한 스프링 빈에게 요청을 위임해서 스프링 기술을 활용한 필터를 사용할 수 있게 한다.
DelegatingFilterProxy는 실제 보안처리를 하지는 않고 필터를 구현한 스프링 빈한테 요청을 위임한다.
DelegatingFilterProxy는 springSecurityFilterChain 이라는 이름으로 생성된 빈을 찾아 요청을 위임.

- 빈 등록

SecurityFilterAutoConfiguration클래스에서 DelegatingFilterProxy를 해당 이름으로 필터를 등록해준다.
"springSecurityFilterChain" 
FilterChainProxy도 동일한 이름으로 빈으로 등록한다.

- Authentication(2)

사용자 이름과 비밀번호를 입력해서 보내면 UserNamePasswordAuthenticationFilter가 요청을 받아서 Authentication 객체를 만들고 
사용자 id와 비밀번호를 해당 객체에 저장한다. 
해당 객체가 실제 인증 검증을 위해 사용되고 처음 생성될 때 Authenticated가 false인 것을 확인할 수 있다.
AuthenticationManger가 인증처리를 총괄한다. 인증이 통과하면 AuthenticationManager가 Authentication객체를 만든다.
이때 Principal에는 사용자 id가 아니라, 조회한 사용자를 나타내는 객체인 UserDetails 객체를 저장한다.
credentials에는 비밀번호를 넣어두는데, 보안상 비어두기도 한다.
Authentication은 인터페이스이며, 해당 인터페이스를 구현한 구현체로 RemembermeAuthenticationToken, 
UsernamePasswordToken, AnnoymouseUserAuthenticationToken등이 있으면, 해당 구현체를 인증에 사용할 수 있다

- Security Context Holder

3번째 모드인 MODE_GLOBAL은 쓰레드 로컬이 아니라 static 변수에 저장하는 방식이다.

- Security Context Persistence Filter(3)

매 요청마다 SC를 생성하거나 세션에서 불러오는 로직이 있기에 요청 응답 후 항상 SC를 삭제한다.

- 전체 흐름

사용자가 정의한 API들에 의해서 filter 목록들을 가지고 있는 HttPSecurity클래스의 객체들이 생성된다.
그리고 해당 객체들을 WebSecuiry에 전달해서, 해당 클래스가 list형태로 HttpSecurity객체들을 가지고 있다.
그리고 WebSecurity에서 FilterChainProxy를 생성하는데, 이 때 생성자로 filter들을 전달해준다.
따라서 FilterChainProxy빈은 filter들을 갖고 있다.
사용자가 요청을 하면 DelegatingFilterProxy 서블릿이 요청을 받는데.
해당 서블릿 필터가 초기화 될 때, 이미 FilterChainProxy는 생성된 상태이다.
DelegatingFilterProxy는 특정한 이름의 빈을 찾는데(springSecurityFilterChain), 해당 빈이 FilterChainProxy이다.
검정색 위가 초기화 부분이다.
이제 필터들의 흐름을 보는데, 인증하는 경우와 자원에 접근하는 두 가지 경우를 가정하고 살펴보자.
사용자가 인증하는 경우.
첫 번째 필터는 SecurityContextPersistenceFilter이다. 해당 필터는 HttpSessionSecurityContextRepository 클래스를 멤버로 갖고 있다.
해당 클래스는 SecurityContext를 생성하고, 세션에 저장하고, 세션에 저장된 SC를 조회하고 참조하는 역할을 하는 클래스이다.
해당 클래스의 loadContext함수를 사용해서 세션에 로그인 시도한 사용자의 SecurityContext가 저장된 이력이 있는지 확인.
지금은 로그인 시도하는 상황을 가정하고 있으니까 세션에 저장된 SC가 없고 따라서 SC를 생성한다.
처음 로그인 시도하는 사용자나 익명 사용자의 경우 새로운 SC를 생성하고 SCHolder에 저장하는 역할을 SecurityContextPersistenceFilter가 한다.
UsernamePasswordAuthenticationFilter에서 인증을 처리하고 SecurityContextPersistenceFilter에서 생성했던 SecurityContext에 인증 객체를 저장한다.
인증에 성공한 다음, 후속 처리를 하는데 그 과정이 SessionManagementFilter가 가지고 있는 3가지 정도의 처리를 인증을 시도하는 당시에
 동시에 처리한다.
ConcurrentSession을 통해 동시적 세션 처리를 한다.
SessionFixation을 통해 세션 고정보호를 한다. 새로운 쿠키를 발급한다.
Register SessionInfo를 통해 로그인한 사용자의 세션 정보가 등록된다.
인증 성공 후 redirect 메소드로 사용자 요청 페이지로 이동하기 전에 인증 성공 SecurityContext객체를 세션에 저장하는 역할을
 SecurityContextPersistenceFilter가 하고 있다.
자원을 요청하는 경우 똑같이 진행되다가 SecurityContextPersistenceFilter의 loadContext부분에서 이제는 세션에 저장된 SC 객체가 있으므로 SC를 새로 생성하지 않고 세션에 저장된 SC객체를 불러서 SCHolder에 저장한다.
그리고 SecurityContextPersistenceFilter에서 클라이언트에 응답하기 직전에 항상 SecurityContext를 삭제한다.
그리고 ConcurrentSessionFilter로 온다. 이 때 동일한 계정으로 2명이상이 접속할 때만 작동하는데, 현재는 한명만 로그인한 상황이므로 건너뛴 다.
RememberMeAuthenticationFilter는 로그인 당시 해당 기능을 활성화 했고, 현재 로그인한 사용자의 세션이 만료되서 인증 객체가 없을 때 동작한다. 
현재는 건너뛴다.
AnonymousAuthenticationFilter는 사용자가 인증 시도도 없이 어떤 자원에 접근하려고 시도 할 때 동작한다.
해당 필터는 AnnonymousAuthencticationToken을 만들어서 SC에 저장한다.
익명 사용자용 필터이다.
SessionManagmentFilter는 세션 객체가 null이거나, SC가 null 일때 동작한다. 현재는 해당 조건에 부합되지 않으므로 건너뛰고.
인증 시도 당시에 해당 필터가 동작을 했다.
ExceptionTranslationFilter는 마지막 인가 처리를 하는 FilterSecurityInterceptor의 예외를 받아서 처리한다.
FilterSecurityInterceptor는 2가지 체크를 하는데, 하나는 현재 접속한 사용자가 SC안에 인증객체가 있는지 확인.
인증 객체가 없으면 그 즉시 인증 예외를 발생.
인증 객체가 있으면 AccessDecisionManager클래스를 통해 인가처리를 한다.
1번쨰 사용자가 로그인한 상태에서, 2번째 사용자가 로그인 하면 로직을 타고가다 SessionManagemnetFIlter에서 전략에 따른 처리를 한다.
이때 2번째 로그인한 사용자가 로그인 성공 전략을 선택하면, 1번째 사용자는 이후 요청을 할 때, ConcurrentSessionFilter에서 세션이 만료되고 
로그아웃 처리된다.
