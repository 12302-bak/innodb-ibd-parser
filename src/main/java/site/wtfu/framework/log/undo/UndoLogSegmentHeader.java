package site.wtfu.framework.log.undo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import site.wtfu.framework.common.Common;
import site.wtfu.framework.page.common.ListBaseNode;
import site.wtfu.framework.page.common.SegmentHeader;

import java.nio.MappedByteBuffer;

/**
 *
 * Copyright https://wtfu.site Inc. All Rights Reserved.
 *
 * @date 2026/9/8
 *                          @since  1.0
 *                          @author 12302
 *
 * storage/innobase/include/trx0undo.h
 */
// /** The offset of the undo log segment header on the first page of the undo
//log segment */
//
//#define	TRX_UNDO_SEG_HDR	(TRX_UNDO_PAGE_HDR + TRX_UNDO_PAGE_HDR_SIZE)
///** Undo log segment header */
///* @{ */
///*-------------------------------------------------------------*/
//#define	TRX_UNDO_STATE		0	/*!< TRX_UNDO_ACTIVE, ... */
//
//#ifndef UNIV_INNOCHECKSUM
//
//#define	TRX_UNDO_LAST_LOG	2	/*!< Offset of the last undo log header
//					on the segment header page, 0 if
//					none */
//#define	TRX_UNDO_FSEG_HEADER	4	/*!< Header for the file segment which
//					the undo log segment occupies */
//#define	TRX_UNDO_PAGE_LIST	(4 + FSEG_HEADER_SIZE)
//					/*!< Base node for the list of pages in
//					the undo log segment; defined only on
//					the undo log segment's first page */
///*-------------------------------------------------------------*/
///** Size of the undo log segment header */
//#define TRX_UNDO_SEG_HDR_SIZE	(4 + FSEG_HEADER_SIZE + FLST_BASE_NODE_SIZE)
///* @} */
@Data
@EqualsAndHashCode(callSuper = true)
public class UndoLogSegmentHeader extends Common<UndoLogSegmentHeader> {

    public short TRX_UNDO_STATE;

    public short TRX_UNDO_LAST_LOG;

    public SegmentHeader TRX_UNDO_FSEG_HEADER;

    public ListBaseNode TRX_UNDO_PAGE_LIST;

    @Override
    protected UndoLogSegmentHeader doDecodeBytes(UndoLogSegmentHeader undoLogSegmentHeader, MappedByteBuffer ibd) {
        TRX_UNDO_STATE = ibd.getShort();
        TRX_UNDO_LAST_LOG = ibd.getShort();
        TRX_UNDO_FSEG_HEADER = new SegmentHeader().decodeBytes(ibd);
        TRX_UNDO_PAGE_LIST = new ListBaseNode().decodeBytes(ibd);
        return this;
    }
}
