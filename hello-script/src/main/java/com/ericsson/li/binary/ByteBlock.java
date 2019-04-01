package com.ericsson.li.binary;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;

import java.math.BigInteger;

public class ByteBlock {
    private ByteBuf byteBuf;
    public ByteBlock(byte[] b) {
        byteBuf = Unpooled.wrappedBuffer(b);
    }

    public ByteBlock(ByteBuf byteBuf) {
        this.byteBuf = byteBuf;
    }


    public byte readS8() {
        return byteBuf.readByte();
    }

    public short readU8() {
        return byteBuf.readUnsignedByte();
    }

    public short readS16() {
        return byteBuf.readShort();
    }

    public short readS16LE() {
        return byteBuf.readShortLE();
    }

    public int readU16() {
        return byteBuf.readUnsignedShort();
    }

    public int readU16LE() {
        return byteBuf.readUnsignedShortLE();
    }

    public int readS24() {
        return byteBuf.readMedium();
    }

    public int readS24LE() {
        return byteBuf.readMediumLE();
    }

    public int readU24() {
        return byteBuf.readUnsignedMedium();
    }
    public int readU24LE() {
        return byteBuf.readUnsignedMediumLE();
    }

    public int readS32() {
        return byteBuf.readInt();
    }

    public int readS32LE() {
        return byteBuf.readIntLE();
    }

    public long readU32() {
        return byteBuf.readUnsignedInt();
    }

    public long readU32LE() {
        return byteBuf.readUnsignedIntLE();
    }

    public long readS64() {
        return byteBuf.readLong();
    }

    public long readS64LE() {
        return byteBuf.readLongLE();
    }

    public BigInteger readU64() {
        long l = byteBuf.readLong();
        if (l >= 0) {
            return BigInteger.valueOf(l);
        }
        return  BigInteger.valueOf(l & Long.MAX_VALUE).add(BigInteger.valueOf(Long.MAX_VALUE)).add(BigInteger.valueOf(1));
    }

    public BigInteger readU64LE() {
        long l = byteBuf.readLongLE();
        if (l >= 0) {
            return BigInteger.valueOf(l);
        }
        return  BigInteger.valueOf(l & Long.MAX_VALUE).add(BigInteger.valueOf(Long.MAX_VALUE)).add(BigInteger.valueOf(1));
    }

    public byte[] readBytes(int length) {
        byte[] b = new byte[length];
        byteBuf.readBytes(b);
        return b;
    }

    public byte[] readBytes() {
        return readBytes(byteBuf.readableBytes());
    }

    public String readHexBytes(int length) {
        byte[] b = readBytes(length);
        return ByteBufUtil.hexDump(b);

    }

    public String readHexBytes() {
        return readHexBytes(byteBuf.readableBytes());
    }

    public ByteBlock readBlock(int length) {
        return new ByteBlock(readBytes(length));
    }

    public ByteBlock readBlock() {
        return new ByteBlock(readBytes());
    }



    public String readString(int length) {
        if (length < 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append((char)byteBuf.readUnsignedByte());
        }

        return sb.toString();
    }

    public String readString2(int length) {
        if (length < 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(byteBuf.readChar());
        }

        return sb.toString();
    }

    public void skip(int length) {
        byteBuf.skipBytes(length);
    }

    public int getCapacity() {
        return byteBuf.capacity();
    }

    public int readerIndex() {
        return byteBuf.readerIndex();
    }

    public int readableBytes() {
        return byteBuf.readableBytes();
    }
    public String hexDump() {
        return ByteBufUtil.hexDump(byteBuf, 0, byteBuf.capacity());
    }
    public String hexDump(int index, int length) {
        return ByteBufUtil.hexDump(byteBuf, index, length);
    }

    public String hexDumpReadable() {
        return ByteBufUtil.hexDump(byteBuf);
    }

    public String prettyHexDump() {
        return ByteBufUtil.prettyHexDump(byteBuf, 0, byteBuf.capacity());
    }
    public String prettyHexDump(int index, int length) {
        return ByteBufUtil.prettyHexDump(byteBuf, index, length);
    }
    public String prettyHexDumpReadable() {
        return ByteBufUtil.prettyHexDump(byteBuf);
    }

    public byte getS8(int index) {
        return byteBuf.getByte(index);
    }

    public short getU8(int index) {
        return byteBuf.getUnsignedByte(index);
    }

    public short getS16(int index) {
        return byteBuf.getShort(index);
    }

    public short getS16LE(int index) {
        return byteBuf.getShortLE(index);
    }

    public int getU16(int index) {
        return byteBuf.getUnsignedShort(index);
    }

    public int getU16LE(int index) {
        return byteBuf.getUnsignedShortLE(index);
    }

    public int getS24(int index) {
        return byteBuf.getMedium(index);
    }

    public int getS24LE(int index) {
        return byteBuf.getMediumLE(index);
    }

    public int getU24(int index) {
        return byteBuf.getUnsignedMedium(index);
    }
    public int getU24LE(int index) {
        return byteBuf.getUnsignedMediumLE(index);
    }

    public int getS32(int index) {
        return byteBuf.getInt(index);
    }

    public int getS32LE(int index) {
        return byteBuf.getIntLE(index);
    }

    public long getU32(int index) {
        return byteBuf.getUnsignedInt(index);
    }

    public long getU32LE(int index) {
        return byteBuf.getUnsignedIntLE(index);
    }

    public long getS64(int index) {
        return byteBuf.getLong(index);
    }

    public long getS64LE(int index) {
        return byteBuf.getLongLE(index);
    }

    public BigInteger getU64(int index) {
        long l = byteBuf.getLong(index);
        if (l >= 0) {
            return BigInteger.valueOf(l);
        }
        return  BigInteger.valueOf(l & Long.MAX_VALUE).add(BigInteger.valueOf(Long.MAX_VALUE)).add(BigInteger.valueOf(1));
    }

    public BigInteger getU64LE(int index) {
        long l = byteBuf.getLongLE(index);
        if (l >= 0) {
            return BigInteger.valueOf(l);
        }
        return  BigInteger.valueOf(l & Long.MAX_VALUE).add(BigInteger.valueOf(Long.MAX_VALUE)).add(BigInteger.valueOf(1));
    }

    public byte[] getBytes() {
        return ByteBufUtil.getBytes(byteBuf, 0, byteBuf.capacity());
    }

    public byte[] getBytes(int index, int length) {
        return ByteBufUtil.getBytes(byteBuf, index, length);
    }

    public byte[] getBytesReadable() {
        return ByteBufUtil.getBytes(byteBuf);
    }

    public ByteBlock cloneBlock() {
        return new ByteBlock(getBytes());
    }

    public ByteBlock getBlock(int index, int length) {
        return new ByteBlock(getBytes(index, length));
    }

    public ByteBlock getBlockReadable() {
        return new ByteBlock(getBytesReadable());
    }



    public String getString(int index, int length) {
        if (length <= 0) {
            return null;
        }
        int last = index + length;
        StringBuilder sb = new StringBuilder();
        for (int i = index; i < last; i++) {
            sb.append((char)byteBuf.getUnsignedByte(i));
        }
        return sb.toString();
    }



    public String getString2(int index, int length) {
        if (length < 0) {
            return null;
        }
        int last = index + length * 2;
        StringBuilder sb = new StringBuilder();
        for (int i = index; i < last; i += 2) {
            sb.append(byteBuf.getChar(i));
        }

        return sb.toString();
    }

    public ByteBuf getByteBuf() {
        return byteBuf;
    }
    public short swapShort(short a) {
        return ByteBufUtil.swapShort(a);
    }
}
