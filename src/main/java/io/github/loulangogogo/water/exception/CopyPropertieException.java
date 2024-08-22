package io.github.loulangogogo.water.exception;

/*********************************************************
 ** 属性复制异常
 ** 
 ** @author 楼兰
 ** @since JDK-1.8
 *********************************************************/
public class CopyPropertieException extends BaseException {
    public CopyPropertieException() {
        super();
    }

    public CopyPropertieException(String message) {
        super(message);
    }

    public CopyPropertieException(String message, Throwable cause) {
        super(message, cause);
    }

    public CopyPropertieException(Throwable cause) {
        super(cause);
    }

    protected CopyPropertieException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
