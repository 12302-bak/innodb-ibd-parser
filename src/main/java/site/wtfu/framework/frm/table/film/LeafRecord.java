package site.wtfu.framework.frm.table.film;

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

    public short film_id;

    public byte[] trx_id;

    public byte[] roll_ptr;

    public String title;

    public String description;

    public Byte release_year;

    public byte language_id;

    public Byte original_language_id;

    public byte rental_duration;

    public short rental_rate;

    public Short length;

    public byte[] replacement_cost;

    public byte rating;

    public Byte special_features;

    public byte[] last_update;

    @Override
    protected CommonRecord doDecodeBytes(MappedByteBuffer ibd, byte[] nullValueField, ByteBuffer eeBuf) {

        int nullValueIndex = 0;

        // read primary key
        film_id = ibd.getShort();

        trx_id = new byte[6]; ibd.get(trx_id);
        roll_ptr = new byte[7]; ibd.get(roll_ptr);

        // other column
        byte[] data = new byte[ RemUtil.readLength(eeBuf) ]; ibd.get(data);
        title = new String(data);

        boolean aNull;
        aNull = RemUtil.isNull(nullValueIndex++, nullValueField);
        if(!aNull){
            data = new byte[ RemUtil.readLength(eeBuf) ]; ibd.get(data);
            description = new String(data);
        }

        aNull = RemUtil.isNull(nullValueIndex++, nullValueField);
        if(!aNull){
            release_year = ibd.get();
        }

        language_id = ibd.get();

        aNull = RemUtil.isNull(nullValueIndex++, nullValueField);
        if(!aNull){
            original_language_id = ibd.get();
        }

        rental_duration = ibd.get();
        rental_rate = ibd.getShort();

        aNull = RemUtil.isNull(nullValueIndex++, nullValueField);
        if(!aNull){
            length = ibd.getShort();
        }

        data = new byte[3]; ibd.get(data);
        replacement_cost = data;

        rating = ibd.get();

        aNull = RemUtil.isNull(nullValueIndex++, nullValueField);
        if(!aNull){
            special_features = ibd.get();
        }

        data = new byte[4]; ibd.get(data);
        last_update = data;

        return this;
    }
}
