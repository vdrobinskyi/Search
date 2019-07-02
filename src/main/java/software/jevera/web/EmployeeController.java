package software.jevera.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import software.jevera.domain.Employee;
import software.jevera.domain.ProfessionalityLevel;
import software.jevera.domain.dto.DateDiffDto;
import software.jevera.domain.dto.EmployeeDto;
import software.jevera.exception.EmployeeAlreadyExist;
import software.jevera.service.EmployeeService;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private HttpSession httpSession;

    @GetMapping("/all")
    public List<Employee> findAll(){
        return employeeService.findAll();
    }
    @GetMapping("/searchByLocation")
    public List<Employee> findByLocation(String workLocation){
        return employeeService.searchByWorkPlace(workLocation);
    }
    @GetMapping("/findByExperience")
    public List<Employee> findByExperience(String startOfWork, Long experience) throws Exception {
        return employeeService.searchByExperience(parseDate(startOfWork), experience);
    }
    @GetMapping("/searchByName")
    public Optional<Employee> findByName(String fullName){
        return employeeService.searchByFullName(fullName);
    }

    @GetMapping("/searchByPosition")
    public List<Employee> findByPosition(String currentPosition){
        return employeeService.searchByCurrentPosition(currentPosition);
    }

    @PostMapping("/registration")
    public Employee registration( EmployeeDto employeeDto,String currentPosition, ProfessionalityLevel professionalityLevel) throws EmployeeAlreadyExist {
        return employeeService.registration(employeeDto, currentPosition, professionalityLevel);
    }
    @PostMapping("/login")
    public Employee login(String login, String password){
        Employee employee = employeeService.login(login, password);
        httpSession.setAttribute("employee", employee);
        return employee;
    }

    @PutMapping("/update/{id}")
    public Employee update(@PathVariable("id") Employee employee, EmployeeDto employeeDto, String currentPosition, ProfessionalityLevel professionalityLevel){
        return employeeService.update(employee,employeeDto, currentPosition, professionalityLevel);
    }

    @DeleteMapping("/deleteEmployee/{id}")
    public void delete(@PathVariable("id") Employee employee){
        employeeService.delete(employee);
    }

    private Date parseDate(String date) throws Exception{
        return new SimpleDateFormat("yyyy.MM.dd").parse(date);
    }
}
