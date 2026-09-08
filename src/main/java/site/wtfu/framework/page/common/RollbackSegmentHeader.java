package site.wtfu.framework.page.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import site.wtfu.framework.common.Common;

import java.nio.MappedByteBuffer;

/**
 *
 * Copyright https://wtfu.site Inc. All Rights Reserved.
 *
 * @date 2026/9/8
 *                          @since 1.0
 *                          @author 12302
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RollbackSegmentHeader extends Common<RollbackSegmentHeader> {

    public int TRX_RSEG_MAX_SIZE;

    public int TRX_RSEG_HISTORY_SIZE;

    public ListBaseNode TRX_RSEG_HISTORY;

    public SegmentHeader TRX_SYS_FSEG_HEADER;

    public int[] TRX_RSEG_UNDO_SLOTS = new int[1024];

    @Override
    protected RollbackSegmentHeader doDecodeBytes(RollbackSegmentHeader transactionSystemHeader, MappedByteBuffer ibd) {
        TRX_RSEG_MAX_SIZE = ibd.getInt();
        TRX_RSEG_HISTORY_SIZE = ibd.getInt();
        TRX_RSEG_HISTORY = new ListBaseNode().decodeBytes(ibd);
        TRX_SYS_FSEG_HEADER = new SegmentHeader().decodeBytes(ibd);
        for (int i = 0; i < TRX_RSEG_UNDO_SLOTS.length; i++) {
            TRX_RSEG_UNDO_SLOTS[i] = ibd.getInt();
        }
        return this;
    }
}
