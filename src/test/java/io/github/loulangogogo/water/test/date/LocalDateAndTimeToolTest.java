package io.github.loulangogogo.water.test.date;

import io.github.loulangogogo.water.date.LocalDateAndTimeTool;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

/*********************************************************
 ** {@link LocalDateTime} 和 {@link LocalDate} 工具的测试类
 **
 ** @author loulan
 ** @since 8
 *********************************************************/
public class LocalDateAndTimeToolTest {

    @Test
    public void toLocalDateTime_01() {
        String dateTime = "2020-01-01 00:00:03";
        LocalDateTime localDateTime = LocalDateAndTimeTool.toLocalDateTime(dateTime,"yyyy-MM-dd HH:mm:ss");
        System.out.println(localDateTime);
    }

    @Test
    public void toLocalDate_01() {
        String date = "2020-01-01";
        LocalDate localDate = LocalDateAndTimeTool.toLocalDate(date,"yyyy-MM-dd");
        System.out.println(localDate);
    }
}
