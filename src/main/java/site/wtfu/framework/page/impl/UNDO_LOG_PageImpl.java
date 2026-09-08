package site.wtfu.framework.page.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import site.wtfu.framework.common.Common;
import site.wtfu.framework.common.ConstVal;
import site.wtfu.framework.log.undo.UndoLogHeader;
import site.wtfu.framework.log.undo.UndoLogSegmentHeader;
import site.wtfu.framework.log.undo.UndoPageHeader;
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
public class UNDO_LOG_PageImpl extends Common<UNDO_LOG_PageImpl> {

    public UndoPageHeader undoPageHeader;

    //public UndoLogSegmentHeader undoLogSegmentHeader;

    //public UndoLogHeader undoLogHeader;
    public byte[] empty_space;

    @Override
    protected UNDO_LOG_PageImpl doDecodeBytes(UNDO_LOG_PageImpl undoLogPage, MappedByteBuffer ibd) {
        undoPageHeader = new UndoPageHeader().decodeBytes(ibd);

        int size = AlignmentUtil.align(ibd.position()) - ConstVal.fil_trailer_length - ibd.position();
        empty_space = new byte[size]; ibd.get(empty_space);
        return this;
    }
}
