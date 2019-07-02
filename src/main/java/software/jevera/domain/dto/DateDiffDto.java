package software.jevera.domain.dto;

public class DateDiffDto {
    private Long experience;

    public DateDiffDto() {

    }

    public Long getExperience() {
        return experience;
    }

    public void setExperience(Long experience) {
        this.experience = experience;
    }

    public DateDiffDto(Long experience) {
        this.experience = experience;
    }
}
