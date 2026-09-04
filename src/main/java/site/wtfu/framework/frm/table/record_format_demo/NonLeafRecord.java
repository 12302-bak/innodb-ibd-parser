package site.wtfu.framework.frm.table.record_format_demo;

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

    private int pageNo;

    @Override
    protected CommonRecord doDecodeBytes(MappedByteBuffer ibd, byte[] nullValueField, ByteBuffer eeBuf) {
        pageNo = ibd.getInt();
        return this;
    }
}
