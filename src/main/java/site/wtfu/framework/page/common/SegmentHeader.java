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
 * storage/innobase/include/fsp0types.h
 */
//typedef	byte	fseg_header_t;
//
//#define FSEG_HDR_SPACE		0	/*!< space id of the inode */
//#define FSEG_HDR_PAGE_NO	4	/*!< page number of the inode */
//#define FSEG_HDR_OFFSET		8	/*!< byte offset of the inode */
@Data
@EqualsAndHashCode(callSuper = true)
public class SegmentHeader extends Common<SegmentHeader> {

    public int FSEG_HDR_SPACE;
    public int FSEG_HDR_PAGE_NO;
    public short FSEG_HDR_OFFSET;

    @Override
    protected SegmentHeader doDecodeBytes(SegmentHeader segmentHeader, MappedByteBuffer ibd) {
        FSEG_HDR_SPACE = ibd.getInt();
        FSEG_HDR_PAGE_NO = ibd.getInt();
        FSEG_HDR_OFFSET = ibd.getShort();
        return segmentHeader;
    }
}
