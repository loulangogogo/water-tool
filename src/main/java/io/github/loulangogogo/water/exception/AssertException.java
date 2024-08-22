package io.github.loulangogogo.water.exception;

/*********************************************************
 ** 断言异常
 ** 
 ** @author loulan
 ** @since JDK-1.8
 *********************************************************/
public class AssertException extends BaseException {
    public AssertException() {
        super();
    }

    public AssertException(String message) {
        super(message);
    }

    public AssertException(String message, Throwable cause) {
        super(message, cause);
    }

    public AssertException(Throwable cause) {
        super(cause);
    }

    protected AssertException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
