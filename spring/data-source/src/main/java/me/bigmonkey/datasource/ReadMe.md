- 애플리케이션 로직에서 커넥션이 필요하면 DriverManager.getConnection() 을 호출한다

- DriverManager 는 라이브러리에 등록된 드라이버 목록을 자동으로 인식한다
이 드라이버들에게 순서대로 다음 정보를 넘겨서 커넥션을 획득할 수 있는지 확인한다

- 이렇게 찾은 커넥션 구현체가 클라이언트에 반환된다.

- 스프링 AOP를 적용하려면 어드바이저, 포인트컷, 어드바이스가 필요
스프링은 트랜잭션 AOP 처리를 위해 다음 클래스를 제공, 스프링 부트를 사용하면 해당 빈들은 스프링 컨테이너에 자동으로 등록

어드바이저 : BeanFactoryTransactionAttributeSourceAdvisor
포인트컷: TransactionAttributesSourcePointcut
어드바이스: TransactionInterceptor
