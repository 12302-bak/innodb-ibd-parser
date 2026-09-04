package site.wtfu.framework.utils;

import java.nio.ByteBuffer;

/**
 *
 * Copyright https://wtfu.site Inc. All Rights Reserved.
 *
 * @date 2026/9/4
 *                          @since  1.0
 *                          @author 12302
 *
 */
public class RemUtil {

    public static boolean isNull(int index, byte[] nullValueField){
        int group = index / 8 ;
        short value = (short) (nullValueField[nullValueField.length - 1 - group] & 0xFF);
        index = index % 8;

        byte compValue = 0x01;
        for (int i = 0; i < index; i++) { compValue <<= 1;}

        return (value & (short)(compValue & 0xFF)) >> index == 1;
    }

    public static int readLength(ByteBuffer eeBuf){
        int unit = 1;

        if(eeBuf.remaining() == 1) { return eeBuf.get(); }
        eeBuf.position(eeBuf.limit() - 2);
        byte[] lengths = new byte[2]; eeBuf.get(lengths);
        byte last = lengths[1];
        int len = last;

        if( (last & 0x80) >> 7 == 1) {
            int other = lengths[0] & 0xFF;
            len = ((last & 0x3f) << 8) + other;
            unit = 2;
        }

        eeBuf.limit(eeBuf.limit() - unit);
        eeBuf.position(0);
        return len;
    }

    public static void main(String[] args) {
        // testReadLength();
        testIsNull();
    }

    public static void testIsNull(){
        byte[] nullValueField = new byte[]{(byte) 0b0010_0100, (byte) 0b1010_0011};
        boolean aNull = isNull(0, nullValueField);
        aNull = isNull(1, nullValueField);
        aNull = isNull(2, nullValueField);
        aNull = isNull(5, nullValueField);
        aNull = isNull(6, nullValueField);
        aNull = isNull(7, nullValueField);
        aNull = isNull(10, nullValueField);
        aNull = isNull(11, nullValueField);
        aNull = isNull(13, nullValueField);
        System.out.println(aNull);
    }

    public static void testReadLength(){
        byte[] bytes = new byte[]{0x12, 0x20, 0x18, (byte) 0x80, (byte) 0x80};
        //bytes = new byte[]{0x12, 0x20, 0x18, (byte) 0x2c, (byte) 0x81};
        ByteBuffer wrap = ByteBuffer.wrap(bytes);
        int len = readLength(wrap);
        len = readLength(wrap);
        len = readLength(wrap);
        System.out.println(len);
    }
}
