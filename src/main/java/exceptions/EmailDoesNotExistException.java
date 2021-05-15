package exceptions;

public class EmailDoesNotExistException extends Exception{

    public EmailDoesNotExistException(String email) {
        super(String.format("An account with the email %s does not exists!", email));
    }
}
