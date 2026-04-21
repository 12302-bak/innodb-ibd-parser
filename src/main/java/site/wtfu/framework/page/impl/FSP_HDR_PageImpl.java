package site.wtfu.framework.page.impl;

import lombok.Data;
import site.wtfu.framework.common.Common;
import site.wtfu.framework.common.ConstVal;
import site.wtfu.framework.page.common.FileSpaceHeader;
import site.wtfu.framework.page.common.XDES_Entry;
import site.wtfu.framework.utils.AlignmentUtil;

import java.nio.MappedByteBuffer;

/**
 *
 * Copyright https://wtfu.site Inc. All Rights Reserved.
 *
 * @date 2026/4/20
 *                          @since  1.0
 *                          @author 12302
 *
 */
@Data
public final class FSP_HDR_PageImpl extends Common<FSP_HDR_PageImpl> {

    public FileSpaceHeader fsp_header;

    // 256 个
    public XDES_Entry[] xdes = new XDES_Entry[256];

    // 5986 byte
    public byte[] empty_space;

    @Override
    protected FSP_HDR_PageImpl doDecodeBytes(FSP_HDR_PageImpl fspHdrPage, MappedByteBuffer ibd) {
        fsp_header = new FileSpaceHeader().decodeBytes(ibd);

        for (int i = 0; i < xdes.length; i++) {
            xdes[i] = new XDES_Entry().decodeBytes(ibd);
        }
        int size = AlignmentUtil.align(ibd.position()) - ConstVal.fil_trailer_length - ibd.position();
        empty_space = new byte[size]; ibd.get(empty_space);
        return this;
    }
}
