package exceptions;

public class CannotBlockAdminException extends Exception
{
    public CannotBlockAdminException(String email) {
        super(String.format("Cannot block account with email %s because it belongs to an administrator!", email));
    }
}
