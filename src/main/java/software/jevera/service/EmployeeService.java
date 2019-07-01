package software.jevera.service;

import lombok.SneakyThrows;
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
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PositionService positionService;
    @Autowired
    private ModelMapper modelMapper;

    public Employee save(Employee employee){
        return employeeRepository.save(employee);
    }

    public Optional<Employee> searchByFullName(String fullName){
        return employeeRepository.findByFullName(fullName);
    }

    public List<Employee> searchByCurrentPosition(String currentPosition){return employeeRepository.findByCurrentPosition(currentPosition); }

    public List<Employee> searchByExperience(Date startOfWork, List<Employee> employees ){

        List<Long> employeesId = employees.stream().map(Employee::getId).collect(toList());
        List<Date> dates = employees.stream().map(Employee::getStartOfWork).collect(toList());
        DateDiffDto dateDiffDto = new DateDiffDto();
        Map<Long, Date> map = IntStream.range(0, employees.size())
                .boxed().collect(toMap(employeesId::get, dates::get));
        for (Map.Entry<Long, Date> id : map.entrySet()){
            Date date = new Date();
            Period period = Period.between(date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate(), startOfWork.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate());
            int diff = Math.abs(period.getYears());
            if (diff >= dateDiffDto.getExperience()) {
                List<Employee> list = new ArrayList<>();
                list.add(employeeRepository.findById(id));
                return list;
            }
            else return Collections.emptyList();
        }

        return Collections.emptyList();
//        return employeeRepository.findByStartOfWork();
    }
    public List<Employee> searchByWorkPlace(String workLocation){return employeeRepository.findByWorkLocation(workLocation);}

    public List<Employee> findAll(){return employeeRepository.findAll();}

    public Employee update(Employee employee, EmployeeDto employeeDto, String currentPosition, ProfessionalityLevel professionalityLevel){
        modelMapper.map(employeeDto, employee);
        if (StringUtils.isNotBlank(employeeDto.getPassword())) {
            employee.setPasswordHash(encryptPassword(employeeDto.getPassword()));
        }
        addRelation(employee,currentPosition, professionalityLevel);
        return save(employee);
    }

    public void delete(Employee employee){
        employeeRepository.delete(employee);
    }

    public Employee registration(EmployeeDto employeeDto, String currentPosition, ProfessionalityLevel professionalityLevel) throws EmployeeAlreadyExist {
        if(isEmployeeExist(employeeDto.getFullName())) {
            throw new EmployeeAlreadyExist(employeeDto.getFullName());
        }
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        employee.setPasswordHash(encryptPassword(employeeDto.getPassword()));

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
        return employeeRepository.findByFullName(fullName)
                .filter(employee -> checkPassword(password, employee))
                .orElseThrow(UncorrectGrant::new);
    }

    private boolean checkPassword(String password, Employee employee) {
        String encryptPassword = encryptPassword(password);
        return encryptPassword.equals(employee.getPasswordHash());
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
