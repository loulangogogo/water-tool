package io.github.loulangogogo.water.exception;

/*********************************************************
 ** 反射异常
 ** 
 ** @author loulan
 ** @since 8
 *********************************************************/
public class ReflectException extends BaseException{
    /**
     * 构造一个无消息的反射异常
     *
     * @author :loulan
     */
    public ReflectException() {
        super();
    }

    /**
     * 构造一个带消息的反射异常
     *
     * @param message 异常消息
     * @author :loulan
     */
    public ReflectException(String message) {
        super(message);
    }

    /**
     * 构造一个带消息和原因的反射异常
     *
     * @param message 异常消息
     * @param cause   异常原因
     * @author :loulan
     */
    public ReflectException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造一个带原因的反射异常
     *
     * @param cause 异常原因
     * @author :loulan
     */
    public ReflectException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造一个带完整参数的反射异常
     *
     * @param message            异常消息
     * @param cause              异常原因
     * @param enableSuppression  是否启用抑制
     * @param writableStackTrace 是否可写堆栈跟踪
     * @author :loulan
     */
    protected ReflectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
