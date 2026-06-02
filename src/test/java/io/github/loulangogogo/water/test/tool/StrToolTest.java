package io.github.loulangogogo.water.test.tool;

import io.github.loulangogogo.water.enums.MaskingDataTypeEnum;
import io.github.loulangogogo.water.tool.StrTool;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * 测试{@link io.github.loulangogogo.water.tool.StrTool}类的功能。
 */
public class StrToolTest {

    /**
     * 测试isEmpty方法，验证null值返回true。
     */
    @Test
    public void testIsEmpty_null() {
        assertTrue(StrTool.isEmpty(null));
    }

    /**
     * 测试isEmpty方法，验证空字符串返回true。
     */
    @Test
    public void testIsEmpty_empty() {
        assertTrue(StrTool.isEmpty(""));
    }

    /**
     * 测试isEmpty方法，验证非空字符串返回false。
     */
    @Test
    public void testIsEmpty_nonEmpty() {
        assertFalse(StrTool.isEmpty("hello"));
    }

    /**
     * 测试isNotEmpty方法，验证非空和空字符串的判断。
     */
    @Test
    public void testIsNotEmpty() {
        assertTrue(StrTool.isNotEmpty("hello"));
        assertFalse(StrTool.isNotEmpty(""));
    }

    /**
     * 测试isBlank方法，验证null、空串、空白字符及非空白字符串的判断。
     */
    @Test
    public void testIsBlank() {
        assertTrue(StrTool.isBlank(null));
        assertTrue(StrTool.isBlank(""));
        assertTrue(StrTool.isBlank(" "));
        assertTrue(StrTool.isBlank("\t\n"));
        assertFalse(StrTool.isBlank("hello"));
    }

    /**
     * 测试isNotBlank方法，验证非空白和空白字符串的判断。
     */
    @Test
    public void testIsNotBlank() {
        assertTrue(StrTool.isNotBlank("hello"));
        assertFalse(StrTool.isNotBlank(" "));
    }

    /**
     * 测试isMatch方法，验证正则匹配成功、失败及null参数处理。
     */
    @Test
    public void testIsMatch() {
        assertTrue(StrTool.isMatch("abc123", "\\w+"));
        assertFalse(StrTool.isMatch("abc123", "^\\d+$"));
        assertFalse(StrTool.isMatch(null, "\\w+"));
        assertFalse(StrTool.isMatch("abc", null));
    }

    /**
     * 测试splitToList方法，验证使用转换函数分割字符串。
     */
    @Test
    public void testSplitToList_withFunction() {
        List<Integer> result = StrTool.splitToList("1,2,3", ",", Integer::parseInt);
        assertEquals(3, result.size());
        assertEquals(Integer.valueOf(1), result.get(0));
        assertEquals(Integer.valueOf(3), result.get(2));
    }

    /**
     * 测试splitToList方法，验证简单字符串分割。
     */
    @Test
    public void testSplitToList_simple() {
        List<String> result = StrTool.splitToList("a,b,c", ",");
        assertEquals(3, result.size());
        assertEquals("a", result.get(0));
        assertEquals("c", result.get(2));
    }

    /**
     * 测试splitToList方法，验证空字符串分割返回空列表。
     */
    @Test
    public void testSplitToList_empty() {
        List<String> result = StrTool.splitToList("", ",");
        assertTrue(result.isEmpty());
    }

    /**
     * 测试substringsBetween方法，验证提取多个匹配子串。
     */
    @Test
    public void testSubstringsBetween() {
        String[] results = StrTool.substringsBetween("[a][b][c]", "[", "]");
        assertArrayEquals(new String[]{"a", "b", "c"}, results);
    }

    /**
     * 测试substringBetween方法，验证使用开闭标签提取子串。
     */
    @Test
    public void testSubstringBetween_tag() {
        assertEquals("hello", StrTool.substringBetween("[hello]world[hello]", "[", "]"));
    }

    /**
     * 测试substringBetween方法，验证使用自定义开闭字符提取子串。
     */
    @Test
    public void testSubstringBetween_openClose() {
        assertEquals("hello", StrTool.substringBetween("<hello>", "<", ">"));
    }

    /**
     * 测试substringBeforeLast方法，验证获取最后一个分隔符之前的子串。
     */
    @Test
    public void testSubstringBeforeLast() {
        assertEquals("com.example", StrTool.substringBeforeLast("com.example.Test", "."));
    }

    /**
     * 测试substringBefore方法，验证获取第一个分隔符之前的子串。
     */
    @Test
    public void testSubstringBefore() {
        assertEquals("com", StrTool.substringBefore("com.example.Test", "."));
    }

