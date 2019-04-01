package com.ericsson.li.binary2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BPParser {
    private static Map<String, BPFieldType> bpFieldTypeMap = new HashMap<String, BPFieldType>();
    private String source;
    private BPParser() {}
    {
        bpFieldTypeMap.put(BPFieldType.S8.getName(), BPFieldType.S8);
        bpFieldTypeMap.put(BPFieldType.U8.getName(), BPFieldType.U8);
        bpFieldTypeMap.put(BPFieldType.S16.getName(), BPFieldType.S16);
        bpFieldTypeMap.put(BPFieldType.U16.getName(), BPFieldType.U16);
        bpFieldTypeMap.put(BPFieldType.S24.getName(), BPFieldType.S24);
        bpFieldTypeMap.put(BPFieldType.U24.getName(), BPFieldType.U24);
        bpFieldTypeMap.put(BPFieldType.S32.getName(), BPFieldType.S32);
        bpFieldTypeMap.put(BPFieldType.U32.getName(), BPFieldType.U32);
        bpFieldTypeMap.put(BPFieldType.S64.getName(), BPFieldType.S64);
        bpFieldTypeMap.put(BPFieldType.U64.getName(), BPFieldType.U64);
        bpFieldTypeMap.put(BPFieldType.STRING.getName(), BPFieldType.STRING);
        bpFieldTypeMap.put(BPFieldType.SKIP.getName(), BPFieldType.SKIP);
        bpFieldTypeMap.put(BPFieldType.BYTE.getName(), BPFieldType.BYTE);

    }
    private List<BPField> bpFieldList = new LinkedList<BPField>();
    public static BPParser build(String source) {
        BPParser bpParser = new BPParser();
        bpParser.setSource(source);
        return bpParser;
    }

    private void parse() throws BPException {
        //String str = "u16 hello.a;  byte:hello.a; skip:5";
        String[] strings = source.split(";");
        for (String s : strings) {
            String tmp = s.trim();
            if (tmp.length() == 0) {
                continue;
            }
            BPField bpField = buildBPField(tmp);
            bpFieldList.add(bpField);
        }
    }

    private BPField buildBPField(String src) throws BPException {
        BPField bpField = new BPField();
        String[] strings = src.split("\\s+");
        parseByBPFieldType(bpField, strings[0].trim());

        if (strings.length == 1) {

        }

        //trim().split("\\s+");

        return bpField;
    }

    private void parseByBPFieldType(BPField bpField, String src) throws BPException {
        String[] strings = src.split(":");
        BPFieldType bpFieldType = bpFieldTypeMap.get(strings[0].trim());
        if (null == bpFieldType) {
            throw new BPException(BPErrorEnum.UNSUPPORTED_TYPE);
        }


    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
