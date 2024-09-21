package io.github.loulangogogo.water.test.io;

import io.github.loulangogogo.water.io.FileTool;
import org.apache.commons.io.filefilter.MagicNumberFileFilter;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/*********************************************************
 ** 文件工具类测试类
 **
 ** @author loulan
 ** @since 8
 *********************************************************/
public class FileToolTest {

    @Test
    public void test02() {
    }

    @Test
    public void getFileMimeType() throws FileNotFoundException {
        File file = new File("target/test-classes/test.PNG");
        System.out.println(FileTool.getFileMimeType(new FileInputStream(file)));
    }
}
