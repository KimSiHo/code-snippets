- JPQL을 실행하면 항상 1차 캐시를 무시하고, 데이터베이스에 직접 SQL을 실행
  - 그리고 실행 결과를 1차 캐시에 보관하고, 최종적으로 1차 캐시에 보관된 결과를 반환

- JPQL을 실행해서, 데이터베이스에서 결과를 가져왔는데 이미 1차 캐시에 동일한 식별자를 가진 엔티티가 있으면
데이터베이스에서 가져온 엔티티를 버리고 1차 캐시에 있는 엔티티를 유지

- JPQL은 엔티티 이름을 사용

- 모든 것을 페지 조인으로 해결할 수는 없음
  - 페치 조인은 객체 그래프를 유지할 때 사용하면 효과적
  - 여러 테이블을 조인해서 엔티티가 가진 모양이 아닌 전혀 다른 결과를 내야 하면, 페치 조인 보다는 일반 조인을 사용하고
    필요한 데이터들만 조회해서 DTO로 반환하는 것이 효과적
    
- 페치 조인이라는 것이 결국 조인 쿼리와 같지만 조인 쿼리는 자동으로 조인된 엔티티의 필드 값을 가져오지 않지만 
페치 조인은 조인된 엔티티의 필드 값을 자동으로 한번에 가져오는 것이다!

- 값을 가져오는 방법은 3가지라고 보면 되는데, 페치 조인된 엔티티를 가져오거나, 페치 조인된 엔티티를 가져와서 애플리케이션 
상에서 DTO로 변환하거나, 조인 쿼리를 사용하고 처음부터 DTO로 직접 가져오는 방법이 있다.

- 정적 쿼리 : jpql은 sql로 변환하면서 코스트가 있는데 정적 쿼리는 로딩 시점에 한 번 하고 안하기에 여러 번 사용할 경우
중복 코스트가 없다.
그리고 어플리케이션 로딩 시점에 쿼리를 검증

- Named 쿼리 환경에 따른 설정은 XML이 항상 우선권을 가진다
애플리케이션 운영 환경에 따라 다른 XML을 배포할 수 있다

- 재고가 10개 미만이 모든 상품의 가격을 10% 상승하려면? JPA 변경 감지 기능으로 실행하면 너무 많은 SQL 실행
executeUpdate()를 쓰면 쿼리 한 번으로 여러 테이블 로우 변경, executeUpdate()의 결과는 영향받은 엔티티 수 반환

- 벌크 연산은 영속성 컨텍스트를 무시하고 데이터베이스에 직접 쿼리하므로 벌크 연산을 한 이후에 디비와 영속성 컨텍스트의
불일치를 해결하기 위해 영속성 컨텍스트 초기화 필요




