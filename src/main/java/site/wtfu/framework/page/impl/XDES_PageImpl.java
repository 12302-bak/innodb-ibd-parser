package site.wtfu.framework.page.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import site.wtfu.framework.common.Common;
import site.wtfu.framework.common.ConstVal;
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
@EqualsAndHashCode(callSuper = true)
public final class XDES_PageImpl extends Common<XDES_PageImpl> {

    // 112 byte
    public byte[] empty_header;

    // 256 个
    public XDES_Entry[] xdes = new XDES_Entry[256];

    // 5986 byte
    public byte[] empty_space;

    @Override
    protected XDES_PageImpl doDecodeBytes(XDES_PageImpl xdesPage, MappedByteBuffer ibd) {
        empty_header = new byte[112]; ibd.get(empty_header);

        for (int i = 0; i < xdes.length; i++) {
            xdes[i] = new XDES_Entry().decodeBytes(ibd);
        }

        int size = AlignmentUtil.align(ibd.position()) - ConstVal.fil_trailer_length - ibd.position();
        empty_space = new byte[size]; ibd.get(empty_space);
        return this;
    }
}