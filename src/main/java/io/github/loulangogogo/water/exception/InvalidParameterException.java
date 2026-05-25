package io.github.loulangogogo.water.exception;

/*********************************************************
 ** 无效的参数异常
 ** 
 ** @author loulan
 ** @since 8
 *********************************************************/
public class InvalidParameterException extends BaseException{
    /**
     * 构造一个无消息的无效参数异常
     *
     * @author :loulan
     */
    public InvalidParameterException() {
        super();
    }

    /**
     * 构造一个带消息的无效参数异常
     *
     * @param message 异常消息
     * @author :loulan
     */
    public InvalidParameterException(String message) {
        super(message);
    }

    /**
     * 构造一个带消息和原因的无效参数异常
     *
     * @param message 异常消息
     * @param cause   异常原因
     * @author :loulan
     */
    public InvalidParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造一个带原因的无效参数异常
     *
     * @param cause 异常原因
     * @author :loulan
     */
    public InvalidParameterException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造一个带完整参数的无效参数异常
     *
     * @param message            异常消息
     * @param cause              异常原因
     * @param enableSuppression  是否启用抑制
     * @param writableStackTrace 是否可写堆栈跟踪
     * @author :loulan
     */
    protected InvalidParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
