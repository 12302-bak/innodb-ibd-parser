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
 * storage/innobase/include/page0page.h
 */
// Index page header starts at the first offset left free by the FIL-module */
//
//typedef	byte		page_header_t;
//#endif /* !UNIV_INNOCHECKSUM */
//
//#define	PAGE_HEADER	FSEG_PAGE_DATA	/* index page header starts at this
//				offset */
///*-----------------------------*/
//#define PAGE_N_DIR_SLOTS 0	/* number of slots in page directory */
//#define	PAGE_HEAP_TOP	 2	/* pointer to record heap top */
//#define	PAGE_N_HEAP	 4	/* number of records in the heap,
//				bit 15=flag: new-style compact page format */
//#define	PAGE_FREE	 6	/* pointer to start of page free record list */
//#define	PAGE_GARBAGE	 8	/* number of bytes in deleted records */
//#define	PAGE_LAST_INSERT 10	/* pointer to the last inserted record, or
//				NULL if this info has been reset by a delete,
//				for example */
//#define	PAGE_DIRECTION	 12	/* last insert direction: PAGE_LEFT, ... */
//#define	PAGE_N_DIRECTION 14	/* number of consecutive inserts to the same
//				direction */
//#define	PAGE_N_RECS	 16	/* number of user records on the page */
//#define PAGE_MAX_TRX_ID	 18	/* highest id of a trx which may have modified
//				a record on the page; trx_id_t; defined only
//				in secondary indexes and in the insert buffer
//				tree */
//#define PAGE_HEADER_PRIV_END 26	/* end of private data structure of the page
//				header which are set in a page create */
///*----*/
//#define	PAGE_LEVEL	 26	/* level of the node in an index tree; the
//				leaf level is the level 0.  This field should
//				not be written to after page creation. */
//#define	PAGE_INDEX_ID	 28	/* index id where the page belongs.
//				This field should not be written to after
//				page creation. */
//
//#ifndef UNIV_INNOCHECKSUM
//
//#define PAGE_BTR_SEG_LEAF 36	/* file segment header for the leaf pages in
//				a B-tree: defined only on the root page of a
//				B-tree, but not in the root of an ibuf tree */
//#define PAGE_BTR_IBUF_FREE_LIST	PAGE_BTR_SEG_LEAF
//#define PAGE_BTR_IBUF_FREE_LIST_NODE PAGE_BTR_SEG_LEAF
//				/* in the place of PAGE_BTR_SEG_LEAF and _TOP
//				there is a free list base node if the page is
//				the root page of an ibuf tree, and at the same
//				place is the free list node if the page is in
//				a free list */
//#define PAGE_BTR_SEG_TOP (36 + FSEG_HEADER_SIZE)
//				/* file segment header for the non-leaf pages
//				in a B-tree: defined only on the root page of
//				a B-tree, but not in the root of an ibuf
//				tree */
@Data
@EqualsAndHashCode(callSuper = true)
public class PageHeader extends Common<PageHeader> {

    public short PAGE_N_DIR_SLOTS;

    public short PAGE_HEAP_TOP;

    public short PAGE_N_HEAP;

    public short PAGE_FREE;

    public short PAGE_GARBAGE;

    public short PAGE_LAST_INSERT;

    public short PAGE_DIRECTION;

    public short PAGE_N_DIRECTION;

    public short PAGE_N_RECS;

    public long PAGE_MAX_TRX_ID;

    // PAGE_HEADER_PRIV_END
    public short PAGE_LEVEL;

    public long PAGE_INDEX_ID;

    // 10 byte
    public SegmentHeader PAGE_BTR_SEG_LEAF;

    // 10 byte
    public SegmentHeader PAGE_BTR_SEG_TOP;

    @Override
    protected PageHeader doDecodeBytes(PageHeader pageHeader, MappedByteBuffer ibd) {

        PAGE_N_DIR_SLOTS = ibd.getShort();
        PAGE_HEAP_TOP = ibd.getShort();
        PAGE_N_HEAP = ibd.getShort();
        PAGE_FREE = ibd.getShort();
        PAGE_GARBAGE = ibd.getShort();

        PAGE_LAST_INSERT = ibd.getShort();
        PAGE_DIRECTION = ibd.getShort();
        PAGE_N_DIRECTION = ibd.getShort();

        PAGE_N_RECS = ibd.getShort();
        PAGE_MAX_TRX_ID = ibd.getLong();

        PAGE_LEVEL = ibd.getShort();
        PAGE_INDEX_ID = ibd.getLong();

        PAGE_BTR_SEG_LEAF = new SegmentHeader().decodeBytes(ibd);
        PAGE_BTR_SEG_TOP = new SegmentHeader().decodeBytes(ibd);
        return pageHeader;
    }
}
