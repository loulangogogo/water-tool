package io.github.loulangogogo.water.exception;

/*********************************************************
 ** sql运行时异常
 **
 ** @author loulan
 ** @since 8
 *********************************************************/
public class SQLRuntimeException extends BaseException {
    public SQLRuntimeException() {
        super();
    }

    public SQLRuntimeException(String message) {
        super(message);
    }

    public SQLRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public SQLRuntimeException(Throwable cause) {
        super(cause);
    }

    protected SQLRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
