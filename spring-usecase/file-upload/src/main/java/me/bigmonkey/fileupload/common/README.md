- HTML form 을 통한 파일 업로드를 이해하려면 먼저 폼을 전송하는 다음 두 가지 방식의 차이를 이해
 > application/x-www-form-urlencoded
 > multipart/form-data

기본적으로 form 데이터 전송을 하면 application/x-www-from-urlencoded 방식으로 전송한다
form 태그에 별도의 enctype 옵션이 없으면 웹 브라우저는 요청 HTTP 메시지의 헤더에 다음 내용을 추가
Content-Type: application/x-www-form-urlencoded
그리고 폼에 입력한 전송할 항목을 HTTP Body에 문자로 &와 같이 구분해서 전송

파일을 업로드 하려면 파일은 문자가 아니라 바이너리 데이터를 전송해야 한다. 문자를 전송하는 이 방식으로 파일을 전송하기는 어렵다
그리고 또 한가지 문제가, 보통 폼을 전송할 때 파일만 전송하는 것이 아니라는 점이다

폼 데이터는 문자로 첨부파일은 바이너리로 전송해야 한다. '문자와 바이너리를 동시에 전송'해야 하는 상황이다
이 문제를 해결하기 위해 HTTP는 'multipart/form-data'라는 전송 방식을 제공

이 방식을 사용하려면 Form 태그에 별도의 enctype = 'multipart/form-data'를 지정해야 한다

spring.servlet.multipart.enabled=true (기본 true)
이 옵션을 켜면 스프링 부트는 서블릿 컨테이너에게 멀티파트 데이터를 처리하라고 설정한다. 참고로 기본 값은 true 이다

로그를 보면 HttpServletRequest 객체가 RequestFacade > StandardMultipartHttpServletRequest 로 변한 것을 확인할 수 있다 

spring.servlet.multipart.enabled 옵션을 켜면 스프링의 DispatcherServlet 에서 멀티파트 리졸버( MultipartResolver )를 실행한다.
멀티파트 리졸버는 멀티파트 요청인 경우 서블릿 컨테이너가 전달하는 일반적인 HttpServletRequest 를 MultipartHttpServletRequest 로 
변환해서 반환한다.
MultipartHttpServletRequest 는 HttpServletRequest 의 자식 인터페이스이고, 멀티파트와 관련된 추가 기능을 제공한다.

스프링이 제공하는 기본 멀티파트 리졸버는 MultipartHttpServletRequest 인터페이스를 구현한 StandardMultipartHttpServletRequest 를 반환한다.
이제 컨트롤러에서 HttpServletRequest 대신에 MultipartHttpServletRequest 를 주입받을 수 있는데, 
이것을 사용하면 멀티파트와 관련된 여러가지 처리를 편리하게 할 수 있다. 
그런데 이후 강의에서 설명할 MultipartFile 이라는 것을 사용하는 것이 더 편하기 때문에 MultipartHttpServletRequest 를 잘 사용하지는 않는다. 
더 자세한 내용은 MultipartResolver 를 검색해보자
