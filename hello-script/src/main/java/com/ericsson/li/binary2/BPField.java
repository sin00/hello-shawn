package com.ericsson.li.binary2;

import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlExpression;

public class BPField {
    private String name;
    private byte[] buff;
    private int size;
    //private BPBitOrder bitOrder = BPBitOrder.MSB;
    private BPEndian endian = BPEndian.BIG;

    private BPFieldType fieldType;
    private boolean isArray;

    //private boolean needCal = true;
    private String sizeExp;
    private JexlExpression je;

    private int calSize(JexlContext jc) {
        if (null != je) {
            size = (Integer) je.evaluate(jc);
        }
        return size;
    }

    private int getBuffLength() {
        return buff.length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getBuff() {
        return buff;
    }

    public void setBuff(byte[] buff) {
        this.buff = buff;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public BPEndian getEndian() {
        return endian;
    }

    public void setEndian(BPEndian endian) {
        this.endian = endian;
    }

    public BPFieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(BPFieldType fieldType) {
        this.fieldType = fieldType;
    }

    public boolean isArray() {
        return isArray;
    }

    public void setArray(boolean array) {
        isArray = array;
    }

    public String getSizeExp() {
        return sizeExp;
    }

    public void setSizeExp(String sizeExp) {
        this.sizeExp = sizeExp;
    }

    public JexlExpression getJe() {
        return je;
    }

    public void setJe(JexlExpression je) {
        this.je = je;
    }
}
