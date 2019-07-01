package software.jevera.exception;

public class EmployeeAlreadyExist extends Throwable {
    private String fullName;

    public EmployeeAlreadyExist(String fullName) {
        this.fullName = fullName;
    }
}
