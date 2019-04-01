package netty;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class ByteTest {
    @Test
    public void isNumeric() {
        System.out.println(StringUtils.isNumeric(null));
        System.out.println(StringUtils.isNumeric(""));
        System.out.println(StringUtils.isNumeric(" "));
        System.out.println(StringUtils.isNumeric("123"));
        System.out.println(StringUtils.isNumeric("12 3"));
        System.out.println(StringUtils.isNumeric("ab2c"));
        System.out.println(StringUtils.isNumeric("12-3"));
        System.out.println(StringUtils.isNumeric("12.3"));
    }

    @Test
    public void paternTest() {
        String str = "lu16       hello.a;  byte:hello.a; skip:5";
        String[] ta1 = str.split(";");
        for (String s1 : ta1) {
            System.out.println(s1 + ",length:" + s1.length());
            System.out.println(s1 + ",length:" + s1.trim().length());

            String[] ta2 = s1.trim().split("\\s+");
            for (String s2 : ta2) {
                System.out.println(s2 + "--,length:" + s2.length());
                System.out.println(s2 + "--,length:" + s2.trim().length());
            }
        }
    }

}
