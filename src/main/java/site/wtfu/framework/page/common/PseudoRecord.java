package site.wtfu.framework.page.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import site.wtfu.framework.common.Common;
import site.wtfu.framework.frm.common.RecordHeader;

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
public class PseudoRecord extends Common<PseudoRecord> {

    private int _limit;

    public RecordHeader recordHeader;

    public String value;

    @Override
    protected PseudoRecord doDecodeBytes(PseudoRecord pseudoRecord, MappedByteBuffer ibd) {
        _limit = ibd.position() - 5;
        recordHeader = new RecordHeader().decodeBytes(ibd, _limit);
        byte[] data = new byte[8]; ibd.get(data);
        value = new String(data);
        return pseudoRecord;
    }
}
