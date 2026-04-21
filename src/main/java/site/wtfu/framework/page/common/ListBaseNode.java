package site.wtfu.framework.page.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import site.wtfu.framework.common.Common;

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
public class ListBaseNode extends Common<ListBaseNode> {

    public int length;

    public FIL_ADDR[] XDES_FLST_NODE = new FIL_ADDR[2];

    @Override
    protected ListBaseNode doDecodeBytes(ListBaseNode listBaseNode, MappedByteBuffer ibd) {
        length = ibd.getInt();
        XDES_FLST_NODE[0] = new FIL_ADDR().decodeBytes(ibd);
        XDES_FLST_NODE[1] = new FIL_ADDR().decodeBytes(ibd);
        return listBaseNode;
    }
}
