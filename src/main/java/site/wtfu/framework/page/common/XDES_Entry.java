package site.wtfu.framework.page.common;

import lombok.Data;
import site.wtfu.framework.common.Common;

import java.nio.MappedByteBuffer;

/**
 *
 * Copyright https://wtfu.site Inc. All Rights Reserved.
 *
 * @date 2026/4/20
 *                          @since  1.0
 *                          @author 12302
 * storage/innobase/include/fsp0fsp.h
 *
 */
///* @defgroup Extent Descriptor Constants (moved from fsp0fsp.c) @{ */
//
///*			EXTENT DESCRIPTOR
//			=================
//
//File extent descriptor data structure: contains bits to tell which pages in
//the extent are free and which contain old tuple version to clean. */
//
///*-------------------------------------*/
//#define	XDES_ID			0	/* The identifier of the segment
//					to which this extent belongs */
//#define XDES_FLST_NODE		8	/* The list node data structure
//					for the descriptors */
//#define	XDES_STATE		(FLST_NODE_SIZE + 8)
//					/* contains state information
//					of the extent */
//#define	XDES_BITMAP		(FLST_NODE_SIZE + 12)
//					/* Descriptor bitmap of the pages
//					in the extent */
///*-------------------------------------*/
@Data
public class XDES_Entry extends Common<XDES_Entry> {

    public long XDES_ID;

    ///* We define the field offsets of a node for the list */
    //#define FLST_PREV	0	/* 6-byte address of the previous list element;
    //				the page part of address is FIL_NULL, if no
    //				previous element */
    //#define FLST_NEXT	FIL_ADDR_SIZE	/* 6-byte address of the next
    //				list element; the page part of address
    //				is FIL_NULL, if no next element */
    public FIL_ADDR[] XDES_FLST_NODE = new FIL_ADDR[2];

    public int XDES_STATE;

    // 16 bytes
    public byte[] XDES_BITMAP;

    @Override
    protected XDES_Entry doDecodeBytes(XDES_Entry xdesEntry, MappedByteBuffer ibd) {
        XDES_ID = ibd.getLong();

        XDES_FLST_NODE[0] = new FIL_ADDR().decodeBytes(ibd);
        XDES_FLST_NODE[1] = new FIL_ADDR().decodeBytes(ibd);

        XDES_STATE = ibd.getInt();
        XDES_BITMAP = new byte[16]; ibd.get(XDES_BITMAP);
        return xdesEntry;
    }
}
