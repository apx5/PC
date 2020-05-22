package Exceptions;

public class UserTakenException extends Exception{

    public UserTakenException(){
        super();
    }

    public UserTakenException(String msg){
        super(msg);
    }


}
