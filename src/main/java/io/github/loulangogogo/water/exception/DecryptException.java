package io.github.loulangogogo.water.exception;

/*********************************************************
 ** 解密异常
 **
 ** @author loulan
 ** @since 8
 *********************************************************/
public class DecryptException extends BaseException {
    public DecryptException() {
        super();
    }

    public DecryptException(String message) {
        super(message);
    }

    public DecryptException(String message, Throwable cause) {
        super(message, cause);
    }

    public DecryptException(Throwable cause) {
        super(cause);
    }

    protected DecryptException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
