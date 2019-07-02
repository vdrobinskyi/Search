package software.jevera.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;


@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String fullName;

    @DateTimeFormat(pattern = "yyyy.MM.dd", iso = DateTimeFormat.ISO.DATE_TIME)
    private Date dateOfBirth;

    @Enumerated
    private EmployeeSex sex;

    private String nationality;

    private String workLocation;

    @DateTimeFormat(pattern = "yyyy.MM.dd", iso = DateTimeFormat.ISO.DATE_TIME)
    @Temporal(TemporalType.DATE)
    private Date startOfWork;

    private String passwordHash;

    @JoinColumn(foreignKey = @ForeignKey)
    private String currentPosition;

    @Enumerated(EnumType.STRING)
    private ProfessionalityLevel professionalityLevel;

    @OneToMany(mappedBy = "position", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<EmployeePositionRelation> employeePositions;

    public ProfessionalityLevel getProfessionalityLevel() {
        return professionalityLevel;
    }

    public void setProfessionalityLevel(ProfessionalityLevel professionalityLevel) {
        this.professionalityLevel = professionalityLevel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public EmployeeSex getSex() {
        return sex;
    }

    public void setSex(EmployeeSex sex) {
        this.sex = sex;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getWorkLocation() {
        return workLocation;
    }

    public void setWorkLocation(String workLocation) {
        this.workLocation = workLocation;
    }

    public Date getStartOfWork() {
        return startOfWork;
    }

    public void setStartOfWork(Date startOfWork) {
        this.startOfWork = startOfWork;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(String currentPosition) {
        this.currentPosition = currentPosition;
    }

    public Set<EmployeePositionRelation> getEmployeePositions() {
        return employeePositions;
    }

    public void setEmployeePositionRelations(Set<EmployeePositionRelation> employeePositions) {
        this.employeePositions = employeePositions;
    }

    public Employee fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }
    public Employee passwordHash(String passwordHash) {
        this.passwordHash = passwordHash;
        return this;
    }
    public Employee dateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }
    public Employee sex(EmployeeSex sex) {
        this.sex = sex;
        return this;
    }
    public Employee nationality(String nationality) {
        this.nationality = nationality;
        return this;
    }
    public Employee workLocation(String workLocation) {
        this.workLocation = workLocation;
        return this;
    }
    public Employee currentPosition(String currentPosition) {
        this.currentPosition = currentPosition;
        return this;
    }
    public Employee startOfWork(Date startOfWork) {
        this.startOfWork = startOfWork;
        return this;
    }

    public Employee employeePositions(EmployeePositionRelation employeePositions) {
        this.employeePositions.add(employeePositions);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) &&
                Objects.equals(fullName, employee.fullName) &&
                Objects.equals(dateOfBirth, employee.dateOfBirth) &&
                sex == employee.sex &&
                Objects.equals(nationality, employee.nationality) &&
                Objects.equals(workLocation, employee.workLocation) &&
                Objects.equals(startOfWork, employee.startOfWork) &&
                Objects.equals(passwordHash, employee.passwordHash) &&
                Objects.equals(currentPosition, employee.currentPosition) &&
                Objects.equals(employeePositions, employee.employeePositions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
