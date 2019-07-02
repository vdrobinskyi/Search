package software.jevera.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.jevera.domain.Employee;
import software.jevera.domain.EmployeePositionRelation;
import software.jevera.domain.Position;
import software.jevera.domain.ProfessionalityLevel;
import software.jevera.domain.dto.DateDiffDto;
import software.jevera.domain.dto.EmployeeDto;
import software.jevera.exception.EmployeeAlreadyExist;
import software.jevera.exception.EntityNotFound;
import software.jevera.exception.UncorrectGrant;
import software.jevera.repository.EmployeeRepository;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Service
@Slf4j
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PositionService positionService;
    @Autowired
    private ModelMapper modelMapper;

    public Employee save(Employee employee){
        log.info("Save employee");
        return employeeRepository.save(employee);
    }

    public Optional<Employee> searchByFullName(String fullName){
        log.info("Start search employees by full name");
        return employeeRepository.findByFullName(fullName);
    }

    public List<Employee> searchByCurrentPosition(String currentPosition){
        log.info("Start search employees by current position");
        return employeeRepository.findByCurrentPosition(currentPosition); }

    public List<Employee> searchByExperience(Date startOfWork, Long experience){
        log.info("Start search employees by experience");
        List<Employee> employees = employeeRepository.findAll();
        List<Long> employeesId = employees.stream().map(Employee::getId).collect(toList());
        List<Date> dates = employees.stream().map(Employee::getStartOfWork).collect(toList());
        Map<Long, Date> employeeIdsAndEmployeeStartWorkDate = IntStream.range(0, employees.size())
                             .boxed().collect(toMap(employeesId::get, dates::get));
        List<Employee> list = new ArrayList<>();
        for (Long id : employeeIdsAndEmployeeStartWorkDate.keySet()){
            Date date = new Date();
            Period period = Period.between(date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate(), startOfWork.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate());
            int diff = Math.abs(period.getYears());
            if (diff >= experience) {
                list.add(employeeRepository.findById(id).orElseThrow(EntityNotFound::new));
            }
        }
        log.info("Find employees by experience {}", list);
        return list;
    }
    public List<Employee> searchByWorkPlace(String workLocation){
        log.info("Start search employees by work place");
        return employeeRepository.findByWorkLocation(workLocation);}

    public List<Employee> findAll(){
        log.info("Start search all employees");
        return employeeRepository.findAll();}

    public Employee update(Employee employee, EmployeeDto employeeDto, String currentPosition, ProfessionalityLevel professionalityLevel){
        modelMapper.map(employeeDto, employee);
        if (StringUtils.isNotBlank(employeeDto.getPassword())) {
            employee.setPasswordHash(encryptPassword(employeeDto.getPassword()));
        }
        addRelation(employee,currentPosition, professionalityLevel);
        log.info("Success update employee {}", employee);
        return save(employee);
    }

    public void delete(Employee employee){
        log.info("Employee delete {}", employee);
        employeeRepository.delete(employee);
    }

    public Employee registration(EmployeeDto employeeDto, String currentPosition, ProfessionalityLevel professionalityLevel) throws EmployeeAlreadyExist {
        if(isEmployeeExist(employeeDto.getFullName())) {
            throw new EmployeeAlreadyExist(employeeDto.getFullName());
        }
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        employee.setPasswordHash(encryptPassword(employeeDto.getPassword()));
        log.info("Registration employee {}", employee);
        addRelation(employee, currentPosition, professionalityLevel);
        return save(employee);

    }

    private void addRelation(Employee employee,String name, ProfessionalityLevel professionalityLevel){
        EmployeePositionRelation employeePositionRelation = new EmployeePositionRelation();
        Employee employee1 = save(employee);
        Position position = positionService.getByNameAndLevel(name, professionalityLevel);
        employeePositionRelation.setEmployee(employee1);
        employeePositionRelation.setPosition(position);
        Set s = new HashSet();
        s.add(employeePositionRelation);
        employee1.setEmployeePositionRelations(s);
        position.setEmployeePositions(s);

    }

    private boolean isEmployeeExist(String employee) {
        return false;
    }

    public Employee login(String fullName, String password){
        log.info("Try logging");
        return employeeRepository.findByFullName(fullName)
                .filter(employee -> checkPassword(password, employee))
                .orElseThrow(UncorrectGrant::new);
    }

    private boolean checkPassword(String password, Employee employee) {
        String encryptPassword = encryptPassword(password);
        boolean check =encryptPassword.equals(employee.getPasswordHash());
        log.info("Successfully login for {}", employee);
        return check;
    }

    @SneakyThrows
    private static String encryptPassword(String password) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(password.getBytes());
            return new BigInteger(1,messageDigest.digest()).toString(16);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
