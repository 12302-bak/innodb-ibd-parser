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
 *                          @since  1.0
 *                          @author 12302
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TRX_SYS_RSEGS_SLOT extends Common<TRX_SYS_RSEGS_SLOT> {

    public int space_id;

    public int page_no;

    // not sure ?
    // public byte[] legacy;

    @Override
    protected TRX_SYS_RSEGS_SLOT doDecodeBytes(TRX_SYS_RSEGS_SLOT trxSysRsegsSlot, MappedByteBuffer ibd) {
        space_id = ibd.getInt();
        page_no = ibd.getInt();
        //legacy = new byte[8]; ibd.get(legacy);
        return this;
    }
}
