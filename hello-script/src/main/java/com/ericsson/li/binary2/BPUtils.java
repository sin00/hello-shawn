package com.ericsson.li.binary2;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Locale;


public final class BPUtils {

  private static final Charset CHARSET_UTF8 = Charset.forName("UTF-8");


  public static byte[] strToUtf8(final String str) {
    final ByteBuffer buffer = CHARSET_UTF8.encode(str);
    final byte[] bytesArray = new byte[buffer.remaining()];
    buffer.get(bytesArray, 0, bytesArray.length);
    return bytesArray;
  }

  public static String utf8ToStr(final byte[] array) {
    return CHARSET_UTF8.decode(ByteBuffer.wrap(array)).toString();
  }

  public static boolean isNumber(final String num) {
    if (num == null || num.length() == 0) {
      return false;
    }
    final boolean firstIsDigit = Character.isDigit(num.charAt(0));
    if (!firstIsDigit && num.charAt(0) != '-') {
      return false;
    }
    boolean dig = firstIsDigit;
    for (int i = 1; i < num.length(); i++) {
      if (!Character.isDigit(num.charAt(i))) {
        return false;
      }
      dig = true;
    }
    return dig;
  }

  public static byte[] packInt(final int value) {
    if ((value & 0xFFFFFF80) == 0) {
      return new byte[] {(byte) value};
    } else if ((value & 0xFFFF0000) == 0) {
      return new byte[] {(byte) 0x80, (byte) (value >>> 8), (byte) value};
    } else {
      return new byte[] {(byte) 0x81, (byte) (value >>> 24), (byte) (value >>> 16), (byte) (value >>> 8), (byte) value};
    }
  }

/*  public static int packInt(final byte[] array, final JBBPIntCounter position, final int value) {
    if ((value & 0xFFFFFF80) == 0) {
      array[position.getAndIncrement()] = (byte) value;
      return 1;
    } else if ((value & 0xFFFF0000) == 0) {
      array[position.getAndIncrement()] = (byte) 0x80;
      array[position.getAndIncrement()] = (byte) (value >>> 8);
      array[position.getAndIncrement()] = (byte) value;
      return 3;
    }
    array[position.getAndIncrement()] = (byte) 0x81;
    array[position.getAndIncrement()] = (byte) (value >>> 24);
    array[position.getAndIncrement()] = (byte) (value >>> 16);
    array[position.getAndIncrement()] = (byte) (value >>> 8);
    array[position.getAndIncrement()] = (byte) value;
    return 5;
  }*/

/*  public static int unpackInt(final byte[] array, final JBBPIntCounter position) {
    final int code = array[position.getAndIncrement()] & 0xFF;
    if (code < 0x80) {
      return code;
    }

    final int result;
    switch (code) {
      case 0x80: {
        result = ((array[position.getAndIncrement()] & 0xFF) << 8) | (array[position.getAndIncrement()] & 0xFF);
      }
      break;
      case 0x81: {
        result = ((array[position.getAndIncrement()] & 0xFF) << 24)
            | ((array[position.getAndIncrement()] & 0xFF) << 16)
            | ((array[position.getAndIncrement()] & 0xFF) << 8)
            | (array[position.getAndIncrement()] & 0xFF);
      }
      break;
      default:
        throw new IllegalArgumentException("Unsupported packed integer prefix [0x" + Integer.toHexString(code).toUpperCase(Locale.ENGLISH) + ']');
    }
    return result;
  }*/

  public static String array2hex(final byte[] array) {
    return byteArray2String(array, "0x", ", ", true, 16);
  }

  public static String array2bin(final byte[] array) {
    return byteArray2String(array, "0b", ", ", true, 2);
  }

  public static String array2oct(final byte[] array) {
    return byteArray2String(array, "0o", ", ", true, 8);
  }

  public static String byteArray2String(final byte[] array, final String prefix, final String delimiter, final boolean brackets, final int radix) {
    if (array == null) {
      return null;
    }

    final int maxlen = Integer.toString(0xFF, radix).length();
    final String zero = "00000000";

    final String normDelim = delimiter == null ? " " : delimiter;
    final String normPrefix = prefix == null ? "" : prefix;

    final StringBuilder result = new StringBuilder(array.length * 4);

    if (brackets) {
      result.append('[');
    }

    boolean nofirst = false;

    for (final byte b : array) {
      if (nofirst) {
        result.append(normDelim);
      } else {
        nofirst = true;
      }

      result.append(normPrefix);

      final String v = Integer.toString(b & 0xFF, radix);
      if (v.length() < maxlen) {
        result.append(zero, 0, maxlen - v.length());
      }
      result.append(v.toUpperCase(Locale.ENGLISH));
    }

    if (brackets) {
      result.append(']');
    }

    return result.toString();
  }

