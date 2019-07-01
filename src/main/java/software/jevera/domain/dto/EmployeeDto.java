package software.jevera.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;
import software.jevera.domain.EmployeeSex;
import software.jevera.domain.Position;
import software.jevera.domain.ProfessionalityLevel;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    @Length(min = 2, max = 55)
    private String fullName;
    @DateTimeFormat(pattern = "yyyy.MM.dd", iso = DateTimeFormat.ISO.DATE_TIME)
    private Date dateOfBirth;
    private EmployeeSex sex;
    private String nationality;
    private String workLocation;
    @DateTimeFormat(pattern = "yyyy.MM.dd", iso = DateTimeFormat.ISO.DATE_TIME)
    private Date startOfWork;
    @JsonIgnore
    @Size(min = 5)
    private String password;

    @Enumerated(EnumType.STRING)
    private ProfessionalityLevel professionalityLevel;

    private String currentPosition;

    public ProfessionalityLevel getProfessionalityLevel() {
        return professionalityLevel;
    }

    public void setProfessionalityLevel(ProfessionalityLevel professionalityLevel) {
        this.professionalityLevel = professionalityLevel;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(String currentPosition) {
        this.currentPosition = currentPosition;
    }

    public EmployeeDto(@Length(min = 2, max = 55) String fullName, @Size(min = 5) String password) {
        this.fullName = fullName;
        this.password = password;
    }
}
