package io.github.loulangogogo.water.exception;

/*********************************************************
 ** 反射异常
 ** 
 ** @author loulan
 ** @since 8
 *********************************************************/
public class ReflectException extends BaseException{
    public ReflectException() {
        super();
    }

    public ReflectException(String message) {
        super(message);
    }

    public ReflectException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReflectException(Throwable cause) {
        super(cause);
    }

    protected ReflectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
