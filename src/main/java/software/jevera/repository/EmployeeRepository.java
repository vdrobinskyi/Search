package software.jevera.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import software.jevera.domain.Employee;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee>findByFullName(String fullName);
    List<Employee> findByCurrentPosition(String currentPosition);
    Employee save(Employee employee);
    List<Employee> findByWorkLocation(String workLocation);

    @Query(value = "SELECT e FROM Employee e")
    List<Employee> findAll();

    Employee findById(Map.Entry<Long, Date> id);

//    @Query(value = "select e from Employee e where ")
//    List<Employee> findByStartOfWork();

}
