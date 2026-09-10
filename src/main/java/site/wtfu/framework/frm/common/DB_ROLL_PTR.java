package site.wtfu.framework.frm.common;

/**
 *
 * Copyright https://wtfu.site Inc. All Rights Reserved.
 *
 * @date 2026/9/9
 *                          @since  1.0
 *                          @author 12302
 *
 */
// 56 bit
public class DB_ROLL_PTR {
    // 1 bit
    public boolean is_insert;
    // 7 bit
    public byte rseg_id;
    // 32 bit
    public int page_no;
    // 16 bit
    public short offset;

    public DB_ROLL_PTR(byte[] roll_ptr){
        is_insert = (roll_ptr[0] & 0x80) >> 7 == 1;
        rseg_id = (byte)(roll_ptr[0] & 0b0111_1111);
        page_no =   ((roll_ptr[1] & 0xFF) << 24) |
                    ((roll_ptr[2] & 0xFF) << 16) |
                    ((roll_ptr[3] & 0xFF) << 8)  |
                    (roll_ptr[4] & 0xFF);
        offset = (short)(((roll_ptr[5] & 0xFF) << 8)  |
                          (roll_ptr[6] & 0xFF));
    }

    public static void main(String[] args) {
        byte[] data = new byte[]{ (byte) 0xBC, 0x0, 0x0, 0x1, 0x33, 0x1, 0x10};
        data = new byte[]{ (byte) 0x3C, 0x0, 0x0, 0x1, 0x33, 0x1, 0x4C};
        //data = new byte[]{ (byte) 0xBC, 0x0, 0x0, 0x1, 0x33, 0x2, (byte) 0xA0};

        DB_ROLL_PTR dbRollPtr = new DB_ROLL_PTR(data);
        System.out.println();
    }
}
