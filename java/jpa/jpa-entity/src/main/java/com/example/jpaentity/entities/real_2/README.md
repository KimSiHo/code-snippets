- 엔티티를 직접 노출할 때는 양방향 연관관계가 걸린 곳은 꼭 한곳은 @JsonIgnore 처리 해야 한다
안 그러면 양쪽을 서로 호출하면서 무한 루프가 걸린다

- 정말 간단한 애플리케이션이 아니면 엔티티를 API 응답으로 외부로 노출하는 것은 좋지 않다
따라서 Hibernate5Module를 사용하기 보다는 DTO로 변환해서 반환하는 것이 더 좋다

- 지연 로딩을 피하기 위해 즉시 로딩으로 설정하면 안된다! JPQL로 조회하는 경우 일단 쿼리가 나가고 즉시 로딩이면
그 이후에 쿼리가 나가기 때문에 N+1 문제가 발생한다.
또한 연관관계가 필요 없는 경우에도 데이터를 항상 조회해서 성능 문제가 발생할 수 있다.
em을 통해서 조회할 때는 eager인 경우 쿼리 한 번에 가져오긴 하지만 연관관계가 필요 없는 경우에도 가져오는 경우는 피할 수 없다!

쿼리 방식 선택 권장 순서
1 우선 엔티티를 DTO로 변환하는 방법을 선택
2 필요하면 페치 조인으로 성능을 최적화 -> 대부분의 성능 이슈가 해결
3 그래도 안되면 DTO로 직접 조회하는 방법을 사용
4 최후의 방법은 JPA가 제공하는 네이티브 SQL이나 스프링 JDBC Template을 사용해서 SQL을 직접 사용

페이지 한계 돌파
- 먼저 ToOne 관계는 모두 페치조인 한다
- 컬렉션은 지연 로딩으로 조회, 지연 로딩 성능 최적화를 위해 @BatchSize를 적용한다
- 이 옵션을 사용하면 컬렉션이나, 프록시 객체를 한꺼번에 설정한 size 만큼 IN 쿼리로 조회

- 또한 조인 보다 DB 데이터 전송량이 최적화 된다
(대부분의 경우 fetch 조인을 통해서 한 번에 가져오는 것이 빠르긴 하지만, 상황에 따라서 데이터 양이 클때는 해당 방식이
더 빠를수도 있다)

- 강사님은 실무에서 v5와 v6 비교해서 대부분 v5를 쓴다고 한다
엔티티 방식을 쓰면서 @BatchSize를 쓰면 DTO 방식으로 쓰면서 in 쿼리를 날리기 위해 작성한 수많은 코드들 (v5)를 자동으로
해주는 것이다
