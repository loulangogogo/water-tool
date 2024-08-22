package io.github.loulangogogo.water.enums;

import io.github.loulangogogo.water.io.IoTool;
import io.github.loulangogogo.water.tool.CharsetTool;
import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;

/*********************************************************
 ** 文件魔数类，根据文件的首部字节来获取文件身份。
 ** 可以防止用户修改文件后缀而无法判断文件的类型数据。
 ** 【该类方法来自apache的poi】
 ** 
 ** @author loulan
 ** @since JDK-1.8
 *********************************************************/
public enum FileMagic {
    /**
     * OLE2 / BIFF8+ stream used for Office 97 and higher documents
     */
    OLE2(0xE11AB1A1E011CFD0L),
    /**
     * OOXML / ZIP stream - The first 4 bytes of an OOXML file, used in detection
     */
    OOXML(0x50, 0x4b, 0x03, 0x04),
    /**
     * XML file - The first 5 bytes of a raw XML file, used in detection
     */
    XML(0x3c, 0x3f, 0x78, 0x6d, 0x6c),
    /**
     * BIFF2 raw stream - for Excel 2
     */
    BIFF2(
            0x09, 0x00, // sid=0x0009
            0x04, 0x00, // size=0x0004
            0x00, 0x00, // unused
            '?', 0x00  // '?' = multiple values
    ),
    /**
     * BIFF3 raw stream - for Excel 3
     */
    BIFF3(
            0x09, 0x02, // sid=0x0209
            0x06, 0x00, // size=0x0006
            0x00, 0x00, // unused
            '?', 0x00  // '?' = multiple values
    ),
    /**
     * BIFF4 raw stream - for Excel 4
     */
    BIFF4(new byte[]{
            0x09, 0x04, // sid=0x0409
            0x06, 0x00, // size=0x0006
            0x00, 0x00, // unused
            '?', 0x00  // '? = multiple values
    }, new byte[]{
            0x09, 0x04, // sid=0x0409
            0x06, 0x00, // size=0x0006
            0x00, 0x00, // unused
            0x00, 0x01
    }),
    /**
     * Old MS Write raw stream
     */
    MSWRITE(
            new byte[]{0x31, (byte) 0xbe, 0x00, 0x00},
            new byte[]{0x32, (byte) 0xbe, 0x00, 0x00}),
    /**
     * RTF document
     */
    RTF("{\\rtf"),
    /**
     * PDF document
     */
    PDF("%PDF"),
    /**
     * Some different HTML documents
     */
    HTML("<!DOCTYP",
            "<html", "\n\r<html", "\r\n<html", "\r<html", "\n<html",
            "<HTML", "\r\n<HTML", "\n\r<HTML", "\r<HTML", "\n<HTML"),
    WORD2(0xdb, 0xa5, 0x2d, 0x00),
    /**
     * JPEG image
     */
    JPEG(
            new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xDB},
            new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xE0, '?', '?', 'J', 'F', 'I', 'F', 0x00, 0x01},
            new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xEE},
            new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xE1, '?', '?', 'E', 'x', 'i', 'f', 0x00, 0x00}),
    /**
     * GIF image
     */
    GIF("GIF87a", "GIF89a"),
    /**
     * PNG Image
     */
    PNG(0x89, 'P', 'N', 'G', 0x0D, 0x0A, 0x1A, 0x0A),
    /**
     * TIFF Image
     */
    TIFF("II*\u0000", "MM\u0000*"),
    /**
     * WMF image with a placeable header
     */
    WMF(0xD7, 0xCD, 0xC6, 0x9A),
    /**
     * EMF image
     */
    EMF(1, 0, 0, 0,
            '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?',
            '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?',
            ' ', 'E', 'M', 'F'),
    /**
     * BMP image
     */
    BMP('B', 'M'),
    // keep UNKNOWN always as last enum!
    /**
     * UNKNOWN magic
     */
    UNKNOWN(new byte[0]);

    // update this if a longer pattern is added
    static final int MAX_PATTERN_LENGTH = 44;

    final byte[][] magic;

    FileMagic(long magic) {
        this.magic = new byte[1][8];
        putLong(this.magic[0], 0, magic);
    }

    FileMagic(int... magic) {
        byte[] one = new byte[magic.length];
        for (int i = 0; i < magic.length; i++) {
            one[i] = (byte) (magic[i] & 0xFF);
        }
        this.magic = new byte[][]{one};
    }

    FileMagic(byte[]... magic) {
        this.magic = magic;
    }

    FileMagic(String... magic) {
        this.magic = new byte[magic.length][];
        int i = 0;
        for (String s : magic) {
            this.magic[i++] = s.getBytes(CharsetTool.CHARSET_1252);
        }
    }

    /**
     * 通过字节数组获取文件魔数
     *
     * @param magic 字节数组
     * @return 魔数没举
     * @author :loulan
     */
    public static FileMagic valueOf(byte[] magic) {
        for (FileMagic fm : values()) {
            for (byte[] ma : fm.magic) {
                // don't try to match if the given byte-array is too short
                // for this pattern anyway
                if (magic.length < ma.length) {
                    continue;
                }

                if (findMagic(ma, magic)) {
                    return fm;
                }
            }
        }
        return UNKNOWN;
    }

    private static boolean findMagic(byte[] expected, byte[] actual) {
        int i = 0;
        for (byte expectedByte : expected) {
            if (actual[i++] != expectedByte && expectedByte != '?') {
                return false;
            }
        }
        return true;
    }


    /**
     * 通过文件获取文件的魔数
     *
     * @param inp 文件
     * @return 魔数没举
     * @throws IOException io异常
     * @author :loulan
     */
    public static FileMagic valueOf(final File inp) throws IOException {
        try (InputStream fis = Files.newInputStream(inp.toPath())) {
            // read as many bytes as possible, up to the required number of bytes
            byte[] data = new byte[MAX_PATTERN_LENGTH];
            int read = IOUtils.read(fis, data, 0, MAX_PATTERN_LENGTH);
            // only use the bytes that could be read
            data = Arrays.copyOf(data, read);

            return FileMagic.valueOf(data);
        }
    }

    /**
     * 通过流获取文件的魔数。【流必须支持标记/重置】<p>
     * 如果不确定你的流是否支持标记/重置，可以使用{@link #prepareToCheckMagic(InputStream)}来进行转换。<p>
     * 如果方法返回的是{@link FileMagic#UNKNOWN}有可能是枚举值不存在或流的首部字节存在垃圾字节。
     *
     * @param inp 支持标记/重置的流
     * @return 文件魔数
     * @throws IOException io异常
     * @author :loulan
     */
    public static FileMagic valueOf(InputStream inp) throws IOException {
        if (!inp.markSupported()) {
            throw new IOException("getFileMagic() only operates on streams which support mark(int)");
        }

        // Grab the first bytes of this stream
        byte[] data = IoTool.peekFirstNBytes(inp, MAX_PATTERN_LENGTH);

        return FileMagic.valueOf(data);
    }

    /**
     * 检查你的流是否可以进行标记/重置，如果不支持就进行转换。
     *
     * @param stream 流
     * @return 可以标记/重置的流
     * @author :loulan
     */
    public static InputStream prepareToCheckMagic(InputStream stream) {
        if (stream.markSupported()) {
            return stream;
        }
        // we used to process the data via a PushbackInputStream, but user code could provide a too small one
        // so we use a BufferedInputStream instead now
        return new BufferedInputStream(stream);
    }


    /**
     * 将long值转换未字节数组
     *
     * @param data   字节数组
     * @param offset 偏移位
     * @param value  要进行转换的long值
     * @author :loulan
     */
    private static void putLong(byte[] data, int offset, long value) {
        data[offset + 0] = (byte) ((value >>> 0) & 0xFF);
        data[offset + 1] = (byte) ((value >>> 8) & 0xFF);
        data[offset + 2] = (byte) ((value >>> 16) & 0xFF);
        data[offset + 3] = (byte) ((value >>> 24) & 0xFF);
        data[offset + 4] = (byte) ((value >>> 32) & 0xFF);
        data[offset + 5] = (byte) ((value >>> 40) & 0xFF);
        data[offset + 6] = (byte) ((value >>> 48) & 0xFF);
        data[offset + 7] = (byte) ((value >>> 56) & 0xFF);
    }
}