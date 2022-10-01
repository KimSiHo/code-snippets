- JOINED 전략을 사용하면 상위 클래스 (Item)에 있는 PK가 하위 클래스의 FK 역할을 한다

- DiscriminatorColumn은 프로그래밍 상으로는 PK, FK로 매핑 되기에 필요가 없지만, db상에서 작업을 하거나
item 테이블만 select 할 때를 위해 추가하는 것이 좋다