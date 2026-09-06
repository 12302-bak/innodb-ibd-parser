package site.wtfu.framework.frm.table.customer;

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
public class AuxiliaryNonLeafRecord extends CommonRecord {

    public byte store_id;

    public short address_id;

    public String last_name;

    public short customer_id;

    public String email;

    private int pageNo;

    @Override
    protected CommonRecord doDecodeBytes(MappedByteBuffer ibd, byte[] nullValueField, ByteBuffer eeBuf) {

        byte[] data;
        int nullValueIndex = 0;

        // index key
        store_id = ibd.get();
        address_id = ibd.getShort();

        boolean aNull;
        aNull = RemUtil.isNull(nullValueIndex++, nullValueField);
        if(!aNull){
            data = new byte[ RemUtil.readLength(eeBuf) ]; ibd.get(data);
            last_name = new String(data);
        }

        // primary key
        customer_id = ibd.getShort();
        data = new byte[ RemUtil.readLength(eeBuf) ]; ibd.get(data);
        email = new String(data);

        pageNo = ibd.getInt();
        return this;
    }
}
