package site.wtfu.framework.log.undo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import site.wtfu.framework.common.Common;
import site.wtfu.framework.page.common.FIL_ADDR;

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
// /** The offset of the undo log page header on pages of the undo log */
//#define	TRX_UNDO_PAGE_HDR	FSEG_PAGE_DATA
///*-------------------------------------------------------------*/
///** Transaction undo log page header offsets */
///* @{ */
//#define	TRX_UNDO_PAGE_TYPE	0	/*!< TRX_UNDO_INSERT or
//					TRX_UNDO_UPDATE */
//#define	TRX_UNDO_PAGE_START	2	/*!< Byte offset where the undo log
//					records for the LATEST transaction
//					start on this page (remember that
//					in an update undo log, the first page
//					can contain several undo logs) */
//#define	TRX_UNDO_PAGE_FREE	4	/*!< On each page of the undo log this
//					field contains the byte offset of the
//					first free byte on the page */
//#define TRX_UNDO_PAGE_NODE	6	/*!< The file list node in the chain
//					of undo log pages */
///*-------------------------------------------------------------*/
//#define TRX_UNDO_PAGE_HDR_SIZE	(6 + FLST_NODE_SIZE)
//					/*!< Size of the transaction undo
//					log page header, in bytes */
///* @} */
@Data
@EqualsAndHashCode(callSuper = true)
public class UndoPageHeader extends Common<UndoPageHeader> {

    public short TRX_UNDO_PAGE_TYPE;

    public short TRX_UNDO_PAGE_START;

    public short TRX_UNDO_PAGE_FREE;

    public FIL_ADDR[] TRX_UNDO_PAGE_NODE = new FIL_ADDR[2];;

    @Override
    protected UndoPageHeader doDecodeBytes(UndoPageHeader undoPageHeader, MappedByteBuffer ibd) {

        TRX_UNDO_PAGE_TYPE = ibd.getShort();
        TRX_UNDO_PAGE_START = ibd.getShort();
        TRX_UNDO_PAGE_FREE = ibd.getShort();

        TRX_UNDO_PAGE_NODE[0] = new FIL_ADDR().decodeBytes(ibd);
        TRX_UNDO_PAGE_NODE[1] = new FIL_ADDR().decodeBytes(ibd);
        return this;
    }
}
