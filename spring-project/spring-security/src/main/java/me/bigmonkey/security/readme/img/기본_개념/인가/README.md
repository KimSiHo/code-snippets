인가 API 권한 설정 방식에는 선언적 방식와 동적 방식 (DB 연동 프로그래밍)이 있다

각 권한은 별개 이기에, admin 권한은 user권한 자원에 접근할 수 없다. 따라서 각 권한을 다 지정해 주어야 한다.
하지만 권한 계층 구조를 사용하면 이렇게 하지 않아도 된다.

- exceptionTranslationFilter

해당 필터는 2가지 종류의 예외를 처리.
filterSecurityInteceptor가 해당 예외들을 발생시킨다. 스프링 시큐리티가 관리하는 필터들 중 맨 마지막에 위치한다.
해당 필터 앞에 위치하는 필터가 ExceptionTranslationFilter이다.
ETF에서 사용자가 넘겨준 인증 값을 filterSecurityInteceptor에 넘겨줄 때, try-catch문으로 호출해서 filterSecurityInteceptor에서 넘겨주는 예외를 처리한다
AuthenticationEntryPoint를 구현한 구현체를 실행. 스프링 시큐리티에서 제공해주는 구현체들이 있다.
사용자가 해당 인터페이스의 구현체를 작성하면, 해당 구현체를 실행한다.
요청정보를 실제로 저장하는 것은 SavedRequest 구현체이고, 해당 구현체를 세션에 저장하는 클래스가 RequestCache이다.

- 전체 흐름

인증받지 않는 사용자가 /user라는 자원에 접근한다고 가정.
그러면 마지막 권한 처리 필터 FilterSecurityInterceptor에서 인증예외를 발생시키고 2가지 일을 처리하는데.
AuthenticationEntryPoint구현체에서, 인증 실패 이후 처리를 하며 보통 로그인 페이지로 이동시킨다.
그리고 인증 실패전의 사용자 요청 정보를 DefaultSavedRequest객체에 저장하고, 해당 객체를 세션에 저장시키는데 세션에 저장시키는 일을
 HttpSessionRequestCache클래스의 객체가 한다.