  public static byte reverseBitsInByte(final byte value) {
    final int v = value & 0xFF;
    return (byte) ((((v * 0x0802 & 0x22110) | (v * 0x8020 & 0x88440)) * 0x10101) >> 16);
  }
/*
  public static String bin2str(final byte[] values) {
    return bin2str(values, JBBPBitOrder.LSB0, false);
  }*/

/*  public static String bin2str(final byte[] values, final boolean separateBytes) {
    return bin2str(values, JBBPBitOrder.LSB0, separateBytes);
  }*/
/*
  public static String bin2str(final byte[] values, final JBBPBitOrder bitOrder, final boolean separateBytes) {
    if (values == null) {
      return null;
    }

    final StringBuilder result = new StringBuilder(values.length * (separateBytes ? 9 : 8));

    boolean nofirst = false;
    for (final byte b : values) {
      if (separateBytes) {
        if (nofirst) {
          result.append(' ');
        } else {
          nofirst = true;
        }
      }

      int a = b;

      if (bitOrder == JBBPBitOrder.MSB0) {
        for (int i = 0; i < 8; i++) {
          result.append((a & 0x1) == 0 ? '0' : '1');
          a >>= 1;
        }
      } else {
        for (int i = 0; i < 8; i++) {
          result.append((a & 0x80) == 0 ? '0' : '1');
          a <<= 1;
        }
      }
    }

    return result.toString();
  }*/


/*  public static List<JBBPAbstractField> fieldsAsList(final JBBPAbstractField... fields) {
    final List<JBBPAbstractField> result = new ArrayList<JBBPAbstractField>();
    Collections.addAll(result, fields);
    return result;
  }*/


/*
  public static byte[] str2bin(final String values) {
    return str2bin(values, JBBPBitOrder.LSB0);
  }

*/
/*
  public static byte[] str2bin(final String values, final JBBPBitOrder bitOrder) {
    if (values == null) {
      return new byte[0];
    }

    int buff = 0;
    int cnt = 0;

    final ByteArrayOutputStream buffer = new ByteArrayOutputStream((values.length() + 7) >> 3);

    final boolean msb0 = bitOrder == JBBPBitOrder.MSB0;

    for (final char v : values.toCharArray()) {
      switch (v) {
        case '_':
        case ' ':
          continue;
        case '0':
        case 'X':
        case 'x':
        case 'Z':
        case 'z': {
          if (msb0) {
            buff >>= 1;
          } else {
            buff <<= 1;
          }
        }
        break;
        case '1': {
          if (msb0) {
            buff = (buff >> 1) | 0x80;
          } else {
            buff = (buff << 1) | 1;
          }
        }
        break;
        default:
          throw new IllegalArgumentException("Detected unsupported char '" + v + ']');
      }
      cnt++;
      if (cnt == 8) {
        buffer.write(buff);
        cnt = 0;
        buff = 0;
      }
    }
    if (cnt > 0) {
      buffer.write(msb0 ? buff >>> (8 - cnt) : buff);
    }
    return buffer.toByteArray();
  }*/

  public static String[] splitString(final String str, final char splitChar) {
    final int length = str.length();
    final StringBuilder bulder = new StringBuilder(Math.max(8, length));

    int counter = 1;
    for (int i = 0; i < length; i++) {
      if (str.charAt(i) == splitChar) {
        counter++;
      }
    }

    final String[] result = new String[counter];

    int position = 0;
    for (int i = 0; i < length; i++) {
      final char chr = str.charAt(i);
      if (chr == splitChar) {
        result[position++] = bulder.toString();
        bulder.setLength(0);
      } else {
        bulder.append(chr);
      }
    }
    if (position < result.length) {
      result[position] = bulder.toString();
    }

    return result;
  }

  public static void assertNotNull(final Object object, final String message) {
    if (object == null) {
      throw new NullPointerException(message == null ? "Object is null" : message);
    }
  }

  public static String int2msg(final int number) {
    return number + " (0x" + Long.toHexString((long) number & 0xFFFFFFFFL).toUpperCase(Locale.ENGLISH) + ')';
  }

  public static String normalizeFieldNameOrPath(final String nameOrPath) {
    assertNotNull(nameOrPath, "Name of path must not be null");
    return nameOrPath.trim().toLowerCase(Locale.ENGLISH);
  }

