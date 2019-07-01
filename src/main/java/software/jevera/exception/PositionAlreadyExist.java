package software.jevera.exception;

public class PositionAlreadyExist extends Throwable {
    private String name;

    public PositionAlreadyExist(String name) {
        this.name = name;
    }
}
