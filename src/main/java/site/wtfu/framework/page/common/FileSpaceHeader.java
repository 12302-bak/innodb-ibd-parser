package site.wtfu.framework.page.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import site.wtfu.framework.common.Common;

import java.nio.MappedByteBuffer;

/**
 *
 * Copyright https://wtfu.site Inc. All Rights Reserved.
 *
 * @date 2026/4/20
 *                          @since  1.0
 *                          @author 12302
 *
 * storage/innobase/include/fsp0fsp.h
 *
 */
///*			SPACE HEADER
//			============
//
//File space header data structure: this data structure is contained in the
//first page of a space. The space for this header is reserved in every extent
//descriptor page, but used only in the first. */
//
///*-------------------------------------*/
//#define FSP_SPACE_ID		0	/* space id */
//#define FSP_NOT_USED		4	/* this field contained a value up to
//					which we know that the modifications
//					in the database have been flushed to
//					the file space; not used now */
//#define	FSP_SIZE		8	/* Current size of the space in
//					pages */
//#define	FSP_FREE_LIMIT		12	/* Minimum page number for which the
//					free list has not been initialized:
//					the pages >= this limit are, by
//					definition, free; note that in a
//					single-table tablespace where size
//					< 64 pages, this number is 64, i.e.,
//					we have initialized the space
//					about the first extent, but have not
//					physically allocated those pages to the
//					file */
//#define	FSP_SPACE_FLAGS		16	/* fsp_space_t.flags, similar to
//					dict_table_t::flags */
//#define	FSP_FRAG_N_USED		20	/* number of used pages in the
//					FSP_FREE_FRAG list */
//#define	FSP_FREE		24	/* list of free extents */
//#define	FSP_FREE_FRAG		(24 + FLST_BASE_NODE_SIZE)
//					/* list of partially free extents not
//					belonging to any segment */
//#define	FSP_FULL_FRAG		(24 + 2 * FLST_BASE_NODE_SIZE)
//					/* list of full extents not belonging
//					to any segment */
//#define FSP_SEG_ID		(24 + 3 * FLST_BASE_NODE_SIZE)
//					/* 8 bytes which give the first unused
//					segment id */
//#define FSP_SEG_INODES_FULL	(32 + 3 * FLST_BASE_NODE_SIZE)
//					/* list of pages containing segment
//					headers, where all the segment inode
//					slots are reserved */
//#define FSP_SEG_INODES_FREE	(32 + 4 * FLST_BASE_NODE_SIZE)
//					/* list of pages containing segment
//					headers, where not all the segment
//					header slots are reserved */
///*-------------------------------------*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FileSpaceHeader extends Common<FileSpaceHeader> {

    /**
     * 表空间的ID
     */
    public int FSP_SPACE_ID;

    /**
     * 这4个字节未被使用，可以忽略
     */
    public int FSP_NOT_USED;

    /**
     * 当前表空间占有的页面数
     */
    public int FSP_SIZE;

    /**
     * 尚未被初始化的最小页号，大于或等于这个页号的区对应的XDES Entry结构都没有被加入FREE链表
     */
    public int FSP_FREE_LIMIT;

    /**
     * 表空间的一些占用存储空间比较小的属性
     */
    public int FSP_SPACE_FLAGS;

    /**
     * FREE_FRAG链表中已使用的页面数量
     */
    public int FSP_FRAG_N_USED;

    /**
     * FREE链表的基节点 16
     */
    public ListBaseNode FSP_FREE;

    /**
     * FREE_FRAG链表的基节点 16
     */
    public ListBaseNode FSP_FREE_FRAG;

    /**
     * FULL_FRAG链表的基节点 16
     */
    public ListBaseNode FSP_FULL_FRAG;

    /**
     * 当前表空间中下一个未使用的 Segment ID
     */
    public long FSP_SEG_ID;

    /**
     * SEG_INODES_FULL链表的基节点 16
     */
    public ListBaseNode FSP_SEG_INODES_FULL;

    /**
     * SEG_INODES_FREE链表的基节点 16
     */
    public ListBaseNode FSP_SEG_INODES_FREE;


    @Override
    protected FileSpaceHeader doDecodeBytes(FileSpaceHeader fileSpaceHeader, MappedByteBuffer ibd) {

        FSP_SPACE_ID = ibd.getInt();
        FSP_NOT_USED = ibd.getInt();
        FSP_SIZE = ibd.getInt();
        FSP_FREE_LIMIT = ibd.getInt();
        FSP_SPACE_FLAGS = ibd.getInt();
        FSP_FRAG_N_USED = ibd.getInt();
        FSP_FREE = new ListBaseNode().decodeBytes(ibd);
        FSP_FREE_FRAG = new ListBaseNode().decodeBytes(ibd);
        FSP_FULL_FRAG = new ListBaseNode().decodeBytes(ibd);
        FSP_SEG_ID = ibd.getLong();
        FSP_SEG_INODES_FULL = new ListBaseNode().decodeBytes(ibd);
        FSP_SEG_INODES_FREE = new ListBaseNode().decodeBytes(ibd);

        return fileSpaceHeader;
    }
}
