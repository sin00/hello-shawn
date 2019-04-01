package com.ericsson.li.binary;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class ByteFrame {
    ArrayList<ByteBuf> bufArrayList = new ArrayList<ByteBuf>();

    public void writeBuffer(byte[] array) {
        bufArrayList.add(Unpooled.copiedBuffer(array));
    }

    public void writeBuffer(byte[] array, int offset, int length) {
        bufArrayList.add(Unpooled.copiedBuffer(array, offset, length));
    }

    public void writeBuffer(ByteBuf buffer) {
        bufArrayList.add(buffer);
    }

    public void writeBuffer(ByteBuffer buffer) {
        bufArrayList.add(Unpooled.copiedBuffer(buffer));
    }

    public void writeBuffer(byte[]... arrays) {
        bufArrayList.add(Unpooled.copiedBuffer(arrays));
    }

    public void writeBuffer(ByteBuf... buffers) {
        bufArrayList.add(Unpooled.copiedBuffer(buffers));
    }

    public void writeBuffer(ByteBuffer... buffers) {
        bufArrayList.add(Unpooled.copiedBuffer(buffers));
    }

    public void writeBuffer(CharSequence string, Charset charset) {
        bufArrayList.add(Unpooled.copiedBuffer(string, charset));
    }

    public void writeBuffer(
            CharSequence string, int offset, int length, Charset charset) {
        bufArrayList.add(Unpooled.copiedBuffer(string, offset, length, charset));
    }

    public void writeBuffer(char[] array, Charset charset) {
        bufArrayList.add(Unpooled.copiedBuffer(array, charset));
    }

    public void writeBuffer(char[] array, int offset, int length, Charset charset) {
        bufArrayList.add(Unpooled.copiedBuffer(array, offset, length, charset));
    }

    private void writeBuffer(CharBuffer buffer, Charset charset) {
        bufArrayList.add(Unpooled.copiedBuffer(buffer, charset));
    }

    public void writeByte(int value) {
        bufArrayList.add(Unpooled.buffer(1).writeByte(value));
    }

    public void writeBytes(int... values) {
        if (values.length <= 0) {
            return;
        }
        ByteBuf byteBuf = Unpooled.buffer(values.length);
        for (int value : values) {
            byteBuf.writeByte(value);
        }
        bufArrayList.add(byteBuf);
    }

    public void writeInt(int value) {
        bufArrayList.add(Unpooled.copyInt(value));
    }

    public void writeInt(int... values) {
        bufArrayList.add(Unpooled.copyInt(values));
    }

    public void writeShort(int value) {
        bufArrayList.add(Unpooled.copyShort(value));
    }

    public void writeShort(short... values) {
        bufArrayList.add(Unpooled.copyShort(values));
    }

    public void writeShort(int... values) {
        bufArrayList.add(Unpooled.copyShort(values));
    }

    public void writeMedium(int value) {
        bufArrayList.add(Unpooled.copyMedium(value));
    }

    public void writeMedium(int... values) {
        bufArrayList.add(Unpooled.copyMedium(values));
    }

    public void writeLong(long value) {
        bufArrayList.add(Unpooled.copyLong(value));
    }

    public void writeLong(long... values) {
        bufArrayList.add(Unpooled.copyLong(values));
    }

    public void writeBoolean(boolean value) {
        bufArrayList.add(Unpooled.copyBoolean(value));
    }

    public void writeBoolean(boolean... values) {
        bufArrayList.add(Unpooled.copyBoolean(values));
    }

    public void writeFloat(float value) {
        bufArrayList.add(Unpooled.copyFloat(value));
    }

    public void writeFloat(float... values) {
        bufArrayList.add(Unpooled.copyFloat(values));
    }

    public void writeDouble(double value) {
        bufArrayList.add(Unpooled.copyDouble(value));
    }

    public void writeDouble(double... values) {
        bufArrayList.add(Unpooled.copyDouble(values));
    }

    public ByteBuf toByteBuf() {
        return Unpooled.wrappedBuffer(bufArrayList.toArray(new ByteBuf[bufArrayList.size()]));
    }

    public byte[] toBytes() {
        return Unpooled.wrappedBuffer((ByteBuf[]) bufArrayList.toArray()).array();
    }

    public ByteBlock toByteBlock() {
        return new ByteBlock(toByteBuf());
    }
}
