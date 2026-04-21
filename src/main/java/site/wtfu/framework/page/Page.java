package site.wtfu.framework.page;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import site.wtfu.framework.common.Common;
import site.wtfu.framework.common.ConstVal;
import site.wtfu.framework.page.common.FileHeader;
import site.wtfu.framework.page.common.FileTrailer;
import site.wtfu.framework.page.impl.*;

import java.nio.MappedByteBuffer;

/**
 *
 * Copyright https://wtfu.site Inc. All Rights Reserved.
 *
 * @date 2026/4/20
 *                          @since  1.0
 *                          @author 12302
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Page extends Common<Page> {

    public FileHeader fileHeader;

    private Object body;

    public FileTrailer fileTrailer;

    @Override
    protected Page doDecodeBytes(Page page, MappedByteBuffer ibd) {
        fileHeader = new FileHeader().decodeBytes(ibd);
        switch (fileHeader.getFIL_PAGE_TYPE()){
            case FIL_PAGE_TYPE_FSP_HDR:
                body = new FSP_HDR_PageImpl().decodeBytes(ibd); break;
            case FIL_PAGE_TYPE_XDES:
                body = new XDES_PageImpl().decodeBytes(ibd); break;
            case FIL_PAGE_INODE:
                body = new INODE_PageImpl().decodeBytes(ibd); break;
            case FIL_PAGE_TYPE_SYS:
                body = new SYS_PageImpl().decodeBytes(ibd); break;
            case FIL_PAGE_INDEX:
                body = new INDEX_PageImpl().decodeBytes(ibd); break;
            case FIL_PAGE_TYPE_ALLOCATED:
            default:
                decodeBody(ibd);
        }
        fileTrailer = new FileTrailer().decodeBytes(ibd);
        return page;
    }

    protected void decodeBody(MappedByteBuffer ibd){
        byte[] data = new byte[ConstVal.PAGE_SIZE - ConstVal.fil_header_length - ConstVal.fil_trailer_length]; ibd.get(data);
        body = data;
    }

    @Override
    public String toString() {
        return "Page{" +
                "no=" + fileHeader.FIL_PAGE_OFFSET +
                ", space_id=" + fileHeader.FIL_PAGE_ARCH_LOG_NO_OR_SPACE_ID +
                ", type=" + fileHeader.FIL_PAGE_TYPE +
                '}';
    }
}
