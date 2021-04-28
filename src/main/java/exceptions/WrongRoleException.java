package exceptions;

public class WrongRoleException extends Exception{

    private String role;

    public WrongRoleException(String role) {
        super(String.format("The role %s does not exists!", role));
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
