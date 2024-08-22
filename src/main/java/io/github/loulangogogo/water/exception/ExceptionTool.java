package io.github.loulangogogo.water.exception;

import io.github.loulangogogo.water.tool.ObjectTool;

import java.io.PrintWriter;
import java.io.StringWriter;

/*********************************************************
 ** 异常工具类
 ** 
 ** @author loulan
 ** @since 8
 *********************************************************/
public class ExceptionTool {
    
    /**
     * 获取异常的堆栈跟踪信息。
     *
     * @param throwable 需要获取堆栈跟踪信息的异常对象。如果为null，则返回null。
     * @return 异常的堆栈跟踪信息字符串。如果在获取过程中发生异常，则返回null。
     * @author     :loulan
     * */
    public static String getStackTrace(Throwable throwable) {
        // 检查传入的异常对象是否为null
        if (ObjectTool.isNull(throwable)) {
            return null;
        }
        try {
            // 使用StringWriter和PrintWriter来捕获异常的堆栈信息
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            throwable.printStackTrace(pw); // 打印堆栈信息到PrintWriter
            sw.close(); // 关闭StringWriter
            pw.close(); // 关闭PrintWriter
            return sw.toString(); // 返回堆栈信息字符串
        }catch (Exception ex){
            ex.printStackTrace(); // 在捕获异常时，打印到标准错误输出
            return null; // 发生异常时返回null
        }
    }
}
