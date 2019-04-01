package com.ericsson.li.binary;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public final class ByteUtil {

    public static final String NEWLINE = "\n";
    private static final char[] HEXDUMP_TABLE = new char[256 * 4];
    public static final byte[] EMPTY_BYTES = {};

    public static ByteUtil getInstance() {return  INSTANCE;}

    static {
        final char[] DIGITS = "0123456789abcdef".toCharArray();
        for (int i = 0; i < 256; i ++) {
            HEXDUMP_TABLE[ i << 1     ] = DIGITS[i >>> 4 & 0x0F];
            HEXDUMP_TABLE[(i << 1) + 1] = DIGITS[i       & 0x0F];
        }
    }

    public static ByteBlock newByteBlock() {return new ByteBlock(Unpooled.buffer());}
    public static ByteBlock newByteBlock(byte[] b) {
        return new ByteBlock(b);
    }
    public static ByteBlock newByteBlock(ByteBuf byteBuf) {
        return new ByteBlock(byteBuf);
    }
    public static ByteFrame newByteFrame() {return new ByteFrame();}

    public static String hexDump(byte[] array) {
        return hexDump(array, 0, array.length);
    }
    private static String hexDump(byte[] array, int fromIndex, int length) {
        if (length < 0) {
            throw new IllegalArgumentException("length: " + length);
        }
        if (length == 0) {
            return "";
        }

        int endIndex = fromIndex + length;
        char[] buf = new char[length << 1];

        int srcIdx = fromIndex;
        int dstIdx = 0;
        for (; srcIdx < endIndex; srcIdx ++, dstIdx += 2) {
            System.arraycopy(
                    HEXDUMP_TABLE, (array[srcIdx] & 0xFF) << 1,
                    buf, dstIdx, 2);
        }

        return new String(buf);
    }



    public static short swapShort(short value) {
        return Short.reverseBytes(value);
    }
    public static int swapMedium(int value) {
        int swapped = value << 16 & 0xff0000 | value & 0xff00 | value >>> 16 & 0xff;
        if ((swapped & 0x800000) != 0) {
            swapped |= 0xff000000;
        }
        return swapped;
    }
    public static int swapInt(int value) {
        return Integer.reverseBytes(value);
    }
    public static long swapLong(long value) {
        return Long.reverseBytes(value);
    }


    public static int decodeHexNibble(final char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        if (c >= 'A' && c <= 'F') {
            return c - ('A' - 0xA);
        }
        if (c >= 'a' && c <= 'f') {
            return c - ('a' - 0xA);
        }
        return -1;
    }

    public static byte decodeHexByte(CharSequence s, int pos) {
        int hi = decodeHexNibble(s.charAt(pos));
        int lo = decodeHexNibble(s.charAt(pos + 1));
        if (hi == -1 || lo == -1) {
            throw new IllegalArgumentException(String.format(
                    "invalid hex byte '%s' at index %d of '%s'", s.subSequence(pos, pos + 2), pos, s));
        }
        return (byte) ((hi << 4) + lo);
    }

    public static byte[] decodeHexDump(CharSequence hexDump) {
        return decodeHexDump(hexDump, 0, hexDump.length());
    }

    public static byte[] decodeHexDump(CharSequence hexDump, int fromIndex, int length) {
        if (length < 0 || (length & 1) != 0) {
            throw new IllegalArgumentException("length: " + length);
        }
        if (length == 0) {
            return EMPTY_BYTES;
        }
        byte[] bytes = new byte[length >>> 1];
        for (int i = 0; i < length; i += 2) {
            bytes[i >>> 1] = decodeHexByte(hexDump, fromIndex + i);
        }
        return bytes;
    }

    private ByteUtil() { }
    private static ByteUtil INSTANCE = new ByteUtil();
}
