package site.wtfu.framework.frm.table.film;

import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class NonLeafRecord extends CommonRecord {

    public short film_id;

    private int pageNo;

    @Override
    protected CommonRecord doDecodeBytes(MappedByteBuffer ibd, byte[] nullValueField, ByteBuffer eeBuf) {
        film_id = ibd.getShort();
        pageNo = ibd.getInt();
        return this;
    }
}
