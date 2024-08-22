package io.github.loulangogogo.water.exception;

/*********************************************************
 ** 线程异常
 ** 
 ** @author loulan
 ** @since JDK-1.8
 *********************************************************/
public class ThreadException extends BaseException {
    public ThreadException() {
        super();
    }

    public ThreadException(String message) {
        super(message);
    }

    public ThreadException(String message, Throwable cause) {
        super(message, cause);
    }

    public ThreadException(Throwable cause) {
        super(cause);
    }

    protected ThreadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
