package io.github.loulangogogo.water.exception;

/*********************************************************
 ** 基础异常,里面的异常都必须继承这个异常
 ** 
 ** @author loulan
 ** @since 8
 *********************************************************/
public class BaseException extends RuntimeException{

    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    protected BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
