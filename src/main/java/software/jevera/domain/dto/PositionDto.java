package software.jevera.domain.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import software.jevera.domain.Employee;
import software.jevera.domain.ProfessionalityLevel;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@NoArgsConstructor
@AllArgsConstructor
public class PositionDto {

    private String name;
    private String abbreviation;
    private String attribute;
    private String parentPosition;
    @Enumerated(EnumType.STRING)
    private ProfessionalityLevel professionalLevel;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getParentPosition() {
        return parentPosition;
    }

    public void setParentPosition(String parentPosition) {
        this.parentPosition = parentPosition;
    }

    public ProfessionalityLevel getProfessionalLevel() {
        return professionalLevel;
    }

    public void setProfessionalLevel(ProfessionalityLevel professionalLevel) {
        this.professionalLevel = professionalLevel;
    }

}
