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
///** The undo log header. There can be several undo log headers on the first
//page of an update undo log segment. */
///* @{ */
///*-------------------------------------------------------------*/
//#define	TRX_UNDO_TRX_ID		0	/*!< Transaction id */
//#define	TRX_UNDO_TRX_NO		8	/*!< Transaction number of the
//					transaction; defined only if the log
//					is in a history list */
//#define TRX_UNDO_DEL_MARKS	16	/*!< Defined only in an update undo
//					log: TRUE if the transaction may have
//					done delete markings of records, and
//					thus purge is necessary */
//#define	TRX_UNDO_LOG_START	18	/*!< Offset of the first undo log record
//					of this log on the header page; purge
//					may remove undo log record from the
//					log start, and therefore this is not
//					necessarily the same as this log
//					header end offset */
//#define	TRX_UNDO_XID_EXISTS	20	/*!< TRUE if undo log header includes
//					X/Open XA transaction identification
//					XID */
//#define	TRX_UNDO_DICT_TRANS	21	/*!< TRUE if the transaction is a table
//					create, index create, or drop
//					transaction: in recovery
//					the transaction cannot be rolled back
//					in the usual way: a 'rollback' rather
//					means dropping the created or dropped
//					table, if it still exists */
//#define TRX_UNDO_TABLE_ID	22	/*!< Id of the table if the preceding
//					field is TRUE */
//#define	TRX_UNDO_NEXT_LOG	30	/*!< Offset of the next undo log header
//					on this page, 0 if none */
//#define	TRX_UNDO_PREV_LOG	32	/*!< Offset of the previous undo log
//					header on this page, 0 if none */
//#define TRX_UNDO_HISTORY_NODE	34	/*!< If the log is put to the history
//					list, the file list node is here */
///*-------------------------------------------------------------*/
///** Size of the undo log header without XID information */
//#define TRX_UNDO_LOG_OLD_HDR_SIZE (34 + FLST_NODE_SIZE)
//
///* Note: the writing of the undo log old header is coded by a log record
//MLOG_UNDO_HDR_CREATE or MLOG_UNDO_HDR_REUSE. The appending of an XID to the
//header is logged separately. In this sense, the XID is not really a member
//of the undo log header. TODO: do not append the XID to the log header if XA
//is not needed by the user. The XID wastes about 150 bytes of space in every
//undo log. In the history list we may have millions of undo logs, which means
//quite a large overhead. */
//
///** X/Open XA Transaction Identification (XID) */
///* @{ */
///** xid_t::formatID */
//#define	TRX_UNDO_XA_FORMAT	(TRX_UNDO_LOG_OLD_HDR_SIZE)
///** xid_t::gtrid_length */
//#define	TRX_UNDO_XA_TRID_LEN	(TRX_UNDO_XA_FORMAT + 4)
///** xid_t::bqual_length */
//#define	TRX_UNDO_XA_BQUAL_LEN	(TRX_UNDO_XA_TRID_LEN + 4)
///** Distributed transaction identifier data */
//#define	TRX_UNDO_XA_XID		(TRX_UNDO_XA_BQUAL_LEN + 4)
///*--------------------------------------------------------------*/
//#define TRX_UNDO_LOG_XA_HDR_SIZE (TRX_UNDO_XA_XID + XIDDATASIZE)
//					/*!< Total size of the undo log header
//					with the XA XID */
///* @} */
@Data
@EqualsAndHashCode(callSuper = true)
public class UndoLogHeader extends Common<UndoLogHeader> {

    public long TRX_UNDO_TRX_ID;

    public long TRX_UNDO_TRX_NO;

    public short TRX_UNDO_DEL_MARKS;

    public short TRX_UNDO_LOG_START;

    public byte TRX_UNDO_XID_EXISTS;

    public byte TRX_UNDO_DICT_TRANS;

    public long TRX_UNDO_TABLE_ID;

    public short TRX_UNDO_NEXT_LOG;

    public short TRX_UNDO_PREV_LOG;

    public FIL_ADDR[] TRX_UNDO_HISTORY_NODE = new FIL_ADDR[2];
    // 140 bytes (本书不会讲述更多关于 XID 是个什么东东，有兴趣的同学可以到搜索引擎或者文档中搜一搜哈。)
    public byte[] XID;

    @Override
    protected UndoLogHeader doDecodeBytes(UndoLogHeader undoLogHeader, MappedByteBuffer ibd) {
        TRX_UNDO_TRX_ID = ibd.getLong();
        TRX_UNDO_TRX_NO = ibd.getLong();
        TRX_UNDO_DEL_MARKS = ibd.getShort();
        TRX_UNDO_LOG_START = ibd.getShort();
        TRX_UNDO_XID_EXISTS = ibd.get();
        TRX_UNDO_DICT_TRANS = ibd.get();
        TRX_UNDO_TABLE_ID = ibd.getLong();
        TRX_UNDO_NEXT_LOG = ibd.getShort();
        TRX_UNDO_PREV_LOG = ibd.getShort();

        TRX_UNDO_HISTORY_NODE[0] = new FIL_ADDR().decodeBytes(ibd);
        TRX_UNDO_HISTORY_NODE[1] = new FIL_ADDR().decodeBytes(ibd);

        // if exist XID?
        return this;
    }
}
