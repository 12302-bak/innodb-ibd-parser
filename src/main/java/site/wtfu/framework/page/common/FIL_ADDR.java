package site.wtfu.framework.page.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import site.wtfu.framework.common.Common;

import java.nio.MappedByteBuffer;

/**
 *
 * Copyright https://wtfu.site Inc. All Rights Reserved.
 *
 * @date 2026/4/20
 *                          @since  1.0
 *                          @author 12302
 * storage/innobase/include/fil0fil.h
 */
///** File space address */
//struct fil_addr_t {
//	ulint	page;		/*!< page number within a space */
//	ulint	boffset;	/*!< byte offset within the page */
//};
@Data
@EqualsAndHashCode(callSuper = true)
public class FIL_ADDR extends Common<FIL_ADDR> {

    public int page;

    public short offset;

    @Override
    protected FIL_ADDR doDecodeBytes(FIL_ADDR filAddr, MappedByteBuffer ibd) {
        page = ibd.getInt();
        offset = ibd.getShort();
        return filAddr;
    }
}
