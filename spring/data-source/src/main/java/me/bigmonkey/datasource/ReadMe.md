- 애플리케이션 로직에서 커넥션이 필요하면 DriverManager.getConnection() 을 호출한다

- DriverManager 는 라이브러리에 등록된 드라이버 목록을 자동으로 인식한다
이 드라이버들에게 순서대로 다음 정보를 넘겨서 커넥션을 획득할 수 있는지 확인한다

- 이렇게 찾은 커넥션 구현체가 클라이언트에 반환된다.

- 스프링 AOP를 적용하려면 어드바이저, 포인트컷, 어드바이스가 필요
스프링은 트랜잭션 AOP 처리를 위해 다음 클래스를 제공, 스프링 부트를 사용하면 해당 빈들은 스프링 컨테이너에 자동으로 등록

어드바이저 : BeanFactoryTransactionAttributeSourceAdvisor
포인트컷: TransactionAttributesSourcePointcut
어드바이스: TransactionInterceptor

- 프로그래밍 방식의 트랜잭션 관리는 스프링 컨테이너나 스프링 AOP 기술 없이 간단히 사용할 수 있지만
실무에서는 대부분 스프링 컨테이너와 스프링 AOP를 사용하기 때문에 거의 사용되지 않는다.

- 프로그래밍 방식 트랜잭션 관리는 테스트 시에 가끔 사용될 때는 있다.

- 스프링 부트가 등장하기 이전에는 데이터소스와 트랜잭션 매니저를 개발자가 직접 스프링 빈으로 등록해서 사용했다
@Bean
DataSource dataSource() {
 return new DriverManagerDataSource(URL, USERNAME, PASSWORD);
}

@Bean
PlatformTransactionManager transactionManager() {
 return new DataSourceTransactionManager(dataSource());
}

- 스프링 부트는 데이터소스( DataSource )를 스프링 빈에 자동으로 등록한다.
자동으로 등록되는 스프링 빈 이름: dataSource
참고로 개발자가 직접 데이터소스를 빈으로 등록하면 스프링 부트는 데이터소스를 자동으로 등록하지
않는다

- 이때 스프링 부트는 다음과 같이 application.properties 에 있는 속성을 사용해서 DataSource 를 생성한다. 
그리고 스프링 빈에 등록한다
spring.datasource.url=jdbc:h2:tcp://localhost/~/test
spring.datasource.username=sa
spring.datasource.password=
스프링 부트가 기본으로 생성하는 데이터소스는 커넥션풀을 제공하는 HikariDataSource 이다. 
spring.datasource.url 속성이 없으면 내장 데이터베이스(메모리 DB)를 생성하려고 시도한다.

스프링 부트는 적절한 트랜잭션 매니저( PlatformTransactionManager )를 자동으로 스프링 빈에 등록한다.
자동으로 등록되는 스프링 빈 이름: transactionManager
참고로 개발자가 직접 트랜잭션 매니저를 빈으로 등록하면 스프링 부트는 트랜잭션 매니저를 자동으로 등록하지 않는다

어떤 트랜잭션 매니저를 선택할지는 현재 등록된 라이브러리를 보고 판단하는데, JDBC를 기술을 사용하면
DataSourceTransactionManager 를 빈으로 등록하고, JPA를 사용하면 JpaTransactionManager 를
빈으로 등록한다. 둘다 사용하는 경우 JpaTransactionManager 를 등록한다. 참고로
JpaTransactionManager 는 DataSourceTransactionManager 가 제공하는 기능도 대부분 지원한다.


- 그렇다면 언제 체크 예외를 사용하고 언제 언체크(런타임) 예외를 사용하면 좋을까?
기본 원칙은 다음 2가지를 기억하자.

- 기본적으로 언체크(런타임) 예외를 사용하자.

- 체크 예외는 비즈니스 로직상 의도적으로 던지는 예외에만 사용하자.
이 경우 해당 예외를 잡아서 반드시 처리해야 하는 문제일 때만 체크 예외를 사용해야 한다. 
예를 들어서 다음과 같은 경우가 있다. 
체크 예외 예)
    계좌 이체 실패 예외
    결제시 포인트 부족 예외
    로그인 ID, PW 불일치 예외
물론 이 경우에도 100% 체크 예외로 만들어야 하는 것은 아니다. 다만 계좌 이체 실패처럼 매우
심각한 문제는 개발자가 실수로 예외를 놓치면 안된다고 판단할 수 있다. 이 경우 체크 예외로 만들어
두면 컴파일러를 통해 놓친 예외를 인지할 수 있다
