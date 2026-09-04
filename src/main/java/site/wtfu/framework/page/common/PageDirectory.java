package site.wtfu.framework.page.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import site.wtfu.framework.common.Common;
import site.wtfu.framework.common.ConstVal;
import site.wtfu.framework.utils.AlignmentUtil;

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
// typedef	byte			page_dir_slot_t;
//typedef page_dir_slot_t		page_dir_t;
//
///* Offset of the directory start down from the page end. We call the
//slot with the highest file address directory start, as it points to
//the first record in the list of records. */
//#define	PAGE_DIR		FIL_PAGE_DATA_END
//
///* We define a slot in the page directory as two bytes */
//#define	PAGE_DIR_SLOT_SIZE	2
@Data
@EqualsAndHashCode(callSuper = true)
public class PageDirectory extends Common<PageDirectory> {

    private final short PAGE_N_DIR_SLOTS;

    // #define	PAGE_DIR_SLOT_SIZE	2
    public short[] slots;

    public PageDirectory(short PAGE_N_DIR_SLOTS){
        this.PAGE_N_DIR_SLOTS = PAGE_N_DIR_SLOTS;
    }

    @Override
    protected PageDirectory doDecodeBytes(PageDirectory pageDirectory, MappedByteBuffer ibd) {
        int slots_block = PAGE_N_DIR_SLOTS * 2;
        int position = AlignmentUtil.align(ibd.position()) - ConstVal.fil_trailer_length - slots_block;
        ibd.position(position);
        _section.setStart(position);

        slots = new short[PAGE_N_DIR_SLOTS];
        for (int i = 0; i < slots.length; i++) {
            slots[i] = ibd.getShort();
        }
        return pageDirectory;
    }
}
