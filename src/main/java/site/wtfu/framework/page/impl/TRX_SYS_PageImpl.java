package site.wtfu.framework.page.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import site.wtfu.framework.common.Common;
import site.wtfu.framework.common.ConstVal;
import site.wtfu.framework.page.common.SegmentHeader;
import site.wtfu.framework.page.common.TRX_SYS_RSEGS_SLOT;
import site.wtfu.framework.utils.AlignmentUtil;

import java.nio.MappedByteBuffer;

/**
 *
 * Copyright https://wtfu.site Inc. All Rights Reserved.
 *
 * @date 2026/9/8
 *                          @since  1.0
 *                          @author 12302
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"empty_space"})
public class TRX_SYS_PageImpl extends Common<TRX_SYS_PageImpl> {

    public long TRX_SYS_TRX_ID_STORE;

    public SegmentHeader TRX_SYS_FSEG_HEADER;

    public TRX_SYS_RSEGS_SLOT[] TRX_SYS_RSEGS = new TRX_SYS_RSEGS_SLOT[128];

    public byte[] empty_space;

    @Override
    protected TRX_SYS_PageImpl doDecodeBytes(TRX_SYS_PageImpl undoLogPage, MappedByteBuffer ibd) {

        TRX_SYS_TRX_ID_STORE = ibd.getLong();
        TRX_SYS_FSEG_HEADER = new SegmentHeader().decodeBytes(ibd);

        for (int i = 0; i < TRX_SYS_RSEGS.length; i++) {
            TRX_SYS_RSEGS[i] = new TRX_SYS_RSEGS_SLOT().decodeBytes(ibd);
        }

        int size = AlignmentUtil.align(ibd.position()) - ConstVal.fil_trailer_length - ibd.position();
        empty_space = new byte[size]; ibd.get(empty_space);
        return this;
    }
}
