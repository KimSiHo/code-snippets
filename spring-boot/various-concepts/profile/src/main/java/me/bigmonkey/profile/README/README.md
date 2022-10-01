# spring boot 2.4 버전부터 애플리케이션 설정 파일( application.properties, application.yml, application.yaml ) 에 대한 구동방식이 변경
# 변경된 이유는 k8s 볼륨 마운트 구성때문이라고 함

# spring.config.activate.on-profile 은 해당 프로파일일때만 해당 파일에 설정된 프로퍼티들을 사용한다

강의 #1
# application.yml 파일에서 위와 같이 아무런 profiles를 주지 않으면 맨 밑에 있는 것이 오버라이드 해서 2.3이 찍힌다 

강의 #2
# prod로 실행시키면 어떤 my.message가 찍히는 지 알기 힘들다 prod를 다 읽은 다음, local을 읽기에 2.2가 찍히기는 하지만 헷갈린다

강의 #3
# spring.profiles에 해당하는 프로파일로 실행될 때에만 해당 프로퍼티 설정들이 동작한다. 2.4 부터는 해당 프로퍼티 설정이 activate.on-profile로
조금 더 직관적이게 변했지만 같은 의미이다

# include는 추가로 읽어들일 것, active는 현재 활성화 시킬 것이다
결과는 똑같은데 의미가 다른 것이다. active는 주로 실행시킬 때 쓰고 include는 설정 파일에 프로파일을 추가할 때 썼었다
하지만 이렇게 하면 너무 복잡하고 애매해서 특정 프로파일을 사용하거나 추가하는 설정과 현재 설정을 적용할 프로파일을 같이 사용할 수 없다

# (2)에서와 같이 activate.on-profile과 'include' 또는 'active'를 같이 쓸 수 없다

강의#4
# 스프링 부트 2.4부터 특정 프로파일을 추가할 수는 없지만 설정 파일을 추가하는 것은 허용한다

강의#5
# my디렉토리밑에 있는 message라는 파일에 어떤 값을 입력하면 해당 값이 my.message 라는 값으로 읽힌다

# 정리하면 프로필 스페시픽 application-prod 같은 것으로 특정 프로파일을 활성화하고 include만 사용하면 될 것 같다
나중에 복잡해지면 group 기능 사용하면 되고.
프로필 스페시픽 기능에 include 기능을 사용하면 안되니, 그냥 group 기능을 쓰면 될 것 같다 