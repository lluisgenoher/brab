package tcm.laq.bitcoinProjectLAQ.application.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String id)  {
        super("User " + id + " does not exist");
    }
}
