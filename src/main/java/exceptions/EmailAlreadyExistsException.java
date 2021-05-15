package exceptions;

public class EmailAlreadyExistsException extends Exception {

    public EmailAlreadyExistsException(String email) {
        super(String.format("An account with the email %s already exists!", email));
    }
}
