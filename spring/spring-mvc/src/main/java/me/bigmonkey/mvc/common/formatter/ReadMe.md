- 컨버전 서비스에는 컨버터만 등록할 수 있고, 포맷터를 등록할 수는 없다
그런데 생각해보면 포맷터는 객체 > 문자, 문자 > 객체로 변환하는 특별한 컨버터일 뿐이다
포맷터를 지원하는 컨버전 서비스를 사용하면 컨버전 서비스에 포맷터를 추가할 수 있다, 내부에서 어댑터 패턴을 사용해서 포맷터가 컨버터처럼 
동작하도록 지원

FormattingConversionservice는 포맷터를 지원하는 컨버전 서비스이다
DefaultFormattingConversionService는 FormattingConversionService에 기본적인 통화, 숫자 관련 몇가지 기본 포맷터를 추가해서 제공

FormattingConversionService는 ConversionService 관련 기능을 상속받기 때문에 결과적으로 컨버터도 포맷터도 모두 등록할 수 있다
그리고 사용할 때는 ConversionService가 제공하는 convert를 사용하면 된다

추가로 스프링 부트는 DefaultFormattingConversionService를 상속 받은 WebConversionService를 내부에서 사용

스프링은 자바에서 기본으로 제공하는 타입들에 대해 수 많은 포맷터를 기본으로 제공
그런데 포맷터는 기본 형식이 지정되어 있기에, 객체의 각 필드마다 다른 형식으로 포맷을 지정하기는 어렵다

스프링은 이런 문제를 해결하기 위해 애노테이션 기반으로 원하는 형식을 지정해서 사용할 수 있는 매우 유용한 포맷터 두 가지를
기본으로 제공

@NumberFormat : 숫자 관련 형식 지정 포맷터 사용 'NumberFormatAnnotationFormatterFactory'
@DateFormat : 날짜 관련 형식 지정 포맷터 사용 'Jsr310DateTimeFormatAnnotationFormatterFactory'

컨버터를 사용하든, 포맷터를 사용하든 등록 방법은 다르지만, 사용할 때는 컨버전 서비스를 통해서 일관성 있게 사용할 수 있다

주의!
메시지 컨버터 (HttpMessageConverter)에는 컨버전 서비스가 적용되지 않는다
특히 객체를 JSON으로 변환할 때 메시지 컨버터를 사용하면서 이 부분을 많이 오해하는데, HttpMessageConverter의 역할은
HTTP 메시지 바디의 내용을 객체로 변환하거나 객체를 HTTP 메시지 바디에 입력하는 것이다
예를 들어서 JSON을 객체로 변환하는 메시지 컨버터는 내부에서 Jackson 같은 라이브러리를 사용
객체를 JSON으로 변환한다면 그 결과는 이 라이브러리에 달린 것이다
따라서 JSON 결과로 만들어지는 숫자나 날짜 포맷을 변경하고 싶으면 해당 라이브러리가 제공하는 설정을 통해서 포맷을 지정해야 한다
결과적으로 이것은 컨버전 서비스와 전혀 관계가 없다
