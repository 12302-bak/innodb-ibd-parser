package site.wtfu.framework.frm.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import site.wtfu.framework.common.Common;

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
public class RecordHeader extends Common<RecordHeader> {

    private int infoBits;

    private int nOwned;

    private short heapNo;

    private int recordType;

    private int nextRecord;

    @Override
    protected RecordHeader doDecodeBytes(RecordHeader header, MappedByteBuffer ibd) {

        // Fields packed in an 8-bit integer (LSB first):
        //  4 bits for n_owned
        //  4 bits for flags
        byte b1 = ibd.get();

        // For performance issue, we don't use EnumUtil find method
        header.setInfoBits((b1 & 0xf0) >> 4);
        header.setNOwned(b1 & 0x0f);

        // Fields packed in a 16-bit integer (LSB first):
        // 3 bits for type
        // 13 bits for heap_number
        short b2 = ibd.getShort();
        header.setRecordType(b2 & 0x07);
        header.setHeapNo((short) ((b2 & 0xfff8) >> 3));

        header.setNextRecord(ibd.getShort());
        header.setNextRecord(header.getNextRecord() + ibd.position());

        return header;
    }
}
