package site.wtfu.framework.frm.table.record_format_demo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import site.wtfu.framework.utils.RemUtil;

import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;

/**
 *
 * Copyright https://wtfu.site Inc. All Rights Reserved.
 *
 * @date 2026/9/3
 *                          @since  1.0
 *                          @author 12302
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LeafRecord extends CommonRecord {

    public byte[] trx_id;

    public byte[] roll_ptr;

    public String c2;

    public String c3;

    public String c4;

    @Override
    protected CommonRecord doDecodeBytes(MappedByteBuffer ibd, byte[] nullValueField, ByteBuffer eeBuf) {

        trx_id = new byte[6]; ibd.get(trx_id);
        roll_ptr = new byte[7]; ibd.get(roll_ptr);

        // 先判断可为 NULL 值；然后读取长度字节
        // 是否为NULL
        byte[] data = new byte[ RemUtil.readLength(eeBuf) ]; ibd.get(data);
        c2 = new String(data);

        // c3
        boolean aNull;
        aNull = RemUtil.isNull(0, nullValueField);
        if(!aNull){
            data = new byte[ 10 ]; ibd.get(data);
            c3 = new String(data);
        }

        aNull = RemUtil.isNull(1, nullValueField);
        if(!aNull){
            data = new byte[ RemUtil.readLength(eeBuf) ]; ibd.get(data);
            c4 = new String(data);
        }

        return this;
    }
}