  public static void closeQuietly(final Closeable closeable) {
    try {
      if (closeable != null) {
        closeable.close();
      }
    } catch (IOException ex) {
      // Keep silence
    }
  }

  /*
  public static byte[] str2UnicodeByteArray(final JBBPByteOrder byteOrder, final String str) {
    final byte[] result = new byte[str.length() << 1];
    int index = 0;
    for (int i = 0; i < str.length(); i++) {
      final int val = str.charAt(i);
      switch (byteOrder) {
        case BIG_ENDIAN: {
          result[index++] = (byte) (val >> 8);
          result[index++] = (byte) val;
        }
        break;
        case LITTLE_ENDIAN: {
          result[index++] = (byte) val;
          result[index++] = (byte) (val >> 8);
        }
        break;
        default:
          throw new Error("Unexpected byte order [" + byteOrder + ']');
      }
    }
    return result;
  }*/

  public static byte[] reverseArray(final byte[] nullableArrayToBeInverted) {
    if (nullableArrayToBeInverted != null && nullableArrayToBeInverted.length > 0) {
      int indexStart = 0;
      int indexEnd = nullableArrayToBeInverted.length - 1;
      while (indexStart < indexEnd) {
        final byte a = nullableArrayToBeInverted[indexStart];
        nullableArrayToBeInverted[indexStart] = nullableArrayToBeInverted[indexEnd];
        nullableArrayToBeInverted[indexEnd] = a;
        indexStart++;
        indexEnd--;
      }
    }
    return nullableArrayToBeInverted;
  }

  public static byte[] splitInteger(final int value, final boolean valueInLittleEndian, final byte[] buffer) {
    final byte[] result;
    if (buffer == null || buffer.length < 4) {
      result = new byte[4];
    } else {
      result = buffer;
    }
    int tmpvalue = value;
    if (valueInLittleEndian) {
      for (int i = 0; i < 4; i++) {
        result[i] = (byte) tmpvalue;
        tmpvalue >>>= 8;
      }
    } else {
      for (int i = 3; i >= 0; i--) {
        result[i] = (byte) tmpvalue;
        tmpvalue >>>= 8;
      }
    }
    return result;
  }

  public static byte[] splitLong(final long value, final boolean valueInLittleEndian, final byte[] buffer) {
    final byte[] result;
    if (buffer == null || buffer.length < 8) {
      result = new byte[8];
    } else {
      result = buffer;
    }
    long tmpvalue = value;
    if (valueInLittleEndian) {
      for (int i = 0; i < 8; i++) {
        result[i] = (byte) tmpvalue;
        tmpvalue >>>= 8;
      }
    } else {
      for (int i = 7; i >= 0; i--) {
        result[i] = (byte) tmpvalue;
        tmpvalue >>>= 8;
      }
    }
    return result;
  }

  public static byte[] concat(final byte[]... arrays) {
    int len = 0;
    for (final byte[] arr : arrays) {
      len += arr.length;
    }

    final byte[] result = new byte[len];
    int pos = 0;
    for (final byte[] arr : arrays) {
      System.arraycopy(arr, 0, result, pos, arr.length);
      pos += arr.length;
    }
    return result;
  }

  public static long reverseByteOrder(long value, int numOfLowerBytesToInvert) {
    if (numOfLowerBytesToInvert < 1 || numOfLowerBytesToInvert > 8) {
      throw new IllegalArgumentException("Wrong number of bytes [" + numOfLowerBytesToInvert + ']');
    }

    long result = 0;

    int offsetInResult = (numOfLowerBytesToInvert - 1) * 8;

    while (numOfLowerBytesToInvert-- > 0) {
      final long thebyte = value & 0xFF;
      value >>>= 8;
      result |= (thebyte << offsetInResult);
      offsetInResult -= 8;
    }

    return result;
  }

  public static String double2str(final double doubleValue, final int radix) {
    if (radix != 10 && radix != 16) {
      throw new IllegalArgumentException("Illegal radix [" + radix + ']');
    }

    final String result;
    if (radix == 16) {
      String converted = Double.toHexString(doubleValue);
      boolean minus = converted.startsWith("-");
      if (minus) {
        converted = converted.substring(1);
      }
      if (converted.startsWith("0x")) {
        converted = converted.substring(2);
      }
      result = (minus ? '-' + converted : converted).toUpperCase(Locale.ENGLISH);
    } else {
      result = Double.toString(doubleValue);
    }
    return result;
  }

