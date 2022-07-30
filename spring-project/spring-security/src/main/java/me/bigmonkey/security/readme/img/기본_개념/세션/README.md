- 세션 고정 보호
방지법으로 인증이 성공할 때마다 새로운 세션을 생성하고, 쿠키를 새로 쓴다.
이렇게 함으로써 공격자는 새로 생성된 세션 ID를 알지 못해서 사용자 정보를 공유할 수 없다.

- 세션 정책
JWT같이 세션을 사용하지 않고 토큰에 사용자의 모든 정보를 저장하는 인증 방식을 사용하면 세션 정책을 Stateless로 설정해야 한다.

- concurrent session filter
SessionManagemnetFilter와 연계해서 동시적 세션 제어를 처리한다.

- 세션 관리
세션이 끊긴 다음, 이전 사용자가 요청을 보내서 concurrentSessionFilter가 세션이 만료되었는지 여부를 확인할 때, 
SessionManagementFilter에 있던 이전 사용자 세션 만료 설정을 참조해서 확인한다.
해당 설정이 되있을 경우, 세션을 만료하도록 logout을 호출하고 오류 페이지를 응답.
sessionManagementFilter에서 세션 만료 설정을 먼저하고 concurrentSessionFilter에서 해당 설정 값이 true이면 로그아웃하는 로직으로 되어있다.

- 세션 전체
ConcurentSessionControlAuthenticationStrategy는 동시적 세션 제어를 처리하는 클래스이다.
해당 사용자로 연결된 session count를 알려준다.



