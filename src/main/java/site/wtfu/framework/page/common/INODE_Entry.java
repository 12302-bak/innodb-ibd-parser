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
 * storage/innobase/include/fsp0fsp.h
 */
///*-------------------------------------*/
//#define	FSEG_ID			0	/* 8 bytes of segment id: if this is 0,
//					it means that the header is unused */
//#define FSEG_NOT_FULL_N_USED	8
//					/* number of used segment pages in
//					the FSEG_NOT_FULL list */
//#define	FSEG_FREE		12
//					/* list of free extents of this
//					segment */
//#define	FSEG_NOT_FULL		(12 + FLST_BASE_NODE_SIZE)
//					/* list of partially free extents */
//#define	FSEG_FULL		(12 + 2 * FLST_BASE_NODE_SIZE)
//					/* list of full extents */
//#define	FSEG_MAGIC_N		(12 + 3 * FLST_BASE_NODE_SIZE)
//					/* magic number used in debugging */
//#define	FSEG_FRAG_ARR		(16 + 3 * FLST_BASE_NODE_SIZE)
//					/* array of individual pages
//					belonging to this segment in fsp
//					fragment extent lists */
//#define FSEG_FRAG_ARR_N_SLOTS	(FSP_EXTENT_SIZE / 2)
//					/* number of slots in the array for
//					the fragment pages */
//#define	FSEG_FRAG_SLOT_SIZE	4	/* a fragment page slot contains its
//					page number within space, FIL_NULL
//					means that the slot is not in use */
@Data
@EqualsAndHashCode(callSuper = true)
public class INODE_Entry extends Common<INODE_Entry> {

    public long FSEG_ID;

    public int FSEG_NOT_FULL_N_USED;

    // 16
    public ListBaseNode FSEG_FREE;
    public ListBaseNode FSEG_NOT_FULL;
    public ListBaseNode FSEG_FULL;

    public int FSEG_MAGIC_N;


    // 32 零散页面，每个结构一共4个字节，表示一个零散页面的页号。
    public int[]  FSEG_FRAG;


    @Override
    protected INODE_Entry doDecodeBytes(INODE_Entry inodeEntry, MappedByteBuffer ibd) {
        FSEG_ID = ibd.getLong();
        FSEG_NOT_FULL_N_USED = ibd.getInt();

        FSEG_FREE = new ListBaseNode().decodeBytes(ibd);
        FSEG_NOT_FULL = new ListBaseNode().decodeBytes(ibd);
        FSEG_FULL = new ListBaseNode().decodeBytes(ibd);

        FSEG_MAGIC_N = ibd.getInt();

        FSEG_FRAG = new int[32];
        for (int i = 0; i < FSEG_FRAG.length; i++) {
            FSEG_FRAG[i] = ibd.getInt();
        }
        return inodeEntry;
    }
}
