package site.wtfu.framework.page.impl;

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
public class SYS_PageImpl extends Common<SYS_PageImpl> {

    public DataDictionaryHeader dict_hdr;

    public int unused;

    public SegmentHeader fseg_header;

    // 16272 byte, rather than ~16336~
    public byte[] empty_space;

    @Override
    protected SYS_PageImpl doDecodeBytes(SYS_PageImpl sysPage, MappedByteBuffer ibd) {
        dict_hdr = new DataDictionaryHeader().decodeBytes(ibd);
        unused = ibd.getInt();
        fseg_header = new SegmentHeader().decodeBytes(ibd);

        int size = AlignmentUtil.align(ibd.position()) - ConstVal.fil_trailer_length - ibd.position();
        empty_space = new byte[size]; ibd.get(empty_space);
        return sysPage;
    }
}
