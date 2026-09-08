package site.wtfu.framework.page.impl.sys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import site.wtfu.framework.common.Common;
import site.wtfu.framework.common.ConstVal;
import site.wtfu.framework.page.common.DataDictionaryHeader;
import site.wtfu.framework.page.common.SegmentHeader;
import site.wtfu.framework.utils.AlignmentUtil;

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
public class SYS_DataDictionaryHeader extends Common<SYS_DataDictionaryHeader> {

    public DataDictionaryHeader dict_hdr;

    public int unused;

    public SegmentHeader fseg_header;

    @Override
    protected SYS_DataDictionaryHeader doDecodeBytes(SYS_DataDictionaryHeader sysPage, MappedByteBuffer ibd) {
        dict_hdr = new DataDictionaryHeader().decodeBytes(ibd);
        unused = ibd.getInt();
        fseg_header = new SegmentHeader().decodeBytes(ibd);

        return sysPage;
    }
}
