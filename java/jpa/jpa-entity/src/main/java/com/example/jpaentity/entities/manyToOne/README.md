- 다대일 연관관계를 설정할 때는 객체 관점에서 일 쪽에도 연관관계를 설정해주는 것이 오류를 미연에 방지할 수 있다
flush 하고 조회하면 일 쪽에도 연관된 엔티티가 다 조회되지만 flush 하기 전에는 일 쪽에 연관관계를 설정해 주지 않으면
일 쪽에서는 연관관계를 알 수 없다.

- 외래 키를 관리하지 않는 연관관계에서 조회한 것도 dirty check는 되서 값 조회 후 변경하면 update 쿼리가 flush 시점에
나간다.
즉, 외래 키 관리만 하지 않는 것이다!

- 외래 키를 관리하는 다 쪽에서 fetch 타입을 eager로 하면 left out 조인으로 일(member)도 같이 가져오고 lazy 로 설정하면
자기만 단순 select 쿼리로 가져온다
