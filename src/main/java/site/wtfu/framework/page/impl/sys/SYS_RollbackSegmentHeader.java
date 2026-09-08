package site.wtfu.framework.page.impl.sys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import site.wtfu.framework.common.Common;
import site.wtfu.framework.page.common.DataDictionaryHeader;
import site.wtfu.framework.page.common.RollbackSegmentHeader;
import site.wtfu.framework.page.common.SegmentHeader;

import java.nio.MappedByteBuffer;

/**
 *
 * Copyright https://wtfu.site Inc. All Rights Reserved.
 *
 * @date 2026/4/21
 *                          @since  1.0
 *                          @author 12302
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SYS_RollbackSegmentHeader extends Common<SYS_RollbackSegmentHeader> {

    public RollbackSegmentHeader rollbackSegmentHeader;

    @Override
    protected SYS_RollbackSegmentHeader doDecodeBytes(SYS_RollbackSegmentHeader sysPage, MappedByteBuffer ibd) {
        rollbackSegmentHeader = new RollbackSegmentHeader().decodeBytes(ibd);
        return sysPage;
    }
}
