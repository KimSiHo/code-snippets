- template.update()
데이터를 변경할 때 사용하면 됨. insert, update, delete SQL에 사용한다. 반환 값은 int 인데 영향 받은 로우 수를 반환

데이터를 저장할 때 PK 생성에 identity 방식을 사용하기에 PK인 ID 값을 개발자가 직접 지정하는 것이 아니라 
비워놓고 저장해야 한다. 그러면 데이터베이스가 PK인 ID를 대신 생성
데이터베이스에 INSERT가 완료 되어야 생성된 PK ID 값을 확인 가능

KeyHolder와 connection.prepareStatement(sql, new String[]{"id})를 사용해서 'id'를 지정해주면 INSERT 쿼리 실행 
이후에 생성된 ID 값을 조회 가능

- template.queryForObject()
결과 로우가 하나일 때 사용
RowMapper는 데이터베이스 반환 결과인 ResultSet을 객체로 변환
결과가 없거나 결과가 둘 이상이면 예외가 발생, 결과가 없을 때 Optional을 반환해야 하므로 결과가 없으면 예외를 잡아서
Optional.empty를 대신 반환하면 된다