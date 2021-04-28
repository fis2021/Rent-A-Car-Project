package exceptions;

public class UsernameDoesNotExistException extends Exception{

    private String username;

    public UsernameDoesNotExistException(String username) {
        super(String.format("An account with the username %s does not exists!", username));
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
