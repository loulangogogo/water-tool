package io.github.loulangogogo.water.exception;

/*********************************************************
 ** IO运行时异常，常用于对IOException的包装
 ** 
 ** @author loulan
 ** @since 8
 *********************************************************/
public class IORuntimeException extends BaseException {
    public IORuntimeException() {
        super();
    }

    public IORuntimeException(String message) {
        super(message);
    }

    public IORuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public IORuntimeException(Throwable cause) {
        super(cause);
    }

    protected IORuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