    /**
     * 测试substringAfter方法，验证获取第一个分隔符之后的子串。
     */
    @Test
    public void testSubstringAfter() {
        assertEquals("example.Test", StrTool.substringAfter("com.example.Test", "."));
    }

    /**
     * 测试substringAfterLast方法，验证获取最后一个分隔符之后的子串。
     */
    @Test
    public void testSubstringAfterLast() {
        assertEquals("Test", StrTool.substringAfterLast("com.example.Test", "."));
    }

    /**
     * 测试substring方法，验证按索引截取字符串。
     */
    @Test
    public void testSubstring() {
        assertEquals("ell", StrTool.substring("hello", 1, 4));
        assertEquals("lo", StrTool.substring("hello", 3));
    }

    /**
     * 测试camelToUnderline方法，验证驼峰转下划线的各种场景。
     */
    @Test
    public void testCamelToUnderline() {
        // Bug fix: should not produce leading underscore
        assertEquals("user_name", StrTool.camelToUnderline("UserName"));
        assertEquals("user_name", StrTool.camelToUnderline("userName"));
        assertEquals("user", StrTool.camelToUnderline("user"));
        assertEquals("", StrTool.camelToUnderline(""));
        assertEquals("", StrTool.camelToUnderline(null));
    }

    /**
     * 测试underlineToCamel方法，验证下划线转驼峰的各种场景。
     */
    @Test
    public void testUnderlineToCamel() {
        assertEquals("userName", StrTool.underlineToCamel("user_name"));
        assertEquals("user", StrTool.underlineToCamel("user"));
        assertEquals("", StrTool.underlineToCamel(""));
        assertEquals("", StrTool.underlineToCamel(null));
    }

    /**
     * 测试equals方法，验证忽略大小写和区分大小写的比较。
     */
    @Test
    public void testEquals_ignoreCase() {
        assertTrue(StrTool.equals("Hello", "hello", true));
        assertFalse(StrTool.equals("Hello", "hello", false));
        assertTrue(StrTool.equals(null, null, false));
        assertFalse(StrTool.equals("a", null, false));
    }

    /**
     * 测试equals方法，验证默认区分大小写的比较。
     */
    @Test
    public void testEquals_default() {
        assertTrue(StrTool.equals("a", "a"));
        assertFalse(StrTool.equals("A", "a"));
    }

