package com.ericsson.li.binary2;


public enum BPFieldType {
    S8("s8"),
    U8("u8"),
    S16("s16"),
    U16("u16"),
    S24("s24"),
    U24("u24"),
    S32("s32"),
    U32("u32"),
    S64("s64"),
    U64("u64"),
    STRING("string"),
    BYTE("byte"),
    SKIP("skip");


    private String name;

    private BPFieldType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
