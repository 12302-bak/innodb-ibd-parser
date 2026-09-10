package site.wtfu.framework.frm.table.customer;

import lombok.Data;
import lombok.EqualsAndHashCode;
import site.wtfu.framework.frm.common.DB_ROLL_PTR;
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

    public short customer_id;

    public String email;

    public byte[] trx_id;

    public DB_ROLL_PTR roll_ptr;

    public byte store_id;

    public String first_name;

    public String last_name;

    public short address_id;

    public byte active;

    public byte[] create_date;

    public byte[] last_update;

    @Override
    protected CommonRecord doDecodeBytes(MappedByteBuffer ibd, byte[] nullValueField, ByteBuffer eeBuf) {

        byte[] data;
        int nullValueIndex = 0;

        // primary key
        customer_id = ibd.getShort();
        data = new byte[ RemUtil.readLength(eeBuf) ]; ibd.get(data);
        email = new String(data);

        trx_id = new byte[6]; ibd.get(trx_id);
        byte[] rp = new byte[7]; ibd.get(rp);
        roll_ptr = new DB_ROLL_PTR(rp);

        // other column
        store_id = ibd.get();

        data = new byte[ RemUtil.readLength(eeBuf) ]; ibd.get(data);
        first_name = new String(data);

        boolean aNull;
        aNull = RemUtil.isNull(nullValueIndex++, nullValueField);
        if(!aNull){
            data = new byte[ RemUtil.readLength(eeBuf) ]; ibd.get(data);
            last_name = new String(data);
        }

        address_id = ibd.getShort();
        active = (byte) (ibd.get() & 0x7F);

        aNull = RemUtil.isNull(nullValueIndex++, nullValueField);
        if(!aNull){
            data = new byte[ 5 ]; ibd.get(data);
            create_date = data;
        }

        data = new byte[ 4 ]; ibd.get(data);
        last_update = data;

        return this;
    }
}
