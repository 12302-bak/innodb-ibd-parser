package site.wtfu.framework.page.impl;

import site.wtfu.framework.common.Common;
import site.wtfu.framework.common.ConstVal;
import site.wtfu.framework.page.common.FIL_ADDR;
import site.wtfu.framework.page.common.INODE_Entry;
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
public class INODE_PageImpl extends Common<INODE_PageImpl> {

    public FIL_ADDR[] fseg_inode = new FIL_ADDR[2];

    // 85 个,   85 * 192 = 16320, ~16128~
    public INODE_Entry[] inode = new INODE_Entry[85];

    // 6 byte
    public byte[] empty_space;

    @Override
    protected INODE_PageImpl doDecodeBytes(INODE_PageImpl inodePage, MappedByteBuffer ibd) {

        fseg_inode[0] = new FIL_ADDR().decodeBytes(ibd);
        fseg_inode[1] = new FIL_ADDR().decodeBytes(ibd);

        for (int i = 0; i < inode.length; i++) {
            inode[i] = new INODE_Entry().decodeBytes(ibd);
        }

        int size = AlignmentUtil.align(ibd.position()) - ConstVal.fil_trailer_length - ibd.position();
        empty_space = new byte[size]; ibd.get(empty_space);
        return inodePage;
    }
}