  public static String float2str(final float floatValue, final int radix) {
    if (radix != 10 && radix != 16) {
      throw new IllegalArgumentException("Illegal radix [" + radix + ']');
    }
    final String result;
    if (radix == 16) {
      String converted = Double.toHexString(floatValue);
      boolean minus = converted.startsWith("-");
      if (minus) {
        converted = converted.substring(1);
      }
      if (converted.startsWith("0x")) {
        converted = converted.substring(2);
      }
      result = (minus ? '-' + converted : converted).toUpperCase(Locale.ENGLISH);
    } else {
      result = Double.toString(floatValue);
    }
    return result;
  }

  public static String ulong2str(final long ulongValue, final int radix, final char[] charBuffer) {
    if (radix < 2 || radix > 36) {
      throw new IllegalArgumentException("Illegal radix [" + radix + ']');
    }

    if (ulongValue == 0) {
      return "0";
    } else {
      final String result;
      if (ulongValue > 0) {
        result = Long.toString(ulongValue, radix).toUpperCase(Locale.ENGLISH);
      } else {
        final char[] buffer = charBuffer == null || charBuffer.length < 64 ? new char[64] : charBuffer;
        int pos = buffer.length;
        long topPart = ulongValue >>> 32;
        long bottomPart = (ulongValue & 0xFFFFFFFFL) + ((topPart % radix) << 32);
        topPart /= radix;
        while ((bottomPart | topPart) > 0) {
          final int val = (int) (bottomPart % radix);
          buffer[--pos] = (char) (val < 10 ? '0' + val : 'A' + val - 10);
          bottomPart = (bottomPart / radix) + ((topPart % radix) << 32);
          topPart /= radix;
        }
        result = new String(buffer, pos, buffer.length - pos);
      }
      return result;
    }
  }

  public static String ensureMinTextLength(final String text, final int neededLen, final char ch, final int mode) {
    final int number = neededLen - text.length();
    if (number <= 0) {
      return text;
    }

    final StringBuilder result = new StringBuilder(neededLen);
    switch (mode) {
      case 0: {
        for (int i = 0; i < number; i++) {
          result.append(ch);
        }
        result.append(text);
      }
      break;
      case 1: {
        result.append(text);
        for (int i = 0; i < number; i++) {
          result.append(ch);
        }
      }
      break;
      default: {
        int leftField = number / 2;
        int rightField = number - leftField;
        while (leftField-- > 0) {
          result.append(ch);
        }
        result.append(text);
        while (rightField-- > 0) {
          result.append(ch);
        }
      }
      break;
    }
    return result.toString();
  }

  public static String removeLeadingZeros(final String str) {
    String result = str;
    if (str != null && str.length() != 0) {
      int startIndex = 0;
      while (startIndex < str.length() - 1) {
        final char ch = str.charAt(startIndex);
        if (ch != '0') {
          break;
        }
        startIndex++;
      }
      if (startIndex > 0) {
        result = str.substring(startIndex);
      }
    }
    return result;
  }

  public static String removeTrailingZeros(final String str) {
    String result = str;
    if (str != null && str.length() != 0) {
      int endIndex = str.length();
      while (endIndex > 1) {
        final char ch = str.charAt(endIndex - 1);
        if (ch != '0') {
          break;
        }
        endIndex--;
      }
      if (endIndex < str.length()) {
        result = str.substring(0, endIndex);
      }
    }
    return result;
  }

  public static boolean arrayStartsWith(final byte[] array, final byte[] str) {
    boolean result = false;
    if (array.length >= str.length) {
      result = true;
      int index = str.length;
      while (--index >= 0) {
        if (array[index] != str[index]) {
          result = false;
          break;
        }
      }
    }
    return result;
  }

  public static boolean arrayEndsWith(final byte[] array, final byte[] str) {
    boolean result = false;
    if (array.length >= str.length) {
      result = true;
      int index = str.length;
      int arrindex = array.length;
      while (--index >= 0) {
        if (array[--arrindex] != str[index]) {
          result = false;
          break;
        }
      }
    }
    return result;
  }

  public static int makeMask(final int value) {
    if (value == 0) {
      return 0;
    }
    if ((value & 0x80000000) != 0) {
      return 0xFFFFFFFF;
    }
    int msk = 1;
    do {
      msk <<= 1;
    }
    while (msk <= value);
    return msk - 1;
  }

  public static boolean equals(final Object o1, final Object o2) {
    if (o1 == o2) {
      return true;
    }
    if (o1 == null || o2 == null) {
      return false;
    }
    return o1.equals(o2);
  }

}