    /**
     * 测试format方法，验证使用Map占位符替换。
     */
    @Test
    public void testFormat_withMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "loulan");
        map.put("age", 18);
        String result = StrTool.format("name={name}, age={age}", map);
        assertEquals("name=loulan, age=18", result);
    }

    /**
     * 测试format方法，验证忽略null值的Map占位符替换。
     */
    @Test
    public void testFormat_withMap_ignoreNull() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "loulan");
        map.put("age", null);
        String result = StrTool.format("name={name}, age={age}", map, true);
        assertEquals("name=loulan, age={age}", result);
    }

    /**
     * 测试format方法，验证使用可变参数占位符替换。
     */
    @Test
    public void testFormat_withObjects() {
        String result = StrTool.format("Hello {}, you are {}", "loulan", 18);
        assertEquals("Hello loulan, you are 18", result);
    }

    /**
     * 测试format方法，验证传入null字符串返回null。
     */
    @Test
    public void testFormat_withNullStr() {
        assertNull(StrTool.format(null, "test"));
    }

    /**
     * 测试format方法，验证参数不足时多余占位符保留。
     */
    @Test
    public void testFormat_excessPlaceholders() {
        String result = StrTool.format("{}-{}-{}", "a", "b");
        assertEquals("a-b-{}", result);
    }

    /**
     * 测试valueOf方法，验证基本类型转字符串。
     */
    @Test
    public void testValueOf() {
        assertEquals("123", StrTool.valueOf(123));
        assertEquals("true", StrTool.valueOf(true));
    }

    /**
     * 测试trim方法，验证移除字符串中指定字符。
     */
    @Test
    public void testTrim() {
        assertEquals("helloworld", StrTool.trim("hello world", " "));
    }

    /**
     * 测试regexStrs方法，验证正则表达式提取多个匹配子串。
     */
    @Test
    public void testRegexStrs_pattern() {
        List<String> result = StrTool.regexStrs("abc123def456", "\\d+");
        assertEquals(2, result.size());
        assertEquals("123", result.get(0));
        assertEquals("456", result.get(1));
    }

    /**
     * 测试regexStr方法，验证正则表达式提取第一个匹配子串。
     */
    @Test
    public void testRegexStr_single() {
        String result = StrTool.regexStr("abc123def", "\\d+");
        assertEquals("123", result);
    }

    // Data masking tests
    /**
     * 测试dataMasking方法，验证手机号脱敏。
     */
    @Test
    public void testDataMasking_phone() {
        String result = StrTool.dataMasking("13812345678", MaskingDataTypeEnum.PHONE);
        assertEquals("138****5678", result);
    }

    /**
     * 测试dataMasking方法，验证三字姓名脱敏。
     */
    @Test
    public void testDataMasking_name_threeChars() {
        String result = StrTool.dataMasking("张三丰", MaskingDataTypeEnum.NAME);
        assertEquals("张*丰", result);
    }

    /**
     * 测试dataMasking方法，验证两字姓名脱敏。
     */
    @Test
    public void testDataMasking_name_twoChars() {
        String result = StrTool.dataMasking("张三", MaskingDataTypeEnum.NAME);
        assertEquals("张*", result);
    }

    /**
     * 测试dataMasking方法，验证邮箱脱敏。
     */
    @Test
    public void testDataMasking_email() {
        String result = StrTool.dataMasking("test@example.com", MaskingDataTypeEnum.EMAIL);
        assertEquals("tes***@example.com", result);
    }

    /**
     * 测试dataMasking方法，验证身份证号脱敏。
     */
    @Test
    public void testDataMasking_idCard() {
        String result = StrTool.dataMasking("110101199001011234", MaskingDataTypeEnum.ID_CARD);
        assertEquals("110101******234", result);
    }

    /**
     * 测试dataMasking方法，验证银行卡号脱敏。
     */
    @Test
    public void testDataMasking_bankCard() {
        String result = StrTool.dataMasking("6222021234567890123", MaskingDataTypeEnum.BANK_CARD);
        // Bank card: show first 4, mask middle 6, show last remaining
        assertNotNull(result);
        assertTrue(result.startsWith("6222"));
        assertTrue(result.contains("******"));
    }

    /**
     * 测试dataMasking方法，验证null值脱敏返回null。
     */
    @Test
    public void testDataMasking_null() {
        assertNull(StrTool.dataMasking(null, MaskingDataTypeEnum.PHONE));
    }

    // ==================== startWith ====================

    /**
     * 测试startWith方法，验证区分大小写的前缀判断。
     */
    @Test
    public void testStartWith_caseSensitive() {
        assertTrue(StrTool.startWith("HelloWorld", "Hello", false));
        assertFalse(StrTool.startWith("HelloWorld", "hello", false));
        assertFalse(StrTool.startWith("HelloWorld", "World", false));
    }

    /**
     * 测试startWith方法，验证忽略大小写的前缀判断。
     */
    @Test
    public void testStartWith_ignoreCase() {
        assertTrue(StrTool.startWith("HelloWorld", "hello", true));
        assertTrue(StrTool.startWith("HelloWorld", "HELLO", true));
        assertTrue(StrTool.startWith("HelloWorld", "Hello", true));
        assertFalse(StrTool.startWith("HelloWorld", "World", true));
    }

    /**
     * 测试startWith方法，验证str为null时返回false。
     */
    @Test
    public void testStartWith_nullStr() {
        assertFalse(StrTool.startWith(null, "Hello", false));
        assertFalse(StrTool.startWith(null, "Hello", true));
    }

    /**
     * 测试startWith方法，验证prefix为null时返回false。
     */
    @Test
    public void testStartWith_nullPrefix() {
        assertFalse(StrTool.startWith("HelloWorld", null, false));
        assertFalse(StrTool.startWith("HelloWorld", null, true));
    }

    /**
     * 测试startWith方法，验证prefix为空串时返回true。
     */
    @Test
    public void testStartWith_emptyPrefix() {
        assertTrue(StrTool.startWith("HelloWorld", "", false));
        assertTrue(StrTool.startWith("HelloWorld", "", true));
    }

    /**
     * 测试startWith方法，验证str为空串且prefix非空时返回false。
     */
    @Test
    public void testStartWith_emptyStr() {
        assertFalse(StrTool.startWith("", "Hello", false));
        assertFalse(StrTool.startWith("", "Hello", true));
        // 空串以空串为前缀应返回true
        assertTrue(StrTool.startWith("", "", false));
    }

    /**
     * 测试startWith方法，验证中文字符串的前缀判断。
     */
    @Test
    public void testStartWith_chinese() {
        assertTrue(StrTool.startWith("楼兰工具有", "楼兰", false));
        assertFalse(StrTool.startWith("楼兰工具有", "工具", false));
        assertTrue(StrTool.startWith("楼兰工具有", "楼兰", true));
    }

    // ==================== startsWith ====================

    /**
     * 测试startsWith方法，验证区分大小写的前缀判断。
     */
    @Test
    public void testStartsWith_match() {
        assertTrue(StrTool.startsWith("HelloWorld", "Hello"));
        assertFalse(StrTool.startsWith("HelloWorld", "hello"));
    }

    /**
     * 测试startsWith方法，验证null处理。
     */
    @Test
    public void testStartsWith_null() {
        assertFalse(StrTool.startsWith(null, "Hello"));
        assertFalse(StrTool.startsWith("HelloWorld", null));
    }

    /**
     * 测试startsWith方法，验证空串处理。
     */
    @Test
    public void testStartsWith_empty() {
        assertTrue(StrTool.startsWith("HelloWorld", ""));
        assertFalse(StrTool.startsWith("", "Hello"));
        assertTrue(StrTool.startsWith("", ""));
    }

    /**
     * 测试startsWith方法，验证中文字符串。
     */
    @Test
    public void testStartsWith_chinese() {
        assertTrue(StrTool.startsWith("楼兰工具", "楼兰"));
        assertFalse(StrTool.startsWith("楼兰工具", "工具"));
    }

    // ==================== startsWithIgnoreCase ====================

    /**
     * 测试startsWithIgnoreCase方法，验证忽略大小写的前缀判断。
     */
    @Test
    public void testStartsWithIgnoreCase_match() {
        assertTrue(StrTool.startsWithIgnoreCase("HelloWorld", "hello"));
        assertTrue(StrTool.startsWithIgnoreCase("HelloWorld", "HELLO"));
        assertTrue(StrTool.startsWithIgnoreCase("HelloWorld", "Hello"));
        assertFalse(StrTool.startsWithIgnoreCase("HelloWorld", "World"));
    }

    /**
     * 测试startsWithIgnoreCase方法，验证null处理。
     */
    @Test
    public void testStartsWithIgnoreCase_null() {
        assertFalse(StrTool.startsWithIgnoreCase(null, "Hello"));
        assertFalse(StrTool.startsWithIgnoreCase("HelloWorld", null));
    }

    /**
     * 测试startsWithIgnoreCase方法，验证空串处理。
     */
    @Test
    public void testStartsWithIgnoreCase_empty() {
        assertTrue(StrTool.startsWithIgnoreCase("HelloWorld", ""));
        assertFalse(StrTool.startsWithIgnoreCase("", "Hello"));
        assertTrue(StrTool.startsWithIgnoreCase("", ""));
    }

    /**
     * 测试startsWithIgnoreCase方法，验证中文字符串。
     */
    @Test
    public void testStartsWithIgnoreCase_chinese() {
        assertTrue(StrTool.startsWithIgnoreCase("楼兰工具", "楼兰"));
        assertFalse(StrTool.startsWithIgnoreCase("楼兰工具", "工具"));
    }

    // ==================== indexOf ====================

    /**
     * 测试indexOf方法，验证正常查找返回正确位置。
     */
    @Test
    public void testIndexOf_found() {
        assertEquals(0, StrTool.indexOf("HelloWorld", "Hello"));
        assertEquals(5, StrTool.indexOf("HelloWorld", "World"));
        assertEquals(2, StrTool.indexOf("HelloWorld", "llo"));
    }

    /**
     * 测试indexOf方法，验证未找到返回-1。
     */
    @Test
    public void testIndexOf_notFound() {
        assertEquals(-1, StrTool.indexOf("HelloWorld", "xyz"));
        assertEquals(-1, StrTool.indexOf("HelloWorld", "hello"));  // 区分大小写
    }

    /**
     * 测试indexOf方法，验证null处理返回-1。
     */
    @Test
    public void testIndexOf_null() {
        assertEquals(-1, StrTool.indexOf(null, "Hello"));
        assertEquals(-1, StrTool.indexOf("HelloWorld", null));
        assertEquals(-1, StrTool.indexOf(null, null));
    }

    /**
     * 测试indexOf方法，验证空串和空子串处理。
     */
    @Test
    public void testIndexOf_empty() {
        assertEquals(0, StrTool.indexOf("HelloWorld", ""));
        assertEquals(-1, StrTool.indexOf("", "Hello"));
        assertEquals(0, StrTool.indexOf("", ""));
    }

    /**
     * 测试indexOf方法，验证中文字符串查找。
     */
    @Test
    public void testIndexOf_chinese() {
        assertEquals(0, StrTool.indexOf("楼兰工具类", "楼兰"));
        assertEquals(2, StrTool.indexOf("楼兰工具类", "工具"));
        assertEquals(-1, StrTool.indexOf("楼兰工具类", "java"));
    }

    // ==================== lastIndexOf ====================

    /**
     * 测试lastIndexOf方法，验证正常查找返回最后一次出现的位置。
     */
    @Test
    public void testLastIndexOf_found() {
        assertEquals(8, StrTool.lastIndexOf("HelloWorld", "l"));
        assertEquals(5, StrTool.lastIndexOf("HelloWorld", "World"));
        assertEquals(3, StrTool.lastIndexOf("abcabc", "abc"));  // 最后一次在索引3
    }

    /**
     * 测试lastIndexOf方法，验证未找到返回-1。
     */
    @Test
    public void testLastIndexOf_notFound() {
        assertEquals(-1, StrTool.lastIndexOf("HelloWorld", "xyz"));
        assertEquals(-1, StrTool.lastIndexOf("HelloWorld", "world"));  // 区分大小写
    }

    /**
     * 测试lastIndexOf方法，验证null处理返回-1。
     */
    @Test
    public void testLastIndexOf_null() {
        assertEquals(-1, StrTool.lastIndexOf(null, "Hello"));
        assertEquals(-1, StrTool.lastIndexOf("HelloWorld", null));
        assertEquals(-1, StrTool.lastIndexOf(null, null));
    }

    /**
     * 测试lastIndexOf方法，验证空串和空子串处理。
     */
    @Test
    public void testLastIndexOf_empty() {
        assertEquals(10, StrTool.lastIndexOf("HelloWorld", ""));  // 空串返回字符串长度
        assertEquals(-1, StrTool.lastIndexOf("", "Hello"));
        assertEquals(0, StrTool.lastIndexOf("", ""));
    }

    /**
     * 测试lastIndexOf方法，验证中文字符串查找。
     */
    @Test
    public void testLastIndexOf_chinese() {
        assertEquals(2, StrTool.lastIndexOf("楼兰楼兰", "楼兰"));  // 最后一次在索引2
        assertEquals(-1, StrTool.lastIndexOf("楼兰工具", "java"));
    }

    // ==================== lastIndexOfIgnoreCase ====================

    /**
     * 测试lastIndexOfIgnoreCase方法，验证忽略大小写查找最后一次出现的位置。
     */
    @Test
    public void testLastIndexOfIgnoreCase_found() {
        assertEquals(8, StrTool.lastIndexOfIgnoreCase("HelloWorld", "L"));
        assertEquals(5, StrTool.lastIndexOfIgnoreCase("HelloWorld", "world"));
        assertEquals(3, StrTool.lastIndexOfIgnoreCase("abcABC", "ABC"));  // 最后一次在索引3
    }

    /**
     * 测试lastIndexOfIgnoreCase方法，验证未找到返回-1。
     */
    @Test
    public void testLastIndexOfIgnoreCase_notFound() {
        assertEquals(-1, StrTool.lastIndexOfIgnoreCase("HelloWorld", "xyz"));
    }

    /**
     * 测试lastIndexOfIgnoreCase方法，验证null处理返回-1。
     */
    @Test
    public void testLastIndexOfIgnoreCase_null() {
        assertEquals(-1, StrTool.lastIndexOfIgnoreCase(null, "Hello"));
        assertEquals(-1, StrTool.lastIndexOfIgnoreCase("HelloWorld", null));
        assertEquals(-1, StrTool.lastIndexOfIgnoreCase(null, null));
    }

    /**
     * 测试lastIndexOfIgnoreCase方法，验证空串和空子串处理。
     */
    @Test
    public void testLastIndexOfIgnoreCase_empty() {
        assertEquals(10, StrTool.lastIndexOfIgnoreCase("HelloWorld", ""));
        assertEquals(-1, StrTool.lastIndexOfIgnoreCase("", "Hello"));
        assertEquals(0, StrTool.lastIndexOfIgnoreCase("", ""));
    }

    /**
     * 测试lastIndexOfIgnoreCase方法，验证中文字符串查找。
     */
    @Test
    public void testLastIndexOfIgnoreCase_chinese() {
        assertEquals(2, StrTool.lastIndexOfIgnoreCase("楼兰楼兰", "楼兰"));
        assertEquals(-1, StrTool.lastIndexOfIgnoreCase("楼兰工具", "java"));
    }
}
