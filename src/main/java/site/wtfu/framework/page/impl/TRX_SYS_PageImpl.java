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

    /**
     * 第 0 号、第 33～127 号回滚段属于一类。
     * 其中第 0 号回滚段必须在系统表空间中（就是说第 0 号回滚段对应的 Rollback Segment Header 页面必须在系统表空间中），
     * 第 33～127 号回滚段既可以在系统表空间中，也可以在自己配置的 undo 表空间中，关于怎么配置我们稍后再说。
     * 如果一个事务在执行过程中由于对普通表的记录做了改动需要分配 Undo页面 链表时，必须从这一类的段中分配相应的 undo slot 。
     *
     * 第 1～32 号回滚段属于一类。这些回滚段必须在临时表空间（对应着数据目录中的 ibtmp1 文件）中。
     * 如果一个事务在执行过程中由于对临时表的记录做了改动需要分配 Undo页面 链表时，必须从这一类的段中分配相应的 undo slot 。
     */
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
