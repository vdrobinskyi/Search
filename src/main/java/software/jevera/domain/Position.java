package software.jevera.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(foreignKey = @ForeignKey)
    private String name;
    private String abbreviation;
    private String attribute;
    private String parentPosition;
    @Enumerated(EnumType.STRING)
    private ProfessionalityLevel professionalLevel;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private Set<EmployeePositionRelation> employeePositions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Set<EmployeePositionRelation> getEmployeePositions() {
        return employeePositions;
    }

    public void setEmployeePositions(Set<EmployeePositionRelation> employeePositions) {
        this.employeePositions = employeePositions;
    }

    public Position employeePositions(EmployeePositionRelation employeePositions) {
        this.employeePositions.add(employeePositions);
        return this;
    }

}
