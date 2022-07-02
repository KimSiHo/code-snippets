- in 쿼리
@Query("select e from employee e where e.employeeName in (:names)")
@Param("names")List<String> names

List<Employee> findByEmplyeeNameIn(List<String> names)