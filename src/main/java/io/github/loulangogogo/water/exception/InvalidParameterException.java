package io.github.loulangogogo.water.exception;

/*********************************************************
 ** 无效的参数异常
 ** 
 ** @author loulan
 ** @since 8
 *********************************************************/
public class InvalidParameterException extends BaseException{
    public InvalidParameterException() {
        super();
    }

    public InvalidParameterException(String message) {
        super(message);
    }

    public InvalidParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidParameterException(Throwable cause) {
        super(cause);
    }

    protected InvalidParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
