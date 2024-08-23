package io.github.loulangogogo.water.exception;

/*********************************************************
 ** 加密异常
 **
 ** @author loulan
 ** @since 8
 *********************************************************/
public class EncryptException extends BaseException {
    public EncryptException() {
        super();
    }

    public EncryptException(String message) {
        super(message);
    }

    public EncryptException(String message, Throwable cause) {
        super(message, cause);
    }

    public EncryptException(Throwable cause) {
        super(cause);
    }

    protected EncryptException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
