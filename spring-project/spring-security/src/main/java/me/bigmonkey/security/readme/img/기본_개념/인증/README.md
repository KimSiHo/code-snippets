- 인증 flow

AM는 실제 인증 처리를 하지 않고 list형태로 갖고 있는 AP에 적절한 AP를 찾아서 해당 AP에 인증 처리를 위임.
AP는 실제로 ID/PW 를 검증하는 역할을 한다.
AP는 사용자가 입력한 ID 값으로 loadUserByUsername(username)을 호출. 여기서 username은 사용자가 입력한 아이디 값이다.
해당 요청을 UserDetailsService에 요청하고 UDS는 DB에서 해당 id를 기준으로 사용자 정보를 찾아온다.
DB에서 User타입으로 받아오고 AP에 반환할 때는 UserDetails로 반환한다.

- Authentication Manager

AuthenticationManager를 구현한 클래스가 ProviderManager이다.
사용자가 다양한 방법은 로그인을 시도하면, 인증 필터를 통해 AP의 구현체인 ProviderManager한테 Authentication 인증 객체가 오고, 
PM은 해당 인증을 처리할 수 있는 AuthenticationProvider를 찾아서 인증을 처리한다.
그리고 PM은 parent라는 속성으로 같은 클래스의 객체인 PM을 가질 수 있으며, 최초 PM에서 인증을 처리할 수 있는 AuthenticationProvider를 
찾을 수 없으면 부모 PM의 AuthenticationProvider를 찾는다.
인증이 성공하면 AP가 성공된 인증 객체를 다시금 인증 필터에 보내는 역할까지 한다. 

- Authentication Provider

ID와 PW검증 외에도 추가 검증을 해당 클래스에서 할 수 있다.
AP는 인터페이스로 두 가지 메소드를 제공한다.
UserDetailsService는 인터페이스로 실제 데이터 계층에서 아이디에 해당하는 사용자를 User객체로 가져오고 리턴.
user객체는 UserDetails인터페이스의 구현체이다.
id에 해당하는 사용자가 없으면 UserNotFound예외가 발생.
